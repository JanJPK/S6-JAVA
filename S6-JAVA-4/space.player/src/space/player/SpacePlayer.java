package space.player;

import space.common.SpaceCommand;
import space.common.SpaceCommandType;
import space.common.SpaceInterfacePlayer;
import space.common.SpaceInterfaceServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SpacePlayer extends UnicastRemoteObject implements SpaceInterfacePlayer
{
    private SpaceInterfaceServer server;
    private PlayerController controller;

    public SpacePlayer(String name, PlayerController controller, SpaceCommandType type) throws RemoteException
    {
        try
        {
            String url = "rmi://localhost/sserver";
            server = (SpaceInterfaceServer) Naming.lookup(url);
            server.registerPlayer(this, type, name);
            this.controller = controller;
        } catch (RemoteException ex)
        {
            System.out.println("Server RemoteException.");
            System.out.println(ex.getMessage());
        } catch (NotBoundException ex)
        {
            System.out.println("Server NotBoundException.");
            System.out.println(ex.getMessage());
        } catch (MalformedURLException ex)
        {
            System.out.println("Server MalformedURLException.");
            System.out.println(ex.getMessage());
        }
    }

    public SpaceInterfaceServer getServer()
    {
        return server;
    }

    //<editor-fold desc="rmi">

    @Override
    public void confirmConnection(String s) throws RemoteException
    {

    }

    @Override
    public void receiveCommand(SpaceCommand spaceCommand) throws RemoteException
    {
        System.out.println("Received command " + spaceCommand.getType() + " " + spaceCommand.getParameters() + ".");
        String text = spaceCommand.getType().name() + " " + spaceCommand.getParameters().toString();
        controller.textField.setText(text);
        controller.CurrentCommand = spaceCommand;
    }

    //</editor-fold>

}
