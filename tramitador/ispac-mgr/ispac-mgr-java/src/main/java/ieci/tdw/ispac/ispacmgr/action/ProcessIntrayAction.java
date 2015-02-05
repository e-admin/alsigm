package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISearchAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import java.io.File;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProcessIntrayAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
    	ClientContext cct = session.getClientContext();
    	
    	IInvesflowAPI invesflowAPI = session.getAPI();

		ISPACRewrite ispacPath = new ISPACRewrite(getServlet().getServletContext());
		
		String xml = ispacPath.rewriteRealPath("xml/IntrayForm.xml");
		String xsl = ispacPath.rewriteRealPath("xsl/SearchForm.xsl");
		
		//////////////////////////////////////////////
		// Formulario de búsqueda
		ISearchAPI searchAPI = invesflowAPI.getSearchAPI();
		
		String frm = searchAPI.buildHTMLSearchForm(new File(xml), xsl, ResourceBundle.getBundle(BUNDLE_NAME, cct.getLocale()), cct.getLocale(), request.getParameterMap());
		request.setAttribute("Form", frm);

		return mapping.findForward("success");
	}

}