package ecosimulator.entities;

import ecosimulator.models.Organism;
import ecosimulator.models.PlantSpecie;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Plant extends Organism {

  private final PlantSpecie specie;
  private final int foodStore;
  private final int foodGeneration;

  public Plant(Genoma genoma, PlantSpecie specie, int xPosition, int yPosition) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.genoma = genoma;
    this.specie = specie;
    this.foodStore = this.genoma.getFactorsSum("Food store");
    this.foodGeneration = this.genoma.getFactorsSum("Food generation");
  }

  public void run() {
    if (this.food < this.foodStore)
      this.food += this.foodGeneration;
    else
      this.food = this.foodStore;
  }

  public void draw(Graphics2D g2) {
    g2.setColor( new Color(
        this.specie.getColor().getRed(),
        this.specie.getColor().getGreen(),
        this.specie.getColor().getBlue(),
        100
    ));
    Rectangle2D rect = new Rectangle2D.Double(this.xPosition, this.yPosition, s.tileSize, s.tileSize);

    g2.fill(rect);
  }

  public PlantSpecie getSpecie() { return specie; }
}
