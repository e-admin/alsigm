package ieci.tecdoc.sgm.usuario.admin.struts.action;

import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.usuario.admin.struts.Constants;
import ieci.tecdoc.sgm.usuario.admin.struts.UserAdminHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Acción encargada de la búsqueda de tipos de liquidaciones. 
 *
 */
public class ModificarUsuarioAction extends AdminUsuariosWebAction {

	private static final Logger logger = Logger.getLogger(ModificarUsuarioAction.class);
	
	private static final String SUCCESS_FORWARD =		"success";
	
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

		try {
			UserAdminHelper.updateUser(request, oForm);
		} catch (AutenticacionUsuarioException e) {
			logger.error(e);
			return mapping.findForward(GLOBAL_FORWARD_ERROR);
		}
		request.setAttribute(Constants.USUARIO_USUARIO_FIELD, (String)oForm.get(Constants.USUARIO_USUARIO_FIELD));
		return mapping.findForward(SUCCESS_FORWARD);
	}
}