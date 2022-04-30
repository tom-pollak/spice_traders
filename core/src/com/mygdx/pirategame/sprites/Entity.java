package com.mygdx.pirategame.sprites;

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
  protected World world;
  protected GameScreen screen;
  public Body b2body;
  public boolean setToDestroy;
  public boolean destroyed;
  public int health;
  public int damage;
  protected HealthBar bar;
  protected float stateTime = (float) Math.random();

  /**
   * Instantiates an enemy
   *
   * @param screen Visual data
   * @param x x position of entity
   * @param y y position of entity
   */
  public Entity(GameScreen screen, float x, float y) {
    this.world = screen.getWorld();
    this.screen = screen;
    setPosition(x, y);
    this.setToDestroy = false;
    this.destroyed = false;
    this.health = 100;
  }

  /** Defines enemy */
  protected abstract void defineBody();

  /** Defines contact */
  public abstract void onContact();

  public abstract void update(float dt);

  /**
   * Checks received damage Increments total damage by damage received
   *
   * @param value Damage received
   */
  public void changeDamageReceived(int value) {
    damage += value;
  }

  public Float getDistance(Entity e) {
    return new Vector2(e.getX() - this.getX(), e.getY() - this.getY()).len();
  }
}
