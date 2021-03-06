/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.manual.ch09;
import org.opensourcephysics.display.*;
import org.opensourcephysics.numerics.*;
import java.awt.*;

public class NBody implements ODE, Drawable {
  int n;
  double[] state = new double[1], force = new double[0], zeros = new double[1];
  Mass[] masses;
  ODEAdaptiveSolver ode_solver;

  public NBody() {
    ode_solver = ODEMultistepSolver.MultistepRK45(this);
    ode_solver.setTolerance(1.0e-9);
    setNBodies(0);
  }

  void setNBodies(int n) {
    if(n!=state.length/4) {
      this.n = n;
      state = new double[4*n+1];
      force = new double[2*n];
      zeros = new double[2*n];
      masses = new Mass[n];
      for(int i = 0;i<n;i++) {
        masses[i] = new Mass(1);
      }
      ode_solver.initialize(ode_solver.getStepSize());
    }
  }

  /**
   * Initialize the n-body problem with the given state.
   * @param initial state
   */
  void initialize(double dt) {
    ode_solver.initialize(dt);
    computeAcceleration(state);
    for(int i = 0;i<n;i++) {
      masses[i].clear();
      masses[i].setXY(state[4*i], state[4*i+2], force[2*i], force[2*i+1]);
    }
  }

  /**
   * Computes the accelerations using pairwise interactions.
   */
  void computeAcceleration(double[] state) {
    System.arraycopy(zeros, 0, force, 0, force.length);
    for(int i = 0;i<n;i++) {
      for(int j = i+1;j<n;j++) {
        double dx = state[4*i]-state[4*j];
        double dy = state[4*i+2]-state[4*j+2];
        double r2 = dx*dx+dy*dy;
        double r3 = r2*Math.sqrt(r2);
        double ax = dx/r3;
        double ay = dy/r3;
        force[2*i] -= masses[j].m*ax;
        force[2*i+1] -= masses[j].m*ay;
        force[2*j] += masses[i].m*ax;
        force[2*j+1] += masses[i].m*ay;
      }
    }
  }

  /**
   * Updates the force array for the given state.
   */
  void updateForce() {
    computeAcceleration(state);
  }

  /**
   * Steps the time using an ode solver.
   */
  public void advanceTime() {
    ode_solver.step();
  }

  /**
   * Gets the rate.
   *
   *
   * Gets the rate.
   *
   * @param state double[]
   * @param rate double[]
   */
  public void getRate(double[] state, double[] rate) {
    computeAcceleration(state); // force array alternates fx and fy
    for(int i = 0;i<n;i++) {
      int i4 = 4*i;
      rate[i4] = state[i4+1];    // x rate is vx
      rate[i4+1] = force[2*i];   // vx rate is fx
      rate[i4+2] = state[i4+3];  // y rate is vy
      rate[i4+3] = force[2*i+1]; // vy rate is fy
    }
    rate[state.length-1] = 1; // time rate is last
  }

  /**
   * Gets the state.
   *
   * @return double[]
   */
  public double getTime() {
    return state[state.length-1];
  }

  /**
   * Gets the state.
   *
   * @return double[]
   */
  public double[] getState() {
    return state;
  }

  /**
   * Draws the n-bodies.
   *
   * @param panel DrawingPanel
   * @param g Graphics
   */
  public void draw(DrawingPanel panel, Graphics g) {
    for(int i = 0;i<n;i++) {
      masses[i].setXY(state[4*i], state[4*i+2], force[2*i], force[2*i+1]);
      masses[i].draw(panel, g);
    }
  }

  class Mass {
    double m = 1;
    Trail trail = new Trail();
    Arrow arrow = new Arrow(0, 0, 0, 0);

    Mass(double m) {
      this.m = m;
      trail.color = Color.darkGray;
    }

    // Clears the trail.
    void clear() {
      trail.clear();
    }

    // Sets the postion and adds to the trail.
    public void setXY(double x, double y, double fx, double fy) {
      arrow.setXY(x, y);
      arrow.setXlength(m*fx/10);
      arrow.setYlength(m*fy/10);
      trail.addPoint(x, y);
    }

    // Draws the mass.
    public void draw(DrawingPanel panel, Graphics g) {
      trail.draw(panel, g);
      arrow.draw(panel, g);
    }
  }
}

/*
 * Open Source Physics software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of the License,
 * or(at your option) any later version.

 * Code that uses any portion of the code in the org.opensourcephysics package
 * or any subpackage (subdirectory) of this package must must also be be released
 * under the GNU GPL license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307 USA
 * or view the license online at http://www.gnu.org/copyleft/gpl.html
 *
 * For additional information and documentation on Open Source Physics,
 * please see <http://www.opensourcephysics.org/>.
 *
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 */
