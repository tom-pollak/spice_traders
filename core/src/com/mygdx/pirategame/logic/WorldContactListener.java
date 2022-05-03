package com.mygdx.pirategame.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.sprites.Entity;
import com.mygdx.pirategame.sprites.Player;
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
      case PirateGame.COLLEGE_BIT | PirateGame.PROJECTILE_BIT:
      case PirateGame.PLAYER_BIT | PirateGame.PROJECTILE_BIT:
      case PirateGame.ENEMY_BIT | PirateGame.PROJECTILE_BIT:
      case PirateGame.PLAYER_BIT | PirateGame.WEATHER_BIT:
      case PirateGame.ENEMY_BIT | PirateGame.WEATHER_BIT:
      case PirateGame.COLLEGE_BIT | PirateGame.WEATHER_BIT:
        Entity entityA = (Entity) fixA.getUserData();
        Entity entityB = (Entity) fixB.getUserData();
        entityA.onContact(entityB);
        entityB.onContact(entityA);
        break;
      case PirateGame.PLAYER_BIT | PirateGame.COLLEGE_SENSOR_BIT:
      case PirateGame.ENEMY_BIT | PirateGame.COLLEGE_SENSOR_BIT:
      case PirateGame.PROJECTILE_BIT | PirateGame.COLLEGE_SENSOR_BIT:
      case PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT:
      case PirateGame.DEFAULT_BIT | PirateGame.ENEMY_BIT:
        if ((fixA.getFilterData().categoryBits == PirateGame.DEFAULT_BIT)
            || (fixA.getFilterData().categoryBits == PirateGame.COLLEGE_SENSOR_BIT)) {
          entityB = (Entity) fixB.getUserData();
          ((InteractiveTileObject) fixA.getUserData()).onContact(entityB);
          if (entityB.getClass().equals(Player.class)) {
            ((Player) entityB).playBreakSound();
          }
        } else {
          entityA = (Entity) fixA.getUserData();
          ((InteractiveTileObject) fixB.getUserData()).onContact(entityA);
          if (entityA.getClass().equals(Player.class)) {
            ((Player) entityA).playBreakSound();
          }
        }
        break;
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
    Fixture fixA = contact.getFixtureA();
    Fixture fixB = contact.getFixtureB();

    int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

    switch (cDef) {
      case PirateGame.PLAYER_BIT | PirateGame.WEATHER_BIT:
      case PirateGame.ENEMY_BIT | PirateGame.WEATHER_BIT:
      case PirateGame.COLLEGE_BIT | PirateGame.WEATHER_BIT:
        Weather weather;
        Entity entity;
        if (fixA.getFilterData().categoryBits == PirateGame.WEATHER_BIT) {
          weather = (Weather) fixA.getUserData();
          entity = (Entity) fixB.getUserData();
        } else {
          weather = (Weather) fixB.getUserData();
          entity = (Entity) fixA.getUserData();
        }
        weather.removeContact(entity);
        break;
    }

    Gdx.app.log("End Contact", "");
  }

  /**
   * (Not Used) Can be called before beginContact to pre-emptively solve it
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
