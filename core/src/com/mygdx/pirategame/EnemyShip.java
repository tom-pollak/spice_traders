package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyShip extends Enemy{
    private float stateTime;
    private Texture enemyShip;
    private boolean setToDestroy;
    private boolean destroyed;
    private int health = 100;

    public EnemyShip(GameScreen screen, float x, float y) {
        super(screen, x, y);
        enemyShip = new Texture("enemyShip1.png");
        setBounds(0,0,64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(enemyShip);
        setOrigin(32 / PirateGame.PPM,55 / PirateGame.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt) {
        if(setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
            float angle = (float) Math.atan2(b2body.getLinearVelocity().y, b2body.getLinearVelocity().x);
            b2body.setTransform(b2body.getWorldCenter(), angle - ((float) Math.PI) / 2.0f);
            setRotation((float) (b2body.getAngle() * 180 / Math.PI));
        }
    }

    public void draw(Batch batch) {
        if(!destroyed) {
            super.draw(batch);
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55 / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.ENEMY_BIT;
        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ISLAND_BIT | PirateGame.ENEMY_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    @Override
    public void onContact() {

    }
}
