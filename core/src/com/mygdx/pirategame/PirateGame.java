package com.mygdx.pirategame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pirategame.screens.*;

/**
 * The start of the program. Sets up the main back bone of the parent.
 * This includes most constants used throught for collision and changing screens
 * Provides access for screens to interact with eachother and the options
 * interface
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class PirateGame extends Game {
    public static final float PPM = 100;

    // Bits used in collisions
    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short ENTITY_BIT = 4;
    public static final short ITEM_BIT = 8;
    public static final short PROJECTILE_BIT = 16;
    public static final short COIN_BIT = 32;

    public SpriteBatch batch;
    public Music song;
    public MainMenu MENU;
    public GameScreen GAME;
    public DeathScreen DEATH;
    public Help HELP;
    public VictoryScreen VICTORY;
    public Options OPTIONS;
    public SkillTree SKILL;
    private AudioControls options;

    // Constant for swapping between screens

    /**
     * Creates the main body of the parent.
     * Establises the batch for the whole parent as well as sets the first screen
     * Also sets up audio interface
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        batch.begin();
        // Set starting screen
        MENU = new MainMenu(this);
        GAME = new GameScreen(this);
        DEATH = new DeathScreen(this);
        HELP = new Help(this);
        VICTORY = new VictoryScreen(this);
        OPTIONS = new Options(this);
        SKILL = new SkillTree(this);
        batch.end();
        setScreen(MENU);
        // Create options
        options = new AudioControls();

        // Set background music and play if valid
        song = Gdx.audio.newMusic(Gdx.files.internal("audio/pirate-music.mp3"));
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
