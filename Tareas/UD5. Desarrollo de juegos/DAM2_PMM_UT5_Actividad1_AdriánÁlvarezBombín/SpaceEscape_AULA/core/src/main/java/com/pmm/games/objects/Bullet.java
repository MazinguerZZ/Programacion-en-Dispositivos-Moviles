package com.pmm.games.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Representa una bala disparada por el jugador.
 * Las balas se mueven verticalmente hacia arriba y pueden colisionar con obstáculos.
 * Cada bala tiene un estado activo que determina si debe renderizarse y actualizarse.
 *
 * @author juegos
 */
public class Bullet {

    private float x;
    private float y;
    private float width;
    private float height;
    private float speed;
    private Texture texture;
    private final Rectangle bounds;
    private boolean active;

    /**
     * Constructor para crear una nueva bala.
     *
     * @param texture Textura gráfica de la bala.
     * @param x Posición horizontal inicial.
     * @param y Posición vertical inicial.
     * @param width Ancho de la bala.
     * @param height Alto de la bala.
     * @param speed Velocidad de movimiento en píxeles por frame (positiva hacia arriba).
     */
    public Bullet(Texture texture, float x, float y, float width, float height, float speed) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.active = true;

        bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Dibuja la bala en el batch proporcionado si está activa.
     *
     * @param batch SpriteBatch donde se renderizará la textura de la bala.
     */
    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(texture, x, y, width, height);
        }
    }

    /**
     * Actualiza la posición de la bala y su estado.
     * La bala se mueve hacia arriba según su velocidad.
     * Se desactiva automáticamente si sale por la parte superior de la pantalla.
     */
    public void update() {
        if (active) {
            y += speed;
            bounds.setPosition(x, y);

            if (y > com.badlogic.gdx.Gdx.graphics.getHeight()) {
                active = false;
            }
        }
    }

    /**
     * Verifica si la bala está activa y debe procesarse.
     *
     * @return true si la bala está activa, false en caso contrario.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Establece el estado activo de la bala.
     *
     * @param active Nuevo estado de la bala.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Obtiene el rectángulo de colisión de la bala.
     *
     * @return Rectángulo que representa los límites de colisión.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Obtiene la posición horizontal actual de la bala.
     *
     * @return Coordenada X de la bala.
     */
    public float getX() {
        return x;
    }

    /**
     * Obtiene la posición vertical actual de la bala.
     *
     * @return Coordenada Y de la bala.
     */
    public float getY() {
        return y;
    }

    /**
     * Libera los recursos gráficos asociados a esta bala.
     * Debe llamarse cuando la bala ya no sea necesaria.
     */
    public void dispose() {
        texture.dispose();
    }
}
