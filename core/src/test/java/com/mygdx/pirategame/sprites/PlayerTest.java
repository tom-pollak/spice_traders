package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.logic.AvailableSpawn;
import com.mygdx.pirategame.screens.GameScreen;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.mygdx.pirategame.screens.GameScreen.*;
import static com.mygdx.pirategame.screens.GameScreen.player;

public class PlayerTest {
  Player player;
  Robot r;
  int originalX;

  @BeforeEach
  void setup() throws AWTException {
    PirateGame pirateGame = new PirateGame();
    GameScreen gameScreen = new GameScreen(pirateGame);
    originalX = 0;
    player = new Player(gameScreen, originalX, 0, new College(
            gameScreen,
            "Goodricke",
            1760 / PirateGame.PPM,
            6767 / PirateGame.PPM,
            "goodricke_flag.png",
            "goodricke_ship.png",
            1,
            new AvailableSpawn()));
    r = new Robot();
  }
  @Test
  void testHandleInput() {
    r.keyPress(KeyEvent.VK_A);
    player.update(1000);
    r.keyRelease(KeyEvent.VK_A);
    Assertions.assertTrue(player.b2body.getPosition().x != originalX);
  }

}
