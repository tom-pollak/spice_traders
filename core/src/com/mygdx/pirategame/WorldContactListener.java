package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case PirateGame.COIN_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.COIN_BIT) {
                    ((Entity) fixA.getUserData()).entityContact();
                }
                else {
                    ((Entity) fixB.getUserData()).entityContact();
                }
                break;
            case PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.DEFAULT_BIT) {
                    if (fixA.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixA.getUserData().getClass())) {
                        ((InteractiveTileObject) fixA.getUserData()).onContact();
                        ((Player) fixB.getUserData()).playBreakSound();
                    }
                }
                else {
                    if (fixB.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixB.getUserData().getClass())) {
                        ((InteractiveTileObject) fixB.getUserData()).onContact();
                    }
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
