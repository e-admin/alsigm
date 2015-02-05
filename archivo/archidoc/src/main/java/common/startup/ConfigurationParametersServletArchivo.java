package common.startup;

import javax.servlet.ServletConfig;

import org.apache.log4j.Logger;

import common.BaseConfiguration;
import common.Globals;
import common.util.FileHelper;

import es.archigest.framework.core.configuration.ConfigurationParametersServlet;

public class ConfigurationParametersServletArchivo extends
		ConfigurationParametersServlet {

	protected static final Logger logger = Logger
			.getLogger(ConfigurationParametersServletArchivo.class);

	public ConfigurationParametersServletArchivo(ServletConfig config) {
		super(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.archigest.framework.core.configuration.ConfigurationParametersServlet
	 * #getParameter(java.lang.String)
	 */
	public String getParameter(String key) {
		String parametro = super.getParameter(key);

		try {
			if (Globals.LOGGING_XMLCONF.equals(key)) {
				BaseConfiguration config = BaseConfiguration.getInstance();

				parametro = FileHelper
						.getNormalizedToSystemFilePath(
								(String) config
										.getConfigurationProperty(BaseConfiguration.ARCHIDOC_BASE_CONFIG_DIR),
								parametro);
			}
		} catch (Exception e) {
			logger.error("Error al configurar el sistema de logging:", e);
		}

		return parametro;
	}

}
