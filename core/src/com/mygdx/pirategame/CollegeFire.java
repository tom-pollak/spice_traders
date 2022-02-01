package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollegeFire extends Sprite {
    private World world;
    private Texture cannonBall;
    private GameScreen screen;
    private float stateTime;
    private boolean destroyed;
    private boolean setToDestroy;
    private Body b2body;
    private Sound fireNoise;
    private Vector2 playerPos;

    public CollegeFire(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        playerPos = screen.getPlayerPos();
        cannonBall = new Texture("cannonBall.png");
        setRegion(cannonBall);
        setBounds(x, y, 10 / PirateGame.PPM, 10 / PirateGame.PPM);
        defineCannonBall();
        fireNoise = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        if (screen.game.getPreferences().isEffectsEnabled()) {
            fireNoise.play(screen.game.getPreferences().getEffectsVolume());
        }
    }

    public void defineCannonBall() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PirateGame.PPM);

        fDef.filter.categoryBits = PirateGame.COLLEGEFIRE_BIT;
        fDef.filter.maskBits = PirateGame.PLAYER_BIT;
        fDef.shape = shape;
        fDef.isSensor = true;

        b2body.createFixture(fDef).setUserData(this);
        playerPos.sub(b2body.getPosition());
        playerPos.nor();
        float speed = 5f;
        b2body.setLinearVelocity(playerPos.scl(speed));
    }

    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        // determines cannonball range
        if(stateTime > 1.5f) {
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
