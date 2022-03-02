package com.models;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@Getter
@Setter
public class Species {
	private Characteristics speciesCharac;
	private List<Species> predators = new ArrayList<>();
	private List<Species> preys = new ArrayList<>();

	@SneakyThrows
	public Species(
	  double hungerIncrease,
	  int range,
	  int lifeSpeculative,
	  int timeBetweenBreeds,
	  int daysToBeMature,
	  int speed,
	  int maxChildrenPerTime,
	  int pregnancyDuration,
	  String imagePath) {
		speciesCharac = new Characteristics(
		  hungerIncrease,
		  range,
		  lifeSpeculative,
		  timeBetweenBreeds,
		  daysToBeMature,
		  speed,
		  maxChildrenPerTime,
		  pregnancyDuration,
		  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)))
		);
	}

	public Characteristics sortGenes() {
		int lifeSpeculative = 1 + (int)(Math.random() * ((this.speciesCharac.lifeSpeculative - 1) + 1));
		int speed = 1 + (int)(Math.random() * ((this.speciesCharac.speed - 1) + 1));
		int range = 1 + (int)(Math.random() * ((this.speciesCharac.range - 1) + 1));

		return new Characteristics(
		  this.speciesCharac.hungerIncrease,
		  range,
		  lifeSpeculative,
		  this.speciesCharac.timeBetweenBreeds,
		  this.speciesCharac.daysToBeMature,
		  speed,
		  this.speciesCharac.maxChildrenPerTime,
		  this.speciesCharac.pregnancyDuration,
		  this.speciesCharac.image
		);
	}

	public Characteristics sortGenes(Organism parent1, Organism parent2) {
		int LS = (parent1.charac.lifeSpeculative + parent2.charac.lifeSpeculative) / 2;
		int S = (parent1.charac.speed + parent2.charac.speed) / 2;
		int R = (parent1.charac.range + parent2.charac.range) / 2;

		return new Characteristics(
		  this.speciesCharac.hungerIncrease,
		  R,
		  LS,
		  this.speciesCharac.timeBetweenBreeds,
		  this.speciesCharac.daysToBeMature,
		  S,
		  this.speciesCharac.maxChildrenPerTime,
		  this.speciesCharac.pregnancyDuration,
		  this.speciesCharac.image
		);
	}
}
