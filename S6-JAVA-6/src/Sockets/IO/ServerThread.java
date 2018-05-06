package Sockets.IO;

import Sockets.NodeInformation;
import Sockets.Soap.SoapManager;
import Sockets.Soap.UnsoapedMessage;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServerThread implements Runnable
{
    //<editor-fold desc="variables">

    private boolean work;
    private NodeInformation nodeInformation;
    private List<SoapOutput> soapOutputs;
    private List<String> processedMessages;
    private ServerSocket serverSocket;
    private SoapManager soapManager;
    private TextArea inbox;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public ServerThread(NodeInformation nodeInformation, TextArea inbox, List<SoapOutput> soapOutputs)
    {
        try
        {
            this.nodeInformation = nodeInformation;
            this.soapOutputs = soapOutputs;
            serverSocket = new ServerSocket(nodeInformation.getPort());
            soapManager = new SoapManager();
            this.inbox = inbox;
            work = true;
            processedMessages = new ArrayList<>();
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
                Socket socket = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String lines = input.lines().collect(Collectors.joining());
                if (!Objects.equals(lines, ""))
                {
                    System.out.println("Received SOAP: " + lines);
                    UnsoapedMessage message = soapManager.decode(lines);
                    System.out.println("unSOAPed: " + message.getMessage());

                    if (!processedMessages.contains(message.getId()))
                    {
                        processMessage(message);
                    }
                }

            } catch (Exception ex)
            {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
    }

    private void processMessage(UnsoapedMessage message)
    {
        processedMessages.add(message.getId());
        switch (message.getType())
        {
            case SINGLE:
            {
                if (message.getTargetPort().equals(nodeInformation.getPort()))
                {
                    inbox.appendText(message.getMessage());
                } else
                {
                    for (SoapOutput ot : soapOutputs)
                    {
                        if (ot.getTargetNodeInformation().getLayer().equals(message.getLayer()))
                            send(message, ot);
                    }
                }
                break;
            }

            case LAYER:
            {
                if (message.getLayer().equals(nodeInformation.getLayer()))
                {
                    inbox.appendText(message.getMessage());
                } else
                {
                    for (SoapOutput ot : soapOutputs)
                    {
                        if (!ot.getTargetNodeInformation().getLayer().equals(message.getLayer()))
                            send(message, ot);
                    }
                }
                break;
            }

            case ALLLAYERS:
            {
                inbox.appendText(message.getMessage());
                for (SoapOutput ot : soapOutputs)
                {
                    send(message, ot);
                }
                break;
            }
        }
    }

    private void send(UnsoapedMessage message, SoapOutput thread)
    {

    }

    //</editor-fold>
}
