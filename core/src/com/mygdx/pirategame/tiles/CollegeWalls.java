package com.mygdx.pirategame.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;
import com.mygdx.pirategame.sprites.College;
import com.mygdx.pirategame.sprites.Entity;

/**
 * College Walls (Alcuin) Checks interaction with walls from map
 *
 * @author Ethan Alabaster, Sam Pearson
 * @version 1.0
 */
public class CollegeWalls extends InteractiveTileObject {
  private final College college;

  /**
   * Sets bounds of college walls
   *
   * @param screen Visual data
   * @param bounds Wall bounds
   */
  public CollegeWalls(GameScreen screen, Rectangle bounds, College college) {
    super(screen, bounds);
    this.college = college;
    fixture.setUserData(this);
    // Set the category bit
    setCategoryFilter(PirateGame.COLLEGE_SENSOR_BIT);
  }

  /** Checks for contact with cannonball */
  @Override
  public void onContact(Entity collidingEntity) {
    Gdx.app.log("wall", "collision");
    // Deal damage to the assigned college
    college.onContact(collidingEntity);
  }
}
