package Sockets.Soap;

public class UnsoapedMessage
{
    private String id;
    private String layer;
    private String message;

    public UnsoapedMessage()
    {
    }

    public UnsoapedMessage(String id, String layer, String message)
    {
        this.id = id;
        this.layer = layer;
        this.message = message;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLayer()
    {
        return layer;
    }

    public void setLayer(String layer)
    {
        this.layer = layer;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
