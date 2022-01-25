package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Combat extends Entity {
    private float stateTime;
    private Texture cannonBall;
    private boolean setToDestroyed;
    private boolean destroyed;
    private Vector2 velocity;

    public Combat(GameScreen screen, float x, float y) {
        super(screen, x, y);
        cannonBall = new Texture("cannonBall.png");
        setBounds(0,0,10 / PirateGame.PPM, 10 / PirateGame.PPM);
        setRegion(cannonBall);
        setOrigin(5 / PirateGame.PPM,5 / PirateGame.PPM);
        velocity = new Vector2(0,0);
        destroyed = false;
    }

    @Override
    protected void defineEntity() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PirateGame.PPM);

        fdef.filter.categoryBits = PirateGame.CANNON_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.ENEMY_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void entityContact() {

    }

    public void update(float dt) {
        stateTime += dt;
        if(setToDestroyed && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            stateTime = 0;
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        }
        if(stateTime > 1) {
            setToDestroyed = true;
        }
    }

    public void draw(Batch batch) {
        if(!destroyed) {
            super.draw(batch);
        }
    }

    public void destroy() {
        setToDestroyed = true;
    }
}
