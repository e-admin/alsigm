package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowBatchSignListAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		ClientContext cct = session.getClientContext();
		ISignAPI signAPI = session.getAPI().getSignAPI();
		///////////////////////////////////////////////
		// Estado de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState state = managerAPI.enterState(getStateticket(request), ManagerState.BATCHSIGNLIST, null);		

		IItemCollection itemcol = signAPI.getCircuitSetps(session.getClientContext().getRespId());

		List list = CollectionBean.getBeanList(itemcol);
		request.setAttribute(ActionsConstants.BATCH_SIGN_LIST, list);
		
	    ///////////////////////////////////////////////
	    // Menus
		request.setAttribute("menus", MenuFactory.getBatchSignListMenu(cct, state, getResources(request)));    		
		///////////////////////////////////////////////
	    // Formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/batchsignlistformatter.xml"));
		request.setAttribute("Formatter", formatter);
		
		return mapping.findForward("success");
	}
}
