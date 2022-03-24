package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.pirategame.logic.ActorTable;
import com.mygdx.pirategame.logic.Alliance;
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

    public EnemyShip(GameScreen screen, ActorTable actorTable, Alliance alliance, float x, float y) {
        super(screen, actorTable);
        alliance.addAlly(this);
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
