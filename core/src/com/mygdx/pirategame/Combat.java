package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Combat extends Sprite {
    protected GameScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean destroyed;
    protected Body body;

    public Combat(GameScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 64 / PirateGame.PPM, 64 / PirateGame.PPM);
        defineCombat();
        destroyed = false;
    }

    public abstract void defineCombat();
    public abstract void use();

    public void update(float dt) {

    }
}
