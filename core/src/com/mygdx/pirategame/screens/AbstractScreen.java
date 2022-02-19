package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

/**
 * Created by Tom Pollak on 21/02/2021
 * Base abstract class for all screens in the game.
 */
public abstract class AbstractScreen implements Screen {

    public final PirateGame parent;
    protected final Stage stage;

    /**
     * Creates a new screen
     *
     * @param pirateGame Game data
     */
    public AbstractScreen(PirateGame pirateGame) {
        parent = pirateGame;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public abstract void show();

    /**
     * (Not Used)
     * Updates the state of each object
     */
    public void update() {
    }

    /**
     * Renders visual data with delta time
     *
     * @param dt Delta time (elapsed time since last parent tick)
     */
    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Changes the camera size
     *
     * @param width  the width of the viewable area
     * @param height the height of the viewable area
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * (Not Used)
     * Pauses parent
     */
    @Override
    public void pause() {
    }

    /**
     * (Not Used)
     * Resumes parent
     */
    @Override
    public void resume() {
    }

    /**
     * (Not Used)
     * Hides parent
     */
    @Override
    public void hide() {
    }

    /**
     * Disposes parent data
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}