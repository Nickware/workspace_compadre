package org.opensourcephysics.sip.ch01;
import org.opensourcephysics.controls.Animation;
import org.opensourcephysics.controls.OSPControl;

public class SHOApp2 {

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    Animation animation = new SHOView2();
    OSPControl control = new OSPControl(animation);
    control.addButton("startAnimation", "Iniciar");
    control.addButton("stopAnimation", "Pausar");
    control.addButton("initializeAnimation", "Inicializar");
    animation.setControl(control);
  }
}