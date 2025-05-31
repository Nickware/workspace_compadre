package org.opensourcephysics.sip.ch01;

import org.opensourcephysics.controls.Animation;
import org.opensourcephysics.controls.OSPControl;

/**
 * ParticleBoxApp: Lanza la aplicación de la partícula rebotando en una caja.
 * Reutiliza la estructura de SHOApp.
 */

public class ParticleBoxApp {
	/**
	 * Inicia la aplicación Java.
	 * 
	 * @param args parámetros de línea de comandos
	 */
	public static void main(String[] args) {
		// 1. Crea una instancia de nuestra vista de la caja de partículas.
		Animation animation = new ParticleBoxView();

		// 2. Crea el panel de control para la simulación.
		OSPControl control = new OSPControl(animation);

		// 3. Agrega botones para controlar la animación.
		control.addButton("startAnimation", "Start");
		control.addButton("stopAnimation", "Stop");
		control.addButton("initializeAnimation", "Initialize");
		control.addButton("resetAnimation", "Reset"); // Añadimos reset para configurar valores por defecto.

		// 4. Asigna el control a la animación.
		animation.setControl(control);
	}
}
