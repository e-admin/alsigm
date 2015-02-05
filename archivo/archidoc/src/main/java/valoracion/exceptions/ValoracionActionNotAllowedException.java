package valoracion.exceptions;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

/**
 * Exception lanzada si se produce un intento de realización de una operación no
 * permitida en el módulo de valoracion por parte de un usuario de la
 * aplicación.
 */
public class ValoracionActionNotAllowedException extends
		ActionNotAllowedException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ValoracionActionNotAllowedException(int codError) {
		super(codError);
		setModule(ArchivoModules.FONDOS_MODULE);
	}
}
