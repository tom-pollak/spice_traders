package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyShip extends Enemy{
    private Texture enemyShip;

    public EnemyShip(GameScreen screen, float x, float y) {
        super(screen, x, y);
        enemyShip = new Texture("enemyShip1.png");
        setBounds(0,0,64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(enemyShip);
        setOrigin(32 / PirateGame.PPM,55 / PirateGame.PPM);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        float angle = (float) Math.atan2(b2body.getLinearVelocity().y, b2body.getLinearVelocity().x);
        b2body.setTransform(b2body.getWorldCenter(), angle - ((float)Math.PI) / 2.0f);
        setRotation((float) (b2body.getAngle() * 180 / Math.PI));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(1200 / PirateGame.PPM, 1200 / PirateGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55 / PirateGame.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
