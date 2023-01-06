/*
 * Author: Weslley Borges dos Santos
 * About: This Java source file initialize the application.
 */

package ecosimulator;

import ecosimulator.GUI.JPanelSimulation;

import javax.swing.*;

public class App extends JFrame {
  public static void main(String[] args) {
    new App();
  }

  public App() {
    Simulation.getInstance().init();

    JPanelSimulation simulation = new JPanelSimulation();

    this.setTitle("EcoSimulator");
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    this.setResizable(false);

    this.add(simulation);

    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);

    simulation.startGame();

    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent e) {
        simulation.stopGame();
      }
    });
  }
}
