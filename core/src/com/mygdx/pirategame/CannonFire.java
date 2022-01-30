package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CannonFire extends Sprite {
    GameScreen screen;
    World world;
    Texture cannonBall;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    Body b2body;
    float angle;
    float velocity;
    Vector2 bodyVel;

    public CannonFire(GameScreen screen, float x, float y, float angle, float velocity, Vector2 bodyVel) {
        this.screen = screen;
        this.angle = angle;
        this.velocity = velocity;
        this.world = screen.getWorld();
        this.bodyVel = bodyVel;

        cannonBall = new Texture("cannonBall.png");
        setRegion(cannonBall);
        setBounds(x, y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
        defineCannonBall();
    }

    public void defineCannonBall() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PirateGame.PPM);

        fDef.filter.categoryBits = PirateGame.CANNON_BIT;
        fDef.filter.maskBits = PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT | PirateGame.COLLEGE_BIT;
        fDef.shape = shape;
        fDef.isSensor = true;

        b2body.createFixture(fDef).setUserData(this);
        float velX = MathUtils.cos(angle) * velocity + bodyVel.x;
        float velY = MathUtils.sin(angle) * velocity + bodyVel.y;
        b2body.applyLinearImpulse(new Vector2(velX, velY), b2body.getWorldCenter(), true);
    }

    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        // determines cannonball range
        if(stateTime > 0.98f) {
            setToDestroy();
        }
    }


    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }
}
