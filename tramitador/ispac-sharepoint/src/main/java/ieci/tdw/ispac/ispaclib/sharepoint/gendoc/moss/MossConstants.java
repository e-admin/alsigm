package ieci.tdw.ispac.ispaclib.sharepoint.gendoc.moss;

public class MossConstants {

	/**
	 * Código de error que indica que la operación fue correcta
	 */
	public static final String ERRORCODE_OK = "0x00000000";

	/**
	 * Prefijo con el que empiezan las propiedades cuando se obtiene el item de sharepoint
	 */
	public static final String PROPERTY_PREFIX = "ows_";

	/**
	 * Propiedad nombre del fichero
	 */
	public static final String METADATA_FILENAME = "LinkFilename";
	public static final String PROPERTY_FILENAME = PROPERTY_PREFIX + METADATA_FILENAME;

	/**
	 * Propiedad Id
	 */
	public static final String METADATA_ID = "ID";
	public static final String PROPERTY_ID = PROPERTY_PREFIX + METADATA_ID;

	/**
	 * Propiedad version
	 */
	public static final String METADATA_VERSION = "owshiddenversion";
	public static final String PROPERTY_VERSION = PROPERTY_PREFIX + METADATA_VERSION;

	/**
	 * Propiedad único ID
	 */
	public static final String METADATA_UNIQUEID = "UniqueId";
	public static final String PROPERTY_UNIQUEID = PROPERTY_PREFIX + METADATA_UNIQUEID;

	/**
	 * Propiedad title
	 */
	public static final String METADATA_TITLE = "Title";
	public static final String PROPERTY_TITLE = PROPERTY_PREFIX + METADATA_TITLE;

	/**
	 * Propiedad referencia de fichero
	 */
	public static final String METADATA_FILEREF = "FileRef";
	public static final String PROPERTY_FILEREF = PROPERTY_PREFIX + METADATA_FILEREF;

	/**
	 * Propiedad tamaño de fichero
	 */
	public static final String METADATA_FILESIZE = "FileSizeDisplay";
	public static final String PROPERTY_FILESIZE = PROPERTY_PREFIX + METADATA_FILESIZE;

	/**
	 * Acciones del método UpdateListItems
	 *
	 */
	public static final String ACTION_NEW = "New";
	public static final String ACTION_UPDATE = "Update";
	public static final String ACTION_DELETE = "Delete";
	public static final String ACTION_RENAME = "Rename";

	public static final String RETURN_CODE_CREATE_FOLDER_ALREADYEXISTS = "AlreadyExists";
	public static final String RETURN_CODE_CREATE_FOLDER_ALREADYEXISTS_ERROR_ID = "13";

	public static final String RETURN_CODE_CREATE_FOLDER_FOLDERNOTFOUND = "FolderNotFound";
	public static final String RETURN_CODE_CREATE_FOLDER_FOLDERNOTFOUND_ERROR_ID = "10";

	public static final String RETURN_CODE_CREATE_FOLDER_OK = "<Result/>";

	// Usados para formar url
	public static final String SLASH ="/";

	public static final char METADATA_SEPARATOR = '#';

	public static final String DEFAULT_CONFIG_FILENAME = "sharepoint-config.xml";

	public static final String REPOSITORY_PROPERTY_USER = "user";
	public static final String REPOSITORY_PROPERTY_PASSWORD = "password";
	public static final String REPOSITORY_PROPERTY_SITEURL = "siteUrl";
	public static final String REPOSITORY_PROPERTY_SITEPATH = "sitePath";
	public static final String REPOSITORY_PROPERTY_LIBRARY = "library";

	public static final String CONFIG_PROPERTY_TOKEN_DOCUMENT_NAME = "documentNameToken";

}
