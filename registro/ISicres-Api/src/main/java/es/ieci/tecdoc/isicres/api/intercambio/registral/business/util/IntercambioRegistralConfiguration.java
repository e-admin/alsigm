package es.ieci.tecdoc.isicres.api.intercambio.registral.business.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;

/**
 * Clase para consultar la configuración referente a Intercambio Registral
 *
 */
public class IntercambioRegistralConfiguration {


	private static final Logger log = Logger.getLogger(IntercambioRegistralConfiguration.class);

	private static IntercambioRegistralConfiguration _instance = null;

	private Properties configurationProperties = new Properties();

	private String isicresIntercambioRegistralConfigurationPath;


	/**
	 * Obtiene la instancia única de configuración
	 */
	public synchronized static IntercambioRegistralConfiguration getInstance() {
		if (_instance == null) {
			_instance = new IntercambioRegistralConfiguration();
		}

		return _instance;
	}



	private IntercambioRegistralConfiguration() {
		initPath();
		initConfigurator();
	}

	/**
	 * Inicializa la configuración buscando el fichero según la política de carga del framework
	 */
	private void initConfigurator() {
		try {
			InputStream stream = getClass().getResourceAsStream(
					isicresIntercambioRegistralConfigurationPath);
			if (stream == null) {
				stream = getClass().getClassLoader().getResourceAsStream(
						isicresIntercambioRegistralConfigurationPath);
			}
			if (stream == null) {
				stream = ClassLoader
						.getSystemResourceAsStream(isicresIntercambioRegistralConfigurationPath);
			}
			if(stream!=null)
			{
				configurationProperties.load(stream);
			}
			else{
				configurationProperties.load(new FileInputStream(isicresIntercambioRegistralConfigurationPath));
			}

		} catch (IOException t) {
			log.fatal("Imposible cargar el fichero de configuracion ["
					+ isicresIntercambioRegistralConfigurationPath + "]", t);
		}
	}

	private void initPath(){
		ConfigFilePathResolverIsicres filePathResolver = ConfigFilePathResolverIsicres.getInstance();
		isicresIntercambioRegistralConfigurationPath = filePathResolver.getIntercambioRegistralConfigPath();
	}

	public String getProperty(String key)
	{
		return configurationProperties.getProperty(key);
	}

	/**
	 * Recupera el código del pais por defecto
	 * @return
	 */
	public String getCountryCode(){
		return configurationProperties.getProperty(IntercambioRegistralConfigurationKeys.COUNTRY_CODE_KEY);
	}

	/**
	 * Recupera el tamaño máximo permitido por fichero
	 * @return
	 */
	public Long getFileMaxSize(){
		return Long.valueOf(configurationProperties.getProperty(IntercambioRegistralConfigurationKeys.FILE_MAX_SIZE_KEY));
	}

	/**
	 * Recupera la información del tamaño máximo permitido para el conjunto de ficheros
	 * @return
	 */
	public Long getFilesSetMaxSize(){
		return Long.valueOf(configurationProperties.getProperty(IntercambioRegistralConfigurationKeys.FILES_SET_MAX_SIZE_TOTAL_KEY));
	}

	/**
	 * Recupera la información del número máximo total de ficheros
	 * @return
	 */
	public Integer getFileMaxNum(){
		return Integer.valueOf(configurationProperties.getProperty(IntercambioRegistralConfigurationKeys.FILE_MAX_NUM_KEY));
	}
	public List<String> getExtensiones(){
		String extensions = configurationProperties.getProperty(IntercambioRegistralConfigurationKeys.FILE_EXTENSIONS_KEY);
		List<String> extensionsList = Arrays.asList(extensions.split(","));
		return extensionsList;
	}

	public boolean getActiveValidationRelationEntidadUnidad() {
		return Boolean
				.parseBoolean(configurationProperties
						.getProperty(
								IntercambioRegistralConfigurationKeys.ACTIVE_VALIDATION_RELATION_ENTIDAD_UNIDAD,
								"false"));
	}
}
