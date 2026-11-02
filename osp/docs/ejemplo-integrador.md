# Ejemplo integrador: simulación de oscilador armónico

Este ejemplo muestra cómo interactúan todas las carpetas principales del proyecto OSP en una simulación realista:

- `src/`: contiene el código fuente de la simulación
- `ant/`: compila y genera el artefacto
- `bin/`: recibe las clases compiladas
- `org/opensourcephysics/...`: espacio de paquetes Java
- `docs/`: documenta la intención y el flujo

---

## 1. Escenario

Queremos modelar un oscilador armónico simple con:

- posición `x`
- velocidad `v`
- masa `m`
- constante elástica `k`

La ecuación diferencial es:

$$
F = -k x
$$

$$
\frac{d^2x}{dt^2} = -\frac{k}{m}x
$$

El modelo se actualiza en pasos temporales y la vista dibuja la posición del sistema.

---

## 2. Ubicación del archivo fuente

La clase debe crearse dentro del árbol de fuentes del proyecto, con el mismo nombre de la clase pública.

Ejemplo de ubicación:

```text
osp/src/org/opensourcephysics/demo/OsciladorSimple.java
```

Y la clase debe declararse así:

```java
package org.opensourcephysics.demo;

import org.opensourcephysics.controls.AbstractSimulation;

public class OsciladorSimple extends AbstractSimulation {
    private double x;
    private double v;
    private double m;
    private double k;

    public void initialize() {
        x = 1.0;
        v = 0.0;
        m = 1.0;
        k = 4.0;
    }

    public void reset() {
        initialize();
    }

    public void doStep() {
        double a = -(k / m) * x;
        v = v + a * 0.01;
        x = x + v * 0.01;
    }
}
```

Reglas que se cumplen aquí:

- el archivo se crea bajo `src/`
- el paquete coincide con la ruta física
- el nombre de la clase coincide con el nombre del archivo
- la clase extiende `AbstractSimulation`, que es un patrón típico de OSP

---

## 3. Cómo participa cada carpeta

### `src/`
Es el origen del desarrollo.

La clase `OsciladorSimple.java` vive aquí y se compila desde este directorio.

### `org/opensourcephysics/...`
Este espacio de paquetes es el namespace de Java del proyecto. En OSP se usa para organizar:

- `controls`
- `numerics`
- `display`
- `tools`
- `analysis`
- y aquí también el ejemplo de simulación `demo`

### `ant/`
La carpeta `ant/build_core.xml` contiene la lógica de compilación.

Cuando se ejecuta:

```bash
ant -f ant/build_core.xml compile
```

Ant compila todo el contenido de `src` y genera las clases Java resultantes.

### `bin/`
La salida compilada se escribe en `bin/` o en el árbol de clases generado por el build.

Ahí se produce una estructura similar a:

```text
bin/org/opensourcephysics/demo/OsciladorSimple.class
```

Esto permite ejecutar o reutilizar la simulación ya compilada.

### `docs/`
La carpeta `docs` registra lo que hace esta simulación, su flujo, su integración con OSP y cómo se compila.

Es donde se documenta la intención del ejemplo, no el código en sí.

---

## 4. Compilación y generación del JAR

La tarea principal del build está en:

```text
osp/ant/build_core.xml
```

Para compilar:

```bash
ant -f ant/build_core.xml compile
```

Para generar el JAR del proyecto completo:

```bash
ant -f ant/build_core.xml all
```

La tarea `all` ejecuta la compilación y luego genera el artefacto JAR final, normalmente bajo:

```text
osp/build/jar/osp.jar
```

En este flujo, la cadena es:

```text
src/ -> ant/build_core.xml -> bin/ o build/classes -> jar
```

---

## 5. Flujo completo de la simulación

El ciclo real de trabajo sería el siguiente:

1. escribes la clase Java en `src`
2. le asignas el paquete correcto
3. te aseguras de que el nombre del archivo coincide con la clase pública
4. ejecutas Ant para compilar
5. la salida queda en `bin` o `build/classes`
6. el JAR se genera con la tarea `jarfile`
7. la simulación queda lista para reutilizar o ejecutar

En términos de arquitectura:

```text
src/ --> compilación con Ant --> bin/ --> JAR --> ejecución / reutilización
```

---

## 6. Resumen integrador

Este ejemplo demuestra que todas las carpetas del proyecto tienen un papel concreto:

- `src`: define el modelo y la lógica del sistema
- `org/...`: organiza el código en paquetes Java
- `ant`: compila y empaqueta el software
- `bin`: contiene la salida compilada
- `docs`: explica el diseño y el uso

La arquitectura del repositorio no es accidental: cada carpeta responde a una fase del ciclo de desarrollo de una simulación OSP.
