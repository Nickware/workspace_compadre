# Simulación de Partícula en una Caja con OpenSource Physics (OSP)

## Descripción
Simulación basada en el framework **OpenSource Physics** que modela el movimiento de una partícula en una caja 2D con:

- **Rebotes elásticos/amortiguados** contra las paredes
- **Pérdida de energía** controlada por coeficiente de restitución
- **Integración numérica** mediante método RK4

## Estructura del Código (MVC)
```plaintext
├── ParticleBoxApp.java         # Punto de entrada (main)
├── ParticleBoxModel.java       # Lógica física (implementa ODE)
├── ParticleBoxView.java        # Visualización (AbstractAnimation)
└── readme_ParticleBox.md       # Este archivo
```

## Características Clave 
> Componente	      |  Detalle
>>
> - Física	          |  Ecuaciones diferenciales con ODE + colisiones manuales
>>
> - Visualización     |	 Herencia de Circle para dibujo automático
>>
> - Controles	Botones |  Start/Stop/Reset con OSPControl
>>
> - Parámetros	      |  Posición inicial, velocidad, coeficiente de restitución, tamaño de caja

## Requisitos
- OpenSource Physics Library
- Java 8+
- IDE (Eclipse/NetBeans/VS Code)

## Personalización
Modificar en `ParticleBoxModel.java`
```java
private double restitution = 0.9; // 1.0 = elástico, <1.0 = amortiguado
private double size = 10;         // Tamaño de la caja
```
## Ejemplos de Uso
### 1. Simulación Básica
```java
model.initialize(0, 0, 2.0, 1.5, 0); // x, y, vx, vy, t
```
### 2. Con gravedad
```java
public void getRate(double[] state, double[] rate) {
    rate[3] = -9.8; // dv_y/dt = -g
}
```
## Licencia
BSD 3-Clause (Compatibilidad con OSP)

## Contribuciones
¡Bienvenidas! Reporta issues o envía PRs con:
- Colisiones entre partículas
- Campos de fuerza personalizados
- Visualización 3D

