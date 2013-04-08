package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.form.UploadForm;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowTemplateAction extends BaseAction
{

 	public ActionForward executeAction(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response,
 			SessionAPI session)
 			throws Exception
	{
 		int entityId = 0;
 		int keyId = 0;
 		int type = 0;
 		String tipo;

 		String parameter;

		UploadForm defaultForm = (UploadForm) form;
		
		parameter = request.getParameter("tipo");
 		if (parameter == null)
 		{
 			parameter = defaultForm.getProperty("TIPO");
 		}
		tipo = parameter;
		
		parameter = request.getParameter("type");
 		if (parameter == null)
 		{
 			parameter = defaultForm.getProperty("ID_TPDOC");
 		}
		type = Integer.parseInt(parameter);

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
		defaultForm.setReadonly("false");
		defaultForm.setProperty("tipo", tipo);

		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
     
        EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));

		if (keyId==ISPACEntities.ENTITY_NULLREGKEYID)
		    entityapp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);

		entityapp.getItem().set("ID_TPDOC", type);

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
 		
 		String sKey = request.getParameter("key");
 		boolean isEspecifica = false; 
 		if (sKey!=null){
 			ITemplateAPI templateAPI = session.getAPI().getTemplateAPI();
 	 		int numProc = templateAPI.countTemplatesProcedure(Integer.parseInt(request.getParameter("key")));
 	 		if (numProc>0)
 	 			request.setAttribute("NUM_PROC", new Integer(numProc));
 	 		
 	 		//calcular si es específica o genérica
 	        int id = Integer.parseInt(sKey);
 	        if(templateAPI.isProcedureTemplate(id))
 	        	isEspecifica = true;
 	    }
 		else {
 			if (tipo.equals("especifica"))
 				isEspecifica = true;
 		}
 		
		if (isEspecifica)
 			entityapp.setProperty("PROCEDURE_TEMPLATE", getResources(request).getMessage("template.especifica"));
 		else
 			entityapp.setProperty("PROCEDURE_TEMPLATE", getResources(request).getMessage("template.generica"));
		
		return mapping.findForward("success");
	}

}