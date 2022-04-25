package com.mygdx.pirategame.logic;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.Player;

public class Inventory implements Disposable {
    public static Stage stage;
    private static Array<Item> inventory;
    public Inventory(Player player){//the entity of which the inventory is going to be shown
        inventory = player.getInventory();
        stage = new Stage(new ScreenViewport());

        //create and setup tables
        Table table1 = new Table();
        table1.bottom().left();
        table1.setFillParent(true);

        //show inventory
        for (Item item : inventory) {
            Image image = new Image(item.getTexture());
            table1.add(image).width(64).height(64).padBottom(16).padLeft(16);
            stage.addActor(table1);
        }

    }

    @Override
    public void dispose() {

    }
}

//https://opengameart.org/content/scp-005-skeleton-key
//