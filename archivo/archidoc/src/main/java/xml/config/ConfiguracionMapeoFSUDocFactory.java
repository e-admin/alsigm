package xml.config;

import org.apache.log4j.Logger;

import common.exceptions.ConfigException;

/**
 * Factoría de creación de objetos ConfiguracionMapeoFSUDoc.
 */
public class ConfiguracionMapeoFSUDocFactory {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(ConfiguracionMapeoFSUDocFactory.class);

	/** Instancia de la clase ConfiguracionMapeoFSUDoc. */
	private static ConfiguracionMapeoFSUDoc cmfsudoc = null;

	/**
	 * Obtiene la instancia del objeto ConfiguracionMapeoFSUDocFactory.
	 * 
	 * @return ConfiguracionMapeoFSUDocFactory.
	 */
	public static ConfiguracionMapeoFSUDoc getConfiguracionMapeoFSUDoc() {
		if (cmfsudoc == null)
			reload();

		return cmfsudoc;
	}

	/**
	 * Recarga la configuración de la aplicación.
	 */
	public static void reload() {
		if (logger.isInfoEnabled())
			logger.info("Recargando contenido del fichero de configuraci\u00F3n de mapeos de fracci\u00F3n de serie a u. docs. ...");

		try {
			// // Ruta del fichero de configuración
			// String mapFSUDocFileName =
			// (String)FrameworkConfigurator.getConfigurator()
			// .getParameters().get(Globals.MAP_FS_UDOC_FILE);
			// if (logger.isDebugEnabled())
			// logger.debug("Leyendo el fichero de mapeo de campos de fs a udocs: "
			// + mapFSUDocFileName);
			//
			// // URL del fichero de reglas
			// URL rulesXmlUrl =
			// ConfiguracionMapeoFSUDocFactory.class.getClassLoader()
			// .getResource(Globals.RULES_MAP_FS_UDOC_FILE);
			// if (logger.isDebugEnabled())
			// logger.debug("Leyendo el fichero de reglas: " + rulesXmlUrl);
			//
			// // Cargar la configuración
			// Digester digester = DigesterLoader.createDigester(rulesXmlUrl);
			// FileInputStream file = new FileInputStream(mapFSUDocFileName);
			// cmfsudoc = (ConfiguracionMapeoFSUDoc) digester.parse(file);

			cmfsudoc = ConfiguracionArchivoManager.getInstance()
					.getConfiguracionMapeoFSUDoc();

			if (logger.isDebugEnabled())
				logger.debug("Fichero de mapeo de campos de fs a udocs:"
						+ cmfsudoc);
		}
		// catch (FileNotFoundException fnfe)
		// {
		// // Que no exista el fichero no es necesariamente un error, puede
		// haber sitios que no lo necesiten,
		// // por lo que se da un mensaje en el log, pero no se propaga la
		// excepción
		// logger.error("No se ha encontrado el fichero de mapeo de campos de fs a udocs",
		// fnfe);
		// cmfsudoc = null;
		// }
		catch (Exception e) {
			logger.error(
					"Error al leer el fichero de mapeo de campos de fs a udocs",
					e);
			throw new ConfigException(e,
					ConfiguracionMapeoFSUDocFactory.class.getName(),
					"Error al leer el fichero de mapeo de campos de fs a udocs");
		}
	}

}
