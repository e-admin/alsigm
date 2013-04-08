package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExpedientIntrayAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		IInvesflowAPI invesFlowAPI = session.getAPI();

		// Listado de procedimientos instanciables por el usuario
		IWorklistAPI workListAPI = invesFlowAPI.getWorkListAPI();
		IItemCollection collection = workListAPI.getCreateProcedures();
		request.setAttribute("InstProcedureList", 
				CollectionBean.getBeanList(collection));
		
		// Presentación de los datos
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory
				.getFormatter(getISPACPath("/digester/instancedformatter.xml"));
		request.setAttribute("FormatterInstProcedure", formatter);

		return mapping.findForward("success");
	}
}
