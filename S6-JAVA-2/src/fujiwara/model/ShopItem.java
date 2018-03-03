package fujiwara.model;

import java.io.File;
import java.util.Date;

public class ShopItem
{
    //<editor-fold desc="variables">

    private File imagePath;
    private Date dateOfLastShipment;
    private String name;
    private float price;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public ShopItem()
    {

    }

    public ShopItem(File imagePath)
    {
        this.imagePath = imagePath;
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
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

    public File getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(File imagePath)
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
