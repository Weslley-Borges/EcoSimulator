package com.GUI;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.*;


public class Chart extends JFrame {
	public Chart(String title, int[][] data) {
		super(title);
		DefaultCategoryDataset dataset = createDataset(data);

		JFreeChart chart = ChartFactory.createLineChart(
		  "Gráfico da Simulação",
		  "Dia",
		  "População",
		  dataset
		);

		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
		panel.setFillZoomRectangle(true);
	}

	private DefaultCategoryDataset createDataset(int[][] data) {
		String series1 = "Raposas";
		String series2 = "Coelhos";
		String series3 = "Grama";

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int includeGrass = Integer.parseInt(JOptionPane.showInputDialog("Incluir a grama?\n[0] - Não\n[1] - Sim"));

		for (int day=0; day < data.length; day++) {
			int[] dayData = data[day];
			dataset.addValue(dayData[0], series1, String.valueOf(day));
			dataset.addValue(dayData[1], series2, String.valueOf(day));
			if (includeGrass == 1)
				dataset.addValue(dayData[2], series3, String.valueOf(day));
		}
		return dataset;
	}
}