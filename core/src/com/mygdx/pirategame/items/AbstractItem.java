package com.mygdx.pirategame.items;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.entities.AbstractEntity;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.screens.GameScreen;

import java.util.ArrayList;

public abstract class AbstractItem extends AbstractActor {

    public boolean isHeld = false;
    public boolean canBeHeld = true;
    public boolean canBeUsed = false;

    public AbstractItem(GameScreen screen, String texturePath) {
        super(screen, texturePath);
        setBounds(0, 0, 48 / PirateGame.PPM, 48 / PirateGame.PPM);
    }

    public void createBody() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        fDef.filter.categoryBits = PirateGame.ITEM_BIT;
        fDef.isSensor = true;
        fDef.shape = getShape();
        body.createFixture(fDef).setUserData(this);
    }

    public abstract String getName();

    public abstract String getDescription();

    public void onPickup(Alliance alliance) {
        isHeld = true;
    }

    public void onDrop() {
        isHeld = false;
    }

    public void use(ArrayList<AbstractEntity> collidingEntities) {
    }

    @Override
    public void collide(AbstractActor other) {
    }

    public void use(float x, float y) {
    }

}
