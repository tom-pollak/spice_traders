package com.mygdx.pirategame.logic;

import com.mygdx.pirategame.screens.GameScreen;
import com.mygdx.pirategame.screens.Hud;
import com.mygdx.pirategame.sprites.AiShip;
import com.mygdx.pirategame.sprites.Coin;
import com.mygdx.pirategame.sprites.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Jakub
 * <p>
 * Used to save game state. The loading is done in GameScreen.java
 */
public class SaveGame { // using JSON because it seems the easiest to write objects to and retrieve them
    /**
     * Constructor for SaveGame
     *
     * @param gameScreen GameScreen object, can find all other game objects
     */
    public SaveGame(GameScreen gameScreen) {
    }

    /*          LOAD IS IN GAMESCREEN.JAVA          */

    /**
     * Saves the game state to a JSON file
     * <p>
     * SAVE JSON FORMAT
     * The JSON saves as an object containing the following keys:
     * player, enemyShips, coins, hud
     * accessible with "json.get("-key-")
     * <p>
     * Player Array:
     * Index 0: Array containing position
     * Index 1: Angle of player
     * Index 2: Inventory array containing each item's information in indexes:
     * Index 0: Type of item
     * Index 1: Texture (toString)
     * Index 2: Array of buffs in JSONObjects with buff type and value, formatted (key, value)
     * <p>
     * Hud Array:
     * Index 0: Health
     * Index 1: Coins
     * Index 2: Score
     * Index 3: Coin Multiplier
     * <p>
     * Enemy Ship Array:
     * Each index contains an array with the data for a single saved ship.
     * For example json,get(0) is the data for the first saved ship.
     * <p>
     * In each index, the array containing the data for a ship:
     * Index 0: Array containing position
     * Index 1: Angle of ship
     * Index 2: Array containing the linear velocity of the ship
     * Index 3: Health
     * Index 4: Damage
     * Index 5: College
     * Index 6: Path to texture of the ship (String)
     * <p>
     * Coin Array:
     * Each index is an array containing the position of a coin.
     *
     * @param player     player
     * @param enemyShips an array of all ships
     * @param coins      an array of all coins
     * @param hud        data from hud (points, score)
     * @param filename   file to save to
     */
    public static void save(Player player, ArrayList<AiShip> enemyShips, ArrayList<Coin> coins, Hud hud, String filename) {
        JSONObject fullSave = new JSONObject();


        // save all player attributes
        JSONArray playerData = new JSONArray();
        ArrayList<Float> position = new ArrayList<Float>();
        position.add(player.b2body.getPosition().x);
        position.add(player.b2body.getPosition().y);
        playerData.add(position);
        playerData.add(player.b2body.getAngle());
        JSONArray inventoryItems = new JSONArray();
        for (Item item : player.inventory) {
            JSONArray itemData = new JSONArray();
            itemData.add(item.getType());
            itemData.add(item.getTexture().toString());
            JSONObject buffs = new JSONObject();
            for (Map.Entry<String, Float> singleBuff : item.buffs.entrySet()) {
                buffs.put(singleBuff.getKey(), singleBuff.getValue());
            }
            itemData.add(buffs);
            inventoryItems.add(itemData);
        }
        playerData.add(inventoryItems);
        fullSave.put("player", playerData);

        // save all important attributes stored in hud
        JSONArray hudData = new JSONArray();
        hudData.add(Hud.getHealth());
        hudData.add(Hud.getCoins());
        hudData.add(Hud.getScore());
        hudData.add(Hud.getCoinMulti());
        fullSave.put("hud", hudData);

        // save all attributes of enemy ships
        JSONArray enemyShipList = new JSONArray();
        for (AiShip ship : enemyShips) {
            if (!ship.destroyed) {
                JSONArray shipData = new JSONArray();
                position = new ArrayList<Float>();
                position.add(ship.b2body.getPosition().x);
                position.add(ship.b2body.getPosition().y);
                shipData.add(position);
                shipData.add(ship.b2body.getAngle());
                ArrayList velocity = new ArrayList<Float>();
                velocity.add(ship.b2body.getLinearVelocity().x);
                velocity.add(ship.b2body.getLinearVelocity().y);
                shipData.add(velocity);
                shipData.add(ship.health);
                shipData.add(ship.damage);
                shipData.add(ship.college.toString());
                shipData.add(ship.texturePath);
                enemyShipList.add(shipData);
            }
        }
        fullSave.put("enemyShips", enemyShipList);

        JSONArray coinsList = new JSONArray();
        for (Coin coin : coins) {
            position = new ArrayList<Float>();
            position.add(coin.b2body.getPosition().x);
            position.add(coin.b2body.getPosition().y);
            coinsList.add(position);
        }
        fullSave.put("coins", coinsList);

        try (FileWriter saveFile = new FileWriter(filename)) {
            saveFile.write(fullSave.toJSONString());
            saveFile.flush();
            System.out.println("Game saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
