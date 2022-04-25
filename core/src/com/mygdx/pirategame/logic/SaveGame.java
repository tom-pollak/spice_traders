package com.mygdx.pirategame.logic;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pirategame.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveGame {//using JSON because it seems the easiest to write objects to and retrieve them
    public SaveGame(GameScreen gameScreen){

    }

    public static void save(Player player, ArrayList<EnemyShip> enemyShips, ArrayList<Coin> coins, Hud hud, String filename) {
        JSONObject fullSave = new JSONObject();

        //save all player attributes
        JSONArray playerData = new JSONArray();
        ArrayList position = new ArrayList<Float>();
        position.add(player.b2body.getPosition().x);
        position.add(player.b2body.getPosition().y);
        playerData.add(position);
        playerData.add(player.b2body.getAngle());
        fullSave.put("player", playerData);

        //save all important attributes stored in hud
        JSONArray hudData = new JSONArray();
        hudData.add(hud.getHealth());
        hudData.add(hud.getCoins());
        hudData.add(hud.getScore());
        hudData.add(hud.getCoinMulti());
        fullSave.put("hud", hudData);

        //save all attributes of enemy ships
        JSONArray enemyShipList = new JSONArray();
        for(EnemyShip ship : enemyShips){
            if(!ship.destroyed) {
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
                shipData.add(ship.college);
                shipData.add(ship.texturePath);
                enemyShipList.add(shipData);
            }
        }
        fullSave.put("enemyShips", enemyShipList);

        JSONArray coinsList = new JSONArray();
        for(Coin coin : coins){
            position = new ArrayList<Float>();
            position.add(coin.b2body.getPosition().x);
            position.add(coin.b2body.getPosition().y);
            coinsList.add(position);
        }
        fullSave.put("coins", coinsList);

        try(FileWriter saveFile = new FileWriter(filename)){
            saveFile.write(fullSave.toJSONString());
            saveFile.flush();
            System.out.println("Game saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
