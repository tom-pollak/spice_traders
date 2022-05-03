package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.Ignore;
import org.junit.Test;

public class SpeedOrbTest {
  /** Method under test: default or parameterless constructor of {@link SpeedOrb} */
  @Test
  @Ignore("TODO: Complete this test")
  public void testConstructor() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke "com.badlogic.gdx.Files.internal(String)"
    // because "com.badlogic.gdx.Gdx.files" is null
    //       at com.badlogic.gdx.graphics.Texture.<init>(Texture.java:110)
    //       at com.mygdx.pirategame.logic.SpeedOrb.<init>(SpeedOrb.java:9)
    //   In order to prevent <init>(Sprite)
    //   from throwing NullPointerException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   <init>(Sprite).
    //   See https://diff.blue/R013 to resolve this issue.

    new SpeedOrb(new Sprite());
  }
}
