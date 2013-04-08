package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoModules;

/**
 * Implementación del gestor de seguridad para el módulo de Descripción.
 */
public class DescripcionSecurityManager extends SecurityManagerBase {

	/** Instancia de la clase. */
	private static DescripcionSecurityManager descripcionSecurityManager = null;

	/** Acción de consultar un elemento del cuadro de clasificación. */
	public final static ActionObject CONSULTAR_FICHA_ELEMENTO_ACTION = ActionObject
			.getInstance(getKey("CONSULTAR_FICHA_ELEMENTO_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/** Acción de editar un elemento del cuadro de clasificación. */
	public final static ActionObject EDITAR_FICHA_ELEMENTO_ACTION = ActionObject
			.getInstance(getKey("EDITAR_FICHA_ELEMENTO_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/** Acción de consultar un descriptor. */
	public final static ActionObject CONSULTAR_FICHA_DESCRIPTOR_ACTION = ActionObject
			.getInstance(getKey("CONSULTAR_FICHA_DESCRIPTOR_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/** Acción de editar un descriptor. */
	public final static ActionObject EDITAR_FICHA_DESCRIPTOR_ACTION = ActionObject
			.getInstance(getKey("EDITAR_FICHA_DESCRIPTOR_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/** Acción de administrar las tablas de validación. */
	public final static ActionObject ADMINISTRAR_TABLAS_VALIDACION_ACTION = ActionObject
			.getInstance(getKey("ADMINISTRAR_TABLAS_VALIDACION_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/** Acción de consultar las tablas de validación. */
	public final static ActionObject CONSULTAR_TABLAS_VALIDACION_ACTION = ActionObject
			.getInstance(getKey("CONSULTAR_TABLAS_VALIDACION_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/** Acción de administrar los descriptores. */
	public final static ActionObject ADMINISTRAR_DESCRIPTORES_ACTION = ActionObject
			.getInstance(getKey("ADMINISTRAR_DESCRIPTORES_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/** Acción de consultar los descriptores. */
	public final static ActionObject CONSULTAR_DESCRIPTORES_ACTION = ActionObject
			.getInstance(getKey("CONSULTAR_DESCRIPTORES_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/** Acción de permitir usar fichas descriptivas en transferencias/altas. */
	public final static ActionObject USO_FICHA_ALTA_TRANSFERENCIA_ACTION = ActionObject
			.getInstance(getKey("USO_FICHA_ALTA_TRANSFERENCIA_ACTION"),
					ArchivoModules.DESCRIPCION_MODULE);

	/**
	 * Constructor.
	 */
	private DescripcionSecurityManager() {
		addAction(CONSULTAR_FICHA_ELEMENTO_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION } });

		addAction(EDITAR_FICHA_ELEMENTO_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION } });

		addAction(CONSULTAR_FICHA_DESCRIPTOR_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_DESCRIPCION_DESCRIPTOR } });

		addAction(EDITAR_FICHA_DESCRIPTOR_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.EDICION_DESCRIPTOR } });

		addAction(CONSULTAR_TABLAS_VALIDACION_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA },
				{ AppPermissions.ADMINISTRACION_TABLAS_VALIDACION } });

		addAction(ADMINISTRAR_TABLAS_VALIDACION_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.ADMINISTRACION_TABLAS_VALIDACION } });

		addAction(CONSULTAR_DESCRIPTORES_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA },
				{ AppPermissions.ADMINISTRACION_DESCRIPTORES } });

		addAction(ADMINISTRAR_DESCRIPTORES_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.ADMINISTRACION_DESCRIPTORES } });

		addAction(USO_FICHA_ALTA_TRANSFERENCIA_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.USO_FICHA_ALTA_TRANSFERENCIA } });
	}

	/**
	 * Obtiene la instancia del gestor de seguridad.
	 * 
	 * @return Instancia del gestor de seguridad.
	 */
	protected static DescripcionSecurityManager getInstance() {
		if (descripcionSecurityManager == null)
			descripcionSecurityManager = new DescripcionSecurityManager();

		return descripcionSecurityManager;
	}

	/**
	 * Obtiene el nombre completo de la clave.
	 * 
	 * @param key
	 *            Clave.
	 * @return Nombre completo de la clave.
	 */
	private static String getKey(String key) {
		return new StringBuffer().append("DescripcionSecurityManager")
				.append(key).toString();
	}

}