package genotype.db.model;

public class Toughness
{
    int id;
    int beta;
    int gamma;
    String rank;

    public Toughness(int beta, int gamma, String rank)
    {
        this.beta = beta;
        this.gamma = gamma;
        this.rank = rank;
    }

    public Toughness(int id, int beta, int gamma, String rank)
    {
        this.id = id;
        this.beta = beta;
        this.gamma = gamma;
        this.rank = rank;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getBeta()
    {
        return beta;
    }

    public void setBeta(int beta)
    {
        this.beta = beta;
    }

    public int getGamma()
    {
        return gamma;
    }

    public void setGamma(int gamma)
    {
        this.gamma = gamma;
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
    }
}
