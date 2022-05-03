package com.mygdx.pirategame.sprites;

import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerTest {
  @Test
  public void testHandleInput() throws AWTException {
    PirateGame PirateGame = new PirateGame();
    GameScreen gameScreen = new GameScreen(PirateGame);
    int originalX = 0;
    Player player = new Player(gameScreen, originalX, 0, GameScreen.player.college);
    Robot r = new Robot();
    r.keyPress(KeyEvent.VK_A);
    player.update(1000);
    r.keyRelease(KeyEvent.VK_A);
    Assertions.assertTrue(player.b2body.getPosition().x != originalX);
  }
}
