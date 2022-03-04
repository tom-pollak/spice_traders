package com.mygdx.pirategame.entities;

import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * @author Tom Pollak
 */
public abstract class AbstractEntity extends AbstractActor {

    public AbstractEntity(GameScreen screen, String texturePath) {
        super(screen, texturePath);
    }

}