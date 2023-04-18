package fr.iutna.maio.block

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
import fr.iutna.maio.engine.tiles.PhysicTile
import fr.iutna.maio.engine.util.SpriteUtil

/**
 * Block abstract class (implement PhysicTile)
 * Make it possible to initialize a Block without passing the sprite,
 * the sprite is initialized in the child classes, depending on the type of block
 */
abstract class Block(
    x: Int = 0,
    y: Int = 0
) : PhysicTile(SpriteUtil.initEmptySprite(), x, y) {}
