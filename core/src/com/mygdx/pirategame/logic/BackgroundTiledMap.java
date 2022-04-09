package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.FloatArray;
import com.mygdx.pirategame.AbstractActor;

import java.util.Objects;


/**
 * Loads renders and calculates collisions for a tiled map.
 */
public class BackgroundTiledMap extends Actor {

    private static final String mapPath = "maps/world.tmx";
    private static final TiledMap map = new TmxMapLoader().load(mapPath);
    private static final MapProperties mapProperties = map.getProperties();
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    Stage stage;

    public BackgroundTiledMap(Stage stage) {
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 70, 50);
        camera.update();
        this.stage = stage;
        this.stage.getViewport().setCamera(camera);

    }

    public static Integer getMapWidth() {
        return mapProperties.get("width", Integer.class);
    }

    public static Integer getMapHeight() {
        return mapProperties.get("height", Integer.class);
    }

    public static Integer getTilePixelWidth() {
        return mapProperties.get("tilewidth", Integer.class);
    }

    public static Integer getTilePixelHeight() {
        return mapProperties.get("tileheight", Integer.class);
    }

    public static Vector2 tileToWorldCoords(Integer tileX, Integer tileY) {
        return new Vector2(tileX * BackgroundTiledMap.getTilePixelWidth(), tileY * BackgroundTiledMap.getTilePixelHeight());
    }

    public static Vector2 worldToTileCoords(Integer worldX, Integer worldY) {
        return new Vector2(worldX / BackgroundTiledMap.getTilePixelWidth(), worldY / BackgroundTiledMap.getTilePixelHeight());
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public MapLayers getLayers() {
        return map.getLayers();
    }

    public TiledMap getTiledMap() {
        return map;
    }

    /**
     * Checks if the tile at the given coordinates is blocked.
     *
     * @param tileX: the x coordinate of the tile
     * @param tileY: the y coordinate of the tile
     * @return true if the tile has the blocked property, false otherwise
     */
    public boolean isTileBlocked(int tileX, int tileY) {
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            if (!Objects.equals(map.getLayers().get(i).getName(), "collisionBoxes")) {
                TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(i);
                TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
                if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"))
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns the tile coordinates of the given world coordinates.
     *
     * @param x the x coordinate of the world
     * @param y the y coordinate of the world
     * @return a pair containing the tile x and tile y coordinates
     */
    public Pair<Integer, Integer> getTileCoords(float x, float y) {
        return new Pair<>((int) (x / getTilePixelWidth()), (int) (y / getTilePixelHeight()));
    }

    public String getMapPath() {
        return mapPath;
    }

    @Override
    public void act(float delta) {
        render();
    }

    public void render() {
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        renderer.dispose();
        map.dispose();
    }

    /**
     * Checks whether the two givin polygons intersect.
     *
     * @param hitbox   the first polygon
     * @param collider the second polygon
     * @return true if the two polygons intersect, false otherwise
     */
    public boolean isCollision(Polygon hitbox, Polygon collider) {
        return Intersector.intersectPolygons(new FloatArray(hitbox.getTransformedVertices()), new FloatArray(collider.getTransformedVertices()));
    }

    /**
     * Calculates if the ship has collided with land on the x or y axis.
     *
     * @param actor the ship to check
     * @param oldX  the old x coordinate of the ship
     * @param oldY  the old y coordinate of the ship
     * @return A boolean pair containing the x and y axis collision
     */
    public Pair<Boolean, Boolean> getMapCollisions(AbstractActor actor, float oldX, float oldY) {
        boolean collidedX = false;
        boolean collidedY = false;

        if (actor.getX() < 0 || actor.getRight() > map.getProperties().get("width", Integer.class) * getTilePixelWidth()) {
            collidedX = true;
        }
        if (actor.getY() < 0 || actor.getTop() > map.getProperties().get("height", Integer.class) * getTilePixelHeight()) {
            collidedY = true;
        }


        MapLayer collisionObjectLayer = map.getLayers().get("collisionBoxes");
        MapObjects objects = collisionObjectLayer.getObjects();
        Polygon hitbox = actor.getHitbox();

        for (PolygonMapObject colliders : objects.getByType(PolygonMapObject.class)) {
            Polygon collider = colliders.getPolygon();
            if (isCollision(hitbox, collider)) {
                hitbox.setPosition(actor.getX(), oldY);
                if (isCollision(hitbox, collider)) collidedX = true;

                hitbox.setPosition(oldX, actor.getY());
                if (isCollision(hitbox, collider)) collidedY = true;
            }
        }
        return new Pair<>(collidedX, collidedY);
    }
}