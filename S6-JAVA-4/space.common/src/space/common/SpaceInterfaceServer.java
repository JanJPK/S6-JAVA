package space.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpaceInterfaceServer extends Remote
{
    void registerPlayer(SpaceInterfacePlayer player, SpaceCommandType type, String name) throws RemoteException;
    void registerCommander(SpaceInterfaceCommander commander, String name) throws RemoteException;
    void removePlayer(String name) throws RemoteException;
    void removeCommander(String name) throws RemoteException;
    void broadcastScore(int score) throws RemoteException;
    void broadcastCommand(SpaceCommand command) throws RemoteException;
}
