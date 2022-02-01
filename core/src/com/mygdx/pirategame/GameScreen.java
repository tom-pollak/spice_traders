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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.*;
import java.util.Random;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Game Screen
 * Class to generate the various screens used to play the game.
 * Instantiates all screen types and displays current screen.
 *
 *@author Ethan Alabaster, Adam Crook, Joe Dickinson, Sam Pearson, Tom Perry, Edward Poulter
 *@version 1.0
 */
public class GameScreen implements Screen {
    private static float maxSpeed = 2.5f;
    private static float accel = 0.05f;
    private float stateTime;

    protected static PirateGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private final Stage stage;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;
    private static HashMap<String, College> colleges = new HashMap<>();
    private static ArrayList<EnemyShip> ships = new ArrayList<>();
    private ArrayList<Coin> Coins = new ArrayList<>();
    private AvailableSpawn invalidSpawn = new AvailableSpawn();
    private Hud hud;

    public static final int GAME_RUNNING = 0;
    public static final int GAME_PAUSED = 1;
    private static int gameStatus;

    private Table pauseTable;
    private Table table;

    public Random rand = new Random();

    /**
     * Initialises the Game Sceen,
     * generates the world data and data for entities that exist upon it
     * @param game passes game data to current class
     */
    public GameScreen(PirateGame game){
        gameStatus = GAME_RUNNING;
        this.game = game;
        // Initialising camera and extendable viewport for viewing game
        camera = new OrthographicCamera();
        camera.zoom = 0.0155f;
        viewport = new ScreenViewport(camera);
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
        colleges = new HashMap<>();
        colleges.put("Alcuin", new College(this, "Alcuin", 1900 / PirateGame.PPM, 2100 / PirateGame.PPM,
                "alcuin_flag.png", "alcuin_ship.png", 0, invalidSpawn));
        colleges.put("Anne Lister", new College(this, "Anne Lister", 6304 / PirateGame.PPM, 1199 / PirateGame.PPM,
                "anne_lister_flag.png", "anne_lister_ship.png", 8, invalidSpawn));
        colleges.put("Constantine", new College(this, "Constantine", 6240 / PirateGame.PPM, 6703 / PirateGame.PPM,
                "constantine_flag.png", "constantine_ship.png", 8, invalidSpawn));
        colleges.put("Goodricke", new College(this, "Goodricke", 1760 / PirateGame.PPM, 6767 / PirateGame.PPM,
                "goodricke_flag.png", "goodricke_ship.png", 8, invalidSpawn));
        ships = new ArrayList<>();
        ships.addAll(colleges.get("Alcuin").fleet);
        ships.addAll(colleges.get("Anne Lister").fleet);
        ships.addAll(colleges.get("Constantine").fleet);
        ships.addAll(colleges.get("Goodricke").fleet);

        //Random coins
        Boolean validLoc;
        int a = 0;
        int b = 0;
        for (int i = 0; i < 20; i++) {
            validLoc = false;
            while (!validLoc) {
                a = rand.nextInt(AvailableSpawn.xCap - AvailableSpawn.xBase) + AvailableSpawn.xBase;
                b = rand.nextInt(AvailableSpawn.yCap - AvailableSpawn.yBase) + AvailableSpawn.yBase;
                validLoc = checkGenPos(a, b);
            }
            ships.add(new EnemyShip(this, a, b, "unaligned_ship.png", "Unaligned"));
        }
        for (int i = 0; i < 100; i++) {
            validLoc = false;
            while (!validLoc) {
                a = rand.nextInt(AvailableSpawn.xCap - AvailableSpawn.xBase) + AvailableSpawn.xBase;
                b = rand.nextInt(AvailableSpawn.yCap - AvailableSpawn.yBase) + AvailableSpawn.yBase;
                validLoc = checkGenPos(a, b);
            }
            Coins.add(new Coin(this, a, b));
        }

        stage = new Stage(new ScreenViewport());
    }

    private Boolean checkGenPos(int x, int y){
        if (invalidSpawn.tileBlocked.containsKey(x)){
            ArrayList<Integer> yTest = invalidSpawn.tileBlocked.get(x);
            if (yTest.contains(y)) {
                return false;
            }
        }
        return true;
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

    public void update(float dt) {
        stateTime += dt;
        handleInput(dt);
        // Stepping the physics engine by time of 1 frame
        world.step(1 / 60f, 6, 2);

        // Update all players and entities
        player.update(dt);
        colleges.get("Alcuin").update(dt);
        colleges.get("Anne Lister").update(dt);
        colleges.get("Constantine").update(dt);
        colleges.get("Goodricke").update(dt);

        for (int i = 0; i < ships.size(); i++) {
            ships.get(i).update(dt);
        }

        for (int i = 0; i < Coins.size(); i++) {
            Coins.get(i).update();
        }
        if (stateTime > 1) {
            if (!colleges.get("Anne Lister").destroyed) {
                colleges.get("Anne Lister").fire();
            }
            if (!colleges.get("Constantine").destroyed) {
                colleges.get("Constantine").fire();
            }
            if (!colleges.get("Goodricke").destroyed) {
                colleges.get("Goodricke").fire();
        }
        stateTime = 0;
    }

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

        Gdx.gl.glClearColor(46/255f, 204/255f, 113/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        // b2dr is the hitbox shapes, can be commented out to hide
        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        // Order determines layering

        for(int i=0;i<Coins.size();i++) {

            Coins.get(i).draw(game.batch);
        }


        player.draw(game.batch);
        colleges.get("Alcuin").draw(game.batch);
        colleges.get("Anne Lister").draw(game.batch);
        colleges.get("Constantine").draw(game.batch);
        colleges.get("Goodricke").draw(game.batch);
        for (int i = 0; i < ships.size(); i++){
            if (ships.get(i).college != "Unaligned") {
                if (colleges.get(ships.get(i).college).destroyed) {
                    ships.get(i).updateTexture("Alcuin", "alcuin_ship.png");
                }
            }
            ships.get(i).draw(game.batch);
        }
        game.batch.end();
        Hud.stage.draw();
        stage.act();
        stage.draw();
        gameOverCheck();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
        Hud.resize( width, height);
        camera.update();
        renderer.setView(camera);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public College getCollege(String collegeName) {
        return colleges.get(collegeName);
    }

    public static void gameOverCheck(){
        if (Hud.getHealth() <= 0 || colleges.get("Alcuin").destroyed) {
            game.changeScreen(PirateGame.DEATH);
            game.killGame();
        }
        else if (colleges.get("Anne Lister").destroyed && colleges.get("Constantine").destroyed && colleges.get("Goodricke").destroyed){
            game.changeScreen(PirateGame.VICTORY);
            game.killGame();
        }
    }

    public Vector2 getPlayerPos(){
        return new Vector2(player.b2body. getPosition().x,player.b2body.getPosition().y);
    }


    public static void changeAcceleration(Float percentage){
        accel = accel * (1 +(percentage/100));
    }
    public static void changeMaxSpeed(Float percentage){
        maxSpeed = maxSpeed * (1 +(percentage/100));
    }
    public static void changeDamage(int value){
        for (int i = 0; i < ships.size(); i++){
            ships.get(i).changeDamageReceived(value);
        }
        colleges.get("Anne Lister").changeDamageReceived(value);
        colleges.get("Constantine").changeDamageReceived(value);
        colleges.get("Goodricke").changeDamageReceived(value);

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
