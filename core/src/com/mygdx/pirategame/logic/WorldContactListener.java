package com.mygdx.pirategame.logic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.mygdx.pirategame.AbstractActor;

/**
 * Tells the parent what to do when certain entities come into contact with each other
 *
 * @author Ethan Alabaster
 * @version 1.0
 */
public class WorldContactListener implements ContactListener {

    /**
     * The start of the collision. Tells the parent what should happen when the contact begins
     *
     * @param contact The object that contains information about the collision
     */
    @Override
    public void beginContact(Contact contact) {
        // Finds contact
        AbstractActor actorA = (AbstractActor) contact.getFixtureA().getUserData();
        AbstractActor actorB = (AbstractActor) contact.getFixtureB().getUserData();

        actorA.collide(actorB);
        actorB.collide(actorA);
    }
}
