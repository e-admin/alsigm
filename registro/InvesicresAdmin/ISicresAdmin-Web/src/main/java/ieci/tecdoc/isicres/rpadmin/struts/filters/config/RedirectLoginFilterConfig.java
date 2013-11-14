package ieci.tecdoc.isicres.rpadmin.struts.filters.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class RedirectLoginFilterConfig {

	private static final String EXCLUDE_PATHS = "excludePaths";

	private static final Logger log = Logger.getLogger(RedirectLoginFilterConfig.class);

	private static RedirectLoginFilterConfig _instance = null;

	private Properties configurationProperties = new Properties();

	private String isicresRedirectFilterConfigPath;

	/**
	 * Obtiene la instancia única de configuración
	 */
	public synchronized static RedirectLoginFilterConfig getInstance(String pathFile) {
		if (_instance == null) {
			_instance = new RedirectLoginFilterConfig(pathFile);
		}

		return _instance;
	}

	private RedirectLoginFilterConfig(String pathFile) {
		isicresRedirectFilterConfigPath = pathFile;
		initConfigurator();
	}

	/**
	 * Inicializa la configuración buscando el fichero según la política de carga del framework
	 */
	private void initConfigurator() {
		try {
			InputStream stream = getClass().getResourceAsStream(
					isicresRedirectFilterConfigPath);
			if (stream == null) {
				stream = getClass().getClassLoader().getResourceAsStream(
						isicresRedirectFilterConfigPath);
			}
			if (stream == null) {
				stream = ClassLoader
						.getSystemResourceAsStream(isicresRedirectFilterConfigPath);
			}
			if(stream!=null)
			{
				configurationProperties.load(stream);
			}
			else{
				configurationProperties.load(new FileInputStream(isicresRedirectFilterConfigPath));
			}

		} catch (IOException e) {
			log.fatal("Imposible cargar el fichero de configuracion ["
					+ isicresRedirectFilterConfigPath + "]", e);
		}
	}

	public String getProperty(String key)
	{
		return configurationProperties.getProperty(key);
	}

	public String getExcludePaths(){
		return configurationProperties.getProperty(EXCLUDE_PATHS);
	}

}
