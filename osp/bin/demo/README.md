# Demo de OSP

Esta carpeta contiene ejemplos sencillos y educativos de cómo usar la biblioteca Open Source Physics (OSP) desde Java.

## Qué contiene

La carpeta `demo` incluye programas pequeños que ilustran el uso de:

- `PlotFrame` para graficar datos
- `AbstractSimulation` para crear simulaciones dinámicas
- `SimulationControl` para controlar parámetros
- visualización 2D dentro del framework OSP

Los ejemplos típicos son:

- `FirstPlotApp.java`: graficación simple de una función
- `SecondPlotApp.java`: ejemplo adicional de trazado
- `OsciladorSimple.java`: simulación de un oscilador armónico
- `OsciladorConControl.java`: simulación con control de parámetros

---

## Estructura de la carpeta

```text
osp/src/demo/
├── FirstPlotApp.java
├── HelloWorldApp.java
├── SecondPlotApp.java
├── OsciladorSimple.java
├── OsciladorConControl.java
└── README.md
```

---

## Cómo compilar los ejemplos

Desde la raíz del proyecto `osp`:

```bash
cd /home/jntorresr/gitHub/workspace_compadre/osp
ant -f ant/build_core.xml compile
```

Esto compila todo el código fuente del proyecto y deja las clases generadas en la salida de compilación del build.

Si solo quieres compilar los ejemplos manualmente, puedes usar `javac` con el classpath adecuado, aunque el flujo recomendado del repositorio es usar Ant.

---

## Cómo ejecutar un ejemplo

### Opción 1: desde la línea de comandos

Desde la raíz del proyecto:

```bash
cd /home/jntorresr/gitHub/workspace_compadre/osp
java -cp build/classes demo.FirstPlotApp
```

Para otros ejemplos:

```bash
java -cp build/classes demo.SecondPlotApp
java -cp build/classes demo.OsciladorSimple
java -cp build/classes demo.OsciladorConControl
```

> El classpath debe apuntar a la salida compilada generada por Ant, normalmente la carpeta de clases del proyecto.

### Opción 2: desde VS Code

1. Abre la carpeta del proyecto `osp` en VS Code.
2. Asegúrate de tener Java Extension Pack instalado.
3. Abre el archivo del ejemplo que quieras ejecutar, por ejemplo `src/demo/FirstPlotApp.java`.
4. En la barra superior, usa el botón de Run Java o pulsa `F5`.
5. Si VS Code no detecta el proyecto, asegúrate de abrir la carpeta raíz `osp` y no solo la subcarpeta `src`.
6. Si aparece un “classpath” o “main class” no configurado, usa la opción “Run Java File” del archivo que has abierto.

---

## Cómo funciona un ejemplo típico

Un ejemplo de OSP suele seguir este patrón:

1. crear un `PlotFrame` o una ventana gráfica
2. definir el rango de ejes
3. agregar puntos con `append(...)`
4. mostrar la ventana con `setVisible(true)`
5. cerrar la aplicación con `JFrame.EXIT_ON_CLOSE`

Ejemplo:

```java
PlotFrame frame = new PlotFrame("x", "y", "Mi gráfica");
frame.setSize(500, 400);
frame.setVisible(true);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

for (double x = -10; x <= 10; x += 0.1) {
  frame.append(0, x, Math.sin(x));
}
```

---

## Cómo usar las simulaciones

### 1. Ejecuta el archivo principal del ejemplo

Por ejemplo:

```bash
java -cp build/classes demo.OsciladorSimple
```

### 2. Observa la ventana gráfica

La simulación muestra un gráfico en tiempo real, con la evolución del sistema.

### 3. Ajusta parámetros

En los ejemplos con control (`OsciladorConControl`), el objeto `SimulationControl` permite modificar:

- posición inicial
- velocidad inicial
- masa
- constante elástica
- paso de integración (`dt`)

### 4. Reejecuta la simulación

Puedes reiniciar la simulación y volver a probar distintas condiciones iniciales.

---

## Compilar y ejecutar desde Visual Studio Code

### Requisitos

- Java JDK instalado
- VS Code con la extensión Java (Java Extension Pack)
- abrir la carpeta raíz `osp` como proyecto

### Pasos

1. Abre VS Code.
2. File > Open Folder...
3. Selecciona la carpeta `/home/jntorresr/gitHub/workspace_compadre/osp`.
4. Espera a que Java detecte la estructura del proyecto.
5. Abre un archivo de demo como `src/demo/FirstPlotApp.java`.
6. Haz clic en `Run Java File` o presiona `F5`.
7. Si el proyecto no se detecta, usa `Java: Clean Java Language Server Workspace` o vuelve a abrir la carpeta.
8. La ventana gráfica de la simulación se mostrará al ejecutar.

---

## Recomendaciones

- Usa la carpeta raíz `osp` como proyecto principal, no solo `src`.
- Mantén los nombres de archivo y clase coincidentes.
- Para ejemplos más complejos, sigue la organización del repositorio en `demo` o subpaquetes del paquete principal.
- Usa Ant como flujo principal de compilación, porque es el que usa el proyecto.

---

## Resumen

La carpeta `demo` es el espacio de pruebas y aprendizaje del proyecto OSP. Aquí se muestran ejemplos mínimos y didácticos que permiten:

- comprender la API de OSP
- visualizar resultados en tiempo real
- aprender a compilar y ejecutar proyectos Java desde la línea de comandos o desde VS Code
- construir simulaciones progresivas desde ejemplos simples hasta modelos más complejos

Si quieres, el siguiente paso podría ser dejar un ejemplo adicional en esta carpeta con una simulación de tipo `Movimiento rectilíneo` o `Sistema masa-resorte` completamente documentado paso a paso.
