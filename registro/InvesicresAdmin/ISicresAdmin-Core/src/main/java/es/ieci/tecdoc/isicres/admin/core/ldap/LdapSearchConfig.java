package es.ieci.tecdoc.isicres.admin.core.ldap;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LdapSearchConfig {
	private static final Logger logger = Logger.getLogger(LdapSearchConfig.class);

	private static String fileName = "ldap.properties";
	private static String KEY_GROUP_SEARCH_FILTER = "ldap.group.search.filter";
	private static String KEY_GROUP_SEARCH_VALID_SELECTION_CLASSES = "ldap.group.search.valid.selection.classes";
	private static String KEY_USER_SEARCH_FILTER = "ldap.user.search.filter";
	private static String KEY_USER_SEARCH_VALID_SELECTION_CLASSES = "ldap.user.search.valid.selection.classes";

	private static Properties props = new Properties();

	static {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			props.load(is);
			is.close();
		} catch (Exception e) {
			logger.error("Error inicializando configuración de búsqueda en LDAP", e);
		}
	}

	public static String getLdapGroupSearchFilter() {
		String key = (String) props.get(KEY_GROUP_SEARCH_FILTER);
		if (key==null)
			logger.error("Error obteniendo el patrón de búsqueda de grupos en LDAP del fichero: " + fileName );
		return key;
	}
	
	public static String getLdapGroupValidSelectionClasses() {
		String key = (String) props.get(KEY_GROUP_SEARCH_VALID_SELECTION_CLASSES);
		if (key==null)
			logger.error("Error obteniendo las clases válidas para seleccionar grupos del fichero: " + fileName );
		return key;
	}
	
	public static String getLdapUserSearchFilter() {
		String key = (String) props.get(KEY_USER_SEARCH_FILTER);
		if (key==null)
			logger.error("Error obteniendo el patrón de búsqueda de usuarios en LDAP del fichero: " + fileName );
		return key;
	}
	
	public static String getLdapUserValidSelectionClasses() {
		String key = (String) props.get(KEY_USER_SEARCH_VALID_SELECTION_CLASSES);
		if (key==null)
			logger.error("Error obteniendo las clases válidas para seleccionar usuarios del fichero: " + fileName );
		return key;
	}
}
