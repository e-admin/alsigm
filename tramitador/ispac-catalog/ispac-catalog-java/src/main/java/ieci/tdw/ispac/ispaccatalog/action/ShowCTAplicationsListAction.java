package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTAplicationsListAction extends BaseAction
{

 	public ActionForward executeAction(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response,
 			SessionAPI session)
 			throws Exception
	{
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		IItemCollection itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_APP,
		        (request.getParameter("property(criterio)")!=null?"WHERE NOMBRE LIKE '%" + DBUtil.replaceQuotes(request.getParameter("property(criterio)")) + "%'":null));
   	 	List ctapllist=CollectionBean.getBeanList(itemcol);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctaplicationslistformatter.xml"));
		request.setAttribute("CTEntityListFormatter", formatter);

   	 	request.setAttribute("CTEntityList", ctapllist);

   	 	return mapping.findForward("success");
	}
}