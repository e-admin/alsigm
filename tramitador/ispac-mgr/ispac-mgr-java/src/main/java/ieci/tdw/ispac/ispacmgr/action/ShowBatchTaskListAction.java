package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowBatchTaskListAction extends BaseAction  {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		//obtener el listado de agrupaciones
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();

		IWorklistAPI workListAPI = invesFlowAPI.getWorkListAPI();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);

		//Se establece el estado
		Map params = request.getParameterMap();
		IState newState = managerAPI.enterState(getStateticket(request), ManagerState.BATCHTASKLIST, params);
		storeStateticket(newState,response);

        // Cargar el contexto de la cabecera (miga de pan)
        SchemeMgr.loadContextHeader(newState, request, getResources(request), session);

        // Establecer el menu
	    request.setAttribute("menus", MenuFactory.getBatchTaskListMenu(cct, newState, getResources(request)));

	    IItemCollection itcBatchTask = null;
		itcBatchTask = workListAPI.getBatchTasks();
		
		List batchTaskList = CollectionBean.getBeanList(itcBatchTask);
		request.setAttribute(ActionsConstants.BATCH_TASK_LIST,batchTaskList);

		return mapping.findForward("success");		
	}

}