package ecosimulator;

import interfaces.Status;

import java.util.List;
import java.util.Random;

class Organism {
    Genoma genoma;
    String specieName;
    Status status = Status.NORMAL;
    Random rn = new Random();
    int feed = 100;

    public Organism(Specie specie) {
      List<String> geneNames = specie.getGenes().keySet().stream().toList();
      List<Integer> valuesQuantities = specie.getGenes().values().stream().toList();

      this.genoma = new Genoma(geneNames, valuesQuantities);
    }
    public Organism(Genoma genoma, String specieName) {
      this.genoma = genoma;
      this.specieName = specieName;
    }

    public Organism breed(Organism secondParent) {
        Genoma filhoteGenoma = this.genoma;

        for (String geneName : this.genoma.getGenes().keySet())
        {
            int[] myGene = this.genoma.getGeneSequence(geneName);
            int[] secondGene = secondParent.genoma.getGeneSequence(geneName);
            int breedOverPoint = rn.nextInt(myGene.length);

            for (int j=0; j < breedOverPoint; j++)
                filhoteGenoma.getGeneSequence(geneName)[j] = myGene[j] == 1 && secondGene[j] == 1 ? 1 : 0;
        }

        // Mutações
        for (int[] geneSequence : filhoteGenoma.getGenes().values())
            if (rn.nextInt(2) == 1)
            {
                int MutationPoint = rn.nextInt(geneSequence.length);
                geneSequence[MutationPoint] = geneSequence[MutationPoint] == 0 ? 1 : 0;

                MutationPoint = rn.nextInt(geneSequence.length);
                geneSequence[MutationPoint] = geneSequence[MutationPoint] == 0 ? 1 : 0;
            }

        return new Organism(filhoteGenoma, this.specieName);
    }

}