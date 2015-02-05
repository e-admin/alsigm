package ieci.tdw.ispac.ispaccatalog.action.searchfrm;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaOrgDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeletePermissionObjectOrganizationAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
			   ActionForm form,
			   HttpServletRequest request, 
			   HttpServletResponse response, 
			   SessionAPI session) throws Exception	{

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT });

		SelectForm selectForm = (SelectForm)form;
		String[] uids = selectForm.getUids();
		String id = request.getParameter("id");
		
		CTFrmBusquedaOrgDAO searchFrmOrgDAO = new CTFrmBusquedaOrgDAO();
		searchFrmOrgDAO.deletePermission(session.getClientContext().getConnection(), id, uids);
		
		ActionForward forward = mapping.findForward("showCTSearchForm");
		return new ActionForward (forward.getName(), forward.getPath() + "?entityId=" + ICatalogAPI.ENTITY_CT_SEARCHFORM + "&regId=" +id, true);
	}
}
