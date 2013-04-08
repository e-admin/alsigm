package es.ieci.tecdoc.fwktd.ldap.core.messages;


/**
 * Constantes de Error junto con las etiquetas para acceder a los mensajes
 * @author Iecisa
 * @version $Revision: 78 $
 *
 */
public final class LdapErrorCodes {

	/**
	 * Constructor privado
	 */
	private LdapErrorCodes(){

	}

	/**
	 * Nombre del fichero properties de errores
	 */
	public static final String BUNDLE = "es.ieci.tecdoc.fwktd.ldap.core.resources.errorcodes";

	/**
	 * Error para indicar que no se ha proporcionado la password
	 */
	public static final String ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY 				= "ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY";

	/**
	 * Error al crear el control de busqueda paginada
	 */
	public static final String ERR_10001_PAGED_RESULTS_CONTROL_CREATION 				= "ERR_10001_PAGED_RESULTS_CONTROL_CREATION";

	/**
	 * Error en la autenticacion
	 */
	public static final String ERR_10002_AUTHENTICATION_PROBLEM 				= "ERR_10002_AUTHENTICATION_PROBLEM";

	/**
	 * Error generico de LDAP
	 */
	public static final String ERR_10003_GENERIC_PROBLEM 				= "ERR_10003_GENERIC_PROBLEM";

	/**
	 * Error resultados obtenidos no concuerdan con los esperados
	 */
	public static final String ERR_10004_RESULT_SIZE_NOT_MATCH_EXPECTED				= "ERR_10004_RESULT_SIZE_NOT_MATCH_EXPECTED";

	/**
	 * Error no se ha encontrado el control
	 */
	public static final String ERR_10005_CONTROL_CONTRUCTOR_NOT_FOUND				= "ERR_10005_CONTROL_CONTRUCTOR_NOT_FOUND";

	/**
	 * Error al crear la instancia del control
	 */
	public static final String ERR_10006_CONTROL_INSTANCE_FAILED				= "ERR_10006_CONTROL_INSTANCE_FAILED";

	/**
	 * Error al obtener metodo de una clase
	 */
	public static final String ERR_10007_CLASS_METHOD_NOT_FOUND				= "ERR_10007_CLASS_METHOD_NOT_FOUND";

	/**
	 * Error al obtener clases de resultados paginados
	 */
	public static final String ERR_10008_PAGINATED_RESULT_CLASSES_NOT_FOUND				= "ERR_10008_PAGINATED_RESULT_CLASSES_NOT_FOUND";

	/**
	 * Error al parsear la cookie del control de resultados paginados
	 */
	public static final String ERR_10009_PAGINATED_RESULT_COOKIE_PARSE_ERROR				= "ERR_10009_PAGINATED_RESULT_COOKIE_PARSE_ERROR";

	/**
	 * Error control no soportado
	 */
	public static final String ERR_10010_CONTROL_NOT_SUPPORTED_ERROR				= "ERR_10010_CONTROL_NOT_SUPPORTED_ERROR";

	/**
	 * Error nodo no encontrado
	 */
	public static final String ERR_10011_NODE_NOT_FOUND_ERROR				= "ERR_10011_NODE_NOT_FOUND";
}
