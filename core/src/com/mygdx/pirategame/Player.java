package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Player extends Sprite {
    private final GameScreen screen;
    private Texture ship;
    public World world;
    public Body b2body;
    private Sound breakSound;
    private Array<CannonFire> cannonBalls;

    public Player(GameScreen screen) {
        this.screen = screen;
        ship = new Texture("alcuin_ship.png");
        this.world = screen.getWorld();
        definePlayer();
        setBounds(0,0,64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(ship);
        setOrigin(32 / PirateGame.PPM,55 / PirateGame.PPM);
        breakSound = Gdx.audio.newSound(Gdx.files.internal("wood-bump.mp3"));

        cannonBalls = new Array<CannonFire>();
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        float angle = (float) Math.atan2(b2body.getLinearVelocity().y, b2body.getLinearVelocity().x);
        b2body.setTransform(b2body.getWorldCenter(), angle - ((float)Math.PI) / 2.0f);
        setRotation((float) (b2body.getAngle() * 180 / Math.PI));

        for(CannonFire ball : cannonBalls) {
            ball.update(dt);
            if(ball.isDestroyed())
                cannonBalls.removeValue(ball, true);
        }
    }

    public void playBreakSound() {
        if (screen.game.getPreferences().isEffectsEnabled()) {
            breakSound.play(screen.game.getPreferences().getEffectsVolume());
        }
    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(1800 / PirateGame.PPM, 2500 / PirateGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55 / PirateGame.PPM);
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.PLAYER_BIT;
        // determining what this BIT can collide with
        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.COIN_BIT | PirateGame.ISLAND_BIT |PirateGame.ENEMY_BIT | PirateGame.COLLEGE_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void fire() {
        cannonBalls.add(new CannonFire(screen, b2body.getPosition().x, b2body.getPosition().y, b2body, 5));
        cannonBalls.add(new CannonFire(screen, b2body.getPosition().x, b2body.getPosition().y, b2body, -5));
        // Cone fire below
        /*cannonBalls.add(new CannonFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() - Math.PI / 6), -5, b2body.getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() - Math.PI / 6), 5, b2body.getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() + Math.PI / 6), -5, b2body.getLinearVelocity()));
        cannonBalls.add(new CannonFire(screen, b2body.getPosition().x, b2body.getPosition().y, (float) (b2body.getAngle() + Math.PI / 6), 5, b2body.getLinearVelocity()));
 */   }

    public void draw(Batch batch){
        super.draw(batch);
        for(CannonFire ball : cannonBalls)
            ball.draw(batch);
    }
}
