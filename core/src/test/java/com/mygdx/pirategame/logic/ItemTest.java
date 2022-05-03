package com.mygdx.pirategame.logic;

import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.Test;

public class ItemTest {
  /** Method under test: default or parameterless constructor of {@link Item} */
  @Test
  public void testConstructor() {
    Sprite sprite = new Sprite();
    Item actualItem = new Item("Item Type", sprite, null);

    assertSame(sprite, actualItem.getParent());
    assertEquals(2, actualItem.buffs.size());
    assertNull(actualItem.getTexture());
    assertEquals("Item Type", actualItem.getType());
  }

  /** Method under test: {@link Item#use(float, float)} */
  @Test
  public void testUse() {
    assertTrue((new Item("Item Type", new Sprite(), null)).use(10.0f, 10.0f));
  }
}
