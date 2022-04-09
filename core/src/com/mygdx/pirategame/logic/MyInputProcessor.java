package com.mygdx.pirategame.logic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.pirategame.entities.Player;
import com.mygdx.pirategame.items.AbstractItem;
import com.mygdx.pirategame.screens.GameScreen;

public class MyInputProcessor extends InputListener {

    private final GameScreen screen;
    private final Player player;

    public MyInputProcessor(GameScreen screen) {
        this.screen = screen;
        this.player = screen.getPlayer();
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (screen.gameState == GameScreen.GameState.PLAY) {
            switch (keycode) {
                case Input.Keys.W:
                    player.input.y -= 1;
                    break;
                case Input.Keys.S:
                    player.input.y += 1;
                    break;
                case Input.Keys.A:
                    player.input.x += 1;
                    break;
                case Input.Keys.D:
                    player.input.x -= 1;
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (screen.gameState == GameScreen.GameState.PLAY) {
            Vector2 thrust = null;
            switch (keycode) {
                case Input.Keys.W:
                    player.input.y += 1;
                    break;
                case Input.Keys.S:
                    player.input.y -= 1;
                    break;
                case Input.Keys.A:
                    player.input.x -= 1;
                    break;
                case Input.Keys.D:
                    player.input.x += 1;
                    break;
                case Input.Keys.ESCAPE:
                    screen.pause();
                    screen.parent.setScreen(screen.parent.MENU);
                    break;
                default:
                    return false;
            }
            return true;
        } else if (screen.gameState == GameScreen.GameState.PAUSE) {
            if (keycode == Input.Keys.ESCAPE) {
                screen.parent.setScreen(screen.parent.GAME);
                screen.resume();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {
        switch (character) {
            case 'e' -> player.pickupOnTile();
            case 'f' -> player.drop();
            case ' ' -> {
                player.useItem();
                System.out.println(player.getX() + " " + player.getY());
            }
            case 'r' -> {
                System.out.println("Inventory:");
                for (int i = 0; i < player.getInventory().size(); i++) {
                    AbstractItem item = player.getInventory().get(i);
                    System.out.println("\t(" + (i + 1) + ") " + item.getName() + ": " + item.getDescription());
                }
            }
        }

        /* Select inventory item */
        if (Character.isDigit(character)) {
            player.switchItem(Character.getNumericValue(character) - 1);
        }
        return true;

    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Vector3 v = new Vector3(x, y, 0);
        Vector3 position = screen.getStage().getCamera().unproject(v);
        if (button == Input.Buttons.LEFT) {
            player.useItem(position.x, position.y);
        }
        return true;
    }
}