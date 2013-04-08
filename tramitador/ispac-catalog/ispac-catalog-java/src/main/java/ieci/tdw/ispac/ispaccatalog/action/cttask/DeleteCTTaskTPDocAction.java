package ieci.tdw.ispac.ispaccatalog.action.cttask;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class DeleteCTTaskTPDocAction extends BaseAction
{
 	public ActionForward executeAction(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response,
 			SessionAPI session)
 			throws Exception
	{
		ClientContext cct = session.getClientContext();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_DOCTYPES_EDIT });
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String stasktpdocId=request.getParameter("tasktpdocId");
		int tasktpdocId=Integer.parseInt(stasktpdocId);

		IItem  item=catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_TASKTYPEDOC,tasktpdocId);
		String cttaskid=item.getString("ID_TRAMITE");
		item.delete(cct);

		ActionForward action=mapping.findForward("success");
		String newpath = action.getPath()+ "?cttaskId="+cttaskid;
		return new ActionForward( action.getName(), newpath, true);
	}
}