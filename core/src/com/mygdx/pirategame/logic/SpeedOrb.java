package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/** Speedup paower-up for the player. Can be added to inventory */
public class SpeedOrb extends Item {

  /**
   * Constructor for the speedup power-up.
   *
   * @param parent The holder of the power-up.
   */
  public SpeedOrb(Sprite parent) {
    super("speed orb", parent, new Texture("Orb_04.png"));
    this.buffs.put("speed", 1.5f);
  }
}
// https://opengameart.org/content/orbs-collection
