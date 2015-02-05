package ieci.tecdoc.sgm.usuario.admin.struts.action;

import ieci.tecdoc.sgm.usuario.admin.struts.AuthenticationHelper;
import ieci.tecdoc.sgm.usuario.admin.struts.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Acción de entrada a la aplicación de pago
 */
public class LoginAction extends Action {
	
	public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";	

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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaValidatorForm oForm = (DynaValidatorForm)form;
		String cUser = null;
		String cPass = null;
		if(oForm != null){
			cUser = (String)oForm.get(Constants.LOGIN_FORM_USER_FIELD);
			cPass = (String)oForm.get(Constants.LOGIN_FORM_PASS_FIELD);			
		}
		if(!AuthenticationHelper.validateUserPassword(cUser, cPass)){
			return mapping.findForward(GLOBAL_FORWARD_LOGIN);
		}
		AuthenticationHelper.authenticateUser(request);
		
		return mapping.findForward(SUCCESS_FORWARD);
	}
}