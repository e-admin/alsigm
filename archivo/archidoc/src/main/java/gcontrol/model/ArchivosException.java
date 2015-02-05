package gcontrol.model;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

public class ArchivosException extends ActionNotAllowedException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO = 1;
	public static final int NO_SE_PUEDE_ELIMINAR_EL_NIVEL_ARCHIVO = 2;
	public static final int NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_EN_FONDOS = 3;
	public static final int NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_EN_UBICACION = 4;
	public static final int NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_EN_EDIFICIOS_SC = 5;
	public static final int NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_CONSULTAS = 6;

	ArchivosException(int reason) {
		super(null, reason, ArchivoModules.ARCHIVOS_MODULE);
	}
}
