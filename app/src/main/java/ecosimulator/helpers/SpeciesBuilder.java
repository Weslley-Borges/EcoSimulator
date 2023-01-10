package ecosimulator.helpers;

import ecosimulator.models.AnimalSpecie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.FileReader;
import java.util.List;
import java.util.*;

public class SpeciesBuilder {
  protected ArrayList<AnimalSpecie> species = new ArrayList<>();
  public SpeciesBuilder() {
    String data = Objects.requireNonNull(getClass().getClassLoader().getResource("Data.json")).getFile();
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader(data)) {
      JSONObject configData = (JSONObject) jsonParser.parse(reader);
      JSONArray speciesJSON = (JSONArray) configData.get("species");

      for (Object o : speciesJSON) {
        JSONObject specieJSON = (JSONObject) o;

        // Cor da espécie

        JSONArray specieColorJSON = (JSONArray) specieJSON.get("color");
        List<Integer> specieColor = new ArrayList<>();

        for (Object c : specieColorJSON) specieColor.add( ((Long) c).intValue() );

        // Predadores e presas

        JSONArray speciePredatorsJSON = (JSONArray) specieJSON.get("predators");
        List<String> speciePredators = new ArrayList<>();
        JSONArray speciePreysJSON = (JSONArray) specieJSON.get("preys");
        List<String> speciePreys = new ArrayList<>();

        for (Object c : speciePredatorsJSON) speciePredators.add((String) c);
        for (Object c : speciePreysJSON) speciePreys.add((String) c);

        // Genes

        JSONArray genesJSON = (JSONArray) specieJSON.get("genes");
        Map<String, Integer> genes = new HashMap<>();

        for (Object gene : genesJSON)
        {
          String geneName = (String) ((JSONObject) gene).keySet().toArray()[0];
          int factorsQuantity = ((Long) ((JSONObject) gene).values().toArray()[0]).intValue();

          genes.put(geneName, factorsQuantity);
        }

        // Cria a espécie

        AnimalSpecie specie = new AnimalSpecie(
            (String) specieJSON.get("name"),
            ((Long) specieJSON.get("initial_amount")).intValue(),
            ((Long) specieJSON.get("lifespan")).intValue(),
            ((Long) specieJSON.get("maturity_age")).intValue(),
            ((Long) specieJSON.get("children")).intValue(),
            genes,
            new Color(specieColor.get(0), specieColor.get(1), specieColor.get(2)),
            speciePredators,
            speciePreys
        );

        species.add(specie);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ArrayList<AnimalSpecie> getSpecies() {
    return species;
  }
}
