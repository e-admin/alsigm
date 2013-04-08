package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author antoniomaria_sanchez at ieci.es
 * @since 19/01/2009
 */
public class ShowSignDetailAction extends BaseAction {

	public static final Logger logger = Logger.getLogger(ShowSignDetailAction.class);
	

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		EntityForm defaultForm = (EntityForm) form;
		
		// Nombre del campo que contiene el valor de la búsqueda
		String field = request.getParameter("field");

		// Número de registro a buscar
		String documentId = defaultForm.getProperty(field);
		
		if (logger.isDebugEnabled()){
			logger.debug("Solicitando detalle de firma de un documento: " + documentId);
		}
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		ISignAPI singAPI = invesflowAPI.getSignAPI();
	
		List details = singAPI.showSignInfo(Integer.parseInt(documentId));
		request.setAttribute("details", details);
		
		return mapping.findForward("success");
	}

}
