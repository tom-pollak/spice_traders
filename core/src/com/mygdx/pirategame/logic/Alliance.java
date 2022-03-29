package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.entities.AbstractEntity;
import com.mygdx.pirategame.entities.Ship;

import java.util.ArrayList;
import java.util.HashSet;

public class Alliance {
    public static Alliance NEUTRAL = new Alliance("Neutral", null, new Texture("img/ship.png"));
    private final String name;
    private final HashSet<AbstractActor> alliedActors = new HashSet<>();
    private final Texture shipTexture;
    private AbstractEntity leader;

    public Alliance(String name, AbstractEntity leader, Texture shipTexture) {
        this.name = name;
        this.leader = leader;
        this.shipTexture = shipTexture;
    }

    public Texture getShipTexture() {
        return shipTexture;
    }

    @Override
    public String toString() {
        return "Alliance{" + "name='" + name + '\'' + '}';
    }

    public AbstractEntity getLeader() {
        return leader;
    }

    public void setLeader(AbstractEntity leader) {
        this.leader = leader;
    }


    public void removeAlly(AbstractActor actor) {
        alliedActors.remove(actor);
    }

    public void addAlly(AbstractActor actor) {
        alliedActors.add(actor);
    }

    public ArrayList<Ship> getShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        for (AbstractActor actor : alliedActors) {
            if (actor instanceof Ship) {
                ships.add((Ship) actor);
            }
        }
        return ships;
    }

    public HashSet<AbstractActor> getAllies() {
        return alliedActors;
    }

    /**
     * @param actor actor to check
     * @return if actor is part of the same alliance
     */
    public boolean isAlly(AbstractActor actor) {
        return this.equals(actor.getAlliance());
    }

    /**
     * Opposite of isAlly, however will also count a neutral actor as not an enemy
     *
     * @return if actor is enemy
     */
    public boolean isEnemy(AbstractActor actor) {
        return !(isAlly(actor) || actor.getAlliance().equals(Alliance.NEUTRAL));
    }

    public String getName() {
        return name;
    }

}
