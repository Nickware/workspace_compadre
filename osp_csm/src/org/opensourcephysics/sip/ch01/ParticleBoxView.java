package org.opensourcephysics.sip.ch01;

import org.opensourcephysics.controls.AbstractAnimation;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.DrawingPanel;
import java.awt.Color;
import java.text.DecimalFormat;

public class ParticleBoxView extends AbstractAnimation {
    private DrawingPanel drawing = new DrawingPanel();
    private DrawingFrame drawingFrame = new DrawingFrame("Particle in a Box", drawing);
    private ParticleBoxModel particle;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public ParticleBoxView(ParticleBoxModel model) {
        this.particle = model;
        
        // Configuración inicial
        drawing.setPreferredMinMax(-5, 5, -5, 5);
        drawing.addDrawable(particle);
        drawingFrame.setSize(500, 500);
        drawingFrame.setVisible(true);
        
        // Configurar controles si están disponibles
        if(control != null) {
            control.setValue("x", 0);
            control.setValue("y", 0);
            control.setValue("vx", 2);
            control.setValue("vy", 1.5);
        }
    }

    @Override
    protected void doStep() {
        // Paso de animación - mueve la partícula
        particle.move();
        
        // Actualiza el tiempo mostrado
        drawing.setMessage("Tiempo: " + decimalFormat.format(particle.getTime()));
        drawing.repaint();
    }

    @Override
    public void initializeAnimation() {
        // Inicializa con valores del control
        double x = control.getDouble("x");
        double y = control.getDouble("y");
        double vx = control.getDouble("vx");
        double vy = control.getDouble("vy");
        
        particle.initialize(x, y, vx, vy, 0);
        drawing.repaint();
    }

    @Override
    public void resetAnimation() {
        // Valores por defecto
        control.setValue("x", 0);
        control.setValue("y", 0);
        control.setValue("vx", 2);
        control.setValue("vy", 1.5);
        initializeAnimation();
    }
}
