# Mapa exacto de paquetes de OSP

Este documento describe la estructura interna del núcleo de Open Source Physics (OSP), tal como aparece bajo `osp/src/org/opensourcephysics`.

## Estructura raíz

```text
osp/src/org/opensourcephysics/
├── analysis/
├── controls/
├── davidson/
├── desktop/
├── display/
├── display2d/
├── display3d/
├── ejs/
├── frames/
├── media/
├── numerics/
├── resources/
├── tools/
```

---

## 1) `analysis/`

Responsabilidad: análisis y tratamiento de datos científicos.

Incluye clases orientadas a:

- análisis de señales
- transformada de Fourier
- ajuste de curvas
- estadística de series temporales
- procesamiento de datos experimentales

Ejemplos de tipos de clases que aparecen ahí:

- `FourierAnalysis`
- `FourierSinCosAnalysis`
- clases relacionadas con ajuste y extracción de propiedades observables

Uso típico:

- leer datos de una simulación o experimento
- calcular frecuencias, tendencias, variaciones, ajustes
- preparar la información para su visualización o comparación teórica

---

## 2) `controls/`

Responsabilidad: control del ciclo de vida de la simulación y de la interfaz de configuración.

Es la capa de control central del framework. Aquí se encuentran los mecanismos para:

- manejar parámetros interactivos
- inicializar y resetear simulaciones
- iniciar/parar animaciones
- controlar paso a paso de la simulación
- serializar estados en XML
- definir interfaces de acceso a propiedades

Clases relevantes:

- `AbstractSimulation`
- `Simulation`
- `AbstractAnimation`
- `Control`
- `XMLControl`
- `XMLControlElement`
- `OSPApplication`
- `SimControl`
- `MainFrame`

Este paquete es el núcleo del patrón “modelo + control + UI”.

---

## 3) `davidson/`

Responsabilidad: integración con el entorno académico y de applets de Davidson / OSP.

Aquí se encuentran componentes más específicos para:

- applets
- infraestructura de demostración
- herramientas académicas ligadas al entorno de Davidson

Su papel es más especializado que los paquetes fundamentales del núcleo.

---

## 4) `desktop/`

Responsabilidad: soporte de la interfaz de escritorio y utilidades GUI.

Incluye:

- clases de gui para uso local
- soporte de navegación/edición/visualización en escritorio
- utilidades de ayuda para componentes interactivos

Contiene además subpaquetes como `desktop/ostermiller`, con utilidades varias de navegación o edición textual.

---

## 5) `display/`

Responsabilidad: representación gráfica 2D básica.

Este paquete contiene los elementos visuales más sencillos para dibujar sobre una ventana:

- ejes
- trazas
- puntos
- líneas
- animación visual de objetos físicos

Es la base para la visualización de sistemas dinámicos.

Clases típicas incluyen componentes de dibujo y renderización para objetos físicos y gráficos simples.

---

## 6) `display2d/`

Responsabilidad: visualización y análisis de datos sobre superficies y campos en 2D.

Aquí se implementan estructuras para:

- retículas
- campos escalares
- campos vectoriales
- contornos
- mapas de calor
- superficies discretas
- visualización de lattice/grid data

Clases representativas:

- `GridPlot`
- `ContourPlot`
- `VectorPlot`
- `InterpolatedPlot`
- `SurfacePlot`
- `ArrayData`
- `BinaryLattice`
- `CellLattice`
- `ColorMapper`

Este subpaquete es el principal responsable de mostrar datos físicos distribuidos en un plano.

---

## 7) `display3d/`

Responsabilidad: representación gráfica 3D.

Incluye clases para:

- proyección espacial
- objetos en 3 dimensiones
- superficies y volúmenes
- visualización de datos físicos tridimensionales

Es la extensión visual del sistema para modelar fenómenos con dimensión espacial 3D.

---

## 8) `ejs/`

Responsabilidad: integración con Easy Java Simulations (EJS).

Este paquete conecta OSP con el entorno de autoría EJS/EJSS, una parte clave del ecosistema OSP para crear simulaciones sin escribir mucho código a mano.

Es la pieza que permite crear simulaciones educativas más accesibles para docentes y estudiantes.

---

## 9) `frames/`

Responsabilidad: marcos y construcciones de ventanas/entorno de trabajo.

Contiene clases para:

- paneles y ventanas principales
- componentes de visualización embebidos
- infraestructura de presentación de contenido gráfico

Es una capa intermedia entre la lógica del modelo y la experiencia de usuario.

---

## 10) `media/`

Responsabilidad: soporte de contenido multimedia.

Incluye componentes para:

- imágenes
- video
- reproducción y procesamiento de medios
- recursos audiovisuales dentro de la aplicación

---

## 11) `numerics/`

Responsabilidad: motor matemático y numérico del proyecto.

Es probablemente el paquete más importante desde el punto de vista técnico. Aquí residen las implementaciones de:

- `ODE` y `ODESolver`
- métodos de integración numérica
- aproximación de funciones
- análisis lineal y algebraico
- interpolación
- FFT
- métodos especiales y funciones matemáticas

Clases claves:

- `ODESolver`
- `RK4`
- `Euler`
- `Verlet`
- `DormandPrince45`
- `FFT`
- `CubicSpline`
- `Interpolation`
- `Complex`
- `Vec3D`
- `VectorMath`

Este subpaquete permite que la simulación compute el estado físico del sistema a partir de ecuaciones y condiciones iniciales.

---

## 12) `resources/`

Responsabilidad: recursos de la aplicación y localización.

Contiene:

- archivos `.java` de recursos localizados
- textos de interfaz
- mensajes de ayuda
- recursos multimedia asociados a la app
- cadenas internacionales para distintos idiomas

Ejemplo: `video_es.java`, `launcher_en.java`, etc.

---

## 13) `tools/`

Responsabilidad: utilidades de análisis, inspección y acompañamiento del usuario.

Aquí se encuentran herramientas para:

- inspección de arrays y datos
- gráficos de funciones
- ajuste de curvas
- paneles de análisis
- navegación de datos
- herramientas de diagnóstico

Ejemplos de clases:

- `DataTool`
- `DatasetCurveFitter`
- `DataColumn`
- `ArrayInspector`
- `Diagnostics`

Este paquete hace que OSP sea útil no solo como librería de simulación, sino también como entorno de trabajo para experimentación y análisis.

---

## Relación entre paquetes

La relación general es la siguiente:

```text
controls
  ├─ define la vida de la simulación
  ├─ usa parámetros y estado
  └─ coordina el ciclo de animación

numerics
  ├─ resuelve ecuaciones diferenciales
  ├─ calcula nuevos estados
  └─ alimenta el modelo físico

display / display2d / display3d
  ├─ reciben el estado del sistema
  └─ renderizan visualmente el resultado

analysis / tools
  ├─ revisan y analizan datos
  └─ ayudan a interpretar resultados

resources
  └─ soporta la capa de interfaz y localización
```

---

## Resumen corto

La carpeta `org/opensourcephysics` está organizada como una plataforma modular de física computacional:

- `controls` = coordinación y control del sistema
- `numerics` = cálculo y resolución matemática
- `display*` = visualización 2D/3D
- `analysis` y `tools` = análisis y diagnóstico
- `resources` = textos y recursos de la interfaz
- `ejs` = integración con EJS
- `media` y `desktop` = soporte para aplicaciones de escritorio y multimedia

Es una arquitectura clásica de biblioteca científica orientada a educación y simulación interactiva.
