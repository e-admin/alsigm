package deposito.exceptions;

import deposito.model.DepositoException;

/**
 * Excepción lanzada cuando se intenta renumerar huecos cuya numeración ya esta
 * en uso.
 */
public class NoPermitidaRenumeracionHuecoCreadaException extends
		DepositoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public NoPermitidaRenumeracionHuecoCreadaException() {
		super(DepositoException.NO_SE_PUEDE_RENUMERAR_A_HUECOS_CREADOS);
	}
}
