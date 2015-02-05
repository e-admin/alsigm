package common.security;

import se.usuarios.AppPermissions;

import common.definitions.ArchivoModules;

/**
 * Implementación del gestor de seguridad para el módulo de Documentos Vitales.
 */
public class DocumentosVitalesSecurityManager extends SecurityManagerBase {

	/** Instancia de la clase. */
	private static DocumentosVitalesSecurityManager docVitSecurityManager = null;

	/** Acción de consultar documentos vitales. */
	public final static ActionObject CONSULTA_DOCUMENTOS_VITALES_ACTION = ActionObject
			.getInstance(getKey("CONSULTA_DOCUMENTOS_VITALES_ACTION"),
					ArchivoModules.DOCUMENTOS_VITALES_MODULE);

	/** Acción de editar documentos vitales. */
	public final static ActionObject EDICION_DOCUMENTOS_VITALES_ACTION = ActionObject
			.getInstance(getKey("EDICION_DOCUMENTOS_VITALES_ACTION"),
					ArchivoModules.DOCUMENTOS_VITALES_MODULE);

	/** Acción de editar documentos vitales. */
	public final static ActionObject ALTA_DOCUMENTOS_VITALES_ACTION = ActionObject
			.getInstance(getKey("ALTA_DOCUMENTOS_VITALES_ACTION"),
					ArchivoModules.DOCUMENTOS_VITALES_MODULE);

	/**
	 * Constructor.
	 */
	private DocumentosVitalesSecurityManager() {
		addAction(CONSULTA_DOCUMENTOS_VITALES_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_TOTAL_SISTEMA },
				{ AppPermissions.CONSULTA_DOCUMENTOS_VITALES },
				{ AppPermissions.EDICION_DOCUMENTOS_VITALES } });

		addAction(EDICION_DOCUMENTOS_VITALES_ACTION, new String[][] {
				{ AppPermissions.ADMINISTRACION_TOTAL_SISTEMA },
				{ AppPermissions.EDICION_DOCUMENTOS_VITALES } });
		addAction(
				ALTA_DOCUMENTOS_VITALES_ACTION,
				new String[][] { { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA } });

	}

	/**
	 * Obtiene la instancia del gestor de seguridad.
	 * 
	 * @return Instancia del gestor de seguridad.
	 */
	protected static DocumentosVitalesSecurityManager getInstance() {
		if (docVitSecurityManager == null)
			docVitSecurityManager = new DocumentosVitalesSecurityManager();

		return docVitSecurityManager;
	}

	/**
	 * Obtiene el nombre completo de la clave.
	 * 
	 * @param key
	 *            Clave.
	 * @return Nombre completo de la clave.
	 */
	private static String getKey(String key) {
		return new StringBuffer().append("DocumentosVitalesSecurityManager")
				.append(key).toString();
	}

}