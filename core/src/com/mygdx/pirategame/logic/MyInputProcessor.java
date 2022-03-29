package com.mygdx.pirategame.logic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.pirategame.entities.Player;
import com.mygdx.pirategame.items.AbstractItem;
import com.mygdx.pirategame.screens.GameScreen;

public class MyInputProcessor implements InputProcessor {

    private final GameScreen screen;
    private final Player player;

    public MyInputProcessor(GameScreen screen) {
        this.screen = screen;
        this.player = screen.getPlayer();
    }

    @Override
    public boolean keyDown(int keycode) {
        Vector2 thrust;
        switch (keycode) {
            case Input.Keys.W:
                thrust = new Vector2(0, player.accel);
                break;
            case Input.Keys.S:
                thrust = new Vector2(0, -player.accel);
                break;
            case Input.Keys.A:
                thrust = new Vector2(-player.accel, 0);
                break;
            case Input.Keys.D:
                thrust = new Vector2(player.accel, 0);
                break;
            case Input.Keys.ESCAPE:
                if (screen.gameState == GameScreen.GameState.PAUSE) {
                    screen.resume();
                    screen.table.setVisible(true);
                    screen.pauseTable.setVisible(false);
                } else {
                    screen.table.setVisible(false);
                    screen.pauseTable.setVisible(true);
                    screen.pause();
                }
            default:
                return false;
        }
        player.body.applyLinearImpulse(thrust, player.body.getWorldCenter(), true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
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
        Vector3 position = screen.getStage().getCamera().unproject(v);
        if (button == Input.Buttons.LEFT) {
            player.useItem(position.x, position.y);
        }
        return true;
    }
}