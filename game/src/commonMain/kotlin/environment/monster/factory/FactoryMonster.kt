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
import fr.iutna.maio.environment.monster.Goomba

/**
 * Factory of Monsters
 */
class FactoryMonster() : AbstractFactoryMonster() {
    /**
     * Container context
     */
    override lateinit var container: Container

    /**
     * Create a Monster
     */
    override suspend fun createMonster(type: TypeMonster, x: Int, y: Int): Monster {
        // Initialize a Monster
        val monster = when (type) {
            TypeMonster.GOOMBA -> Goomba(64 * x, 64 * y)
        }
        // Init the Monster in the container
        monster.initInContainer(container)
        // Return Monster
        return monster
    }
}
