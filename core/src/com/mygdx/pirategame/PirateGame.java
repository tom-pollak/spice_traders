package com.mygdx.pirategame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PirateGame extends Game {
	public static final int WIDTH = 320 * 4;
	public static final int HEIGHT = 180 * 4;
	public static final float PPM = 100;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
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
