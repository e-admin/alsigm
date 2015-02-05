package ieci.tdw.ispac.ispaccatalog.action.publisher;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
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

public class ShowPubErrorMilestonesListAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception	{

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PUB_MILESTONES_READ,
				ISecurityAPI.FUNC_PUB_MILESTONES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		IPublisherAPI publisherAPI = invesFlowAPI.getPublisherAPI();

        IItemCollection itemcol = publisherAPI.getMilestones(IPublisherAPI.MILESTONE_STATUS_ERROR);
        List puberrormilestoneslist=CollectionBean.getBeanList(itemcol);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/publisher/puberrormilestoneslistformatter.xml"));
		request.setAttribute("PubErrorMilestonesListFormatter", formatter);

   	 	request.setAttribute("PubErrorMilestonesList",puberrormilestoneslist);
   	 	
   	 	// Obtener los procedimientos para las descripciones en la vista
   	 	IItemCollection procedureCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_PROCEDURE, null);
   	 	request.setAttribute("ProceduresMap", procedureCollection.toMapStringKey());
   	 	
   	 	// Obtener las fases para las descripciones en la vista
   	 	IItemCollection stageCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_STAGE, null);
   	 	request.setAttribute("StagesMap", stageCollection.toMapStringKey());
   	 	
   	 	// Obtener los trámmites para las descripciones en la vista
   	 	IItemCollection taskCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_TASK, null);
   	 	request.setAttribute("TasksMap", taskCollection.toMapStringKey());
   	 	
   	 	// Obtener los tipos de documentos para las descripciones en la vista
   	 	IItemCollection typeDocCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TYPEDOC, null);
   	 	request.setAttribute("TypeDocsMap", typeDocCollection.toMapStringKey()); 

   	 	return mapping.findForward("success");
	}
 	
}