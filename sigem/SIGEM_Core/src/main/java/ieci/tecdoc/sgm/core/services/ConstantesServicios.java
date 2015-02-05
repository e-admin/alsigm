package ieci.tecdoc.sgm.core.services;

public final class ConstantesServicios {

	/**
	 * Constantes para la invocación de servicios.
	 */
	public static final String SERVICE_RETURN_OK				= "OK";
	public static final String SERVICE_RETURN_ERROR				= "ERROR";
	
	public static final String SERVICE_RETURN_UNKNOWN_ERROR		= "00000000";
	
	/**
	 * Constantes para nombres de servicio
	 */
	public static final String SERVICE_USER_AUTHENTICATION		=  "SIGEM_ServicioAutenticacion";
	public static final String SERVICE_DIGITAL_SIGN				=  "SIGEM_ServicioFirmaElectronica";
	public static final String SERVICE_CRYPTO_VALIDATION		=  "SIGEM_ServicioValidacion";
	public static final String SERVICE_PAYMENT					=  "SIGEM_ServicioPagoTelematico";
	public static final String SERVICE_QUERY_EXPS				=  "SIGEM_ServicioConsultaExpedientes";
	public static final String SERVICE_USER_SESSION				=  "SIGEM_ServicioSesionUsuario";
	public static final String SERVICE_NOTIFICATIONS			=  "SIGEM_ServicioNotificaciones";
	public static final String SERVICE_REPOSITORY				=  "SIGEM_ServicioRepositorioDocumentosTramitacion";
	public static final String SERVICE_PROCESSING				=  "SIGEM_ServicioTramitacion";
	public static final String SERVICE_THIRDPARTY				=  "SIGEM_ServicioTerceros";
	public static final String SERVICE_REGISTRY					=  "SIGEM_RegistroPresencial";
	public static final String SERVICE_PROCEDURES				=  "SIGEM_ServicioCatalogoTramites";	
	public static final String SERVICE_EREGISTRY				=  "SIGEM_ServicioRegistroTelematico";
	public static final String SERVICE_ORGANISMS				=  "SIGEM_ServicioOrganismos";
	public static final String SERVICE_CERTIFICATION			=  "SIGEM_ServicioCertificacion";
	public static final String SERVICE_ADMIN_SESION				=  "SIGEM_SesionAdministracion";
	public static final String SERVICE_BACK_OFFICE_USER_MANAGER	=  "SIGEM_GestionUsuariosBackOffice";
	public static final String SERVICE_ENTITIES 				=  "SIGEM_Entidades";
	public static final String SERVICE_ADMIN_MANAGER			=  "SIGEM_GestionAdministracion";
	public static final String SERVICE_CATASTRO					=  "SIGEM_ServicioCatastro";
	public static final String SERVICE_GEOLOCALIZACION			=  "SIGEM_ServicioGeoLocalizacion";
	public static final String SERVICE_ESTRUCTURA_ORGANIZATIVA	=  "SIGEM_ServicioEstructuraOrganizativa";
	public static final String SERVICE_PERMISOS_USUARIOS_BACK_OFFICE =  "SIGEM_ServicioPermisosUsuarioBackOffice";
	public static final String SERVICE_ANTIVIRUS				=  "SIGEM_ServicioAntivirus";
	public static final String SERVICE_PUBLISHER				=  "SIGEM_Publicador";
	public static final String SERVICE_SHORT_MESSAGES			=  "SIGEM_MensajesCortos";
	public static final String SERVICE_CALENDAR					=  "SIGEM_ServicioCalendario";
	public static final String CONSOLIDATION_MESSAGES			=  "SIGEM_Consolidacion";
	public static final String SERVICE_TIMES					=  "SIGEM_Tiempos";
	public static final String SERVICE_GESTION_CSV				=  "SIGEM_ServicioGestionCSV";
	
	/**
	 * Constantes para codigos de error
	 * Código de error: XXXXXXXXXXXX
	 * (2)Prefijo servicio + (8)Número de error;
	 */
	public static final int SIGEM_ERROR_PREFIX_LENGTH							=	2;
	public static final int SIGEM_ERROR_CODE_LENGTH								=	8;
	public static final String SIGEM_ERROR_DEFAULT_MASK_ELEMENT					= 	"0";
	public static final String SIGEM_ERROR_PREFIX								=	"00";
	
	public static final String SERVICE_USER_AUTHENTICATION_ERROR_PREFIX			=	"10";
	public static final String SERVICE_DIGITAL_SIGN_ERROR_PREFIX				=	"11";	
	public static final String SERVICE_CRYPTO_VALIDATION_ERROR_PREFIX			=	"12";
	public static final String SERVICE_PAYMENT_ERROR_PREFIX						=	"13";
	public static final String SERVICE_QUERY_EXPS_ERROR_PREFIX					=	"14";
	public static final String SERVICE_USER_SESSION_ERROR_PREFIX				=	"150";
	public static final String SERVICE_NOTIFICATIONS_ERROR_PREFIX				=	"16";
	public static final String SERVICE_REPOSITORY_ERROR_PREFIX					=	"17";	
	public static final String SERVICE_PROCESSING_ERROR_PREFIX					=	"18";
	public static final String SERVICE_THIRDPARTY_ERROR_PREFIX					=	"19";
	public static final String SERVICE_PROCEDURES_ERROR_PREFIX					=	"20";
	public static final String SERVICE_REGISTRY_ERROR_PREFIX					=	"21";
	public static final String SERVICE_EREGISTRY_ERROR_PREFIX					=	"9";
	public static final String SERVICE_ORGANISM_ERROR_PREFIX					=	"8";
	public static final String SERVICE_CERTIFICATION_ERROR_PREFIX				=	"7";
	public static final String SERVICE_BACK_OFFICE_USER_MANAGER_ERROR_PREFIX	=	"25";
	public static final String SERVICE_ENTITIES_ERROR_PREFIX					=	"26";
	public static final String SERVICE_ADMINISTRATION_ERROR_PREFIX				=	"27";
	public static final String SERVICE_ADM_MANAGER_ERROR_PREFIX					=   "28";
	public static final String SERVICE_CATASTRO_ERROR_PREFIX					=	"22";
	public static final String SERVICE_GEOLOCALIZACION_ERROR_PREFIX				=	"23";
	public static final String SERVICE_ANTIVIRUS_ERROR_PREFIX					=	"23";
	public static final String SERVICE_PUBLISHER_ERROR_PREFIX					=	"29";
	public static final String SERVICE_SHORT_MESSAGES_ERROR_PREFIX				=	"30";
	public static final String SERVICE_CALENDAR_ERROR_PREFIX					=	"23";
	public static final String CONSOLIDATION_MESSAGES_ERROR_PREFIX				=	"31";
	public static final String TIEMPOS_MESSAGES_ERROR_PREFIX					=	"32";
	

	/**
	 * Implementaciones específicas de servicios.
	 */
	public static final String LOCAL_SERVICE_DIGITAL_SIGN_AFIRMA_IMPL			=	"SIGEM_ServicioFirmaElectronica.AFIRMA.API";
	
	/**
	 * constantes de uso general
	 */
	public static final String LABEL_TRUE		=	"true";
	public static final String LABEL_FALSE		=	"false";
	
	public static final String DATE_PATTERN 	=	"dd/MM/yy HH:mm:ss";
}
