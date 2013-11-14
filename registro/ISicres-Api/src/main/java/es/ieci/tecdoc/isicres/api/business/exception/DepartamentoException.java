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
 *          Clase que identifica los errores tipicos en las operacion de login y
 *          permisos
 */
public class DepartamentoException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public DepartamentoException() {
		super();
	}

	/**
	 * @param message
	 */
	public DepartamentoException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DepartamentoException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DepartamentoException(String message, Throwable cause) {
		super(message, cause);
	}

}
