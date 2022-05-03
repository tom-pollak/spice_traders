package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Health Bar Displays the health of players Displays the health of colleges Displays the health of
 * enemy ships Creates and displays a health bar for entities with health
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class HealthBar {
  private final Sprite healthBar;
  private final Texture image;

  private final Entity owner;
  private final float maxHealthBar;
  private Integer maxHealth;

  /**
   * Instantiates health bar Sets health bar
   *
   * @param owner Parent entity of health bar
   */
  public HealthBar(Entity owner) {
    this.owner = owner;
    image = new Texture("HealthBar.png");
    healthBar = new Sprite(image);
    // Sets size of the health bar
    healthBar.setScale(0.0155f);
    healthBar.setSize(healthBar.getWidth(), healthBar.getHeight() - 2f);

    // Sets location of bar
    healthBar.setX(this.owner.b2body.getPosition().x - 0.68f);
    healthBar.setY(this.owner.b2body.getPosition().y + this.owner.getHeight() / 2);
    healthBar.setOrigin(0, 0);
    this.maxHealth = owner.health;
    maxHealthBar = healthBar.getWidth();
  }

  /** Updates health bar */
  public void update() {
    if (owner != null) {
      // Update location
      healthBar.setX((owner.b2body.getPosition().x - 0.68f));
      healthBar.setY(owner.b2body.getPosition().y + owner.getHeight() / 2);
    }
  }

  /** Renders health bar */
  public void render(Batch batch) {
    healthBar.draw(batch);
  }

  /**
   * Updates healthbar with regards to damage
   *
   * @param currentHealth Damage recieved
   */
  public void changeHealth(float currentHealth) {
    float health = currentHealth / maxHealth;
    if (health > 1) {
      health = 1;
      maxHealth = (int) currentHealth;
    }
    // Changes bar size when damaged
    healthBar.setSize(maxHealthBar * health, healthBar.getHeight());
  }
}
