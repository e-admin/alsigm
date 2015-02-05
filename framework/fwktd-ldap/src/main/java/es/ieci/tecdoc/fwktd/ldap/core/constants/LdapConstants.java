package es.ieci.tecdoc.fwktd.ldap.core.constants;


/**
 * Constantes para el conector LDAP
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public final class LdapConstants {

	/**
	 * Parametro vacio
	 */
	public static final String EMPTY_PARAMETER="";

	/**
	 * Parametro true
	 */
	public static final String TRUE_PARAMETER="true";

	/**
	 * Valor por defecto para pool
	 */
	public static final boolean LDAP_POOL_DEFAULT_VALUE=false;

	/**
	 * Caracter separador de nodos
	 */
	public static final String NODE_SEPARATOR = ",";

	/**
	 * Factoria Ldap por defecto
	 */
	public static final String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

	/**
	 * Atributos binarios en el contexto
	 */
	public static final String CONTEXT_ATTRIBUTES_BINARY = "java.naming.ldap.attributes.binary";

	/**
	 * Atributo tipo de objeto
	 */
	public static final String OBJECT_CLASS_ATTRIBUTE = "objectClass";

	/**
	 * Parametro de entorno pool
	 */
	public static final String POOL_ENV_PARAMETER = "com.sun.jndi.ldap.connect.pool";

	/**
	 * Parametro de entorno pool timeout
	 */
	public static final String POOL_TIMEOUT_ENV_PARAMETER = "com.sun.jndi.ldap.connect.pool.timeout";

	/**
	 * Tipo de autenticacion
	 */
	public static final String SECURITY_AUTHENTICATION = "simple";

	/**
	 * Constante para indicar busqueda sin limite de resultados
	 */
	public static final int NO_SEARCH_LIMIT = 0;

	/**
	 * Control de resultados por paginas
	 */
	public static final String SIMPLE_PAGED_RESULTS_CONTROL = "1.2.840.113556.1.4.319";

	/**
	 * Atributo control soportado
	 */
	public static final String SUPPORTED_CONTROL_ATTRIBUTE = "supportedcontrol";

	/**
	 * Tamanio por defecto de las paginas de resultados
	 * Con el conector de Spring da problemas en OpenLdap si el numero de resultados es > tamanio de pagina
	 */
	public static final int PAGE_SIZE = 500;

	/**
	 * Control de resultados paginados en request para jdk > 1.5
	 */
	public static final String DEFAULT_REQUEST_CONTROL = "javax.naming.ldap.PagedResultsControl";

	/**
	 * Control de resultados paginados en response para jdk > 1.5
	 */
	public static final String DEFAULT_RESPONSE_CONTROL = "javax.naming.ldap.PagedResultsResponseControl";

	/**
	 * Control de resultados paginados en request para jdk = 1.4
	 */
	public static final String FALLBACK_REQUEST_CONTROL = "com.sun.jndi.ldap.ctl.PagedResultsControl";

	/**
	 * Control de resultados paginados en response para jdk = 1.4
	 */
	public static final String FALLBACK_RESPONSE_CONTROL = "com.sun.jndi.ldap.ctl.PagedResultsResponseControl";

	/**
	 * Constructor privado
	 */
	private LdapConstants(){

	}
}
