/**
 * 
 */
package ieci.tecdoc.sgm.config.ecologo;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.util.Properties;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class EcoLogoConfigLoader {

	private static EcoLogoConfigLoader instance = null;

	/**
	 * 
	 */
	private static final String LOGO_HEIGHT_KEY = "logo.height";
	/**
	 * 
	 */
	private static final String LOGO_WIDTH_KEY = "logo.width";
	/**
	 * 
	 */
	private static final String IFRAME_HEIGHT_KEY = "iframe.height";
	/**
	 * 
	 */
	private static final String IFRAME_WIDTH_KEY = "iframe.width";
	/**
	 * 
	 */
	private static final String APPLICATION_VERSION_KEY = "application.version";
	/**
	 * 
	 */
	private static final String SREPALS_LOGGER_SERVLET_KEY = "srepals.logger.servlet";
	/**
	 * 
	 */
	private static final String SREPALS_LOGGER_PORT_KEY = "srepals.logger.port";
	/**
	 * 
	 */
	private static final String SREPALS_LOGGER_HOST_KEY = "srepals.logger.host";
	/**
	 * 
	 */
	private static final String APPLICATION_DNS_KEY = "application.dns";
	/**
	 * 
	 */
	private static final String APPLICATION_NAME_KEY = "application.name.";
	public static String CONFIG_SUBDIR = "SIGEM_ECOLogo";
	public static String CONFIG_FILE = "ECOLogoConfig.properties";

	protected Properties properties;

	private EcoLogoConfigLoader() {
		properties = loadConfiguration();
	}

	public static EcoLogoConfigLoader getInstance() {
		if (instance == null) {
			instance = new EcoLogoConfigLoader();
		}
		return instance;
	}

	public String getCodigoAplicacion(String idApp) {
		String key = APPLICATION_NAME_KEY;
		key = key + idApp;
		String result = getValue(key);
		return result;
	}

	public String getDNS() {
		String key = APPLICATION_DNS_KEY;
		String result = getValue(key);
		return result;
	}

	public String getHost() {
		String key = SREPALS_LOGGER_HOST_KEY;
		String result = getValue(key);
		return result;
	}

	public String getPort() {
		String key = SREPALS_LOGGER_PORT_KEY;
		String result = getValue(key);
		return result;
	}

	public String getServlet() {

		String key = SREPALS_LOGGER_SERVLET_KEY;
		String result = getValue(key);
		return result;
	}

	public String getVersion() {

		String key = APPLICATION_VERSION_KEY;
		String result = getValue(key);
		return result;
	}

	public String getIFrameWidth() {

		String key = IFRAME_WIDTH_KEY;
		String result = getValue(key);
		return result;
	}

	public String getIframeHeight() {

		String key = IFRAME_HEIGHT_KEY;
		String result = getValue(key);
		return result;
	}

	public String getLogoWidth() {

		String key = LOGO_WIDTH_KEY;
		String result = getValue(key);
		return result;
	}

	public String getLogoHeight() {

		String key = LOGO_HEIGHT_KEY;
		String result = getValue(key);
		return result;
	}

	protected String getValue(String key) {
		String result = "";
		result = properties.getProperty(key);
		return result;
	}

	protected Properties loadConfiguration() {

		Properties result = null;
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		result = pathResolver.loadProperties(CONFIG_FILE, CONFIG_SUBDIR);

		return result;
	}

}
