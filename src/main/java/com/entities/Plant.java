package com.entities;

import com.interfaces.Status;
import com.models.Organism;
import com.models.Species;
import java.awt.*;


public class Plant extends Organism {
	public Plant(Species species) {
		this.setCharac(species.sortGenes());
		this.species = species;
		this.position = w.randomPosition();
	}
	public Plant(Species species, int x, int y) {
		this.setCharac(species.sortGenes());
		this.species = species;
		this.position = new Point(x,y);
	}

	public void simulate() {
		Point pos = this.getPosition();
		if (pos.x < 0 || pos.x >= w.simulationWidth || pos.y < 0 || pos.y >= w.simulationHeight)
			this.charac.addStatus(Status.DEAD);

		if (!this.charac.is(Status.DEAD) && !this.charac.is(Status.EATEN)) {
			this.passADay();
			this.charac.setBreedUrge(this.charac.getBreedUrge() + 1);
			this.breed();

			for (int i=0; i < w.organisms.size(); i++) {
				Organism o = w.organisms.get(i);
				if (o.getSpecies()==this.species && o!=this && o.getPosition().distance(this.position) == 0)
					o.getCharac().addStatus(Status.DEAD);
			}
		}
	}

	public void breed() {
		if (this.charac.getBreedUrge() < 30 || this.charac.getDaysAlive() < 60)
			return;

		int unit = w.tileSize;
		int unitX = (w.random.nextInt(100) >= 50) ? unit : -unit;
		int unitY = (w.random.nextInt(100) >= 50) ? unit : -unit;

		w.organisms.add(new Plant(
		  this.species,
		  this.getPosition().x + (w.random.nextInt(1, this.charac.getRange()+1) * unitX),
		  this.getPosition().y + (w.random.nextInt(1, this.charac.getRange()+1) * unitY)
		));

		this.charac.setBreedUrge(0);
	}
}
