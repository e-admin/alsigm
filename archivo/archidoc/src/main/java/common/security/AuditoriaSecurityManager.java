package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoModules;

/**
 *
 */
public class AuditoriaSecurityManager extends SecurityManagerBase {

	static AuditoriaSecurityManager auditoriaSecurityManager = null;

	public final static ActionObject INSERTAR_ACTION = ActionObject
			.getInstance(getKey("INSERTAR_ACTION"),
					ArchivoModules.SERVICIOS_MODULE);

	private AuditoriaSecurityManager() {
		addAction(
				INSERTAR_ACTION,
				new String[][] { { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA } });
	}

	private static String getKey(String key) {
		return new StringBuffer("AuditoriaSecurityManager").append(key)
				.toString();
	}

	static AuditoriaSecurityManager getInstance() {
		if (auditoriaSecurityManager == null)
			auditoriaSecurityManager = new AuditoriaSecurityManager();

		return auditoriaSecurityManager;
	}
}
