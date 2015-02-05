package se.instituciones.exceptions;

import common.exceptions.SistemaExternoException;

/**
 * Excepción lanzada cuando ocurre algún error en el conector con el Sistema
 * Gestor de Organización.
 */
public class GestorOrganismosException extends SistemaExternoException {

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
	public GestorOrganismosException(Throwable e) {
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
	public GestorOrganismosException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public GestorOrganismosException(String cause) {
		super(cause);
	}
}
