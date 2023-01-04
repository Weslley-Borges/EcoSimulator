/*
 * Author: Weslley Borges dos Santos
 * About: This Java source file initialize the application.
 */

package ecosimulator;

import java.util.Random;

public class App {
  static Organism[] organisms = new Organism[100];
  static Random rn = new Random();

  public static void main(String[] args) {
    SpeciesBuilder speciesBuilder = new SpeciesBuilder();

    for (int i=0; i < 10; i++)
      organisms[i] = new Organism(speciesBuilder.getSpecies().get(0));

    for (int i = 10; i < 100; i++)
    {
      Organism parentOne = organisms[rn.nextInt(i-1)];
      Organism parentTwo = organisms[rn.nextInt(i-1)];

      while (parentTwo == parentOne)
        parentTwo = organisms[rn.nextInt(i-1)];

      organisms[i] = parentOne.breed(parentTwo);
    }

    showTable();
  }

  public static void showTable() {
    String leftAlignFormat = "| %-9d | %-5s | %-6s | %-9s | %-8f |%n";

    System.out.format("+-----------+-------+-----------+------------+---------+%n");
    System.out.format("| Genoma ID | Speed |   Sight   | Breed Urge | Fitness |%n");
    System.out.format("+-----------+-------+-----------+------------+---------+%n");

    for (int i = 0; i < organisms.length; i++)
      System.out.format (
              leftAlignFormat,
              i+1,
              organisms[i].genoma.getGeneSequenceString("Speed"),
              organisms[i].genoma.getGeneSequenceString("Sight"),
              organisms[i].genoma.getGeneSequenceString("Breed urge"),
              organisms[i].genoma.getFitness()
      );

    System.out.format("+-----------+-------+-----------+------------+---------+%n");
  }

}
