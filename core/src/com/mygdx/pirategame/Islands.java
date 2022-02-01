package com.mygdx.pirategame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Sets up the class for all the Islands. Deals with what happens on collision and its properties
 */
public class Islands extends InteractiveTileObject {
    /**
     * Instantiates a new Islands.
     *
     * @param screen visual data
     * @param bounds Rectangle boundary (world boundary)
     */
    public Islands(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.DEFAULT_BIT);
    }

    /**
     * When contact occurs between the ship and island. deal damage to the ship
     */
    @Override
    public void onContact() {
        Gdx.app.log("island", "collision");
        Hud.changeHealth(-10);
    }
}
