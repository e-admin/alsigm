package se.procedimientos.exceptions;

import common.exceptions.SistemaExternoException;

/**
 * Excepción lanzada cuando ocurre algún error en el conector con el Sistema
 * Gestor de Catálogo.
 */
public class GestorCatalogoException extends SistemaExternoException {

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
	public GestorCatalogoException(Throwable e) {
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
	public GestorCatalogoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public GestorCatalogoException(String cause) {
		super(cause);
	}
}
