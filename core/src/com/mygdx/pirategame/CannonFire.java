package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Cannon Fire
 * Combat related cannon fire
 * Used by player and colleges,
 * Use should extend to enemy ships when implementing ship combat
 *
 *@author Ethan Alabaster
 *@version 1.0
 */
public class CannonFire extends Sprite {
    private World world;
    private Texture cannonBall;
    private float stateTime;
    private boolean destroyed;
    private boolean setToDestroy;
    private Body b2body;
    private float angle;
    private float velocity;
    private Vector2 bodyVel;
    private Sound fireNoise;

    /**
     * Instantiates cannon fire
     * Determines general cannonball data
     * Determines firing sound
     *
     * @param screen visual data
     * @param x x value of origin
     * @param y y value of origin
     * @param body body of origin
     * @param velocity velocity of the cannon ball
     */
    public CannonFire(GameScreen screen, float x, float y, Body body, float velocity) {
        this.velocity = velocity;
        this.world = screen.getWorld();
        bodyVel = body.getLinearVelocity();
        angle = body.getAngle();

        cannonBall = new Texture("cannonBall.png");
        setRegion(cannonBall);
        setBounds(x, y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
        defineCannonBall();
        fireNoise = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        if (screen.game.getPreferences().isEffectsEnabled()) {
            fireNoise.play(screen.game.getPreferences().getEffectsVolume());
        }
    }

    /**
     * Defines the existance, direction, shape and size of a cannonball
     */
    public void defineCannonBall() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PirateGame.PPM);

        fDef.filter.categoryBits = PirateGame.CANNON_BIT;
        fDef.filter.maskBits = PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT;
        fDef.shape = shape;
        fDef.isSensor = true;

        b2body.createFixture(fDef).setUserData(this);
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
    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        // determines cannonball range
        if(stateTime > 0.98f) {
            setToDestroy();
        }
    }

    /**
     * Changes destruction state
     */
    public void setToDestroy(){
        setToDestroy = true;
    }

    /**
     * Returns destruction status
     */
    public boolean isDestroyed(){
        return destroyed;
    }
}
