/*
 * Created on 11-nov-2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
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

/**
 * @author alberto
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AddAppEntityPcdAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

        IClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		int itemId = Integer.parseInt(request.getParameter("pentId"));

		IItem item=catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_ENTITY, itemId);

		String pcdId = item.get("ID_PCD").toString();

		String appId=request.getParameter("appId");
		if (appId==null || appId.length()==0)
		    item.set("FRM_EDIT", (Object) null);
		else
		    item.set("FRM_EDIT", Integer.parseInt(appId));
		item.store(cct);

		ActionForward forward = mapping.findForward("success");
		String path = forward.getPath() + "?pcdId=" + pcdId;
		return new ActionForward( forward.getName(), path, true);
	}

}
