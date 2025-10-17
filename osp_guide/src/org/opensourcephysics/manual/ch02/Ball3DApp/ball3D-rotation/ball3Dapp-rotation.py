from vpython import sphere, box, vector, color, rate, cylinder, label, scene, distant_light, arrow, ring, cross, dot, mag
import numpy as np
import math

# Parámetros iniciales
g = 9.8                      # gravedad (m/s^2)
dt = 0.01                    # paso de tiempo (s)
restitution = 0.9            # coeficiente de restitución
floor_y = 0                  # posición del suelo (y)
ball_radius = 1.0            # radio aumentado
ball_init_y = 15.0           # posición inicial más alta

# Configuración de la escena
scene.background = color.white
scene.center = vector(0, 10, 0)
scene.range = 20
scene.width = 1000
scene.height = 800
scene.title = "Simulación 3D con Rotación de Partícula"

# Función para crear colores pastel (RGB normalizado)
def pastel_color(r, g, b):
    return vector(r/255.0, g/255.0, b/255.0)

# Colores pastel
ball_color = pastel_color(153, 204, 255)       # azul pastel
platform_color = pastel_color(255, 179, 204)   # rosa pastel
platform_edge_color = pastel_color(230, 128, 153) # rosa pastel oscuro
trail_color = pastel_color(102, 153, 204)      # azul pastel para rastro

# Configurar iluminación
distant_light(direction=vector(1, 1, 1), color=color.gray(0.7))
distant_light(direction=vector(-1, -1, -1), color=color.gray(0.3))

# Crear pelota con marcadores de rotación
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

# Marcadores de rotación (ejes locales)
marker_x = arrow(pos=ball.pos, axis=vector(ball_radius, 0, 0), 
                color=pastel_color(255, 153, 179), shaftwidth=0.1)
marker_y = arrow(pos=ball.pos, axis=vector(0, ball_radius, 0), 
                color=pastel_color(153, 230, 153), shaftwidth=0.1)
marker_z = arrow(pos=ball.pos, axis=vector(0, 0, ball_radius), 
                color=pastel_color(153, 179, 255), shaftwidth=0.1)

# Anillo ecuatorial para visualizar rotación
equator = ring(
    pos=ball.pos,
    axis=vector(0, 1, 0),
    radius=ball_radius * 1.1,
    thickness=0.05,
    color=pastel_color(255, 230, 102),  # amarillo pastel
    opacity=0.6
)

# Plataforma con bordes decorativos
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

# Ejes de referencia 3D con colores pastel
cylinder(pos=vector(-12, 0, 0), axis=vector(24, 0, 0), radius=0.15, color=pastel_color(255, 153, 179))
cylinder(pos=vector(0, -2, 0), axis=vector(0, 30, 0), radius=0.15, color=pastel_color(153, 230, 153))
cylinder(pos=vector(0, 0, -12), axis=vector(0, 0, 24), radius=0.15, color=pastel_color(153, 179, 255))

# Rejilla en el plano XZ
for i in range(-10, 11, 2):
    cylinder(pos=vector(-10, floor_y, i), axis=vector(20, 0, 0), radius=0.08, color=pastel_color(179, 230, 179), opacity=0.5)
    cylinder(pos=vector(i, floor_y, -10), axis=vector(0, 0, 20), radius=0.08, color=pastel_color(179, 230, 179), opacity=0.5)

# Etiquetas de ejes
label(pos=vector(13, 0, 0), text="X", color=pastel_color(230, 102, 128), height=25, box=False)
label(pos=vector(0, 31, 0), text="Y", color=pastel_color(102, 204, 102), height=25, box=False)
label(pos=vector(0, 0, 13), text="Z", color=pastel_color(102, 128, 230), height=25, box=False)

# Variables de estado con rotación
class ParticleState:
    def __init__(self):
        # Posición y velocidad lineal
        self.position = vector(0, ball_init_y, 0)
        self.velocity = vector(3.0, 0.0, 2.5)
        
        # Rotación (cuaterniones para evitar bloqueo de cardán)
        self.quaternion = [1, 0, 0, 0]  # [w, x, y, z]
        
        # Velocidad angular (radianes/segundo)
        self.angular_velocity = vector(0, 0, 0)
        
        # Propiedades físicas
        self.mass = 1.0
        self.radius = ball_radius
        self.inertia = (2/5) * self.mass * self.radius**2  # Esfera sólida
        
        # Historial
        self.bounce_count = 0

# Inicializar estado
state = ParticleState()

# Funciones matemáticas para rotación
def quaternion_multiply(q1, q2):
    """Multiplicación de cuaterniones"""
    w1, x1, y1, z1 = q1
    w2, x2, y2, z2 = q2
    return [
        w1*w2 - x1*x2 - y1*y2 - z1*z2,
        w1*x2 + x1*w2 + y1*z2 - z1*y2,
        w1*y2 - x1*z2 + y1*w2 + z1*x2,
        w1*z2 + x1*y2 - y1*x2 + z1*w2
    ]

def quaternion_rotate_vector(q, v):
    """Rotar vector por cuaternión"""
    q_vec = [0, v.x, v.y, v.z]
    q_conj = [q[0], -q[1], -q[2], -q[3]]
    
    # q * v * q_conj
    temp = quaternion_multiply(q, q_vec)
    result = quaternion_multiply(temp, q_conj)
    
    return vector(result[1], result[2], result[3])

def update_rotation(state, dt):
    """Actualizar rotación usando cuaterniones"""
    # Velocidad angular en el sistema de referencia local
    omega_local = state.angular_velocity
    
    # Convertir a cuaternión de velocidad angular
    omega_magnitude = mag(omega_local)
    if omega_magnitude > 1e-10:
        omega_normalized = omega_local / omega_magnitude
        theta = omega_magnitude * dt / 2
        
        dq = [math.cos(theta), 
              math.sin(theta) * omega_normalized.x,
              math.sin(theta) * omega_normalized.y, 
              math.sin(theta) * omega_normalized.z]
        
        # Actualizar cuaternión
        state.quaternion = quaternion_multiply(dq, state.quaternion)
        
        # Normalizar cuaternión (evitar deriva numérica)
        q_norm = math.sqrt(sum(q**2 for q in state.quaternion))
        state.quaternion = [q/q_norm for q in state.quaternion]

def apply_impulse(state, impulse_point, impulse_vector):
    """Aplicar impulso que causa rotación"""
    # Vector del centro al punto de aplicación
    r = impulse_point - state.position
    
    # Torque: τ = r × F
    torque = cross(r, impulse_vector)
    
    # Aceleración angular: α = τ / I
    angular_acceleration = torque / state.inertia
    
    # Actualizar velocidad angular
    state.angular_velocity += angular_acceleration * dt

def handle_collision(state, collision_normal, collision_point):
    """Manejar colisión con rotación"""
    # Velocidad en el punto de colisión
    v_collision_point = state.velocity + cross(state.angular_velocity, 
                                             collision_point - state.position)
    
    # Componente normal de la velocidad
    v_normal = dot(v_collision_point, collision_normal) * collision_normal
    
    if dot(v_normal, collision_normal) < 0:  # Acercándose
        # Coeficiente de restitución
        impulse_magnitude = -(1 + restitution) * dot(v_collision_point, collision_normal)
        impulse_magnitude /= (1/state.mass + state.inertia/mag(collision_point - state.position)**2)
        
        impulse_vector = impulse_magnitude * collision_normal
        
        # Aplicar impulso al centro de masa
        state.velocity += impulse_vector / state.mass
        
        # Aplicar impulso para rotación
        apply_impulse(state, collision_point, impulse_vector)
        
        # Corregir posición
        penetration = state.radius - dot(state.position - collision_point, collision_normal)
        if penetration > 0:
            state.position += collision_normal * penetration
        
        state.bounce_count += 1
        return True
    return False

def update_visual_rotation(state):
    """Actualizar marcadores visuales de rotación"""
    # Rotar marcadores de ejes
    marker_x.axis = quaternion_rotate_vector(state.quaternion, vector(ball_radius, 0, 0))
    marker_y.axis = quaternion_rotate_vector(state.quaternion, vector(0, ball_radius, 0))
    marker_z.axis = quaternion_rotate_vector(state.quaternion, vector(0, 0, ball_radius))
    
    # Actualizar posiciones
    marker_x.pos = state.position
    marker_y.pos = state.position
    marker_z.pos = state.position
    
    # Rotar anillo ecuatorial
    equator.axis = quaternion_rotate_vector(state.quaternion, vector(0, 1, 0))
    equator.pos = state.position

# Información de visualización
info_text = label(
    pos=vector(-18, 28, 0),
    text=f"ALTURA: {state.position.y:.1f} m\n"
         f"VEL Y: {state.velocity.y:.1f} m/s\n"
         f"REBOTES: {state.bounce_count}\n"
         f"ω: ({state.angular_velocity.x:.1f}, {state.angular_velocity.y:.1f}, {state.angular_velocity.z:.1f}) rad/s",
    color=pastel_color(77, 77, 128),
    height=14,
    box=False
)

# Texto de instrucciones
instructions = label(
    pos=vector(0, -5, 0),
    text="Rotación activada - Física mejorada",
    color=pastel_color(128, 77, 153),
    height=15,
    box=False
)

print("=" * 60)
print("SIMULACIÓN 3D CON ROTACIÓN DE PARTÍCULA")
print("=" * 60)
print("Física implementada:")
print("- Rotación con cuaterniones (sin bloqueo de cardán)")
print("- Momento de inercia para esfera sólida")
print("- Conservación de momento angular")
print("- Colisiones con transferencia de energía rotacional")
print("=" * 60)

# Agregar velocidad angular inicial para demostración
state.angular_velocity = vector(5, 3, 2)  # rad/s

# Colores pastel para el ciclo de rebotes
pastel_colors = [
    pastel_color(153, 204, 255),  # azul claro
    pastel_color(204, 179, 255),  # lila
    pastel_color(255, 204, 179),  # melocotón
    pastel_color(204, 255, 179),  # verde menta
    pastel_color(255, 179, 204)   # rosa
]

try:
    while state.bounce_count < 25:
        rate(100)
        
        # === MOVIMIENTO LINEAL ===
        # Gravedad
        state.velocity.y -= g * dt
        
        # Actualizar posición
        state.position += state.velocity * dt
        
        # === ROTACIÓN ===
        update_rotation(state, dt)
        
        # === COLISIONES ===
        # Colisión con el suelo
        if state.position.y <= state.radius and state.velocity.y < 0:
            collision_point = vector(state.position.x, 0, state.position.z)
            collision_normal = vector(0, 1, 0)
            if handle_collision(state, collision_normal, collision_point):
                # Cambiar color de la pelota en cada rebote
                ball.color = pastel_colors[state.bounce_count % len(pastel_colors)]
                print(f"Rebote #{state.bounce_count} - "
                      f"Velocidad: {mag(state.velocity):.2f} m/s - "
                      f"Velocidad Angular: {mag(state.angular_velocity):.2f} rad/s")
        
        # Colisiones con bordes
        if abs(state.position.x) > 9.5:
            side = 1 if state.position.x > 0 else -1
            collision_point = vector(9.5 * side, state.position.y, state.position.z)
            collision_normal = vector(-side, 0, 0)
            handle_collision(state, collision_normal, collision_point)
        
        if abs(state.position.z) > 9.5:
            side = 1 if state.position.z > 0 else -1
            collision_point = vector(state.position.x, state.position.y, 9.5 * side)
            collision_normal = vector(0, 0, -side)
            handle_collision(state, collision_normal, collision_point)
        
        # === ACTUALIZAR VISUALIZACIÓN ===
        ball.pos = state.position
        update_visual_rotation(state)
        
        # Actualizar información
        info_text.text = (f"ALTURA: {state.position.y:.1f} m\n"
                         f"VEL Y: {state.velocity.y:.1f} m/s\n"
                         f"REBOTES: {state.bounce_count}\n"
                         f"ω: ({state.angular_velocity.x:.1f}, "
                         f"{state.angular_velocity.y:.1f}, "
                         f"{state.angular_velocity.z:.1f}) rad/s")

except KeyboardInterrupt:
    print("\nSimulación interrumpida por el usuario")

# Mensaje final
final_text = label(
    pos=vector(0, 22, 0),
    text=f"SIMULACIÓN COMPLETADA\n"
         f"TOTAL REBOTES: {state.bounce_count}\n"
         f"VELOCIDAD ANGULAR FINAL: {mag(state.angular_velocity):.2f} rad/s",
    color=pastel_color(128, 77, 153),
    height=18,
    box=False
)

print("=" * 60)
print("RESULTADOS FINALES:")
print(f"Total de rebotes: {state.bounce_count}")
print(f"Posición final: ({state.position.x:.1f}, {state.position.y:.1f}, {state.position.z:.1f})")
print(f"Velocidad lineal final: ({state.velocity.x:.1f}, {state.velocity.y:.1f}, {state.velocity.z:.1f}) m/s")
print(f"Velocidad angular final: ({state.angular_velocity.x:.1f}, {state.angular_velocity.y:.1f}, {state.angular_velocity.z:.1f}) rad/s")
print(f"Energía rotacional: {0.5 * state.inertia * mag(state.angular_velocity)**2:.2f} J")
print("=" * 60)