package Sockets.IO;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputThread implements Runnable
{
    //<editor-fold desc="variables">

    private boolean work;
    private Socket socket;
    private TextArea inbox;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public InputThread(Socket socket, TextArea inbox)
    {
        this.socket = socket;
        this.inbox = inbox;
        work = true;
    }

    //</editor-fold>

    //<editor-fold desc="get/set">


    //</editor-fold>

    //<editor-fold desc="methods">

    public void run()
    {
        try
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (work)
            {
                try
                {
                    String inputString = input.readLine();

                } catch (Exception ex)
                {
                    System.out.println("Exception: " + ex.getMessage());
                }

            }

        } catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
        }

    }

    //private void decide()

    //</editor-fold>
}
