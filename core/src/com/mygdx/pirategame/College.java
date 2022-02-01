package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Random;

public class College extends Enemy{
    private Texture enemyCollege;
    private boolean setToDestroy;
    protected boolean destroyed;
    public int health = 100;
    public int maxHealth = 100;
    public Random rand = new Random();
    protected HealthBar bar;
    private String currentCollege;
    private Array<CannonFire> cannonBalls;
    private AvailableSpawn noSpawn;
    private int damage;

    public ArrayList<EnemyShip> fleet = new ArrayList<>();

    public College(GameScreen screen, String college, float x, float y, String flag, String ship, int ship_no, AvailableSpawn invalidSpawn) {
        super(screen, x, y);
        noSpawn = invalidSpawn;
        currentCollege = flag;
        enemyCollege = new Texture(flag);
        setBounds(0,0,64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(enemyCollege);
        setOrigin(32 / PirateGame.PPM,55 / PirateGame.PPM);

        setToDestroy = false;
        destroyed = false;
        bar = new HealthBar(this);
        damage = 10;

        cannonBalls = new Array<>();

        int ranX = 0;
        int ranY = 0;
        boolean spawnIsValid;

        for (int i = 0; i < ship_no; i++){
            spawnIsValid = false;
            while (!spawnIsValid){
                ranX = rand.nextInt(2000) - 1000;
                ranY = rand.nextInt(2000) - 1000;
                ranX = (int)Math.floor(x + (ranX / PirateGame.PPM));
                ranY = (int)Math.floor(y + (ranY / PirateGame.PPM));
                System.out.println(ship);
                spawnIsValid = getCoord(ranX, ranY);
            }
            fleet.add(new EnemyShip(screen, ranX, ranY, ship, college));
        }
    }

    public boolean getCoord(int x, int y) {
        if (noSpawn.tileBlocked.containsKey(x)) {
            if (noSpawn.tileBlocked.get(x).contains(y)) {
                return false;
            }
        } else if (x < noSpawn.xBase || x > noSpawn.xCap || y < noSpawn.yBase || y > noSpawn.yCap) {
            return false;
        }
        return true;
    }

    public void update(float dt) {
        if(setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;

            if (currentCollege.equals("alcuin_flag.png")){
                GameScreen.gameOverCheck();
            }
            if (!currentCollege.equals("alcuin_flag.png")){
                Hud.changePoints(100);
                Hud.changeCoins(rand.nextInt(10));
            }
            for(CannonFire ball : cannonBalls) {
                ball.update(dt);
                if(ball.isDestroyed())
                    cannonBalls.removeValue(ball, true);
            }
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        }
        if(health <= 0) {
            setToDestroy = true;
        }
        bar.update();
        if(health <= 0) {
            setToDestroy = true;
        }
    }

    public void draw(Batch batch) {
        if(!destroyed) {
            super.draw(batch);
            bar.render(batch);
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
        shape.setRadius(750 / PirateGame.PPM);
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.COLLEGESENSOR_BIT;
        // determining what this BIT can collide with
        fdef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.CANNON_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void onContact() {
        Gdx.app.log("enemy", "collision");
        health -= damage;
        bar.changeHealth(damage);
        fire();
    }

    public void fire() {

    }


    public void changeDamageRecieved(int value){
        damage = value;
    }
}
