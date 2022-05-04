package com.mygdx.pirategame.sprites;

import static com.mygdx.pirategame.screens.GameScreen.*;

import com.mygdx.pirategame.PirateGame;
import java.awt.*;
import java.awt.event.KeyEvent;
import org.junit.Test;
import org.junit.jupiter.api.*;

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
}
