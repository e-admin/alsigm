package common.exceptions;

/**
 * Excepción de sintaxis incorrecta
 */
public class SintaxErrorException extends DBException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param e
	 *            Excepción capturada.
	 */
	public SintaxErrorException(Throwable e) {
		super(e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            Causa de la excepción.
	 * @param cause
	 *            Causa de la excepción.
	 */
	public SintaxErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public SintaxErrorException(String cause) {
		super(cause);
	}
}
