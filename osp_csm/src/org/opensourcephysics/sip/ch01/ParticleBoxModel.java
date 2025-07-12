package org.opensourcephysics.sip.ch01;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK4;
import java.awt.Color;

public class ParticleBoxModel extends Circle implements ODE {
    private double[] state = {0, 0, 0, 0, 0}; // x, y, vx, vy, t
    private double size = 10; // Tamaño de la caja
    private double restitution = 0.9;
    private ODESolver solver = new RK4(this);
    
    public ParticleBoxModel() {
        this.pixRadius = 5; // Tamaño visual
        this.color = Color.BLUE;
    }

    public void initialize(double x, double y, double vx, double vy, double t) {
        state[0] = x;
        state[1] = y;
        state[2] = vx;
        state[3] = vy;
        state[4] = t;
        setXY(x, y);
    }

    public void move() {
        solver.step();
        handleCollisions();
        setXY(state[0], state[1]);
    }

    private void handleCollisions() {
        // Lógica de colisiones con las paredes
        double radius = 0.5;
        if(state[0] < -size/2 + radius) {
            state[0] = -size/2 + radius;
            state[2] *= -restitution;
        }
        if(state[0] > size/2 - radius) {
            state[0] = size/2 - radius;
            state[2] *= -restitution;
        }
        if(state[1] < -size/2 + radius) {
            state[1] = -size/2 + radius;
            state[3] *= -restitution;
        }
        if(state[1] > size/2 - radius) {
            state[1] = size/2 - radius;
            state[3] *= -restitution;
        }
    }

    // Métodos de ODE
    public double[] getState() { return state; }
    
    public void getRate(double[] state, double[] rate) {
        rate[0] = state[2]; // dx/dt = vx
        rate[1] = state[3]; // dy/dt = vy
        rate[2] = 0;        // dvx/dt = 0
        rate[3] = 0;        // dvy/dt = 0
        rate[4] = 1;        // dt/dt = 1
    }

    public double getTime() { return state[4]; }
    
    
    
    
}
