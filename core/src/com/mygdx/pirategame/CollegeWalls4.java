package com.mygdx.pirategame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * College Walls (Anne Lister)
 * Checks interaction with walls from map
 *
 *@author Ethan Alabaster, Sam Pearson
 *@version 1.0
 */
public class CollegeWalls4 extends InteractiveTileObject {
    private GameScreen screen;

    /**
     * Sets bounds of college walls
     *
     * @param screen Visual data
     * @param bounds Wall bounds
     */
    public CollegeWalls4(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.COLLEGE_BIT);
    }

    /**
     * Checks for contact with cannonball
     */
    @Override
    public void onContact() {
        Gdx.app.log("wall", "collision");
        screen.getCollege("Anne Lister").onContact();

    }
}
