package ieci.tdw.ispac.ispaccatalog.action;

import java.util.List;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IReportsAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.form.UploadForm;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowReportAction extends BaseAction
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
				ISecurityAPI.FUNC_COMP_REPORTS_READ,
				ISecurityAPI.FUNC_COMP_REPORTS_EDIT });

		int entityId = 0;
 		int keyId = 0;
 	
 		String parameter;

		UploadForm defaultForm = (UploadForm) form;
		

		parameter = request.getParameter("entityId");
 		if (parameter == null)
 		{
 			parameter = defaultForm.getEntity();
 		}
 		entityId = Integer.parseInt(parameter);

		parameter = request.getParameter("regId");
 		if (parameter == null)
 		{
 			parameter = defaultForm.getKey();
 		}
 		keyId = Integer.parseInt(parameter);

 		defaultForm.setEntity( Integer.toString(entityId));
		defaultForm.setKey(Integer.toString(keyId));
	
		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
     
        EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));

		if (keyId==ISPACEntities.ENTITY_NULLREGKEYID)
		    entityapp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);

	

		ISPACRewrite ispacPath = new ISPACRewrite(getServlet().getServletContext());
		String strURL = ispacPath.rewriteRelativePath("common/empty.jsp");
		strURL = entityapp.getURL();
		
		// Visualiza los datos de la entidad
		if (defaultForm.getActions() == null ||
			    defaultForm.getActions().equals("success"))
				defaultForm.setEntityApp(entityapp);

		// Página jsp asociada a la presentación de la entidad
		request.setAttribute("application", strURL);
		request.setAttribute("EntityId",Integer.toString(entityId));
		request.setAttribute("KeyId", Integer.toString(keyId));
		
		// Generamos la ruta de navegación hasta la pantalla actual.
		BreadCrumbsContainer bcm = BreadCrumbsManager.getInstance(catalogAPI).getBreadCrumbs(request);
 		request.setAttribute("BreadCrumbs", bcm.getList());
 		
 		ClientContext cct = session.getClientContext();

    	IInvesflowAPI invesflowAPI = cct.getAPI();
    	IReportsAPI reportsAPI = invesflowAPI.getReportsAPI();
		
		IItem informe = defaultForm.getEntityApp().getItem();
		
		
		// Responsables asignados al informe
		List permissionObjectOrganizactionList = reportsAPI.getReportOrganization(informe.getKeyInt());
        
		// Cargar el formateador de la lista de responsables
   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/supervision/supervisionformatter.xml"));
		request.setAttribute("CTReportOrgListFormatter", formatter);

   	 	request.setAttribute("CTReportOrgList", permissionObjectOrganizactionList);			
		
 		
 				
		return mapping.findForward("success");
	}

}