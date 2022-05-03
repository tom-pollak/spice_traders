package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pirategame.screens.GameScreen;
import com.mygdx.pirategame.screens.Hud;

import static com.mygdx.pirategame.screens.GameScreen.player;

/**
 * Entity AiShip Generates enemy ship data Instantiates an enemy ship
 *
 * @author Ethan Alabaster, Sam Pearson, Edward Poulter
 * @version 1.0
 */
public class AiShip extends Ship {
    /**
     * Instantiates enemy ship
     *
     * @param screen  Visual data
     * @param x       x coordinates of entity
     * @param y       y coordinates of entity
     * @param path    path of texture file
     * @param college College ship is assigned to
     */
    public AiShip(GameScreen screen, float x, float y, String path, College college) {
        super(screen, x, y, path, college);
        // Set audios
    }

    /**
     * Updates the state of each object with delta time Checks for ship destruction
     *
     * @param dt Delta time (elapsed time since last game tick)
     */
    public void update(float dt) {
        super.update(dt);
        stateTime += dt;

        if (!destroyed) {
            moveTowardsPlayer(dt);
            fireAtPlayer();
        }
    }

    /**
     * Called in update to fire at player if certain conditions are met
     */
    private void fireAtPlayer() {
        if (stateTime > 1 && getDistance(player) < 10) {
            if (screen.getPlayerPos() != null) {
                cannonBalls.add(new Projectile(screen, b2body.getPosition().x, b2body.getPosition().y, "cannonBall.png", screen.getPlayerPos()));
            }
            stateTime = 0;
        }
    }

    private void moveTowardsPlayer(float dt) {
        Vector2 cur_coord = new Vector2(getX(), getY());
        Vector2 player_coord = new Vector2(GameScreen.player.getX(), GameScreen.player.getY());
        Vector2 college_coord = null;
        if (college != null) {
            college_coord = new Vector2(college.getX(), college.getY());
        }
        Vector2 target = null;

        if (college != null && getDistance(college) > 50) {
            target = new Vector2(college_coord.x - cur_coord.x, college_coord.y - cur_coord.y);
            target.limit2(1).scl(0.05f);
        } else if (getDistance(GameScreen.player) < 10) {
            target = new Vector2(player_coord.x - cur_coord.x, player_coord.y - cur_coord.y);
            target.limit2(1).scl(0.05f);
        }
        try {
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
            if (target != null) {
                b2body.applyLinearImpulse(target, b2body.getWorldCenter(), true); // Player uses linear impulse
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    /**
     * Checks contact Changes health in accordance with contact and damage
     */
    @Override
    public void onContact(Entity collidingEntity) {
        Gdx.app.log("enemy", "collision");
        // Play collision sound
        if (GameScreen.game.getPreferences().isEffectsEnabled()) {
            hit.play(GameScreen.game.getPreferences().getEffectsVolume());
        }
        // Deal with the damage
        health -= collidingEntity.getDamage();
        bar.changeHealth(collidingEntity.getDamage());
        Hud.changePoints(5);
    }

    /**
     * Updates the ship image. Particuarly change texture on college destruction
     *
     * @param college Associated college
     * @param path    Path of new texture
     */
    public void updateTexture(College college, String path) {
        this.college = college;
        shipTexture = new Texture(path);
        setRegion(shipTexture);
    }
}
