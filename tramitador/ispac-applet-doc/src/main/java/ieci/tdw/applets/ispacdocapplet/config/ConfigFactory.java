package ieci.tdw.applets.ispacdocapplet.config;

/**
 * Factoría para obtener la configuración.
 *
 */
public class ConfigFactory {

	/**
	 * Configuración local.
	 */
	private static LocalConfig localConfig = null;
	
	/**
	 * Obtiene una instancia de la configuración local.
	 * @return Configuración local.
	 */
	public static synchronized LocalConfig getLocalConfig() {
	
		if (localConfig == null) {
			localConfig = new LocalConfig();
		}
		
		return localConfig;
	}
}
