package com.pmm.games;

/**
 * Enumeración que representa los diferentes estados del juego.
 * Cada estado define una fase específica del flujo del juego.
 *
 * MENU: Pantalla principal con opciones para comenzar.
 * PLAYING: Estado activo de juego donde el jugador controla la nave.
 * GAME_OVER: Pantalla que muestra el final del juego y la puntuación obtenida.
 *
 * @author juegos
 */
public enum GameState {

    /**
     * Estado de menú principal.
     * El jugador puede iniciar un nuevo juego desde este estado.
     */
    MENU,

    /**
     * Estado de juego activo.
     * El jugador controla la nave, dispara y evita obstáculos.
     */
    PLAYING,

    /**
     * Estado de juego terminado.
     * Muestra la puntuación final y opciones para volver al menú.
     */
    GAME_OVER
}
