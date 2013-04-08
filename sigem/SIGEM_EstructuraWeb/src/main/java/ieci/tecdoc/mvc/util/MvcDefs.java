package ieci.tecdoc.mvc.util;

public final class MvcDefs
{

	//~ Static fields/initializers
	// ----------------------------------------------

    public static final String TOKEN_BD_CONNECT_ERROR = "BD_CONNECT_ERROR";
    public static final String TOKEN_LDAP_CONNECT_ERROR = "LDAP_CONNECT_ERROR";
    
    
	public static final String	TOKEN_LOGIN_PATH						= "loginPath";
	public static final String	TOKEN_PERMITTED_PATHS					= "permittedPaths";
	public static final String	TOKEN_TIMEOUT_PERMITTED_PATHS			= "timeuotPermittedPaths";
	public static final String	TOKEN_SESSION_CHECK_PATHS				= "sessionCheckPaths";
	public static final String	TOKEN_EXCLUDED_PATHS					= "excludedPaths";
	public static final String	TOKEN_DEFAULT_ENCODING					= "defaultEncoding";
	public static final String	PERMITTED_PATHS_SEPARATOR				= ",";
	public static final String	LANGUAGES_PATHS_SEPARATOR				= "_";
	public static final String	TOKEN_LOGIN_METHOD						= "loginMethod";
	public static final String	TOKEN_CNTS_NUM_TRIES					= "cntsTriesNum";
	public static final String	TOKEN_IDOC_SESSION_ID					= "IDOC_SESSION_ID";
	public static final String	TOKEN_ACS_AUTH							= "AcsAccessObject";
	public static final String	TOKEN_ACTIVE_USERS						= "activeUsers";
	public static final String	TOKEN_PAGE_SCOPE						= "page";
	public static final String	TOKEN_REQUEST_SCOPE						= "request";
	public static final String	TOKEN_SESSION_SCOPE						= "session";
	public static final String	TOKEN_APPLICATION_SCOPE					= "application";
	public static final String	TOKEN_PACKAGE							= "ieci.tecdoc.mvc.taglibs";
	public static final int		ROOT_NODE								= 0;
	public static final char	CHAR_SLASH								= '/';
	public static final String	TOKEN_LOG4J_PROPERTY_CHECK				= "log4j_check";
	public static final String	TOKEN_JCONFIG_PROPERTY_CHECK			= "check";
	public static final String	TOKEN_INIT_SYSTEM_FILE					= "system_file";
	public static final String	TOKEN_INIT_LDAP_FILE					= "ldap_file";
	public static final String	TOKEN_SYSTEM_LANGUAGE					= "system_language";
	public static final String	TOKEN_ARCHIVE_FOLDERS_SEARCH			= "arch_flds_search";
	public static final String	TOKEN_TREE_MENU							= "ieci.tecdoc.mvc.util.tree_menu";
	public static final String	TOKEN_FOLDER_TREE_MENU					= "ieci.tecdoc.mvc.util.folder_tree_menu";
	public static final String	TOKEN_FOLDER							= "ieci.tecdoc.mvc.util.folder";
	public static final String	TOKEN_MEMORY_FOLDER						= "ieci.tecdoc.mvc.util.folder.memory";
	public static final String	TOKEN_TREE_COOKIES						= "treeCookies";
	public static final String	TOKEN_INPUT_TYPE_TEXT					= "text";
	public static final String	TOKEN_INPUT_TYPE_TEXTAREA				= "textarea";
	public static final String	TOKEN_SYSTEM_PROPERLY_STARTED			= "system_start_error";
	public static final String	TOKEN_SESSION_LIFE_TIMEOUT				= "session_life_time";
	public static final String	TOKEN_DEFAULT_SELECTED_TAB_VALUES		= "values";
	public static final String	TOKEN_DEFAULT_SELECTED_TAB_DOCS			= "docs";
	public static final String	TOKEN_FILE_EXT_AVI						= "avi";
	public static final String	TOKEN_FILE_EXT_EXCEL					= "xls";
	public static final String	TOKEN_FILE_EXT_GIF						= "gif";
	public static final String	TOKEN_FILE_EXT_HTML						= "html";
	public static final String	TOKEN_FILE_EXT_JPG						= "jpg";
	public static final String	TOKEN_FILE_EXT_JPEG						= "jpeg";
	public static final String	TOKEN_FILE_EXT_PDF						= "pdf";
	public static final String	TOKEN_FILE_EXT_POWERPOINT				= "ppt";
	public static final String	TOKEN_FILE_EXT_TIF						= "tif";
	public static final String	TOKEN_FILE_EXT_TIFF						= "tiff";
	public static final String	TOKEN_FILE_EXT_TXT						= "txt";
	public static final String	TOKEN_FILE_EXT_WMV						= "wmv";
	public static final String	TOKEN_FILE_EXT_WORD						= "doc";
	public static final String	TOKEN_FILE_EXT_XML						= "xml";
	public static final String	TOKEN_FILE_EXT_XSL						= "xsl";
	public static final String	TOKEN_FILE_EXT_ZIP						= "zip";
	public static final String	TOKEN_USER								= "USER";
	public static final String	TOKEN_FOLDER_GLOBAL_LIST				= "fld_global_list";
	public static final String	TOKEN_FOLDER_MEMORY_GLOBAL_LIST			= "fld_memory_global_list";
	public static final String	TOKEN_FOLDER_ROOT_LIST					= "fld_root_list";
	public static final String	TOKEN_DEFAULT_MENU						= "default_menu";
	public static final String	TOKEN_MENU_REPOSITORY_KEY				= "repository";
	public static final String	TOKEN_TABS_REPOSITORY					= "tabsRepository";
	public static final String	TOKEN_TABS								= "tabs";
	public static final String	TOKEN_DOCUMENT_ID						= "docId";
	public static final String	TOKEN_ARCHIVE_SEARCH_RESULTS_FORM		= "searchResultsForm";
	public static final String	TOKEN_ARCHIVE_SEARCH_FILTER_FORM		= "searchFilterForm";
	public static final String	TOKEN_ARCHIVE_SEARCH_RESULTS			= "folderIds";
	public static final int		MENU_CLSF_OPTION						= 0;
	public static final int		MENU_DOCUMENT_OPTION					= 1;
	public static final int		MENU_LINK_OPTION						= 2;
	public static final int		MENU_CLSF_ROOT_OPTION					= 3;
	public static final int		MENU_DOCUMENT_UPLOADED_OPTION			= 4;
	public static final int		CABINET									= 1;																								//DIRECTORIO
	public static final int		ARCHIVADOR								= 2;
	public static final int		FOLDER									= 3;
	public static final int		DOCUMENT								= 4;
	public static final int		LINK									= 5;
	public static final String	TOKEN_NEW_FOLDER						= "newFolder";
	public static final String	TOKEN_UPLOAD_PARAMETERS					= "ieci.tecdoc.mvc.util.folder.FolderParameters";
	public static final String	TOKEN_MAX_FILE_NAME_LENGTH				= "maxFileNameLength";
	public static final String	TOKEN_ACTIVE_TREE_NODE					= "activeTreeNode";
	public static final String	TOKEN_IS_MODIFY_FOLDER_COMMIT			= "commit";
	public static final int		SC_NOT_DOCUMENT_ADDED					= 420;
	public static final int		SC_WRONG_PARAMETERS						= 421;
	public static final String	TOKEN_DOC_REGEXP						= "doc([0-9])+.[a-zA-Z]+$";
	public static final String	TOKEN_DEFAULT_DOC_NAME					= "doc";
	public static final String	TOKEN_BUTTON_RESTORE					= "restore";
	public static final String	DEFAULT_MESSAGES_KEY					= "org.apache.struts.action.MESSAGE";
	public static final String	BASE_MESSAGES_RESOURCE_TEXT				= "message";
	public static final String	BASE_ERROR_MESSAGES_RESOURCE_TEXT		= "error";
	public static final String	TOKEN_WEP_APP_DEFAULT_CLASSPATH			= "WEB-INF/classes/";

	// Forwards
	public static final String	TOKEN_FORWARD_SUCCESS					= "success";
	public static final String	TOKEN_FORWARD_ERROR						= "error";
	public static final String	TOKEN_FORWARD_TREE						= "tree";
	public static final String	TOKEN_FORWARD_FOLDER_TREE				= "foldertree";
	public static final String	TOKEN_FORWARD_NODATA					= "noData";
	public static final String	TOKEN_FORWARD_FORMERROR					= "formerror";
	public static final String	TOKEN_FORWARD_SYSTEM_FAILURE			= "system.failure";
	public static final String	TOKEN_FORWARD_SYSTEM_ERROR				= "system.error";
	public static final String	TOKEN_FORWARD_INVALIDATED				= "invalidated";
	public static final String	TOKEN_FORWARD_UNEXPECTED				= "unexpected";
	public static final String	TOKEN_FORWARD_SAVE						= "save";
	public static final String	TOKEN_FORWARD_LIST						= "list";
	public static final String	TOKEN_FORWARD_SEARCH					= "search";
	public static final String	TOKEN_TABLE_ID							= "tableId";
	public static final String	TOKEN_FIELD_ID							= "fieldId";
	public static final String	TOKEN_VALIDATION_TABLE					= "VldTblToken";
	public static final String	TOKEN_VALIDATION_FORM					= "VldTableForm";
	public static final String	TOKEN_FORWARD_SUCCESS_CREATE			= "successCreate";

	// Plugins
	public static final String	PLUGIN_IDIOMA							= "idioma";
	public static final String	PLUGIN_DEFAULT_SKIN						= "default_skin";
	public static final String	PLUGIN_PROTOCOL							= "protocol";
	public static final String	PLUGIN_BASE_DIR							= "base_dir";
	public static final String	PLUGIN_IMAGES_DIR						= "images_dir";
	public static final String	PLUGIN_LOG4J_PATH						= "log4j_path";
	public static final String	PLUGIN_LOG4J_FILE						= "log4j_file";
	public static final String	PLUGIN_INVESDOC_ADMIN_LOGIN				= "invesdoc_admin_login";
	public static final String	PLUGIN_INVESDOC_ADMIN_PWD				= "invesdoc_admin_pwd";
	public static final String	PLUGIN_HTTP_SERVER						= "httpServer";
	public static final String	PLUGIN_HTTP_PORT						= "httpPort";
	public static final String	PLUGIN_INIT_SYSTEM_FILE					= "system_file";
	public static final String	PLUGIN_INIT_LDAP_FILE					= "ldap_file";
	public static final String	PLUGIN_APP_LANGUAGES					= "app_languages";
	public static final String	PLUGIN_TREE_MOUSEOVER_CLASS				= "mouseover_class";
	public static final String	PLUGIN_TREE_MOUSEOUT_CLASS				= "mouseout_class";
	public static final String	PLUGIN_MAX_TREE_NODES					= "numberOfMenusToLoad";
	public static final String	PLUGIN_DEFAULT_TAB						= "default_tab";
	public static final String	PLUGIN_EXCEPTION_HANDLE					= "exception_handle";
	public static final String	PLUGIN_DEAFULT_APP_LOCALE				= "locale";
	public static final String PLUGIN_DBENGINE                    		= "dbEngine";

	// Request
	public static final String	TOKEN_NODE								= "node";
	public static final String	TOKEN_NODE_NAME							= "nodeName";
	public static final String	TOKEN_NODE_OPEN							= "nodeOpen";
	public static final String	TOKEN_NODE_CLASS						= "nodeClass";
	public static final String	TOKEN_CABINET_ID						= "idArchivador";
	public static final String	TOKEN_FOLDER_ID							= "id";
	public static final String	TOKEN_SUBMIT_CLEAR						= "Limpiar";
	public static final String	TOKEN_SUBMIT_SAVE						= "Salvar";
	public static final String	TOKEN_SUBMIT_SEARCH						= "Buscar";
	public static final String	TOKEN_SUBMIT_SORT						= "Sort";
	public static final String	TOKEN_SUBMIT_VALUE						= "submitValue";
	public static final String	TOKEN_SUBMIT_CANCEL						= "Cancelar";
	public static final String	TOKEN_SUBMIT_FINALIZE					= "Finalize";
	public static final String	TOKEN_SUBMIT_VALUE_FINALIZE				= "Cerrar";
	public static final String	TOKEN1_PAGE_NUMBER						= "d-16544-p";
	public static final String	TOKEN2_PAGE_NUMBER						= "d-49653-p";
	public static final String	TOKEN_LOCALE							= "locale";
	public static final String	TOKEN_LANGUAGE							= "language";
	public static final String	TOKEN_ACTIVE_CABINET_ID					= "activeArch";
	public static final String	TOKEN_TAB								= "tab";
	public static final String	TOKEN_TAB_NAME							= "tabName";
	public static final String	TOKEN_ISREADONLY						= "readOnly";
	public static final String	TOKEN_FLD_NAME							= "name";
	public static final String	TOKEN_NODE_ORDER						= "order";
	public static final String	TOKEN_ELEMENT_ID						= "elementId";
	public static final String	TOKEN_ACTION_PARAMETER					= "method";
	public static final String	TOKEN_CREATE_FOLDER						= "createFld";

	// Tree
	public static final String	TOKEN_ROOT_NODE_NAME					= "tree";
	public static final String	TOKEN_ROOT_DOCUMENTS_NODE_NAME			= "documentsTree";
	public static final String	TOKEN_ROOT_MEMORY_NODE_NAME				= "memory_tree";
	public static final String	TOKEN_DEFAULT_NODE_NAME					= "default";

	//business
	public static final String	TOKEN_ARCHIVE							= "ArchiveObject";
	public static final String	TOKEN_ARCHIVE_FILTER					= "ArchiveFilters";
	public static final String	TOKEN_MULTIVALUES_TOKEN					= ";";
	public static final String	TOKEN_VALIDATION_EQUALS					= "=";
	public static final String	TOKEN_VALIDATION_STARTS_WITH			= "%";
	public static final String	TOKEN_VALIDATION_CONTAINS				= "%%";
	// Messages
	public static final String	MESSAGE_LOGIN_DEFAULT_ERROR				= "message.login.default_error";
	public static final String	MESSAGE_LOGIN_DUPLICATE					= "message.login.duplicate_user_in_system";
	public static final String	MESSAGE_INVALID_AUTH_SPEC				= "message.login.ec_invalid_auth_spec";
	public static final String	MESSAGE_INVALID_USER_NAME				= "message.login.ec_invalid_user_name";
	public static final String	MESSAGE_TOO_MANY_LOGIN_ATTEMPS			= "message.login.ec_too_many_login_attempts";
	public static final String	MESSAGE_NO_USER_IN_IDOC_SYSTEM			= "message.login.ec_no_user_in_idoc_system";
	public static final String	MESSAGE_NOT_VALID_LOGIN_METHOD			= "message.login.ec_not_valid_login_method";
	public static final String	MESSAGE_NOT_AUTH_ENTER_APP				= "message.login.ec_not_auth_enter_app";
	public static final String	MESSAGE_INIT_SYSTEM_FILE				= "message.init.system.config.file";

	//ERRORS
	public static final String	ERROR_APPLICATION_GENERAL				= "error.application.general";
	public static final String	ERROR_SEARCH_NO_RESULTS					= "error.search.no_results";
	public static final String	ERROR_FOLDER_MAY_NOT_LOCK				= "error.folder.may_not_lock";
	public static final String	ERROR_PARAMETER_STACK					= "errorStack";
	public static final String	ERROR_BEAN								= "errorbean";
	public static final String	EXCEPTION_HANDLE_DEVELOPMENT			= "development";
	public static final String	EXCEPTION_HANDLE_PRODUCCION				= "produccion";

	// URLs
	public static final String	URL_INVOKER_SERVLET						= "/error/404.jsp";
	public static final String	URL_APPLICATION_ERROR					= "/error/initialization_error.jsp";
	public static final String	URL_LOGIN								= "/acs/login.jsp";
	public static final String	URL_LOGOUT								= "/acs/logout.jsp";
	public static final String	URL_WELCOME_ACTION						= "/html/Welcome.do";
	public static final String	URL_SEARCH_PAGE_ACTION					= "/searchPage.do";
	public static final String	URL_SEARCH_LINK_ACTION					= "/searchLinkFld.do";
	public static final String	URL_REMOVE_LINK_ACTION					= "/html/removeLinkFld.do";
	public static final String	URL_SEARCH_ACTION						= "/html/search";

	// Filters Paths
	public static final String	FILTER_GZIP_PATH						= "/html";

	// Cookies Names
	public static final String	COOKIE_TREEVIEW							= "treeView";
	public static final String	COOKIE_SELECTED_TAB						= "selectedTab";

	// Messages Resources
	public static final String	MESSAGES_RESOURCE_ERRORS				= "errors";
	public static final String	MESSAGES_RESOURCE_AUDIT					= "audit";
	public static final String	MESSAGES_RESOURCE_MENU					= "menu";
	public static final String	MESSAGES_RESOURCE_ERRORS_PATH			= "resources.errors";
	public static final String	MESSAGES_RESOURCE_AUDIT_PATH			= "resources.audit";
	public static final String	MESSAGES_RESOURCE_MENU_PATH				= "resources.menu";
	public static final String	MESSAGES_RESOURCE_DEFAULT_PATH			= "resources.application";

	// Audit
	public static final String	AUDIT_LOGGER							= "auditLogger";
	public static final String	AUDIT_USER								= "user";
	public static final String	AUDIT_ACTION							= "action";
	public static final String	AUDIT_CABINET							= "cabinet";
	public static final String	AUDIT_FOLDER							= "folder";
	public static final String	AUDIT_MESSAGE							= "message";
	public static final String	AUDIT_DATE								= "date";
	public static final String	AUDIT_HEADER_USER						= "Usuari";
	public static final String	AUDIT_HEADER_ACTION						= "Acci&oacute;";
	public static final String	AUDIT_HEADER_CABINET					= "Arxivador";
	public static final String	AUDIT_HEADER_FOLDER						= "Carpeta";
	public static final String	AUDIT_HEADER_MESSAGE					= "Missatge";
	public static final String	AUDIT_HEADER_DATE						= "Data";
	public static final String	AUDIT_INIT_MESSAGE						= "Data d'inici del Log";
	public static final String	AUDIT_MESSAGE_ACTION_LOGIN				= "message.audit.action.login";
	public static final String	AUDIT_MESSAGE_ACTION_VIEW_FOLDER		= "message.audit.action.viewFolder";
	public static final String	AUDIT_MESSAGE_ACTION_MODIFY_FOLDER		= "message.audit.action.modifyFolder";
	public static final String	AUDIT_MESSAGE_LOGIN						= "message.audit.login";
	public static final String	AUDIT_MESSAGE_VIEW_FOLDER				= "message.audit.viewFolder";
	public static final String	AUDIT_MESSAGE_MODIFY_FOLDER				= "message.audit.modifyFolder";
}