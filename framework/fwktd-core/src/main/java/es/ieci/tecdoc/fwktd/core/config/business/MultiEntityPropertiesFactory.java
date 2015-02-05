package es.ieci.tecdoc.fwktd.core.config.business;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigurationResourceLoader;
import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

/**
 * Factoría para la carga de ficheros de propiedades por entidad.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MultiEntityPropertiesFactory {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MultiEntityPropertiesFactory.class);

	/**
	 * Cargador de recursos.
	 */
	private ConfigurationResourceLoader configurationResourceLoader = null;

	/**
	 * Mapa de propiedades por entidad.
	 */
	private Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

	/**
	 * Propiedades generales
	 */
	private Properties properties = null;

	/**
	 * Nombre del fichero de configuración.
	 */
	private String fileName = null;


	/**
	 * Constructor.
	 */
	public MultiEntityPropertiesFactory() {
		super();
	}

	public ConfigurationResourceLoader getConfigurationResourceLoader() {
		return configurationResourceLoader;
	}

	public void setConfigurationResourceLoader(
			ConfigurationResourceLoader configurationResourceLoader) {
		this.configurationResourceLoader = configurationResourceLoader;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Obtiene las propiedades para la entidad.
	 * @return Propiedades.
	 */
	public Properties getProperties() {

		// Identificador de la entidad (en entornos multientidad)
		String entity = MultiEntityContextHolder.getEntity();
		if (StringUtils.isBlank(entity)) { //Entorno monoentidad

			return getDefaultProperties();

		} else { // Entorno multientidad

			// Obtener las propiedades de la caché
			Properties entityProperties = propertiesMap.get(entity);
			if (entityProperties == null) {

				// Cargar las propiedades del fichero de configuración
				entityProperties = getProperties(entity + "/" + getFileName());

				// Si no hay fichero de propiedades específico para la
				// entidad, se coge el de por defecto.
				if (entityProperties == null) {
					entityProperties = getDefaultProperties();
				}

				// Guardar las propiedades en la caché
				propertiesMap.put(entity, entityProperties);
			}

			return entityProperties;
		}
	}

	protected Properties getDefaultProperties() {

		if (properties == null) {

			// Obtener las propriedades por defecto
			properties = getProperties(getFileName());

			if (properties == null) {
				properties = new Properties();
			}
		}

		return properties;
	}

	protected Properties getProperties(String resourceName) {

		Properties props = null;

		if (getConfigurationResourceLoader() != null) {

			// Obtener la ruta del fichero de configuración
			Resource resource = getConfigurationResourceLoader().loadResource(resourceName, null);
			logger.info("Fichero de propiedades: {} => {}", resourceName, resource);

			if (resource != null) {
				try {
					props = new Properties();
					props.load(resource.getInputStream());
				} catch (Exception e) {
					logger.error("Error al leer el fichero de propiedades: " + resource, e);
				}
			} else {
				logger.warn("No se ha encontrado en fichero de propiedades [{}]", resourceName);
			}

		} else {
			logger.warn("No se ha configurado el ConfigurationResourceLoader para el fichero [{}]", resourceName);
		}

		return props;
	}
}
