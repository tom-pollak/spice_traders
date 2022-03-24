package com.mygdx.pirategame.logic;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.entities.AbstractEntity;
import com.mygdx.pirategame.entities.Player;
import com.mygdx.pirategame.entities.Ship;
import com.mygdx.pirategame.items.AbstractItem;

import java.util.ArrayList;

/**
 * Is a repository for all actors in the game.
 */
public class ActorTable {
    private final ArrayList<AbstractItem> items = new ArrayList<>();
    private final ArrayList<AbstractEntity> entities = new ArrayList<>();
    private final Stage stage;
    private final BackgroundTiledMap map;
    private Player player;

    public ActorTable(Stage stage, BackgroundTiledMap map) {
        this.stage = stage;
        this.map = map;
    }

    /**
     * Check if actor is placed on a tile it will produce a collsion.
     * It will only be checked against its own type.
     * (a.k.a entities will be checked against entities, items against items)
     *
     * @param actor actor to check
     * @param x     x coordinate of tile
     * @param y     y coordinate of tile
     * @return true if actor would collide with tile, false otherwise
     */
    public boolean willCollide(AbstractActor actor, float x, float y) {
        Polygon actorHitbox = actor.getHitbox();
        actorHitbox.setPosition(x, y);

        ArrayList<AbstractActor> checkActors = new ArrayList<>();
        if (actor instanceof AbstractEntity) {
            checkActors.addAll(entities);
        } else if (actor instanceof AbstractItem) {
            checkActors.addAll(items);
        }

        for (AbstractActor checkActor : checkActors) {
            if (map.isCollision(actorHitbox, checkActor.getHitbox())) {
                return true;
            }
        }
        return false;
    }


    public void addActor(AbstractEntity entity) {
        if (entity instanceof Player) {
            this.player = (Player) entity;
        }
        if (entity.getStage() == null) {
            stage.addActor(entity);
        }
        entities.add(entity);
    }

    public Player getPlayer() {
        return player;
    }

    public void addActor(AbstractItem item) {
        if (item.getStage() == null) {
            stage.addActor(item);
        }
        items.add(item);
    }

    public void removeActor(AbstractEntity entity) {
        entities.remove(entity);
    }

    public void removeActor(AbstractItem item) {
        items.remove(item);
    }

    public ArrayList<Ship> getShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        for (AbstractEntity entity : entities) {
            if (entity instanceof Ship) {
                ships.add((Ship) entity);
            }
        }
        return ships;
    }

    public ArrayList<AbstractItem> getItems() {
        return items;
    }

    public AbstractItem getItemInEntity(AbstractEntity entity) {
        for (AbstractItem item : items) {
            if (map.isCollision(entity.getHitbox(), item.getHitbox()) && !item.isHeld) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<AbstractEntity> getCollidingEntities(AbstractEntity entity) {
        ArrayList<AbstractEntity> entitiesOnTile = new ArrayList<>();
        for (AbstractEntity collidingEntity : entities) {
            if (map.isCollision(entity.getHitbox(), collidingEntity.getHitbox()) && !collidingEntity.equals(entity)) {
                entitiesOnTile.add(collidingEntity);
            }
        }
        return entitiesOnTile;
    }

    /**
     * Gets the closest enemy ship to the given ship.
     *
     * @param ship: The original ship.
     * @return The closest enemy ship, the tile distance between the ships.
     */
    public Pair<Ship, Float> getClosestEnemyShip(AbstractEntity ship) {
        if (ship.getAlliance().getLeader() == null) {
            return null;
        }
        Ship closest = null;
        float distance = Float.MAX_VALUE;
        for (AbstractEntity newShip : entities) {
            if (!(newShip instanceof Ship) || ship.getAlliance().isAlly(newShip) || ship.isOutOfRange(newShip.getX(), newShip.getY()) || ship.getAlliance().getLeader().isOutOfRange(newShip.getX(), newShip.getY())) {
                continue;
            }

            float d = (float) Math.sqrt(Math.pow(ship.getX() - newShip.getX(), 2) + Math.pow(ship.getY() - newShip.getY(), 2));
            if (d < distance) {
                distance = d;
                closest = (Ship) newShip;
            }
        }
        if (closest == null) {
            return null;
        }
        return new Pair<>(closest, distance / BackgroundTiledMap.getTilePixelWidth());
    }
}
