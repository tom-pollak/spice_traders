package com.mygdx.pirategame.logic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.pirategame.pathfinding.bridge.NavTmxMapLoader;
import com.mygdx.pirategame.pathfinding.bridge.NavigationTiledMapLayer;
import com.mygdx.pirategame.pathfinding.pathfinding.PathFindingException;
import com.mygdx.pirategame.pathfinding.pathfinding.grid.GridCell;
import com.mygdx.pirategame.pathfinding.pathfinding.grid.finders.GridFinderOptions;
import com.mygdx.pirategame.pathfinding.pathfinding.grid.finders.ThetaStarGridFinder;
import java.util.List;

/** Theta* search on a tiled map. */
public class Pathfinding {
  private final ThetaStarGridFinder<GridCell> finder;
  NavigationTiledMapLayer navLayer;

  public Pathfinding() {
    TiledMap map = new NavTmxMapLoader("navigation", "walkable", "0").load("map.tmx");
    navLayer =
        (NavigationTiledMapLayer)
            map.getLayers().get("navigation"); // Use navigation layer to determine walkable tiles
    GridFinderOptions options = new GridFinderOptions();
    options.allowDiagonal = true;
    finder = new ThetaStarGridFinder<>(GridCell.class, options);
  }

  public List<GridCell> findPath(int startX, int startY, int endX, int endY) {
    try {
      return finder.findPath(startX, startY, endX, endY, navLayer);
    } catch (NullPointerException | PathFindingException ignored) {
      return null;
    }
  }
}
