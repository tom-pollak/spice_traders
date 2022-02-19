package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.pirategame.PirateGame;

/**
 * Death Screen
 * Produces a death screen on player death
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class DeathScreen extends AbstractScreen {

    /**
     * Creates a new screen
     *
     * @param pirateGame Game data
     */
    public DeathScreen(PirateGame pirateGame) {
        super(pirateGame);
    }

    /**
     * Shows new screen
     */
    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("skin\\uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        // Create tables for the text and button
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Table table2 = new Table();
        table2.center();
        table2.setFillParent(true);

        Label deathMsg = new Label("YOU  DIED", new Label.LabelStyle(new BitmapFont(), Color.RED));
        deathMsg.setFontScale(4f);
        table.add(deathMsg).center();
        stage.addActor(table);

        //Creat button
        TextButton backButton = new TextButton("Return To Menu", skin);

        //Return to main menu and kill screen
        backButton.addListener(new ChangeListener() {
            /**
             * Switches screen
             * Returns to menu
             *
             * @param event Updates system event state to meny
             * @param actor updates scene
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(parent.MENU);
                parent.killEndScreen();
            }
        });

        table2.add(backButton).fillX().uniformX();
        table2.bottom();

        stage.addActor(table2);

    }

}
