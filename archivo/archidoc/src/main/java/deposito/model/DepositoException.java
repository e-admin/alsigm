package deposito.model;

import auditoria.ArchivoErrorCodes;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

/**
 * Excepcion de deposito
 * 
 */
public class DepositoException extends ActionNotAllowedException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static int ELEMENT_NOT_EMPTY = ArchivoErrorCodes.ERROR_ELEMENT_NOT_EMPTY;

	public static int NOT_LAST_CHILD = ArchivoErrorCodes.ERROR_NOT_LAST_CHILD;

	public static int LONGITUD_MENOR_Q_LONGITUD_FORMATO = ArchivoErrorCodes.ERROR_LONGITUD_MENOR_Q_LONGITUD_FORMATO;

	public static int CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS = ArchivoErrorCodes.ERROR_CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS;

	public static int ELEMENT_DELETED = ArchivoErrorCodes.ERROR_ELEMENT_DELETED;

	public static int EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO = ArchivoErrorCodes.EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO;

	public static int NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION = ArchivoErrorCodes.NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION;

	public static int NO_SE_PUEDE_ELIMINAR_EL_FORMATO = ArchivoErrorCodes.NO_SE_PUEDE_ELIMINAR_EL_FORMATO;

	public static int NO_SE_PUEDE_CREAR_IDENTIFICADOR_UNICO_HUECO = ArchivoErrorCodes.NO_SE_PUEDE_CREAR_IDENTIFICADOR_UNICO_HUECO;

	public static int NO_SE_PUEDE_REUBICAR_EN_UI_NO_MULTIDOC = ArchivoErrorCodes.NO_SE_PUEDE_REUBICAR_EN_UI_NO_MULTIDOC;

	public static int NUMERACION_HUECO_EN_USO = ArchivoErrorCodes.NUMERACION_HUECO_EN_USO;

	public static int NO_SE_PUEDE_RENUMERAR_HUECOS_ALFANUMERICOS = ArchivoErrorCodes.NO_SE_PUEDE_RENUMERAR_HUECOS_ALFANUMERICOS;

	public static int NO_SE_PUEDE_RENUMERAR_A_HUECOS_OCUPADOS = ArchivoErrorCodes.NO_SE_PUEDE_RENUMERAR_A_HUECOS_OCUPADOS;
	public static int NO_SE_PUEDE_RENUMERAR_MAXIMO_NUMERACION_EXCEDIDO = ArchivoErrorCodes.NO_SE_PUEDE_RENUMERAR_MAXIMO_NUMERACION_EXCEDIDA;

	public static int NO_SE_PUEDE_RENUMERAR_A_HUECOS_CREADOS = ArchivoErrorCodes.NO_SE_PUEDE_RENUMERAR_A_HUECOS_CREADOS;

	public static int NO_SE_PUEDE_UBICAR_A_HUECOS_NO_LIBRES = ArchivoErrorCodes.NO_SE_PUEDE_UBICAR_A_HUECOS_NO_LIBRES;

	public static int NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES = ArchivoErrorCodes.NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES;

	public static int NO_SE_PUEDE_REUBICAR_A_HUECOS_NO_LIBRES = ArchivoErrorCodes.NO_SE_PUEDE_REUBICAR_A_HUECOS_NO_LIBRES;

	public static int NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES_SIGNATURA_ASOCIADA_A_HUECO = ArchivoErrorCodes.NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES_SIGNATURA_ASOCIADA_A_HUECO;

	public static int COMPACTAR_HUECO_OCUPADO_PROCESO_COMPACTACION = ArchivoErrorCodes.COMPACTAR_HUECO_OCUPADO_PROCESO_COMPACTACION;

	public static int NO_SE_PUEDE_CAMBIAR_ESTADO_HUECO_MODIFICACION = ArchivoErrorCodes.NO_SE_PUEDE_CAMBIAR_ESTADO_HUECO_MODIFICACION;

	public static int SIGNATURA_HUECO_OCUPADA = ArchivoErrorCodes.SIGNATURA_HUECO_OCUPADA;

	public DepositoException(int reason) {
		super(null, reason, ArchivoModules.DEPOSITOS_MODULE);
	}

	/**
	 * Genera la unica excepcion publica de deposito. Se debe emplear para
	 * indicar que un elemento que el usuario esta viendo actualemente no existe
	 * 
	 */
	public DepositoException() {
		super(null, ELEMENT_DELETED, ArchivoModules.DEPOSITOS_MODULE);
	}

}
