//
// FileName: Configurator.java
//
package com.ieci.tecdoc.common.utils;

import gnu.trove.THashMap;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;

/**
 * Esta clase sirve valores de configuración presentes en el archivo
 * ISicres-Configuration.xml. Patrón Singleton.
 *
 * @author lmvicente
 * @version
 * @since
 * @creationDate 24-mar-2004
 */

public class Configurator {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	// public static final String CONFIGURATOR_NAME =
	// "/ISicres-Configuration.xml";
	protected static final Logger log = Logger.getLogger(Configurator.class);

	protected static Configurator _instance = null;

	protected Document _document = null;

	protected Map _attributes = new THashMap();

	protected String isicresConfigurationPath;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	protected Configurator() {
		initPath();
		initConfiguratorServer();
		initConfiguratorDesktop();
	}

	/**
	 *
	 * @return
	 */
	public synchronized static Configurator getInstance() {
		if (_instance == null) {
			_instance = new Configurator();
		}

		return _instance;
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		String result = null;
		if (_attributes.containsKey(key)) {
			result = (String) _attributes.get(key);
		} else {
			result = getQueryValue(key);
			_attributes.put(key, result);

			StringBuffer buffer = new StringBuffer();
			buffer.append("Configurator ISicres key [");
			buffer.append(key);
			buffer.append("] value [");
			buffer.append(result);
			buffer.append("]");
			log.info(buffer.toString());
		}
		return result;
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	public Boolean getPropertyAsBoolean(String key) {
		Boolean result = null;
		if (_attributes.containsKey(key)) {
			result = new Boolean((String) _attributes.get(key));
		} else {
			result = new Boolean(getQueryValue(key));
			_attributes.put(key, result);

			StringBuffer buffer = new StringBuffer();
			buffer.append("Configurator ISicres key [");
			buffer.append(key);
			buffer.append("] value [");
			buffer.append(result);
			buffer.append("]");
			log.info(buffer.toString());
		}
		return result;
	}

	/**
	 *
	 * @param key
	 * @return
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
			buffer.append("Configurator ISicres key [");
			buffer.append(key);
			buffer.append("] value [");
			buffer.append(result);
			buffer.append("]");

			log.info(buffer.toString());
		}
		return result;
	}

	/**
	 *
	 * @return
	 */
	public int getDefaultPageValidationListSize() {
		int size = Integer
				.parseInt(getProperty(ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_VALIDATION_LIST_SIZE));
		if (size > 300) {
			size = 300;
		}
		if (size < 5) {
			size = 5;
		}
		return size;
	}

	protected void initPath() {
		ConfigFilePathResolverIsicres filePathResolver = ConfigFilePathResolverIsicres
				.getInstance();
		isicresConfigurationPath = filePathResolver
				.getISicresConfigurationPath();
	}

	/**
	 *
	 */
	protected void initConfiguratorServer() {
		try {

			InputStream stream = getClass().getResourceAsStream(
					isicresConfigurationPath);
			if (stream == null) {
				stream = getClass().getClassLoader().getResourceAsStream(
						isicresConfigurationPath);
			}
			if (stream == null) {
				stream = ClassLoader
						.getSystemResourceAsStream(isicresConfigurationPath);
			}

			SAXReader reader = new SAXReader();

			if (stream != null) {
				_document = reader.read(stream);
			} else {
				_document = reader.read(new File(isicresConfigurationPath));
			}

			// Precarga de algunos valores conocidos y necesarios.
			getProperty(ConfigurationKeys.KEY_SERVER_DAO_IMPLEMENTATION);
			getProperty(ConfigurationKeys.KEY_SERVER_REPOSITORY_MANAGER);
			// getProperty(ConfigurationKeys.KEY_SERVER_REPOSITORY_CONNECTOR);
			getProperty(ConfigurationKeys.KEY_SERVER_CHECK_PASSWORD);
			getProperty(ConfigurationKeys.KEY_SERVER_AUTHENTICATION_POLICY);
			getProperty(ConfigurationKeys.KEY_SERVER_AUTHENTICATION_POLICY_TYPE);
			getProperty(ConfigurationKeys.KEY_SERVER_CACHE_ENTRY_TIMEOUT);
			getProperty(ConfigurationKeys.KEY_SERVER_CACHE_ENTRY_SESSION_IDGENERATOR);
			getProperty(ConfigurationKeys.KEY_SERVER_CACHE_CLEANER_SLEEP_TIME);
			getProperty(ConfigurationKeys.KEY_SERVER_CACHE_CLASS);
		} catch (Throwable t) {
			log.fatal("Imposible cargar el fichero de configuracion ["
					+ isicresConfigurationPath + "]", t);
		}
	}

	/**
	 *
	 */
	protected void initConfiguratorDesktop() {
		try {
			InputStream stream = getClass().getResourceAsStream(
					isicresConfigurationPath);
			if (stream == null) {
				stream = getClass().getClassLoader().getResourceAsStream(
						isicresConfigurationPath);
			}
			if (stream == null) {
				stream = ClassLoader
						.getSystemResourceAsStream(isicresConfigurationPath);
			}

			SAXReader reader = new SAXReader();

			if (stream != null) {
				_document = reader.read(stream);
			} else {
				_document = reader.read(new File(isicresConfigurationPath));
			}

			// Precarga de algunos valores conocidos y necesarios.
			getProperty(ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_TABLE_RESULTS_SIZE);
			getProperty(ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_VALIDATION_LIST_SIZE);
			getProperty(ConfigurationKeys.KEY_DESKTOP_INITIAL_FACTORY);
			getProperty(ConfigurationKeys.KEY_DESKTOP_PROVIDER_URL);
			getProperty(ConfigurationKeys.KEY_DESKTOP_URL_PKGS);
			getProperty(ConfigurationKeys.KEY_DESKTOP_USECOMPRESSEDCONTENTS);
			getProperty(ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES);
			getProperty(ConfigurationKeys.KEY_DESKTOP_DISTRIBUTION_OFFICE_ASOC);
			getProperty(ConfigurationKeys.KEY_DESKTOP_COMPULSA_POLICY);
		} catch (Throwable t) {
			log.fatal("Error leyendo configuracion.", t);
		}
	}

	/**
	 *
	 * @param query
	 * @return
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