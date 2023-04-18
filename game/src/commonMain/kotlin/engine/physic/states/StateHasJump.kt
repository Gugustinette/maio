package fr.iutna.maio.engine.physic.states

import fr.iutna.maio.engine.physic.PhysicSprite

/**
 * StateHasJump class
 * In this state, the sprite has jumped
 */
class StateHasJump(private val physicSprite: PhysicSprite) : PhysicState {
    /**
     * Make the sprite jump
     */
    override fun jump() {
        // Do nothing, the sprite has already jumped
    }
}
