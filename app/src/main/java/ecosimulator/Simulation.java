package ecosimulator;

import ecosimulator.entities.Organism;
import ecosimulator.helpers.SpeciesBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
  public static Simulation instance = null;
  public List<Organism> individuals = new ArrayList<>();
  public final int simulationWidth = 700;
  public final int simulationHeight = 700;

  public final Random rn = new Random();
  public int simulationDays = 50;
  public int delay = 100;

  public static Simulation getInstance() {
    if (Simulation.instance == null)
      Simulation.instance = new Simulation();
    return instance;
  }

  public void init() {
    SpeciesBuilder speciesBuilder = new SpeciesBuilder();

    // Cria os primeiros organismos

    speciesBuilder.getSpecies().forEach(specie -> {
      for (int i=0; i < specie.getInitialAmount(); i++)
        individuals.add(new Organism(specie));
    });

    // Os organismos se reproduzem, causando a diversificação genética

    speciesBuilder.getSpecies().forEach(specie -> {
      List<Organism> newBorn = new ArrayList<>();
      List<Organism> population = new ArrayList<>(individuals
          .stream()
          .filter(organism -> organism.getSpecieName().equals(specie.getSpecieName()))
          .toList());

      for (int i=0; i < specie.getInitialAmount() * 2; i++)
      {
        Organism parentOne = population.get(rn.nextInt(population.size()));
        Organism parentTwo = population.get(rn.nextInt(population.size()));

        while (parentTwo == parentOne)
          parentTwo = population.get(rn.nextInt(population.size()));

        newBorn.add(parentOne.breed(parentTwo));
      }

      individuals.addAll(newBorn);
    });
  }
}
