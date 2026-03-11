package com.pmm.games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScoreScreen implements Screen {

    private Stage stage;
    private int score;
    private boolean showScoreScreen;
    private Table table;

    public ScoreScreen(int score) {
        this.score= score;
        this.showScoreScreen= true;

        stage= new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table= new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

    }

    @Override
    public void show() {
        int rectWidth= 300;
        int rectHeight= 150;
        Pixmap pixmap= new Pixmap(rectWidth, rectHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.6f, 0.6f, 0, 0.3f);
        pixmap.fill();

        Texture texture= new Texture(pixmap);
        pixmap.dispose();

        Image background= new Image(texture);
        table.add(background).width(rectWidth).height(rectHeight).pad(10);

        Table innerTable= new Table();
        innerTable.setFillParent(true);
        table.addActor(innerTable);

        Skin skin= new Skin(Gdx.files.internal("skins/uiskin.json"));
        Label scoreLabel= new Label("Tu puntuación: " + score, skin);
        scoreLabel.setFontScale(2f);

        innerTable.add(scoreLabel).center().padTop(20).row();

        TextButton menuButton= new TextButton("MENÚ", skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showScoreScreen= false;
            }
        });

        innerTable.add(menuButton).padTop(20).width(100).height(30);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isShowScoreScreen() { return showScoreScreen; }
}
