package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.gui.Hud;
import com.mygdx.pirategame.screens.GameScreen;

public class Ship extends AbstractEntity {
    private static final Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("ship-hit.wav"));
    private final Sound destroySound = Gdx.audio.newSound(Gdx.files.internal("ship-explosion-2.wav"));

    public Ship(GameScreen screen) {
        super(screen, "unaligned_ship.png");
        setMaxHealth(100);
        setHealth(100);
    }

    public Ship(GameScreen screen, String textureName) {
        super(screen, textureName);
        setMaxHealth(100);
        setHealth(100);
    }


    @Override
    public void collide(AbstractActor collidingActor) {
        Gdx.app.log("enemy", "collision");
        //Play collision sound
        if (screen.parent.getPreferences().isEffectsEnabled()) {
            hitSound.play(screen.parent.getPreferences().getEffectsVolume());
        }
        health -= getCollisionDamage(collidingActor);
        healthBar.setHealth(health);
        Hud.changePoints(5);
    }

    public Integer getCollisionDamage(AbstractActor collidingActor) {
        // TODO return absolute velocity of both actors * multiplier (can be changed by upgrades)
        return 0;
    }

    @Override
    public void act(float delta) {
        healthBar.update();
        if (health <= 0) die();
    }

    @Override
    public void die() {
        super.die();
        destroySound.play();
    }
}
