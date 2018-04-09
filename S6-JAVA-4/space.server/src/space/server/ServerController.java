package space.server;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.rmi.Naming;

public class ServerController
{
    public GridPane grid;

    public void startServer()
    {
        try
        {
            int port = 1099;
            String url = "rmi://localhost/sserver";
            java.rmi.registry.LocateRegistry.createRegistry(port);
            SpaceServer ss = new SpaceServer();
            Naming.rebind(url, ss);
            System.out.println("Server ready.");
        } catch (Exception ex)
        {
            System.out.println("Server exception.");
            System.out.println(ex.getMessage());
        }
    }

    public void stopServer()
    {
        Stage stage = (Stage) grid.getScene().getWindow();
        stage.close();
    }
}
