package com.pmm.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pmm.games.objects.Obstacle;
import com.pmm.games.objects.Player;
import com.pmm.games.screens.ScoreScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SpaceEscape extends Game {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;

    // ESTADOS
    private GameState gameState, nextGameState;
    private Texture gameLogo;
    private boolean gameStateChanged;

    // OBJETOS
    private Player player;
    private Array<Obstacle> obstacles;
    private Texture obstacleTexture;
    private float spawnTimer;

    // SCREEN
    private ScoreScreen scoreScreen;
    private boolean showScoreScreen, gamePaused;

    // AUDIO
    private Music backgroundMenuMusic, backgroundGameMusic;

    // ILUMINACION
    private Texture whitePixel;
    private boolean flashActive= false;
    private float flashTimer;

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

        // Inicializamos los asteroides
        obstacles= new Array<>();
        obstacleTexture= new Texture("images/asteroid_texture.png");
        spawnTimer= 0;

        // Inicializamos música
        backgroundMenuMusic= Gdx.audio.newMusic(Gdx.files.internal("music/space-adventure.mp3"));
        backgroundMenuMusic.setLooping(true);
        backgroundMenuMusic.setVolume(0.1f);

        backgroundGameMusic= Gdx.audio.newMusic(Gdx.files.internal("music/space-simple.mp3"));
        backgroundGameMusic.setLooping(true);
        backgroundGameMusic.setVolume(2f);

        // ILUMINACION
        Pixmap pixmap= new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel= new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        actualizarObjetos();

        gestionarInputs();

        batch.begin();

        representacionEstado();

        batch.end();

        if(gameStateChanged) {
            if(nextGameState==GameState.MENU && gameState==GameState.GAME_OVER){
                obstacles.clear();
            }

            if(nextGameState==GameState.PLAYING && gameState==GameState.MENU){
                backgroundMenuMusic.pause();
                backgroundGameMusic.play();
                player.activate();
            }

            gameState= nextGameState;
            gameStateChanged= false;
        }
    }

    private void gestionarInputs() {
        switch(gameState) {
            case MENU:
                if(Gdx.input.isTouched()){
                    nextGameState= GameState.PLAYING;
                    gameStateChanged= true;
                }

                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    nextGameState= GameState.PLAYING;
                    gameStateChanged= true;
                }
                break;
            case PLAYING:

                if(Gdx.input.isTouched()){
                    // Definimos una zona terminal/muerta donde ya no movemos la nave para evitar temblores
                    int DEAD_ZONE= 5;

                    // Movimiento lateral
                    float deltaX = Gdx.input.getX() - (player.getX() + player.getWidth()/2);
                    if (deltaX > DEAD_ZONE) player.moveRight();
                    else if (deltaX < Math.negateExact(DEAD_ZONE)) player.moveLeft();
                    // Movimiento vertical
                    // CUIDADO: coordenadas de pantalla
                    //              origen: arriba-izquierda y crece hacia abajo
                    //          coordenadas del mundo
                    //              origen: abajo-izquierda y crece hacia arriba
                    float invertedY = Gdx.graphics.getHeight() - Gdx.input.getY();
                    float deltaY = invertedY - (player.getY() + player.getHeight()/2);

                    if (deltaY > DEAD_ZONE) player.moveUp();
                    else if (deltaY < Math.negateExact(DEAD_ZONE)) player.moveDown();
                }

                if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    player.moveLeft();
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    player.moveRight();
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    player.moveUp();
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    player.moveDown();
                }

                if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    int score= 100;
                    scoreScreen= new ScoreScreen(score);
                    setScreen(scoreScreen);

                    showScoreScreen= true;
                }

                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    flashActive= true;
                    flashTimer= 0.5f;
                }

                break;

            case GAME_OVER:

                if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    nextGameState= GameState.MENU;
                    gameStateChanged= true;
                    showScoreScreen= true;
                    gamePaused= true;
                    //player.pause();
                }

                break;
        }
    }

    private void actualizarObjetos() {

        float deltaTime= Gdx.graphics.getDeltaTime();

        spawnTimer+= deltaTime;

        if(spawnTimer > 1.2f) {
            addObstacle();
            spawnTimer= 0;
        }

        for(int i= obstacles.size - 1; i >= 0; i--) {
            Obstacle obstacle= obstacles.get(i);
            obstacle.update();

            if(obstacle.isOutOfScreen()) {
                obstacles.removeIndex(i);
            }
        }

        if(flashActive) {
            flashTimer-= deltaTime;
            if(flashTimer <= 0) {
                flashActive= false;
            }
        }

    }

    private void addObstacle() {
        float width= 48;
        float heigth= 48;

        float x= MathUtils.random(0, Gdx.graphics.getWidth() - width);
        float y= Gdx.graphics.getHeight();

        obstacles.add(new Obstacle(obstacleTexture, x, y, width, heigth, 3f));
    }

    private void representacionEstado() {

        int screenWidth= Gdx.graphics.getWidth();
        int screenHeight= Gdx.graphics.getHeight();

        switch(gameState) {
            case MENU:
                batch.draw(gameLogo, 0, 0, screenWidth, screenHeight);
                font.draw(batch, "Pulsa ESPACIO para comenzar", 100, 100);

                backgroundMenuMusic.play();
                break;
            case PLAYING:
                ScreenUtils.clear(Color.BLACK);

                if(flashActive) {
                    batch.setColor(1, 1, 1, 0.6f);
                    batch.draw(whitePixel, 0, 0, screenWidth, screenHeight);
                    batch.setColor(1, 1, 1, 1f);
                }

                batch.draw(image, 140, 210);
                font.setColor(Color.WHITE);
                font.draw(batch, "Pulsa ESC para terminar", 10, screenHeight-10);
                font.setColor(Color.RED);
                font.draw(batch, "Jugando...", (float)(screenWidth*0.45), 100);

                player.render(batch);

                for(int i= obstacles.size - 1; i >= 0; i--) {
                    Obstacle obstacle= obstacles.get(i);
                    obstacle.render(batch);
                }

                for(Obstacle obstacle : obstacles) {
                    if (obstacle.getBounds().overlaps(player.getBounds())) {
                        gameState= GameState.GAME_OVER;
                        gameStateChanged= false;
                        break;
                    }
                }

                if(showScoreScreen) {
                    if (scoreScreen.isShowScoreScreen()) {
                        scoreScreen.render(Gdx.graphics.getDeltaTime());
                    } else {
                        showScoreScreen= false;
                        gamePaused= false;
                    }
                }

                break;

            case GAME_OVER:
                ScreenUtils.clear(Color.RED);
                font.setColor(Color.YELLOW);
                font.draw(batch, "GAME OVER", 100, 150);
                font.draw(batch, "Pulsa ENTER para volver al menú inicial", 100, 100);
                break;
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        font.dispose();
        player.dispose();
        obstacleTexture.dispose();
    }
}
