package ieci.tdw.ispac.ispaccatalog.action.entities;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AddEntityPcdAction extends BaseAction
{

 	public ActionForward executeAction(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response,
 			SessionAPI session)
 			throws Exception
	{
		IClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String sEntityId=request.getParameter("entityId");
		String sPcdId=request.getParameter("pcdId");
		if (sEntityId==null || sPcdId==null)
		    return mapping.findForward("success");

		int entityId=Integer.parseInt(sEntityId);
		int pcdId=Integer.parseInt(sPcdId);

		request.setAttribute("Refresh","true");

		//Si ya existe no se crea y se debe notificar al usuario.
		String sqlquery="WHERE ID_ENT = "+entityId+" AND ID_PCD ="+pcdId;
		int ncount=catalogAPI.countCTEntities(ICatalogAPI.ENTITY_P_ENTITY,sqlquery);

		ActionForward forward = mapping.findForward("success");
		String redirected = forward.getPath() + "?entityId=" + entityId + "&pcdId=" + sPcdId;
	    forward = new ActionForward( forward.getName(), redirected, true);

		if (ncount>0)
		    return forward;

		//Se calcula el orden para la nueva entidad
		IItem item=catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_ENTITY);
		item.set("ID_ENT", entityId);
		item.set("ID_PCD", pcdId);
		item.set("ORDEN",  item.getKeyInt());
		item.set("TP_REL", 0);
		item.store(cct);

		return forward;
	}
}
