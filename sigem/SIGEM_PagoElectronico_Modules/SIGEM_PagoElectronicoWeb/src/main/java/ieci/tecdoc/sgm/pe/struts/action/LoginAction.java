package ieci.tecdoc.sgm.pe.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción de entrada a la aplicación de pago
 */
public class LoginAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(LoginAction.class);	
	private static final String SUCCESS_FORWARD = "success";
	
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward(SUCCESS_FORWARD);
	}
}