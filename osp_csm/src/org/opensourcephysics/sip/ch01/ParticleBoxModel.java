package org.opensourcephysics.sip.ch01;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK4; // Podríamos usar Euler si quisieramos simplificar, pero RK4 es más general.

/**
 * ParticleBoxModel define una partícula que rebota en una caja
 * con disipación de energía al chocar.
 * La caja está definida por límites implícitos.
 */

public class ParticleBoxModel extends Circle implements ODE {
    // Estado de la partícula: {x, y, vx, vy, t}
    double[] state = new double[] {0.0, 0.0, 0.0, 0.0, 0.0}; // x, y, vx, vy, t
    double mass = 1.0; // Masa de la partícula
    double radius = 0.5; // Radio de la partícula (asumiendo que es un Circle)
    double restitution = 0.9; // Coeficiente de restitución (0 a 1), 1 = sin pérdida, <1 = pérdida de energía.
    double xMin = -5.0, xMax = 5.0; // Límites de la caja en X
    double yMin = -5.0, yMax = 5.0; // Límites de la caja en Y

    ODESolver ode_solver = new RK4(this); // Solucionador de ecuaciones diferenciales

    /**
     * Constructor para ParticleBoxModel.
     */
    public ParticleBoxModel() {
        setRadius(radius); // Establece el radio del círculo visual.
        setColor(java.awt.Color.BLUE); // Color de la partícula.
    }

    /**
     * Inicializa la posición, velocidad y tiempo de la partícula.
     * @param x Posición inicial en X
     * @param y Posición inicial en Y
     * @param vx Velocidad inicial en X
     * @param vy Velocidad inicial en Y
     * @param t Tiempo inicial
     */
    public void initialize(double x, double y, double vx, double vy, double t) {
        // Actualiza el estado interno
        state[0] = x;
        state[1] = y;
        state[2] = vx;
        state[3] = vy;
        state[4] = t;

        // Actualiza la posición visual del círculo
        setXY(x, y);
    }

    /**
     * Obtiene el tiempo actual.
     * @return Tiempo actual
     */
    public double getTime() {
        return state[4];
    }

    /**
     * Obtiene la velocidad en X.
     * @return Velocidad en X
     */
    public double getVx() {
        return state[2];
    }

    /**
     * Obtiene la velocidad en Y.
     * @return Velocidad en Y
     */
    public double getVy() {
        return state[3];
    }

    /**
     * Obtiene el arreglo de estado.
     * @return Un arreglo que contiene {x, y, vx, vy, t}
     */
    @Override
    public double[] getState() {
        return state;
    }

    /**
     * Calcula las tasas de cambio (derivadas) para el solucionador ODE.
     * dx/dt = vx
     * dy/dt = vy
     * dvx/dt = 0 (no hay fuerzas horizontales entre choques)
     * dvy/dt = 0 (no hay fuerzas verticales entre choques)
     * dt/dt = 1
     * @param stateArr El arreglo de estado actual
     * @param rateArr El arreglo de tasas de cambio a llenar
     */
    @Override
    public void getRate(double[] stateArr, double[] rateArr) {
        rateArr[0] = stateArr[2]; // dx/dt = vx
        rateArr[1] = stateArr[3]; // dy/dt = vy
        rateArr[2] = 0.0;         // dvx/dt = 0 (no hay aceleración en X)
        rateArr[3] = 0.0;         // dvy/dt = 0 (no hay aceleración en Y)
        rateArr[4] = 1.0;         // dt/dt = 1
    }

    /**
     * Mueve la partícula un paso de tiempo y maneja los rebotes.
     */
    public void move() {
        double oldX = state[0];
        double oldY = state[1];

        ode_solver.step(); // Calcula el nuevo estado después de un dt

        // Obtiene las nuevas posiciones y velocidades
        double newX = state[0];
        double newY = state[1];
        double vx = state[2];
        double vy = state[3];

        // --- Lógica de rebote y disipación de energía ---

        // Rebote en pared derecha o izquierda
        if (newX + radius > xMax) { // Si la partícula excedió el límite derecho
            state[0] = xMax - radius; // Coloca la partícula justo en el límite
            state[2] = -vx * restitution; // Invierte la velocidad X y aplica disipación
        } else if (newX - radius < xMin) { // Si la partícula excedió el límite izquierdo
            state[0] = xMin + radius; // Coloca la partícula justo en el límite
            state[2] = -vx * restitution; // Invierte la velocidad X y aplica disipación
        }

        // Rebote en pared superior o inferior
        if (newY + radius > yMax) { // Si la partícula excedió el límite superior
            state[1] = yMax - radius; // Coloca la partícula justo en el límite
            state[3] = -vy * restitution; // Invierte la velocidad Y y aplica disipación
        } else if (newY - radius < yMin) { // Si la partícula excedió el límite inferior
            state[1] = yMin + radius; // Coloca la partícula justo en el límite
            state[3] = -vy * restitution; // Invierte la velocidad Y y aplica disipación
        }

        // Asegúrate de que las coordenadas visuales del círculo coincidan con el estado
        setXY(state[0], state[1]);
    }
}
