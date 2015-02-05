package common.exceptions;

import common.definitions.ArchivoModules;

/**
 * Excepcion de organizaciones
 * 
 */
public class OrganizacionException extends ActionNotAllowedException {

	private static final long serialVersionUID = 2525232182957647375L;

	public static final int ERROR_AL_MOSTRAR_ELEMENTO_ORGANIZACION = 15001;

	public static final int ERROR_ELEMENTO_NO_ENCONTRADO = 15002;

	public OrganizacionException(int reason) {
		super(null, reason, ArchivoModules.ORGANIZACION_MODULE);
	}

}