package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoModules;

/**
 * Gestor de seguridad para el módulo de gestión de usuarios y permisos
 */
public class ControlAccesoSecurityManager extends SecurityManagerBase {

	static ControlAccesoSecurityManager instance = null;

	/** *************** GRUPOS ***************** */
	public final static ActionObject ALTA_GRUPO = ActionObject.getInstance(
			getKey("ALTA_GRUPO"), ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject MODIFICACION_GRUPO = ActionObject
			.getInstance(getKey("MODIFICACION_GRUPO"),
					ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject ELIMINAR_GRUPO = ActionObject.getInstance(
			getKey("ELIMINAR_GRUPO"), ArchivoModules.USUARIOS_MODULE);

	/** *************** ORGANOS ***************** */
	public final static ActionObject ALTA_ORGANO = ActionObject.getInstance(
			getKey("ALTA_ORGANO"), ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject MODIFICACION_ORGANO = ActionObject
			.getInstance(getKey("MODIFICACION_ORGANO"),
					ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject ELIMINAR_ORGANO = ActionObject
			.getInstance(getKey("ELIMINAR_ORGANO"),
					ArchivoModules.USUARIOS_MODULE);

	/** *************** ROLES ***************** */
	public final static ActionObject ALTA_ROL = ActionObject.getInstance(
			getKey("ALTA_ROL"), ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject MODIFICACION_ROL = ActionObject
			.getInstance(getKey("MODIFICACION_ROL"),
					ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject ELIMINAR_ROL = ActionObject.getInstance(
			getKey("ELIMINAR_ROL"), ArchivoModules.USUARIOS_MODULE);

	/** *************** USUARIOS ***************** */
	public final static ActionObject ALTA_USUARIO = ActionObject.getInstance(
			getKey("ALTA_USUARIO"), ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject MODIFICACION_USUARIO = ActionObject
			.getInstance(getKey("MODIFICACION_USUARIO"),
					ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject ELIMINAR_USUARIO = ActionObject
			.getInstance(getKey("ELIMINAR_USUARIO"),
					ArchivoModules.USUARIOS_MODULE);

	/** *************** LISTA ACCESO ***************** */
	public final static ActionObject ALTA_LISTA_ACCESO = ActionObject
			.getInstance(getKey("ALTA_LISTA_ACCESO"),
					ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject MODIFICACION_LISTA_ACCESO = ActionObject
			.getInstance(getKey("MODIFICACION_LISTA_ACCESO"),
					ArchivoModules.USUARIOS_MODULE);

	public final static ActionObject ELIMINAR_LISTA_ACCESO = ActionObject
			.getInstance(getKey("ELIMINAR_LISTA_ACCESO"),
					ArchivoModules.USUARIOS_MODULE);

	ControlAccesoSecurityManager() {

		/** *************** GRUPOS ***************** */
		addAction(
				ALTA_GRUPO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				MODIFICACION_GRUPO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				ELIMINAR_GRUPO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });

		/** *************** ORGANOS ***************** */
		addAction(
				ALTA_ROL,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				MODIFICACION_ROL,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				ELIMINAR_ROL,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });

		/** *************** ROLES ***************** */
		addAction(
				ALTA_ORGANO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				MODIFICACION_ORGANO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				ELIMINAR_ORGANO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });

		/** *************** USUARIOS ***************** */
		addAction(
				ALTA_USUARIO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				MODIFICACION_USUARIO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				ELIMINAR_USUARIO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });

		/***************** LISTAS ACCESO **************/
		addAction(
				ALTA_LISTA_ACCESO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				MODIFICACION_LISTA_ACCESO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });
		addAction(
				ELIMINAR_LISTA_ACCESO,
				new String[][] { { AppPermissions.ADMINISTRACION_USUARIOS_ORGANOS_GRUPOS_ROLES } });

	}

	static ControlAccesoSecurityManager getInstance() {
		if (instance == null)
			instance = new ControlAccesoSecurityManager();
		return instance;
	}

	/**
	 * Obtiene el nombre completo de la clave.
	 * 
	 * @param key
	 *            Clave.
	 * @return Nombre completo de la clave.
	 */
	private static String getKey(String key) {
		return new StringBuffer().append("ControlAccesoSecurityManager")
				.append(key).toString();
	}

}