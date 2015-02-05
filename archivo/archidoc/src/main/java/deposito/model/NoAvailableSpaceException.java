package deposito.model;

import common.exceptions.CheckedArchivoException;

/**
 * Excepcion que se produce cuando se intenta ocupar en mas espacio del
 * disponible en algún elemento del depósito físico
 */
public class NoAvailableSpaceException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public NoAvailableSpaceException() {
	}
}
