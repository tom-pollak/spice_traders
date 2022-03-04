package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class AbstractActor extends Actor {
    protected final Screen screen;
    protected Texture texture;

    public AbstractActor(Screen screen, String texturePath) {
        this.screen = screen;
        this.texture = new Texture(texturePath);
    }

    public AbstractActor(Screen screen) {
        this.screen = screen;
    }

    public static Texture getScaledTexture(String imgPath, int width, int height) {
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(imgPath));
        Pixmap pixmapNew = new Pixmap(width, height, pixmapOriginal.getFormat());
        pixmapNew.drawPixmap(pixmapOriginal, 0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), 0, 0, pixmapNew.getWidth(), pixmapNew.getHeight());
        Texture texture = new Texture(pixmapNew);
        pixmapOriginal.dispose();
        pixmapNew.dispose();
        return texture;
    }

    public abstract void entityContact();

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.setWidth(texture.getWidth());
        this.setHeight(texture.getHeight());
        this.setBounds(getX(), getY(), getWidth(), getHeight());
        this.setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);

    }

    @Override
    public void setX(float x) {
        super.setX(x);
        this.setOriginX(getX() + getWidth() / 2);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        this.setOriginY(getY() + getHeight() / 2);
    }

    public Polygon getHitbox() {
        Polygon hitbox = new Polygon();
        hitbox.setVertices(new float[]{0, 0, getWidth(), 0, 0, getHeight(), getWidth(), getHeight()});
        hitbox.setPosition(getX(), getY());
        return hitbox;
    }

    public void dispose() {
        screen.dispose();
        texture.dispose();
    }

}
