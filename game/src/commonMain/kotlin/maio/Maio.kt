package fr.iutna.maio.maio

import fr.iutna.maio.engine.util.SpriteUtil
import fr.iutna.maio.engine.physic.PhysicSprite
import fr.iutna.maio.engine.tiles.Tile
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korev.Key
import com.soywiz.klock.*
import com.soywiz.korio.file.std.*
import com.soywiz.korim.format.*
import com.soywiz.korim.bitmap.*
import kotlin.math.*

/**
 * Maio class
 */
class Maio : PhysicSprite(
    SpriteUtil.initEmptySprite()
) {
	/**
	 * Attributes
	 */
	// Move Speed
	var moveSpeed = 8
	// Debug Text
	var maioDebugText: Text = Text("")
	// Function to store for the death callback
	var deathCallback: () -> Unit = {}
	// Function to store for the win callback
	var winCallback: () -> Unit = {}

	/**
	 * Sprites
	 */
	// IDLE Sprite
	private var maioIdleSprite: SpriteAnimation = SpriteUtil.initEmptySprite()
	// Running Animation
	private var maioRunningAnimation_Right: SpriteAnimation = SpriteUtil.initEmptySprite()
	private var maioRunningAnimation_Left: SpriteAnimation = SpriteUtil.initEmptySprite()

	/**
	 * Initialize Maio
	 */
	suspend fun initInContainer(container: Container, input: Input) {
		/**
		 * Load Sprites
		 */
		// IDLE Sprite
		maioIdleSprite = SpriteUtil.initSprite("scaledSprites/maio/maio.png", 1, 1, false)
		// Running Animation Right
		maioRunningAnimation_Right = SpriteUtil.initSprite("scaledSprites/maio/animations/maio-walk.png", 3, 1, false)
		// Running Animation Left (Flip the right animation)
		maioRunningAnimation_Left = SpriteUtil.initSprite("scaledSprites/maio/animations/maio-walk.png", 3, 1, true)

		// Initialize PhysicSprite
		super.initInContainer(container, 64 * 4, 0)

		// Add a key listener
		addUpdater { _: TimeSpan ->
			if (input.keys[Key.LEFT]) {
				x -= moveSpeed * delta
				// Play Maio Running Animation
				playAnimationLooped(maioRunningAnimation_Left)
			}
			if (input.keys[Key.RIGHT]) {
				x += moveSpeed * delta
				// Play Maio Running Animation
				playAnimationLooped(maioRunningAnimation_Right)
			}
			// If none of the horizontal keys are pressed, play the idle animation
			if (!input.keys[Key.LEFT] && !input.keys[Key.RIGHT]) {
				playAnimationLooped(maioIdleSprite)
			}
			if (input.keys[Key.UP]) {
				// y -= 1 * delta
				super.jump()
			}
			if (input.keys[Key.DOWN]) {
				// y += 1 * delta
			}
			// If Maio is below the screen
			if (y > 64 * 15.0) {
				// Call the death callback
				deathCallback()
			}
			// If Maio has won
			if (x > 900) {
				// Call the win callback
				winCallback()
			}
			// Debug
			// debug(container)
		}
	}

	/**
	 * Attach a callback to the death of Maio
	 * @param callback The callback to call when Maio dies
	 */
	fun attachDeathCallback(callback: () -> Unit) {
		deathCallback = callback
	}

	/**
	 * Attach a callback to the win of Maio
	 * @param callback The callback to call when Maio wins
	 */
	fun attachWinCallback(callback: () -> Unit) {
		winCallback = callback
	}

	/**
	 * Debug function
	 */
	fun debug(container: Container) {
		// Remove the texts
		maioDebugText.removeFromParent()
		// Display Maio's position on the screen
		maioDebugText = container.text(
			"Maio's position: (${x.toInt()}, ${y.toInt()})\n" +
			"Maio's grid position: (${(x / 64).toInt()}, ${(y / 64).toInt()})"
		)
	}
}
