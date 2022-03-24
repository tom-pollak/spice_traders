package com.mygdx.pirategame.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pirategame.AbstractActor;
import com.mygdx.pirategame.entities.Player;
import com.mygdx.pirategame.gui.Hud;
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
    private final Sound coinPickup = Gdx.audio.newSound(Gdx.files.internal("coin-pickup.mp3"));
    private boolean setToDestroyed;
    private boolean destroyed;

    public Coin(GameScreen screen, float x, float y) {
        super(screen, "coin.png");
        setPosition(x, y);
    }


    @Override
    public void collide(AbstractActor collidingActor) {
        if (collidingActor instanceof Player) {
            Hud.addCoins(1);
            die();
            Gdx.app.log("coin", "collision");
            //Play pickup sound
            if (screen.parent.getPreferences().isEffectsEnabled()) {
                coinPickup.play(screen.parent.getPreferences().getEffectsVolume());
            }

        }
    }

    public String getDescription() {
        return "A shiny coin. Is it chocolate? No.";
    }
}
