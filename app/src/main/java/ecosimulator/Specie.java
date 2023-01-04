package ecosimulator;

import java.util.Map;

class Specie {
  private final String specieName;
  private final int initialAmount;
  private final int lifespan;
  Map<String, Integer> genes; // {geneName: factorsQuantity}

  public Specie(String specieName, int initialAmount, int lifespan, Map<String, Integer> genes) {
    this.genes = genes;
    this.lifespan = lifespan;
    this.specieName = specieName;
    this.initialAmount = initialAmount;
  }

  public int getLifespan() { return lifespan; }
  public String getSpecieName() { return specieName; }
  public int getInitialAmount() { return initialAmount; }
  public Map<String, Integer> getGenes() { return genes; }

}