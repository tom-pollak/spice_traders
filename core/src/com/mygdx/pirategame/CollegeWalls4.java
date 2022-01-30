package com.mygdx.pirategame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class CollegeWalls4 extends InteractiveTileObject {
    public CollegeWalls4(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.COLLEGE_BIT);
    }

    @Override
    public void onContact() {
        Gdx.app.log("wall", "collision");

    }
}
