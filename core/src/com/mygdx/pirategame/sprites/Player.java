package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.logic.Item;
import com.mygdx.pirategame.logic.SpeedOrb;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * Creates the class of the player. Everything that involves actions coming from the player boat
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */
public class Player extends Entity {
    private final Sound breakSound;
    private final Array<PlayerFire> cannonBalls;
    public final Array<Item> inventory;
    public int damageDeal;
    public float damageBuff;
    public float speedBuff;

    /**
     * Instantiates a new Player. Constructor only called once per game
     *
     * @param screen visual data
     */
    public Player(GameScreen screen, Integer x, Integer y) {
        super(screen, x, y);
        // Retrieves world data and creates ship texture
        this.screen = screen;
        Texture ship = new Texture("player_ship.png");
        this.world = screen.getWorld();
        this.damageDeal = 20;

        // Defines a player, and the players position on screen and world
        defineBody();
        setBounds(x, y, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(ship);
        setOrigin(32 / PirateGame.PPM, 55 / PirateGame.PPM);

        // Sound effect for damage
        breakSound = Gdx.audio.newSound(Gdx.files.internal("wood-bump.mp3"));

        // Sets cannonball array
        cannonBalls = new Array<>();

        //initialise inventory
        inventory = new Array<>();
        inventory.add(new SpeedOrb(this));
        inventory.add(new SpeedOrb(this));

        //update at startup
        update(0);
    }

    /**
     * Update the position of the player. Also updates any cannon balls the player generates
     *
     * @param dt Delta Time
     */
    public void update(float dt) {
        // Updates position and orientation of player
        setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        float angle = (float) Math.atan2(b2body.getLinearVelocity().y, b2body.getLinearVelocity().x);
        b2body.setTransform(b2body.getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
        setRotation((float) (b2body.getAngle() * 180 / Math.PI));

        // Updates cannonball data
        for (PlayerFire ball : cannonBalls) {
            ball.update(dt);
            if (ball.isDestroyed()) cannonBalls.removeValue(ball, true);
        }

        //update how inventory items affect the player
        damageBuff = 1;
        speedBuff = 1;
        for (Item item : inventory){
            item.buffs.forEach((buff, multiplier) -> {
                switch (buff) {
                    case "speed":
                        speedBuff *= multiplier;
                        break;
                    case "dmg":
                        damageBuff *= multiplier;
                        break;
                }
            });
        }
    }

    /**
     * Plays the break sound when a boat takes damage
     */
    public void playBreakSound() {
        // Plays damage sound effect
        if (GameScreen.game.getPreferences().isEffectsEnabled()) {
            breakSound.play(GameScreen.game.getPreferences().getEffectsVolume());
        }
    }

    /**
     * Defines all the parts of the player's physical model. Sets it up for collisons
     */
    @Override
    public void defineBody() {
        // Defines a players position
        BodyDef bdef = new BodyDef();
        bdef.position.set(1200 / PirateGame.PPM, 2500 / PirateGame.PPM); // Default Pos: 1800,2500
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        // Defines a player's shape and contact borders
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55 / PirateGame.PPM);

        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.PLAYER_BIT;

        // determining what this BIT can collide with
        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.COIN_BIT | PirateGame.ENEMY_BIT | PirateGame.COLLEGE_BIT | PirateGame.COLLEGESENSOR_BIT | PirateGame.COLLEGEFIRE_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void onContact(Entity collider) {
    }

    /**
     * Called when E is pushed. Causes 1 cannon ball to spawn on both sides of the ships wih their relative velocity
     */
    public void fire() {
        // Fires cannons
        cannonBalls.add(new PlayerFire(screen, b2body, 5, damageDeal*damageBuff));
        cannonBalls.add(new PlayerFire(screen, b2body, -5, damageDeal*damageBuff));

        // Cone fire below
        /*cannonBalls.add(new PlayerFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() - Math.PI / 6), -5, b2body.getLinearVelocity()));
        cannonBalls.add(new PlayerFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() - Math.PI / 6), 5, b2body.getLinearVelocity()));
        cannonBalls.add(new PlayerFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() + Math.PI / 6), -5, b2body.getLinearVelocity()));
        cannonBalls.add(new PlayerFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() + Math.PI / 6), 5, b2body.getLinearVelocity()));
        }
         */
    }

    /**
     * Draws the player using batch
     * Draws cannonballs using batch
     *
     * @param batch The batch of the program
     */
    public void draw(Batch batch) {
        // Draws player and cannonballs
        super.draw(batch);
        for (PlayerFire ball : cannonBalls)
            ball.draw(batch);
    }

    public Array<Item> getInventory() {
        return this.inventory;
    }
}
