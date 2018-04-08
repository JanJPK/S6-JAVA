package space.server;

import space.common.SpaceCommandType;
import space.common.SpaceInterfacePlayer;

public class ConnectedPlayer
{
    private SpaceInterfacePlayer connection;
    private SpaceCommandType type;
    private String name;

    public ConnectedPlayer(SpaceInterfacePlayer connection, SpaceCommandType type, String name)
    {
        this.connection = connection;
        this.type = type;
        this.name = name;
    }

    public SpaceInterfacePlayer getConnection()
    {
        return connection;
    }

    public void setConnection(SpaceInterfacePlayer connection)
    {
        this.connection = connection;
    }

    public SpaceCommandType getType()
    {
        return type;
    }

    public void setType(SpaceCommandType type)
    {
        this.type = type;
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
