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
 *          Clase que identifica los errores que se producen en la distribucion
 *
 */
public class DistribucionException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public DistribucionException() {
		super();
	}

	/**
	 * @param message
	 */
	public DistribucionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DistribucionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DistribucionException(String message, Throwable cause) {
		super(message, cause);
	}

}
