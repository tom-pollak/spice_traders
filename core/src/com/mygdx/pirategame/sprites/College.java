package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.logic.AvailableSpawn;
import com.mygdx.pirategame.screens.GameScreen;
import com.mygdx.pirategame.screens.Hud;

import java.util.ArrayList;
import java.util.Random;

/**
 * College Class to generate the enemy entity college Instantiates colleges Instantiates college
 * fleets
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */
public class College extends Entity {
    private final Texture enemyCollege;
    private final String name;
    private final String currentCollege;
    private final Array<Projectile> cannonBalls;
    private final AvailableSpawn noSpawn;
    public Random rand = new Random();
    public ArrayList<AiShip> fleet = new ArrayList<>();

    /**
     * @param screen       Visual data
     * @param name         College name i.e. "Alcuin" used for fleet assignment
     * @param x            College position on x-axis
     * @param y            College position on y-axis
     * @param flag         College flag sprite (image name)
     * @param ship         College ship sprite (image name)
     * @param ship_no      Number of college ships to produce
     * @param invalidSpawn Spawn data to check spawn validity when generating ships
     */
    public College(GameScreen screen, String name, float x, float y, String flag, String ship, int ship_no, AvailableSpawn invalidSpawn) {
        super(screen, x, y);
        this.screen = screen;
        this.name = name;
        noSpawn = invalidSpawn;
        currentCollege = flag;
        enemyCollege = new Texture(flag);
        // Set the position and size of the college
        setBounds(x, y, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(enemyCollege);
        setOrigin(32 / PirateGame.PPM, 55 / PirateGame.PPM);
        defineBody();
        bar = new HealthBar(this);
        damage = 10;
        cannonBalls = new Array<>();
        int ranX = 0;
        int ranY = 0;
        boolean spawnIsValid;

        // Generates college fleet
        for (int i = 0; i < ship_no; i++) {
            spawnIsValid = false;
            while (!spawnIsValid) {
                ranX = rand.nextInt(2000) - 1000;
                ranY = rand.nextInt(2000) - 1000;
                ranX = (int) Math.floor(x + (ranX / PirateGame.PPM));
                ranY = (int) Math.floor(y + (ranY / PirateGame.PPM));
                spawnIsValid = getCoord(ranX, ranY);
            }
            fleet.add(new AiShip(screen, ranX, ranY, ship, this));
        }
    }

    /**
     * Checks ship spawning in at a valid location
     *
     * @param x x position to test
     * @param y y position to test
     * @return isValid : returns the validity of the proposed spawn point
     */
    public boolean getCoord(int x, int y) {
        if (x < AvailableSpawn.xBase || x >= AvailableSpawn.xCap || y < AvailableSpawn.yBase || y >= AvailableSpawn.yCap) {
            return false;
        } else if (noSpawn.tileBlocked.containsKey(x)) {
            return !noSpawn.tileBlocked.get(x).contains(y);
        }
        return true;
    }

    /**
     * Updates the state of each object with delta time Checks for college destruction Checks for
     * cannon fire
     *
     * @param dt Delta time (elapsed time since last game tick)
     */
    public void update(float dt) {
        // If college is set to destroy and isnt, destroy it
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;

            // If it is the player ally college, end the game for the player
            if (currentCollege.equals("alcuin_flag.png")) {
                screen.gameOverCheck();
            }
            // Award the player coins and points for destroying a college
            if (!currentCollege.equals("alcuin_flag.png")) {
                Hud.changePoints(100);
                Hud.changeCoins(rand.nextInt(10));
            }
        }
        // If not destroyed, update the college position
        else if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        }
        if (health <= 0) {
            setToDestroy = true;
        }
        bar.update();
        if (health <= 0) {
            setToDestroy = true;
        }
        // Update cannon balls
        for (Projectile ball : cannonBalls) {
            ball.update(dt);
            if (ball.isDestroyed()) cannonBalls.removeValue(ball, true);
        }

        for (AiShip ship : fleet) {
            ship.update(dt);
        }
    }

    /**
     * Draws the batch of cannonballs
     */
    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
            // Render health bar
            bar.render(batch);
            // Render balls
            for (Projectile ball : cannonBalls) ball.draw(batch);

            for (AiShip ship : fleet) {
                ship.draw(batch);
            }
        }
    }

    /**
     * Sets the data to define a college as an enemy
     */
    @Override
    protected void defineBody() {
        // sets the body definitions
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        // Sets collision boundaries
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55 / PirateGame.PPM);
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.COLLEGE_BIT;
        // determining what this BIT can collide with
        fdef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.PROJECTILE_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * Contact detection Allows for the college to take damage
     */
    @Override
    public void onContact(Entity collidingEntity) {
        // Damage the college and lower health bar
        Gdx.app.log("enemy", "collision");
        health -= collidingEntity.getDamage();
        bar.changeHealth(collidingEntity.getDamage());
    }

    /**
     * Fires cannonballs
     */
    public void fire() {
        cannonBalls.add(new Projectile(screen, b2body.getPosition().x, b2body.getPosition().y, "cannonBall.png", screen.getPlayerPos()));
    }

    public String getName() {
        return name;
    }
}
