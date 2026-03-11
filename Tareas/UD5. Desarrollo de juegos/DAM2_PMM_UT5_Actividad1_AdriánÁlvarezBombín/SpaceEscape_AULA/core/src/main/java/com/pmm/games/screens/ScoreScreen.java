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

/**
 * Pantalla que muestra la puntuación final del jugador.
 * Proporciona una interfaz con la puntuación obtenida y un botón para volver al menú principal.
 *
 * @author juegos
 */
public class ScoreScreen implements Screen {

    private Stage stage;
    private int score;
    private boolean showScoreScreen;
    private Table table;

    /**
     * Constructor de la pantalla de puntuación.
     *
     * @param score La puntuación final que se mostrará al jugador.
     */
    public ScoreScreen(int score) {
        this.score = score;
        this.showScoreScreen = true;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
    }

    /**
     * Inicializa y configura los elementos de la interfaz de usuario.
     * Crea un fondo semitransparente, muestra la puntuación y un botón para volver al menú.
     */
    @Override
    public void show() {
        int rectWidth = 300;
        int rectHeight = 150;
        Pixmap pixmap = new Pixmap(rectWidth, rectHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.6f, 0.6f, 0, 0.3f);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        Image background = new Image(texture);
        table.add(background).width(rectWidth).height(rectHeight).pad(10);

        Table innerTable = new Table();
        innerTable.setFillParent(true);
        table.addActor(innerTable);

        Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        Label scoreLabel = new Label("Tu puntuación: " + score, skin);
        scoreLabel.setFontScale(2f);

        innerTable.add(scoreLabel).center().padTop(20).row();

        TextButton menuButton = new TextButton("MENÚ", skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showScoreScreen = false;
            }
        });

        innerTable.add(menuButton).padTop(20).width(100).height(30);
    }

    /**
     * Renderiza la pantalla en cada frame.
     *
     * @param delta Tiempo transcurrido desde el último frame en segundos.
     */
    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    /**
     * Ajusta la interfaz cuando cambia el tamaño de la ventana.
     *
     * @param width  Nuevo ancho de la ventana.
     * @param height Nuevo alto de la ventana.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Se ejecuta cuando la aplicación pierde el foco (minimizada o en segundo plano).
     */
    @Override
    public void pause() {
        // No se requiere implementación específica
    }

    /**
     * Se ejecuta cuando la aplicación recupera el foco.
     */
    @Override
    public void resume() {
        // No se requiere implementación específica
    }

    /**
     * Se ejecuta cuando la pantalla se oculta pero no se destruye.
     */
    @Override
    public void hide() {
        // No se requiere implementación específica
    }

    /**
     * Libera todos los recursos utilizados por esta pantalla.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Indica si la pantalla de puntuación debe seguir mostrándose.
     *
     * @return true si la pantalla debe mostrarse, false si debe cerrarse.
     */
    public boolean isShowScoreScreen() {
        return showScoreScreen;
    }
}
