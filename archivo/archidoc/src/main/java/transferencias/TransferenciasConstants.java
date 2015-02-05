package transferencias;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import transferencias.vos.DocumentoVO;
import transferencias.vos.PrevisionVO;

import common.ConfigConstants;
import common.Messages;
import common.SigiaUtilConstants;
import common.vos.TypeDescVO;

/**
 * Constantes con los nombres de los atributos en los diferentes contextos bajo
 * los que los actions dejan la informacion que las paginas van a mostrar
 * 
 */
public final class TransferenciasConstants {

	private final static String getKey(String key) {
		return "TransferenciasConstants" + "|" + key;
	}

	/**
	 * Patron para el formateo del numero de orden de las previsiones de
	 * transferencia
	 */
	public final static String FORMAT_ORDEN = "0000";
	public final static String SEPARADOR_COLORES_CALENDAR = ",";
	public final static String LISTA_ORGANOS_KEY = getKey("LISTA_ORGANOS_KEY");
	public final static String LISTA_GESTORES_KEY = getKey("LISTA_GESTORES_KEY");
	public final static String GESTOR_KEY = getKey("GESTOR_KEY");
	public final static String PREVISION_KEY = getKey("PREVISIONKEY");
	public final static String TIPOOPERACION_KEY = getKey("tipooperacion");
	public static final String LISTA_PREVISIONES_EN_ELABORACION_KEY = getKey("LISTA_PREVISIONES_EN_ELABORACION_KEY");
	public static final String LISTA_PREVISIONES_A_GESTIONAR_KEY = getKey("LISTA_PREVISIONES_A_GESTIONAR_KEY");
	public final static String LISTA_PREVISIONES_KEY = getKey("LISTAPREVISIONESKEY");
	public static final String PREVISIONES_GESTOR_KEY = getKey("PREVISIONES_GESTOR_KEY");

	public final static String LISTA_UINST_ENTRE_ARCHIVOS_KEY = getKey("LISTA_UINST_ENTRE_ARCHIVOS_KEY");
	public final static String LISTA_UDOCS_ENTRE_ARCHIVOS_KEY = getKey("LISTA_UDOCS_ENTRE_ARCHIVOS_KEY");
	public final static String LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY = getKey("LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY");
	public final static String LISTA_CODIGOSFONDO_KEY = getKey("LISTACODIGOSFONDO");
	public final static String LISTA_CODIGOSARCHIVO_KEY = getKey("LISTACODIGOSARCHIVO");
	public final static String LISTA_CODIGOSARCHIVORECEPTORES_KEY = getKey("LISTACODIGOSARCHIVORECEPTORES");
	// public final static String LISTA_PREVISIONES_SINFINALIZAR_KEY =
	// getKey("LISTA_PREVISIONES_SINFINALIZAR_KEY");
	public final static String USERVO_KEY = getKey("USERVO_KEY");
	public final static String LISTA_SERIES_KEY = getKey("LISTA_SERIES_KEY");
	public final static String LISTA_PROCEDIMIENTOS_KEY = getKey("LISTA_PROCEDIMIENTOS_KEY");
	public final static String LISTA_FORMATOS_KEY = getKey("LISTA_FORMATOS_KEY");

	public static final String LISTA_FORMAS_DOCUMENTALES_KEY = getKey("LISTA_FORMAS_DOCUMENTALES_KEY");
	public final static String LISTA_DEPOSITOS_KEY = getKey("LISTA_DEPOSITOS_KEY");
	public final static String LISTA_EDIFICIOS_KEY = getKey("LISTA_EDIFICIOS_KEY");
	public final static String DETALLEPREVISION_KEY = getKey("DETALLEPREVISION_KEY");
	public final static String DETALLEPREVISION_SELECCIONADO_KEY = getKey("DETALLEPREVISION_SELECCIONADO_KEY");
	public final static String LISTA_ESTADOS_KEY = getKey("LISTA_ESTADOS_KEY");
	public final static String LISTA_TIPOS_KEY = getKey("LISTA_TIPOS_KEY");
	public static final String SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS = getKey("SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS");
	public static final String NOMBRE_MES_KEY = getKey("NOMBRE_MES_KEY");
	public static final String INDEX_MES_KEY = getKey("INDEX_MES_KEY");
	public static final String NUM_PREVISIONES_MES_KEY = getKey("NUM_PREVISIONES_MES_KEY");

	public static final String NUM_UNIDADES_INSTALACION_MES_KEY = getKey("NUM_UNIDADES_INSTALACION_MES_KEY");
	public static final String NUM_DIAS_OCUPADOS_MES_KEY = getKey("NUM_DIAS_OCUPADOS_MES_KEY");
	public static final String DIAS_OCUPADOS_MES_KEY = getKey("DIAS_OCUPADOS_MES_KEY");
	public static final String PREVISIONES_MES_KEY = getKey("PREVISIONES_MES_KEY");
	public static final String CALENDAR_CONFIG_KEY = getKey("CALENDAR_CONFIG_KEY");
	public static final String CALENDAR_LEGEND_KEY = getKey("CALENDAR_LEGEND_KEY");
	public static final String CALENDAR_MES_KEY = getKey("CALENDAR_MES_KEY");
	public static final String LISTA_NIVELES_DOCUMENTALES_KEY = getKey("LISTA_NIVELES_DOCUMENTALES_KEY");
	public static final String LISTA_RANGOS_UDOC = getKey("LISTA_RANGOS_UDOC");

	public static final String UI_REENCAJADO_KEY = getKey("UI_REENCAJADO_KEY");
	public static final String LISTA_UDOCS_REENCAJADO_KEY = getKey("LISTA_UDOCS_REENCAJADO_KEY");

	/********************* RELACIONES *******************************/
	public static final String SON_RELACIONES_FINALIZADAS = getKey("SON_RELACIONES_FINALIZADAS");
	public static final String SON_RELACIONES_RECHAZADAS = getKey("SON_RELACIONES_RECHAZADAS");
	public static final String LISTA_RELACIONES_EN_ELABORACION_KEY = getKey("LISTA_RELACIONES_EN_ELABORACION_KEY");
	public static final String LISTA_RELACIONES_A_GESTIONAR_KEY = getKey("LISTA_RELACIONES_A_GESTIONAR_KEY");
	public static final String LISTA_RESERVAS_ESPACION_KEY = getKey("LISTA_RESERVAS_ESPACION_KEY");
	public final static String LISTA_RELACIONES_KEY = getKey("LISTA_RELACIONES_KEY");
	public final static String LISTA_DESCENDIENTES_KEY = getKey("LISTA_DESCENDIENTES_KEY");
	// public final static String INFO_RELACION_KEY =
	// getKey("INFO_RELACION_KEY");
	public final static String RELACION_KEY = getKey("RELACION_KEY");
	public final static String INGRESO_DIRECTO_KEY = getKey("INGRESO_DIRECTO_KEY");
	public final static String RELACION_EN_ARCHIVO = getKey("RELACION_EN_ARCHIVO");
	public static final String EXISTEN_RELACIONES_A_ELIMINAR = getKey("EXISTEN_RELACIONES_A_ELIMINAR");

	public final static String RELACION_EN_ORG_REMITENTE = getKey("RELACION_EN_ORG_REMITENTE");
	public final static String UNIDAD_DOCUMENTAL = getKey("__UNIDAD_DOCUMENTAL");
	public final static String ORGANISMO_PRODUCTOR = getKey("__ORGANISMO_PRODUCTOR");
	public final static String EMPLAZAMIENTO = getKey("EMPLAZAMIENTO");
	public static final String LISTA_SOPORTES = getKey("LISTA_SOPORTES");
	public static final String LISTA_FORMATOS_DOCUMENTO = getKey("LISTA_FORMATOS_DOCUMENTO");
	public static final String LISTA_PRODUCTORES = getKey("LISTA_PRODUCTORES");
	public static final String SOPORTES_UDOC = getKey("SOPORTES_UDOC");
	public final static String LISTADO_USUARIOS_KEY = getKey("LISTADO_USUARIOS_KEY");

	public final static String LISTADO_UNIDADES_DOCUMENTALES_KEY = getKey("LISTADO_UNIDADES_DOCUMENTALES_KEY");
	public final static String LISTADO_UNIDADES_INSTALACION_SELEC_KEY = getKey("LISTADO_UNIDADES_INSTALACION_SELEC_KEY");
	public static final String RESULTADOS_BUSQUEDA_INTERESADOS = getKey("RESULTADOS_BUSQUEDA_INTERESADOS");
	public final static String REQUEST_URI = getKey("REQUEST_URI");
	public static final String LISTA_CAJAS_KEY = getKey("LISTA_CAJAS_KEY");
	public static final String EXPEDIENTES_SIN_ASIGNAR = getKey("EXPEDIENTES_SIN_ASIGNAR");
	public static final String INCORPORANDO_UNIDAD_DOCUMENTAL = getKey("INCORPORANDO_UNIDAD_DOCUMENTAL");
	public final static String CAJAS_KEY = getKey("CAJAS_KEY");
	public final static String VER_BOTON_SIGNATURAR_KEY = getKey("VER_BOTON_SIGNATURAR_KEY");
	public final static String VER_BOTON_ACEPTAR_COTEJO_KEY = getKey("VER_BOTON_ACEPTAR_COTEJO_KEY");
	public final static String VER_BOTON_GENERAR_CARTELAS_KEY = getKey("VER_BOTON_GENERAR_CARTELAS_KEY");

	public final static String VER_BOTON_FINALIZAR_COTEJO_KEY = getKey("VER_BOTON_FINALIZAR_COTEJO_KEY");
	public final static String PUEDE_SER_SIGNATURADA_KEY = getKey("PUEDE_SER_SIGNATURADA_KEY");
	public final static String CAJAS_ABIERTAS = getKey("CAJAS_ABIERTAS");
	public final static String CAJA_SELECCIONADA_KEY = getKey("CAJA_SELECCIONADA");
	public static final String UDOCS_EN_UI = getKey("UDOCS_EN_UI");
	public static final String ASIGNACION_UDOC2UI = getKey("ASIGNACION_UDOC2UI");
	public final static String ULTIMA_CAJA_VISTA = getKey("ULTIMA_CAJA_VISTA");
	public final static String DEPOSITO_KEY = getKey("DEPOSITO_KEY");
	public final static String CARTELA_GESTOR_KEY = getKey("CARTELA_GESTOR_KEY");
	public static final String INFO_UDOCREEACR = getKey("INFO_UDOCREEACR");

	public final static String CAJA_KEY = getKey("CAJA_KEY");
	public final static String CAJA_VISITADOS_KEY = getKey("CAJA_VISITADOS_KEY");
	public final static String CAJA_MAINTAIN_VISITADOS_KEY = getKey("CAJA_MAINTAIN_VISITADOS_KEY");
	public static final String PARTE_UDOC_TRANSFORMER = getKey("PARTE_UDOC_TRANSFORMER");
	public final static String CODIGO_FONDO_RELACION = getKey("CODIGO_FONDO_RELACION");
	public static final String LISTA_ROLES_INTERESADO = getKey("LISTA_ROLES_INTERESADO");
	public static String INFO_INTERESADO = getKey("LISTA_ROLES_INTERESADO");
	public static final String LISTA_EXPEDIENTES = getKey("LISTA_EXPEDIENTES");
	public static final String NUM_EXPEDIENTES = getKey("NUM_EXPEDIENTES");
	public static final String LISTA_EXPEDIENTES_A_ELIMINAR = getKey("LISTA_EXPEDIENTES_A_ELIMINAR");
	public final static String LISTA_UDOCS_ELECTRONICAS_KEY = getKey("LISTA_UDOCS_ELECTRONICAS");
	public final static String LISTA_UDOCS_ELECTRONICAS_COTEJO_KEY = getKey("LISTA_UDOCS_ELECTRONICAS_COTEJO");
	public final static String UDOCS_UI_KEY = getKey("UDOCS_UI_KEY");

	public static final String DOCUMENTO = getKey("DOCUMENTO");
	public static final String PAISES = getKey("PAISES");
	public static final String PROVINCIAS = getKey("PROVINCIAS");
	public static final String CONCEJOS = getKey("CONCEJOS");
	public static final String PARROQUIAS = getKey("PARROQUIAS");
	public static final String POBLACIONES = getKey("POBLACIONES");
	public static final String MUNICIPIO = getKey("MUNICIPIO");
	public final static String NO_EXISTE_ESPACIO_DISPONIBLE = getKey("NO_EXISTE_ESPACIO_DISPONIBLE");
	public final static String LISTA_HUEVOS_RESERVADOS = getKey("LISTA_HUEVOS_RESERVADOS");

	public final static String LISTA_UBICACIONES = getKey("LISTA_UBICACIONES");
	public final static String LISTA_CARTELAS = getKey("LISTA_CARTELAS");
	public final static String NUM_UNIDADES_INSTALACION = getKey("NUM_UNIDADES_INSTALACION");
	public final static String TIPO_RELACION_KEY = getKey("TIPO_RELACION_KEY");
	public final static String PARTE_UDOC_SELECCIONADA = getKey("PARTE_UDOC_SELECCIONADA");
	public final static String PATH_KEY = getKey("PATH_KEY");
	public final static String PARENT_NAME = getKey("PARENT_NAME");
	public final static String IS_LAST_LEVEL = getKey("IS_LAST_LEVEL");
	public final static String ID_SELECCIONADO = getKey("ID_SELECCIONADO");
	public final static String ARCHIVO_KEY = getKey("ARCHIVO_KEY");
	public final static String LISTA_FICHAS_KEY = getKey("LISTA_FICHAS_KEY");
	public final static String MAP_TODAS_FICHAS_KEY = getKey("MAP_TODAS_FICHAS_KEY");

	public static final String IMPORT_RESULT_XML_KEY = getKey("IMPORT_RESULT_XML");
	public static final String IMPORT_RESULT_XSL_KEY = getKey("IMPORT_RESULT_XSL");

	public static final String CONTADOR_UNIDADES_DOCUMENTALES = getKey("CONTADOR_UNIDADES_DOCUMENTALES");
	public static final String POSICION_UDOC = getKey("POSICION_UDOC");
	public static final String ORDEN_UDOC = getKey("ORDEN_UDOC");
	public final static String UNIDAD_DOCUMENTAL_A_DUPLICAR = getKey("UNIDAD_DOCUMENTAL_A_DUPLICAR");

	public final static String FLAG_CREACION_UDOC_REL_MANTENER_INFORMACION = "creacionUdocRelacionMantenerInformacion";
	public final static String FLAG_MODIFICADA_UDOC_RELACION = "udocRelacionConModificacionesPendientesDeGuardar";

	/**
	 * Lista de Unidades de Instalación para la relación entre archivos
	 */
	public final static String LISTA_UINST_PARA_RELACION_ENTRE_ARCHIVOS = getKey("LISTA_UINST_PARA_RELACION_ENTRE_ARCHIVOS");
	public final static Map previsionTO = SigiaUtilConstants
			.getConstantsMap(PrevisionVO.class);
	public static Map tiposTransferencia = SigiaUtilConstants
			.getConstantsMap(TipoTransferencia.class);
	public static Map tiposSignaturacion = SigiaUtilConstants
			.getConstantsMap(TipoSignaturacionConstants.class);
	public static Map estadoPrevision = SigiaUtilConstants
			.getConstantsMap(EstadoPrevisionConstants.class);
	public static Map estadoREntrega = SigiaUtilConstants
			.getConstantsMap(EstadoREntregaConstants.class);
	public static Map estadoCotejo = SigiaUtilConstants
			.getConstantsMap(EstadoCotejo.class);
	public static Map reservaPrevision = SigiaUtilConstants
			.getConstantsMap(ReservaPrevision.class);
	public static Map tiposDocumento = SigiaUtilConstants
			.getConstantsMap(DocumentoVO.class);
	public static final String banderaEdicionCreacion = "banderaModificandoCreando";

	// Errores
	public static final String NECESARIO_INTRODUCIR_UNA_SIGNATURA = "errors.relaciones.introduzcaUnaSignatura";
	public static final String NECESARIO_SELECCIONAR_EXPEDIENTES_PARA_LA_CAJA = "errors.relaciones.necesarioSeleccionarExpedientes";
	public static final String SIGNATURA_DEBE_SER_UN_VALOR_NUMERICO = "errors.relaciones.signaturaDebeSerValorNumerico";
	public static final String ERROR_TRANSFERENCIAS_SEL_UN_EXPEDIENTE = "archigest.archivo.transferencias.selUnExpediente";
	public static final String ERROR_TRANSFERENCIAS_PREVISIONES_NO_EXISTE_ORGANO = "archigest.archivo.transferencias.previsiones.noExisteOrgano";
	public static final String ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_URL_WS_EMPTY = "archigest.archivo.transferencias.import.file.ws.url.empty";

	public static final String ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_NOT_FOUND = "archigest.archivo.transferencias.import.file.not.found";
	public static final String ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_REMOTE_ERROR = "archigest.archivo.transferencias.import.file.remote.error";
	public static final String ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_SERVICE_ERROR = "archigest.archivo.transferencias.import.file.service.error";
	public static final String ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_INPUT_ERROR = "archigest.archivo.transferencias.import.file.input.error";

	// private static final String
	// NECESARIO_SELECCIONAR_EXPEDIENTES_PARA_LA_CAJA =
	// "errors.relaciones.necesarioSeleccionarExpedientes";
	public final static String NECESARIO_INTRODUCIR_AMBOS_RANGOS = "errors.relaciones.introduzcaAmbosRangos";
	public final static String NECESARIO_INTRODUCIR_RANGO_INICIAL = "errors.relaciones.introduzcaRangoInicial";
	public final static String NECESARIO_INTRODUCIR_RANGO_FINAL = "errors.relaciones.introduzcaRangoFinal";

	public static final String NECESARIO_SELECCIONAR_FORMATO_REENCAJADO = "errors.relaciones.reencajado.necesarioSeleccionarFormato";
	public static final String NO_ENCONTRADA_CAJA = "errors.relaciones.noEncontradaCaja";

	// Labels informes
	public static final String LABEL_INFORME_CARTELAS_OFICINA_CAJA = "archigest.archivo.informe.cartelas.oficina.caja";
	public static final String LABEL_INFORME_CARTELAS_ARCHIVO_PA = "archigest.archivo.informe.cartelas.archivo.pa";
	public static final String LABEL_INFORME_CARTELAS_ARCHIVO_TITULO = "archigest.archivo.informe.cartelas.archivo.titulo";
	public static final String LABEL_INFORME_INFORMERELACION_TITULO = "archigest.archivo.informe.informeRelacion.titulo";
	public static final String LABEL_INFORME_INFORMERELACION_SIGNATURA = "archigest.archivo.informe.informeRelacion.signatura";
	public static final String LABEL_INFORME_INFORMERELACION_CAJA = "archigest.archivo.informe.informeRelacion.caja";
	public static final String LABEL_INFORME_INFORMERELACION_RELACION = "archigest.archivo.informe.informeRelacion.relacion";
	public static final String LABEL_INFORME_INFORMERELACION_REMITENTE = "archigest.archivo.informe.informeRelacion.remitente";
	public static final String LABEL_INFORME_INFORMEALTAUDOCS_TITULO = "archigest.archivo.justificanteAltaUdoc.titulo";
	public static final String LABEL_INFORME_INFORMEALTAUDOCS_CODIGO = "archigest.archivo.justificanteAltaUdoc.codigoAlta";
	public static final String LABEL_INFORME_INFORMEALTAUDOCS_PRODUCTOR = "archigest.archivo.justificanteAltaUdoc.unidad.productora";
	public static final String LABEL_INFORME_INFORMEALTAUDOCS_SERIE = "archigest.archivo.serie";
	public static final String LABEL_INFORME_INFORMEALTAUDOCS_ARCHIVO = "archigest.archivo.justificanteAltaUdoc.archivoCustodia";
	public static final String LABEL_INFORME_INFORMEALTAUDOCS_FIRMA_ARCHIVO = "archigest.archivo.justificanteAltaUdoc.firmaArchivo";
	public static final String LABEL_INFORME_INFORMERELACION_ARCHIVO_RECEPTOR = "archigest.archivo.informe.informeRelacion.archivoReceptor";
	public static final String LABEL_INFORME_INFORMERELACION_ARCHIVO_REMITENTE_NO_DEFINIDO = "archigest.archivo.informe.informeRelacion.remitenteNoDefinido";
	public static final String LABEL_INFORME_INFORMERELACION_ARCHIVO_RECEPTOR_NO_DEFINIDO = "archigest.archivo.informe.informeRelacion.archivoReceptorNoDefinido";
	public static final String LABEL_INFORME_INFORMERELACION_FECHA_RECEPCION = "archigest.archivo.informe.informeRelacion.fechaRecepcion";
	public static final String LABEL_INFORME_INFORMERESERVAS_TITULO = "archigest.archivo.informe.informeReservas.titulo";
	public static final String LABEL_INFORME_INFORMERESERVAS_HUECO = "archigest.archivo.informe.informeReservas.hueco";
	public static final String LABEL_INFORME_INFORMEUBICACION_TITULO = "archigest.archivo.informe.informeUbicacion.titulo";
	public static final String LABEL_INFORME_INFORMEUBICACION_TITULO_REL_ENTREGA = "archigest.archivo.informe.informeUbicacion.relEntrega";
	public static final String LABEL_INFORME_INFORMEUBICACION_TITULO_INGRESO_DIRECTO = "archigest.archivo.informe.informeUbicacion.ingresoDirecto";
	public static final String LABEL_INFORME_INFORMEUBICACION_SIGNATURA = "archigest.archivo.informe.informeUbicacion.signatura";
	public static final String LABEL_INFORME_INFORMEUBICACION_UBICACION = "archigest.archivo.informe.informeUbicacion.ubicacion";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_NUM_CAJAS = "archigest.archivo.transferencias.nUndInstal";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_REL_REENCAJADA = "archigest.archivo.transferencias.relReencajada";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_NUM_CAJAS_REENCAJADAS = "archigest.archivo.transferencias.numCajasReencajadas";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_FECHA_ESTADO = "archigest.archivo.informe.informeRelacion.fechaEstado";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_ASUNTO = "archigest.archivo.transferencias.asunto";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_FECHA_INICIAL = "archigest.archivo.fechaInicial";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_FECHA_FINAL = "archigest.archivo.fechaFinal";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_TITULO = "archigest.archivo.informe.informeDetallesRelacion.titulo";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_FIRMA_REMITENTE = "archigest.archivo.informe.informeDetallesRelacion.firmaRemitente";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_FIRMA_ARCHIVO = "archigest.archivo.informe.informeDetallesRelacion.firmaArchivo";
	public static final String LABEL_INFORME_INFORMEDETALLESRELACION_SIGNATURA = "archigest.archivo.informe.informeDetalleRelacion.signatura";
	public final static String IMPOSIBLE_ENVIAR_XRELACION_TIENE_UDOC_CON_DOC_FISICOS_ASOCIADAS_SIN_CAJA_MESSAGE_KEY = "errors.relaciones.ImposibleEnviarRelacionUdocSinCaja";
	public final static String PROBLEMA_SEGURIDAD_SOBRE_RELACION_MESSAGE_KEY = "errors.relaciones.problemaDeSeguridad";
	public final static String USUARIO_NO_TIENE_PERMISOS_SOBRE_LA_RELACION_MESSAGE_KEY = "errors.relaciones.usuarioSinPermisosSobreLaRelacion";
	public final static String RECEPCION_NO_PERMITIDA_XRELACION_NO_TIENE_ESPACIO_EN_DEPOSITO_MESSAGE_KEY = "errors.relaciones.ImposibleRecibirYReservarRelacionNoTieneEspacioEnDeposito";
	public final static String RECEPCION_NO_PERMITIDA_XRELACION_SIN_UDOC_ASOCIADAS_MESSAGE_KEY = "errors.relaciones.ImposibleRecibirRelacionNoTieneUdocAsociadas";
	public final static String ERROR_SIN_GESTORES_ARCHIVO = "errors.relaciones.sinGestoresArchivo";
	public final static String LABEL_TRANSFERENCIAS_PREVISION_DETALLADA = "archigest.archivo.transferencias.previsiondetallada";
	public final static String LABEL_TRANSFERENCIAS_PREVISION_NO_DETALLADA = "archigest.archivo.transferencias.previsionnodetallada";
	public final static String LABEL_TRANSFERENCIAS_TIPO_TRANS = "archigest.archivo.transferencias.tipotrans";
	public final static String LABEL_TRANSFERENCIAS_ESTADO_PREVISION = "archigest.archivo.transferencias.estadoPrevision.";
	public final static String LABEL_TRANSFERENCIAS_PREVISIONES_BUSQUEDA_FECHA = "archigest.archivo.transferencias.previsiones.busqueda.fecha";
	public final static String LABEL_TRANSFERENCIAS_RELACIONES_BUSQUEDA_FECHA = "archigest.archivo.transferencias.relaciones.busqueda.fecha";
	public final static String LABEL_TRANSFERENCIAS_RELACIONES_BUSQUEDA_FECHA_ESTADO = "archigest.archivo.transferencias.relaciones.busqueda.fecha.estado";
	public final static String LABEL_TRANSFERENCIAS_USER_GESTOR = "archigest.archivo.transferencias.userGestor";
	public final static String LABEL_TRANSFERENCIAS_PREVISIONES_BUSCAR_PREV = "archigest.archivo.transferencias.previsiones.buscarPrev";
	public final static String LABEL_TRANSFERENCIAS_RELACIONES_BUSCAR_REL = "archigest.archivo.transferencias.relaciones.buscarRel";
	public final static String LABEL_ARCHIVO_CUSTODIA = "archigest.archivo.archivoCustodia";
	public final static String LABEL_TRANSFERENCIAS_FORMATO = "archigest.archivo.transferencias.formato";
	public final static String LABEL_TRANSFERENCIAS_SOPORTE_DOCUMENTAL = "archigest.archivo.transferencias.soporteDocumental";
	public final static String LABEL_TRANSFERENCIAS_ORG_PROD = "archigest.archivo.transferencias.orgProd";
	public final static String LABEL_TRANSFERENCIAS_NIVEL_DOCUMENTAL = "archigest.archivo.transferencias.nivelDocumental";

	public static final String LABEL_TRANSFERENCIAS_IMPORT_FILE = "archigest.archivo.transferencias.import.file";

	public final static String ERROR_FONDOS_NO_PERMISOS_INGRESO_DIRECTO = "archigest.archivo.fondos.no.permisos.ingreso.directo";
	public final static String ERROR_NECESARIO_SELECCIONAR_UBICACION = "errors.relaciones.esNecesarioSeleccionarUbicacion";
	public final static String TRANSFERENCIAS_ESTADO_RELACION = "archigest.archivo.transferencias.estadoRelacion";

	public final static String TRANSFERENCIAS_TIPO_EDICION_ADICION_LINEA_DETALLE = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.ADICION_LINEA_DETALLE";
	public final static String TRANSFERENCIAS_TIPO_EDICION_MODIFCACION_LINEA_DETALLE = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.MODIFICACION_LINEA_DETALLE";
	public final static String TRANSFERENCIAS_TIPO_EDICION_ELIMIACION_LINEA_DETALLE = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.ELIMINACION_LINEA_DETALLE";
	public final static String TRANSFERENCIAS_TIPO_EDICION_MODIFICACION_FECHAS_TRANSFERENCIA = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.MODIFICACION_FECHAS_TRANSFERENCIA";
	public final static String TRANSFERENCIAS_TIPO_EDICION_INCORPORACION_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.INCORPORACION_UNIDAD_DOCUMENTAL";
	public final static String TRANSFERENCIAS_TIPO_EDICION_MODIFICACION_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.MODIFICACION_UNIDAD_DOCUMENTAL";
	public final static String TRANSFERENCIAS_TIPO_EDICION_ELIMINACION_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.ELIMINACION_UNIDAD_DOCUMENTAL";
	public final static String TRANSFERENCIAS_TIPO_EDICION_BLOQUEO_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.BLOQUEO_UNIDAD_DOCUMENTAL";
	public final static String TRANSFERENCIAS_TIPO_EDICION_DESBLOQUEO_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.DESBLOQUEO_UNIDAD_DOCUMENTAL";
	public final static String TRANSFERENCIAS_TIPO_EDICION_EDICION_CABECERA = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.EDICION_CABECERA";
	public final static String TRANSFERENCIAS_TIPO_EDICION_MODIFICAR_UBICACION = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.MODIFICAR_UBICACION";

	public final static String TRANSFERENCIAS_TIPO_EDICION_INCORPORACION_UNIDAD_INSTALACION = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.INCORPORACION_UNIDAD_INSTALACION";
	public final static String TRANSFERENCIAS_TIPO_EDICION_ELIMINACION_UNIDAD_INSTALACION = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION.ELIMINACION_UNIDAD_INSTALACION";

	public final static String MODIFICACIONES_EN_TABS_DE_EXPEDIENTE = "MODIFICACIONES_EN_TABS_DE_EXPEDIENTE";
	public final static String LISTADO_TERCEROS_SIZE = "LISTADO_TERCEROS_SIZE";

	public final static String TEXTO_CAJAS = "archigest.archivo.transferencias.seleccionarAlgunasCajas";

	public final static String LABEL_DOCS_ELECTRONICOS = "archigest.archivo.transferencias.documentos.electronicos";
	public final static String LABEL_DOCS_FISICOS = "archigest.archivo.transferencias.documentos.fisicos";

	public final static String ERROR_NUEVA_FECHA_FIN_PREVISION_MENOR_ANTERIOR = "errors.relaciones.prevision.NuevaFechaFinalHaDeSerSuperiorALaActualFinalPrevision";

	public final static String SOPORTA_BUSQUEDA_EXTENDIDA = "SOPORTA_BUSQUEDA_EXTENDIDA";

	public final static String CON_REENCAJADO_KEY = "CON_REENCAJADO_KEY";
	public static final String REENCAJADO_VIEW_OBJECT_KEY = "REENCAJADO_VIEW_OBJECT_KEY";
	public final static String UDOC_UI_SELECCIONADA = getKey("UDOC_UI_SELECCIONADA");
	public final static String NUM_CAJA = getKey("NUM_CAJA");

	/**
	 * Obtiene los tipos de transferencias.
	 * 
	 * @return Tipos de transferencias.
	 */
	public static List getTiposTransferencias(Locale locale) {
		List tipos = new ArrayList();
		tipos.add(new TypeDescVO(1, Messages.getString(
				LABEL_TRANSFERENCIAS_TIPO_TRANS + "1", locale)));
		tipos.add(new TypeDescVO(2, Messages.getString(
				LABEL_TRANSFERENCIAS_TIPO_TRANS + "2", locale)));
		tipos.add(new TypeDescVO(3, Messages.getString(
				LABEL_TRANSFERENCIAS_TIPO_TRANS + "3", locale)));
		if (ConfigConstants.getInstance()
				.getPermitirTransferenciasEntreArchivos()) {
			tipos.add(new TypeDescVO(4, Messages.getString(
					LABEL_TRANSFERENCIAS_TIPO_TRANS + "4", locale)));
		}
		return tipos;
	}

	/**
	 * Obtiene los tipos de transferencias para búsquedas de previsiones según
	 * los permisos del usuario conectado.
	 * 
	 * @param getIds
	 *            Booleano para indicar si se quieren ids
	 * @return Tipos de transferencias para búsquedas de previsiones según los
	 *         permisos del usuario conectado.
	 */
	private static List getTiposTransferenciasBusquedaPrevisionesWithClientPermissions(
			ServiceClient client, boolean getIds) {
		Locale locale = client.getLocale();
		List tipos = new ArrayList();
		// Transferencias ordinarias
		// Permisos necesarios:
		// 100 -> ADMINISTRACION_TOTAL_SISTEMA
		// 101 -> CONSULTA_TOTAL_SISTEMA
		// 200 -> ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS
		// 201 -> AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS
		// 204 -> GESTION_TRANSFERENCIAS_ORGANO_REMITENTE
		// 205 -> GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR
		if (client.hasAnyPermission(new String[] {
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
				AppPermissions.CONSULTA_TOTAL_SISTEMA,
				AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS,
				AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS,
				AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE,
				AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR, })) {
			if (getIds)
				tipos.add(new Integer(TipoTransferencia.ORDINARIA
						.getIdentificador()));
			else
				tipos.add(new TypeDescVO(TipoTransferencia.ORDINARIA
						.getIdentificador(), Messages.getString(
						LABEL_TRANSFERENCIAS_TIPO_TRANS + "1", locale)));
		}

		// Transferencias extraordinarias
		// Permisos necesarios:
		// 100 -> ADMINISTRACION_TOTAL_SISTEMA
		// 101 -> CONSULTA_TOTAL_SISTEMA
		// 202 -> ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS
		// 203 -> AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS
		// 204 -> GESTION_TRANSFERENCIAS_ORGANO_REMITENTE
		// 205 -> GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR
		if (client
				.hasAnyPermission(new String[] {
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
						AppPermissions.CONSULTA_TOTAL_SISTEMA,
						AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS,
						AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS,
						AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE,
						AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR, })) {
			if (getIds) {
				tipos.add(new Integer(
						TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
								.getIdentificador()));
				tipos.add(new Integer(
						TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
								.getIdentificador()));
			} else {
				tipos.add(new TypeDescVO(
						TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
								.getIdentificador(), Messages.getString(
								LABEL_TRANSFERENCIAS_TIPO_TRANS + "2", locale)));
				tipos.add(new TypeDescVO(
						TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
								.getIdentificador(), Messages.getString(
								LABEL_TRANSFERENCIAS_TIPO_TRANS + "3", locale)));
			}
		}
		if (ConfigConstants.getInstance()
				.getPermitirTransferenciasEntreArchivos()) {
			// Transferencias entre archivos
			// Permisos necesarios:
			// 100 -> ADMINISTRACION_TOTAL_SISTEMA
			// 101 -> CONSULTA_TOTAL_SISTEMA
			// 209 -> ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS
			// 204 -> GESTION_TRANSFERENCIAS_ORGANO_REMITENTE
			// 205 -> GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR
			if (client.hasAnyPermission(new String[] {
					AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
					AppPermissions.CONSULTA_TOTAL_SISTEMA,
					AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS,
					AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE,
					AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR, })) {
				if (getIds) {
					tipos.add(new Integer(TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador()));
				} else {
					tipos.add(new TypeDescVO(TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador(), Messages.getString(
							LABEL_TRANSFERENCIAS_TIPO_TRANS + "4", locale)));
				}
			}
		}
		return tipos;
	}

	/**
	 * Obtiene los tipos de transferencias para búsquedas de previsiones según
	 * los permisos del usuario conectado.
	 * 
	 * @param getIds
	 *            Booleano para indicar si se quieren ids
	 * @return Tipos de transferencias para búsquedas de previsiones según los
	 *         permisos del usuario conectado.
	 */
	public static List getTiposTransferenciasBusquedaPrevisionesWithClientPermissions(
			ServiceClient client) {
		return getTiposTransferenciasBusquedaPrevisionesWithClientPermissions(
				client, false);
	}

	/**
	 * Obtiene los tipos de transferencias para búsquedas de previsiones según
	 * los permisos del usuario conectado.
	 * 
	 * @param getIds
	 *            Booleano para indicar si se quieren ids
	 * @return Tipos de transferencias para búsquedas de previsiones según los
	 *         permisos del usuario conectado.
	 */
	public static int[] getIdsTiposTransferenciasBusquedaPrevisionesWithClientPermissions(
			ServiceClient client) {
		List tipos = getTiposTransferenciasBusquedaPrevisionesWithClientPermissions(
				client, true);
		int[] tiposInt = new int[tipos.size()];
		int i = 0;
		ListIterator it = tipos.listIterator();
		while (it.hasNext()) {
			Integer tipo = (Integer) it.next();
			tiposInt[i] = tipo.intValue();
			i++;
		}
		return tiposInt;

	}

	/**
	 * Obtiene los tipos de transferencias para búsquedas de relaciones según
	 * los permisos del usuario conectado.
	 * 
	 * @param getIds
	 *            Booleano para indicar si se quieren ids
	 * @return Tipos de transferencias para búsquedas de relaciones según los
	 *         permisos del usuario conectado.
	 */
	private static List getTiposTransferenciasBusquedaRelacionesWithClientPermissions(
			ServiceClient client, boolean getIds) {
		Locale locale = client.getLocale();
		List tipos = new ArrayList();
		// Transferencias ordinarias
		// Permisos necesarios:
		// 100 -> ADMINISTRACION_TOTAL_SISTEMA
		// 101 -> CONSULTA_TOTAL_SISTEMA
		// 200 -> ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS
		// 201 -> AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS
		// 204 -> GESTION_TRANSFERENCIAS_ORGANO_REMITENTE
		// 205 -> GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR
		// 206 -> GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR
		// 207 -> UBICACION_RELACIONES_ENTREGA
		// 208 -> ASIGNACION_RESERVA_ESPACIO_DEPOSITO
		if (client.hasAnyPermission(new String[] {
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
				AppPermissions.CONSULTA_TOTAL_SISTEMA,
				AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS,
				AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS,
				AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE,
				AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR,
				AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR,
				AppPermissions.UBICACION_RELACIONES_ENTREGA,
				AppPermissions.ASIGNACION_RESERVA_ESPACIO_DEPOSITO })) {
			if (getIds)
				tipos.add(new Integer(TipoTransferencia.ORDINARIA
						.getIdentificador()));
			else
				tipos.add(new TypeDescVO(TipoTransferencia.ORDINARIA
						.getIdentificador(), Messages.getString(
						LABEL_TRANSFERENCIAS_TIPO_TRANS + "1", locale)));
		}

		// Transferencias extraordinarias
		// Permisos necesarios:
		// 100 -> ADMINISTRACION_TOTAL_SISTEMA
		// 101 -> CONSULTA_TOTAL_SISTEMA
		// 202 -> ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS
		// 203 -> AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS
		// 204 -> GESTION_TRANSFERENCIAS_ORGANO_REMITENTE
		// 205 -> GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR
		// 206 -> GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR
		// 207 -> UBICACION_RELACIONES_ENTREGA
		// 208 -> ASIGNACION_RESERVA_ESPACIO_DEPOSITO
		if (client
				.hasAnyPermission(new String[] {
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
						AppPermissions.CONSULTA_TOTAL_SISTEMA,
						AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS,
						AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS,
						AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE,
						AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR,
						AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR,
						AppPermissions.UBICACION_RELACIONES_ENTREGA,
						AppPermissions.ASIGNACION_RESERVA_ESPACIO_DEPOSITO })) {
			if (getIds) {
				tipos.add(new Integer(
						TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
								.getIdentificador()));
				tipos.add(new Integer(
						TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
								.getIdentificador()));
			} else {
				tipos.add(new TypeDescVO(
						TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
								.getIdentificador(), Messages.getString(
								LABEL_TRANSFERENCIAS_TIPO_TRANS + "2", locale)));
				tipos.add(new TypeDescVO(
						TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
								.getIdentificador(), Messages.getString(
								LABEL_TRANSFERENCIAS_TIPO_TRANS + "3", locale)));
			}
		}
		if (ConfigConstants.getInstance()
				.getPermitirTransferenciasEntreArchivos()) {
			// Transferencias entre archivos
			// Permisos necesarios:
			// 100 -> ADMINISTRACION_TOTAL_SISTEMA
			// 101 -> CONSULTA_TOTAL_SISTEMA
			// 209 -> ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS
			// 204 -> GESTION_TRANSFERENCIAS_ORGANO_REMITENTE
			// 205 -> GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR
			// 206 -> GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR
			// 207 -> UBICACION_RELACIONES_ENTREGA
			// 208 -> ASIGNACION_RESERVA_ESPACIO_DEPOSITO
			if (client.hasAnyPermission(new String[] {
					AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
					AppPermissions.CONSULTA_TOTAL_SISTEMA,
					AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS,
					AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE,
					AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR,
					AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR,
					AppPermissions.UBICACION_RELACIONES_ENTREGA,
					AppPermissions.ASIGNACION_RESERVA_ESPACIO_DEPOSITO })) {
				if (getIds) {
					tipos.add(new Integer(TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador()));
				} else {
					tipos.add(new TypeDescVO(TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador(), Messages.getString(
							LABEL_TRANSFERENCIAS_TIPO_TRANS + "4", locale)));
				}
			}
		}
		return tipos;
	}

	/**
	 * Obtiene los tipos de transferencias para búsquedas de relaciones según
	 * los permisos del usuario conectado.
	 * 
	 * @param getIds
	 *            Booleano para indicar si se quieren ids
	 * @return Tipos de transferencias para búsquedas de relaciones según los
	 *         permisos del usuario conectado.
	 */
	public static List getTiposTransferenciasBusquedaRelacionesWithClientPermissions(
			ServiceClient client) {
		return getTiposTransferenciasBusquedaRelacionesWithClientPermissions(
				client, false);
	}

	/**
	 * Obtiene los tipos de transferencias para búsquedas de relaciones según
	 * los permisos del usuario conectado.
	 * 
	 * @param getIds
	 *            Booleano para indicar si se quieren ids
	 * @return Tipos de transferencias para búsquedas de relaciones según los
	 *         permisos del usuario conectado.
	 */
	public static int[] getIdsTiposTransferenciasBusquedaRelacionesWithClientPermissions(
			ServiceClient client) {
		List tipos = getTiposTransferenciasBusquedaRelacionesWithClientPermissions(
				client, true);
		int[] tiposInt = new int[tipos.size()];
		int i = 0;
		ListIterator it = tipos.listIterator();
		while (it.hasNext()) {
			Integer tipo = (Integer) it.next();
			tiposInt[i] = tipo.intValue();
			i++;
		}
		return tiposInt;
	}
}
