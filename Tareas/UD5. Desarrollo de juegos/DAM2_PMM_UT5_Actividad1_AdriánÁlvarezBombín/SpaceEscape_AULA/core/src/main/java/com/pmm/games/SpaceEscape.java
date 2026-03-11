package com.pmm.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pmm.games.objects.Bullet;
import com.pmm.games.objects.Obstacle;
import com.pmm.games.objects.Player;
import com.pmm.games.screens.ScoreScreen;

/**
 * SpaceEscape - Juego de nave espacial con sistema de disparos y destrucción de asteroides.
 * El jugador controla una nave que puede moverse y disparar para destruir asteroides.
 * La puntuación se calcula en base al tiempo jugado y asteroides destruidos.
 * El juego incluye tres estados principales: MENU, PLAYING y GAME_OVER.
 *
 * ADAPTACIONES INTRODUCIDAS - TEMÁTICA ANGRY BIRDS VS CERDITOS:
 * 1. TEMÁTICA COMPLETA ANGRY BIRDS: Transformación del juego original de naves espaciales
 *    a una temática de Angry Birds donde los pájaros disparan a cerditos.
 * 2. ELEMENTOS VISUALES PERSONALIZADOS:
 *    - Jugador: Pajaro rojo (red.png) que es como si fuera la nave
 *    - Obstáculos: Cerditos (cerdo.png) como enemigos a destruir
 *    - Balas: Proyectiles rojos (bala.jpg) representando el ataque de los pájaros
 * 3. AUDIO TEMÁTICO COMPLETO:
 *    - Música de menú: Tema principal de Angry Birds (angry-birds.mp3)
 *    - Música de juego: Música épica de batalla (battle.mp3)
 *    - Efectos de sonido:
 *        * Disparo: Sonido característico de lanzamiento (piuw.mp3)
 *        * Explosión: Sonido de cerdo eliminado (muerteCerdo.mp3)
 *        * Motor: Zumbido de tensión (tension-drones.mp3)
 * 4. MECÁNICAS DE JUEGO ADAPTADAS:
 *    - Los "asteroides" son ahora cerditos que caen desde arriba
 *    - El "jugador" es un pájaro que debe defender su posición
 *    - Puntuación: Se gana tiempo sobreviviendo + 10 puntos por cerdo destruido
 * 5. EFECTOS VISUALES MEJORADOS:
 *    - Flash al disparar para mayor impacto visual
 *    - Hitboxes ajustadas para colisiones más precisas
 *    - Interfaz con colores vibrantes (rojo, amarillo) acorde a la temática
 * 6. CONTROLES MULTIPLATAFORMA:
 *    - Táctil: Movimiento deslizando el dedo
 *    - Teclado: Flechas para movimiento, ESPACIO para disparar
 *    - Ratón: Click derecho como alternativa de disparo
 *
 * @author Adrián Álvarez y Alejandro Medina
 * @version 1.1 - Edición Angry Birds
 */
public class SpaceEscape extends Game {

    /** Lote de sprites para renderizar gráficos. */
    private SpriteBatch batch;

    /** Textura de fondo del juego. */
    private Texture image;

    /** Fuente para renderizar texto en pantalla. */
    private BitmapFont font;

    /** Estado actual del juego. */
    private GameState gameState;

    /** Próximo estado del juego para transición. */
    private GameState nextGameState;

    /** Logo del juego para la pantalla de menú. */
    private Texture gameLogo;

    /** Indica si el estado del juego ha cambiado y necesita procesamiento. */
    private boolean gameStateChanged;

    /** Instancia del jugador controlable. */
    private Player player;

    /** Lista de obstáculos (asteroides) activos en el juego. */
    private Array<Obstacle> obstacles;

    /** Textura utilizada para los obstáculos. */
    private Texture obstacleTexture;

    /** Temporizador para la generación de nuevos obstáculos. */
    private float spawnTimer;

    /** Pantalla de puntuación para mostrar resultados. */
    private ScoreScreen scoreScreen;

    /** Indica si debe mostrarse la pantalla de puntuación. */
    private boolean showScoreScreen;

    /** Indica si el juego está en pausa. */
    private boolean gamePaused;

    /** Música de fondo para la pantalla de menú. */
    private Music backgroundMenuMusic;

    /** Música de fondo durante el juego. */
    private Music backgroundGameMusic;

    /** Efecto de sonido para explosiones de asteroides. */
    private Sound explosionSound;

    /** Textura de un píxel blanco para efectos visuales. */
    private Texture whitePixel;

    /** Controla si el efecto de flash está activo. */
    private boolean flashActive;

    /** Temporizador para el efecto de flash. */
    private float flashTimer;

    /** Puntuación total del jugador. */
    private int score;

    /** Contador de asteroides destruidos por el jugador. */
    private int asteroidsDestroyed;

    /** Tiempo total transcurrido durante el juego actual. */
    private float gameTime;

    /** Textura utilizada para las balas del jugador. */
    private Texture bulletTexture;

    /**
     * Inicializa todos los recursos del juego: texturas, sonidos, música y objetos del juego.
     * Configura el estado inicial del juego en MENU.
     * Este método se llama una vez al inicio de la aplicación.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("images/noche.jpg");
        font = new BitmapFont();
        gameLogo = new Texture("images/space.jpg");
        gameState = GameState.MENU;
        gameStateChanged = false;
        score = 0;
        asteroidsDestroyed = 0;
        gameTime = 0;
        Texture playerTexture = new Texture("images/red.png");
        float x = (Gdx.graphics.getWidth() - 64) / 2.0f;
        player = new Player(playerTexture, x, 50, 64, 64, 5f);
        obstacles = new Array<>();
        obstacleTexture = new Texture("images/cerdo.png");
        spawnTimer = 0;
        bulletTexture = new Texture("images/bala.jpg");
        backgroundMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/angry-birds.mp3"));
        backgroundMenuMusic.setLooping(true);
        backgroundMenuMusic.setVolume(0.5f);
        backgroundGameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/battle.mp3"));
        backgroundGameMusic.setLooping(true);
        backgroundGameMusic.setVolume(1.5f);
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/muerteCerdo.mp3"));
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel = new Texture(pixmap);
        pixmap.dispose();
    }

    /**
     * Método principal de renderizado del juego. Se ejecuta en cada frame.
     * Limpia la pantalla, actualiza objetos, gestiona inputs y renderiza el estado actual.
     * También maneja las transiciones entre estados del juego.
     */
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        actualizarObjetos();
        gestionarInputs();
        batch.begin();
        representacionEstado();
        batch.end();

        if (gameStateChanged) {
            if (nextGameState == GameState.MENU && gameState == GameState.GAME_OVER) {
                obstacles.clear();
                backgroundGameMusic.stop();
                backgroundMenuMusic.play();
                score = 0;
                asteroidsDestroyed = 0;
                gameTime = 0;
                spawnTimer = 0;
            }

            if (nextGameState == GameState.PLAYING && gameState == GameState.MENU) {
                backgroundMenuMusic.pause();
                backgroundGameMusic.play();
                player.activate();
                gameTime = 0;
                spawnTimer = 0;
                obstacles.clear();
            }

            gameState = nextGameState;
            gameStateChanged = false;
        }
    }

    /**
     * Gestiona las entradas del usuario según el estado actual del juego.
     * Maneja controles táctiles, de teclado y ratón para menú, juego y game over.
     * Define las acciones para cada estado y tecla específica.
     */
    private void gestionarInputs() {
        switch (gameState) {
            case MENU:
                if (Gdx.input.isTouched()) {
                    nextGameState = GameState.PLAYING;
                    gameStateChanged = true;
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    nextGameState = GameState.PLAYING;
                    gameStateChanged = true;
                }
                break;

            case PLAYING:
                if (Gdx.input.isTouched()) {
                    int DEAD_ZONE = 5;
                    float deltaX = Gdx.input.getX() - (player.getX() + player.getWidth() / 2);
                    if (deltaX > DEAD_ZONE) player.moveRight();
                    else if (deltaX < Math.negateExact(DEAD_ZONE)) player.moveLeft();
                    float invertedY = Gdx.graphics.getHeight() - Gdx.input.getY();
                    float deltaY = invertedY - (player.getY() + player.getHeight() / 2);
                    if (deltaY > DEAD_ZONE) player.moveUp();
                    else if (deltaY < Math.negateExact(DEAD_ZONE)) player.moveDown();
                }

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    player.moveLeft();
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    player.moveRight();
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    player.moveUp();
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    player.moveDown();
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    player.shoot();
                    flashActive = true;
                    flashTimer = 0.1f;
                }

                if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                    player.shoot();
                    flashActive = true;
                    flashTimer = 0.1f;
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    calcularPuntuacionFinal();
                    scoreScreen = new ScoreScreen(score);
                    setScreen(scoreScreen);
                    showScoreScreen = true;
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    nextGameState = GameState.GAME_OVER;
                    gameStateChanged = true;
                }
                break;

            case GAME_OVER:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    backgroundGameMusic.stop();
                    nextGameState = GameState.MENU;
                    gameStateChanged = true;
                    showScoreScreen = true;
                    gamePaused = true;
                }
                break;
        }
    }

    /**
     * Actualiza la lógica del juego: tiempo, jugador, obstáculos, colisiones y efectos.
     * Solo actualiza la generación de obstáculos cuando el juego está en estado PLAYING.
     */
    private void actualizarObjetos() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (gameState == GameState.PLAYING) {
            gameTime += deltaTime;

            spawnTimer += deltaTime;
            if (spawnTimer > 0.5f) {
                addObstacle();
                spawnTimer = 0;
            }
        }

        player.update(deltaTime);

        for (int i = obstacles.size - 1; i >= 0; i--) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.update();

            if (obstacle.isOutOfScreen()) {
                obstacles.removeIndex(i);
            }
        }

        detectarColisionesBalasAsteroides();

        if (flashActive) {
            flashTimer -= deltaTime;
            if (flashTimer <= 0) {
                flashActive = false;
            }
        }
    }

    /**
     * Detecta colisiones entre balas del jugador y asteroides.
     * Cuando una bala golpea un asteroide, destruye ambos objetos,
     * reproduce sonido de explosión y aumenta el contador de asteroides destruidos.
     */
    private void detectarColisionesBalasAsteroides() {
        Array<Bullet> bullets = player.getBullets();

        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);

            for (int j = obstacles.size - 1; j >= 0; j--) {
                Obstacle obstacle = obstacles.get(j);

                if (bullet.getBounds().overlaps(obstacle.getBounds())) {
                    obstacle.destroy();
                    explosionSound.play(0.7f);
                    asteroidsDestroyed++;
                    bullet.setActive(false);
                    bullets.removeIndex(i);
                    break;
                }
            }
        }
    }

    /**
     * Calcula la puntuación final del juego.
     * La puntuación se compone de: 1 punto por segundo jugado + 10 puntos por asteroide destruido.
     */
    private void calcularPuntuacionFinal() {
        int tiempoPuntos = (int) gameTime;
        int asteroidesPuntos = asteroidsDestroyed * 10;
        score = tiempoPuntos + asteroidesPuntos;
    }

    /**
     * Genera un nuevo obstáculo (asteroide) en una posición aleatoria en la parte superior de la pantalla.
     * Los obstáculos se generan con tamaño y velocidad predefinidos.
     */
    private void addObstacle() {
        float width = 62;
        float height = 48;
        float x = MathUtils.random(0, Gdx.graphics.getWidth() - width);
        float y = Gdx.graphics.getHeight();
        obstacles.add(new Obstacle(obstacleTexture, x, y, width, height, 3f));
    }

    /**
     * Renderiza el estado actual del juego: menú, juego en curso o pantalla de game over.
     * Incluye todos los elementos gráficos: fondo, jugador, obstáculos, texto de información y efectos.
     *
     * @see GameState
     */
    private void representacionEstado() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        switch (gameState) {
            case MENU:
                batch.draw(gameLogo, 0, 0, screenWidth, screenHeight);
                font.draw(batch, "Pulsa ESPACIO o ENTER para comenzar", 100, 100);
                font.draw(batch, "Controles: FLECHAS para mover, ESPACIO para disparar", 50, 50);
                font.draw(batch, "ENTER para ver puntuación durante el juego", 50, 30);
                backgroundMenuMusic.play();
                break;

            case PLAYING:
                batch.draw(image, 0, 0, screenWidth, screenHeight);

                if (flashActive) {
                    batch.setColor(1, 1, 1, 0.6f);
                    batch.draw(whitePixel, 0, 0, screenWidth, screenHeight);
                    batch.setColor(1, 1, 1, 1f);
                }

                font.setColor(Color.WHITE);
                font.draw(batch, "Pulsa ESC para terminar", 10, screenHeight - 10);
                font.draw(batch, "ENTER para ver puntuación", 10, screenHeight - 30);
                font.setColor(Color.YELLOW);
                font.draw(batch, "Tiempo: " + (int) gameTime + "s", screenWidth - 150, screenHeight - 10);
                font.draw(batch, "Asteroides: " + asteroidsDestroyed, screenWidth - 150, screenHeight - 30);
                int puntuacionActual = (int) gameTime + (asteroidsDestroyed * 10);
                font.draw(batch, "Puntos: " + puntuacionActual, screenWidth - 150, screenHeight - 50);
                font.setColor(Color.RED);
                font.draw(batch, "Jugando...", (float) (screenWidth * 0.45), 100);
                player.render(batch);

                for (int i = obstacles.size - 1; i >= 0; i--) {
                    Obstacle obstacle = obstacles.get(i);
                    obstacle.render(batch);
                }

                for (Obstacle obstacle : obstacles) {
                    if (obstacle.getBounds().overlaps(player.getBounds())) {
                        gameState = GameState.GAME_OVER;
                        gameStateChanged = false;
                        calcularPuntuacionFinal();
                        break;
                    }
                }

                if (showScoreScreen) {
                    backgroundGameMusic.pause();
                    if (scoreScreen != null && scoreScreen.isShowScoreScreen()) {
                        scoreScreen.render(Gdx.graphics.getDeltaTime());
                    } else {
                        showScoreScreen = false;
                        gamePaused = false;
                        scoreScreen = null;
                        setScreen(null);
                        if (gameState == GameState.PLAYING) {
                            backgroundGameMusic.play();
                        }
                    }
                }
                break;

            case GAME_OVER:
                ScreenUtils.clear(Color.RED);
                font.setColor(Color.YELLOW);
                font.draw(batch, "GAME OVER", screenWidth / 2 - 50, screenHeight / 2 + 50);
                font.draw(batch, "Puntuación final: " + score, screenWidth / 2 - 70, screenHeight / 2);
                font.draw(batch, "Pulsa ENTER para volver al menú", screenWidth / 2 - 120, screenHeight / 2 - 50);
                break;
        }
    }

    /**
     * Libera todos los recursos del juego cuando se cierra la aplicación.
     * Este método debe llamarse al finalizar la aplicación para evitar fugas de memoria.
     */
    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        font.dispose();
        player.dispose();
        obstacleTexture.dispose();
        backgroundMenuMusic.dispose();
        backgroundGameMusic.dispose();
        whitePixel.dispose();
        explosionSound.dispose();
        bulletTexture.dispose();
    }
}
