# Demo3D_5App — Comparación: original vs. versión con contador de rebotes

Este documento resume los cambios realizados sobre `Demo3D_5App.java` (capítulo 3, `org.opensourcephysics.sip.ch03`) para agregar un conteo en vivo de las veces que la pelota rebota contra las paredes de la caja.

---

## 1. Resumen del cambio

| | Original | Extendida |
|---|---|---|
| **Campos nuevos** | — | `int bounceCount` |
| **Lógica de colisión** | Invierte velocidad (`vx/vy/vz = -vx/vy/vz`) sin registrar el evento | Invierte velocidad **y** incrementa `bounceCount` en cada eje que colisiona |
| **Feedback visual** | Ninguno (solo la animación 3D) | Texto flotante en la ventana 3D vía `frame.setMessage(...)` mostrando el conteo en vivo |
| **Reinicio de estado** | `x, y, z, vx, vy, vz` aleatorios + `trail.clear()` | + `bounceCount = 0` en cada `initialize()` |
| **Criterio de conteo** | N/A | Cada eje cuenta su propio rebote de forma independiente (una esquina exacta puede sumar 2 o 3 en el mismo paso) |

---

## 2. Qué se mantuvo intacto

- La estructura general de `AbstractSimulation` (`initialize`, `doStep`, `main`) no cambió.
- La física del movimiento (integración simple `x += vx*dt`, reflexión especular al tocar pared) es exactamente la misma.
- `frame`, `ball`, `trail` y toda la configuración 3D del constructor original permanecen sin alteración.

## 3. Qué se agregó, campo por campo

```java
// Único campo nuevo de la clase:
int bounceCount = 0;
```

Un solo `int`, sin dependencias externas ni estructuras nuevas — el cambio de menor huella posible para lograr el objetivo pedido.

## 4. Cambios dentro de los métodos existentes

### `initialize()`
- Se agregó `bounceCount = 0;` junto a la limpieza de estado que ya existía (`trail.clear()`), para que cada nueva corrida empiece el conteo desde cero.

### `doStep()`
- Se agregó `bounceCount++;` dentro de cada uno de los tres `if` de colisión (`x<min||x>max`, `y<min||y>max`, `z<min||z>max`), inmediatamente después de invertir la velocidad correspondiente — el mismo punto exacto donde ocurre el rebote físico, sin necesidad de detectarlo por separado.
- Se agregó `frame.setMessage("Rebotes: " + bounceCount);` al final del método, usando un método que `Display3DFrame` ya trae incorporado, sin construir UI propia.

### `main()`
- Sin cambios.

## 5. Decisión de diseño confirmada (pendiente en la versión original de la conversación)

Quedó abierta la pregunta de si un rebote en una esquina exacta (dos o tres paredes tocadas en el mismo paso de tiempo) debía contar como 1 evento o como 2–3 eventos independientes. La versión entregada cuenta **cada eje por separado** (tal como está en el código de arriba), porque físicamente son colisiones independientes con paredes distintas, aunque coincidan en el mismo paso de simulación.

**Alternativa disponible si se prefiere lo contrario** — contar como un solo rebote aunque toquen varios ejes a la vez —, reemplazando los tres incrementos independientes por un flag:

```java
protected void doStep() {
  x += vx*dt; y += vy*dt; z += vz*dt;
  boolean bounced = false;
  if(x<min||x>max) { vx = -vx; bounced = true; }
  if(y<min||y>max) { vy = -vy; bounced = true; }
  if(z<min||z>max) { vz = -vz; bounced = true; }
  if(bounced) bounceCount++;
  ball.setXYZ(x, y, z);
  trail.addPoint(x, y, z);
  frame.setMessage("Rebotes: " + bounceCount);
}
```

No se aplicó esta alternativa por defecto porque no hubo confirmación explícita de cuál de los dos criterios se prefiere — queda documentado aquí para decidirlo cuando haga falta.
