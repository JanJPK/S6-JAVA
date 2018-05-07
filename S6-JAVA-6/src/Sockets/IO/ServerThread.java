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
            System.out.println("Server started");
            System.out.println("Listening on port: " + nodeInformation.getPort());
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
                System.out.println("Received connection request: " + socket.getPort());
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
        System.out.println("Processing message of type: " + message.getType() + "; id: " + message.getId());
        System.out.println("Port: " + message.getTargetPort());
        System.out.println("Layer: " + message.getLayer());
        switch (message.getType())
        {
            case SINGLE:
            {
                if (message.getTargetPort().equals(String.valueOf(nodeInformation.getPort())))
                {
                    inbox.appendText(message.getMessage() + "\n");
                } else
                {
                    for (SoapOutput ot : soapOutputs)
                    {
                        send(message, ot);
                    }
                }
                break;
            }

            case LAYER:
            {
                if (message.getLayer().equals(nodeInformation.getLayer()))
                {
                    inbox.appendText(message.getMessage() + "\n");
                }
                for (SoapOutput ot : soapOutputs)
                {
                    if (!ot.getTargetNodeInformation().getLayer().equals(message.getLayer()))
                        send(message, ot);
                }
                break;
            }

            case ALLLAYERS:
            {
                inbox.appendText(message.getMessage() + "\n");
                for (SoapOutput ot : soapOutputs)
                {
                    send(message, ot);
                }
                break;
            }
        }
    }

    private void send(UnsoapedMessage message, SoapOutput soapOutput)
    {
        soapOutput.send(message);
    }

    //</editor-fold>
}
