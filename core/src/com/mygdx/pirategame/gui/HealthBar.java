package com.mygdx.pirategame.gui;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pirategame.entities.AbstractEntity;

/**
 * Health Bar
 * Displays the health of players
 * Displays the health of colleges
 * Displays the health of enemy ships
 * Creates and displays a health bar for entities with health
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class HealthBar extends Sprite {
    private final Sprite healthBar;

    private final AbstractEntity owner;

    /**
     * Instantiates health bar
     * Sets health bar
     *
     * @param owner Parent entity of health bar
     */
    public HealthBar(AbstractEntity owner) {
        this.owner = owner;
        Texture image = new Texture("HealthBar.png");
        healthBar = new Sprite(image);
        //Sets size of the health bar
        healthBar.setScale(0.0155f);
        healthBar.setSize(healthBar.getWidth(), healthBar.getHeight() - 2f);

        update();
        healthBar.setOrigin(0, 0);
    }

    /**
     * Updates health bar
     */
    public void update() {
        healthBar.setX((owner.getX() - 0.68f));
        healthBar.setY(owner.getY() + owner.getHeight() / 2);
    }

    public void remove() {
        healthBar.getTexture().dispose();
    }

    /**
     * Renders health bar
     */
    public void render(Batch batch) {
        healthBar.draw(batch);
    }


    /**
     * Updates healthbar with regards to damage
     *
     * @param value Damage recieved
     */
    public void setHealth(float value) {
        //Changes bar size when damaged
        healthBar.setSize(healthBar.getWidth() - value, healthBar.getHeight());
    }
}