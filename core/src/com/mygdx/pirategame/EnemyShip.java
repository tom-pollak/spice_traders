package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.logic.Pathfinding;

import static com.mygdx.pirategame.GameScreen.accel;
import static com.mygdx.pirategame.GameScreen.maxSpeed;

/**
 * Enemy Ship
 * Generates enemy ship data
 * Instantiates an enemy ship
 *
 * @author Ethan Alabaster, Sam Pearson, Edward Poulter
 * @version 1.0
 */
public class EnemyShip extends Enemy {
    private Texture enemyShip;
    public String college;
    private Sound destroy;
    private Sound hit;
    private final Pathfinding pathfinding = new Pathfinding();
    private Array<FireCannonBall> cannonBalls;
    private float stateTime = 0;

    /**
     * Instantiates enemy ship
     *
     * @param screen     Visual data
     * @param x          x coordinates of entity
     * @param y          y coordinates of entity
     * @param path       path of texture file
     * @param assignment College ship is assigned to
     */
    public EnemyShip(GameScreen screen, float x, float y, String path, String assignment) {
        super(screen, x, y);
        enemyShip = new Texture(path);
        //Assign college
        college = assignment;
        cannonBalls = new Array<>();
        //Set audios
        destroy = Gdx.audio.newSound(Gdx.files.internal("ship-explosion-2.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("ship-hit.wav"));
        //Set the position and size of the college
        setBounds(0, 0, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(enemyShip);
        setOrigin(32 / PirateGame.PPM, 55 / PirateGame.PPM);

        damage = 20;
    }

    /**
     * Updates the state of each object with delta time
     * Checks for ship destruction
     *
     * @param dt Delta time (elapsed time since last game tick)
     */
    public void update(float dt) {
        stateTime += dt;
        System.out.println("State time: " + stateTime);
        //        Vector2 cur_coord = screen.backgroundTiledMap.getTileCoords(getX(), getY());
        Vector2 cur_coord = new Vector2(getX(), getY());
        //        Vector2 player_coord = screen.backgroundTiledMap.getTileCoords(screen.player.getX(), screen.player.getY());
        Vector2 player_coord = new Vector2(screen.player.getX(), screen.player.getY());
        //        List<GridCell> searchPath = pathfinding.findPath((int) cur_coord.x, (int) cur_coord.y, (int) player_coord.x, (int) player_coord.y);
        // Navigate towards first tile in search path
        try {
            //                GridCell firstNode = searchPath.get(0);
            //            Vector2 target = screen.backgroundTiledMap.getTileCoords(firstNode.x, firstNode.y);
            Vector2 target = new Vector2(player_coord.x - cur_coord.x, player_coord.y - cur_coord.y);
            //            System.out.println("Target: " + target);
            target.limit2(1).scl(0.05f);
            //            System.out.println("Target: " + target);
            // Checking if player at max velocity, and keeping them below max
            if (b2body.getLinearVelocity().x >= maxSpeed) {
                b2body.applyLinearImpulse(new Vector2(-accel, 0), b2body.getWorldCenter(), true);
            }
            if (b2body.getLinearVelocity().x <= -maxSpeed) {
                b2body.applyLinearImpulse(new Vector2(accel, 0), b2body.getWorldCenter(), true);
            }
            if (b2body.getLinearVelocity().y >= maxSpeed) {
                b2body.applyLinearImpulse(new Vector2(0, -accel), b2body.getWorldCenter(), true);
            }
            if (b2body.getLinearVelocity().y <= -maxSpeed) {
                b2body.applyLinearImpulse(new Vector2(0, accel), b2body.getWorldCenter(), true);
            }
            b2body.applyLinearImpulse(target, b2body.getWorldCenter(), true); // Player uses linear impulse
        } catch (IndexOutOfBoundsException ignored) {
        }

        //If ship is set to destroy and isnt, destroy it
        if (setToDestroy && !destroyed) {
            //Play death noise
            if (GameScreen.game.getPreferences().isEffectsEnabled()) {
                destroy.play(GameScreen.game.getPreferences().getEffectsVolume());
            }
            world.destroyBody(b2body);
            destroyed = true;
            //Change player coins and points
            Hud.changePoints(20);
            Hud.changeCoins(10);
        } else if (!destroyed) {
            //Update position and angle of ship
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
            float angle = (float) Math.atan2(b2body.getLinearVelocity().y, b2body.getLinearVelocity().x);
            b2body.setTransform(b2body.getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
            setRotation((float) (b2body.getAngle() * 180 / Math.PI));
            //Update health bar
            bar.update();
            if (stateTime > 1) {
                cannonBalls.add(new FireCannonBall(screen, b2body.getPosition().x, b2body.getPosition().y));
                stateTime = 0;
            }

            //Update cannon balls
            for (FireCannonBall ball : cannonBalls) {
                System.out.println("Updating cannon ball");
                ball.update(dt);
                if (ball.isDestroyed()) cannonBalls.removeValue(ball, true);
            }

        }
        if (health <= 0) {
            setToDestroy = true;
        }

        // below code is to move the ship to a coordinate (target)
        //Vector2 target = new Vector2(960 / PirateGame.PPM, 2432 / PirateGame.PPM);
        //target.sub(b2body.getPosition());
        //target.nor();
        //float speed = 1.5f;
        //b2body.setLinearVelocity(target.scl(speed));
    }

    /**
     * Constructs the ship batch
     *
     * @param batch The batch of visual data of the ship
     */
    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
            //Render health bar
            bar.render(batch);
        }
    }

    /**
     * Defines the ship as an enemy
     * Sets data to act as an enemy
     */
    @Override
    protected void defineEnemy() {
        //sets the body definitions
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Sets collision boundaries
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55 / PirateGame.PPM);
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.ENEMY_BIT;
        // determining what this BIT can collide with
        //        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT;
        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.COLLEGE_BIT | PirateGame.COLLEGESENSOR_BIT | PirateGame.COLLEGEFIRE_BIT | PirateGame.PLAYER_BIT | PirateGame.CANNON_BIT | PirateGame.ENEMY_BIT;
        fdef.shape = shape;
        //        fdef.restitution = 0.7f;
        //        fdef.friction = 0.2f;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * Checks contact
     * Changes health in accordance with contact and damage
     */
    @Override
    public void onContact() {
        Gdx.app.log("enemy", "collision");
        //Play collision sound
        if (screen.game.getPreferences().isEffectsEnabled()) {
            hit.play(screen.game.getPreferences().getEffectsVolume());
        }
        //Deal with the damage
        health -= damage;
        bar.changeHealth(damage);
        Hud.changePoints(5);
    }

    /**
     * Updates the ship image. Particuarly change texture on college destruction
     *
     * @param alignment Associated college
     * @param path      Path of new texture
     */
    public void updateTexture(String alignment, String path) {
        college = alignment;
        enemyShip = new Texture(path);
        setRegion(enemyShip);
    }
}
