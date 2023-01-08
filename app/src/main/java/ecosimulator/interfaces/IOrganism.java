package ecosimulator.interfaces;

import ecosimulator.Simulation;
import ecosimulator.entities.Genoma;

import java.awt.*;

public interface IOrganism {
  Genoma genoma = null;
  String specieName = null;
  Simulation s = Simulation.getInstance();
  double xPosition = 0;
  double yPosition = 0;
  Color color = null;
}
