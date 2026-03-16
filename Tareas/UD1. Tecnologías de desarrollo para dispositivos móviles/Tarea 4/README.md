# ACTIVIDAD 1.4 Entendiendo y modificando una App de Android

**Ciclo Formativo de Grado Superior**  
**Desarrollo de Aplicaciones Multiplataforma**  
**Programación Multimedia y Dispositivos Móviles**

---

## 1. Objetivo

Demostrar conocimientos sobre tecnologías de desarrollo para dispositivos móviles, sus características y capacidades, considerando los siguientes aspectos:

- Análisis de la estructura de aplicaciones existentes para dispositivos móviles identificando las clases utilizadas (RA1.f).
- Realización de modificaciones sobre aplicaciones existentes (RA1.g).
- Uso de emuladores para comprobar el funcionamiento de las aplicaciones (RA1.h).

---

## 2. Enunciado

**Individualmente**, realizad la siguiente tarea competencial evaluable partiendo de la aplicación para Android **PuzleBotones** proporcionada por el profesor como parte de la actividad:

### Apartado 1 – Análisis de la aplicación

Analizad la aplicación y responded en un documento de texto a las siguientes cuestiones:

1.1. ¿Qué archivo, y dónde, define el nombre y el icono con el que la aplicación aparece en el emulador o dispositivo?

1.2. ¿Qué clase contiene el código que controla la lógica de la aplicación y responde a los clics de los botones?

1.3. ¿En qué carpeta se encuentran los layouts definidos en XML para la interfaz de usuario?

1.4. ¿Cómo se llama el archivo donde se guardan los textos visibles para el usuario (como el nombre de la app o los textos de botones), en lugar de escribirlos directamente en el código?

1.5. ¿Dónde se almacenan los colores definidos para reutilizarlos en toda la aplicación?

1.6. ¿En qué carpeta se guardan los iconos de la aplicación (`ic_launcher`)?

1.7. ¿Qué dos etiquetas en el `AndroidManifest.xml` indican cuál es la actividad principal que se abre al ejecutar la app?

1.8. ¿Qué archivo de Gradle gestiona las dependencias externas que puede necesitar la aplicación?

1.9. Si quieres añadir un nuevo botón en la interfaz mediante XML, ¿en qué archivo lo harías?

1.10. ¿Qué diferencia hay entre la carpeta `java/` y la carpeta `res/` dentro de un proyecto Android?

### Apartado 2 – Modificaciones en la aplicación

Introducid las siguientes modificaciones en la aplicación:

2.1. Modificad el icono de la instalación por otro a vuestra elección que se visualice correctamente.

2.2. Modificad el texto que se muestra en el título de la actividad/ventana añadiendo en primer lugar vuestras iniciales.

2.3. Modificar el color original utilizado como recurso por un valor YELLOW con tono amarillo.

2.4. Cambiar el color de todos los botones que conforman el puzle cuando se logra una victoria, por un color RED también definido como recurso.

2.5. Modificar el mensaje mostrado cuando se logra poner todos los botones en el color secundario diciendo **"¡Lo logré!"**.

### Apartado 3 – Emuladores

Generad dos dispositivos emuladores de las siguientes características y ejecutad la aplicación modificada en cada uno de ellos tomando capturas e incluyéndolas en el documento de texto:

3.1. Pixel 4 – API 29

3.2. Pixel Fold – API 36

### Entrega

Generad un fichero `.zip` con el contenido del proyecto y subidlo al aula virtual, junto con el documento de texto, ambos con el nombre de fichero:  
`DAM2_PMM_UT1_Actividad4_Nombre-alumno`

---

## 3. Evaluación

- La evaluación se realizará conforme a la siguiente lista de cotejo.
- La presentación de trabajos **no genuinos o plagiados** conllevará una calificación de **0 puntos** en la actividad para todos los alumnos involucrados.
- La actividad deberá entregarse antes de la fecha de cierre configurada en el aula virtual para ser evaluada. De no ser así, se puntuará con un 0. Se reabrirá el plazo de entrega para las convocatorias de Ordinaria y Extraordinaria para aquellos alumnos que deban presentarse a ellas.
- Tras la entrega, el docente podrá realizar preguntas a los alumnos para evaluar su grado de contribución a la actividad.

---

## Lista de Cotejo – Modificación de App de Android

| Criterio de evaluación | Sí | No | Observaciones |
|---|---|---|---|
| 1. **(2 puntos)** Ha respondido correctamente a al menos 5 de las preguntas del apartado primero. | ☐ | ☐ | |
| 2. **(1 punto)** Ha respondido correctamente a otras 3 preguntas además de las anteriores. | ☐ | ☐ | |
| 3. **(1 punto)** Ha respondido correctamente a las 2 preguntas restantes además de las anteriores. | ☐ | ☐ | |
| 4. **(2 puntos)** Ha introducido correctamente al menos 2 de las modificaciones del apartado segundo. | ☐ | ☐ | |
| 5. **(2 puntos)** Ha introducido correctamente las 2 modificaciones restantes además de las anteriores. | ☐ | ☐ | |
| 6. **(2 puntos)** Se han generado los 2 emuladores y se ha ejecutado correctamente la aplicación modificada en ambos. | ☐ | ☐ | |

**Puntuación máxima: 10 puntos.**
