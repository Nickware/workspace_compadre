# Cómo construir una simulación en OSP

Esta guía describe el flujo práctico para crear una simulación en OSP, partiendo de la base del framework y llegando a un modelo ejecutable con actualización numérica y visualización.

---

## 1. Entender el patrón arquitectónico

OSP sigue un patrón típico de simulación interactiva:

- `Control`: configura parámetros
- `Simulation`: representa el modelo dinámico
- `ODESolver`: actualiza el modelo con integración numérica
- `Display`: presenta el estado visual

La idea es separar claramente:

- la lógica física del sistema
- los parámetros de entrada
- el cálculo de evolución temporal
- la representación gráfica

---

## 2. Crear la clase de simulación

La base de una simulación normalmente será una clase que extienda de `AbstractSimulation` o que implemente la interfaz de simulación adecuada.

Importante:

- el archivo Java debe crearse dentro del árbol de fuentes del proyecto, siguiendo el mismo paquete Java
- el nombre del archivo debe coincidir exactamente con el nombre de la clase pública
- si la clase se llama `MiSimulacion`, entonces el archivo debe ser `MiSimulacion.java`
- la ruta debe corresponder al paquete. Por ejemplo, si usas el paquete `org.misimulacion`, el archivo debe ir en `src/org/misimulacion/MiSimulacion.java`

La clase debe definir al menos:

- estado del sistema
- parámetros configurables
- inicialización del modelo
- método de actualización por paso
- representación visual

Ejemplo conceptual:

```java
public class MiSimulacion extends AbstractSimulation {
    private double x;
    private double v;
    private double masa;

    public void initialize() {
        x = 0;
        v = 1.0;
        masa = 1.0;
        // configurar variables del control si hace falta
    }

    public void reset() {
        initialize();
    }

    public void doStep() {
        // actualizar el estado del sistema
        x = x + v * 0.01;
        // opcional: repintar o actualizar objetos visuales
    }
}
```

La clave es que `doStep()` o el equivalente de la simulación actualiza el sistema en pasos discretos.

---

## 3. Definir el estado físico

Las variables de estado deben reflejar las magnitudes dinámicas del sistema:

- posición
- velocidad
- energía
- temperatura
- campo eléctrico
- concentración
- etc.

Un modelo bien hecho evita mezclar la física con la interfaz gráfica. La lógica del sistema y la representación visual deben estar separadas.

---

## 4. Configurar parámetros desde un control

OSP usa `Control` para leer y configurar valores del modelo.

Esto permite:

- cambiar masa, fuerza, frecuencia, amortiguación, etc.
- ajustar el sistema mientras se ejecuta
- guardar y cargar estados
- reutilizar la misma simulación con distintas condiciones

La idea práctica es:

- exponer parámetros del sistema en un objeto `Control`
- vincular esos parámetros con variables internas del modelo
- recalcular el estado cuando cambian

---

## 5. Elegir el método numérico

La mayor parte del cálculo del sistema se hace con un solver de ecuaciones diferenciales.

Dependiendo del problema puedes usar:

- `Euler`
- `RK4`
- `Verlet`
- un solver adaptativo
- un solver de eventos

El flujo típico es:

```text
modelo -> derivadas -> ODESolver -> nuevo estado -> visualización
```

Esto convierte el modelo en una evolución temporal continua, aproximada en una secuencia de pasos.

---

## 6. Actualizar la visualización

Después del cálculo del nuevo estado, la capa visual debe reflejar el resultado.

Eso puede incluir:

- mover una partícula en pantalla
- redibujar un campo vectorial
- cambiar el valor de una superficie
- actualizar una línea de trayectoria

Las clases de visualización en `display`, `display2d` y `display3d` son las encargadas de transformar los datos del modelo en objetos gráficos.

---

## 7. Construir la lógica del paso

El paso de una simulación suele seguir este patrón:

```java
public void doStep() {
    // 1. leer parámetros actuales
    // 2. calcular derivadas / fuerza / flujo
    // 3. actualizar estado con un solver o fórmula explícita
    // 4. actualizar objetos gráficos
}
```

Un paso típico puede consistir en:

1. obtener la fuerza o la derivada
2. actualizar velocidad
3. actualizar posición
4. validar límites
5. repintar

---

## 8. Reutilizar el framework para casos reales

Un ejemplo de caso real en OSP puede ser:

- oscilador armónico
- caída libre
- movimiento planetario
- difusión térmica
- onda en cuerda
- fenómeno de potencial eléctrico
- campo vectorial en 2D

En todos los casos, la estructura es similar:

- definir variables de estado
- definir ecuaciones del sistema
- iterar con un paso temporal
- renderizar el resultado

---

## 9. Guardar y recuperar el estado

OSP permite serializar el estado con XML a través de las clases del paquete `controls`, especialmente `XMLControl`.

Esto es importante porque permite:

- guardar una configuración
- restaurar un estado previo
- compartir simulations reproducibles
- preparar materiales educativos con valores iniciales bien definidos

---

## 10. Buenas prácticas

Para construir simulaciones legibles y mantenibles en OSP:

- separa física y visualización
- usa nombres claros para variables de estado
- define métodos pequeños para cada responsabilidad
- no mezcles control de UI con la ecuación del sistema
- usa un solver adecuado para la precisión requerida
- documenta los parámetros físicos que se exponen al usuario

---

## 11. Generar un JAR desde la fuente

Una vez que la simulación está implementada, el siguiente paso es compilarla y empaquetarla como JAR.

### 11.1 Ubicación del código fuente

En este repositorio, el código fuente principal vive en:

```text
osp/src/
```

Si vas a agregar tu clase nueva, debe quedar dentro del árbol `src` en una ruta coherente con el paquete.

Por ejemplo:

```text
osp/src/org/opensourcephysics/demo/MiSimulacion.java
```

o bien otra estructura equivalente, siempre con el mismo nombre de paquete que el directorio físico.

### 11.2 Regla clave: nombre de archivo = nombre de clase

Si defines esta clase:

```java
package org.opensourcephysics.demo;

public class MiSimulacion extends AbstractSimulation {
    // ...
}
```

entonces el archivo debe llamarse exactamente:

```text
MiSimulacion.java
```

y debe guardarse en:

```text
osp/src/org/opensourcephysics/demo/
```

### 11.3 Compilar con Ant

El proyecto usa Ant. La configuración de build está en:

```text
osp/ant/build_core.xml
```

Desde la carpeta raíz del proyecto OSP, puedes ejecutar:

```bash
ant -f ant/build_core.xml compile
```

o bien la tarea general:

```bash
ant -f ant/build_core.xml all
```

La tarea `compile` compila todo el código fuente bajo `src` hacia `build/classes`.

La tarea `all` ejecuta el proceso completo y genera el JAR.

### 11.4 Generación del JAR

La tarea `jarfile` en `build_core.xml` crea el archivo `.jar` a partir de las clases compiladas.

La configuración del script usa:

```xml
<jar jarfile="${jar.dir}osp.jar" basedir="${classes.dir}" manifest="${classes.dir}/meta-inf/Manifest.mf" />
```

Esto implica que, en la práctica, el JAR final se genera en:

```text
osp/build/jar/osp.jar
```

### 11.5 Verificar el resultado

Después de ejecutar la tarea de compilación, revisa si existe el artefacto:

```text
osp/build/jar/osp.jar
```

Si existe, el paquete Java ha sido generado correctamente a partir de la fuente.

---

## 12. Resumen del proceso

Un proyecto típico de simulación en OSP sigue este flujo:

```text
1. Crear el archivo Java en la ruta correcta del paquete
2. Asegurar que el nombre del archivo coincide con la clase pública
3. Definir la clase con el patrón de simulación de OSP
4. Implementar initialize() y reset()
5. Definir la evolución del sistema en doStep()
6. Usar ODESolver si el problema es dinámico
7. Actualizar la capa visual
8. Compilar con Ant
9. Generar el JAR desde la fuente
10. Guardar o restaurar estado si hace falta
```

Con esto ya puedes construir, compilar y empaquetar una simulación funcional, visual y reutilizable sobre la base de OSP.
