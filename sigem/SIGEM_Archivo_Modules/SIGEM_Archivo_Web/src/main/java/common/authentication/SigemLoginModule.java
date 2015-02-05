package common.authentication;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import java.io.StringReader;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;

import se.usuarios.ServiceClient;
import se.usuarios.TipoUsuario;

import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ArchivoModelException;
import common.util.StringUtils;

import es.archigest.framework.facilities.security.ArchigestIdentifier;
import es.archigest.framework.facilities.security.SecurityConstants;
import es.archigest.framework.facilities.security.SecurityLevel;
import es.archigest.framework.facilities.security.SecurityLevelPrincipal;
import es.archigest.framework.facilities.security.jaas.callbacks.HttpRequestCallback;
import es.archigest.framework.facilities.security.jaas.callbacks.RequestDataCallback;
import es.archigest.framework.facilities.security.jaas.callbacks.ScenarioCallback;
import es.archigest.framework.modules.security.common.client.ArchigestLoginModule;


/**
 * Módulo para la autenticación de usuarios.
 */
public class SigemLoginModule extends ArchigestLoginModule {

    public static final String SESSION_USER_PROMPT				= "SESSION.USER.PROMPT";
    public static final String SESSION_USER_ADM_PROMPT				= "SESSION.USER.ADM.PROMPT";
    public static final String IDIOMA_PROMPT				= "IDIOMA.PROMPT";

    public static final String DEFAULT_SESSION_PROMPT			= "j_session";
    public static final String DEFAULT_ADM_SESSION_PROMPT			= "j_admsession";
    public static final String DEFAULT_IDIOMA_PROMPT			= "j_idioma";
    public static final SecurityLevel DEFAULT_SECURITY_LEVEL 	= SecurityLevel.level1;

    protected String sessionPrompt;
    protected String sessionAdmPrompt;
    protected String idiomaPrompt;
    protected String session;
    protected String remoteIpAddress;

    private final String TAG_SESSION_DATA_XML = "Datos_Especificos";
    private final String PROPERTY_SESSION_DATA_USER = "IdUsuario";
    private final String PROPERTY_SESSION_DATA_USER_GUID = "LdapGuid";
    private final String PROPERTY_SESSION_AUTHENTICATION_TYPE = "TipoAutenticacion";
    private final String PROPERTY_SESSION_DATA_TYPE = "TipoUsuario";
    private final String METHOD_SESSION_DATA_USER = "setIdUsuario";
    private final String METHOD_SESSION_DATA_USER_GUID = "setLdapGuid";
    private final String METHOD_SESSION_AUTHENTICACION_TYPE = "setTipoAutenticacion";
    private final String METHOD_SESSION_DATA_TYPE = "setTipoUsuario";
    private final String TAG_BEGIN_SESSION_DATA_XML = "<"+TAG_SESSION_DATA_XML+">";
    private final String TAG_END_SESSION_DATA_XML = "</"+TAG_SESSION_DATA_XML+">";
    private final String XPATH_SEPARATOR = "/";

	/**
	 * Constructor.
	 */
	public SigemLoginModule()
    {
		super();
        sessionPrompt = DEFAULT_SESSION_PROMPT;
        sessionAdmPrompt = DEFAULT_ADM_SESSION_PROMPT;
        idiomaPrompt = DEFAULT_IDIOMA_PROMPT;

    }


    /**
     * Inicializa este <code>LoginModule</code>.
     * @param subject El <code>Subject</code> que será autenticado.
     * @param callbackHandler Un <code>CallbackHandler</code> para la comunicación con el usuario.
     * @param sharedState Estado compartido con otros LoginModules.
     * @param options Opciones especificadas en la <code>Configuration</code> para este <code>LoginModule</code>.
     */
    public void initialize(Subject subject, CallbackHandler handler, Map sharedState, Map options)
    {
        super.initialize(subject, handler, sharedState, options);

        logger.info("Entrada en initialize");

        // Leer el promp de la opción de sesión del fichero de configuración JAAS
        String customSessionPrompt = (String) options.get(SESSION_USER_PROMPT);
        if (customSessionPrompt!= null)
            sessionPrompt = customSessionPrompt;

        // Leer el promp de la opción de sesión del fichero de configuración JAAS
        String customSessionAdmPrompt = (String) options.get(SESSION_USER_ADM_PROMPT);
        if (customSessionAdmPrompt!= null)
            sessionAdmPrompt = customSessionAdmPrompt;

        // Leer el promp de la opción de idioma de configuración JAAS
        String customIdiomaPrompt = (String) options.get(IDIOMA_PROMPT);
        if (customIdiomaPrompt!= null)
            idiomaPrompt = customIdiomaPrompt;

        if(logger.isDebugEnabled()){
            logger.debug("Prompt para la opción de sesión: " + sessionPrompt);
            logger.debug("Prompt para la opción de sesión de administración: " + sessionAdmPrompt);
            logger.debug("Prompt para la opción de idioma: " + idiomaPrompt);
        }
    }


    /**
     * Realiza la autenticación del usuario (fase 1).
     * @return true si el usuario está autenticado.
     * @throws LoginException si se produce algún error.
     */
    public boolean login() throws LoginException
    {
    	logger.info("Entrada en login");

        // Callbacks
    	ScenarioCallback scenCb = new ScenarioCallback();
        RequestDataCallback sessionCb = new RequestDataCallback(sessionPrompt);
        RequestDataCallback sessionAdmCb = new RequestDataCallback(sessionAdmPrompt);
        RequestDataCallback idiomaCb = new RequestDataCallback(idiomaPrompt);
        HttpRequestCallback requestCb = new HttpRequestCallback();

        // Leer callbacks
        handleCallbacks(new Callback[] { scenCb, sessionCb, sessionAdmCb, idiomaCb, requestCb });

        // Comprobando el nivel de seguridad
        String levelStr = scenCb.getLoginLevel();
        super.level = (levelStr != null ? SecurityLevel.getEnum(levelStr) : DEFAULT_SECURITY_LEVEL);
        if (super.level.compareTo(SecurityLevel.level0) == 0)
            return true;

		// Obtener la request
		HttpServletRequest request = requestCb.getRequest();

		// Obtener el idioma del callback de idioma
		String idioma = (String) idiomaCb.getValue();

        // Excepción lanzada la primera vez que se entra en la página
        if (sessionAdmCb.getValue() != null) {
        	// Sesión
			session = (String) sessionAdmCb.getValue();

			// Dirección IP del usuario
			remoteIpAddress = requestCb.getRequest().getRemoteAddr();

			authenticateAdmin(remoteIpAddress, session, idioma, request);
        } else {
			// Sesión
			session = (String) sessionCb.getValue();

			// Dirección IP del usuario
			remoteIpAddress = requestCb.getRequest().getRemoteAddr();

			authenticate(remoteIpAddress, session, idioma, request);
        }

        return true;
    }

    /**
     * Método que realiza el commit del proceso de autenticación (fase 2).
     * <p>Se llama a este método si la autenticación ha finalizado con éxito.</p>
     * @return true si el proceso se termina correctamente.
     * @throws LoginException si se produce algún error.
     */
    public boolean commit() throws LoginException
    {
		logger.info("Entrada en commit");

		// Salvar el nivel de seguridad del usuario
        super.subject.getPrincipals().add( new SecurityLevelPrincipal(super.level) );

        return true;
    }


    /**
     * Método que aborta el proceso de autenticación (fase 2).
     * <p>Se llama a este método si la autenticación ha finalizado con error.</p>
     * @return true si el método finaliza con éxito, o false en caso contrario.
     * @throws LoginException si se produce algún error.
     */
    public boolean abort() throws LoginException
	{
    	logger.info("Entrada en abort");
    	return super.abort();
	}


    /**
     * Método que cierra la sesión del <code>Subject</code>.
     * @return true si el método finaliza con éxito, o false en caso contrario.
     * @throws LoginException si se produce algún error.
     */
    public boolean logout() throws LoginException
	{
    	logger.info("Entrada en logout");
    	return super.logout();
	}


    /**
     * Lee los parámetros de entrada (callbacks).
     * @param cbList Lista de callbacks.
     */
    protected void handleCallbacks(Callback [] cbList)
    {
        try
        {
        	super.handler.handle(cbList);
        }
        catch(Exception e)
        {
            logger.error("No se ha podido obtener datos para autenticar.", e);
        }
    }

    private SessionData getSessionData(String xml) throws Exception{

    	SessionData data = new SessionData();

		Digester digester = new Digester();

        digester.setValidating(false);

        // Configuration
        digester.addObjectCreate(TAG_SESSION_DATA_XML, SessionData.class);
        String pathUser = TAG_SESSION_DATA_XML+XPATH_SEPARATOR+PROPERTY_SESSION_DATA_USER;
        digester.addCallMethod(pathUser, METHOD_SESSION_DATA_USER, 1);
        digester.addCallParam(pathUser, 0);

        String pathAuthenticationType = TAG_SESSION_DATA_XML+XPATH_SEPARATOR+PROPERTY_SESSION_AUTHENTICATION_TYPE;
        digester.addCallMethod(pathAuthenticationType, METHOD_SESSION_AUTHENTICACION_TYPE, 1);
        digester.addCallParam(pathAuthenticationType, 0);

        String pathType = TAG_SESSION_DATA_XML+XPATH_SEPARATOR+PROPERTY_SESSION_DATA_TYPE;
        digester.addCallMethod(pathType, METHOD_SESSION_DATA_TYPE, 1);
        digester.addCallParam(pathType, 0);

        String pathUserGuid = TAG_SESSION_DATA_XML+XPATH_SEPARATOR+PROPERTY_SESSION_DATA_USER_GUID;
        digester.addCallMethod(pathUserGuid, METHOD_SESSION_DATA_USER_GUID, 1);
        digester.addCallParam(pathUserGuid, 0);

		data = (SessionData) digester.parse(new StringReader(xml));

    	return data;
    }

    protected void authenticate(String ip, String keySesionUsuario, String idioma, HttpServletRequest request) throws LoginException
    {
    	Sesion sesion = null;

		try
		{
			// Autenticar el usuario
			if (AutenticacionBackOffice.autenticar(request)) {
				sesion = AutenticacionBackOffice.obtenerDatos(request);
				if (sesion!=null) {
					super.subject.getPrincipals().add( new ArchigestIdentifier(SecurityConstants.USERNAME, sesion.getUsuario()) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_LOGIN, sesion.getUsuario()) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_IP_ADDRESS, ip) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.SESSION, keySesionUsuario) );
					String entidad = sesion.getIdEntidad();
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.ENTITY, entidad) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.IDIOMA, idioma) );

					request.getSession().setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, sesion.getIdEntidad());

					// Obtener los datos específicos de archivo
					if (StringUtils.isNotEmpty(sesion.getDatosEspecificos())){
						String xml = TAG_BEGIN_SESSION_DATA_XML+sesion.getDatosEspecificos()+TAG_END_SESSION_DATA_XML;
						try {
							SessionData sessionData = getSessionData(xml);
							if (sessionData!=null){
								super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_TYPE, sessionData.getTipoUsuario()) );

								String tipoAutenticacion = sessionData.getTipoAutenticacion();
								if (tipoAutenticacion!=null){
									if (tipoAutenticacion.equals(DatosUsuario.AUTHENTICATION_TYPE_LDAP)){
										super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.EXTERNAL_USER_ID, sessionData.getLdapGuid()) );
									} else {
										// Autenticacion Invesdoc
										super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.EXTERNAL_USER_ID, sessionData.getIdUsuario()) );
									}
								} else {
									logger.error("No se ha encontrado el tipo de autenticación en los datos de sesión");
								}

							}
						} catch (Exception e){
							e.printStackTrace();
						}
					}

					// Salvar el nivel de seguridad del usuario
			        super.subject.getPrincipals().add( new SecurityLevelPrincipal(super.level) );
				}
			}
		}
		catch (Exception e)
		{
			try {
				if ((sesion!=null) && (StringUtils.isNotEmpty(sesion.getIdEntidad()))){

					// Obtener el repositorio de servicios
					ServiceRepository services = ServiceRepository.getInstance(ServiceClient.createWithEntity(sesion.getIdEntidad()));

				    //Obtenemos el servicio de prestamos para el usuario conectado
				    GestionSistemaBI gs = services.lookupGestionSistemaBI();

					//Registrar error en la autenticación
				    gs.doLogin(sesion.getUsuario(), remoteIpAddress, false);
				}
			} catch (ArchivoModelException e1) {}
		}
    }

    protected void authenticateAdmin(String ip, String keySesionUsuarioEntidad, String idioma, HttpServletRequest request) throws LoginException
    {
    	ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion sesion = null;

		try
		{
/*			sesion = new ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion();
			sesion.setUsuario("archivo");
			sesion.setIdEntidad("001");
			sesion.setIdSesion("asdsad");
			sesion.setDatosEspecificos("<IdUsuario>5</IdUsuario>");*/

			if (AutenticacionAdministracion.autenticarEntidad(request,ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO)) {
				sesion = AutenticacionAdministracion.obtenerDatosEntidad(request);
				if (sesion!=null) {
					super.subject.getPrincipals().add( new ArchigestIdentifier(SecurityConstants.USERNAME, sesion.getUsuario()) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_LOGIN, sesion.getUsuario()) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_IP_ADDRESS, ip) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.SESSION_ADM, keySesionUsuarioEntidad) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.ENTITY, sesion.getIdEntidad()) );
					super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.IDIOMA, idioma) );

					request.getSession().setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, sesion.getIdEntidad());

					// Obtener los datos específicos de archivo --> Si no se pasa el id de usuario en el sistema externo se supone
					// un usuario administrador, en otro caso se supone interno
					if (StringUtils.isNotEmpty(sesion.getDatosEspecificos())){
						String xml = TAG_BEGIN_SESSION_DATA_XML+sesion.getDatosEspecificos()+TAG_END_SESSION_DATA_XML;

						try {
							SessionData sessionData = getSessionData(xml);
							if (sessionData!=null){
								if (StringUtils.isNotEmpty(sessionData.getIdUsuario())){
									super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.EXTERNAL_USER_ID, sessionData.getIdUsuario()) );
									super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_TYPE, TipoUsuario.INTERNO) );
								} else {
									super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_TYPE, TipoUsuario.ADMINISTRADOR) );
								}
							} else {
								super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_TYPE, TipoUsuario.ADMINISTRADOR) );
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						super.subject.getPrincipals().add( new ArchivoIdentifier(ArchivoIdentifier.USER_TYPE, TipoUsuario.ADMINISTRADOR) );
					}

					// Salvar el nivel de seguridad del usuario
			        super.subject.getPrincipals().add( new SecurityLevelPrincipal(super.level) );
				}
			}
		}
		catch (Exception e)
		{
			try {
				if ((sesion!=null) && (StringUtils.isNotEmpty(sesion.getIdEntidad()))){

					// Obtener el repositorio de servicios
					ServiceRepository services = ServiceRepository.getInstance(ServiceClient.createWithEntity(sesion.getIdEntidad()));

				    //Obtenemos el servicio de prestamos para el usuario conectado
				    GestionSistemaBI gs = services.lookupGestionSistemaBI();

					//Registrar error en la autenticación
				    gs.doLogin(sesion.getUsuario(), remoteIpAddress, false);
				}
			} catch (ArchivoModelException e1) {}
		}
    }
}
