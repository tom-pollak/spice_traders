package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.logic.*;
import com.mygdx.pirategame.sprites.*;
import com.mygdx.pirategame.tiles.WorldCreator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Game Screen Class to generate the various screens used to play the game. Instantiates all screen
 * types and displays current screen.
 *
 * @author Ethan Alabaster, Adam Crook, Joe Dickinson, Sam Pearson, Tom Perry, Edward Poulter
 * @version 1.0
 */
public class GameScreen implements Screen {
    public static final int GAME_RUNNING = 0;
    public static final int GAME_PAUSED = 1;
    public static PirateGame game;
    public static Player player;
    public static ArrayList<AiShip> ships = new ArrayList<>();
    public static int gameStatus;
    public static int difficulty;
    private static HashMap<String, College> colleges = new HashMap<>();
    private static ArrayList<Coin> Coins = new ArrayList<>();
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final World world = new World(new Vector2(0, 0), true);
    private final Box2DDebugRenderer b2dr;
    private final AvailableSpawn invalidSpawn = new AvailableSpawn();
    private final Inventory inventoryHud;
    private final ArrayList<SeaMonster> monsters = new ArrayList<>();
    private final ArrayList<Weather> weathers = new ArrayList<>();
    public Random rand = new Random();
    public Boolean loadSaveData;
    private float stateTime;
    private Hud hud;
    private Table pauseTable;
    private Table table;

    /**
     * Initialises the Game Screen, generates the world data and data for entities that exist upon it,
     *
     * @param game passes game data to current class,
     */
    public GameScreen(PirateGame game) {
        gameStatus = GAME_RUNNING;
        GameScreen.game = game;
        // Initialising camera and extendable viewport for viewing game
        camera = new OrthographicCamera();
        camera.zoom = 0.0155f;
        viewport = new ScreenViewport(camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        player = new Player(this, 10, 10, colleges.get("Alcuin"));

        // Initialize a hud
        hud = createHud(game.batch);

        // Initialising box2d physics
        b2dr = new Box2DDebugRenderer();

        // making the Tiled tmx file render as a map
        TmxMapLoader maploader = new TmxMapLoader();
        map = maploader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PirateGame.PPM);

        // Setting up contact listener for collisions
        world.setContactListener(new WorldContactListener());

        // Spawning enemy ship and coin. x and y is spawn location
        colleges = new HashMap<>();
        colleges.put("Alcuin", new College(this, "Alcuin", 1900 / PirateGame.PPM, 2100 / PirateGame.PPM, "alcuin_flag.png", "alcuin_ship.png", 0, invalidSpawn));
        colleges.put("Anne Lister", new College(this, "Anne Lister", 6304 / PirateGame.PPM, 1199 / PirateGame.PPM, "anne_lister_flag.png", "anne_lister_ship.png", 2, invalidSpawn));
        colleges.put("Constantine", new College(this, "Constantine", 6240 / PirateGame.PPM, 6703 / PirateGame.PPM, "constantine_flag.png", "constantine_ship.png", 1, invalidSpawn));
        colleges.put("Goodricke", new College(this, "Goodricke", 1760 / PirateGame.PPM, 6767 / PirateGame.PPM, "goodricke_flag.png", "goodricke_ship.png", 1, invalidSpawn));
        ships = new ArrayList<>();
        ships.addAll(colleges.get("Alcuin").fleet);
        ships.addAll(colleges.get("Anne Lister").fleet);
        ships.addAll(colleges.get("Constantine").fleet);
        ships.addAll(colleges.get("Goodricke").fleet);

        monsters.add(new SeaMonster(this, 20, 20));

        new WorldCreator(this);
        // Random ships
        boolean validLoc;
        int a = 0;
        int b = 0;
        //        for (int i = 0; i < 0; i++) {
        //            validLoc = false;
        //            while (!validLoc) {
        //                //Get random x and y coords
        //                a = rand.nextInt(AvailableSpawn.xCap - AvailableSpawn.xBase) +
        // AvailableSpawn.xBase;
        //                b = rand.nextInt(AvailableSpawn.yCap - AvailableSpawn.yBase) +
        // AvailableSpawn.yBase;
        //                //Check if valid
        //                validLoc = checkGenPos(a, b);
        //            }
        //            //Add a ship at the random coords
        //            ships.add(new AiShip(this, a, b, "unaligned_ship.png", College.NEUTRAL));
        //        }

        // Random coins
        Coins = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            validLoc = false;
            while (!validLoc) {
                // Get random x and y coords
                a = rand.nextInt(AvailableSpawn.xCap - AvailableSpawn.xBase) + AvailableSpawn.xBase;
                b = rand.nextInt(AvailableSpawn.yCap - AvailableSpawn.yBase) + AvailableSpawn.yBase;
                validLoc = checkGenPos(a, b);
            }
            // Add a coins at the random coords
            Coins.add(new Coin(this, a, b));
        }

        // Setting stage
        stage = new Stage(new ScreenViewport());
        inventoryHud = new Inventory(player);
        loadSaveData = false;
    }

    /**
     * Changes the amount of damage done by each hit. Accessed by skill tree
     *
     * @param value damage dealt
     */
    public static void changeDamage(int value) {

        for (AiShip ship : ships) {
            ship.changeDamageReceived(value);
        }
        colleges.get("Anne Lister").changeDamageReceived(value);
        colleges.get("Constantine").changeDamageReceived(value);
        colleges.get("Goodricke").changeDamageReceived(value);
    }

    /**
     * Updates acceleration by a given percentage. Accessed by skill tree
     *
     * @param percentage percentage increase
     */
    public static void changeAcceleration(Float percentage) {
        player.setAccel(player.getAccel() * (1 + (percentage / 100)));
    }

    /**
     * Updates max speed by a given percentage. Accessed by skill tree
     *
     * @param percentage percentage increase
     */
    public static void changeMaxSpeed(Float percentage) {
        player.setMaxSpeed(player.getMaxSpeed() * (1 + (percentage / 100)));
    }

    /**
     * Creates a new HUD for the player
     *
     * @param batch batch to draw to
     * @return the new HUD
     */
    public Hud createHud(SpriteBatch batch) {
        return new Hud(batch);
    }

    /**
     * Creates new HUD for the player
     *
     * @param batch     batch to draw to
     * @param health    health of the player
     * @param coins     coins of the player
     * @param score     score of the player
     * @param coinMulti player's coin multiplier
     * @return the new HUD
     */
    public Hud createHud(SpriteBatch batch, int health, int coins, int score, int coinMulti) {
        Hud hud = new Hud(batch);
        Hud.setHealth(health);
        hud.setCoins(coins);
        hud.setScore(score);
        hud.setCoinMulti(coinMulti);
        return hud;
    }

    /**
     * Makes this the current screen for the game. Generates the buttons to be able to interact with
     * what screen is being displayed. Creates the escape menu and pause button
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // GAME BUTTONS
        final TextButton pauseButton = new TextButton("Pause", skin);
        final TextButton skill = new TextButton("Skill Tree", skin);
        final TextButton shop = new TextButton("Shop", skin);

        // PAUSE MENU BUTTONS
        final TextButton start = new TextButton("Resume", skin);
        final TextButton options = new TextButton("Options", skin);
        final TextButton save = new TextButton("Save", skin);
        final TextButton load = new TextButton("Load", skin);
        TextButton exit = new TextButton("Exit", skin);

        // Create main table and pause tables
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        pauseTable = new Table();
        pauseTable.setFillParent(true);
        stage.addActor(pauseTable);

        // Set the visability of the tables. Particuarly used when coming back from options or skillTree
        if (gameStatus == GAME_PAUSED) {
            table.setVisible(false);
            pauseTable.setVisible(true);
        } else {
            pauseTable.setVisible(false);
            table.setVisible(true);
        }

        // ADD TO TABLES
        table.add(pauseButton);
        table.row().pad(10, 0, 10, 0);
        table.left().top();

        pauseTable.add(start).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(skill).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(shop).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(options).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(save).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(load).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(exit).fillX().uniformX();
        pauseTable.center();

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                table.setVisible(false);
                pauseTable.setVisible(true);
                pause();
            }
        });
        skill.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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

        shop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseTable.setVisible(false);
                game.setScreen(new ShopScreen(game));
            }
        });

        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseTable.setVisible(false);
                game.setScreen(new Options(game, game.getScreen()));
            }
        });
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseTable.setVisible(false);
                SaveGame.save(player, ships, Coins, hud, "save.json");
                resume();
            }
        });
        load.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseTable.setVisible(false);
                try {
                    load("save.json");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                resume();
            }
        });
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    /**
     * Updates the state of each object with delta time
     *
     * @param dt Delta time (elapsed time since last game tick)
     */
    public void update(float dt) {
        stateTime += dt;
        // Stepping the physics engine by time of 1 frame
        world.step(1 / 60f, 6, 2);

        // Update all players and entities
        player.update(dt);
        inventoryHud.update();
        for (College college : colleges.values()) {
            college.update(dt);
        }

        // Updates coin
        for (Coin coin : Coins) {
            coin.update();
        }

        for (SeaMonster monster : monsters) {
            monster.update(dt);
        }
        createRandomWeatherEvents();
        for (Weather weather : weathers) {
            weather.update(dt);
        }
        // After a delay check if a college is destroyed. If not, if can fire
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

    /**
     * Renders the visual data for all objects Changes and renders new visual data for ships
     *
     * @param dt Delta time (elapsed time since last game tick)
     */
    @Override
    public void render(float dt) {
        if (gameStatus == GAME_RUNNING) {
            update(dt);
        } else {
            handleInput(dt);
        }

        Gdx.gl.glClearColor(46 / 255f, 204 / 255f, 113 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        // b2dr is the hitbox shapes, can be commented out to hide
        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        // Order determines layering

        // Renders coins
        for (Coin coin : Coins) {
            coin.draw(game.batch);
        }
        for (SeaMonster monster : monsters) {
            monster.draw(game.batch);
        }
        for (Weather weather : weathers) {
            weather.draw(game.batch);
        }

        // Renders colleges
        player.draw(game.batch);
        colleges.get("Alcuin").draw(game.batch);
        colleges.get("Anne Lister").draw(game.batch);
        colleges.get("Constantine").draw(game.batch);
        colleges.get("Goodricke").draw(game.batch);

        // Updates all ships
        //        for (AiShip ship : ships) {
        //            if (!Objects.equals(ship.college, "Unaligned")) {
        //                //Flips a colleges allegence if their college is destroyed
        //                if (colleges.get(ship.college).destroyed) {
        //
        //                    ship.updateTexture(ship.college, "alcuin_ship.png");
        //                }
        //            }
        //            ship.draw(game.batch);
        //        }
        game.batch.end();
        Hud.stage.draw();
        Inventory.stage.act();
        Inventory.stage.draw();
        stage.act();
        stage.draw();
        // Checks game over conditions
        gameOverCheck();
    }

    /**
     * Changes the camera size, Scales the hud to match the camera
     *
     * @param width  the width of the viewable area
     * @param height the height of the viewable area
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
        Hud.resize(width, height);
        camera.update();
        renderer.setView(camera);
    }

    /**
     * Returns the map
     *
     * @return map : returns the world map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Returns the world (map and objects)
     *
     * @return world : returns the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Returns the college from the colleges hashmap
     *
     * @param collegeName uses a college name as an index
     * @return college : returns the college fetched from colleges
     */
    public College getCollege(String collegeName) {
        return colleges.get(collegeName);
    }

    /**
     * Checks if the game is over i.e. goal reached (all colleges bar "Alcuin" are destroyed)
     */
    public void gameOverCheck() {
        // Lose game if ship on 0 health or Alcuin is destroyed
        if (Hud.getHealth() <= 0 || colleges.get("Alcuin").destroyed) {
            game.changeScreen(PirateGame.DEATH);
            game.killGame();
        }
        // Win game if all colleges destroyed
        else if (colleges.get("Anne Lister").destroyed && colleges.get("Constantine").destroyed && colleges.get("Goodricke").destroyed) {
            game.changeScreen(PirateGame.VICTORY);
            game.killGame();
        }
    }

    /**
     * Fetches the player's current position
     *
     * @return position vector : returns the position of the player
     */
    public Vector2 getPlayerPos() {
        return new Vector2(player.b2body.getPosition().x, player.b2body.getPosition().y);
    }

    /**
     * Tests validity of randomly generated position
     *
     * @param x random x value
     * @param y random y value
     */
    private Boolean checkGenPos(int x, int y) {
        if (invalidSpawn.tileBlocked.containsKey(x)) {
            ArrayList<Integer> yTest = invalidSpawn.tileBlocked.get(x);
            return !yTest.contains(y);
        }
        return true;
    }

    /**
     * Pauses game
     */
    @Override
    public void pause() {
        gameStatus = GAME_PAUSED;
    }

    /**
     * Resumes game
     */
    @Override
    public void resume() {
        gameStatus = GAME_RUNNING;
    }

    /**
     * (Not Used) Hides game
     */
    @Override
    public void hide() {
    }

    /**
     * Disposes game data
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        stage.dispose();
    }

    public void setPlayerPos(Float x, Float y) {
        player.setX(x);
        player.setY(y);
    }

    public void load(String filename) throws FileNotFoundException {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            Object obj = jsonParser.parse(reader);
            JSONObject list = (JSONObject) obj;
            loadPlayer(list);
            loadEnemyShips(list);
            loadHud(list);
            loadCoins((list));

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        update(0);
    }

    /**
     * Loads the player's data from the JSON file
     *
     * @param json : JSON object to load from
     */
    private void loadPlayer(JSONObject json) {
        JSONArray playerData = (JSONArray) json.get("player");
        JSONArray playerPos = (JSONArray) playerData.get(0);
        float playerDirection = ((Double) playerData.get(1)).floatValue();
        // JSON reader insisted on a value being read being a double, so it must be a double converted
        // to float
        GameScreen.player.b2body.setTransform(((Double) playerPos.get(0)).floatValue(), ((Double) playerPos.get(1)).floatValue(), playerDirection);
        GameScreen.player.b2body.setLinearVelocity(0, 0);
        player.inventory.clear();
        JSONArray inventoryData = (JSONArray) playerData.get(2);
        for (Object item : inventoryData) {
            String type = ((JSONArray) item).get(0).toString();
            String texturePath = ((JSONArray) item).get(1).toString();
            Texture texture = new Texture(texturePath);
            Item loadedItem = new Item(type, player, texture);

            JSONObject buffs = (JSONObject) ((JSONArray) item).get(2);
            Set keySet = buffs.keySet();
            for (Object key : keySet) {
                String keyString = key.toString();
                loadedItem.buffs.put(keyString, ((Double) buffs.get(keyString)).floatValue());
            }
            player.inventory.add(loadedItem);
        }
    }

    /**
     * Loads enemy ships from JSON object
     *
     * @param json JSON object to load from
     */
    private void loadEnemyShips(JSONObject json) {
        JSONArray allShips = (JSONArray) json.get("enemyShips");
        GameScreen.ships.clear();
        GameScreen.colleges.forEach((key, college) -> college.fleet.clear());
        for (Object allShip : allShips) {
            JSONArray shipData = (JSONArray) allShip;
            JSONArray shipPos = (JSONArray) shipData.get(0);
            String college = (shipData.get(5)).toString();
            AiShip restoredShip = new AiShip(this, 0, 0, shipData.get(6).toString(), colleges.get(college));
            restoredShip.health = ((Long) shipData.get(3)).intValue();
            restoredShip.damage = ((Long) shipData.get(4)).intValue();
            JSONArray shipVelocity = (JSONArray) shipData.get(2);
            float shipAngle = ((Double) shipData.get(1)).floatValue();
            restoredShip.b2body.setTransform(((Double) shipPos.get(0)).floatValue(), ((Double) shipPos.get(1)).floatValue(), shipAngle);
            restoredShip.b2body.setLinearVelocity(((Double) shipVelocity.get(0)).floatValue(), ((Double) shipVelocity.get(1)).floatValue());
            GameScreen.ships.add(restoredShip);
            if (GameScreen.colleges.containsKey(college)) {
                GameScreen.colleges.get(college).fleet.add(restoredShip);
            }
        }
    }

    /**
     * Loads the HUD data from the JSON object.
     *
     * @param json The JSON file to load from.
     */
    private void loadHud(JSONObject json) {
        JSONArray hudData = (JSONArray) json.get("hud");
        int health = ((Long) hudData.get(0)).intValue();
        int coins = ((Long) hudData.get(1)).intValue();
        int score = ((Long) hudData.get(2)).intValue();
        int coinMult = ((Long) hudData.get(3)).intValue();
        hud = createHud(game.batch, health, coins, score, coinMult);
        // Time changed by 0 as the function updates parts of the HUD gui
    }

    /**
     * Loads game's coins from JSON file
     *
     * @param json JSON object to load from
     */
    private void loadCoins(JSONObject json) {
        JSONArray coinsArray = (JSONArray) json.get("coins");
        Coins.clear();
        for (Object coinData : coinsArray) {
            JSONArray position = (JSONArray) coinData;
            float x = ((Double) position.get(0)).floatValue();
            float y = ((Double) position.get(1)).floatValue();
            Coins.add(new Coin(this, x, y));
        }
    }

    /**
     * Handles the escape key press, which will pause the game.
     *
     * @param dt Delta time
     */
    public void handleInput(Float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (gameStatus == GAME_PAUSED) {
                resume();
                table.setVisible(true);
                pauseTable.setVisible(false);
            } else {
                table.setVisible(false);
                pauseTable.setVisible(true);
                pause();
            }
        }
    }

    /**
     * Creates random weather entities in random positions
     */
    public void createRandomWeatherEvents() {
        if (MathUtils.random(0, 100) < 20) {
            float x = MathUtils.random(0, stage.getWidth());
            float y = MathUtils.random(0, stage.getHeight());
            Weather cloud = new Weather(this, x, y, MathUtils.random(1, 20), MathUtils.random(1, 20), "cloud.png");
            cloud.setDamageOnTurn(10);
            cloud.setMovement(new Vector2(MathUtils.random(), MathUtils.random()), MathUtils.random(0.2f, 1.5f));
            weathers.add(cloud);
            Gdx.app.log("Weather", "Cloud created: " + x + " " + y);
        }
    }

    public Player getPlayer() {
        return player;
    }
    //        for (int i = 0; i < 0; i++) {
    //            validLoc = false;
    //            while (!validLoc) {
    //                //Get random x and y coords
    //                a = rand.nextInt(AvailableSpawn.xCap - AvailableSpawn.xBase) +
    // AvailableSpawn.xBase;
    //                b = rand.nextInt(AvailableSpawn.yCap - AvailableSpawn.yBase) +
    // AvailableSpawn.yBase;
    //                //Check if valid
    //                validLoc = checkGenPos(a, b);
    //            }
    //            //Add a ship at the random coords
    //            ships.add(new AiShip(this, a, b, "unaligned_ship.png", College.NEUTRAL));
    //        }

}
