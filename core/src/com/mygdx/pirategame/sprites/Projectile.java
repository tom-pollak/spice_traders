package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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

public class Projectile extends Entity {
    private boolean destroyed;
    private boolean setToDestroy;
    protected final Vector2 target;
    private Float speed = 5f;

    /**
     * Defines player position
     * Defines cannonballs
     *
     * @param screen      Visual data
     * @param x           x position of player
     * @param y           y position of player
     * @param texturePath
     * @param target
     */
    public Projectile(GameScreen screen, float x, float y, String texturePath, Vector2 target) {
        super(screen, x, y);
        this.target = target;
        System.out.println("target: " + target);
        //Set the position and size of the ball
        setRegion(new Texture(texturePath));
        setBounds(x, y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
        if (!(this instanceof PlayerFire)) defineBody();
    }

    public Projectile(GameScreen screen, float x, float y, Float velocity, String texturePath, Vector2 target) {
        this(screen, x, y, texturePath, target);
        this.speed = velocity;

    }

    /**
     * Defines cannonball data
     * Defines cannonball shape
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
        fDef.filter.categoryBits = PirateGame.COLLEGEFIRE_BIT;
        // determining what this BIT can collide with
        fDef.filter.maskBits = PirateGame.PLAYER_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);

        // Math for firing the cannonball at the player
        target.sub(b2body.getPosition());
        target.nor();
        b2body.setLinearVelocity(target.scl(speed));
    }

    @Override
    public void onContact() {


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
