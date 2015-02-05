package axis.mtom.client.handler;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.handlers.BasicHandler;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Obtiene el fichero MTOM/XOP referenciado por el parametro "mtomNodePath" del fichero "client-config.wsdd"
 * Expone un Stream para el contenido del fichero a los clientes mediante un ThreadLocal
 */
public class XOPHandler extends BasicHandler {

	private static final Logger logger = Logger.getLogger(XOPHandler.class);
	
	// Parametros del "client-config.wsdd"
	private static final String HANDLER_OPTION_MTOM_NODE_PATH = "mtomNodePath";
	private static final String HANDLER_OPTION_OPERATION_NAME = "operationName";
	private static final String HANDLER_OPTION_SERVICE_NAME = "serviceName";
	
	// Stream para el contenido del fichero 
	private static ThreadLocal documentStream = new ThreadLocal();
    public static InputStream getDocumentStream() {
        return (InputStream)documentStream.get();
    }
    
	/**
	 * Copia la referencia del Stream a contenido de fichero buscando el nodo especificado en el fichero client-config.wsdd.
	 */
    public void invoke(MessageContext msgContext) throws AxisFault {
    	
    	// Solo procesar el servicio y operacion indicados
    	if (isTargetedServiceAndOperation(msgContext)) {
    	
			// Obtener el nodo SOAP del parametro correspondiente en client-config.wsdd
			String mtomNodePath = (String) getOption(HANDLER_OPTION_MTOM_NODE_PATH);
			if (mtomNodePath == null) {
				logger.debug("No se ha especificado el parametro mtomNodePath en el fichero client-config.wsdd");
			} else {
				
				String[] pathToNode = mtomNodePath.split("\\.");
				int nodesLevel = pathToNode.length;
				
				try {
					
					// Obtener SOAP Body
					Message msg = msgContext.getResponseMessage();
					SOAPMessage soapMessage = msgContext.getResponseMessage();
		            SOAPBody soapBody = soapMessage.getSOAPBody();
		            
		        	// Buscar mtomNodePath en SOAP Body
		            Node currentNode = null;
		            int currentNodeLevel = 0;
		            NodeList nodeList = soapBody.getChildNodes();
		            while (currentNodeLevel < nodesLevel && nodeList != null) {
		            	currentNode = null;
		                for (int i = 0; i < nodeList.getLength(); i++) {
		                	if (nodeList.item(i).getLocalName().equals(pathToNode[currentNodeLevel])) {
		                		currentNode = nodeList.item(i);
		                		break;
		                	}
		                }
		                if (currentNode != null) {
		                    nodeList = currentNode.getChildNodes();
		                } else {
		                	nodeList = null;
		                }
		            	currentNodeLevel++;
		            }
		            
		            // mtomNodePath encontrado
		            if (currentNode != null) {
		            
			            // Recuperar la referencia al Attachment Soap del atributo "Include" 
		            	String attachmentRef = null;
		            	if (currentNode.getChildNodes() != null && 
						    currentNode.getChildNodes().getLength() > 0) {
				    		NamedNodeMap nnm = currentNode.getChildNodes().item(0).getAttributes();
				    		for (int j = 0; j < nnm.getLength(); j++) {
				    			attachmentRef = nnm.item(j).getNodeValue();
				    		}
				    		// (!) Eliminar el nodo XOP para prevenir errores de unmarshalling en Axis
				    		currentNode.getParentNode().removeChild(currentNode);
		            	} else {
		            		logger.debug("No se ha encontrado la referencia en el nodo " + mtomNodePath);
		            	}
			    		
						// Recuperar el Attachment SOAP de los attachments usando la referencia
		            	if (attachmentRef != null) {
		            		
				            Iterator attachments = msg.getAttachments();
							while (attachments.hasNext()) {
								AttachmentPart attachmentPart = (AttachmentPart)attachments.next();
								if (attachmentPart.getContentIdRef().equals(attachmentRef)) {
								    documentStream.set(attachmentPart.getDataHandler().getInputStream());
								}
							}
							
				            // Persistir los cambios en SOAP Body
							TransformerFactory tff = TransformerFactory.newInstance();
				            Transformer tf = tff.newTransformer();
				            Source sc = soapMessage.getSOAPPart().getContent();
				            StringWriter modifiedBody = new StringWriter();
				            StreamResult result = new StreamResult(modifiedBody);
				            tf.transform(sc, result);            
				            Message modifiedMsg = new Message(modifiedBody.toString(), false);
				            msgContext.setResponseMessage(modifiedMsg);
				            
		            	} else {
		            		logger.debug("No se ha encontrado la referencia al fichero XOP en el nodo" + mtomNodePath);
		            	}
			            
		            } else {
		            	
		            	logger.debug("Nodo " + mtomNodePath + " no encontrado en el SOAP Body");
		            	
		            }
		            
				} catch (Exception e) {
					throw new AxisFault("Handler exception", e);
				}
			}
        }
		
	}

    /**
     * Comprueba servicio y operacion del fichero "client-config.wsdd"
     * con el servicio y operacion de la respuesta
     */
    private boolean isTargetedServiceAndOperation(MessageContext msgContext) {
    	
    	boolean parametersFound = false;
    	
    	String serviceName = (String) getOption(HANDLER_OPTION_SERVICE_NAME);
        if (serviceName == null) {
        	logger.debug("No se ha especificado la propiedad serviceName en el fichero client-config.wsdd");
        }
        
		Service locator = (Service) msgContext.getProperty(Call.WSDL_SERVICE);
		if (locator != null && locator.getServiceName().toString().equals(serviceName)) {
	    	String operationName = (String) getOption(HANDLER_OPTION_OPERATION_NAME);
	        if (operationName == null) {
	        	logger.debug("No se ha especificado la operacion operationName en el fichero client-config.wsdd");
	        } else if (msgContext.getOperation().getName().equals(operationName)) {
			    parametersFound = true;
	        }
		}
		
		return parametersFound;
    }
    
}
