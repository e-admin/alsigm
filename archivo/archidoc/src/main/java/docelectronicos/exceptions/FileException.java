package docelectronicos.exceptions;

import common.exceptions.UncheckedArchivoException;

/**
 * Excepción lanzada cuando ocurre algún error con los ficheros.
 */
public class FileException extends UncheckedArchivoException {
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
	public FileException(Throwable e) {
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
	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public FileException(String cause) {
		super(cause);
	}
}
