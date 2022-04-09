package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.pirategame.PirateGame;

/**
 * Provides a UI for the user to interact with the audioControls interface
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class Options extends AbstractScreen {

    /**
     * Instantiates a new Options screen
     *
     * @param pirateGame the main starting body of the parent. Where screen swapping is carried out.
     */
    public Options(PirateGame pirateGame) {
        super(pirateGame);
    }

    /**
     * What should be displayed on the options screen
     */
    @Override
    public void show() {
        super.show();
        //Set the input processor
        Gdx.input.setInputProcessor(stage);
        // Create the main table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //The skin for the actors
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        //Music Sliders and Check boxes
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);

        //Set value to current option
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());

        volumeMusicSlider.addListener(event -> {
            parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());  //Change music value in options to slider
            parent.song.setVolume(parent.getPreferences().getMusicVolume()); //Change the volume

            return false;
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);

        //Check if it should be checked or unchecked by default
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());

        musicCheckbox.addListener(event -> {
            boolean enabled = musicCheckbox.isChecked(); //Get checked value
            parent.getPreferences().setMusicEnabled(enabled); //Set

            if (parent.getPreferences().isMusicEnabled()) { //Play or don't
                parent.song.play();
            } else {
                parent.song.pause();
            }

            return false;
        });

        //EFFECTS
        final Slider volumeEffectSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeEffectSlider.setValue(parent.getPreferences().getEffectsVolume()); //Set value to current option
        volumeEffectSlider.addListener(event -> {
            parent.getPreferences().setEffectsVolume(volumeEffectSlider.getValue()); //Change effect value in options to slider
            return false;
        });

        final CheckBox effectCheckbox = new CheckBox(null, skin);
        effectCheckbox.setChecked(parent.getPreferences().isEffectsEnabled());
        effectCheckbox.addListener(event -> {
            boolean enabled = effectCheckbox.isChecked(); //Get checked value
            parent.getPreferences().setEffectsEnabled(enabled); //Set
            return false;
        });

        // return to main screen button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(parent.MENU);
            }
        });


        Label titleLabel = new Label("Options", skin);
        Label musicLabel = new Label("Music Volume", skin);
        Label effectLabel = new Label("Effect Volume", skin);
        Label musicOnLabel = new Label("Music On/Off", skin);
        Label effectOnLabel = new Label("Effect On/Off", skin);

        //add buttons,sliders and labels to table
        table.add(titleLabel).colspan(2);
        table.row().pad(10, 0, 0, 0);
        table.add(musicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10, 0, 0, 0);
        table.add(musicOnLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10, 0, 0, 0);
        table.add(effectLabel).left();
        table.add(volumeEffectSlider);
        table.row().pad(10, 0, 0, 0);
        table.add(effectOnLabel).left();
        table.add(effectCheckbox);
        table.row().pad(10, 0, 0, 10);
        table.add(backButton);

    }

    /**
     * Renders the visual data for all objects
     *
     * @param delta Delta Time
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Changes the camera size, Scales the hud to match the camera
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
        // TODO Auto-generated method stub
    }

    /**
     * (Not Used)
     * Resumes parent
     */
    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    /**
     * (Not Used)
     * Hides parent
     */

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    /**
     * Disposes parent data
     */
    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}




