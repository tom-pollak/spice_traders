package com.mygdx.pirategame.items;

import com.badlogic.gdx.Screen;
import com.mygdx.pirategame.AbstractActor;

public abstract class AbstractItem extends AbstractActor {

    public AbstractItem(Screen screen, String texturePath) {
        super(screen, texturePath);
    }
}
