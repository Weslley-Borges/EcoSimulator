package com.models;

import com.interfaces.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import java.awt.image.BufferedImage;
import java.util.*;


@Getter
@Setter
public class Characteristics {
	protected int
	  hunger=0, range, breedUrge = 0, daysAlive = 1, lifeSpeculative,
	  speed, maxChildrenPerTime, daysPregnant, pregnancyDuration, pregnantOf;

	protected char gender;
	protected BufferedImage image;
	protected List<Status> status = new ArrayList<>();

	@SneakyThrows
	public Characteristics(
	  int range,
	  int lifeSpeculative,
	  int speed,
	  int maxChildrenPerTime,
	  int pregnancyDuration,
	  BufferedImage image) {
		this.range = range;
		this.gender = (new Random().nextInt(100) > 50) ? 'F': 'M';
		this.addStatus(Status.NORMAL);
		this.lifeSpeculative = lifeSpeculative  * 360;
		this.speed = speed;
		this.maxChildrenPerTime = maxChildrenPerTime;
		this.pregnancyDuration = pregnancyDuration;
		this.image = image;
	}


	// Status ------------------------------------
	public Boolean is(Status status) {
		return this.status.contains(status);
	}
	public void removeStatus(Status status) {
		this.status.remove(status);
	}
	public void addStatus(Status status) {
		if (!this.status.contains(status))
			this.status.add(status);
	}
}
