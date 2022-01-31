package com.mygdx.pirategame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

class HealthBar {
    private Sprite healthBar;
    private Texture image;

    private EnemyShip ownerBoat;
    private College ownerCollege;


    public HealthBar(EnemyShip owner){
        this.ownerBoat = owner;
        this.ownerCollege = null;
        image = new Texture("HealthBar.png");
        healthBar = new Sprite(image);
        healthBar.setScale(0.0155f);
        healthBar.setSize(healthBar.getWidth(), healthBar.getHeight() - 2f);

        healthBar.setX (ownerBoat.b2body.getPosition().x - 0.68f);
        healthBar.setY(ownerBoat.b2body.getPosition().y + ownerBoat.getHeight() / 2);
        healthBar.setOrigin(0,0);
    }

    public HealthBar(College owner) {
        this.ownerCollege = owner;
        this.ownerBoat = null;
        image = new Texture("HealthBar.png");
        healthBar = new Sprite(image);
        healthBar.setScale(0.0135f);
        healthBar.setSize(healthBar.getWidth(), healthBar.getHeight() - 1.5f);

        healthBar.setX( ownerCollege.b2body.getPosition().x - 0.68f);
        healthBar.setY(ownerCollege.b2body.getPosition().y + ownerCollege.getHeight() / 2);
        healthBar.setOrigin(0,0);
    }

    public void update(){
        if (ownerBoat != null) {
            healthBar.setX( (ownerBoat.b2body.getPosition().x - 0.68f));
            healthBar.setY(ownerBoat.b2body.getPosition().y + ownerBoat.getHeight() / 2);
        }
        else{
            healthBar.setX( (ownerCollege.b2body.getPosition().x - 0.68f));
            healthBar.setY(ownerCollege.b2body.getPosition().y + ownerCollege.getHeight() / 2 + 0.2f);
        }

    }
    public void render(Batch batch){
        healthBar.draw(batch);

    }

    public void changeHealth(float value){
        healthBar.setSize(healthBar.getWidth() - value, healthBar.getHeight());
    }


}