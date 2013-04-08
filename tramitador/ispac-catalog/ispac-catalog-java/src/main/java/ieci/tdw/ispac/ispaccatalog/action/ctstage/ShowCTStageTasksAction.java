package ieci.tdw.ispac.ispaccatalog.action.ctstage;

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


public class ShowCTStageTasksAction extends BaseAction
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
				ISecurityAPI.FUNC_INV_STAGES_READ,
				ISecurityAPI.FUNC_INV_STAGES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String sctstageId=request.getParameter("ctstageId");
		String filtro=" WHERE CTFSTR.ID_TRAMITE =CTTASK.ID  AND CTFSTR.ID_FASE = "+sctstageId;

		HashMap tablemap=new HashMap();
		tablemap.put("CTTASK",new Integer(ICatalogAPI.ENTITY_CT_TASK));
		tablemap.put("CTFSTR",new Integer(ICatalogAPI.ENTITY_CT_STAGETASK));

		IItemCollection itemcol=catalogAPI.queryCTEntities(tablemap,filtro);
        List cttasklist=CollectionBean.getBeanList(itemcol);

        int ctstageId=Integer.parseInt(sctstageId);

        IItem stage=catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_STAGE,ctstageId);
        ItemBean stagebean=new ItemBean(stage);
		request.setAttribute("CTStage", stagebean);
		
   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = null;
		
		// Formateador para la lista de eventos
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_STAGES_EDIT)) {
			formatter = factory.getFormatter(getISPACPath("/formatters/ctstagetasksformatter.xml"));			
		} else {
			formatter = factory.getFormatter(getISPACPath("/formatters/ctstagetasksreadonlyformatter.xml"));
		}

		request.setAttribute("Formatter", formatter);
   	 	request.setAttribute("ItemList", cttasklist);

   	 	//Identificador para los enlaces del menu.
   	 	request.setAttribute("KeyId",sctstageId);

   	 	return mapping.findForward("success");
	}
}