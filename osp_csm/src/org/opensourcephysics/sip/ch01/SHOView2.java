package org.opensourcephysics.sip.ch01;
import org.opensourcephysics.controls.AbstractAnimation;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.Stripchart;
import org.opensourcephysics.display.axes.XAxis;

/**
 * SHOView2 draws the oscillator and counts complete oscillations.
 */
public class SHOView2 extends AbstractAnimation {
  PlottingPanel plot = new PlottingPanel("time", "x", "x(t)");
  DrawingFrame plottingFrame = new DrawingFrame("SHO Data", plot);
  DrawingPanel drawing = new DrawingPanel();
  DrawingFrame drawingFrame = new DrawingFrame("SHO Simulation", drawing);
  Dataset stripChart = new Stripchart(20, 10); // strip chart of x(t)
  SHOModel sho = new SHOModel();               // harmonic oscillator
  double previousX;
  int equilibriumCrossings;
  int completedOscillations;

  /**
   * Constructs SHOView2.
   */
  public SHOView2() {
    drawing.setPreferredMinMax(-5, 5, -1, 1);
    drawing.addDrawable(new XAxis("x"));
    drawing.addDrawable(sho);
    drawingFrame.setSize(300, 150);
    drawingFrame.setVisible(true);
    plot.addDrawable(stripChart);
    plottingFrame.setSize(300, 375);
    plottingFrame.setVisible(true);
  }

  /**
   * Initializes the animation.
   */
  public void initializeAnimation() {
    super.initializeAnimation();
    double x = control.getDouble("x0");
    double v = control.getDouble("v0");
    sho.initialize(x, v, 0);
    previousX = x;
    equilibriumCrossings = 0;
    completedOscillations = 0;
    drawing.setMessage("t=0, vueltas=0");
    stripChart.clear();
    stripChart.append(0, x);
    drawing.repaint();
    plot.repaint();
  }

  /**
   * Does an animation step.
   */
  protected void doStep() {
    sho.move(); // moves the particle
    double currentX = sho.getX();
    if (currentX != 0 && previousX != 0 &&
        ((previousX < 0 && currentX > 0) || (previousX > 0 && currentX < 0))) {
      equilibriumCrossings++;
      completedOscillations = equilibriumCrossings / 2;
    }
    if (currentX != 0) {
      previousX = currentX;
    }
    stripChart.append(sho.getTime(), currentX);
    drawing.setMessage("t="+decimalFormat.format(sho.getTime())+
        ", vueltas="+completedOscillations);
    drawing.repaint();
    plot.repaint();
  }

  /**
   * Resets the animation to a predefined state.
   */
  public void resetAnimation() {
    super.resetAnimation();
    control.setValue("x0", 4);
    control.setValue("v0", 0);
    initializeAnimation();
  }
}