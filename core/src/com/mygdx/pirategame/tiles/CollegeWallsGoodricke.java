package com.mygdx.pirategame.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;
import com.mygdx.pirategame.sprites.Entity;

/**
 * College Walls (Goodricke) Checks interaction with walls from map
 *
 * @author Ethan Alabaster, Sam Pearson
 * @version 1.0
 */
public class CollegeWallsGoodricke extends InteractiveTileObject {
  private final GameScreen screen;

  /**
   * Sets bounds of college walls
   *
   * @param screen Visual data
   * @param bounds Wall bounds
   */
  public CollegeWallsGoodricke(GameScreen screen, Rectangle bounds) {
    super(screen, bounds);
    this.screen = screen;
    fixture.setUserData(this);
    // Set the category bit
    setCategoryFilter(PirateGame.COLLEGE_SENSOR_BIT);
  }

  /** Checks for contact with cannonball */
  @Override
  public void onContact(Entity collidingEntity) {
    Gdx.app.log("wall", "collision");
    // Deal damage to the assigned college
    screen.getCollege("Goodricke").onContact(collidingEntity);
  }
}
