package com.mygdx.pirategame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pirategame.CannonFire;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.items.AbstractItem;
import com.mygdx.pirategame.screens.GameScreen;

/**
 * Creates the class of the player. Everything that involves actions coming from the player boat
 *
 * @author Ethan Alabaster, Edward Poulter
 * @version 1.0
 */
public class Player extends Ship {
    public final InputProcessor input = new InputProcessor() {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.W:
                    velocity.y = speed;
                    break;
                case Input.Keys.S:
                    velocity.y = -speed;
                    break;
                case Input.Keys.A:
                    velocity.x = -speed;
                    break;
                case Input.Keys.D:
                    velocity.x = speed;
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.A:
                    /* D is pressed and A is released, reverse the direction */
                    if (!Gdx.input.isKeyPressed(Input.Keys.D)) velocity.x = 0;
                    else velocity.x = speed;
                    break;
                case Input.Keys.D:
                    if (!Gdx.input.isKeyPressed(Input.Keys.A)) velocity.x = 0;
                    else velocity.x = -speed;
                    break;
                case Input.Keys.W:
                    if (!Gdx.input.isKeyPressed(Input.Keys.S)) velocity.y = 0;
                    else velocity.y = -speed;
                    break;
                case Input.Keys.S:
                    if (!Gdx.input.isKeyPressed(Input.Keys.W)) velocity.y = 0;
                    else velocity.y = speed;
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public boolean keyTyped(char character) {
            switch (character) {
                case 'e':
                    pickup();
                    break;
                case 'f':
                    drop();
                    break;
                case ' ':
                    useItem();
                    System.out.println(getX() + " " + getY());
                    break;
                case 'r':
                    System.out.println("Inventory:");
                    for (int i = 0; i < getInventory().size(); i++) {
                        AbstractItem item = getInventory().get(i);
                        System.out.println("\t(" + (i + 1) + ") " + item.getName() + ": " + item.getDescription());
                    }
                    break;

            }

            /* Select inventory item */
            if (Character.isDigit(character)) {
                switchItem(Character.getNumericValue(character) - 1);
            }
            return true;

        }


        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            Vector3 v = new Vector3(x, y, 0);
            try {
                Vector3 position = getStage().getCamera().unproject(v);
                if (button == Input.Buttons.LEFT) {
                    useItem(position.x, position.y);
                }
                return true;

            } catch (Exception e) {
                return false;
            }
        }
    };
    private final Sound breakSound;
    private final Array<CannonFire> cannonBalls;

    /**
     * Instantiates a new Player. Constructor only called once per parent
     *
     * @param screen visual data
     */
    public Player(GameScreen screen) {
        // Retrieves world data and creates ship texture

        // Defines a player, and the players position on screen and world
        super(screen, "player.png");
        setBounds(0, 0, 64 / PirateGame.PPM, 110 / PirateGame.PPM);
        setOrigin(32 / PirateGame.PPM, 55 / PirateGame.PPM);

        // Sound effect for damage
        breakSound = Gdx.audio.newSound(Gdx.files.internal("wood-bump.mp3"));

        // Sets cannonball array
        cannonBalls = new Array<>();
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

    /**
     * Called when E is pushed. Causes 1 cannon ball to spawn on both sides of the ships wih their relative velocity
     */
    public void fire() {
    }
}
