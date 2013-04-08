package common.actions;

import es.archigest.framework.web.filter.security.common.SecurityGlobals;
import gcontrol.ControlAccesoConstants;

import javax.security.auth.Subject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import se.usuarios.AppUserRIImpl;
import se.usuarios.ServiceClient;
import se.usuarios.TipoUsuario;
import se.usuarios.exceptions.AppUserException;
import util.SigemMultiEntityUtil;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.bi.GestionSessionBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ArchivoModelException;
import common.forms.LoginForm;
import common.view.MenuConfigurator;


/**
 * Action que se ejecuta al entrar en la aplicación.
 */
public class SigemLoginAction extends LoginBaseAction {


	protected static final Logger logger = Logger.getLogger(SigemLoginAction.class);

	/**
	 * Realiza el login en la aplicación.
	 * @param mapping {@link ActionMapping} con los mapeos asociado.
	 * @param form {@link ActionForm} asociado al action.
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
    protected void executeLogin(ActionMapping actionMapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		final MessageResources messages = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);

		// Comprobar que no exista un tipo de usuario con id de administrador (0)
		boolean existTipoAdministrador = ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo()
				.getConfiguracionControlAcceso().existTipoUsuario(TipoUsuario.ADMINISTRADOR);

		if (existTipoAdministrador){
			obtenerErrores(request, true)
			.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(Constants.LOGIN_APPLICATION_ADMINISTRATOR_TYPE_ERROR));
			logger.error(messages.getMessage(Constants.LOGIN_APPLICATION_ADMINISTRATOR_TYPE_ERROR));
			setReturnActionFordward(request, actionMapping.findForward("error"));
			request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, SigemMultiEntityUtil.getUrlLogout(request));
			return;
		}

    	// Usuario de la aplicación
		AppUser user = null;

		// Información de la autenticación del usuario
		Subject subject = (Subject) request.getSession(true).getAttribute(SecurityGlobals.SUBJECT);

    	if ((subject == null) || ((StringUtils.isEmpty(AppUserBaseRImpl.getSessionKey(subject)))&&(StringUtils.isEmpty(AppUserBaseRImpl.getSessionAdmKey(subject))))){
			obtenerErrores(request, true)
			.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(Constants.LOGIN_MULTIENTITY_APPLICATION_SESSION_ERROR));
			logger.error(messages.getMessage(Constants.LOGIN_MULTIENTITY_APPLICATION_SESSION_ERROR));
			setReturnActionFordward(request, actionMapping.findForward("error"));
			if (ConfigConstants.getInstance().getEntidadRequerida()){
				request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, SigemMultiEntityUtil.getUrlLogout(request));
			}
			request.getSession(true).removeAttribute(SecurityGlobals.SUBJECT);
			return;
    	}

		// Repositorio de servicios
        ServiceRepository services = null;
		try {
			services = ServiceRepository.getInstance(ServiceClient.createWithEntity(AppUserBaseRImpl.getUserEntity(subject)));
		} catch (ArchivoModelException e1) {
			obtenerErrores(request, true)
			.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(Constants.LOGIN_APPLICATION_DATABASE_ERROR));
			logger.error(messages.getMessage(Constants.LOGIN_APPLICATION_DATABASE_ERROR));
			setReturnActionFordward(request, actionMapping.findForward("error"));
			if (ConfigConstants.getInstance().getEntidadRequerida()){
				request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, SigemMultiEntityUtil.getUrlLogout(request));
			}
			request.getSession(true).removeAttribute(SecurityGlobals.SUBJECT);
			return;
		}

        //Obtenemos el servicio de sistema para el usuario conectado
        GestionSistemaBI gs = services.lookupGestionSistemaBI();
		//Obtenemos el servicio de gestion de sesiones
		GestionSessionBI serviceSession = services.lookupGestionSessionBI();

        // Formulario de login
		LoginForm frm = (LoginForm) form;

		try
		{

			// Chequear el caso de una configuración incorrecta: código de unidad documental coincide con signatura física y existe una signaturación por archivo en vez de una global
			if (!ConfigConstants.getInstance().getCodigoUdocUnico() && ConfigConstants.getInstance().getSignaturacionPorArchivo())
			{
				ActionErrors errors = new ActionErrors();
				errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(Constants.LOGIN_APPLICATION_ERROR_COD_UNICO_SIG_ARCHIVOS));
			    logger.error(messages.getMessage(Constants.LOGIN_APPLICATION_ERROR_COD_UNICO_SIG_ARCHIVOS));
				obtenerErrores(request, true).add(errors);
				setReturnActionFordward(request, actionMapping.findForward("error"));
				if (ConfigConstants.getInstance().getEntidadRequerida()){
					request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, SigemMultiEntityUtil.getUrlLogout(request));
				}
				request.getSession(true).removeAttribute(SecurityGlobals.SUBJECT);
				return;
			}

			// Obtener los datos de usuario
			user = AppUserRIFactory.createAppUserRI().getAppUser(subject);
			if (logger.isDebugEnabled())
				logger.debug("AppUser:" + Constants.NEWLINE + user);

			// Si el usuario es interno debe tener un organo asociado. Si no es asi error
			boolean esSesionAdministracion = StringUtils.isNotEmpty(AppUserRIImpl.getSessionAdmKey(subject));
			boolean tieneIdUsuarioExterno = StringUtils.isNotEmpty(AppUserRIImpl.getExternalUserId(subject));

			if((!esSesionAdministracion)||(esSesionAdministracion&&tieneIdUsuarioExterno)){
				if(user!=null && user.getUserType().equals(TipoUsuario.INTERNO)){
					if(user.getOrganization()==null || user.getOrganization().getIdOrg()==null){
						obtenerErrores(request, true)
							.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(Constants.LOGIN_ERROR_USER_INTERNO_SIN_ORGANO));
					    logger.error(messages.getMessage(Constants.LOGIN_ERROR_USER_INTERNO_SIN_ORGANO));
						setReturnActionFordward(request, actionMapping.findForward("error"));
						if (ConfigConstants.getInstance().getEntidadRequerida()){
							request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, SigemMultiEntityUtil.getUrlLogout(request));
						}
						request.getSession(true).removeAttribute(SecurityGlobals.SUBJECT);
						return;
					}
				}
			}

			// Si hay una sesión de administración cambiar el nombre del usuario por el del administrador
			if (StringUtils.isNotEmpty(AppUserRIImpl.getSessionAdmKey(subject))){
				user.setName(AppUserRIImpl.getUserLogin(subject));
			}

			// Guardar la información del usuario en el contenedor de sesión
			request.getSession().setAttribute(Constants.USUARIOKEY, user);

			//Registrar autenticación correcta
			gs.doLogin( user.getId(), request.getRemoteAddr(), true);

			// Configurar el menú del usuario
			MenuConfigurator.configureUserMenu(request, getServlet().getServletContext());

			// Reinicio la pila de navegación
			resetInvocationStack(request);

			//Inicializamos la sesion del usuario
			String ticket = serviceSession.login( request.getSession().getId(), user.getId());
			if (!request.getSession().getId().equalsIgnoreCase(ticket) )
			{
				boolean expulsar = false;
				String checkExpulsar = request.getParameter("expulsar");
				if (checkExpulsar!=null && checkExpulsar.trim().length()>0)
				    expulsar = true;

				if (expulsar)
				    ticket = serviceSession.loginExpulsando( request.getSession().getId(), user.getId());
				else
				{
				    ActionErrors errors = new ActionErrors();
				    errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.LOGIN_USER_CONNECTED));

				    //Añadir los errores producidos
				    obtenerErrores(request, true).add(errors);
					//Activamos el check de expulsion de usuario
					request.setAttribute(ControlAccesoConstants.EXPULSAR_KEY,new Boolean("true"));
					//Redirigimos a la pagina adecuada
					setReturnActionFordward(request, actionMapping.findForward("error"));

					if (ConfigConstants.getInstance().getEntidadRequerida()){
						request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, SigemMultiEntityUtil.getUrlLogout(request));
					}
					request.getSession(true).removeAttribute(SecurityGlobals.SUBJECT);
					return;
				}
			}

			//Creamos la cookie necesaria para el mantenimiento de la sesion
			Cookie cookieUser = new Cookie("user", ticket);
	        response.addCookie(cookieUser);
		}
		catch (AppUserException e)
		{
			logger.warn("Error en la autenticación del usuario", e);


			//Incrementamos el numero de intentos
			Object objNumMaxIntentos=request.getSession().getAttribute(Constants.NUM_MAX_INTENTOS);

			if(objNumMaxIntentos!=null)
			{
				int intentos=((Integer)objNumMaxIntentos).intValue();
				Integer numMaxIntentos=new Integer(++intentos);
				request.getSession().setAttribute(Constants.NUM_MAX_INTENTOS, numMaxIntentos);

			}
			else
			{
				request.getSession().setAttribute(Constants.NUM_MAX_INTENTOS, new Integer(1));
			}

			 // Nos aseguramos que el usuario tenga una sesión correcta.
			/*HttpSession session = */request.getSession(false);

			// Crear la lista de errores
			ActionErrors errors = new ActionErrors();

			// Añadir el error a la lista de errores
			switch (e.getErrorCode())
			{
				case AppUserException.USER_NOT_FOUND:
					errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.LOGIN_APPLICATION_NOT_FOUND));
					break;

				case AppUserException.USER_NOT_ACTIVATED:
					errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.LOGIN_APPLICATION_NOT_ACTIVATED));
					break;

				case AppUserException.USER_EXPIRED:
					errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.LOGIN_APPLICATION_EXPIRED));
					break;

				case AppUserException.USER_WITH_NO_PERMISSIONS:
					errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.LOGIN_APPLICATION_NO_PERMISSIONS));
					break;

				case AppUserException.USER_ORGANIZATION_ERROR:
					errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.LOGIN_APPLICATION_ORGANIZATION_ERROR));
					break;

				// TODO: ATipo de usuario no encontrado
				case AppUserException.USER_TYPE_NOT_FOUND:
					errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.LOGIN_USER_TYPE_NOT_FOUND));
					break;

				default:
					errors.add(Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION, new Short(e.getErrorCode())));
			}

			try
			{
				//Registrar error en la autenticación
				gs.doLogin( frm.getLogin(), request.getRemoteAddr(), false);
			}
			catch (Exception e2)
			{
				logger.error("Error al auditar el error en la autenticación", e2);

				// Añadir el error
				errors.add(Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(Constants.ERROR_GENERAL_MESSAGE, e2.getLocalizedMessage()));
			}

			// Añadir los errores producidos
			obtenerErrores(request, true).add(errors);

			setReturnActionFordward(request, actionMapping.findForward("error"));

			if (ConfigConstants.getInstance().getEntidadRequerida()){
				request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, SigemMultiEntityUtil.getUrlLogout(request));
			}
			request.getSession(true).removeAttribute(SecurityGlobals.SUBJECT);
		}
		catch (Exception e)
		{
			logger.error("Error en la autenticación del usuario", e);

			// Crear la lista de errores
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(Constants.ERROR_GENERAL_MESSAGE, e.getLocalizedMessage()));

			try
			{
				//Registrar error en la autenticación
				gs.doLogin( frm.getLogin(), request.getRemoteAddr(), false);
			}
			catch (Exception e2)
			{
				logger.error("Error al auditar el error en la autenticación", e2);

				// Añadir el error
				errors.add(Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(Constants.ERROR_GENERAL_MESSAGE, e2.getLocalizedMessage()));
			}

			// Añadir los errores producidos
			obtenerErrores(request, true).add(errors);

			setReturnActionFordward(request, actionMapping.findForward("error"));

			if (ConfigConstants.getInstance().getEntidadRequerida()){
				request.setAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL, SigemMultiEntityUtil.getUrlLogout(request));
			}
			request.getSession(true).removeAttribute(SecurityGlobals.SUBJECT);
		}
    }
}