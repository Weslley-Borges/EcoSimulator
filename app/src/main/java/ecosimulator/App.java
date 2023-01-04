/*
 * Author: Weslley Borges dos Santos
 * About: This Java source file initialize the application.
 */

package ecosimulator;

import com.jakewharton.fliptables.FlipTable;
import ecosimulator.entities.Organism;
import ecosimulator.entities.Population;
import ecosimulator.helpers.SpeciesBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
  public static void main(String[] args) {
    SpeciesBuilder speciesBuilder = new SpeciesBuilder();
    List<Population> populations = new ArrayList<>();
    Random rn = new Random();

    speciesBuilder.getSpecies().forEach(specie -> {
      // Os primeiros organismos são colocados

      Population population = new Population(specie.getSpecieName());

      for (int i=0; i < specie.getInitialAmount(); i++)
        population.addIndividual(new Organism(specie));

      populations.add(population);

      // Os organismos se reproduzem, causando a diversificação genética

      for (int i=0; i < specie.getInitialAmount() * 10; i++) {
        Organism parentOne = population.getIndividuals()
                .get(rn.nextInt(population.getIndividuals().size()-1));
        Organism parentTwo = population.getIndividuals()
                .get(rn.nextInt(population.getIndividuals().size()-1));

        while (parentTwo == parentOne)
          parentTwo = population.getIndividuals().get(rn.nextInt(population.getIndividuals().size()-1));

        population.getIndividuals().add(parentOne.breed(parentTwo));
      }

    });


    populations.forEach(App::showTable);

    /*
    for (int i=0; i < 10; i++)
      individual[i] = new Organism(speciesBuilder.getSpecies().get(0));

    for (int i = 10; i < 100; i++)
    {
      Organism parentOne = individual[rn.nextInt(i-1)];
      Organism parentTwo = individual[rn.nextInt(i-1)];

      while (parentTwo == parentOne)
        parentTwo = individual[rn.nextInt(i-1)];

      individual[i] = parentOne.breed(parentTwo);
    }
    */
  }

  public static void showTable(Population population) {
    Organism individualExample = population.getIndividuals().get(0);
    int columns = individualExample.getGenoma().getGenes().keySet().toArray().length;

    String[] headers = new String[columns+1];
    String[][] data = new String[population.getIndividuals().size()][columns+1];

    for (int i=0; i < columns; i++) {
      headers[i] = individualExample.getGenoma().getGenes().keySet().toArray()[i].toString();
    }
    headers[columns] = "Fitness";

    for (int i=0; i < population.getIndividuals().size(); i++) {
      Organism individual = population.getIndividuals().get(i);

      for (int j=0; j < headers.length-1; j++) {
        data[i][j] = individual.getGenoma().getGeneSequenceString(headers[j]);
      }
      data[i][columns] = ""+individual.getGenoma().getFitness();
    }

    System.out.println(FlipTable.of(headers, data));
  }

}
