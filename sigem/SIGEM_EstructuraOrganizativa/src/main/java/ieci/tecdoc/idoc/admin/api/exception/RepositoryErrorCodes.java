package ieci.tecdoc.idoc.admin.api.exception;

/**
 * Proporciona los códigos de error específicos del API de administración de
 * repositorios de volúmenes.
 */
public class RepositoryErrorCodes
{
	private RepositoryErrorCodes()
	{
	}
	/**
	 * Código de error base.
	 */
	public static final long EC_PREFIX = 3003000;
	/**
	 * Ya existe un repositorio con ese nombre.
	 */
	public static final long EC_REP_EXIST_NAME = EC_PREFIX + 1;
	/**
	 * Ya existe un repositorio con esa ruta de acceso.
	 */
	public static final long EC_REP_EXIST_FILE = EC_PREFIX + 2;
	/**
	 * No se ha podido crear el fichero.
	 */
	public static final long EC_REP_NOT_EXIST_FILE = EC_PREFIX + 3;
	/**
	 * No se permite introducir comillas en el texto.
	 */
	public static final long EC_REP_REMARKS_EXIST_QUOTES = EC_PREFIX + 4;
	/**
	 * No existe el repositorio que se quiere cargar.
	 */
	public static final long EC_REP_NOT_EXIST = EC_PREFIX + 5;
	/**
	 * No se puede eliminar el repositorio porque tiene volúmenes.
	 */
	public static final long EC_REP_HAS_VOLUMES = EC_PREFIX + 6;
	
	/**
	 * No se permite crear más de un repositorio de base de datos
	 */
	public static final long EC_REP_DB_EXISTS = EC_PREFIX + 7;
	
}
