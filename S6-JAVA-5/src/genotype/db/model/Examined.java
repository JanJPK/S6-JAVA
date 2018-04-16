package genotype.db.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Examined
{
    @XmlTransient
    int Id;
    @XmlElement(name = "genotype")
    String genotype;
    @XmlElement(name = "rank")
    String rank;

    public Examined()
    {
    }

    public Examined(String genotype, String rank)
    {
        this.genotype = genotype;
        this.rank = rank;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }

    public String getGenotype()
    {
        return genotype;
    }

    public void setGenotype(String genotype)
    {
        this.genotype = genotype;
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
    }

    @Override
    public String toString()
    {
        return genotype + " " + rank;
    }
}
