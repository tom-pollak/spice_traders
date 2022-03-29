package com.mygdx.pirategame.items;

import com.mygdx.pirategame.entities.AbstractEntity;
import com.mygdx.pirategame.entities.TreasureChest;
import com.mygdx.pirategame.screens.GameScreen;

import java.util.ArrayList;


public class Key extends AbstractItem {
    private final String name;
    private final String description;

    public Key(GameScreen screen, String name, String description) {
        super(screen, "img/key.png");
        this.name = name;
        this.description = description;
    }

    /**
     * Calls the method open for the entity on the same tile as the key
     *
     * @param entities the entities currently interctable with the key
     */

    @Override
    public void use(ArrayList<AbstractEntity> entities) {
        for (AbstractEntity entity : entities) {
            if (entity instanceof TreasureChest) {
                ((TreasureChest) entity).open(this);
            }
        }
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
