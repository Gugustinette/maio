package fr.iutna.maio.map

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
// KorIO Xml
import com.soywiz.korio.serialization.xml.*

// SpriteUtil
import fr.iutna.maio.engine.util.SpriteUtil

// Engine
import fr.iutna.maio.engine.tiles.Tile

// Tiles
import fr.iutna.maio.environment.tiles.factory.TypeTile
import fr.iutna.maio.environment.tiles.factory.FactoryTile

// Blocks
import fr.iutna.maio.block.factory.TypeBlock
import fr.iutna.maio.block.factory.FactoryBlock

// Monster
import fr.iutna.maio.environment.monster.Monster
import fr.iutna.maio.environment.monster.factory.TypeMonster
import fr.iutna.maio.environment.monster.factory.FactoryMonster

// Maio
import fr.iutna.maio.maio.Maio

/**
 * Map
 * Help to read the map
 */
class Map() {
    /**
     * Attributes
     */
    // Map width
    var mapWidth: Int = 0
    // Map height
    var mapHeight: Int = 0
    // Map Matrix
    var mapMatrix: Array<Array<Tile>> = arrayOf()
    // Monster array
    var monsterArray: Array<Monster> = arrayOf()
    // Misc array
    var miscArray: Array<Tile> = arrayOf()

    /**
     * Initialize the map
     */
    suspend fun initMap(container: Container, mapPath: String) {
        // Read the XML file
        val map = resourcesVfs[mapPath].readXml()

        // Get the map width
        mapWidth = map.child("width")?.innerXml?.toInt() ?: error("No width node found")
        // Get the map height
        mapHeight = map.child("height")?.innerXml?.toInt() ?: error("No height node found")

        // Initialize the map matrix
        mapMatrix = Array(mapWidth) { Array(mapHeight) { Tile(
            SpriteUtil.initEmptySprite(),
            0,
            0
        ) } }


        /**
         * Factories
         */
        // Tile factory
        val factoryTile = FactoryTile()
        // Give container context to the factory
        factoryTile.container = container

        // Block factory
        val factoryBlock = FactoryBlock()
        // Give container context to the factory
        factoryBlock.container = container

        // Monster factory
        val factoryMonster = FactoryMonster()
        // Give container context to the factory
        factoryMonster.container = container

        // Create invisible wall at the left of the map
        for (i in 0 until mapHeight) {
            val newInvisibleBlock = factoryBlock.createBlock(TypeBlock.EMPTY, -1, i)
            // Add the block to the misc array
            miscArray += newInvisibleBlock
        }

        // File the matrix with TypeTile.BACKGROUND tiles
        for (i in 0 until mapWidth) {
            // File the 2 last cells of each row with TypeBlock.GROUND blocks
            for (j in mapHeight - 2 until mapHeight) {
                mapMatrix[i][j] = factoryBlock.createBlock(TypeBlock.GROUND, i, j)
            }
            // File the rest of the cells with TypeTile.BACKGROUND tiles
            for (j in 0 until mapHeight - 2) {
                mapMatrix[i][j] = factoryTile.createTile(TypeTile.BACKGROUND, i, j)
            }
        }

        /**
         * Tiles
         */
        // Read the tiles parent element
        val tilesElement = map.child("tiles") ?: error("No tiles node found")
        // Read the tiles inside the parent element
        val tiles = tilesElement.children("tile")
        // For each tile
        for (tile in tiles) {
            // Get the tile type
            val type = tile.getString("type") ?: error("No type attribute found")

            // Get the tile x
            val x = tile.getInt("x") ?: error("No x attribute found")

            // Get the tile y
            val y = tile.getInt("y") ?: error("No y attribute found")

            // Create the tile
            val newTile = factoryTile.createTile(TypeTile.valueOf(type), x, y)

            // Remove the tile from the map matrix
            container.removeChild(mapMatrix[x][y])

            // Add the tile to the map matrix
            mapMatrix[x][y] = newTile
        }

        /**
         * Blocks
         */
        // Read the blocks parent element
        val blocksElement = map.child("blocks") ?: error("No blocks node found")
        // Read the blocks inside the parent element
        val blocks = blocksElement.children("block")
        // For each block
        for (block in blocks) {
            // Get the block type
            val type = block.getString("type") ?: error("No type attribute found")

            // Get the block x
            val x = block.getInt("x") ?: error("No x attribute found")

            // Get the block y
            val y = block.getInt("y") ?: error("No y attribute found")

            // Create the block
            val newBlock = factoryBlock.createBlock(TypeBlock.valueOf(type), x, y)

            // Remove the tile from the map matrix
            container.removeChild(mapMatrix[x][y])

            // Add the block to the map matrix
            mapMatrix[x][y] = newBlock
        }

        /**
         * Monsters
         */
        // Initialize the monster array
        monsterArray = arrayOf()
        // Read the monsters parent element
        val monstersElement = map.child("monsters") ?: error("No monsters node found")
        // Read the monsters inside the parent element
        val monsters = monstersElement.children("monster")
        // For each monster
        for (monster in monsters) {
            // Get the monster type
            val type = monster.getString("type") ?: error("No type attribute found")

            // Get the monster x
            val x = monster.getInt("x") ?: error("No x attribute found")

            // Get the monster y
            val y = monster.getInt("y") ?: error("No y attribute found")

            // Create the monster
            val newMonster = factoryMonster.createMonster(TypeMonster.valueOf(type), x, y)

            // Add the monster to the monster array
            monsterArray += newMonster
        }
    }

    /**
     * Initialize camera effect
     */
    suspend fun initCamera(container: Container, maio: Maio) {
        // Treshold
        val treshold_left = 300.0
        val treshold_right = 600.0
        val map_right_border = 512*2 - 64
        val map_left_border = 0.0
        // Compensate the camera effect
        val compensate = 1.0
		// Add a key listener
		container.addUpdater { _: TimeSpan ->
            // If maio is approaching the right border
            if (maio.x >= treshold_right - maio.width) {
                // If lastest tiles are not visible
                val tile = mapMatrix[mapWidth - 1][0]
                if (tile.x > map_right_border) {
                    // Move all the sprites to the left
                    for (i in 0 until mapWidth) {
                        for (j in 0 until mapHeight) {
                            mapMatrix[i][j].x -= maio.moveSpeed
                        }
                    }
                    for (monster in monsterArray) {
                        monster.x -= maio.moveSpeed
                    }
                    for (misc in miscArray) {
                        misc.x -= maio.moveSpeed
                    }
                    maio.x = treshold_right - maio.width - compensate
                }
            }
            // If maio is approaching the left border
            if (maio.x <= treshold_left) {
                // If first tiles are not visible
                val tile = mapMatrix[0][0]
                if (tile.x < map_left_border) {
                    // Move all the sprites to the right
                    for (i in 0 until mapWidth) {
                        for (j in 0 until mapHeight) {
                            mapMatrix[i][j].x += maio.moveSpeed
                        }
                    }
                    for (monster in monsterArray) {
                        monster.x += maio.moveSpeed
                    }
                    for (misc in miscArray) {
                        misc.x += maio.moveSpeed
                    }
                    maio.x = treshold_left + compensate
                }
            }
		}
    }

    /**
     * Reset the map
     */
    fun reset(container: Container) {
        // Delete all the sprites
        for (i in 0 until mapWidth) {
            for (j in 0 until mapHeight) {
                container.removeChild(mapMatrix[i][j])
            }
        }
        for (monster in monsterArray) {
            container.removeChild(monster)
        }
        for (misc in miscArray) {
            container.removeChild(misc)
        }
    }
}
