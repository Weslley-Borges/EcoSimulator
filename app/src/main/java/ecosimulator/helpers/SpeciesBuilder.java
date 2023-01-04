package ecosimulator.helpers;

import ecosimulator.entities.Specie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
                genes
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
