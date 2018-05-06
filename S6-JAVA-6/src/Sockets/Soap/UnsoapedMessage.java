package Sockets.Soap;

public class UnsoapedMessage
{
    private String id;
    private String targetPort;
    private String layer;
    private String message;
    private MessageType type;

    public UnsoapedMessage()
    {
    }

    public UnsoapedMessage(String id, String targetPort, String layer, String message, MessageType type)
    {
        this.id = id;
        this.targetPort = targetPort;
        this.layer = layer;
        this.message = message;
        this.type = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTargetPort()
    {
        return targetPort;
    }

    public void setTargetPort(String targetPort)
    {
        this.targetPort = targetPort;
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

    public MessageType getType()
    {
        return type;
    }

    public void setType(String type)
    {
        switch (type)
        {
            case "SINGLE":
                this.type = MessageType.SINGLE;
                break;
            case "LAYER":
                this.type = MessageType.LAYER;
                break;
            case "ALLLAYERS":
                this.type = MessageType.ALLLAYERS;
                break;
        }
    }
}
