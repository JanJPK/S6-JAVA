package Sockets.IO;

import javafx.scene.control.TextArea;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread implements Runnable
{
    //<editor-fold desc="variables">

    private boolean work;
    private ServerSocket serverSocket;
    private List<InputThread> inputThreads;
    private TextArea inbox;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public ServerThread(int port, TextArea inbox)
    {
        try
        {
            serverSocket = new ServerSocket(port);
            inputThreads = new ArrayList<>();
            this.inbox = inbox;
            work = true;
        } catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
        }

    }

    //</editor-fold>

    //<editor-fold desc="get/set">


    //</editor-fold>

    //<editor-fold desc="methods">

    public void run()
    {
        while (work)
        {
            try
            {
                Socket newSocket = serverSocket.accept();
                InputThread newThread = new InputThread(newSocket, inbox);
                inputThreads.add(newThread);
                newThread.run();
            } catch (Exception ex)
            {
                System.out.println("Exception: " + ex.getMessage());
            }

        }
    }

    //</editor-fold>
}
