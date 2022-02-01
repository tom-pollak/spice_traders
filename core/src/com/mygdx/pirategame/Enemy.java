package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Enemy extends Sprite {
    protected World world;
    protected GameScreen screen;
    public Body b2body;
    public boolean setToDestroy;
    public boolean destroyed;
    public int health;
    public int damage;
    protected HealthBar bar;


    public Enemy(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        this.setToDestroy = false;
        this.destroyed = false;
        this.health = 100;


        defineEnemy();
        bar = new HealthBar(this);
    }
    protected abstract void defineEnemy();
    public abstract void onContact();
    public abstract void update(float dt);
}
