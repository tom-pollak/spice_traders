package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entities.College;
import com.mygdx.pirategame.entities.EnemyShip;
import com.mygdx.pirategame.entities.Player;
import com.mygdx.pirategame.gui.Hud;
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
    public GameState gameState = GameState.PLAY;
    public float ppt;
    public Table pauseTable;
    public Table table;
    ActorTable actorTable;
    private Hud hud;
    private BackgroundTiledMap map;
    private Player player;
    private College bossCollege;
    private World world;
    private Box2DDebugRenderer b2dr;

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
     * Makes this the current screen for the parent.
     * Generates the buttons to be able to interact with what screen is being displayed.
     * Creates the escape menu and pause button
     */
    @Override
    public void show() {
        super.show();
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
        stage.addActor(player);
        stage.setKeyboardFocus(player);
        Gdx.input.setInputProcessor(new MyInputProcessor(this));


        College alcuin = new College(this, "Alcuin", new Vector2(1900, 2100), "colleges/alcuin_flag.png", "colleges/alcuin_ship.png");
        new College(this, "Anne Lister", new Vector2(6304, 1199), "colleges/anne_lister_flag.png", "colleges/anne_lister_ship.png");
        new College(this, "Constantine", new Vector2(6240, 6703), "colleges/constantine_flag.png", "colleges/constantine_ship.png");
        new College(this, "Goodricke", new Vector2(1760, 6767), "colleges/goodricke_flag.png", "colleges/goodricke_ship.png");

        setBossCollege(alcuin);

        float x = 0, y = 0;
        //Random ships
        new EnemyShip(this, alcuin.getAlliance());
        new Coin(this, x, y);

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
        if (gameState == GameState.PAUSE) {
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
     * Renders the visual data for all objects
     * Changes and renders new visual data for ships
     *
     * @param dt Delta time (elapsed time since last parent tick)
     */
    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(46 / 255f, 204 / 255f, 113 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Hud.stage.act();
        Hud.stage.draw();
        stage.act();
        stage.draw();

        // Centre camera on player boat
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();

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
        stage.getViewport().update(width, height);
        stage.getCamera().viewportWidth = width / 1.5f;
        stage.getCamera().viewportHeight = height / 1.5f;
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
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
        parent.pause();
    }

    /**
     * Resumes parent
     */
    @Override
    public void resume() {
        gameState = GameState.PLAY;
        parent.resume();
    }

    /**
     * Disposes parent data
     */
    @Override
    public void dispose() {
        map.dispose();
        hud.dispose();
        stage.dispose();
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
