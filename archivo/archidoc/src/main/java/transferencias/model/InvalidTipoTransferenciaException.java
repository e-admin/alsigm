package transferencias.model;

import common.exceptions.CheckedArchivoException;

/**
 * Lanzada cuando se intenta determinar el tipo de operacion de una
 * transferencia a partir de un tipo de transferencia y de un tipo de prevision.
 * Las posiblilidades válidas son:
 * <ul>
 * <li>Tipo de transferencia: TRANSFERENCIA ORDINARIA y Tipo previsión:
 * PREVISIÓN DETALLADA</li>
 * <li>Tipo de transferencia: TRANSFERENCIA EXTRAORDINARIA NO SIGNATURADA y Tipo
 * previsión: PREVISIÓN DETALLADA</li>
 * <li>Tipo de transferencia: TRANSFERENCIA EXTRAORDINARIA NO SIGNATURADA y Tipo
 * previsión: PREVISIÓN NO DETALLADA</li>
 * <li>Tipo de transferencia: TRANSFERENCIA EXTRAORDINARIA SIGNATURADA y Tipo
 * previsión: PREVISIÓN NO DETALLADA</li>
 * </ul>
 */
public class InvalidTipoTransferenciaException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTipoTransferenciaException(String cause) {
		super(cause);
	}
}