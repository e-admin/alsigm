package es.ieci.tecdoc.archidoc.organization.messages;


/**
 * Constantes de error junto con las etiquetas para acceder a los mensajes
 * @author Iecisa
 * @version $Revision: 6586 $
 *
 */
public final class OrganizationErrorCodes {

	/**
	 * Constructor privado
	 */
	private OrganizationErrorCodes(){

	}

	/**
	 * Nombre del fichero properties de errores
	 */
	public static final String BUNDLE = "es.ieci.tecdoc.archidoc.organization.resources.errorcodes";

	/**
	 * Error al inicializar el conector
	 */
	public static final String ERR_20001 				= "ERR_20001_CONNECTOR_INICIALIZATION";

	/**
	 * Error al obtener los hijos de un organo
	 */
	public static final String ERR_20002 				= "ERR_20002_GET_ORGANIZATION_CHILDREN";

	/**
	 * Error al obtener las instituciones productoras
	 */
	public static final String ERR_20003 				= "ERR_20003_GET_ROOT_ORGANIZATIONS";

	/**
	 * Error al obtener un organo
	 */
	public static final String ERR_20004 				= "ERR_20004_GET_ORGANIZATION_ATTRIBUTE_VALUE";

	/**
	 * Error al obtener el organo del usuario
	 */
	public static final String ERR_20005 				= "ERR_20005_GET_USER_ORGANIZATION";

	/**
	 * Error al obtener los organos que contienen un texto en su nombre
	 */
	public static final String ERR_20006 				= "ERR_20006_GET_ORGANIZATIONS_WITH_NAME";

	/**
	 * Error al obtener los organos antecesores
	 */
	public static final String ERR_20007 				= "ERR_20007_GET_ORGANIZATIONS_ANCESTORS";

	/**
	 * Error al obtener los organos dependientes
	 */
	public static final String ERR_20008 				= "ERR_20008_GET_ORGANIZATIONS_DEPENDENT";

	/**
	 * Error al obtener los usuarios de los organos
	 */
	public static final String ERR_20009 				= "ERR_20009_GET_USERS_FROM_ORGANIZATIONS";

	/**
	 * Error tipo de parametro invalido
	 */
	public static final String ERR_20010 				= "ERR_20010_PARAMETER_VALUE_INVALID";

}
