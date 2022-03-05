package com.models;

import com.interfaces.IOrganism;
import com.interfaces.Status;
import lombok.Getter;
import lombok.Setter;
import com.WorldData;
import java.awt.*;


@Getter @Setter
public abstract class Organism implements IOrganism {
	protected Species species;
	protected Characteristics charac;
	protected WorldData w = WorldData.getInstance();
	protected Point position = new Point(0,0);


	public void passADay() {
		this.charac.setDaysAlive(this.charac.getDaysAlive() + 1);
		if (this.charac.getDaysAlive() >= this.charac.getLifeSpeculative())
			this.charac.addStatus(Status.DEAD);
	}

	public int getDistance(Point target) {
		return ((int) this.position.distance(target)) / w.tileSize;
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(this.charac.image, this.position.x, this.position.y, w.tileSize, w.tileSize, null);
	}
}
