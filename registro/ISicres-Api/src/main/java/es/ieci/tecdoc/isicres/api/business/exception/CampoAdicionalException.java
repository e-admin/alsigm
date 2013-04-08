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
 *          Excepcion para tratar los errores en los campos adicionales de un
 *          registro.
 *
 */
public class CampoAdicionalException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public CampoAdicionalException() {
		super();
	}

	/**
	 * @param message
	 */
	public CampoAdicionalException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CampoAdicionalException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CampoAdicionalException(String message, Throwable cause) {
		super(message, cause);
	}

}
