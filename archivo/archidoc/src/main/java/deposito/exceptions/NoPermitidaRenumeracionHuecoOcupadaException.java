package deposito.exceptions;

import deposito.model.DepositoException;

/**
 * Excepción lanzada cuando se intenta renumerar huecos con estado ocupado.
 */
public class NoPermitidaRenumeracionHuecoOcupadaException extends
		DepositoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public NoPermitidaRenumeracionHuecoOcupadaException() {
		super(DepositoException.NO_SE_PUEDE_RENUMERAR_A_HUECOS_OCUPADOS);
	}
}
