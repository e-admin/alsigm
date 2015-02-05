package ieci.tdw.ispac.ispaccatalog.action.publisher;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeletePubRuleAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form, 
									   HttpServletRequest request,
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PUB_RULES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
        IPublisherAPI publisherAPI = invesFlowAPI.getPublisherAPI();

		// Formulario asociado a la regla
		EntityForm defaultForm = (EntityForm) form;

		int keyId = Integer.parseInt(defaultForm.getKey());

		// Información de la regla
		IItem rule = publisherAPI.getRule(keyId);
		
		// Eliminar la regla
		publisherAPI.deleteRule(keyId);
		
		String pcdId = request.getParameter("pcdId");
		if (!StringUtils.isEmpty(pcdId)) {
		
			return new ActionForward(new StringBuffer()
		    		.append("/showPubRulesList.do?pcdId=")
		    		.append(pcdId)
		    		.append("&stageId=")
		    		.append(rule.getString("ID_FASE"))
		    		.append("&taskId=")
		    		.append(rule.getString("ID_TRAMITE"))
		    		.append("&typeDoc=")
		    		.append(rule.getString("TIPO_DOC"))
		    		.toString(), true);
		} else {
			return new ActionForward("/showPubRulesGroupList.do", true);
		}
	}
	
}