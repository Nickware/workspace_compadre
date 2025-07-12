package org.opensourcephysics.sip.ch01;

import org.opensourcephysics.controls.Animation;
import org.opensourcephysics.controls.OSPControl;

public class ParticleBoxApp {
    public static void main(String[] args) {
        ParticleBoxModel model = new ParticleBoxModel();  // Crea el modelo
        Animation animation = new ParticleBoxView(model); // Pásalo a la vista
        OSPControl control = new OSPControl(animation);
        
        control.addButton("startAnimation", "Start");
        control.addButton("stopAnimation", "Stop");
        control.addButton("initializeAnimation", "Reset");
        
        animation.setControl(control);
    }
}
