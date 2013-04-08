package deposito.exceptions;

import deposito.model.DepositoException;

/**
 * Excepción lanzada cuando se intenta modificar el estado de un hueco y el
 * estado original no coincide con el esperado
 */
public class DepositoEstadoHuecoCambiadoException extends DepositoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DepositoEstadoHuecoCambiadoException() {
		super(DepositoException.NO_SE_PUEDE_CAMBIAR_ESTADO_HUECO_MODIFICACION);
	}
}
