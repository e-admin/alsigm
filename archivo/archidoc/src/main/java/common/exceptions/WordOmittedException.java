package common.exceptions;

/**
 * Excepción de palabra omitida.
 */
public class WordOmittedException extends DBException {

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
	public WordOmittedException(Throwable e) {
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
	public WordOmittedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public WordOmittedException(String cause) {
		super(cause);
	}
}
