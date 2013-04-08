package es.ieci.plusvalias.endpoint;

import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.server.endpoint.AbstractDomPayloadEndpoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author angel_castro@ieci.es - 28/07/2010
 */
public abstract class AbstractPlusvaliaEndpoint extends AbstractDomPayloadEndpoint
{
	public static Logger logger = Logger.getLogger(AbstractPlusvaliaEndpoint.class);
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;

	protected Element invokeInternal(Element requestElement, Document responseDocument) throws Exception
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("Incoming Liquidacion request");
		}

		Object request = null;
		Node nextNode = requestElement;
		short nodeType = 0;
		boolean success = false;

		do
		{
			nodeType = nextNode.getNodeType();
			
			if (nodeType == Node.ELEMENT_NODE && !nextNode.getNodeName().equals("ns1:MESSAGE_REQUEST"))
			{
				DOMSource source = new DOMSource(nextNode);
				request = unmarshaller.unmarshal(source);
				success = true;
			}
			else if (nodeType == Node.ELEMENT_NODE)
			{
				nextNode = nextNode.getFirstChild();
			}
			else
			{ // Saltar hijos tipo texto.
				nextNode = nextNode.getNextSibling();
			}
		}
		while (!success && nextNode != null);

		Object response = invoke(request, xmlToString(nextNode));

		DOMResult result = new DOMResult();
		marshaller.marshal(response, result);

		DOMSource source = new DOMSource(result.getNode());
		Node nodeDocumento = source.getNode();
		Node nodeElemento = nodeDocumento.getFirstChild();

		Node nodeResponse = responseDocument.importNode(nodeElemento, true);

		if (requestElement.getNodeName().equals("ns1:MESSAGE_REQUEST"))
		{
			Element responseElement = responseDocument.createElementNS("http://192.168.4.2:85/plusvalias3/services/SOAPGateway", "ns1:MESSAGE_RESPONSE");
			responseElement.appendChild(nodeResponse);
			
			return responseElement;
		}
		else
		{
			return (Element) nodeResponse;
		}
	}

	public void setUnmarshaller(Unmarshaller unmarshaller)
	{
		this.unmarshaller = unmarshaller;
	}

	public void setMarshaller(Marshaller marshaller)
	{
		this.marshaller = marshaller;
	}

	public String xmlToString(Node node)
	{
		try
		{
			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString();
		}
		catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (TransformerException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public abstract Object invoke(Object request, String xml) throws Exception;
}
