package ieci.tecdoc.idoc.admin.api.exception;

/**
 * Proporciona los códigos de error específicos del API de administración de
 * lista de volúmenes.
 */
public class VolumeListErrorCodes {
	private VolumeListErrorCodes()
	{
	}
	
	/**
	 * Código de error base.
	 */
	public static final long EC_PREFIX = 3005000;
	/**
	 * Ya existe una lista de volúmenes con ese nombre.
	 */
	public static final long EC_LISTVOL_EXIST_NAME = EC_PREFIX + 1;
	/**
	 * No se permite introducir comillas en el texto.
	 */
	public static final long EC_LISTVOL_REMARKS_EXIST_QUOTES = EC_PREFIX + 2;
	/**
	 * No existe la lista de volúmenes que se quiere cargar.
	 */
	public static final long EC_LISTVOL_NOT_EXIST = EC_PREFIX + 3;
	/**
	 * No se puede borrar la lista, está asociada a un archivador.
	 */
	public static final long EC_LISTVOL_ASSOC_ARCH = EC_PREFIX + 4;
	/**
	 * La lista no tiene volúmenes.
	 */
	public static final long EC_LISTVOL_NOT_VOLS = EC_PREFIX + 5;
	/**
	 * Ya existe ese volumen en la lista.
	 */
	public static final long EC_LISTVOL_EXIST_VOL = EC_PREFIX + 6;
	
	/**
	 * No existe la relación lista / volumen
	 */
	public static final long EC_LISTVOL_NOT_EXIST_REL = EC_PREFIX + 7;
}
