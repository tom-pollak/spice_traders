package com.mygdx.pirategame.logic;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.pirategame.screens.GameScreen;

public class MapBodyBuilder {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float ppt = 0;

    public static void buildTiles(BackgroundTiledMap map, GameScreen screen) {
        ppt = screen.ppt;
        World world = screen.getWorld();
        MapObjects objects = map.getLayers().get("collisionBoxes").getObjects();
        System.out.println(objects.getCount());


        //        for (MapObject object : objects) {
        //            if (object instanceof PolygonMapObject) {
        //                PolygonShape shape = new PolygonShape();
        //                shape.set(((PolygonMapObject) object).getPolygon().getTransformedVertices());
        //                BodyDef bDef = new BodyDef();
        //                bDef.type = BodyDef.BodyType.StaticBody;
        //                Body body = world.createBody(bDef);
        //
        //                FixtureDef fDef = new FixtureDef();
        //                fDef.filter.categoryBits = PirateGame.DEFAULT_BIT;
        //                fDef.shape = shape;
        //                body.createFixture(fDef);
        //                shape.dispose();
        //                //                shape = getPolygon((PolygonMapObject) object);
        //            } else {
        //                System.out.println("Tile is not a polygon map object");
        //            }
        //        }
        System.out.println("Tiles built");
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        //        float[] worldVertices = new float[vertices.length];
        //
        //        for (int i = 0; i < vertices.length; ++i) {
        //            worldVertices[i] = vertices[i] / ppt;
        //        }

        polygon.set(vertices);
        return polygon;
    }
}