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

import fr.iutna.maio.environment.tiles.BackgroundTile
import fr.iutna.maio.environment.tiles.bush.BushTileLeft
import fr.iutna.maio.environment.tiles.bush.BushTileCenter
import fr.iutna.maio.environment.tiles.bush.BushTileRight
import fr.iutna.maio.environment.tiles.flag.FlagTileBody
import fr.iutna.maio.environment.tiles.flag.FlagTileHead

/**
 * Factory of tiles
 */
class FactoryTile() : AbstractFactoryTile() {
    /**
     * Container context
     */
    override lateinit var container: Container

    /**
     * Create a tile
     */
    override suspend fun createTile(type: TypeTile, x: Int, y: Int): Tile {
        // Initialize a tile
        val tile = when (type) {
            TypeTile.BACKGROUND -> BackgroundTile(64 * x, 64 * y)
            TypeTile.BUSH_LEFT -> BushTileLeft(64 * x, 64 * y)
            TypeTile.BUSH_CENTER -> BushTileCenter(64 * x, 64 * y)
            TypeTile.BUSH_RIGHT -> BushTileRight(64 * x, 64 * y)
            TypeTile.FLAG_BODY -> FlagTileBody(64 * x, 64 * y)
            TypeTile.FLAG_HEAD -> FlagTileHead(64 * x, 64 * y)
        }
        // Init the tile in the container
        tile.initInContainer(container)
        // Return tile
        return tile
    }
}
