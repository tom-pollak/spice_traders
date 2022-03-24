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
 * The type for the victory screen
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class VictoryScreen extends AbstractScreen {

    /**
     * Instantiates a new Victory screen.
     *
     * @param pirateGame the main starting body of the parent. Where screen swapping is carried out.
     */
    public VictoryScreen(PirateGame pirateGame) {
        super(pirateGame);
    }

    /**
     * What should be displayed on the victory screen
     */
    @Override
    public void show() {
        //Creates the skin for the buttons and labels to use
        Skin skin = new Skin(Gdx.files.internal("skin\\uiskin.json"));
        //Sets stage to be the input processor
        Gdx.input.setInputProcessor(stage);

        //Creates 2 tables for actors to go into, one for text and one for the return button
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Table table2 = new Table();
        table2.center();
        table2.setFillParent(true);

        //Set the message and add to table
        Label victoryMsg = new Label("YOU WON", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        victoryMsg.setFontScale(3f);
        Label victoryMsg2 = new Label("CONGRATULATIONS PIRATE", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        victoryMsg2.setFontScale(3f);
        table.add(victoryMsg).center();
        table.row();
        table.add(victoryMsg2).center().padTop(20);
        stage.addActor(table);

        //Create return button
        TextButton backButton = new TextButton("Return To Menu", skin);

        //Set the click response
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                //Return to main screen
                parent.setScreen(parent.MENU);
                parent.dispose();
            }
        });

        //add to table
        table2.add(backButton).fillX().uniformX();
        table2.bottom();

        stage.addActor(table2);

    }
}
