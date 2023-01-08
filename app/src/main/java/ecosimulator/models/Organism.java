package ecosimulator.models;

import ecosimulator.Simulation;
import ecosimulator.entities.Genoma;
import ecosimulator.entities.Specie;
import ecosimulator.interfaces.Status;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Organism {
  protected Genoma genoma;
  protected final Simulation s = Simulation.getInstance();
  protected int xPosition, yPosition;
  protected Specie specie;
  protected Status status = Status.NORMAL;
  protected double breedUrge = 0;
  protected double foodUrge = 0;
  protected int age;
  protected int lifespan;

  public void draw(Graphics2D g2) {
    g2.setColor(this.specie.getColor());
    Rectangle2D rect = new Rectangle2D.Double(this.xPosition, this.yPosition, s.tileSize, s.tileSize);

    g2.fill(rect);
  }

  // Getters

  public double getBreedUrge() {
    return breedUrge;
  }
  public double getFoodUrge() { return foodUrge; }
  public int getyPosition() {
    return yPosition;
  }
  public int getxPosition() {
    return xPosition;
  }
  public Genoma getGenoma() {
    return genoma;
  }
  public Specie getSpecie() {
    return specie;
  }
  public Status getStatus() { return status; }
  public int getAge() { return age; }

  // Setters

  public void setBreedUrge(double breedUrge) { this.breedUrge = breedUrge; }
  public void setxPosition(int xPosition) { this.xPosition = xPosition; }
  public void setyPosition(int yPosition) { this.yPosition = yPosition; }
  public void setFoodUrge(double foodUrge) {
    this.foodUrge = foodUrge;
  }
  public void setStatus(Status status) {
    this.status = status;
  }

}
