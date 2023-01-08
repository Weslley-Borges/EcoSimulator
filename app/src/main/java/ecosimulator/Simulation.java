package ecosimulator;

import ecosimulator.entities.Animal;
import ecosimulator.entities.Genoma;
import ecosimulator.helpers.SpeciesBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
  public List<Animal> individuals = new ArrayList<>();
  public List<Animal> newBorns = new ArrayList<>();
  public static Simulation instance = null;
  public final int simulationHeight = 700;
  public final int simulationWidth = 700;
  public final Random rn = new Random();
  public int tileSize = 20;
  public int delay = 100;
  public int days = 0;

  public static Simulation getInstance() {
    if (Simulation.instance == null)
      Simulation.instance = new Simulation();
    return instance;
  }

  public void init() {
    SpeciesBuilder speciesBuilder = new SpeciesBuilder();

    // Cria os primeiros organismos

    speciesBuilder.getSpecies().forEach(specie ->
    {
      for (int i=0; i < specie.getInitialAmount(); i++)
      {
        List<String> geneNames = specie.getGenes().keySet().stream().toList();
        List<Integer> valuesQuantities = specie.getGenes().values().stream().toList();

        Genoma genoma = new Genoma(geneNames, valuesQuantities);
        int xPosition = rn.nextInt(simulationWidth / tileSize) * tileSize;
        int yPosition = rn.nextInt(simulationHeight / tileSize) * tileSize;

        individuals.add(new Animal(genoma, specie, xPosition, yPosition));
      }
    });
  }
}
