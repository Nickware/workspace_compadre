# Descripción de la Simulación 3D con VPython

Este script crea una simulación interactiva en 3D de una pelota que rebota sobre una plataforma, utilizando física realista de gravedad y colisión, y una estética visual amigable con colores pastel. Los rebotes son detectados, contabilizados y el cambio de color de la pelota en cada contacto permite visualizar la dinámica física de manera atractiva y didáctica.

------

## Características principales

- **Simulación física** en tiempo real con gravedad, coeficiente de restitución y conservando las componentes horizontales.
- **Interfaz 3D interactiva:** puede rotar la vista y hacer zoom con el mouse para explorar la simulación desde cualquier ángulo.
- **Visuales atractivos:** la escena usa colores pastel y diferentes elementos gráficos para reforzar la comprensión espacial (rejilla, ejes, etiquetas).
- **Dinámica controlada:** la pelota rebota en los bordes laterales y traseros de la plataforma, con reducción y cambio de sentido de la velocidad correspondiente.
- **Información clara:** etiquetas muestran la altura, velocidad y número de rebotes en tiempo real, y mensajes en consola para seguimiento adicional.
- **Límite de rebotes:** la simulación se detiene tras alcanzar un número máximo configurado, mostrando un resumen final.

------

## Requisitos

- [VPython](https://vpython.org/) (`pip install vpython`)
- Python 3.x

------

## Uso

Ejecutar el script directamente en tu entorno Python compatible con VPython:

```
bash
python pelota_rebotando_3d.py
```

Interactúar con la simulación usando el mouse:

- **Rotar:** arrastrar con el mouse.
- **Zoom:** usar la rueda del mouse.

Para detener la simulación anticipadamente, pulsa `Ctrl+C` en la consola.

------

## Parámetros principales

- `g`: aceleración gravitatoria, en m/s².
- `restitution`: coeficiente de restitución para los rebotes.
- `ball_radius`: radio de la pelota.
- `ball_init_y`: altura inicial de la pelota.
- `vx`, `vy`, `vz`: velocidades iniciales en los tres ejes.
- `max_bounces`: número máximo de rebotes simulados.

Todos estos valores pueden ajustarse para experimentar diferentes efectos físicos y visuales.

------

## Personalización y exploración

- Modificar los colores pastel para probar diferentes estilos.
- Cambiar la opacidad de la plataforma o el tamaño de la pelota.
- Ajustar `restitution`, velocidad inicial, y el tamaño de la plataforma para investigar el comportamiento físico en distintas condiciones.
- Usar VPython para agregar más elementos, obstáculos, o múltiple pelotas.

------

# Explicación del Modelo de Pelota Rebotando

Una simulación física interactiva en 3D que muestra el movimiento y rebotes de una pelota bajo los efectos de la gravedad, implementada en Python usando VPython.

## Características

###  Visualización

- **Renderizado 3D completo** con ejes coordenados visibles
- **Colores pastel** suaves y armónicos
- **Plataforma decorativa** con bordes y esquinas
- **Sistema de iluminación** con múltiples fuentes de luz
- **Trazado de trayectoria** que muestra el camino de la pelota
- **Interfaz interactiva** con rotación y zoom

### Física Realista

- **Gravedad terrestre** (9.8 m/s²)
- **Coeficiente de restitución** ajustable
- **Rebotes elásticos** con pérdida de energía
- **Movimiento en 3 dimensiones** (X, Y, Z)
- **Colisiones precisas** con el suelo y bordes

### Monitoreo en Tiempo Real

- **Panel informativo** con altura, velocidad y contador de rebotes
- **Salida por consola** con datos detallados de cada rebote
- **Condición de parada** automática cuando la pelota se detiene

## Instalación

### Prerrequisitos

- Python 3.6 o superior
- pip (gestor de paquetes de Python)

### Instalación de Dependencias

```bash
# Instalar VPython
pip install vpython

# O si tienes problemas con la instalación:
pip install vpython --upgrade
```

## Uso

### Ejecutar la Simulación

```bash
python simulacion_pelota_3d.py
```

### Controles de la Interfaz

- **Rotar vista**: Arrastrar con el mouse
- **Zoom**: Usar la rueda del mouse
- **Detener**: Ctrl+C en la consola

### Parámetros Ajustables

En el código puedes modificar:

```python
# Parámetros físicos
g = 9.8                      # Gravedad (m/s²)
restitution = 0.9            # Coeficiente de restitución (0.0-1.0)
ball_radius = 1.0            # Tamaño de la pelota
ball_init_y = 15.0           # Altura inicial

# Parámetros visuales
max_bounces = 25             # Límite de rebotes
trail_radius = 0.3           # Grosor del rastro
```

## Descripción Física

### Ecuaciones Implementadas

**Movimiento vertical (caída libre):**

```
y(t) = y₀ + v₀t - 0.5gt²
v(t) = v₀ - gt
```

**Rebote (conservación de momentum):**

```
v' = -v × restitution
```

**Movimiento horizontal:**

```
x(t) = x₀ + vₓt
z(t) = z₀ + v𝔃t
```

### Variables de Simulación

| Variable      | Descripción                | Valor por Defecto |
| ------------- | -------------------------- | ----------------- |
| `g`           | Aceleración gravitacional  | 9.8 m/s²          |
| `restitution` | Coeficiente de restitución | 0.9               |
| `dt`          | Paso de tiempo             | 0.01 s            |
| `vx, vz`      | Velocidades horizontales   | 3.0, 2.5 m/s      |

## Paleta de Colores Pastel

La simulación utiliza una paleta de colores pastel cuidadosamente seleccionada:

- **Pelota**: Azul cielo (`#99CCFF`)
- **Plataforma**: Rosa pastel (`#FFB3CC`)
- **Ejes**: Verde menta, rosa suave, azul claro
- **Información**: Textos en tonos púrpura pastel

##  Salida por Consola

La simulación proporciona feedback detallado:

```
==================================================
SIMULACIÓN 3D DE PELOTA REBOTANDO
==================================================
Rebote # 1 - Velocidad Y:  12.35 m/s - Altura:  0.5 m
Rebote # 2 - Velocidad Y:  10.12 m/s - Altura:  0.5 m
...
==================================================
SIMULACIÓN TERMINADA
Total de rebotes: 25
Posición final: X=2.3, Y=0.5, Z=-1.8
Velocidad final: X=0.4, Y=0.8, Z=0.3 m/s
==================================================
```

## Estructura del Código

```
simulacion_pelota_3d.py
├── Configuración inicial
├── Definición de colores pastel
├── Creación de objetos 3D
├── Configuración de iluminación
├── Bucle principal de simulación
│   ├── Actualización de posición
│   ├── Cálculo de gravedad
│   ├── Detección de colisiones
│   └── Actualización de visualización
└── Mensajes finales
```

## Aplicaciones Educativas

Esta simulación es ideal para:

- **🏫 Enseñanza de física** (movimiento parabólico, energía)
- **💻 Programación científica** con visualización 3D
- **🎨 Diseño de visualizaciones** científicas atractivas
- **📚 Demostraciones interactivas** en el aula

## Contribuir

Las contribuciones son bienvenidas. Puede:

1. Reportar bugs o problemas
2. Sugerir nuevas características
3. Mejorar la documentación
4. Optimizar el código

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Soporte

Si tiene problemas o preguntas:

1. Revisar los issues existentes
2. Crear un nuevo issue con detalles del problema
3. Contactar al desarrollador

