# ACTIVIDAD 2.3 Base de datos de frases motivadoras

**Ciclo Formativo de Grado Superior**  
**Desarrollo de Aplicaciones Multiplataforma**  
**Programación Multimedia y Dispositivos Móviles**

---

## 1. Objetivo

Demostrar conocimientos sobre desarrollo de aplicaciones para dispositivos móviles, considerando los siguientes aspectos:

- Utilizar las clases necesarias para la conexión y comunicación con dispositivos inalámbricos (RA2.c).
- Desarrollar aplicaciones que hacen uso de las funcionalidades proporcionadas por los sensores (RA2.d).
- Utilizar las clases necesarias para establecer conexiones y comunicaciones HTTP y HTTPS (RA2.e).
- Utilizar las clases necesarias para establecer conexiones con almacenes de datos garantizando la persistencia (RA2.f).
- Empaquetar y desplegar las aplicaciones desarrolladas en dispositivos móviles reales (RA2.h).
- Documentar los procesos necesarios para el desarrollo de las aplicaciones (RA2.i).

---

## 2. Enunciado

**En pareja**, realizad la siguiente tarea competencial evaluable para demostrar un conocimiento básico en relación con "Aplicaciones Móviles Android":

1. Cread una aplicación para Android, con una interfaz similar (no se exigirá que sea idéntica) que permita:

   - **a)** Cuando se pulse el botón **"Descargar Nueva Frase"**:
     - i) Hacer una petición HTTP a la URL sin bloquear el hilo principal: `https://zenquotes.io/api/random`
     - ii) Recibir una frase en formato JSON en cada llamada a dicha URL.
     - iii) Almacenar la frase en una base de datos local SQLite.
     - iv) Actualizar el historial de frases almacenadas en un RecyclerView (solo cuando se haya pulsado el botón "Mostrar Frases").

   - **b)** Cuando se pulse el botón **"Mostrar Frases"**:
     - i) Mostrar en el RecyclerView las frases almacenadas en la base de datos local SQLite.
     - ii) Modificar el texto del botón para que muestre "Ocultar Frases".
     - iii) Cuando se pulse el botón con el texto "Ocultar Frases" se deberá ocultar la información del RecyclerView y modificar el texto del botón para que muestre de nuevo "Mostrar Frases".

   - **c)** Cuando se pulse el botón **"Eliminar Frases"**:
     - i) Eliminar las frases de la base de datos SQLite.
     - ii) Actualizar el RecyclerView para que no muestre ninguna frase.

2. Se propone una estructura de clases como la siguiente (no es obligatoria):

   ```
   com.pmm.a23
   ├── data/
   │   ├── Frase
   │   ├── FrasesDAO
   │   └── FrasesDBHelper
   ├── net/
   │   └── HttpUtils
   └── ui/
       ├── FrasesAdapter
       └── MainActivity
   ```

3. Exportad en un `.zip` el proyecto de Android Studio y subidlo al aula virtual con el siguiente nombre:  
   `DAM2_PMM_UT2_Actividad3_Nombres-alumnos`

---

## 3. Evaluación

- La evaluación se realizará conforme a la siguiente lista de cotejo.
- La presentación de trabajos **no genuinos o plagiados** conllevará una calificación de **0 puntos** en la actividad para todos los alumnos involucrados.
- La actividad deberá entregarse antes de la fecha de cierre configurada en el aula virtual para ser evaluada. De no ser así, se puntuará con un 0. Se reabrirá el plazo de entrega para las convocatorias de Ordinaria y Extraordinaria para aquellos alumnos que deban presentarse a ellas.
- Tras la entrega, el docente podrá realizar preguntas a los alumnos para evaluar su grado de contribución a la actividad.

---

## Lista de Cotejo – Base de datos de frases motivadoras

**Nombre del alumno/grupo:** ___________________________  
**Fecha:** ___________________________

| Criterio de evaluación | Sí | No | Observaciones |
|---|---|---|---|
| 1. **(1 punto)** Muestra todos los botones y componentes solicitados. | ☐ | ☐ | |
| 2. **(1 punto)** Se asignan los permisos necesarios para establecer conexiones y comunicaciones. | ☐ | ☐ | |
| 3. **(2 puntos)** Realiza la descarga de datos a través de conexión HTTP/HTTPS sin bloquear la aplicación. | ☐ | ☐ | |
| 4. **(1 punto)** Procesa adecuadamente el formato JSON de los objetos descargados. | ☐ | ☐ | |
| 5. **(2 puntos)** Realiza correctamente la persistencia de datos, incluyendo la creación de la base de datos, la actualización de los datos y su eliminación. | ☐ | ☐ | |
| 6. **(1 punto)** Muestra y oculta correctamente la información en el RecyclerView conforme a lo requerido. | ☐ | ☐ | |
| 7. **(1 punto)** El despliegue y ejecución de la aplicación se realiza sin excepciones o errores. | ☐ | ☐ | |
| 8. **(1 punto)** Documentar la aplicación mediante comentarios en el código. | ☐ | ☐ | |

**Puntuación máxima: 10 puntos.**
