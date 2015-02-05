package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;

/**
 * Clase que gestiona la configuración de ISPAC.
 *
 */
public class ISPACConfiguration extends PropertiesConfiguration {

	private static final long serialVersionUID = -3173809870321809908L;

	private static ISPACConfiguration mInstance = null;

	private static final String DEFAULT_CONFIG_FILENAME = "ispac.properties";

	public final static String DCMT_CABINET = "DCMT_CABINET";
	public final static String DCMT_DOCBASE = "DCMT_DOCBASE";
	public final static String DCMT_PASSWORD = "DCMT_PASSWORD";
	public final static String DCMT_USER = "DCMT_USER";
	public final static String DRIVER = "DRIVER";

	// Path del repositorio temporal
	public final static String TEMPORARY_PATH = "TEMPORARY_PATH";
	public final static String OPEN_OFFICE_CONNECT = "OPEN_OFFICE_CONNECT";
	public final static String OPEN_OFFICE_ADDITIONAL_INSTANCES = "OPEN_OFFICE_ADDITIONAL_INSTANCES";
	public final static String OPEN_OFFICE_TIMEOUT = "OPEN_OFFICE_TIMEOUT";

	public final static String PASSWORD = "PASSWORD";
	public final static String POOLNAME = "POOLNAME";
	public final static String URL = "URL";
	public final static String USER = "USER";
	public final static String FILE_ENTITY_FORM = "FILE_ENTITY_FORM";
	public final static String FOLDER_ENTITY_OUTPUT = "FOLDER_ENTITY_OUTPUT";
	public final static String TEMPLATE_PATH = "TEMPLATE_PATH";
	public final static String REPORT_PATH ="REPORT_PATH";
	public final static String IMAGES_REPOSITORY_PATH="IMAGES_REPOSITORY_PATH";
	public final static String CONNECTOR_MANAGER = "CONNECTOR_MANAGER";
	public final static String CONNECTOR_MANAGER_CFG_VARNAME = "CONNECTOR_MANAGER_CFG_VARNAME";
	public final static String CONNECTOR_ARCHIVE = "CONNECTOR_ARCHIVE";
	public final static String REPOSITORY_PATH = "REPOSITORY_PATH";
	public final static String MAX_DIRECTORY_FILES = "MAX_DIRECTORY_FILES";
	public final static String DIGESTER_RELATIVE_PATH = "DIGESTER_RELATIVE_PATH";
	public final static String KEEP_ALIVE = "KEEP_ALIVE";
	public final static String SESSION_TIMEOUT = "SESSION_TIMEOUT";
	public final static String DEFAULT_TEMPLATE = "DEFAULT_TEMPLATE";
	public final static String SICRESS_POOL_NAME = "SICRESS_POOL_NAME";
	public final static String SICRESS_ARCHIVE_ID = "SICRESS_ARCHIVE_ID";

	public final static String CATALOG_ENTITYDEF = "CATALOG_ENTITYDEF";
	public final static String SCHEME_DBA		= "SCHEME_DBA";

	public final static String MAX_SEARCH_FRM_RESULTS ="MAX_SEARCH_FRM_RESULTS";
	public final static String MAX_EXPS_SHOW_TRASH ="MAX_EXPS_SHOW_TRASH";
	public final static String MAX_TBL_SEARCH_VALUES="MAX_TBL_SEARCH_VALUES";

	public final static String THIRDPARTY_SICRES_POOL_NAME_PATTERN = "THIRDPARTY_SICRES_POOL_NAME_PATTERN";

	/* =========================================================================
	 * Configuración de SICRES.
	 * ====================================================================== */
	/** Gestor de registro SICRES. */
	public final static String SICRES_CONNECTOR_CLASS = "SICRES_CONNECTOR_CLASS";

	/** Tipo de documento por defecto para los documentos de los registros distribuidos. */
	public final static String SICRES_INTRAY_DEFAULT_DOCUMENT_TYPE = "SICRES_INTRAY_DEFAULT_DOCUMENT_TYPE";

	/** Indica si al registrar de salida un documento se envía el contenido del documento. */
	public final static String SICRES_SEND_DOCUMENTS_CONTENT = "SICRES_SEND_DOCUMENTS_CONTENT";


	/* =========================================================================
	 * Configuración del API de acceso a terceros.
	 * ====================================================================== */
	public final static String THIRDPARTY_API_CLASS = "THIRDPARTY_API_CLASS";

	/* =========================================================================
	 * Configuración del API de BPM
	 * ====================================================================== */
	public final static String BPM_API_CLASS = "BPM_API_CLASS";

	/* =========================================================================
	 * Configuración del connector con el directorio de usuarios
	 * ====================================================================== */
	public final static String DIRECTORY_CONNECTOR_CLASS = "DIRECTORY_CONNECTOR_CLASS";

	/* =========================================================================
	 * Identificador del archivador a usar como repositorio de documentos electronicos firmados.
	 * ====================================================================== */
	public final static String RDE_ARCHIVE_ID = "RDE_ARCHIVE_ID";

	/* =========================================================================
	 * Nombre de repositorio a utilizar para almacenar en la tabla de documentos.
	 * ====================================================================== */
	public static final String REPOSITORY = "REPOSITORY";

	public static final String CTLG_APPLICATION = "CTLG_APPLICATION";
	public static final String TRAM_APPLICATION = "TRAM_APPLICATION";
	public static final String SUPERUSER = "SUPERUSER";

	/* =========================================================================
	 * Más de un organismo (MULTIORGANIZATION = YES/NO)
	 * y parámetro en el que se recibe el código del organismo al que se conecta el usuario.
	 * ====================================================================== */
//	public static final String MULTIORGANIZATION = "MULTIORGANIZATION";
//	public static final String MULTIORGANIZATION_PARAM = "MULTIORGANIZATION_PARAM";

	/* =========================================================================
	 * Con un único organismo se necesita el id de la tabla SPAC_ORGANISMOS_NUMEXP
	 * para obtener el siguiente número de expediente.
	 * ====================================================================== */
//	public static final String ORGANIZATION_ID = "ORGANIZATION_ID";
//	public static final String ORGANIZATION_CODE = "ORGANIZATION_CODE";
//
	/*=========================================================================
	 *
	 * Patron para el nombre de los Datasource cuando se trabaja con multiorganismo
	 * =========================================================================
	 */
	public static final String POOLNAME_PATTERN = "POOLNAME_PATTERN";
	public static final String USERS_POOLNAME_PATTERN = "USERS_POOLNAME_PATTERN";


	/* =========================================================================
	 * Tipo de contador para expedientes
	 * ====================================================================== */
	public static final String EXPEDIENT_COUNTER_TYPE = "EXPEDIENT_COUNTER_TYPE";
	public static final String EXPEDIENT_COUNTER_TYPE_PROCEDURE = "Procedure";

	/* =========================================================================
	 * Formato para el número de expediente.
	 * ====================================================================== */
	public static final String FORMAT_NUM_EXP = "FORMAT_NUM_EXP";

	/* =========================================================================
	 * Configuración de la clase que establece el contexto para los elementos
	 * estáticos de la presentación en un entorno distribuido.
	 * ====================================================================== */
	public static final String STATIC_CONTEXT_CLASS = "STATIC_CONTEXT_CLASS";

	/* =========================================================================
	 * Tooltips.
	 * ====================================================================== */
	public static final String TOOLTIPS_SHOW = "TOOLTIPS_SHOW";
	public static final String TOOLTIPS_STYLE = "TOOLTIPS_STYLE";

	/* =========================================================================
	 * Gestión del publicador
	 * ====================================================================== */
	public static final String PUBLISHER_MANAGEMENT_ACTIVE = "PUBLISHER_MANAGEMENT_ACTIVE";


	/* =========================================================================
	 * Gestión de  tablas jerarquicas
	 * ====================================================================== */
	public static final String HIERARCHICAL_TABLES_MANAGEMENT_ACTIVE = "HIERARCHICAL_TABLES_MANAGEMENT_ACTIVE";

	/* =========================================================================
	 * Gestión de firmas digitales
	 * ====================================================================== */
	public final static String DIGITAL_SIGN_CONNECTOR_CLASS = "DIGITAL_SIGN_CONNECTOR_CLASS";

	/* =========================================================================
	 * Configuración del conector de sellado
	 * ====================================================================== */
	public final static String STAMP_CONNECTOR_CLASS = "STAMP_CONNECTOR_CLASS";

	/* =========================================================================
	 * Configuración del conector de productores.
	 * ====================================================================== */
	public final static String PRODUCERS_CONNECTOR_CLASS = "PRODUCERS_CONNECTOR_CLASS";

	/* ============================================================================
	 * Configuración del conector de gestión de firmas. Portafimas
	 */
	public final static String PROCESS_SIGN_CONNECTOR_CLASS="PROCESS_SIGN_CONNECTOR_CLASS";

	/* ============================================================================
	 * Configuración del conector de gestión de firmas. Portafimas
	 */
	public final static String PROCESS_SIGN_CONNECTOR_QUERY_URL="PROCESS_SIGN_CONNECTOR_QUERY_URL";
	public final static String PROCESS_SIGN_CONNECTOR_MODIFY_URL="PROCESS_SIGN_CONNECTOR_MODIFY_URL";
	public final static String PROCESS_SIGN_CONNECTOR_USER="PROCESS_SIGN_CONNECTOR_USER";
	public final static String PROCESS_SIGN_CONNECTOR_PASSWORD="PROCESS_SIGN_CONNECTOR_PASSWORD";
	public final static String PROCESS_SIGN_CONNECTOR_APPLICATION="PROCESS_SIGN_CONNECTOR_APPLICATION";
	public final static String PROCESS_SIGN_CONNECTOR_DOCTYPE="PROCESS_SIGN_CONNECTOR_DOCTYPE";

	/* =========================================================================
	 * Gestor de plantillas
	 * ====================================================================== */

	/** Clase que implementa el conector de combinación de plantillas. */
	public final static String PARSER_CONNECTOR_CLASS = "PARSER_CONNECTOR_CLASS";

	/** Nivel de anidamiento de marcadores de plantillas*/
	public final static String PARSER_CONNECTOR_TAGS_NESTING_LEVEL = "PARSER_CONNECTOR_TAGS_NESTING_LEVEL";


	/**
	 * Constructor.
	 */
	protected ISPACConfiguration() {
		super();
	}

	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized ISPACConfiguration getInstance()
			throws ISPACException {

		if (mInstance == null) {
			mInstance = new ISPACConfiguration();
			mInstance.initiate(DEFAULT_CONFIG_FILENAME);
		}
		return mInstance;
	}

//	public FileInputStream getFileInputStream(String fileName)
//            throws ISPACException {
//
//		if (fileName == null) {
//			throw new ISPACException(
//					"No se ha proporcionado el nombre del fichero de configuración");
//		}
//
//		try {
//			String folder = get(ISPACConfiguration.FOLDER);
//			File file = new File(folder, fileName);
//			return new FileInputStream(file);
//
//		} catch (FileNotFoundException e) {
//			throw new ISPACException(e);
//		}
//	}
}
