package com.mygdx.pirategame.logic;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

/** Loads renders and calculates collisions for a tiled map. */
public class BackgroundTiledMap {

  private final TiledMap map;

  public BackgroundTiledMap(TiledMap map) {
    this.map = new TmxMapLoader().load("map.tmx");
  }

  public MapLayers getLayers() {
    return map.getLayers();
  }

  public int getTileWidth() {
    TiledMapTileLayer indexLayer = (TiledMapTileLayer) map.getLayers().get(0);
    return indexLayer.getTileWidth();
  }

  public int getTileHeight() {
    TiledMapTileLayer indexLayer = (TiledMapTileLayer) map.getLayers().get(0);
    return indexLayer.getTileHeight();
  }

  /**
   * Checks if the tile at the given coordinates is blocked.
   *
   * @param tileX: the x coordinate of the tile
   * @param tileY: the y coordinate of the tile
   * @return true if the tile has the blocked property, false otherwise
   */
  public boolean isTileBlocked(int tileX, int tileY) {
    for (int i = 0; i < map.getLayers().getCount(); i++) {
      if (!Objects.equals(map.getLayers().get(i).getName(), "collisionBoxes")) {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(i);
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
        if (cell != null
            && cell.getTile() != null
            && cell.getTile().getProperties().containsKey("blocked")) return true;
      }
    }
    return false;
  }

  /**
   * Returns the tile coordinates of the given world coordinates.
   *
   * @param x the x coordinate of the world
   * @param y the y coordinate of the world
   * @return a pair containing the tile x and tile y coordinates
   */
  public Vector2 getTileCoords(float x, float y) {
    return new Vector2(Math.round(x / getTileWidth()), Math.round(y / getTileHeight()));
  }

  public int getTileX(float x) {
    return (int) (x / getTileWidth());
  }

  public int getTileY(float y) {
    return (int) (y / getTileHeight());
  }
}
