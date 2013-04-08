package com.ieci.tecdoc.isicres.servlets.core;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.IdiomaUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.Utils;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;
import com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;



/**
 * Clase de ayuda al login.jsp
 * @author Iecisa
 * @version $Revision$
 *
 */
public class LoginJspHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoginJspHelper.class);

	public static final String APPID_KEY="AppId";
	public static final String ARCHIVEID_KEY="ArchiveId";
	public static final String FOLDERID_KEY="FolderId";
	public static final String DISTID_KEY="DistId";
	public static final String USELDAP_KEY="UseLDAP";
	public static final String USINGOSAUTH_KEY="UsingOSAuth";
	public static final String PASSWORDCTRL_KEY="PasswordCtrl";
	public static final String NAMECTRL_KEY="NameCtrl";
	public static final String USERDN_KEY="UserDn";
	public static final String TRIESCTRL_KEY="TriesCtrl";

	protected LoginJspHelper(){
		super();
	}

	public  Long getAppId(HttpServletRequest request){
		Long result=RequestUtils.parseRequestParameterAsLong(request,APPID_KEY);
		return result;
	}

	public  Integer getArchiveId(HttpServletRequest request){
		Integer result = RequestUtils.parseRequestParameterAsInteger(request, ARCHIVEID_KEY, new Integer(0));
		return result;
	}

	public  Integer getFolderId(HttpServletRequest request){
		Integer result=RequestUtils.parseRequestParameterAsInteger(request, FOLDERID_KEY, new Integer(-1));
		return result;
	}

	public  Integer getDistId(HttpServletRequest request){
		Integer result=RequestUtils.parseRequestParameterAsInteger(request, DISTID_KEY, new Integer(-1));
		return result;
	}

	public  Boolean getUseLdap(HttpServletRequest request){
		Boolean result = RequestUtils.parseRequestParameterAsBoolean(request, USELDAP_KEY, Boolean.FALSE);
		return result;

	}
	public  Boolean getUsingOSAuth(HttpServletRequest request){
		Boolean result = RequestUtils.parseRequestParameterAsBoolean(request, USINGOSAUTH_KEY, Boolean.FALSE);
		return result;

	}

	/**
	 * obtiene el Password para autenticarse
	 * @param request
	 * @return
	 */
	public  String getPasswordCrypt(HttpServletRequest request){
		String result = RequestUtils.parseRequestParameterAsString(request, PASSWORDCTRL_KEY);
		return result;
	}

	/**
	 * obtiene el nombre para autenticarse
	 * @param request
	 * @return
	 */
	public  String getName(HttpServletRequest request){
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
	 * Obtiene el numero de reintentos de autenticacion
	 * @param request
	 * @return
	 */
	public  int  getTriesCtrl(HttpServletRequest request){
		int result = RequestUtils.parseRequestParameterAsint(request,TRIESCTRL_KEY);
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
	 * Metodo invocado desde login.jsp para realizar el proceso de login
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	public  void doWork(ServletRequest req, ServletResponse res) throws IOException {

		HttpServletResponse response=(HttpServletResponse)res;
		HttpServletRequest request=(HttpServletRequest) req;
		PrintWriter out = response.getWriter();

		SecurityUseCase securityUseCase = new SecurityUseCase();


		//seteamos las cabeceras de respuesta
		setupResponse(response);

		//obtenemos el locale
		Locale locale = IdiomaUtils.getInstance().getLocale(
				IdiomaUtils.getInstance().getNumIdioma(request));

		// El campo error nos va a indicar el tipo de error ha mostar al usuario.
		// Los tipos de errores pueden ser: usuario bloqueado, password incorrecto, login invalido, etc.
		String error = "0";


		int tries = 0;
		UseCaseConf useCaseConf = null;


		//comprobamos si estamos en un proceso de autenticacion
		if (isAuthProcess(request)) {

			//no se sabe para que es este map
			Map persistFields = new HashMap();
			try {
				//Seteamos datos para auditoria
				Utils.setAuditContext(request);

				//obtenemos el useCaseConf para el proceso de autenticacaion
				useCaseConf=getUseCaseConf(request);

				String name=getName(request);
				String passwordCrypt=getPasswordCrypt(request);
				tries = getTriesCtrl(request);
				useCaseConf.setPassword(passwordCrypt);

				// Validación del usuario.
				securityUseCase.login(useCaseConf, name, passwordCrypt);


				Integer archiveId = getArchiveId(request);
				Integer folderId = getFolderId(request);

				//comprobamos si la pagina destino es la bandeja de distribucion
				// si es, abrimos el libro correspodiente y obtenemos los permisos
				if (isOpenDistribucion(request)) {
					BookUseCase bookUseCase = new BookUseCase();
					bookUseCase.openBook(useCaseConf, archiveId);
					bookUseCase.getPermsRegisterDistPen(useCaseConf,archiveId, folderId);
				}

				// Una vez validado el usuario se introduce en la sesión diversos parametros.
				saveSessionData(request, useCaseConf, persistFields);

				//redireccion
				success(request,response,useCaseConf);

			} catch (SecurityException e) {
				logger.fatal("Error de seguridad", e);
				error = "1";
				ResponseUtils.generateJavaScriptError(out, e);
				if (e.getCode().equals(
						SecurityException.ERROR_PASSWORD_INCORRECT)) {
					logger.error("Password incorrecta", e);
					tries++;
				}
			} catch (BookException e) {
				logger.fatal("Error en el libro", e);
				error = "1";
				ResponseUtils.generateJavaScriptError(out, e);
			} catch (ValidationException e) {
				logger.fatal("Error de validacion", e);
				error = "1";
				ResponseUtils.generateJavaScriptLog(out, RBUtil
						.getInstance(locale).getProperty(
								Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
			} catch (Exception e) {
				logger.fatal("Error en las comunicaciones", e);
				error = "1";
				ResponseUtils.generateJavaScriptLog(out, RBUtil
						.getInstance(locale).getProperty(
								Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
			}
		}// del if de inicio de autenticacion

	}


	/**
	 * metodo para salvar en session los datos necesarios una vez autenticado con exito
	 * @param request
	 * @param useCaseConf
	 * @param persistFields
	 */
	protected void saveSessionData(HttpServletRequest request, UseCaseConf useCaseConf,Map persistFields){

		HttpSession session = request.getSession(true);
		session.setAttribute(Keys.J_USECASECONF, useCaseConf);
		session.setAttribute(Keys.J_USERNAME, getName(request));
		session.setAttribute(Keys.J_IDIOMA, IdiomaUtils.getInstance().getIdioma(request));
		session.setAttribute(Keys.J_NUM_IDIOMA, IdiomaUtils.getInstance().getNumIdioma(request));
		session.setAttribute(Keys.J_PERSISTFIELDS, persistFields);

	}


	/**
	 * Comprobamos si debemos abrir la ventana de la bandeja de distribucion
	 * @param request
	 * @return
	 */
	public boolean isOpenDistribucion(HttpServletRequest request){

		boolean result=false;

		 Integer archiveId = getArchiveId(request);
		result=(archiveId.intValue() != 0);

		return result;
	}



	/**
	 * Metodo invocado para redirigir a la web destion cuando el login se efectuo con exito en la jsp login.jsp
	 *
	 * @param request
	 * @param response
	 * @param useCaseConf
	 * @throws IOException
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	protected  void success(HttpServletRequest request, HttpServletResponse response,UseCaseConf useCaseConf) throws IOException, BookException, SessionException, ValidationException{

		Integer archiveId = getArchiveId(request);
		Integer folderId=getFolderId(request);
		Integer distId = getDistId(request);
		String caseSensitive=getCaseSensitive(useCaseConf);
		String enabledIntercambioRegistral = Configurator.getInstance().getProperty(ConfigurationKeys.KEY_INTERCAMBIO_ENABLE_INTERCAMBIO_REGISTRAL);

		Boolean canSendToIntercambioRegistral = Boolean.FALSE;
		try{
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					useCaseConf.getSessionID());
			ISicresGenPerms permisos = (ISicresGenPerms)cacheBag.get(ServerKeys.GENPERMS_USER);
			canSendToIntercambioRegistral =permisos.canAccessRegInterchange();
		}catch (TecDocException e) {
				//logger.error("Error al obtener el permiso de intermcabio registral. Se deshabilita.",e);
			}

		PrintWriter out = response.getWriter();

		// si es el procedimiento normal de login se abre el frame mainfrm_iframes.htm
		if (!isOpenDistribucion(request)) {
			out.write("<script language=\"javascript\">");
			out.write("top.g_SessionPId = \""
					+ useCaseConf.getSessionID() + "\";");
			out.write("top.g_CaseSensitive = \"" + caseSensitive
					+ "\";");
			out.write("top.g_EnabledIntercambioRegistral = \""
					+ enabledIntercambioRegistral + "\";");
			out.write("top.g_canSendIntercambioRegistral = \""
					+ Boolean.toString(canSendToIntercambioRegistral) + "\";");
			out
					.write("window.open(top.g_URL + \"/mainfrm_iframes.htm\", \"Main\",\"location=no\",true);");
			out.write("</script>");
		} else {
			//si se le pasa como parametro un id de archivador mostramos las distribuciones
			out.write("<script language=\"javascript\">");
			out.write("top.g_CaseSensitive = \"" + caseSensitive
					+ "\";");
			out.write("top.OpenFolderPenDtr(" + folderId.toString()
					+ "," + archiveId.toString() + ","
					+ distId.toString() + ")");
			out.write("</script>");

		}
	}

	/**
	 *  Comprobamos si es case sensitive
	 * @param useCaseConf
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	protected String getCaseSensitive(UseCaseConf useCaseConf) throws BookException, SessionException, ValidationException{

		String result="";

		if ((useCaseConf != null) && (useCaseConf.getSessionID() != null)	&& (useCaseConf.getEntidadId() != null)) {

			SessionInformation sessionInformation = BookSession.getSessionInformation(useCaseConf.getSessionID(), useCaseConf.getLocale(), useCaseConf.getEntidadId());
			result = sessionInformation.getCaseSensitive();
		}
		return result;
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
		Long numIdioma= IdiomaUtils.getInstance().getNumIdioma(request);
		Locale locale= IdiomaUtils.getInstance().getLocale(numIdioma);
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
}
