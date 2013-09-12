package common.exceptions;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionError;

import salas.SalasConsultaConstants;
import salas.exceptions.SalasConsultaException;
import transferencias.exceptions.RelacionOperacionNoPermitidaException;
import auditoria.ArchivoErrorCodes;

import common.Messages;
import common.definitions.ArchivoModules;

import deposito.DepositoConstants;
import deposito.exceptions.TipoElementoDepositoException;
import deposito.global.Constants;
import deposito.model.DepositoException;
import docelectronicos.exceptions.DocElectronicosException;
import docvitales.exceptions.DocumentoVitalException;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import gcontrol.exceptions.UsuariosNotAllowedException;
import gcontrol.model.ArchivosException;

/**
 * Permite el formateo de los mensajes de error a motrar cuando se produce una
 * excepcion en el sistema mediante la asociacion de una clave en el
 * application.properties con los codigos de error
 *
 */
public class StrutsExceptionFormatter {

	/**
	 * Clase para mappeo de errores
	 */
	public class MessageMapping {
		HashMap mapping = new HashMap();
		int module;

		public MessageMapping() {
		}

		private String getComposedKey(int module, int codError) {
			return new StringBuffer().append(module).append("#")
					.append(codError).toString();
		}

		void configModule(int module) {
			this.module = module;
		}

		public void put(int numError, String keyMessage) {
			mapping.put(getComposedKey(module, numError), keyMessage);
		}

		public String getMessage(ActionNotAllowedException e) {
			return (String) mapping.get(getComposedKey(e.getModule(),
					e.getCodError()));
		}

		// TODO PREPARA LAS CHEKED PARA Q SE PUEDAN DIFERENCIAR
		public String getMessage(CheckedArchivoException e) {
			// return (String)mapping.get(getComposedKey(e.getModule(),
			// e.getCodError()));
			return null;
		}

	}

	public static StrutsExceptionFormatter instance = null;
	public static MessageMapping mappingErrorsMessages = null;

	private static final String DEFAULT_ERROR_KEY = common.Constants.ERROR_ACCION_NO_PERMITIDA;

	// //mapeo de errores y mensajes
	// static HashMap messages = new HashMap();
	// messages
	// EL_CODIGO_DE_LA_ENTIDAD_YA_EXISTE
	// private final static String EL_CODIGO_DE_LA_ENTIDAD_YA_EXISTE =
	// "errors.fondos.codigoEntidadYaExiste";
	// private final static String EL_NOMBRE_DE_LA_ENTIDAD_YA_EXISTE =
	// "errors.fondos.NombreEntidadYaExiste";

	void initMap() {
		mappingErrorsMessages = new MessageMapping();

		/* AUDITORIA MODULE */
		mappingErrorsMessages.configModule(ArchivoModules.AUDITORIA_MODULE);

		/* DEPOSITOS MODULE */
		mappingErrorsMessages.configModule(ArchivoModules.DEPOSITOS_MODULE);

		mappingErrorsMessages.put(DepositoException.ELEMENT_NOT_EMPTY,
				Constants.ERROR_DEPOSITO_ELEMENTO_NO_VACIO);

		mappingErrorsMessages.put(DepositoException.ELEMENT_DELETED,
				Constants.ERROR_DEPOSITO_ELEMENTO_BORRADO);

		mappingErrorsMessages.put(DepositoException.NOT_LAST_CHILD,
				Constants.DELETE_ERROR_NOT_LAST_CHILD);

		mappingErrorsMessages.put(
				DepositoException.LONGITUD_MENOR_Q_LONGITUD_FORMATO,
				Constants.LONGITUD_DE_ELEMENTO_MENOR_QUE_LONGITUD_FORMATO);

		mappingErrorsMessages.put(
				DepositoException.CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS,
				Constants.CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS);

		mappingErrorsMessages
				.put(DepositoException.EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO,
						Constants.EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO);

		mappingErrorsMessages
				.put(DepositoException.NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION,
						Constants.NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION);

		mappingErrorsMessages.put(
				DepositoException.NO_SE_PUEDE_ELIMINAR_EL_FORMATO,
				Constants.NO_SE_PUEDE_ELIMINAR_EL_FORMATO);

		mappingErrorsMessages.put(
				DepositoException.NO_SE_PUEDE_REUBICAR_EN_UI_NO_MULTIDOC,
				Constants.NO_SE_PUEDE_REUBICAR_EN_UI_NO_MULTIDOC);

		mappingErrorsMessages.put(DepositoException.NUMERACION_HUECO_EN_USO,
				Constants.NUMERACION_HUECO_EN_USO);

		mappingErrorsMessages.put(
				DepositoException.NO_SE_PUEDE_RENUMERAR_HUECOS_ALFANUMERICOS,
				Constants.NO_SE_PUEDE_RENUMERAR_HUECOS_ALFANUMERICOS);

		mappingErrorsMessages.put(
				DepositoException.NO_SE_PUEDE_RENUMERAR_A_HUECOS_OCUPADOS,
				Constants.NO_SE_PUEDE_RENUMERAR_A_HUECOS_OCUPADOS);

		mappingErrorsMessages.put(
				DepositoException.NO_SE_PUEDE_RENUMERAR_A_HUECOS_CREADOS,
				Constants.NO_SE_PUEDE_RENUMERAR_A_HUECOS_CREADOS);

		mappingErrorsMessages
				.put(DepositoException.NO_SE_PUEDE_RENUMERAR_MAXIMO_NUMERACION_EXCEDIDO,
						Constants.NO_SE_PUEDE_RENUMERAR_MAXIMO_NUMERACION_EXCEDIDO);

		mappingErrorsMessages.put(
				DepositoException.NO_SE_PUEDE_UBICAR_A_HUECOS_NO_LIBRES,
				Constants.NO_SE_PUEDE_UBICAR_A_HUECOS_NO_LIBRES);

		mappingErrorsMessages.put(
				DepositoException.NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES,
				Constants.NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES);

		mappingErrorsMessages.put(
				DepositoException.NO_SE_PUEDE_REUBICAR_A_HUECOS_NO_LIBRES,
				Constants.NO_SE_PUEDE_REUBICAR_A_HUECOS_NO_LIBRES);

		mappingErrorsMessages.put(
				DepositoException.COMPACTAR_HUECO_OCUPADO_PROCESO_COMPACTACION,
				Constants.COMPACTAR_HUECO_OCUPADO_PROCESO_COMPACTACION);

		mappingErrorsMessages
				.put(DepositoException.NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES_SIGNATURA_ASOCIADA_A_HUECO,
						Constants.NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES_SIGNATURA_ASOCIADA_A_HUECO);

		mappingErrorsMessages
				.put(DepositoException.NO_SE_PUEDE_CAMBIAR_ESTADO_HUECO_MODIFICACION,
						Constants.NO_SE_PUEDE_CAMBIAR_ESTADO_HUECO_MODIFICACION);

		mappingErrorsMessages.put(DepositoException.SIGNATURA_HUECO_OCUPADA,
				Constants.SIGNATURA_HUECO_OCUPADA);

		/* DESCRIPCION MODULE */
		mappingErrorsMessages.configModule(ArchivoModules.DESCRIPCION_MODULE);

		/* DOCS ELECTRONICOS MODULE */
		mappingErrorsMessages
				.configModule(ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE);

		/* DOCUMENTOS VITALES MODULE */
		mappingErrorsMessages
				.configModule(ArchivoModules.DOCUMENTOS_VITALES_MODULE);

		mappingErrorsMessages.put(
				DocumentoVitalException.EXISTE_DOCUMENTO_VITAL_VIGENTE,
				common.Constants.DOCVITALES_DOCVITAL_ERROR_EXISTENTE_MSG);

		/* EXPLOTACION MODULE */
		mappingErrorsMessages.configModule(ArchivoModules.EXPLOTACION_MODULE);

		/* MAPEO FONDOS */
		mappingErrorsMessages.configModule(ArchivoModules.FONDOS_MODULE);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.X_EL_USUARIO_SELECCIONADO_NO_ES_GESTOR_DE_SERIE,
						common.Constants.ERRORS_FONDOS_USUARIO_NO_GESTOR_SERIE);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.X_PRODUCTOR_DEL_PROCEDIMIENTO_NO_SE_ENCUENTRA_EN_EL_SIST_ORG,
						common.Constants.ERRORS_FONDOS_PRODUCTOR_NO_ORGANIZACION);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XCL_SERIE_NO_BORRABLE_TIENE_HIJOS_VIGENTES,
						common.Constants.ERRORS_FONDOS_CS_NO_POSIBLE_ELIMINAR_X_HIJOS_VIGENTES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XCL_FONDO_NO_BORRABLE_TIENE_HIJOS,
						common.Constants.ERRORS_FONDOS_CF_NO_POSIBLE_ELIMINAR_X_HIJOS);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XCL_SERIE_NO_BORRABLE_TIENE_SERIES,
						common.Constants.ERRORS_FONDOS_CS_NO_POSIBLE_ELIMINAR_X_TENER_SERIES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XCLASIFICADOR_NO_PUEDE_TENER_FONDOS,
						common.Constants.ERRORS_FONDOS_CS_NO_PUEDE_TENER_FONDOS);

		mappingErrorsMessages.put(
				FondosOperacionNoPermitidaException.XCODIGO_ENTIDAD_YA_EXISTE,
				common.Constants.ERRORS_FONDOS_CODIGO_ENTIDAD_YA_EXISTE);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XENTIDAD_PRODUCTORA_ASIGNADA,
						common.Constants.ERRORS_FONDOS_NOMBRE_ENTIDAD_YA_EXISTE);

		mappingErrorsMessages.put(
				FondosOperacionNoPermitidaException.XCODIGO_SERIE_YA_EXISTE,
				common.Constants.ERRORS_FONDOS_CODSERIE_YA_EXISTE);

		mappingErrorsMessages.put(
				FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE,
				common.Constants.ERRORS_FONDOS_CODELEMENTO_YA_EXISTE);

		mappingErrorsMessages.put(
				FondosOperacionNoPermitidaException.XELEMENTO_NO_VACIO,
				common.Constants.ERRORS_FONDOS_ELEMENTO_NO_VACIO);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XFONDO_NO_BORRABLE_ENT_TIENE_ORGANOS_ASOCIADOS,
						common.Constants.ERRORS_FONDOS_ELIMINACION_X_ORGANOS_ASOCIADOS);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XFONDO_NO_BORRABLE_TIENE_CLASIFICADORES_SERIE_VIGENTES,
						common.Constants.ERRORS_FONDOS_BORRAR_X_CLASIFS_SERIES_VIGENTES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XFONDO_NO_BORRABLE_TIENE_SERIES_ASOCIADAS,
						common.Constants.ERRORS_FONDOS_ELIMINACION_X_SERIES_DESCENDIENTES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XFONDO_NO_MOVIBLE_XTENER_UDOCS,
						common.Constants.ERRORS_FONDOS_MOVER_X_TENER_UDOCS);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XLA_SERIE_SE_ENCUENTRA_EN_UN_ESTADO_INCORRECTO_PARA_REALIZAR_ESTA_OPERACION,
						common.Constants.ERRORS_FONDOS_OPERACION_NOPERMITIDA_X_ESTADO);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XSERIE_TIENE_VALORACION_DICTAMINADA,
						common.Constants.ERRORS_FONDOS_OPERACION_NOPERMITIDA_X_VALORACION_DICTAMINADA);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_TIENE_HIJOS,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_X_HIJOS_VIGENTES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_TIENE_SERIES,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_X_SERIES_VIGENTES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_HAY_HERMANOS_CON_MISMO_CODIGO,
						common.Constants.ERRORS_FONDOS_MOVER_X_MISMO_COD_EN_DESTINO);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_UDOCS_EN_ELIMINACION_NO_FINALIZADA,
						common.Constants.ERROR_NO_SE_PUEDE_MOVER_UDOCS_EN_ELIMINACION_NO_FINALIZADA);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_TIENE_SERIES,
						common.Constants.ERRORS_FONDOS_MOVER_X_SERIES_DESCENDIENTES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_NUEVO_PADRE_NO_VIGENTE,
						common.Constants.ERRORS_FONDOS_MOVER_X_DESTINO_NO_VIGENTE);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_TIENE_PERMISOS_SOBRE_LA_SERIE,
						common.Constants.ERRORS_FONDOS_CAMBIO_X_NO_SER_GESTOR);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_PADRE_TIENE_ESTADO_NO_VIGENTE,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_X_PADRE_NO_VIGENTE);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_TITULO,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_TITULO);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_FECHA_EXTREMA_INICIAL,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_F_INICIAL);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_DEFINICION,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_DEFINICION);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_TRAMITES,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_TRAMITES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_NORMATIVA,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_FALTAR_NORMATIVA);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_NO_ESTA_IDENTIFICADA,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_NO_IDENTIFICADA);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_NO_TIENE_PRODUCTORES_ASOCIADOS,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_SERIE_X_NO_PRODUCTORES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XEL_PROCEDIMIENTO_NO_TIENE_PRODUCTORES_ASOCIADOS,
						common.Constants.ERRORS_FONDOS_ASOCIAR_PROCEDIMIENTO_SERIE_X_NO_PRODUCTORES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XTIPO_CLASIFICADOR_NO_PUEDE_SER_CAMBIADO,
						common.Constants.ERRORS_FONDOS_CAMBIAR_ESTADO_CLASIF_X_DESCENDIENTES);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.CAMPOS_OBLIGATORIOS_DESCRIPCION_SIN_VALOR,
						common.Constants.ERRORS_FONDOS_PASO_A_VIGENTE_SIN_DESCRIBIR);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_PROVOCA_FONDOS_DISTINTOS_EN_MISMA_CAJA,
						common.Constants.ERRORS_MOVER_UDOCS_CAJA_DISTINTOS_FONDOS);

		mappingErrorsMessages
				.put(ArchivoErrorCodes.ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION,
						common.Constants.ERRORS_FONDOS_CREAR_X_EXISTE_OTRA_VALORACION);

		mappingErrorsMessages
				.put(ArchivoErrorCodes.ERROR_FRACCION_SERIE_NO_DIVISIBLE_XPRESTAMO_CONSULTA,
						common.Constants.ERRORS_FONDOS_FRACCION_SERIE_NO_DIVISIBLE_XPRESTAMO_CONSULTA);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.ELEMENTO_TIENE_DOCUMENTOS_ASOCIADOS,
						common.Constants.ERRORS_FONDOS_ELEMENTO_TIENE_DOCUMENTOS_ASOCIADOS);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.XDIVISION_FS_ESTADO_INCORRECTO_BORRADO,
						common.Constants.XDIVISION_FS_ESTADO_INCORRECTO_BORRADO);

		mappingErrorsMessages
				.put(FondosOperacionNoPermitidaException.ERROR_SIN_PERMISOS_GESTION_UDOCS_CONSERVADAS,
						Constants.ERROR_SIN_PERMISOS_GESTION_UDOCS_CONSERVADAS_KEY);

		mappingErrorsMessages
		.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_ELIMINAR_SERIE_TIENE_RELACIONES,
				Constants.ERROR_NO_SE_PUEDE_ELIMINAR_SERIE_TIENE_RELACIONES);

		mappingErrorsMessages
		.put(FondosOperacionNoPermitidaException.XNO_SE_PUEDE_ELIMINAR_SERIE_TIENE_DETALLES,
				Constants.ERROR_NO_SE_PUEDE_ELIMINAR_SERIE_TIENE_DETALLES_PREVISON);


		/** SERVICioS MODULE **/
		mappingErrorsMessages.configModule(ArchivoModules.SERVICIOS_MODULE);

		/** SISTEMAS MODULE **/
		mappingErrorsMessages.configModule(ArchivoModules.SISTEMA_MODULE);

		/** TRANSFERENCIAS MODULE **/
		mappingErrorsMessages
				.configModule(ArchivoModules.TRANSFERENCIAS_MODULE);

		mappingErrorsMessages.put(ArchivoErrorCodes.FONDO_DESTINO_NO_DEFINIDO,
				common.Constants.ERRORS_TRANSF_NO_POSIBLE_TRANSFERIR);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.ARCHIVO_DESTINO_NO_DEFINIDO,
				common.Constants.ERRORS_TRANSF_NO_POSIBLE_TRANSFERIR_ARCHIVO);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.FONDOS_DESTINO_NO_DEFINIDOS,
				common.Constants.ERRORS_FONDOS_DESTINO_NO_DEFINIDOS);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.FONDOS_DESTINO_NO_DEFINIDOS_ALTA,
				common.Constants.ERRORS_FONDOS_DESTINO_NO_DEFINIDOS_ALTA);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.ARCHIVOS_DESTINO_NO_DEFINIDOS_ALTA,
				common.Constants.ERRORS_ARCHIVOS_DESTINO_NO_DEFINIDOS_ALTA);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.ARCHIVOS_DESTINO_NO_DEFINIDOS,
				common.Constants.ERRORS_ARCHIVOS_DESTINO_NO_DEFINIDOS);

		mappingErrorsMessages
				.put(ArchivoErrorCodes.RELACION_UDOC_CON_DOC_FISICOS_SIN_CAJA,
						common.Constants.ERRORS_RELAC_ENVIAR_X_UDOCS_FISICOS_FUERA_CAJA);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.ERROR_NO_HAY_PREVISIONES_SELECCIONADAS,
				common.Constants.ERRORS_PREVISIONES_NO_SELECTED);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.ERROR_NO_HAY_RELACIONES_SELECCIONADAS,
				common.Constants.ERRORS_RELACIONES_NO_SELECTED);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.ERROR_NO_EXISTE_GESTOR_ESPECIFICADO,
				common.Constants.ERRORS_TRANSF_GESTOR_NO_ENCONTRADO);

		mappingErrorsMessages
				.put(ArchivoErrorCodes.ERROR_GESTOR_ESTANDAR_CON_PREVISION_A_GESTIONAR,
						common.Constants.ERRORS_PREVISIONES_GESTOR_CON_OTRA_PREVISION);

		mappingErrorsMessages
				.put(ArchivoErrorCodes.PREVISION_EN_CURSO_EN_ORGANO_REMITENTE,
						common.Constants.ERRORS_PREVISIONES_ORGANO_GESTOR_OTRA_PREVISION);

		mappingErrorsMessages
				.put(ArchivoErrorCodes.PREVISION_ENTRE_ARCHIVOS_EN_CURSO_EN_USUARIO_REMITENTE,
						common.Constants.ERRORS_PREVISIONES_USUARIO_GESTOR_OTRA_PREVISION_ENTRE_ARCH);

		mappingErrorsMessages
				.put(ArchivoErrorCodes.EXISTE_LINEA_DETALLE_MISMO_PROCEDIMIENTO,
						common.Constants.ERRORS_PREVISIONES_OTRO_DETALLE_MISMO_PROCEDIMIENTO);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.EXISTE_LINEA_DETALLE_MISMA_SERIE,
				common.Constants.ERRORS_PREVISIONES_OTRO_DETALLE_MISMA_SERIE);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_PRIMERA_POSICION,
						common.Constants.ERRORS_RELACIONES_SUBIR_FIRST_UDOC);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_ULTIMA_POSICION,
						common.Constants.ERRORS_RELACIONES_BAJAR_LAST_UDOC);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_MOVER_POSICIONES_NO_CONSECUTIVAS,
						common.Constants.ERRORS_RELACIONES_MOVER_UDOCS_NO_CONSECUTIVAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_DIVIDIR_LA_RELACION_NO_TIENE_FORMATO_MULTIDOCUMENTO,
						common.Constants.ERRORS_RELACIONES_DIVIDIR_FORMATO);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_ELIMINAR_LA_ULTIMA_PARTE,
						common.Constants.ERRORS_RELACIONES_SOLO_ELIMINAR_LAST_PARTE);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_DIVIDIR_LA_ULTIMA_UDOC_DE_LA_CAJA,
						common.Constants.ERRORS_RELACIONES_SOLO_DIVIDIR_LAST_UDOC);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_DIVIDIR_LA_ULTIMA_PARTE_DE_UDOC,
						common.Constants.ERRORS_RELACIONES_SOLO_DIVIDIR_LAST_PARTE);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEN_RELACION_ES_CON_ERRORES_SOLO_ES_POSIBLE_EXTRAER_EXPEDIENTES_CON_ERRORES,
						common.Constants.ERRORS_RELACIONES_SACAR_SOLO_UDOCS_ERRONEAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_INCORPORAR_EXPEDIENTES_A_UNA_CAJA_CON_LA_PRIMERA_PARTE_O_LA_ULTIMA_DE_UNA_UNIDAD_DOC,
						common.Constants.ERRORS_RELACIONES_METER_UDOCS_CAJA_CON_PARTE);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_EXTRAER_EXPEDIENTES_COMPLETOS,
						common.Constants.ERRORS_RELACIONES_SOLO_EXTRAER_UDOCS_COMPLETAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_ELIMINAR_CAJAS_VACIAS,
						common.Constants.ERRORS_RELACIONES_SOLO_ELIMINAR_CAJAS_VACIAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XLA_SIGNATURA_YA_EXISTE_EN_EL_SISTEMA,
						common.Constants.ERRORS_RELACIONES_SIGNATURA_REPETIDA);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XLA_CAJA_A_ASIGNAR_CONTIENE_UNIDADES_CON_DISTINTO_FONDO,
						common.Constants.ERRORS_RELACIONES_LA_CAJA_A_ASIGNAR_CONTIENE_UNIDADES_CON_DISTINTO_FONDO);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_ASIGNAR_CAJA_POR_ESTAR_BLOQUEADA,
						null);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_NO_EXISTE_EN_EL_ARCHIVO,
						common.Constants.ERRORS_RELACIONES_HUECO_ASOCIADO_SIGNATURA_NO_EXISTE_EN_ARCHIVO);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_DEL_ARCHIVO_ESTA_UTILIZADO,
						common.Constants.ERRORS_RELACIONES_HUECO_ASOCIADO_A_LA_SIGNATURA_DEL_ARCHIVO_ESTA_UTILIZADO);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTO_FORMATO_RELACION,
						common.Constants.ERROR_RELACIONES_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTO_FORMATO_RELACION);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEL_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION,
						common.Constants.ERROR_RELACIONES_HUECO_ASOCIADO_A_LA_SIGNATURA_TIENE_DISTINTA_UBICACION_RELACION);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XSIGNATURA_ASOCIADA_HUECO_UTILIZADA_EN_RELACION,
						common.Constants.ERROR_RELACIONES_SIGNATURA_ASOCIADA_HUECO_UTILIZADA_EN_RELACION);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XHUECOS_ASOCIADOS_RELACION_YA_UTILIZADOS,
						null);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XSOLO_ES_POSIBLE_ELIMINAR_UDOCS_CON_ERRORES,
						common.Constants.ERRORS_RELACIONES_SOLO_ELIMINAR_UDOCS_ERRONEAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_BLOQUEAR_UDOC_BLOQUEADAS,
						common.Constants.ERRORS_RELACIONES_NO_PERMITIR_BLOQUEAR_UDOCS_BLOQUEADAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_DESBLOQUEAR_UDOC_DESBLOQUEADAS,
						common.Constants.ERRORS_RELACIONES_NO_PERMITIR_DESBLOQUEAR_UDOCS_DESBLOQUEADAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XLA_RELACION_SIGNATURADA_PARA_PODER_SER_ENVIADA_HA_DE_TENER_SIGNATURA_EN_TODAS_SUS_CAJAS,
						common.Constants.ERRORS_RELACIONES_FALTAN_SIGNATURAS_CAJAS);

		mappingErrorsMessages.put(
				RelacionOperacionNoPermitidaException.XSOLO_PERMITIDO_A_GESTOR,
				common.Constants.ERRORS_RELACIONES_ONLY_ALLOW_TO_GESTOR);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XRELACION_EN_ESTADO_INCORRECTO,
						common.Constants.ERRORS_RELACIONES_ESTADO_INCORRECTO);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XNO_PERMITIDO_EN_TRANSFERENCIAS_ORDINARIAS,
						common.Constants.ERRORS_TRANSF_NOT_ALLOWED_IN_ORDINARIAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEN_RELACION_ES_CON_ERRORES_SOLO_ES_POSIBLE_ELIMINAR_PARTE_EXPEDIENTE_CON_ERRORES,
						common.Constants.ERRORS_RELACIONES_ONLY_REMOVE_WRONG_UDOCS_PARTS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEN_ORDINARIA_NO_PUEDE_FINALIZARSE_COTEJO_SI_EXISTE_ERRORES_EN_CAJAS_NO_DEVUELTAS,
						common.Constants.ERRORS_RELACIONES_FIN_COTEJO_SIN_DEVOLUCION_FISICA);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEN_ORDINARIAS_SI_PARTE_CON_ERRORES_TODAS_CON_ERRORES,
						common.Constants.ERRORS_RELACIONES_PARTE_CON_ERRORES_TODAS_CON_ERRORES);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XRELACION_TIENE_UDOCS_EN_ESTADO_PENDIENTE,
						common.Constants.ERRORS_RELACIONES_FIN_COTEJO_X_UDOCS_PENDIENTES);

		// mappingErrorsMessages.put(RelacionOperacionNoPermitidaException.XEN_ORDINARIA_NO_SE_PUEDEN_MODIFICAR_DATOS_DE_NINGUN_EXPEDIENTE,
		// "En Transferencia Ordinaria no se puede modificar los datos de ningún expediente");

		mappingErrorsMessages
				.put(ArchivoErrorCodes.EXISTE_RELACION_MISMA_SERIE,
						common.Constants.ERRORS_RELACIONES_NOT_ALLOWED_X_OTRA_REL_SAME_PREV_Y_SERIE);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.X_EL_USUARIO_NO_TIENE_PERMISOS_REQUERIDOS_VALIDACION,
						common.Constants.X_EL_USUARIO_NO_TIENE_PERMISOS_REQUERIDOS_VALIDACION);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XLA_UNIDAD_INSTALACION_NO_ES_MULTIDOCUMENTO,
						common.Constants.ERRORS_RELACIONES_UI_NO_MULTIDOCUMENTO);

		mappingErrorsMessages.put(
				RelacionOperacionNoPermitidaException.X_SIGNATURAUI_NO_EXISTE,
				common.Constants.ERRORS_RELACIONES_SIGNATURAUI_NO_EXISTE);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.X_IGUAL_SIGNATURA_QUE_UI_EXISTENTE,
						common.Constants.ERRORS_RELACIONES_DIVIDIR_SIGNATURAUI_NO_PERMITIDA);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XEL_FORMATO_CAJA_NO_FORMATO_RELACION,
						common.Constants.ERRORS_FORMATO_NO_VALIDO);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.XLA_RELACION_SIGNATURADA_PARA_FINALIZAR_HA_DE_TENER_SIGNATURA_EN_TODAS_SUS_CAJAS,
						common.Constants.ERRORS_RELACIONES_FINALIZAR_CORRECCION_FALTAN_SIGNATURAS_CAJAS);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.X_RELACION_ESTADO_UBICACION_INCORRECTO,
						common.Constants.ERRORS_ESTADO_UBICACION_INCORRECTO);

		mappingErrorsMessages
				.put(RelacionOperacionNoPermitidaException.X_RELACION_ESTADO_VALIDACION_INCORRECTO,
						common.Constants.ERRORS_ESTADO_VALIDACION_INCORRECTO);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.REEA_NO_MULTIDOC_CON_UIS_CON_VARIAS_UDOCS,
				common.Constants.ERRORS_RELAC_NO_MULTIDOC_CON_UIS_VARIOS_UDOCS);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.REEA_NO_MULTIDOC_CON_UIS_CON_VARIAS_FS,
				common.Constants.ERRORS_RELAC_NO_MULTIDOC_CON_UIS_VARIAS_FS);

		mappingErrorsMessages.put(
				ArchivoErrorCodes.REEA_REENCAJADO_NO_FINALIZA_SIN_UDOCS,
				common.Constants.ERRORS_RELAC_NO_FINALIZA_SIN_UDOCS);

		/** USUARIOS MODULE **/
		mappingErrorsMessages.configModule(ArchivoModules.USUARIOS_MODULE);

		mappingErrorsMessages.put(
				UsuariosNotAllowedException.XUSUARIO_YA_EXISTE,
				common.Constants.ERRORS_GCONTROL_NUEVO_USUARIO_YA_EXISTE);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.XORGANO_NO_EXISTE_EL_SISTEMA_ES_NECESARIO_CREARLO,
						common.Constants.ERRORS_GCONTROL_NUEVO_USUARIO_ORGANO_NO_EN_SISTEMA);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.XLISTA_NO_BORRABLE_TIENE_ELEMENTOS_DEL_CUADRO_ASOCIADOS,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_ELIMINAR_X_ELEMENTOS_CF_ASOCIADOS);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.XLISTA_NO_BORRABLE_TIENE_DESCRIPTORES_ASOCIADOS,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_ELIMINAR_X_DESCRIPTORES_ASOCIADOS);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.XLISTA_NO_BORRABLE_TIENE_FORMATOS_DE_FICHA_ASOCIADOS,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_ELIMINAR_X_FORMS_FICHA_ASOCIADOS);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.XLISTA_NO_BORRABLE_PERTENECE_A_PRODUCTOR_SERIE,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_ELIMINAR_X_PERTENECER_A_PRODUCTOR);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.USUARIO_TIENE_PREVISIONES_EN_ELABORACION,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_USER_WITH_PREVS_EN_ELABORACION);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.USUARIO_TIENE_RELACIONES_EN_ELABORACION,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_USER_WITH_RELS_EN_ELABORACION);

		mappingErrorsMessages.put(
				UsuariosNotAllowedException.USUARIO_TIENE_RELACIONES,
				common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_USER_WITH_RELS);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.USUARIO_ES_GESTOR_SERIES,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_USER_ES_GESTOR_SERIES);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.USUARIO_TIENE_PRESTAMOS_EN_CURSO,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_USER_CON_PRESTAMOS_EN_CURSO);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.USUARIO_TIENE_CONSULTAS_EN_CURSO,
						common.Constants.ERRORS_GCONTROL_LISTADO_ACCESO_USER_CON_CONSULTAS_EN_CURSO);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.USUARIO_NO_PERTENECE_A_NINGUN_ARCHIVO,
						common.Constants.ERRORS_GCONTROL_NEW_PREV_ENTRE_ARCH_USER_NOT_BELONG_TO_ANY_ARCH);

		mappingErrorsMessages
				.put(UsuariosNotAllowedException.USUARIO_TIENE_ASOCIADO_USUARIO_SALA_CONSULTA,
						common.Constants.ERRORS_GCONTROL_USUARIO_TIENE_ASOCIADO_USUARIO_SALA_CONSULTA);

		/* DOC ELECTRONICOS */

		mappingErrorsMessages
				.configModule(ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_SE_PUEDE_CREAR_TAREA_FALTA_REPOSITORIO_ECM_DESCRIPTOR,
						common.Constants.ERRORS_EDOCS_CREAR_TAREA_DESCRIPTOR);
		mappingErrorsMessages
				.put(DocElectronicosException.XNO_SE_PUEDE_CREAR_TAREA_FALTA_REPOSITORIO_ECM_NIVEL_CUADRO,
						common.Constants.ERRORS_EDOCS_CREAR_TAREA_NIVEL_CUADRO);

		mappingErrorsMessages
				.put(DocElectronicosException.XSOLO_ES_POSIBLE_FINALIZAR_TAREAS_EN_ESTADO_PENDIENTE_O_CON_ERRORES,
						common.Constants.ERRORS_EDOCS_ONLY_END_TAREAS_PENDIENTES_O_CON_ERRORES);

		mappingErrorsMessages
				.put(DocElectronicosException.XTAREA_CON_ERRORES_NO_PUEDE_FINAL_X_TENER_CL_EN_ESTADO_NO_VALIDO,
						common.Constants.ERRORS_EDOCS_END_TAREA_ERRONEA_X_CL_NO_VALIDO);

		mappingErrorsMessages
				.put(DocElectronicosException.XTAREA_CON_ERRORES_NO_PUEDE_FINAL_X_TENER_DOC_EN_ESTADO_NO_VALIDO,
						common.Constants.ERRORS_EDOCS_END_TAREA_ERRONEA_X_DOC_NO_VALIDO);

		mappingErrorsMessages.put(
				DocElectronicosException.XNO_TENER_PERMISOS_SOBRE_TAREA,
				common.Constants.ERRORS_EDOCS_NO_PERMISOS_EN_TAREA);

		mappingErrorsMessages.put(
				DocElectronicosException.XELEMENTOS_SIN_VALIDAR,
				common.Constants.ERRORS_EDOCS_ELEMENTOS_SIN_VALIDAR);

		mappingErrorsMessages
				.put(DocElectronicosException.XSOLO_ES_POSIBLE_ELIMINAR_TAREAS_ES_ESTADO_PENDIENTE,
						common.Constants.ERRORS_EDOCS_SOLO_ELIMINAR_TAREAS_PENDIENTES);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_ES_POSIBLE_CAMBIAR_ESTADO_A_ELEMENTOS_PUBLICADOS,
						common.Constants.ERRORS_EDOCS_CAMBIAR_ESTADO_A_PUBLICADOS);

		mappingErrorsMessages
				.put(DocElectronicosException.XPARA_VALIDAR_LA_TAREA_DEBE_ESTAR_EN_ESTADO_FINALIZADA_CAPTURA,
						common.Constants.ERRORS_EDOCS_SOLO_VALIDAR_TAREA_FINALIZADA);

		mappingErrorsMessages
				.put(DocElectronicosException.XSOLO_ES_POSIBLE_FINALIZAR_VALIDACION_TAREA_FINALIZADA,
						common.Constants.ERRORS_EDOCS_SOLO_END_VALIDACION_TAREA_FINALIZADA);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_ES_POSIBLE_MODIFICAR_LA_TAREA_EN_ESTADO_FINALIZADA,
						common.Constants.ERRORS_EDOCS_NO_MODIFICAR_TAREA_FINALIZADA);

		mappingErrorsMessages
				.put(DocElectronicosException.XESTE_ELEMENTO_AUN_TIENE_UNA_CAPTURA_CON_VALIDACION_NO_FINALIZADA,
						common.Constants.ERRORS_EDOCS_ELEMENTO_CON_CAPTURA_NO_VALIDADA);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_SE_PUEDE_CREAR_TAREA_EL_USUARIO_NO_TIENE_ACCESO_AL_ELEMENTO_SOBRE_EL_Q_SE_REALIZA_LA_TAREA,
						common.Constants.ERRORS_EDOCS_USUARIOS_SIN_PERMISOS_OBJETO_TAREA);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_SE_PUEDE_REALIZAR_OPERACION_FALTA_REPOSITORIO_ECM_DESCRIPTOR,
						common.Constants.ERRORS_EDOCS_NO_SE_PUEDE_REALIZAR_OPERACION_FALTA_REPOSITORIO_ECM_DESCRIPTOR);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_SE_PUEDE_REALIZAR_OPERACION_FALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO,
						common.Constants.ERRORS_EDOCS_NO_SE_PUEDE_REALIZAR_OPERACION_FALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_CLASIFICADOR_XTIENE_DESCENDIENTES,
						common.Constants.ERRORS_EDOCS_NO_SE_PUEDE_ELIMINAR_CLASIFICADOR_XTIENE_DESCENCIENTES);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_DOCUMENTO_XFALTA_REPOSITORIO_ECM_DESCRIPTOR,
						common.Constants.ERRORS_EDOCS_NO_SE_PUEDE_ELIMINAR_DOCUMENTO_FALTA_REPOSITORIO_ECM_DESCRIPTOR);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_DOCUMENTO_XFALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO,
						common.Constants.ERRORS_EDOCS_NO_SE_PUEDE_ELIMINAR_DOCUMENTO_FALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_EXISTEN_DOCUMENTOS_ELECTRONICOS_ASOCIADOS_DESCRIPTOR,
						common.Constants.ERRORS_EDOCS_NO_EXISTEN_DOCUMENTOS_ELECTRONICOS_ASOCIADOS_DESCRIPTOR);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_EXISTEN_DOCUMENTOS_ELECTRONICOS_ASOCIADOS_ELEMENTO_CUADRO,
						common.Constants.ERRORS_EDOCS_NO_EXISTEN_DOCUMENTOS_ELECTRONICOS_ASOCIADOS_ELEMENTO_CUADRO);

		mappingErrorsMessages
				.put(DocElectronicosException.XNO_PERMITIDO_PARA_UNIDADES_SIN_VALIDAR,
						common.Constants.XNO_PERMITIDO_PARA_UNIDADES_SIN_VALIDAR);

		mappingErrorsMessages.configModule(ArchivoModules.ARCHIVOS_MODULE);

		mappingErrorsMessages.put(
				ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO,
				common.Constants.ERROR_NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_NAME);
		mappingErrorsMessages.put(
				ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_NIVEL_ARCHIVO,
				common.Constants.ERROR_NO_SE_PUEDE_ELIMINAR_NIVEL);
		mappingErrorsMessages
				.put(ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_EN_FONDOS,
						common.Constants.ERRORS_ARCHS_ELIMINACION_NOT_ALLOWED_ESTA_EN_FONDOS);
		mappingErrorsMessages
				.put(ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_EN_UBICACION,
						common.Constants.ERRORS_ARCHS_ELIMINACION_NOT_ALLOWED_ESTA_EN_UBICACIONES);
		mappingErrorsMessages
				.put(ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_EN_EDIFICIOS_SC,
						common.Constants.ERRORS_ARCHS_ELIMINACION_NOT_ALLOWED_ESTA_EN_EDIFICIOS_SC);
		mappingErrorsMessages
				.put(ArchivosException.NO_SE_PUEDE_ELIMINAR_EL_ARCHIVO_EXISTE_CONSULTAS,
						common.Constants.ERRORS_ARCHS_ELIMINACION_NOT_ALLOWED_ESTA_EN_CONSULTAS);

		// GESTION DE SALAS
		mappingErrorsMessages.configModule(ArchivoModules.SALAS_MODULE);

		mappingErrorsMessages.put(
				SalasConsultaException.NO_ELIMINAR_USUARIO_CON_CONSULTAS,
				SalasConsultaConstants.ERROR_NO_ELIMINAR_USUARIO_CON_CONSULTAS);
		mappingErrorsMessages.put(
				SalasConsultaException.NO_ELIMINAR_USUARIO_EN_SALA,
				SalasConsultaConstants.ERROR_NO_ELIMINAR_USUARIO_EN_SALA);
		mappingErrorsMessages.put(
				SalasConsultaException.NO_MODIFICAR_USUARIO_EN_SALA,
				SalasConsultaConstants.ERROR_NO_MODIFICAR_USUARIO_EN_SALA);
		mappingErrorsMessages.put(
				SalasConsultaException.NOMBRE_EDIFICIO_DUPLICADO,
				SalasConsultaConstants.ERROR_NOMBRE_EDIFICIO_DUPLICADO);
		mappingErrorsMessages.put(SalasConsultaException.EDIFICIO_CON_SALAS,
				SalasConsultaConstants.ERROR_NO_ELIMINAR_EDIFICIO_CON_SALAS);
		mappingErrorsMessages.put(
				SalasConsultaException.EDIFICIO_CON_MESAS_OCUPADAS,
				SalasConsultaConstants.ERROR_EDIFICIO_CON_MESAS_OCUPADAS);
		mappingErrorsMessages.put(SalasConsultaException.NOMBRE_SALA_DUPLICADO,
				SalasConsultaConstants.ERROR_NOMBRE_SALA_DUPLICADO);
		mappingErrorsMessages.put(
				SalasConsultaException.NO_ELIMINAR_SALA_CON_MESAS,
				SalasConsultaConstants.ERROR_NO_ELIMINAR_SALA_CON_MESAS);
		mappingErrorsMessages.put(SalasConsultaException.REGISTRO_MESA_OCUPADA,
				SalasConsultaConstants.ERROR_REGISTRO_DE_MESA_OCUPADA);
		mappingErrorsMessages
				.put(SalasConsultaException.USUARIO_CONSULTA_SALAS_USUARIO_APLICACION_YA_CREADO,
						SalasConsultaConstants.ERROR_USUARIO_CONSULTA_SALAS_USUARIO_APLICACION_YA_CREADO);
		mappingErrorsMessages
				.put(SalasConsultaException.ARCHIVO_USUARIO_CONSULTA_SALA_CON_MESA_OCUPADA,
						SalasConsultaConstants.ERROR_ARCHIVO_USUARIO_CONSULTA_SALA_CON_MESA_OCUPADA);
		mappingErrorsMessages
				.put(SalasConsultaException.ERROR_AL_MOSTRAR_ELEMENTO,
						SalasConsultaConstants.ERROR_ARCHIVO_USUARIO_CONSULTA_SALA_CON_MESA_OCUPADA);
		mappingErrorsMessages
				.put(SalasConsultaException.ERROR_ELEMENTO_NO_ENCONTRADO,
						SalasConsultaConstants.ERROR_ARCHIVO_USUARIO_CONSULTA_SALA_CON_MESA_OCUPADA);
		mappingErrorsMessages.put(
				SalasConsultaException.ERROR_AL_MOSTRAR_ELEMENTO,
				SalasConsultaConstants.ERROR_AL_MOSTRAR_ELEMENTO_KEY);
		mappingErrorsMessages.put(
				SalasConsultaException.ERROR_ELEMENTO_NO_ENCONTRADO,
				SalasConsultaConstants.ERROR_ELEMENTO_NO_ENCONTRADO_KEY);

		// GESTION DE ORGANIZACION
		mappingErrorsMessages.configModule(ArchivoModules.ORGANIZACION_MODULE);

		mappingErrorsMessages.put(
				OrganizacionException.ERROR_AL_MOSTRAR_ELEMENTO_ORGANIZACION,
				Constants.ERROR_AL_MOSTRAR_ELEMENTO_ORGANIZACION_KEY);
		mappingErrorsMessages.put(
				OrganizacionException.ERROR_ELEMENTO_NO_ENCONTRADO,
				Constants.ERROR_ELEMENTO_NO_ENCONTRADO_KEY);

	}

	/**
	 * Constructor
    *
	 * @param resources
	 *            Mensajes internacionalizados
	 */
	public StrutsExceptionFormatter(/* MessageResources resources */) {
		super();
		initMap();
	}

	public ActionError formatException(ActionNotAllowedException anae) {
		ActionError error = null;

		String message = mappingErrorsMessages.getMessage(anae);
		if (message != null)
			error = new ActionError(message);
		else
			error = new ActionError(
					anae.getMessage() != null ? anae.getMessage()
							: DEFAULT_ERROR_KEY);

		return error;
	}

	public ActionError formatException(TipoElementoDepositoException tede,
			Locale locale) {
		ActionError error = null;

		switch (tede.getCodError()) {
		case TipoElementoDepositoException.NO_ELIMINABLE_TIENE_DESCENDIENTES:
			error = new ActionError(DepositoConstants.ERROR_ELIMINAR_NIVEL_HIJO);
			break;
		case TipoElementoDepositoException.NO_ELIMINABLE_TIPO_REFERENCIADO:
			error = new ActionError(
					DepositoConstants.ERROR_ELIMINAR_NIVEL_REFERENCIADO);
			break;
		case TipoElementoDepositoException.NOMBRE_DUPLICADO:
			error = new ActionError(DepositoConstants.ERROR_NIVEL_DUPLICADO,
					Messages.getDefaultString(
							DepositoConstants.LABEL_TIPO_ELEMENTO_NOMBRE,
							locale));
			break;
		case TipoElementoDepositoException.NOMBRE_ABREVIADO_DUPLICADO:
			error = new ActionError(
					DepositoConstants.ERROR_NIVEL_DUPLICADO,
					Messages.getDefaultString(
							DepositoConstants.LABEL_TIPO_ELEMENTO_NOMBRE_ABREVIADO,
							locale));
			break;
		case TipoElementoDepositoException.CARACTER_ORDENACION_HERMANO_DUPLICADO:
			error = new ActionError(
					DepositoConstants.ERROR_HERMANO_DUPLICADO,
					Messages.getDefaultString(
							DepositoConstants.LABEL_TIPO_ELEMENTO_CARACTER_ORDENACION,
							locale));
			break;
		case TipoElementoDepositoException.CARACTER_ORDENACION_NO_EDITABLE:
			error = new ActionError(
					DepositoConstants.ERROR_TIPO_ELEMENTO_DEPOSITO_NO_EDITABLE,
					Messages.getDefaultString(
							DepositoConstants.LABEL_TIPO_ELEMENTO_CARACTER_ORDENACION,
							locale));
			break;
		case TipoElementoDepositoException.ASIGNABLE_NO_EDITABLE:
			error = new ActionError(
					DepositoConstants.ERROR_TIPO_ELEMENTO_DEPOSITO_NO_EDITABLE,
					Messages.getDefaultString(
							DepositoConstants.LABEL_TIPO_ELEMENTO_ASIGNABLE,
							locale));
			break;
		case TipoElementoDepositoException.TIPO_ORDENACION_NO_EDITABLE:
			error = new ActionError(
					DepositoConstants.ERROR_TIPO_ELEMENTO_DEPOSITO_NO_EDITABLE,
					Messages.getDefaultString(
							DepositoConstants.LABEL_TIPO_ELEMENTO_TIPO_ORDENACION,
							locale));
			break;
		case TipoElementoDepositoException.CARACTER_ORDENACION_PADRE_DUPLICADO:
			error = new ActionError(DepositoConstants.ERROR_PADRE_DUPLICADO);
			break;
		default:
			error = new ActionError(DEFAULT_ERROR_KEY);
			break;
		}
		return error;
	}

	public ActionError formatException(CheckedArchivoException cae) {
		ActionError error = null;

		String message = mappingErrorsMessages.getMessage(cae);
		if (message != null)
			error = new ActionError(message);
		else
			error = new ActionError(cae.getMessage() != null ? cae.getMessage()
					: DEFAULT_ERROR_KEY);

		return error;
	}

	public ActionError formatException(ArchivosException ae) {
		ActionError error = null;

		String message = mappingErrorsMessages.getMessage(ae);
		if (message != null)
			error = new ActionError(message);
		else
			error = new ActionError(ae.getMessage() != null ? ae.getMessage()
					: DEFAULT_ERROR_KEY);

		return error;
	}

	// String getMessageI18n(Locale locale, String messageKey){
	// // String ret = resources.getMessage(locale, messageKey, new Object[]{});
	// String ret = null;
	// try{
	// ret = resources.getMessage(messageKey);
	//
	// }catch(Exception e){
	// return messageKey;
	//
	// }
	// return ret;
	// }

	public static StrutsExceptionFormatter getInstance() {
		if (instance == null)
			instance = new StrutsExceptionFormatter();
		return instance;
	}
}
