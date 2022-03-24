package com.mygdx.pirategame.items;

import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entities.AbstractEntity;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.screens.GameScreen;

import java.util.ArrayList;

public abstract class AbstractItem extends AbstractActor {

    public boolean isHeld = false;

    public AbstractItem(GameScreen screen, String texturePath) {
        super(screen, texturePath);
        setBounds(0, 0, 48 / PirateGame.PPM, 48 / PirateGame.PPM);
        setOrigin(24 / PirateGame.PPM, 24 / PirateGame.PPM);
    }

    public abstract String getDescription();

    public void onPickup(Alliance alliance) {
        isHeld = true;
    }

    public void onDrop() {
        isHeld = false;
    }

    public void use(ArrayList<AbstractEntity> collidingEntities) {
    }

    public void use(float x, float y) {
    }
}
