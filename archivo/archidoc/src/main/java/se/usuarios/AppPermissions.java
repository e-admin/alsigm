package se.usuarios;

import common.ConfigConstants;
import common.definitions.ArchivoModules;
import common.util.ArrayUtils;

/**
 * Clase que establece los permisos de la aplicación.
 */
public class AppPermissions {

	// Permisos del SISTEMA
	public static final String ADMINISTRACION_TOTAL_SISTEMA = "100";
	public static final String CONSULTA_TOTAL_SISTEMA = "101";

	// Permisos del módulo de TRANSFERENCIAS
	public static final String ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS = "200";
	public static final String AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS = "201";
	public static final String ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS = "202";
	public static final String AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS = "203";
	public static final String GESTION_TRANSFERENCIAS_ORGANO_REMITENTE = "204";
	public static final String GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR = "205";
	public static final String GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR = "206";
	public static final String UBICACION_RELACIONES_ENTREGA = "207";
	public static final String ASIGNACION_RESERVA_ESPACIO_DEPOSITO = "208";
	public static final String ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS = "209";

	public static final String CORRECCION_ERRORES_COTEJO_EN_ARCHIVO = "211";
	public static final String BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES = "212";

	// Permisos del módulo de PRÉSTAMOS Y CONSULTAS
	public static final String ESTANDAR_SOLICITUD_PRESTAMOS = "300";
	public static final String AMPLIADO_SOLICITUD_PRESTAMOS = "301";
	public static final String GESTION_PRESTAMOS_ARCHIVO = "302";
	public static final String GESTION_PRESTAMOS_ORGANO_SOLICITANTE = "303";
	public static final String ESTANDAR_SOLICITUD_CONSULTAS = "304";
	public static final String GESTION_SOLICITUDES_CONSULTAS = "305";
	public static final String ENTREGA_UNIDADES_DOCUMENTALES = "306";

	// Permisos del módulo de DEPÓSITO
	public static final String CONSULTA_DEPOSITO = "400";
	public static final String REUBICACION_UNIDADES_INSTALACION = "401";
	public static final String CREACION_ELEMENTOS_DEPOSITO = "402";
	public static final String MODIFICACION_ELEMENTOS_DEPOSITO = "403";
	public static final String ELIMINACION_ELEMENTOS_DEPOSITO = "404";
	public static final String REUBICACION_UNIDADES_DOCUMENTALES = "405";
	public static final String ADMINISTRACION_FORMATOS = "406";

	// Permisos del módulo de FONDOS DOCUMENTALES
	public static final String CONSULTA_CUADRO_CLASIFICACION = "500";
	public static final String SOLICITUD_ALTA_SERIE = "501";
	public static final String GESTOR_SERIE = "502";
	public static final String GESTION_SOLICITUDES_SERIE = "503";
	public static final String CESION_CONTROL_GESTOR_SERIE = "504";
	public static final String CREACION_CUADRO_CLASIFICACION = "505";
	public static final String MODIFICACION_CUADRO_CLASIFICACION = "506";
	public static final String ELIMINACION_CUADRO_CLASIFICACION = "507";
	public static final String INGRESO_DIRECTO_UNIDADES_DOCUMENTALES = "508";

	// Permisos del módulo de DESCRIPCIÓN
	public static final String CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION = "600";
	public static final String EDICION_DESCRIPCION_CUADRO_CLASIFICACION = "601";
	public static final String ADMINISTRACION_TABLAS_VALIDACION = "602";
	public static final String ADMINISTRACION_DESCRIPTORES = "603";
	public static final String CONSULTA_DESCRIPCION_DESCRIPTOR = "604";
	public static final String EDICION_DESCRIPTOR = "605";
	public static final String ADMINISTRACION_DESCRIPCION = "606";
	public static final String USO_FICHA_ALTA_TRANSFERENCIA = "607";

	// Permisos del módulo de GESTIÓN DE USUARIOS
	public static final String CONSULTA_USUARIOS_ORGANOS_GRUPOS_ROLES = "700";
	public static final String ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES = "701";

	// Permisos para el módulo de DOCUMENTOS VITALES
	public static final String CONSULTA_DOCUMENTOS_VITALES = "800";
	public static final String EDICION_DOCUMENTOS_VITALES = "801";

	// Permisos del módulo de DOCUMENTOS ELECTRÓNICOS
	public static final String CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION = "900";
	public static final String EDICION_DOCUMENTOS_CUADRO_CLASIFICACION = "901";
	public static final String ADMINISTRACION_TAREAS_CAPTURA = "902";
	public static final String CAPTURA_DOCUMENTOS = "903";
	// public static final String ADMINISTRACION_FICHAS_CLASIFICADORES = "904";

	// Permisos para el módulo de Valoración y Eliminacion
	public static final String GESTION_VALORACIONES = "1000";
	public static final String GESTION_ELIMINACION_ESTANDAR = "1001";
	public static final String GESTION_ELIMINACION_TOTAL = "1002";
	public static final String GESTION_UDOCS_SERIE_CONSERVADAS = "1003";

	public static final String VISIBLE = "1100";

	// PERMISOS DE SALAS
	public static final String CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS = "1400";
	public static final String CONSULTA_ESTRUCTURA_SALAS = "1401";
	public static final String ADMINISTRACION_USUARIOS_SALAS_CONSULTA = "1402";
	public static final String CONSULTA_USUARIOS_SALAS = "1403";
	public static final String REGISTRO_ASISTENCIA_SALAS = "1404";

	static String[] PERMISOS_TRANSFERENCIAS = {
			ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS,
			AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS,
			ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS,
			AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS,
			GESTION_TRANSFERENCIAS_ORGANO_REMITENTE,
			GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR,
			GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR,
			UBICACION_RELACIONES_ENTREGA, ASIGNACION_RESERVA_ESPACIO_DEPOSITO,
			CORRECCION_ERRORES_COTEJO_EN_ARCHIVO };

	static String[] PERMISOS_PRESTAMOS_Y_CONSULTAS = {
			ESTANDAR_SOLICITUD_PRESTAMOS, AMPLIADO_SOLICITUD_PRESTAMOS,
			GESTION_PRESTAMOS_ARCHIVO, GESTION_PRESTAMOS_ORGANO_SOLICITANTE,
			ESTANDAR_SOLICITUD_CONSULTAS, GESTION_SOLICITUDES_CONSULTAS,
			ENTREGA_UNIDADES_DOCUMENTALES };

	// Permisos del módulo de DEPÓSITO
	static String[] PERMISOS_DEPOSITO = { CONSULTA_DEPOSITO,
			REUBICACION_UNIDADES_INSTALACION, CREACION_ELEMENTOS_DEPOSITO,
			MODIFICACION_ELEMENTOS_DEPOSITO, ELIMINACION_ELEMENTOS_DEPOSITO,
			REUBICACION_UNIDADES_DOCUMENTALES, ADMINISTRACION_FORMATOS };

	// Permisos del módulo de FONDOS DOCUMENTALES
	static String[] PERMISOS_FONDOS_DOCUMENTALES = {
			CONSULTA_CUADRO_CLASIFICACION, SOLICITUD_ALTA_SERIE, GESTOR_SERIE,
			GESTION_SOLICITUDES_SERIE, CESION_CONTROL_GESTOR_SERIE,
			CREACION_CUADRO_CLASIFICACION, MODIFICACION_CUADRO_CLASIFICACION,
			ELIMINACION_CUADRO_CLASIFICACION, GESTION_VALORACIONES,
			GESTION_ELIMINACION_ESTANDAR, GESTION_ELIMINACION_TOTAL,
			BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES,
			GESTION_UDOCS_SERIE_CONSERVADAS };

	// Permisos del módulo de DESCRIPCIÓN
	static String[] PERMISOS_DESCRIPCION = {
			CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION,
			EDICION_DESCRIPCION_CUADRO_CLASIFICACION,
			ADMINISTRACION_TABLAS_VALIDACION, ADMINISTRACION_DESCRIPTORES,
			CONSULTA_DESCRIPCION_DESCRIPTOR, EDICION_DESCRIPTOR,
			ADMINISTRACION_DESCRIPCION, USO_FICHA_ALTA_TRANSFERENCIA };

	// Permisos del módulo de GESTIÓN DE USUARIOS
	static String[] PERMISOS_USUARIOS = {
			CONSULTA_USUARIOS_ORGANOS_GRUPOS_ROLES,
			ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES };

	// Permisos del módulo de DOCUMENTOS ELECTRÓNICOS
	static String[] PERMISOS_DOCUMENTOS_ELECTRONICOS = {
			CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION,
			EDICION_DOCUMENTOS_CUADRO_CLASIFICACION, CAPTURA_DOCUMENTOS,
			ADMINISTRACION_TAREAS_CAPTURA };

	// Permisos del módulo de DOCUMENTOS VITALES
	static String[] PERMISOS_DOCUMENTOS_VITALES = {
			CONSULTA_DOCUMENTOS_VITALES, EDICION_DOCUMENTOS_VITALES };

	// Permisos del modulo Control de Acceso (SISTEMAS)
	static String[] PERMISOS_SISTEMA = { ADMINISTRACION_TOTAL_SISTEMA,
			CONSULTA_TOTAL_SISTEMA };

	// Permisos de Gestión de Salas
	static String[] PERMISOS_ESTRUCTURA_SALAS = {
			CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS,
			CONSULTA_ESTRUCTURA_SALAS, ADMINISTRACION_USUARIOS_SALAS_CONSULTA,
			CONSULTA_USUARIOS_SALAS, REGISTRO_ASISTENCIA_SALAS };

	public static String[] getPermisosModulo(int modulo) {
		String[] permisos = new String[0];
		switch (modulo) {
		case ArchivoModules.SISTEMA_MODULE:
			permisos = PERMISOS_SISTEMA;
			break;
		case ArchivoModules.TRANSFERENCIAS_MODULE:
			permisos = PERMISOS_TRANSFERENCIAS;

			// Comprobar si se permite realizar transferencias entre archivos
			if (ConfigConstants.getInstance()
					.getPermitirTransferenciasEntreArchivos()) {
				permisos = (String[]) ArrayUtils.add(permisos,
						ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS);
			}

			break;
		case ArchivoModules.AUDITORIA_MODULE:
			// permisos = PERMISOS_;
			break;
		case ArchivoModules.DEPOSITOS_MODULE:
			permisos = PERMISOS_DEPOSITO;
			break;
		case ArchivoModules.DESCRIPCION_MODULE:
			permisos = PERMISOS_DESCRIPCION;
			break;
		case ArchivoModules.FONDOS_MODULE:
			permisos = PERMISOS_FONDOS_DOCUMENTALES;

			// Comprobar si se permite realizar altas directas de unidades
			// documentales
			if (ConfigConstants.getInstance()
					.getPermitirAltaDirectaUnidadesDocumentales()) {
				permisos = (String[]) ArrayUtils.add(permisos,
						INGRESO_DIRECTO_UNIDADES_DOCUMENTALES);
			}

			break;
		case ArchivoModules.SERVICIOS_MODULE:
			permisos = PERMISOS_PRESTAMOS_Y_CONSULTAS;
			break;
		case ArchivoModules.USUARIOS_MODULE:
			permisos = PERMISOS_USUARIOS;
			break;
		case ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE:
			permisos = PERMISOS_DOCUMENTOS_ELECTRONICOS;
			break;
		case ArchivoModules.DOCUMENTOS_VITALES_MODULE:
			permisos = PERMISOS_DOCUMENTOS_VITALES;
			break;
		case ArchivoModules.SALAS_MODULE:
			if (ConfigConstants.getInstance().getMostrarSalas()) {
				permisos = PERMISOS_ESTRUCTURA_SALAS;
			}
			break;
		}
		return permisos;
	}

}