package com.GUI;

import com.WorldData;
import com.entities.Animal;
import com.entities.Plant;
import com.interfaces.Status;
import com.models.Organism;
import lombok.SneakyThrows;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;


public class SimulationPanel extends JPanel implements ActionListener{
    WorldData w = WorldData.getInstance();
    Timer timer;

    public SimulationPanel() {
        this.setPreferredSize(new Dimension(w.simulationWidth, w.simulationHeight));
        this.setBackground(new Color(135, 68, 36));
        this.setDoubleBuffered(true);

        int grassOrganisms = Integer.parseInt(JOptionPane.showInputDialog("Quanta grama?"));
        int rabbitOrganisms = Integer.parseInt(JOptionPane.showInputDialog("Quantos coelhos?"));
        int wolfOrganisms = Integer.parseInt(JOptionPane.showInputDialog("Quantas raposas?"));

        for (int i=0; i<grassOrganisms; i++) w.organisms.add(new Plant(w.grass));
        for (int i=0; i<rabbitOrganisms; i++) w.organisms.add(new Animal(w.rabbit));
        for (int i=0; i<wolfOrganisms; i++) w.organisms.add(new Animal(w.fox));
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
        this.printTable();
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

		this.printTable();
    }

    @SneakyThrows
    public void printTable() {
        String[][] table = new String[][] {
          { "Specie", "Organisms"},
          { "Grama", Integer.toString((int) w.organisms.stream().filter(o -> o.getSpecies() == w.grass).count())},
          { "Raposa", Integer.toString((int) w.organisms.stream().filter(o -> o.getSpecies() == w.fox).count()) },
          { "Coelho", Integer.toString((int) w.organisms.stream().filter(o -> o.getSpecies() == w.rabbit).count())}
        };

		// Calcula a largura apropriada para cada coluna, baseado no tamanho dos dados em
        // cada coluna.
        Map<Integer, Integer> columnLengths = new HashMap<>(); // <numero_coluna, largura_coluna>
        Arrays.stream(table)
          .forEach(row -> Stream.iterate(0, (column -> column < row.length), (column -> ++column))
            .forEach(column -> {
                columnLengths.putIfAbsent(column, 0);
                if (columnLengths.get(column) < row[column].length()) columnLengths.put(column, row[column].length());
            }
        ));

        // Monta a tabela e imprime na tela
        final StringBuilder formatString = new StringBuilder("");
        columnLengths.forEach((key, value) -> formatString.append("| %" + "" + value + "s "));
        formatString.append("|\n");

        Stream
          .iterate(0, (i -> i < table.length), (i -> ++i))
          .forEach(a -> System.out.printf(formatString.toString(), table[a]));
        System.out.println("\n");
    }
}
