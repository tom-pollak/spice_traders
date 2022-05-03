package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;


/**
 * Created by Jakub
 * <p>
 * Class representing items in game
 */
public class Item {
    public final Texture texture;
    public final HashMap<String, Float> buffs;
    private final String type;
    private Sprite parent;

    /**
     * Constructor for item
     *
     * @param itemType type of item
     * @param parent   parent sprite (usually entity)
     * @param img      texture of item
     */
    public Item(String itemType, Sprite parent, Texture img) {
        this.parent = parent;
        this.type = itemType;
        this.texture = img;
        this.buffs = new HashMap<>();
        // multipliers for the buffs e.g 1.5 = 50% buff
        this.buffs.put("speed", 1f);
        this.buffs.put("dmg", 1f);
    }

    /**
     * @return type of item
     */
    public String getType() {
        return this.type;
    }

    /**
     * @return parent sprite
     */
    public Sprite getParent() {
        return this.parent;
    }

    /**
     * @return sets a new parent sprite
     */
    public void setParent(Sprite newParent) {
        this.parent = newParent;
    }

    /**
     * Returns using the item
     *
     * @param x x coordinate of position to use item
     * @param y y coordinate of position to use item
     * @return true if item was used, false otherwise
     */
    public Boolean use(float x, float y) {
        return true; // FINISH THIS
    }

    /**
     * @return returns the texture of the item
     */
    public Texture getTexture() {
        return this.texture;
    }
}
