package ieci.tdw.ispac.ispaccatalog.action.cttask;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTTaskTPDocsAction extends BaseAction
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
				ISecurityAPI.FUNC_INV_DOCTYPES_READ,
				ISecurityAPI.FUNC_INV_DOCTYPES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String scttaskId=request.getParameter("cttaskId");
		String filtro=" WHERE CTTRTD.ID_TPDOC =CTTPDOC.ID  AND CTTRTD.ID_TRAMITE = "+scttaskId;
		HashMap tablemap=new HashMap();
		tablemap.put("CTTPDOC",new Integer(ICatalogAPI.ENTITY_CT_TYPEDOC));
		tablemap.put("CTTRTD",new Integer(ICatalogAPI.ENTITY_CT_TASKTYPEDOC));

		IItemCollection itemcol=catalogAPI.queryCTEntities(tablemap,filtro);
        List cttasklist=CollectionBean.getBeanList(itemcol);

        int cttaskId=Integer.parseInt(scttaskId);

        IItem cttask=catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_TASK,cttaskId);
        ItemBean cttaskbean=new ItemBean(cttask);
		request.setAttribute("CTTask", cttaskbean);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = null;

		// Establece el formateador de las fases del catálogo
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_TASKS_EDIT)) {
			formatter = factory.getFormatter(getISPACPath("/formatters/cttasktpdocsformatter.xml"));
		} else {
			formatter = factory.getFormatter(getISPACPath("/formatters/cttasktpdocsreadonlyformatter.xml"));
		}

		request.setAttribute("Formatter", formatter);
   	 	request.setAttribute("ItemList", cttasklist);

   	 	//Identificador para los enlaces del menu.
   	 	request.setAttribute("KeyId",scttaskId);

   	 	return mapping.findForward("success");
	}
}