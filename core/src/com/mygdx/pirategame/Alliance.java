package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.pirategame.entities.College;

import java.util.ArrayList;
import java.util.HashSet;

public class Alliance {
    public static Alliance NEUTRAL = new Alliance("Neutral", null, new Texture("img/ship.png"));
    private final String name;
    private final HashSet<AbstractActor> alliedActors = new HashSet<>();
    private final Texture shipTexture;
    private College leader;

    public Alliance(String name, College leader, Texture shipTexture) {
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

    public College getLeader() {
        return leader;
    }

    public void setLeader(College leader) {
        this.leader = leader;
    }


    public void removeAlly(AbstractActor actor) {
        alliedActors.remove(actor);
    }

    public void addAlly(AbstractActor actor) {
        alliedActors.add(actor);
    }

    // Make isEnemy which is isAlly \cup NEUTRAL?

    public ArrayList<Ship> getShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        for (GameActor gameActor : alliedActors) {
            if (gameActor instanceof Ship) {
                ships.add((Ship) gameActor);
            }
        }
        return ships;
    }

    public HashSet<GameActor> getAllies() {
        return alliedActors;
    }

    public boolean isAlly(GameActor actor) {
        return this.equals(actor.getAlliance());
    }

    public String getName() {
        return name;
    }
}
