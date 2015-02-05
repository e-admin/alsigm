package es.ieci.tecdoc.fwktd.ldap.core.constants;


/**
 * Clase de utilidad para almacenar propiedades de configuracion
 * @author Iecisa
 * @version $Revision: 114 $
 */
public final class LdapConfigurationConstants {

	/**
	 * Clave para el motor de directorio
	 */
	public static final String LDAP_ENGINE="LDAP_ENGINE";

	/**
	 * Clave para la url del servidor
	 */
	public static final String LDAP_URL="LDAP_URL";

	/**
	 * Clave de busqueda base del servidor
	 */
	public static final String LDAP_BASE="LDAP_BASE";

	/**
	 * Clave para el usuario para conectar con el servidor
	 */
	public static final String LDAP_USER="LDAP_USER";

	/**
	 * Clave para la contrasenia del usuario para conectar con el servidor
	 */
	public static final String LDAP_PASSWORD="LDAP_PASSWORD";

	/**
	 * Clave para indicar si se utiliza pooling
	 */
	public static final String LDAP_POOL="LDAP_POOL";

	/**
	 * Clave para indicar el timeout del pool
	 */
	public static final String LDAP_POOL_TIMEOUT="LDAP_POOL_TIMEOUT";

	/**
	 * Clave para indicar el atributo guid
	 */
	public static final String LDAP_GUID_ATTRIBUTE="LDAP_GUID_ATTRIBUTE";

	/**
	 * Clave para indicar el atributo dn
	 */
	public static final String LDAP_DN_ATTRIBUTE="LDAP_DN_ATTRIBUTE";

	/**
	 * Definicion de los valores de configuracion necesarios para el proxy
	 */
	public static final String [] LDAP_PROXY_CONFIG_PARAMETERS = new String [] {
		LDAP_ENGINE,
		LDAP_URL,
		LDAP_BASE,
		LDAP_USER,
		LDAP_PASSWORD,
		LDAP_POOL,
		LDAP_POOL_TIMEOUT,
		LDAP_GUID_ATTRIBUTE,
		LDAP_DN_ATTRIBUTE
	};

	/**
	 * Clave para indicar donde se buscan usuarios
	 */
	public static final String LDAP_USERS_SEARCH_START="LDAP_USERS_SEARCH_START";

	/**
	 * Clave para indicar el ambito de busqueda usuarios
	 */
	public static final String LDAP_USERS_SEARCH_SCOPE="LDAP_USERS_SEARCH_SCOPE";

	/**
	 * Clave para indicar el atributo de busqueda de usuarios
	 */
	public static final String LDAP_USERS_SEARCH_ATTRIBUTE="LDAP_USERS_SEARCH_ATTRIBUTE";

	/**
	 * Clave para indicar las clases de busqueda de usuarios
	 */
	public static final String LDAP_USERS_SEARCH_OBJECTCLASSES="LDAP_USERS_SEARCH_OBJECTCLASSES";

	/**
	 * Clave para indicar donde se buscan grupos
	 */
	public static final String LDAP_GROUPS_SEARCH_START="LDAP_GROUPS_SEARCH_START";

	/**
	 * Clave para indicar el ambito de busqueda de grupos
	 */
	public static final String LDAP_GROUPS_SEARCH_SCOPE="LDAP_GROUPS_SEARCH_SCOPE";

	/**
	 * Clave para indicar el atributo de busqueda de grupos
	 */
	public static final String LDAP_GROUPS_SEARCH_ATTRIBUTE="LDAP_GROUPS_SEARCH_ATTRIBUTE";

	/**
	 * Clave para indicar las clases de busqueda de grupos
	 */
	public static final String LDAP_GROUPS_SEARCH_OBJECTCLASSES="LDAP_GROUPS_SEARCH_OBJECTCLASSES";

	/**
	 * Clave para acceder al objeto con los atributos a recuperar de usuarios
	 */
	public static final String LDAP_USERS_ATTRIBUTES="LDAP_USERS_ATTRIBUTES";

	/**
	 * Clave para acceder al objeto con los atributos a recuperar de grupos
	 */
	public static final String LDAP_GROUPS_ATTRIBUTES="LDAP_GROUPS_ATTRIBUTES";

	/**
	 * Definicion de los valores de configuracion necesarios para el manager
	 */
	public static final String [] LDAP_MANAGER_CONFIG_PARAMETERS = new String [] {
		LDAP_USERS_SEARCH_START,
		LDAP_USERS_SEARCH_SCOPE,
		LDAP_USERS_SEARCH_ATTRIBUTE,
		LDAP_USERS_SEARCH_OBJECTCLASSES,
		LDAP_GROUPS_SEARCH_START,
		LDAP_GROUPS_SEARCH_SCOPE,
		LDAP_GROUPS_SEARCH_ATTRIBUTE,
		LDAP_GROUPS_SEARCH_OBJECTCLASSES
	};

	/**
	 * Constructor privado
	 */
	private LdapConfigurationConstants(){

	}
}
