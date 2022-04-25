package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

public class Item {
    private Sprite parent;
    private final String type;
    public final Texture texture;
    public final HashMap<String, Float> buffs;
    public Item(String itemType, Sprite parent, Texture img){
        this.parent = parent;
        this.type = itemType;
        this.texture = img;
        this.buffs = new HashMap<>();
        //multipliers for the buffs e.g 1.5 = 50% buff
        this.buffs.put("speed", 1f);
        this.buffs.put("dmg", 1f);
    }

    public String getType(){return this.type;}

    public Sprite getParent(){return this.parent;}

    public void setParent(Sprite newParent){this.parent = newParent;}

    public Boolean use(float x, float y){
        return true;//FINISH THIS
    }

    public Texture getTexture() {
        return this.texture;
    }
}
