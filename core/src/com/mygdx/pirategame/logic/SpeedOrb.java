package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpeedOrb extends Item{

    public SpeedOrb(Sprite parent) {
        super("speed orb", parent, new Texture("Orb_04.png"));
        this.buffs.put("speed", 1.5f);
    }
}
//https://opengameart.org/content/orbs-collection