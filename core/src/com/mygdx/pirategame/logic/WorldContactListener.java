package com.mygdx.pirategame.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.entities.AbstractEntity;
import com.mygdx.pirategame.entities.Player;
import com.mygdx.pirategame.items.Coin;

import static com.mygdx.pirategame.PirateGame.*;

/**
 * Tells the game what to do when certain entities come into contact with each other
 *
 * @author Ethan Alabaster
 * @version 1.0
 */
public class WorldContactListener implements ContactListener {

    /**
     * The start of the collision. Tells the game what should happen when the contact begins
     *
     * @param contact The object that contains information about the collision
     */
    @Override
    public void beginContact(Contact contact) {
        // Finds contact
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_BIT | COIN_BIT:
                if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
                    ((Player) fixA.getUserData()).pickup((Coin) fixB.getUserData());
                } else {
                    ((Player) fixB.getUserData()).pickup((Coin) fixA.getUserData());
                }
                break;
            case ENTITY_BIT:
            case PLAYER_BIT | ENTITY_BIT:
                AbstractEntity entityA = (AbstractEntity) fixA.getUserData();
                AbstractEntity entityB = (AbstractEntity) fixB.getUserData();
                entityA.collide(entityB);
                entityB.collide(entityA);
                break;
            case ENTITY_BIT | PROJECTILE_BIT:
                if (fixA.getFilterData().categoryBits == ENTITY_BIT) {
                    AbstractEntity entity = (AbstractEntity) fixA.getUserData();
                    AbstractEntity projectile = (AbstractEntity) fixB.getUserData();
                    entity.damage(projectile.getDamage());
                    projectile.die();
                }
                break;
            // Collide with wall
            case ENTITY_BIT | DEFAULT_BIT:
                if (fixA.getFilterData().categoryBits == ENTITY_BIT) {
                    AbstractEntity entity = (AbstractEntity) fixA.getUserData();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cDef);
        }
    }

    /**
     * Run when contact is ended. Nearly empty since nothing special needs to happen when a contact is ended
     *
     * @param contact The object that contains information about the collision
     */
    @Override
    public void endContact(Contact contact) {
        // Displays contact message
        Gdx.app.log("End Contact", "");
    }

    /**
     * (Not Used)
     * Can be called before beginContact to pre emptively solve it
     *
     * @param contact     The object that contains information about the collision
     * @param oldManifold Predicted impulse based on old data
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * (Not Used)
     * Can be called before beginContact to post emptively solve it
     *
     * @param contact The object that contains information about the collision
     * @param impulse The signal recieved
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
