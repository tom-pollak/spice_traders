package com.mygdx.pirategame.logic;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.sprites.Player;

public class Inventory implements Disposable {
    public static Stage stage;
    private Table table1;
    private final Player player;
    public Inventory(Player parent){//the entity of which the inventory is going to be shown
        stage = new Stage(new ScreenViewport());
        player = parent;
        table1 = new Table();
        update();
    }

    public void update(){
        table1.reset();
        Array<Item> inventory = player.getInventory();
        //create and setup tables
        table1 = new Table();
        table1.bottom().left();
        table1.setFillParent(true);

        //setup inventory in tables
        for (Item item : inventory) {
            Image image = new Image(item.getTexture());
            table1.add(image).width(64).height(64).padBottom(16).padLeft(16);
        }
        stage.addActor(table1);
    }

    @Override
    public void dispose() {

    }
}

//https://opengameart.org/content/scp-005-skeleton-key
//