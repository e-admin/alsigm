package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: ShowProceduresAction.java,v 1.1.2.1 2008/02/29 09:33:51 jconca Exp $
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowProceduresAction extends CatalogoTramitesWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("success");
	}

}
