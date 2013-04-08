package ieci.tdw.ispac.audit.config;

import gnu.trove.THashMap;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * Esta clase sirve valores de configuración presentes en el archivo ispac-audit-configuration.xml (eventos de distribución). Patrón Singleton.
 *
 * @author IECISA
 */

public class ConfiguratorAudit {

	private static final Logger log = Logger.getLogger(ConfiguratorAudit.class);

	private static ConfiguratorAudit _instance = null;

	private Document _document = null;

	private Map _attributes = new THashMap();

	private String ispacAuditConfigurationPath;

	private ConfiguratorAudit() {
		initPath();
		initConfigurator();
	}

	private void initPath(){
		//Obtenemos el path del fichero ispac-audit-configuration.xml
		ispacAuditConfigurationPath = ConfigFilePathResolverIspacAudit
				.getInstance().resolveFullPath(
						ConfigurationAuditFileKeys.ISPAC_AUDIT_CONFIGURATION);
	}

	public synchronized static ConfiguratorAudit getInstance() {
		if (_instance == null) {
			_instance = new ConfiguratorAudit();
		}

		return _instance;
	}

	private void initConfigurator() {
		try {
			InputStream stream = getClass().getResourceAsStream(
					ispacAuditConfigurationPath);
			if (stream == null) {
				stream = getClass().getClassLoader().getResourceAsStream(
						ispacAuditConfigurationPath);
			}
			if (stream == null) {
				stream = ClassLoader
						.getSystemResourceAsStream(ispacAuditConfigurationPath);
			}

			SAXReader reader = new SAXReader();

			if (stream != null) {
				_document = reader.read(stream);
			} else {
				_document = reader.read(new File(ispacAuditConfigurationPath));
			}
		} catch (Throwable t) {
			log.fatal("Imposible cargar el fichero de configuracion ["
					+ ispacAuditConfigurationPath + "]", t);
		}
	}

	/**
	 * Metodo para obtener la información del a key pasada como parámetro
	 *
	 * @param key
	 * @return String - valor de la key
	 */
	public String getProperty(String key) {
		String result = null;
		if (_attributes.containsKey(key)) {
			result = (String) _attributes.get(key);
		} else {
			result = getQueryValue(key);
			_attributes.put(key, result);

			StringBuffer buffer = new StringBuffer();
			buffer.append("Configurator Ispac key [");
			buffer.append(key);
			buffer.append("] value [");
			buffer.append(result);
			buffer.append("]");
			log.info(buffer.toString());
		}
		return result;
	}

	/**
	 * Metodo para obtener la información booleana del a key pasada como parámetro
	 *
	 * @param key
	 * @return boolean - valor de la key en booleano
	 */
	public boolean getPropertyBoolean(String key) {
		boolean result = false;
		String type = null;

		if (_attributes.containsKey(key)) {
			type = (String) _attributes.get(key);
			result = new Boolean(type).booleanValue();
		} else {
			type = getQueryValue(key);
			_attributes.put(key, type);
			result = new Boolean(type).booleanValue();

			StringBuffer buffer = new StringBuffer();
			buffer.append("Configurator Ispac key [");
			buffer.append(key);
			buffer.append("] value [");
			buffer.append(result);
			buffer.append("]");

			log.info(buffer.toString());
		}
		return result;
	}

	/**
	 * Metodo que busca en el XML la query pasada como parámetro
	 *
	 * @param query
	 * @return String - valor obtenido del XML
	 */
	protected String getQueryValue(String query) {
		String value = null;

		// Distinguimos si queremos un atributo o un nodo, ya que su forma de
		// recuperacion es distinta
		if (query.lastIndexOf("@") == -1) {
			if (_document.selectSingleNode(query) != null) {
				value = (_document.selectSingleNode(query)).getText();
			}
		} else {
			List list = _document.selectNodes(query);
			if (list != null && !list.isEmpty()) {
				Attribute attribute = (Attribute) list.get(0);
				value = attribute.getValue();
			}
		}

		// Quitamos los espacios al final y al principio.
		if (value != null && value.length() > 0) {
			value = value.trim();
		}
		return value;
	}

	
}
