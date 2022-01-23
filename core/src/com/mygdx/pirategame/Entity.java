package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity extends Sprite {
    protected World world;
    protected GameScreen screen;
    public Body b2body;

    public Entity(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEntity();
    }

    protected abstract void defineEntity();
    public abstract void entityContact();
}
