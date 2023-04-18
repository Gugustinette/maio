package fr.iutna.maio.environment.tiles.factory

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

/**
 * Abstract class for the creation of tiles
 */
abstract class AbstractFactoryTile() {
    /**
     * Container context
     */
    abstract val container: Container

    /**
     * Create a tile
     */
    abstract suspend fun createTile(type: TypeTile, x: Int, y: Int): Tile
}
