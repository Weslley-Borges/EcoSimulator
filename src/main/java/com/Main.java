package com;

import com.GUI.SimulationPanel;
import javax.swing.*;
import java.awt.*;


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
				System.out.println("A simulação foi encerada");
				System.exit(0);
			}
		});
	}
}
