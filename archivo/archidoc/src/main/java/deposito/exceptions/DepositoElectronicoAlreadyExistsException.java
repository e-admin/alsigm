package deposito.exceptions;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción lanzada cuando se intenta crear un depósito electrónico con un
 * identificador externo que ya existe.
 */
public class DepositoElectronicoAlreadyExistsException extends
		CheckedArchivoException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DepositoElectronicoAlreadyExistsException() {
		super();
	}
}
