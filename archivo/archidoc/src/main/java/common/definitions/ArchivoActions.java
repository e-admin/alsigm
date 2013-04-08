package common.definitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import auditoria.vos.ArchivoAction;
import auditoria.vos.ArchivoModule;

/**
 * Clase que encapsula las acciones existentes para cada uno de los módulos de
 * la aplicación.
 */
public class ArchivoActions {
	// Ctes de Identificacion de las Acciones

	/** TRANSFERENCIAS_MODULE - Acciones 1XX */
	public static final int TRANSFERENCIAS_MODULE_ALTA_PREVISION_TRANSFERENCIA = 100;
	public static final int TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA = 101;
	// public static final int
	// TRANSFERENCIAS_MODULE_CREACION_LINEA_DETALLE_TRANSFERENCIA = 125;
	// public static final int
	// TRANSFERENCIAS_MODULE_ELIMINACION_LINEA_DETALLE_TRANSFERENCIA = 126;
	public static final int TRANSFERENCIAS_MODULE_CESION_PREVISION_TRANSFERENCIA = 102;
	public static final int TRANSFERENCIAS_MODULE_BORRADO_PREVISION_TRANSFERENCIA = 103;
	public static final int TRANSFERENCIAS_MODULE_CONSULTA_PREVISION_TRANSFERENCIA = 104;
	public static final int TRANSFERENCIAS_MODULE_ENVIO_PREVISION_TRANSFERENCIA = 105;
	public static final int TRANSFERENCIAS_MODULE_RECHAZO_PREVISION_TRANSFERENCIA = 106;
	public static final int TRANSFERENCIAS_MODULE_ACEPTACION_PREVISION_TRANSFERENCIA = 107;
	// public static final int
	// TRANSFERENCIAS_MODULE_INFORME_PREVISION_TRANSFERENCIA = 108;
	public static final int TRANSFERENCIAS_MODULE_CIERRE_PREVISION_TRANSFERENCIA = 109;
	public static final int TRANSFERENCIAS_MODULE_ALTA_RELACION_ENTREGA = 110;
	public static final int TRANSFERENCIAS_MODULE_BORRADO_RELACION_ENTREGA = 111;
	public static final int TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA = 112;
	// public static final int
	// TRANSFERENCIAS_MODULE_INCORPORACION_UNIDAD_DOCUMENTAL = 127;
	// public static final int TRANSFERENCIAS_MODULE_BORRADO_UNIDAD_DOCUMENTAL =
	// 128;
	// public static final int
	// TRANSFERENCIAS_MODULE_MODIFICACION_UNIDAD_DOCUMENTAL = 112;
	public static final int TRANSFERENCIAS_MODULE_CONSULTA_RELACION_ENTREGA = 113;
	public static final int TRANSFERENCIAS_MODULE_ENVIO_RELACION_ENTREGA = 114;
	public static final int TRANSFERENCIAS_MODULE_RECEPCION_RELACION_ENTREGA = 115;
	public static final int TRANSFERENCIAS_MODULE_GUARDAR_COTEJO_RELACION_ENTREGA = 116;
	public static final int TRANSFERENCIAS_MODULE_COTEJO_RELACION_ENTREGA = 117;
	public static final int TRANSFERENCIAS_MODULE_CORRECION_RELACION_ENTREGA = 118;
	public static final int TRANSFERENCIAS_MODULE_VALIDACION_RELACION_ENTREGA = 119;
	public static final int TRANSFERENCIAS_MODULE_CESION_RELACION_ENTREGA = 120;
	public static final int TRANSFERENCIAS_MODULE_CESION_ARCHIVO_RELACION_ENTREGA = 121;
	// public static final int TRANSFERENCIAS_MODULE_UBICACION_UDOCS = 122;
	// public static final int TRANSFERENCIAS_MODULE_RESERVA_UDOCS = 123;

	/** DEPOSITOS_MODULE - Acciones 2XX */
	public static final int DEPOSITOS_MODULE_ALTA_ELEMENTO_DEPOSITO = 203;
	public static final int DEPOSITOS_MODULE_MODIFICACION_ELEMENTO_DEPOSITO = 204;
	public static final int DEPOSITOS_MODULE_ELIMINACION_ELEMENTO_DEPOSITO = 205;
	public static final int DEPOSITOS_MODULE_RESERVA_HUECO = 206;
	public static final int DEPOSITOS_MODULE_MODIFICACION_ESTADO_HUECO = 207;
	public static final int DEPOSITOS_MODULE_UBICAR_RELACION = 208;
	public static final int DEPOSITOS_MODULE_REUBICAR_UI = 209;
	public static final int DEPOSITOS_MODULE_CONSULTA_DEPOSITO = 210;
	public static final int DEPOSITOS_MODULE_ALTA_DEPOSITO_ELECTRONICO = 211;
	public static final int DEPOSITOS_MODULE_MODIFICACION_DEPOSITO_ELECTRONICO = 212;
	public static final int DEPOSITOS_MODULE_BAJA_DEPOSITO_ELECTRONICO = 213;
	public static final int DEPOSITOS_MODULE_REUBICAR_UDOCS = 214;
	public static final int DEPOSITOS_MODULE_ALTA_FORMATO = 215;
	public static final int DEPOSITOS_MODULE_MODIFICACION_FORMATO = 216;
	public static final int DEPOSITOS_MODULE_BAJA_FORMATO = 217;

	// public static final int DEPOSITOS_MODULE_PASAR_HUECO_A_INUTILIZABLE =
	// 208;
	// public static final int DEPOSITOS_MODULE_PASAR_HUECO_A_LIBRE = 209;
	// public static final int DEPOSITOS_MODULE_LIBERACION_HUECOS = 211;
	// public static final int DEPOSITOS_MODULE_REUBICACION_UNIDADES = 212;

	/** PRESTAMOS_MODULE - Acciones 3XX */
	public static final int SERVICIOS_MODULE_CONSULTA_PRESTAMO = 300;
	// public static final int SERVICIOS_MODULE_BUSQUEDA_UDOCS_PRESTAMO = 301;
	// public static final int SERVICIOS_MODULE_BUSQUEDA_UDOCS = 301;
	public static final int SERVICIOS_MODULE_ALTA_PRESTAMO = 303;
	public static final int SERVICIOS_MODULE_EDICION_PRESTAMO = 304;
	public static final int SERVICIOS_MODULE_ENVIO_SOLICITUD_PRESTAMO = 305;
	public static final int SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_PRESTAMO = 306;
	public static final int SERVICIOS_MODULE_ENTREGA_UDOCS_PRESTAMO = 308;
	public static final int SERVICIOS_MODULE_SOLICITUD_PRORROGA_UDOCS = 311;
	public static final int SERVICIOS_MODULE_GESTION_PRORROGA_UDOCS = 312;
	public static final int SERVICIOS_MODULE_RECLAMACION_UDOCS_PRESTAMO = 314;
	public static final int SERVICIOS_MODULE_CESION_PRESTAMO = 316;
	public static final int SERVICIOS_MODULE_CONSULTA_CONSULTA = 317;
	// public static final int SERVICIOS_MODULE_BUSQUEDA_UDOCS_CONSULTA = 318;
	public static final int SERVICIOS_MODULE_ALTA_CONSULTA = 320;
	public static final int SERVICIOS_MODULE_EDICION_CONSULTA = 321;
	public static final int SERVICIOS_MODULE_ENVIO_SOLICITUD_CONSULTA = 322;
	public static final int SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_CONSULTA = 323;
	public static final int SERVICIOS_MODULE_ENTREGA_UDOCS_CONSULTA = 325;
	public static final int SERVICIOS_MODULE_DEVOLUCION_UDOCS = 326;
	public static final int SERVICIOS_MODULE_ALTA_UDOC = 327;
	public static final int SERVICIOS_MODULE_BAJA_UDOC = 328;
	public static final int SERVICIOS_MODULE_BAJA_PRESTAMO = 329;
	public static final int SERVICIOS_MODULE_BAJA_CONSULTA = 330;
	public static final int SERVICIOS_MODULE_CESION_PRESTAMO_REVISION_DOC = 331;

	/** DESCRIPCION_MODULE - Acciones 4XX */
	public static final int DESCRIPCION_MODULE_BUSQUEDA_FONDOS = 400;
	public static final int DESCRIPCION_MODULE_BUSQUEDA_AUTORIDADES = 401;
	public static final int DESCRIPCION_MODULE_CREACION_CAMPO = 402;
	public static final int DESCRIPCION_MODULE_MODIFICACION_CAMPO = 403;
	public static final int DESCRIPCION_MODULE_ELIMINACION_CAMPO = 404;
	public static final int DESCRIPCION_MODULE_CREACION_FICHA_ELEMENTO = 405;
	public static final int DESCRIPCION_MODULE_CONSULTA_FICHA_ELEMENTO = 406;
	public static final int DESCRIPCION_MODULE_EDICION_FICHA_ELEMENTO = 407;
	public static final int DESCRIPCION_MODULE_CREACION_FICHA_DESCRIPTOR = 408;
	public static final int DESCRIPCION_MODULE_CONSULTA_FICHA_DESCRIPTOR = 409;
	public static final int DESCRIPCION_MODULE_EDICION_FICHA_DESCRIPTOR = 410;
	public static final int DESCRIPCION_MODULE_ALTA_LISTA_VALORES = 411;
	public static final int DESCRIPCION_MODULE_MODIFICACION_LISTA_VALORES = 412;
	public static final int DESCRIPCION_MODULE_BAJA_LISTA_VALORES = 413;
	public static final int DESCRIPCION_MODULE_ALTA_LISTA_DESCRIPTORES = 414;
	public static final int DESCRIPCION_MODULE_MODIFICACION_LISTA_DESCRIPTORES = 415;
	public static final int DESCRIPCION_MODULE_BAJA_LISTA_DESCRIPTORES = 416;
	public static final int DESCRIPCION_MODULE_ALTA_DESCRIPTOR = 417;
	public static final int DESCRIPCION_MODULE_MODIFICACION_DESCRIPTOR = 418;
	public static final int DESCRIPCION_MODULE_BAJA_DESCRIPTOR = 419;
	public static final int DESCRIPCION_MODULE_ALTA_VALOR_VALIDACION = 420;
	public static final int DESCRIPCION_MODULE_MODIFICACION_VALOR_VALIDACION = 421;
	public static final int DESCRIPCION_MODULE_BAJA_VALOR_VALIDACION = 422;
	public static final int DESCRIPCION_MODULE_REEMPLAZO_CAMPO_ELEMENTO = 423;
	public static final int DESCRIPCION_MODULE_UNIFICAR_DESCRIPTORES = 424;

	/** FONDOS_MODULE - CUADRO DE CLASIFICACION - Acciones 5XX */
	public static final int FONDOS_MODULE_ALTA_ELEMENTO = 500;
	public static final int FONDOS_MODULE_MODIFICACION_ELEMENTO = 501;
	public static final int FONDOS_MODULE_BAJA_ELEMENTO = 502;
	public static final int FONDOS_MODULE_PUBLICACION_ELEMENTO = 503;
	public static final int FONDOS_MODULE_SOLICITUD_ALTA_SERIE = 506;
	public static final int FONDOS_MODULE_AUTORIZACION_DENEGACION_SERIE = 507;
	public static final int FONDOS_MODULE_MODIFICACION_SERIE = 509;
	public static final int FONDOS_MODULE_BAJA_SERIE = 510;
	public static final int FONDOS_MODULE_MOVER_ELEMENTO = 511;

	public static final int FONDOS_MODULE_IDENTIFICACION_SERIE = 600;
	public static final int FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE = 601;
	public static final int FONDOS_MODULE_GESTION_SOLICITUDES_SERIE = 602;
	public static final int FONDOS_MODULE_CONSULTA_SOLICITUDES_SERIE = 603;
	public static final int FONDOS_MODULE_CEDER_CONTROL_SERIE = 617;
	public static final int FONDOS_MODULE_CONSULTA_CUADRO = 618;

	public static final int FONDOS_MODULE_SOLICITUD_VALIDACION_VALORACION = 631;
	public static final int FONDOS_MODULE_RECHAZO_VALIDACION_VALORACION = 632;
	public static final int FONDOS_MODULE_AUTORIZACION_VALIDACION_VALORACION = 633;
	public static final int FONDOS_MODULE_BAJA_VALORACION = 634;
	public static final int FONDOS_MODULE_ALTA_VALORACION = 635;
	public static final int FONDOS_MODULE_ACTUALIZAR_VALORACION = 636;
	public static final int FONDOS_MODULE_AUTORIZACION_EVALUACION_VALORACION = 637;
	public static final int FONDOS_MODULE_RECHAZO_EVALUACION_VALORACION = 638;
	public static final int FONDOS_MODULE_DICTAMEN_VALORACION = 639;
	public static final int FONDOS_MODULE_CERRAR_DICTAMEN_VALORACION = 640;
	public static final int FONDOS_MODULE_IMPRIMIR_VALORACION = 641;

	public static final int FONDOS_MODULE_ALTA_ELIMINACION = 642;
	public static final int FONDOS_MODULE_ACTUALIZACION_ELIMINACION = 643;
	public static final int FONDOS_MODULE_SOLICITAR_APROBACION_ELIMINACION = 644;
	public static final int FONDOS_MODULE_APROBAR_ELIMINACION = 645;
	public static final int FONDOS_MODULE_RECHAZAR_ELIMINACION = 646;
	public static final int FONDOS_MODULE_EJECUTAR_ELIMINACION = 647;
	public static final int FONDOS_MODULE_DESTRUIR_FISICAMENTE = 648;
	public static final int FONDOS_MODULE_BAJA_UDOC = 649;
	public static final int FONDOS_MODULE_FINALIZAR_ELIMINACION = 650;
	public static final int FONDOS_MODULE_BAJA_UDOCENUI = 651;
	public static final int FONDOS_MODULE_BAJA_UI = 652;
	public static final int FONDOS_MODULE_BAJA_ELIMINACION = 653;
	// public static final int FONDOS_MODULE_BAJA_HUECO = 654;
	public static final int FONDOS_MODULE_IMPRIMIR_SELECCION = 655;

	public static final int FONDOS_MODULE_EDITAR_VALORACIONES = 656;
	public static final int FONDOS_MODULE_EDITAR_SELECCIONES = 657;

	public static final int FONDOS_MODULE_ALTA_INGRESO_DIRECTO = 658;
	public static final int FONDOS_MODULE_EDICION_INGRESO_DIRECTO = 659;
	public static final int FONDOS_MODULE_BORRADO_INGRESO_DIRECTO = 660;
	public static final int FONDOS_MODULE_SELECCION_UBICACION_INGRESO_DIRECTO = 661;
	public static final int FONDOS_MODULE_SIGNATURACION_UBICACION_VALIDACION_INGRESO_DIRECTO = 662;
	public static final int FONDOS_MODULE_CREAR_UDOCS_FRACCION_SERIE = 663;
	public static final int FONDOS_MODULE_BORRADO_DIVISIONFS = 664;
	public static final int FONDOS_MODULE_ELIMINACION_MARCA_CONSERVADA_UDOCS = 665;

	/** USUARIOS_MODULE - Acciones 7XX */
	public static final int USUARIOS_MODULE_ALTA_USUARIO = 700;
	public static final int USUARIOS_MODULE_MODIFICACION_USUARIO = 701;
	public static final int USUARIOS_MODULE_ELIMINACION_USUARIO = 702;
	public static final int USUARIOS_MODULE_ASIGNACION_ROLE = 703;
	public static final int USUARIOS_MODULE_DEASIGNACION_ROLE = 704;
	public static final int USUARIOS_MODULE_ASIGNACION_GRUPO = 705;
	public static final int USUARIOS_MODULE_DEASIGNACION_GRUPO = 706;

	/** DOCUMENTOS_VITALES_MODULE - Acciones 8XX */
	public static final int DOCUMENTOS_VITALES_MODULE_ALTA_TIPO = 800;
	public static final int DOCUMENTOS_VITALES_MODULE_MODIFICACION_TIPO = 801;
	public static final int DOCUMENTOS_VITALES_MODULE_ELIMINACION_TIPO = 802;
	public static final int DOCUMENTOS_VITALES_MODULE_ALTA = 803;
	public static final int DOCUMENTOS_VITALES_MODULE_MODIFICACION = 804;
	public static final int DOCUMENTOS_VITALES_MODULE_ELIMINACION = 805;
	public static final int DOCUMENTOS_VITALES_MODULE_VALIDACION = 806;
	public static final int DOCUMENTOS_VITALES_MODULE_RECHAZO = 807;
	public static final int DOCUMENTOS_VITALES_MODULE_PASO_A_HISTORICO = 808;
	public static final int DOCUMENTOS_VITALES_MODULE_CONSULTA = 809;

	/** EXPLOTACION_MODULE - Acciones 9XX */

	/** DOCUMENTOS_ELECTRONICOS_MODULE - Acciones 10XX */
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_CLASIFICADOR = 1000;
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_CLASIFICADOR = 1001;
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_CLASIFICADOR = 1002;
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_DOCUMENTO = 1003;
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_DOCUMENTO = 1004;
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_DOCUMENTO = 1005;

	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_INSERTAR_TAREA = 1006;
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_ELIMINAR_TAREA = 1007;
	public static final int DOCUMENTOS_ELECTRONICOS_MODULE_CAMBIAR_ESTADO_TAREA = 1008;

	/** AUDITORIA_MODULE - Acciones 11XX */
	public static final int AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ACTION = 1100;
	/**
	 * public static final int AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_USER =
	 * 1101;
	 */
	public static final int AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_GROUP = 1102;
	/**
	 * public static final int AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ROL =
	 * 1103;
	 */
	public static final int AUDITORIA_MODULE_CONSULTAS = 1104;

	/** LOGIN_MODULE - Acciones 12XX */
	public static final int SISTEMA_MODULE_LOGIN_OK = 1200;
	public static final int SISTEMA_MODULE_LOGIN_FAIL = 1201;
	public static final int SISTEMA_MODULE_LOGOUT_OK = 1202;

	/** GESTION DE SALAS */
	public static final int SALAS_MODULE_VER_EDIFICIOS = 1400;
	public static final int SALAS_MODULE_VER_EDIFICIO = 1401;
	public static final int SALAS_MODULE_ALTA_EDIFICIO = 1402;
	public static final int SALAS_MODULE_EDICION_EDIFICIO = 1403;
	public static final int SALAS_MODULE_ELIMINAR_EDIFICIO = 1404;

	public static final int SALAS_MODULE_VER_SALA = 1410;
	public static final int SALAS_MODULE_VER_SALAS = 1411;
	public static final int SALAS_MODULE_ALTA_SALA = 1412;
	public static final int SALAS_MODULE_EDICION_SALA = 1413;
	public static final int SALAS_MODULE_ELIMINAR_SALA = 1414;

	public static final int SALAS_MODULE_VER_MESA = 1420;
	public static final int SALAS_MODULE_VER_MESAS = 1421;
	public static final int SALAS_MODULE_ALTA_MESA = 1422;
	public static final int SALAS_MODULE_EDICION_MESA = 1423;
	public static final int SALAS_MODULE_ELIMINAR_MESA = 1424;

	public static final int SALAS_MODULE_VER_USUARIO = 1430;
	public static final int SALAS_MODULE_VER_USUARIOS = 1431;
	public static final int SALAS_MODULE_ALTA_USUARIO = 1432;
	public static final int SALAS_MODULE_EDICION_USUARIO = 1433;
	public static final int SALAS_MODULE_ELIMINAR_USUARIO = 1434;

	public static final int SALAS_MODULE_VER_REGISTRO = 1440;
	public static final int SALAS_MODULE_VER_REGISTROS = 1441;
	public static final int SALAS_MODULE_ALTA_REGISTRO = 1442;
	public static final int SALAS_MODULE_EDICION_REGISTRO = 1443;
	public static final int SALAS_MODULE_ELIMINAR_REGISTRO = 1444;

	// Nombres de las Acciones
	/** TRANSFERENCIAS_MODULE - Nombres */
	public static final String TRANSFERENCIAS_MODULE_ALTA_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.ALTA_PREVISION_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.EDICION_PREVISION_TRANSFERENCIA";
	// public static final String
	// TRANSFERENCIAS_MODULE_ALTA_LINEA_DETALLE_PREVISION_TRANSFERENCIA_NAME =
	// "nombreAccion.transferencias.ALTA_LINEA_DETALLE_PREVISION_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_MODULE_CESION_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.CESION_PREVISION_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_MODULE_BORRADO_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.BORRADO_PREVISION_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_MODULE_CONSULTA_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.CONSULTA_PREVISION_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_MODULE_ENVIO_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.ENVIO_PREVISION_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_MODULE_RECHAZO_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.RECHAZO_PREVISION_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_MODULE_ACEPTACION_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.ACEPTACION_PREVISION_TRANSFERENCIA";
	// public static final String
	// TRANSFERENCIAS_MODULE_INFORME_PREVISION_TRANSFERENCIA_NAME =
	// "Previsión: Informes";
	public static final String TRANSFERENCIAS_MODULE_CIERRE_PREVISION_TRANSFERENCIA_NAME = "nombreAccion.transferencias.CIERRE_PREVISION_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_MODULE_ALTA_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.ALTA_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_BORRADO_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.BORRADO_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.EDICION_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_CONSULTA_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.CONSULTA_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_ENVIO_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.ENVIO_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_RECEPCION_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.RECEPCION_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_GUARDAR_COTEJO_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.GUARDAR_COTEJO_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_COTEJO_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.COTEJO_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_CORRECION_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.CORRECION_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_VALIDACION_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.VALIDACION_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_CESION_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.CESION_RELACION_ENTREGA";
	public static final String TRANSFERENCIAS_MODULE_CESION_ARCHIVO_RELACION_ENTREGA_NAME = "nombreAccion.transferencias.CESION_ARCHIVO_RELACION_ENTREGA";
	// public static final String TRANSFERENCIAS_MODULE_RESERVA_UDOCS_NAME =
	// "Relación: Reserva de espacio";
	// public static final String TRANSFERENCIAS_MODULE_UBICACION_UDOCS_NAME =
	// "nombreAccion.transferencias.UBICACION_UDOCS";

	/** DEPOSITOS_MODULE - Nombres */
	public static final String DEPOSITOS_MODULE_ALTA_ELEMENTO_DEPOSITO_NAME = "nombreAccion.deposito.ALTA_ELEMENTO_DEPOSITO";
	public static final String DEPOSITOS_MODULE_MODIFICACION_ELEMENTO_DEPOSITO_NAME = "nombreAccion.deposito.MODIFICACION_ELEMENTO_DEPOSITO";
	public static final String DEPOSITOS_MODULE_ELIMINACION_ELEMENTO_DEPOSITO_NAME = "nombreAccion.deposito.ELIMINACION_ELEMENTO_DEPOSITO";
	public static final String DEPOSITOS_MODULE_RESERVA_HUECO_NAME = "nombreAccion.deposito.GESTION_RESERVA_ESPACIO";
	public static final String DEPOSITOS_MODULE_MODIFICACION_ESTADO_HUECO_NAME = "nombreAccion.deposito.MODIFICACION_ESTADO_HUECO";
	public static final String DEPOSITOS_MODULE_UBICAR_RELACION_NAME = "nombreAccion.deposito.UBICAR_RELACION";
	public static final String DEPOSITOS_MODULE_REUBICAR_UI_NAME = "nombreAccion.deposito.REUBICAR_UI";
	public static final String DEPOSITOS_MODULE_CONSULTA_DEPOSITO_NAME = "nombreAccion.deposito.CONSULTA_DEPOSITO";
	public static final String DEPOSITOS_MODULE_ALTA_DEPOSITO_ELECTRONICO_NAME = "nombreAccion.deposito.ALTA_DEPOSITO_ELECTRONICO";
	public static final String DEPOSITOS_MODULE_MODIFICACION_DEPOSITO_ELECTRONICO_NAME = "nombreAccion.deposito.MODIFICACION_DEPOSITO_ELECTRONICO";
	public static final String DEPOSITOS_MODULE_BAJA_DEPOSITO_ELECTRONICO_NAME = "nombreAccion.deposito.BAJA_DEPOSITO_ELECTRONICO";
	public static final String DEPOSITOS_MODULE_REUBICAR_UDOCS_NAME = "nombreAccion.deposito.REUBICAR_UDOCS";
	public static final String DEPOSITOS_MODULE_ALTA_FORMATO_NAME = "nombreAccion.deposito.DEPOSITOS_MODULE_ALTA_FORMATO";
	public static final String DEPOSITOS_MODULE_MODIFICACION_FORMATO_NAME = "nombreAccion.deposito.DEPOSITOS_MODULE_MODIFICACION_FORMATO_NAME";
	public static final String DEPOSITOS_MODULE_BAJA_FORMATO_NAME = "nombreAccion.deposito.DEPOSITOS_MODULE_BAJA_FORMATO_NAME";

	// public static final String
	// DEPOSITOS_MODULE_PASAR_HUECO_A_INUTILIZABLE_NAME = "Huecos: Inutilizar";
	// public static final String DEPOSITOS_MODULE_PASAR_HUECO_A_LIBRE_NAME =
	// "Huecos: Habilitar";
	// public static final String DEPOSITOS_MODULE_LIBERACION_HUECOS_NAME =
	// "Huecos: Liberar reserva";
	// public static final String DEPOSITOS_MODULE_REUBICACION_UNIDADES_NAME =
	// "Huecos: Reubicar";

	/** PRESTAMOS_MODULE - Nombres */
	public static final String SERVICIOS_MODULE_CONSULTA_PRESTAMO_NAME = "nombreAccion.servicios.CONSULTA_PRESTAMO";
	// public static final String SERVICIOS_MODULE_BUSQUEDA_UDOCS_NAME =
	// "nombreAccion.servicios.BUSQUEDA_UDOCS";
	public static final String SERVICIOS_MODULE_ALTA_PRESTAMO_NAME = "nombreAccion.servicios.ALTA_PRESTAMO";
	public static final String SERVICIOS_MODULE_EDICION_PRESTAMO_NAME = "nombreAccion.servicios.EDICION_PRESTAMO";
	public static final String SERVICIOS_MODULE_ENVIO_SOLICITUD_PRESTAMO_NAME = "nombreAccion.servicios.ENVIO_SOLICITUD_PRESTAMO";
	public static final String SERVICIOS_MODULE_ACEPTACION_SOLICITUD_PRESTAMO_NAME = "nombreAccion.servicios.ACEPTACION_SOLICITUD_PRESTAMO";
	public static final String SERVICIOS_MODULE_ENTREGA_UDOCS_NAME = "nombreAccion.servicios.ENTREGA_UDOCS";
	public static final String SERVICIOS_MODULE_SOLICITUD_PRORROGA_UDOCS_NAME = "nombreAccion.servicios.SOLICITUD_PRORROGA_UDOCS";
	public static final String SERVICIOS_MODULE_GESTION_PRORROGA_UDOCS_NAME = "nombreAccion.servicios.GESTION_PRORROGA_UDOCS";
	public static final String SERVICIOS_MODULE_RECLAMACION_UDOCS_NAME = "nombreAccion.servicios.RECLAMACION_UDOCS";
	public static final String SERVICIOS_MODULE_CESION_PRESTAMO_NAME = "nombreAccion.servicios.CESION_PRESTAMO";
	public static final String SERVICIOS_MODULE_CONSULTA_CONSULTA_NAME = "nombreAccion.servicios.CONSULTA_CONSULTA";
	public static final String SERVICIOS_MODULE_CESION_PRESTAMO_REVISION_DOC_NAME = "nombreAccion.servicios.CESION_PRESTAMO_REVISION_DOC";
	// public static final String SERVICIOS_MODULE_BUSQUEDA_UDOCS_CONSULTA_NAME
	// = "nombreAccion.servicios.BUSQUEDA_UDOCS_CONSULTA";
	public static final String SERVICIOS_MODULE_ALTA_CONSULTA_NAME = "nombreAccion.servicios.ALTA_CONSULTA";
	public static final String SERVICIOS_MODULE_EDICION_CONSULTA_NAME = "nombreAccion.servicios.EDICION_CONSULTA";
	public static final String SERVICIOS_MODULE_ENVIO_SOLICITUD_CONSULTA_NAME = "nombreAccion.servicios.ENVIO_SOLICITUD_CONSULTA";
	public static final String SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_CONSULTA_NAME = "nombreAccion.servicios.AUTORIZACION_SOLICITUD_CONSULTA";
	public static final String SERVICIOS_MODULE_ENTREGA_UDOCS_CONSULTA_NAME = "nombreAccion.servicios.ENTREGA_UDOCS_CONSULTA";
	public static final String SERVICIOS_MODULE_DEVOLUCION_UDOCS_NAME = "nombreAccion.servicios.DEVOLUCION_UDOCS";
	public static final String SERVICIOS_MODULE_ALTA_UDOC_NAME = "nombreAccion.servicios.ALTA_UDOC";
	public static final String SERVICIOS_MODULE_BAJA_UDOC_NAME = "nombreAccion.servicios.BAJA_UDOC";
	public static final String SERVICIOS_MODULE_BAJA_PRESTAMO_NAME = "nombreAccion.servicios.BAJA_PRESTAMO";
	public static final String SERVICIOS_MODULE_BAJA_CONSULTA_NAME = "nombreAccion.servicios.BAJA_CONSULTA";

	/** DESCRIPCION_MODULE - Nombres */
	public static final String DESCRIPCION_MODULE_BUSQUEDA_FONDOS_NAME = "nombreAccion.descripcion.BUSQUEDA_FONDOS";
	public static final String DESCRIPCION_MODULE_BUSQUEDA_AUTORIDADES_NAME = "nombreAccion.descripcion.BUSQUEDA_AUTORIDADES";
	public static final String DESCRIPCION_MODULE_CREACION_CAMPO_NAME = "nombreAccion.descripcion.CREACION_CAMPO";
	public static final String DESCRIPCION_MODULE_MODIFICACION_CAMPO_NAME = "nombreAccion.descripcion.MODIFICACION_CAMPO";
	public static final String DESCRIPCION_MODULE_ELIMINACION_CAMPO_NAME = "nombreAccion.descripcion.ELIMINACION_CAMPO";
	public static final String DESCRIPCION_MODULE_CREACION_FICHA_ELEMENTO_NAME = "nombreAccion.descripcion.CREACION_FICHA_ELEMENTO";
	public static final String DESCRIPCION_MODULE_CONSULTA_FICHA_ELEMENTO_NAME = "nombreAccion.descripcion.CONSULTA_FICHA_ELEMENTO";
	public static final String DESCRIPCION_MODULE_EDICION_FICHA_ELEMENTO_NAME = "nombreAccion.descripcion.EDICION_FICHA_ELEMENTO";
	public static final String DESCRIPCION_MODULE_CREACION_FICHA_DESCRIPTOR_NAME = "nombreAccion.descripcion.CREACION_FICHA_DESCRIPTOR";
	public static final String DESCRIPCION_MODULE_CONSULTA_FICHA_DESCRIPTOR_NAME = "nombreAccion.descripcion.CONSULTA_FICHA_DESCRIPTOR";
	public static final String DESCRIPCION_MODULE_EDICION_FICHA_DESCRIPTOR_NAME = "nombreAccion.descripcion.EDICION_FICHA_DESCRIPTOR";
	public static final String DESCRIPCION_MODULE_ALTA_LISTA_VALORES_NAME = "nombreAccion.descripcion.ALTA_LISTA_VALORES";
	public static final String DESCRIPCION_MODULE_MODIFICACION_LISTA_VALORES_NAME = "nombreAccion.descripcion.MODIFICACION_LISTA_VALORES";
	public static final String DESCRIPCION_MODULE_BAJA_LISTA_VALORES_NAME = "nombreAccion.descripcion.BAJA_LISTA_VALORES";
	public static final String DESCRIPCION_MODULE_ALTA_LISTA_DESCRIPTORES_NAME = "nombreAccion.descripcion.ALTA_LISTA_DESCRIPTORES";
	public static final String DESCRIPCION_MODULE_MODIFICACION_LISTA_DESCRIPTORES_NAME = "nombreAccion.descripcion.MODIFICACION_LISTA_DESCRIPTORES";
	public static final String DESCRIPCION_MODULE_BAJA_LISTA_DESCRIPTORES_NAME = "nombreAccion.descripcion.BAJA_LISTA_DESCRIPTORES";
	public static final String DESCRIPCION_MODULE_ALTA_DESCRIPTOR_NAME = "nombreAccion.descripcion.ALTA_DESCRIPTOR";
	public static final String DESCRIPCION_MODULE_MODIFICACION_DESCRIPTOR_NAME = "nombreAccion.descripcion.MODIFICACION_DESCRIPTOR";
	public static final String DESCRIPCION_MODULE_BAJA_DESCRIPTOR_NAME = "nombreAccion.descripcion.BAJA_DESCRIPTOR";
	public static final String DESCRIPCION_MODULE_ALTA_VALOR_VALIDACION_NAME = "nombreAccion.descripcion.ALTA_VALOR_VALIDACION";
	public static final String DESCRIPCION_MODULE_MODIFICACION_VALOR_VALIDACION_NAME = "nombreAccion.descripcion.MODIFICACION_VALOR_VALIDACION";
	public static final String DESCRIPCION_MODULE_BAJA_VALOR_VALIDACION_NAME = "nombreAccion.descripcion.BAJA_VALOR_VALIDACION";
	public static final String DESCRIPCION_MODULE_REEMPLAZO_CAMPO_ELEMENTO_NAME = "nombreAccion.descripcion.REEMPLAZO_CAMPO_ELEMENTO";
	public static final String DESCRIPCION_MODULE_UNIFICAR_DESCRIPTORES_NAME = "nombreAccion.descripcion.UNIFICAR_DESCRIPTORES";

	/** FONDOS_MODULE - CUADRO DE CLASIFICACION - Nombres */

	public static final String FONDOS_MODULE_ALTA_ELEMENTO_NAME = "nombreAccion.cf.ALTA_ELEMENTO";
	public static final String FONDOS_MODULE_MODIFICACION_ELEMENTO_NAME = "nombreAccion.cf.MODIFICACION_ELEMENTO";
	public static final String FONDOS_MODULE_BAJA_ELEMENTO_NAME = "nombreAccion.cf.BAJA_ELEMENTO";
	public static final String FONDOS_MODULE_PUBLICACION_ELEMENTO_NAME = "nombreAccion.cf.PUBLICACION_ELEMENTO";
	public static final String FONDOS_MODULE_SOLICITUD_ALTA_SERIE_NAME = "nombreAccion.cf.SOLICITUD_ALTA_SERIE";
	public static final String FONDOS_MODULE_AUTORIZACION_DENEGACION_SERIE_NAME = "nombreAccion.cf.AUTORIZACION_DENEGACION_SERIE";
	public static final String FONDOS_MODULE_MODIFICACION_SERIE_NAME = "nombreAccion.cf.MODIFICACION_SERIE";
	public static final String FONDOS_MODULE_BAJA_SERIE_NAME = "nombreAccion.cf.BAJA_SERIE";
	public static final String FONDOS_MODULE_IDENTIFICACION_SERIE_NAME = "nombreAccion.cf.IDENTIFICACION_SERIE";
	public static final String FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE_NAME = "nombreAccion.cf.SOLICITUD_CAMBIOS_SERIE";
	public static final String FONDOS_MODULE_CONSULTA_SOLICITUDES_SERIE_NAME = "nombreAccion.cf.CONSULTA_SOLICITUDES_SERIE";
	public static final String FONDOS_MODULE_GESTION_SOLICITUDES_SERIE_NAME = "nombreAccion.cf.GESTION_SOLICITUDES_SERIE";
	public static final String FONDOS_MODULE_CEDER_CONTROL_SERIE_NAME = "nombreAccion.cf.CEDER_CONTROL_SERIE";
	public static final String FONDOS_MODULE_CONSULTA_CUADRO_NAME = "nombreAccion.cf.CONSULTA_CUADRO";
	public static final String FONDOS_MODULE_SOLICITUD_VALIDACION_VALORACION_NAME = "nombreAccion.cf.SOLICITUD_VALIDACION_VALORACION";
	public static final String FONDOS_MODULE_RECHAZO_VALIDACION_VALORACION_NAME = "nombreAccion.cf.RECHAZO_VALIDACION_VALORACION";
	public static final String FONDOS_MODULE_AUTORIZACION_VALIDACION_VALORACION_NAME = "nombreAccion.cf.AUTORIZACION_VALIDACION_VALORACION";
	public static final String FONDOS_MODULE_BAJA_VALORACION_NAME = "nombreAccion.cf.BAJA_VALORACION";
	public static final String FONDOS_MODULE_ALTA_VALORACION_NAME = "nombreAccion.cf.ALTA_VALORACION";
	public static final String FONDOS_MODULE_ACTUALIZAR_VALORACION_NAME = "nombreAccion.cf.ACTUALIZAR_VALORACION";
	public static final String FONDOS_MODULE_AUTORIZACION_EVALUACION_VALORACION_NAME = "nombreAccion.cf.AUTORIZACION_EVALUACION_VALORACION";
	public static final String FONDOS_MODULE_RECHAZO_EVALUACION_VALORACION_NAME = "nombreAccion.cf.RECHAZO_EVALUACION_VALORACION";
	public static final String FONDOS_MODULE_DICTAMEN_VALORACION_NAME = "nombreAccion.cf.DICTAMEN_VALORACION";
	public static final String FONDOS_MODULE_CERRAR_DICTAMEN_VALORACION_NAME = "nombreAccion.cf.CERRAR_DICTAMEN_VALORACION";
	public static final String FONDOS_MODULE_IMPRIMIR_VALORACION_NAME = "nombreAccion.cf.IMPRIMIR_VALORACION";
	public static final String FONDOS_MODULE_EDITAR_VALORACIONES_NAME = "nombreAccion.cf.EDITAR_VALORACIONES";
	public static final String FONDOS_MODULE_IMPRIMIR_SELECCION_NAME = "nombreAccion.cf.IMPRIMIR_SELECCION";
	public static final String FONDOS_MODULE_ALTA_ELIMINACION_NAME = "nombreAccion.cf.ALTA_ELIMINACION";
	public static final String FONDOS_MODULE_BAJA_ELIMINACION_NAME = "nombreAccion.cf.BAJA_ELIMINACION";
	public static final String FONDOS_MODULE_ACTUALIZACION_ELIMINACION_NAME = "nombreAccion.cf.ACTUALIZACION_ELIMINACION";
	public static final String FONDOS_MODULE_SOLICITAR_APROBACION_ELIMINACION_NAME = "nombreAccion.cf.SOLICITAR_APROBACION_ELIMINACION";
	public static final String FONDOS_MODULE_APROBAR_ELIMINACION_NAME = "nombreAccion.cf.APROBAR_ELIMINACION";
	public static final String FONDOS_MODULE_RECHAZAR_ELIMINACION_NAME = "nombreAccion.cf.RECHAZAR_ELIMINACION";
	public static final String FONDOS_MODULE_EJECUTAR_ELIMINACION_NAME = "nombreAccion.cf.EJECUTAR_ELIMINACION";
	public static final String FONDOS_MODULE_DESTRUIR_FISICAMENTE_NAME = "nombreAccion.cf.DESTRUIR_FISICAMENTE";
	public static final String FONDOS_MODULE_BAJA_UDOC_NAME = "nombreAccion.cf.BAJA_UDOC";
	public static final String FONDOS_MODULE_FINALIZAR_ELIMINACION_NAME = "nombreAccion.cf.FINALIZAR_ELIMINACION";
	public static final String FONDOS_MODULE_BAJA_UDOCENUI_NAME = "nombreAccion.cf.BAJA_UDOCENUI";
	public static final String FONDOS_MODULE_BAJA_UI_NAME = "nombreAccion.cf.BAJA_UI";
	// public static final String FONDOS_MODULE_BAJA_HUECO_NAME =
	// "Eliminación: Baja de Hueco";
	public static final String FONDOS_MODULE_EDITAR_SELECCIONES_NAME = "nombreAccion.cf.EDITAR_SELECCIONES";
	public static final String FONDOS_MODULE_MOVER_ELEMENTO_NAME = "nombreAccion.cf.MOVER_ELEMENTO";
	public static final String FONDOS_MODULE_ELIMINAR_MARCA_CONSERVADA_NAME = "nombreAccion.cf.ELIMINAR_MARCA_CONSERVADA";

	public static final String FONDOS_MODULE_ALTA_INGRESO_DIRECTO_NAME = "nombreAccion.cf.ALTA_INGRESO_DIRECTO";
	public static final String FONDOS_MODULE_EDICION_INGRESO_DIRECTO_NAME = "nombreAccion.cf.EDICION_INGRESO_DIRECTO";
	public static final String FONDOS_MODULE_BORRADO_INGRESO_DIRECTO_NAME = "nombreAccion.cf.BORRADO_INGRESO_DIRECTO";
	public static final String FONDOS_MODULE_SELECCION_UBICACION_INGRESO_DIRECTO_NAME = "nombreAccion.cf.SELECCION_UBICACION_INGRESO_DIRECTO";
	public static final String FONDOS_MODULE_SIGNATURACION_UBICACION_VALIDACION_INGRESO_DIRECTO_NAME = "nombreAccion.cf.SIGNATURACION_UBICACION_VALIDACION_INGRESO_DIRECTO";
	public static final String FONDOS_MODULE_CREAR_UDOCS_FRACCION_SERIE_NAME = "nombreAccion.cf.CREAR_UDOCS_FRACCION_SERIE";
	/** USUARIOS_MODULE - Nombres */

	public static final String USUARIOS_MODULE_ALTA_USUARIO_NAME = "nombreAccion.usuarios.ALTA_USUARIO";
	public static final String USUARIOS_MODULE_MODIFICACION_USUARIO_NAME = "nombreAccion.usuarios.MODIFICACION_USUARIO";
	public static final String USUARIOS_MODULE_ELIMINACION_USUARIO_NAME = "nombreAccion.usuarios.ELIMINACION_USUARIO";
	public static final String USUARIOS_MODULE_ASIGNACION_GRUPO_NAME = "nombreAccion.usuarios.ASIGNACION_GRUPO";
	public static final String USUARIOS_MODULE_DEASIGNACION_GRUPO_NAME = "nombreAccion.usuarios.DEASIGNACION_GRUPO";
	public static final String USUARIOS_MODULE_ASIGNACION_ROLE_NAME = "nombreAccion.usuarios.ASIGNACION_ROLE";
	public static final String USUARIOS_MODULE_DEASIGNACION_ROLE_NAME = "nombreAccion.usuarios.DEASIGNACION_ROLE";

	/** DOCUMENTOS_VITALES_MODULE - Nombres */
	public static final String DOCUMENTOS_VITALES_MODULE_ALTA_TIPO_NAME = "nombreAccion.docVitales.ALTA_TIPO";
	public static final String DOCUMENTOS_VITALES_MODULE_MODIFICACION_TIPO_NAME = "nombreAccion.docVitales.MODIFICACION_TIPO";
	public static final String DOCUMENTOS_VITALES_MODULE_ELIMINACION_TIPO_NAME = "nombreAccion.docVitales.ELIMINACION_TIPO";
	public static final String DOCUMENTOS_VITALES_MODULE_ALTA_NAME = "nombreAccion.docVitales.ALTA";
	public static final String DOCUMENTOS_VITALES_MODULE_MODIFICACION_NAME = "nombreAccion.docVitales.MODIFICACION";
	public static final String DOCUMENTOS_VITALES_MODULE_ELIMINACION_NAME = "nombreAccion.docVitales.ELIMINACION";
	public static final String DOCUMENTOS_VITALES_MODULE_VALIDACION_NAME = "nombreAccion.docVitales.VALIDACION";
	public static final String DOCUMENTOS_VITALES_MODULE_RECHAZO_NAME = "nombreAccion.docVitales.RECHAZO";
	public static final String DOCUMENTOS_VITALES_MODULE_PASO_A_HISTORICO_NAME = "nombreAccion.docVitales.PASO_A_HISTORICO";
	public static final String DOCUMENTOS_VITALES_MODULE_CONSULTA_NAME = "nombreAccion.docVitales.CONSULTA";

	/** EXPLOTACION_MODULE - Nombres */

	/** DOCUMENTOS_ELECTRONICOS_MODULE - Nombres */

	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_CLASIFICADOR_NAME = "nombreAccion.docElectronicos.ALTA_CLASIFICADOR";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_CLASIFICADOR_NAME = "nombreAccion.docElectronicos.MODIFICACION_CLASIFICADOR";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_CLASIFICADOR_NAME = "nombreAccion.docElectronicos.BAJA_CLASIFICADOR";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_DOCUMENTO_NAME = "nombreAccion.docElectronicos.ALTA_DOCUMENTO";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_DOCUMENTO_NAME = "nombreAccion.docElectronicos.MODIFICACION_DOCUMENTO";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_DOCUMENTO_NAME = "nombreAccion.docElectronicos.BAJA_DOCUMENTO";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_INSERTAR_TAREA_NAME = "nombreAccion.docElectronicos.INSERTAR_TAREA";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_ELIMINAR_TAREA_NAME = "nombreAccion.docElectronicos.ELIMINAR_TAREA";
	public static final String DOCUMENTOS_ELECTRONICOS_MODULE_CAMBIAR_ESTADO_TAREA_NAME = "nombreAccion.docElectronicos.CAMBIAR_ESTADO_TAREA";

	/** AUDITORIA_MODULE - Nombres */
	public static final String AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ACTION_NAME = "nombreAccion.auditoria.MODIFICACION_LOGLEVEL_ACTION";
	/**
	 * public static final String
	 * AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_USER_NAME =
	 * "Auditoría: Establecer Nivel de Usuario";";
	 */
	public static final String AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_GROUP_NAME = "nombreAccion.auditoria.MODIFICACION_LOGLEVEL_GROUP";
	/**
	 * public static final String
	 * AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ROL_NAME =
	 * "Auditoría: Establecer Nivel de Rol";";
	 */
	public static final String AUDITORIA_MODULE_CONSULTAS_NAME = "nombreAccion.auditoria.CONSULTAS";

	/** LOGIN_MODULE - Nombre */
	public static final String SISTEMA_MODULE_LOGIN_OK_NAME = "nombreAccion.cAcceso.LOGIN_OK";
	public static final String SISTEMA_MODULE_LOGIN_FAIL_NAME = "nombreAccion.cAcceso.LOGIN_FAIL";
	public static final String SISTEMA_MODULE_LOGOUT_OK_NAME = "nombreAccion.cAcceso.LOGOUT_OK";

	/** GESTION DE SALAS */
	public static final String SALAS_MODULE_VER_EDIFICIOS_NAME = "nombreAccion.salas.SALAS_MODULE_VER_EDIFICIOS";
	public static final String SALAS_MODULE_VER_EDIFICIO_NAME = "nombreAccion.salas.SALAS_MODULE_VER_EDIFICIO";
	public static final String SALAS_MODULE_ALTA_EDIFICIO_NAME = "nombreAccion.salas.SALAS_MODULE_ALTA_EDIFICIO";
	public static final String SALAS_MODULE_EDICION_EDIFICIO_NAME = "nombreAccion.salas.SALAS_MODULE_EDICION_EDIFICIO";
	public static final String SALAS_MODULE_ELIMINAR_EDIFICIO_NAME = "nombreAccion.salas.SALAS_MODULE_ELIMINAR_EDIFICIO";

	public static final String SALAS_MODULE_VER_SALA_NAME = "nombreAccion.salas.SALAS_MODULE_VER_SALA";
	public static final String SALAS_MODULE_VER_SALAS_NAME = "nombreAccion.salas.SALAS_MODULE_VER_SALAS";
	public static final String SALAS_MODULE_ALTA_SALA_NAME = "nombreAccion.salas.SALAS_MODULE_ALTA_SALA";
	public static final String SALAS_MODULE_MODIFICAR_SALA_NAME = "nombreAccion.salas.SALAS_MODULE_EDICION_SALA";
	public static final String SALAS_MODULE_ELIMINAR_SALA_NAME = "nombreAccion.salas.SALAS_MODULE_ELIMINAR_SALA";

	public static final String SALAS_MODULE_VER_USUARIO_NAME = "nombreAccion.salas.SALAS_MODULE_VER_USUARIO";
	public static final String SALAS_MODULE_VER_USUARIOS_NAME = "nombreAccion.salas.SALAS_MODULE_VER_USUARIOS";
	public static final String SALAS_MODULE_ALTA_USUARIO_NAME = "nombreAccion.salas.SALAS_MODULE_ALTA_USUARIO";
	public static final String SALAS_MODULE_MODIFICAR_USUARIO_NAME = "nombreAccion.salas.SALAS_MODULE_MODIFICAR_USUARIO";
	public static final String SALAS_MODULE_ELIMINAR_USUARIO_NAME = "nombreAccion.salas.SALAS_MODULE_ELIMINAR_USUARIO";

	public static final String SALAS_MODULE_VER_MESA_NAME = "nombreAccion.salas.SALAS_MODULE_VER_MESA";
	public static final String SALAS_MODULE_VER_MESAS_NAME = "nombreAccion.salas.SALAS_MODULE_VER_MESAS";
	public static final String SALAS_MODULE_ALTA_MESA_NAME = "nombreAccion.salas.SALAS_MODULE_ALTA_MESA";
	public static final String SALAS_MODULE_MODIFICAR_MESA_NAME = "nombreAccion.salas.SALAS_MODULE_EDICION_MESA";
	public static final String SALAS_MODULE_ELIMINAR_MESA_NAME = "nombreAccion.salas.SALAS_MODULE_ELIMINAR_MESA";

	public static final String SALAS_MODULE_VER_REGISTRO_NAME = "nombreAccion.salas.SALAS_MODULE_VER_REGISTRO";
	public static final String SALAS_MODULE_VER_REGISTROS_NAME = "nombreAccion.salas.SALAS_MODULE_VER_REGISTROS";
	public static final String SALAS_MODULE_ALTA_REGISTRO_NAME = "nombreAccion.salas.SALAS_MODULE_ALTA_REGISTRO";
	public static final String SALAS_MODULE_MODIFICAR_REGISTRO_NAME = "nombreAccion.salas.SALAS_MODULE_EDICION_REGISTRO";
	public static final String SALAS_MODULE_ELIMINAR_REGISTRO_NAME = "nombreAccion.salas.SALAS_MODULE_ELIMINAR_REGISTRO";

	/** Asociacion accion/nombre */
	private static HashMap actionNames;

	static {
		actionNames = new HashMap();

		// Nombres TRANSFERENCIAS
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_ALTA_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_ALTA_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_CESION_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_CESION_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_BORRADO_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_BORRADO_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_CONSULTA_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_CONSULTA_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_ENVIO_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_ENVIO_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_RECHAZO_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_RECHAZO_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_ACEPTACION_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_ACEPTACION_PREVISION_TRANSFERENCIA_NAME);
		// actionNames.put(new
		// Integer(TRANSFERENCIAS_MODULE_INFORME_PREVISION_TRANSFERENCIA),TRANSFERENCIAS_MODULE_INFORME_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_CIERRE_PREVISION_TRANSFERENCIA),
				TRANSFERENCIAS_MODULE_CIERRE_PREVISION_TRANSFERENCIA_NAME);
		actionNames.put(
				new Integer(TRANSFERENCIAS_MODULE_ALTA_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_ALTA_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_BORRADO_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_BORRADO_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_CONSULTA_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_CONSULTA_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_ENVIO_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_ENVIO_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_RECEPCION_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_RECEPCION_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_COTEJO_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_COTEJO_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_GUARDAR_COTEJO_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_GUARDAR_COTEJO_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_CORRECION_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_CORRECION_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_VALIDACION_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_VALIDACION_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_CESION_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_CESION_RELACION_ENTREGA_NAME);
		actionNames.put(new Integer(
				TRANSFERENCIAS_MODULE_CESION_ARCHIVO_RELACION_ENTREGA),
				TRANSFERENCIAS_MODULE_CESION_ARCHIVO_RELACION_ENTREGA_NAME);
		// actionNames.put(new
		// Integer(TRANSFERENCIAS_MODULE_UBICACION_UDOCS),TRANSFERENCIAS_MODULE_UBICACION_UDOCS_NAME);
		// actionNames.put(new
		// Integer(TRANSFERENCIAS_MODULE_RESERVA_UDOCS),TRANSFERENCIAS_MODULE_RESERVA_UDOCS_NAME);

		// Nombres DEPOSITOS
		actionNames.put(new Integer(DEPOSITOS_MODULE_ALTA_ELEMENTO_DEPOSITO),
				DEPOSITOS_MODULE_ALTA_ELEMENTO_DEPOSITO_NAME);
		actionNames.put(new Integer(
				DEPOSITOS_MODULE_MODIFICACION_ELEMENTO_DEPOSITO),
				DEPOSITOS_MODULE_MODIFICACION_ELEMENTO_DEPOSITO_NAME);
		actionNames.put(new Integer(
				DEPOSITOS_MODULE_ELIMINACION_ELEMENTO_DEPOSITO),
				DEPOSITOS_MODULE_ELIMINACION_ELEMENTO_DEPOSITO_NAME);
		actionNames.put(new Integer(DEPOSITOS_MODULE_RESERVA_HUECO),
				DEPOSITOS_MODULE_RESERVA_HUECO_NAME);
		actionNames.put(
				new Integer(DEPOSITOS_MODULE_MODIFICACION_ESTADO_HUECO),
				DEPOSITOS_MODULE_MODIFICACION_ESTADO_HUECO_NAME);
		actionNames.put(new Integer(DEPOSITOS_MODULE_UBICAR_RELACION),
				DEPOSITOS_MODULE_UBICAR_RELACION_NAME);
		actionNames.put(new Integer(DEPOSITOS_MODULE_REUBICAR_UDOCS),
				DEPOSITOS_MODULE_REUBICAR_UDOCS_NAME);
		actionNames.put(new Integer(DEPOSITOS_MODULE_REUBICAR_UI),
				DEPOSITOS_MODULE_REUBICAR_UI_NAME);
		// actionNames.put(new
		// Integer(DEPOSITOS_MODULE_CONSULTA_DEPOSITO),DEPOSITOS_MODULE_CONSULTA_DEPOSITO_NAME);
		actionNames.put(
				new Integer(DEPOSITOS_MODULE_ALTA_DEPOSITO_ELECTRONICO),
				DEPOSITOS_MODULE_ALTA_DEPOSITO_ELECTRONICO_NAME);
		actionNames.put(new Integer(
				DEPOSITOS_MODULE_MODIFICACION_DEPOSITO_ELECTRONICO),
				DEPOSITOS_MODULE_MODIFICACION_DEPOSITO_ELECTRONICO_NAME);
		actionNames.put(
				new Integer(DEPOSITOS_MODULE_BAJA_DEPOSITO_ELECTRONICO),
				DEPOSITOS_MODULE_BAJA_DEPOSITO_ELECTRONICO_NAME);
		// actionNames.put(new
		// Integer(DEPOSITOS_MODULE_PASAR_HUECO_A_INUTILIZABLE),DEPOSITOS_MODULE_PASAR_HUECO_A_INUTILIZABLE_NAME);
		// actionNames.put(new
		// Integer(DEPOSITOS_MODULE_PASAR_HUECO_A_LIBRE),DEPOSITOS_MODULE_PASAR_HUECO_A_LIBRE_NAME);
		// actionNames.put(new
		// Integer(DEPOSITOS_MODULE_LIBERACION_HUECOS),DEPOSITOS_MODULE_LIBERACION_HUECOS_NAME);
		// actionNames.put(new
		// Integer(DEPOSITOS_MODULE_REUBICACION_UNIDADES),DEPOSITOS_MODULE_REUBICACION_UNIDADES_NAME);

		// Nombres PRESTAMOS
		actionNames.put(new Integer(SERVICIOS_MODULE_CONSULTA_PRESTAMO),
				SERVICIOS_MODULE_CONSULTA_PRESTAMO_NAME);
		// actionNames.put(new
		// Integer(SERVICIOS_MODULE_BUSQUEDA_UDOCS),SERVICIOS_MODULE_BUSQUEDA_UDOCS_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_ALTA_PRESTAMO),
				SERVICIOS_MODULE_ALTA_PRESTAMO_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_EDICION_PRESTAMO),
				SERVICIOS_MODULE_EDICION_PRESTAMO_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_ENVIO_SOLICITUD_PRESTAMO),
				SERVICIOS_MODULE_ENVIO_SOLICITUD_PRESTAMO_NAME);
		actionNames.put(new Integer(
				SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_PRESTAMO),
				SERVICIOS_MODULE_ACEPTACION_SOLICITUD_PRESTAMO_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_ENTREGA_UDOCS_PRESTAMO),
				SERVICIOS_MODULE_ENTREGA_UDOCS_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_DEVOLUCION_UDOCS),
				SERVICIOS_MODULE_DEVOLUCION_UDOCS_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_SOLICITUD_PRORROGA_UDOCS),
				SERVICIOS_MODULE_SOLICITUD_PRORROGA_UDOCS_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_GESTION_PRORROGA_UDOCS),
				SERVICIOS_MODULE_GESTION_PRORROGA_UDOCS_NAME);
		actionNames.put(
				new Integer(SERVICIOS_MODULE_RECLAMACION_UDOCS_PRESTAMO),
				SERVICIOS_MODULE_RECLAMACION_UDOCS_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_CESION_PRESTAMO),
				SERVICIOS_MODULE_CESION_PRESTAMO_NAME);
		actionNames.put(new Integer(
				SERVICIOS_MODULE_CESION_PRESTAMO_REVISION_DOC),
				SERVICIOS_MODULE_CESION_PRESTAMO_REVISION_DOC_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_CONSULTA_CONSULTA),
				SERVICIOS_MODULE_CONSULTA_CONSULTA_NAME);
		// actionNames.put(new
		// Integer(SERVICIOS_MODULE_BUSQUEDA_UDOCS_CONSULTA),SERVICIOS_MODULE_BUSQUEDA_UDOCS_CONSULTA_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_ALTA_CONSULTA),
				SERVICIOS_MODULE_ALTA_CONSULTA_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_EDICION_CONSULTA),
				SERVICIOS_MODULE_EDICION_CONSULTA_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_ENVIO_SOLICITUD_CONSULTA),
				SERVICIOS_MODULE_ENVIO_SOLICITUD_CONSULTA_NAME);
		actionNames.put(new Integer(
				SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_CONSULTA),
				SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_CONSULTA_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_ENTREGA_UDOCS_CONSULTA),
				SERVICIOS_MODULE_ENTREGA_UDOCS_CONSULTA_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_DEVOLUCION_UDOCS),
				SERVICIOS_MODULE_DEVOLUCION_UDOCS_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_ALTA_UDOC),
				SERVICIOS_MODULE_ALTA_UDOC_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_BAJA_UDOC),
				SERVICIOS_MODULE_BAJA_UDOC_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_BAJA_PRESTAMO),
				SERVICIOS_MODULE_BAJA_PRESTAMO_NAME);
		actionNames.put(new Integer(SERVICIOS_MODULE_BAJA_CONSULTA),
				SERVICIOS_MODULE_BAJA_CONSULTA_NAME);

		// Nombres DESCRIPCION
		actionNames.put(new Integer(DESCRIPCION_MODULE_BUSQUEDA_FONDOS),
				DESCRIPCION_MODULE_BUSQUEDA_FONDOS_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_BUSQUEDA_AUTORIDADES),
				DESCRIPCION_MODULE_BUSQUEDA_AUTORIDADES_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_CREACION_CAMPO),
				DESCRIPCION_MODULE_CREACION_CAMPO_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_MODIFICACION_CAMPO),
				DESCRIPCION_MODULE_MODIFICACION_CAMPO_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_ELIMINACION_CAMPO),
				DESCRIPCION_MODULE_ELIMINACION_CAMPO_NAME);
		actionNames.put(
				new Integer(DESCRIPCION_MODULE_CREACION_FICHA_ELEMENTO),
				DESCRIPCION_MODULE_CREACION_FICHA_ELEMENTO_NAME);
		actionNames.put(
				new Integer(DESCRIPCION_MODULE_CONSULTA_FICHA_ELEMENTO),
				DESCRIPCION_MODULE_CONSULTA_FICHA_ELEMENTO_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_EDICION_FICHA_ELEMENTO),
				DESCRIPCION_MODULE_EDICION_FICHA_ELEMENTO_NAME);
		actionNames.put(new Integer(
				DESCRIPCION_MODULE_CREACION_FICHA_DESCRIPTOR),
				DESCRIPCION_MODULE_CREACION_FICHA_DESCRIPTOR_NAME);
		actionNames.put(new Integer(
				DESCRIPCION_MODULE_CONSULTA_FICHA_DESCRIPTOR),
				DESCRIPCION_MODULE_CONSULTA_FICHA_DESCRIPTOR_NAME);
		actionNames.put(
				new Integer(DESCRIPCION_MODULE_EDICION_FICHA_DESCRIPTOR),
				DESCRIPCION_MODULE_EDICION_FICHA_DESCRIPTOR_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_ALTA_LISTA_VALORES),
				DESCRIPCION_MODULE_ALTA_LISTA_VALORES_NAME);
		actionNames.put(new Integer(
				DESCRIPCION_MODULE_MODIFICACION_LISTA_VALORES),
				DESCRIPCION_MODULE_MODIFICACION_LISTA_VALORES_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_BAJA_LISTA_VALORES),
				DESCRIPCION_MODULE_BAJA_LISTA_VALORES_NAME);
		actionNames.put(
				new Integer(DESCRIPCION_MODULE_ALTA_LISTA_DESCRIPTORES),
				DESCRIPCION_MODULE_ALTA_LISTA_DESCRIPTORES_NAME);
		actionNames.put(new Integer(
				DESCRIPCION_MODULE_MODIFICACION_LISTA_DESCRIPTORES),
				DESCRIPCION_MODULE_MODIFICACION_LISTA_DESCRIPTORES_NAME);
		actionNames.put(
				new Integer(DESCRIPCION_MODULE_BAJA_LISTA_DESCRIPTORES),
				DESCRIPCION_MODULE_BAJA_LISTA_DESCRIPTORES_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_ALTA_DESCRIPTOR),
				DESCRIPCION_MODULE_ALTA_DESCRIPTOR_NAME);
		actionNames.put(
				new Integer(DESCRIPCION_MODULE_MODIFICACION_DESCRIPTOR),
				DESCRIPCION_MODULE_MODIFICACION_DESCRIPTOR_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_BAJA_DESCRIPTOR),
				DESCRIPCION_MODULE_BAJA_DESCRIPTOR_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_ALTA_VALOR_VALIDACION),
				DESCRIPCION_MODULE_ALTA_VALOR_VALIDACION_NAME);
		actionNames.put(new Integer(
				DESCRIPCION_MODULE_MODIFICACION_VALOR_VALIDACION),
				DESCRIPCION_MODULE_MODIFICACION_VALOR_VALIDACION_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_BAJA_VALOR_VALIDACION),
				DESCRIPCION_MODULE_BAJA_VALOR_VALIDACION_NAME);
		actionNames.put(
				new Integer(DESCRIPCION_MODULE_REEMPLAZO_CAMPO_ELEMENTO),
				DESCRIPCION_MODULE_REEMPLAZO_CAMPO_ELEMENTO_NAME);
		actionNames.put(new Integer(DESCRIPCION_MODULE_UNIFICAR_DESCRIPTORES),
				DESCRIPCION_MODULE_UNIFICAR_DESCRIPTORES_NAME);

		// Nombres FONDOS - CLASIFICACION
		actionNames.put(new Integer(FONDOS_MODULE_ALTA_ELEMENTO),
				FONDOS_MODULE_ALTA_ELEMENTO_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_MODIFICACION_ELEMENTO),
				FONDOS_MODULE_MODIFICACION_ELEMENTO_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_BAJA_ELEMENTO),
				FONDOS_MODULE_BAJA_ELEMENTO_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_PUBLICACION_ELEMENTO),
				FONDOS_MODULE_PUBLICACION_ELEMENTO_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_CONSULTA_CUADRO),
				FONDOS_MODULE_CONSULTA_CUADRO_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_MOVER_ELEMENTO),
				FONDOS_MODULE_MOVER_ELEMENTO_NAME);

		// Nombres FONDOS - SERIES
		actionNames.put(new Integer(FONDOS_MODULE_SOLICITUD_ALTA_SERIE),
				FONDOS_MODULE_SOLICITUD_ALTA_SERIE_NAME);
		actionNames.put(
				new Integer(FONDOS_MODULE_AUTORIZACION_DENEGACION_SERIE),
				FONDOS_MODULE_AUTORIZACION_DENEGACION_SERIE_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_MODIFICACION_SERIE),
				FONDOS_MODULE_MODIFICACION_SERIE_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_BAJA_SERIE),
				FONDOS_MODULE_BAJA_SERIE_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_IDENTIFICACION_SERIE),
				FONDOS_MODULE_IDENTIFICACION_SERIE_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE),
				FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_CONSULTA_SOLICITUDES_SERIE),
				FONDOS_MODULE_CONSULTA_SOLICITUDES_SERIE_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_GESTION_SOLICITUDES_SERIE),
				FONDOS_MODULE_GESTION_SOLICITUDES_SERIE_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_CEDER_CONTROL_SERIE),
				FONDOS_MODULE_CEDER_CONTROL_SERIE_NAME);

		actionNames.put(new Integer(
				FONDOS_MODULE_SOLICITUD_VALIDACION_VALORACION),
				FONDOS_MODULE_SOLICITUD_VALIDACION_VALORACION_NAME);
		actionNames.put(
				new Integer(FONDOS_MODULE_RECHAZO_VALIDACION_VALORACION),
				FONDOS_MODULE_RECHAZO_VALIDACION_VALORACION_NAME);
		actionNames.put(new Integer(
				FONDOS_MODULE_AUTORIZACION_VALIDACION_VALORACION),
				FONDOS_MODULE_AUTORIZACION_VALIDACION_VALORACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_BAJA_VALORACION),
				FONDOS_MODULE_BAJA_VALORACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_ALTA_VALORACION),
				FONDOS_MODULE_ALTA_VALORACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_ACTUALIZAR_VALORACION),
				FONDOS_MODULE_ACTUALIZAR_VALORACION_NAME);
		actionNames.put(new Integer(
				FONDOS_MODULE_AUTORIZACION_EVALUACION_VALORACION),
				FONDOS_MODULE_AUTORIZACION_EVALUACION_VALORACION_NAME);
		actionNames.put(
				new Integer(FONDOS_MODULE_RECHAZO_EVALUACION_VALORACION),
				FONDOS_MODULE_RECHAZO_EVALUACION_VALORACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_CERRAR_DICTAMEN_VALORACION),
				FONDOS_MODULE_CERRAR_DICTAMEN_VALORACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_DICTAMEN_VALORACION),
				FONDOS_MODULE_DICTAMEN_VALORACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_IMPRIMIR_VALORACION),
				FONDOS_MODULE_IMPRIMIR_VALORACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_EDITAR_VALORACIONES),
				FONDOS_MODULE_EDITAR_VALORACIONES_NAME);

		actionNames.put(new Integer(FONDOS_MODULE_IMPRIMIR_SELECCION),
				FONDOS_MODULE_IMPRIMIR_SELECCION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_ALTA_ELIMINACION),
				FONDOS_MODULE_ALTA_ELIMINACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_ACTUALIZACION_ELIMINACION),
				FONDOS_MODULE_ACTUALIZACION_ELIMINACION_NAME);
		actionNames.put(new Integer(
				FONDOS_MODULE_SOLICITAR_APROBACION_ELIMINACION),
				FONDOS_MODULE_SOLICITAR_APROBACION_ELIMINACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_APROBAR_ELIMINACION),
				FONDOS_MODULE_APROBAR_ELIMINACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_RECHAZAR_ELIMINACION),
				FONDOS_MODULE_RECHAZAR_ELIMINACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_EJECUTAR_ELIMINACION),
				FONDOS_MODULE_EJECUTAR_ELIMINACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_DESTRUIR_FISICAMENTE),
				FONDOS_MODULE_DESTRUIR_FISICAMENTE_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_BAJA_UDOC),
				FONDOS_MODULE_BAJA_UDOC_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_FINALIZAR_ELIMINACION),
				FONDOS_MODULE_FINALIZAR_ELIMINACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_BAJA_UDOCENUI),
				FONDOS_MODULE_BAJA_UDOCENUI_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_BAJA_UI),
				FONDOS_MODULE_BAJA_UI_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_BAJA_ELIMINACION),
				FONDOS_MODULE_BAJA_ELIMINACION_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_EDITAR_SELECCIONES),
				FONDOS_MODULE_EDITAR_SELECCIONES_NAME);

		actionNames.put(new Integer(FONDOS_MODULE_ALTA_INGRESO_DIRECTO),
				FONDOS_MODULE_ALTA_INGRESO_DIRECTO_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_EDICION_INGRESO_DIRECTO),
				FONDOS_MODULE_EDICION_INGRESO_DIRECTO_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_BORRADO_INGRESO_DIRECTO),
				FONDOS_MODULE_BORRADO_INGRESO_DIRECTO_NAME);
		actionNames.put(new Integer(
				FONDOS_MODULE_SELECCION_UBICACION_INGRESO_DIRECTO),
				FONDOS_MODULE_SELECCION_UBICACION_INGRESO_DIRECTO_NAME);
		actionNames
				.put(new Integer(
						FONDOS_MODULE_SIGNATURACION_UBICACION_VALIDACION_INGRESO_DIRECTO),
						FONDOS_MODULE_SIGNATURACION_UBICACION_VALIDACION_INGRESO_DIRECTO_NAME);
		actionNames.put(new Integer(FONDOS_MODULE_CREAR_UDOCS_FRACCION_SERIE),
				FONDOS_MODULE_CREAR_UDOCS_FRACCION_SERIE_NAME);
		actionNames.put(new Integer(
				FONDOS_MODULE_ELIMINACION_MARCA_CONSERVADA_UDOCS),
				FONDOS_MODULE_ELIMINAR_MARCA_CONSERVADA_NAME);

		// Nombres USUARIOS
		actionNames.put(new Integer(USUARIOS_MODULE_ALTA_USUARIO),
				USUARIOS_MODULE_ALTA_USUARIO_NAME);
		actionNames.put(new Integer(USUARIOS_MODULE_MODIFICACION_USUARIO),
				USUARIOS_MODULE_MODIFICACION_USUARIO_NAME);
		actionNames.put(new Integer(USUARIOS_MODULE_ELIMINACION_USUARIO),
				USUARIOS_MODULE_ELIMINACION_USUARIO_NAME);

		actionNames.put(new Integer(USUARIOS_MODULE_ASIGNACION_ROLE),
				USUARIOS_MODULE_ASIGNACION_ROLE_NAME);
		actionNames.put(new Integer(USUARIOS_MODULE_DEASIGNACION_ROLE),
				USUARIOS_MODULE_DEASIGNACION_ROLE_NAME);

		actionNames.put(new Integer(USUARIOS_MODULE_ASIGNACION_GRUPO),
				USUARIOS_MODULE_ASIGNACION_GRUPO_NAME);
		actionNames.put(new Integer(USUARIOS_MODULE_DEASIGNACION_GRUPO),
				USUARIOS_MODULE_DEASIGNACION_GRUPO_NAME);

		// Nombres DOCUMENTOS VITALES
		actionNames.put(new Integer(DOCUMENTOS_VITALES_MODULE_ALTA_TIPO),
				DOCUMENTOS_VITALES_MODULE_ALTA_TIPO_NAME);
		actionNames.put(
				new Integer(DOCUMENTOS_VITALES_MODULE_MODIFICACION_TIPO),
				DOCUMENTOS_VITALES_MODULE_MODIFICACION_TIPO_NAME);
		actionNames.put(
				new Integer(DOCUMENTOS_VITALES_MODULE_ELIMINACION_TIPO),
				DOCUMENTOS_VITALES_MODULE_ELIMINACION_TIPO_NAME);
		actionNames.put(new Integer(DOCUMENTOS_VITALES_MODULE_ALTA),
				DOCUMENTOS_VITALES_MODULE_ALTA_NAME);
		actionNames.put(new Integer(DOCUMENTOS_VITALES_MODULE_MODIFICACION),
				DOCUMENTOS_VITALES_MODULE_MODIFICACION_NAME);
		actionNames.put(new Integer(DOCUMENTOS_VITALES_MODULE_ELIMINACION),
				DOCUMENTOS_VITALES_MODULE_ELIMINACION_NAME);
		actionNames.put(new Integer(DOCUMENTOS_VITALES_MODULE_VALIDACION),
				DOCUMENTOS_VITALES_MODULE_VALIDACION_NAME);
		actionNames.put(new Integer(DOCUMENTOS_VITALES_MODULE_RECHAZO),
				DOCUMENTOS_VITALES_MODULE_RECHAZO_NAME);
		actionNames.put(
				new Integer(DOCUMENTOS_VITALES_MODULE_PASO_A_HISTORICO),
				DOCUMENTOS_VITALES_MODULE_PASO_A_HISTORICO_NAME);
		actionNames.put(new Integer(DOCUMENTOS_VITALES_MODULE_CONSULTA),
				DOCUMENTOS_VITALES_MODULE_CONSULTA_NAME);

		// Nombres EXPLOTACION

		// Nombres DOCUMENTOS ELECTRONICOS
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_CLASIFICADOR),
				DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_CLASIFICADOR_NAME);
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_CLASIFICADOR),
				DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_CLASIFICADOR_NAME);
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_CLASIFICADOR),
				DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_CLASIFICADOR_NAME);
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_DOCUMENTO),
				DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_DOCUMENTO_NAME);
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_DOCUMENTO),
				DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_DOCUMENTO_NAME);
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_DOCUMENTO),
				DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_DOCUMENTO_NAME);
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_INSERTAR_TAREA),
				DOCUMENTOS_ELECTRONICOS_MODULE_INSERTAR_TAREA_NAME);
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_ELIMINAR_TAREA),
				DOCUMENTOS_ELECTRONICOS_MODULE_ELIMINAR_TAREA_NAME);
		actionNames.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_CAMBIAR_ESTADO_TAREA),
				DOCUMENTOS_ELECTRONICOS_MODULE_CAMBIAR_ESTADO_TAREA_NAME);

		// Nombres AUDITORIA
		actionNames.put(new Integer(
				AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ACTION),
				AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ACTION_NAME);
		/**
		 * actionNames.put(new
		 * Integer(AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_USER
		 * ),AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_USER_NAME);
		 */
		actionNames.put(new Integer(
				AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_GROUP),
				AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_GROUP_NAME);
		/**
		 * actionNames.put(new
		 * Integer(AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ROL
		 * ),AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ROL_NAME);
		 */
		actionNames.put(new Integer(AUDITORIA_MODULE_CONSULTAS),
				AUDITORIA_MODULE_CONSULTAS_NAME);

		// Nombres LOGIN
		actionNames.put(new Integer(SISTEMA_MODULE_LOGIN_OK),
				SISTEMA_MODULE_LOGIN_OK_NAME);
		actionNames.put(new Integer(SISTEMA_MODULE_LOGIN_FAIL),
				SISTEMA_MODULE_LOGIN_FAIL_NAME);
		actionNames.put(new Integer(SISTEMA_MODULE_LOGOUT_OK),
				SISTEMA_MODULE_LOGOUT_OK_NAME);

		// Acciones SALAS
		// actionNames.put(new
		// Integer(SALAS_MODULE_VER_EDIFICIOS),SALAS_MODULE_VER_EDIFICIOS_NAME);
		actionNames.put(new Integer(SALAS_MODULE_VER_EDIFICIO),
				SALAS_MODULE_VER_EDIFICIO_NAME);
		actionNames.put(new Integer(SALAS_MODULE_ALTA_EDIFICIO),
				SALAS_MODULE_ALTA_EDIFICIO_NAME);
		actionNames.put(new Integer(SALAS_MODULE_EDICION_EDIFICIO),
				SALAS_MODULE_EDICION_EDIFICIO_NAME);
		actionNames.put(new Integer(SALAS_MODULE_ELIMINAR_EDIFICIO),
				SALAS_MODULE_ELIMINAR_EDIFICIO_NAME);

		actionNames.put(new Integer(SALAS_MODULE_VER_SALA),
				SALAS_MODULE_VER_SALA_NAME);
		actionNames.put(new Integer(SALAS_MODULE_ALTA_SALA),
				SALAS_MODULE_ALTA_SALA_NAME);
		actionNames.put(new Integer(SALAS_MODULE_EDICION_SALA),
				SALAS_MODULE_MODIFICAR_SALA_NAME);
		actionNames.put(new Integer(SALAS_MODULE_ELIMINAR_SALA),
				SALAS_MODULE_ELIMINAR_SALA_NAME);

		actionNames.put(new Integer(SALAS_MODULE_VER_USUARIO),
				SALAS_MODULE_VER_USUARIO_NAME);
		actionNames.put(new Integer(SALAS_MODULE_ALTA_USUARIO),
				SALAS_MODULE_ALTA_USUARIO_NAME);
		actionNames.put(new Integer(SALAS_MODULE_EDICION_USUARIO),
				SALAS_MODULE_MODIFICAR_USUARIO_NAME);
		actionNames.put(new Integer(SALAS_MODULE_ELIMINAR_USUARIO),
				SALAS_MODULE_ELIMINAR_USUARIO_NAME);
	}

	/** Asociacio accion/modulo */
	private static HashMap actionModules;

	static {
		actionModules = new HashMap();

		// TRANSFERENCIAS
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_ALTA_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_EDICION_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_CESION_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_BORRADO_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_CONSULTA_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_ENVIO_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_RECHAZO_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_ACEPTACION_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		// actionModules.put(new
		// Integer(TRANSFERENCIAS_MODULE_INFORME_PREVISION_TRANSFERENCIA),new
		// Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_CIERRE_PREVISION_TRANSFERENCIA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_ALTA_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_BORRADO_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_CONSULTA_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_ENVIO_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_RECEPCION_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_COTEJO_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_GUARDAR_COTEJO_RELACION_ENTREGA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_CORRECION_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_VALIDACION_RELACION_ENTREGA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_CESION_RELACION_ENTREGA), new Integer(
				ArchivoModules.TRANSFERENCIAS_MODULE));
		actionModules.put(new Integer(
				TRANSFERENCIAS_MODULE_CESION_ARCHIVO_RELACION_ENTREGA),
				new Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		// actionModules.put(new
		// Integer(TRANSFERENCIAS_MODULE_UBICACION_UDOCS),new
		// Integer(ArchivoModules.TRANSFERENCIAS_MODULE));
		// actionModules.put(new
		// Integer(TRANSFERENCIAS_MODULE_RESERVA_UDOCS),new
		// Integer(ArchivoModules.TRANSFERENCIAS_MODULE));

		// DEPOSITOS
		actionModules.put(new Integer(DEPOSITOS_MODULE_ALTA_ELEMENTO_DEPOSITO),
				new Integer(ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(
				DEPOSITOS_MODULE_MODIFICACION_ELEMENTO_DEPOSITO), new Integer(
				ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(
				DEPOSITOS_MODULE_ELIMINACION_ELEMENTO_DEPOSITO), new Integer(
				ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(DEPOSITOS_MODULE_RESERVA_HUECO),
				new Integer(ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(
				DEPOSITOS_MODULE_MODIFICACION_ESTADO_HUECO), new Integer(
				ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(DEPOSITOS_MODULE_UBICAR_RELACION),
				new Integer(ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(DEPOSITOS_MODULE_REUBICAR_UI),
				new Integer(ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(DEPOSITOS_MODULE_REUBICAR_UDOCS),
				new Integer(ArchivoModules.DEPOSITOS_MODULE));
		// actionModules.put(new Integer(DEPOSITOS_MODULE_CONSULTA_DEPOSITO),new
		// Integer(ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(
				DEPOSITOS_MODULE_ALTA_DEPOSITO_ELECTRONICO), new Integer(
				ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(
				DEPOSITOS_MODULE_MODIFICACION_DEPOSITO_ELECTRONICO),
				new Integer(ArchivoModules.DEPOSITOS_MODULE));
		actionModules.put(new Integer(
				DEPOSITOS_MODULE_BAJA_DEPOSITO_ELECTRONICO), new Integer(
				ArchivoModules.DEPOSITOS_MODULE));

		// actionModules.put(new
		// Integer(DEPOSITOS_MODULE_PASAR_HUECO_A_INUTILIZABLE),new
		// Integer(ArchivoModules.DEPOSITOS_MODULE));
		// actionModules.put(new
		// Integer(DEPOSITOS_MODULE_PASAR_HUECO_A_LIBRE),new
		// Integer(ArchivoModules.DEPOSITOS_MODULE));
		// actionModules.put(new Integer(DEPOSITOS_MODULE_LIBERACION_HUECOS),new
		// Integer(ArchivoModules.DEPOSITOS_MODULE));
		// actionModules.put(new
		// Integer(DEPOSITOS_MODULE_REUBICACION_UNIDADES),new
		// Integer(ArchivoModules.DEPOSITOS_MODULE));

		// PRESTAMOS
		actionModules.put(new Integer(SERVICIOS_MODULE_CONSULTA_PRESTAMO),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		// actionModules.put(new Integer(SERVICIOS_MODULE_BUSQUEDA_UDOCS),new
		// Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_ALTA_PRESTAMO),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_EDICION_PRESTAMO),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(
				new Integer(SERVICIOS_MODULE_ENVIO_SOLICITUD_PRESTAMO),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(
				SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_PRESTAMO), new Integer(
				ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_ENTREGA_UDOCS_PRESTAMO),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(
				new Integer(SERVICIOS_MODULE_SOLICITUD_PRORROGA_UDOCS),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_GESTION_PRORROGA_UDOCS),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(
				SERVICIOS_MODULE_RECLAMACION_UDOCS_PRESTAMO), new Integer(
				ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_CESION_PRESTAMO),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(
				SERVICIOS_MODULE_CESION_PRESTAMO_REVISION_DOC), new Integer(
				ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_CONSULTA_CONSULTA),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		// actionModules.put(new
		// Integer(SERVICIOS_MODULE_BUSQUEDA_UDOCS_CONSULTA),new
		// Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_ALTA_CONSULTA),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_EDICION_CONSULTA),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(
				new Integer(SERVICIOS_MODULE_ENVIO_SOLICITUD_CONSULTA),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(
				SERVICIOS_MODULE_AUTORIZACION_SOLICITUD_CONSULTA), new Integer(
				ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_ENTREGA_UDOCS_CONSULTA),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_DEVOLUCION_UDOCS),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_ALTA_UDOC), new Integer(
				ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_BAJA_UDOC), new Integer(
				ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_BAJA_PRESTAMO),
				new Integer(ArchivoModules.SERVICIOS_MODULE));
		actionModules.put(new Integer(SERVICIOS_MODULE_BAJA_CONSULTA),
				new Integer(ArchivoModules.SERVICIOS_MODULE));

		// DESCRIPCION
		actionModules.put(new Integer(DESCRIPCION_MODULE_BUSQUEDA_FONDOS),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(DESCRIPCION_MODULE_BUSQUEDA_AUTORIDADES),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(DESCRIPCION_MODULE_CREACION_CAMPO),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(DESCRIPCION_MODULE_MODIFICACION_CAMPO),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(DESCRIPCION_MODULE_ELIMINACION_CAMPO),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_CREACION_FICHA_ELEMENTO), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_CONSULTA_FICHA_ELEMENTO), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(
				new Integer(DESCRIPCION_MODULE_EDICION_FICHA_ELEMENTO),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_CREACION_FICHA_DESCRIPTOR), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_CONSULTA_FICHA_DESCRIPTOR), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_EDICION_FICHA_DESCRIPTOR), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(DESCRIPCION_MODULE_ALTA_LISTA_VALORES),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_MODIFICACION_LISTA_VALORES), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(DESCRIPCION_MODULE_BAJA_LISTA_VALORES),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_ALTA_LISTA_DESCRIPTORES), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_MODIFICACION_LISTA_DESCRIPTORES),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_BAJA_LISTA_DESCRIPTORES), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(DESCRIPCION_MODULE_ALTA_DESCRIPTOR),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_MODIFICACION_DESCRIPTOR), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(DESCRIPCION_MODULE_BAJA_DESCRIPTOR),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(
				new Integer(DESCRIPCION_MODULE_ALTA_VALOR_VALIDACION),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_MODIFICACION_VALOR_VALIDACION), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(
				new Integer(DESCRIPCION_MODULE_BAJA_VALOR_VALIDACION),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(new Integer(
				DESCRIPCION_MODULE_REEMPLAZO_CAMPO_ELEMENTO), new Integer(
				ArchivoModules.DESCRIPCION_MODULE));
		actionModules.put(
				new Integer(DESCRIPCION_MODULE_UNIFICAR_DESCRIPTORES),
				new Integer(ArchivoModules.DESCRIPCION_MODULE));

		// FONDOS - CLASIFICACION
		actionModules.put(new Integer(FONDOS_MODULE_ALTA_ELEMENTO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_MODIFICACION_ELEMENTO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_BAJA_ELEMENTO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_PUBLICACION_ELEMENTO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_CONSULTA_CUADRO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_MOVER_ELEMENTO),
				new Integer(ArchivoModules.FONDOS_MODULE));

		// FONDOS - SERIES
		actionModules.put(new Integer(FONDOS_MODULE_SOLICITUD_ALTA_SERIE),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(
				FONDOS_MODULE_AUTORIZACION_DENEGACION_SERIE), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_MODIFICACION_SERIE),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_BAJA_SERIE), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_IDENTIFICACION_SERIE),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_GESTION_SOLICITUDES_SERIE),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_CEDER_CONTROL_SERIE),
				new Integer(ArchivoModules.FONDOS_MODULE));

		actionModules.put(new Integer(
				FONDOS_MODULE_SOLICITUD_VALIDACION_VALORACION), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(
				FONDOS_MODULE_RECHAZO_VALIDACION_VALORACION), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(
				FONDOS_MODULE_AUTORIZACION_VALIDACION_VALORACION), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_BAJA_VALORACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_ALTA_VALORACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_ACTUALIZAR_VALORACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(
				FONDOS_MODULE_AUTORIZACION_EVALUACION_VALORACION), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(
				FONDOS_MODULE_RECHAZO_EVALUACION_VALORACION), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_DICTAMEN_VALORACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(
				new Integer(FONDOS_MODULE_CERRAR_DICTAMEN_VALORACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		// actionModules.put(new Integer(FONDOS_MODULE_IMPRIMIR_VALORACION),new
		// Integer(ArchivoModules.FONDOS_MODULE));
		// actionModules.put(new Integer(FONDOS_MODULE_IMPRIMIR_SELECCION),new
		// Integer(ArchivoModules.FONDOS_MODULE));

		actionModules.put(new Integer(FONDOS_MODULE_ALTA_ELIMINACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_ACTUALIZACION_ELIMINACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(
				FONDOS_MODULE_SOLICITAR_APROBACION_ELIMINACION), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_APROBAR_ELIMINACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_RECHAZAR_ELIMINACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_EJECUTAR_ELIMINACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_DESTRUIR_FISICAMENTE),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_BAJA_UDOC), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_FINALIZAR_ELIMINACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_BAJA_UDOCENUI),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_BAJA_UI), new Integer(
				ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_BAJA_ELIMINACION),
				new Integer(ArchivoModules.FONDOS_MODULE));
		// actionModules.put(new Integer(FONDOS_MODULE_BAJA_HUECO),new
		// Integer(ArchivoModules.FONDOS_MODULE));

		actionModules.put(new Integer(FONDOS_MODULE_ALTA_INGRESO_DIRECTO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_EDICION_INGRESO_DIRECTO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(FONDOS_MODULE_BORRADO_INGRESO_DIRECTO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(
				FONDOS_MODULE_SELECCION_UBICACION_INGRESO_DIRECTO),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules
				.put(new Integer(
						FONDOS_MODULE_SIGNATURACION_UBICACION_VALIDACION_INGRESO_DIRECTO),
						new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(
				new Integer(FONDOS_MODULE_CREAR_UDOCS_FRACCION_SERIE),
				new Integer(ArchivoModules.FONDOS_MODULE));
		actionModules.put(new Integer(
				FONDOS_MODULE_ELIMINACION_MARCA_CONSERVADA_UDOCS), new Integer(
				ArchivoModules.FONDOS_MODULE));

		// actionModules.put(new Integer(FONDOS_MODULE_EDITAR_SELECCIONES),new
		// Integer(ArchivoModules.FONDOS_MODULE));
		// actionModules.put(new Integer(FONDOS_MODULE_EDITAR_VALORACIONES),new
		// Integer(ArchivoModules.FONDOS_MODULE));

		// USUARIOS
		actionModules.put(new Integer(USUARIOS_MODULE_ALTA_USUARIO),
				new Integer(ArchivoModules.USUARIOS_MODULE));
		actionModules.put(new Integer(USUARIOS_MODULE_MODIFICACION_USUARIO),
				new Integer(ArchivoModules.USUARIOS_MODULE));
		actionModules.put(new Integer(USUARIOS_MODULE_ELIMINACION_USUARIO),
				new Integer(ArchivoModules.USUARIOS_MODULE));

		actionModules.put(new Integer(USUARIOS_MODULE_ASIGNACION_ROLE),
				new Integer(ArchivoModules.USUARIOS_MODULE));
		actionModules.put(new Integer(USUARIOS_MODULE_DEASIGNACION_ROLE),
				new Integer(ArchivoModules.USUARIOS_MODULE));

		actionModules.put(new Integer(USUARIOS_MODULE_ASIGNACION_GRUPO),
				new Integer(ArchivoModules.USUARIOS_MODULE));
		actionModules.put(new Integer(USUARIOS_MODULE_DEASIGNACION_GRUPO),
				new Integer(ArchivoModules.USUARIOS_MODULE));

		// DOCUMENTOS VITALES
		actionModules.put(new Integer(DOCUMENTOS_VITALES_MODULE_ALTA_TIPO),
				new Integer(ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_VITALES_MODULE_MODIFICACION_TIPO), new Integer(
				ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_VITALES_MODULE_ELIMINACION_TIPO), new Integer(
				ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(DOCUMENTOS_VITALES_MODULE_ALTA),
				new Integer(ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(DOCUMENTOS_VITALES_MODULE_MODIFICACION),
				new Integer(ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(DOCUMENTOS_VITALES_MODULE_ELIMINACION),
				new Integer(ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(DOCUMENTOS_VITALES_MODULE_VALIDACION),
				new Integer(ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(DOCUMENTOS_VITALES_MODULE_RECHAZO),
				new Integer(ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_VITALES_MODULE_PASO_A_HISTORICO), new Integer(
				ArchivoModules.DOCUMENTOS_VITALES_MODULE));
		actionModules.put(new Integer(DOCUMENTOS_VITALES_MODULE_CONSULTA),
				new Integer(ArchivoModules.DOCUMENTOS_VITALES_MODULE));

		// EXPLOTACION

		// DOCUMENTOS ELECTRONICOS
		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_CLASIFICADOR), new Integer(
				ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_CLASIFICADOR),
				new Integer(ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_CLASIFICADOR), new Integer(
				ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_ALTA_DOCUMENTO), new Integer(
				ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_DOCUMENTO),
				new Integer(ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_DOCUMENTO), new Integer(
				ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));

		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_INSERTAR_TAREA), new Integer(
				ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_ELIMINAR_TAREA), new Integer(
				ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));
		actionModules.put(new Integer(
				DOCUMENTOS_ELECTRONICOS_MODULE_CAMBIAR_ESTADO_TAREA),
				new Integer(ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE));

		// AUDITORIA
		actionModules.put(new Integer(
				AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ACTION), new Integer(
				ArchivoModules.AUDITORIA_MODULE));
		/**
		 * actionModules.put(new
		 * Integer(AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_USER),new
		 * Integer(ArchivoModules.AUDITORIA_MODULE));
		 */
		actionModules.put(new Integer(
				AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_GROUP), new Integer(
				ArchivoModules.AUDITORIA_MODULE));
		/**
		 * actionModules.put(new
		 * Integer(AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ROL),new
		 * Integer(ArchivoModules.AUDITORIA_ROL));
		 */
		actionModules.put(new Integer(AUDITORIA_MODULE_CONSULTAS), new Integer(
				ArchivoModules.AUDITORIA_MODULE));

		// LOGIN
		actionModules.put(new Integer(SISTEMA_MODULE_LOGIN_OK), new Integer(
				ArchivoModules.SISTEMA_MODULE));
		actionModules.put(new Integer(SISTEMA_MODULE_LOGIN_FAIL), new Integer(
				ArchivoModules.SISTEMA_MODULE));
		actionModules.put(new Integer(SISTEMA_MODULE_LOGOUT_OK), new Integer(
				ArchivoModules.SISTEMA_MODULE));

		// GESTION DE SALAS
		/** GESTION DE SALAS */
		actionModules.put(new Integer(SALAS_MODULE_ALTA_EDIFICIO), new Integer(
				ArchivoModules.SALAS_MODULE));
		actionModules.put(new Integer(SALAS_MODULE_EDICION_EDIFICIO),
				new Integer(ArchivoModules.SALAS_MODULE));
		actionModules.put(new Integer(SALAS_MODULE_ELIMINAR_EDIFICIO),
				new Integer(ArchivoModules.SALAS_MODULE));

		actionModules.put(new Integer(SALAS_MODULE_ALTA_SALA), new Integer(
				ArchivoModules.SALAS_MODULE));
		actionModules.put(new Integer(SALAS_MODULE_EDICION_SALA), new Integer(
				ArchivoModules.SALAS_MODULE));
		actionModules.put(new Integer(SALAS_MODULE_ELIMINAR_SALA), new Integer(
				ArchivoModules.SALAS_MODULE));

		actionModules.put(new Integer(SALAS_MODULE_ALTA_USUARIO), new Integer(
				ArchivoModules.SALAS_MODULE));
		actionModules.put(new Integer(SALAS_MODULE_EDICION_USUARIO),
				new Integer(ArchivoModules.SALAS_MODULE));
		actionModules.put(new Integer(SALAS_MODULE_ELIMINAR_USUARIO),
				new Integer(ArchivoModules.SALAS_MODULE));

	}

	/**
	 * Devuelve el nombre asociado a la accion o null en caso de no existir la
	 * accion.
	 * 
	 * @param action
	 *            Accion de la que deseamos obtener el nombre
	 * @return Nombre de la accion
	 */
	public static String getActionName(int action) {
		return (String) actionNames.get(new Integer(action));
	}

	/**
	 * Devuelve el tipo del modulo al que está asociado una acción
	 * 
	 * @param action
	 *            Accion de la que deseamos conocer su módulo.
	 * @return Identificador del módulo al que está asociado la acción o -1 en
	 *         caso de no existir.
	 */
	public static int getModule(int action) {
		int result = -1;
		Integer i = (Integer) actionModules.get(new Integer(action));

		if (i != null)
			result = i.intValue();

		return result;
	}

	/**
	 * Devuelve el nombre del módulo al que está asociado una acción.
	 * 
	 * @param action
	 *            Acción de la que deseamos conocer el nombre del módulo.
	 * @return Nombre del módulo al que está asociado la acción o null en caso
	 *         de no existir.
	 */
	public static String getModuleName(int action) {
		return ArchivoModules.getModuleName(getModule(action));
	}

	/**
	 * Obtiene un listado de las acciones existentes
	 * 
	 * @return Listado de las acciones {@link auditoria.vos.ArchivoAction}
	 *         existentes
	 */
	public static Collection getActions() {
		Integer i = null;
		String name = null;
		ArrayList result = new ArrayList();
		Iterator it = actionNames.keySet().iterator();

		while (it.hasNext()) {
			i = (Integer) it.next();

			name = (String) actionNames.get(i);

			ArchivoAction action = new ArchivoAction();
			action.setId(i.intValue());
			action.setName(name);

			ArchivoModule module = new ArchivoModule();
			module.setId(getModule(i.intValue()));
			module.setName(getModuleName(i.intValue()));
			action.setModule(module);

			result.add(action);
		}

		// Ordenamso los resultados
		Collections.sort(result);

		return result;
	}

	/**
	 * Obtiene un listado de las acciones existentes para un determinado módulo
	 * 
	 * @return Listado de las acciones {@link auditoria.vos.ArchivoAction}
	 *         existentes para un módulo
	 */
	public static Collection getActions(int module) {
		ArrayList result = new ArrayList();
		Set actions = actionModules.entrySet();

		Iterator it = actions.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();

			int moduleEntry = ((Integer) entry.getValue()).intValue();
			int actionEntry = ((Integer) entry.getKey()).intValue();

			if (moduleEntry == module) {
				ArchivoAction action = new ArchivoAction();
				action.setId(actionEntry);
				action.setName(getActionName(actionEntry));

				ArchivoModule smodule = new ArchivoModule();
				smodule.setId(moduleEntry);
				smodule.setName(getModuleName(moduleEntry));
				action.setModule(smodule);

				result.add(action);
			}
		}

		// Ordenamos los resultados
		Collections.sort(result, new ArchivoActionsComparator());

		return result;
	}
}
