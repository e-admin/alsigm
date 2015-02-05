package xml.config;

import org.apache.log4j.Logger;

import common.exceptions.ConfigException;

/**
 * Factoría de creación de objetos ConfiguracionBusquedas.
 */
public class ConfiguracionBusquedasFactory {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(ConfiguracionBusquedasFactory.class);

	/** Instancia de la clase ConfiguracionBusquedas. */
	private static ConfiguracionBusquedas cbs = null;

	/**
	 * Obtiene la instancia del objeto ConfiguracionBusquedas.
	 * 
	 * @return ConfiguracionBusquedas.
	 */
	public static ConfiguracionBusquedas getConfiguracionBusquedas() {
		if (cbs == null)
			reload();

		return cbs;
	}

	/**
	 * Recarga la configuración de la aplicación.
	 */
	public static void reload() {
		if (logger.isInfoEnabled())
			logger.info("Recargando contenido del fichero de configuraci\u00F3n...");

		try {
			cbs = ConfiguracionArchivoManager.getInstance()
					.getConfiguracionBusquedas();
			if (logger.isDebugEnabled())
				logger.debug("Fichero de configuraci\u00F3n:\n" + cbs);
		} catch (Exception e) {
			logger.error("Error al leer la configuración de las búsquedas", e);
			throw new ConfigException(e,
					ConfiguracionBusquedasFactory.class.getName(),
					"Error al leer la configuración de las búsquedas");
		}
	}

}
