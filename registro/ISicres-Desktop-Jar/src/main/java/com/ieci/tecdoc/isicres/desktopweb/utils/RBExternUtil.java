package com.ieci.tecdoc.isicres.desktopweb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;

/**
 * @author LMVICENTE
 * @creationDate 30-abr-2004 16:05:03
 * @version
 * @since
 */

public class RBExternUtil {

	protected static final Logger log = Logger.getLogger(RBExternUtil.class);

	private static Map resourceBundles = new HashMap(3);

	protected String isicresResourcesPath;

	protected static final String PROPERTIES_EXTENSION = ".properties";

	// SOLUCION CONFIGURATOR
	 protected Properties _properties = new Properties();

	protected RBExternUtil(Locale locale) {
		ConfigFilePathResolverIsicres filePathResolver = ConfigFilePathResolverIsicres
				.getInstance();
		isicresResourcesPath = filePathResolver.getISicresResourcesPath();
		
		if (!locale.equals(Locale.getDefault())) {
			String pathNoExtension = isicresResourcesPath.substring(0,
					isicresResourcesPath.lastIndexOf(PROPERTIES_EXTENSION));
			pathNoExtension = pathNoExtension + "_" + locale.toString()
					+ PROPERTIES_EXTENSION;
			isicresResourcesPath = pathNoExtension;
		}

		try {

			FileInputStream stream = (FileInputStream) getClass()
					.getResourceAsStream(isicresResourcesPath);
			if (stream == null) {
				stream = (FileInputStream) getClass().getClassLoader()
						.getResourceAsStream(isicresResourcesPath);
			}
			if (stream == null) {
				stream = (FileInputStream) ClassLoader
						.getSystemResourceAsStream(isicresResourcesPath);
			}
			if (stream == null) {
				stream = new FileInputStream(isicresResourcesPath);
			}
			if (stream == null) {
				stream = new FileInputStream(new File(isicresResourcesPath));
			}

			_properties.load(stream);

		} catch (Exception e) {
			log.fatal("Imposible cargar el fichero de configuracion ["
					+ isicresResourcesPath + "]", e);
		}
	}

	public synchronized static RBExternUtil getInstance(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		RBExternUtil _instance = null;
		if (resourceBundles.containsKey(locale)) {
			_instance = (RBExternUtil) resourceBundles.get(locale);
		} else {
			_instance = new RBExternUtil(locale);
			resourceBundles.put(locale, _instance);
		}

		return _instance;
	}

	public String getProperty(String key) {
		if (_properties.containsKey(key)) {
			return _properties.getProperty(key);
		}
		return null;
	}

	// FIN SOLUCION CONFIGURATOR

	// SOLUCION RESOURCE BUNDLE

	// private ResourceBundle rb = null;
	//
	// public RBExternUtil(Locale locale) {
	// ConfigFilePathResolverIsicres filePathResolver =
	// ConfigFilePathResolverIsicres
	// .getInstance();
	// isicresResourcesPath = filePathResolver.getISicresResourcesPath();
	// String pathNoExtension = isicresResourcesPath.substring(0,
	// isicresResourcesPath.lastIndexOf(PROPERTIES_EXTENSION));
	// if (!locale.equals(Locale.getDefault())) {
	// pathNoExtension = pathNoExtension + locale.toString()
	// + PROPERTIES_EXTENSION;
	// }
	// isicresResourcesPath = pathNoExtension;
	//
	// rb = ResourceBundle.getBundle(isicresResourcesPath, locale);
	// }
	//
	// public static synchronized RBExternUtil getInstance(Locale locale) {
	// RBExternUtil rbUtil = null;
	// if (locale == null) {
	// locale = Locale.getDefault();
	// }
	// if (resourceBundles.containsKey(locale)) {
	// rbUtil = (RBExternUtil) resourceBundles.get(locale);
	// } else {
	// rbUtil = new RBExternUtil(locale);
	// resourceBundles.put(locale, rbUtil);
	// }
	// return rbUtil;
	// }
	//
	// public String getProperty(String key) {
	// return getProperty(key, "@@" + key + "@@");
	// }
	//
	// public String getProperty(String key, String defaultValue) {
	// String result = defaultValue;
	//
	// try {
	// result = rb.getString(key);
	// } catch (MissingResourceException mrE) {
	// }
	//
	// return result;
	// }

}
