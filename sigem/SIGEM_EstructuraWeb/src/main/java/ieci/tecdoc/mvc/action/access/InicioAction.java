package ieci.tecdoc.mvc.action.access;

import ieci.tecdoc.mvc.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción de entrada a la aplicación de pago
 */
public class InicioAction extends BaseAction {
	
	private static final Logger logger = Logger.getLogger(LoginAction.class);	
	//public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";
	private static final String SUCCESS_FORWARD = "success";
	

	
	protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward(SUCCESS_FORWARD);
	}
}