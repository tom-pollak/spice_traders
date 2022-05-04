package com.mygdx.pirategame.sprites;

import com.mygdx.pirategame.PirateGame;
import org.junit.Test;
import org.junit.jupiter.api.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.mygdx.pirategame.screens.GameScreen.*;

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
    Assertions.assertTrue(
        pirateGame.getGameScreen().getPlayer().b2body.getPosition().x != originalX);
  }

  @Test
  public void testHandleInputTurning() throws AWTException {
    PirateGame pirateGame = new PirateGame();
    pirateGame.changeScreen(pirateGame.GAME);
    float originalAngle = pirateGame.getGameScreen().getPlayer().b2body.getAngle();
    r = new Robot();
    r.keyPress(KeyEvent.VK_D);
    pirateGame.getGameScreen().getPlayer().update(1000);
    r.keyRelease(KeyEvent.VK_D);
    Assertions.assertTrue(
        pirateGame.getGameScreen().getPlayer().b2body.getAngle() != originalAngle);
  }

  @Test
  public void testCoinTouch() {
    PirateGame pirateGame = new PirateGame();
    pirateGame.changeScreen(pirateGame.GAME);
    pirateGame.getGameScreen().addCoin(100, 100);
    int sizeBeforeTouch = pirateGame.getGameScreen().Coins.size();
    pirateGame.getGameScreen().getPlayer().setPosition(100, 100);
    pirateGame.getGameScreen().getPlayer().update(50);
    Assertions.assertFalse(pirateGame.getGameScreen().Coins.size() == sizeBeforeTouch);
  }
}
