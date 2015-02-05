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
 *          tipicas de Libro
 *
 */
public class LibroException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public LibroException() {
		super();
	}

	/**
	 * @param message
	 */
	public LibroException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LibroException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LibroException(String message, Throwable cause) {
		super(message, cause);
	}

}
