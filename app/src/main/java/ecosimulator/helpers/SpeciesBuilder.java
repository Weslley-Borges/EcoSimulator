package ecosimulator.helpers;

import ecosimulator.entities.Specie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.FileReader;
import java.util.List;
import java.util.*;

public class SpeciesBuilder {
  protected ArrayList<Specie> species = new ArrayList<>();
  public SpeciesBuilder() {
    String data = Objects.requireNonNull(getClass().getClassLoader().getResource("Data.json")).getFile();
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader(data)) {
      JSONObject configData = (JSONObject) jsonParser.parse(reader);
      JSONArray speciesJSON = (JSONArray) configData.get("species");

      for (Object o : speciesJSON) {
        JSONObject specieJSON = (JSONObject) o;
        JSONArray genesJSON = (JSONArray) specieJSON.get("genes");
        Map<String, Integer> genes = new HashMap<>();
        List specieColorJSON = ((JSONArray) specieJSON.get("color")).stream().toList();
        int[] specieColor = new int[3];

        for (int i=0; i < 3; i++) {
          int c = ((Long) specieColorJSON.get(i)).intValue();
          specieColor[i] = c;
        }

        for (Object gene : genesJSON)
        {
          String geneName = (String) ((JSONObject) gene).keySet().toArray()[0];
          int factorsQuantity = ((Long) ((JSONObject) gene).values().toArray()[0]).intValue();

          genes.put(geneName, factorsQuantity);
        }

        Specie specie = new Specie(
            (String) specieJSON.get("name"),
            ((Long) specieJSON.get("initial_amount")).intValue(),
            ((Long) specieJSON.get("lifespan")).intValue(),
            genes,
            new Color(specieColor[0], specieColor[1], specieColor[2])
        );

        species.add(specie);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Specie> getSpecies() {
    return species;
  }
}
