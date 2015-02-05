package common.actions;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.security.auth.Subject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import se.usuarios.AppUser;
import se.usuarios.AppUserBaseRImpl;
import se.usuarios.AppUserRIFactory;
import se.usuarios.ServiceClient;
import se.usuarios.TipoUsuario;
import se.usuarios.exceptions.AppUserException;
import xml.config.CampoDescriptivoConfigMapFSUDoc;
import xml.config.ConfiguracionControlAcceso;
import xml.config.ConfiguracionMapeoFSUDoc;
import xml.config.ConfiguracionMapeoFSUDocFactory;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Superusuario;

import common.ConfigConstants;
import common.Constants;
import common.bi.GestionDescripcionBI;
import common.bi.GestionSessionBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ArchivoModelException;
import common.forms.LoginForm;
import common.view.MenuConfigurator;

import descripcion.vos.CampoDatoVO;
import es.archigest.framework.web.filter.security.common.SecurityGlobals;
import gcontrol.ControlAccesoConstants;

/**
 * Action que se ejecuta al entrar en la aplicación.
 */
public class LoginAction extends LoginBaseAction {

	protected static final Logger logger = Logger.getLogger(LoginAction.class);

	/**
	 * Realiza el login en la aplicación.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void executeLogin(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		// Comprobar que no exista un tipo de usuario con id de administrador
		// (0)
		boolean existTipoAdministrador = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionControlAcceso()
				.existTipoUsuario(TipoUsuario.ADMINISTRADOR);

		if (existTipoAdministrador) {
			obtenerErrores(request, true)
					.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(
									Constants.LOGIN_APPLICATION_ADMINISTRATOR_TYPE_ERROR));
			logger.error(messages
					.getMessage(Constants.LOGIN_APPLICATION_ADMINISTRATOR_TYPE_ERROR));
			setReturnActionFordward(request, actionMapping.findForward("error"));
			return;
		}

		// Usuario de la aplicación
		AppUser user = null;

		// Información de la autenticación del usuario
		Subject subject = (Subject) request.getSession(true).getAttribute(
				SecurityGlobals.SUBJECT);

		// Repositorio de servicios
		ServiceRepository services = null;
		try {
			services = ServiceRepository.getInstance(ServiceClient
					.createWithEntity(AppUserBaseRImpl.getUserEntity(subject)));
		} catch (ArchivoModelException e1) {
			obtenerErrores(request, true)
					.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(
									Constants.LOGIN_APPLICATION_DATABASE_ERROR));
			logger.error(messages
					.getMessage(Constants.LOGIN_APPLICATION_DATABASE_ERROR));
			setReturnActionFordward(request, actionMapping.findForward("error"));
			return;
		}

		// Obtenemos el servicio de sistema para el usuario conectado
		GestionSistemaBI gs = services.lookupGestionSistemaBI();
		// Obtenemos el servicio de gestion de sesiones
		GestionSessionBI serviceSession = services.lookupGestionSessionBI();

		// Formulario de login
		LoginForm frm = (LoginForm) form;

		try {
			int numMaxIntentos = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
					.getNumMaxIntentos();

			if (numMaxIntentos > 0) {
				Object objIntentos = request.getSession().getAttribute(
						Constants.NUM_MAX_INTENTOS);
				if (objIntentos != null) {
					int intentos = ((Integer) objIntentos).intValue();
					if (intentos >= numMaxIntentos) {
						ActionErrors errors = new ActionErrors();
						errors.add(
								Constants.ERROR_GENERAL_MESSAGE,
								new ActionError(
										Constants.LOGIN_APPLICATION_NUM_MAX_INTENTOS));

						obtenerErrores(request, true).add(errors);
						setReturnActionFordward(request,
								actionMapping.findForward("error"));
						return;
					}
				}
			}

			// Chequear el caso de una configuración incorrecta: código de
			// unidad documental coincide con signatura física y existe una
			// signaturación por archivo en vez de una global
			if (!ConfigConstants.getInstance().getCodigoUdocUnico()
					&& ConfigConstants.getInstance()
							.getSignaturacionPorArchivo()) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(
								Constants.LOGIN_APPLICATION_ERROR_COD_UNICO_SIG_ARCHIVOS));
				logger.error(messages
						.getMessage(Constants.LOGIN_APPLICATION_ERROR_COD_UNICO_SIG_ARCHIVOS));
				obtenerErrores(request, true).add(errors);
				setReturnActionFordward(request,
						actionMapping.findForward("error"));
			}

			// Chequear el caso de una configuración incorrecta: existen campos
			// descriptivos que se quieren mapear de la fracción de serie a la
			// unidad
			// documental que no están dados de alta en la base de datos
			ConfiguracionMapeoFSUDoc configMap = ConfiguracionMapeoFSUDocFactory
					.getConfiguracionMapeoFSUDoc();
			if (configMap != null) {
				Map campos = configMap.getCamposDescriptivos();
				if (campos != null) {
					ActionErrors errors = new ActionErrors();
					GestionDescripcionBI descripcionBI = services
							.lookupGestionDescripcionBI();
					Iterator it = campos.entrySet().iterator();

					while (it.hasNext()) {
						Entry entry = (Entry) it.next();
						CampoDescriptivoConfigMapFSUDoc cdmap = (CampoDescriptivoConfigMapFSUDoc) entry
								.getValue();

						CampoDatoVO cdVO = descripcionBI.getCampoDato(cdmap
								.getValorDestino());

						if (cdVO == null) {
							errors.add(
									Constants.ERROR_GENERAL_MESSAGE,
									new ActionError(
											Constants.LOGIN_APPLICATION_ERROR_FICHERO_MAP_FS_UDOC));
							logger.error(messages
									.getMessage(Constants.LOGIN_APPLICATION_ERROR_FICHERO_MAP_FS_UDOC));
							obtenerErrores(request, true).add(errors);
							setReturnActionFordward(request,
									actionMapping.findForward("error"));
							break;
						}
					}
				}
			}

			// Datos de usuario
			user = AppUserRIFactory.createAppUserRI().getAppUser(subject);
			if (logger.isDebugEnabled())
				logger.debug("AppUser:" + Constants.NEWLINE + user);

			// Establecer el locale del usuario
			user.setLocale(request.getLocale());

			// si el usuario es interno debe tener un organo asociado. Si no es
			// asi error
			// Obtener el nombre del usuario conectado
			String userName = AppUserBaseRImpl.getUserLogin(subject);

			// Comprobar si es un usuario administrador
			ConfiguracionControlAcceso caa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionControlAcceso();
			Superusuario superusuario = caa.findSuperusuario(userName);

			if (superusuario == null) {
				if (user != null
						&& user.getUserType().equals(TipoUsuario.INTERNO)) {
					if (user.getOrganization() == null
							|| user.getOrganization().getIdOrg() == null) {
						obtenerErrores(request, true)
								.add(Constants.ERROR_GENERAL_MESSAGE,
										new ActionError(
												Constants.LOGIN_ERROR_USER_INTERNO_SIN_ORGANO));
						logger.error(messages
								.getMessage(Constants.LOGIN_ERROR_USER_INTERNO_SIN_ORGANO));
						setReturnActionFordward(request,
								actionMapping.findForward("error"));
						return;
					}
				}
			}

			// Guardar la información del usuario en el contenedor de sesión
			request.getSession().setAttribute(Constants.USUARIOKEY, user);

			// Registrar autenticación correcta
			gs.doLogin(user.getId(), request.getRemoteAddr(), true);

			// Configurar el menú del usuario
			MenuConfigurator.configureUserMenu(request, getServlet()
					.getServletContext());

			// Reinicio la pila de navegación
			resetInvocationStack(request);

			// Inicializamos la sesion del usuario
			String ticket = serviceSession.login(request.getSession().getId(),
					user.getId());
			if (!request.getSession().getId().equalsIgnoreCase(ticket)) {
				boolean expulsar = false;
				String checkExpulsar = request.getParameter("expulsar");
				if (checkExpulsar != null && checkExpulsar.trim().length() > 0)
					expulsar = true;

				if (expulsar)
					ticket = serviceSession.loginExpulsando(request
							.getSession().getId(), user.getId());
				else {
					ActionErrors errors = new ActionErrors();
					errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.LOGIN_USER_CONNECTED));

					// Añadir los errores producidos
					obtenerErrores(request, true).add(errors);
					// Activamos el check de expulsion de usuario
					request.setAttribute(ControlAccesoConstants.EXPULSAR_KEY,
							new Boolean("true"));
					// Redirigimos a la pagina adecuada
					setReturnActionFordward(request,
							actionMapping.findForward("error"));
				}
			}

			// Creamos la cookie necesaria para el mantenimiento de la sesion
			Cookie cookieUser = new Cookie("user", ticket);
			response.addCookie(cookieUser);
		} catch (AppUserException e) {
			logger.warn("Error en la autenticación del usuario", e);

			// Incrementamos el numero de intentos
			Object objNumMaxIntentos = request.getSession().getAttribute(
					Constants.NUM_MAX_INTENTOS);

			if (objNumMaxIntentos != null) {
				int intentos = ((Integer) objNumMaxIntentos).intValue();
				Integer numMaxIntentos = new Integer(++intentos);
				request.getSession().setAttribute(Constants.NUM_MAX_INTENTOS,
						numMaxIntentos);

			} else {
				request.getSession().setAttribute(Constants.NUM_MAX_INTENTOS,
						new Integer(1));
			}

			// Crear la lista de errores
			ActionErrors errors = new ActionErrors();

			// Añadir el error a la lista de errores
			switch (e.getErrorCode()) {
			case AppUserException.USER_NOT_FOUND:
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						Constants.LOGIN_APPLICATION_NOT_FOUND));
				break;

			case AppUserException.USER_NOT_ACTIVATED:
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						Constants.LOGIN_APPLICATION_NOT_ACTIVATED));
				break;

			case AppUserException.USER_EXPIRED:
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						Constants.LOGIN_APPLICATION_EXPIRED));
				break;

			case AppUserException.USER_WITH_NO_PERMISSIONS:
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						Constants.LOGIN_APPLICATION_NO_PERMISSIONS));
				break;

			case AppUserException.USER_ORGANIZATION_ERROR:
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						Constants.LOGIN_APPLICATION_ORGANIZATION_ERROR));
				break;

			// TODO: ATipo de usuario no encontrado
			case AppUserException.USER_TYPE_NOT_FOUND:
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
						Constants.LOGIN_USER_TYPE_NOT_FOUND));
				break;

			default:
				errors.add(Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION,
								new Short(e.getErrorCode())));
			}

			try {
				// Registrar error en la autenticación
				gs.doLogin(frm.getLogin(), request.getRemoteAddr(), false);
			} catch (Exception e2) {
				logger.error("Error al auditar el error en la autenticación",
						e2);

				// Añadir el error
				errors.add(
						Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(Constants.ERROR_GENERAL_MESSAGE, e2
								.getLocalizedMessage()));
			}

			// Añadir los errores producidos
			obtenerErrores(request, true).add(errors);

			setReturnActionFordward(request, actionMapping.findForward("error"));
		} catch (Exception e) {
			logger.error("Error en la autenticación del usuario", e);

			// Crear la lista de errores
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
					Constants.ERROR_GENERAL_MESSAGE, e.getLocalizedMessage()));

			try {
				// Registrar error en la autenticación
				gs.doLogin(frm.getLogin(), request.getRemoteAddr(), false);
			} catch (Exception e2) {
				logger.error("Error al auditar el error en la autenticación",
						e2);

				// Añadir el error
				errors.add(
						Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(Constants.ERROR_GENERAL_MESSAGE, e2
								.getLocalizedMessage()));
			}

			// Añadir los errores producidos
			obtenerErrores(request, true).add(errors);

			setReturnActionFordward(request, actionMapping.findForward("error"));
		}
	}
}