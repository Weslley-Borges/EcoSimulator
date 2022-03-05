package com.models;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
public class Species {
	private Characteristics speciesCharac;
	private List<Species> predators = new ArrayList<>();
	private List<Species> preys = new ArrayList<>();

	@SneakyThrows
	public Species(int range, int lifeSpec, int speed, int maxCPerTime, int pregDuration, String imgPath) {
		speciesCharac = new Characteristics(
		  range,
		  lifeSpec,
		  speed,
		  maxCPerTime,
		  pregDuration,
		  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imgPath)))
		);
	}

	public Characteristics sortGenes() {
		int lifeSpeculative = 1 + (int)(Math.random() * ((this.speciesCharac.lifeSpeculative - 1) + 1));
		int speed = 1 + (int)(Math.random() * ((this.speciesCharac.speed - 1) + 1));
		int range = 1 + (int)(Math.random() * ((this.speciesCharac.range - 1) + 1));

		return new Characteristics(
		  range,
		  lifeSpeculative,
		  speed,
		  this.speciesCharac.maxChildrenPerTime,
		  this.speciesCharac.pregnancyDuration,
		  this.speciesCharac.image
		);
	}
}
