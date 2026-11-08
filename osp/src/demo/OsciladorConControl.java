package demo;

import javax.swing.JFrame;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.PlotFrame;

public class OsciladorConControl extends AbstractSimulation {
  private PlotFrame frame;

  private double x;      // posición
  private double v;      // velocidad
  private double time;   // tiempo
  private double m;      // masa
  private double k;      // constante elástica
  private double dt;     // paso de integración

  public OsciladorConControl() {
    frame = new PlotFrame("tiempo", "x(t)", "Oscilador armónico");
    frame.setSize(550, 420);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredMinMax(0, 20, -2, 2);

    SimulationControl control = new SimulationControl(this);
    control.setValue("posicion inicial", 1.0);
    control.setValue("velocidad inicial", 0.0);
    control.setValue("masa", 1.0);
    control.setValue("constante k", 4.0);
    control.setValue("dt", 0.02);
    setControl(control);
  }

  @Override
  public void initialize() {
    x = getControl().getDouble("posicion inicial");
    v = getControl().getDouble("velocidad inicial");
    m = getControl().getDouble("masa");
    k = getControl().getDouble("constante k");
    dt = getControl().getDouble("dt");

    time = 0;
    frame.clearData();
    frame.append(0, time, x);
  }

  @Override
  public void reset() {
    initialize();
  }

  @Override
  public void doStep() {
    double a = -(k / m) * x;
    v = v + a * dt;
    x = x + v * dt;
    time += dt;

    frame.append(0, time, x);

    if (time > 20) {
      frame.clearData();
      time = 0;
    }
  }

  public static void main(String[] args) {
    OsciladorConControl sim = new OsciladorConControl();
    sim.initialize();
    sim.startSimulation();
  }
}