package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.pirategame.AvailableSpawn;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entities.College;
import com.mygdx.pirategame.entities.EnemyShip;
import com.mygdx.pirategame.entities.Player;
import com.mygdx.pirategame.gui.Hud;
import com.mygdx.pirategame.items.Coin;
import com.mygdx.pirategame.logic.ActorTable;
import com.mygdx.pirategame.logic.BackgroundTiledMap;
import com.mygdx.pirategame.logic.Pathfinding;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Game Screen
 * Class to generate the various screens used to play the parent.
 * Instantiates all screen types and displays current screen.
 *
 * @author Ethan Alabaster, Adam Crook, Joe Dickinson, Sam Pearson, Tom Perry, Edward Poulter
 * @version 1.0
 */
public class GameScreen extends AbstractScreen {
    private static final HashMap<String, College> colleges = new HashMap<>();
    private final Hud hud;
    private Table pauseTable;
    private Table table;

    /**
     * Initialises the Game Screen,
     * generates the world data and data for entities that exist upon it,
     *
     * @param parent passes parent data to current class,
     */
    public GameScreen(PirateGame parent) {
        super(parent);

    }

    /**
     * Updates acceleration by a given percentage. Accessed by skill tree
     *
     * @param percentage percentage increase
     */
    public static void changeAcceleration(Float percentage) {
        accel = accel * (1 + (percentage / 100));
    }

    /**
     * Updates max speed by a given percentage. Accessed by skill tree
     *
     * @param percentage percentage increase
     */
    public static void changeMaxSpeed(Float percentage) {
        maxSpeed = maxSpeed * (1 + (percentage / 100));
    }

    /**
     * Makes this the current screen for the parent.
     * Generates the buttons to be able to interact with what screen is being displayed.
     * Creates the escape menu and pause button
     */
    @Override
    public void show() {


        Pathfinding pathfinding = new Pathfinding("map.tmx");
        BackgroundTiledMap map = new BackgroundTiledMap(stage, "map.tmx");
        stage.addActor(map);
        ActorTable actorTable = new ActorTable(stage, map);
        Hud hud = new Hud(parent.batch);

        Player player = new Player(this);
        stage.addActor(player);
        stage.setKeyboardFocus(player);
        Gdx.input.setInputProcessor(player.input);

        // Spawning enemy ship and coin. x and y is spawn location
        colleges.put("Alcuin", new College(this, "Alcuin", 1900 / PirateGame.PPM, 2100 / PirateGame.PPM, "alcuin_flag.png"));
        colleges.put("Anne Lister", new College(this, "Anne Lister", 6304 / PirateGame.PPM, 1199 / PirateGame.PPM, "anne_lister_flag.png"));
        colleges.put("Constantine", new College(this, "Constantine", 6240 / PirateGame.PPM, 6703 / PirateGame.PPM, "constantine_flag.png"));
        colleges.put("Goodricke", new College(this, "Goodricke", 1760 / PirateGame.PPM, 6767 / PirateGame.PPM, "goodricke_flag.png"));
        ships = new ArrayList<>();
        ships.addAll(colleges.get("Alcuin").fleet);
        ships.addAll(colleges.get("Anne Lister").fleet);
        ships.addAll(colleges.get("Constantine").fleet);
        ships.addAll(colleges.get("Goodricke").fleet);

        //Random ships
        boolean validLoc;
        int a = 0;
        int b = 0;
        for (int i = 0; i < 20; i++) {
            validLoc = false;
            while (!validLoc) {
                //Get random x and y coords
                a = rand.nextInt(AvailableSpawn.xCap - AvailableSpawn.xBase) + AvailableSpawn.xBase;
                b = rand.nextInt(AvailableSpawn.yCap - AvailableSpawn.yBase) + AvailableSpawn.yBase;
                //Check if valid
                validLoc = checkGenPos(a, b);
            }
            //Add a ship at the random coords
            ships.add(new EnemyShip(this, a, b, "unaligned_ship.png", "Unaligned"));
        }


        //Random coins
        Coins = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            validLoc = false;
            while (!validLoc) {
                //Get random x and y coords
                a = rand.nextInt(AvailableSpawn.xCap - AvailableSpawn.xBase) + AvailableSpawn.xBase;
                b = rand.nextInt(AvailableSpawn.yCap - AvailableSpawn.yBase) + AvailableSpawn.yBase;
                validLoc = checkGenPos(a, b);
            }
            //Add a coins at the random coords
            Coins.add(new Coin(this, a, b));
        }
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        //GAME BUTTONS
        final TextButton pauseButton = new TextButton("Pause", skin);
        final TextButton skill = new TextButton("Skill Tree", skin);

        //PAUSE MENU BUTTONS
        final TextButton start = new TextButton("Resume", skin);
        final TextButton options = new TextButton("Options", skin);
        TextButton exit = new TextButton("Exit", skin);


        //Create main table and pause tables
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        pauseTable = new Table();
        pauseTable.setFillParent(true);
        stage.addActor(pauseTable);


        //Set the visability of the tables. Particuarly used when coming back from options or skillTree
        if (gameStatus == GAME_PAUSED) {
            table.setVisible(false);
            pauseTable.setVisible(true);
        } else {
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
                parent.setScreen(parent.SKILL);
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
                parent.setScreen(parent.OPTIONS);
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
     * Checks for input and performs an action
     * Applies to keys "W" "A" "S" "D" "E" "Esc"
     * <p>
     * Caps player velocity
     *
     * @param dt Delta time (elapsed time since last parent tick)
     */
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
     * Renders the visual data for all objects
     * Changes and renders new visual data for ships
     *
     * @param dt Delta time (elapsed time since last parent tick)
     */
    @Override
    public void render(float dt) {
        handleInput(dt); // TODO Not sure if this is needed

        Gdx.gl.glClearColor(46 / 255f, 204 / 255f, 113 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Hud.stage.act();
        Hud.stage.draw();
        stage.act();
        stage.draw();

        // Centre camera on player boat
        camera.position.x = player.getX() + player.getWidth() / 2;
        camera.position.y = player.getY() + player.getHeight() / 2;
        camera.update();
        renderer.setView(camera);
        renderer.render();
        //Checks parent over conditions
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
     * Returns the college from the colleges hashmap
     *
     * @param collegeName uses a college name as an index
     * @return college : returns the college fetched from colleges
     */
    public College getCollege(String collegeName) {
        return colleges.get(collegeName);
    }

    /**
     * Checks if the parent is over
     * i.e. goal reached (all colleges bar "Alcuin" are destroyed)
     */
    public void gameOverCheck() {
        //Lose parent if ship on 0 health or Alcuin is destroyed
        if (Hud.getHealth() <= 0 || colleges.get("Alcuin").destroyed) {
            parent.setScreen(parent.DEATH);
            parent.dispose();
        }
        //Win parent if all colleges destroyed
        else if (colleges.get("Anne Lister").destroyed && colleges.get("Constantine").destroyed && colleges.get("Goodricke").destroyed) {
            parent.setScreen(parent.VICTORY);
            parent.dispose();
        }
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
     * Pauses parent
     */
    @Override
    public void pause() {
        gameStatus = GAME_PAUSED;
    }

    /**
     * Resumes parent
     */
    @Override
    public void resume() {
        gameStatus = GAME_RUNNING;
    }

    /**
     * Disposes parent data
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        hud.dispose();
        stage.dispose();
    }
}
