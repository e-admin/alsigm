package se.geograficos.exceptions;

import common.exceptions.SistemaExternoException;

/**
 * Excepción lanzada cuando ocurre algún error en el conector con el Gestor de
 * Geográficos.
 */
public class GestorGeograficosException extends SistemaExternoException {

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
	public GestorGeograficosException(Throwable e) {
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
	public GestorGeograficosException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public GestorGeograficosException(String cause) {
		super(cause);
	}
}
