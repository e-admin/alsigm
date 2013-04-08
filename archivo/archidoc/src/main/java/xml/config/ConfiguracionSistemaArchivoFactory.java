package xml.config;

import org.apache.log4j.Logger;

import common.exceptions.ConfigException;

/**
 * Factoría de creación de objetos ConfiguracionSistemaArchivo.
 */
public class ConfiguracionSistemaArchivoFactory {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(ConfiguracionSistemaArchivoFactory.class);

	/** Instancia de la clase ConfiguracionSistemaArchivo. */
	private static ConfiguracionSistemaArchivo csa = null;

	/**
	 * Obtiene la instancia del objeto ConfiguracionSistemaArchivo.
	 * 
	 * @return ConfiguracionSistemaArchivo.
	 */
	public static ConfiguracionSistemaArchivo getConfiguracionSistemaArchivo()
			throws ConfigException {
		if (csa == null)
			reload();

		return csa;
	}

	/**
	 * Recarga la configuración de la aplicación.
	 */
	public static void reload() throws ConfigException {
		if (logger.isInfoEnabled())
			logger.info("Recargando contenido del fichero de configuraci\u00F3n...");

		csa = ConfiguracionArchivoManager.getInstance().getArchivoCfg(true);

	}

}
