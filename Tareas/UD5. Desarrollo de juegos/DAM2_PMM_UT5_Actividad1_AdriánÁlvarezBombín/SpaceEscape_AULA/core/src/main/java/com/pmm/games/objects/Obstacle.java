package com.pmm.games.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Representa un obstáculo (asteroide) en el juego.
 * Los obstáculos se mueven desde la parte superior hacia abajo de la pantalla
 * y pueden ser destruidos por las balas del jugador.
 * Cada obstáculo tiene un rectángulo de colisión más pequeño que su tamaño visual
 * para proporcionar un área de colisión más justa.
 *
 * @author juegos
 */
public class Obstacle {

    private float x;
    private float y;
    private float width;
    private float height;
    private float speed;
    private Texture texture;
    private final Rectangle bounds;
    private boolean active;

    /**
     * Constructor para crear un nuevo obstáculo.
     *
     * @param texture Textura gráfica del obstáculo.
     * @param x Posición horizontal inicial.
     * @param y Posición vertical inicial (debe estar por encima de la pantalla).
     * @param width Ancho visual del obstáculo.
     * @param height Alto visual del obstáculo.
     * @param speed Velocidad de movimiento en píxeles por frame (positiva hacia abajo).
     */
    public Obstacle(Texture texture, float x, float y, float width, float height, float speed) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.active = true;

        bounds = new Rectangle(
            x + width / 8,
            y + height / 8,
            width - width / 4,
            height - height / 4);
    }

    /**
     * Dibuja el obstáculo en el batch proporcionado si está activo.
     *
     * @param batch SpriteBatch donde se renderizará la textura del obstáculo.
     */
    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(texture, x, y, width, height);
        }
    }

    /**
     * Actualiza la posición del obstáculo.
     * El obstáculo se mueve hacia abajo según su velocidad.
     * Actualiza también la posición del rectángulo de colisión.
     */
    public void update() {
        if (active) {
            y -= speed;
            bounds.setPosition(x + width / 8, y + height / 8);
        }
    }

    /**
     * Verifica si el obstáculo ha salido de la pantalla o ha sido destruido.
     *
     * @return true si el obstáculo está fuera de la pantalla por la parte inferior
     *         o si ha sido destruido, false en caso contrario.
     */
    public boolean isOutOfScreen() {
        return (y + height) < 0 || !active;
    }

    /**
     * Obtiene el rectángulo de colisión del obstáculo.
     * El rectángulo de colisión es más pequeño que el tamaño visual para
     * proporcionar un área de colisión más justa al jugador.
     *
     * @return Rectángulo que representa los límites de colisión.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Marca el obstáculo como destruido.
     * Un obstáculo destruido no se renderiza ni actualiza.
     */
    public void destroy() {
        this.active = false;
    }

    /**
     * Verifica si el obstáculo está activo (no destruido).
     *
     * @return true si el obstáculo está activo, false si ha sido destruido.
     */
    public boolean isActive() {
        return active;
    }
}
