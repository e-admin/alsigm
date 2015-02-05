package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class PropertiesConfiguration extends Properties {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(PropertiesConfiguration.class);
	
//	public final static String FOLDER = "FOLDER";
//	public final static String HOST = "HOST";
	
	
	/**
	 * Constructor.
	 */
	public PropertiesConfiguration() {
		super();
//		put(FOLDER, ".");
//		put(HOST, "localhost");
	}

	protected void initiate(String configFileName) throws ISPACException {
		
		try {

			if (logger.isInfoEnabled()) {
				logger.info("Cargando fichero de configuración: " + configFileName);
			}

//			// Almacenar la ruta del directorio de configuración
//			File parent = file.getParentFile();
//			if ((parent != null) && parent.isDirectory()) {
//				put(FOLDER, parent.getAbsolutePath());
//			}
	
			// Cargar la información del fichero
			InputStream in = ConfigurationHelper.getConfigFileInputStream(configFileName);
			load(in);
			in.close();
			
		} catch (Exception e) {
			logger.error("Error al inicializar el fichero de configuración: " + configFileName, e);
			throw new ISPACException(e);
		}
	}
	
	public String get(String key) {
		return (String) super.get(key);
	}

	public String get(String key, String defaultValue) {
		String value = (String) super.get(key);
		if (StringUtils.isBlank(value)) {
			value = defaultValue;
		}
		return value;
	}

	public int getInt(String key) {
		return Integer.parseInt(get(key));
	}

	public int getInt(String key, int defaultValue) {
		return TypeConverter.parseInt(get(key), defaultValue);
	}

	public void put(String key, String value) {
		super.put(key, value);
	}
}
