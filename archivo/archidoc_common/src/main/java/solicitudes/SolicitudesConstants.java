package solicitudes;

public class SolicitudesConstants {
	public static final String DETALLES_UDOCS_KEY="DETALLES_UDOCS_KEY";

	//public static final String LABEL_INFORMES_INFORMEDEVOLUCIONONSULTA_TITULO="archigest.archivo.informe.informeDevolucionConsulta.titulo";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCION_TITULO="archigest.archivo.informe.informeDevolucion.titulo";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONES_TITULO="archigest.archivo.informe.informeDevoluciones.titulo";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_FECHA="archigest.archivo.informe.informeDevolucionFechas.fecha";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_SIGNATURA="archigest.archivo.informe.informeDevolucionFechas.signatura";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_EXPEDIENTE="archigest.archivo.informe.informeDevolucionFechas.expediente";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_TITULO_UDOC="archigest.archivo.informe.informeDevolucionFechas.tituloUdoc";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_UBICACION="archigest.archivo.informe.informeDevolucionFechas.ubicacion";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONCONSULTA_VISTOBUENO="archigest.archivo.informe.informeDevolucionConsulta.vistoBueno";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONCONSULTA_SOLICITANTE="archigest.archivo.informe.informeDevolucionConsulta.solicitante";

	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONCONSULTA_REPRESENTANTE="archigest.archivo.informe.informeDevolucionConsulta.representante";
	public static final String LABEL_INFORMES_INFORMEDEVOLUCIONCONSULTA_SOLICITUD="archigest.archivo.informe.informeDevolucionConsulta.solicitud";
	public static final String LABEL_INFORMES_CONSULTA_DATOS_CABECERA="archigest.archivo.solicitudes.consulta.datosCabecera";
	public static final String LABEL_INFORMES_SOLICITUDESCONSULTA_TITULO_TESTIGO="archigest.archivo.informe.solicitudesConsultas.tituloTestigo";
	public static final String LABEL_INFORMES_SOLICITUDES_VISTO_BUENO="archigest.archivo.informe.informeSolicitudPrestamo.vistoBueno";
	public static final String LABEL_FORM_FECHA_SOLICITUD="archigest.archivo.busqueda.form.fecha.solicitud";
	public static final String LABEL_FORM_TIPO_SERVICIO="archigest.archivo.busqueda.form.tipoServicio";
	public static final String LABEL_FECHA_ENTREGA = "archigest.archivo.prestamos.fentrega";
	public static final String LABEL_FECHA_DEVOLUCION = "archigest.archivo.consultas.fdevolucion";

	public static final String LABEL_TITULO = "archigest.archivo.consultas.titulo";
	public static final String LABEL_F_INICAL  = "archigest.archivo.fechaInicial";
	public static final String LABEL_F_FINAL = "archigest.archivo.fechaFinal";
	public static final String LABEL_FECHA_IMPRESION = "archigest.archivo.solicitudes.fecha.impresion";
	public static final String ERRORS_SOLICITUDES_UDOC_NO_ENTREGADA="errors.solicitudes.prestamos.udocnoentregada";
	public static final String PREFFIX_SOLICITUDES_ESTADO="archigest.archivo.solicitudes.estado.";
	public static final String LABEL_INFORMES_ENTREGASOLICITUD_SOLICITANTE_AUTORIZADO="archigest.archivo.informe.entregaSolicitud.solicitanteAutorizado";
	public static final String LABEL_INFORMES_ENTREGASOLICITUD_AUTORIZADO="archigest.archivo.informe.entregaSolicitud.autorizado";

	public static final String LABEL_SOLICITANTE = "archigest.archivo.consultas.solicitante";
	public static final String LABEL_REPRESENTANTE = "archigest.archivo.consultas.representante";
	public static final String LABEL_ORG_INSTITUCION = "archigest.archivo.solicitudes.organismo.institucion";
	public static final String LABEL_ORG_SOLICITANTE = "archigest.archivo.solicitudes.organismo.solicitante";
	public static final String LABEL_UNIDADES_DOCUMENTALES ="archigest.archivo.consultas.udocs";
	//public static final String LABEL_RECIBI = "organization.archivo.informe.informeSolicitudPrestamo.firmaYSelloArchivo";

	//PRESTAMOS
	public static final String LABEL_DATOS_PRESTAMO = "archigest.archivo.solicitudes.prestamo.datosCabecera";
	public static final String LABEL_NUMERO_PRESTAMO = "archigest.archivo.solicitudes.numero.prestamo";

	//CONSULTAS
	public static final String LABEL_DATOS_CONSULTA = "archigest.archivo.solicitudes.consulta.datosCabecera";
	public static final String LABEL_NUMERO_CONSULTA = "archigest.archivo.solicitudes.numero.consulta";

	public final static String FORMAT_ID_SOLICITUD = "000000";
	public final static String DEVOLUCION_DETALLE_BUSQUEDA_POR_EXPEDIENTE = "2";

	/** Estados de Disponibilidad/NO Disponibilidad de los DETALLES DE PRESTAMOS Y CONSULTAS **/
	public final static int ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE = 100;
	// Este estado es para el caso de préstamos de cajas no descritas que ya están incluidas en otro préstamo,
	// se indicará que está disponible, pero que puede que algún expediente no descrito esté prestado ya
	public final static int ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL = 101;
	public final static int ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_AUTORIZADA_ENTREGADA = 200;
	public final static int ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_RESERVADA = 201;
	public final static int ESTADO_DISPONIBILIDAD_DETALLE_NO_DISPONIBLE_X_BLOQUEADA = 202;

	public final static String TIPO_SOLICITUD_PRESTAMO = "P";
	public final static String TIPO_SOLICITUD_CONSULTA = "C";
	public final static String LISTA_UDOCS_RELACIONADAS = "LISTA_UDOCS_RELACIONADAS";
	public final static String ENVIO_FINALIZADO_KEY = "ENVIO_FINALIZADO_KEY";

	public final static String LISTA_MOTIVOS_KEY = "LISTA_MOTIVOS_KEY";
	public final static String LISTA_TIPOS_ENTIDAD_KEY = "LISTA_TIPOS_ENTIDAD_KEY";
	public final static String LISTA_TIPOS_CONSULTA_KEY = "LISTA_TIPOS_CONSULTA_KEY";
	public final static String LISTA_VISIBILIDAD_CONSULTA_KEY = "LISTA_VISIBILIDAD_CONSULTA_KEY";
	public final static String MOTIVO_KEY = "MOTIVO_KEY";
	public final static String MOTIVO_EDITABLE_KEY = "MOTIVO_EDITABLE_KEY";
	public final static String LISTA_DETALLES_UDOCS_SOLICITUD_KEY = "LISTA_DETALLES_UDOCS_SOLICITUD_KEY";
	public final static String ARCHIVO_SOLICITUD_KEY = "ARCHIVO_SOLICITUD_KEY";
	public final static String LISTA_UDOCS_SOLICITUD_KEY = "LISTA_UDOCS_SOLICITUD_KEY";

	/**
	 * Lista de prestamos a los que se les pueden añadir unidades documentales desde los resultados de la búsqueda
	 */
	public final static String PRESTAMOS_PARA_ANIADIR_UDOCS_KEY = "PRESTAMOS_PARA_ANIADIR_UDOCS_KEY";

	/**
	 * Lista de prestamos a los que se les pueden añadir unidades documentales desde los resultados de la búsqueda
	 */
	public final static String CONSULTAS_PARA_ANIADIR_UDOCS_KEY = "CONSULTAS_PARA_ANIADIR_UDOCS_KEY";

	public final static String MOTIVO_CONSULTA = "archigest.archivo.solicitudes.consulta";
	public final static String MOTIVO_PRESTAMO = "archigest.archivo.solicitudes.prestamo";
	public final static String MOTIVO_RECHAZO = "archigest.archivo.solicitudes.rechazo";

	public final static int TIPO_SOLICITUD_PRESTAMO_INT = 1;
	public final static int TIPO_SOLICITUD_CONSULTA_INT = 2;
	public final static int TIPO_PRORROGA_INT = 3;

	/** Visibilidad de usuario */
	public final static int VISIBILIDAD_ARCHIVO = 1;

	public final static int VISIBILIDAD_NO_ARCHIVO = 2;

	public final static int VISIBILIDAD_AMBOS = 3;

	public final static String ETIQUETA_ID_MOTIVO = "errors.solicitudes.etiqueta.idMotivo";

	public static final String ERROR_AL_CREAR_NUEVO_TEMA = "errors.solicitudes.error.crear.tema";

	public static final String ERROR_AL_GUARDAR_CONSULTA = "errors.solicitudes.error.guardar.consulta";
}
