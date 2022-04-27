package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * Cannon Fire
 * Combat related cannon fire
 * Used by player and colleges,
 * Use should extend to enemy ships when implementing ship combat
 *
 * @author Ethan Alabaster
 * @version 1.0
 */
public class PlayerFire extends Projectile {
    private final float angle;
    private final float velocity;
    private final Vector2 bodyVel;
    private final Sound fireNoise;

    /**
     * Instantiates cannon fire
     * Determines general cannonball data
     * Determines firing sound
     *
     * @param screen   visual data
     * @param body     body of origin
     * @param velocity velocity of the cannonball
     */
    public PlayerFire(GameScreen screen, Body body, float velocity) {
        super(screen, body.getPosition().x, body.getPosition().y, "cannonBall.png", null);
        this.velocity = velocity;
        //sets the angle and velocity
        bodyVel = body.getLinearVelocity();
        angle = body.getAngle();
        defineBody();

        fireNoise = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        if (GameScreen.game.getPreferences().isEffectsEnabled()) {
            fireNoise.play(GameScreen.game.getPreferences().getEffectsVolume());
        }
    }

    /**
     * Defines the existence, direction, shape and size of a cannonball
     */
    @Override
    public void defineBody() {
        //sets the body definitions
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        //Sets collision boundaries
        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PirateGame.PPM);

        // setting BIT identifier
        fDef.filter.categoryBits = PirateGame.CANNON_BIT;
        // determining what this BIT can collide with
        fDef.filter.maskBits = PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT;
        fDef.shape = shape;
        fDef.isSensor = true;
        b2body.createFixture(fDef).setUserData(this);

        //Velocity maths
        float velX = MathUtils.cos(angle) * velocity + bodyVel.x;
        float velY = MathUtils.sin(angle) * velocity + bodyVel.y;

        b2body.applyLinearImpulse(new Vector2(velX, velY), b2body.getWorldCenter(), true);
    }
}