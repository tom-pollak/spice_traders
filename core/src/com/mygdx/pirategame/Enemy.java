package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Enemy
 * Class to generate enemies
 * Instantiates enemies
 *
 *@author Ethan Alabaster
 *@version 1.0
 */
public abstract class Enemy extends Sprite {
    protected World world;
    protected GameScreen screen;
    public Body b2body;
    public boolean setToDestroy;
    public boolean destroyed;
    public int health;
    public int damage;
    protected HealthBar bar;

    /**
     * Instantiates an enemy
     *
     * @param screen Visual data
     * @param x x position of entity
     * @param y y position of entity
     */
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

    /**
     * Defines enemy
     */
    protected abstract void defineEnemy();

    /**
     * Defines contact
     */
    public abstract void onContact();
    public abstract void update(float dt);

    /**
     * Checks recieved damage
     * Increments total damage by damage received
     * @param value Damage received
     */
    public void changeDamageReceived(int value){
        damage += value;
    }
}
