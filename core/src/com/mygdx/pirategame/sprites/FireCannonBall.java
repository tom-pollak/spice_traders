package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * College Fire
 * Defines college attack method
 * Defines college cannonball projectiles
 *
 * @author Ethan Alabaster
 * @version 1.0
 */

public class FireCannonBall extends Sprite {
    private final World world;
    private final Texture cannonBall;
    private float stateTime;
    private boolean destroyed;
    private boolean setToDestroy;
    private Body b2body;
    private final Vector2 playerPos;
    private Float speed = 5f;

    /**
     * Defines player position
     * Defines cannonballs
     *
     * @param screen Visual data
     * @param x      x position of player
     * @param y      y position of player
     */
    public FireCannonBall(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        playerPos = screen.getPlayerPos();
        cannonBall = new Texture("cannonBall.png");
        //Set the position and size of the ball
        setRegion(cannonBall);
        setBounds(x, y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
        defineCannonBall();
    }

    public FireCannonBall(GameScreen screen, float x, float y, Float speed, String texturePath) {
        this.world = screen.getWorld();
        playerPos = screen.getPlayerPos();
        cannonBall = new Texture(texturePath);
        this.speed = speed;

        //Set the position and size of the ball
        setRegion(cannonBall);
        setBounds(x, y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
        defineCannonBall();
    }

    /**
     * Defines cannonball data
     * Defines cannonball shape
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
        fDef.filter.categoryBits = PirateGame.COLLEGEFIRE_BIT;
        // determining what this BIT can collide with
        fDef.filter.maskBits = PirateGame.PLAYER_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);

        // Math for firing the cannonball at the player
        playerPos.sub(b2body.getPosition());
        playerPos.nor();
        b2body.setLinearVelocity(playerPos.scl(speed));
    }

    /**
     * Updates state with delta time
     * Defines range of cannon fire
     *
     * @param dt Delta time (elapsed time since last game tick)
     */
    public void update(float dt) {
        stateTime += dt;
        //If college is set to destroy and isnt, destroy it
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        // determines cannonball range
        if (stateTime > 2f) {
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
