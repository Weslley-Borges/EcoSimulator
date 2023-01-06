package ecosimulator.entities;

import ecosimulator.Simulation;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Organism {
  private final Genoma genoma;
  private final String specieName;
  private final Simulation s = Simulation.getInstance();
  private double xPosition, yPosition;
  private Color color;

  public Organism(Specie specie) {
    List<String> geneNames = specie.getGenes().keySet().stream().toList();
    List<Integer> valuesQuantities = specie.getGenes().values().stream().toList();

    this.genoma = new Genoma(geneNames, valuesQuantities);
    this.specieName = specie.getSpecieName();
    this.color = specie.getColor();

    this.xPosition = s.rn.nextInt(s.simulationWidth);
    this.yPosition = s.rn.nextInt(s.simulationHeight);
  }

  public Organism(Genoma genoma, String specieName, double xPosition, double yPosition) {
    this.genoma = genoma;
    this.specieName = specieName;
    this.xPosition = xPosition;
    this.yPosition = yPosition;
  }

  public Organism breed(Organism secondParent) {
    Genoma filhoteGenoma = this.genoma;

    // Combinar alguns valores de cada gene
    for (String geneName : this.genoma.getGenes().keySet())
    {
      int[] myGene = this.genoma.getGeneSequence(geneName);
      int[] secondGene = secondParent.genoma.getGeneSequence(geneName);
      int breedOverPoint = s.rn.nextInt(myGene.length);

      for (int j=0; j < breedOverPoint; j++)
        filhoteGenoma.getGeneSequence(geneName)[j] = myGene[j] == 1 && secondGene[j] == 1 ? 1 : 0;
    }

    // Mutações (alterar 2 valores de cada gene)
    for (int[] geneSequence : filhoteGenoma.getGenes().values())
      for (int i =0; i < 2; i++) {
        int MutationPoint = s.rn.nextInt(geneSequence.length);
        geneSequence[MutationPoint] = geneSequence[MutationPoint] == 0 ? 1 : 0;
      }

    return new Organism(filhoteGenoma, this.specieName, this.xPosition, this.yPosition);
  }

  public void moveTo(int xTarget, int yTarget) {
    double dX = xTarget - this.xPosition;
    double dY = yTarget - this.yPosition;
    double direction = Math.atan2(dY,dX);
    int speed = this.genoma.getFactorsSum("Speed");

    this.setxPosition(this.xPosition + (speed * Math.cos(direction)));
    this.setyPosition(this.yPosition + (speed * Math.sin(direction)));
  }

  public void moveRandomly() {
    int speed = this.genoma.getFactorsSum("Speed");

    this.setxPosition( this.xPosition + (s.rn.nextInt(-speed, speed + 1) * 10) );
    this.setyPosition( this.yPosition + (s.rn.nextInt(-speed, speed + 1) * 10) );

    this.putOnScreen();
  }

  private void putOnScreen() {
    if (this.xPosition < 0) this.xPosition = 0;
    else if (this.xPosition >= s.simulationWidth)
      this.xPosition = s.simulationWidth - 10;

    if (this.yPosition < 0) this.yPosition = 0;
    else if (this.yPosition >= s.simulationHeight)
      this.yPosition = s.simulationHeight - 10;
  }

  public void draw(Graphics2D g2) {
    g2.setColor(this.color);
    Rectangle2D rect = new Rectangle2D.Double(this.xPosition, this.yPosition, 10, 10);

    g2.fill(rect);
  }

  public String getSpecieName() { return specieName; }
  public double getxPosition() { return xPosition; }
  public double getyPosition() { return yPosition; }

  public void setxPosition(double xPosition) { this.xPosition = xPosition; }
  public void setyPosition(double yPosition) { this.yPosition = yPosition; }
}