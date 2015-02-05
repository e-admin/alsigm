package ieci.tdw.ispac.ispaccatalog.action.reports;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IReportsAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteResponsiblesReportAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
			   						   ActionForm form,
			   						   HttpServletRequest request, 
			   						   HttpServletResponse response, 
			   						   SessionAPI session) throws Exception	{
		
    	ClientContext cct = session.getClientContext();

    	// Comprobar si el usuario tiene asignadas las funciones adecuadas
    	FunctionHelper.checkFunctions(request, cct, new int[] {
    		ISecurityAPI.FUNC_COMP_REPORTS_EDIT });

    	IInvesflowAPI invesflowAPI = cct.getAPI();
    	IReportsAPI reportsAPI = invesflowAPI.getReportsAPI();
    	
		String id = request.getParameter("id");

		SelectForm selectForm = (SelectForm)form;
		String[] uids = selectForm.getUids();
		
		// Eliminar los responsables asociados al informe
		reportsAPI.deleteResponsiblesReport(id, uids);
		
		ActionForward forward = mapping.findForward("success");

		return new ActionForward(forward.getName(), 
								 forward.getPath() + "?entityId=" + ICatalogAPI.ENTITY_CT_INFORMES + "&regId=" + id, true);
	}
	
}