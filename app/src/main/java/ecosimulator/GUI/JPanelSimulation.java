package ecosimulator.GUI;

import ecosimulator.Simulation;
import ecosimulator.entities.Animal;
import ecosimulator.interfaces.Status;

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
    s.days++;
    s.individuals.forEach(Animal::run);

    long deaths = s.individuals.stream().filter(i -> i.getStatus() == Status.DEAD).count();

    s.individuals.removeIf(i -> i.getStatus() == Status.DEAD);
    s.individuals.addAll(s.newBorns);

    long saldo = s.newBorns.size() - deaths;

    if (saldo != 0)
      System.out.println(s.days +" - Saldo de natalidade = "+ saldo );
    s.newBorns.clear();
  }



  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    s.individuals.forEach(individual -> individual.draw(g2));
  }
}
