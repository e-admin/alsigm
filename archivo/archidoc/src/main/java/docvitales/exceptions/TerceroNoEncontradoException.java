package docvitales.exceptions;

import common.exceptions.UncheckedArchivoException;

/**
 * Excepción lanzada cuando no se ha encontrado un tercero.
 */
public class TerceroNoEncontradoException extends UncheckedArchivoException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public TerceroNoEncontradoException() {
		super("No se ha encontrado el tercero");
	}
}
