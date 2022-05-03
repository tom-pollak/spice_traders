package com.mygdx.pirategame.pathfinding.pathfinding.grid;

import com.mygdx.pirategame.pathfinding.pathfinding.NavigationNode;

/**
 * A node within a {@link NavigationGridGraphNode}. It contains an [x,y] coordinate.
 *
 * @author Xavier Guzman
 */
public interface NavigationGridGraphNode extends NavigationNode {
  int getX();

  void setX(int x);

  int getY();

  void setY(int y);
}
