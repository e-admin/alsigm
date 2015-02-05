package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.form.UploadForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowCTAppFormAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {
 		
 		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_READ,
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

		UploadForm uploadForm = (UploadForm) form;
		
 		// Obtener la aplicación
 		String id = request.getParameter("regId");
 		uploadForm.setKey(id);
 		
		IItem item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_APP, Integer.parseInt(id));
		String labelentity = request.getParameter("label");
		if (StringUtils.isBlank(labelentity)) {
			labelentity = item.getString("ENT_PRINCIPAL_NOMBRE");
		}

		request.setAttribute("Caption", getMessage(request, cct.getLocale(), "entityManage.title.show.form.caption", new String[] {item.getString("NOMBRE"), labelentity}));
		//request.setAttribute("Caption", getMessage(request, cct.getLocale(), "entityManage.title.show.form.caption", new String[] {item.getString("NOMBRE"), item.getString("ENT_PRINCIPAL_NOMBRE")}));
		//request.setAttribute("CaptionKey", "entityManage.title.show.form");

		return mapping.findForward("success");
	}
 	
}