package com.entities;

import com.interfaces.Status;
import com.models.Organism;
import com.models.Species;
import java.awt.*;
import java.util.List;


public class Animal extends Organism {
	public Animal(Species species) {
		this.setCharac(species.sortGenes());
		this.species = species;
		this.position = w.randomPosition();
	}
	public Animal(Animal parent) {
		this.charac = parent.species.sortGenes();
		this.species = parent.species;
		this.position = new Point (parent.position.x, parent.position.y);
	}

	public void simulate() {
		if (this.charac.is(Status.DEAD) || this.charac.is(Status.EATEN)) return;
		this.passADay();

		this.move();
		this.checkHunger();
		this.checkPregnancy();
		this.eat();
		this.breed();
		this.preventPredator();

		if (
		  this.charac.is(Status.PREGNANT) &&
			this.charac.getDaysPregnant() >= this.charac.getPregnancyDuration()
		) {
			w.organisms.add(new Animal(this));
			this.charac.removeStatus(Status.PREGNANT);
			this.charac.setDaysPregnant(0);
			this.charac.setDaysToNextBreed(this.charac.getDaysToNextBreed() + this.charac.getTimeBetweenBreeds());
		}
	}


	// Movimentação ----------------------------------------------

	public void move() {
		int tileSize = w.tileSize, speed = this.charac.getSpeed();
		Point me = this.position;


		me.x += w.random.nextInt(-speed, speed + 1) * tileSize;
		me.y += w.random.nextInt(-speed, speed + 1) * tileSize;


		if (me.x < 0) me.x = 0;
		else if (me.x >= w.simulationWidth)
			me.x = w.simulationWidth - tileSize;

		if (me.y < 0) me.y = 0;
		else if (me.y >= w.simulationHeight)
			me.y = w.simulationHeight - tileSize;

		this.charac.setHunger(this.charac.getHunger() + this.charac.getHungerIncrease());
	}

	private void moveTo(Point to) {
		Point from = this.position;

		if (from.x > to.x)
			from.x -= w.tileSize * this.charac.getSpeed();
		else if (from.x < to.x)
			from.x += w.tileSize * this.charac.getSpeed();

		if (from.y > to.y)
			from.y -= w.tileSize * this.charac.getSpeed();
		else if (from.y < to.y)
			from.y += w.tileSize * this.charac.getSpeed();
	}

	public void runAway(Point from) {
		if (this.position.distance(from) <= w.tileSize * this.charac.getRange()) {
			Point me = this.position;

			if (me.x > from.x)
				me.x += w.tileSize * this.charac.getSpeed();
			else if (me.x < from.x)
				me.x -= w.tileSize * this.charac.getSpeed();

			if (me.y > from.y)
				me.y += w.tileSize * this.charac.getSpeed();
			else if (me.y < from.y)
				me.y -= w.tileSize * this.charac.getSpeed();
		}
	}


	// Reprodução -----------------------------------------------------
	public void breed() {
		Animal couple = this.searchPartner();
		if (couple == null) return;

		int children = 1 + (int)(Math.random() * ((this.charac.getMaxChildrenPerTime() - 1) + 1));
		for (Animal a: new Animal[] {this, couple}) {
			if (a.charac.getGender() == 'F') {
				a.charac.setPregnantOf(children);
				a.charac.setDaysPregnant(0);
				a.charac.addStatus(Status.PREGNANT);
				a.charac.setDaysToNextBreed(a.charac.getDaysToNextBreed() + a.charac.getTimeBetweenBreeds());
			}
		}

		couple.charac.setBreedUrge(couple.charac.getBreedUrge() - 20);
		this.charac.setBreedUrge(this.charac.getBreedUrge() - 20);
	}

	public Animal searchPartner() {
		if (!this.charac.isBiologicallyAlready() || this.charac.is(Status.PREGNANT)) return null;

		List<Organism> canBreed = w.organisms
		  .stream()
		  .filter(r -> r instanceof Animal)
		  .filter(r -> ((Animal) r).canBreed(((Animal) r), this))
		  .toList();

		return (Animal) this.getClosestEntity(this, canBreed);
	}

	public Boolean canBreed(Animal a1, Animal a2) {
		Boolean isFemaleAndIsNotPregnant =
		  a1.charac.getGender() != a2.charac.getGender() && !a1.charac.is(Status.PREGNANT);
		return isFemaleAndIsNotPregnant && a1.charac.isBiologicallyAlready();
	}


	// Predativismo -------------------------------------------------------------
	private void eat() {
		List<Organism> preys = w.organisms
		  .stream()
		  .filter(o -> this.species.getPreys().contains(o.getSpecies()))
		  .toList();

		if (preys.size() == 0) return;
		Organism closest = this.getClosestEntity(this, preys);
		if (closest == null) return;

		if (
		  !closest.getCharac().is(Status.DEAD) && !closest.getCharac().is(Status.EATEN) &&
			this.isClosely(closest) && this.charac.is(Status.HUNGRY)
		) {
			closest.getCharac().addStatus(Status.EATEN);
			this.charac.setHunger(this.charac.getHunger() - 30);
		}
	}

	public void preventPredator() {
		List<Organism> predators = w.organisms
		  .stream()
		  .filter(o -> this.species.getPredators().contains(o.getSpecies()))
		  .toList();

		if (predators.size() > 0) {
			Organism closest = this.getClosestEntity(this, predators);
			if (closest != null)
				this.runAway(closest.getPosition());
		}

	}

	// Métodos auxiliares -----------------------------------------------------

	protected <T extends Organism> boolean isClosely(T entity) {
		if (this.position.distance(entity.getPosition()) <= w.tileSize * this.charac.getRange())
			this.moveTo(entity.getPosition());

		return this.position.distance(entity.getPosition()) <= w.tileSize;
	}

	public void checkHunger() {
		if (this.charac.getHunger() >= 100)
			this.charac.addStatus(Status.DEAD);
		else if (this.charac.getHunger() >= 50) {
			this.charac.addStatus(Status.HUNGRY);
			this.charac.removeStatus(Status.NORMAL);
		}
		else {
			this.charac.addStatus(Status.NORMAL);
			this.charac.removeStatus(Status.HUNGRY);
		}

	}

	public void checkPregnancy() {
		if (!this.charac.is(Status.PREGNANT)) {
			if (this.charac.getBreedUrge() < 100)
				this.charac.setBreedUrge(this.charac.getBreedUrge() + 1);

			if (this.charac.getDaysToNextBreed() > 0)
				this.charac.setDaysToNextBreed(this.charac.getDaysToNextBreed() - 1);
		} else
			this.charac.setDaysPregnant(this.charac.getDaysPregnant() + 1);
	}

}
