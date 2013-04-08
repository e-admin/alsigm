package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowCTReportsListAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_REPORTS_READ,
				ISecurityAPI.FUNC_COMP_REPORTS_EDIT });
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		String pattern = request.getParameter("property(criterio)");

		IItemCollection itemcol = catalogAPI.getCTReports(pattern);
		List itemBeans = CollectionBean.getBeanList(itemcol);

		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();

		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctreportslistformatter.xml"));
		request.setAttribute("CTReportsListFormatter", formatter);
		request.setAttribute("CTReportsList", itemBeans);

		return mapping.findForward("success");
	}

}
