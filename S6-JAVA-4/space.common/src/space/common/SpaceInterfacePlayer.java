package space.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpaceInterfacePlayer extends Remote
{
    void confirmConnection(String message) throws RemoteException;
    void receiveCommand(SpaceCommand command) throws RemoteException;
}
