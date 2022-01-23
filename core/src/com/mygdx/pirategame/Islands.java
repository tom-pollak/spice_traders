package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Islands extends InteractiveTileObject {
    public Islands(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.ISLAND_BIT);
    }

    @Override
    public void onContact() {
        Gdx.app.log("island", "collision");
        Hud.changeHealth(-10);
    }
}
