package com.mygdx.pirategame.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.pirategame.gui.HealthBar;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * Enemy
 * Class to generate enemies
 * Instantiates enemies
 *
 * @author Ethan Alabaster
 * @version 1.0
 */
public abstract class Enemy extends Sprite {
    public Body b2body;
    public boolean setToDestroy;
    public boolean destroyed;
    public int health;
    public int damage;
    protected World world;
    protected GameScreen screen;
    protected HealthBar bar;

    /**
     * Instantiates an enemy
     *
     * @param screen Visual data
     * @param x      x position of entity
     * @param y      y position of entity
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
     *
     * @param value Damage received
     */
    public void changeDamageReceived(int value) {
        damage += value;
    }
}
