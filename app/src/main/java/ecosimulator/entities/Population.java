package ecosimulator.entities;

import java.util.ArrayList;
import java.util.List;

public class Population {
  private String specieName;
  private List<Animal> individuals = new ArrayList<>();

  public Population(String specieName) {
    this.specieName = specieName;
  }

  public String getSpecie() {
    return specieName;
  }

  public List<Animal> getIndividuals() {
    return individuals;
  }

  public void addIndividual(Animal individual) {
    this.individuals.add(individual);
  }
  public void removeIndividual(Animal individual) {
    this.individuals.remove(individual);
  }

}