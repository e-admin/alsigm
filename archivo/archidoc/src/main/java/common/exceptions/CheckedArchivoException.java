package common.exceptions;

/**
 * Excepcion padre de la jerarquia de exception chequeadas.
 */
public class CheckedArchivoException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public CheckedArchivoException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param e
	 *            Excepción capturada.
	 */
	public CheckedArchivoException(Throwable e) {
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
	public CheckedArchivoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public CheckedArchivoException(String cause) {
		super(cause);
	}

}
