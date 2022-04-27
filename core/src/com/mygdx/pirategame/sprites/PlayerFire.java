package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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
public class PlayerFire extends Sprite {
    private final World world;
    private final Texture cannonBall;
    private float stateTime;
    private boolean destroyed;
    private boolean setToDestroy;
    private Body b2body;
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
     * @param velocity velocity of the cannon ball
     */
    public PlayerFire(GameScreen screen, Body body, float velocity) {
        this.velocity = velocity;
        this.world = screen.getWorld();
        //sets the angle and velocity
        bodyVel = body.getLinearVelocity();
        angle = body.getAngle();
        this.b2body = body;

        //set cannonBall dimensions for the texture
        cannonBall = new Texture("cannonBall.png");
        setRegion(cannonBall);
        setBounds(b2body.getPosition().x, b2body.getPosition().y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
        //set collision bounds
        defineCannonBall();
        //set sound for fire and play if on
        fireNoise = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        if (GameScreen.game.getPreferences().isEffectsEnabled()) {
            fireNoise.play(GameScreen.game.getPreferences().getEffectsVolume());
        }
    }

    /**
     * Defines the existance, direction, shape and size of a cannonball
     */
    public void defineCannonBall() {
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

    /**
     * Updates state with delta time
     * Defines range of cannon fire
     *
     * @param dt Delta time (elapsed time since last game tick)
     */
    public void update(float dt) {
        stateTime += dt;
        //Update position of ball
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //If ball is set to destroy and isnt, destroy it
        if ((setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        // determines cannonball range
        if (stateTime > 0.98f) {
            setToDestroy();
        }
    }

    /**
     * Changes destruction state
     */
    public void setToDestroy() {
        setToDestroy = true;
    }

    /**
     * Returns destruction status
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
