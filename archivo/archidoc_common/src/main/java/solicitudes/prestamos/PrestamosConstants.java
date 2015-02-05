package solicitudes.prestamos;

import java.util.Map;


import common.SigiaUtilConstants;

import fondos.CamposBusquedasConstants;

/**
 * Clase con las constantes para el módulo de préstamos.
 */
public final class PrestamosConstants {

	private static final StringBuffer buff = new StringBuffer();

	private final static String getKey(String innerKey){
		      buff.setLength(0);
		      return buff.append("PrestamosConstants_").append(innerKey).toString();
	 }

	/** Tipos de Usuario **/
	public final static String TIPO_INTERNO_STRING = "1";
	public final static String TIPO_EXTERNO_STRING = "2";
	public final static int TIPO_INTERNO_INT = 1;
	public final static int TIPO_EXTERNO_INT = 2;

	/** Tipos de prestamo **/
	public final static int TIPO_PRESTAMO_DIRECTO = 1;
	public final static int TIPO_PRESTAMO_INDIRECTO = 2;

    /** Estado de los PRESTAMOS*/
	public final static int ESTADO_PRESTAMO_ABIERTO = 1;
	public final static int ESTADO_PRESTAMO_SOLICITADO = 2;
	public final static int ESTADO_PRESTAMO_RESERVADO = 3;
	public final static int ESTADO_PRESTAMO_AUTORIZADO = 4;
	public final static int ESTADO_PRESTAMO_DENEGADO = 5;
	public final static int ESTADO_PRESTAMO_ENTREGADO = 6;
	public final static int ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO = 7;
	public final static int ESTADO_PRESTAMO_DEVUELTO = 8;

	/** Estados de la SOLICITUD*/
	public final static int ESTADO_SOLICITUD_PENDIENTE = 1;
	public final static int ESTADO_SOLICITUD_RESERVADA = 2;
	public final static int ESTADO_SOLICITUD_AUTORIZADA = 3;
	public final static int ESTADO_SOLICITUD_DENEGADA = 4;
	public final static int ESTADO_SOLICITUD_ENTREGADA = 5;
	public final static int ESTADO_SOLICITUD_DEVUELTA = 6;

	/** Estados de la PRORROGA*/
	public final static int ESTADO_PRORROGA_SOLICITADA = 1;
	public final static int ESTADO_PRORROGA_AUTORIZADA = 2;
	public final static int ESTADO_PRORROGA_DENEGADA = 3;

	/** Estado de los DETALLES DE PRESTAMOS **/
	public final static int ESTADO_DETALLE_PENDIENTE = 1;
	public final static int ESTADO_DETALLE_RESERVADA = 2;
	public final static int ESTADO_DETALLE_AUTORIZADA = 3;
	public final static int ESTADO_DETALLE_DENEGADA = 4;
	public final static int ESTADO_DETALLE_ENTREGADA = 5;
	public final static int ESTADO_DETALLE_DEVUELTA = 6;
	// public final static int ESTADO_DETALLE_AUTORIZADA_PARCIAL = 7;
	public final static int HORAS_DIA = 24;

	/** Estado de los DOCUMENTOS A REVISAR **/
	public static Map estadoRevDoc = SigiaUtilConstants.getConstantsMap(EstadoRevDoc.class);

	public static final String LISTA_ESTADOS_KEY = "LISTA_ESTADOS_KEY";
	public static final String LISTA_NOTAS_KEY = "LISTA_NOTAS_KEY";
	public static final String LISTA_PRESTAMOS_EN_ELABORACION_KEY = "LISTA_PRESTAMOS_EN_ELABORACION_KEY";
	public static final String LISTA_PRESTAMOS_A_GESTIONAR_KEY = "LISTA_PRESTAMOS_A_GESTIONAR_KEY";
	public static final String LISTA_PRESTAMOS_A_ENTREGAR_KEY = "LISTA_PRESTAMOS_A_ENTREGAR_KEY";
	public static final String LISTA_PRESTAMOS_NO_DISPONIBLES_KEY = "LISTA_PRESTAMOS_NO_DISPONIBLES_KEY";
	public final static String LISTA_PRESTAMOS_KEY = "LISTA_PRESTAMOS_KEY";
	public final static String LISTA_REVISIONES_DOC_KEY = "LISTA_REVISIONES_DOC_KEY";
	public final static String VER_COLUMNA_NOTAS = "VER_COLUMNA_NOTAS";
	public final static String VER_COLUMNA_SELECCIONAR = "VER_COLUMNA_SELECCIONAR";
	public final static String PROCESO_AUTORIZACON_PRESTAMO_KEY = getKey("PROCESO_AUTORIZACON_PRESTAMO_KEY");

	public final static String VER_COLUMNA_DISPONIBILIDAD = "VER_COLUMNA_DISPONIBILIDAD";
	public final static String LISTA_PRORROGAS_KEY = "LISTA_PRORROGAS_KEY";
	public final static String LISTA_USUARIOS_KEY = "LISTA_USUARIOS_KEY";
	public final static String USUARIO_SELECCIONADO_KEY = "USUARIO_SELECCIONADO_KEY";
	public  final static String VER_BOTON_ELIMINAR = "VER_BOTON_ELIMINAR";
	public  final static String VER_BOTON_SELECCIONAR = "VER_BOTON_SELECCIONAR";
	public  final static String VER_BOTON_ENVIAR_SOLICITAR = "VER_BOTON_ENVIAR_SOLICITAR";

	public  final static String VER_BOTON_VER_DISPONIBILIDAD = "VER_BOTON_VER_DISPONIBILIDAD";
	public  final static String VER_BOTON_VER_DISPONIBILIDAD_PRORROGA = "VER_BOTON_VER_DISPONIBILIDAD_PRORROGA";
	public  final static String VER_BOTON_VER_DISPONIBILIDAD_ENTREGA = "VER_BOTON_VER_DISPONIBILIDAD_ENTREGA";
	public  final static String VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR = "VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR";
	public  final static String VER_BOTON_ENTREGAR = "VER_BOTON_ENTREGAR";
	public final static String VER_BOTON_IMPRIMIR_SALIDA = "VER_BOTON_IMPRIMIR_SALIDA";
	public final static String VER_BOTON_IMPRIMIR_ENTRADA = "VER_BOTON_IMPRIMIR_ENTRADA";
	public final static String VER_BOTON_IMPRIMIR_RECLAMACION_1 = "VER_BOTON_IMPRIMIR_RECLAMACION_1";
	public final static String VER_BOTON_IMPRIMIR_RECLAMACION_2 = "VER_BOTON_IMPRIMIR_RECLAMACION_2";
	public final static String VER_BOTON_IMPRIMIR_ETIQUETAS = "VER_BOTON_IMPRIMIR_ETIQUETAS";

	public  final static String VER_BOTON_EDITAR = "VER_BOTON_EDITAR";
	public  final static String PERMITIR_EDITAR_OBSERVACIONES = "PERMITIR_EDITAR_OBSERVACIONES";
	public  final static String VER_BOTON_ANADIR_DETALLE = "VER_BOTON_ANADIR_DETALLE";
	public  final static String VER_BOTON_ELIMINAR_DETALLE = "VER_BOTON_ELIMINAR_DETALLE";
	public  final static String VER_BOTON_AUTORIZAR_DETALLE = "VER_BOTON_AUTORIZAR_DETALLE";
    public  final static String VER_BOTON_DENEGAR_DETALLE = "VER_BOTON_DENEGAR_DETALLE";
    public  final static String VER_BOTON_DENEGAR_TODAS = "VER_BOTON_DENEGAR_TODAS";
    public  final static String VER_BOTON_DEVOLVER = "VER_BOTON_DEVOLVER";
    public  final static String VER_BOTON_SOL_PRORROGA = "VER_BOTON_SOL_PRORROGA";
    public  final static String VER_BOTON_SOLICITAR_RESERVA = "VER_BOTON_SOLICITAR_RESERVA";

	public final static String VER_PRORROGAS_SOLICITADAS = "VER_PRORROGAS_SOLICITADAS";
    public final static String VER_BOTON_AUTORIZAR_PRORROGA = "VER_BOTON_AUTORIZAR_PRORROGA";
    public final static String VER_BOTON_DENEGAR_PRORROGA = "VER_BOTON_DENEGAR_PRORROGA";
	public final static String VER_BOTON_RECLAMACION_1 = "VER_BOTON_RECLAMACION_1";
	public final static String VER_BOTON_RECLAMACION_2 = "VER_BOTON_RECLAMACION_2";
	public final static String VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES = "VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES";
	public final static String VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS = "VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS";

	/** Cte para mostrar el boton de cesión de prestamo*/
	public final static String VER_BOTON_CESION = "VER_BOTON_CESION";
    public final static String VER_FECHA_DEVOLUCION = "VER_FECHA_DEVOLUCION";
    public final static String VER_FECHA_MAX_FIN_PRESTAMOS = "VER_FECHA_MAX_FIN_PRESTAMOS";
    public final static String VER_FECHA_INICIO_RESERVA = "VER_FECHA_INICIO_RESERVA";
	public final static String VER_FECHA_ENTREGA = "VER_FECHA_ENTREGA";
	public final static String VER_RESERVA = "VER_RESERVA";
	public final static String VER_LISTA_DETALLES_PARA_DEPOSITO = "VER_LISTA_DETALLES_PARA_DEPOSITO";

	/** Cte para mostrar el select con los tipos de solicitante en modo edicion*/
	public final static String VER_SELECT = "VER_SELECT_EDICION";

	/** Cte con el nombre temporal en la session para el detalle de un prestamo*/
	public final static String PRESTAMO_KEY = "PRESTAMOKEY";
	public final static String REVISION_DOC_KEY = "REVISION_DOC_KEY";

	/** Cte para mostrat el display con los prestamos que se pueden ceder*/
	public final static String VER_PRESTAMOS_CESION = "VER_PRESTAMOS_CESION";
	public final static String DETALLE_PRESTAMO_KEY = "DETALLEPRESTAMOKEY";
	public final static String LISTA_NO_DISPONIBLES = "LISTA_NO_DISPONIBLES";
//	public final static String FORMAT_ID_PRESTAMO = "000000";
	public final static String FECHA_MAX_FIN_PRESTAMO_KEY = "FECHA_MAX_FIN_PRESTAMO_KEY";
	public final static String FECHA_DEVOLUCION_KEY = "FECHA_DEVOLUCION_KEY";
	public final static String FECHA_ENTREGA_KEY = "FECHA_ENTREGA_KEY";

	public final static String PRORROGA_SOLICITADA_KEY = "PRORROGA_SOLICITADA_KEY";
	public final static String ESTADO_PRORROGA_SOLICITADA_KEY = "ESTADO_PRORROGA_SOLICITADA_KEY";
	public final static String FECHA_PRORROGA_SOLICITADA_KEY = "FECHA_PRORROGA_SOLICITADA_KEY";
	public final static String ESTADO_PRORROGA_AUTORIZADA_KEY = "ESTADO_PRORROGA_AUTORIZADA_KEY";
	public final static String ESTADO_PRORROGA_DENEGADA_KEY = "ESTADO_PRORROGA_DENEGADA_KEY";
	public final static String FECHA_PRORROGA_DENEGADA_KEY = "FECHA_PRORROGA_DENEGADA_KEY";
	public final static String MOTIVO_RECHAZO_PRORROGA_KEY = "MOTIVO_RECHAZO_PRORROGA_KEY";
	public final static String LISTADO_BUSQUEDA_UDOCS = "LISTADO_BUSQUEDA_UDOCS";

	/** Para mostrar el listado de unidades documentales*/
	public final static String MOSTRAR_LISTADO_BUSQUEDA_UDOCS = "MOSTRAR_LISTADO_BUSQUEDA_UDOCS";
	public final static String LISTADO_FONDOS_BUSQUEDA_UDOCS = "LISTADO_FONDOS_BUSQUEDA_UDOCS";
	public final static String LISTADO_TIPOS_USUARIO = "LISTADO_TIPOS_USUARIO";
	public final static String LISTADO_TIPOS_SERVICIO ="LISTADO_TIPOS_SERVICIO";
	public final static String LISTA_DETALLES_DEVUELTOS_FALLOS = "LISTA_DETALLES_DEVUELTOS_FALLOS";
	public final static String LISTA_DETALLES_DEVUELTOS_EXITOS = "LISTA_DETALLES_DEVUELTOS_EXITOS";

	/** Cte para almacenar el listado con los posibles gestores*/
	public final static String LISTA_GESTORES_KEY = "LISTA_GESTORES_KEY";

	/** Cte para almacenar en sesion las udocs que se van a devolver*/
	public final static String LISTA_UDOC_ADEVOLVER = "LISTA_UDOC_ADEVOLVER";

	/** Cte para almacenar en sesion las udocs que se van a devolver*/
	public final static String LISTA_UDOCS_DEVUELTAS = "LISTA_UDOCS_DEVUELTAS";

	/** Cte para activar el check de TODOS en cesion de control*/
	public final static String CHECKTODOS = "CHECKTODOS";

	/** Cte para activar el check de GESTORen cesion de control*/
	public final static String CHECKGESTOR = "CHECKGESTOR";

	/** Cte para activar el check de buscar por expediente en añadir udocs*/
	public final static String CHECKEXP = "CHECKEXP";

	/** Cte para activar el check de buscar por fondo y signatura en añadir udocs*/
	public final static String CHECKFONDOYSIGNATURA = "CHECKFONDOYSIGNATURA";

	/** Cte para indicar el tipo de usuario*/
	public final static String CHECK_INTERNO = "CHECK_INTERNO";

	public final static String CHECK_EXTERNO = "CHECK_EXTERNO";

	public  final static String PARAMETRO_CODIGO_PRESTAMO = "prestamo";

	public final static int TIPO_SOLICITUD_PRESTAMO = 1;

	/** Cte con el nombre temporal en la session para el Listado de los motivos de rechazo de un prestamo*/
	public final static String LISTA_MOTIVO_RECHAZO_KEY = "LISTAMOTIVORECHAZOKEY";

	/** Cte con el nombre temporal en la session para el Listado de los motivos de prorroga de un prestamo*/
	public final static String LISTA_MOTIVO_RECHAZO_PRORROGA_KEY = "LISTAMOTIVORECHAZOPRORROGAKEY";

	/** Cte para visualizar los organos en el formulario de busqueda*/
	public final static String VER_ORGANOS_KEY = "VER_ORGANOS_KEY";

	/** Cte que contiene el listado de orfanos para la busqueda*/
	public final static String LISTA_ORGANOS_KEY = "LISTA_ORGANOS_KEY";
    public final static String MOTIVO_RECHAZO_KEY = "MOTIVORECHAZOKEY";
    public final static String ID_USUARIO_KEY = "ID_USUARIO_KEY";
	public final static String NUM_PRORROGAS_KEY = "NUM_PRORROGAS_KEY";

	/**Cte para almacenar el nuevo gestor de un prestamo*/
	public final static String GESTOR_KEY = "GESTOR_KEY";

	public final static String FICHERO_CONFIGURACION = "solicitudes.properties";

	/**Cte para visualizar los usuario s en el formulario de busqeuda*/
	public final static String VER_USUARIOS_KEY = "VER_USUARIOS_KEY";

	/** Cte donde se guarda el expediente en la busqueda de udocs por expediente */
	public final static String EXPEDIENTE_BUSQUEDA_UDOCS = "EXPEDIENTE_BUSQUEDA_UDOCS";

	/** Cte donde se guarda la signatura en la busqueda de udocs por fondo+signatura */
	public final static String SIGNATURA_BUSQUEDA_UDOCS = "SIGNATURA_BUSQUEDA_UDOCS";

	/** Cte donde se guarda el fondo en la busqueda de udocs por fondo+signatura */
	public final static String FONDO_BUSQUEDA_UDOCS = "FONDO_BUSQUEDA_UDOCS";

	public final static String LISTA_GESTORESREVDOC_KEY = "LISTADO_GESTORESREVDOC_KEY";

	/** Metodo que se llama en la recarga del displaytag*/
	public final static String METHOD = "METHOD";

	/** Motivos de rechazo de préstamo */
	public final static int MOTIVO_RECHAZO_PRESTAMO = 1;

	/** Motivos de prorroga de préstamo */
	public final static int MOTIVO_PRORROGA_PRESTAMO = 3;

	/** Att del Request con el id del solicitante */
	public final static String SOLICITANTE_REQUEST = "solicitante";

	/** Cte para indicar que un solicitante es externo en modo edicion*/
	public final static String ES_EXTERNO = "ES_EXTERNO";

	/** Cte para incluir el listado de archivos existentes*/
	public final static String LISTA_ARCHIVOS = "LISTA_ARCHIVOS";

	public final static String PRESTAMO_FORM = "prestamoForm";

	public final static String PROPERTY_NOMBRE = "NOMBRE";
	public final static String PROPERTY_TIPO_SOLICITANTE = "TIPO_SOLICITANTE";
	public final static String PROPERTY_ID_USR_SOLICITANTE = "ID_USR_SOLICITANTE";
	public final static String PROPERTY_ID_PRESTAMO = "ID_PRESTAMO";
	public final static String TIPO_EXTERNO = "externo";
	public final static String TIPO_INTERNO = "interno";
	public final static String PROPERTY_APELLIDOS = "APELLIDOS";
	public final static String PROPERTY_ORGANIZACION = "ORGANIZACION";
	public final static String PROPERTY_ID_ORGANIZACION = "ID_ORGANIZACION";
	public final static String PROPERTY_DEPENDENTORGANIZATIONLIST = "DEPENDENTORGANIZATIONLIST";
	public final static String PROPERTY_ARCHIVOS_CUSTODIA = "PROPERTY_ARCHIVOS_CUSTODIA";

	public final static String TIPO_SERVICIO_PRESTAMO="Prestamo";
	public final static String TIPO_SERVICIO_CONSULTA="Consulta";

	public final static String NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_ESTADO = "errors.solicitudes.prestamos.soloSeAutorizanEnSolicitado";
	public final static String NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_NO_DISPONIBLE= "errors.solicitudes.prestamos.soloSeAutorizanDisponibles";
	public final static String NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_ARCHIVO = "errors.solicitudes.prestamos.NoArchivoDeCustodia";
	public final static String SOLO_ES_POSIBLE_ENVIAR_PRESTAMOS_EN_ESTADO_ABIERTO_MESSAGE_KEY = "errors.solicitudes.prestamos.soloEsPosibleEnviarPrestamosEnEstadoAbierto";
	public final static String PRESTAMO_NO_PUEDE_SER_ENVIADO_PQ_NO_TIENE_DETALLE_MESSAGE_KEY = "errors.solicitudes.prestamos.prestamoNoPuedeSerEnviadoPQNoTieneDetalle";
	public final static String PRESTAMO_NO_PUEDE_SER_ENVIADO_X_FECHA_RESERVA_NOVALIDA_MESSAGE_KEY = "errors.solicitudes.prestamos.prestamoNoPuedeSerEnviadoXFechaReservaNoValida";
	public final static String NO_ES_POSIBLE_ELIMINAR_PRESTAMO_EN_PROCESO_MESSAGE_KEY = "errors.solicitudes.prestamos.soloEsPosibleEliminarPrestamosEnProceso";
	public final static String PRESTAMO_NO_PUEDE_SER_AUTORIZADO_X_ESTADO_NOVALIDO_MESSAGE_KEY = "errors.solicitudes.prestamos.soloEsPosibleAutorizarPrestamosSolicitados";
	public final static String PRESTAMO_NO_PUEDE_SER_CREADO_X_OTRO_ABIERTO = "errors.solicitudes.prestamos.usuarioYaTieneAbiertoUnaSolicitud";
	public final static String PRESTAMO_NO_PUEDE_SER_MODIFICADO_X_OTRO_ABIERTO = "errors.solicitudes.prestamos.usuarioYaTieneAbiertoUnaSolicitud";
	public final static String PRESTAMO_NO_EDITABLE = "errors.solicitudes.prestamos.noeditable";
	public final static String PRESTAMO_NO_RESERVADO = "errors.solicitudes.prestamos.noreservado";
	public final static String PRESTAMO_NO_ENTREGABLE_X_FECHA = "errors.solicitudes.prestamos.fechaEntregaReservaNoValida";
	public final static String PRESTAMO_NO_ENTREGABLE_X_USUARIO = "errors.solicitudes.prestamos.EntregaReservaNoValidaXUsuario";

	public final static String ERROR_PRESTAMO_NO_AUTORIZADO = "errors.solicitudes.prestamos.EntregaConPrestamosNoAutorizado";
	public final static String ERROR_ENTREGA_ARCHIVO_NO_VALIDO = "errors.solicitudes.prestamos.EntregaArchivoNoValido";
	public final static String ERROR_RECLAMAR_ESTADO_NO_VALIDO = "errors.solicitudes.prestamos.ReclamarEstadoNoValido";
	public final static String ERROR_RECLAMAR_EN_FECHA_VALIDA = "errors.solicitudes.prestamos.ReclamarFechaValida";;
	public final static String ERROR_RECLAMAR_X_PRORROGA = "errors.solicitudes.prestamos.ReclamarXProrroga";;
	public final static String ERROR_DEVOLUCION_DETALLE = "errors.solicitudes.prestamos.devolucionPrestamo";
	public final static String ERROR_PRESTAMOS_NORG_SOLICITANTE = "errors.solicitudes.prestamos.norgsolicitante";
	public final static String ERROR_PRESTAMOS_NUSR_SOLICITANTE = "errors.solicitudes.prestamos.nusrsolicitante";
	public final static String ERROR_PRESTAMOS_FECHA_INICIO_RESERVA = "errors.solicitudes.prestamos.fechaInicioReserva";
	public final static String ERROR_PRESTAMOS_FECHA_INICIO_MAYOR_ACTUAL = "errors.solicitudes.prestamos.fechaInicioMayorActual";
	public final static String ERROR_PRESTAMOS_FORMATO_EXPEDIENTEFS = "archigest.archivo.solicitudes.expresionValidacion.expedienteFS";
	public final static String PRESTAMO_NO_PUEDE_SER_ENTREGADO_X_FMAXFIN_SUPERADA = "errors.solicitudes.prestamos.prestamoNoPuedeSerEntregadoXFechaMaxFinSuperada";
	public final static String ERRORS_SOLICITUDES_DETALLES_NO_DISPONIBLES = "errors.solicitudes.prestamos.noPosibleEnviarXdetallesNoDisponibles";
	public final static String ERROR_PRESTAMOS_UNIDAD_MARCADA_REVISION_NO_DEVUELTA = "errors.solicitudes.prestamos.unidad.marcada.revision.no.devuelta";
	public final static String ERROR_PRESTAMOS_VARIAS_PARTES_UNIDAD_DOCUMENTAL_MARCADAS_REVISION = "errors.solicitudes.prestamos.varias.partes.unidad.documental.marcadas.revision";
	public final static String ERROR_PRESTAMOS_REVISION_DOCUMENTACION_EXISTENTE_UNIDAD = "errors.solicitudes.prestamos.revision.documentacion.existente.unidad";

	public final static String CRITERIO_BUSQUEDA_OBLIGATORIO = "errors.solicitudes.prestamos.criterioObligatorio";
    //public final static String NO_ES_POSIBLE_ENTREGAR_PRESTAMO_SIN_UDOC_MESSAGE_KEY = "errors.solicitudes.prestamos.noEsPosibleEntregarPrestamoSinUDoc";
	public final static String ORGANO_USUARIO_SELECCIONADO_KEY = "ORGANO_USUARIO_SELECCIONADO_KEY";
	public final static String NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY ="NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY";
	public final static String ID_UDOC_DEVUELTA = "idUdocDevuelta";
	public final static String CON_ERRORES = "CON_ERRORES";
	public final static String FECHA_SOLICITUD_KEY = "FECHA_SOLICITUD_KEY";
	public final static String LISTA_TIPOS_ENTREGA = "LISTA_TIPOS_ENTREGA";

	//Revisión de Documentación UDocs
	/**Cte para visualizar la lista de documentos de unidades documentales*/
	public final static String LISTA_REVISION_DOCUMENTACION_KEY = "LISTA_REVISION_DOCUMENTACION_KEY";
	public final static String ERROR_ALTA_UDOC_YA_EXISTE = "errors.solicitudes.prestamos.altaUdoc.yaExiste";
	public final static String ERROR_ALTA_UDOC_USUARIO_SIN_PERMISOS = "archigest.archivo.fondos.no.permisos.ingreso.directo";
	public final static String ERROR_ALTA_UDOC_NO_VALIDADA = "errors.solicitudes.prestamos.altaUdoc.noValidada";
	public final static String ERROR_ALTA_UDOC_NO_EXISTE = "errors.solicitudes.prestamos.altaUdoc.noExiste";
	public final static String ERROR_ALTA_UDOC_NO_ABIERTA = "errors.solicitudes.prestamos.altaUdoc.noAbierta";
	public final static String ERROR_NO_POSIBLE_RECHAZAR_REVDOC_CON_ALTA = "errors.solicitudes.prestamos.rechazar.noValido";

	// Búsquedas
	public static final String CFG_BUSQUEDA_KEY = getKey("CFG_BUSQUEDA_KEY");
	public static Map camposBusquedas = SigiaUtilConstants.getConstantsMap(CamposBusquedasConstants.class);
	public static String LISTA_IDS_ELEMENTOS_CF = "LISTA_IDS_ELEMENTOS_CF";
	public static String LISTA_IDS_TIPOS_ELEMENTOS_CF ="LISTA_IDS_TIPOS_ELEMENTOS_CF";
	public static String MAP_ELEMENTOS_CF_VISITADOS = "MAP_ELEMENTOS_CF_VISITADOS";

	//labels informes
	public static final String LABEL_INFORMES_RECLAMACIONPRESTAMO_TITULO="archigest.archivo.informe.informeReclamacionPrestamo.titulo";
	public static final String LABEL_INFORMES_RECLAMACIONPRESTAMO_SOLICITUD="archigest.archivo.informe.informeReclamacionPrestamo.solicitud";
	public static final String LABEL_INFORMES_RECLAMACIONPRESTAMO_VISTO_BUENO="archigest.archivo.informe.informeReclamacionPrestamo.vistoBueno";
	public static final String LABEL_INFORMES_SOLICITUDPRESTAMO_TITULO="archigest.archivo.informe.informeSolicitudPrestamo.titulo";
	public static final String LABEL_INFORMES_SOLICITUDPRESTAMO_SOLICITUD="archigest.archivo.informe.informeSolicitudPrestamo.solicitud";
	public static final String LABEL_INFORMES_SOLICITUDPRESTAMO_SOLICITANTE="archigest.archivo.informe.informeSolicitudPrestamo.solicitante";
	public static final String LABEL_INFORMES_TESTIGOPRESTAMO_TITULO="archigest.archivo.informe.testigoPrestamo.titulo";
	public static final String LABEL_INFORMES_TESTIGOPRESTAMO_SOLICITUD="archigest.archivo.informe.testigoPrestamo.solicitud";
	public static final String LABEL_PRESTAMOS_GESTOR_PRESTAMO="archigest.archivo.prestamos.gestorPrestamo";
	public static final String LABEL_PRESTAMOS_PRESTAMO="archigest.archivo.prestamos.prestamo";
	public static final String LABEL_PRESTAMOS_REVISION_DOC="archigest.archivo.prestamos.revisionDocumentacion";
	public static final String LABEL_INFORMES_OBSERVACIONES="archigest.archivo.solicitudes.observaciones";

	public static final String LABEL_INFORMES_NUMERO_PRESTAMO= "archigest.archivo.solicitudes.numero.prestamo";
	public static final String LABEL_INFORMES_NUMERO_CONSULTA= "archigest.archivo.solicitudes.numero.consulta";
	public static final String LABEL_INFORMES_FECHA_ENTREGA ="archigest.archivo.prestamos.fentrega";
	public static final String LABEL_INFORMES_FECHA_DEVOLUCION ="archigest.archivo.solicitudes.fdevolucion";
	public static final String LABEL_INFORMES_ORGANISMO_INSTITUCION ="archigest.archivo.solicitudes.organismo.institucion";
	public static final String LABEL_INFORMES_ORGANISMO_SOLICITANTE	="archigest.archivo.solicitudes.organismo.solicitante";
	public static final String LABEL_INFORMES_UNIDAD_DOCUMENTAL="archigest.archivo.unidadDoc";
	public static final String LABEL_INFORMES_SIGNATURA="archigest.archivo.signatura";
	public static final String LABEL_INFORMES_TITULO_UDOC="archigest.archivo.informe.tituloUdoc";
	public static final String LABEL_INFORMES_UBICACION="archigest.archivo.ubicacion.en.deposito";
	public static final String LABEL_INFORMES_FECHA_INICIAL="archigest.archivo.fechaInicial";
	public static final String LABEL_PRESTAMOS_FIN_PRORROGA = "archigest.archivo.prestamo.fecha.fin.prorroga";

	public static final String LABEL_INFORMES_FECHA_FINAL="archigest.archivo.fechaFinal";
	public static final String LABEL_INFORMES_PAGINA	= "archigest.archivo.informes.pagina";
	public static final String LABEL_INFORMES_DE	    = "archigest.archivo.informes.de";
	public static final String LABEL_CODIGO_UDOC		= "archigest.archivo.informe.codigoUdoc";
	public static final String LABEL_INFORMES_UNIDADES_RECLAMADAS = "archigest.archivo.informe.reclamacion.unidades.reclamadas";
	public static final String LABEL_INFORMES_UNIDADES_DOCUMENTALES ="archigest.archivo.unidadesDocumentales";
	public static final String LABEL_NUMERO_EXPEDIENTE = "archigest.archivo.informe.numeroExpediente";
	public static final String LABEL_FECHA_SOLICITUD = "archigest.archivo.prestamos.fsolicitud";
	public static final String LABEL_FIRMA_SELLO = "archigest.archivo.informe.informeSolicitudPrestamo.firmaYSelloReceptor";
	public static final String LABEL_INFORMES_CONTENEDOR="archigest.archivo.informeSolicitudPrestamo.contenedor";
	public static final String LABEL_INFORMES_CONTIENE="archigest.archivo.informeSolicitudPrestamo.contiene";

	/* Para las firmas */
	public static final String LABEL_FIRMA1 = "archigest.archivo.prestamos.firma1";
	public static final String LABEL_FIRMA2 = "archigest.archivo.prestamos.firma2";
	public static final String LABEL_FIRMA3 = "archigest.archivo.prestamos.firma3";
	public static final String LABEL_FIRMA4 = "archigest.archivo.prestamos.firma4";

	public static final String PRESTAMO_ARCHIVO_ID_KEY = "PRESTAMO_ARCHIVO_ID_KEY";
	public static final String PRESTAMO_ARCHIVO_NOMBRE_KEY = "PRESTAMO_ARCHIVO_NOMBRE_KEY";
	public static final String ACCION_ANIADIR_A_PRESTAMO_KEY = "ACCION_ANIADIR_A_PRESTAMO_KEY";

	public static final String PRESTAMO_USUARIO_ARCHIVO_GESTOR_KEY = "PRESTAMO_USUARIO_ARCHIVO_GESTOR_KEY";

	public static final String LABEL_ESTADO_PRESTAMOS_BASE = "archigest.archivo.solicitudes.estado.";
	public static final String LABEL_SITUACION_PRESTAMOS_BASE = "archigest.archivo.prestamos.situacion.";

	public final static String ESTADO_PRORROGA_SOLICITADA_MESSAGE_KEY 	= "archigest.archivo.prestamo.estadoSolictudProrroga.1";
	public final static String ESTADO_PRORROGA_AUTORIZADA_MESSAGE_KEY 	= "archigest.archivo.prestamo.estadoSolictudProrroga.2";
	public final static String ESTADO_PRORROGA_DENEGADA_MESSAE_KEY		= "archigest.archivo.prestamo.estadoSolictudProrroga.3";



	public static final String LABEL_PRESTAMOS_SITUACION_CADUCADO ="archigest.archivo.prestamos.situacion.C";
	public static final String LABEL_PRESTAMOS_SITUACION_RECLAMADO_1 = "archigest.archivo.prestamos.situacion.R1";
	public static final String LABEL_PRESTAMOS_SITUACION_RECLAMADO_2 = "archigest.archivo.prestamos.situacion.R2";
	public static final String LABEL_PRESTAMOS_SITUACION_PRORROGA_SOLICITADA = "archigest.archivo.prestamos.situacion.PS";
	public static final String LABEL_PRESTAMOS_SITUACION_PRORROGA_DENEGADA = "archigest.archivo.prestamos.situacion.PD";
	public final static String LABEL_PRESTAMOS_PRORROGA_AUTORIZADA ="archigest.archivo.prestamos.situacion.PA";
	public static final String LABEL_FECHA_FIN_PRORROGA_VIGENTE = "archigest.archivo.prestamo.fecha.fin.prorroga.vigente";

	/**
	 *
	 */
	public static final String SITUACION_CADUCADO = "C";
	/**
	 *
	 */
	public static final String SITUACION_RECLAMADO_UNA_VEZ = "R1";
	/**
	 *
	 */
	public static final String SITUACION_RECLAMADO_DOS_VECES = "R2";
	/**
	 *
	 */
	public static final String SITUACION_PRORROGA_SOLICITADA = "PS";
	/**
	 *
	 */
	public static final String SITUACION_PRORROGA_DENEGADA = "PD";

}
