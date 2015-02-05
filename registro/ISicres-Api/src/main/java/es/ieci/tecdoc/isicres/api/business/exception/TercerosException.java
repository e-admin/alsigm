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
 *          con terceros
 *
 */
public class TercerosException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public TercerosException() {
		super();
	}

	/**
	 * @param message
	 */
	public TercerosException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TercerosException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TercerosException(String message, Throwable cause) {
		super(message, cause);
	}

}
