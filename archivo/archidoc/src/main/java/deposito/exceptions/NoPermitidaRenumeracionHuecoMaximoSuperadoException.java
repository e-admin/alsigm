package deposito.exceptions;

import deposito.model.DepositoException;

/**
 * Excepción lanzada cuando se intenta renumerar huecos con estado ocupado.
 */
public class NoPermitidaRenumeracionHuecoMaximoSuperadoException extends
		DepositoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public NoPermitidaRenumeracionHuecoMaximoSuperadoException() {
		super(
				DepositoException.NO_SE_PUEDE_RENUMERAR_MAXIMO_NUMERACION_EXCEDIDO);
	}
}
