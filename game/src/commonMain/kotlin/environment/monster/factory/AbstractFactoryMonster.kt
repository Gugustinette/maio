package fr.iutna.maio.environment.monster.factory

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

import fr.iutna.maio.environment.monster.Monster

/**
 * Abstract class for the creation of Monsters
 */
abstract class AbstractFactoryMonster() {
    /**
     * Container context
     */
    abstract val container: Container

    /**
     * Create a Monster
     */
    abstract suspend fun createMonster(type: TypeMonster, x: Int, y: Int): Monster
}
