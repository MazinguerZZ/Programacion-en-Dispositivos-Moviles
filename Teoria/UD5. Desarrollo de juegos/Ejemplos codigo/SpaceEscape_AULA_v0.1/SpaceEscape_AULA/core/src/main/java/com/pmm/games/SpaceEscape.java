package com.pmm.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pmm.games.objects.Player;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SpaceEscape extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;

    // ESTADOS
    private GameState gameState, nextGameState;
    private Texture gameLogo;
    private boolean gameStateChanged;

    // OBJETOS
    private Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("images/libgdx.png");
        font= new BitmapFont();

        gameLogo= new Texture("images/space_escape.png");
        gameState= GameState.MENU;
        gameStateChanged= false;

        Texture playerTexture= new Texture("images/player_texture.png");
        float x= (Gdx.graphics.getWidth() - 64)/2.0f;
        player= new Player(playerTexture, x, 50, 64, 64, 5f);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        gestionarInputs();

        batch.begin();

        representacionEstado();

        batch.end();

        if(gameStateChanged) {
            gameState= nextGameState;
            gameStateChanged= false;
        }
    }

    private void gestionarInputs() {
        switch(gameState) {
            case MENU:
                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    nextGameState= GameState.PLAYING;
                    gameStateChanged= true;
                }
                break;
            case PLAYING:
                if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    player.moveLeft();
                }

                break;

            case GAME_OVER:
                break;
        }
    }


    private void representacionEstado() {

        int screenWidth= Gdx.graphics.getWidth();
        int screenHeight= Gdx.graphics.getHeight();

        switch(gameState) {
            case MENU:
                batch.draw(gameLogo, 0, 0, screenWidth, screenHeight);
                font.draw(batch, "Pulsa ESPACIO para comenzar", 100, 100);
                break;
            case PLAYING:
                ScreenUtils.clear(Color.BLACK);
                batch.draw(image, 140, 210);
                font.setColor(Color.WHITE);
                font.draw(batch, "Pulsa ESC para terminar", 10, screenHeight-10);
                font.setColor(Color.RED);
                font.draw(batch, "Jugando...", (float)(screenWidth*0.45), 100);

                player.render(batch);

                break;

            case GAME_OVER:
                break;
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        font.dispose();
    }
}
