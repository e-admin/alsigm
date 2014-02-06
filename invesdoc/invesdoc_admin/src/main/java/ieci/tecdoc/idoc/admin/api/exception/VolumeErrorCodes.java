package ieci.tecdoc.idoc.admin.api.exception;

/**
 * Proporciona los códigos de error específicos del API de administración de
 * volúmenes.
 */
public class VolumeErrorCodes 
{
	public VolumeErrorCodes()
	{	
	}
	
	/**
	 * Código de error base.
	 */
	public static final long EC_PREFIX = 3004000;
	/**
	 * Ya existe un volumen con ese nombre.
	 */
	public static final long EC_VOL_EXIST_NAME = EC_PREFIX + 1;
	/**
	 * Ya existe un volumen con esa ruta de acceso.
	 */
	public static final long EC_VOL_EXIST_FILE = EC_PREFIX + 2;
	/**
	 * No se ha podido crear el fichero.
	 */
	public static final long EC_VOL_NOT_EXIST_FILE = EC_PREFIX + 3;
	/**
	 * No se permite introducir comillas en el texto.
	 */
	public static final long EC_VOL_REMARKS_EXIST_QUOTES = EC_PREFIX + 4;
	/**
	 * No existe el volumen que se quiere cargar.
	 */
	public static final long EC_VOL_NOT_EXIST = EC_PREFIX + 5;
	/**
	 * No se puede eliminar el volumen porque tiene ficheros.
	 */
	public static final long EC_VOL_HAS_FILES = EC_PREFIX + 6;
	/**
	 * Debe cumplimentar 'Capacidad' con un valor superior 
	 * a la ocupación actual del volumen.
	 */
	public static final long EC_VOL_MAXSIZE_EXCEED = EC_PREFIX + 7;
	
	/**
	 * No se permite crear más volúmenes para este tipo de repositorio
	 */
	public static final long EC_VOL_NO_PERMIT_CREATE_VOL = EC_PREFIX + 8;
	/**
	 * No se permite borrar el volumen perteneciente a este tipo de repositorio 
	 * debe borrar el repositorio.
	 */
	public static final long EC_VOL_NO_PERMIT_DELETE_VOL = EC_PREFIX + 9;
	
}
