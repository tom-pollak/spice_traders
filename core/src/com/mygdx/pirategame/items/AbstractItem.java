package com.mygdx.pirategame.items;

import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;

public abstract class AbstractItem extends AbstractActor {

    public boolean isHeld = false;

    public AbstractItem(GameScreen screen, String texturePath) {
        super(screen, texturePath);
        setBounds(0, 0, 48 / PirateGame.PPM, 48 / PirateGame.PPM);
        setOrigin(24 / PirateGame.PPM, 24 / PirateGame.PPM);
    }
}
