package Sockets.Soap;

import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;

public class SoapManager
{
    //<editor-fold desc="variables">

    MessageFactory factory;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public SoapManager()
    {
        try
        {
            factory = MessageFactory.newInstance();
        } catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
        }
    }


    //</editor-fold>

    //<editor-fold desc="methods">

    public SOAPMessage encode(UnsoapedMessage unsoapedMessage)
    {
        try
        {
            SOAPMessage message = factory.createMessage();
            SOAPPart part = message.getSOAPPart();
            SOAPEnvelope envelope = part.getEnvelope();
            SOAPHeader header = envelope.getHeader();
            SOAPBody body = envelope.getBody();

            header.addAttribute(envelope.createName("Id"), unsoapedMessage.getId());
            header.addAttribute(envelope.createName("Layer"), unsoapedMessage.getLayer());
            SOAPBodyElement bodyElement = body.addBodyElement(envelope.createName("Message"));
            bodyElement.addChildElement("MessageText").addTextNode(unsoapedMessage.getMessage());

            return message;
        } catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
            return null;
        }
    }

    public UnsoapedMessage decode(String input)
    {

        try
        {
            SOAPMessage message = factory.createMessage(null, new ByteArrayInputStream(input.getBytes()));
            SOAPPart part = message.getSOAPPart();
            SOAPEnvelope envelope = part.getEnvelope();
            SOAPHeader header = envelope.getHeader();
            SOAPBody body = envelope.getBody();

            UnsoapedMessage unsoapedMessage = new UnsoapedMessage();
            unsoapedMessage.setId(header.getAttribute("Id"));
            unsoapedMessage.setLayer(header.getAttribute("Layer"));
            NodeList messageNode = body.getElementsByTagName("MessageText");
            unsoapedMessage.setMessage(messageNode.item(0).getChildNodes().item(0).getNodeValue());

            return unsoapedMessage;
        } catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
            return null;
        }
    }
}
