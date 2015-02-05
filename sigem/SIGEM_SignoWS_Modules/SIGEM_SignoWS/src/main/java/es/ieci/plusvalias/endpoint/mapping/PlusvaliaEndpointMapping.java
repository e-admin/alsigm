package es.ieci.plusvalias.endpoint.mapping;

import javax.xml.transform.dom.DOMSource;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.mapping.AbstractMapBasedEndpointMapping;
import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.Node;

import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;

/**
 * @author angel_castro@ieci.es - 28/07/2010
 */
public class PlusvaliaEndpointMapping extends AbstractMapBasedEndpointMapping {

	protected String getLookupKeyForMessage(MessageContext messageContext)throws Exception {
		//Resultado Cálculo: http://ancert.notariado.org/XML/Plusvalias/Liquidacion/CALCULO_LIQUIDACION
		//Resultado Pago: http://ancert.notariado.org/XML/Plusvalias/Liquidacion/PAGO_LIQUIDACION

		String key = null;
		
		if (messageContext.getRequest() instanceof SoapMessage) {
			SoapMessage request = (SoapMessage) messageContext.getRequest();
		    DOMSource domSource = (DOMSource) request.getPayloadSource();
		    
		    Node nextNode = domSource.getNode();
		    short nodeType = 0;
			boolean success = false;
			do {
				nodeType = nextNode.getNodeType();
				//if (nodeType == Node.ELEMENT_NODE && (nextNode.getNodeName().indexOf("MESSAGE_REQUEST")!=-1)) {
				if (nodeType == Node.ELEMENT_NODE && !nextNode.getNodeName().equals("ns1:MESSAGE_REQUEST")) {
					String nameespaceUri = nextNode.getNamespaceURI();
					String operation = nextNode.getNodeName();
					key = nameespaceUri + "/" + operation;    	
					success = true;
				}else if (nodeType == Node.ELEMENT_NODE ){
					nextNode = nextNode.getFirstChild();	
				}else{ // Saltar hijos tipo texto.
					nextNode = nextNode.getNextSibling();
				}
			} while (!success && nextNode != null);
			if(!success){
				throw new PlusvaliaException(ErrorCode.COMUNICATION_ERROR);
			}
		}
		return key;
	}

	protected boolean validateLookupKey(String key) {
		return true;
	}
}
