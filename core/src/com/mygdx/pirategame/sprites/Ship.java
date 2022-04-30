package com.mygdx.pirategame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;
import com.mygdx.pirategame.screens.Hud;

/**
 * Entity AiShip Generates enemy ship data Instantiates an enemy ship
 *
 * @author Ethan Alabaster, Sam Pearson, Edward Poulter
 * @version 1.0
 */
public class Ship extends Entity {
  protected final Sound destroy;
  protected final Sound hit;
  public String texturePath;
  public College college;
  protected Texture shipTexture;
  protected Array<Projectile> cannonBalls = new Array<>();

  /**
   * Instantiates enemy ship
   *
   * @param screen Visual data
   * @param x x coordinates of entity
   * @param y y coordinates of entity
   * @param path path of texture file
   * @param college College ship is assigned to
   */
  public Ship(GameScreen screen, float x, float y, String path, College college) {
    super(screen, x, y);
    shipTexture = new Texture(path);
    this.texturePath = path;
    this.college = college;
    // Assign college
    // Set audios
    destroy = Gdx.audio.newSound(Gdx.files.internal("ship-explosion-2.wav"));
    hit = Gdx.audio.newSound(Gdx.files.internal("ship-hit.wav"));
    // Set the position and size of the college
    setBounds(x, y, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
    setRegion(shipTexture);
    setOrigin(32 / PirateGame.PPM, 55 / PirateGame.PPM);
    defineBody();
    bar = new HealthBar(this);

    damage = 20;
  }

  /**
   * Updates the state of each object with delta time Checks for ship destruction
   *
   * @param dt Delta time (elapsed time since last game tick)
   */
  public void update(float dt) {
    stateTime += dt;

    // If ship is set to destroy and isnt, destroy it
    if (setToDestroy && !destroyed) {
      destroyShip();
    } else if (!destroyed) {
      // Update position and angle of ship
      updatePosition();
      // Update health bar
      bar.update();
      updateCannonBalls(dt);
    }
    if (health <= 0) {
      setToDestroy = true;
    }

    // below code is to move the ship to a coordinate (target)
    // Vector2 target = new Vector2(960 / PirateGame.PPM, 2432 / PirateGame.PPM);
    // target.sub(b2body.getPosition());
    // target.nor();
    // float speed = 1.5f;
    // b2body.setLinearVelocity(target.scl(speed));
  }

  private void updateCannonBalls(float dt) {
    // Update cannon balls
    for (Projectile ball : cannonBalls) {
      ball.update(dt);
      if (ball.isDestroyed()) cannonBalls.removeValue(ball, true);
    }
  }

  private void updatePosition() {
    setPosition(
        b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
    float angle = (float) Math.atan2(b2body.getLinearVelocity().y, b2body.getLinearVelocity().x);
    b2body.setTransform(b2body.getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
    setRotation((float) (b2body.getAngle() * 180 / Math.PI));
  }

  private void destroyShip() {
    // Play death noise
    if (GameScreen.game.getPreferences().isEffectsEnabled()) {
      destroy.play(GameScreen.game.getPreferences().getEffectsVolume());
    }
    world.destroyBody(b2body);
    destroyed = true;
    // Change player coins and points
    Hud.changePoints(20);
    Hud.changeCoins(10);
    for (Projectile ball : cannonBalls) {
      ball.setToDestroy();
    }
  }

  /**
   * Constructs the ship batch
   *
   * @param batch The batch of visual data of the ship
   */
  public void draw(Batch batch) {
    if (!destroyed) {
      super.draw(batch);
      // Render health bar
      bar.render(batch);
      for (Projectile ball : cannonBalls) ball.draw(batch);
    }
  }

  /** Defines the ship as an enemy Sets data to act as an enemy */
  @Override
  protected void defineBody() {
    createBody();

    // Sets collision boundaries
    FixtureDef fdef = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(55 / PirateGame.PPM);
    // setting BIT identifier
    fdef.filter.categoryBits = PirateGame.ENEMY_BIT;
    // determining what this BIT can collide with
    //        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT |
    // PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT;
    // PLAYER_BIT
    fdef.filter.maskBits =
        PirateGame.DEFAULT_BIT
            | PirateGame.COLLEGE_BIT
            | PirateGame.COLLEGESENSOR_BIT
            | PirateGame.COLLEGEFIRE_BIT
            | PirateGame.PLAYER_BIT
            | PirateGame.CANNON_BIT
            | PirateGame.ENEMY_BIT;
    fdef.shape = shape;
    //        fdef.restitution = 0.7f;
    //        fdef.friction = 0.2f;
    b2body.createFixture(fdef).setUserData(this);
  }

  protected void createBody() {
    // sets the body definitions
    BodyDef bdef = new BodyDef();
    bdef.position.set(getX(), getY());
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body = world.createBody(bdef);
  }

  /** Checks contact Changes health in accordance with contact and damage */
  @Override
  public void onContact() {
    Gdx.app.log("enemy", "collision");
    // Play collision sound
    if (GameScreen.game.getPreferences().isEffectsEnabled()) {
      hit.play(GameScreen.game.getPreferences().getEffectsVolume());
    }
    // Deal with the damage
    health -= damage;
    bar.changeHealth(damage);
    Hud.changePoints(5);
  }

  /**
   * Updates the ship image. Particuarly change texture on college destruction
   *
   * @param college Associated college
   * @param path Path of new texture
   */
  public void updateTexture(College college, String path) {
    this.college = college;
    shipTexture = new Texture(path);
    setRegion(shipTexture);
  }
}
