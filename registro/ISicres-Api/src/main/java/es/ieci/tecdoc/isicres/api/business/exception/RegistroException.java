/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.exception;

/**
 *
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase que identifica los errores que se producen en las operaciones
 *          tipicas del registro
 *
 */
public class RegistroException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public RegistroException() {
		super();
	}

	/**
	 * @param message
	 */
	public RegistroException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RegistroException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RegistroException(String message, Throwable cause) {
		super(message, cause);
	}

}
