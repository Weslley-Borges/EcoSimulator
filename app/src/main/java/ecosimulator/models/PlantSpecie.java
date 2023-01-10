package ecosimulator.models;

import ecosimulator.interfaces.ISpecie;

import java.awt.*;
import java.util.Map;

public class PlantSpecie implements ISpecie {

  private final String specieName;
  Map<String, Integer> genes;
  private final Color color;

  public PlantSpecie (String specieName, Map<String, Integer> genes, Color color) {
    this.genes = genes;
    this.specieName = specieName;
    this.color = color;
  }

  public Map<String, Integer> getGenes() { return genes; }
  public String getSpecieName() { return specieName; }
  public Color getColor() { return color; }
}