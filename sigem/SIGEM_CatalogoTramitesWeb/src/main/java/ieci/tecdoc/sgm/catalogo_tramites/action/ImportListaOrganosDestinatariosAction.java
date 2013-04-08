package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: ListaOrganosDestinatariosAction.java,v 1.5.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para importar en los órganos destinatarios unidades administrativas de registro
 * @author IECISA
 *
 */
public class ImportListaOrganosDestinatariosAction extends CatalogoTramitesWebAction {

	private static final String USER_ACTION_SELECT_ORG_FORWARD = "selectOrg";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward(USER_ACTION_SELECT_ORG_FORWARD);
	}
	
}
