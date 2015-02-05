package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.impl.SessionAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Página de inicio
 *
 */
public class HomeAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
   	 	return mapping.findForward("success");
	}
}