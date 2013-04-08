package es.ieci.tecdoc.isicres.compulsa.connector.exception;

/**
 * @author Iecisa
 * @version $Revision$
 *
 *          RunTimeException para excepciones ocurridas en el connector, las
 *          distintas implementaciones podrán heredar de ella y asi tener sus
 *          propias excepciones runTime
 */

public class ISicresCompulsaConnectorException extends RuntimeException {

	public ISicresCompulsaConnectorException() {
		super();
	}

	public ISicresCompulsaConnectorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ISicresCompulsaConnectorException(String message) {
		super(message);
	}

	public ISicresCompulsaConnectorException(Throwable cause) {
		super(cause);
	}

}
