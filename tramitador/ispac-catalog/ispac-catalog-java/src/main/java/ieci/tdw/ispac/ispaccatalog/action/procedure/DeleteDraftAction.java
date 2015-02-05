package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteDraftAction extends BaseAction
{
 	public ActionForward executeAction(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response,
 			SessionAPI session)
 			throws Exception
	{

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		int entityId = 0;
 		int pcdId = 0;
 		int groupId = 0;
 		EntityForm defaultForm = (EntityForm) form;

 		String sEntityId = request.getParameter("entityId");
 		String sProcId = request.getParameter("regId");
 		String sGroupId = request.getParameter("groupId");

 		if(sProcId == null) sProcId = defaultForm.getKey();
 		if(sGroupId == null) sGroupId = defaultForm.getEntity();

		// Si no llegan los parámetros correctos informamos
		// al usuario y volvemos a la pantalla de origen.
 		if( (sGroupId == null) || (sProcId == null )) {
 				throw new ISPACInfo("Par&aacute;metros de entrada incorrectos.");
 		}
		try{
			entityId = Integer.parseInt(sEntityId);
			pcdId = Integer.parseInt(sProcId);
			groupId = Integer.parseInt(sGroupId);
		}catch(NumberFormatException e){
			throw new ISPACInfo("Par&aacute;metros de entrada incorrectos.");
		}

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI pcdAPI= invesFlowAPI.getProcedureAPI();

		try{
			pcdAPI.deleteProcedure(pcdId);
		}catch(ISPACInfo e){
			throw e;
		}catch(ISPACException e){
			throw new ISPACInfo("Error eliminando procedimiento. Error: " + e.getMessage());
		}

		return getActionForward(mapping, request, session, entityId, groupId);
	}
 	
 	private ActionForward getActionForward(ActionMapping mapping,
 			HttpServletRequest request, SessionAPI session, int entityId,
 			int groupId) throws ISPACException {
 		
 		int regId = getLastPcdId(session, groupId);
 		String url;
 		
 		if (regId > 0) {
			ActionForward fwd = mapping.findForward("pcd");
			url = new StringBuffer(request.getContextPath())
				.append(fwd.getPath())
				.append(fwd.getPath().indexOf("?") > 0 ? "&" : "?")
				.append("entityId=").append(entityId)
				.append("&regId=").append(regId)
				.toString();
 		} else {
 			String forwardName = (entityId == ICatalogAPI.ENTITY_P_PROCEDURE ? 
 					"pcds" : "subPcds");
 			
			ActionForward fwd = mapping.findForward(forwardName);
			url = new StringBuffer(request.getContextPath())
				.append(fwd.getPath()).toString();
 		}
 		
		request.setAttribute("target", "top");
		request.setAttribute("url", url);
		
		return mapping.findForward("loadOnTarget");
 		
 	}
 	
 	private int getLastPcdId(SessionAPI session, int groupId) 
 			throws ISPACException {
 		
 		int pcdId = -1;
 		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		String whereClause = new StringBuffer(" WHERE NVERSION=(SELECT MAX(NVERSION) FROM SPAC_P_PROCEDIMIENTOS WHERE ID_GROUP=")
			.append(groupId).append(") AND ID_GROUP=").append(groupId)
			.toString();

		IItemCollection itemcol = catalogAPI.queryCTEntities(
				ICatalogAPI.ENTITY_P_PROCEDURE, whereClause);
 		if (itemcol != null) {
 			if (itemcol.next()) {
 				IItem item = itemcol.value();
 				pcdId = item.getInt("ID");
 			}
 		}
 		
		return pcdId;
 	}
}