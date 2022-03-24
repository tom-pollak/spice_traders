package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.gui.HealthBar;
import com.mygdx.pirategame.items.AbstractItem;
import com.mygdx.pirategame.items.Coin;
import com.mygdx.pirategame.items.Key;
import com.mygdx.pirategame.logic.ActorTable;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.logic.Pair;
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
    private int itemIndex;
    private int inventoryCapacity;

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
        alliance.removeAlly(this);
        dropAll();
        if (!(this instanceof CannonBall)) System.out.println(this + " was destroyed!");
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


    public AbstractItem getHeldItem() {
        try {
            return inventory.get(itemIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void addItem(AbstractItem item, int index) {
        item.onPickup(getAlliance());
        if (!(item instanceof Coin)) inventory.add(index, item);
    }

    public void addItem(AbstractItem item) {
        item.onPickup(getAlliance());
        inventory.add(item);
    }

    /**
     * Picks up the item on the same tile as the entity.
     * If there is no item on the tile, nothing happens.
     * If the entity is inventory an item, the item is dropped.
     *
     * @return true if an item was picked up, false otherwise
     */
    public boolean pickup() {
        AbstractItem item = actorTable.getItemInEntity(this);
        if (item == null) {
            System.out.println("No item to pick up");
            return false;
        }
        if (getHeldItem() != null && !(item instanceof Coin)) {
            drop();
        }
        System.out.println("Picked up " + item);
        addItem(item, itemIndex);
        return true;
    }

    /**
     * Drops the item at the entities item index.
     * The item is removed from the inventory and placed on the map.
     * The item will be placed on an empty tile around the entity.
     *
     * @return true if the item was dropped, false otherwise
     */
    public boolean drop() {
        try {
            AbstractItem droppedItem = inventory.remove(itemIndex);
            Integer x;
            Integer y;
            System.out.println(getX() + " " + getY());
            System.out.println(getOriginX() + " " + getOriginY());
            Pair<Integer, Integer> emptyTile = droppedItem.findEmptyTile(getOriginX(), getOriginY());
            if (emptyTile == null) {
                System.out.println("AbstractEntity.drop() - No empty tile found");
                return false;
            }
            x = emptyTile.fst;
            y = emptyTile.snd;
            System.out.println("AbstractEntity.drop() - Dropping item at " + x + ", " + y);
            droppedItem.setPosition(x, y);
            droppedItem.onDrop();
            System.out.println(this + " dropped " + droppedItem);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No item to drop");
            return false;
        }
        return true;
    }

    /**
     * Drops every item in the entity's inventory.
     */
    public boolean dropAll() {
        boolean droppedAll = true;
        while (inventory.size() > 0) {
            droppedAll = drop();
        }
        return droppedAll;
    }

    /**
     * Switches the item in the entity's inventory to the item at the given index.
     * If the index is out of bounds, nothing happens.
     * If the index is the same as the current item index, nothing happens.
     *
     * @param index the index of the item to switch to
     */
    public void switchItem(int index) {
        if (index >= 0 && index < inventoryCapacity) {
            System.out.println("AbstractEntity.switchItem() - switched to item " + getHeldItem());
            itemIndex = index;
        } else {
            System.out.println("AbstractEntity.switchItem() - index out of bounds");
        }
    }

    public void useItem() {
        AbstractItem item = getHeldItem();
        if (item != null) {
            item.use(actorTable.getCollidingEntities(this));
        } else {
            System.out.println("No item to use");
        }
    }

    public void useItem(float x, float y) {
        AbstractItem item = getHeldItem();
        if (item != null) {
            item.use(x, y);
        } else {
            System.out.println("No item to use");
        }
    }

    /**
     * Decreases the entity's health by the specified amount.
     * If the entity's health is 0 or less, it is considered dead.
     *
     * @param damage the amount of damage to deal
     */
    public void damage(float damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    /**
     * Heals the entity by the specified amount.
     * Will only be healed up to the entity's max health.
     *
     * @param heal the amount to heal
     */
    public void heal(int heal) {
        health += heal;
        if (health > maxHealth) health = maxHealth;
    }

    /**
     * Performs an action if supplied a valid key
     * By default, does nothing
     *
     * @param key the key to check
     * @return true if the key was valid, false otherwise
     */
    public boolean open(Key key) {
        System.out.println("Cannot be opened");
        return false;
    }


}