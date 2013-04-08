package valoracion.exceptions;

import common.exceptions.ActionNotAllowedException;

/**
 * Exception lanzada si se produce un intento de realización de una operación no
 * permitida en el módulo de eliminacion de series por parte de un usuario de la
 * aplicación.
 */
public class EliminacionActionNotAllowedException extends
		ActionNotAllowedException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public EliminacionActionNotAllowedException(int codError) {
		super(codError);
	}
}
