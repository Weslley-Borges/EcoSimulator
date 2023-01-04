package ecosimulator.entities;

import java.util.*;

public class Genoma {
    private float fitness = 0;
    private final Map<String, int[]> genes = new HashMap<>();

    public Genoma(List<String> geneNames, List<Integer> valuesQuantities) {
        // Inserir os valores na sequência de cada gene (valores 1 ou 0, aleatoriamente).

        for (int i=0; i < geneNames.size(); i++)
        {
            int valuesQuantity = valuesQuantities.get(i);
            int[] values = new int[valuesQuantity];

            Random rn = new Random();
            for (int j=0; j < valuesQuantity; j++)
                values[j] = Math.abs(rn.nextInt() % 2);

            genes.put(geneNames.get(i), values);
        }
        this.setFitness();
    }

    public String getGeneSequenceString(String geneName) {
        String gene = null;

        for (int i=0; i < this.genes.size() && gene == null; i++)
        {
            String actualGeneName = this.genes.keySet().stream().toList().get(i);
            if (actualGeneName.equals(geneName)) gene = actualGeneName;
        }

        return this.geneToString(this.genes.get(geneName));
    }

    private String geneToString(int[] factors) {
        StringBuilder string = new StringBuilder();

        for (int factor : factors) string.append(factor);
        return string.toString();
    }

    public void setFitness() {
        float genesProductsSum = 0;
        float factorsSum = 0;

        for (String geneName : this.genes.keySet())
        {
            int[] sequence = this.genes.get(geneName);

            factorsSum += sequence.length;
            int subSum = 0;

            for (int value : sequence) subSum+= value;
            genesProductsSum += subSum * factorsSum;
        }

        this.fitness = genesProductsSum / factorsSum;
    }

    public float getFitness() { return this.fitness; }

    public int[] getGeneSequence(String geneName) {
        return this.genes.get(geneName);
    }

    public Map<String, int[]> getGenes() { return this.genes; }
}