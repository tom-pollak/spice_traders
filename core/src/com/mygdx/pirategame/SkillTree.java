package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


import java.util.ArrayList;
import java.util.List;


public class SkillTree implements Screen {

    private final PirateGame parent;
    private final Stage stage;

    //To store whether buttons are enabled or disabled
    private final List<Integer> states = new ArrayList<Integer>();

    //In the constructor, the parent and stage are set. Also the states list is set
    public SkillTree(PirateGame myGame){
        parent = myGame;
        stage = new Stage(new ScreenViewport());

        //0 = enabled, 1 = disabled
        states.add(0);
        states.add(1);
        states.add(1);
        states.add(1);
    }


    @Override
    public void show() {
        //Set the input processor
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Table for the return button
        final Table Other = new Table();
        Other.setFillParent(true);
        stage.addActor(Other);


        //The skin for the actors
        Skin skin = new Skin(Gdx.files.internal("skin\\uiskin.json"));

        //create skill tree buttons

        final TextButton movement1 = new TextButton("Movement Speed + 20%", skin);
        movement1.setTransform(true);
        movement1.setScale(0.9f); //make slightly smaller

        //Sets enabled or disabled
        if (states.get(0) == 1){
            movement1.setDisabled(true);
        }


        final TextButton damage1 = new TextButton("Damage + 5", skin);
        damage1.setTransform(true);
        damage1.setScale(0.9f); //make slightly smaller

        //Sets enabled or disabled
        if (states.get(1) == 1){
            damage1.setDisabled(true);
        }

        final TextButton GoldMulti1 = new TextButton("Gold Multiplier x2", skin);
        GoldMulti1.setTransform(true);
        GoldMulti1.setScale(0.9f); //make slightly smaller

        //Sets enabled or disabled
        if (states.get(2) == 1){
            GoldMulti1.setDisabled(true);
        }

        final TextButton movement2 = new TextButton("Movement Speed + 20%", skin);
        movement2.setTransform(true);
        movement2.setScale(0.9f); //make slightly smaller

        //Sets enabled or disabled
        if (states.get(3) == 1){
            movement2.setDisabled(true);
        }

        //Return Button
        TextButton backButton = new TextButton("Return", skin);

        //Change the value and set below skills to be available.Update list accordingly
        movement1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (Hud.getCoins() >= 1) {
                    Hud.changeCoins(-1);
                    //Change acceleration
                    GameScreen.changeAcceleration(20F);
                    //Change Max speed
                    GameScreen.changeMaxSpeed(20F);
                    damage1.setDisabled(false);
                    GoldMulti1.setDisabled(false);
                    movement2.setDisabled(false);
                    movement1.setDisabled(true);

                    states.clear();
                    states.add(1);
                    states.add(0);
                    states.add(0);
                    states.add(0);
                }

            }
        });

        movement2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (Hud.getCoins() >= 1) {
                    Hud.changeCoins(-1);
                    //Change acceleration
                    GameScreen.changeAcceleration(20F);
                    //Change Max speed
                    GameScreen.changeMaxSpeed(20F);
                    movement2.setDisabled(true);

                    states.set(3, 1); //Set list value
            }}
        });

        damage1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //change damage
                if (Hud.getCoins() >= 1) {
                    Hud.changeCoins(-1);
                    damage1.setDisabled(true);

                    states.set(1, 1); //Set list value
            }}
        });

        GoldMulti1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //change coin multiplier
                if (Hud.getCoins() >= 1) {
                    Hud.changeCoins(-1);
                    Hud.changeCoinsMulti(2);
                    GoldMulti1.setDisabled(true);

                    states.set(2, 1); //Set list value
            }}
        });



        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                parent.changeScreen(PirateGame.GAME); //Return to game
            }
        });

        //add buttons to main table
        table.add(movement1).colspan(3);
        table.row().pad(10, 0, 10, 0);
        table.add(movement2);
        table.add(GoldMulti1);
        table.add(damage1);
        table.top();

        //add return button
        Other.add(backButton);
        Other.bottom().left();

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        // TODO Auto-generated method stub
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}




