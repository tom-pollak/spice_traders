package com.mygdx.pirategame.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.sprites.Entity;
import com.mygdx.pirategame.sprites.Player;
import com.mygdx.pirategame.sprites.Projectile;
import com.mygdx.pirategame.tiles.InteractiveTileObject;

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

    // Fixes contact to an entity
    switch (cDef) {
      case PirateGame.COIN_BIT | PirateGame.PLAYER_BIT:
      case PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT:
        ((Entity) fixA.getUserData()).onContact();
        ((Entity) fixB.getUserData()).onContact();
        break;
      case PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT:
      case PirateGame.DEFAULT_BIT | PirateGame.ENEMY_BIT:
        wallCollide(fixA, fixB);
        break;
      case PirateGame.COLLEGE_BIT | PirateGame.CANNON_BIT:
      case PirateGame.PLAYER_BIT | PirateGame.CANNON_BIT:
      case PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT:
        if (fixA.getFilterData().categoryBits == PirateGame.CANNON_BIT) {
          ((Projectile) fixA.getUserData()).setToDestroy();
          ((Entity) fixB.getUserData()).onContact();
        } else {
          ((Projectile) fixB.getUserData()).setToDestroy();
          ((Entity) fixB.getUserData()).onContact();
        }
        break;
    }
  }

  private void wallCollide(Fixture fixA, Fixture fixB) {
    if (fixA.getFilterData().categoryBits == PirateGame.DEFAULT_BIT) {
      if (fixA.getUserData() != null
          && InteractiveTileObject.class.isAssignableFrom(fixA.getUserData().getClass())) {
        ((InteractiveTileObject) fixA.getUserData()).onContact();
        ((Player) fixB.getUserData()).playBreakSound();
      }
    } else {
      if (fixB.getUserData() != null
          && InteractiveTileObject.class.isAssignableFrom(fixB.getUserData().getClass())) {
        ((InteractiveTileObject) fixB.getUserData()).onContact();
      }
    }
  }

  /**
   * Run when contact is ended. Nearly empty since nothing special needs to happen when a contact is
   * ended
   *
   * @param contact The object that contains information about the collision
   */
  @Override
  public void endContact(Contact contact) {
    // Displays contact message
    Gdx.app.log("End Contact", "");
  }

  /**
   * (Not Used) Can be called before beginContact to pre emptively solve it
   *
   * @param contact The object that contains information about the collision
   * @param oldManifold Predicted impulse based on old data
   */
  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {}

  /**
   * (Not Used) Can be called before beginContact to post emptively solve it
   *
   * @param contact The object that contains information about the collision
   * @param impulse The signal recieved
   */
  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {}
}
