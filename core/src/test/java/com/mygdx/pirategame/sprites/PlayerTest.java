package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.logic.AvailableSpawn;
import com.mygdx.pirategame.screens.GameScreen;
import org.junit.Test;
import org.junit.jupiter.api.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.mygdx.pirategame.screens.GameScreen.*;
import static com.mygdx.pirategame.screens.GameScreen.player;

public class PlayerTest {
  private Robot r;
  private int originalX;
  @Test
  public void testHandleInput() throws AWTException {
    PirateGame pirateGame = new PirateGame();
    pirateGame.changeScreen(pirateGame.GAME);
    r = new Robot();
    r.keyPress(KeyEvent.VK_W);
    pirateGame.getGameScreen().getPlayer().update(1000);
    r.keyRelease(KeyEvent.VK_W);
    Assertions.assertTrue(pirateGame.getGameScreen().getPlayer().b2body.getPosition().x != originalX);
  }

}
