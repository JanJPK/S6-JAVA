package genotype.db;

import genotype.db.model.Flagella;
import genotype.db.model.Toughness;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DatabaseGenerator
{

    public List<Toughness> generateToughness(int count)
    {
        List<Toughness> toughness = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            int beta = ThreadLocalRandom.current().nextInt(10, 50);
            int gamma = ThreadLocalRandom.current().nextInt(10, 50);
            String rank;

            switch (ThreadLocalRandom.current().nextInt(1, 5))
            {
                case 1:
                    rank = "a";
                    break;
                case 2:
                    rank = "b";
                    break;
                case 3:
                    rank = "c";
                    break;
                case 4:
                    rank = "d";
                    break;
                default:
                    rank = "a";
                    break;
            }
            toughness.add(new Toughness(beta, gamma, rank));
        }
        return toughness;
    }

    public List<Flagella> generateFlagella(int count)
    {
        List<Flagella> flagella = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            int alpha = ThreadLocalRandom.current().nextInt(10, 50);
            int beta = ThreadLocalRandom.current().nextInt(10, 50);
            int number = ThreadLocalRandom.current().nextInt(1, 4);
            flagella.add(new Flagella(alpha, beta, number));
        }
        return flagella;
    }
}
