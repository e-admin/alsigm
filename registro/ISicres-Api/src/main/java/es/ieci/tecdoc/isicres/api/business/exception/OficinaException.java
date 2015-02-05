/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.exception;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase que identifica los errores que se producen en las operaciones
 *          tipicas de Oficina
 *
 */
public class OficinaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public OficinaException() {
		super();
	}

	/**
	 * @param message
	 */
	public OficinaException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public OficinaException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OficinaException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
