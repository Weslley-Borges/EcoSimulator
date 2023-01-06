package ecosimulator.entities;

import java.awt.*;
import java.util.Map;

public class Specie {
  private final String specieName;
  private final int initialAmount;
  private final int lifespan;
  Map<String, Integer> genes; // {geneName: factorsQuantity}
  Color color;

  public Specie(String specieName, int initialAmount, int lifespan, Map<String, Integer> genes, Color color) {
    this.genes = genes;
    this.lifespan = lifespan;
    this.specieName = specieName;
    this.initialAmount = initialAmount;
    this.color = color;
  }

  public int getLifespan() { return lifespan; }
  public String getSpecieName() { return specieName; }
  public int getInitialAmount() { return initialAmount; }
  public Map<String, Integer> getGenes() { return genes; }
  public Color getColor() {return color;}
}