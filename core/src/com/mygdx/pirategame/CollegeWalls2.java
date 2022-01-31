package com.mygdx.pirategame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class CollegeWalls2 extends InteractiveTileObject {
    private GameScreen screen;
    public CollegeWalls2(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.COLLEGE_BIT);
    }

    @Override
    public void onContact() {
        Gdx.app.log("wall", "collision");
        screen.getCollege("Derwent").onContact();
    }
}
