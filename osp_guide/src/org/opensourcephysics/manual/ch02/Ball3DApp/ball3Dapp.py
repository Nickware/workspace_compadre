from vpython import sphere, box, vector, color, rate, cylinder, label, scene, distant_light

# Parámetros iniciales
g = 9.8                      # gravedad (m/s^2)
dt = 0.01                    # paso de tiempo (s)
restitution = 0.9            # coeficiente de restitución
floor_y = 0                  # posición del suelo (y)
ball_radius = 1.0            # radio aumentado
ball_init_y = 15.0           # posición inicial más alta

# Configuración de la escena
scene.background = color.white  # fondo blanco
scene.center = vector(0, 10, 0)
scene.range = 20
scene.width = 1000
scene.height = 800
scene.title = "Simulación 3D de Pelota Rebotando"

# Función para crear colores pastel (RGB normalizado)
def pastel_color(r, g, b):
    return vector(r/255.0, g/255.0, b/255.0)

# Colores pastel
ball_color = pastel_color(153, 204, 255)       # azul pastel
platform_color = pastel_color(255, 179, 204)   # rosa pastel
platform_edge_color = pastel_color(230, 128, 153) # rosa pastel oscuro
grid_color = pastel_color(179, 230, 179)       # verde pastel
trail_color = pastel_color(102, 153, 204)      # azul pastel para rastro

# Configurar iluminación suave
distant_light(direction=vector(1, 1, 1), color=color.gray(0.7))
distant_light(direction=vector(-1, -1, -1), color=color.gray(0.3))

# Crear pelota con mayor tamaño
ball = sphere(
    pos=vector(0, ball_init_y, 0), 
    radius=ball_radius, 
    color=ball_color,
    shininess=0.6,
    make_trail=True,
    trail_type="points",
    trail_color=trail_color,
    trail_radius=0.3,
    interval=5,
    retain=200
)

# Crear plataforma de rebote 3D más grande
platform = box(
    pos=vector(0, floor_y - 0.5, 0), 
    size=vector(20, 1, 20), 
    color=platform_color,
    opacity=0.8
)

# Bordes decorativos de la plataforma
box(pos=vector(0, floor_y, 10.5), size=vector(22, 0.2, 1), color=platform_edge_color, opacity=0.6)
box(pos=vector(0, floor_y, -10.5), size=vector(22, 0.2, 1), color=platform_edge_color, opacity=0.6)
box(pos=vector(10.5, floor_y, 0), size=vector(1, 0.2, 22), color=platform_edge_color, opacity=0.6)
box(pos=vector(-10.5, floor_y, 0), size=vector(1, 0.2, 22), color=platform_edge_color, opacity=0.6)

# Esquinas decorativas
sphere(pos=vector(10.5, floor_y, 10.5), radius=0.3, color=platform_edge_color)
sphere(pos=vector(10.5, floor_y, -10.5), radius=0.3, color=platform_edge_color)
sphere(pos=vector(-10.5, floor_y, 10.5), radius=0.3, color=platform_edge_color)
sphere(pos=vector(-10.5, floor_y, -10.5), radius=0.3, color=platform_edge_color)

# Ejes de referencia 3D más visibles con colores suaves
# Eje X (rosa pastel)
cylinder(pos=vector(-12, 0, 0), axis=vector(24, 0, 0), radius=0.15, color=pastel_color(255, 153, 179))
# Eje Y (verde pastel)
cylinder(pos=vector(0, -2, 0), axis=vector(0, 30, 0), radius=0.15, color=pastel_color(153, 230, 153))
# Eje Z (azul pastel)
cylinder(pos=vector(0, 0, -12), axis=vector(0, 0, 24), radius=0.15, color=pastel_color(153, 179, 255))

# Rejilla en el plano XZ para mejor referencia 3D
for i in range(-10, 11, 2):
    # Líneas en X
    cylinder(pos=vector(-10, floor_y, i), axis=vector(20, 0, 0), radius=0.08, color=grid_color, opacity=0.5)
    # Líneas en Z
    cylinder(pos=vector(i, floor_y, -10), axis=vector(0, 0, 20), radius=0.08, color=grid_color, opacity=0.5)

# Etiquetas de ejes más grandes con colores pastel
label(pos=vector(13, 0, 0), text="X", color=pastel_color(230, 102, 128), height=25, box=False)
label(pos=vector(0, 31, 0), text="Y", color=pastel_color(102, 204, 102), height=25, box=False)
label(pos=vector(0, 0, 13), text="Z", color=pastel_color(102, 128, 230), height=25, box=False)

# Velocidad inicial
vy = 0.0
# Movimiento lateral más pronunciado para mejor efecto 3D
vx = 3.0
vz = 2.5

# Contador de rebotes
bounce_count = 0
max_bounces = 25  # Límite de rebotes para la simulación

# Información de visualización
info_text = label(
    pos=vector(-18, 28, 0),
    text=f"ALTURA: {ball_init_y:.1f} m\nVELOCIDAD Y: {vy:.1f} m/s\nREBOTES: {bounce_count}",
    color=pastel_color(77, 77, 128),  # azul grisáceo pastel
    height=18,
    box=False
)

# Texto de instrucciones
instructions = label(
    pos=vector(0, -5, 0),
    text="Rota: Arrastra mouse\nZoom: Rueda del mouse",
    color=pastel_color(128, 77, 153),  # púrpura pastel
    height=15,
    box=False
)

print("=" * 50)
print("SIMULACIÓN 3D DE PELOTA REBOTANDO")
print("=" * 50)
print("Presiona Ctrl+C en la consola para detener")
print("Puedes rotar la vista arrastrando con el mouse")
print("Usa la rueda del mouse para hacer zoom")
print("=" * 50)

# Colores pastel para el ciclo de rebotes
pastel_colors = [
    pastel_color(153, 204, 255),  # azul claro
    pastel_color(204, 179, 255),  # lila
    pastel_color(255, 204, 179),  # melocotón
    pastel_color(204, 255, 179),  # verde menta
    pastel_color(255, 179, 204)   # rosa
]

try:
    while bounce_count < max_bounces:
        rate(100)
        
        # Actualizar posición (movimiento en 3D)
        ball.pos.x += vx * dt
        ball.pos.y += vy * dt
        ball.pos.z += vz * dt
        
        # Actualizar velocidad vertical (gravedad)
        vy -= g * dt
        
        # Detectar colisión con el suelo
        if ball.pos.y <= ball_radius + floor_y and vy < 0:
            # Aplicar rebote con coeficiente de restitución
            vy = -vy * restitution
            vx *= 0.93  # Reducción de velocidad horizontal
            vz *= 0.93
            ball.pos.y = ball_radius + floor_y
            bounce_count += 1
            
            # Cambiar color de la pelota en cada rebote (ciclo de colores pastel)
            ball.color = pastel_colors[bounce_count % len(pastel_colors)]
            
            # Actualizar información
            info_text.text = f"ALTURA: {ball.pos.y:.1f} m\nVELOCIDAD Y: {vy:.1f} m/s\nREBOTES: {bounce_count}"
            print(f"Rebote #{bounce_count:2d} - Velocidad Y: {vy:6.2f} m/s - Altura: {ball.pos.y:5.1f} m")
        
        # Rebotes en los bordes de la plataforma
        if abs(ball.pos.x) > 9.5:
            vx = -vx * 0.85
            ball.pos.x = 9.5 if ball.pos.x > 0 else -9.5
            print(f"Rebote en borde X - Velocidad X: {vx:.2f} m/s")
        
        if abs(ball.pos.z) > 9.5:
            vz = -vz * 0.85
            ball.pos.z = 9.5 if ball.pos.z > 0 else -9.5
            print(f"Rebote en borde Z - Velocidad Z: {vz:.2f} m/s")

except KeyboardInterrupt:
    print("\nSimulación interrumpida por el usuario")

# Mensaje final con color pastel
final_text = label(
    pos=vector(0, 22, 0),
    text=f"SIMULACIÓN COMPLETADA\nTOTAL REBOTES: {bounce_count}",
    color=pastel_color(128, 77, 153),  # púrpura pastel
    height=22,
    box=False
)

print("=" * 50)
print(f"SIMULACIÓN TERMINADA")
print(f"Total de rebotes: {bounce_count}")
print(f"Posición final: X={ball.pos.x:.1f}, Y={ball.pos.y:.1f}, Z={ball.pos.z:.1f}")
print(f"Velocidad final: X={vx:.1f}, Y={vy:.1f}, Z={vz:.1f} m/s")
print("=" * 50)
