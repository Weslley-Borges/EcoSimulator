package com.GUI;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


public class Chart extends JFrame {
	public Chart(String title, int[][] data) {
		super(title);
		DefaultCategoryDataset dataset = createDataset(data);

		JFreeChart chart = ChartFactory.createLineChart(
		  "Gráfico da Simulação coelhos-raposas",
		  "Dia",
		  "População",
		  dataset
		);

		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
	}

	private DefaultCategoryDataset createDataset(int[][] data) {
		String series1 = "Raposas";
		String series2 = "Coelhos";

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int day=0; day < data.length; day++) {
			int[] dayData = data[day];
			dataset.addValue(dayData[0], series1, String.valueOf(day));
			dataset.addValue(dayData[1], series2, String.valueOf(day));
		}
		return dataset;
	}
}