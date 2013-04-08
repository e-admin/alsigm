package es.ieci.tecdoc.fwktd.csv.aplicacionExternaConnector.ws.servlet;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolverImpl;
import es.ieci.tecdoc.fwktd.core.spring.context.AppContext;

public class Log4jInitServlet extends HttpServlet {

	private static final long serialVersionUID = -7856625992761040293L;

	public static String LOG4J_XML_FILENAME_DEFAULT = "log4j.xml";
	public static String LOG4J_PROPERTIES_FILENAME_DEFAULT = "log4j.properties";

	protected static String SPRING_CONFIG_BEAN_DEFAULT = "fwktd_csv_aplicacionExternaConnector_ws_configurationResourceLoader";
	protected static String CONFIG_SUBDIR_KEY = "CONFIG_SUBDIR";
	protected static String CONFIGURATION_BEAN_DEFAULT = "fwktd_csv_aplicacionExternaConnector_ws_configurationBean";

	public void init() throws ServletException {

		ConfigFilePathResolver configFilePathResolver = new ConfigFilePathResolverImpl(
				AppContext.getApplicationContext(),
				SPRING_CONFIG_BEAN_DEFAULT,
				CONFIG_SUBDIR_KEY,
				CONFIGURATION_BEAN_DEFAULT);

		// Directorio que contiene el fichero de configuración
		String subdir = getServletConfig().getInitParameter("subdir");

		// Resetear la configuración de los logs
		LogManager.resetConfiguration();

		String file = getLog4jXMLPath(configFilePathResolver, subdir);
		if (file != null) {
			if (new File(file).exists()) {
				DOMConfigurator.configureAndWatch(file);
				Logger.getLogger(Log4jInitServlet.class).info(
						"log4j cargado [" + file + "]");
			} else {
				Logger.getLogger(Log4jInitServlet.class).info(
						"log4j no encontrado [" + file + "]");
			}
		} else {
			file = getLog4jPropertiesPath(configFilePathResolver, subdir);
			if (new File(file).exists()) {
				PropertyConfigurator.configureAndWatch(file);
				Logger.getLogger(Log4jInitServlet.class).info(
						"log4j cargado [" + file + "]");
			} else {
				Logger.getLogger(Log4jInitServlet.class).info(
						"log4j no encontrado [" + file + "]");
			}
		}
	}

	public String getLog4jXMLPath(
			ConfigFilePathResolver configFilePathResolver, String subDir) {
		if (StringUtils.isNotBlank(subDir)) {
			return configFilePathResolver.resolveFullPath(
					LOG4J_XML_FILENAME_DEFAULT, subDir);
		} else {
			return configFilePathResolver
					.resolveFullPath(LOG4J_XML_FILENAME_DEFAULT);
		}
	}

	public String getLog4jPropertiesPath(
			ConfigFilePathResolver configFilePathResolver, String subDir) {
		if (StringUtils.isNotBlank(subDir)) {
			return configFilePathResolver.resolveFullPath(
					LOG4J_PROPERTIES_FILENAME_DEFAULT, subDir);
		} else {
			return configFilePathResolver
					.resolveFullPath(LOG4J_PROPERTIES_FILENAME_DEFAULT);
		}
	}

}
