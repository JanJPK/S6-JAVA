package space.server;

import space.common.SpaceInterfaceCommander;

public class ConnectedCommander
{
    private SpaceInterfaceCommander connection;
    private String name;

    public ConnectedCommander(SpaceInterfaceCommander connection, String name)
    {
        this.connection = connection;
        this.name = name;
    }

    public SpaceInterfaceCommander getConnection()
    {
        return connection;
    }

    public void setConnection(SpaceInterfaceCommander connection)
    {
        this.connection = connection;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
