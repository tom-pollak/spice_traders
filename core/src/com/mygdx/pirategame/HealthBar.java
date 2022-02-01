package com.mygdx.pirategame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

class HealthBar {
    private Sprite healthBar;
    private Texture image;

    private Enemy owner;



    public HealthBar(Enemy owner){
        this.owner = owner;
        image = new Texture("HealthBar.png");
        healthBar = new Sprite(image);
        healthBar.setScale(0.0155f);
        healthBar.setSize(healthBar.getWidth(), healthBar.getHeight() - 2f);

        healthBar.setX (this.owner.b2body.getPosition().x - 0.68f);
        healthBar.setY(this.owner.b2body.getPosition().y + this.owner.getHeight() / 2);
        healthBar.setOrigin(0,0);
    }


    public void update(){
        if (owner != null) {
            healthBar.setX( (owner.b2body.getPosition().x - 0.68f));
            healthBar.setY(owner.b2body.getPosition().y + owner.getHeight() / 2);
        }


    }
    public void render(Batch batch){
        healthBar.draw(batch);

    }

    public void changeHealth(float value){
        healthBar.setSize(healthBar.getWidth() - value, healthBar.getHeight());
    }


}