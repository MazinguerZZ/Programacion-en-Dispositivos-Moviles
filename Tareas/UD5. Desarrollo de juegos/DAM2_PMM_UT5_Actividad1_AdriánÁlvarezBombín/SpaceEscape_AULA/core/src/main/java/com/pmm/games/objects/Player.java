package com.pmm.games.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Representa al jugador controlado en el juego.
 * El jugador puede moverse en todas las direcciones dentro de los límites de la pantalla,
 * disparar balas y colisionar con obstáculos.
 * Mantiene un sistema de disparo con tiempo de recarga y gestiona todas las balas activas.
 *
 * @author juegos
 */
public class Player {

    private float x;
    private float y;
    private float width;
    private float height;
    private float speed;
    private Texture texture;
    private final Rectangle bounds;
    private Sound engineSound;
    private Sound shootSound;
    private Texture bulletTexture;
    private Array<Bullet> bullets;
    private float shootCooldown;
    private float shootTimer;

    /**
     * Constructor para crear al jugador.
     *
     * @param texture Textura gráfica del jugador.
     * @param x Posición horizontal inicial.
     * @param y Posición vertical inicial.
     * @param width Ancho visual del jugador.
     * @param height Alto visual del jugador.
     * @param speed Velocidad de movimiento en píxeles por frame.
     */
    public Player(Texture texture, float x, float y, float width, float height, float speed) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;

        bounds = new Rectangle(
            x + width / 8,
            y + height / 8,
            width - width / 4,
            height - height / 4);

        engineSound = Gdx.audio.newSound(Gdx.files.internal("sounds/tension-drones.mp3"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/piuw.mp3"));
        bulletTexture = new Texture("images/bala.jpg");
        bullets = new Array<>();
        shootCooldown = 0.3f;
        shootTimer = 0;
    }

    /**
     * Dibuja al jugador y todas sus balas activas.
     *
     * @param batch SpriteBatch donde se renderizará la textura del jugador.
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
    }

    /**
     * Actualiza la lógica del jugador y todas sus balas.
     *
     * @param deltaTime Tiempo transcurrido desde el último frame en segundos.
     */
    public void update(float deltaTime) {
        if (shootTimer > 0) {
            shootTimer -= deltaTime;
        }

        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update();

            if (!bullet.isActive()) {
                bullets.removeIndex(i);
            }
        }
    }

    /**
     * Intenta disparar una nueva bala si no está en tiempo de recarga.
     * Crea una bala en la posición del jugador, reproduce sonido de disparo
     * y reinicia el temporizador de recarga.
     */
    public void shoot() {
        if (shootTimer <= 0) {
            float bulletX = x + width / 2 - 5;
            float bulletY = y + height;
            float bulletWidth = 10;
            float bulletHeight = 20;
            float bulletSpeed = 10f;

            Bullet newBullet = new Bullet(bulletTexture, bulletX, bulletY,
                bulletWidth, bulletHeight, bulletSpeed);
            bullets.add(newBullet);

            shootSound.play(0.5f);
            shootTimer = shootCooldown;
        }
    }

    /**
     * Obtiene todas las balas activas del jugador.
     *
     * @return Array con todas las balas activas.
     */
    public Array<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Mueve al jugador hacia la izquierda, manteniéndolo dentro de los límites de la pantalla.
     */
    public void moveLeft() {
        x -= speed;
        float limit = 0;
        if (x < limit) x = limit;
        updateBounds();
    }

    /**
     * Mueve al jugador hacia la derecha, manteniéndolo dentro de los límites de la pantalla.
     */
    public void moveRight() {
        x += speed;
        float limit = Gdx.graphics.getWidth() - width;
        if (x > limit) x = limit;
        updateBounds();
    }

    /**
     * Mueve al jugador hacia arriba, manteniéndolo dentro de los límites de la pantalla.
     */
    public void moveUp() {
        y += speed;
        float limit = Gdx.graphics.getHeight() - height;
        if (y > limit) y = limit;
        updateBounds();
    }

    /**
     * Mueve al jugador hacia abajo, manteniéndolo dentro de los límites de la pantalla.
     */
    public void moveDown() {
        y -= speed;
        float limit = 0;
        if (y < limit) y = limit;
        updateBounds();
    }

    /**
     * Actualiza la posición del rectángulo de colisión del jugador.
     */
    private void updateBounds() {
        bounds.setPosition(
            x + width / 8,
            y + height / 8);
    }

    /**
     * Libera todos los recursos asociados al jugador.
     * Incluye texturas, sonidos y todas las balas activas.
     */
    public void dispose() {
        texture.dispose();
        engineSound.dispose();
        shootSound.dispose();
        bulletTexture.dispose();

        for (Bullet bullet : bullets) {
            bullet.dispose();
        }
        bullets.clear();
    }

    /**
     * Obtiene la posición horizontal actual del jugador.
     *
     * @return Coordenada X del jugador.
     */
    public float getX() {
        return x;
    }

    /**
     * Obtiene la posición vertical actual del jugador.
     *
     * @return Coordenada Y del jugador.
     */
    public float getY() {
        return y;
    }

    /**
     * Obtiene el rectángulo de colisión del jugador.
     * El rectángulo es más pequeño que el tamaño visual para un gameplay más justo.
     *
     * @return Rectángulo que representa los límites de colisión.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Activa el sonido del motor del jugador.
     * Se llama al comenzar el juego.
     */
    public void activate() {
        engineSound.play(1f);
    }

    /**
     * Obtiene el ancho visual del jugador.
     *
     * @return Ancho en píxeles.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Obtiene el alto visual del jugador.
     *
     * @return Alto en píxeles.
     */
    public float getHeight() {
        return height;
    }
}
