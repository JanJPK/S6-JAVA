package Sockets.App;

import Sockets.IO.ServerThread;
import Sockets.IO.SoapOutput;
import Sockets.NodeInformation;
import Sockets.Soap.UnsoapedMessage;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocketsController
{
    public TextField textFieldPort;
    public TextField textFieldLayer;
    public TextArea textAreaInbox;
    public TextArea textAreaOutbox;
    public TextField textFieldTarget;
    public TextField textFieldTargetPort;
    public TextField textFieldMessageTarget;
    public RadioButton radioButtonAlllayers;
    public RadioButton radioButtonLayer;
    public RadioButton radioButtonSingle;
    public TextField textFieldTargetLayer;
    public TextField textFieldQuickConfig;

    private NodeInformation nodeInformation;
    private ServerThread serverThread;
    private List<SoapOutput> soapOutputs;
    private int counter;

    private List<Integer> ports = Arrays.asList(2222, 2223, 2224, 2225, 2226, 2227, 2228, 2229, 2230);

    public void initialize()
    {
        soapOutputs = new ArrayList<>();
        counter = 0;
    }

    public void sendMessage()
    {
        if (serverThread == null)
            return;

        UnsoapedMessage message = new UnsoapedMessage();
        message.setMessage(textAreaOutbox.getText());
        String messageTarget = textFieldMessageTarget.getText();
        if (radioButtonSingle.isSelected())
        {
            message.setType("SINGLE");
            message.setTargetPort(messageTarget);
            message.setLayer("NONE");
        }
        if (radioButtonLayer.isSelected())
        {
            message.setType("LAYER");
            message.setTargetPort("0");
            message.setLayer(messageTarget);
        }
        if (radioButtonAlllayers.isSelected())
        {
            message.setType("ALLLAYERS");
            message.setTargetPort("0");
            message.setLayer("NONE");
        }
        String id = String.valueOf(nodeInformation.getPort()) + String.valueOf(counter);
        message.setId(id);

        sendToAllSoapOutputs(message);
        counter++;
    }

    public void start()
    {
        nodeInformation = new NodeInformation(Integer.parseInt(textFieldPort.getText()), textFieldLayer.getText());
        serverThread = new ServerThread(nodeInformation, textAreaInbox, soapOutputs);
        Thread thread = new Thread(serverThread);
        thread.start();
    }

    public void connectToTargetPort()
    {
        if (serverThread == null)
            return;

        NodeInformation targetNode = new NodeInformation(Integer.parseInt(textFieldTargetPort.getText()), textFieldTargetLayer.getText());
        soapOutputs.add(new SoapOutput(targetNode, nodeInformation.getPort()));
    }

    public void quickConfig()
    {
        int index = Integer.parseInt(textFieldQuickConfig.getText());
        textFieldPort.setText(String.valueOf(ports.get(index)));
        String layer;
        if (index > 5)
            layer = "c";
        else
        {
            if (index > 2)
                layer = "b";
            else
                layer = "a";
        }
        textFieldLayer.setText(layer);
        start();
    }

    private void sendToAllSoapOutputs(UnsoapedMessage message)
    {
        System.out.println("Sending a message to everyone: " + message.getMessage());
        for (SoapOutput so : soapOutputs)
        {
            so.send(message);
        }
    }
}
