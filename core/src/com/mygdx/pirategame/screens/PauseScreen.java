package com.mygdx.pirategame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.pirategame.PirateGame;

public class PauseScreen extends AbstractScreen {

    public Table pauseTable;
    public Table table;

    public PauseScreen(PirateGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        //GAME BUTTONS
        final TextButton pauseButton = new TextButton("Pause", skin);
        final TextButton skill = new TextButton("Skill Tree", skin);

        //PAUSE MENU BUTTONS
        final TextButton start = new TextButton("Resume", skin);
        final TextButton options = new TextButton("Options", skin);
        TextButton exit = new TextButton("Exit", skin);


        //Create main table and pause tables
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //ADD TO TABLES
        table.add(pauseButton);
        table.row().pad(10, 0, 10, 0);
        table.left().top();

        pauseTable.add(start).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(skill).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(options).fillX().uniformX();
        pauseTable.row().pad(20, 0, 10, 0);
        pauseTable.add(exit).fillX().uniformX();
        pauseTable.center();

        pauseTable = new Table();
        pauseTable.setFillParent(true);
        stage.addActor(pauseTable);
    }

}
