package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;
import ieci.tdw.ispac.ispacweb.security.UserCredentials;
import ieci.tdw.ispac.ispacweb.security.UserCredentialsHelper;
import ieci.tdw.ispac.ispacweb.util.PathUtils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public abstract class BaseAction extends Action
{
 	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(BaseAction.class);

	// String del estado de la aplicación
	//protected static final String ATTR_STATETICKET="ATTR_STATETICKET";

    protected static final String ATTR_ACTIVE_SCREEN="ATTR_ACTIVE_SCREEN";

	protected static final String LAST_URL_SESSION_KEY="ISPAC_LAST_URL";
	private static final String ERROR_FORWARD_KEY="appError";
	public static final String APPLICATION_ERRORS="appErrors";
	public static final String APPLICATION_DEFAULT_ERROR_FORWARD = "error";

	// Content type
	private static final String CONTENT_TYPE = "text/html; charset=ISO-8859-15";

	
	/**
	 * This is the main action called from the Struts framework.
	 *
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		// Establecer el ContentType
		response.setContentType(CONTENT_TYPE);

		SessionAPI sesion = null;
		
		try {
			
			sesion = SessionAPIFactory.getSessionAPI(request, response);

		} catch (ISPACException e) {
			
			logger.warn("Error al obtener la sesión", e);
			
			ActionMessages messages = new ActionMessages();
			messages.add("session",new ActionMessage("error.session"));
			saveErrors(request,messages);
			return mapping.findForward("fail");
		}
		
		String username = sesion.getRespName();
		if (!StringUtils.equalsIgnoreCase(sesion.getUserName(), 
				sesion.getClientContext().getUser().getName())) {
			username = sesion.getUserName();
		}
		
		request.setAttribute("User", username);

		try {
			
			setAuditContext(request, sesion);
			ActionForward forward = executeAction(mapping, form, request, response, sesion);
			
			// Contexto para la vista
			// utilizado en el Tag de Calendario para obtener el idioma de la aplicación
			request.setAttribute("ClientContext", sesion.getClientContext());
			
			// Guardamos la ultima URL accedida correctamente.
			if ((forward != null) && (!forward.getRedirect())) {
				setLastURL(request);
			}
			
			return forward;
		}
		catch(ISPACInfo e) {
			
			// Obtener el recurso para el mensaje de error de la excepción
			saveAppErrors(request, getActionMessages(request, e));
			
			// Si no se ha definido la gestión de error para la acción actual
			// en el struts-config se vuelve a invocar la anterior.
			ActionForward actionForward = mapping.findForward(BaseAction.ERROR_FORWARD_KEY);
			if(actionForward != null)
			    return actionForward;
			
			//Si la acción anterior coincide con la actual
			//hemos entrado en un bucle: lanzamos una ISPACException
			//que será capturada por el manejador definido en el struts-config
			if(compareLastURL(request))
				throw new ISPACException("No se encuentra un forward válido para la gestión del error", e);

			return new ActionForward((String)request.getSession().getAttribute(BaseAction.LAST_URL_SESSION_KEY) );
		}
		finally {
			sesion.release();
		}
	}

	public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionAPI session)
			throws Exception;

	public String getPath() {
		return getRealPath("/");
	}

	public String getRealPath(String path) {
		return PathUtils.getRealPath(servlet, path);
	}

	public String getISPACPath() {
	    ISPACRewrite rewrite=new ISPACRewrite(servlet.getServletContext());
		return rewrite.getBasePath();
	}

	public String getISPACPath(String relativepath) {
	    ISPACRewrite rewrite=new ISPACRewrite(servlet.getServletContext());
		return rewrite.rewritePath(relativepath);
	}
/*
	public String getStateticket(HttpServletRequest request) {
		return (String)request.getAttribute(ATTR_STATETICKET);
	}
*/

	public UserCredentials getUserCredentials(HttpServletRequest request,
			ClientContext ctx) throws ISPACException {
		return UserCredentialsHelper.getUserCredentials(request, ctx);
	}

	public void saveAppErrors(HttpServletRequest request, ActionMessages errors){
		request.setAttribute(BaseAction.APPLICATION_ERRORS, errors);
	}
	public ActionMessages getAppErrors(HttpServletRequest request){
		return (ActionMessages)request.getAttribute(BaseAction.APPLICATION_ERRORS);
		
	}

	public ActionMessages getActionMessages(HttpServletRequest request, Exception e){
		ActionMessages errors = new ActionMessages();
		ActionMessage mensaje = null;
		
		if (e instanceof ISPACException) {
			
			ISPACException ie = (ISPACException) e;
			
			String msg = ie.getExtendedMessage(request.getLocale());
			if (msg != null) {
			
				msg = MessagesFormatter.format(msg, ie.getArgs());
			}
			
			mensaje = new ActionMessage("ispacexception", msg);
		}
		else {
			mensaje = new ActionMessage("ispacexception", e.getMessage());
		}
		
		errors.add(ActionMessages.GLOBAL_MESSAGE, mensaje);
		return errors;
	}

	private void setLastURL(HttpServletRequest request){
		request.getSession().setAttribute(BaseAction.LAST_URL_SESSION_KEY, getUrl(request));
	}

	private boolean compareLastURL(HttpServletRequest request){
		String lastURL = (String)request.getSession().getAttribute(BaseAction.LAST_URL_SESSION_KEY);
		String actual = getUrl(request);
		if(actual.equals(lastURL)){
			return true;
		}
		return false;
	}

	private String getUrl(HttpServletRequest request){
		StringBuffer sUrl = new StringBuffer(request.getServletPath());
		String sQueryString = request.getQueryString();
		if((sQueryString != null) && (!sQueryString.equals(""))){
			sUrl.append("?");
			sUrl.append(sQueryString);
		}
		return sUrl.toString();
	}

	protected void setFormatter(HttpServletRequest request, String name, 
			String path) throws ISPACException {
		
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath(path));
		request.setAttribute(name, formatter);
	}
	
	public String getMessage(HttpServletRequest request, Locale locale,
			String key) {
		return (getResources(request).getMessage(locale, key));
	}

	public String getMessage(HttpServletRequest request, Locale locale,
			String key, Object params[]) {
		return (getResources(request).getMessage(locale, key, params));
	}
   
	public void setActiveScreen(HttpServletRequest request, String activeScreen) {
		request.getSession().setAttribute(ATTR_ACTIVE_SCREEN, activeScreen);
	}
	
	public String getActiveScreen(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(ATTR_ACTIVE_SCREEN);
	}
	
	/**
	 * @param request
	 */
	private void setAuditContext(HttpServletRequest request, SessionAPI session) {
		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		auditContext.setUser(session.getUserName());
		auditContext.setUserId(session.getClientContext().getUser().getUID());
		AuditContextHolder.setAuditContext(auditContext);
	}

	
}