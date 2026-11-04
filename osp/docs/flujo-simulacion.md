# Flujo de ejecución de una simulación en OSP

Este documento describe el ciclo real de ejecución de una simulación dentro del núcleo de OSP, desde el arranque hasta el paso de integración numérica.

---

## 1. Arranque de la simulación

La clase base de referencia es `AbstractSimulation`, definida en `org.opensourcephysics.controls`.

Cuando una simulación comienza, se ejecuta un flujo típico como este:

- se establece el `Control` asociado a la simulación
- se inicializa el marco principal de la interfaz
- se bloquean los valores del control mientras se reinicia la simulación
- se llama a `resetAnimation()`
- se llama a `initializeAnimation()`
- se lanza el hilo de animación o ejecución

En otras palabras, el arranque no solo “empieza a correr”; primero prepara el estado inicial, monta la vista y deja el modelo listo para operar.

---

## 2. Inicialización del modelo

La simulación concreta suele implementar métodos como:

- `initialize()`
- `reset()`
- `start()`
- `stop()`

Estos métodos se usan para:

- fijar condiciones iniciales
- establecer parámetros físicos
- preparar arrays de estado
- definir condiciones de frontera o topología del problema
- asociar valores del control a variables del modelo

La lógica de arranque está separada de la lógica de cálculo. Es decir, la UI y las estructuras de control no mezclan directamente el comportamiento del modelo.

---

## 3. Bucleación del paso temporal

La parte clave del sistema es el mecanismo de paso. En `AbstractSimulation` se encapsula la orden de ejecución:

1. `startRunning()`
2. `doStep()` o el equivalente de la simulación
3. incremento del contador de pasos
4. `stopRunning()`
5. repintado de los marcos animados

El comportamiento central se produce en `stepAnimation()`, que invoca la actualización del modelo y luego repinta la representación visual.

---

## 4. Paso de integración numérica

Una vez el modelo está inicializado, la integración real ocurre en la capa `numerics`.

El principio es el siguiente:

- el estado del sistema se representa como un vector de variables
- el solver de ODE toma ese estado y calcula su derivada
- aplica un método como Euler, RK4, Verlet o un solver adaptativo
- actualiza los valores del sistema en función del tamaño de paso
- devuelve el estado nuevo al modelo

La interfaz base es `ODESolver`, que define:

- `initialize(stepSize)`
- `step()`
- `setStepSize(stepSize)`
- `getStepSize()`

Esto permite que el modelo físico no dependa de un único algoritmo de integración. Por ejemplo, el mismo sistema puede resolverse con Euler para pruebas rápidas o con RK4 para precisión.

---

## 5. Actualización de la vista

Una vez calculado el nuevo estado, la capa visual toma esos datos y los renderiza:

- posiciones de partículas o cuerpos
- valores de campos
- contornos o mapas de calor
- trayectorias, ejes, líneas de evolución

Esto ocurre a través de paquetes como:

- `display`
- `display2d`
- `display3d`

La separación es clara: la lógica física cambia el estado; la visualización sólo refleja ese estado.

---

## 6. Ciclo continuo

El patrón general del sistema es:

```text
estado inicial
    ↓
control / parámetros
    ↓
solver numérico
    ↓
estado actualizado
    ↓
renderizado gráfico
    ↓
repetición del paso
```

En otras palabras, una simulación en OSP es un bucle de estado-discreto:

- calcule el siguiente estado
- represéntelo
- vuelva a repetir

Eso es lo que le da a OSP la sensación de “animación científica” en vez de una simple reproducción de gráficos estáticos.

---

## 7. Resumen ejecutivo

La arquitectura de OSP separa claramente dos cosas:

- la lógica física y numérica del sistema
- la representación visual y el control interactivo

Esto permite crear simulaciones dinámicas, reutilizables y pedagógicamente útiles, sin mezclar la matemática del modelo con la capa de presentación.
