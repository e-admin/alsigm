package common.exceptions;

/**
 * Padre de las excepciones de archivo no chequeadas.
 */
public class UncheckedArchivoException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public UncheckedArchivoException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param e
	 *            Excepción capturada.
	 */
	public UncheckedArchivoException(Throwable e) {
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
	public UncheckedArchivoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public UncheckedArchivoException(String cause) {
		super(cause);
	}
}
