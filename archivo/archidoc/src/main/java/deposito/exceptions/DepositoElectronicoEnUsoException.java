package deposito.exceptions;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción lanzada cuando se intenta eliminar un depósito electrónico en uso.
 */
public class DepositoElectronicoEnUsoException extends CheckedArchivoException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DepositoElectronicoEnUsoException() {
		super();
	}
}
