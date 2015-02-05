package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteTemplateAction extends BaseAction
{
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form, 
									   HttpServletRequest request,
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception	{
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT,
				ISecurityAPI.FUNC_INV_TEMPLATES_EDIT});

		IInvesflowAPI invesFlowAPI = session.getAPI();
        IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
        ITemplateAPI templateAPI = invesFlowAPI.getTemplateAPI();
        
		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;

		int keyId = Integer.parseInt(defaultForm.getKey());
		int entityId = Integer.parseInt(defaultForm.getEntity());
		int type = Integer.parseInt(defaultForm.getProperty("ID_TPDOC"));

		
		// Comprobar si se puede eliminar
		if (procedureAPI.isInUse( entityId, keyId))
		{
			ActionForward forward = mapping.findForward("use");
			String action = forward.getPath() + "?entityId=" + entityId + "&keyId=" + keyId + "&type=" + type;
			return new ActionForward( forward.getName(), action, true);
		}
		
		//si la plantilla es de tipo especifica hay q eliminar registro de la tabla q asocia plantilla a procedimientos
		if (templateAPI.isProcedureTemplate(keyId)){
			templateAPI.deleteTemplateProc(keyId);
		} else {
			templateAPI.deleteTemplate(keyId);
		}

		ActionForward action = mapping.findForward("success");
		String redirected = action.getPath()
						  + "?type="
						  + defaultForm.getProperty("ID_TPDOC");
		return new ActionForward( action.getName(), redirected, true);					
	}
}