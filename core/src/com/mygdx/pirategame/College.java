package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Random;

public class College extends Enemy{
    private float stateTime;
    private Texture enemyCollege;
    private boolean setToDestroy;
    private boolean destroyed;
    public int health = 100;
    public Random rand = new Random();

    public ArrayList<EnemyShip> fleet = new ArrayList<>();

    public College(GameScreen screen, float x, float y, String flag, String ship, int ship_no) {
        super(screen, x, y);
        enemyCollege = new Texture(flag);
        setBounds(0,0,64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(enemyCollege);
        setOrigin(32 / PirateGame.PPM,55 / PirateGame.PPM);
        setToDestroy = false;
        destroyed = false;
        int ranx;
        int rany;
        for (int i = 0; i < ship_no; i++){
            ranx = rand.nextInt(2000) - 1000;
            rany = rand.nextInt(2000) - 1000;
            fleet.add(new EnemyShip(screen, x + (ranx / PirateGame.PPM), y + (rany / PirateGame.PPM), ship));
        }
    }

    public void update(float dt) {
        if(setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
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
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55 / PirateGame.PPM);
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.ENEMY_BIT;
        // determining what this BIT can collide with
        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ISLAND_BIT | PirateGame.ENEMY_BIT;
        fdef.shape = shape;
        fdef.restitution = 0.7f;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void onContact() {
        Gdx.app.log("enemy", "collision");
    }
}
