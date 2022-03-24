package com.mygdx.pirategame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pirategame.gui.Hud;
import com.mygdx.pirategame.logic.ActorTable;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.logic.BackgroundTiledMap;
import com.mygdx.pirategame.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

/**
 * College
 * Class to generate the enemy entity college
 * Instantiates colleges
 * Instantiates college fleets
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */

public class College extends AbstractEntity {
    public Random rand = new Random();
    public ArrayList<Ship> fleet = new ArrayList<>();
    public Vector2 spawn;
    public boolean destroyed = false;

    public College(GameScreen screen, String name, ActorTable actorTable, Vector2 tileCoord, String texturePath, String shipTexture) {
        super(screen, actorTable, texturePath);
        setAlliance(new Alliance(name, this, new Texture(shipTexture)));
        Vector2 worldCoords = BackgroundTiledMap.tileToWorldCoords((int) tileCoord.x, (int) tileCoord.y);
        setPosition(worldCoords.x, worldCoords.y);
        setHealth(100);
        actorTable.addActor(this);
    }


    @Override
    public void die() {
        super.die();
        //If it is the player ally college, end the parent for the player
        if (this.equals(screen.getBossCollege())) {
            Hud.changePoints(500);
            screen.gameOverCheck();
        } else {
            Hud.changePoints(100);
            Hud.addCoins(rand.nextInt(10));
        }
        destroyed = true;
    }

    /**
     * Updates the state of each object with delta time
     * Checks for college destruction
     * Checks for cannon fire
     *
     * @param dt Delta time (elapsed time since last parent tick)
     */
    public void act(float dt) {
        //If college is set to destroy and isnt, destroy it

        //If not destroyed, update the college position
        setPosition(getX() - getWidth() / 2f, getY() - getHeight() / 2f);

        if (health <= 0) {
            die();
        }
        healthBar.update();
        //Update cannon balls
    }

    /**
     * Fires cannonballs
     */
    public void fire() {
        //        cannonBalls.add(new CollegeFire(screen, b2body.getPosition().x, b2body.getPosition().y));
        // TODO: Fire a cannonball
    }

    public void setSpawn(int x, int y) {
        spawn.x = x;
        spawn.y = y;
    }
}

