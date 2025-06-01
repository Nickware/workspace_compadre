package org.opensourcephysics.sip.ch01;
import org.opensourcephysics.controls.AbstractAnimation;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.Stripchart;
import org.opensourcephysics.display.axes.XAxis; // Podríamos añadir ejes si queremos
import org.opensourcephysics.display.axes.YAxis; // Para ejes en 2D

import java.text.DecimalFormat; // Para formatear el tiempo

/**
 * ParticleBoxView dibuja la partícula rebotando en una caja
 * y opcionalmente puede graficar su energía o velocidad.
 * Reutiliza la estructura de SHOView.
 */
public class ParticleBoxView extends AbstractAnimation {
    // Panels y Frames para la visualización
    DrawingPanel drawing = new DrawingPanel();
    DrawingFrame drawingFrame = new DrawingFrame("Particle in a Box Simulation", drawing);

    // Opcional: Para graficar la energía cinética (similar al stripchart de SHO)
    // PlottingPanel energyPlot = new PlottingPanel("Time", "Kinetic Energy", "Energy vs. Time");
    // DrawingFrame energyPlottingFrame = new DrawingFrame("Kinetic Energy", energyPlot);
    // Stripchart energyStripChart = new Stripchart(20, 100); // 20 puntos, y-range up to 100

    ParticleBoxModel particle = new ParticleBoxModel(); // Nuestra partícula

    // Formateador para el tiempo
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    /**
     * Constructor para ParticleBoxView. Configura los paneles y añade la partícula.
     */
    public ParticleBoxView() {
        // Configura el panel de dibujo para nuestra caja
        // Los límites deben coincidir con los de ParticleBoxModel, pero OSPDisplay usa el centro.
        // Si el modelo usa -5 a 5, el panel también.
        drawing.setPreferredMinMax(particle.xMin, particle.xMax, particle.yMin, particle.yMax);
        drawing.addDrawable(particle); // Añade la partícula al panel para que sea dibujada
        drawingFrame.setSize(500, 500); // Tamaño de la ventana de simulación
        drawingFrame.setVisible(true);

        // Opcional: Configuración del gráfico de energía
        // energyPlot.addDrawable(energyStripChart);
        // energyPlottingFrame.setSize(500, 250);
        // energyPlottingFrame.setVisible(true);

        // Añadir parámetros de control al OSPControl
        control.setValue("x0", 0.0);
        control.setValue("y0", 0.0);
        control.setValue("vx0", 2.0);
        control.setValue("vy0", 3.0);
        control.setValue("restitution", particle.restitution); // Permitir al usuario cambiar el coeficiente.
        control.setValue("radius", particle.radius);           // Permitir cambiar el radio.

        // Añadir listeners para los parámetros que el usuario puede cambiar
        // control.addPropertyChangeListener("restitution", e -> particle.restitution = control.getDouble("restitution"));
        // control.addPropertyChangeListener("radius", e -> {
        //    particle.radius = control.getDouble("radius");
        //    particle.setRadius(particle.radius); // Actualiza el radio visual
        // });
    }

    /**
     * Inicializa la animación con los valores del control.
     */
    @Override
    public void initializeAnimation() {
        super.initializeAnimation(); // Llama a la inicialización base de AbstractAnimation

        // Obtiene los valores iniciales del control
        double x0 = control.getDouble("x0");
        double y0 = control.getDouble("y0");
        double vx0 = control.getDouble("vx0");
        double vy0 = control.getDouble("vy0");
        
        // Lee estos valores del control
        double restitution = control.getDouble("restitution");
        double radius = control.getDouble("radius");

        // Inicializa la partícula con estos valores
        particle.initialize(x0, y0, vx0, vy0, 0); // Siempre inicia en t=0
        particle.restitution = restitution; // Asegura que el modelo tenga el valor del control
        particle.setRadius(radius); // Asegura que el modelo tenga el radio del control
        // particle.radius(radius);

        // Actualiza el mensaje en el panel y limpia el gráfico
        drawing.setMessage("Time: 0.00");
        // energyStripChart.clear();
        // energyStripChart.append(0, calculateKineticEnergy()); // Agrega el punto inicial de energía

        // Repinta los paneles para mostrar el estado inicial
        drawing.repaint();
        // energyPlot.repaint();
    }

    /**
     * Realiza un paso de la animación.
     */
    @Override
    protected void doStep() {
        particle.move(); // Mueve la partícula (y maneja rebotes)

        // Actualiza el mensaje de tiempo en el panel
        drawing.setMessage("Time: " + decimalFormat.format(particle.getTime()));

        // Opcional: Añade la energía cinética al stripchart
        // energyStripChart.append(particle.getTime(), calculateKineticEnergy());

        // Repinta los paneles para mostrar la actualización
        drawing.repaint();
        // energyPlot.repaint();
    }

    /**
     * Reinicia la animación a un estado predefinido.
     */
    @Override
    public void resetAnimation() {
        super.resetAnimation(); // Llama al reset base de AbstractAnimation

        // Establece valores por defecto en el control (pueden ser diferentes a los iniciales del constructor)
        control.setValue("x0", 0.0);
        control.setValue("y0", 0.0);
        control.setValue("vx0", 3.0);
        control.setValue("vy0", 2.0);
        control.setValue("restitution", 0.9);
        control.setValue("radius", 0.5);

        initializeAnimation(); // Re-inicializa la animación con estos valores.
    }

    /* Opcional: Método para calcular la energía cinética */
    /*
    private double calculateKineticEnergy() {
        double vx = particle.getVx();
        double vy = particle.getVy();
        return 0.5 * particle.mass * (vx*vx + vy*vy);
    }
    */
}
