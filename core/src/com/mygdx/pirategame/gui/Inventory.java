package com.mygdx.pirategame.gui;

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

    }

    @Override
    public void dispose() {

    }
}
