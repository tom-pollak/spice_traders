package com.mygdx.pirategame.entities;

import com.mygdx.pirategame.AvailableSpawn;
import com.mygdx.pirategame.gui.Hud;
import com.mygdx.pirategame.logic.ActorTable;
import com.mygdx.pirategame.logic.Alliance;
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

    /**
     * @param screen      Visual data
     * @param college     College name i.e. "Alcuin" used for fleet assignment
     * @param x           College position on x-axis
     * @param y           College position on y-axis
     * @param texturePath College flag sprite (image name)
     */
    public College(GameScreen screen, ActorTable actorTable, float x, float y, String texturePath) {
        super(screen, actorTable, texturePath);
        setAlliance(new Alliance(name, this, shipTexture));
        setPosition(x, y);
        setHealth(100);
        actorTable.addActor(this);
    }

    /**
     * Checks ship spawning in at a valid location
     *
     * @param x x position to test
     * @param y y position to test
     * @return isValid : returns the validity of the proposed spawn point
     */
    public boolean getCoord(int x, int y) {
        if (x < AvailableSpawn.xBase || x >= AvailableSpawn.xCap || y < AvailableSpawn.yBase || y >= AvailableSpawn.yCap) {
            return false;
        } else if (noSpawn.tileBlocked.containsKey(x)) {
            return !noSpawn.tileBlocked.get(x).contains(y);
        }
        return true;
    }

    @Override
    public void die() {
        super.die();
        //If it is the player ally college, end the parent for the player
        if (currentCollege.equals("alcuin_flag.png")) {
            screen.gameOverCheck();
        }
        //Award the player coins and points for destroying a college
        if (!currentCollege.equals("alcuin_flag.png")) {
            Hud.changePoints(100);
            Hud.addCoins(rand.nextInt(10));
        }
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
}

