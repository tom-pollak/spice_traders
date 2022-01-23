package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Coin extends Entity {
    private Texture coin;
    private boolean setToDestroyed;
    private boolean destroyed;

    public Coin(GameScreen screen, float x, float y) {
        super(screen, x, y);
        coin = new Texture("coin.png");
        setBounds(0,0,48 / PirateGame.PPM, 48 / PirateGame.PPM);
        setRegion(coin);
        setOrigin(24 / PirateGame.PPM,24 / PirateGame.PPM);
    }

    public void update(float dt) {
        if(setToDestroyed && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            Hud.changeCoins(1);
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        }
    }

    @Override
    protected void defineEntity() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(700 / PirateGame.PPM, 1200 / PirateGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(24 / PirateGame.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("coin");
    }

    @Override
    public void entityContact() {
        Gdx.app.log("coin", "collision");
        setToDestroyed = true;
    }

    public void draw(Batch batch) {
        if(!destroyed) {
            super.draw(batch);
        }
    }

}
