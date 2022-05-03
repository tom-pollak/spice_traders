package com.mygdx.pirategame.logic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AvailableSpawnTest {
  /** Method under test: default or parameterless constructor of {@link AvailableSpawn} */
  @Test
  public void testConstructor() {
    HashMap<Integer, ArrayList<Integer>> integerArrayListMap = (new AvailableSpawn()).tileBlocked;
    assertEquals(45, integerArrayListMap.size());
    ArrayList<Integer> getResult = integerArrayListMap.get(Double.SIZE);
    assertEquals(15, getResult.size());
    assertEquals(9, (int) getResult.get(0));
    assertEquals(10, (int) getResult.get(1));
    assertEquals(69, (int) getResult.get(13));
    assertEquals(70, (int) getResult.get(14));
    ArrayList<Integer> getResult1 = integerArrayListMap.get(69);
    assertEquals(20, getResult1.size());
    assertEquals(Short.SIZE, (int) getResult1.get(0));
    assertEquals(17, (int) getResult1.get(1));
    assertEquals(69, (int) getResult1.get(18));
    assertEquals(70, (int) getResult1.get(19));
    ArrayList<Integer> getResult2 = integerArrayListMap.get(68);
    assertEquals(20, getResult2.size());
    assertEquals(Short.SIZE, (int) getResult2.get(0));
    assertEquals(17, (int) getResult2.get(1));
    assertEquals(69, (int) getResult2.get(18));
    assertEquals(70, (int) getResult2.get(19));
    ArrayList<Integer> getResult3 = integerArrayListMap.get(67);
    assertEquals(Short.SIZE, getResult3.size());
    assertEquals(12, (int) getResult3.get(0));
    assertEquals(13, (int) getResult3.get(1));
    assertEquals(69, (int) getResult3.get(14));
    assertEquals(70, (int) getResult3.get(15));
    ArrayList<Integer> getResult4 = integerArrayListMap.get(66);
    assertEquals(12, getResult4.size());
    assertEquals(12, (int) getResult4.get(0));
    assertEquals(13, (int) getResult4.get(1));
    assertEquals(69, (int) getResult4.get(10));
    assertEquals(70, (int) getResult4.get(11));
    ArrayList<Integer> getResult5 = integerArrayListMap.get(65);
    assertEquals(15, getResult5.size());
    assertEquals(9, (int) getResult5.get(0));
    assertEquals(10, (int) getResult5.get(1));
    assertEquals(69, (int) getResult5.get(13));
    assertEquals(70, (int) getResult5.get(14));
  }
}
