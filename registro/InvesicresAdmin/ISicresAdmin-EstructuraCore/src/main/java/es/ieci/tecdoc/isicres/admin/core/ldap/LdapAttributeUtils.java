package es.ieci.tecdoc.isicres.admin.core.ldap;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LdapAttributeUtils {

	private static final Logger logger = Logger.getLogger(LdapAttributeUtils.class);

	//Keys de los atributos
	private static final String KEY_ACTIVEDIRECTORY_GUID = "AD_GUID";
	private static final String KEY_IPLANET_GUID = "IP_GUID";
	private static final String KEY_OPENLDAP_GUID = "OL_GUID";

	private static final String KEY_ACTIVEDIRECTORY_GUID_GROUP = "AD_GUID_GROUP";
	private static final String KEY_IPLANET_GUID_GROUP = "IP_GUID_GROUP";
	private static final String KEY_OPENLDAP_GUID_GROUP = "OL_GUID_GROUP";

	//Fichero del que obtenemos el nombre de los atributos
	private static String fileName = "ldap.properties";

	private static Properties props = new Properties();

	static {
		try {
			//obtenemos los datos del fichero
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName);
			props.load(is);
			is.close();
		} catch (Exception e) {
			logger.error(
					"Error inicializando configuración de búsqueda en LDAP", e);
		}
	}

	/**
	 * Método que busca el atributo de guid para un sistema Active Directory
	 * @return valor para la key: KEY_ACTIVEDIRECTORY_GUID
	 */
	public static String getLdapAttributeActiveDirectoryGUID() {
		String key = (String) props.get(KEY_ACTIVEDIRECTORY_GUID);
		if (key == null) {
			//Asignamos el valor que tenia por defecto
			key = "objectGUID";
			logger.error("Error obteniendo el guid para active directory del fichero: "
					+ fileName);
		}
		return key;
	}

	/**
	 * Método que busca el atributo de guid para un sistema IPLANET
	 * @return valor para la key: KEY_IPLANET_GUID
	 */
	public static String getLdapAttributeIplanetGUID() {
		String key = (String) props.get(KEY_IPLANET_GUID);
		if (key == null) {
			//Asignamos el valor que tenia por defecto
			key = "nsuniqueid";
			logger.error("Error obteniendo el guid para iplanet del fichero: "
					+ fileName);
		}
		return key;
	}

	/**
	 * Método que busca el atributo de guid para un sistema OPENLDAP
	 * @return valor para la key: KEY_OPENLDAP_GUID
	 */
	public static String getLdapAttributeOpenLdapGUID() {
		String key = (String) props.get(KEY_OPENLDAP_GUID);
		if (key == null) {
			//Asignamos el valor que tenia por defecto
			key = "uidnumber";
			logger.error("Error obteniendo el guid para open ldap del fichero: "
					+ fileName);
		}
		return key;
	}




	//ATRIBUTOS DE GRUPOS
	/**
	 * Método que busca el atributo de guid para un sistema Active Directory
	 * @return valor para la key: KEY_ACTIVEDIRECTORY_GUID
	 */
	public static String getLdapAttributeGroupActiveDirectoryGUID() {
		String key = (String) props.get(KEY_ACTIVEDIRECTORY_GUID_GROUP);
		if (key == null) {
			//Asignamos el valor que tenia por defecto
			key = "objectGUID";
			logger.error("Error obteniendo el guid para active directory del fichero: "
					+ fileName);
		}
		return key;
	}

	/**
	 * Método que busca el atributo de guid para un sistema IPLANET
	 * @return valor para la key: KEY_IPLANET_GUID
	 */
	public static String getLdapAttributeGroupIplanetGUID() {
		String key = (String) props.get(KEY_IPLANET_GUID_GROUP);
		if (key == null) {
			//Asignamos el valor que tenia por defecto
			key = "nsuniqueid";
			logger.error("Error obteniendo el guid para iplanet del fichero: "
					+ fileName);
		}
		return key;
	}

	/**
	 * Método que busca el atributo de guid para un sistema OPENLDAP
	 * @return valor para la key: KEY_OPENLDAP_GUID
	 */
	public static String getLdapAttributeGroupOpenLdapGUID() {
		String key = (String) props.get(KEY_OPENLDAP_GUID_GROUP);
		if (key == null) {
			//Asignamos el valor que tenia por defecto
			key = "uidnumber";
			logger.error("Error obteniendo el guid para open ldap del fichero: "
					+ fileName);
		}
		return key;
	}




}
