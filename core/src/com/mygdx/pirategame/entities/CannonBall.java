package com.mygdx.pirategame.entities;

import com.mygdx.pirategame.screens.GameScreen;

public class CannonBall extends AbstractEntity {
    private final float damage = 10;


    public CannonBall(GameScreen screen, String texturePath) {
        super(screen, texturePath);
    }

    @Override
    public Float getDamage() {
        return damage;
    }

}
