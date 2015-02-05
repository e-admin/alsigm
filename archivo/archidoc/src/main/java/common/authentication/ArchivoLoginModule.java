package common.authentication;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;

import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorFactory;
import se.usuarios.ServiceClient;
import xml.config.ConfiguracionControlAcceso;

import common.Constants;
import common.Messages;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ArchivoModelException;
import common.exceptions.UserLockedLoginException;

import es.archigest.framework.facilities.security.ArchigestIdentifier;
import es.archigest.framework.facilities.security.SecurityConstants;
import es.archigest.framework.facilities.security.SecurityLevel;
import es.archigest.framework.facilities.security.SecurityLevelPrincipal;
import es.archigest.framework.facilities.security.exceptions.RequiredPasswordException;
import es.archigest.framework.facilities.security.exceptions.UnmanagedLoginException;
import es.archigest.framework.facilities.security.jaas.callbacks.HttpRequestCallback;
import es.archigest.framework.facilities.security.jaas.callbacks.RequestDataCallback;
import es.archigest.framework.facilities.security.jaas.callbacks.ScenarioCallback;
import es.archigest.framework.modules.security.common.client.ArchigestLoginModule;

/**
 * Módulo para la autenticación de usuarios.
 */
public class ArchivoLoginModule extends ArchigestLoginModule {

	public static final String USER_TYPE_PROMPT = "USER.TYPE.PROMPT";
	public static final String USER_PROMPT = "USER.PROMPT";
	public static final String PASSWORD_PROMPT = "PASSWORD.PROMPT";
	public static final String SUPERUSER_PROMPT = "SUPERUSER.PROMPT";

	public static final String DEFAULT_USER_TYPE_PROMPT = "j_usertype";
	public static final String DEFAULT_USER_PROMPT = "j_username";
	public static final String DEFAULT_PASSWORD_PROMPT = "j_password";
	public static final String DEFAULT_SUPERUSER_PROMPT = "j_superuser";
	public static final SecurityLevel DEFAULT_SECURITY_LEVEL = SecurityLevel.level1;

	protected String userTypePrompt;
	protected String userNamePrompt;
	protected String passwdPrompt;
	protected String superuserPrompt;
	protected String userName;
	protected String identifier;
	protected String userType;
	protected String superuser;
	protected String remoteIpAddress;
	protected String actorType;

	/**
	 * Constructor.
	 */
	public ArchivoLoginModule() {
		super();

		userTypePrompt = DEFAULT_USER_TYPE_PROMPT;
		userNamePrompt = DEFAULT_USER_PROMPT;
		passwdPrompt = DEFAULT_PASSWORD_PROMPT;
		superuserPrompt = DEFAULT_SUPERUSER_PROMPT;
	}

	/**
	 * Inicializa este <code>LoginModule</code>.
	 *
	 * @param subject
	 *            El <code>Subject</code> que será autenticado.
	 * @param callbackHandler
	 *            Un <code>CallbackHandler</code> para la comunicación con el
	 *            usuario.
	 * @param sharedState
	 *            Estado compartido con otros LoginModules.
	 * @param options
	 *            Opciones especificadas en la <code>Configuration</code> para
	 *            este <code>LoginModule</code>.
	 */
	public void initialize(Subject subject, CallbackHandler handler,
			Map sharedState, Map options) {
		super.initialize(subject, handler, sharedState, options);

		logger.info("Entrada en initialize");

		// Leer el promp del tipo de usuario del fichero de configuración JAAS
		String customUserTypePrompt = (String) options.get(USER_TYPE_PROMPT);
		if (customUserTypePrompt != null)
			userTypePrompt = customUserTypePrompt;

		if (logger.isDebugEnabled())
			logger.debug("Prompt para el tipo de usuario: " + userTypePrompt);

		// Leer el promp del usuario del fichero de configuración JAAS
		String customUserNamePrompt = (String) options.get(USER_PROMPT);
		if (customUserNamePrompt != null)
			userNamePrompt = customUserNamePrompt;

		if (logger.isDebugEnabled())
			logger.debug("Prompt para el usuario: " + userNamePrompt);

		// Leer el promp de la clave del usuario del fichero de configuración
		// JAAS
		String customPasswdPrompt = (String) options.get(PASSWORD_PROMPT);
		if (customPasswdPrompt != null)
			passwdPrompt = customPasswdPrompt;

		if (logger.isDebugEnabled())
			logger.debug("Prompt para la clave: " + passwdPrompt);

		// Leer el promp de la opción de superusuario del fichero de
		// configuración JAAS
		String customSuperuserPrompt = (String) options.get(SUPERUSER_PROMPT);
		if (customSuperuserPrompt != null)
			superuserPrompt = customSuperuserPrompt;

		if (logger.isDebugEnabled())
			logger.debug("Prompt para la opción de superusuario: "
					+ superuserPrompt);
	}

	/**
	 * Realiza la autenticación del usuario (fase 1).
	 *
	 * @return true si el usuario está autenticado.
	 * @throws LoginException
	 *             si se produce algún error.
	 */
	public boolean login() throws LoginException {
		logger.info("Entrada en login");

		// Callbacks
		ScenarioCallback scenCb = new ScenarioCallback();
		RequestDataCallback userTypeCb = new RequestDataCallback(userTypePrompt);
		NameCallback userNameCb = new NameCallback(userNamePrompt);
		PasswordCallback passCb = new PasswordCallback(passwdPrompt, false);
		RequestDataCallback superuserCb = new RequestDataCallback(
				superuserPrompt);
		HttpRequestCallback requestCb = new HttpRequestCallback();

		// Leer callbacks
		handleCallbacks(new Callback[] { scenCb, userTypeCb, userNameCb,
				passCb, superuserCb, requestCb });

		// Comprobando el nivel de seguridad
		String levelStr = scenCb.getLoginLevel();
		super.level = (levelStr != null ? SecurityLevel.getEnum(levelStr)
				: DEFAULT_SECURITY_LEVEL);
		if (super.level.compareTo(SecurityLevel.level0) == 0)
			return true;

		// Excepción lanzada la primera vez que se entra en la página
		if ((userTypeCb.getValue() == null && superuserCb.getValue() == null)
				|| (userNameCb.getName() == null)
				|| (passCb.getPassword() == null))
			throw new RequiredPasswordException();

		// Login del usuario
		userName = userNameCb.getName();

		// Tipo de usuario
		userType = (String) userTypeCb.getValue();

		// Dirección IP del usuario
		remoteIpAddress = requestCb.getRequest().getRemoteAddr();

		// Actor
		actorType = scenCb.getActorType();

		// Opción de superusuario
		superuser = (String) superuserCb.getValue();

		if (superuser != null && !superuser.equals(""))
			userType = ConfiguracionControlAcceso.SUPERUSER_TYPE;

		// Autenticar al usuario
		try {
			authenticate(userName, new String(passCb.getPassword()),
					remoteIpAddress, userType, superuser);
		} catch (UserLockedLoginException e) {
			String userLockedMessage = Constants.LOGIN_APPLICATION_USER_LOCKED;
			throw new LoginException(Messages.getString(userLockedMessage,
					requestCb.getRequest().getLocale()));
		}

		return true;
	}

	/**
	 * Método que realiza el commit del proceso de autenticación (fase 2).
	 * <p>
	 * Se llama a este método si la autenticación ha finalizado con éxito.
	 * </p>
	 *
	 * @return true si el proceso se termina correctamente.
	 * @throws LoginException
	 *             si se produce algún error.
	 */
	public boolean commit() throws LoginException {
		logger.info("Entrada en commit");

		// Guardar los identificadores del usuario
		super.subject.getPrincipals().add(
				new ArchigestIdentifier(SecurityConstants.USERNAME, userName));
		super.subject.getPrincipals().add(
				new ArchigestIdentifier(SecurityConstants.ID_THIRD_PARTY,
						identifier));
		super.subject.getPrincipals().add(
				new ArchigestIdentifier(SecurityConstants.IP, remoteIpAddress));
		super.subject.getPrincipals()
				.add(new ArchigestIdentifier(SecurityConstants.ACTOR_TYPE,
						actorType));

		super.subject.getPrincipals().add(
				new ArchivoIdentifier(ArchivoIdentifier.USER_LOGIN, userName));
		super.subject.getPrincipals().add(
				new ArchivoIdentifier(ArchivoIdentifier.EXTERNAL_USER_ID,
						identifier));
		super.subject.getPrincipals().add(
				new ArchivoIdentifier(ArchivoIdentifier.USER_TYPE, userType));
		super.subject.getPrincipals().add(
				new ArchivoIdentifier(ArchivoIdentifier.USER_IP_ADDRESS,
						remoteIpAddress));

		// Salvar el nivel de seguridad del usuario
		super.subject.getPrincipals().add(
				new SecurityLevelPrincipal(super.level));

		return true;
	}

	/**
	 * Método que aborta el proceso de autenticación (fase 2).
	 * <p>
	 * Se llama a este método si la autenticación ha finalizado con error.
	 * </p>
	 *
	 * @return true si el método finaliza con éxito, o false en caso contrario.
	 * @throws LoginException
	 *             si se produce algún error.
	 */
	public boolean abort() throws LoginException {
		logger.info("Entrada en abort");
		return super.abort();
	}

	/**
	 * Método que cierra la sesión del <code>Subject</code>.
	 *
	 * @return true si el método finaliza con éxito, o false en caso contrario.
	 * @throws LoginException
	 *             si se produce algún error.
	 */
	public boolean logout() throws LoginException {
		logger.info("Entrada en logout");
		return super.logout();
	}

	/**
	 * Lee los parámetros de entrada (callbacks).
	 *
	 * @param cbList
	 *            Lista de callbacks.
	 */
	protected void handleCallbacks(Callback[] cbList) {
		try {
			super.handler.handle(cbList);
		} catch (Exception e) {
			logger.error("No se ha podido obtener datos para autenticar.", e);
			throw new UnmanagedLoginException(e, "Cargando callbacks",
					"No se han podido cargar los callbacks correctamente");
		}
	}

	/**
	 * Autentica al usuario.
	 *
	 * @param userName
	 *            Nombre del usuario.
	 * @param password
	 *            Clave del usuario.
	 * @para ip Dirección IP del usuario.
	 * @param userType
	 *            Tipo de usuario.
	 * @throws LoginException
	 *             si ocurre algún error.
	 */
	protected void authenticate(String userName, String password, String ip,
			String userType, String superuser) throws LoginException {
		ServiceRepository services;
		try {
			services = ServiceRepository.getInstance(ServiceClient
					.createWithEntity(null));
		} catch (ArchivoModelException e1) {
			throw new LoginException(e1.getMessage());
		}

		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionSistemaBI gs = services.lookupGestionSistemaBI();

		try {
			// Instancia del conector adecuado para el tipo de usuario
			AuthenticationConnector authClient = AuthenticationConnectorFactory
					.getConnector(userType, superuser, null);

			// Autenticación del usuario
			identifier = authClient.authenticate(userName, password, ip);
		} catch (LoginException e) {
			// TODO 0.Revisar si se puede mandar a la auditoria la excepcion
			// correcta
			// Registrar error en la autenticación
			gs.doLogin(userName, remoteIpAddress, false);

			throw e;
		} catch (UserLockedLoginException e) {
			throw e;
		} catch (Exception e1) {
			// TODO 0.Revisar si se puede mandar a la auditoria la excepcion
			// correcta
			// Registrar error en la autenticación
			gs.doLogin(userName, remoteIpAddress, false);

			throw new LoginException(e1.getMessage());
		}
	}
}
