package com.mygdx.pirategame.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pirategame.Player;

public class Item {
    private Sprite parent;
    private final String type;
    public final Texture texture;
    public Item(String itemType, Sprite parent, Texture img){
        this.parent = parent;
        this.type = itemType;
        this.texture = img;

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
