package ecosimulator.interfaces;

import java.awt.*;
import java.util.Map;

public interface ISpecie {
  Map<String, Integer> getGenes();
  String getSpecieName();
  Color getColor();
}
