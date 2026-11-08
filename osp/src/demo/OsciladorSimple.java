package demo;

import javax.swing.JFrame;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.frames.PlotFrame;

public class OsciladorSimple extends AbstractSimulation {
  private PlotFrame frame;
  private double x;      // posición
  private double v;      // velocidad
  private double m;      // masa
  private double k;      // constante elástica
  private double time;

  public OsciladorSimple() {
    frame = new PlotFrame("tiempo", "x(t)", "Oscilador armónico");
    frame.setSize(500, 400);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void initialize() {
    x = 1.0;      // condición inicial
    v = 0.0;
    m = 1.0;
    k = 4.0;
    time = 0.0;

    frame.clearData();
    frame.setPreferredMinMax(0, 20, -2, 2);
    frame.append(0, time, x);
  }

  @Override
  public void reset() {
    initialize();
  }

  @Override
  public void doStep() {
    double a = -(k / m) * x;  // aceleración: a = -k/m * x
    v = v + a * 0.02;
    x = x + v * 0.02;
    time += 0.02;

    // Se agrega el punto a la gráfica
    frame.append(0, time, x);

    // Para no saturar la ventana, se puede limitar la cantidad de puntos
    if (time > 20) {
      frame.clearData();
      time = 0;
    }
  }

  public static void main(String[] args) {
    OsciladorSimple sim = new OsciladorSimple();
    sim.initialize();
    sim.startSimulation();
  }
}