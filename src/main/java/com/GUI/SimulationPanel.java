package com.GUI;

import com.WorldData;
import com.entities.Animal;
import com.entities.Plant;
import com.interfaces.Status;
import com.models.Organism;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;


public class SimulationPanel extends JPanel implements ActionListener{
    WorldData w = WorldData.getInstance();
    int simulationDays;
    Timer timer;

    public SimulationPanel() {
        this.setPreferredSize(new Dimension(w.simulationWidth, w.simulationHeight));
        this.setBackground(new Color(135, 68, 36));
        this.setDoubleBuffered(true);

        simulationDays = Integer.parseInt(JOptionPane.showInputDialog("Quantos dias de simulação?"));
        int grassOrganisms = Integer.parseInt(JOptionPane.showInputDialog("Quanta grama?"));
        int rabbitOrganisms = Integer.parseInt(JOptionPane.showInputDialog("Quantos coelhos?"));
        int foxOrganisms = Integer.parseInt(JOptionPane.showInputDialog("Quantas raposas?"));

        w.data = new int[simulationDays][3];
        w.data[0] = new int[]{foxOrganisms, rabbitOrganisms, grassOrganisms};

        for (int i=0; i<grassOrganisms; i++) w.organisms.add(new Plant(w.grass));
        for (int i=0; i<rabbitOrganisms; i++) w.organisms.add(new Animal(w.rabbit));
        for (int i=0; i<foxOrganisms; i++) w.organisms.add(new Animal(w.fox));
    }

    public void startGame() {
        timer = new Timer(w.delay, this);
        timer.start();
    }
    public void stopGame() {
        timer.stop();
    }


    // Processos da simulação -------------------------- //

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    public void update() {
        w.passADay();

        for (int i=0; i< w.organisms.size(); i++)
            w.organisms.get(i).simulate();

        w.organisms.removeIf(o -> o.getCharac().is(Status.DEAD) || o.getCharac().is(Status.EATEN));
        this.addDataToArray();
    }

    public void addDataToArray() {
        int grasses = (int) w.organisms.stream().filter(o -> o.getSpecies() == w.grass).count();
        int foxes = (int) w.organisms.stream().filter(o -> o.getSpecies() == w.fox).count();
        int rabbits = (int) w.organisms.stream().filter(o -> o.getSpecies() == w.rabbit).count();

        w.data[w.days-1] = new int[]{foxes, rabbits, grasses};

        if (simulationDays == w.days) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        List<Organism> grasses = w.organisms.stream().filter(o -> o.getSpecies() == w.grass).toList();
        for (Organism entity : grasses)
            entity.draw(g2);

        List<Organism> otherOrganism = w.organisms.stream().filter(o -> !grasses.contains(o)).toList();
        for (Organism entity : otherOrganism)
            entity.draw(g2);
    }
}
