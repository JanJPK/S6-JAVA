package genotype;

import genotype.db.model.Examined;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "examinedGenotypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExaminedCollection
{
    @XmlElementWrapper(name = "examinedGenotypes")
    @XmlElement(name = "examinedGenotype")
    private List<Examined> examined;

    public ExaminedCollection()
    {

    }

    public ExaminedCollection(List<Examined> examined)
    {
        this.examined = examined;
    }

    public List<Examined> getExamined()
    {
        return examined;
    }

    public void setExamined(List<Examined> examined)
    {
        this.examined = examined;
    }
}
