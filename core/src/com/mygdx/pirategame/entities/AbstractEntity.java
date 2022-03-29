package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.gui.HealthBar;
import com.mygdx.pirategame.items.AbstractItem;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.logic.Pair;
import com.mygdx.pirategame.screens.GameScreen;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Tom Pollak
 */
public abstract class AbstractEntity extends AbstractActor {
    protected final float friction = 0.2f;
    private final ArrayList<AbstractItem> inventory = new ArrayList<>();
    private final Integer movementRange = 0;
    private final int inventoryCapacity = 3;
    public float maxHealth = 0;
    public float health = maxHealth;
    public float accel = 0.5f;
    protected HealthBar healthBar = new HealthBar(this);
    private int inventoryIndex = 0;

    public AbstractEntity(GameScreen screen, String texturePath) {
        super(screen, texturePath);
        createBody();
    }

    public AbstractEntity(GameScreen screen) {
        super(screen);
        this.world = screen.getWorld();
        createBody();
    }


    public void setHealth(float newHealth) {
        if (newHealth < 0) {
            System.out.println("Health cannot be less than 0!");
        } else if (newHealth > maxHealth) {
            System.out.println("Health was set greater than max health!");
            System.out.println("Health: " + newHealth + " Max Health: " + maxHealth);
            this.health = maxHealth;
        } else {
            this.health = newHealth;
        }
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

    /**
     * Applies drag to entity
     * Max speed will be: sqrt(accel / friction)
     */
    protected void applyDrag() {
        Vector2 vel = body.getLinearVelocity();
        float speed2 = vel.len2();
        body.applyForce(vel.scl(-friction * speed2), body.getWorldCenter(), false);
    }

    public void createBody() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        fDef.filter.categoryBits = PirateGame.ENTITY_BIT;
        fDef.shape = getShape();
        fDef.friction = friction;
        body.createFixture(fDef).setUserData(this);
    }

    @Override
    public void draw(Batch batch, float delta) {
        super.draw(batch, delta);
        healthBar.render(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Vector2 position = body.getPosition();
        float a = body.getAngle() * MathUtils.radiansToDegrees;

        setCenter(position);
        setRotation(a);
        healthBar.setHealth(health);
        healthBar.update();
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
        healthBar.remove();
    }

    public ArrayList<AbstractItem> getInventory() {
        return inventory;
    }

    public boolean isOutOfRange(float x, float y) {
        float xDiff = Math.abs(getX() - x);
        float yDiff = Math.abs(getY() - y);
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff) > movementRange;
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
            return inventory.get(inventoryIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void addItem(AbstractItem item, int index) {
        item.onPickup(alliance);
        if (item.canBeHeld) {
            inventory.add(index, item);
        }
    }

    /**
     * Picks up the item on the same tile as the entity.
     * If there is no item on the tile, nothing happens.
     * If the entity is inventory an item, the item is dropped.
     *
     * @return true if an item was picked up, false otherwise
     */
    public boolean pickupOnTile() {
        AbstractItem item = actorTable.getItemInEntity(this);
        if (item == null) {
            System.out.println("No item to pick up");
            return false;
        }
        pickup(item);
        return true;
    }

    public void pickup(AbstractItem item) {
        if (getHeldItem() != null && item.canBeHeld) {
            drop();
        }
        addItem(item, inventoryIndex);
        System.out.println("Picked up " + item);
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
            AbstractItem droppedItem = inventory.remove(inventoryIndex);
            Integer x;
            Integer y;
            System.out.println(getCenter());
            Pair<Integer, Integer> emptyTile = droppedItem.findEmptyTile(getCenter().x, getCenter().y);
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
     * Called when E is pushed. Causes 1 cannon ball to spawn on both sides of the ships wih their relative velocity
     */
    public void fire() {
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
            inventoryIndex = index;
        } else {
            System.out.println("AbstractEntity.switchItem() - index out of bounds");
        }
    }

    public void useItem() {
        AbstractItem item = getHeldItem();
        if (item != null && item.canBeUsed) {
            item.use(actorTable.getCollidingEntities(this));
        } else {
            System.out.println("No item/cannot be used");
        }
    }

    public void useItem(float x, float y) {
        AbstractItem item = getHeldItem();
        if (item != null && item.canBeUsed) {
            item.use(x, y);
        } else {
            System.out.println("No item/cannot be used");
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
        System.out.println("AbstractEntity.damage() - " + this + " took " + damage + " damage");
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

    public Float getDamage() {
        return 0f;
    }
}