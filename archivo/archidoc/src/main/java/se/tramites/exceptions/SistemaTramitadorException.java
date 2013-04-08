package se.tramites.exceptions;

import common.exceptions.SistemaExternoException;

/**
 * Excepción lanzada cuando ocurre algún error en el conector con el Sistema
 * Tramitador.
 */
public class SistemaTramitadorException extends SistemaExternoException {
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
	public SistemaTramitadorException(Throwable e) {
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
	public SistemaTramitadorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public SistemaTramitadorException(String cause) {
		super(cause);
	}
}
