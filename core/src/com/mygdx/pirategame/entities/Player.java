package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.items.Key;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * Creates the class of the player. Everything that involves actions coming from the player boat
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */
public class Player extends Ship {
    private final Sound breakSound;

    /**
     * Instantiates a new Player. Constructor only called once per parent
     *
     * @param screen visual data
     */
    public Player(GameScreen screen) {
        // Retrieves world data and creates ship texture

        // Defines a player, and the players position on screen and world
        super(screen, "img/player_ship.png");
        setBounds(0, 0, 64 / PirateGame.PPM, 110 / PirateGame.PPM);

        // Sound effect for damage
        breakSound = Gdx.audio.newSound(Gdx.files.internal("audio/wood-bump.mp3"));
        addItem(new Key(screen, "key", "the best key for testing inventory"), 0);
        addItem(new Key(screen, "key", "the best key for testing inventory"), 0);
    }

    @Override
    public void act(float delta) {
        handleInput();
        super.act(delta);
    }

    @Override
    public void createBody() {
        super.createBody();
        body.destroyFixture(body.getFixtureList().get(0));

        FixtureDef fDef = new FixtureDef();
        fDef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fDef.shape = getShape();
        fDef.friction = friction;
        body.createFixture(fDef).setUserData(this);
    }

    /**
     * Plays the break sound when a boat takes damage
     */
    public void playBreakSound() {
        // Plays damage sound effect
        if (screen.parent.getPreferences().isEffectsEnabled()) {
            breakSound.play(screen.parent.getPreferences().getEffectsVolume());
        }
    }


}
