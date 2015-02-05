package salas.exceptions;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class SalasConsultaException extends ActionNotAllowedException {

	private static final long serialVersionUID = 1L;

	public static final int NO_ELIMINAR_USUARIO_CON_CONSULTAS = 1;
	public static final int NO_ELIMINAR_USUARIO_EN_SALA = 2;
	public static final int NO_MODIFICAR_USUARIO_EN_SALA = 3;

	public static final int NOMBRE_EDIFICIO_DUPLICADO = 4;
	public static final int EDIFICIO_CON_SALAS = 5;
	public static final int EDIFICIO_CON_MESAS_OCUPADAS = 6;
	public static final int NOMBRE_SALA_DUPLICADO = 7;
	public static final int NO_ELIMINAR_SALA_CON_MESAS = 8;
	public static final int REGISTRO_MESA_OCUPADA = 9;
	public static final int USUARIO_CONSULTA_SALAS_USUARIO_APLICACION_YA_CREADO = 10;
	public static final int ARCHIVO_USUARIO_CONSULTA_SALA_CON_MESA_OCUPADA = 11;

	public static final int ERROR_AL_MOSTRAR_ELEMENTO = 12;

	public static final int ERROR_ELEMENTO_NO_ENCONTRADO = 13;

	/**
	 * @param codError
	 */
	public SalasConsultaException(int codError) {
		super(null, codError, ArchivoModules.SALAS_MODULE);
	}

}
