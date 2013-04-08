package common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import common.exceptions.ConfigException;

public class BaseConfiguration {

	/**
	 * Nombre del fichero xml donde se definen los beans de spring
	 */
	private static final String SPRING_BEANS_FILE = "archidoc-config-beans.xml";

	/**
	 * Nombre del bean de configuración de archivo
	 */
	private static final String SPRING_ARCHIVO_CONFIGURATION_BEAN = "ARCHIVO.CONFIGURATION.BEAN";

	/* Posibles propiedades de configuracion */
	public static final String HTTPS_PORT = "HTTPS_PORT";
	public static final String ARCHIDOC_BASE_CONFIG_DIR = "ARCHIDOC_BASE_CONFIG_DIR";

	/**
	 * Objeto de configuracion
	 */
	private static BaseConfiguration baseConfiguration = null;

	/**
	 * Map para almacenar las propiedades de configuracion
	 */
	private static Map configMap = new HashMap();

	/**
	 * Constructor privado
	 */
	private BaseConfiguration() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				SPRING_BEANS_FILE);
		configMap = (Map) context.getBean(SPRING_ARCHIVO_CONFIGURATION_BEAN);
	}

	public static synchronized BaseConfiguration getInstance()
			throws ConfigException {

		if (baseConfiguration == null) {
			baseConfiguration = new BaseConfiguration();
		}

		return baseConfiguration;
	}

	public Object getConfigurationProperty(String key) {
		return configMap.get(key);
	}

}
