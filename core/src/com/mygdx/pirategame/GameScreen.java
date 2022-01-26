package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;

import java.util.PriorityQueue;

public class GameScreen implements Screen {
    private static float maxSpeed = 2;
    private static float accel = 0.02f;

    protected PirateGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private final Stage stage;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;
    private EnemyShip enemyShip;
    private Hud hud;
    private Coin coin;

    public static final int GAME_RUNNING = 0;
    public static final int GAME_PAUSED = 1;
    private static int gameStatus;
    private float cannonFire;

    private Table pauseTable;
    private Table table;

    public GameScreen(PirateGame game){
        gameStatus = GAME_RUNNING;
        this.game = game;
        // Initialising camera and extendable viewport for viewing game
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(PirateGame.WIDTH / PirateGame.PPM, PirateGame.HEIGHT / PirateGame.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Initialize a hud
        hud = new Hud(game.batch,this.game);

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
        enemyShip = new EnemyShip(this, 714 / PirateGame.PPM, 1200 / PirateGame.PPM);
        coin = new Coin(this, 700 / PirateGame.PPM, 1000 / PirateGame.PPM);
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        //BUTTONS ADD
        Skin skin = new Skin(Gdx.files.internal("skin\\uiskin.json"));

        //GAME BUTTONS
        final TextButton pauseButton = new TextButton("Pause",skin);
        final TextButton skill = new TextButton("Skill Tree", skin);

        //PAUSE MENU BUTTONS
        final TextButton start = new TextButton("Resume", skin);
        final TextButton options = new TextButton("Options", skin);
        TextButton exit = new TextButton("Exit", skin);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        pauseTable = new Table();
        pauseTable.setFillParent(true);
        stage.addActor(pauseTable);

        if (gameStatus == GAME_PAUSED){
            table.setVisible(false);
            pauseTable.setVisible(true);
        }
        else{
            pauseTable.setVisible(false);
            table.setVisible(true);
        }

        //ADD TO TABLES
        table.add(pauseButton);
        table.row().pad(10, 0, 10, 0);
        table.left().top();

        pauseTable.add(start).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(skill).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(options).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(exit).fillX().uniformX();
        pauseTable.center();

        //BUTTON LISTENERS
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                table.setVisible(false);
                pauseTable.setVisible(true);
                pause();

            }
        });
        skill.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                pauseTable.setVisible(false);
                game.changeScreen(PirateGame.SKILL);
            }
        });
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseTable.setVisible(false);
                table.setVisible(true);
                resume();

            }
        });
        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseTable.setVisible(false);
                game.setScreen(new Options(game,game.getScreen()));
            }
        }
        );
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    public void handleInput(float dt) {
        if (gameStatus == GAME_RUNNING) {
            // Left physics impulse on 'A'
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.b2body.applyLinearImpulse(new Vector2(-accel, 0), player.b2body.getWorldCenter(), true);
            }
            // Right physics impulse on 'D'
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.b2body.applyLinearImpulse(new Vector2(accel, 0), player.b2body.getWorldCenter(), true);
            }
            // Up physics impulse on 'W'
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.b2body.applyLinearImpulse(new Vector2(0, accel), player.b2body.getWorldCenter(), true);
            }
            // Down physics impulse on 'S'
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.b2body.applyLinearImpulse(new Vector2(0, -accel), player.b2body.getWorldCenter(), true);
            }
            // Cannon fire on 'E'
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                player.fire();
            }
            // Checking if player at max velocity, and keeping them below max
            if (player.b2body.getLinearVelocity().x >= maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(-accel, 0), player.b2body.getWorldCenter(), true);
            }
            if (player.b2body.getLinearVelocity().x <= -maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(accel, 0), player.b2body.getWorldCenter(), true);
            }
            if (player.b2body.getLinearVelocity().y >= maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(0, -accel), player.b2body.getWorldCenter(), true);
            }
            if (player.b2body.getLinearVelocity().y <= -maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(0, accel), player.b2body.getWorldCenter(), true);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(gameStatus == GAME_PAUSED) {
                resume();
                table.setVisible(true);
                pauseTable.setVisible(false);
            }
            else {
                table.setVisible(false);
                pauseTable.setVisible(true);
                pause();
            }
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
        if (gameStatus == GAME_RUNNING) {
            update(delta);
        }
        else{handleInput(delta);}

        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        if (Hud.getHealth() <= 0) {
            game.changeScreen(PirateGame.DEATH);
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }


    public static void changeAcceleration(Float percentage){
        accel = accel * (1 +(percentage/100));
    }
    public static void changeMaxSpeed(Float percentage){
        maxSpeed = maxSpeed * (1 +(percentage/100));
    }


    @Override
    public void pause() {
        gameStatus = GAME_PAUSED;
    }

    @Override
    public void resume() {
        gameStatus = GAME_RUNNING;
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
        stage.dispose();
    }
}
