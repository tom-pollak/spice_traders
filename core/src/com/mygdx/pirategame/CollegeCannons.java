package com.mygdx.pirategame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class CollegeCannons extends InteractiveTileObject {
    public CollegeCannons(GameScreen screen, Rectangle bounds) {
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
