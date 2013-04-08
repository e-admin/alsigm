package deposito.exceptions;

import deposito.model.DepositoException;

/**
 * Excepción lanzada cuando se intenta reservar en un hueco que ya está
 * utilizado y hay signatura asociada a hueco.
 */
public class DepositoHuecoReservarNoLibreSignaturaAsociadaHuecoException extends
		DepositoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DepositoHuecoReservarNoLibreSignaturaAsociadaHuecoException() {
		super(
				DepositoException.NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES_SIGNATURA_ASOCIADA_A_HUECO);
	}
}
