package com.mygdx.pirategame.sprites;

import com.mygdx.pirategame.screens.GameScreen;

/**
 * Created by tom SeaMonster class extends the AIShip, will act as a monster that the player must
 * avoid
 */
public class SeaMonster extends AiShip {
  public SeaMonster(GameScreen screen, float x, float y) {
    super(screen, x, y, "sea_monster.png", null);
  }
}
