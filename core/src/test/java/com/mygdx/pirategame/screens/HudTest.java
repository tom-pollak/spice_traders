package com.mygdx.pirategame.screens;

import com.mygdx.pirategame.PirateGame;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

public class HudTest {
  /** Method under test: {@link Hud#resize(int, int)} */
  @Test
  @Ignore
  @Disabled("TODO: Complete this test")
  public void testResize() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke
    // "com.badlogic.gdx.scenes.scene2d.Stage.getViewport()" because
    // "com.mygdx.pirategame.screens.Hud.stage" is null
    //       at com.mygdx.pirategame.screens.Hud.resize(Hud.java:109)
    //   In order to prevent resize(int, int)
    //   from throwing NullPointerException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   resize(int, int).
    //   See https://diff.blue/R013 to resolve this issue.

    Hud.resize(1, 1);
  }

  /** Method under test: {@link Hud#changeHealth(int)} */
  @Test
  @Ignore
  @Disabled("TODO: Complete this test")
  public void testChangeHealth() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because
    // "com.mygdx.pirategame.screens.Hud.health" is null
    //       at com.mygdx.pirategame.screens.Hud.changeHealth(Hud.java:118)
    //   In order to prevent changeHealth(int)
    //   from throwing NullPointerException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   changeHealth(int).
    //   See https://diff.blue/R013 to resolve this issue.

    Hud.changeHealth(42);
  }

  /** Method under test: {@link Hud#changeCoins(int)} */
  @Test
  @Ignore
  @Disabled("TODO: Complete this test")
  public void testChangeCoins() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because
    // "com.mygdx.pirategame.screens.Hud.coins" is null
    //       at com.mygdx.pirategame.screens.Hud.changeCoins(Hud.java:129)
    //   In order to prevent changeCoins(int)
    //   from throwing NullPointerException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   changeCoins(int).
    //   See https://diff.blue/R013 to resolve this issue.

    Hud.changeCoins(42);
  }

  /** Method under test: {@link Hud#changeCoins(int)} */
  @Test
  @Ignore
  public void testChangeCoins2() {
    // TODO: Complete this test.
    //   Reason: R004 No meaningful assertions found.
    //   Diffblue Cover was unable to create an assertion.
    //   Make sure that fields modified by changeCoins(int)
    //   have package-private, protected, or public getters.
    //   See https://diff.blue/R004 to resolve this issue.

    Hud.changeCoins(0);
  }

  /** Method under test: {@link Hud#changePoints(int)} */
  @Test
  @Ignore
  @Disabled("TODO: Complete this test")
  public void testChangePoints() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because
    // "com.mygdx.pirategame.screens.Hud.score" is null
    //       at com.mygdx.pirategame.screens.Hud.changePoints(Hud.java:140)
    //   In order to prevent changePoints(int)
    //   from throwing NullPointerException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   changePoints(int).
    //   See https://diff.blue/R013 to resolve this issue.

    Hud.changePoints(42);
  }

  /** Method under test: {@link Hud#changeCoinsMulti(int)} */
  @Test
  @Ignore
  @Disabled("TODO: Complete this test")
  public void testChangeCoinsMulti() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because
    // "com.mygdx.pirategame.screens.Hud.coinMulti" is null
    //       at com.mygdx.pirategame.screens.Hud.changeCoinsMulti(Hud.java:152)
    //   In order to prevent changeCoinsMulti(int)
    //   from throwing NullPointerException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   changeCoinsMulti(int).
    //   See https://diff.blue/R013 to resolve this issue.

    Hud.changeCoinsMulti(42);
  }

  /** Method under test: {@link Hud#setHealth(int)} */
  @Ignore
  @Test
  public void testSetHealth() {
    // TODO: Complete this test.
    //   Reason: R004 No meaningful assertions found.
    //   Diffblue Cover was unable to create an assertion.
    //   Make sure that fields modified by setHealth(int)
    //   have package-private, protected, or public getters.
    //   See https://diff.blue/R004 to resolve this issue.

    Hud.setHealth(1);
  }

  @Test
  public void testExperienceIncreaseOverTime() {
    PirateGame pirateGame = new PirateGame();
    Hud hud = new Hud(pirateGame.batch);
    int initialCoinAmount = hud.getScore();
    hud.update(1000);
    Assertions.assertNotEquals((int) hud.getScore(), initialCoinAmount);
  }
}
