package com.mygdx.pirategame.logic;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;

public class MapBodyBuilder {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float ppt = 0;

    public static void buildTiles(BackgroundTiledMap map, GameScreen screen) {
        ppt = screen.ppt;
        World world = screen.getWorld();
        MapObjects objects = map.getLayers().get("collisionBoxes").getObjects();


        for (MapObject object : objects) {
            PolygonShape shape;
            if (object instanceof TextureMapObject) {
                continue;
            } else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject) object);
            } else {
                System.out.println("Tile is not a polygon map object");
                continue;
            }

            BodyDef bDef = new BodyDef();
            bDef.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bDef);

            FixtureDef fDef = new FixtureDef();
            fDef.filter.categoryBits = PirateGame.DEFAULT_BIT;
            fDef.shape = shape;
            body.createFixture(fDef);

            shape.dispose();
        }
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }
}