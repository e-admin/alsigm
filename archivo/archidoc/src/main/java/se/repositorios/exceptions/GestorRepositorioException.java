package se.repositorios.exceptions;

import common.exceptions.SistemaExternoException;

/**
 * Excepción lanzada cuando ocurre algún error en el conector con el Sistema
 * Gestor de Repositorio.
 */
public class GestorRepositorioException extends SistemaExternoException {

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
	public GestorRepositorioException(Throwable e) {
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
	public GestorRepositorioException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public GestorRepositorioException(String cause) {
		super(cause);
	}
}
