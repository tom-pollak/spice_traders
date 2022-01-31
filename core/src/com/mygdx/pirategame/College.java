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

    public ArrayList<EnemyShip> fleet = new ArrayList<>();

    public College(GameScreen screen, float x, float y, String flag, String ship, int ship_no) {
        super(screen, x, y);
        currentCollege = flag;
        enemyCollege = new Texture(flag);
        setBounds(0,0,64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setRegion(enemyCollege);
        setOrigin(32 / PirateGame.PPM,55 / PirateGame.PPM);

        setToDestroy = false;
        destroyed = false;
        bar = new HealthBar(this);

        cannonBalls = new Array<CannonFire>();

        int ranX = 0;
        int ranY = 0;
        Boolean spawnIsValid;

        for (int i = 0; i < ship_no; i++){
            spawnIsValid = false;
            while (!spawnIsValid){
                ranX = rand.nextInt(2000) - 1000;
                ranY = rand.nextInt(2000) - 1000;
                ranX = (int)Math.floor(x + (ranX / PirateGame.PPM));
                ranY = (int)Math.floor(y + (ranY / PirateGame.PPM));
                spawnIsValid = getCoord(ranX, ranY);
            }

            fleet.add(new EnemyShip(screen, ranX, ranY, ship));
        }
    }

    public boolean getCoord(int x, int y) {
        System.out.println("X: " + x + "   Y: " + y);
        int a = 1;
        if (a == 0) {
            if (x <= 0 || y <= 7) {
                return false;
            } else if (x >= 4 && x <= 11 && y >= 83 && y <= 86) {
                return false;
            } else if (x == 53 && y >= 10 && y <= 11) {
                return false;
            } else if (x >= 54 && x <= 56 && y >= 10 && y <= 12) {
                return false;
            } else if (x >= 57 && x <= 71 && y >= 10 && y <= 13) {
                return false;
            } else if (x >= 63 && x <= 70 && y == 9) {
                return false;
            } else if (x >= 69 && x <= 74 && y >= 14 && y <= 18) {
                return false;
            } else if (x >= 70 && x <= 73 && y == 19) {
                return false;
            } else if (x >= 62 && x <= 63 && y >= 14 && y <= 15) {
                return false;
            } else if (x >= 64 && x <= 68 && y >= 14 && y <= 16) {
                return false;
            } else {
                return true;
            }
        } else if (x <= 50 && y >= 50) {
            if (y >= 75){
                return false;
            }else if (x >= 9 && x <= 16 && y >= 60 && y <= 63){
                return false;
            }else if (x >= 11 && x <= 25 && y >= 64 && y <= 69){
                return false;
            }else if (x >= 12 && x <= 25 && y >= 70 && y <= 71) {
                return false;
            }else{
                return true;
            }
        }else if (x >= 50 && y >= 50) {
            if (y >= 75) {
                return false;
            }else if (x >= 58 && x <= 71 && y >= 64 && y <= 70) {
                return false;
            }else if (x >= 67 && x <= 71 && y >= 60 && y <= 64) {
                return false;
            }else if (x >= 57 && x <= 59 && y >= 57 && y <= 62) {
                return false;
            }else{
                return true;
            }
        }else {
            return true;
        }
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
                claimCollege();
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
        for(CannonFire ball : cannonBalls) {
            ball.update(dt);
            if(ball.isDestroyed())
                cannonBalls.removeValue(ball, true);
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
        fdef.restitution = 0.7f;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void onContact() {
        Gdx.app.log("enemy", "collision");
        health -= 10;
        bar.changeHealth(10);
    }

    public void fire() {
        Gdx.app.log("enemy", "collision");
        health -= 10;
        bar.changeHealth(10);
    }

    public void claimCollege(){
        new College(screen, b2body.getPosition().x / PirateGame.PPM, b2body.getPosition().y / PirateGame.PPM,
                "alcuin_flag.png", "alcuin_ship.png", 4);
    }
}
