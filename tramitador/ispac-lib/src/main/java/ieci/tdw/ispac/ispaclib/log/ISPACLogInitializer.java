package ieci.tdw.ispac.ispaclib.log;

import ieci.tdw.ispac.ispaclib.configuration.ConfigurationHelper;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Clase para inicializar los logs.
 *
 */
public final class ISPACLogInitializer {

	private static final String DEFAULT_CONFIG_FILENAME = "log4j.xml";
	private static final String DEFAULT_CONFIG_SUBDIR = "ispac";
	
	public static void init() {
		ISPACLogInitializer.init(DEFAULT_CONFIG_SUBDIR, DEFAULT_CONFIG_FILENAME, false);
	}

	public static void init(String subdir, String configFileName) {
		ISPACLogInitializer.init(subdir, configFileName, true);
	}

	public static void init(String subdir, String configFileName, boolean resetconfig) {

		if (resetconfig) {
			LogManager.resetConfiguration();
		}

		// Obtener el fichero de configuración de logs.
		String filename = ConfigurationHelper.getConfigFilePath(subdir, configFileName);

		// Configurar los logs
		DOMConfigurator.configureAndWatch(filename);

	    Logger logger = Logger.getLogger(ISPACLogInitializer.class);
		logger.debug("");
		logger.debug("ISPAC Framework starting....");
		logger.debug("");
	}
}
