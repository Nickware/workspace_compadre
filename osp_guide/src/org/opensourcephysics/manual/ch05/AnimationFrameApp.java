/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.manual.ch05;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;

/**
 * AnimationFrameApp demonstrates how to create an animation by extending AbstractAnimation.
 *
 * This program is similar to RotationApp.
 *
 * @author W. Christian
 * @version 1.0
 */
public class AnimationFrameApp extends AbstractAnimation {
  DrawingPanel panel = new PlottingPanel("x", "y", "Rotating Shape");
  DrawingFrame frame = new DrawingFrame(panel);
  InteractiveShape ishape = InteractiveShape.createRectangle(2, 1, 2, 1);
  double theta = 0, dtheta = 0.1;

  /**
   * Constructs a RotationApp.
   */
  public AnimationFrameApp(double dtheta) {
    this.dtheta = dtheta;
    panel.setPreferredMinMax(-5, 5, -5, 5);
    panel.addDrawable(ishape);
    panel.setBuffered(true);  // double buffer for smoother drawing.
    frame.setVisible(true);
  }

  /**
   * Does an animation step.
   */
  protected void doStep() {
    theta += dtheta;
    ishape.setTheta(theta);
    panel.render();
    // frame.render();
  }

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  public static void main(String[] args) {
    Animation animation = new AnimationFrameApp(0.1); // create an animation
    Control control = new AnimationControl(animation);
    animation.setControl(control);
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
