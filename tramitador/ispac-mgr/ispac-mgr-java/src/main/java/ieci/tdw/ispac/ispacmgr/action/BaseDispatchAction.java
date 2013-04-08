package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * Clase abstracta que añade la funcionalidad de la clase DispatchAction de
 * Struts.
 *
 */
public abstract class BaseDispatchAction extends BaseAction {

    protected Class clazz;
    protected static Logger log = Logger.getLogger(BaseDispatchAction.class);
    protected static MessageResources messages = 
    	MessageResources.getMessageResources("org.apache.struts.actions.LocalStrings");
    protected HashMap methods;
    protected Class types[];

    
    /**
     * Constructor.
     *
     */
    public BaseDispatchAction() {
        this.clazz = getClass();
        this.methods = new HashMap();
        this.types = (new Class[] {
            ActionMapping.class, 
            ActionForm.class, 
            HttpServletRequest.class, 
            HttpServletResponse.class,
            SessionAPI.class
        });
    }


	/**
	 * Acción principal de la acción
	 * @param mapping Objeto ActionMapping.
	 * @param form Objeto ActionForm.
	 * @param request Petición HTTP que se está procesando.
	 * @param response Respuesta HTTP que se está procesando.
	 * @param session Información de la sesión.
	 */
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, 
			SessionAPI session) throws Exception {

		
		setAuditContext(request, session);
		
        if (isCancelled(request)) {
			ActionForward af = cancelled(mapping, form, request, response, 
					session);
			if (af != null) {
				return af;
			}
		}
        
		String parameter = mapping.getParameter();
		if (parameter == null) {
			String message = messages.getMessage("dispatch.handler", 
					mapping.getPath());
			log.error(message);
			throw new ServletException(message);
		}
		
		String name = getMethodName(mapping, form, request, response, parameter);
		if (StringUtils.isEmpty(name))
			name = "defaultExecute";
		
		if ("execute".equals(name) || "perform".equals(name)) {
			String message = messages.getMessage("dispatch.recursive", 
					mapping.getPath());
			log.error(message);
			throw new ServletException(message);
		} else {
			return dispatchMethod(mapping, form, request, response, session, 
					name);
		}
    }

    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
    			HttpServletRequest request, HttpServletResponse response,
    			SessionAPI session)
    		throws Exception {
        String message = messages.getMessage("dispatch.parameter", 
        		mapping.getPath(), mapping.getParameter());
        log.error(message);
        throw new ServletException(message);
    }

    protected ActionForward cancelled(ActionMapping mapping, ActionForm form, 
    			HttpServletRequest request, HttpServletResponse response,
    			SessionAPI session)
        	throws Exception {
        return null;
    }

    protected ActionForward dispatchMethod(ActionMapping mapping, 
    			ActionForm form, HttpServletRequest request, 
    			HttpServletResponse response, SessionAPI session, String name) 
    		throws Exception {
    	
        if(name == null) {
            return unspecified(mapping, form, request, response, session);
        }
        
        Method method = null;
        try {
			method = getMethod(name);
		} catch (NoSuchMethodException e) {
			String message = messages.getMessage("dispatch.method", 
					mapping.getPath(), name);
			log.error(message, e);
			throw e;
		}
		
		ActionForward forward = null;
		try {
			Object args[] = { mapping, form, request, response, session };
			forward = (ActionForward) method.invoke(this, args);
		} catch (ClassCastException e) {
			String message = messages.getMessage("dispatch.return", 
					mapping.getPath(), name);
			log.error(message, e);
			throw e;
		} catch (IllegalAccessException e) {
			String message = messages.getMessage("dispatch.error", 
					mapping.getPath(), name);
			log.error(message, e);
			throw e;
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof Exception) {
				throw (Exception) t;
			} else {
				String message = messages.getMessage("dispatch.error", 
						mapping.getPath(), name);
				log.error(message, e);
				throw new ServletException(t);
			}
		}
        return forward;
    }

    protected Method getMethod(String name) throws NoSuchMethodException {
    	
        Method method = (Method) methods.get(name);
        if(method == null) {
            method = clazz.getMethod(name, types);
            methods.put(name, method);
        }
        return method;
    }

    protected String getMethodName(ActionMapping mapping, ActionForm form, 
    		HttpServletRequest request, HttpServletResponse response, 
    		String parameter) throws Exception {
        return request.getParameter(parameter);
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