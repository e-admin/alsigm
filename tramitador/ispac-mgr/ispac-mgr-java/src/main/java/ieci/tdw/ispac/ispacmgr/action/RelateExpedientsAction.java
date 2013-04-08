package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RelateExpedientsAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		ClientContext cct = session.getClientContext();
		// Estado del contexto de tramitacion
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IInvesflowAPI invesflowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
		IState currentstate = managerAPI.currentState(getStateticket(request));
		String relation = request.getParameter("relation");
		String idsStage = request.getParameter("idsStage");
		idsStage = StringUtils.replace(idsStage, "-", ",");
		
		IItemCollection itemCol = entitiesAPI.queryEntities(SpacEntities.SPAC_FASES, "WHERE ID IN ("+idsStage+")");
		String numExpPadre = null;
		if (itemCol.next()){
			numExpPadre = itemCol.value().getString("NUMEXP");
		}
		
		IItem item;
		while(itemCol.next()){
			item = entitiesAPI.createEntity(SpacEntities.SPAC_EXP_RELACIONADOS);
			item.set("NUMEXP_PADRE", numExpPadre);
			item.set("NUMEXP_HIJO", itemCol.value().getString("NUMEXP"));
			item.set("RELACION", relation);
			item.store(cct);
		}
		return NextActivity.refresh(request, mapping, currentstate);
	}
}