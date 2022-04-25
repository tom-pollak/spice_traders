package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pirategame.entities.AbstractEntity;
import com.mygdx.pirategame.items.AbstractItem;

import java.util.ArrayList;

public class Inventory implements Disposable {
    public static Stage stage;
    private static ArrayList<AbstractItem> inventory;
    public Inventory(AbstractEntity player){//the entity of which the inventory is going to be shown
        inventory = player.getInventory();
        stage = new Stage(new ScreenViewport());

        //create and setup tables
        Table table1 = new Table();
        table1.bottom().left();
        table1.setFillParent(true);

        //show inventory
        for (AbstractItem item : inventory) {
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