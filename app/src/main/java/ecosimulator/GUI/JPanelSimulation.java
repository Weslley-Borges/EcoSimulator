package ecosimulator.GUI;

import ecosimulator.Simulation;
import ecosimulator.entities.Animal;
import ecosimulator.entities.Plant;
import ecosimulator.interfaces.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelSimulation extends JPanel implements ActionListener {
  Simulation s = Simulation.getInstance();
  Timer timer;
  int passingDays = 0;
  int deaths = 0;
  int births = 0;

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
    passingDays++;

    if (passingDays % 10 == 0) {
      System.out.println(s.days +" - Saldo de natalidade = "+ (births-deaths) +" - população: "+s.individuals.stream().filter(i -> i instanceof Animal).count());
      passingDays = 0;
      deaths = 0;
      births = 0;
    }

    s.individuals.forEach(o -> {
      if (o instanceof Animal)
        ((Animal) o).run();
      else if (o instanceof Plant)
        ((Plant) o).run();
    });

    deaths += s.individuals.stream().filter(i -> i.getStatus() == Status.DEAD).count();

    s.individuals.removeIf(i -> i.getStatus() == Status.DEAD);
    s.individuals.addAll(s.newBorns);

    births += s.newBorns.size();

    s.newBorns.clear();
  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    s.individuals.stream().filter(i -> i instanceof Plant).toList().forEach(i -> ((Plant) i).draw(g2));
    s.individuals.stream().filter(i -> i instanceof Animal).toList().forEach(i -> ((Animal) i).draw(g2));
  }
}
