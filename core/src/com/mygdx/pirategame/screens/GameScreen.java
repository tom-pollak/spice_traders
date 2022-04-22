package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entities.College;
import com.mygdx.pirategame.entities.Player;
import com.mygdx.pirategame.gui.Hud;
import com.mygdx.pirategame.gui.Inventory;
import com.mygdx.pirategame.items.Coin;
import com.mygdx.pirategame.logic.*;


/**
 * Game Screen
 * Class to generate the various screens used to play the parent.
 * Instantiates all screen types and displays current screen.
 *
 * @author Ethan Alabaster, Adam Crook, Joe Dickinson, Sam Pearson, Tom Perry, Edward Poulter
 * @version 1.0
 */
public class GameScreen extends AbstractScreen {
    private final Hud hud;
    private final Inventory inventory_hud;
    private final BackgroundTiledMap map;
    private final Player player;
    private final World world;
    private final Box2DDebugRenderer b2dr;
    public GameState gameState = GameState.PLAY;
    public float ppt;
    ActorTable actorTable;
    private College bossCollege;

    /**
     * Initialises the Game Screen,
     * generates the world data and data for entities that exist upon it,
     *
     * @param parent passes parent data to current class,
     */
    public GameScreen(PirateGame parent) {
        super(parent);
        Gdx.input.setInputProcessor(stage);
        hud = new Hud(parent.batch);
        map = new BackgroundTiledMap(stage);
        stage.addActor(map);

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new WorldContactListener());
        b2dr = new Box2DDebugRenderer();
        MapBodyBuilder.buildTiles(map, this);

        Pathfinding pathfinding = new Pathfinding(map.getMapPath());
        actorTable = new ActorTable(stage, map);

        player = new Player(this);
        player.setPosition(1000, 1000);
        player.addListener(new MyInputProcessor(this));
        stage.setKeyboardFocus(player);

        inventory_hud = new Inventory(player);


        //        College alcuin = new College(this, "Alcuin", new Vector2(1900, 2100), "colleges/alcuin_flag.png", "colleges/alcuin_ship.png");
        College alcuin = new College(this, "Alcuin", new Vector2(1000, 0), "colleges/alcuin_flag.png", "colleges/alcuin_ship.png");
        College anne_lister = new College(this, "Anne Lister", new Vector2(6304, 1199), "colleges/anne_lister_flag.png", "colleges/anne_lister_ship.png");
        new College(this, "Constantine", new Vector2(6240, 6703), "colleges/constantine_flag.png", "colleges/constantine_ship.png");
        new College(this, "Goodricke", new Vector2(1760, 6767), "colleges/goodricke_flag.png", "colleges/goodricke_ship.png");

        setBossCollege(alcuin);
        anne_lister.getAlliance().addAlly(player);


        float x = 0, y = 0;
        //Random ships
        //        new EnemyShip(this, alcuin.getAlliance());
        new Coin(this, x, y);

    }

    /**
     * Makes this the current screen for the parent.
     * Generates the buttons to be able to interact with what screen is being displayed.
     * Creates the escape menu and pause button
     */
    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }


    /**
     * Renders the visual data for all objects
     * Changes and renders new visual data for ships
     *
     * @param dt Delta time (elapsed time since last parent tick)
     */
    @Override
    public void render(float dt) {
        world.step(dt, 6, 2);
        b2dr.render(world, stage.getCamera().combined);
        stage.getCamera().position.set(player.getX(), player.getY(), 0);
        stage.getCamera().update();
        super.render(dt);

        Hud.stage.act();
        Hud.stage.draw();
        Inventory.stage.act();
        Inventory.stage.draw();


        // Centre camera on player boat
        //        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
        //        System.out.println("Player Position: " + player.getCenter().x + ", " + player.getCenter().y);
        //        player.body.applyForceToCenter(new Vector2(100, 100), true);

        //        gameOverCheck();
    }

    /**
     * Changes the camera size, Scales the hud to match the camera
     *
     * @param width  the width of the viewable area
     * @param height the height of the viewable area
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().viewportWidth = width / 1.5f;
        stage.getCamera().viewportHeight = height / 1.5f;
        //        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();
    }

    /**
     * Returns the map
     *
     * @return map : returns the world map
     */
    public BackgroundTiledMap getMap() {
        return map;
    }

    /**
     * Checks if the parent is over
     * i.e. goal reached (all colleges bar "Alcuin" are destroyed)
     */
    public void gameOverCheck() {
        //Lose parent if ship on 0 health or Alcuin is destroyed
        if (Hud.getHealth() <= 0 || player.getAlliance().getLeader() == null) {
            parent.setScreen(parent.DEATH);
            parent.dispose();
        }
        //Win parent if boss college destroyed
        else if (getBossCollege() == null) {
            parent.setScreen(parent.VICTORY);
            parent.dispose();
        }
    }

    /**
     * Pauses parent
     */
    @Override
    public void pause() {
        gameState = GameState.PAUSE;
    }

    /**
     * Resumes parent
     */
    @Override
    public void resume() {
        gameState = GameState.PLAY;
    }

    /**
     * Disposes parent data
     */
    @Override
    public void dispose() {
        map.dispose();
        hud.dispose();
        stage.dispose();
        b2dr.dispose();
    }

    public College getBossCollege() {
        return bossCollege;
    }

    public void setBossCollege(College bossCollege) {
        this.bossCollege = bossCollege;
    }

    public ActorTable getActorTable() {
        return actorTable;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public Stage getStage() {
        return stage;
    }

    public enum GameState {
        PLAY, PAUSE, END
    }
}
