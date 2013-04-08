package solicitudes.consultas;

import java.util.Map;

import common.SigiaUtilConstants;

import fondos.CamposBusquedasConstants;

/**
 * Clase que encapsula las ctes para el modulo de consultas
 */
public final class ConsultasConstants {

	private static final StringBuffer buff = new StringBuffer();

	private final static String getKey(String innerKey){
		      buff.setLength(0);
		      return buff.append("ConsultasConstants_").append(innerKey).toString();
	 }

	/** Tipo de la consulta*/
	public final static int TIPO_CONSULTA_DIRECTA = 1;
	public final static int TIPO_CONSULTA_INDIRECTA = 2;

	/** Tipo de entidad consultora*/
	public final static String TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR = "1";
	public final static String TIPO_ENTIDAD_CONSULTORA_CIUDADANO = "2";
	public final static String TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO = "3";

	public final static int TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT = 1;
	public final static int TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT = 2;
	public final static int TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO_INT = 3;

	/** Estados de la consulta*/
    public final static int ESTADO_CONSULTA_ABIERTA = 1;
	public final static int ESTADO_CONSULTA_SOLICITADA = 2;
	public final static int ESTADO_CONSULTA_RESERVADA = 3;
	public final static int ESTADO_CONSULTA_AUTORIZADA = 4;
	public final static int ESTADO_CONSULTA_DENEGADA = 5;
	public final static int ESTADO_CONSULTA_ENTREGADA = 6;
	public final static int ESTADO_CONSULTA_DEVUELTA_INCOMPLETA = 7;
	public final static int ESTADO_CONSULTA_DEVUELTA = 8;

	/** Estados de la solicitud */
	public final static int ESTADO_SOLICITUD_PENDIENTE = 1;
	public final static int ESTADO_SOLICITUD_RESERVADA = 2;
	public final static int ESTADO_SOLICITUD_AUTORIZADA = 3;
	public final static int ESTADO_SOLICITUD_DENEGADA = 4;
	public final static int ESTADO_SOLICITUD_ENTREGADA = 5;
	public final static int ESTADO_SOLICITUD_DEVUELTA = 6;

	/** Estado de los DETALLES DE CONSULTAS*/
	public final static int ESTADO_DETALLE_PENDIENTE = 1;
	public final static int ESTADO_DETALLE_RESERVADA = 2;
	public final static int ESTADO_DETALLE_AUTORIZADA = 3;
	public final static int ESTADO_DETALLE_DENEGADA = 4;
	public final static int ESTADO_DETALLE_ENTREGADA = 5;
	public final static int ESTADO_DETALLE_DEVUELTA = 6;



	//Horas dia
	public final static int HORAS_DIA = 24;
	//Otras Constantes de Consultas
	public static final String LISTA_CONSULTAS_EN_ELABORACION_KEY = "LISTA_CONSULTAS_EN_ELABORACION_KEY";
	public static final String LISTA_CONSULTAS_A_GESTIONAR_KEY = "LISTA_CONSULTAS_A_GESTIONAR_KEY";
	public static final String LISTA_CONSULTAS_A_ENTREGAR_KEY = "LISTA_CONSULTAS_A_ENTREGAR_KEY";
	public final static String LISTA_CONSULTAS_KEY = "LISTA_CONSULTAS_KEY";
	public static final String LISTA_CONSULTAS_NO_DISPONIBLES_KEY = "LISTA_CONSULTAS_NO_DISPONIBLES_KEY";
	public final static String LISTA_ESTADOS_KEY = "LISTA_ESTADOS_KEY";
    public final static String VER_BOTON_ELIMINAR = "VER_BOTON_ELIMINAR";
    public final static String VER_BOTON_ENVIAR_SOLICITAR = "VER_BOTON_ENVIAR_SOLICITAR";
    public final static String VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR = "VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR";
    public final static String VER_BOTON_ENTREGAR = "VER_BOTON_ENTREGAR";
	public final static String VER_BOTON_IMPRIMIR_SALIDA = "VER_BOTON_IMPRIMIR_SALIDA";
	public final static String VER_BOTON_IMPRIMIR_ENTRADA = "VER_BOTON_IMPRIMIR_ENTRADA";
    public final static String VER_BOTON_EDITAR = "VER_BOTON_EDITAR";

    public final static String VER_BOTON_ANADIR_DETALLE = "VER_BOTON_ANADIR_DETALLE";
    public final static String VER_BOTON_ELIMINAR_DETALLE = "VER_BOTON_ELIMINAR_DETALLE";
    public final static String VER_BOTON_AUTORIZAR_DETALLE = "VER_BOTON_AUTORIZAR_DETALLE";
    public final static String VER_BOTON_DENEGAR_DETALLE = "VER_BOTON_DENEGAR_DETALLE";
    public final static String VER_BOTON_DENEGAR_TODAS = "VER_BOTON_DENEGAR_TODAS";
    public final static String VER_BOTON_DEVOLVER = "VER_BOTON_DEVOLVER";
    public final static String VER_BOTONES_MODIFICAR_COLUMNAS = "VER_BOTONES_MODIFICAR_COLUMNAS";
    public final static String VER_FECHA_DEVOLUCION = "VER_FECHA_DEVOLUCION";
	public final static String VER_FECHA_ENTREGA = "VER_FECHA_ENTREGA";
    public final static String VER_FECHA_MAX_FIN_CONSULTA = "VER_FECHA_MAX_FIN_CONSULTA";
    public final static String VER_FECHA_INICIO_RESERVA = "VER_FECHA_INICIO_RESERVA";
    public final static String VER_BOTON_SOLICITAR_RESERVA = "VER_BOTON_SOLICITAR_RESERVA";

	public final static String VER_RESERVA = "VER_RESERVA";
	public final static String VER_LISTA_DETALLES_PARA_DEPOSITO = "VER_LISTA_DETALLES_PARA_DEPOSITO";
	public final static String CHECK_INTERNO = "CHECK_INTERNO";
	public final static String CONSULTA_KEY = "CONSULTAKEY";
    public final static String DETALLE_CONSULTA_KEY = "DETALLECONSULTAKEY";
    public final static String LISTA_TEMA_KEY = "LISTATEMAKEY";
    public final static String LISTA_USUARIOS_KEY = "LISTA_USUARIOS_KEY";
	public final static String LISTA_USUARIOS_INVESTIGADORES_KEY = "LISTA_USUARIOS_INVESTIGADORES_KEY";
    public final static String LISTA_USUARIOS_CONSULTAS_KEY = "LISTA_USUARIOS_CONSULTAS_KEY";

	public final static String LISTADO_BUSQUEDA_UDOCS = "LISTADO_BUSQUEDA_UDOCS";
    public final static String FECHA_MAX_FIN_CONSULTA_KEY = "FECHA_MAX_FIN_CONSULTA_KEY";
    public final static String FECHA_DEVOLUCION_KEY = "FECHA_DEVOLUCION_KEY";
	public final static String FECHA_ENTREGA_KEY = "FECHA_ENTREGA_KEY";
    public final static String TEMA_KEY = "TEMAKEY";
    public final static String LISTA_USUARIOS_SALA_INVESTIGADORES_KEY = "LISTA_USUARIOS_SALA_INVESTIGADORES_KEY";

    public final static String LISTA_MOTIVO_RECHAZO_KEY = "LISTAMOTIVORECHAZOKEY";
    public final static String MOTIVO_RECHAZO_KEY = "MOTIVORECHAZOKEY";
    public final static String LISTA_MOTIVO_KEY = "LISTAMOTIVOKEY";
    public final static String MOTIVO_KEY = "MOTIVOKEY";
	public final static String ID_USUARIO_KEY = "ID_USUARIO_KEY";
	public final static String ID_ORGANO_USUARIO_SELECCIONADO_KEY = "ID_ORGANO_USUARIO_SELECCIONADO_KEY";
    public final static String LISTA_NO_DISPONIBLES = "LISTA_NO_DISPONIBLES";
	public final static String PARAMETRO_CODIGO_CONSULTA = "consulta";
	public final static int  TIPO_SOLICITUD_CONSULTA = 2;
	public final static String FICHERO_CONFIGURACION = "solicitudes.properties";
	public final static String METHOD = "METHOD";

	/** Check para el estado del radio el tipo de solicitante*/
	public final static String CHECK_CIUDADANO = "CHECK_CIUDADANO";

	/** Check para el estado del radio el tipo de solicitante*/
	public final static String CHECK_INVESTIGADOR = "CHECK_INVESTIGADOR";

	/** Check para el estado del radio el tipo de solicitante*/
	public final static String CHECK_ORGEXTERNO = "CHECK_ORGEXTERNO";

	/** Check para el estado del radio el tipo de busqueda de unidades documentals*/
	public final static String CHECKEXP = "CHECKEXP";

	/** Cte con el esxpediente para la busqueda de unidades documentales*/
	public final static String EXPEDIENTE_BUSQUEDA_UDOCS = "EXPEDIENTE_BUSQUEDA_UDOCS";

	/** Cte para mostrar el listado de las unidades documentales en la busque*/
	public final static String MOSTRAR_LISTADO_BUSQUEDA_UDOCS = "MOSTRAR_LISTADO_BUSQUEDA_UDOCS";

	/** Check para el estado del radio el tipo de busqueda de unidades documentals*/
	public final static String CHECKFONDOYSIGNATURA = "CHECKFONDOYSIGNATURA";

	/** Cte con el fondo para la busqueda de unidades documentales*/
	public final static String FONDO_BUSQUEDA_UDOCS = "FONDO_BUSQUEDA_UDOCS";

	/** Cte con la signatura para la busqueda de unidades documentales*/
	public final static String SIGNATURA_BUSQUEDA_UDOCS = "SIGNATURA_BUSQUEDA_UDOCS";

	/** Cte con los fondos existentes para la busqueda de unidades documentales*/
	public final static String LISTADO_FONDOS_BUSQUEDA_UDOCS = "LISTADO_FONDOS_BUSQUEDA_UDOCS";

	/** Cte para ver la columna de información */
	public final static String VER_BOTON_INFORMACION = "VER_BOTON_INFORMACION";

	/** Cte para ver la columna de información */
	public final static String VER_BOTONES_COPIAS = "VER_BOTONES_COPIAS";

	/** Cte para ver el boton de disponibilidad*/
	public final static String VER_BOTON_VER_DISPONIBILIDAD = "VER_BOTON_VER_DISPONIBILIDAD";

	/** Cte para ver la columna de disponibilidad */
	public final static String VER_COLUMNA_DISPONIBILIDAD = "VER_COLUMNA_DISPONIBILIDAD";

	/** Cte para ver la columna de modificación de expediente de fracción de serie en préstamo/consulta */
	public final static String VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS = "VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS";

	/** Cte donde van los detalles de una consulta para su impresion de la papeleta de salida*/
	public final static String DETALLES_CONSULTA_IMPRIMIR = "DETALLES_CONSULTA_IMPRIMIR";

	/** Mensajes de error de mapeo de exceptions*/
	public final static String CONSULTA_NO_PUEDE_SER_CREADA_X_OTRO_ABIERTA = "errors.solicitudes.consultas.usuarioYaTieneAbiertoUnaSolicitud";
	public final static String NO_ES_POSIBLE_ELIMINAR_CONSULTA_EN_PROCESO_MESSAGE_KEY = "errors.solicitudes.consultas.soloEsPosibleEliminarConsultasEnProceso";
	public final static String SOLO_ES_POSIBLE_ENVIAR_CONSULTA_EN_ESTADO_ABIERTO_MESSAGE_KEY = "errors.solicitudes.consultas.soloEsPosibleEnviarConsultasEnEstadoAbierto";
	public final static String CONSULTA_NO_PUEDE_SER_ENVIADA_PQ_NO_TIENE_DETALLE_MESSAGE_KEY = "errors.solicitudes.consultas.consultaNoPuedeSerEnviadaPQNoTieneDetalle";
	public final static String NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_ESTADO = "errors.solicitudes.consultas.soloSeAutorizanEnSolicitado";
	public final static String CONSULTA_NO_RESERVADA = "errors.solicitudes.consultas.noreservada";
	public final static String CONSULTA_NO_ENTREGABLE_X_FECHA = "errors.solicitudes.consultas.fechaEntregaReservaNoValida";
	public final static String ERROR_CONSULTA_NO_ENTREGABLE_X_USUARIO = "errors.solicitudes.consultas.EntregaReservaNoValidaXUsuario";
	public final static String ERROR_CONSULTA_NO_AUTORIZADA = "errors.solicitudes.consultas.EntregaConConsultaNoAutorizado";
	public final static String CONSULTA_NO_PUEDE_SER_ENTREGADA_X_FMAXFIN_SUPERADA = "errors.solicitudes.consultas.consultaNoPuedeSerEntregadaXFechaMaxFinSuperada";

	/**Lista de archivos*/
	public final static String LISTA_ARCHIVOS = "LISTA_ARCHIVOS";

	/**Identificador del usuario investigador seleccionado*/
	public final static String ID_INVESTIGADOR = "ID_INVESTIGADOR";
	public final static String PROPERTY_NOMBRE = "NOMBRE";
	public final static String PROPERTY_TIPO_SOLICITANTE = "TIPO_SOLICITANTE";
	public final static String PROPERTY_DEPENDENTORGANIZATIONLIST = "DEPENDENTORGANIZATIONLIST";
	public final static String NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY = "NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY";
	public final static String ID_UDOC_DEVUELTA = "idUdocDevuelta";
	public final static String LISTA_TIPOS_ENTREGA = "LISTA_TIPOS_ENTREGA";

	//	Búsquedas
	public static final String CFG_BUSQUEDA_KEY = getKey("CFG_BUSQUEDA_KEY");
	public static Map camposBusquedas = SigiaUtilConstants.getConstantsMap(CamposBusquedasConstants.class);
	public static String LISTA_IDS_ELEMENTOS_CF = "LISTA_IDS_ELEMENTOS_CF";
	public static String MAP_ELEMENTOS_CF_VISITADOS = "MAP_ELEMENTOS_CF_VISITADOS";

	//Errores
	public static final String ERRORS_SOLICITUDES_CONSULTA_NO_EDITABLE = "errors.solicitudes.consultas.noeditable";
	public static final String ERRORS_SOLICITUDES_CONSULTA_FECHA_INICIO_RESERVA = "errors.solicitudes.consultas.fechaInicioReserva";
	public static final String ERRORS_SOLICITUDES_CONSULTA_FECHA_INICIO_MAYOR_ACTUAL = "errors.solicitudes.consultas.fechaInicioMayorActual";
	public static final String ERRORS_SOLICITUDES_CONSULTA_FECHA_NUM_USER_CONSULTOR = "errors.solicitudes.consultas.nusrconsultor";
	public static final String ERRORS_SOLICITUDES_CONSULTA_FECHA_NUM_OGR_CONSULTOR = "errors.solicitudes.consultas.norgconsultor";
	public static final String ERRORS_SOLICITUDES_CONSULTA_TEMA_NUEVO = "errors.solicitudes.consultas.temaNuevo";
	public static final String ERRORS_SOLICITUDES_DETALLES_NO_DISPONIBLES = "errors.solicitudes.consultas.noPosibleEnviarXdetallesNoDisponibles";

	//Informes
	public static final String INFORME_SOLICITUDESCONSULTA_TITULO_CONSULTA="archigest.archivo.informe.solicitudesConsultas.tituloConsulta";
	public static final String INFORME_SOLICITUDESCONSULTA_SOLICITUD="archigest.archivo.informe.solicitudesConsultas.solicitud";
	public static final String SOLICITUDES_CONSULTA_ESTADO="archigest.archivo.solicitudes.consulta.estado";
	public static final String CONSULTAS_COPIAS_SIMPLES="archigest.archivo.consultas.copiasSimples";
	public static final String CONSULTAS_COPIAS_CERTIFICADAS="archigest.archivo.consultas.copiasCertificadas";
	public static final String BUSQUEDA_CONSULTAS_FECHA_ENTREGA="archigest.archivo.consultas.busqueda.fechaEntrega";

	/* Para las firmas */
	public static final String LABEL_FIRMA1 = "archigest.archivo.prestamos.firma1";
	public static final String LABEL_FIRMA2 = "archigest.archivo.prestamos.firma2";
	public static final String LABEL_FIRMA3 = "archigest.archivo.prestamos.firma3";
	public static final String LABEL_FIRMA4 = "archigest.archivo.prestamos.firma4";

	public static final String VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES = "VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES";

	public static final String ACCION_ANIADIR_A_CONSULTA_KEY = "ACCION_ANIADIR_A_CONSULTA_KEY";


	public static final String LABEL_SOLICITANTE = "archigest.archivo.prestamos.solicitante";

	public static final String LABEL_REPRESENTANTE = "archigest.archivo.consultas.representante";

	public static final String LABEL_ESTADO_CONSULTAS_BASE = "archigest.archivo.solicitudes.consulta.estado.";


	public static final String CONSULTA_VIEW_OBJECT_KEY = "CONSULTA_VIEW_OBJECT_KEY";
}


