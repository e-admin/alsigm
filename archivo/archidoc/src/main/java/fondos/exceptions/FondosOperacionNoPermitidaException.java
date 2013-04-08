package fondos.exceptions;

import java.util.List;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

/**
 * Motivos por los que es posible que no se permite llevar a cabo alguna de las
 * operaciones disponibles en el módulo de gestión de fondos documentales
 */
public class FondosOperacionNoPermitidaException extends
		ActionNotAllowedException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	List idsProblematicos = null;

	protected static int i = 0;
	public static final int XCODIGO_ENTIDAD_YA_EXISTE = 1;
	public static final int XENTIDAD_PRODUCTORA_ASIGNADA = 2;
	public static final int XCLASIFICADOR_NO_PUEDE_TENER_FONDOS = 3;
	public static final int XFONDO_NO_BORRABLE_TIENE_SERIES_ASOCIADAS = 4;
	public static final int XFONDO_NO_BORRABLE_TIENE_CLASIFICADORES_SERIE_VIGENTES = 5;
	public static final int XFONDO_NO_BORRABLE_ENT_TIENE_ORGANOS_ASOCIADOS = 6;
	public static final int XCODIGO_YA_EXISTE = 7;
	public static final int XNOMBRE_YA_EXISTE = 8;
	public static final int XELEMENTO_NO_VACIO = 9;
	public static final int XCL_SERIE_NO_BORRABLE_TIENE_HIJOS_VIGENTES = 10;
	public static final int XCL_SERIE_NO_BORRABLE_TIENE_SERIES = 11;
	public static final int XNO_SE_PUEDE_CAMBIAR_ESTADO_X_TIENE_HIJOS = 12;
	public static final int XNO_SE_PUEDE_CAMBIAR_ESTADO_X_TIENE_SERIES = 13;
	public static final int XCODIGO_SERIE_YA_EXISTE = 14;
	public static final int XNO_SE_PUEDE_MOVER_HAY_HERMANOS_CON_MISMO_CODIGO = 15;
	public static final int XFONDO_NO_MOVIBLE_XTENER_UDOCS = 16;
	public static final int XNO_TIENE_PERMISOS_SOBRE_LA_SERIE = 17;
	public static final int XLA_SERIE_SE_ENCUENTRA_EN_UN_ESTADO_INCORRECTO_PARA_REALIZAR_ESTA_OPERACION = 18;
	public static final int X_EL_USUARIO_SELECCIONADO_NO_ES_GESTOR_DE_SERIE = 19;
	public static final int X_PRODUCTOR_DEL_PROCEDIMIENTO_NO_SE_ENCUENTRA_EN_EL_SIST_ORG = 20;
	public static final int XNO_SE_PUEDE_CAMBIAR_ESTADO_X_PADRE_TIENE_ESTADO_NO_VIGENTE = 21;

	public static final int XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_TITULO = 22;
	public static final int XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_FECHA_EXTREMA_INICIAL = 24;
	public static final int XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_DEFINICION = 25;
	public static final int XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_TRAMITES = 26;
	public static final int XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_NORMATIVA = 27;
	public static final int XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_NO_ESTA_IDENTIFICADA = 28;
	public static final int XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_NO_TIENE_PRODUCTORES_ASOCIADOS = 29;

	public static final int XEL_PROCEDIMIENTO_NO_TIENE_PRODUCTORES_ASOCIADOS = 30;

	public static final int XTIPO_CLASIFICADOR_NO_PUEDE_SER_CAMBIADO = 31;
	public static final int ESTADO_SERIE_NO_ES_NO_VIGENTE = 32;
	public static final int XSERIE_TIENE_VALORACION_DICTAMINADA = 33;
	public static final int NO_SE_PUEDE_CAMBIAR_ENTIDAD_PRODUCTORA_FONDO_CON_SERIES = 34;
	public static final int NO_SE_PUEDE_CAMBIAR_CODIGO_FONDO_NO_VACIO = 35;
	public static final int ELEMENTO_TIENE_DOCUMENTOS_ASOCIADOS = 36;
	public static final int CAMPOS_OBLIGATORIOS_DESCRIPCION_SIN_VALOR = 37;
	public static final int XNO_SE_PUEDE_MOVER_TIENE_SERIES = 38;
	public static final int XNO_SE_PUEDE_MOVER_NUEVO_PADRE_NO_VIGENTE = 39;
	public static final int XCL_FONDO_NO_BORRABLE_TIENE_HIJOS = 40;
	public static final int XDIVISION_FS_ESTADO_INCORRECTO_BORRADO = 41;
	public static final int XNO_SE_PUEDE_MOVER_PROVOCA_FONDOS_DISTINTOS_EN_MISMA_CAJA = 42;

	public static final int ERROR_SIN_PERMISOS_GESTION_UDOCS_CONSERVADAS = 43;

	public static final int XNO_SE_PUEDE_MOVER_UDOCS_EN_ELIMINACION_NO_FINALIZADA = 44;

	public FondosOperacionNoPermitidaException(int motivo) {
		super("", motivo, ArchivoModules.FONDOS_MODULE);
	}

	public FondosOperacionNoPermitidaException(int motivo, List idsProblematicos) {
		super("", motivo, ArchivoModules.FONDOS_MODULE);
		this.idsProblematicos = idsProblematicos;
	}

	public List getIdsProblematicos() {
		return idsProblematicos;
	}
}
