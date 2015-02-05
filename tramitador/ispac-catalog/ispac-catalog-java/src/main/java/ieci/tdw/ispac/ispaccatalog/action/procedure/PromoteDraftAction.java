package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PromoteDraftAction extends BaseAction
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

		int pcdId = 0;
 		EntityForm defaultForm = (EntityForm) form;

 		String entityId = request.getParameter("entityId");
 		String sProcId = request.getParameter("regId");
 		String sGroupId = request.getParameter("groupId");

 		if(sProcId == null) sProcId = defaultForm.getKey();
 		if(sGroupId == null) sGroupId = defaultForm.getEntity();

		// Si no llegan los parámetros correctos informamos
		// al usuario y volvemos a la pantalla de origen.
 		if( (sGroupId == null) || (sProcId == null ))
 			throw new ISPACInfo("exception.info.inputParamsError");

 		try{
 		   pcdId = Integer.parseInt(sProcId);
		}catch(NumberFormatException e){
			throw new ISPACInfo("exception.info.inputParamsError");
		}

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI pcdAPI= invesFlowAPI.getProcedureAPI();
	   	IBPMAPI bpmAPI = null;
    	boolean commit = true;
		try{
			IItem itemProcedure = invesFlowAPI.getProcedure(pcdId);
    		bpmAPI = invesFlowAPI.getBPMAPI();
    		bpmAPI.installProcess(itemProcedure.getString("ID_PCD_BPM"));
			
			pcdAPI.markDraftAsCurrent(pcdId);

		
		} catch(ISPACInfo e) {
			commit = false;
			throw e;
		}catch(ISPACException e) {
			commit = false;
			throw new ISPACInfo("Error promoviendo procedimiento. Error: " + e.getMessage());
		}finally{
			if (bpmAPI != null)
				bpmAPI.closeBPMSession(commit);
		}			
		
		

//		ActionForward fwd = mapping.findForward("reloadProc");
		ActionForward fwd = mapping.findForward("success");
		String path = new StringBuffer(fwd.getPath())
			.append(fwd.getPath().indexOf("?") > 0 ? "&" : "?")
			.append("entityId=").append(entityId)
			.append("&regId=").append(sProcId)
			.toString();

		return new ActionForward(fwd.getName(), path, fwd.getRedirect());
	}
}