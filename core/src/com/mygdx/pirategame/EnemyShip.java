package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Enemy Ship
 * Generates enemy ship data
 * Instantiates an enemy ship
 *
 *@author Ethan Alabaster, Sam Pearson, Edward Poulter
 *@version 1.0
 */
public class EnemyShip extends Enemy{
    private Texture enemyShip;
    public String college;
    private Sound destroy;
    private Sound hit;

    /**
     * Instantiates enemy ship
     *
     * @param screen Visual data
     * @param x x coordinates of entity
     * @param y y coordinates of entity
     * @param path path of texture file
     * @param assignment College ship is assigned to
     */
    public EnemyShip(GameScreen screen, float x, float y, String path, String assignment) {
        super(screen, x, y);
        enemyShip = new Texture(path);
        college = assignment;
        destroy = Gdx.audio.newSound(Gdx.files.internal("ship-explosion-2.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("ship-hit.wav"));
        setBounds(0,0,64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(enemyShip);
        setOrigin(32 / PirateGame.PPM,55 / PirateGame.PPM);
        damage = 20;
    }

    /**
     * Updates the state of each object with delta time
     * Checks for ship destruction
     *
     * @param dt Delta time (elapsed time since last game tick)
     */
    public void update(float dt) {
        if(setToDestroy && !destroyed) {
            destroy.play(screen.game.getPreferences().getEffectsVolume());
            world.destroyBody(b2body);
            destroyed = true;
            Hud.changePoints(20);
            Hud.changeCoins(10);
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
            float angle = (float) Math.atan2(b2body.getLinearVelocity().y, b2body.getLinearVelocity().x);
            b2body.setTransform(b2body.getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
            setRotation((float) (b2body.getAngle() * 180 / Math.PI));
            bar.update();
        }
        if(health <= 0) {
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
        if(!destroyed) {
            super.draw(batch);
            bar.render(batch);
        }
    }

    /**
     * Defines the ship as an enemy
     * Sets data to act as an enemy
     */
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55 / PirateGame.PPM);
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.ENEMY_BIT;
        // determining what this BIT can collide with
        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT;
        fdef.shape = shape;
        fdef.restitution = 0.7f;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * Checks contact
     * Changes health in accordance with contact and damage
     */
    @Override
    public void onContact() {
        Gdx.app.log("enemy", "collision");
        hit.play(screen.game.getPreferences().getEffectsVolume());
        health -= damage;
        bar.changeHealth(damage);
        Hud.changePoints(5);
    }

    /**
     * Updates the ship image
     *
     * @param alignment Associated college
     * @param path Path of new texture
     */
    public void updateTexture(String alignment, String path){
        college = alignment;
        enemyShip = new Texture(path);
        setRegion(enemyShip);
    }
}
