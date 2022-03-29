package com.mygdx.pirategame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.mygdx.pirategame.items.Key;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.screens.GameScreen;

public class TreasureChest extends AbstractEntity {
    private final Alliance keyAlliance;
    private final String description;
    private boolean opened = false;

    public TreasureChest(GameScreen screen, String description) {
        super(screen, "img/treasure-chest.png");
        this.description = description;
        this.keyAlliance = new Alliance(toString(), this, new Texture("img/key.png"));
        setTexture(texture);
        //        this.setPosition(getX(), getY());
        //        this.setOrigin(getX() + getWidth() / 2, getX() + getHeight() / 2);
    }

    @Override
    public void setTexture(Texture texture) {
        texture = getScaledTexture(((FileTextureData) texture.getTextureData()).getFileHandle().path(), 50, 50);
        super.setTexture(texture);
    }


    /**
     * If key is allied with the chest, the chest is opened.
     *
     * @param key the key to check
     * @return true if the chest is opened, false otherwise
     */
    public boolean open(Key key) {
        if (opened) {
            System.out.println("Chest already opened");
            return true;
        }
        if (keyAlliance.isAlly(key)) {
            System.out.println("You have opened the chest");
            setTexture(new Texture("img/treasure-chest-open.png"));
            dropAll();
            opened = true;
            return true;
        }
        System.out.println("You need the correct key to open the chest");
        return false;
    }

    public void addKey(Key key) {
        key.setAlliance(keyAlliance);
    }

    public Key generateKey() {
        Key key = new Key(screen, "Key", description);
        addKey(key);
        return key;
    }

    @Override
    public String toString() {
        return "Treasure Chest: " + description;
    }
}
