package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class Help implements Screen {

    private final PirateGame parent;
    private final Stage stage;


    //In the constructor, the parent and stage are set. Also the states list is set
    public Help(PirateGame myGame){
        parent = myGame;
        stage = new Stage(new ScreenViewport());

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

        //Text

        Label Controls1 = new Label("WASD to move", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label Controls2 = new Label("E to fire", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label Controls3 = new Label("ESCAPE to see menu", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Label objective1 = new Label("The objective is to take over or destroy all other colleges", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label objective2 = new Label("Destroy the college flag with cannons", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label objective3 = new Label("Collect coins on the way", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Label skillInfo1 = new Label("Automatically upgrade as you play", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label skillInfo2 = new Label("See your upgrades in the skills tab", new Label.LabelStyle(new BitmapFont(), Color.WHITE));



        //Return Button
        TextButton backButton = new TextButton("Return", skin);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                parent.changeScreen(PirateGame.MENU);
            }
        });

        table.add(backButton);
        table.row().pad(10, 0, 10, 0);
        table.left().top();

        //add return button
        Other.add(Controls1);
        Other.row();
        Other.add(Controls2);
        Other.row();
        Other.add(Controls3).padBottom((40));
        Other.row();
        Other.add(objective1);
        Other.row();
        Other.add(objective2);
        Other.row();
        Other.add(objective3).padBottom((40));
        Other.row();
        Other.add(skillInfo1);
        Other.row();
        Other.add(skillInfo2).padBottom((40));

        Other.center();



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




