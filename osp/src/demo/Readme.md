# SecondPlotApp

SecondPlotApp is a simple Java application that demonstrates how to plot mathematical functions using the [Open Source Physics (OSP)](https://www.opensourcephysics.org/) library alongside Java Swing. This script visualizes the cosine function (`cos(x)`) across a defined range in a graphical window.

## Descripción

Este programa Java crea una ventana gráfica mediante `PlotFrame` (de OSP) para mostrar la función coseno en el intervalo [-10, 10]. Cada punto de la curva se agrega iterativamente, y la gráfica resultante se despliega en una interfaz sencilla y personalizable.

### Características principales

- Utilizar Java Swing para la gestión de la ventana.
- Recurrir a la biblioteca OSP para la creación fácil de plots matemáticos.
- Muestra la función $\cos(x)$ en un rango configurable.
- Interfaz sencilla, ideal para aprendizaje o presentaciones educativas.
- El gráfico contiene etiquetas personalizadas en los ejes y el título.

## Ejecución

1. Asegúrarse de tener configurada la biblioteca OSP en tu proyecto Java.
2. Compilar y ejecuta el archivo `SecondPlotApp.java`.
3. Se abrirá una ventana mostrando la gráfica de la función coseno.

## Mejoras Sugeridas y Aportes

Aunque este script es funcional, hay múltiples vías de mejora y personalización:

- **Configurabilidad**: Permitir que el usuario elija el rango de $x$, el incremento (`dx`), o incluso la función matemática a graficar (ej. usando interfaces gráficas o argumentos por consola).
- **Más funciones**: Incorporar la capacidad de graficar múltiples funciones o series de datos en la misma ventana.
- **Estilizado**: Mejorar la presentación visual, eligiendo paletas de colores, tipos de líneas o puntos, y estilos de ejes.
- **Exportación**: Agregar funcionalidades para guardar la gráfica generada como imagen (`.png`) o exportar los datos graficados.
- **Interactivo**: Habilitar zoom, desplazamiento o inspección de puntos en la gráfica de forma interactiva.
- **Documentación**: Incluir más comentarios en el código y ejemplos de uso para facilitar su comprensión y extensión.
- **Internacionalización**: Facilitar el cambio de idioma para las etiquetas y títulos desde parámetros externos o archivos de recursos.

¡Siéntasen libres de adaptar, mejorar y compartir este sencillo punto de partida para visualización matemática en Java!

---
**Contribuciones y mejoras son bienvenidas.** Si tienen correcciones o nuevas ideas, no duden en abrir una propuesta o enviar un comentario.
