// 
// FileName: ConfiguratorDistEvents.java
//
package com.ieci.tecdoc.common.utils;

import gnu.trove.THashMap;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;

/**
 * Esta clase sirve valores de configuración presentes en el archivo ISicres-Events-Configuration.xml (eventos de distribución). Patrón Singleton.
 * 
 */

public class ConfiguratorEvents {

	/*******************************************************************************************************************
	 * Attributes
	 ******************************************************************************************************************/

	private static final Logger log = Logger
			.getLogger(ConfiguratorEvents.class);

	// private static final String CONFIGURATOR_NAME =
	// "/ISicres-Events-Configuration.xml";

	private static String eventType = null;

	private static ConfiguratorEvents _instance = null;

	private Document _document = null;

	private Map _attributes = new THashMap();

	private String isicresEventsConfigurationPath;

	/*******************************************************************************************************************
	 * Constructors
	 ******************************************************************************************************************/

	private ConfiguratorEvents() {
		initPath();
		initConfigurator();
	}

	/*******************************************************************************************************************
	 * Public methods
	 ******************************************************************************************************************/

	public synchronized static ConfiguratorEvents getInstance(
			String eventId) {
		if (_instance == null) {
			_instance = new ConfiguratorEvents();
		}
		setEvent(eventId);
		return _instance;
	}

	public String getProperty(String key) {
		String result = null;
		key = key + "'" + eventType	+ "']";
		if (_attributes.containsKey(key)) {
			result = (String) _attributes.get(key);
		} else {
			result = getQueryValue(key);
			_attributes.put(key, result);
			StringBuffer buffer = new StringBuffer();
			buffer.append("ConfiguratorEvents ISicres-Server key [");
			buffer.append(key);
			buffer.append("] value [");
			buffer.append(result);
			buffer.append("]");
			log.info(buffer.toString());
		}
		return result;
	}

	public Boolean getPropertyAsBoolean(String key) {
		Boolean result = null;
		if (_attributes.containsKey(key)) {
			result = new Boolean((String) _attributes.get(key));
		} else {
			result = new Boolean(getQueryValue(key));
			_attributes.put(key, result);

			StringBuffer buffer = new StringBuffer();
			buffer.append("Configurator ISicres-Server key [");
			buffer.append(key);
			buffer.append("] value [");
			buffer.append(result);
			buffer.append("]");
			log.info(buffer.toString());
		}
		return result;
	}

	/*******************************************************************************************************************
	 * Private methods
	 ******************************************************************************************************************/
	
	private void initPath(){
		ConfigFilePathResolverIsicres filePathResolver = ConfigFilePathResolverIsicres.getInstance();
		isicresEventsConfigurationPath = filePathResolver
				.getISicresConfigurationEventsPath();
	}

	private void initConfigurator() {
		try {
			InputStream stream = getClass().getResourceAsStream(
					isicresEventsConfigurationPath);
			if (stream == null) {
				stream = getClass().getClassLoader().getResourceAsStream(
						isicresEventsConfigurationPath);
			}
			if (stream == null) {
				stream = ClassLoader
						.getSystemResourceAsStream(isicresEventsConfigurationPath);
			}

			SAXReader reader = new SAXReader();

			if (stream != null) {
				_document = reader.read(stream);
			} else {
				_document = reader.read(new File(isicresEventsConfigurationPath));
			}
		} catch (Throwable t) {
			log.fatal("Imposible cargar el fichero de configuracion ["
					+ isicresEventsConfigurationPath + "]", t);
		}
	}

	private String getQueryValue(String query) {
		String value = null;

		// Distinguimos si queremos un atributo o un nodo, ya que su forma de recuperacion es distinta
		if (query.lastIndexOf("@") == -1) {
			if (_document.selectSingleNode(query) != null) {
				value = (_document.selectSingleNode(query)).getText();
			}
		} else {
			List list = _document.selectNodes(query);
			if (list != null && !list.isEmpty()) {
				Element element = (Element) list.get(0);
				value = element.getText();
			}
		}

		// Quitamos los espacios al final y al principio.
		if (value != null && value.length() > 0) {
			value = value.trim();
		}

		return value;
	}

	private static void setEvent(String eventId) {
			eventType = eventId;
	}
}