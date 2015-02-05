package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.SearchForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;
import ieci.tdw.ispac.ispacweb.util.PathUtils;
import ieci.tdw.ispac.ispacweb.util.RequestUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

public abstract class BaseAction extends Action
{
 	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(BaseAction.class);

//	// String del estado de la aplicación
//	protected static final String ATTR_STATETICKET = "ATTR_STATETICKET";
	
	// Fichero de recursos de la aplicación
	protected static final String BUNDLE_NAME = "ieci.tdw.ispac.ispacmgr.resources.ApplicationResources";
	
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
			saveMessages(request.getSession(),messages);
			
			return mapping.findForward("fail");
		}
		
		String username = sesion.getRespName();
		request.setAttribute("User", username);
		try {
			ActionMessages errors = (ActionMessages) request.getSession().getAttribute(Globals.ERROR_KEY);
			if ((errors != null) && (!errors.isEmpty())) {
				saveErrors(request, errors);
				request.getSession().removeAttribute(Globals.ERROR_KEY);
			}
			setAuditContext(request, sesion);
			ActionForward forward = executeAction(mapping, form, request, response, sesion);
			
			// Contexto para la vista
			// utilizado en el Tag de Calendario para obtener el idioma de la aplicación
			request.setAttribute("ClientContext", sesion.getClientContext());
			
			return forward;
		}
		catch(ISPACInfo e) {
			String refresh="false";
			request.setAttribute(Globals.MESSAGE_KEY,e);
			request.getSession().setAttribute("infoAlert", e);
			request.setAttribute("refresh", refresh);
			if(e.isRefresh()){
				IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(sesion.getClientContext());
				IState currentstate = managerAPI.currentState(getStateticket(request));
				refresh = NextActivity.refresh(request, mapping, currentstate).getPath();
				request.setAttribute("refresh", refresh);
				return mapping.findForward("refresh");
			}
			else{
				
				return mapping.findForward("noRefresh");
			}
				
		}
		finally {
			sesion.release();
		}
	}

//	public void setOrganizationInfo(HttpServletRequest request){
//		OrganizationUserInfo info =(OrganizationUserInfo)request.getSession()
//			.getAttribute(ScopeConstants.ORGANIZATION_USER_INFO);
//		if (info != null) {
//			OrganizationUser.setOrganizationUserInfo(info);
//		}
//	}

	public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionAPI session)
			throws Exception;

	protected void storeStateticket(IState iState,HttpServletResponse response)
	throws ISPACException {
		String newStateticket = iState.getTicket();
    	Cookie cookieInfo = new Cookie("contextInfo", newStateticket);
		response.addCookie(cookieInfo);
	}

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

	public String getStateticket(HttpServletRequest request) {
		return (String)request.getAttribute(SessionAPIFactory.ATTR_STATETICKET);
	}
	
	public void setStateticket(HttpServletRequest request, IState iState)
	throws ISPACException {
		request.setAttribute(SessionAPIFactory.ATTR_STATETICKET, iState.getTicket());
	}
	
//	public void setStateticket(HttpServletRequest request, String stateticket)
//	throws ISPACException {
//		request.setAttribute(ATTR_STATETICKET, stateticket);
//	}
	
	public ActionForward composeActionForward(ActionForm form, String nameAction, HashSet ignoredParams){
		ActionForward ret = new ActionForward();
		
		String url = RequestUtil.getPath(nameAction, getMap(form), ignoredParams);
		ret.setPath(url);
		ret.setRedirect(true);
		return ret;
		
	}
	
	private Map getMap(ActionForm frm)
	{
		Map map = new HashMap();
		
		Field [] fields = frm.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
			map.put(fields[i].getName(), getAttributeValue(frm, fields[i].getName()));

		return map;
	}
	private Object getAttributeValue(ActionForm frm,String attribute)
	{
		Object value = null;
		
		try
		{
			if ( (attribute != null) && (attribute.length() > 0) )
			{
				Method method = findMethod(frm, "get".concat(attribute));
				if (method != null)
					value = method.invoke(frm, null);
			}
		}
		catch(Exception e)
		{
			value = null;
		}
		
		return value;
	}
	
	/**
	 * Obtiene un método.
	 * @param name Nombre del método.
	 * @return Método.
	 */
	private Method findMethod(ActionForm frm,String name)
	{
		Method[] methods = frm.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) 
			if (methods[i].getName().equalsIgnoreCase(name))
				return methods[i];
		return null;
	}
	
 	public String getDisplayTagOrderParams(HttpServletRequest request) throws Exception {
 		
 		String displayTagOrderParams = "";
        Enumeration paramNames = request.getParameterNames();
        
        while (paramNames.hasMoreElements()) {
        	
        	String name = (String) paramNames.nextElement();
            
        	if ((name != null) && 
        		(name.startsWith("d-"))) {
        		
        		String value = request.getParameter(name);
        		String param = name + "=" + value;
        		
        		if (StringUtils.isEmpty(displayTagOrderParams)) {
        			displayTagOrderParams = param;
        		}
        		else {
        			displayTagOrderParams = displayTagOrderParams +  "&" + param;
        		}
        	}
        }
        
        return displayTagOrderParams;
 	}

	protected void setQuery(SearchInfo searchinfo, SearchForm searchForm)
			throws ISPACException {
		
		Map values = searchForm.getValuesMap();
		Map operators = searchForm.getOperatorsMap();
		Set valueKeys = values.keySet();
		Iterator iter = valueKeys.iterator();

		while (iter.hasNext()) {
			String key = (String) iter.next();
			String[] keys = key.split(":");
			String value = (String) values.get(key);
			String operator = (String) operators.get(key);
			searchinfo.setFieldValueForEntity(keys[0], keys[1], value);
			searchinfo.setFieldOperatorForEntity(keys[0], keys[1], operator);
		}
	}
	
	public String getMessage(HttpServletRequest request, Locale locale, String key) {
		
		MessageResources resources = getResources(request);
		if ((resources != null) && resources.isPresent(locale, key)) {
			return (resources.getMessage(locale, key));
		}
		
		return key;
	}
		
	public String getMessage(HttpServletRequest request, Locale locale, String key, Object params[]) {
		
		MessageResources resources = getResources(request);
		if ((resources != null) && resources.isPresent(locale, key)) {
			return (resources.getMessage(locale, key, params));
		}
		
		return key;
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