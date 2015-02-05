package deposito.exceptions;

import deposito.model.DepositoException;

/**
 * Excepción lanzada cuando se intenta ubicar en un hueco que ya está utilizado.
 */
public class DepositoHuecoReservarNoLibreException extends DepositoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DepositoHuecoReservarNoLibreException() {
		super(DepositoException.NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES);
	}
}
