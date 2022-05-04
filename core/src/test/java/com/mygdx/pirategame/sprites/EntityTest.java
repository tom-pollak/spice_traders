package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.graphics.Texture;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

public class EntityTest {
  /** Method under test: {@link Entity#getScaledTexture(String, Integer, Integer)} */
  @Test
  public void testGetScaledTexture() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke "com.badlogic.gdx.Files.internal(String)"
    // because "com.badlogic.gdx.Gdx.files" is null
    //       at com.mygdx.pirategame.sprites.Entity.getScaledTexture(Entity.java:46)
    //   In order to prevent getScaledTexture(String, Integer, Integer)
    //   from throwing NullPointerException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   getScaledTexture(String, Integer, Integer).
    //   See https://diff.blue/R013 to resolve this issue.

     Texture test1 = Entity.getScaledTexture("./assets/ship.png", 64, 64);
     Texture test2 = Entity.getScaledTexture("./assets/Orb_04.png", 32, 32);
     Assertions.assertEquals(test1.getWidth(), 64);
     Assertions.assertEquals(test1.getHeight(), 64);
     Assertions.assertEquals(test2.getWidth(), 32);
     Assertions.assertEquals(test2.getHeight(), 32);
  }

  /** Method under test: {@link Entity#getScaledTexture(String, Integer, Integer)} */
  @org.junit.jupiter.api.Test
  @Disabled("TODO: Complete this test")
  void testGetScaledTexture2() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke "com.badlogic.gdx.Files.internal(String)"
    // because "com.badlogic.gdx.Gdx.files" is null
    //       at com.mygdx.pirategame.sprites.Entity.getScaledTexture(Entity.java:46)
    //   In order to prevent getScaledTexture(String, Integer, Integer)
    //   from throwing NullPointerException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   getScaledTexture(String, Integer, Integer).
    //   See https://diff.blue/R013 to resolve this issue.

    Entity.getScaledTexture("Img Path", 1, 1);
  }

}
