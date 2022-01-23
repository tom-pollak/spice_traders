package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Options implements Screen {

    private final PirateGame myGame;
    private final Screen parent;
    private final Stage stage;


    //In the constructor, the game base is set as well as screen. Also the parent is set separately to allow for easy return
    public Options(PirateGame myGame, Screen parent){
        this.myGame = myGame;
        this.parent = parent;
        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        //Set the input processor
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        // Create a table that fills the screen.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //The skin for the actors
        Skin skin = new Skin(Gdx.files.internal("skin\\uiskin.json"));

        //Music Sliders and Check
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( myGame.getPreferences().getMusicVolume() ); //Set value to current option
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                myGame.getPreferences().setMusicVolume(volumeMusicSlider.getValue());  //Change music value in options to slider
                myGame.song.setVolume(myGame.getPreferences().getMusicVolume()); //Change the volume

                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( myGame.getPreferences().isMusicEnabled() ); //Check if it should be checked
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked(); //Get checked value
                myGame.getPreferences().setMusicEnabled( enabled ); //Set

                if(myGame.getPreferences().isMusicEnabled()){ //Play or don't
                    myGame.song.play();
                }
                else {
                    myGame.song.pause();}

                return false;
            }
        });


        //EFFECTS
        final Slider volumeEffectSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeEffectSlider.setValue( myGame.getPreferences().getEffectsVolume() ); //Set value to current option
        volumeEffectSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                myGame.getPreferences().setEffectsVolume(volumeEffectSlider.getValue()); //Change effect value in options to slider
                return false;
            }
        });

        final CheckBox effectCheckbox = new CheckBox(null, skin);
        effectCheckbox.setChecked( myGame.getPreferences().isEffectsEnabled() );
        effectCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = effectCheckbox.isChecked(); //Get checked value
                myGame.getPreferences().setEffectsEnabled( enabled ); //Set
                return false;
            }
        });

        // return to main screen button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myGame.setScreen(parent);
            }
        });

        //add buttons and label to table

        Label titleLabel = new Label("Options", skin);
        Label musicLabel = new Label("Music Volume", skin);
        Label effectLabel = new Label("Effect Volume", skin);
        Label musicOnLabel = new Label("Music On/Off", skin);
        Label effectOnLabel = new Label("Effect On/Off", skin);

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,0);
        table.add(musicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,0);
        table.add(musicOnLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,0);
        table.add(effectLabel).left();
        table.add(volumeEffectSlider);
        table.row().pad(10,0,0,0);
        table.add(effectOnLabel).left();
        table.add(effectCheckbox);
        table.row().pad(10,0,0,10);
        table.add(backButton);

    }



    @Override
    public void render(float delta) {
        //Set the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();



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
        // TODO Auto-generated method stub
    }
}




