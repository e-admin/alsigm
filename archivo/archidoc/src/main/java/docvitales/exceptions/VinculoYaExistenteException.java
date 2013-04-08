package docvitales.exceptions;

import common.exceptions.UncheckedArchivoException;

/**
 * Excepción lanzada cuando se intenta crear un vínculo ya existente entre un
 * documento vital y un expediente.
 */
public class VinculoYaExistenteException extends UncheckedArchivoException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public VinculoYaExistenteException() {
		super("Ya existe un vínculo entre el documento vital y el expediente");
	}
}
