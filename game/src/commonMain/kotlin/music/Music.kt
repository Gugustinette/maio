package fr.iutna.maio.music

import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import com.soywiz.korim.bitmap.*
// Music
import com.soywiz.korau.sound.*

/**
 * Music class
 * Load and play music
 */
class Music {
    // Attributes
    private var music: Sound? = null
	/**
	 * Initialize Music
	 */
	suspend fun initInContainer() {
        music = resourcesVfs["musics/cat.mp3"].readMusic()
        music?.play()
	}
}
