package common.startup;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;

import common.util.converters.DateConverter;
import common.util.converters.StringConverter;

import es.archigest.framework.core.configuration.FrameworkConfigurator;
import es.archigest.framework.core.initialization.Launchable;

public class AppConfig implements Launchable {

	/** Logger de la clase */
	private static Logger logger = Logger.getLogger(AppConfig.class);

	private static String APP_BASE_PATH = null;

	public static AppConfig newInstance() {
		logger.debug("Creando instancia de AppConfig");
		return new AppConfig();
	}

	public void create() {
		// Registro de conversores de objetos para uso en BeanUtils
		ConvertUtils.register(new DateConverter(), java.util.Date.class);
		ConvertUtils.register(new StringConverter(), java.lang.String.class);
	}

	public void destroy() {
	}

	public static String getConfigParameter(String parameter) {
		return (String) FrameworkConfigurator.getConfigurator().getParameters()
				.get(parameter);
	}

	public static String getBasePath() {
		return APP_BASE_PATH;
	}

	public static void setBasePath(String basePath) {
		APP_BASE_PATH = basePath;
	}
}
