package com.mygdx.pirategame.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.screens.GameScreen;
import com.mygdx.pirategame.sprites.Entity;
import com.mygdx.pirategame.sprites.HealthBar;

import java.util.ArrayList;

/**
 * Created by tom
 * Weather entity that is used to add hazardous conditions to the game
 */
public class Weather extends Entity {
    private final boolean canDamage = false;
    private final ArrayList<Entity> affectedEntities = new ArrayList<>();
    private Integer damageOnTurn = 0;
    private Float speedMultiplier = 1.0f;

    /**
     * Constructor for the weather entity
     *
     * @param screen      GameScreen
     * @param x           x coordinate of the weather entity
     * @param y           y coordinate of the weather entity
     * @param width       width of the weather entity
     * @param height      height of the weather entity
     * @param texturePath path to the texture of the weather entity
     */
    public Weather(GameScreen screen, Float x, Float y, Integer width, Integer height, String texturePath) {
        super(screen, x, y);
        //        Texture texture = getScaledTexture(texturePath, width, height);
        // Set the position and size of the coin
        setBounds(x, y, width, height);
        // Set the texture
        setRegion(new Texture(texturePath));
        defineBody();
        bar = new HealthBar(this);
    }

    public Integer getDamageOnTurn() {
        return this.damageOnTurn;
    }

    public void setDamageOnTurn(Integer damageOnTurn) {
        this.damageOnTurn = damageOnTurn;
    }

    public Float getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    public void setSpeedMultiplier(Float speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public void setMovement(Vector2 direction, Float speed) {
        b2body.setLinearVelocity(direction.scl(speed));
    }

    /**
     * Defines the Box2D body of the weather entity
     * Can collide with the player or an enemy ship
     */
    @Override
    protected void defineBody() {
        // sets the body definitions
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        // Sets collision boundaries
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        System.out.println(getWidth() + " " + getHeight());
        shape.setAsBox(getWidth() / 2, getHeight() / 2);
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.WEATHER_BIT;
        // determining what this BIT can collide with
        fdef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.ENEMY_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * Called when a entity moves into the weather entity
     *
     * @param collidingEntity the entity that is colliding with the weather entity
     */
    @Override
    public void onContact(Entity collidingEntity) {
        Gdx.app.log("Weather", "Collision with " + collidingEntity.getClass().getSimpleName());
        affectedEntities.add(collidingEntity);
    }

    /**
     * @param dt
     */
    @Override
    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if (stateTime > 1) {
            stateTime = 0;
            for (Entity entity : affectedEntities) {
                Gdx.app.log("Weather", "Affecting " + entity.getClass().getSimpleName());
                entity.damage(damageOnTurn);
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        //        batch.enableBlending();
        //
        setAlpha(0.5f);
        super.draw(batch);

        //        batch.disableBlending();
    }

    public void removeContact(Entity entity) {
        affectedEntities.remove(entity);
    }
}
