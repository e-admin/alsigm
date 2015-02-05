package ieci.tdw.ispac.ispaccatalog.action.publisher;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACIsInUseException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeletePubConditionAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form, 
									   HttpServletRequest request,
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_PUB_CONDITIONS_EDIT });
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IPublisherAPI publisherAPI = invesFlowAPI.getPublisherAPI();

		// Formulario asociado a la condición
		EntityForm defaultForm = (EntityForm) form;

		int conditionId = Integer.parseInt(defaultForm.getKey());
		
		try {
			publisherAPI.deleteCondition(conditionId);
		} catch (ISPACIsInUseException e) {
			throw new ISPACInfo(getMessage(request, cct.getLocale(), "form.pubCondition.msg.noDeleted"));
		}
	
		return mapping.findForward("success");
	}
		
}