package ecosimulator.GUI;

import ecosimulator.Simulation;
import ecosimulator.entities.Organism;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelSimulation extends JPanel implements ActionListener {
  Simulation s = Simulation.getInstance();
  Timer timer;

  public JPanelSimulation() {
    this.setPreferredSize(new Dimension(s.simulationWidth, s.simulationHeight));
    this.setBackground(new Color(135, 68, 36));
    this.setDoubleBuffered(true);

  }

  public void startGame() {
    timer = new Timer(s.delay, this);
    timer.start();
  }

  public void stopGame() {
    timer.stop();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    update();
    repaint();
  }

  public void update() {
    s.individuals.forEach(Organism::moveRandomly);
  }



  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    s.individuals.forEach(individual -> individual.draw(g2));
  }
}
