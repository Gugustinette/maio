package fr.iutna.maio.environment.monster

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
 * Monster class
 */
open class Monster(
	xPos: Int = 0,
	yPos: Int = 0
) : PhysicSprite(
    SpriteUtil.initEmptySprite()
) {
    /**
     * Attributes
     */
    // x position
    var xPos = xPos
    // y position
    var yPos = yPos

	/**
	 * Initialize Monster
	 */
	open suspend fun initInContainer(container: Container) {
		// Initialize PhysicSprite
		super.initInContainer(container, xPos, yPos)
	}
}
