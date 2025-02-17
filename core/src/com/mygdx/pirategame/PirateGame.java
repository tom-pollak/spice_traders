package com.mygdx.pirategame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pirategame.logic.AudioControls;
import com.mygdx.pirategame.screens.*;
import java.io.FileNotFoundException;

/**
 * The start of the program. Sets up the main back bone of the game. This includes most constants
 * used throught for collision and changing screens Provides access for screens to interact with
 * eachother and the options interface
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class PirateGame extends Game {
  public static final float PPM = 100;

  // Bits used in collisions
  public static final short DEFAULT_BIT = 1;
  public static final short PLAYER_BIT = 2;
  public static final short COIN_BIT = 8;
  public static final short PROJECTILE_BIT = 16;
  public static final short ENEMY_BIT = 32;

  public static final short COLLEGE_BIT = 64;
  public static final short WEATHER_BIT = 128;

  public static final short COLLEGE_SENSOR_BIT = 4;

  // Constant for swapping between screens
  public static final int MENU = 0;
  public static final int GAME = 1;
  public static final int SKILL = 2;
  public static final int DEATH = 3;
  public static final int HELP = 4;
  public static final int VICTORY = 5;
  public SpriteBatch batch;
  public Music song;
  // Variable for each screen
  private MainMenu menuScreen;
  private GameScreen gameScreen;
  private SkillTree skillTreeScreen;
  private DeathScreen deathScreen;
  private Help helpScreen;
  private VictoryScreen victoryScreen;
  private AudioControls options;

  /**
   * Creates the main body of the game. Establises the batch for the whole game as well as sets the
   * first screen Also sets up audio interface
   */
  @Override
  public void create() {
    batch = new SpriteBatch();
    // Set starting screen
    MainMenu mainMenu = new MainMenu(this);
    setScreen(mainMenu);
    // Create options
    options = new AudioControls();

    // Set background music and play if valid
    song = Gdx.audio.newMusic(Gdx.files.internal("pirate-music.mp3"));
    song.setLooping(true);
    if (getPreferences().isMusicEnabled()) {
      song.play();
    }
    song.setVolume(getPreferences().getMusicVolume());
  }

  /**
   * Changes the screen without killing the prior screen. Allows for the screens to be returned to
   * without making new ones
   *
   * @param screen the number of the screen that the user wants to swap to
   */
  public void changeScreen(int screen) {
    // Depending on which value given, change the screen
    switch (screen) {
      case MENU:
        if (menuScreen == null) menuScreen = new MainMenu(this);
        this.setScreen(menuScreen);
        break;

      case GAME:
        if (gameScreen == null) gameScreen = new GameScreen(this);
        if (skillTreeScreen == null) skillTreeScreen = new SkillTree(this);
        this.setScreen(gameScreen);
        break;

      case SKILL:
        if (skillTreeScreen == null) skillTreeScreen = new SkillTree(this);
        this.setScreen(skillTreeScreen);
        break;

      case DEATH:
        if (deathScreen == null) deathScreen = new DeathScreen(this);
        this.setScreen(deathScreen);
        break;

      case HELP:
        if (helpScreen == null) helpScreen = new Help(this);
        this.setScreen(helpScreen);
        break;

      case VICTORY:
        if (victoryScreen == null) victoryScreen = new VictoryScreen(this);
        this.setScreen(victoryScreen);
        break;
    } //
  }

  /**
   * Allows the user to interact with the audio options
   *
   * @return the options object
   */
  public AudioControls getPreferences() {
    return this.options;
  }

  public GameScreen getGameScreen() {
    return this.gameScreen;
  }

  /** Kills the game screen and skill tree so they can be refreshed on next game start */
  public void killGame() {
    gameScreen = null;
    skillTreeScreen = null;
  }

  /** Kill end screens so they can be made again. */
  public void killEndScreen() {
    deathScreen = null;
    victoryScreen = null;
  }

  /** (Not Used) Renders the visual data for all objects */
  @Override
  public void render() {
    super.render();
  }

  /** Disposes game data */
  @Override
  public void dispose() {
    batch.dispose();
  }

  public void load(String filename) {
    try {
      gameScreen.load(filename);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
}
