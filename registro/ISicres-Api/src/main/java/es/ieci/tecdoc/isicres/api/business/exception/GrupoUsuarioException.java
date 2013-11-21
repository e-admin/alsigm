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
public class GrupoUsuarioException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public GrupoUsuarioException() {
		super();
	}

	/**
	 * @param message
	 */
	public GrupoUsuarioException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public GrupoUsuarioException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GrupoUsuarioException(String message, Throwable cause) {
		super(message, cause);
	}

}
