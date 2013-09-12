package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoModules;

/**
 * Implementacion del manager de seguridad para el m�dulo de pr�stamos
 */
public class ServiciosSecurityManager extends SecurityManagerBase {

	private static ServiciosSecurityManager prestamosSecurityManager = null;

	public final static ActionObject INSERTAR_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("INSERTAR_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject VER_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("VER_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject EDITAR_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("EDITAR_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject DELETE_DETALLES_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("DELETE_DETALLES_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject OBTENER_UDOCS_BY_NUMEXP_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("OBTENER_UDOCS_BY_NUMEXP_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject PRESTAMOS_X_SOLICITANTE_ACTION = ActionObject
			.getInstance(getKey("PRESTAMOS_X_SOLICITANTE_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject PRESTAMOS_GESTION_ACTION = ActionObject
			.getInstance(getKey("PRESTAMOS_GESTION_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	// public final static ActionObject PRESTAMOS_X_USUARIOGESTOR_ACTION =
	// ActionObject.getInstance(getKey("PRESTAMOS_X_USUARIOGESTOR_ACTION"),
	// ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject USUARIOS_PRESTAMOS_ACTION = ActionObject
			.getInstance(getKey("USUARIOS_PRESTAMOS_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject ENVIAR_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("ENVIAR_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject AUTORIZAR_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("AUTORIZAR_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject DENEGAR_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("DENEGAR_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject FINALIZAR_AUTORIZACION_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("FINALIZAR_AUTORIZACION_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject ELIMINAR_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("ELIMINAR_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject SOLICITAR_ENTREGA_RESERVA_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("SOLICITAR_ENTREGA_RESERVA_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject ENTREGAR_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("ENTREGAR_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject OBTENER_UDOCS_DEVUELTAS_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("OBTENER_UDOCS_DEVUELTAS_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject DEVOLVER_UDOCS_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("DEVOLVER_UDOCS_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject SOLICITAR_PRORROGA_ACTION = ActionObject
			.getInstance(getKey("SOLICITAR_PRORROGA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject AUTORIZAR_PRORROGA_ACTION = ActionObject
			.getInstance(getKey("AUTORIZAR_PRORROGA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject DENEGAR_PRORROGA_ACTION = ActionObject
			.getInstance(getKey("DENEGAR_PRORROGA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject RECLAMAR_ACTION = ActionObject
			.getInstance(getKey("RECLAMAR_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	// public final static ActionObject PRESTAMOS_ENTREGAR_ACTION =
	// ActionObject.getInstance(getKey("PRESTAMOS_ENTREGAR_ACTION"),ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject DETALLES_IMPRIMIR_SALIDA_PRESTAMO = ActionObject
			.getInstance(getKey("DETALLES_IMPRIMIR_SALIDA_PRESTAMO"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject ANIADIR_UDOC_PRESTAMO_ACTION = ActionObject
			.getInstance(getKey("ANIADIR_UDOC_PRESTAMO_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject CEDER_CONTROL_ACTION = ActionObject
			.getInstance(getKey("CEDER_CONTROL_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject REVISION_DOCUMENTACION_UDOC = ActionObject
			.getInstance(getKey("REVISION_DOCUMENTACION_UDOC"),
					ArchivoModules.SERVICIOS_MODULE);

	/** Consultas */
	public final static ActionObject INSERTAR_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("INSERTAR_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject CONSULTAS_GESTION_ACTION = ActionObject
			.getInstance(getKey("CONSULTAS_GESTION_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject OBTENER_UDOCS_CONSULTA_BY_NUMEXP = ActionObject
			.getInstance(getKey("OBTENER_UDOCS_CONSULTA_BY_NUMEXP"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject ELIMINAR_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("ELIMINAR_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject DELETE_DETALLES_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("DELETE_DETALLES_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject ANIADIR_UDOC_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("ANIADIR_UDOC_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject ENVIAR_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("ENVIAR_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject AUTORIZAR_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("AUTORIZAR_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject DENEGAR_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("DENEGAR_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject FINALIZAR_AUTORIZACION_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("FINALIZAR_AUTORIZACION_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject SOLICITAR_ENTREGA_RESERVA_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("SOLICITAR_ENTREGA_RESERVA_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject CONSULTAS_ENTREGAR_ACTION = ActionObject
			.getInstance(getKey("CONSULTAS_ENTREGAR_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject ENTREGAR_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("ENTREGAR_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject OBTENER_UDOCS_DEVUELTAS_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("OBTENER_UDOCS_DEVUELTAS_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject VER_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("VER_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);
	public final static ActionObject EDITAR_CONSULTA_ACTION = ActionObject
			.getInstance(getKey("EDITAR_CONSULTA_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);

	public final static ActionObject CAMBIAR_ESTADO_A_SOLICITADA = ActionObject
					.getInstance(getKey("CAMBIAR_ESTADO_A_SOLICITADA"),
							ArchivoModules.SERVICIOS_MODULE);

	static ServiciosSecurityManager getInstance() {
		if (prestamosSecurityManager == null)
			prestamosSecurityManager = new ServiciosSecurityManager();
		return prestamosSecurityManager;
	}

	private ServiciosSecurityManager() {
		/**
		 * Esta matriz de permisos expresa una disyuncion de conjunciones de
		 * permisos, es decir: Se tiene que cumplir al menos todos los permisos
		 * de una de las filas de permisos de la matriz.
		 */
		String[][] permisosConsultaPrestamo = new String[][] {
				{ AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS },
				{ AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS },
				{ AppPermissions.GESTION_PRESTAMOS_ARCHIVO },
				{ AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE },
				{ AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA } };
		addAction(VER_PRESTAMO_ACTION, permisosConsultaPrestamo);
		addAction(OBTENER_UDOCS_BY_NUMEXP_PRESTAMO_ACTION,
				permisosConsultaPrestamo);
		addAction(PRESTAMOS_X_SOLICITANTE_ACTION, permisosConsultaPrestamo);

		String[][] permisosEdicionPrestamo = new String[][] {
				{ AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS },
				{ AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS },
				{ AppPermissions.GESTION_PRESTAMOS_ARCHIVO },
				{ AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE },
				{ AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES } };
		addAction(INSERTAR_PRESTAMO_ACTION, permisosEdicionPrestamo);
		addAction(EDITAR_PRESTAMO_ACTION, permisosEdicionPrestamo);
		addAction(DELETE_DETALLES_PRESTAMO_ACTION, permisosEdicionPrestamo);
		addAction(ENVIAR_PRESTAMO_ACTION, permisosEdicionPrestamo);
		addAction(ELIMINAR_PRESTAMO_ACTION, permisosEdicionPrestamo);
		addAction(SOLICITAR_ENTREGA_RESERVA_PRESTAMO_ACTION,
				permisosEdicionPrestamo);
		addAction(SOLICITAR_PRORROGA_ACTION, permisosEdicionPrestamo);
		addAction(ANIADIR_UDOC_PRESTAMO_ACTION, permisosEdicionPrestamo);

		String[][] permisosGestorPrestamo = new String[][] { { AppPermissions.GESTION_PRESTAMOS_ARCHIVO } };
		// addAction(PRESTAMOS_X_USUARIOGESTOR_ACTION, permisosGestorPrestamo);
		addAction(USUARIOS_PRESTAMOS_ACTION, permisosGestorPrestamo);
		addAction(PRESTAMOS_GESTION_ACTION, permisosGestorPrestamo);
		addAction(RECLAMAR_ACTION, permisosGestorPrestamo);

		addAction(
				CEDER_CONTROL_ACTION,
				new String[][] { { AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE } });

		addAction(AUTORIZAR_PRESTAMO_ACTION, permisosGestorPrestamo);
		addAction(DENEGAR_PRESTAMO_ACTION, permisosGestorPrestamo);
		addAction(FINALIZAR_AUTORIZACION_PRESTAMO_ACTION,
				permisosGestorPrestamo);
		addAction(DEVOLVER_UDOCS_PRESTAMO_ACTION, permisosGestorPrestamo);
		addAction(AUTORIZAR_PRORROGA_ACTION, permisosGestorPrestamo);
		addAction(DENEGAR_PRORROGA_ACTION, permisosGestorPrestamo);

		String[][] permisosEntregaPrestamo = new String[][] { { AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES } };
		addAction(ENTREGAR_PRESTAMO_ACTION, permisosEntregaPrestamo);
		addAction(OBTENER_UDOCS_DEVUELTAS_PRESTAMO_ACTION,
				permisosEntregaPrestamo);
		// addAction(PRESTAMOS_ENTREGAR_ACTION,permisosEntregaPrestamo);
		addAction(DETALLES_IMPRIMIR_SALIDA_PRESTAMO, permisosEntregaPrestamo);

		/** Consultas */
		String[][] permisosConsultas = new String[][] {
				{ AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS },
				{ AppPermissions.GESTION_SOLICITUDES_CONSULTAS },
				{ AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA } };
		addAction(VER_CONSULTA_ACTION, permisosConsultas);
		addAction(OBTENER_UDOCS_CONSULTA_BY_NUMEXP, permisosConsultas);

		String[][] permisosElaboracionConsultas = new String[][] {
				{ AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS },
				{ AppPermissions.GESTION_SOLICITUDES_CONSULTAS },
				{ AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES } };
		addAction(INSERTAR_CONSULTA_ACTION, permisosElaboracionConsultas);
		addAction(ELIMINAR_CONSULTA_ACTION, permisosElaboracionConsultas);
		addAction(DELETE_DETALLES_CONSULTA_ACTION, permisosElaboracionConsultas);
		addAction(ANIADIR_UDOC_CONSULTA_ACTION, permisosElaboracionConsultas);
		addAction(ENVIAR_CONSULTA_ACTION, permisosElaboracionConsultas);
		addAction(SOLICITAR_ENTREGA_RESERVA_CONSULTA_ACTION,
				permisosElaboracionConsultas);
		addAction(EDITAR_CONSULTA_ACTION, permisosElaboracionConsultas);

		String[][] permisosGestionConsultas = new String[][] { { AppPermissions.GESTION_SOLICITUDES_CONSULTAS } };
		addAction(CONSULTAS_GESTION_ACTION, permisosGestionConsultas);
		addAction(AUTORIZAR_CONSULTA_ACTION, permisosGestionConsultas);
		addAction(DENEGAR_CONSULTA_ACTION, permisosGestionConsultas);

		addAction(FINALIZAR_AUTORIZACION_CONSULTA_ACTION,
				permisosGestionConsultas);

		String[][] permisosEntregaConsultas = new String[][] { { AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES } };
		addAction(CONSULTAS_ENTREGAR_ACTION, permisosEntregaConsultas);
		addAction(ENTREGAR_CONSULTA_ACTION, permisosEntregaConsultas);

		addAction(CAMBIAR_ESTADO_A_SOLICITADA, permisosGestionConsultas);

		addAction(OBTENER_UDOCS_DEVUELTAS_CONSULTA_ACTION,
				permisosEntregaConsultas);

		String[][] permisosRevisionDocumentacion = new String[][] { {
				AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION,
				AppPermissions.ADMINISTRACION_DESCRIPCION } };
		addAction(REVISION_DOCUMENTACION_UDOC, permisosRevisionDocumentacion);
	}

	private static String getKey(String key) {
		return new StringBuffer("PrestamosSecurityManager").append(key)
				.toString();
	}

}
