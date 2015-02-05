package ieci.tecdoc.sgm.usuario.admin.struts.action;

import ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario;
import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.usuario.admin.struts.UserAdminHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Acción encargada de la búsqueda de tipos de liquidaciones. 
 *
 */
public class CrearUsuarioAction extends AdminUsuariosWebAction {

	private static final Logger logger = Logger.getLogger(CrearUsuarioAction.class);
	
	private static final String USER_EXISTS_KEY =		"ieci.tecdoc.sgm.autenticacion.admin.struts.error1";
	private static final String SUCCESS_FORWARD =		"success";
	private static final String ERROR_FORWARD =			"error";
	
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
		
		DynaValidatorForm oForm = (DynaValidatorForm) form;

		DatosUsuario user = null;
		try {
			user= UserAdminHelper.getUser(request, oForm);
		} catch (Exception e) {
			logger.debug(e);
		}
		if(user != null){
			ActionErrors oError = new ActionErrors();
			oError.add(ActionErrors.GLOBAL_ERROR, new ActionError(USER_EXISTS_KEY));
			saveErrors(request, oError);
			return mapping.findForward(ERROR_FORWARD);
		}
		try {
			UserAdminHelper.createUser(request, oForm);
		} catch (AutenticacionUsuarioException e) {
			logger.error(e);
			return mapping.findForward(ERROR_FORWARD);
		}
		
		return mapping.findForward(SUCCESS_FORWARD);
	}
}