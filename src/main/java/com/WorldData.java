package com;

import com.models.Organism;
import com.models.Species;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WorldData {
	// Variáveis do mundo
	public static WorldData instance = new WorldData();
	public Random random = new Random();
	public int days = 1;
	public int delay = 70;
	public int[][] data;

	// Escala
	public final int scale = 2;
	public final int originalTileSize = 10;
	public final int tileSize = originalTileSize * scale;

	// Tamanho dos componentes da GUI
	final int maxScreenCol = 40;
	final int maxScreenRow = 30;
	public int simulationWidth = tileSize * maxScreenCol;
	public int simulationHeight = tileSize * maxScreenRow;

	// Lista de organismos e espécies
	public List<Organism> organisms = new ArrayList<>();
	public Species fox = new Species(6, 13, 1, 4, 30, "/sprites/fox.png");
	public Species rabbit = new Species(4, 10, 1, 14, 40, "/sprites/rabbit.png");
	public Species grass = new Species(4, 1, 0, 1, 0,"/sprites/grass.png");

	{
		fox.setPreys(new ArrayList<>(List.of(rabbit)));
		rabbit.setPreys(new ArrayList<>(List.of(grass)));
		rabbit.setPredators(new ArrayList<>(List.of(fox)));
	}

	public static WorldData getInstance() {
		return instance;
	}

	public Point randomPosition() {
		Point pos = new Point(0,0);

		pos.x = this.random.nextInt(this.simulationWidth/this.tileSize) * this.tileSize;
		pos.y = this.random.nextInt(this.simulationHeight/this.tileSize) * this.tileSize;
		return pos;
	}
	public void passADay() {
		this.days++;
	}
}
