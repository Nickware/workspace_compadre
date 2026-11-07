# Ball3DApp

El script **Ball3DApp** es una simulación 3D interactiva de una pelota rebotando, desarrollada con **Open Source Physics (OSP)**. Representa un ejemplo educativo avanzado que combina física realista, gráficos 3D y una interfaz de usuario dinámica.

## Estructura y componentes principales

### **Herencia y framework**
```java
public class Ball3DApp extends AbstractSimulation
```
- Extiende `AbstractSimulation` para manejo automático de animación
- Usa `Display3DFrame` para renderizado 3D con `simple3d`
- Integra `EjsControl` para interfaz declarativa

### **Objetos 3D creados**
| Elemento | Tipo | Propósito |
|----------|------|-----------|
| `ball` | `ElementEllipsoid` | Pelota rebotante (1x1x1 unidades) |
| `block` | `ElementBox` | Plataforma roja (4x4x1) con resolución 5x5x2 |

### **Física implementada**
```java
double z = ball.getZ() + vz*dt - 4.9*dt*dt;  // Posición con gravedad
vz -= 9.8*dt;                                // Aceleración g=9.8
if(vz<0 && z<1.0) vz = -vz;                 // Rebote perfectamente elástico
```
- **Movimiento vertical** bajo gravedad (ecuación cinemática)
- **Detección de colisión**: solo cuando cae (vz<0) y toca z=1.0
- **Paso temporal**: dt=0.05s

## Interfaz de usuario dinámica

### **Controles generados por EjsControl**
```
[Start] [Stop] [Step] [☑ 3D]
```
- **Botones estándar**: play/pausa/paso único
- **Checkbox "3D"**: alterna entre perspectiva 3D y proyección plana YZ

### **Configuración declarativa**
```java
gui.add("CheckBox", "parent=inputPanel;variable=3d;text=3D;selected=true;action=set3d;");
```
Sintaxis XML-like para UI sin código Swing manual.

## Evolución del código

| Autor | Versión | Contribuciones |
|-------|---------|----------------|
| W. Christian | 1.0 | Física y 3D base |
| N. Torres | 2.0 | **Nueva versión** con EjsControl mejorado |

## Ventajas pedagógicas

1. **Visualización 3D interactiva** con cambio de perspectiva
2. **Física verificable**: trayectoria parabólica clásica
3. **Extensible**: fácil añadir disipación, múltiples bolas, etc.
4. **Interfaz profesional** sin complejidad de código UI

## Escena visual
```
Vista 3D: Pelota azul rebotando sobre bloque rojo
Límites: X,Y [-5,5] Z [0,10]
Mensaje: "t=0.00" actualizado en tiempo real
```

**Perfecto ejemplo de OSP para enseñar**: cinemática, gráficos 3D científicos, y desarrollo de simulaciones interactivas educativas.
