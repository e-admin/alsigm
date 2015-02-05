package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowStageTPDocsListAction extends BaseAction
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
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String stageId = request.getParameter("StageId");
		String query = " WHERE PFSTD.ID_TPDOC = CTTPDOC.ID AND PFSTD.ID_FASE = " + stageId;
		HashMap map = new HashMap();
		map.put("CTTPDOC",new Integer(ICatalogAPI.ENTITY_CT_TYPEDOC));
		map.put("PFSTD",new Integer(ICatalogAPI.ENTITY_P_FSTD));

		IItemCollection collection = catalogAPI.queryCTEntities(map,query);
        List list = CollectionBean.getBeanList( collection);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/procedure/stagetpdocsformatter.xml"));

		request.setAttribute("ItemsListFormatter", formatter);
   	 	request.setAttribute("ItemsList", list);

   	 	return mapping.findForward("success");
	}
}