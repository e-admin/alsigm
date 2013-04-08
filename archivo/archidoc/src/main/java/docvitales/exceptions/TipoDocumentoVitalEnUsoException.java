package docvitales.exceptions;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción lanzada cuando se intenta eliminar un tipo de documento vital en
 * uso.
 */
public class TipoDocumentoVitalEnUsoException extends CheckedArchivoException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public TipoDocumentoVitalEnUsoException() {
		super();
	}
}
