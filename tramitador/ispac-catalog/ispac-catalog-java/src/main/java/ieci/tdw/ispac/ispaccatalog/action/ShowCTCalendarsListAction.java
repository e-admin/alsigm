package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowCTCalendarsListAction extends BaseAction {
	
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
				ISecurityAPI.FUNC_COMP_CALENDARS_READ,
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		StringBuffer query = new StringBuffer();
		query.append(request.getParameter("property(criterio)")!=null?"WHERE NOMBRE LIKE '%" + DBUtil.replaceQuotes(request.getParameter("property(criterio)")) + "%'" : "");
		query.append(" ORDER BY ID ");
		
        
		IItemCollection itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SPAC_CALENDARIOS,query.toString());
		List itemBeans = CollectionBean.getBeanList(itemcol);
		request.setAttribute("CTCalendarsList", itemBeans);

		// Formateador para la lista de eventos
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_COMP_CALENDARS_EDIT)) {
			setFormatter(request, "CTCalendarsListFormatter", "/formatters/ctcalendarslistformatter.xml");
		} else {
			setFormatter(request, "CTCalendarsListFormatter", "/formatters/ctcalendarslistreadonlyformatter.xml");
		}

   	 	return mapping.findForward("success");
	}

}
