package deposito.exceptions;

import deposito.model.DepositoException;

/**
 * Excepción lanzada cuando se intenta renumerar un hueco con una numeración que
 * no es numérica.
 */
public class NoPermitidaRenumeracionHuecoAlfanumericaException extends
		DepositoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public NoPermitidaRenumeracionHuecoAlfanumericaException() {
		super(DepositoException.NO_SE_PUEDE_RENUMERAR_HUECOS_ALFANUMERICOS);
	}
}
