/**
 *
 */
package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class SalasSecurityManager extends SecurityManagerBase {

	static SalasSecurityManager salasSecurityManager = null;

	static ActionObject getActionObject(String nombre) {
		return ActionObject.getInstance(nombre, ArchivoModules.SALAS_MODULE);
	}

	public final static ActionObject VER_EDIFICIO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_EDIFICIO_NAME);
	public final static ActionObject VER_EDIFICIOS_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_EDIFICIOS_NAME);
	public final static ActionObject ALTA_EDIFICIO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ALTA_EDIFICIO_NAME);
	public final static ActionObject MODIFICAR_EDIFICIO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_EDICION_EDIFICIO_NAME);
	public final static ActionObject ELIMINAR_EDIFICIO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ELIMINAR_EDIFICIO_NAME);

	public static final ActionObject VER_SALA_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_SALA_NAME);
	public static final ActionObject VER_SALAS_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_SALAS_NAME);
	public static final ActionObject ALTA_SALA_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ALTA_SALA_NAME);
	public static final ActionObject MODIFICAR_SALA_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_MODIFICAR_SALA_NAME);
	public static final ActionObject ELIMINAR_SALA_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ELIMINAR_SALA_NAME);

	public static final ActionObject VER_MESA_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_MESA_NAME);
	public static final ActionObject VER_MESAS_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_MESAS_NAME);
	public static final ActionObject ALTA_MESA_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ALTA_MESA_NAME);
	public static final ActionObject MODIFICAR_MESA_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_MODIFICAR_MESA_NAME);
	public static final ActionObject ELIMINAR_MESA_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ELIMINAR_MESA_NAME);

	public static final ActionObject VER_USUARIO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_USUARIO_NAME);
	public static final ActionObject VER_USUARIOS_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_USUARIOS_NAME);
	public static final ActionObject ALTA_USUARIO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ALTA_USUARIO_NAME);
	public static final ActionObject MODIFICAR_USUARIO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_MODIFICAR_USUARIO_NAME);
	public static final ActionObject ELIMINAR_USUARIO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ELIMINAR_USUARIO_NAME);

	public static final ActionObject VER_REGISTRO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_REGISTRO_NAME);
	public static final ActionObject VER_REGISTROS_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_VER_REGISTROS_NAME);
	public static final ActionObject ALTA_REGISTRO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ALTA_REGISTRO_NAME);
	public static final ActionObject MODIFICAR_REGISTRO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_MODIFICAR_REGISTRO_NAME);
	public static final ActionObject ELIMINAR_REGISTRO_ACTION = getActionObject(ArchivoActions.SALAS_MODULE_ELIMINAR_REGISTRO_NAME);

	private SalasSecurityManager() {
		addAction(
				VER_EDIFICIO_ACTION,
				new String[][] {
						{ AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_TOTAL_SISTEMA }

				});

		addAction(
				VER_EDIFICIOS_ACTION,
				new String[][] {
						{ AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });

		addAction(
				ALTA_EDIFICIO_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });
		addAction(
				MODIFICAR_EDIFICIO_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });
		addAction(
				ELIMINAR_EDIFICIO_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });

		addAction(
				VER_SALA_ACTION,
				new String[][] {
						{ AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_TOTAL_SISTEMA }

				});

		addAction(
				VER_SALAS_ACTION,
				new String[][] {
						{ AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });
		addAction(
				ALTA_SALA_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });
		addAction(
				MODIFICAR_SALA_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });
		addAction(
				ELIMINAR_SALA_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });

		addAction(VER_USUARIO_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_USUARIOS_SALAS_CONSULTA },
				{ AppPermissions.CONSULTA_USUARIOS_SALAS },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });

		addAction(VER_USUARIOS_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_USUARIOS_SALAS_CONSULTA },
				{ AppPermissions.CONSULTA_USUARIOS_SALAS },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });

		addAction(
				ALTA_USUARIO_ACTION,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_SALAS_CONSULTA } });
		addAction(
				MODIFICAR_USUARIO_ACTION,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_SALAS_CONSULTA } });
		addAction(
				ELIMINAR_USUARIO_ACTION,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_SALAS_CONSULTA } });

		addAction(
				ALTA_MESA_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });
		addAction(
				MODIFICAR_MESA_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });
		addAction(
				ELIMINAR_MESA_ACTION,
				new String[][] { { AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS } });

		addAction(
				VER_MESA_ACTION,
				new String[][] {
						{ AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });

		addAction(
				VER_MESAS_ACTION,
				new String[][] {
						{ AppPermissions.CREACION_ELIMINACION_MODIFICACION_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_ESTRUCTURA_SALAS },
						{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });

		addAction(ALTA_REGISTRO_ACTION,
				new String[][] { { AppPermissions.REGISTRO_ASISTENCIA_SALAS } });
		addAction(MODIFICAR_REGISTRO_ACTION,
				new String[][] { { AppPermissions.REGISTRO_ASISTENCIA_SALAS } });
		addAction(ELIMINAR_REGISTRO_ACTION,
				new String[][] { { AppPermissions.REGISTRO_ASISTENCIA_SALAS } });

		addAction(VER_REGISTRO_ACTION, new String[][] {
				{ AppPermissions.REGISTRO_ASISTENCIA_SALAS },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });

		addAction(VER_REGISTROS_ACTION, new String[][] {
				{ AppPermissions.REGISTRO_ASISTENCIA_SALAS },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA } });
	}

	static SalasSecurityManager getInstance() {
		if (salasSecurityManager == null)
			salasSecurityManager = new SalasSecurityManager();

		return salasSecurityManager;
	}

}
