package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;

public class DepositoSecurityManager extends SecurityManagerBase {

	static DepositoSecurityManager depositoSecurityManager = null;

	static ActionObject getActionObject(String nombre) {
		return ActionObject
				.getInstance(nombre, ArchivoModules.DEPOSITOS_MODULE);
	}

	public final static ActionObject ALTA_ELEMENTO_ACTION = getActionObject(ArchivoActions.DEPOSITOS_MODULE_ALTA_ELEMENTO_DEPOSITO_NAME);
	public final static ActionObject MODIFICAR_ELEMENTO_ACTION = getActionObject(ArchivoActions.DEPOSITOS_MODULE_MODIFICACION_ELEMENTO_DEPOSITO_NAME);
	public final static ActionObject ELIMINACION_ELEMENTO_ACTION = getActionObject(ArchivoActions.DEPOSITOS_MODULE_ELIMINACION_ELEMENTO_DEPOSITO_NAME);
	public final static ActionObject CONSULTA_DEPOSITO = getActionObject(ArchivoActions.DEPOSITOS_MODULE_CONSULTA_DEPOSITO_NAME);
	public final static ActionObject REUBICACION_UNIDADES_INSTALACION = getActionObject(ArchivoActions.DEPOSITOS_MODULE_REUBICAR_UI_NAME);
	public final static ActionObject REUBICACION_UNIDADES_DOCUMENTALES = getActionObject(ArchivoActions.DEPOSITOS_MODULE_REUBICAR_UDOCS_NAME);
	public final static ActionObject ALTA_FORMATO_ACTION = getActionObject(ArchivoActions.DEPOSITOS_MODULE_ALTA_FORMATO_NAME);
	public final static ActionObject MODIFICAR_FORMATO_ACTION = getActionObject(ArchivoActions.DEPOSITOS_MODULE_MODIFICACION_FORMATO_NAME);
	public final static ActionObject BAJA_FORMATO_ACTION = getActionObject(ArchivoActions.DEPOSITOS_MODULE_BAJA_FORMATO_NAME);

	private DepositoSecurityManager() {
		addAction(
				ALTA_ELEMENTO_ACTION,
				new String[][] { { AppPermissions.CREACION_ELEMENTOS_DEPOSITO } });
		addAction(
				MODIFICAR_ELEMENTO_ACTION,
				new String[][] { { AppPermissions.MODIFICACION_ELEMENTOS_DEPOSITO } });
		addAction(
				ELIMINACION_ELEMENTO_ACTION,
				new String[][] { { AppPermissions.ELIMINACION_ELEMENTOS_DEPOSITO } });

		addAction(CONSULTA_DEPOSITO,
				new String[][] { { AppPermissions.CONSULTA_DEPOSITO } });
		addAction(
				REUBICACION_UNIDADES_INSTALACION,
				new String[][] { { AppPermissions.REUBICACION_UNIDADES_INSTALACION } });
		addAction(
				REUBICACION_UNIDADES_DOCUMENTALES,
				new String[][] { { AppPermissions.REUBICACION_UNIDADES_DOCUMENTALES } });

		addAction(ALTA_FORMATO_ACTION,
				new String[][] { { AppPermissions.ADMINISTRACION_FORMATOS } });
		addAction(MODIFICAR_FORMATO_ACTION,
				new String[][] { { AppPermissions.ADMINISTRACION_FORMATOS } });
		addAction(BAJA_FORMATO_ACTION,
				new String[][] { { AppPermissions.ADMINISTRACION_FORMATOS } });
	}

	public static DepositoSecurityManager getInstance() {
		if (depositoSecurityManager == null)
			depositoSecurityManager = new DepositoSecurityManager();
		return depositoSecurityManager;
	}

}
