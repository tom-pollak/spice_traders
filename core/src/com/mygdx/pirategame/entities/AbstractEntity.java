package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.gui.HealthBar;
import com.mygdx.pirategame.items.AbstractItem;
import com.mygdx.pirategame.logic.ActorTable;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.screens.GameScreen;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Tom Pollak
 */
public abstract class AbstractEntity extends AbstractActor {
    private final ArrayList<AbstractItem> inventory = new ArrayList<>();
    private final Integer movementRange = 0;
    public float maxHealth = 0;
    public float health = maxHealth;
    protected HealthBar healthBar;
    protected ActorTable actorTable;

    public AbstractEntity(GameScreen screen, ActorTable actorTable, String texturePath) {
        super(screen, texturePath);
        this.actorTable = actorTable;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void heal(float amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    @Override
    public void draw(Batch batch, float delta) {
        super.draw(batch, delta);
        healthBar.render(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (healthBar != null) {
            healthBar.setHealth(health);
            healthBar.update();
        }
    }

    @Override
    public void collide(AbstractActor other) {
        //Damage the college and lower health bar
        Gdx.app.log(this.toString(), "Collided with " + other.toString());
        if (other instanceof AbstractEntity) {
            AbstractEntity entity = (AbstractEntity) other;
            // TODO: Change this to something that actually damages the entity
        }
    }

    @Override
    public void die() {
        super.die();
        if (healthBar != null) {
            healthBar.remove();
        }
    }

    public ArrayList<AbstractItem> getInventory() {
        return inventory;
    }

    public boolean isOutOfRange(float x, float y) {
        float xDiff = Math.abs(getX() - x);
        float yDiff = Math.abs(getY() - y);
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff) > getMovementRange();
    }

    private double getMovementRange() {
        return movementRange;
    }

    @Override
    public void setAlliance(Alliance newAlliance) {
        HashSet<AbstractActor> oldAllianceMembers = (HashSet<AbstractActor>) alliance.getAllies().clone();
        oldAllianceMembers.remove(this);
        super.setAlliance(newAlliance);
        for (AbstractItem item : this.getInventory()) {
            item.setAlliance(newAlliance);
        }
        if (this instanceof College) {
            super.setAlliance(alliance);
            for (AbstractActor actor : oldAllianceMembers) {
                actor.setAlliance(newAlliance);
            }
        }
    }


}