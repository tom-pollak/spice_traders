package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;

public class Combat extends Entity {
    private float stateTime;
    private Texture cannonBall;
    private boolean setToDestroyed;
    private boolean destroyed;

    public Combat(GameScreen screen, float x, float y) {
        super(screen, x, y);
        cannonBall = new Texture("cannonBall.png");
        setBounds(0,0,10 / PirateGame.PPM, 10 / PirateGame.PPM);
        setRegion(cannonBall);
        setOrigin(5 / PirateGame.PPM,5 / PirateGame.PPM);
        destroyed = false;
        Player player = new Player(screen);
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
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.CANNON_BIT;
        // determining what this BIT can collide with
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
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        }
        if(stateTime == 5) {
            setToDestroyed = true;
        }
    }

    public void draw(Batch batch) {
        if(!destroyed) {
            super.draw(batch);
        }
    }
}
