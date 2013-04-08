package se;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción lanzada cuando la funcionalidad que se va a ejecutar no es
 * aplicable.
 */
public class NotAvailableException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public NotAvailableException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public NotAvailableException(String cause) {
		super(cause);
	}
}
