package space.server;

import space.common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceServer extends UnicastRemoteObject implements SpaceInterfaceServer
{
    private ConnectedCommander commander;
    private HashMap<String, ConnectedPlayer> players;

    public SpaceServer() throws RemoteException
    {
        players = new HashMap<>();
    }

    //<editor-fold desc="rmi">

    @Override
    public void registerPlayer(SpaceInterfacePlayer connection, SpaceCommandType type, String name) throws RemoteException
    {
        System.out.println("Player " + name + type + " has connected.");
        ConnectedPlayer player = new ConnectedPlayer(connection, type, name);
        players.put(name, player);
        if (commander != null)
        {
            commander.getConnection().receivePlayerList(createPlayersList());
        } else
        {
            System.out.println("Error, commander is null.");
        }
    }

    @Override
    public void registerCommander(SpaceInterfaceCommander connection, String name) throws RemoteException
    {
        System.out.println("Commander " + name + " has connected.");
        commander = new ConnectedCommander(connection, name);
        commander.getConnection().receiveScore(2137);
    }

    @Override
    public void removePlayer(String name) throws RemoteException
    {
        System.out.println("Commander " + name + " has connected.");
        players.remove(name);
        if (commander != null)
        {
            commander.getConnection().receivePlayerList(createPlayersList());
        } else
        {
            System.out.println("Error, commander is null.");
        }
    }

    @Override
    public void removeCommander(String name) throws RemoteException
    {
        System.out.println("Commander " + name + " has connected.");
        commander = null;
    }

    @Override
    public void broadcastScore(int score) throws RemoteException
    {
        System.out.println("Score changed by " + score);
        commander.getConnection().receiveScore(score);
    }

    @Override
    public void broadcastCommand(SpaceCommand spaceCommand) throws RemoteException
    {
        System.out.println("Sending " + spaceCommand.getType() + " command.");
        for (Map.Entry<String, ConnectedPlayer> entry : players.entrySet())
        {
            if (entry.getValue().getType() == spaceCommand.getType())
                entry.getValue().getConnection().receiveCommand(spaceCommand);
        }
    }

    //</editor-fold>

    private List<String> createPlayersList()
    {
        List<String> playerDisplayNames = new ArrayList<>();
        for (Map.Entry<String, ConnectedPlayer> entry : players.entrySet())
        {
            playerDisplayNames.add(entry.getValue().getName() + " " + entry.getValue().getType());
        }
        return playerDisplayNames;
    }
}
