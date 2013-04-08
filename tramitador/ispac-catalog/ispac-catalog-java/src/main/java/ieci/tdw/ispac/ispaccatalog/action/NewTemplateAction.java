package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaclib.app.EntityApp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class NewTemplateAction extends BaseAction
{

 	public ActionForward executeAction(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response,
	SessionAPI session)
	throws Exception
	{
		EntityForm defaultForm = (EntityForm) form;

		int type = Integer.parseInt(request.getParameter("type"));

		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(ICatalogAPI.ENTITY_CT_TEMPLATE,
															   ISPACEntities.ENTITY_NULLREGKEYID,
															   getRealPath(""));

		entityapp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);
		entityapp.getItem().set("ID_TPDOC", type);

		String strURL = entityapp.getURL();

		// Visualiza los datos de la entidad
		defaultForm.setEntityApp(entityapp);

		defaultForm.setEntity(Integer.toString(ICatalogAPI.ENTITY_CT_TEMPLATE));

		// Página jsp asociada a la presentación de la entidad
		request.setAttribute("application", strURL);

		return mapping.findForward("success");
	}

}