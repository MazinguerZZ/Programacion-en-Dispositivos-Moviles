# Space Birds

Space Birds es un juego arcade 2D desarrollado con el framework libGDX. Este proyecto transforma un clásico juego de disparos espaciales en una experiencia divertida y temática inspirada en Angry Birds. En lugar de pilotar una nave espacial, controlas al icónico pájaro Rojo, con la misión de derribar a los molestos cerdos que caen del cielo.

El juego original, `SpaceEscape`, sirvió como base, pero ha sido completamente rediseñado con gráficos y sonidos personalizados para encajar en el universo de Angry Birds.

## Jugabilidad

El objetivo es sobrevivir el mayor tiempo posible mientras derribas cerdos que caen. El juego cuenta con tres estados: un menú principal, la pantalla de juego activo y una pantalla de fin de partida que muestra tu puntuación final.

- **Sobrevive:** Esquiva los cerdos que caen. Una sola colisión termina la partida.
- **Dispara:** Lanza proyectiles para destruir a los cerdos antes de que te alcancen.
- **Puntúa:** Tu puntuación es una combinación de tu tiempo de supervivencia y un bonus por cada cerdo que destruyas.

## Características

- **Temática Angry Birds:** Todos los recursos visuales, desde el jugador y los enemigos hasta los proyectiles, están reemplazados por personajes de Angry Birds. El audio también sigue la temática, con música clásica y efectos de sonido característicos.
- **Puntuación dinámica:** Gana 1 punto por cada segundo que sobrevivas y 10 puntos por cada cerdo destruido.
- **Efectos visuales:** Se produce un destello en pantalla con cada disparo, añadiendo impacto al juego.
- **Soporte multiplataforma:** Compatible con escritorio (Windows, macOS, Linux) y Android.
- **Múltiples esquemas de control:** Juega con teclado, ratón o pantalla táctil.

## Controles

### Escritorio

- **Movimiento:** Teclas de dirección (`↑`, `↓`, `←`, `→`)
- **Disparar:** `ESPACIO` o `Clic derecho del ratón`
- **Salir:** `ESC`

### Android

- **Movimiento:** Arrastra el dedo por la pantalla para mover al personaje.

## Cómo ejecutarlo

Este es un proyecto basado en Gradle. Puedes ejecutarlo directamente desde la línea de comandos sin necesidad de instalar un IDE.

### Requisitos previos

- Java Development Kit (JDK) 17 o superior.
- Android SDK (para compilar la versión Android).

### Ejecutar la versión de escritorio

1. Clona el repositorio en tu máquina local:
   ```sh
   git clone https://github.com/mazinguerzz/space_birds.git
   ```
2. Navega al subdirectorio del proyecto:
   ```sh
   cd space_birds/SpaceEscape_AULA
   ```
3. Ejecuta el juego con el wrapper de Gradle:
   - **En Windows:**
     ```sh
     gradlew.bat lwjgl3:run
     ```
   - **En macOS/Linux:**
     ```sh
     ./gradlew lwjgl3:run
     ```

## Estructura del proyecto

El repositorio está organizado en módulos estándar de libGDX:

- `core/`: Contiene la lógica principal del juego, independiente de la plataforma, incluyendo estados del juego, entidades (Jugador, Obstáculo, Bala) y pantallas.
- `lwjgl3/`: Lanzador de la aplicación de escritorio usando LWJGL3.
- `android/`: Lanzador de la aplicación Android y archivos específicos de la plataforma.
- `assets/`: Contiene todos los recursos del juego, incluyendo imágenes, música, efectos de sonido y skins de la interfaz.

## Créditos

- **Autores:** Adrián Álvarez y Alejandro Medina
- **Framework:** [libGDX](https://libgdx.com/)
