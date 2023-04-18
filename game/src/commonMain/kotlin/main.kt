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

// Inputs
import com.soywiz.korev.Key

// Util
import fr.iutna.maio.engine.util.SpriteUtil

// Maio
import fr.iutna.maio.maio.Maio

// Map
import fr.iutna.maio.map.Map

// Music
import fr.iutna.maio.music.Music

suspend fun main() = Korge(
	// Set the game resolution to 512x512
	width = 512*2, height = 512*2,
	// Original NES resolution is 256x224
	virtualWidth = 256*4, // 256*2=512
	virtualHeight = 224*4, // 224*2=448
	bgcolor = Colors["#2b2b2b"]
) {
	val sceneContainer = sceneContainer()

	sceneContainer.changeTo({ MyScene() })
}

class MyScene : Scene() {
	// Map instance
	private val map = Map()
	// Maio instance
	private val maio = Maio()
	// Music instance
	private val music = Music()

	override suspend fun SContainer.sceneMain() {
		// Initialize the map
		map.initMap(this, "maps/world1-1.xml")

		// Initialize Maio
		maio.initInContainer(this, input)

		// Initialize the camera
		map.initCamera(this, maio)

		// Initialize the music
		music.initInContainer()

		// Chrono
		var chrono = DateTime.now()

		// On Maio death
		maio.attachDeathCallback({
			// Display a title text
			text("You died", textSize = 32.0, color = Colors.RED).position(
				// Center the text
				512.0 - 32.0 * 4,
				448.0 - 32.0
			).scale(2.0)
			// Reset the game
			map.reset(this)
			// Remove maio
			removeChild(maio)
		})

		// On Maio's win
		maio.attachWinCallback({
			// Display a title text
			text("You win", textSize = 32.0, color = Colors.GREEN).position(
				// Center the text
				512.0 - 32.0 * 4,
				448.0 - 32.0
			).scale(2.0)
			// Compute the time
			val time = DateTime.now() - chrono
			// Display the time
			text("Time: ${time.seconds}", textSize = 24.0, color = Colors.GREEN).position(
				// Center the text
				512.0 - 32.0 * 4,
				448.0 + 32.0 * 6
			).scale(2.0)
			// Reset the game
			map.reset(this)
			// Remove maio
			removeChild(maio)
		})
	}
}
