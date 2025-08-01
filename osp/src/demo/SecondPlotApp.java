package demo;
import javax.swing.JFrame;
import org.opensourcephysics.frames.PlotFrame;

public class FirstPlotApp {
  public static void main(String[] args) {
    PlotFrame frame = new PlotFrame("posición", "amplitud", "Segunda Grafica"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    frame.setSize(400, 400);
    for(double x = -10, dx = 0.1; x<10; x += dx) {
      frame.append(0, x, Math.cos(x));
    }
    //frame.setAutoscaleX(false);
    //frame.setAutoscaleY(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //DiagnosticsForThreads.aboutThreads();
  }
}
