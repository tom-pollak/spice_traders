package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;

public class GameScreen implements Screen {
    public static final float maxSpeed = 2;
    public static final float accel = 0.02f;

    private PirateGame game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;
    private EnemyShip enemyShip;
    private Hud hud;
    private Coin coin;

    public GameScreen(PirateGame game){
        this.game = game;
        // Initialising camera and extendable viewport for viewing game
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(PirateGame.WIDTH / PirateGame.PPM, PirateGame.HEIGHT / PirateGame.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Initialize a hud
        hud = new Hud(game.batch);

        // Initialising box2d physics
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();
        player = new Player(this);

        // making the Tiled tmx file render as a map
        maploader = new TmxMapLoader();
        map = maploader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PirateGame.PPM);
        new WorldCreator(this);
        
        // Setting up contact listener for collisions
        world.setContactListener(new WorldContactListener());

        // Spawning enemy ship and coin. x and y is spawn location
        enemyShip = new EnemyShip(this, 1200 / PirateGame.PPM, 900 / PirateGame.PPM);
        coin = new Coin(this, 700 / PirateGame.PPM, 1000 / PirateGame.PPM);
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        // Left physics impulse on 'A'
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.b2body.applyLinearImpulse(new Vector2(-accel, 0), player.b2body.getWorldCenter(), true);
        }
        // Right physics impulse on 'D'
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.b2body.applyLinearImpulse(new Vector2(accel, 0), player.b2body.getWorldCenter(), true);
        }
        // Up physics impulse on 'W'
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.b2body.applyLinearImpulse(new Vector2(0, accel), player.b2body.getWorldCenter(), true);
        }
        // Down physics impulse on 'S'
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.b2body.applyLinearImpulse(new Vector2(0, -accel), player.b2body.getWorldCenter(), true);
        }
        // Checking if player at max velocity, and keeping them below max
        if(player.b2body.getLinearVelocity().x >= maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(-accel, 0), player.b2body.getWorldCenter(), true);
        }
        if(player.b2body.getLinearVelocity().x <= -maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(accel, 0), player.b2body.getWorldCenter(), true);
        }
        if(player.b2body.getLinearVelocity().y >= maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(0, -accel), player.b2body.getWorldCenter(), true);
        }
        if(player.b2body.getLinearVelocity().y <= -maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(0, accel), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt){
        handleInput(dt);
        // Stepping the physics engine by time of 1 frame
        world.step(1 / 60f, 6, 2);

        // Update all players and entities
        player.update(dt);
        enemyShip.update(dt);
        coin.update(dt);
        hud.update(dt);

        // Centre camera on player boat
        camera.position.x = player.b2body.getPosition().x;
        camera.position.y = player.b2body.getPosition().y;
        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        // b2dr is the hitbox shapes, can be commented out to hide
        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        // Order determines layering
        coin.draw(game.batch);
        player.draw(game.batch);
        enemyShip.draw(game.batch);

        game.batch.end();
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
