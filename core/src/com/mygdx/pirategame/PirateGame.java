package com.mygdx.pirategame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pirategame.screens.*;


/**
 * The start of the program. Sets up the main back bone of the parent.
 * This includes most constants used throught for collision and changing screens
 * Provides access for screens to interact with eachother and the options interface
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class PirateGame extends Game {
    public static final float PPM = 100;

    //Bits used in collisions
    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short COLLEGEFIRE_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short CANNON_BIT = 16;
    public static final short ENEMY_BIT = 32;
    public static final short COLLEGE_BIT = 64;
    public static final short COLLEGESENSOR_BIT = 128;

    public SpriteBatch batch;
    public Music song;
    public Screen MENU = new MainMenu(this);
    public Screen GAME = new GameScreen(this);
    public Screen DEATH = new DeathScreen(this);
    public Screen HELP = new Help(this);
    public Screen VICTORY = new VictoryScreen(this);
    public Screen OPTIONS = new Options(this, this.getScreen());
    public Screen SKILL = new SkillTree(this);
    private AudioControls options;

    //Constant for swapping between screens

    /**
     * Creates the main body of the parent.
     * Establises the batch for the whole parent as well as sets the first screen
     * Also sets up audio interface
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        //Set starting screen
        MainMenu mainMenu = new MainMenu(this);
        setScreen(mainMenu);
        //Create options
        options = new AudioControls();

        //Set background music and play if valid
        song = Gdx.audio.newMusic(Gdx.files.internal("pirate-music.mp3"));
        song.setLooping(true);
        if (getPreferences().isMusicEnabled()) {
            song.play();
        }
        song.setVolume(getPreferences().getMusicVolume());
    }


    /**
     * Allows the user to interact with the audio options
     *
     * @return the options object
     */
    public AudioControls getPreferences() {
        return this.options;
    }


    /**
     * Disposes parent data
     */
    @Override
    public void dispose() {
        batch.dispose();
        song.dispose();
        MENU.dispose();
        GAME.dispose();
        SKILL.dispose();
        DEATH.dispose();
        HELP.dispose();
        VICTORY.dispose();
    }
}
