package ecosimulator.models;

import ecosimulator.Simulation;
import ecosimulator.entities.Genoma;
import ecosimulator.interfaces.Status;

public abstract class Organism {
  protected final Simulation s = Simulation.getInstance();
  protected Status status = Status.NORMAL;
  protected int xPosition, yPosition;
  protected double food = 100;
  protected Genoma genoma;

  // Getters

  public int getyPosition() {
    return yPosition;
  }
  public int getxPosition() {
    return xPosition;
  }
  public Genoma getGenoma() {
    return genoma;
  }
  public Status getStatus() { return status; }
  public double getFood() { return food; }

  // Setters

  public void setStatus(Status status) {
    this.status = status;
  }
  public void setFood(double food) {
    this.food = food;
  }

}
