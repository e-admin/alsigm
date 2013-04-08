package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ExitAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Ahora el formulario de búsqueda está en sesión y se mantienen los parámetros de la última búsqueda realizada
		request.getSession().removeAttribute(
				ActionsConstants.FORM_SEARCH_RESULTS);

		SessionAPI sesion = null;
		try {

			sesion = SessionAPIFactory.getSessionAPI(request, response);

		} catch (ISPACException e) {
			ActionMessages errors = new ActionMessages();
			errors.add("session", new ActionMessage("error.session"));
			saveMessages(request.getSession(), errors);
			return mapping.findForward("fail");
		}

		sesion.logout();

		return mapping.findForward("success");
	}

}