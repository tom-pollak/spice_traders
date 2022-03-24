package com.mygdx.pirategame.items;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.entities.AbstractEntity;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;
import com.eng.game.screens.Play;
import com.mygdx.pirategame.entities.TreasureChest;

import java.util.ArrayList;


public class Key extends AbstractItem {
    public Key(String name, String description, BackgroundTiledMap map, ActorTable actorTable) {
        super(name, description, new Texture("img/key.png"), map, actorTable);
    }

    /**
     * Calls the method open for the entity on the same tile as the key
     *
     * @param entities the entities currently interctable with the key
     */

    @Override
    public void use(ArrayList<AbstractEntity> entities) {
        // TODO: implement use
        for (AbstractEntity entity : entities) {
            if (entity instanceof TreasureChest) {
                (TreasureChest) entity entity.open(this);
            }
        }
    }

    @Override
    public void onPickup(Alliance alliance) {
        isHeld = true;
        Play.hasKey = true;
    }
}
