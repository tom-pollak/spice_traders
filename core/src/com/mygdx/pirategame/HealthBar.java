package com.mygdx.pirategame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

class HealthBar {
    private Sprite healthBar1;
    private Texture healthBar2;

    private EnemyShip ownerBoat;
    private College ownerCollege;


    public HealthBar(EnemyShip owner){

        this.ownerBoat = owner;
        this.ownerCollege = null;
        healthBar2 = new Texture("HealthBar.png");
        healthBar1 = new Sprite(healthBar2);
        healthBar1.setScale(0.0135f);

        System.out.println(ownerBoat.b2body.getPosition().y + ownerBoat.getHeight());
        healthBar1.setX (ownerBoat.b2body.getPosition().x - 0.68f);
        healthBar1.setY(ownerBoat.b2body.getPosition().y + ownerBoat.getHeight() / 2);
        healthBar1.setOrigin(0,0);
    }

    public HealthBar(College owner) {
        this.ownerCollege = owner;
        this.ownerBoat = null;
        healthBar2 = new Texture("HealthBar.png");
        healthBar1 = new Sprite(healthBar2);
        healthBar1.setScale(0.0135f);

        System.out.println(ownerCollege.b2body.getPosition().y + ownerCollege.getHeight());
        healthBar1.setX( ownerCollege.b2body.getPosition().x - 0.68f);
        healthBar1.setY(ownerCollege.b2body.getPosition().y + ownerCollege.getHeight() / 2);
        healthBar1.setOrigin(0,0);
    }


    public void update(){
        if (ownerBoat != null) {
            healthBar1.setX( (ownerBoat.b2body.getPosition().x - 0.68f));
            healthBar1.setY(ownerBoat.b2body.getPosition().y + ownerBoat.getHeight() / 2);

        }
        else{
            healthBar1.setX( (ownerCollege.b2body.getPosition().x - 0.68f));
            healthBar1.setY(ownerCollege.b2body.getPosition().y + ownerCollege.getHeight() / 2 + 0.2f);



        }

    }
    public void render(Batch batch){
        healthBar1.draw(batch);

    }



}