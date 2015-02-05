package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoModules;

/**
 * Implementación del gestor de seguridad para el módulo de Documentos
 * Electrónicos.
 */
public class DocumentosElectronicosSecurityManager extends SecurityManagerBase {

	/** Instancia de la clase. */
	private static DocumentosElectronicosSecurityManager docElecSecurityManager = null;

	/** Acción de consultar documentos del cuadro de clasificación. */
	public final static ActionObject CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION = ActionObject
			.getInstance(
					getKey("CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION"),
					ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE);

	/** Acción de editar documentos del cuadro de clasificación. */
	public final static ActionObject EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION = ActionObject
			.getInstance(
					getKey("EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION"),
					ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE);

	/** Acción de administrar las tareas de captura de documentos. */
	public final static ActionObject ADMINISTRACION_TAREAS_CAPTURA_ACTION = ActionObject
			.getInstance(getKey("ADMINISTRACION_TAREAS_CAPTURA_ACTION"),
					ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE);

	/** Acción de capturar documentos. */
	public final static ActionObject CAPTURA_DOCUMENTOS_ACTION = ActionObject
			.getInstance(getKey("CAPTURA_DOCUMENTOS_ACTION"),
					ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE);

	/**
	 * Constructor.
	 */
	private DocumentosElectronicosSecurityManager() {
		addAction(
				CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION,
				new String[][] {
						{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
						{ AppPermissions.CONSULTA_TOTAL_SISTEMA },
						{ AppPermissions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION } });

		addAction(
				EDICION_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION,
				new String[][] {
						{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
						{ AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION },
						{ AppPermissions.CAPTURA_DOCUMENTOS } });

		addAction(ADMINISTRACION_TAREAS_CAPTURA_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.ADMINISTRACION_TAREAS_CAPTURA } });

		addAction(CAPTURA_DOCUMENTOS_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.CAPTURA_DOCUMENTOS } });

	}

	/**
	 * Obtiene la instancia del gestor de seguridad.
	 * 
	 * @return Instancia del gestor de seguridad.
	 */
	protected static DocumentosElectronicosSecurityManager getInstance() {
		if (docElecSecurityManager == null)
			docElecSecurityManager = new DocumentosElectronicosSecurityManager();

		return docElecSecurityManager;
	}

	/**
	 * Obtiene el nombre completo de la clave.
	 * 
	 * @param key
	 *            Clave.
	 * @return Nombre completo de la clave.
	 */
	private static String getKey(String key) {
		return new StringBuffer()
				.append("DocumentosElectronicosSecurityManager").append(key)
				.toString();
	}

}