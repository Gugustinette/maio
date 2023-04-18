package fr.iutna.maio.engine.physic.states

import fr.iutna.maio.engine.physic.PhysicSprite

/**
 * StateStand class
 * In this state, the sprite is either standing or walking
 */
class StateStand(private val physicSprite: PhysicSprite) : PhysicState {
    /**
     * Make the sprite jump
     */
    override fun jump() {
        // Apply thrust to make the sprite jump
        physicSprite.dy = -physicSprite.thrust * physicSprite.delta
        // Set the sprite state to hasJump
        physicSprite.state = StateHasJump(physicSprite)
    }
}
