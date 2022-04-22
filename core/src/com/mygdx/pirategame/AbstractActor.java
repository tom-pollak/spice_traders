package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.pirategame.logic.ActorTable;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.logic.Pair;
import com.mygdx.pirategame.logic.Upgrades;
import com.mygdx.pirategame.screens.GameScreen;

public abstract class AbstractActor extends Actor {
    protected final GameScreen screen;
    protected final ActorTable actorTable;
    public Body body;
    protected World world;
    protected Texture texture;
    protected Alliance alliance = Alliance.NEUTRAL;
    protected Upgrades upgrades = new Upgrades(this);

    public AbstractActor(GameScreen screen, String texturePath) {
        this(screen);
        setTexture(new Texture(texturePath));
    }

    public AbstractActor(GameScreen screen) {
        this.screen = screen;
        this.actorTable = screen.getActorTable();
        //        this.actorTable.addActor(this);
        screen.getStage().addActor(this);
        System.out.println(screen.getStage().getActors());
        this.world = screen.getWorld();
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

    public abstract void createBody();

    public abstract void collide(AbstractActor other);

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.setWidth(texture.getWidth());
        this.setHeight(texture.getHeight());
        this.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Vector2 getCenter() {
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public void setCenter(Vector2 center) {
        setPosition(center.x - getWidth() / 2, center.y - getHeight() / 2);
    }

    public void setCenter(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        //        this.setCenter(getX() + getWidth() / 2, getY() + getHeight() / 2);

    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    public Polygon getHitbox() {
        Polygon hitbox = new Polygon();
        hitbox.setVertices(new float[]{0, 0, getWidth(), 0, 0, getHeight(), getWidth(), getHeight()});
        hitbox.setPosition(getX(), getY());
        return hitbox;
    }

    public PolygonShape getShape() {
        PolygonShape hitbox = new PolygonShape();
        hitbox.set(new float[]{0, 0, getWidth(), 0, 0, getHeight(), getWidth(), getHeight()});
        return hitbox;
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //        Vector2 localCoords = screenToLocalCoordinates(getCenter());
        Vector2 localCoords = getCenter();
        //        System.out.println("Drawing " + this + "at " + localCoords);
        batch.draw(texture, localCoords.x, localCoords.y);
        super.draw(batch, parentAlpha);
    }

    public void die() {
        remove();
        dispose();
        Gdx.app.log(this.toString(), "died");
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public void setAlliance(Alliance newAlliance) {
        alliance.removeAlly(this);

        this.alliance = newAlliance;
        newAlliance.addAlly(this);
    }

    public Pair<Integer, Integer> findEmptyTile(float startX, float startY) {
        for (int x = (int) ((int) startX - 2 * getWidth()); x <= startX + 2 * getWidth(); x++) {
            for (int y = (int) ((int) startY - 2 * getHeight()); y <= startY + 2 * getHeight(); y++) {
                if (screen.getActorTable().willCollide(this, x, y) && !screen.getMap().isTileBlocked(x, y)) {
                    return new Pair<>(x, y);
                }
            }
        }
        return null;
    }
}
