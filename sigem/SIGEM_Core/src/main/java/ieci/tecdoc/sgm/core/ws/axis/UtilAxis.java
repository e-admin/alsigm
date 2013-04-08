package ieci.tecdoc.sgm.core.ws.axis;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;

public class UtilAxis {

    /**
     * obtiene el nombre de la implementacion de la cabecera de un contexto de  mensaje
     * llamado por getBean
     * @param msgContext contexto de mensaje del que se obtendra el nombre de la implementacion
     */
    final public static String getImplementacion(MessageContext msgContext) throws SOAPException {
    	Message msg = msgContext.getRequestMessage();
    	SOAPHeader header=msg.getSOAPHeader();
	
    	for(java.util.Iterator it=header.examineAllHeaderElements();it.hasNext();) {
    		SOAPHeaderElement elem=(SOAPHeaderElement)it.next();
    		String s=elem.getTagName();
    		/*
	    	System.out.println("tagName: "+elem.getTagName());
	    	System.out.println("value: "+elem.getValue());
    		 */
    		if(s.endsWith(Constants.IMPLEMENTATION_HEADER)) {
    			return elem.getValue();
    		}
    	}
    	return null;
    }

}
