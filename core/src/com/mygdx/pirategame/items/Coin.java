package com.mygdx.pirategame.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pirategame.gui.Hud;
import com.mygdx.pirategame.logic.Alliance;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * Coin
 * Creates an object for each coin
 * Extends the entity class to define coin as an entity
 *
 * @author Joe Dickinson
 * @version 1.0
 */
public class Coin extends AbstractItem {
    public final Integer value = 1;
    private final Sound coinPickup = Gdx.audio.newSound(Gdx.files.internal("coin-pickup.mp3"));

    /**
     * Coin
     * Constructor for coin object
     *
     * @param x      - x coordinate of coin
     * @param y      - y coordinate of coin
     * @param screen - Game screen
     */
    public Coin(GameScreen screen, float x, float y) {
        super(screen, "coin.png");
        setPosition(x, y);
        canBeHeld = false;
    }

    @Override
    public void onPickup(Alliance alliance) {
        Hud.addCoins(value);
        if (screen.parent.getPreferences().isEffectsEnabled()) {
            coinPickup.play(screen.parent.getPreferences().getEffectsVolume());
        }
        die();
        Gdx.app.log("coin", "collision");
    }

    @Override
    public String getName() {
        return "Shiny!";
    }

    public String getDescription() {
        return "A shiny coin. Is it chocolate? No.";
    }
}
