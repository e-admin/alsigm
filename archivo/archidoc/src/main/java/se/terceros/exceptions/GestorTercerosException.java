package se.terceros.exceptions;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción lanzada cuando ocurre algún error en el conector con el Gestor de
 * Terceros.
 */
public class GestorTercerosException extends CheckedArchivoException {

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
	public GestorTercerosException(Throwable e) {
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
	public GestorTercerosException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public GestorTercerosException(String cause) {
		super(cause);
	}
}
