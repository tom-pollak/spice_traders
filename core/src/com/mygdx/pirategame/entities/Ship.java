package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.gui.Hud;
import com.mygdx.pirategame.logic.ActorTable;
import com.mygdx.pirategame.logic.Pair;
import com.mygdx.pirategame.screens.GameScreen;

public class Ship extends AbstractEntity {
    private static final Sound destroySound = Gdx.audio.newSound(Gdx.files.internal("ship-explosion-2.wav"));
    private static final Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("ship-hit.wav"));
    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();


    public Ship(GameScreen screen, ActorTable actorTable) {
        super(screen, actorTable, "unaligned_ship.png");
        actorTable.addActor(this);
        setHealth(100);
    }

    public Ship(GameScreen screen, ActorTable actorTable, float x, float y) {
        super(screen, actorTable, "unaligned_ship.png");
        actorTable.addActor(this);
        setPosition(x, y);
        setHealth(100);
    }


    @Override
    public void collide(AbstractActor collidingActor) {
        Gdx.app.log("enemy", "collision");
        //Play collision sound
        if (screen.parent.getPreferences().isEffectsEnabled()) {
            hitSound.play(screen.parent.getPreferences().getEffectsVolume());
        }
        health -= getCollisionDamage(collidingActor);
        healthBar.setHealth(health);
        Hud.changePoints(5);
    }

    public Integer getCollisionDamage(AbstractActor collidingActor) {
        // TODO return absolute velocity of both actors * multiplier (can be changed by upgrades)
        return 0;
    }

    @Override
    public void act(float delta) {
        // TODO
        // Old code
        super.act(delta);
        float oldX = getX(), oldY = getY();

        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);


        Pair<Boolean, Boolean> collisions = screen.getMap().getMapCollisions(this, oldX, oldY);
        boolean collisionX = collisions.fst;
        boolean collisionY = collisions.snd;

        if (collisionX) {
            setX(oldX);
        }
        if (collisionY) {
            setY(oldY);
        }
        this.setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);


        // New code
        float angle = (float) Math.atan2(velocity.y, velocity.x) * MathUtils.radiansToDegrees;
        setRotation(angle - 90);
        //        b2body.setTransform(b2body.getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
        //        setRotation((float) (b2body.getAngle() * 180 / Math.PI));
        //Update health bar
        healthBar.update();
        if (health <= 0) die();
    }
}
