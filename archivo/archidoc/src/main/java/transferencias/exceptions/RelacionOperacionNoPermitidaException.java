package transferencias.exceptions;

import transferencias.vos.RelacionEntregaVO;
import auditoria.ArchivoErrorCodes;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

/**
 * Excepcion que es disparada cuando se intentan llevar a cabo operaciones sobre
 * relaciones de entrega que por algun motivo no estan permitidas
 */
public class RelacionOperacionNoPermitidaException extends
		ActionNotAllowedException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int XNO_ES_POSIBLE_MOVER_PRIMERA_POSICION = 1;
	public static final int XNO_ES_POSIBLE_MOVER_ULTIMA_POSICION = 2;
	public static final int XNO_ES_POSIBLE_MOVER_POSICIONES_NO_CONSECUTIVAS = 3;
	public static final int XNO_ES_POSIBLE_DIVIDIR_LA_RELACION_NO_TIENE_FORMATO_MULTIDOCUMENTO = 4;
	public static final int XSOLO_ES_POSIBLE_ELIMINAR_LA_ULTIMA_PARTE = 5;
	public static final int XSOLO_ES_POSIBLE_DIVIDIR_LA_ULTIMA_UDOC_DE_LA_CAJA = 6;
	public static final int XSOLO_ES_POSIBLE_DIVIDIR_LA_ULTIMA_PARTE_DE_UDOC = 7;
	public static final int XSOLO_ES_POSIBLE_INCORPORAR_EXPEDIENTES_A_UNA_CAJA_CON_LA_PRIMERA_PARTE_O_LA_ULTIMA_DE_UNA_UNIDAD_DOC = 8;
	public static final int XSOLO_ES_POSIBLE_EXTRAER_EXPEDIENTES_COMPLETOS = 9;
	public static final int XSOLO_ES_POSIBLE_ELIMINAR_CAJAS_VACIAS = 10;
	public static final int XLA_SIGNATURA_YA_EXISTE_EN_EL_SISTEMA = 11;
	public static final int XSOLO_ES_POSIBLE_ELIMINAR_UDOCS_CON_ERRORES = 12;
	public static final int XLA_RELACION_SIGNATURADA_PARA_PODER_SER_ENVIADA_HA_DE_TENER_SIGNATURA_EN_TODAS_SUS_CAJAS = 13;
	public static final int XSOLO_PERMITIDO_A_GESTOR = ArchivoErrorCodes.SOLO_PERMITIDO_A_GESTOR;
	public static final int XRELACION_EN_ESTADO_INCORRECTO = ArchivoErrorCodes.RELACION_EN_ESTADO_INCORRECTO;
	public static final int XNO_PERMITIDO_EN_TRANSFERENCIAS_ORDINARIAS = ArchivoErrorCodes.NO_PERMITIDO_EN_TRANSFERENCIAS_ORDINARIAS;
	public static final int XEN_RELACION_ES_CON_ERRORES_SOLO_ES_POSIBLE_ELIMINAR_PARTE_EXPEDIENTE_CON_ERRORES = 14;
	public static final int XEN_ORDINARIA_NO_PUEDE_FINALIZARSE_COTEJO_SI_EXISTE_ERRORES_EN_CAJAS_NO_DEVUELTAS = ArchivoErrorCodes.EN_ORDINARIA_NO_PUEDE_FINALIZARSE_COTEJO_SI_EXISTE_ERRORES_EN_CAJAS_NO_DEVUELTAS;
	public static final int XRELACION_TIENE_UDOCS_EN_ESTADO_PENDIENTE = ArchivoErrorCodes.RELACION_TIENE_UDOCS_EN_ESTADO_PENDIENTE;
	public static final int XEN_RELACION_ES_CON_ERRORES_SOLO_ES_POSIBLE_EXTRAER_EXPEDIENTES_CON_ERRORES = 15;
	public static final int XEN_ORDINARIAS_SI_PARTE_CON_ERRORES_TODAS_CON_ERRORES = ArchivoErrorCodes.EN_ORDINARIAS_SI_PARTE_CON_ERRORES_TODAS_CON_ERRORES;
	public static final int X_EL_USUARIO_NO_TIENE_PERMISOS_REQUERIDOS_VALIDACION = 41;
	public static final int XLA_UNIDAD_INSTALACION_NO_ES_MULTIDOCUMENTO = 42;
	public static final int X_SIGNATURAUI_NO_EXISTE = 43;
	public static final int X_IGUAL_SIGNATURA_QUE_UI_EXISTENTE = 44;
	public static final int XEL_FORMATO_CAJA_NO_FORMATO_RELACION = 45;
	public static final int XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_NO_EXISTE_EN_EL_ARCHIVO = 46;
	public static final int XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_DEL_ARCHIVO_ESTA_UTILIZADO = 47;
	public static final int XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTO_FORMATO_RELACION = 48;
	public static final int XHUECOS_ASOCIADOS_RELACION_YA_UTILIZADOS = 49;
	public static final int XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION = 50;
	public static final int XSIGNATURA_ASOCIADA_HUECO_UTILIZADA_EN_RELACION = 51;
	public static final int XLA_CAJA_A_ASIGNAR_CONTIENE_UNIDADES_CON_DISTINTO_FONDO = 52;
	public static final int XNO_ES_POSIBLE_ASIGNAR_CAJA_POR_ESTAR_BLOQUEADA = 53;
	public static final int XLA_RELACION_SIGNATURADA_PARA_FINALIZAR_HA_DE_TENER_SIGNATURA_EN_TODAS_SUS_CAJAS = 54;
	public static final int X_RELACION_ESTADO_UBICACION_INCORRECTO = 55;
	public static final int X_RELACION_ESTADO_VALIDACION_INCORRECTO = 56;
	public static final int XNO_ES_POSIBLE_BLOQUEAR_UDOC_BLOQUEADAS = 57;
	public static final int XNO_ES_POSIBLE_DESBLOQUEAR_UDOC_DESBLOQUEADAS = 58;

	public static final int XNO_EXISTE_MAPEO_POR_DEFECTO = 59;

	// public static final int
	// XEN_ORDINARIA_NO_SE_PUEDEN_MODIFICAR_DATOS_DE_NINGUN_EXPEDIENTE =
	// ArchivoErrorCodes.EN_ORDINARIA_NO_SE_PUEDEN_MODIFICAR_DATOS_DE_NINGUN_EXPEDIENTE;
	public RelacionEntregaVO relacionVO = null;

	public RelacionOperacionNoPermitidaException(int motivo, String causa) {
		super(motivo, causa);
		setModule(ArchivoModules.TRANSFERENCIAS_MODULE);
	}

	public RelacionOperacionNoPermitidaException(int motivo) {
		super(motivo);
		setModule(ArchivoModules.TRANSFERENCIAS_MODULE);
	}

	public RelacionEntregaVO getRelacionVO() {
		return this.relacionVO;
	}

	public void setRelacionVO(RelacionEntregaVO relacionVO) {
		this.relacionVO = relacionVO;
	}

}