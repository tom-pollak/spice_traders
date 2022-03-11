package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * Enemy Ship
 * Generates enemy ship data
 * Instantiates an enemy ship
 *
 * @author Ethan Alabaster, Sam Pearson, Edward Poulter
 * @version 1.0
 */
public class EnemyShip extends Ship {
    private final Sound destroy = Gdx.audio.newSound(Gdx.files.internal("ship-explosion-2.wav"));
    private final Sound hit = Gdx.audio.newSound(Gdx.files.internal("ship-hit.wav"));
    public String college;
    private Texture enemyShip;

    /**
     * Instantiates enemy ship
     *
     * @param screen     Visual data
     * @param x          x coordinates of entity
     * @param y          y coordinates of entity
     * @param path       path of texture file
     * @param assignment College ship is assigned to
     */
    public EnemyShip(GameScreen screen, float x, float y, String path, String assignment) {
        super(screen, x, y);
    }


    /**
     * Constructs the ship batch
     *
     * @param batch The batch of visual data of the ship
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }


    /**
     * Updates the ship image. Particuarly change texture on college destruction
     *
     * @param alignment Associated college
     * @param path      Path of new texture
     */
    public void updateTexture(String alignment, String path) {
        college = alignment;
        enemyShip = new Texture(path);
    }
}
