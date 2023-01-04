package ecosimulator.entities;

import java.util.ArrayList;
import java.util.List;

public class Population {
  private String specieName;
  private List<Organism> individuals = new ArrayList<>();

  public Population(String specieName) {
    this.specieName = specieName;
  }

  public String getSpecie() {
    return specieName;
  }

  public List<Organism> getIndividuals() {
    return individuals;
  }

  public void addIndividual(Organism individual) {
    this.individuals.add(individual);
  }
  public void removeIndividual(Organism individual) {
    this.individuals.remove(individual);
  }

}