
# FirstPlotApp.java

Primer ejemplo introductorio del framework **Open Source Physics (OSP)**, diseñado para mostrar cómo crear gráficos científicos simples de manera rápida y elegante.

## Propósito del código

**`FirstPlotApp`** genera un gráfico de la función **seno** (sin(x)) en el intervalo [-10, 10], demostrando las capacidades básicas de visualización científica de OSP.

***

## Análisis del código

### Estructura principal
```java
PlotFrame frame = new PlotFrame("position", "amplitude", "First Plot");
```
- Crea un marco de gráfico con **etiquetas personalizadas** para ejes X ("position") e Y ("amplitude")
- Título: "First Plot"

### Bucle de generación de datos
```java
for(double x = -10, dx = 0.1; x<10; x += dx) {
  frame.append(0, x, Math.sin(x));
}
```
- **Dataset 0**: Identificador del conjunto de datos
- **x**: desde -10 hasta 10 con paso 0.1 (201 puntos)
- **y = sin(x)**: función seno clásica

### Configuración final
```java
frame.setSize(400, 400);
frame.setVisible(true);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
```

***

## Características de OSP destacadas

| Componente | Función | Ventaja |
|------------|---------|---------|
| `PlotFrame` | Marco listo para gráficos científicos | Ejes automáticos, etiquetas, escalas |
| `append(0, x, y)` | Añadir puntos a dataset | Automático: conexión de puntos, colores |
| Tamaño fijo | Ventana 400x400 px | Consistente para tutoriales |

***

## Salida visual esperada

```
Gráfico de sin(x):
- Eje X: "position" (-10 → 10)
- Eje Y: "amplitude" (-1 → 1)
- Curva suave del seno con 201 puntos
- Ventana cuadrada optimizada
```

## Contexto educativo

Este ejemplo **mínimo viable** enseña:
1. **Importación OSP**: `org.opensourcephysics.frames.*`
2. **Creación instantánea** de gráficos científicos
3. **Sintaxis simple** para datos continuos
4. **Licencia GPL** obligatoria para derivados

**Ideal para**: Introducción a OSP, prototipado rápido de funciones matemáticas, enseñanza de visualización científica.

Es el "Hello World" de la física computacional con Java.
