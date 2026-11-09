# Estructura de carpetas raíz de OSP

Este documento describe la función de cada carpeta principal del proyecto Open Source Physics en la raíz de `osp`.

## Vista general

```text
osp/
├── ant/
├── bin/
├── docs/
├── src/
├── .classpath
├── .project
├── README.md
├── osp.iml
├── osp.jardesc
└── ...
```

> Nota: la carpeta `org` no aparece como directorio raíz del proyecto; la organización de paquetes Java se encuentra dentro de `src/org/opensourcephysics` y, tras compilar, dentro de `bin/org/opensourcephysics`.

---

## 1) `ant/`

Responsabilidad: scripts de construcción del proyecto.

Esta carpeta guarda los archivos Ant que compilan, empaquetan y generan artefactos del proyecto.

Ejemplo clave:

- `ant/build_core.xml`

Qué hace:

- compilar el código fuente Java
- preparar las clases en una estructura de salida
- crear un JAR principal
- generar documentación Javadoc
- preparar la salida de build para distribuir la biblioteca

Es la capa que transforma el código fuente en artefactos ejecutables o reutilizables.

---

## 2) `src/`

Responsabilidad: código fuente del proyecto.

Es la carpeta principal de desarrollo. Aquí vive todo el software Java del núcleo OSP.

Estructura típica:

```text
src/
└── org/
    └── opensourcephysics/
        ├── analysis/
        ├── controls/
        ├── display/
        ├── display2d/
        ├── display3d/
        ├── numerics/
        ├── tools/
        └── ...
```

Qué contiene:

- clases de simulación
- solvers numéricos
- renderizadores gráficos
- utilidades de análisis
- recursos de la aplicación
- ejemplos y demos

Es la fuente real del framework. Todo lo que se compila parte de esta carpeta.

---

## 3) `bin/`

Responsabilidad: artefactos compilados y salida ejecutable.

La carpeta `bin` contiene los resultados de la compilación, es decir, las clases Java ya convertidas a bytecode, además de recursos y carpetas de distribución.

Estructura típica:

```text
bin/
└── org/
    └── opensourcephysics/
        ├── analysis/
        ├── controls/
        ├── display/
        └── ...
```

Qué puede encontrarse aquí:

- clases compiladas `.class`
- copias de recursos de la aplicación
- artefactos ejecutables generados para pruebas o uso directo
- ejemplos ya compilados

Es la salida que resulta de compilar `src`.

---

## 4) `org/` (como espacio de paquetes Java)

Aunque no es una carpeta raíz del proyecto, `org` es un espacio de paquetes fundamental dentro del árbol Java.

La convención de Java obliga a organizar las clases bajo un nombre de dominio invertido, y en OSP esto se ve así:

```text
src/org/opensourcephysics/...
```

Esto significa:

- `org` es el primer nivel del paquete
- `opensourcephysics` es el nombre del proyecto/a la organización del paquete
- luego vienen subpaquetes como `controls`, `numerics`, `display`, `tools`, etc.

En la salida compilada, el mismo patrón aparece bajo:

```text
bin/org/opensourcephysics/...
```

Es decir, la carpeta `org` no es “extra”; es el nombre lógico del espacio de paquetes del proyecto.

---

## 5) `docs/`

Responsabilidad: documentación técnica y conceptual del proyecto.

Esta carpeta agrupa material de ayuda para entender la arquitectura del sistema y el flujo de trabajo.

Incluye documentos como:

- mapa de paquetes
- explicación del flujo de simulación
- guía de cómo construir simulaciones
- referencias a la estructura del proyecto

Esto ayuda a que el proyecto se entienda sin necesidad de inspecionar todo el código fuente a la vez.

---

## 6) `README.md`

Responsabilidad: presentación general del proyecto.

Este archivo sirve como punto de entrada para entender:

- qué es OSP
- para qué sirve
- cómo está organizado
- qué documentación complementaria existe

Es la primera capa de documentación para cualquier usuario que entra al repositorio.

---

## 7) `osp.jardesc` y archivos de proyecto

Estos archivos son soporte de entorno de desarrollo.

- `osp.jardesc`: configuración del jar del proyecto
- `.project` y `.classpath`: configuración del proyecto para IDEs Java
- `osp.iml`: configuración de IntelliJ/IDEA

Su función es facilitar la edición y compilación del proyecto en entornos de desarrollo.

---

## Resumen

En términos prácticos, cada carpeta cumple un rol claro:

- `src`: desarrollo del código fuente
- `bin`: salida compilada
- `ant`: automatización de build
- `docs`: documentación técnica
- `org` (bajo `src`/`bin`): espacio de paquetes Java del proyecto

La forma correcta de entender esta estructura es:

```text
src  = código fuente
bin  = clases compiladas
ant  = scripts de construcción
org  = namespace de paquetes Java
```
