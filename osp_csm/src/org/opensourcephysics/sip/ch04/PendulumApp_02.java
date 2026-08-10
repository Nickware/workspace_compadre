package org.opensourcephysics.sip.ch04;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.display.*;
import javax.swing.JFrame;
import java.awt.GridLayout;

/**
 * PendulumApp solves and displays the time evolution of a pendulum by stepping a pendulum model.
 * Extendido para mostrar energía cinética, potencial y total en una sola ventana con tres secciones,
 * con escalas Y comparables y trazo continuo sin marcadores.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.2
 */
public class PendulumApp_02 extends AbstractSimulation {
  PlotFrame plotFrame = new PlotFrame("Time", "Theta", "Theta versus time");
  Pendulum pendulum = new Pendulum();
  DisplayFrame displayFrame = new DisplayFrame("Pendulum");

  // --- Ventana de energía: un solo JFrame, tres paneles apilados ---
  JFrame energyFrame = new JFrame("Energía del péndulo");
  PlottingPanel kineticPanel   = new PlottingPanel("Time", "KE", "Energía cinética");
  PlottingPanel potentialPanel = new PlottingPanel("Time", "PE", "Energía potencial");
  PlottingPanel totalPanel     = new PlottingPanel("Time", "E total", "Energía total");
  Dataset kineticData   = new Dataset();
  Dataset potentialData = new Dataset();
  Dataset totalData     = new Dataset();

  // Parámetros físicos asumidos (modelo normalizado m = L = 1); ajustar si tu Pendulum
  // define masa, longitud o gravedad distintos.
  double mass = 1.0;
  double length = 1.0;
  double g = 9.8;

  /**
   * Constructs the PendulumApp and intializes the display.
   */
  public PendulumApp_02() {
    displayFrame.addDrawable(pendulum);
    displayFrame.setPreferredMinMax(-1.2, 1.2, -1.2, 1.2);

    // Trazo continuo, sin marcadores de punto (0 = NO_MARKER)
    kineticData.setConnected(true);
    kineticData.setMarkerShape(0);
    potentialData.setConnected(true);
    potentialData.setMarkerShape(0);
    totalData.setConnected(true);
    totalData.setMarkerShape(0);

    kineticPanel.addDrawable(kineticData);
    potentialPanel.addDrawable(potentialData);
    totalPanel.addDrawable(totalData);

    energyFrame.setLayout(new GridLayout(3, 1));
    energyFrame.add(kineticPanel);
    energyFrame.add(potentialPanel);
    energyFrame.add(totalPanel);
    energyFrame.setSize(420, 720);
    energyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    energyFrame.setVisible(true);
  }

  /**
   * Initializes the simulation.
   */
  public void initialize() {
    double dt = control.getDouble("dt");
    double theta = control.getDouble("initial theta");
    double thetaDot = control.getDouble("initial dtheta/dt");
    pendulum.setState(theta, thetaDot);
    pendulum.setStepSize(dt);

    kineticData.clear();
    potentialData.clear();
    totalData.clear();

    // Energía total esperada según las condiciones iniciales (línea recta teórica,
    // sin disipación): sirve como techo común para las tres escalas Y.
    double ke0 = 0.5 * mass * length * length * thetaDot * thetaDot;
    double pe0 = mass * g * length * (1 - Math.cos(theta));
    double eMax = (ke0 + pe0) * 1.15; // 15% de margen visual
    if(eMax < 1e-6) eMax = 1.0; // evita rango cero si parte en reposo y theta=0

    kineticPanel.setAutoscaleX(true);
    kineticPanel.setPreferredMinMaxY(0, eMax);
    potentialPanel.setAutoscaleX(true);
    potentialPanel.setPreferredMinMaxY(0, eMax);
    totalPanel.setAutoscaleX(true);
    totalPanel.setPreferredMinMaxY(0, eMax);
  }

  /**
   * Does a time step.
   */
  public void doStep() {
    double theta    = pendulum.state[0];
    double thetaDot = pendulum.state[1];
    double t        = pendulum.state[2];

    plotFrame.append(0, t, theta); // angle vs time data added

    double ke = 0.5 * mass * length * length * thetaDot * thetaDot;
    double pe = mass * g * length * (1 - Math.cos(theta));
    double total = ke + pe;

    kineticData.append(t, ke);
    potentialData.append(t, pe);
    totalData.append(t, total);

    pendulum.step(); // advances the state by one time step
  }

  /**
   * Resets the simulation.
   */
  public void reset() {
    pendulum.state[2] = 0; // set time = 0
    control.setValue("initial theta", 0.2);
    control.setValue("initial dtheta/dt", 0);
    control.setValue("dt", 0.1);
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) { // creates a simulation control structure using this class
    SimulationControl.createApp(new PendulumApp_02());
  }
}