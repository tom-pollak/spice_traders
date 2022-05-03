package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.pirategame.PirateGame;

/**
 * Main menu is the first screen the player sees. Allows them to navigate where they want to go to
 *
 * @author Sam Pearson
 * @version 1.0
 */
public class MainMenu implements Screen {

  private final PirateGame parent;
  private final Stage stage;
  private int difficultyLevel;

  /**
   * Instantiates a new Main menu.
   *
   * @param PirateGame the main starting body of the game. Where screen swapping is carried out.
   */
  public MainMenu(PirateGame PirateGame) {
    parent = PirateGame;
    stage = new Stage(new ScreenViewport());
    difficultyLevel = 1;
  }

  /** What should be displayed on the options screen */
  @Override
  public void show() {
    // Set the input processor
    Gdx.input.setInputProcessor(stage);
    // Create a table for the buttons
    Table table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    // The skin for the actors
    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    // create buttons
    TextButton newGame = new TextButton("New Game", skin);
    TextButton help = new TextButton("Help", skin);
    TextButton options = new TextButton("Options", skin);
    TextButton exit = new TextButton("Exit", skin);
    TextButton load = new TextButton("Load", skin);
    TextButton difficulty = new TextButton("Normal Difficulty", skin);

    // add buttons to table
    table.add(newGame).fillX().uniformX();
    table.row();
    table.add(load).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);
    table.add(difficulty).fillX().uniformX();
    table.row();
    table.add(help).fillX().uniformX();
    table.row();
    table.add(options).fillX().uniformX();
    table.row();
    table.add(exit).fillX().uniformX();

    // add listeners to the buttons

    // Start a game
    newGame.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            parent.changeScreen(PirateGame.GAME);
            GameScreen.difficulty = difficultyLevel;
          }
        });
    // Help Screen
    help.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            parent.changeScreen(PirateGame.HELP);
          }
        });

    // Go to edit options
    options.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            parent.setScreen(new Options(parent, parent.getScreen()));
          }
        });

    load.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            parent.changeScreen(PirateGame.GAME);
            GameScreen.difficulty = difficultyLevel;
            parent.load("save.json");
          }
        });

    // Quit game
    exit.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            Gdx.app.exit();
          }
        });

    difficulty.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            difficultyLevel++;
            if (difficultyLevel > 3) {
              difficultyLevel = 1;
            }
            if (difficultyLevel == 1) {
              difficulty.setText("Easy Difficulty");
            } else if (difficultyLevel == 2) {
              difficulty.setText("Normal Difficulty");
            } else if (difficultyLevel == 3) {
              difficulty.setText("Hard Difficulty");
            }
            stage.draw();
          }
        });
  }

  /**
   * Renders the visual data for all objects
   *
   * @param delta Delta Time
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0f, 0f, 0f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
  }

  /**
   * Changes the camera size, Scales the hud to match the camera
   *
   * @param width the width of the viewable area
   * @param height the height of the viewable area
   */
  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  /** (Not Used) Pauses game */
  @Override
  public void pause() {}

  /** (Not Used) Resumes game */
  @Override
  public void resume() {}

  /** (Not Used) Hides game */
  @Override
  public void hide() {}

  /** Disposes game data */
  @Override
  public void dispose() {
    stage.dispose();
  }
}
