package com.mygdx.pirategame.entities;

import com.mygdx.pirategame.screens.GameScreen;

public class DestructableTile extends AbstractEntity {

    public static boolean isDestructable = false;

    public DestructableTile(GameScreen screen) {
        super(screen);
        this.health = 100;
    }

}
