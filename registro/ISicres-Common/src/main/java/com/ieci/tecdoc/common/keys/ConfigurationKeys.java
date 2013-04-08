package com.ieci.tecdoc.common.keys;

public interface ConfigurationKeys {

	/**
	 * *************************************************************************
	 */
	/** ******* Configuracion del Server ******** */
	/**
	 * *************************************************************************
	 */
	public static final String KEY_SERVER_AUTHENTICATION_POLICY = "/ISicres-Configuration/ISicres-Server/Authentication/AuthenticationPolicy";
	public static final String KEY_SERVER_AUTHENTICATION_POLICY_TYPE = "/ISicres-Configuration/ISicres-Server/Authentication/AuthenticationPolicyType";
	public static final String KEY_SERVER_REPOSITORY_MANAGER = "/ISicres-Configuration/ISicres-Server/Repository/RepositoryManager";
	// public static final String KEY_SERVER_REPOSITORY_CONNECTOR =
	// "/ISicres-Configuration/ISicres-Server/Repository/RepositoryConnector";
	public static final String KEY_SERVER_CHECK_PASSWORD = "/ISicres-Configuration/ISicres-Server/Authentication/CheckPassword";
	public static final String KEY_SERVER_CACHE_CLASS = "/ISicres-Configuration/ISicres-Server/SessionCache/@class";
	public static final String KEY_SERVER_CACHE_ENTRY_TIMEOUT = "/ISicres-Configuration/ISicres-Server/SessionCache/CacheEntryTimeout";
	public static final String KEY_SERVER_CACHE_ENTRY_SESSION_IDGENERATOR = "/ISicres-Configuration/ISicres-Server/SessionCache/CacheEntrySessionIDGenerator";
	public static final String KEY_SERVER_CACHE_CLEANER_SLEEP_TIME = "/ISicres-Configuration/ISicres-Server/SessionCache/CacheCleanerSleepTime";
	public static final String KEY_SERVER_DAO_IMPLEMENTATION = "/ISicres-Configuration/ISicres-Server/DatabaseDAO/DAOImplementation";

	// Validación de personas
	public static final String KEY_SERVER_PERSON_VALIDATION_IMPLEMENTATION = "/ISicres-Configuration/ISicres-Server/PersonValidation/PersonValidationImplementation";
	public static final String KEY_SERVER_MAXROWSFORVALIDATIONRULES = "/ISicres-Configuration/ISicres-Server/MaxRowsForValidationRules";

	public static final String KEY_TIMEOUT_FTP = "/ISicres-Configuration/ISicres-Server/TimeOutSocket";

	public static final String KEY_SERVER_DISTRIBUTION_REGISTER_IN_FROM_DISTRIBUTION = "/ISicres-Configuration/ISicres-Server/Distribution/RegisterInFromDistribution";
	public static final String KEY_SERVER_DISTRIBUTION_MANUAL_BOOK_OUT = "/ISicres-Configuration/ISicres-Server/Distribution/DistributionManualBookOut";


	/**
	 * *************************************************************************
	 */
	/** ******* Configuracion del Desktop ******** */
	/**
	 * *************************************************************************
	 */
	public static final String KEY_DESKTOP_TIME_LOCK_REGISTER_USER = "/ISicres-Configuration/ISicres-DesktopWeb/TimeLockRegisterUser";
	public static final String KEY_DESKTOP_DEFAULT_PAGE_INTER_DIREC_VIEW = "/ISicres-Configuration/ISicres-DesktopWeb/DefaultPageInterDirecView";
	public static final String KEY_DESKTOP_DEFAULT_PAGE_TABLE_RESULTS_SIZE = "/ISicres-Configuration/ISicres-DesktopWeb/DefaultPageTableResultsSize";
	public static final String KEY_DESKTOP_DEFAULT_PAGE_VALIDATION_LIST_SIZE = "/ISicres-Configuration/ISicres-DesktopWeb/DefaultPageValidationListSize";
	public static final String KEY_DESKTOP_DEFAULT_PAGE_DISTRIBUTION_MINUTA_SIZE = "/ISicres-Configuration/ISicres-DesktopWeb/DefaultPageDistributionMinutaSize";
	public static final String KEY_DESKTOP_DEFAULT_PAGE_PERSON_SIZE = "/ISicres-Configuration/ISicres-DesktopWeb/DefaultPagePersonSize";
	public static final String KEY_DESKTOP_INITIAL_FACTORY = "/ISicres-Configuration/ISicres-DesktopWeb/ISicres-Server-Connection/InitialFactory";
	public static final String KEY_DESKTOP_PROVIDER_URL = "/ISicres-Configuration/ISicres-DesktopWeb/ISicres-Server-Connection/ProviderURL";
	public static final String KEY_DESKTOP_URL_PKGS = "/ISicres-Configuration/ISicres-DesktopWeb/ISicres-Server-Connection/UrlPkgs";
	public static final String KEY_DESKTOP_USECOMPRESSEDCONTENTS = "/ISicres-Configuration/ISicres-DesktopWeb/UseCompresedGZIPContents";
	public static final String KEY_DESKTOP_ACTIVATEIDIOMSELECT = "/ISicres-Configuration/ISicres-DesktopWeb/ActivateIdiomSelect";
	public static final String KEY_DESKTOP_SHOWUSERINFORMATION = "/ISicres-Configuration/ISicres-DesktopWeb/ShowUserInformation";
	public static final String KEY_DESKTOP_MAXROWSFORVALIDATIONRULES = "/ISicres-Configuration/ISicres-DesktopWeb/MaxRowsForValidationRules";
	public static final String KEY_DESKTOP_TEMPORALDIRECTORYNAME = "/ISicres-Configuration/ISicres-DesktopWeb/TemporalDirectoryName";
	public static final String KEY_DESKTOP_MAXUPLOADFILESIZE = "/ISicres-Configuration/ISicres-DesktopWeb/MaxUploadFileSize";

	public static final String KEY_DESKTOP_ISRELATIVE_DIRECTORYNAMEFORREPORTS = "/ISicres-Configuration/ISicres-DesktopWeb/TemporalRelativeDirectoryNameForReports/@isRelative";
	public static final String KEY_DESKTOP_TEMPORALRELATIVEDIRECTORYNAMEFORREPORTS = "/ISicres-Configuration/ISicres-DesktopWeb/TemporalRelativeDirectoryNameForReports";

	public static final String KEY_DESKTOP_ISRELATIVE_TEMPORALRELATIVEDIRECTORYTEMPLATEFORREPORTS = "/ISicres-Configuration/ISicres-DesktopWeb/TemporalRelativeDirectoryTemplateForReports/@isRelative";
	public static final String KEY_DESKTOP_TEMPORALRELATIVEDIRECTORYTEMPLATEFORREPORTS = "/ISicres-Configuration/ISicres-DesktopWeb/TemporalRelativeDirectoryTemplateForReports";

	public static final String KEY_DESKTOP_USESCHEDULERCLEANERTHREAD = "/ISicres-Configuration/ISicres-DesktopWeb/UseSchedulerCleanerThread";
	public static final String KEY_DESKTOP_REPORTSDATASOURCEJNDINAME = "/ISicres-Configuration/ISicres-DesktopWeb/ReportsDataSourceJNDIName";
	public static final String KEY_DESKTOP_DISTRIBUTION_OFFICE_ASOC = "/ISicres-Configuration/ISicres-DesktopWeb/Distribution/DistributionOficAsoc";

	public static final String KEY_DESKTOP_IDIOMA = "/ISicres-Configuration/ISicres-DesktopWeb/Idioma";
	public static final String KEY_DESKTOP_MAX_REPORT_REGISTERS = "/ISicres-Configuration/ISicres-DesktopWeb/MaxReportRegister";

	public static final String KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR = "/ISicres-Configuration/ISicres-DesktopWeb/TemporalDirectoryName/@isRelative";
	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_CODE = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/AdministrativeUnits/@code";
	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_ABBV = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/AdministrativeUnits/@abbreviation";
	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_NAME = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/AdministrativeUnits/@name";

	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGADMIN_CODE = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/RegistryOrganizations/@code";
	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGADMIN_ABBV = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/RegistryOrganizations/@abbreviation";
	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGADMIN_NAME = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/RegistryOrganizations/@name";

	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGOFF_CODE = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/RegistryOffices/@code";
	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGOFF_ABBV = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/RegistryOffices/@abbreviation";
	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGOFF_NAME = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/RegistryOffices/@name";

	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_SUBJTYPE_CODE = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/SubjectTypes/@code";
	public static final String KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_SUBJTYPE_NAME = "/ISicres-Configuration/ISicres-DesktopWeb/QueryResultsTableRepresentation/SubjectTypes/@name";

	public static final String KEY_DESKTOP_ISRELATIVE_COMPULSA_FONDO_PATH = "/ISicres-Configuration/ISicres-DesktopWeb/Compulsar/FondoPath/@isRelative";
	public static final String KEY_DESKTOP_COMPULSA_FONDO_PATH = "/ISicres-Configuration/ISicres-DesktopWeb/Compulsar/FondoPath";
	public static final String KEY_DESKTOP_ISRELATIVE_COMPULSA_DATOS_PATH = "/ISicres-Configuration/ISicres-DesktopWeb/Compulsar/DatosCompulsaPath/@isRelative";
	public static final String KEY_DESKTOP_COMPULSA_DATOS_PATH = "/ISicres-Configuration/ISicres-DesktopWeb/Compulsar/DatosCompulsaPath";
	public static final String KEY_DESKTOP_COMPULSA_POLICY = "/ISicres-Configuration/ISicres-DesktopWeb/Compulsar/CompulsaPolicy";

	public static final String KEY_DESKTOP_IDOCIMGENABLESAVEAS = "/ISicres-Configuration/ISicres-DesktopWeb/IdocImgEnableSaveAs";

	// Nuevas entradas para la configuracion de la generacion de informes
	public static final String KEY_DESKTOP_REPORTS_DTD_PATH_RELATIVE = "/ISicres-Configuration/ISicres-DesktopWeb/Reports/DtdPath/@isRelative";
	public static final String KEY_DESKTOP_REPORTS_DTD_PATH = "/ISicres-Configuration/ISicres-DesktopWeb/Reports/DtdPath";
	public static final String KEY_DESKTOP_REPORTS_LIB_PATH_RELATIVE = "/ISicres-Configuration/ISicres-DesktopWeb/Reports/LibPath/@isRelative";
	public static final String KEY_DESKTOP_REPORTS_LIB_PATH = "/ISicres-Configuration/ISicres-DesktopWeb/Reports/LibPath";
	public static final String KEY_DESKTOP_REPORTS_JASPERREPORTS_PATH = "/ISicres-Configuration/ISicres-DesktopWeb/Reports/JasperReportsDtd";
	public static final String KEY_DESKTOP_REPORTS_JASPERREPORTS_LIB = "/ISicres-Configuration/ISicres-DesktopWeb/Reports/JasperReportsLib";

	public static final String KEY_DESKTOP_PROVINCIA_POR_DEFECTO = "/ISicres-Configuration/ISicres-DesktopWeb/DefaultProvincia";

	// Configuración de intercambio registral
	public static final String KEY_INTERCAMBIO_ENABLE_INTERCAMBIO_REGISTRAL = "/ISicres-Configuration/ISicres-IntercambioRegistral/EnableIntercambioRegistral";

	// Configuración de los WS
	public static final String KEY_WS_DISTRIBUTION_AUTODISTRIBUTION_REGISTER_IMPORT = "/ISicres-Configuration/ISicres-WS/Distribution/AutoDistributionRegisterImport";
	public static final String KEY_WS_FIRST_COLLECTIONS_INIT_VALUE = "/ISicres-Configuration/ISicres-WS/FirstCollectionsInitValue";
	

}
