package ieci.tecdoc.sgm.usuario.admin.struts.action;

import ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario;
import ieci.tecdoc.sgm.usuario.admin.struts.Constants;
import ieci.tecdoc.sgm.usuario.admin.struts.UserAdminHelper;
import ieci.tecdoc.sgm.usuario.datatypes.Usuario;

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
public class ConsultaUsuarioAction extends AdminUsuariosWebAction {

	private static final Logger logger = Logger.getLogger(ModificarUsuarioAction.class);
	
	private static final String SUCCESS_FORWARD =		"success";
	private static final String ERROR_FORWARD =			"error";
	private static final String USER_KEY =				"usuario";
	
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
		String cIdUser = (String)oForm.get(Constants.USUARIO_USUARIO_FIELD);
		if((cIdUser == null) || ("".equals(cIdUser))){
			cIdUser = (String)request.getAttribute(Constants.USUARIO_USUARIO_FIELD);
		}
		// obtenemos los datos del usuario
		DatosUsuario oUser = null;
		try {
			oUser = UserAdminHelper.getUser(request, oForm);
		} catch (Exception e) {
			return mapping.findForward(GLOBAL_FORWARD_ERROR);
		}
		request.setAttribute(USER_KEY, oUser);

		return mapping.findForward(SUCCESS_FORWARD);
	}
}