package com.mygdx.pirategame.sprites;

import static com.mygdx.pirategame.screens.GameScreen.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.logic.Item;
import com.mygdx.pirategame.logic.SpeedOrb;
import com.mygdx.pirategame.screens.GameScreen;
import com.mygdx.pirategame.screens.Hud;

/**
 * Creates the class of the player. Everything that involves actions coming from the player boat
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */
public class Player extends Ship {
  public final Array<Item> inventory;
  private final Sound breakSound;
  public int damage;
  public float damageBuff;
  public float speedBuff;

  /**
   * Creates the player, singleton class
   *
   * @param screen The game screen
   * @param x Spawn x position
   * @param y Spawn y position
   * @param college The college the player is in
   */
  public Player(GameScreen screen, Integer x, Integer y, College college) {
    super(screen, x, y, "player_ship.png", college);

    // Sound effect for damage
    breakSound = Gdx.audio.newSound(Gdx.files.internal("wood-bump.mp3"));

    // Sets cannonball array
    inventory = new Array<>();
    inventory.add(new SpeedOrb(this));
    inventory.add(new SpeedOrb(this));
    health = 200;
    damage = 20;
  }

  /** Plays the break sound when a boat takes damage */
  public void playBreakSound() {
    // Plays damage sound effect
    if (GameScreen.game.getPreferences().isEffectsEnabled()) {
      breakSound.play(GameScreen.game.getPreferences().getEffectsVolume());
    }
  }

  /** Defines all the parts of the player's physical model. Sets it up for collisons */
  @Override
  public void defineBody() {
    createBody();

    // Defines a player's shape and contact borders
    FixtureDef fdef = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(55 / PirateGame.PPM);

    // setting BIT identifier
    fdef.filter.categoryBits = PirateGame.PLAYER_BIT;

    // determining what this BIT can collide with
    fdef.filter.maskBits =
        PirateGame.DEFAULT_BIT
            | PirateGame.COIN_BIT
            | PirateGame.ENEMY_BIT
            | PirateGame.COLLEGE_BIT
            | PirateGame.PROJECTILE_BIT
            | PirateGame.COLLEGE_SENSOR_BIT
            | PirateGame.WEATHER_BIT;
    fdef.shape = shape;
    b2body.createFixture(fdef).setUserData(this);
  }

  @Override
  public void onContact(Entity collidingEntity) {
    System.out.println("Player contact");
    super.onContact(collidingEntity);
  }

  /**
   * Checks for input and performs an action Applies to keys "W" "A" "S" "D" "E" "Esc"
   *
   * <p>Caps player velocity
   *
   * @param dt Delta time (elapsed time since last game tick)
   */
  public void handleInput(float dt) {
    if (gameStatus == GAME_RUNNING) {
      // Left physics impulse on 'A'
      if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        b2body.applyLinearImpulse(new Vector2(-accel, 0), player.b2body.getWorldCenter(), true);
      }
      // Right physics impulse on 'D'
      if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        b2body.applyLinearImpulse(new Vector2(accel, 0), player.b2body.getWorldCenter(), true);
      }
      // Up physics impulse on 'W'
      if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        b2body.applyLinearImpulse(new Vector2(0, accel), player.b2body.getWorldCenter(), true);
      }
      // Down physics impulse on 'S'
      if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        b2body.applyLinearImpulse(new Vector2(0, -accel), player.b2body.getWorldCenter(), true);
      }
      // Cannon fire on 'E'
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        fire();
      }
      // Checking if player at max velocity, and keeping them below max
      if (player.b2body.getLinearVelocity().x >= maxSpeed) {
        player.b2body.applyLinearImpulse(
            new Vector2(-accel, 0), player.b2body.getWorldCenter(), true);
      }
      if (player.b2body.getLinearVelocity().x <= -maxSpeed) {
        player.b2body.applyLinearImpulse(
            new Vector2(accel, 0), player.b2body.getWorldCenter(), true);
      }
      if (player.b2body.getLinearVelocity().y >= maxSpeed) {
        player.b2body.applyLinearImpulse(
            new Vector2(0, -accel), player.b2body.getWorldCenter(), true);
      }
      if (player.b2body.getLinearVelocity().y <= -maxSpeed) {
        player.b2body.applyLinearImpulse(
            new Vector2(0, accel), player.b2body.getWorldCenter(), true);
      }
    }
  }

  /**
   * Called when Space is pushed. Causes 1 cannon ball to spawn on both sides of the ships wih their
   * relative velocity
   */
  public void fire() {
    // Fires cannons
    cannonBalls.add(new PlayerFire(screen, b2body, 5));
    cannonBalls.add(new PlayerFire(screen, b2body, -5));

    // Cone fire below
    /*cannonBalls.add(new PlayerFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() - Math.PI / 6), -5, b2body.getLinearVelocity()));
    cannonBalls.add(new PlayerFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() - Math.PI / 6), 5, b2body.getLinearVelocity()));
    cannonBalls.add(new PlayerFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() + Math.PI / 6), -5, b2body.getLinearVelocity()));
    cannonBalls.add(new PlayerFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() + Math.PI / 6), 5, b2body.getLinearVelocity()));
    }
     */
  }

  /**
   * Draws the player using batch Draws cannonballs using batch
   *
   * @param batch The batch of the program
   */
  public void draw(Batch batch) {
    Gdx.app.log("Player", "Coord: " + getX() + ", " + getY());
    // Draws player and cannonballs
    super.draw(batch);
    for (Projectile ball : cannonBalls) ball.draw(batch);
  }

  @Override
  public void update(float dt) {
    super.update(dt);
    handleInput(dt);
    Hud.setHealth(health);

    // update how inventory items affect the player
    damageBuff = 1;
    speedBuff = 1;
    for (Item item : inventory) {
      item.buffs.forEach(
          (buff, multiplier) -> {
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

  public Array<Item> getInventory() {
    return this.inventory;
  }

  @Override
  public int getDamage() {
    return Math.round(damage * damageBuff);
  }
}
