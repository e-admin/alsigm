package common.exceptions;

/**
 * Excepción generada cuando ocurre algún error en la encriptación o
 * desencriptación.
 */
public class CryptoException extends UncheckedArchivoException {
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
	public CryptoException(Throwable e) {
		super(e.getMessage());
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            Causa de la excepción.
	 * @param cause
	 *            Causa de la excepción.
	 */
	public CryptoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public CryptoException(String cause) {
		super(cause);
	}
}
