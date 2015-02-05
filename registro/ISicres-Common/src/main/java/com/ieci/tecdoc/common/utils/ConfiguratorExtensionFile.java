package com.ieci.tecdoc.common.utils;

import gnu.trove.THashMap;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ieci.tecdoc.common.conf.FileExtensionConf;
import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;
import com.ieci.tecdoc.common.keys.ConfigurationExtensionFileKeys;

/**
 * Clase que carga los datos del fichero de configuracion ISicres-Extension-Files-Configuration.xml
 *
 * @author Blimea
 *
 */
public class ConfiguratorExtensionFile {

	private static final Logger log = Logger.getLogger(ConfiguratorExtensionFile.class);

	private static ConfiguratorExtensionFile _instance = null;

	private Document _document = null;

	private Map _attributes = new THashMap();

	private Map extensionFileConfig = null;

	private String isicresExtensionFileConfigurationPath;

	/*******************************************************************************************************************
	 * Constructors
	 ******************************************************************************************************************/

	private ConfiguratorExtensionFile() {
		initPath();
		initConfigurator();
		initExtensionFileConfig();

	}

	private void initPath(){
		ConfigFilePathResolverIsicres filePathResolver = ConfigFilePathResolverIsicres.getInstance();
		isicresExtensionFileConfigurationPath = filePathResolver.getISicresExtensionFilesConfigurationPath();
	}


	public synchronized static ConfiguratorExtensionFile getInstance() {
		if (_instance == null) {
			_instance = new ConfiguratorExtensionFile();
		}

		return _instance;
	}

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

	public Map getExtensionFileConfig() {
		return extensionFileConfig;
	}

	/**
	 * Metodo que carga la coleccion con la configuracion segun la extension de los ficheros
	 *
	 */
	public void initExtensionFileConfig(){

		List list = _document.selectNodes(ConfigurationExtensionFileKeys.KEY_EXTENSIONS_FILES_CONFIGURATION);

		if(list != null && !list.isEmpty()){
			extensionFileConfig = new HashMap();
			FileExtensionConf fileConfig = null;
			for(Iterator it = list.iterator(); it.hasNext();){
				Element element = (Element) it.next();

				fileConfig = getFileConfig(element);

				extensionFileConfig.put(fileConfig.getExtension(), fileConfig);
			}
		}
	}

	/**
	 * Metodo que carga el objeto {@link FileExtensionConf} segun el nodo que se pasa como parametro
	 *
	 * @param node - Nodo extension-configuration del xml
	 *
	 * @return {@link FileExtensionConf}
	 */
	private FileExtensionConf getFileConfig(Element node) {
		//Obtenemos los valores del xml para el nodo a tratar
		String extension = node.selectSingleNode(
				ConfigurationExtensionFileKeys.KEY_EXTENSION_FILE).getText();

		String showDialogDownloadFile = node.selectSingleNode(
				ConfigurationExtensionFileKeys.KEY_SHOW_DIALOG_DOWNLOAD_FILE)
				.getText();

		//Generamos el objeto FileExtensionConf con los valores del xml
		FileExtensionConf fileConfig = new FileExtensionConf();
		fileConfig.setExtension(extension.toLowerCase());
		fileConfig.setShowDialogDownloadFile(Boolean.parseBoolean(showDialogDownloadFile));

		if(log.isDebugEnabled()){
			log.debug(fileConfig.toString());
		}

		//retornamos el objeto
		return fileConfig;
	}

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



	private void initConfigurator() {
		try {
			InputStream stream = getClass().getResourceAsStream(
					isicresExtensionFileConfigurationPath);
			if (stream == null) {
				stream = getClass().getClassLoader().getResourceAsStream(
						isicresExtensionFileConfigurationPath);
			}
			if (stream == null) {
				stream = ClassLoader
						.getSystemResourceAsStream(isicresExtensionFileConfigurationPath);
			}

			SAXReader reader = new SAXReader();

			if (stream != null) {
				_document = reader.read(stream);
			} else {
				_document = reader.read(new File(isicresExtensionFileConfigurationPath));
			}
		} catch (Throwable t) {
			log.fatal("Imposible cargar el fichero de configuracion ["
					+ isicresExtensionFileConfigurationPath + "]", t);
		}
	}
}
