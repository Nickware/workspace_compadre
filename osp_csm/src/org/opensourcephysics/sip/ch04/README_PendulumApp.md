# PendulumApp — Comparación: original vs. versión extendida

Este documento resume los cambios realizados sobre `PendulumApp.java` (capítulo 4, `org.opensourcephysics.sip.ch04`) para agregar visualización de energía cinética, potencial y total del péndulo.

---

## 1. Resumen del cambio

| | Original | Extendida |
|---|---|---|
| **Ventanas mostradas** | 2 (`plotFrame`, `displayFrame`) | 3 (+ `energyFrame`) |
| **Datos graficados** | Ángulo θ vs. tiempo | + Energía cinética, potencial y total vs. tiempo |
| **Layout de energía** | No existe | 1 ventana (`JFrame`), 3 paneles apilados (`GridLayout(3,1)`) |
| **Escalas de los 3 paneles** | N/A | Rango Y común, calculado dinámicamente en `initialize()` |
| **Estilo de trazo** | N/A | Línea continua, sin marcadores de punto |
| **Cálculo físico nuevo** | Ninguno | `KE = ½ m L² θ̇²`, `PE = m g L (1 − cos θ)`, `E = KE + PE` |

---

## 2. Qué se mantuvo intacto

- La estructura general de `AbstractSimulation` (`initialize`, `doStep`, `reset`, `main`) no cambió.
- `plotFrame` (θ vs. tiempo) y `displayFrame` (animación del péndulo) siguen funcionando exactamente igual que en el original.
- El modelo `Pendulum` no se modificó — todos los cambios están contenidos en `PendulumApp`.

## 3. Qué se agregó, campo por campo

```java
// Nuevos campos de la clase:
JFrame energyFrame = new JFrame("Energía del péndulo");
PlottingPanel kineticPanel, potentialPanel, totalPanel;
Dataset kineticData, potentialData, totalData;
double mass = 1.0, length = 1.0, g = 9.8;
```

- **`energyFrame` + 3× `PlottingPanel`**: en vez de tres `PlotFrame` (que abrirían tres ventanas separadas), se construye un único `JFrame` con `GridLayout(3,1)` y se le insertan tres `PlottingPanel` directamente — el mismo patrón que usa internamente `PlotFrame`, aplicado a mano para controlar el layout.
- **`mass`, `length`, `g`**: parámetros físicos explícitos. *Supuesto declarado*: modelo normalizado (m = L = 1), consistente con `pendulum.state` usado en el archivo original. Si el `Pendulum` real usa otros valores, estas tres líneas son el único lugar que hay que ajustar.

## 4. Cambios dentro de los métodos existentes

### `PendulumApp()` (constructor)
- Se agregó configuración de estilo de línea (`setConnected(true)`, `setMarkerShape(0)`) sobre los tres `Dataset` nuevos, para que se dibujen como línea continua en vez de puntos sueltos.
- Se construye y muestra `energyFrame` al final del constructor, igual que ya se hacía con `displayFrame`.

### `initialize()`
- Se agregó el cálculo de `eMax` a partir de las condiciones iniciales reales (`theta`, `thetaDot`) leídas del panel de control, para fijar un rango Y común a los tres paneles de energía — así las tres escalas son comparables visualmente sin importar qué condiciones iniciales pruebe el usuario.
- Se agregó `kineticData.clear()`, `potentialData.clear()`, `totalData.clear()` junto a la limpieza que ya existía en `trail`/estado, para que cada nueva corrida no arrastre datos de la anterior.

### `doStep()`
- Se agregaron 3 líneas de cálculo físico (`ke`, `pe`, `total`) y 3 llamadas `append()`, ejecutadas en cada paso de tiempo junto al `plotFrame.append()` que ya existía.

### `reset()`
- Sin cambios.

## 5. Verificación física (confirmada, no asumida)

Con el modelo sin amortiguamiento ni forzamiento:
- **Energía total**: constante (línea recta), como corresponde a un sistema conservativo — confirmado en la corrida real.
- **Energía cinética y potencial**: oscilan de forma senoidal e inversa entre sí (cuando una sube, la otra baja en la misma proporción), de modo que su suma se cancela en la línea recta de energía total.

Este comportamiento es la validación física esperada del modelo — si en algún momento la línea de energía total dejara de ser recta, sería señal de un error numérico (paso de tiempo `dt` demasiado grande) o de que el modelo real sí incluye disipación/forzamiento no contemplados aquí.

## 6. Decisiones abiertas (pendientes de por confirmación)

- El nombre simbólico de la constante `NO_MARKER` en la API de OSP tiene referencias contradictorias en la documentación disponible (`NO_MARKER` vs `NOMARKER`); se usó el literal `0`, confirmado en ejemplos reales de la bibliografía, en vez de arriesgar un error de compilación.
- Si `mass`, `length` o `g` deben tomarse de `Pendulum` en vez de estar hardcodeados en `PendulumApp`, se puede exponer esos valores como getters en el modelo y leerlos desde aquí — no se hizo porque no se tuvo acceso al archivo `Pendulum.java` original.
