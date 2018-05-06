package Sockets.IO;

import Sockets.NodeInformation;
import Sockets.Soap.SoapManager;
import Sockets.Soap.UnsoapedMessage;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.Socket;

public class SoapOutput
{
    //<editor-fold desc="variables">

    private SoapManager soapManager;
    private NodeInformation targetNodeInformation;
    private int parentPort;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public SoapOutput(NodeInformation targetNodeInformation, int parentPort)
    {
        soapManager = new SoapManager();
        this.targetNodeInformation = targetNodeInformation;
        this.parentPort = parentPort;
    }

    //</editor-fold>

    //<editor-fold desc="get/set">

    public NodeInformation getTargetNodeInformation()
    {
        return targetNodeInformation;
    }

    public void setTargetNodeInformation(NodeInformation targetNodeInformation)
    {
        this.targetNodeInformation = targetNodeInformation;
    }


    //</editor-fold>

    //<editor-fold desc="methods">

    public void send(UnsoapedMessage message)
    {
        try
        {
            Socket socket = new Socket(String.valueOf(parentPort), targetNodeInformation.getPort());
            SOAPMessage soapedMessage = soapManager.encode(message);
            String stringifiedSoap = stringifySoap(soapedMessage);
            if (stringifiedSoap != null)
            {
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
                output.write(stringifiedSoap);
                output.flush();
                output.close();
            }

        } catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
        }

    }

    private String stringifySoap(SOAPMessage message)
    {
        try
        {
            StringWriter sw = new StringWriter();
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource((message.getSOAPPart())), new StreamResult(sw));
            return sw.toString();

        } catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
            return null;
        }
    }

    //</editor-fold>
}
