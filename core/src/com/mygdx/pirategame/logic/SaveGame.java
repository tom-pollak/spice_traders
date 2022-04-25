//package com.mygdx.pirategame.logic;
//
//import com.badlogic.gdx.utils.Json;
//import com.badlogic.gdx.utils.JsonValue;
//import com.mygdx.pirategame.entities.AbstractEntity;
//import com.mygdx.pirategame.entities.Player;
//import org.json.simple.JSONObject;
//import org.json.simple.JSONArray;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.Savepoint;
//import java.util.Arrays;
//
//public class SaveGame {//using JSON because it seems the easiest to write objects to and retrieve them
//
//    public SaveGame(){
//
//    }
//
//    public static void save(Player playerSave, AbstractEntity[] entitiesSave, String filename) {
//        JSONObject fullSave = new JSONObject();
//        fullSave.put("player", playerSave);
//
//        JSONArray entityList = new JSONArray();
//        entityList.addAll(Arrays.asList(entitiesSave));
//        fullSave.put("entities", entityList);
//
//        try(FileWriter saveFile = new FileWriter(filename)){
//            saveFile.write(fullSave.toString());
//            saveFile.flush();
//            System.out.println("Game saved to " + filename);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void load() {
//
//    }
//}
