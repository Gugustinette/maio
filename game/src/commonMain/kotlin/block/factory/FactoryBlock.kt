package fr.iutna.maio.block.factory

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

import fr.iutna.maio.block.Block
import fr.iutna.maio.block.BlockEmpty
import fr.iutna.maio.block.BlockBrick
import fr.iutna.maio.block.BlockBrickTop
import fr.iutna.maio.block.BlockGround
import fr.iutna.maio.block.BlockObstacle
import fr.iutna.maio.block.BlockObjectOn
import fr.iutna.maio.block.BlockObjectOff
import fr.iutna.maio.block.pipe.PipeTopLeft
import fr.iutna.maio.block.pipe.PipeTopRight
import fr.iutna.maio.block.pipe.PipeBodyLeft
import fr.iutna.maio.block.pipe.PipeBodyRight

/**
 * Factory of block
 */
class FactoryBlock() : AbstractFactoryBlock() {
    /**
     * Container context
     */
    override lateinit var container: Container

    /**
     * Create a block
     */
    override suspend fun createBlock(type: TypeBlock, x: Int, y: Int): Block {
        // Initialize a block
        val block = when (type) {
            TypeBlock.EMPTY -> BlockEmpty(64 * x, 64 * y)
            TypeBlock.GROUND -> BlockGround(64 * x, 64 * y)
            TypeBlock.BRICK -> BlockBrick(64 * x, 64 * y)
            TypeBlock.BRICK_TOP -> BlockBrickTop(64 * x, 64 * y)
            TypeBlock.OBSTACLE -> BlockObstacle(64 * x, 64 * y)
            TypeBlock.OBJECT_ON -> BlockObjectOn(64 * x, 64 * y)
            TypeBlock.OBJECT_OFF -> BlockObjectOff(64 * x, 64 * y)
            TypeBlock.PIPE_TOP_LEFT -> PipeTopLeft(64 * x, 64 * y)
            TypeBlock.PIPE_TOP_RIGHT -> PipeTopRight(64 * x, 64 * y)
            TypeBlock.PIPE_BODY_LEFT -> PipeBodyLeft(64 * x, 64 * y)
            TypeBlock.PIPE_BODY_RIGHT -> PipeBodyRight(64 * x, 64 * y)
        }
        // Init the tile in the container
        block.initInContainer(container)
        // Return block
        return block
    }
}
