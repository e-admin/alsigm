package es.ieci.plusvalias.exception;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.oxm.MarshallingFailureException;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.AbstractEndpointExceptionResolver;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author angel_castro@ieci.es - 10/09/2010
 */
public class PlusvaliaExceptionResolver extends AbstractEndpointExceptionResolver implements MessageSourceAware
{
	public static Logger logger = Logger.getLogger(PlusvaliaExceptionResolver.class);
	private MessageSource messageSource;

	protected boolean resolveExceptionInternal(MessageContext messageContext, Object endpoint, Exception ex)
	{
		logger.error("PlusvaliaExceptionResolver source: " + ex.getMessage(), ex);

		if (messageContext.getRequest() instanceof SoapMessage)
		{
			SoapMessage request = (SoapMessage)messageContext.getRequest();
			
			SoapHeader header = request.getEnvelope().getHeader();
			
			header.removeHeaderElement(QName.valueOf("{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd}Security"));		
			
			DOMSource domSource = (DOMSource)request.getPayloadSource();
			
			if (logger.isDebugEnabled())
			{
				logger.debug("Exception SoapMessage: " + request.toString());
			}

			Node nextNode = domSource.getNode();
			Node nodeReply = null;
			short nodeType = 0;
			boolean success = false;

			do
			{
				nodeType = nextNode.getNodeType();

				if (nodeType == Node.ELEMENT_NODE && nextNode.getNodeName().equals("ns1:MESSAGE_REQUEST"))
				{
					try
					{
						Document document = nextNode.getOwnerDocument();

						Node nodeProcessReturn = document.createElementNS(nextNode.getNamespaceURI(), "ns1:MESSAGE_RESPONSE");

						// Recorremos el documento Soap hasta llegar al nodo ns1:MESSAGE_REQUEST
						Node a = document.getFirstChild();
						Node soapBody = a.getFirstChild().getNextSibling().getNextSibling().getNextSibling();
						Node c = soapBody.getFirstChild();
						// Nodo Base:Process
						Node baseProcess = c.getNextSibling();
						
						if (baseProcess == null)
						{
							baseProcess = c;
						}

						// Recogemos Nodo operacion: cálculo o pago
						Node operacion;

						if (baseProcess.getFirstChild().getNodeType() == Node.ELEMENT_NODE)
						{
							operacion = baseProcess.getFirstChild();
						}
						else
						{
							operacion = baseProcess.getFirstChild().getNextSibling();
						}
						
						Node nodoRequest = operacion.getFirstChild().getNextSibling();
						Node nodoCabecera = nodoRequest.getFirstChild().getNextSibling();

						nodeProcessReturn.appendChild(operacion);

						// Quitamos del mensaje Soap el nodo base:process y añadimos el base:processReturn
						soapBody.removeChild(baseProcess);
						soapBody.appendChild(nodeProcessReturn);

						// Quitamos nodo Request
						//removeNodeRequest(operacion);
						if (nodoRequest != null)
						{
							operacion.removeChild(nodoRequest);
						}

						// Añadimos el nodo Reply
						nodeReply = getNodeReply(ex, operacion.getNamespaceURI(), nodoCabecera);
						Node nodeReply2 = operacion.getOwnerDocument().importNode(nodeReply, true);
						operacion.appendChild(nodeReply2);
						success = true;
					}
					catch (Exception e)
					{
						nodeReply = getResponse(ex, nextNode);
						success = true;
					}

				}
				else if (nodeType == Node.ELEMENT_NODE && !nextNode.getNodeName().equals("ns1:MESSAGE_REQUEST")
						&& !nextNode.getNodeName().equals("base:MESSAGE_RESPONSE"))
				{
					nodeReply = getResponse(ex, nextNode);
					success = true;
				}
				else if (nodeType == Node.ELEMENT_NODE)
				{
					nextNode = nextNode.getFirstChild();
				}
				else
				{
					// Saltar hijos tipo texto.
					nextNode = nextNode.getNextSibling();
				}
			}
			while (!success && nextNode != null);

			messageContext.setResponse(request);
		}

		return true;
	}
	
	private Node getResponse(Exception ex, Node nextNode)
	{
		Node nodeReply = getNodeReply(ex, nextNode.getNamespaceURI(), null);
		Node nodeReply2 = nextNode.getOwnerDocument().importNode(nodeReply, true);
		removeNodeRequest(nextNode);
		nextNode.appendChild(nodeReply2);
		
		return nodeReply;
	}

	private void removeNodeRequest(Node nodeParent)
	{
		short nodeType = 0;
		Node nodeTemp = nodeParent;
		boolean success = false;

		do
		{
			nodeType = nodeTemp.getNodeType();

			if (nodeType == Node.ELEMENT_NODE && nodeTemp.getNodeName().equals("REQUEST"))
			{
				nodeParent.removeChild(nodeTemp);
				success = true;
			}
			else if (nodeType == Node.ELEMENT_NODE)
			{
				nodeTemp = nodeTemp.getFirstChild();
			}
			else
			{ // Saltar hijos tipo texto.
				nodeTemp = nodeTemp.getNextSibling();
			}
		}
		while (!success && nodeTemp != null);
	}

	private Node getNodeReply(Exception ex, String nameSpace, Node cabecera)
	{
		// Calculamos el ErrorCode y el Mensaje
		String errorCode = "0000";
		String message = "";

		if (ex instanceof PlusvaliaException)
		{
			PlusvaliaException exception = (PlusvaliaException) ex;
			errorCode = exception.getErrorCode();
		}
		else if (ex instanceof JAXBException || ex instanceof UnmarshallingFailureException
				|| ex instanceof MarshallingFailureException)
		{
			errorCode = ErrorCode.COMUNICATION_ERROR;
		}
		else
		{
			errorCode = ErrorCode.UNKNOWN;
		}
		
		message = getMessageSource(errorCode);

		// Generamos los nodos del Reply
		Element nodoReply = null;

		try
		{
			DocumentBuilder documentBuilder;
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = documentBuilder.newDocument();

			nodoReply = doc.createElementNS(nameSpace, "ns1:REPLY");
			Element nodoResultado = doc.createElementNS(nameSpace, "ns1:RESULTADO");
			Element nodoCabecera = doc.createElementNS(nameSpace, "ns1:CABECERA");
			Element nodoRetorno = doc.createElementNS(nameSpace, "ns1:RETORNO");
			Element nodoError = doc.createElementNS(nameSpace, "ns1:ERROR");
			Element nodoCodigo = doc.createElementNS(nameSpace, "ns1:CODIGO");
			Element nodoDescripcion = doc.createElementNS(nameSpace, "ns1:DESCRIPCION");

			nodoDescripcion.appendChild(doc.createTextNode(message));
			nodoCodigo.appendChild(doc.createTextNode(errorCode));
			nodoRetorno.appendChild(doc.createTextNode("true"));

			nodoError.appendChild(nodoCodigo);
			nodoError.appendChild(nodoDescripcion);

			nodoResultado.appendChild(nodoRetorno);
			nodoResultado.appendChild(nodoError);
			
			nodoCabecera = copyNodeAndChilds(doc, nodoCabecera, cabecera);

			nodoReply.appendChild(nodoCabecera);
			nodoReply.appendChild(nodoResultado);

		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (FactoryConfigurationError e)
		{
			e.printStackTrace();
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug(errorCode + " " + message);
		}

		return nodoReply;
	}
	
	private Element copyNodeAndChilds(Document doc, Element parentNode, Node originalNode)
	{
		if (originalNode != null && parentNode != null)
		{
			NodeList nodos = originalNode.getChildNodes();
			
			for (int i = 0; i < nodos.getLength(); i++)
			{
				Node nodo = nodos.item(i);
				
				if (nodo.getNodeType() == Node.ELEMENT_NODE)
				{
					Element newNode = doc.createElementNS(nodo.getNamespaceURI(), nodo.getNodeName());
					
					Node value = nodo.getFirstChild();
					
					newNode.appendChild(doc.createTextNode(value.getNodeValue()));
					
					parentNode.appendChild(newNode);
				}
			}
		}
		
		return parentNode;
	}

	public void setMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	public String getMessageSource(String code)
	{
		return messageSource.getMessage(code, null, null);
	}
}
