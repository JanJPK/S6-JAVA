package genotype;

import genotype.db.model.Examined;
import genotype.db.model.Flagella;
import genotype.db.model.Toughness;

import java.util.List;

public class GenotypeAnalyzer
{
    private List<Flagella> flagellaList;
    private List<Toughness> toughnessList;

    public GenotypeAnalyzer(List<Flagella> flagellaList, List<Toughness> toughnessList)
    {
        this.flagellaList = flagellaList;
        this.toughnessList = toughnessList;
    }

    public Examined analyze(String genotype)
    {
        int alpha = Integer.parseInt(String.valueOf(genotype.charAt(0)) + String.valueOf(genotype.charAt(5)));
        int beta = Integer.parseInt(String.valueOf(genotype.charAt(1)) + String.valueOf(genotype.charAt(4)));
        int gamma = Integer.parseInt(String.valueOf(genotype.charAt(2)) + String.valueOf(genotype.charAt(3)));

        Flagella newFlagella = new Flagella(alpha, beta, 0);
        Toughness newToughness = new Toughness(beta, gamma, "");

        double bestDistance = Double.MAX_VALUE;
        Flagella bestFlagella = null;
        for (Flagella flagella : flagellaList)
        {
            double distance = getDistanceFlagella(flagella, newFlagella);
            if (distance < bestDistance)
            {
                bestDistance = distance;
                bestFlagella = flagella;
            }
        }
        newFlagella.setNumber(bestFlagella.getNumber());

        bestDistance = Double.MAX_VALUE;
        Toughness bestToughness = null;
        for (Toughness toughness : toughnessList)
        {
            double distance = getDistanceToughness(toughness, newToughness);
            if (distance < bestDistance)
            {
                bestDistance = distance;
                bestToughness = toughness;
            }
        }
        newToughness.setRank(bestToughness.getRank());

        String rank = newFlagella.getNumber() + newToughness.getRank();
        return new Examined(genotype, rank);
    }

    private double getDistanceFlagella(Flagella analyzed, Flagella analyzing)
    {
        double a = Math.pow(analyzed.getAlpha() - analyzing.getAlpha(), 2.0);
        double b = Math.pow(analyzed.getBeta() - analyzing.getBeta(), 2.0);
        return Math.sqrt(a + b);
    }

    private double getDistanceToughness(Toughness analyzed, Toughness analyzing)
    {
        double a = Math.pow(analyzed.getGamma() - analyzing.getGamma(), 2.0);
        double b = Math.pow(analyzed.getBeta() - analyzing.getBeta(), 2.0);
        return Math.sqrt(a + b);
    }
}
