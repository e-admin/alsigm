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
public class UsuarioException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public UsuarioException() {
		super();
	}

	/**
	 * @param message
	 */
	public UsuarioException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UsuarioException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UsuarioException(String message, Throwable cause) {
		super(message, cause);
	}

}
