package ieci.tecdoc.isicres.rpadmin.struts.acciones.login;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.util.Idioma;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.Utils;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroSicres3Utils;
import es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion;

public class LoginAction extends RPAdminWebAction{

	/**
	 * 
	 */
	private static final String IS_LDAP_AUTHENTICATION_POLICY_SESSION_ATTR_KEY = "isLdapAuthenticationPolicy";

	private static final Logger logger = Logger.getLogger(LoginAction.class);

	public static final String USELDAP_KEY="UseLDAP";
	public static final String USINGOSAUTH_KEY="UsingOSAuth";
	public static final String NAMECTRL_KEY="NameCtrl";
	public static final String USERDN_KEY="UserDn";

	public  Boolean getUseLdap(HttpServletRequest request){
		Boolean result = RequestUtils.parseRequestParameterAsBoolean(request, USELDAP_KEY, Boolean.FALSE);
		return result;

	}
	public  Boolean getUsingOSAuth(HttpServletRequest request){
		Boolean result = RequestUtils.parseRequestParameterAsBoolean(request, USINGOSAUTH_KEY, Boolean.FALSE);
		return result;

	}

	/**
	 * obtiene el nombre para autenticarse
	 * @param request
	 * @return
	 */
	public static  String getName(HttpServletRequest request){
	 String result = RequestUtils.parseRequestParameterAsStringWithEmpty(request,NAMECTRL_KEY);
	 return result;

	}

	/**
	 * Obtiene la entidad actual (contexto multientidad) en producto siempre es la misma
	 * @param request
	 * @return
	 */
	public  String getIdEntidad(HttpServletRequest request){
		String result = Keys.KEY_BUILD_TYPE_INVESICRES;
		return result;
	}

	/**
	 * obtiene el userDn para autenticarse en caso de autenticacion useLdap && usingOSAuth
	 * @param request
	 * @return
	 */
	public  String getUserDn(HttpServletRequest request){
		String result = RequestUtils.parseRequestParameterAsStringWithEmpty(request, USERDN_KEY);
		return result;
	}

	/**
	 * seteamos propiedades de la responde del proceso de login.jsp
	 * @param response
	 */
	protected void setupResponse(HttpServletResponse response){
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

	}

	/**
	 * Metodo que comprueba si la peticion actual es un proceso de login
	 *
	 * @param request
	 * @return
	 */
	protected  boolean isAuthProcess(HttpServletRequest request){
		boolean result=false;
		result=(getUseLdap(request).booleanValue() && getUsingOSAuth(request).booleanValue()) || StringUtils.isNotEmpty(getName(request)) ;
		return result;
	}

	/**
	 * Metodo  que obtiene el <code>UseCaseConf</code> necesario segun el tipo de autenticacion
	 * @param request
	 * @return
	 */
	protected  UseCaseConf getUseCaseConf(HttpServletRequest request){

		UseCaseConf result=null;

		boolean useLdap=getUseLdap(request).booleanValue();
		boolean usingOSAuth=getUsingOSAuth(request).booleanValue();
		String name=getName(request);
		Long numIdioma= new Idioma().getNumIdioma(request);
		Locale locale= new Idioma().getLocale(numIdioma);
		String userDn=getUserDn(request);
		String idEntidad=getIdEntidad(request);



		result=(UseCaseConf) request.getSession().getAttribute(Keys.J_USECASECONF);

		if (result==null){
			result = new UseCaseConf();
		}


		if (useLdap && usingOSAuth){
			result.setLocale(locale);
			result.setUserDn(userDn);
			result.setUseLdap(new Boolean(useLdap));
			result.setUsingOSAuth(new Boolean(usingOSAuth));
			result.setEntidadId(idEntidad);
			result.setUserName(name);
		}else{

			if (name.length() != 0) {
				result.setLocale(locale);
				result.setEntidadId(idEntidad);
				result.setUseLdap(new Boolean(useLdap));
				result.setUserName(name);

			}
		}

		return result;
	}

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//Obtenemos la validación de si el usuario es interno
		boolean connectAsSuperUser;
		if("on".equalsIgnoreCase(request.getParameter("connectAsSuperUser"))){
			connectAsSuperUser = true;
		}else{
			connectAsSuperUser = false;
		}

		//Lugar donde redireccionamos la respuesta del action - por defecto siempre ira al login
		String redireccion = "login";

		//seteamos las cabeceras de respuesta
		setupResponse(response);

		//obtenemos el locale
		Idioma idioma = new Idioma();
		Long numIdioma= idioma.getNumIdioma(request);
		Locale locale = idioma.getLocale(numIdioma);
		//asignamos el locale recuperado
		setLocale(request, locale);

		UseCaseConf useCaseConf = null;

		String enabledIntercambioRegistral = Configurator.getInstance().getProperty(ConfigurationKeys.KEY_INTERCAMBIO_ENABLE_INTERCAMBIO_REGISTRAL);
		request.getSession().setAttribute("enabledIntercambioRegistral", enabledIntercambioRegistral);

		//Comprobamos si está configura la autenticación con ldap
		boolean isLdapAuthenticationPolicy = isAuthenticationLDAP();
		request.getSession().setAttribute(IS_LDAP_AUTHENTICATION_POLICY_SESSION_ATTR_KEY, isLdapAuthenticationPolicy);
		
		//comprobamos si estamos en un proceso de autenticacion
		if (isAuthProcess(request)) {
			try {
				//Seteamos datos para auditoria
				Utils.setAuditContext(request);

				//obtenemos el useCaseConf para el proceso de autenticacaion
				useCaseConf=getUseCaseConf(request);

				//comprobamos si el usuario ha indicado validación por usuario interno (SYSSUPERUSER)
				if(connectAsSuperUser){
					// validamos el usuario se autentica como usuario
					// interno de invesdoc (SYSSUPERUSER),
					// independientemente de la configuración LDAP
					getLogInvesdocInstance().loginSysSuperUser(request, useCaseConf);

					if(isAuthenticationLDAP()){
						// sistema LDAP
						useCaseConf.setUseLdap(new Boolean(true));
					}
				}else{
					// sino comprobamos el tipo de autenticación
					if (isAuthenticationLDAP()) {
						// sistema LDAP
						useCaseConf.setUseLdap(new Boolean(true));
						getLoginLDAPInstance().login(request, useCaseConf);
					}else{
						// sistema INVESDOC
						getLogInvesdocInstance().login(request, useCaseConf);
					}
				}

				request.getSession().setAttribute(IS_LDAP_AUTHENTICATION_POLICY_SESSION_ATTR_KEY, useCaseConf.getUseLdap());
				redireccion = calculateSuccess(useCaseConf);

			}catch (SecurityException e) {
				logger.fatal("Error de seguridad", e);
				ActionErrors errores = new ActionErrors();
				ActionError error = new ActionError("errors.detail", e.getMessage());
				errores.add("Error interno", error);
				saveErrors(request, errores);
				return mapping.findForward("error");
			} catch (ValidationException e) {
				logger.fatal("Error de validacion", e);
				ActionErrors errores = new ActionErrors();
				ActionError error = null;
				error = new ActionError("ieci.tecdoc.isicresadmin.login.exception.validation");
				errores.add("Error interno", error);
				saveErrors(request, errores);
				return mapping.findForward("error");
//				throw new ISicresRPAdminException(ISicresAdminDAOException.LOGIN_VALIDATION_EXCEPTION,e);
			} catch (Exception e) {
				//El usuario no puede autenticarse
				logger.fatal("Error en las comunicaciones", e);
				ActionErrors errores = new ActionErrors();
				ActionError error = null;
				error = new ActionError("ieci.tecdoc.isicresadmin.login.exception");
				errores.add("Error interno", error);
				saveErrors(request, errores);
				return mapping.findForward("error");
			}
		}
		return mapping.findForward(redireccion);
	}

	/**
	 * Método que informa si la autenticación es por LDAP
	 *
	 * @return boolean - TRUE - autenticacion LDAP / FALSE - Otro tipo de autenticacion
	 */
	private boolean isAuthenticationLDAP() {
		boolean result = false;

		String authenticationPolicyType = Configurator
				.getInstance()
				.getProperty(
						ConfigurationKeys.KEY_SERVER_AUTHENTICATION_POLICY_TYPE);

		if (StringUtils.equalsIgnoreCase(authenticationPolicyType,
				Keys.AUTHENTICATION_POLICY_TYPE_LDAP)) {
			result = true;
		}

		return result;

	}

	private String calculateSuccess(UseCaseConf useCaseConf) {
		String success = "success";

		try{
			boolean superUser = SecuritySession.isSuperuser(useCaseConf.getSessionID());
			if(!superUser)
			{
				CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(useCaseConf.getSessionID());
				ISicresGenPerms genPerms = (ISicresGenPerms) cacheBag.get(ServerKeys.GENPERMS_USER);
				if(genPerms.getCanModifyUsers())
				{
					success = "successUsuarios";
				}
				else if(genPerms.getCanModifyAdminUnits())
				{
					success = "successUnidades";
				}
				else if(genPerms.getCanModifyIssueTypes())
				{
					success = "successTiposAsunto";
				}
				else if(genPerms.getCanModifyReports())
				{
					success = "successInformes";
				}
				else
				{
					success = "successTiposTransporte";
				}
			}
		}catch (Exception e) {
			logger.error("Error comprobando los permisos para redireccion", e);
		}
		return success;
	}
	public static LoginLDAP getLoginLDAPInstance(){
		return new LoginLDAP();
	}

	public static LoginInvesdoc getLogInvesdocInstance(){
		return new LoginInvesdoc();
	}

	/**
	 * metodo para salvar en session los datos necesarios una vez autenticado con exito
	 * @param request
	 * @param useCaseConf
	 */
	protected static void saveSessionData(HttpServletRequest request, UseCaseConf useCaseConf){

		HttpSession session = request.getSession(true);
		session.setAttribute(Keys.J_USECASECONF, useCaseConf);
		session.setAttribute(Keys.J_USERNAME, getName(request));

		Idioma idioma = new Idioma();
		session.setAttribute(Keys.J_IDIOMA, idioma.getIdioma(request));
		session.setAttribute(Keys.J_NUM_IDIOMA, idioma.getNumIdioma(request));

		session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, useCaseConf.getEntidadId());
		session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_USUARIO, useCaseConf.getUserName());
		session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM, useCaseConf.getUserName());
		session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD, useCaseConf.getEntidadId());
	}


	/**
	 * metodo para salvar en session los datos necesarios una vez autenticado con exito
	 * @param request
	 * @param useCaseConf
	 */
	protected static void saveSessionData(HttpServletRequest request, UseCaseConf useCaseConf, boolean isSuperuser, ISicresGenPerms genPerms){

		saveSessionData(request,useCaseConf);
		HttpSession session = request.getSession(true);
		session.setAttribute(ServerKeys.GENPERMS_USER, genPerms);
		session.setAttribute("isSuperuser", isSuperuser);
		session.setAttribute(ServerKeys.IS_SICRES3, DefinicionLibroSicres3Utils.isSicres3Enabled());
	}

	/**
	 * Metodo para comprobar si el usuario que esta logueandose tiene permisos de administracion
	 * @param genPerms
	 * @return
	 */
	protected static boolean tienePermisosAdministracion(ISicresGenPerms genPerms)
	{
		return (genPerms.isCanModifyAdminUnits() ||
				genPerms.isCanModifyIssueTypes() ||
				genPerms.isCanModifyReports() ||
				genPerms.isCanModifyTransportTypes() ||
				genPerms.isCanModifyUsers());
	}

}
