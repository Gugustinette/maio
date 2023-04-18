package fr.iutna.maio.block.pipe

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
import fr.iutna.maio.engine.tiles.Tile
import fr.iutna.maio.engine.util.SpriteUtil
import fr.iutna.maio.block.Block

/**
 * PipeBodyLeft class (implement Block)
 * Initialize the sprite with SpriteUtil
 */
class PipeBodyLeft(
    x: Int = 0,
    y: Int = 0
) : Block(x, y) {
    /**
     * Initialize PipeBodyLeft
     */
    override suspend fun initInContainer(container: Container) {
        // Super init
        super.initInContainer(container)
        // Set the sprite
        playAnimationLooped(
            SpriteUtil.initSprite("scaledSprites/environment/blocks/pipe-body-left.png", 1, 1, false)
        )
    }
}
