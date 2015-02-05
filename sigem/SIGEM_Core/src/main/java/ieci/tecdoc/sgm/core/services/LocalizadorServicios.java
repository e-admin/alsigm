package ieci.tecdoc.sgm.core.services;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.admsesion.administracion.ServicioAdministracionSesionesAdministrador;
import ieci.tecdoc.sgm.core.services.admsesion.backoffice.ServicioAdministracionSesionesBackOffice;
import ieci.tecdoc.sgm.core.services.antivirus.ServicioAntivirus;
import ieci.tecdoc.sgm.core.services.autenticacion.ServicioAutenticacionUsuarios;
import ieci.tecdoc.sgm.core.services.calendario.ServicioCalendario;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catastro.ServicioCatastro;
import ieci.tecdoc.sgm.core.services.certificacion.ServicioCertificacion;
import ieci.tecdoc.sgm.core.services.consolidacion.ServicioConsolidacion;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativa;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativaLdap;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ServicioGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestioncsv.ServicioGestionCSV;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.core.services.pago.ServicioPagoTelematico;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.ServicioPermisosBackOffice;
import ieci.tecdoc.sgm.core.services.publicador.ServicioPublicador;
import ieci.tecdoc.sgm.core.services.registro.ServicioRegistro;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesionadmin.ServicioSesionAdministracion;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;

public class LocalizadorServicios {

	private static final String KEY_AUTH_SERVICE_DEFAULT_IMPL 	= 			"AUTH_SERVICE_DEFAULT_IMPL";
	private static final String DIGITAL_SIGN_SERVICE_DEFAULT_IMPL 	= 		"DIGITAL_SIGN_SERVICE_DEFAULT_IMPL";
	private static final String CRYPTO_VALIDATION_SERVICE_DEFAULT_IMPL = 	"CRYPTO_VALIDATION_SERVICE_DEFAULT_IMPL";
	private static final String PAYMENT_SERVICE_DEFAULT_IMPL =				"PAYMENT_SERVICE_DEFAULT_IMPL";
	private static final String QUERY_EXPS_SERVICE_DEFAULT_IMPL =			"QUERY_EXPS_SERVICE_DEFAULT_IMPL";
	private static final String USER_SESSION_SERVICE_DEFAULT_IMPL =			"USER_SESSION_SERVICE_DEFAULT_IMPL";
	private static final String NOTIFICATIONS_SERVICE_DEFAULT_IMPL =		"NOTIFICATIONS_SERVICE_DEFAULT_IMPL";
	private static final String REPOSITORY_SERVICE_DEFAULT_IMPL =			"REPOSITORY_SERVICE_DEFAULT_IMPL";
	private static final String PROCESSING_SERVICE_DEFAULT_IMPL =			"PROCESSING_SERVICE_DEFAULT_IMPL";
	private static final String THIRDPARTY_SERVICE_DEFAULT_IMPL =			"THIRDPARTY_SERVICE_DEFAULT_IMPL";
	private static final String PROCEDURES_SERVICE_DEFAULT_IMPL =			"PROCEDURES_SERVICE_DEFAULT_IMPL";
	private static final String ESTRUCTURA_ORGANIZATIVA_SERVICE_DEFAULT_IMPL =		"ESTRUCTURA_ORGANIZATIVA_SERVICE_DEFAULT_IMPL";
	private static final String ESTRUCTURA_ORGANIZATIVA_LDAP_SERVICE_DEFAULT_IMPL =		"ESTRUCTURA_ORGANIZATIVA_LDAP_SERVICE_DEFAULT_IMPL";
	private static final String PERMISOS_BACKOFFICE_SERVICE_DEFAULT_IMPL =		"PERMISOS_BACKOFFICE_SERVICE_DEFAULT_IMPL";
	private static final String EREGISTRY_SERVICE_DEFAULT_IMPL =			"EREGISTRY_SERVICE_DEFAULT_IMPL";
	private static final String REGISTRY_SERVICE_DEFAULT_IMPL =				"REGISTRY_SERVICE_DEFAULT_IMPL";
	private static final String CERTIFICATION_SERVICE_DEFAULT_IMPL =		"CERTIFICATION_SERVICE_DEFAULT_IMPL";
	private static final String ADMIN_SESION_SERVICE_DEFAULT_IMPL =			"SESION_ADMINISTRACION_SERVICE_DEFAULT_IMPL";
	private static final String BACK_OFFICE_USER_MANAGER_SERVICE_DEFAULT_IMPL =		"GESTION_USUARIOS_BACK_OFFICE_SERVICE_DEFAULT_IMPL";
	private static final String ENTITIES_SERVICE_DEFAULT_IMPL =			"ENTITIES_SERVICE_DEFAULT_IMPL";
	private static final String RPADMIN_SERVICE_DEFAULT_IMPL =			"RPADMIN_SERVICE_DEFAULT_IMPL";	
	private static final String ADM_USER_MANAGER_SERVICE_DEFAULT_IMPL =		"GESTION_USUARIOS_ADM_SERVICE_DEFAULT_IMPL";
	private static final String APP_ADMIN_SERVICE_DEFAULT_IMPL = "APP_ADMIN_SERVICE_DEFAULT_IMPL";
	private static final String CATASTRO_SERVICE_DEFAULT_IMPL =				"CATASTRO_SERVICE_DEFAULT_IMPL";
	private static final String GEOLOCALIZACION_SERVICE_DEFAULT_IMPL =				"GEOLOCALIZACION_SERVICE_DEFAULT_IMPL";
	private static final String ANTIVIRUS_SERVICE_DEFAULT_IMPL =				"ANTIVIRUS_SERVICE_DEFAULT_IMPL";
	private static final String PUBLISHER_SERVICE_DEFAULT_IMPL =				"PUBLISHER_SERVICE_DEFAULT_IMPL";
	private static final String ADM_SESION_ADM_SERVICE_DEFAULT_IMPL =			"ADM_SESION_ADM_SERVICE_DEFAULT_IMPL";
	private static final String ADM_SESION_BKOFF_SERVICE_DEFAULT_IMPL =			"ADM_SESION_BKOFF_SERVICE_DEFAULT_IMPL";
	private static final String CALENDARIO_SERVICE_DEFAULT_IMPL =				"CALENDARIO_SERVICE_DEFAULT_IMPL";
	private static final String SHORT_MESSAGES_SERVICE_DEFAULT_IMPL =			"SHORT_MESSAGES_SERVICE_DEFAULT_IMPL";
	private static final String CONSOLIDATION_SERVICE_DEFAULT_IMPL =			"CONSOLIDATION_SERVICE_DEFAULT_IMPL";
	private static final String TIEMPOS_SERVICE_DEFAULT_IMPL =				"TIEMPOS_SERVICE_DEFAULT_IMPL";
	private static final String GESTION_CSV_SERVICE_DEFAULT_IMPL=			"GESTION_CSV_SERVICE_DEFAULT_IMPL";

	public static Object getServicio(String psImplName) throws SigemException{
		return getServicio(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static Object getServicio(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			Object oService = null;
			try {
				oService = poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	public static ServicioAdministracionSesionesBackOffice getServicioAdministracionSesionesBackOffice() throws SigemException{
		return getServicioAdministracionSesionesBackOffice(DefaultConfiguration.getConfiguration(), ADM_SESION_BKOFF_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioAdministracionSesionesBackOffice getServicioAdministracionSesionesBackOffice(String psImplName) throws SigemException{
		return getServicioAdministracionSesionesBackOffice(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioAdministracionSesionesBackOffice getServicioAdministracionSesionesBackOffice(Config poConfig) throws SigemException{
		return getServicioAdministracionSesionesBackOffice(poConfig, ADM_SESION_BKOFF_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioAdministracionSesionesBackOffice getServicioAdministracionSesionesBackOffice(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioAdministracionSesionesBackOffice oService = null;
			try {
				oService = (ServicioAdministracionSesionesBackOffice)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	public static ServicioAdministracionSesionesAdministrador getServicioAdministracionSesionesAdministrador() throws SigemException{
		return getServicioAdministracionSesionesAdministrador(DefaultConfiguration.getConfiguration(), ADM_SESION_ADM_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioAdministracionSesionesAdministrador getServicioAdministracionSesionesAdministrador(String psImplName) throws SigemException{
		return getServicioAdministracionSesionesAdministrador(DefaultConfiguration.getConfiguration(), psImplName);
	}
	
	public static ServicioAdministracionSesionesAdministrador getServicioAdministracionSesionesAdministrador(Config poConfig) throws SigemException{
		return getServicioAdministracionSesionesAdministrador(poConfig, ADM_SESION_ADM_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioAdministracionSesionesAdministrador getServicioAdministracionSesionesAdministrador(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioAdministracionSesionesAdministrador oService = null;
			try {
				oService = (ServicioAdministracionSesionesAdministrador)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	public static ServicioAutenticacionUsuarios getServicioAutenticacionUsuarios() throws SigemException{
		return getServicioAutenticacionUsuarios(DefaultConfiguration.getConfiguration(), KEY_AUTH_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioAutenticacionUsuarios getServicioAutenticacionUsuarios(String psImplName) throws SigemException{
		return getServicioAutenticacionUsuarios(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioAutenticacionUsuarios getServicioAutenticacionUsuarios(Config poConfig) throws SigemException{
		return getServicioAutenticacionUsuarios(poConfig, KEY_AUTH_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioAutenticacionUsuarios getServicioAutenticacionUsuarios(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioAutenticacionUsuarios oService = null;
			try {
				oService = (ServicioAutenticacionUsuarios)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	public static ServicioFirmaDigital getServicioFirmaDigital() throws SigemException{
		return getServicioFirmaDigital(DefaultConfiguration.getConfiguration(), DIGITAL_SIGN_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioFirmaDigital getServicioFirmaDigital(String psImplName) throws SigemException{
		return getServicioFirmaDigital(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioFirmaDigital getServicioFirmaDigital(Config poConfig) throws SigemException{
		return getServicioFirmaDigital(poConfig, DIGITAL_SIGN_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioFirmaDigital getServicioFirmaDigital(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioFirmaDigital oService = null;
			try {
				oService = (ServicioFirmaDigital)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	public static ServicioCriptoValidacion getServicioCriptoValidacion() throws SigemException{
		return getServicioCriptoValidacion(DefaultConfiguration.getConfiguration(), CRYPTO_VALIDATION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCriptoValidacion getServicioCriptoValidacion(String psImplName) throws SigemException{
		return getServicioCriptoValidacion(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioCriptoValidacion getServicioCriptoValidacion(Config poConfig) throws SigemException{
		return getServicioCriptoValidacion(poConfig, CRYPTO_VALIDATION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCriptoValidacion getServicioCriptoValidacion(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioCriptoValidacion oService = null;
			try {
				oService = (ServicioCriptoValidacion)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	public static ServicioPagoTelematico getServicioPagoTelematico() throws SigemException{
		return getServicioPagoTelematico(DefaultConfiguration.getConfiguration(), PAYMENT_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioPagoTelematico getServicioPagoTelematico(String psImplName) throws SigemException{
		return getServicioPagoTelematico(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioPagoTelematico getServicioPagoTelematico(Config poConfig) throws SigemException{
		return getServicioPagoTelematico(poConfig, PAYMENT_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioPagoTelematico getServicioPagoTelematico(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioPagoTelematico oService = null;
			try {
				oService = (ServicioPagoTelematico)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	public static ServicioConsultaExpedientes getServicioConsultaExpedientes() throws SigemException{
		return getServicioConsultaExpedientes(DefaultConfiguration.getConfiguration(), QUERY_EXPS_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioConsultaExpedientes getServicioConsultaExpedientes(String psImplName) throws SigemException{
		return getServicioConsultaExpedientes(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioConsultaExpedientes getServicioConsultaExpedientes(Config poConfig) throws SigemException{
		return getServicioConsultaExpedientes(poConfig, QUERY_EXPS_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioConsultaExpedientes getServicioConsultaExpedientes(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioConsultaExpedientes oService = null;
			try {
				oService = (ServicioConsultaExpedientes)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	
	public static ServicioSesionUsuario getServicioSesionUsuario() throws SigemException{
		return getServicioSesionUsuario(DefaultConfiguration.getConfiguration(), USER_SESSION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioSesionUsuario getServicioSesionUsuario(String psImplName) throws SigemException{
		return getServicioSesionUsuario(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioSesionUsuario getServicioSesionUsuario(Config poConfig) throws SigemException{
		return getServicioSesionUsuario(poConfig, USER_SESSION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioSesionUsuario getServicioSesionUsuario(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioSesionUsuario oService = null;
			try {
				oService = (ServicioSesionUsuario)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	public static ServicioNotificaciones getServicioNotificaciones() throws SigemException{
		return getServicioNotificaciones(DefaultConfiguration.getConfiguration(), NOTIFICATIONS_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioNotificaciones getServicioNotificaciones(String psImplName) throws SigemException{
		return getServicioNotificaciones(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioNotificaciones getServicioNotificaciones(Config poConfig) throws SigemException{
		return getServicioNotificaciones(poConfig, NOTIFICATIONS_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioNotificaciones getServicioNotificaciones(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioNotificaciones oService = null;
			try {
				oService = (ServicioNotificaciones)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	public static ServicioRepositorioDocumentosTramitacion getServicioRepositorioDocumentosTramitacion() throws SigemException{
		return getServicioRepositorioDocumentosTramitacion(DefaultConfiguration.getConfiguration(), REPOSITORY_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioRepositorioDocumentosTramitacion getServicioRepositorioDocumentosTramitacion(String psImplName) throws SigemException{
		return getServicioRepositorioDocumentosTramitacion(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioRepositorioDocumentosTramitacion getServicioServicioRepositorioDocumentosTramitacion(Config poConfig) throws SigemException{
		return getServicioRepositorioDocumentosTramitacion(poConfig, REPOSITORY_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioRepositorioDocumentosTramitacion getServicioRepositorioDocumentosTramitacion(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioRepositorioDocumentosTramitacion oService = null;
			try {
				oService = (ServicioRepositorioDocumentosTramitacion)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	/*
	 * =======================================================================
	 * Métodos de acceso al Servicio de Publicador.
	 * =======================================================================
	 */
	
	/**
	 * Obtiene el servicio de publicador.
	 * @return Servicio del Publicador.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioPublicador getServicioPublicador() 
			throws SigemException {
		return getServicioPublicador(DefaultConfiguration.getConfiguration(), 
				PUBLISHER_SERVICE_DEFAULT_IMPL);
	}
	
	/**
	 * Obtiene el servicio del publicador.
	 * @param psImplName Nombre de la implementación del servicio.
	 * @return Servicio del Publicador.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioPublicador getServicioPublicador(String psImplName) 
			throws SigemException {
		return getServicioPublicador(DefaultConfiguration.getConfiguration(), 
				psImplName);
	}
	
	/**
	 * Obtiene el servicio de Publicador.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @return Servicio del publicador.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioPublicador getServicioPublicador(Config poConfig) 
			throws SigemException {
		return getServicioPublicador(poConfig, PUBLISHER_SERVICE_DEFAULT_IMPL);
	}
	
	
	
	/**
	 * Obtiene el servicio del pubicador.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @param pcImpl Nombre de la implementación del servicio.
	 * @return Servicio del publicador.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioPublicador getServicioPublicador(Config poConfig, 
			String pcImpl) throws SigemException {
		
		if(poConfig != null) {
			ServicioPublicador oService = null;
			try {
				oService = (ServicioPublicador) poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		} else {
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	
	/*
	 * =======================================================================
	 * Métodos de acceso al Servicio de Tramitación.
	 * =======================================================================
	 */
	
	/**
	 * Obtiene el servicio de Tramitación.
	 * @return Servicio de Tramitación.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTramitacion getServicioTramitacion() 
			throws SigemException {
		return getServicioTramitacion(DefaultConfiguration.getConfiguration(), 
				PROCESSING_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Tramitación.
	 * @param psImplName Nombre de la implementación del servicio.
	 * @return Servicio de Tramitación.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTramitacion getServicioTramitacion(String psImplName) 
			throws SigemException {
		return getServicioTramitacion(DefaultConfiguration.getConfiguration(), 
				psImplName);
	}

	/**
	 * Obtiene el servicio de Tramitación.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @return Servicio de Tramitación.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTramitacion getServicioTramitacion(Config poConfig) 
			throws SigemException {
		return getServicioTramitacion(poConfig, PROCESSING_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Tramitación.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @param pcImpl Nombre de la implementación del servicio.
	 * @return Servicio de Tramitación.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTramitacion getServicioTramitacion(Config poConfig, 
			String pcImpl) throws SigemException {
		
		if(poConfig != null) {
			ServicioTramitacion oService = null;
			try {
				oService = (ServicioTramitacion) poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		} else {
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	/*
	 * =======================================================================
	 * Métodos de acceso al Servicio de Terceros.
	 * =======================================================================
	 */
	
	/**
	 * Obtiene el servicio de Terceros.
	 * @return Servicio de Terceros.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTerceros getServicioTerceros() 
			throws SigemException {
		return getServicioTerceros(DefaultConfiguration.getConfiguration(), 
				THIRDPARTY_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Terceros.
	 * @param psImplName Nombre de la implementación del servicio.
	 * @return Servicio de Terceros.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTerceros getServicioTerceros(String psImplName) 
			throws SigemException {
		return getServicioTerceros(DefaultConfiguration.getConfiguration(), 
				psImplName);
	}

	/**
	 * Obtiene el servicio de Terceros.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @return Servicio de Terceros.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTerceros getServicioTerceros(Config poConfig) 
			throws SigemException {
		return getServicioTerceros(poConfig, THIRDPARTY_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Terceros.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @param pcImpl Nombre de la implementación del servicio.
	 * @return Servicio de Terceros.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTerceros getServicioTerceros(Config poConfig, 
			String pcImpl) throws SigemException {
		
		if(poConfig != null) {
			ServicioTerceros oService = null;
			try {
				oService = (ServicioTerceros) poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		} else {
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	public static ServicioCatalogoTramites getServicioCatalogoTramites() throws SigemException{
		return getServicioCatalogoTramites(DefaultConfiguration.getConfiguration(), PROCEDURES_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCatalogoTramites getServicioCatalogoTramites(String psImplName) throws SigemException{
		return getServicioCatalogoTramites(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioCatalogoTramites getServicioCatalogoTramites(Config poConfig) throws SigemException{
		return getServicioCatalogoTramites(poConfig, PROCEDURES_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCatalogoTramites getServicioCatalogoTramites(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioCatalogoTramites oService = null;
			try {
				oService = (ServicioCatalogoTramites)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	public static ServicioEstructuraOrganizativa getServicioEstructuraOrganizativa() throws SigemException{
		return getServicioEstructuraOrganizativa(DefaultConfiguration.getConfiguration(), ESTRUCTURA_ORGANIZATIVA_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioEstructuraOrganizativa getServicioEstructuraOrganizativa(String psImplName) throws SigemException{
		return getServicioEstructuraOrganizativa(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioEstructuraOrganizativa getServicioEstructuraOrganizativa(Config poConfig) throws SigemException{
		return getServicioEstructuraOrganizativa(poConfig, ESTRUCTURA_ORGANIZATIVA_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioEstructuraOrganizativa getServicioEstructuraOrganizativa(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioEstructuraOrganizativa oService = null;
			try {
				oService = (ServicioEstructuraOrganizativa)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	public static ServicioEstructuraOrganizativaLdap getServicioEstructuraOrganizativaLdap() throws SigemException{
		return getServicioEstructuraOrganizativaLdap(DefaultConfiguration.getConfiguration(), ESTRUCTURA_ORGANIZATIVA_LDAP_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioEstructuraOrganizativaLdap getServicioEstructuraOrganizativaLdap(String psImplName) throws SigemException{
		return getServicioEstructuraOrganizativaLdap(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioEstructuraOrganizativaLdap getServicioEstructuraOrganizativaLdap(Config poConfig) throws SigemException{
		return getServicioEstructuraOrganizativaLdap(poConfig, ESTRUCTURA_ORGANIZATIVA_LDAP_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioEstructuraOrganizativaLdap getServicioEstructuraOrganizativaLdap(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioEstructuraOrganizativaLdap oService = null;
			try {
				oService = (ServicioEstructuraOrganizativaLdap)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	
	public static ServicioPermisosBackOffice getServicioPermisosBackOffice() throws SigemException{
		return getServicioPermisosBackOffice(DefaultConfiguration.getConfiguration(), PERMISOS_BACKOFFICE_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioPermisosBackOffice getServicioPermisosBackOffice(String psImplName) throws SigemException{
		return getServicioPermisosBackOffice(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioPermisosBackOffice getServicioPermisosBackOffice(Config poConfig) throws SigemException{
		return getServicioPermisosBackOffice(poConfig, PERMISOS_BACKOFFICE_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioPermisosBackOffice getServicioPermisosBackOffice(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioPermisosBackOffice oService = null;
			try {
				oService = (ServicioPermisosBackOffice)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}	
	

	public static ServicioRegistro getServicioRegistro() throws SigemException{
		return getServicioRegistro(DefaultConfiguration.getConfiguration(), REGISTRY_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioRegistro getServicioRegistro(String psImplName) throws SigemException{
		return getServicioRegistro(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioRegistro getServicioRegistro(Config poConfig) throws SigemException{
		return getServicioRegistro(poConfig, REGISTRY_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioRegistro getServicioRegistro(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioRegistro oService = null;
			try {
				oService = (ServicioRegistro)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}


	public static ServicioRegistroTelematico getServicioRegistroTelematico() throws SigemException{
		return getServicioRegistroTelematico(DefaultConfiguration.getConfiguration(), EREGISTRY_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioRegistroTelematico getServicioRegistroTelematico(String psImplName) throws SigemException{
		return getServicioRegistroTelematico(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioRegistroTelematico getServicioRegistroTelematico(Config poConfig) throws SigemException{
		return getServicioRegistroTelematico(poConfig, EREGISTRY_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioRegistroTelematico getServicioRegistroTelematico(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioRegistroTelematico oService = null;
			try {
				oService = (ServicioRegistroTelematico)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	
	public static ServicioCertificacion getServicioCertificacion() throws SigemException{
		return getServicioCertificacion(DefaultConfiguration.getConfiguration(), CERTIFICATION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCertificacion getServicioCertificacion(String psImplName) throws SigemException{
		return getServicioCertificacion(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioCertificacion getServicioCertificacion(Config poConfig) throws SigemException{
		return getServicioCertificacion(poConfig, CERTIFICATION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCertificacion getServicioCertificacion(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioCertificacion oService = null;
			try {
				oService = (ServicioCertificacion)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	
	public static ServicioSesionAdministracion getServicioSesionAdministracion() throws SigemException{
		return getServicioSesionAdministracion(DefaultConfiguration.getConfiguration(), ADMIN_SESION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioSesionAdministracion getServicioSesionAdministracion(String psImplName) throws SigemException{
		return getServicioSesionAdministracion(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioSesionAdministracion getServicioSesionAdministracion(Config poConfig) throws SigemException{
		return getServicioSesionAdministracion(poConfig, ADMIN_SESION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioSesionAdministracion getServicioSesionAdministracion(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioSesionAdministracion oService = null;
			try {
				oService = (ServicioSesionAdministracion)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}	
	
	
	
	
	
	
	public static ServicioGestionUsuariosBackOffice getServicioAutenticacionUsuariosBackOffice() throws SigemException{
		return getServicioAutenticacionUsuariosBackOffice(DefaultConfiguration.getConfiguration(), BACK_OFFICE_USER_MANAGER_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioGestionUsuariosBackOffice getServicioAutenticacionUsuariosBackOffice(String psImplName) throws SigemException{
		return getServicioAutenticacionUsuariosBackOffice(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioGestionUsuariosBackOffice getServicioAutenticacionUsuariosBackOffice(Config poConfig) throws SigemException{
		return getServicioAutenticacionUsuariosBackOffice(poConfig, BACK_OFFICE_USER_MANAGER_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioGestionUsuariosBackOffice getServicioAutenticacionUsuariosBackOffice(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioGestionUsuariosBackOffice oService = null;
			try {
				oService = (ServicioGestionUsuariosBackOffice)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}	




	public static ServicioEntidades getServicioEntidades() throws SigemException{
		return getServicioEntidades(DefaultConfiguration.getConfiguration(), ENTITIES_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioEntidades getServicioEntidades(String psImplName) throws SigemException{
		return getServicioEntidades(DefaultConfiguration.getConfiguration(), psImplName);
	}
	
	public static ServicioEntidades getServicioEntidades(Config poConfig) throws SigemException{
		return getServicioEntidades(poConfig, ENTITIES_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioEntidades getServicioEntidades(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioEntidades oService = null;
			try {
				oService = (ServicioEntidades)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}	

	public static ServicioRPAdmin getServicioRPAdmin() throws SigemException{
		return getServicioRPAdmin(DefaultConfiguration.getConfiguration(), RPADMIN_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioRPAdmin getServicioRPAdmin(String psImplName) throws SigemException{
		return getServicioRPAdmin(DefaultConfiguration.getConfiguration(), psImplName);
	}
	
	public static ServicioRPAdmin getServicioRPAdmin(Config poConfig) throws SigemException{
		return getServicioRPAdmin(poConfig, RPADMIN_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioRPAdmin getServicioRPAdmin(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioRPAdmin oService = null;
			try {
				oService = (ServicioRPAdmin)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}




	public static ServicioGestionUsuariosAdministracion getServicioAutenticacionUsuariosAdministracion() throws SigemException{
		return getServicioAutenticacionUsuariosAdministracion(DefaultConfiguration.getConfiguration(), ADM_USER_MANAGER_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioGestionUsuariosAdministracion getServicioAutenticacionUsuariosAdministracion(String psImplName) throws SigemException{
		return getServicioAutenticacionUsuariosAdministracion(DefaultConfiguration.getConfiguration(), psImplName);
	}
	
	public static ServicioGestionUsuariosAdministracion getServicioAutenticacionUsuariosAdministracion(Config poConfig) throws SigemException{
		return getServicioAutenticacionUsuariosAdministracion(poConfig, ADM_USER_MANAGER_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioGestionUsuariosAdministracion getServicioAutenticacionUsuariosAdministracion(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioGestionUsuariosAdministracion oService = null;
			try {
				oService = (ServicioGestionUsuariosAdministracion)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}	

	public static ServicioAdministracion getServicioAdministracion() throws SigemException{
		return getServicioAdministracion(DefaultConfiguration.getConfiguration(), APP_ADMIN_SERVICE_DEFAULT_IMPL);
	}
	
	public static ServicioAdministracion getServicioAdministracion(Config poConfig) throws SigemException{
		return getServicioAdministracion(poConfig, APP_ADMIN_SERVICE_DEFAULT_IMPL);
	}

	
	public static ServicioAdministracion getServicioAdministracion(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioAdministracion oService = null;
			try {
				oService = (ServicioAdministracion)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	public static ServicioGeoLocalizacion getServicioGeoLocalizacion() throws SigemException{
		return getServicioGeoLocalizacion(DefaultConfiguration.getConfiguration(), GEOLOCALIZACION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioGeoLocalizacion getServicioGeoLocalizacion(String psImplName) throws SigemException{
		return getServicioGeoLocalizacion(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioGeoLocalizacion getServicioGeoLocalizacion(Config poConfig) throws SigemException{
		return getServicioGeoLocalizacion(poConfig, GEOLOCALIZACION_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioGeoLocalizacion getServicioGeoLocalizacion(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioGeoLocalizacion oService = null;
			try {
				oService = (ServicioGeoLocalizacion)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	public static ServicioCatastro getServicioCatastro() throws SigemException{
		return getServicioCatastro(DefaultConfiguration.getConfiguration(), CATASTRO_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCatastro getServicioCatastro(String psImplName) throws SigemException{
		return getServicioCatastro(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioCatastro getServicioCatastro(Config poConfig) throws SigemException{
		return getServicioCatastro(poConfig, CATASTRO_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCatastro getServicioCatastro(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioCatastro oService = null;
			try {
				oService = (ServicioCatastro)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	

	public static ServicioAntivirus getServicioAntivirus() throws SigemException{
		return getServicioAntivirus(DefaultConfiguration.getConfiguration(), ANTIVIRUS_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioAntivirus getServicioAntivirus(String psImplName) throws SigemException{
		return getServicioAntivirus(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioAntivirus getServicioAntivirus(Config poConfig) throws SigemException{
		return getServicioAntivirus(poConfig, ANTIVIRUS_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioAntivirus getServicioAntivirus(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioAntivirus oService = null;
			try {
				oService = (ServicioAntivirus)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	public static ServicioCalendario getServicioCalendario() throws SigemException{
		return getServicioCalendario(DefaultConfiguration.getConfiguration(), CALENDARIO_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCalendario getServicioCalendario(String psImplName) throws SigemException{
		return getServicioCalendario(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static ServicioCalendario getServicioCalendario(Config poConfig) throws SigemException{
		return getServicioCalendario(poConfig, CALENDARIO_SERVICE_DEFAULT_IMPL);
	}

	public static ServicioCalendario getServicioCalendario(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null){
			ServicioCalendario oService = null;
			try {
				oService = (ServicioCalendario)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}


	/*
	 * =======================================================================
	 * Métodos de acceso al Servicio de Mensajes Cortos.
	 * =======================================================================
	 */
	
	/**
	 * Obtiene el servicio de Mensajes Cortos.
	 * @return Servicio de Mensajes Cortos.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioMensajesCortos getServicioMensajesCortos() 
			throws SigemException {
		return getServicioMensajesCortos(DefaultConfiguration.getConfiguration(), 
				SHORT_MESSAGES_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Mensajes Cortos.
	 * @param psImplName Nombre de la implementación del servicio.
	 * @return Servicio de Mensajes Cortos.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioMensajesCortos getServicioMensajesCortos(String psImplName) 
			throws SigemException {
		return getServicioMensajesCortos(DefaultConfiguration.getConfiguration(), 
				psImplName);
	}

	/**
	 * Obtiene el servicio de Mensajes Cortos.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @return Servicio de Mensajes Cortos.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioMensajesCortos getServicioMensajesCortos(Config poConfig) 
			throws SigemException {
		return getServicioMensajesCortos(poConfig, SHORT_MESSAGES_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Mensajes Cortos.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @param pcImpl Nombre de la implementación del servicio.
	 * @return Servicio de Mensajes Cortos.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioMensajesCortos getServicioMensajesCortos(Config poConfig, 
			String pcImpl) throws SigemException {
		
		if(poConfig != null) {
			ServicioMensajesCortos oService = null;
			try {
				oService = (ServicioMensajesCortos) poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		} else {
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	/*
	 * =======================================================================
	 * Métodos de acceso al Servicio de Consolidacion.
	 * =======================================================================
	 */
	
	/**
	 * Obtiene el servicio de Consolidación.
	 * @return Servicio de Consolidación.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioConsolidacion getServicioConsolidacion() 
			throws SigemException {
		return getServicioConsolidacion(DefaultConfiguration.getConfiguration(), 
				CONSOLIDATION_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Consolidación.
	 * @param psImplName Nombre de la implementación del servicio.
	 * @return Servicio de Consolidación.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioConsolidacion getServicioConsolidacion(String psImplName) 
			throws SigemException {
		return getServicioConsolidacion(DefaultConfiguration.getConfiguration(), 
				psImplName);
	}

	/**
	 * Obtiene el servicio de Consolidación.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @return Servicio de Consolidación.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioConsolidacion getServicioConsolidacion(Config poConfig) 
			throws SigemException {
		return getServicioConsolidacion(poConfig, CONSOLIDATION_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Consolidación.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @param pcImpl Nombre de la implementación del servicio.
	 * @return Servicio de Consolidación.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioConsolidacion getServicioConsolidacion(Config poConfig, 
			String pcImpl) throws SigemException {
		
		if(poConfig != null) {
			ServicioConsolidacion oService = null;
			try {
				oService = (ServicioConsolidacion) poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		} else {
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	
	
	/*
	 * =======================================================================
	 * Métodos de acceso al Servicio de TIEMPOS
	 * =======================================================================
	 */
	
	/**
	 * Obtiene el servicio de Tiempos.
	 * @return Servicio de Tiempos.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTiempos getServicioTiempos() 
			throws SigemException {
		return getServicioTiempos(DefaultConfiguration.getConfiguration(), 
				TIEMPOS_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Tiempos.
	 * @param psImplName Nombre de la implementación del servicio.
	 * @return Servicio de Tiempos.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTiempos getServicioTiempos(String psImplName) 
			throws SigemException {
		return getServicioTiempos(DefaultConfiguration.getConfiguration(), 
				psImplName);
	}

	/**
	 * Obtiene el servicio de Tiempos.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @return Servicio de Tiempos.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTiempos getServicioTiempos(Config poConfig) 
			throws SigemException {
		return getServicioTiempos(poConfig, TIEMPOS_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de Tiempos.
	 * @param poConfig Configuración de la factoría de servicios.
	 * @param pcImpl Nombre de la implementación del servicio.
	 * @return Servicio de Tiempos.
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioTiempos getServicioTiempos(Config poConfig, 
			String pcImpl) throws SigemException {
		
		if(poConfig != null) {
			ServicioTiempos oService = null;
			try {
				oService = (ServicioTiempos) poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		} else {
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
	/*
	 * Métodos de acceso al servicio de gestión de CSV 
	 */
	
	/**
	 * Obtiene el servicio de gestión de CSV
	 * @return Servicio de gestión de CSV
	 * @throws SigemException Si ocurre algún error
	 */
	public static ServicioGestionCSV getServicioGestionCSV() throws SigemException{
		return getServicioGestionCSV(DefaultConfiguration.getConfiguration(), 
				GESTION_CSV_SERVICE_DEFAULT_IMPL);
	}
	
	/**
	 * Obtiene el servicio de gestión de CSV
	 * @param psImplName Nombre de la implementación del servicio.
	 * @return Servicio de gestión de CSV
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioGestionCSV getServicioGestionCSV(String psImplName) 
			throws SigemException {
		return getServicioGestionCSV(DefaultConfiguration.getConfiguration(), 
				psImplName);
	}
	
	/**
	 * Obtiene el servicio de gestión de CSV
	 * 
	 * @param poConfig Configuración de la factoría de servicios.
	 * @return Servicio de gestión de CSV
	 * @throws SigemException si ocurre algún error.
	 */
	public static ServicioGestionCSV getServicioGestionCSV(Config poConfig) 
			throws SigemException {
		return getServicioGestionCSV(poConfig, GESTION_CSV_SERVICE_DEFAULT_IMPL);
	}

	/**
	 * Obtiene el servicio de gestión de CSV
	 * 
	 * @param poConfig Configuración de la factoría de servicios.
	 * @param pcImpl Nombre de la implementación del servicio.
	 * @return Servicio de gestión de CSV
	 * @throws SigemException Si ocurre algún error
	 */
	public static ServicioGestionCSV getServicioGestionCSV(Config poConfig, String pcImpl) throws SigemException{
		if(poConfig != null) {
			ServicioGestionCSV oService = null;
			try {
				oService = (ServicioGestionCSV) poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		} else {
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
	
}
