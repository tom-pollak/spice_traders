package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * Entity Class to generate enemies Instantiates enemies
 *
 * @author Ethan Alabaster
 * @version 1.0
 */
public abstract class Entity extends Sprite {
    public Body b2body;
    public boolean setToDestroy = false;
    public boolean destroyed = false;
    public int health = 100;
    public int damage = 0;
    protected World world;
    protected GameScreen screen;
    protected HealthBar bar;
    protected float stateTime = (float) Math.random();

    /**
     * Instantiates an enemy
     *
     * @param screen Visual data
     * @param x      x position of entity
     * @param y      y position of entity
     */
    public Entity(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
    }

    public static Texture getScaledTexture(String imgPath, Integer width, Integer height) {
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(imgPath));
        Pixmap pixmapNew = new Pixmap(width, height, pixmapOriginal.getFormat());
        pixmapNew.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmapNew.getWidth(), pixmapNew.getHeight());
        Texture texture = new Texture(pixmapNew);
        pixmapOriginal.dispose();
        pixmapNew.dispose();
        return texture;
    }

    /**
     * Defines enemy
     */
    protected abstract void defineBody();

    /**
     * Defines contact
     */
    public abstract void onContact(Entity collidingEntity);

    public abstract void update(float dt);

    /**
     * Checks received damage Increments total damage by damage received
     *
     * @param value Damage received
     */
    public void changeDamageReceived(int value) {
        damage += value;
    }

    /**
     * Gets the distance between the entity and another givent entity
     *
     * @param e Entity to check distance to
     * @return Distance between entities
     */
    public Float getDistance(Entity e) {
        return new Vector2(e.getX() - this.getX(), e.getY() - this.getY()).len();
    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            setToDestroy = true;
        }
    }

    protected int getDamage() {
        return 0;
    }
}
