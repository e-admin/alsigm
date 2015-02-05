package ieci.tdw.ispac.ispaccatalog.action.publisher;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowPubRulesListAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception	{

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PUB_RULES_READ,
				ISecurityAPI.FUNC_PUB_RULES_EDIT });

 		EntityForm defaultForm = (EntityForm) form;

 		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		IPublisherAPI publisherAPI = invesFlowAPI.getPublisherAPI();

		int pcdId = Integer.parseInt(request.getParameter("pcdId"));
		int stageId = Integer.parseInt(request.getParameter("stageId"));
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		int docTypeId = Integer.parseInt(request.getParameter("typeDoc"));

		String method = request.getParameter("method");
		if ((!StringUtils.isEmpty(method)) && (method.equals("modRuleOrder"))) {
			modRuleOrder(request, session, pcdId, stageId, taskId, docTypeId);
		}

        IItemCollection itemcol = publisherAPI.getRules(pcdId, stageId, taskId, docTypeId);
        List pubruleslist = CollectionBean.getBeanList(itemcol);

        if (pubruleslist.isEmpty()) {
    		return new ActionForward("/showPubRulesGroupList.do", true);
        }

		// Formateador para la lista de eventos
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_PUB_RULES_EDIT)) {
			setFormatter(request, "PubRulesListFormatter", "/formatters/publisher/pubruleslistformatter.xml");
		} else {
			setFormatter(request, "PubRulesListFormatter", "/formatters/publisher/pubruleslistreadonlyformatter.xml");
		}

		// Establecer las descripciones para los eventos
        for (int i = 0; i < pubruleslist.size(); i++) {

            ItemBean item = (ItemBean) pubruleslist.get(i);
            item.setProperty("EVENT_NAME", getMessage(request, cct.getLocale(), "event.milestone.type." + item.getString("ID_EVENTO")));

            if (item.getString("ID_EVENTO").equals("8")) {
            	item.setProperty("INFO_NAME", getMessage(request, cct.getLocale(), "id.info.type." + item.getString("ID_INFO")));
            }
            else {
            	item.setProperty("INFO_NAME", getMessage(request, cct.getLocale(), "form.pubRule.msg.noSelected"));
            }
        }
   	 	request.setAttribute("PubRulesList",pubruleslist);

   	 	// Obtener las acciones para las descripciones en la vista
   	 	IItemCollection actionCollection = publisherAPI.getActions();
   	 	request.setAttribute("ActionsMap", actionCollection.toMapStringKey());

   	 	// Obtener las condiciones para las descripciones en la vista
   	 	IItemCollection conditionCollection = publisherAPI.getConditions();
   	 	request.setAttribute("ConditionsMap", conditionCollection.toMapStringKey());

   	 	// Obtener las aplicaciones para las descripciones en la vista
   	 	IItemCollection applicationCollection = publisherAPI.getApplications();
   	 	request.setAttribute("ApplicationsMap", applicationCollection.toMapStringKey());

   	 	// Descripción del procedimiento
   	 	if (pcdId == -1) {
   	 		defaultForm.setProperty("PCD_NAME", getMessage(request, cct.getLocale(), "form.pubRule.msg.all"));
   	 	} else {
	   	 	IItem procedure = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_PROCEDURE, pcdId);
	   	 	defaultForm.setProperty("PCD_NAME", procedure.getString("NOMBRE"));
	   	 	defaultForm.setProperty("PCD_NVERSION", procedure.getString("NVERSION"));
	   	 	defaultForm.setProperty("PCD_ESTADO", procedure.getString("ESTADO"));
   	 	}

   	 	// Descripción de la fase
   	 	if (stageId == -1) {
   	 		defaultForm.setProperty("STAGE_NAME", getMessage(request, cct.getLocale(), "form.pubRule.msg.all.a"));
   	 	} else if (stageId == 0) {
   	 		defaultForm.setProperty("STAGE_NAME", getMessage(request, cct.getLocale(), "form.pubRule.msg.noSelected"));
   	 	} else {
	   	 	IItem stage = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_STAGE, stageId);
	   	 	defaultForm.setProperty("STAGE_NAME", stage.getString("NOMBRE"));
   	 	}

   	 	// Descripción del trámite
   	 	if (taskId == -1) {
   	 		defaultForm.setProperty("TASK_NAME", getMessage(request, cct.getLocale(), "form.pubRule.msg.all"));
   	 	} else if (taskId == 0) {
   	 		defaultForm.setProperty("TASK_NAME", getMessage(request, cct.getLocale(), "form.pubRule.msg.noSelected"));
   	 	} else {
		 	IItem task = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_TASK, taskId);
		 	defaultForm.setProperty("TASK_NAME", task.getString("NOMBRE"));
   	 	}

   	 	// Descripción del tipo de documento
   	 	if (docTypeId == -1) {
   	 		defaultForm.setProperty("TYPEDOC_NAME", getMessage(request, cct.getLocale(), "form.pubRule.msg.all"));
   	 	} else if (docTypeId == 0) {
   	 		defaultForm.setProperty("TYPEDOC_NAME", getMessage(request, cct.getLocale(), "form.pubRule.msg.noSelected"));
   	 	} else {
		 	IItem docType = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_TYPEDOC, docTypeId);
		 	defaultForm.setProperty("TYPEDOC_NAME", docType.getString("NOMBRE"));
   	 	}

		request.setAttribute("pcdId", String.valueOf(pcdId));
		request.setAttribute("stageId", String.valueOf(stageId));
		request.setAttribute("taskId", String.valueOf(taskId));
		request.setAttribute("typeDoc", String.valueOf(docTypeId));

   	 	return mapping.findForward("success");
	}

	private void modRuleOrder(HttpServletRequest request,
						 	  SessionAPI session,
						 	  int pcdId,
						 	  int stageId,
						 	  int taskId,
						 	  int docTypeId) throws ISPACException {

		int id = Integer.parseInt(request.getParameter("regId"));
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		int infoId = Integer.parseInt(request.getParameter("infoId"));
        int order = Integer.parseInt(request.getParameter("order"));

        IInvesflowAPI invesFlowAPI = session.getAPI();
        IPublisherAPI publisherAPI = invesFlowAPI.getPublisherAPI();

		if (request.getParameter("ModOrden").equalsIgnoreCase("INC")) {
			publisherAPI.incRuleOrder(id, pcdId, stageId, taskId, docTypeId, eventId, infoId, order);
		} else {
			publisherAPI.decRuleOrder(id, pcdId, stageId, taskId, docTypeId, eventId, infoId, order);
		}
	}

}