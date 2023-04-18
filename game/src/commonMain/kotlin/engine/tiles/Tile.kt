package fr.iutna.maio.engine.tiles

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

/**
 * Tile class
 */
open class Tile(
    sprite: SpriteAnimation,
    xPos: Int = 0,
    yPos: Int = 0
) : Sprite(
    sprite
){
    /**
     * Attributes
     */
    // x position
    var xPos = xPos
    // y position
    var yPos = yPos

    /**
     * Initialize Tile
     */
    open suspend fun initInContainer(container: Container) {
        // Add the sprite to the container
        container.addChild(this)

		// Set the sprite position
		xy(this.xPos, this.yPos)
    }
}
