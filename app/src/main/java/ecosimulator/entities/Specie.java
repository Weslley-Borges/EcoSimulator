package ecosimulator.entities;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Specie {
  private final String specieName;
  private final int initialAmount;
  private final int lifespan;
  private final List<String> predators;
  private final List<String> preys;
  private final Color color;
  private final int maturityAge;
  Map<String, Integer> genes; // {geneName: factorsQuantity}

  public Specie
      (
        String specieName,
        int initialAmount, int lifespan, int maturityAge,
        Map<String, Integer> genes, Color color,
        List<String> predators, List<String> preys
      )
  {
    this.genes = genes;
    this.lifespan = lifespan;
    this.maturityAge = maturityAge;
    this.specieName = specieName;
    this.initialAmount = initialAmount;
    this.color = color;
    this.predators = predators;
    this.preys = preys;
  }

  public int getMaturityAge() {return maturityAge;}
  public int getLifespan() { return lifespan; }
  public String getSpecieName() { return specieName; }
  public int getInitialAmount() { return initialAmount; }
  public Map<String, Integer> getGenes() { return genes; }
  public Color getColor() {return color;}
  public Specie getSpecie() {return this;}

  public List<String> getPredators() {
    return predators;
  }

  public List<String> getPreys() {
    return preys;
  }
}