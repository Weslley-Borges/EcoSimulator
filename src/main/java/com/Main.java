package com;

import com.GUI.Chart;
import com.GUI.SimulationPanel;
import javax.swing.*;
import java.util.Arrays;


public class Main extends JFrame {
	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		SimulationPanel simulationPanel = new SimulationPanel();

		this.setTitle("EcologyGame");
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);

		this.add(simulationPanel);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		simulationPanel.startGame();

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				simulationPanel.stopGame();

				SwingUtilities.invokeLater(() -> {
					Chart example = new Chart("Line Chart Example", WorldData.getInstance().data);
					example.setAlwaysOnTop(true);
					example.pack();
					example.setSize(600, 500);
					example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					example.setVisible(true);
				});
			}
		});
	}
}
