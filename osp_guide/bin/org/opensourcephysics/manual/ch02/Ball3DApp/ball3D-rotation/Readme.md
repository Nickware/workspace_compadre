# Modelo de Simulación 3D con Rotación de Partícula

# Características del Modelo con Rotación

### **Sistema de Rotación Implementado**

1. **Cuaterniones para Rotación**
   
   - Evita el bloqueo de cardán
   - Interpolación suave
   - Combinación de rotaciones
   
2. **Física Rotacional Completa**
   
   ```python
   # Momento de inercia para esfera sólida
   inertia = (2/5) * mass * radius**2
   
   # Ecuaciones de movimiento rotacional
   torque = cross(r, F)
   angular_acceleration = torque / inertia
   ```
   
3. **Colisiones con Transferencia Rotacional**
   
   - Impulsos aplicados en puntos específicos
   - Conservación de momento angular
   - Transferencia energía lineal ↔ rotacional

###  **Visualización de la Rotación**

- **Marcadores de ejes locales** (rojo, verde, azul)
- **Anillo ecuatorial** que muestra el plano de rotación
- **Actualización en tiempo real** de la orientación
- **Feedback numérico** de velocidad angular

###  **Rotación Avanzada**
- Cuaterniones para rotación sin bloqueo de cardán
- Velocidad angular en 3 ejes
- Momento de inercia para esfera sólida
- Transferencia de energía rotacional en colisiones

###  **Paleta de Colores Pastel**
- Azul pastel para la partícula
- Rosa pastel para la plataforma
- Marcadores de ejes en colores coordinados
- Ciclo de colores en cada rebote

###  **Física Mejorada**
- Colisiones realistas con transferencia de momento angular
- Conservación de energía rotacional
- Detección precisa de puntos de contacto
- Corrección de penetración

###  **Visualización 3D Completa**
- Marcadores de ejes locales rotando
- Anillo ecuatorial para referencia visual
- Ejes de coordenadas globales
- Información en tiempo real de velocidad angular

###  **Mejoras Físicas Incluidas**

1. **Energía Rotacional**
   
   ```python
   rotational_energy = 0.5 * inertia * angular_velocity_magnitude**2
   ```
   
2. **Efectos Giroscópicos**
   
   - Estabilización durante el vuelo
   - Precesión bajo gravedad
   
3. **Colisiones Realistas**
   
   - Puntos de contacto específicos
   - Rozamiento y deslizamiento

### **Parámetros Ajustables**

```python
# Propiedades de rotación
angular_velocity = vector(5, 3, 2)  # Velocidad angular inicial
inertia = (2/5) * mass * radius**2  # Momento de inercia

# Coeficientes de fricción
static_friction = 0.3
dynamic_friction = 0.2
```

###  **Métricas de Análisis**

- **Velocidad angular** en cada eje
- **Energía rotacional** vs energía lineal
- **Transferencia de energía** en colisiones
- **Estabilidad rotacional**

Este modelo transforma la simulación básica en un sistema físico completo que muestra cómo la rotación afecta el movimiento y las colisiones en 3D. 

¡La simulación incluye un modelo físico rotacional con una estética visual atractiva! 