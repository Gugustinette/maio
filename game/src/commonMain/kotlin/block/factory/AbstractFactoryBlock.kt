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

/**
 * Abstract class for the creation of blocks
 */
abstract class AbstractFactoryBlock() {
    /**
     * Container context
     */
    abstract val container: Container

    /**
     * Create a block
     */
    abstract suspend fun createBlock(type: TypeBlock, x: Int, y: Int): Block
}
