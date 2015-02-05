package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;

/**
 * Definición de los permisos necesarios para llevar a cabo las acciones del
 * módulo de gestión de fondos documentales que requieren que el usuario que las
 * invoca posea algún permiso
 */
public final class FondosSecurityManager extends SecurityManagerBase {

	static FondosSecurityManager fondosSecurityManager = null;

	static ActionObject getActionObject(String nombre) {
		return ActionObject.getInstance(nombre, ArchivoModules.FONDOS_MODULE);
	}

	public final static ActionObject CONSULTA_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_CONSULTA_CUADRO_NAME);
	public final static ActionObject BAJAUDOC_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_BAJA_UDOC_NAME);
	public final static ActionObject BAJAUDOC_ENUI_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_BAJA_UDOCENUI_NAME);

	public final static ActionObject ALTA_ELEMENTO_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_ALTA_ELEMENTO_NAME);
	public final static ActionObject MODIFICAR_ELEMENTO_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_MODIFICACION_ELEMENTO_NAME);
	public final static ActionObject ELIMINACION_ELEMENTO_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_BAJA_ELEMENTO_NAME);
	public final static ActionObject PUBLICAR_ELEMENTO_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_PUBLICACION_ELEMENTO_NAME);
	public final static ActionObject SOLICITUD_ALTA_SERIE_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_SOLICITUD_ALTA_SERIE_NAME);
	public final static ActionObject ALTA_DIRECTA_SERIE = getActionObject(ArchivoActions.FONDOS_MODULE_ALTA_ELEMENTO_NAME);
	public final static ActionObject CONSULTA_SOLICITUDES_SERIE_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_CONSULTA_SOLICITUDES_SERIE_NAME);
	public final static ActionObject GESTION_SOLICITUDES_SERIE_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_GESTION_SOLICITUDES_SERIE_NAME);
	public final static ActionObject SOLICITUD_Y_AUTORIZACION_AUTOMATICA_CAMBIOS_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_GESTION_SOLICITUDES_SERIE_NAME);
	public final static ActionObject SOLICITUD_CAMBIOS_ESTADO_O_MODIF_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE_NAME);
	public final static ActionObject SOLICITUD_CAMBIOS_IDENTIFICACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE_NAME);
	public final static ActionObject PASO_A_EN_ESTUDIO_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE_NAME);
	public final static ActionObject CEDER_CONTROL_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_CEDER_CONTROL_SERIE_NAME);
	public final static ActionObject MOVER_ELEMENTO_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_MOVER_ELEMENTO_NAME);

	// Acciones de Valoraciones
	public final static ActionObject AUTORIZAR_VALIDACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_AUTORIZACION_VALIDACION_VALORACION_NAME);
	public final static ActionObject RECHAZAR_VALIDACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_RECHAZO_VALIDACION_VALORACION_NAME);
	public final static ActionObject AUTORIZAR_EVALUACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_AUTORIZACION_EVALUACION_VALORACION_NAME);
	public final static ActionObject RECHAZAR_EVALUACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_RECHAZO_EVALUACION_VALORACION_NAME);
	public final static ActionObject DICTAMINAR_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_DICTAMEN_VALORACION_NAME);
	public final static ActionObject IMPRIMIR_VALORACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_IMPRIMIR_VALORACION_NAME);
	public final static ActionObject IMPRIMIR_SELECCION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_IMPRIMIR_SELECCION_NAME);
	public final static ActionObject EDITAR_VALORACIONES_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_EDITAR_VALORACIONES_NAME);

	// ELIMINACION ACTIONS
	public final static ActionObject CREAR_ELIMINACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_ALTA_ELIMINACION_NAME);
	public final static ActionObject ACTUALIZAR_ELIMINACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_ACTUALIZACION_ELIMINACION_NAME);
	public final static ActionObject SOLICITAR_APROBACION_ELIMINACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_SOLICITAR_APROBACION_ELIMINACION_NAME);
	public final static ActionObject APROBAR_ELIMINACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_APROBAR_ELIMINACION_NAME);
	public final static ActionObject RECHAZAR_ELIMINACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_RECHAZAR_ELIMINACION_NAME);
	public final static ActionObject EJECUTAR_ELIMINACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_EJECUTAR_ELIMINACION_NAME);
	public final static ActionObject DESTRUIR_FISICAMENTE_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_DESTRUIR_FISICAMENTE_NAME);
	public final static ActionObject FINALIZAR_ELIMINACION_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_FINALIZAR_ELIMINACION_NAME);
	public final static ActionObject EDITAR_SELECCIONES_ACTION = getActionObject(ArchivoActions.FONDOS_MODULE_EDITAR_SELECCIONES_NAME);

	// CONSERVACION ACTIONS
	public final static ActionObject ELIMINAR_MARCA_CONSERVADA = getActionObject(ArchivoActions.FONDOS_MODULE_ELIMINAR_MARCA_CONSERVADA_NAME);

	private FondosSecurityManager() {

		addAction(
				ALTA_ELEMENTO_ACTION,
				new String[][] { { AppPermissions.CREACION_CUADRO_CLASIFICACION } });
		addAction(
				MODIFICAR_ELEMENTO_ACTION,
				new String[][] { { AppPermissions.MODIFICACION_CUADRO_CLASIFICACION } });
		addAction(
				ELIMINACION_ELEMENTO_ACTION,
				new String[][] { { AppPermissions.ELIMINACION_CUADRO_CLASIFICACION } });
		addAction(
				PUBLICAR_ELEMENTO_ACTION,
				new String[][] { { AppPermissions.MODIFICACION_CUADRO_CLASIFICACION } });
		addAction(SOLICITUD_ALTA_SERIE_ACTION,
				new String[][] { { AppPermissions.SOLICITUD_ALTA_SERIE } });
		addAction(ALTA_DIRECTA_SERIE, new String[][] { {
				AppPermissions.SOLICITUD_ALTA_SERIE,
				AppPermissions.GESTION_SOLICITUDES_SERIE } });
		addAction(CONSULTA_SOLICITUDES_SERIE_ACTION, new String[][] {
				{ AppPermissions.GESTION_SOLICITUDES_SERIE },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });
		addAction(GESTION_SOLICITUDES_SERIE_ACTION,
				new String[][] { { AppPermissions.GESTION_SOLICITUDES_SERIE } });
		addAction(SOLICITUD_Y_AUTORIZACION_AUTOMATICA_CAMBIOS_ACTION,
				new String[][] { { AppPermissions.GESTOR_SERIE,
						AppPermissions.GESTION_SOLICITUDES_SERIE } });
		addAction(SOLICITUD_CAMBIOS_ESTADO_O_MODIF_ACTION,
				new String[][] { { AppPermissions.GESTOR_SERIE } });
		addAction(SOLICITUD_CAMBIOS_IDENTIFICACION_ACTION,
				new String[][] { { AppPermissions.GESTOR_SERIE } });
		addAction(PASO_A_EN_ESTUDIO_ACTION,
				new String[][] { { AppPermissions.GESTION_SOLICITUDES_SERIE } });
		addAction(CEDER_CONTROL_ACTION, new String[][] { {
				AppPermissions.GESTOR_SERIE,
				AppPermissions.CESION_CONTROL_GESTOR_SERIE } });
		addAction(
				MOVER_ELEMENTO_ACTION,
				new String[][] { { AppPermissions.MODIFICACION_CUADRO_CLASIFICACION } });

		addAction(CONSULTA_ACTION, new String[][] {
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_CUADRO_CLASIFICACION } });
		addAction(BAJAUDOC_ACTION, new String[][] {
				{ AppPermissions.ELIMINACION_CUADRO_CLASIFICACION },
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA } });
		addAction(BAJAUDOC_ENUI_ACTION, new String[][] {
				{ AppPermissions.ELIMINACION_CUADRO_CLASIFICACION },
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA } });

		addAction(ELIMINAR_MARCA_CONSERVADA, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.GESTION_UDOCS_SERIE_CONSERVADAS } });

		// VALORACION
		String[][] permisosValoracion = new String[][] { { AppPermissions.GESTION_VALORACIONES } };
		addAction(AUTORIZAR_VALIDACION_ACTION, permisosValoracion);
		addAction(RECHAZAR_VALIDACION_ACTION, permisosValoracion);
		addAction(AUTORIZAR_EVALUACION_ACTION, permisosValoracion);
		addAction(RECHAZAR_EVALUACION_ACTION, permisosValoracion);
		addAction(DICTAMINAR_ACTION, permisosValoracion);
		addAction(IMPRIMIR_VALORACION_ACTION, permisosValoracion);
		addAction(EDITAR_VALORACIONES_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.GESTOR_SERIE } });

		// ELIMINACION
		addAction(EDITAR_SELECCIONES_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.GESTION_ELIMINACION_ESTANDAR },
				{ AppPermissions.GESTION_ELIMINACION_TOTAL } });

		String[][] permisosEliminacion = new String[][] {
				{ AppPermissions.GESTION_ELIMINACION_ESTANDAR },
				{ AppPermissions.GESTION_ELIMINACION_TOTAL },
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA } };
		addAction(CREAR_ELIMINACION_ACTION, permisosEliminacion);
		addAction(ACTUALIZAR_ELIMINACION_ACTION, permisosEliminacion);
		addAction(SOLICITAR_APROBACION_ELIMINACION_ACTION, permisosEliminacion);
		addAction(DESTRUIR_FISICAMENTE_ACTION, permisosEliminacion);
		addAction(FINALIZAR_ELIMINACION_ACTION, permisosEliminacion);
		addAction(IMPRIMIR_SELECCION_ACTION, permisosEliminacion);

		String[][] permisosAdminEliminacion = new String[][] {
				{ AppPermissions.GESTION_ELIMINACION_TOTAL },
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA } };
		addAction(APROBAR_ELIMINACION_ACTION, permisosAdminEliminacion);
		addAction(RECHAZAR_ELIMINACION_ACTION, permisosAdminEliminacion);

		String[][] permisosDepositoEliminacion = new String[][] {
				{ AppPermissions.ELIMINACION_ELEMENTOS_DEPOSITO },
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA } };
		addAction(EJECUTAR_ELIMINACION_ACTION, permisosDepositoEliminacion);
	}

	public static FondosSecurityManager getInstance() {
		if (fondosSecurityManager == null)
			fondosSecurityManager = new FondosSecurityManager();
		return fondosSecurityManager;
	}

}
