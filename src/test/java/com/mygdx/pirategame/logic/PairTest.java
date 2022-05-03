package com.mygdx.pirategame.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class PairTest {
    /**
     * Method under test: default or parameterless constructor of {@link Pair}
     */
    @Test
    public void testConstructor() {
        Pair<Object, Object> actualPair = new Pair<>("Fst", "Snd");

        assertEquals("Fst", actualPair.fst);
        assertEquals("Snd", actualPair.snd);
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals() {
        assertNotEquals(new Pair<>("Fst", "Snd"), null);
        assertNotEquals(new Pair<>("Fst", "Snd"), "Different type to Pair");
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals2() {
        Pair<Object, Object> pair = new Pair<>("Fst", "Snd");
        assertEquals(pair, pair);
        int expectedHashCodeResult = pair.hashCode();
        assertEquals(expectedHashCodeResult, pair.hashCode());
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals3() {
        Pair<Object, Object> pair = new Pair<>("Fst", "Snd");
        Pair<Object, Object> pair1 = new Pair<>("Fst", "Snd");

        assertEquals(pair, pair1);
        int expectedHashCodeResult = pair.hashCode();
        assertEquals(expectedHashCodeResult, pair1.hashCode());
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals4() {
        Pair<Object, Object> pair = new Pair<>(1, "Snd");
        assertNotEquals(pair, new Pair<>("Fst", "Snd"));
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals5() {
        Pair<Object, Object> pair = new Pair<>(new Pair<>("Fst", "Snd"), "Snd");
        assertNotEquals(pair, new Pair<>("Fst", "Snd"));
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals6() {
        Pair<Object, Object> pair = new Pair<>(null, "Snd");
        assertNotEquals(pair, new Pair<>("Fst", "Snd"));
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals7() {
        Pair<Object, Object> pair = new Pair<>("Fst", 1);
        assertNotEquals(pair, new Pair<>("Fst", "Snd"));
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals8() {
        Pair<Object, Object> pair = new Pair<>("Fst", new Pair<>("Fst", "Snd"));
        assertNotEquals(pair, new Pair<>("Fst", "Snd"));
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals9() {
        Pair<Object, Object> pair = new Pair<>("Fst", null);
        assertNotEquals(pair, new Pair<>("Fst", "Snd"));
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals10() {
        Pair<Object, Object> pair = new Pair<>("Fst", "Snd");
        assertNotEquals(pair, new Pair<>(null, "Snd"));
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals11() {
        Pair<Object, Object> pair = new Pair<>("Fst", "Snd");
        assertNotEquals(pair, new Pair<>("Fst", null));
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals12() {
        Pair<Object, Object> pair = new Pair<>(new Pair<>("Fst", "Snd"), "Snd");
        Pair<Object, Object> pair1 = new Pair<>(new Pair<>("Fst", "Snd"), "Snd");

        assertEquals(pair, pair1);
        int expectedHashCodeResult = pair.hashCode();
        assertEquals(expectedHashCodeResult, pair1.hashCode());
    }

    /**
     * Method under test: {@link Pair#equals(Object)}
     */
    @Test
    public void testEquals13() {
        Pair<Object, Object> pair = new Pair<>("Fst", new Pair<>("Fst", "Snd"));
        Pair<Object, Object> pair1 = new Pair<>("Fst", new Pair<>("Fst", "Snd"));

        assertEquals(pair, pair1);
        int expectedHashCodeResult = pair.hashCode();
        assertEquals(expectedHashCodeResult, pair1.hashCode());
    }
}

