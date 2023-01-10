package ecosimulator.models;

import ecosimulator.interfaces.ISpecie;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class AnimalSpecie implements ISpecie {

  private final int initialAmount, childrenByBreed,lifespan, maturityAge;
  private final List<String> predators;
  private final List<String> preys;
  private final String specieName;
  Map<String, Integer> genes;
  private final Color color;

  public AnimalSpecie
      (
        String specieName,
        int initialAmount, int lifespan, int maturityAge, int childrenByBreed,
        Map<String, Integer> genes, Color color,
        List<String> predators, List<String> preys
      )
  {
    this.genes = genes;
    this.lifespan = lifespan;
    this.childrenByBreed = childrenByBreed;
    this.maturityAge = maturityAge;
    this.specieName = specieName;
    this.initialAmount = initialAmount;
    this.color = color;
    this.predators = predators;
    this.preys = preys;
  }

  public int getInitialAmount() { return initialAmount; }
  public int getMaturityAge() { return maturityAge; }
  public int getLifespan() { return lifespan; }

  public Map<String, Integer> getGenes() { return genes; }
  public List<String> getPredators() {
    return predators;
  }
  public List<String> getPreys() {
    return preys;
  }

  public int getChildrenByBreed() { return childrenByBreed;}
  public String getSpecieName() { return specieName; }
  public Color getColor() { return color; }
}