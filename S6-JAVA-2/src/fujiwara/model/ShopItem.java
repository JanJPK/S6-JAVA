package fujiwara.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopItem
{
    //<editor-fold desc="variables">

    @XmlElement
    private String imagePath;
    @XmlElement
    private Date dateOfLastShipment;
    @XmlElement
    private String name;
    @XmlElement
    private long price;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public ShopItem()
    {

    }

    public ShopItem(String imagePath)
    {
        this.imagePath = imagePath;
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    public long getPrice()
    {
        return price;
    }

    public void setPrice(long price)
    {
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDateOfLastShipment()
    {
        return dateOfLastShipment;
    }

    public void setDateOfLastShipment(Date dateOfLastShipment)
    {
        this.dateOfLastShipment = dateOfLastShipment;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    //</editor-fold>

    //<editor-fold desc="methods">

    @Override
    public String toString()
    {
        return name;
    }

    //</editor-fold>
}
