package common.security;

import se.usuarios.AppPermissions;

import common.ConfigConstants;
import common.definitions.ArchivoModules;

/**
 * Gestor de seguridad para el modulo de transferencias
 */
public class TransferenciasSecurityManager extends SecurityManagerBase {
	static TransferenciasSecurityManager instance = null;

	// Acciones sobre previsiones
	public final static ActionObject GESTION_TRANSFERENCIAS = ActionObject
			.getInstance("GESTION_TRANSFERENCIAS",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject GESTION_PREVISION_EN_ORGANO_REMITENTE = ActionObject
			.getInstance("GESTION_PREVISION_EN_ORGANO_REMITENTE",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject GESTION_PREVISION_EN_ARCHIVO_RECEPTOR = ActionObject
			.getInstance("GESTION_PREVISION_EN_ARCHIVO_RECEPTOR",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject GESTION_RELACION_EN_ORGANO_REMITENTE = ActionObject
			.getInstance("GESTION_RELACION_EN_ORGANO_REMITENTE",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject GESTION_RELACION_EN_ARCHIVO_RECEPTOR = ActionObject
			.getInstance("GESTION_RELACION_EN_ARCHIVO_RECEPTOR",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject CREAR_PREVISION = ActionObject
			.getInstance("CREAR_PREVISION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject CERRAR_PREVISIONES = ActionObject
			.getInstance("CERRAR_PREVISIONES",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject ALTA_PREVISION_ORDINARIA = ActionObject
			.getInstance("ALTA_PREVISION_ORDINARIA",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject ALTA_PREVISION_EXTRAORDINARIA = ActionObject
			.getInstance("ALTA_PREVISION_EXTRAORDINARIA",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject ALTA_PREVISION_EXTRAORDINARIA_ARCHIVO = ActionObject
			.getInstance("ALTA_PREVISION_EXTRAORDINARIA_ARCHIVO",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject ALTA_PREVISION_ENTRE_ARCHIVOS = ActionObject
			.getInstance("ALTA_PREVISION_ENTRE_ARCHIVOS",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	// public final static ActionObject ELIMINAR_PREVISION =
	// ActionObject.getInstance(
	// "ELIMINAR_PREVISION", ArchivoModules.TRANSFERENCIAS_MODULE);
	// public final static ActionObject ENVIAR_PREVISION =
	// ActionObject.getInstance("ENVIAR_PREVISION",
	// ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject ACEPTAR_PREVISION = ActionObject
			.getInstance("ACEPTAR_PREVISION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	// public final static ActionObject EDITAR_PREVISION =
	// ActionObject.getInstance("EDITAR_PREVISION",
	// ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject MODIFICAR_PREVISION = ActionObject
			.getInstance("MODIFICAR_PREVISION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	// // Acciones sobre relaciones
	// public final static ActionObject CREAR_RELACION =
	// ActionObject.getInstance("CREAR_RELACION",
	// ArchivoModules.TRANSFERENCIAS_MODULE);
	// public final static ActionObject MODIFICAR_RELACION =
	// ActionObject.getInstance("MODIFICAR_RELACION",
	// ArchivoModules.TRANSFERENCIAS_MODULE);
	// public final static ActionObject ENVIAR_RELACION =
	// ActionObject.getInstance("ENVIAR_RELACION",
	// ArchivoModules.TRANSFERENCIAS_MODULE);
	// public final static ActionObject ELIMINAR_RELACION =
	// ActionObject.getInstance("ELIMINAR_RELACION",
	// ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject RECIBIR_RELACION = ActionObject
			.getInstance("RECIBIR_RELACION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject COTEJAR_RELACION = ActionObject
			.getInstance("COTEJAR_RELACION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject FINALIZAR_CORRECCION = ActionObject
			.getInstance("FINALIZAR_CORRECCION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject IMPRESION_CARTELAS_PROVISIONALES = ActionObject
			.getInstance("IMPRESION_CARTELAS_PROVISIONALES",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject IMPRESION_CARTELAS_DEFINITIVAS = ActionObject
			.getInstance("IMPRESION_CARTELAS_DEFINITIVAS",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject VALIDAR_RELACION = ActionObject
			.getInstance("VALIDAR_RELACION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject MODIFICAR_UBICACION = ActionObject
			.getInstance("MODIFICAR_UBICACION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject GESTION_SOLICITUD_RESERVA = ActionObject
			.getInstance("GESTION_SOLICITUD_RESERVA",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject UBICAR_RELACION = ActionObject
			.getInstance("UBICAR_RELACION",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject CESION_CONTROL_PREVISIONES = ActionObject
			.getInstance("CESION_CONTROL_PREVISIONES",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject CESION_CONTROL_RELACIONES_EN_ARCHIVO = ActionObject
			.getInstance("CESION_CONTROL_RELACIONES_EN_ARCHIVO",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject CESION_CONTROL_RELACIONES_EN_ORG_REMITENTE = ActionObject
			.getInstance("CESION_CONTROL_RELACIONES_EN_ORG_REMITENTE",
					ArchivoModules.TRANSFERENCIAS_MODULE);

	// Ingreso directo de unidades documentales
	public final static ActionObject ELABORACION_INGRESOS_DIRECTOS = ActionObject
			.getInstance("ELABORACION_INGRESOS_DIRECTOS",
					ArchivoModules.TRANSFERENCIAS_MODULE);

	// Acciones sobre previsiones
	public final static ActionObject CORRECCION_ERRORES_EN_ARCHIVO = ActionObject
			.getInstance("CORRECCION_ERRORES_EN_ARCHIVO",
					ArchivoModules.TRANSFERENCIAS_MODULE);

	public final static ActionObject ACTIVAR_REENCAJADO = ActionObject
			.getInstance("ACTIVAR_REENCAJADO",
					ArchivoModules.TRANSFERENCIAS_MODULE);
	public final static ActionObject CANCELAR_REENCAJADO = ActionObject
			.getInstance("CANCELAR_REENCAJADO",
					ArchivoModules.TRANSFERENCIAS_MODULE);

	private TransferenciasSecurityManager() {
		addAction(
				GESTION_TRANSFERENCIAS,
				new String[][] {
						{ AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR },
						{ AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE },
						{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
						{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
						{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
						{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
						{ AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS },
						{ AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR },
						{ AppPermissions.UBICACION_RELACIONES_ENTREGA },
						{ AppPermissions.ASIGNACION_RESERVA_ESPACIO_DEPOSITO },
						{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });

		addAction(
				CORRECCION_ERRORES_EN_ARCHIVO,
				new String[][] { { AppPermissions.CORRECCION_ERRORES_COTEJO_EN_ARCHIVO } });

		addAction(
				ELABORACION_INGRESOS_DIRECTOS,
				new String[][] { { AppPermissions.INGRESO_DIRECTO_UNIDADES_DOCUMENTALES } });

		String[][] permisosElaboracionTransferencias = {
				{ AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE },
				{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
				{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
				{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
				{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
				{ AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS },
				{ AppPermissions.INGRESO_DIRECTO_UNIDADES_DOCUMENTALES } };
		addAction(GESTION_PREVISION_EN_ORGANO_REMITENTE,
				permisosElaboracionTransferencias);

		addAction(
				GESTION_PREVISION_EN_ARCHIVO_RECEPTOR,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR } });
		addAction(GESTION_RELACION_EN_ORGANO_REMITENTE,
				permisosElaboracionTransferencias);

		addAction(GESTION_RELACION_EN_ARCHIVO_RECEPTOR, new String[][] {
				{ AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR },
				{ AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR } });
		addAction(
				CREAR_PREVISION,
				new String[][] {
						{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
						{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
						{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
						{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
						{ AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS } });
		addAction(
				CERRAR_PREVISIONES,
				new String[][] {
						{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
						{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
						{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
						{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
						{ AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS } });
		addAction(
				ALTA_PREVISION_ORDINARIA,
				new String[][] {
						{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS },
						{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS } });
		addAction(
				ALTA_PREVISION_EXTRAORDINARIA,
				new String[][] {
						{ AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS },
						{ AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS } });
		addAction(
				ALTA_PREVISION_EXTRAORDINARIA_ARCHIVO,
				new String[][] { { AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS } });
		addAction(
				ALTA_PREVISION_ENTRE_ARCHIVOS,
				new String[][] { { AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS } });

		// addAction(ELIMINAR_PREVISION,
		// new String[][] { {
		// AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		// addAction(ENVIAR_PREVISION,
		// new String[][] { {
		// AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		addAction(
				ACEPTAR_PREVISION,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR } });
		// addAction(EDITAR_PREVISION,
		// new String[][] { {
		// AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		addAction(
				MODIFICAR_PREVISION,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR } });
		//
		// addAction(CREAR_RELACION,
		// new String[][] { {
		// AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		// addAction(MODIFICAR_RELACION,
		// new String[][] { {
		// AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		// addAction(ENVIAR_RELACION,
		// new String[][] { {
		// AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		// addAction(ELIMINAR_RELACION,
		// new String[][] { {
		// AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });

		addAction(
				RECIBIR_RELACION,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR } });
		addAction(COTEJAR_RELACION, new String[][] {
				{ AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR },
				{ AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR } });
		// No se esta utilizando. Comentada la comprobacion
		// GestionRelacionesEntregaBIImpl.finalizarCorreccionErrores(String
		// idRelacion) throws ActionNotAllowedException
		addAction(
				FINALIZAR_CORRECCION,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		addAction(
				IMPRESION_CARTELAS_PROVISIONALES,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		addAction(
				IMPRESION_CARTELAS_DEFINITIVAS,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR } });
		addAction(VALIDAR_RELACION, new String[][] { {
				AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR,
				AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR } });
		addAction(
				MODIFICAR_UBICACION,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR } });
		addAction(
				GESTION_SOLICITUD_RESERVA,
				new String[][] { { AppPermissions.ASIGNACION_RESERVA_ESPACIO_DEPOSITO } });
		addAction(
				UBICAR_RELACION,
				new String[][] { { AppPermissions.UBICACION_RELACIONES_ENTREGA } });
		addAction(
				CESION_CONTROL_PREVISIONES,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });
		addAction(
				CESION_CONTROL_RELACIONES_EN_ARCHIVO,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR } });
		addAction(
				CESION_CONTROL_RELACIONES_EN_ORG_REMITENTE,
				new String[][] { { AppPermissions.GESTION_TRANSFERENCIAS_ORGANO_REMITENTE } });

		if (ConfigConstants.getInstance().getPermitirReencajado()) {
			addAction(
					ACTIVAR_REENCAJADO,
					new String[][] { { AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS } });
			addAction(
					CANCELAR_REENCAJADO,
					new String[][] { { AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS } });
		}
	}

	static TransferenciasSecurityManager getInstance() {
		if (instance == null)
			instance = new TransferenciasSecurityManager();
		return instance;
	}
}