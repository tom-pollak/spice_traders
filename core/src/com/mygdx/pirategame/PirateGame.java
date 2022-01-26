package com.mygdx.pirategame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PirateGame extends Game {
	public static final int WIDTH = 320 * 4;
	public static final int HEIGHT = 180 * 4;
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short ISLAND_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short CANNON_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short PLACEHOLDER_BIT = 64;

	public SpriteBatch batch;

	private MainMenu menuScreen;
	private GameScreen gameScreen;
	private SkillTree skillTreeScreen;
	private DeathScreen deathScreen;

	private audioControls options;
	public Music song;

	public final static int MENU = 0;
	public final static int GAME = 1;
	public final static int SKILL = 2;
	public final static int DEATH = 3;

	@Override
	public void create () {
		batch = new SpriteBatch();
		MainMenu mainMenu = new MainMenu(this);
		setScreen(mainMenu);
		options = new audioControls();

		song = Gdx.audio.newMusic(Gdx.files.internal("pirate-music.mp3"));
		song.setLooping(true);
		if(getPreferences().isMusicEnabled()){
			song.play();
		}
		song.setVolume(getPreferences().getMusicVolume());
	}

	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MainMenu(this);
				this.setScreen(menuScreen);
				break;

			case GAME:
				if (gameScreen == null) gameScreen = new GameScreen(this);
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
		}
	}

	//Allows interaction with the options document
	public audioControls getPreferences() {
		return this.options;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
