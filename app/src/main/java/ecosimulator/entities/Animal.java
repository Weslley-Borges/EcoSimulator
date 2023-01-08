package ecosimulator.entities;

import ecosimulator.interfaces.Status;
import ecosimulator.models.Organism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animal extends Organism {
  public Animal(Genoma genoma, Specie specie, int xPosition, int yPosition) {
    this.genoma = genoma;
    this.specie = specie;
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.lifespan = this.specie.getLifespan();
  }

  public void run() {
    this.breedUrge += this.genoma.getFactorsSum("Breed urge") + 1;
    this.foodUrge ++;
    this.age++;

    if (this.age >= this.lifespan || (this.foodUrge >= 100 && this.specie.getSpecieName().equals("Wolf"))) {
      this.setStatus(Status.DEAD);
      return;
    }

    if (!this.hunt())
      this.moveRandomly();

    this.breed();
    this.escape();
  }

  public void breed() {
    if (this.breedUrge < 50 || this.age < this.specie.getMaturityAge()) {
      return;
    }
    if (this.breedUrge > 100) {
      this.breedUrge = 100;
    }

    // Procura um possível parceiro

    List<String> mySpecie = new ArrayList<>();
    mySpecie.add(this.specie.getSpecieName());
    Map<Integer, Organism> targets = this.getTargets(mySpecie);

    if (targets == null) return;

    Organism partner = null;

    while (partner == null && targets.size() > 0)
    {
      int minDist = (int) targets.keySet().toArray()[0];
      for (int dist : targets.keySet())
        if (dist < minDist) minDist = dist;

      Organism possiblePartner = targets.get(minDist);
      boolean isMature = possiblePartner.getAge() >= possiblePartner.getSpecie().getMaturityAge();

      if ( isMature && possiblePartner.getBreedUrge() >= 50)
        partner = possiblePartner;
      else
        targets.remove(minDist);
    }

    if (partner == null) {
      return;
    }
    if (partner.getBreedUrge() > 100) {
      partner.setBreedUrge(100);
    }

    this.moveTo(partner.getxPosition(), partner.getyPosition());

    if (this.getDistance(partner.getxPosition(), partner.getyPosition()) <= s.tileSize * 3) {
      Genoma filhoteGenoma = this.genoma;

      // Combinar alguns valores de cada gene
      for (String geneName : this.genoma.getGenes().keySet())
      {
        int[] myGene = this.genoma.getGeneSequence(geneName);
        int[] secondGene = partner.getGenoma().getGeneSequence(geneName);
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

      s.newBorns.add(new Animal(filhoteGenoma, this.specie, this.xPosition, this.yPosition));
      partner.setBreedUrge(partner.getBreedUrge() / 2);
      partner.setFoodUrge(partner.getFoodUrge() + 50);
      this.breedUrge /= 2;
      this.foodUrge +=  50;

    }
  }

  public boolean hunt() {
    if (this.foodUrge >= 100)
      return false;

    Map<Integer, Organism> targets = this.getTargets(this.specie.getPreys());

    if (targets == null)
      return false;

    int minDist = (int) targets.keySet().toArray()[0];
    for (int dist : targets.keySet())
      if (dist < minDist)
        minDist = dist;

    Organism closest = targets.get(minDist);
    this.moveTo(closest.getxPosition(), closest.getyPosition());

    if (this.getDistance(closest.getxPosition(), closest.getyPosition()) <= s.tileSize * 2) {
      closest.setStatus(Status.DEAD);
      this.foodUrge -= 40;//(100 - closest.getFoodUrge());
    }
    return true;
  }

  public void escape() {
    Map<Integer, Organism> targets = this.getTargets(this.specie.getPredators());

    if (targets == null) return;

    int minDist = (int) targets.keySet().toArray()[0];
    for (int dist : targets.keySet())
      if (dist < minDist)
        minDist = dist;

    Organism closest = targets.get(minDist);

    this.runAway(closest.getxPosition(), closest.getyPosition());
  }

  private Map<Integer,Organism> getTargets(List<String> targetSpecies) {
    Map<Integer, Organism> targets = new HashMap<>();

    s.individuals.forEach(i ->
    {
      int distance = this.getDistance(i.getxPosition(), i.getxPosition());
      boolean isFromType = targetSpecies.contains(i.specie.getSpecieName());
      boolean isOnSight = distance <= this.genoma.getFactorsSum("Sight") * s.tileSize;
      boolean isAlive = i.getStatus() == Status.NORMAL;

      if (isFromType && isOnSight && isAlive)
        targets.put(distance, i);
    });

    if (targets.size() == 0)
      return null;

    return targets;
  }


  public void moveTo(int xTarget, int yTarget) {
    int speed = this.genoma.getFactorsSum("Speed");
    int dX = this.xPosition - xTarget;
    int dY = this.yPosition - yTarget;

    this.xPosition += speed * Math.cos(dX) * s.tileSize;
    this.xPosition += speed * Math.sin(dY) * s.tileSize;
    this.putOnScreen();
  }

  public void runAway(int xFrom, int yFrom) {
    int speed = this.genoma.getFactorsSum("Speed");
    int dX = xFrom - this.xPosition;
    int dY = yFrom - this.yPosition;

    if (dX < 0)
      this.xPosition += s.tileSize * speed;
    else if (dX > 0)
      this.xPosition -= s.tileSize * speed;

    if (dY < 0)
      this.yPosition += s.tileSize * speed;
    else if (dY > 0)
      this.yPosition -= s.tileSize * speed;

    this.putOnScreen();
  }

  private int getDistance(int xTarget, int yTarget) {
    int dX = xTarget - this.xPosition;
    int dY = yTarget - this.yPosition;

    return (int) Math.sqrt((dX*dX + dY*dY));
  }

  public void moveRandomly() {
    if (status == Status.DEAD)
      return;

    int speed = this.genoma.getFactorsSum("Speed");

    this.xPosition += (s.rn.nextInt(-speed, speed + 1)) * s.tileSize;
    this.yPosition += (s.rn.nextInt(-speed, speed + 1)) * s.tileSize;
    this.putOnScreen();
  }


  private void putOnScreen() {
    if (this.xPosition < 0) this.xPosition = 0;
    else if (this.xPosition >= s.simulationWidth)
      this.xPosition = s.simulationWidth - s.tileSize;

    if (this.yPosition < 0) this.yPosition = 0;
    else if (this.yPosition >= s.simulationHeight)
      this.yPosition = s.simulationHeight - s.tileSize;
  }

}