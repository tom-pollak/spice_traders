package com.mygdx.pirategame.tiles;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * This is the class where all boundaries and collisions are created for the map.
 *
 * @author Ethan Alabaster
 * @version 1.0
 */
public class WorldCreator {
  /**
   * Starts the creation of the boundaries
   *
   * @param screen the screen that the boundaries are relevant for
   */
  public WorldCreator(GameScreen screen) {
    TiledMap map = screen.getMap();

    // Object class is islands, stuff for boat to collide with
    for (RectangleMapObject object :
        map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = object.getRectangle();

      new Islands(screen, rect);
    }
    for (RectangleMapObject object :
        map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = object.getRectangle();

      new CollegeWallsAlcuin(screen, rect);
    }
    for (RectangleMapObject object :
        map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = object.getRectangle();

      new CollegeWallsGoodricke(screen, rect);
    }
    for (RectangleMapObject object :
        map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = object.getRectangle();

      new CollegeWallsConstantine(screen, rect);
    }
    for (RectangleMapObject object :
        map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = object.getRectangle();

      new CollegeWallsAnneLister(screen, rect);
    }
  }
}
