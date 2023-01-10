package ecosimulator.entities;

import ecosimulator.interfaces.Status;
import ecosimulator.models.AnimalSpecie;
import ecosimulator.models.Organism;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Animal extends Organism {

  private final AnimalSpecie specie;
  private int age;

  public Animal(Genoma genoma, AnimalSpecie specie, int xPosition, int yPosition) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.genoma = genoma;
    this.specie = specie;
  }

  public void run() {
    this.food--;
    this.age++;

    if (this.age >= this.specie.getLifespan()) {
      this.status = Status.DEAD;
      return;
    }

    if (this.food <= 0) {
      this.setStatus(Status.DEAD);
      return;
    }

    this.searchPartner();
    this.escape();

    if (!this.hunt())
      this.moveRandomly();
  }

  public void searchPartner() {
    if (this.food <= 50 || this.age < this.specie.getMaturityAge())
      return;

    // Procura um possível parceiro

    List<String> mySpecie = Collections.singletonList(this.specie.getSpecieName());
    Map<Integer, Organism> targets = this.getTargets(mySpecie);

    if (targets != null) {
      int minDist = 10000;

      for (int dist : targets.keySet())
      {
        Animal o = (Animal) targets.get(dist);
        boolean isMature = o.getAge() >= o.getSpecie().getMaturityAge();

        if (dist < minDist && isMature && o.getFood() > 50)
          minDist = dist;
      }

      Organism p = targets.get(minDist);
      if (p == null)
        return;
      this.moveTo(p.getxPosition(), p.getyPosition());

      if (this.getDistance(p.getxPosition(), p.getyPosition()) <= this.genoma.getFactorsSum("Speed") * s.tileSize) {
        for (int i=0; i < this.specie.getChildrenByBreed(); i++)
          this.breed(p);

        p.setFood(p.getFood() - 50);
        this.food -=  50;
      }

    }
  }

  private void breed(Organism partner) {
      this.xPosition = partner.getxPosition();
      this.yPosition = partner.getyPosition();
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
  }

  public boolean hunt() {
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
      this.xPosition = closest.getxPosition();
      this.yPosition = closest.getyPosition();

      if (closest instanceof Animal){
        closest.setStatus(Status.DEAD);
        this.food += closest.getFood();

      } else {
        Plant plant = (Plant) closest;
        this.food += (100 - this.food);
        plant.setFood(this.getFood() - (100 - this.food));
      }
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
      boolean isFromType = false;

      if (i instanceof Animal) isFromType = targetSpecies.contains(((Animal) i).getSpecie().getSpecieName());
      if (i instanceof Plant) isFromType = targetSpecies.contains(((Plant) i).getSpecie().getSpecieName());

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

  public void draw(Graphics2D g2) {
    g2.setColor(this.specie.getColor());
    Rectangle2D rect = new Rectangle2D.Double(this.xPosition, this.yPosition, s.tileSize, s.tileSize);

    g2.fill(rect);
  }

  public int getAge() { return age; }
  public AnimalSpecie getSpecie() {
    return specie;
  }
}