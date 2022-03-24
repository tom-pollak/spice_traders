package com.mygdx.pirategame.logic;

import com.mygdx.pirategame.AbstractActor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Upgrades {
    private final AbstractActor parent;
    private final JSONObject upgrades;
    JSONParser parser = new JSONParser();
    private JSONObject allUpgrades;

    {
        try {
            this.allUpgrades = (JSONObject) parser.parse(new FileReader("assets/upgrades.json"));
        } catch (IOException | ParseException e) {
            this.allUpgrades = null;
        }
    }

    public Upgrades(AbstractActor actor) {
        this.parent = actor;
        if (allUpgrades == null) {
            this.upgrades = null;
        } else {
            this.upgrades = (JSONObject) allUpgrades.get(this.parent.getClass().getSimpleName());
        }
    }
}
