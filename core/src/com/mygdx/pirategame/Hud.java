package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    //private Integer worldTimer;
    private float timeCount;
    private Integer score;
    public static Integer health;
    private Texture minimap;
    private Texture hp;
    private Texture boxBackground;
    private Texture coinPic;

    //private Label timeLabel;
    private Label scoreLabel;
    private static Label healthLabel;
    private static Label coinLabel;
    private static Label pointsText;
    private static Integer coins;
    //private Image minimapImg;
    private Image hpImg;
    private Image box;
    private Image coin;

    public Hud(SpriteBatch sb, final PirateGame game) {
        //worldTimer = 0;
        health = 100;
        score = 0;
        coins = 0;
        minimap = new Texture("minimap.png");
        hp = new Texture("hp.png");
        boxBackground = new Texture("hudBG.png");
        coinPic = new Texture("coin.png");

        //minimapImg = new Image(minimap);
        hpImg = new Image(hp);
        box = new Image(boxBackground);
        coin = new Image(coinPic);

        viewport = new ExtendViewport(PirateGame.WIDTH, PirateGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table1 = new Table();
        Table table2 = new Table();
        Table table3 = new Table();
        Table table4 = new Table();
        Table table5 = new Table();
        table1.top().right();
        table1.setFillParent(true);
        table2.top().right();
        table2.setFillParent(true);
        table3.top().right();
        table3.setFillParent(true);
        table4.top().left();
        table4.setFillParent(true);

        //timeLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //timeLabel.setFontScale(2f);
        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(2f);
        healthLabel = new Label(String.format("%03d", health), new Label.LabelStyle(new BitmapFont(), Color.RED));
        healthLabel.setFontScale(2f);
        coinLabel = new Label(String.format("%03d", coins), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        coinLabel.setFontScale(2f);
        pointsText = new Label("Points:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pointsText.setFontScale(2f);

        table3.add(box).width(250).height(250).padBottom(15).padLeft(30);
        table2.add(hpImg).width(64).height(64).padTop(16).padRight(160);
        table2.row();
        table2.add(coin).width(64).height(64).padTop(16).padRight(160);
        table2.row();
        table2.add(pointsText).width(64).height(64).padTop(6).padRight(160);
        table1.add(healthLabel).padTop(32).top().right().padRight(80);
        table1.row();
        //table1.add(timeLabel).expandX().padBottom(15).top().right().padRight(60);
        //table1.row();
        table1.add(coinLabel).padTop(36).top().right().padRight(80);
        table1.row();
        table1.add(scoreLabel).padTop(36).top().right().padRight(80);
        //table2.add(minimapImg).width(64).height(64).padBottom(15).expandX().padLeft(1100);
        stage.addActor(table3);
        stage.addActor(table2);
        stage.addActor(table1);
    }

    public void update(float dt) {
        timeCount += dt;
        if(timeCount >= 1) {
            //worldTimer += 1;
            //timeLabel.setText(String.format("%03d", worldTimer));
            if(health != 100) {
                health += 1;
                healthLabel.setText(String.format("%02d", health));
            }
            timeCount = 0;
        }
    }

    public static void changeHealth(int value) {
        health += value;
        healthLabel.setText(String.format("%02d", health));
    }
    public static void changeCoins(int value) {
        coins += value;
        coinLabel.setText(String.format("%03d", coins));
    }

    public static Integer getHealth(){
        return health;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

