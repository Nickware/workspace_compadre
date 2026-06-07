/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */


/* This program is a 3D demonstration of various elements such as cylinders, spheres, cones, and surfaces.
 * It uses the Open Source Physics library to create a 3D display frame and add these elements to it.
 * The surface is defined by a mathematical function that creates a wave-like pattern.
 *
 * @author Francisco Esquembre
 * @version 1.0  05/16/05
 */

/* The main method initializes the 3D display frame, sets its properties, and adds various 3D elements to it.
 * It then calculates the data for a surface element using a mathematical function and displays the frame.
 */

/* The code begins by importing the necessary classes from the Open Source Physics library. 
 * /It then defines a class called Demo3D_1App with a main method that serves as the entry point for the application.
 * In the main method, a Display3DFrame is created with the title "3D Demo 1". The frame's preferred minimum and maximum values for the x, y, and z axes are set to -1.0 and 1.0, respectively.
 * The frame's decoration type is set to display axes, and the altitude and azimuth angles are set to 0.6 and 0.2, respectively.  
 * Next, several 3D elements are created and added to the frame. These include two cylinders, a sphere, a cone, and a surface.
 * The first cylinder is positioned at (0, 0, -0.4) with a size of (0.4, 0.4, 0.8) and a resolution of (5, 5, 2).
 * The second cylinder is positioned at (0.8, -0.8, -0.4) with a size of (0.0, 0.0, 0.8) and a resolution of (5, 5, 2).
 * The sphere is positioned at (-0.8, 0.8, 0) with a size of (0.4, 0.4, 0.4).
 * The cone is positioned at (-0.8, -0.8, 0.0) with a size of (0.4, 0.4, 0.8), a resolution of (5, 5, 5), and a fill color of pink.
 * The surface is positioned at (-1, -1, -1) with a fill color of red. The data for the surface is calculated using a nested loop that iterates through values of i and j to fill a 2D array with x, y, and z coordinates based on a mathematical function.
 */ 

//The code creates a Display3DFrame and adds several 3D elements to it: two cylinders, a sphere, a cone, and a surface. 
//The surface is defined by a 2D array of points that are calculated using a mathematical function. 
//The frame is then displayed on the screen.

package org.opensourcephysics.sip.ch03;
import org.opensourcephysics.display3d.simple3d.*; // Change this line to get a new implementation
import org.opensourcephysics.frames.*;

/**
 * A demonstration of 3D Elements
 * @author Francisco Esquembre
 * @version 1.0  05/16/05
 */
public class Demo3D_1App {

  /**
   * Starts the Java application.
   * @param args  command line parameters
   */
  static public void main(String args[]) {
    Display3DFrame frame = new Display3DFrame("3D Demo 1");
    // panel = new DrawingPanel3D(DrawingPanel3D.DISPLAY_PLANAR_XY);
    frame.setPreferredMinMax(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);
    frame.setDecorationType(org.opensourcephysics.display3d.core.VisualizationHints.DECORATION_AXES);
    frame.setAltitude(0.6);
    frame.setAzimuth(0.2);
    Element cylinder1 = new ElementCylinder();
    cylinder1.setXYZ(0, 0, -0.4);
    cylinder1.setSizeXYZ(0.4, 0.4, 0.8);
    cylinder1.getStyle().setResolution(new Resolution(5, 5, 2));
    frame.addElement(cylinder1);
    Element cylinder2 = new ElementCylinder();
    cylinder2.setXYZ(0.8, -0.8, -0.4);
    cylinder2.setSizeXYZ(0.0, 0.0, 0.8);
    cylinder2.setSizeZ(0.2);
    cylinder2.getStyle().setResolution(new Resolution(5, 5, 2));
    frame.addElement(cylinder2);
    Element sphere1 = new ElementEllipsoid();
    sphere1.setXYZ(-0.8, 0.8, 0);
    sphere1.setSizeXYZ(0.4, 0.4, 0.4);
    frame.addElement(sphere1);
    Element cone1 = new ElementCone();
    cone1.setXYZ(-0.8, -0.8, 0.0);
    cone1.setSizeXYZ(0.4, 0.4, 0.8);
    cone1.getStyle().setResolution(new Resolution(5, 5, 5));
    cone1.getStyle().setFillColor(java.awt.Color.PINK);
    frame.addElement(cone1);
    ElementSurface surface1 = new ElementSurface();
    surface1.setXYZ(-1, -1, -1);
    surface1.getStyle().setFillColor(java.awt.Color.RED);
    frame.addElement(surface1);
    int nu = 16, nv = 32;
    double[][][] data = new double[nu][nv][3];
    for(int i = 0;i<nu;i++) {
      for(int j = 0;j<nv;j++) {
        data[i][j][1] = 0.0+i*2.0/(nu-1);
        data[i][j][0] = 0.0+j*2.0/(nv-1);
        data[i][j][2] = Math.cos(3.0*(data[i][j][1]-1))*(data[i][j][0]-1)*(1.5-data[i][j][0])/2.0;
      }
    }
    surface1.setData(data);
    frame.setSize(600, 600);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
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
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 */
