package org.opensourcephysics.demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.Control;
import org.opensourcephysics.frames.PlotFrame;

public class OsciladorConControl extends AbstractSimulation {
  private PlotFrame frame;
  private Control control;

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
    frame.setXRange(0, 20);
    frame.setYRange(-2, 2);
  }

  @Override
  public void initialize() {
    x = control.getDouble("posicion inicial");
    v = control.getDouble("velocidad inicial");
    m = control.getDouble("masa");
    k = control.getDouble("constante k");
    dt = control.getDouble("dt");

    time = 0;
    frame.clearData();
    frame.append(0, time, x);
  }

  @Override
  public void reset() {
    initialize();
  }

  @Override
  public void start() {
    // se puede hacer algo antes del inicio, si hace falta
  }

  @Override
  public void doStep() {
    double a = -(k / m) * x;  // a = -k/m * x
    v = v + a * dt;
    x = x + v * dt;
    time += dt;

    frame.append(0, time, x);

    // si se pasa de cierto tiempo, se limpia para evitar saturar la gráfica
    if (time > 20) {
      frame.clearData();
      time = 0;
    }
  }

  @Override
  public void setControl(Control control) {
    super.setControl(control);
    this.control = control;

    if (control != null) {
      control.setValue("posicion inicial", 1.0);
      control.setValue("velocidad inicial", 0.0);
      control.setValue("masa", 1.0);
      control.setValue("constante k", 4.0);
      control.setValue("dt", 0.02);
    }
  }

  public static void main(String[] args) {
    OsciladorConControl sim = new OsciladorConControl();

    // Esto es lo más cercano al patrón OSP:
    // se crea un control y se inyecta al modelo
    Control control = new Control() {
      private java.util.Map<String, Double> values = new java.util.HashMap<>();

      public void setValue(String name, double value) {
        values.put(name, value);
      }

      public double getDouble(String name) {
        return values.getOrDefault(name, 0.0);
      }

      public void setValue(String name, String value) {}
      public String getString(String name) { return ""; }
      public int getInt(String name) { return 0; }
      public boolean getBoolean(String name) { return false; }
      public void setAdjustableValue(String name, double value) {}
      public void setAdjustableValue(String name, int value) {}
      public void setAdjustableValue(String name, boolean value) {}
      public void setLockValues(boolean lock) {}
      public void setValue(String name, int value) {}
      public void setValue(String name, boolean value) {}
      public void clear() {}
      public void remove(String name) {}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener l) {}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener l) {}
      public String[] getPropertyNames() { return new String[0]; }
      public void setProperties(java.util.Properties props) {}
      public java.util.Properties getProperties() { return new java.util.Properties(); }
      public boolean isLocked() { return false; }
      public void setFrame(java.awt.Frame frame) {}
      public void load(String fileName) {}
      public void save(String fileName) {}
      public void setVisible(boolean b) {}
      public void update() {}
      public void setEnabled(boolean enabled) {}
      public void dispose() {}
      public void setTitle(String title) {}
      public void setSize(int width, int height) {}
      public void setLocation(int x, int y) {}
      public void setResizable(boolean resizable) {}
      public void setAlwaysOnTop(boolean onTop) {}
      public void setBackground(java.awt.Color color) {}
      public void setForeground(java.awt.Color color) {}
      public void addActionListener(ActionListener l) {}
      public void removeActionListener(ActionListener l) {}
      public void fireActionEvent(ActionEvent e) {}
    };

    sim.setControl(control);
    sim.initialize();
    sim.startSimulation();
  }
}