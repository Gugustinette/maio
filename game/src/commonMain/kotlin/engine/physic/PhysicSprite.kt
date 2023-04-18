package fr.iutna.maio.engine.physic

import com.soywiz.klock.*
import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korev.Key
import com.soywiz.korge.input.*
import kotlin.math.*
import fr.iutna.maio.engine.tiles.PhysicTile
import fr.iutna.maio.engine.util.SpriteUtil
import fr.iutna.maio.engine.enum.DirectionEnum
import fr.iutna.maio.engine.physic.states.PhysicState
import fr.iutna.maio.engine.physic.states.StateStand

/**
 * PhysicSprite class that extends Sprite
 * It is allready handling the gravity and the jump
 */
open class PhysicSprite(
    sprite: SpriteAnimation
) : Sprite(
    sprite
){
	/**
	 * State
	 */
    // State
    open var state : PhysicState = StateStand(this)

    /**
     * Time
     */
    // Delta
    open var delta = 1.0

	/**
	 * Physics attributes
	 */
	// Mass
	private var mass = 1.0
	// Vertical change
	open var dy = 0.0

	// Constants
	// Thrust (Jump power)
	open var thrust = 20.0
	// G Constants (Gravity)
	private val g = 6.67408 / (10.0)

    /**
     * Initialize PhysicSprite
     */
    open suspend fun initInContainer(container: Container, x: Int, y: Int) {
        // Add the sprite to the container
        container.addChild(this)

		// Set the sprite position
		xy(x, y)

		// Add a key listener
		addUpdater { timespan: TimeSpan ->
			delta = timespan / 16.milliseconds
			/**
			 * Physics
			 */
			dy += g * delta
			super.y += dy
		}

		// OnCollision
		onCollision {
			// If the collision is with a tile
			if (it is PhysicTile) {
				// Get direction from the tile
				val direction = SpriteUtil.getDirectionFromSpriteToSprite(this as Sprite, it as Sprite)
				// Debug
				// SpriteUtil.Debug(container, this as Sprite, Colors.RED, "Sprite1")
				// SpriteUtil.Debug(container, it as Sprite, Colors.GREEN, "Sprite2")

				// If direction is up
				if (direction == DirectionEnum.UP && dy > 0) {
					// Make sure the sprite is on the tile
					super.y = it.y - super.height
					// Maio has touched the ground, change state
                    state = StateStand(this as PhysicSprite)
					// Set the vertical change to 0
					dy = 0.0
				}
				// If direction is down
				if (direction == DirectionEnum.DOWN) {
					// Make sure the sprite is below the tile
					super.y = it.y + it.height
					// If vertical change is negative, set it to 0
					if (dy < 0) {
						dy = 0.0
					}
				}
				// If direction is left
				if (direction == DirectionEnum.LEFT) {
					// Make sure the sprite is on the left of the tile
					super.x = it.x - super.width
				}
				// If direction is right
				if (direction == DirectionEnum.RIGHT) {
					// Make sure the sprite is on the right of the tile
					super.x = it.x + it.width
				}
			}
		}
    }

    /**
     * Make Sprite jump
     */
    fun jump() {
        // Call the jump function on state
        state.jump()
    }
}
