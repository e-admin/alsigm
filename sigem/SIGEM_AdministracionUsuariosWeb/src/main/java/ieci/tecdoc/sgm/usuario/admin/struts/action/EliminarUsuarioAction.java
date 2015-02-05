package ieci.tecdoc.sgm.usuario.admin.struts.action;

import ieci.tecdoc.sgm.usuario.admin.struts.UserAdminHelper;

import java.util.List;

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
public class EliminarUsuarioAction extends AdminUsuariosWebAction {

	private static final Logger logger = Logger.getLogger(EliminarUsuarioAction.class);
	
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
			UserAdminHelper.deleteUser(request, oForm);
		} catch (Exception e) {
		}

		return mapping.findForward(SUCCESS_FORWARD);
	}
}