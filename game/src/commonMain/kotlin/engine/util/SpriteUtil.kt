package fr.iutna.maio.engine.util

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
import kotlin.math.*
import fr.iutna.maio.engine.enum.DirectionEnum

/**
 * SpriteUtil class
 */
class SpriteUtil {
    /**
     * Static
     */
    companion object {
        /**
         * Sprite Map
         * It is used to cache the SpriteAnimation objects so we don't have to load them again
         * The key for each sprite is its path + its columns + its rows + its flipX
         */
        private val spriteCacheMap = mutableMapOf<String, SpriteAnimation>()

        /**
         * Init a sprite
         */
        suspend fun initSprite(spritePath: String, columns: Int, rows: Int, flipX: Boolean): SpriteAnimation {
            // Get the key for the sprite
            val key = spritePath + columns + rows + flipX

            // If the sprite is already in the cache
            if (spriteCacheMap.containsKey(key)) {
                // Return the sprite from the cache
                return spriteCacheMap[key]!!
            }

            // Else, read the sprite
            val bitmap = resourcesVfs[spritePath].readBitmap()
            // If flipX was asked
            if (flipX) {
                // Flip the sprite on X axis
                bitmap.flipX()
            }

            // Create the sprite animation
            val sprite = SpriteAnimation(
                // Read the sprite map
                spriteMap = bitmap,
                spriteWidth = 64,
                spriteHeight = 64,
                marginTop = 0,
                marginLeft = 0,
                columns = columns,
                rows = rows,
                offsetBetweenColumns = 0,
                offsetBetweenRows = 0,
            )

            // Add the sprite to the cache
            spriteCacheMap[key] = sprite

            // Return the sprite
            return sprite
        }

        /**
         * Init a empty sprite
         */
        fun initEmptySprite(): SpriteAnimation {
            // Return the empty sprite
            return SpriteAnimation(
                spriteMap = Bitmap32(1, 1),
                spriteWidth = 1,
                spriteHeight = 1,
                marginTop = 0,
                marginLeft = 0,
                columns = 1,
                rows = 1,
                offsetBetweenColumns = 0,
                offsetBetweenRows = 0,
            )
        }

        /**
         * Take 2 sprite and tell the direction from the first to the second
         * For example, the first sprite would be Maio, and the second would be a block
         * If Maio is on the left of the block, the function will return DirectionEnum.LEFT
         * @param sprite1 The first sprite
         * @param sprite2 The second sprite
         * @return The direction from the first to the second
         */
        fun getDirectionFromSpriteToSprite(sprite1: Sprite, sprite2: Sprite): DirectionEnum {
            // Get the center point of each sprite
            val sprite1CenterX = sprite1.x + sprite1.width / 2
            val sprite1CenterY = sprite1.y + sprite1.height / 2
            val sprite2CenterX = sprite2.x + sprite2.width / 2
            val sprite2CenterY = sprite2.y + sprite2.height / 2

            /**
             * To determine the direction, we get the angle between the 2 sprites
             * Then we convert the angle to a direction
             */
            // Get the angle between the 2 sprites
            val angle = atan2(sprite2CenterY - sprite1CenterY, sprite2CenterX - sprite1CenterX)
            // Convert the angle to a direction (only 4 directions)
            val direction = when {
                angle in -PI / 4..PI / 4 -> DirectionEnum.LEFT
                angle in PI / 4..3 * PI / 4 -> DirectionEnum.UP
                angle in -3 * PI / 4..-PI / 4 -> DirectionEnum.DOWN
                else -> DirectionEnum.RIGHT
            }

            // Return the direction
            return direction
        }




        /**
         * DebugObject
         */
        data class DebugObject(
            val id: String,
            val circle: View,
            val rect1: SolidRect,
            val rect2: SolidRect,
            val rect3: SolidRect,
            val rect4: SolidRect,
        )

        /**
         * Array that will contain all the drawing with an id attached
         */
        val debugDrawing = mutableListOf<DebugObject>()

        /**
         * Debug
         */
        fun Debug(container: Container, sprite: Sprite, color: RGBA = Colors.RED, id: String = "debug") {
            // Find the object with the id
            val objectFound = debugDrawing.find { it.id == id }
            // If the object was found
            if (objectFound != null) {
                // Remove the drawing
                container.removeChild(objectFound.circle)
                container.removeChild(objectFound.rect1)
                container.removeChild(objectFound.rect2)
                container.removeChild(objectFound.rect3)
                container.removeChild(objectFound.rect4)
                // Remove the object from the array
                debugDrawing.remove(objectFound)
            }
            
            // Get the center point of each sprite
            val spriteCenterX = sprite.x + sprite.width / 2
            val spriteCenterY = sprite.y + sprite.height / 2

            // Debug
            val circle1 = container.circle(2.0, Colors.RED) {
                xy(spriteCenterX, spriteCenterY)
            }

            // Draw rect around the sprites
            val rect1 = container.solidRect(sprite.width, 2.0, color) {
                xy(sprite.x, sprite.y)
            }
            val rect2 = container.solidRect(sprite.width, 2.0, color) {
                xy(sprite.x, sprite.y + sprite.height)
            }
            val rect3 = container.solidRect(2.0, sprite.height, color) {
                xy(sprite.x, sprite.y)
            }
            val rect4 = container.solidRect(2.0, sprite.height, color) {
                xy(sprite.x + sprite.width, sprite.y)
            }

            // Add the drawing to the array with the id
            debugDrawing.add(DebugObject(id, circle1, rect1, rect2, rect3, rect4))
        }
    }
}
