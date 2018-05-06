package Sockets;

public class NodeInformation
{
    private int port;
    private String layer;

    public NodeInformation(int port, String layer)
    {
        this.port = port;
        this.layer = layer;
    }

    public String getLayer()
    {
        return layer;
    }

    public void setLayer(String layer)
    {
        this.layer = layer;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

}
