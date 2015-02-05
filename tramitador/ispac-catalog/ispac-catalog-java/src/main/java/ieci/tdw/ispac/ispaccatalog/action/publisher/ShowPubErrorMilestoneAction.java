package ieci.tdw.ispac.ispaccatalog.action.publisher;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.app.PubErrorMilestoneApp;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowPubErrorMilestoneAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception	{

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PUB_MILESTONES_READ,
				ISecurityAPI.FUNC_PUB_MILESTONES_EDIT });

		EntityForm defaultForm = (EntityForm) form;
 		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		IPublisherAPI publisherAPI = invesFlowAPI.getPublisherAPI();
		
		int id = Integer.parseInt(request.getParameter("id"));
		int applicationId = Integer.parseInt(request.getParameter("applicationId"));
		String systemId = request.getParameter("systemId");
		
        //IItem milestone = publisherAPI.getMilestone(id, applicationId, systemId, IPublisherAPI.MILESTONE_STATUS_ERROR);
		IItem milestone = publisherAPI.getMilestone(id, applicationId, systemId);

        EntityApp entityapp = new PubErrorMilestoneApp(session.getClientContext());
        entityapp.setMainEntity(ICatalogAPI.ENTITY_PUB_MILESTONES);
        entityapp.setItem(milestone);
        entityapp.initiate();
        
        defaultForm.setEntityApp(entityapp);
        
		// Página jsp asociada a la presentación
		request.setAttribute("application", entityapp.getURL());

		// Generamos la ruta de navegación hasta la pantalla actual
		BreadCrumbsContainer bcm = BreadCrumbsManager.getInstance(catalogAPI).getBreadCrumbs(request);
 		request.setAttribute("BreadCrumbs", bcm.getList());
        
   	 	return mapping.findForward("success");
	}
 	
}