package fondos;

import java.util.Map;

import se.usuarios.AppPermissions;

import common.SigiaUtilConstants;

import fondos.model.EstadoDivisionFS;
import fondos.model.EstadoSolicitudSerie;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.SubtipoNivelCF;
import fondos.model.TipoBusqueda;
import fondos.model.TipoEntidad;
import fondos.model.TipoNivelCF;

/** Constantes utilizadas en la capa de presentación del módulo */
public class FondosConstants {

	private static final StringBuffer buff = new StringBuffer();

	private final static String getKey(String innerKey) {
		buff.setLength(0);
		return buff.append("FondosConstants_").append(innerKey).toString();
	}

	public static final String SIST_GESTOR_ORGARCHIGEST = "ORGARCHIGEST";
	public static final String CUADRO_CLF_VIEW_NAME = "cuadroClasificacion";
	public static final String CUADRO_CLF_SEL_VIEW_NAME = "cuadroClasificacionSeleccion";
	public static final String ELEMENTO_CF_KEY = getKey("ELEMENTO_CF");
	public static final String NIVEL_CF_KEY = getKey("NIVEL_CF_KEY");
	public static final String NIVEL_CF_PADRE_KEY = getKey("NIVEL_CF_PADRE_KEY");
	public static final String LISTA_SUBNIVELES_KEY = getKey("LISTA_SUBNIVELES_KEY");
	public static final String LISTA_PAISES_KEY = getKey("LISTA_PAISES_KEY");
	public static final String LISTA_COMUNIDADES_KEY = getKey("LISTA_COMUNIDADES_KEY");
	public static final String LISTA_INSTITUCIONES_KEY = getKey("LISTA_INSTITUCIONES_KEY");
	public static final String LISTA_ENTIDADES = getKey("LISTA_ENTIDADES");
	public static final String FONDO_KEY = getKey("FONDO_KEY");
	public static final String FONDO_ORIGEN_KEY = getKey("FONDO_ORIGEN_KEY");
	public static final String FONDO_DESTINO_KEY = getKey("FONDO_DESTINO_KEY");
	public static final String CLASIFICADOR_ORIGEN_KEY = getKey("CLASIFICADOR_ORIGEN_KEY");
	public static final String CLASIFICADOR_DESTINO_KEY = getKey("CLASIFICADOR_DESTINO_KEY");
	public static final String MOVIMIENTO_FINALIZADO_KEY = getKey("MOVIMIENTO_FINALIZADO_KEY");
	public static final String NOMBRE_LISTA_ACCESO_KEY = getKey("NOMBRE_LISTA_ACCESO_KEY");
	public static final String LISTA_ELEMENTOS_CF = getKey("LISTA_ELEMENTOS_CF");
	public static final String NUM_RESULTADOS_LISTA_ELEMENTOS_CF = getKey("NUM_RESULTADOS_LISTA_ELEMENTOS_CF");
	public static final String LISTA_IDS_ELEMENTOS_CF = getKey("LISTA_IDS_ELEMENTOS_CF");
	public static final String MAP_ELEMENTOS_CF_VISITADOS = getKey("MAP_ELEMENTOS_CF_VISITADOS");
	public static final String LISTA_UDOCS_AMOVER = getKey("LISTA_UDOCS_AMOVER");
	public static final String LISTA_CACHEADA_KEY = getKey("LISTA_CACHEADA_KEY");
	public static final String LISTA_FONDOS_KEY = getKey("LISTA_FONDOS_KEY");
	// public static final String N_FONDOS_KEY = getKey("N_FONDOS_KEY");
	public static final String ENT_PRODUCTORA_KEY = getKey("ENT_PRODUCTORA_KEY");
	public static final String CODIGO_ENT_PRODUCTORA_MODIFICABLE = getKey("CODIGO_ENT_PRODUCTORA_MODIFICABLE");
	public static final String CFVIEW_PARA_SELECCION_SERIE = "CFVIEW_PARA_SELECCION_SERIE";
	public static final String NODO_SELECCIONADO_KEY = getKey("NODO_SELECCIONADO");
	public static final String CHILDS_ELEMENTO_CF_KEY = getKey("CHILDS_ELEMENTO_CF_KEY");
	public static final String CHILD_TYPES_ELEMENTO_CF_KEY = getKey("CHILD_TYPES_ELEMENTO_CF_KEY");
	public static final String SERIE_KEY = getKey("SERIE_KEY");
	public static final String IDENTIFICACION_SERIE_KEY = getKey("IDENTIFICACION_SERIE_KEY");
	public static final String LISTA_UNIDADES_DOCUMENTALES = getKey("LISTA_UNIDADES_DOCUMENTALES");
	public static final String LISTA_UNIDADES_DOCUMENTALES_INFORME_SERIE = getKey("LISTA_UNIDADES_DOCUMENTALES_INFORME_SERIE");
	public static final String LISTA_PROCEDIMIENTOS_KEY = "LISTA_PROCEDIMIENTOS_KEY";
	public static final String LISTA_PROCEDIMIENTOS_ANADIR_KEY = "LISTA_PROCEDIMIENTOS_ANADIR_KEY";
	public static final String LISTA_ESTADOS_KEY = getKey("LISTA_ESTADOS_KEY");
	public static final String LISTA_NIVELES_KEY = getKey("LISTA_NIVELES_KEY");
	public static final String LISTA_NIVELES_TIPO_SERIE_KEY = getKey("LISTA_NIVELES_TIPO_SERIE_KEY");
	public static final String LISTA_NIVELES_TIPO_UNIDAD_DOCUMENTAL = getKey("LISTA_NIVELES_TIPO_UNIDAD_DOCUMENTAL");
	public static final String REFRESH_VIEW_KEY = getKey("REFRESH_VIEW_KEY");
	public static final String LISTA_SOLICITUDES_PENDIENTES_APROBACION_KEY = getKey("LISTA_SOLICITUDES_PENDIENTES_APROBACION_KEY");
	public static final String LISTA_SOLICITUDES_A_GESTIONAR_KEY = getKey("LISTA_SOLICITUDES_A_GESTIONAR_KEY");
	public static final String LISTA_SOLICITUDES_KEY = getKey("LISTA_SOLICITUDES_KEY");
	public static final String DETALLE_SOLICITUD_KEY = getKey("DETALLE_SOLICITUD_KEY");
	public static final String LISTA_GESTORES_SERIE_KEY = getKey("LISTA_GESTORES_SERIE_KEY");
	public static final String SERIES_EN_IDENTIFICACION = getKey("SERIES_EN_IDENTIFICACION");
	// public static final String LISTA_ORGANOS_KEY =
	// getKey("LISTA_ORGANISMOS_KEY");
	public static final String LISTA_POSIBLES_PRODUCTORES_KEY = getKey("LISTA_POSIBLES_PRODUCTORES_KEY");
	public static final String LISTA_PRODUCTORES_KEY = getKey("LISTA_PRODUCTORES_KEY");
	public static final String MOSTRAR_SIN_PRODUCTOR = getKey("MOSTRAR_SIN_PRODUCTOR");

	public static final String LISTA_PRODUCTORES_SERIE_VO_KEY = getKey("LISTA_PRODUCTORES_SERIE_VO_KEY");
	public static final String LISTA_INFO_PRODUCTORES_SERIE_KEY = getKey("LISTA_INFO_PRODUCTORES_SERIE_KEY");

	public static final String TABLA_TEMPORAL = getKey("TABLA_TEMPORAL_KEY");

	public static final String ADD_PRODUCTOR_HISTORICO = getKey("ADD_PRODUCTOR_HISTORICO");

	public static final String LISTA_ARCHIVOS_KEY = getKey("LISTA_ARCHIVOS_KEY");
	public static final String GESTOR_SERIES_KEY = getKey("GESTOR_SERIES_KEY");
	public static final String TIPOS_CLASIFICADOR_SERIES = getKey("TIPOS_CLASIFICADOR_SERIES_KEY");
	public static final String TIPOS_CLASIFICADOR_FONDOS = getKey("TIPOS_CLASIFICADOR_FONDOS_KEY");
	public static final String PADRE_ELEMENTO_CF = getKey("PADRE_ELEMENTO_CF");
	public static final String JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION = getKey("JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION");
	public static final String UDOC_KEY = getKey("UDOC_KEY");
	public static final String LISTA_FICHAS_PARA_UDOCS_KEY = getKey("LISTA_FICHAS_PARA_UDOCS_KEY");
	public static final String LISTA_FICHAS_PARA_SERIES_KEY = getKey("LISTA_FICHAS_PARA_SERIES_KEY");
	public static final String NIVEL_ELEMENTO_CF = getKey("NIVEL_ELEMENTO_CF");
	public static final String NIVEL_SERIE_KEY = getKey("NIVEL_SERIE_KEY");
	public static final String NIVEL_UDOC_KEY = getKey("NIVEL_UDOC_KEY");
	public static final String REPOSITORIOS_ECM_KEY = getKey("REPOSITORIOS_ECM_KEY");
	public static final String LISTA_FICHAS_CL_DOCUMENTALES_KEY = getKey("LISTA_FICHAS_CL_DOCUMENTALES_KEY");
	public static final String LISTA_FICHAS_KEY = getKey("LISTA_FICHAS_KEY");
	public static final String LISTA_CAMPOS_FICHA_KEY = getKey("LISTA_CAMPOS_FICHA_KEY");
	public static final String LISTA_CL_DOCUMENTALES_KEY = getKey("LISTA_CL_DOCUMENTALES_KEY");
	public static final String LISTA_SOPORTES_KEY = getKey("LISTA_SOPORTES_KEY");
	public static final String LISTA_ELEMENTOS_BANDEJA_KEY = getKey("LISTA_ELEMENTOS_BANDEJA_KEY");
	public static final String LISTAS_CONTROL_ACCESO_KEY = getKey("LISTAS_CONTROL_ACCESO_KEY");
	public static final String LISTAS_CONTROL_ACCESO_PRODUCTORES_KEY = getKey("LISTAS_CONTROL_ACCESO_PRODUCTORES_KEY");
	public static final String LISTA_SERIES_DESTINO = getKey("LISTA_SERIES_DESTINO");
	public static final String PRODUCTORES_VIGENTES_KEY = getKey("PRODUCTORES_VIGENTES_KEY");
	public static final String PRODUCTOR_A_HISTORICO_KEY = getKey("PRODUCTOR_A_HISTORICO_KEY");
	public static final String PRODUCTORES_HISTORICOS_KEY = getKey("PRODUCTORES_HISTORICOS_KEY");
	public static final String PRODUCTORES_HISTORICOS_ELIMINADOS_KEY = getKey("PRODUCTORES_HISTORICOS_ELIMINADOS_KEY");
	public static final String PRODUCTORES_ORIGINALES_VIGENTES_KEY = getKey("PRODUCTORES_ORIGINALES_VIGENTES_KEY");
	public static final String PRODUCTORES_ORIGINALES_HISTORICOS_KEY = getKey("PRODUCTORES_ORIGINALES_HISTORICOS_KEY");
	public static final String PRODUCTORES_ORIGINALES_SERIE_KEY = getKey("PRODUCTORES_ORIGINALES_SERIE_KEY");
	public static final String NUEVOS_PRODUCTORES_ANTES_KEY = getKey("NUEVOS_PRODUCTORES_ANTES_KEY");
	public static final String ANTIGUOS_PRODUCTORES_VIGENTES_ANTES_KEY = getKey("ANTIGUOS_PRODUCTORES_VIGENTES_ANTES_KEY");
	public static final String NUEVOS_PRODUCTORES_DESPUES_KEY = getKey("NUEVOS_PRODUCTORES_DESPUES_KEY");
	public static final String ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_KEY = getKey("ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_KEY");
	public static final String ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_PRIMER_NODO_KEY = getKey("ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_PRIMER_NODO_KEY");
	public static final String SOLICITUDES_KEY = getKey("SOLICITUDES_KEY");
	public static final String CFG_BUSQUEDA_KEY = getKey("CFG_BUSQUEDA_KEY");
	public static final String CFG_BUSQUEDA_SERIE_KEY = getKey("CFG_BUSQUEDA_SERIE_KEY");
	public static final String PRECONDICIONES_BUSQUEDA_KEY = getKey("PRECONDICIONES_BUSQUEDA_KEY");
	public static final String ELEMENTOS_SELECCIONADOS_BUSQUEDA_KEY = getKey("ELEMENTOS_SELECCIONADOS_BUSQUEDA_KEY");
	public static final String CFG_BUSQUEDAS_KEY = getKey("CFG_BUSQUEDAS_KEY");
	public static final String PRODUCTOR_SUSTITUIDOR_KEY = getKey("PRODUCTOR_SUSTITUIDOR_KEY");
	public static final String PRODUCTOR_SUSTITUIDO_KEY = getKey("PRODUCTOR_SUSTITUIDO_KEY");
	public static final String SUSTITUCION_VIGENTE_HISTORICO_KEY = getKey("SUSTITUCION_VIGENTE_HISTORICO_KEY");
	public static final String LISTAS_ACCESO = getKey("LISTAS_ACCESO");
	public static final String LISTA_INFO_PROD_VIGENTES_HISTORICOS = getKey("LISTA_INFO_PROD_VIGENTES_HISTORICOS");
	public static final String PRODUCTORES_ORIGINALES_VIGENTES_A_ELEGIR_KEY = getKey("PRODUCTORES_ORIGINALES_VIGENTES_A_ELEGIR_KEY");
	public static final String PRODUCTORES_A_DAR_DE_ALTA_KEY = getKey("PRODUCTORES_A_DAR_DE_ALTA_KEY");
	public static final String PRODUCTORES_PASADOS_A_HISTORICOS_KEY = getKey("PRODUCTORES_PASADOS_A_HISTORICOS_KEY");
	public static final String DESDE_METODO_KEY = getKey("DESDE_METODO_KEY");
	public static final String LISTA_TIPOS_DOCUMENTACION_KEY = getKey("LISTA_TIPOS_DOCUMENTACION_KEY");
	public static final String LISTA_NIVELES_DOCUMENTALES_KEY = getKey("LISTA_NIVELES_DOCUMENTALES_KEY");
	public static final String INFO_UDOCS_SERIE = getKey("INFO_UDOCS_SERIE");
	public static final String NIVELES_FICHA_UDOC_REP_ECM = getKey("NIVELES_FICHA_UDOC_REP_ECM");
	public static final String BANDERA_MOSTRAR_RANGOS = getKey("BANDERA_MOSTRAR_RANGOS");
	public static final String LISTA_RANGOS_UDOC = getKey("LISTA_RANGOS_UDOC");
	public static final String PERMITIR_VER_SIGNATURAS = getKey("PERMITIR_VER_SIGNATURAS");
	public static final String ACCION_ELEMENTOS_KEY = getKey("ACCION_ELEMENTOS_KEY");
	public static final String LISTA_TIPOS_NIVEL_KEY = getKey("LISTA_TIPOS_NIVEL_KEY");
	public static final String PUEDE_SER_NIVEL_INICIAL = getKey("PUEDE_SER_NIVEL_INICIAL");
	public static final String MOSTRAR_SUBTIPOS = getKey("MOSTRAR_SUBTIPOS");
	public static final String LISTA_SUBTIPOS_NIVEL = getKey("LISTA_SUBTIPOS_NIVEL");
	public static final String LISTA_NIVELES_HIJO = getKey("LISTA_NIVELES_HIJO");
	public static final String TIPO_NIVEL_CUADRO_EDITABLE = getKey("TIPO_NIVEL_CUADRO_EDITABLE");
	public static final String LISTA_POSIBLES_NIVELES_HIJO = getKey("LISTA_POSIBLES_NIVELES_HIJO");
	public static final String FICHA_DESCRIPCION_NIVEL = getKey("FICHA_DESCRIPCION_NIVEL");
	public static final String REPOSITORIO_ECM_NIVEL = getKey("REPOSITORIO_ECM_NIVEL");
	public static final String NIVEL_CUADRO_ACTION = getKey("NIVEL_CUADRO_ACTION");
	public static final String PUEDE_TENER_HIJOS = getKey("PUEDE_TENER_HIJOS");
	public static final String IS_PRESTAMO = getKey("IS_PRESTAMO");

	public static Map tiposNivel = SigiaUtilConstants
			.getConstantsMap(TipoNivelCF.class);
	public static Map tiposEntidad = SigiaUtilConstants
			.getConstantsMap(TipoEntidad.class);
	public static Map estadoElementoCF = SigiaUtilConstants
			.getConstantsMap(IElementoCuadroClasificacion.class);
	public static Map estadosSolicitudesSerie = SigiaUtilConstants
			.getConstantsMap(EstadoSolicitudSerie.class);
	public static Map tiposBusquedas = SigiaUtilConstants
			.getConstantsMap(TipoBusqueda.class);
	public static Map camposBusquedas = SigiaUtilConstants
			.getConstantsMap(CamposBusquedasConstants.class);
	public static Map subtiposNivel = SigiaUtilConstants
			.getConstantsMap(SubtipoNivelCF.class);
	public static Map estadosDivisionFS = SigiaUtilConstants
			.getConstantsMap(EstadoDivisionFS.class);

	// Etiquetas
	public final static String LABEL_FONDOS_INGRESO_BUSCAR_INGRESO = "archigest.archivo.fondos.ingreso.directo.buscarIngreso";

	public static final int MARCA_VACIO = 0;
	/**
	 * Productor creado anteriormente al estado actual
	 */
	public static final int MARCA_ANTERIOR_ESTADO_ACTUAL = 1;

	/**
	 * Productor creado en el estado actual pero no guardado en base de datos
	 * 
	 */
	public static final int MARCA_CREADO_EN_ESTADO_ACTUAL_SIN_GUARDAR = 2;

	/**
	 * Productor creado en el estado actual y guardado en base de datos
	 */
	public static final int MARCA_CREADO_EN_ESTADO_ACTUAL_GUARDADO = 4;

	/**
	 * Productor pasado a histórico en el estado actual pero no guardado
	 */
	public static final int MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR = 8;

	/**
	 * Productor pasado a histórico en el estado actual y guardado en base de
	 * datos.
	 */
	public static final int MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO = 16;

	/**
	 * Productor creado como histórico en el estado actual pero no guardado
	 */
	public static final int MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR = 32;

	/**
	 * Productor creado como histórico en el estado actual y guardado en base de
	 * datos.
	 */
	public static final int MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO = 64;

	/**
	 * Productor pasado a vigente en estado actual sin guardar
	 */
	public static final int MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR = 128;

	/**
	 * Productor pasado a vigente en estado actual guardado
	 */
	public static final int MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO = 256;

	/**
	 * Productor sustituido por uno vigente, sin guardar
	 */
	public static final int MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR = 512;

	/**
	 * Productor sustituido por uno vigente, guardado
	 */
	public static final int MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO = 1024;

	/**
	 * Productor Eliminado
	 */
	public static final int MARCA_ELIMINADO = 2048;

	public static final String UDOCS_A_MOVER = getKey("UDOCS_A_MOVER");
	public static final String SERIE_DESTINO = getKey("SERIE_DESTINO");
	public static final String UDOC_A_DIVIDIR = getKey("UDOC_A_DIVIDIR");
	public static final String UDOCS_FRACCION_SERIE = getKey("UDOCS_FRACCION_SERIE");
	public static final String DIVISION_FRACCION_SERIE = getKey("DIVISION_FRACCION_SERIE");
	public static final String UNIDAD_DOCUMENTAL_EN_FS = getKey("UNIDAD_DOCUMENTAL_EN_FS");
	public static final String BANDERA_CREACION_EDICION = getKey("BANDERA_CREACION_EDICION");
	public static final String VALOR_BANDERA_CREACION = "C";
	public static final String VALOR_BANDERA_EDICION = "M";
	public static final String LISTA_DIVISIONESFS_KEY = getKey("LISTA_DIVISIONESFS_KEY");
	public static final String SON_DIVISIONESFS_FINALIZADAS = getKey("SON_DIVISIONESFS_FINALIZADAS");
	public final static String FLAG_CREACION_UDOC_FS_MANTENER_INFORMACION = "creacionUdocFSMantenerInformacion";

	public static final String FONDOS_ESTADO = "archigest.archivo.cf.estado";
	public static final String FONDOS_BLOQUEO = "archigest.archivo.bloqueo.unidades.documentales";
	public static final String UDOCS_ARCHIVO_CF_ESTADO_SOLICITUD = "archigest.archivo.cf.estadoSolicitud";
	public static final String LABEL_CF_BUSQUEDA_FECHA = "archigest.archivo.cf.busqueda.fecha";

	public static final String NECESARIO_SELECCIONAR_UNA_SERIE = "errors.fondos.seleccioneAlMenosUnaSerieParaCambiarSuGestor";
	public static final String INTRODUZCA_ALGUN_CRITERIO_DE_BUSQUEDA = "errors.fondos.introduzcaAlgunCriterioDeBusquedaParaLaBusquedaDeSeries";
	public static final String NECESARIO_SELECCIONAR_PADRE_MESSAGE_KEY = "archigest.archivo.cf.necesarioSeleccionarUnaNuevaUbicacion";
	public static final String ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO = "archigest.archivo.transferencias.productorVigenteNoSeleccionado";
	public static final String ERROR_PRODUCTOR_HISTORICO_NO_SELECCIONADO = "archigest.archivo.transferencias.productorHistoricoNoSeleccionado";
	public static final String ERROR_ORGANO_PRODUCTOR_NO_SELECCIONADO = "archigest.archivo.transferencias.organoProductorNoSeleccionado";
	public static final String ERROR_FONDOS_SERIES_ELIMINAR_CON_UDOCS = "errors.fondos.series.eliminacion.conUDocs";
	public static final String ERROR_TABLA_VALIDACION_TIPO_DOCUMENTACION = "errors.configuracion.sin.tabla.validacion.tipo.documentacion";
	public static final String ERROR_VALIDACION_UDOCS_SIN_PERMISOS = "archigest.archivo.error.validacionUdocs.sinPermisos";
	public static final String ERROR_ORGANO_SIN_CODIGO = "archigest.archivo.series.organo.sin.codigo";
	public static final String ERROR_UDOC_EN_ARCHIVO_SIN_PERMISOS = "archigest.archivo.error.fondos.udoc.archivo.sinpermisos";
	public static final String ERROR_ACCESO_ELEMENTO_SIN_PERMISOS = "archigest.archivo.error.fondos.elemento.sinPermisos";
	public static final String ERROR_ACCESO_PADRES_ELEMENTO_SIN_PERMISOS = "archigest.archivo.error.fondos.padreElemento.sinPermisos";
	public static final String ERROR_PRODUCTOR_VIGENTE_DEBE_SER_NUEVO = "archigest.archivo.series.productor.debe.ser.nuevo";
	public static final String ERROR_PRODUCTOR_VIGENTE_DEBE_SER_EXISTENTE = "archigest.archivo.series.productor.debe.ser.existente";
	public static final String ERROR_PRODUCTOR_NO_ELIMINABLE_TIENE_UDOCS = "archigest.archivo.series.productor.con.udocs";
	public static final String ERROR_PRODUCTOR_HISTORICO_NO_ELIMINABLE = "archigest.archivo.series.productor.historico.no.eliminable";
	public static final String ERROR_PRODUCTOR_HISTORICO_NO_PASAR_VIGENTE = "archigest.archivo.series.productor.historico.no.pasar.vigente";
	public static final String ERROR_PRODUCTOR_YA_ESTA_SUSTITUYENDO_A_OTRO = "archigest.archivo.series.productor.vigente.ya.esta.sustituyendo.a.otro";

	public final static String MSG_RESOURCES_TIPO_ENTIDAD = "archigest.archivo.cf.productorTipoEntidad";
	public final static String MSG_RESOURCES_TIPO_ORGANISMO = "archigest.archivo.cf.productorTipoOrganismo";
	public final static String MSG_INTERESADOS_PRINCIPALES_MAYOR_QUE = "archigest.archivo.interesadosPrincipales.mayorQue";
	public final static String FONDOS_ESTADO_DIVISIONFS = "archigest.archivo.fondos.estadoDivisionFS";

	public static final String LABEL_INFORMES_MOVER_FONDOS_TITULO = "archigest.archivo.informe.movimiento.fondos";
	public static final String LABEL_INFORMES_MOVER_CLASIFICADOR_TITULO = "archigest.archivo.informe.movimiento.clasificador";
	public static final String LABEL_INFORMES_MOVER_UDOCS_ENTRE_SERIES = "archigest.archivo.informe.movimiento.udocs.entre.series";
	public static final String LABEL_INFORMES_MOVER_SERIE_TITULO = "archigest.archivo.informe.movimiento.serie";
	public static final String LABEL_INFORME_UDOCS_SERIE_TITULO = "archigest.archivo.udocs.serie.titulo";

	public static final String VO_BUSQUEDA_FONDOS = "VO_BUSQUEDA_FONDOS";
	public static final String INFORME_BUSQUEDA_URI_RETORNO = "INFORME_BUSQUEDA_URI_RETORNO";
	public static final String SHOW_PRODUCTOR = "SHOW_PRODUCTOR";
	public static final String ESTADO_NO_VIGENTE = "1";
	public static final String ESTADO_VIGENTE = "2";

	public static final String[] PERMISOS_CONSULTA_ELEMENTO_CUADRO = new String[] {
			AppPermissions.CONSULTA_CUADRO_CLASIFICACION,
			AppPermissions.MODIFICACION_CUADRO_CLASIFICACION };
	public static final String[] PERMISOS_EDICION_ELEMENTO_CUADRO = new String[] { AppPermissions.MODIFICACION_CUADRO_CLASIFICACION };
	public static final String[] PERMISOS_CONSULTA_DESCRIPCION_ELEMENTO_CUADRO = new String[] {
			AppPermissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION,
			AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION };
	public static final String[] PERMISOS_EDICION_DESCRIPCION_ELEMENTO_CUADRO = new String[] { AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION };
	public static final String[] PERMISOS_CONSULTA_DOCUMENTOS_ELEMENTO_CUADRO = new String[] {
			AppPermissions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION,
			AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION };
	public static final String[] PERMISOS_EDICION_DOCUMENTOS_ELEMENTO_CUADRO = new String[] { AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION };

	public static final String LISTA_FORMAS_REEMPLAZO_SIMPLE_KEY = "LISTA_TIPOS_REEMPLAZO_SIMPLE_KEY";

	public static final String ESTADO_PRODUCTOR_VIGENTE = "archigest.archivo.estado.productor.vigente";
	public static final String ESTADO_PRODUCTOR_HISTORICO = "archigest.archivo.estado.productor.historico";

	public static final String UDOC_BLOQUEADA = "1";
	public static final String UDOC_DESBLOQUEADA = "0";

	public static final String UDOC_CONSERVADA = "1";
	public static final String UDOC_NO_CONSERVADA = "0";

	public static final String TODOS_PRODUCTORES = "0";
	public static final String SIN_PRODUCTOR = "1";

	public static String getTextoMarcas(int marcas) {

		String retorno = marcas + " -";

		switch (marcas) {

		case FondosConstants.MARCA_VACIO:
			return retorno + "";

		case FondosConstants.MARCA_ANTERIOR_ESTADO_ACTUAL:
			return retorno + "Creado anteriormente al estado actual";
		case FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_SIN_GUARDAR:
			return retorno
					+ "Creado en el estado actual pero no guardado en base de datos";

		case FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_GUARDADO:
			return retorno
					+ "Creado en el estado actual y guardado en base de datos";

		case FondosConstants.MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR:
			return retorno
					+ "Pasado a histórico en el estado actual pero no guardado";

		case FondosConstants.MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO:
			return retorno
					+ "Pasado a histórico en el estado actual y guardado en base de datos.";

		case FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR:
			return retorno
					+ "Creado como histórico en el estado actual pero no guardado";

		case FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO:
			return retorno
					+ "Creado como histórico en el estado actual y guardado en base de datos.";

		case FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR:
			return retorno + "Pasado a Vigente en Estado Actual Sin Guardar";

		case FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO:
			return retorno + "Pasado a Vigente En Estado Actual Guardado";

		case FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR:
			return retorno
					+ "Sustuido por Vigente en Estado Actual Sin Guardar";

		case FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO:
			return retorno + "Sustuido por Vigente en Estado Actual Guardado";

		case FondosConstants.MARCA_ELIMINADO:
			return retorno + "Eliminado";

		default:
			return new Integer(marcas).toString();
		}

	}
}