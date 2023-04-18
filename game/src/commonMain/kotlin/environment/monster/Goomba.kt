package fr.iutna.maio.environment.monster

import fr.iutna.maio.engine.util.SpriteUtil
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
 * Goomba class
 */
class Goomba(
	xPos: Int = 0,
	yPos: Int = 0
) : Monster(
	xPos,
	yPos
) {
	/**
	 * Sprites
	 */
	// Walk Animation
	private var goombaWalkAnimation: SpriteAnimation = SpriteUtil.initEmptySprite()

	/**
	 * Initialize Goomba
	 */
	override suspend fun initInContainer(container: Container) {
		/**
		 * Load Sprites
		 */
		// IDLE Sprite
		goombaWalkAnimation = SpriteUtil.initSprite("scaledSprites/environment/monster/goomba-walk.png", 2, 1, false)

		// Initialize Monster
		super.initInContainer(container)

        // Play Goomba Walk Animation
        playAnimationLooped(goombaWalkAnimation,
            TimeSpan(300.0)
        )
	}
}
