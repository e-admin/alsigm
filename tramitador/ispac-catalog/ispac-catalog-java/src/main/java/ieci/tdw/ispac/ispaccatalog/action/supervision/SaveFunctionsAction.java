/*
 * Created on Oct 25, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaccatalog.action.supervision;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.frmsupervision.SaveFunctionsForm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveFunctionsAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception	{
				
	    SaveFunctionsForm functionsForm = (SaveFunctionsForm)form;
	    
	    // Uid del responsable seleccionado
	    String uid = request.getParameter("uid");
	    
	    updateFunctions(session, uid, functionsForm);

	    ActionForward forward = mapping.findForward("showInfoEntry");
	    return new ActionForward (forward.getName(), forward.getPath() + "?" + request.getQueryString(), true);
	}

	private void updateFunctions(SessionAPI session,
								 String uid, SaveFunctionsForm form) throws ISPACException {
		
		ISecurityAPI securityAPI = session.getAPI().getSecurityAPI();

		// Funciones seleccionadas
		Map selectedFunctions = new HashMap();
		
		if (form.getCreatePcd()) {
			selectedFunctions.put(new Integer(ISecurityAPI.FUNC_CREATEPCD), null);
		}
		
		if (form.getMonitorizeSystem()) {
			selectedFunctions.put(new Integer(ISecurityAPI.FUNC_SYSTEMMONITORIZE), null);
		}
		
		if (form.isEnterCatalog()) {
			selectedFunctions.put(new Integer(ISecurityAPI.FUNC_ENTERCATALOG), null);
		}
		
		if (form.isMonitoringSupervisor()) {
			selectedFunctions.put(new Integer(ISecurityAPI.FUNC_MONITORINGSUPERVISOR), null);
		}
		
		if (form.isTotalSupervisor()) {
			selectedFunctions.put(new Integer(ISecurityAPI.FUNC_TOTALSUPERVISOR), null);
		}

		// Funciones relacionadas con el inventario
		
		if (form.isReadProcedures()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_PROCEDURES_READ), null);
		}
		if (form.isEditProcedures()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_PROCEDURES_EDIT), null);
		}
		if (form.isReadStages()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_INV_STAGES_READ), null);
		}
		if (form.isEditStages()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_INV_STAGES_EDIT), null);
		}
		if (form.isReadTasks()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_INV_TASKS_READ), null);
		}
		if (form.isEditTasks()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_INV_TASKS_EDIT), null);
		}
		if (form.isReadDocTypes()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_DOCTYPES_READ), null);
		}
		if (form.isEditDocTypes()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_DOCTYPES_EDIT), null);
		}
		if (form.isReadTemplates()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_TEMPLATES_READ), null);
		}
		if (form.isEditTemplates()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_TEMPLATES_EDIT), null);
		}
		if (form.isReadSubprocesses()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_SUBPROCESS_READ), null);
		}
		if (form.isEditSubprocesses()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_SUBPROCESS_EDIT), null);
		}
		if (form.isReadSignCircuits()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_READ), null);
		}
		if (form.isEditSignCircuits()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT), null);
		}

		// Funciones relacionadas con los componentes
		
		if (form.isReadCompEntities()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_ENTITIES_READ), null);
		}
		if (form.isEditCompEntities()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_ENTITIES_EDIT), null);
		}
		if (form.isReadCompValidationTables()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_READ), null);
		}
		if (form.isEditCompValidationTables()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT), null);
		}
		if (form.isReadCompHierarchicalTables()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_READ), null);
		}
		if (form.isEditCompHierarchicalTables()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT), null);
		}
		if (form.isReadCompRules()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_COMP_RULES_READ), null);
		}
		if (form.isEditCompRules()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_COMP_RULES_EDIT), null);
		}
		if (form.isReadCompSearchForms()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_SEARCH_FORMS_READ), null);
		}
		if (form.isEditCompSearchForms()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT), null);
		}
		if (form.isReadCompCalendars()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_CALENDARS_READ), null);
		}
		if (form.isEditCompCalendars()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_CALENDARS_EDIT), null);
		}
		if (form.isReadCompReports()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_REPORTS_READ), null);
		}
		if (form.isEditCompReports()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_REPORTS_EDIT), null);
		}
		if (form.isReadCompSystemVars()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_SYSTEM_VARS_READ), null);
		}
		if (form.isEditCompSystemVars()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_COMP_SYSTEM_VARS_EDIT), null);
		}
		if (form.isReadCompHelps()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_COMP_HELPS_READ), null);
		}
		if (form.isEditCompHelps()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_COMP_HELPS_EDIT), null);
		}

		// Funciones relacionadas con el publicador
		
		if (form.isReadPubActions()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_PUB_ACTIONS_READ), null);
		}
		if (form.isEditPubActions()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_PUB_ACTIONS_EDIT), null);
		}
		if (form.isReadPubApplications()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_PUB_APPLICATIONS_READ), null);
		}
		if (form.isEditPubApplications()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_PUB_APPLICATIONS_EDIT), null);
		}
		if (form.isReadPubConditions()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_PUB_CONDITIONS_READ), null);
		}
		if (form.isEditPubConditions()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_PUB_CONDITIONS_EDIT), null);
		}
		if (form.isReadPubRules()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_PUB_RULES_READ), null);
		}
		if (form.isEditPubRules()) {
			selectedFunctions.put(
					new Integer(ISecurityAPI.FUNC_PUB_RULES_EDIT), null);
		}
		if (form.isReadPubMilestones()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_PUB_MILESTONES_READ), null);
		}
		if (form.isEditPubMilestones()) {
			selectedFunctions.put(new Integer(
					ISecurityAPI.FUNC_PUB_MILESTONES_EDIT), null);
		}

		// Funciones relacionadas con la gestión de permisos
		
		if (form.isReadPermissions()) {
			selectedFunctions.put(new Integer(ISecurityAPI.FUNC_PERM_READ),
					null);
		}
		if (form.isEditPermissions()) {
			selectedFunctions.put(new Integer(ISecurityAPI.FUNC_PERM_EDIT),
					null);
		}

		// Actualizar las funciones
		if (selectedFunctions.isEmpty()) {
			securityAPI.deleteFunctions(uid);
		} else {
			securityAPI.changeFunctions(uid, selectedFunctions);
		}
	}
	
}