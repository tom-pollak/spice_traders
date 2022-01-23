package com.mygdx.pirategame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Islands extends InteractiveTileObject {
    public Islands(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.DEFAULT_BIT);
    }

    @Override
    public void onContact() {
        Gdx.app.log("island", "collision");

        Hud.changeHealth(-10);
    }
}
