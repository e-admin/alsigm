package es.ieci.tecdoc.isicres.document.connector.exception;
/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

/**
 * RunTimeException para excepciones ocurridas en el connector, las distintas implementaciones podrán heredar de ella y asi tener sus propias excepciones runTime  
 */
public class IsicresDocumentConnectorException extends RuntimeException {

	public IsicresDocumentConnectorException() {
		super();
	
	}

	public IsicresDocumentConnectorException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public IsicresDocumentConnectorException(String message) {
		super(message);
	
	}

	public IsicresDocumentConnectorException(Throwable cause) {
		super(cause);
	
	}

}
