# Demo3D_5App vs Demo3D_6App

Este README compara los dos ejemplos 3D del capítulo 3: `Demo3D_5App` y `Demo3D_6App`.

## 1. Objetivo general

Ambos programas muestran una pelota moviéndose dentro de una caja 3D con rebotes en las paredes. La diferencia principal es que `Demo3D_6App` agrega conteo y retroalimentación visual del número de rebotes que ocurren durante la simulación.

---

## 2. Comparación rápida

| Aspecto | Demo3D_5App | Demo3D_6App |
|---|---|---|
| Base física | Sí | Sí |
| Pelota en caja 3D | Sí | Sí |
| Trail de movimiento | Sí | Sí |
| Rebotación en paredes | Sí | Sí |
| Conteo de rebotes | No | Sí |
| Mensaje visible en pantalla | No | Sí (`frame.setMessage(...)`) |
| Reinicio del contador | No aplica | Sí (`bounceCount = 0`) |

---

## 3. Demo3D_5App

`Demo3D_5App` es la versión base.

### Comportamiento
- Genera una posición inicial aleatoria para `x, y, z`.
- Genera velocidades aleatorias `vx, vy, vz`.
- En cada paso:
  - actualiza la posición,
  - invierte la velocidad cuando la pelota toca un límite,
  - dibuja el nuevo punto en el trail.

### Código clave

```java
protected void doStep() {
  x += vx*dt;
  y += vy*dt;
  z += vz*dt;
  if(x<min||x>max) {
    vx = -vx;
  }
  if(y<min||y>max) {
    vy = -vy;
  }
  if(z<min||z>max) {
    vz = -vz;
  }
  ball.setXYZ(x, y, z);
  trail.addPoint(x, y, z);
}
```

Esta versión es útil para entender la dinámica básica de una partícula en una caja 3D sin ninguna métrica adicional.

---

## 4. Demo3D_6App

`Demo3D_6App` mantiene la misma lógica física que `Demo3D_5App`, pero añade un contador de rebotes.

### Cambios principales (líneas agregadas)

Estas son las líneas que se incorporaron específicamente en la versión 6:

```java
int bounceCount = 0;   // <-- nuevo: contador de rebotes
```

```java
public void initialize() {
  x = (max-min)*(Math.random()-0.5);
  y = (max-min)*(Math.random()-0.5);
  z = (max-min)*(Math.random()-0.5);
  vx = (max-min)*(Math.random()-0.5);
  vy = (max-min)*(Math.random()-0.5);
  vz = (max-min)*(Math.random()-0.5);
  ball.setXYZ(x, y, z);
  trail.clear();
  bounceCount = 0;   // <-- reiniciar al iniciar una nueva corrida
}
```

```java
protected void doStep() {
  x += vx*dt;
  y += vy*dt;
  z += vz*dt;
  if(x<min||x>max) {
    vx = -vx;
    bounceCount++;
  }
  if(y<min||y>max) {
    vy = -vy;
    bounceCount++;
  }
  if(z<min||z>max) {
    vz = -vz;
    bounceCount++;
  }
  ball.setXYZ(x, y, z);
  trail.addPoint(x, y, z);
  frame.setMessage("Rebotes: " + bounceCount);   // <-- se ve en vivo en la ventana 3D
}
```

Estas líneas convierten la simulación de una simple animación en una experiencia observacional con conteo en tiempo real.

---

## 5. Qué se mantiene igual

La estructura base no cambia:
- `AbstractSimulation`
- `initialize()`
- `doStep()`
- `main()`
- `Display3DFrame`, `ElementCircle`, `ElementTrail`

La evolución de `Demo3D_5App` a `Demo3D_6App` es un refinamiento funcional, no un cambio radical de modelo físico.

---

## 6. En resumen

`Demo3D_5App` es la versión simple y didáctica.
`Demo3D_6App` es la misma simulación, pero con información adicional útil para análisis o visualización del comportamiento de la pelota.

Si se quiere un ejemplo más observacional y con feedback inmediato, `Demo3D_6App` es la mejor opción.
