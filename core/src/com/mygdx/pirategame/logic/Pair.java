package com.mygdx.pirategame.logic;

/**
 * Created by tom
 * Used as a return type when returning a pair of objects
 *
 * @param <A>
 * @param <B>
 */
public class Pair<A, B> {
    public A fst;
    public B snd;

    /**
     * Constructor, takes two objects
     *
     * @param fst first object
     * @param snd second object
     */
    public Pair(A fst, B snd) {
        super();
        this.fst = fst;
        this.snd = snd;
    }

    /**
     * Returns the generated hashcode of the pair
     *
     * @return hashcode
     */
    public int hashCode() {
        int hashFirst = fst != null ? fst.hashCode() : 0;
        int hashSecond = snd != null ? snd.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    /**
     * Compares two pairs
     *
     * @param other pair to compare to
     * @return true if the pairs are equal and false if they are not
     */
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return ((this.fst == otherPair.fst || (this.fst != null && otherPair.fst != null && this.fst.equals(otherPair.fst))) && (this.snd == otherPair.snd || (this.snd != null && otherPair.snd != null && this.snd.equals(otherPair.snd))));
        }

        return false;
    }

    /**
     * Returns the string representation of the pair
     *
     * @return string representation
     */
    public String toString() {
        return "(" + fst + ", " + snd + ")";
    }
}
