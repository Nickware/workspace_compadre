# Open Source Physics (OSP)

Este directorio contiene el núcleo del proyecto Open Source Physics, una biblioteca Java para la simulación, visualización y análisis de fenómenos físicos en contexto educativo e investigativo.

La idea central es proporcionar un conjunto de herramientas para:

- modelar sistemas físicos mediante ecuaciones diferenciales
- visualizar resultados en 2D y 3D
- controlar parámetros de simulación desde interfaces gráficas
- guardar/recargar estados de simulación usando XML
- analizar datos experimentales y simulados

---

## Objetivo del proyecto

OSP no es una única aplicación, sino una plataforma de software abierto orientada a la física computacional. Está diseñada para apoyar la enseñanza y la investigación mediante simulaciones interactivas, gráficos dinámicos y análisis numérico.

El proyecto combina varios elementos:

- motor de simulación
- resolución numérica de ecuaciones diferenciales
- librerías gráficas para datos y campos
- herramientas de análisis y ajuste de datos
- recursos de interfaz y utilidades de laboratorio virtual

---

## Estructura principal

### `src/`
Es el código fuente del proyecto.

```text
src/
├── demo/
├── org/
│   └── opensourcephysics/
│       ├── analysis/
│       ├── controls/
│       ├── davidson/
│       ├── desktop/
│       ├── display/
│       ├── display2d/
│       ├── display3d/
│       ├── ejs/
│       ├── frames/
│       ├── media/
│       ├── numerics/
│       ├── resources/
│       └── tools/
```

### Subpaquetes clave

#### `analysis`
Contiene clases para analizar datos y señales:

- transformadas de Fourier
- ajuste de curvas
- estadística básica
- análisis de series temporales

#### `controls`
Es la capa de control y coordinación del sistema.

Aquí se definen mecanismos para:

- inicializar simulaciones
- arrancar/parar hilos de animación
- manejar índices de paso temporal
- configurar parámetros interactivos
- serializar objetos a XML

Clases destacadas:

- `AbstractSimulation`
- `Simulation`
- `Control`
- `XMLControl`
- `OSPApplication`

#### `numerics`
Es el núcleo matemático y numérico del proyecto.

Incluye:

- solvers de ecuaciones diferenciales ordinarias (ODE)
- métodos Runge-Kutta, Euler, Verlet, adaptativos, etc.
- funciones matemáticas y especiales
- álgebra lineal básica
- interpolación y ajuste

Clases centrales:

- `ODESolver`
- `RK4`
- `DormandPrince45`
- `Euler`
- `FFT`
- `Function`
- `Complex`

#### `display`, `display2d`, `display3d`
Estas carpetas implementan la capa visual.

- `display`: objetos gráficos 2D y elementos básicos de dibujo
- `display2d`: retículas, superficies, mapas de calor, contornos, vectores
- `display3d`: visualización espacial en tres dimensiones

El objetivo es representar el estado físico de manera intuitiva sin depender de un motor gráfico externo.

#### `tools`
Aquí residen utilidades, inspección visual de datos y herramientas auxiliares para análisis, exploración y manipulación de resultados.

#### `resources`
Contiene recursos de la aplicación, textos, cadenas localizadas y materiales auxiliares usados por la interfaz.

#### `media` y `desktop`
Suministran soporte para multimedia, UI de escritorio y componentes de interacción con ventanas, menús y contenido visual.

---

## Patrón arquitectónico

La arquitectura de OSP se basa en una separación clara entre:

1. modelo físico
2. motor numérico
3. control de parámetros
4. representación visual
5. serialización y análisis

Este enfoque permite crear simulaciones que ajustan su comportamiento de forma interactiva, manteniendo el cálculo separado de la capa de presentación.

En la práctica, una simulación típica sigue este flujo:

1. se inicializa el estado del sistema
2. se cargan parámetros desde un `Control`
3. se ejecuta un paso de integración numérica
4. se actualiza la representación gráfica
5. se puede guardar o exportar el estado del sistema

---

## Cómo se compila

El proyecto usa Ant para construir la biblioteca principal. La configuración está en:

- `ant/build_core.xml`

Los objetivos principales incluyen:

- `compile`: compila el código Java
- `jarfile`: genera el JAR principal
- `docs`: genera documentación Javadoc

La salida compilada se genera normalmente en una carpeta `build/`.

---

## Documentación técnica

La documentación del núcleo OSP está organizada en la carpeta `docs`:

- [docs/README.md](docs/README.md): índice de documentación
- [docs/carpeta-raiz-osp.md](docs/carpeta-raiz-osp.md): explicación de las carpetas principales del proyecto (`ant`, `src`, `bin`, `docs`, `org`)
- [docs/paquetes-osp.md](docs/paquetes-osp.md): mapa exacto de paquetes y responsabilidades
- [docs/flujo-simulacion.md](docs/flujo-simulacion.md): flujo de ejecución desde el arranque hasta el paso de integración
- [docs/como-construir-simulacion.md](docs/como-construir-simulacion.md): guía práctica para crear una simulación OSP desde cero
- [docs/contenido-portable-launchbuilder.md](docs/contenido-portable-launchbuilder.md): creación y empaquetado portable de contenido HTML con OSP LaunchBuilder/Launcher

---

## Ejemplos y demos

Además del código base, la carpeta `src/demo` incluye ejemplos pequeños para explorar cómo se usa la librería en práctica. Estos demos sirven como referencia para enfermar los conceptos de simulación y librerías gráficas.

---

## Licencia

El proyecto usa licencia GPL, como se indica en los archivos fuente. Esto refleja su naturaleza de software libre y abierto.

---

## Resumen técnico

La carpeta OSP es el núcleo de un framework para simulaciones físicas interactivas en Java. Está organizada como una librería científica modular, con una fuerte división entre:

- cálculo numérico
- control del modelo
- representación gráfica
- análisis de datos
- serialización de estado

Es una base ideal para herramientas educativas, laboratorios virtuales y simulaciones científicas accesibles.

---

## En una línea

OSP es un motor académico de física computacional: un conjunto de librerías Java para simular, visualizar y analizar sistemas físicos de manera interactiva y reutilizable.
