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

public class DeathScreen implements Screen {

    private final PirateGame parent;
    private final Stage stage;

    private Label deathMsg;

    public DeathScreen(PirateGame pirateGame){
        parent = pirateGame;
        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skin\\uiskin.json"));
        Gdx.input.setInputProcessor(stage);


        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Table table2 = new Table();
        table2.center();
        table2.setFillParent(true);

        deathMsg = new Label("YOU  DIED", new Label.LabelStyle(new BitmapFont(), Color.RED));
        deathMsg.setFontScale(4f);
        table.add(deathMsg).center();
        stage.addActor(table);

        TextButton backButton = new TextButton("Return To Menu", skin);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                parent.changeScreen(PirateGame.MENU);
                parent.killDeathScreen();
            }
        });

        table2.add(backButton).fillX().uniformX();
        table2.bottom();

        stage.addActor(table2);

    }

    @Override
    public void show() {

    }

    public void update(float dt){

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
