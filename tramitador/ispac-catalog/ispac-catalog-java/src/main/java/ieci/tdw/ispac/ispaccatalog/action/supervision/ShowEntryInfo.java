package ieci.tdw.ispac.ispaccatalog.action.supervision;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaccatalog.action.frmsupervision.SaveFunctionsForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.resp.Responsible;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowEntryInfo extends DirectoryAction {

	
	public static String SOLAPA_FUNCIONES_SISTEMA="1";
	public static String SOLAPA_SUPERVISION="2";
	public static String SOLAPA_SUSTITUTOS="3";
	
	public ActionForward directoryExecuteAction(ActionMapping mapping, 
												ActionForm form,
												HttpServletRequest request, 
												HttpServletResponse response,
												SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_READ,
				ISecurityAPI.FUNC_PERM_EDIT });
		
		// Se obtiene el responsable del cual calcular sus propiedades o privilegios
		IResponsible resp = null;
		IInvesflowAPI invesflowapi= session.getAPI();
		IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();

	  	String uid = request.getParameter("uid");
	  	if (uid == null)
	  	{
	  		//resp = (Responsible) respAPI.getRespFromDN(respAPI.getRootContext());
	  		resp =  respAPI.getRootResp();
	  		uid = resp.getUID();
	  		request.setAttribute("uid", uid);
	  	}
	  	else
	  		resp = (Responsible) respAPI.getResp(uid);

		// Permisos o Propiedades: por defecto, privilegios
		String entryinfo = getInfoEntry(request, response);
		if (entryinfo == null	|| !(entryinfo.equals(Constants.PROPERTIES)
				|| entryinfo.equals(Constants.PRIVILEGES)))
			entryinfo = Constants.PRIVILEGES;

		request.setAttribute("entryinfo", entryinfo);
		if (entryinfo.equals(Constants.PROPERTIES))
		{
			// Añadimos informacion con los atributos de la entrada
			LinkedHashMap attributes = respAPI.getRespProperties(uid);
			if (attributes != null)
				request.setAttribute("attributes", attributes);
		}
		else {
			
			List followedModeSupervisedsList = new ArrayList();
			List totalModeSupervisedsList = new ArrayList();
			List substitutesList = new ArrayList();
			
			String tab = request.getParameter("block");
			if (tab==null || tab.length()==0) tab = "1";
			if (SOLAPA_FUNCIONES_SISTEMA.equals(tab)) {
				
				IItemCollection functions = respAPI.getFunctions(uid);
				saveFunctionsInRequest(request, functions);
			}
			else if (SOLAPA_SUPERVISION.equals(tab)) {
				
				IItemCollection followedModeSuperviseds = respAPI.getFollowedModeSuperviseds(uid);
				followedModeSupervisedsList = CollectionBean.getBeanList(followedModeSuperviseds);
				Collections.sort(followedModeSupervisedsList, Responsible.getRespComparator());
				
				IItemCollection totalModeSuperviseds = respAPI.getTotalModeSuperviseds(uid);
				totalModeSupervisedsList = CollectionBean.getBeanList(totalModeSuperviseds);
				Collections.sort(totalModeSupervisedsList, Responsible.getRespComparator());
				
	            BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/supervision/supervisionformatter.xml"));
	            request.setAttribute("SupervisionFormatter", formatter);
			}
			else if (SOLAPA_SUSTITUTOS.equals(tab)) {
				
				// Lista de sustitutos
	            IItemCollection itemCollSustitutes = respAPI.getSustitutesAssets(uid);
	            substitutesList = CollectionBean.getBeanList(itemCollSustitutes);
	            
	            BeanFormatter sustitutoFormatter = factory.getFormatter(getISPACPath("/formatters/supervision/substitutionformatter.xml"));
	            request.setAttribute("SustitutoFormatter", sustitutoFormatter);
			}
			
			request.setAttribute("followedModeSuperviseds", followedModeSupervisedsList);
			request.setAttribute("totalModeSuperviseds", totalModeSupervisedsList);
			request.setAttribute("SUSTITUTOS_LIST", substitutesList);
		}
		
		return mapping.findForward("success");
	}

	private void saveFunctionsInRequest(HttpServletRequest request, 
										IItemCollection functions) throws ISPACException {
		
		SaveFunctionsForm form = new SaveFunctionsForm ();
		
		while (functions.next()) {
			
			IItem function = functions.value();
			int functionCode = function.getInt("FUNCION");
			
			switch (functionCode) {
			
				case ISecurityAPI.FUNC_CREATEPCD:
					form.setCreatePcd(true);
					break;

				case ISecurityAPI.FUNC_SYSTEMMONITORIZE:
					form.setMonitorizeSystem(true);
					break;

				case ISecurityAPI.FUNC_ENTERCATALOG:
					form.setEnterCatalog(true);
					break;

				case ISecurityAPI.FUNC_MONITORINGSUPERVISOR:
					form.setMonitoringSupervisor(true);
					break;

				case ISecurityAPI.FUNC_TOTALSUPERVISOR:
					form.setTotalSupervisor(true);
					break;
					
				case ISecurityAPI.FUNC_INV_PROCEDURES_READ:
					form.setReadProcedures(true);
					break;
					
				case ISecurityAPI.FUNC_INV_PROCEDURES_EDIT:
					form.setEditProcedures(true);
					break;
					
				case ISecurityAPI.FUNC_INV_STAGES_READ:
					form.setReadStages(true);
					break;
					
				case ISecurityAPI.FUNC_INV_STAGES_EDIT:
					form.setEditStages(true);
					break;

				case ISecurityAPI.FUNC_INV_TASKS_READ:
					form.setReadTasks(true);
					break;

				case ISecurityAPI.FUNC_INV_TASKS_EDIT:
					form.setEditTasks(true);
					break;

				case ISecurityAPI.FUNC_INV_DOCTYPES_READ:
					form.setReadDocTypes(true);
					break;

				case ISecurityAPI.FUNC_INV_DOCTYPES_EDIT:
					form.setEditDocTypes(true);
					break;

				case ISecurityAPI.FUNC_INV_TEMPLATES_READ:
					form.setReadTemplates(true);
					break;

				case ISecurityAPI.FUNC_INV_TEMPLATES_EDIT:
					form.setEditTemplates(true);
					break;

				case ISecurityAPI.FUNC_INV_SUBPROCESS_READ:
					form.setReadSubprocesses(true);
					break;

				case ISecurityAPI.FUNC_INV_SUBPROCESS_EDIT:
					form.setEditSubprocesses(true);
					break;

				case ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_READ:
					form.setReadSignCircuits(true);
					break;

				case ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT:
					form.setEditSignCircuits(true);
					break;

				case ISecurityAPI.FUNC_COMP_ENTITIES_READ:
					form.setReadCompEntities(true);
					break;

				case ISecurityAPI.FUNC_COMP_ENTITIES_EDIT:
					form.setEditCompEntities(true);
					break;

				case ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_READ:
					form.setReadCompValidationTables(true);
					break;

				case ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT:
					form.setEditCompValidationTables(true);
					break;

				case ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_READ:
					form.setReadCompHierarchicalTables(true);
					break;

				case ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT:
					form.setEditCompHierarchicalTables(true);
					break;

				case ISecurityAPI.FUNC_COMP_RULES_READ:
					form.setReadCompRules(true);
					break;

				case ISecurityAPI.FUNC_COMP_RULES_EDIT:
					form.setEditCompRules(true);
					break;

				case ISecurityAPI.FUNC_COMP_SEARCH_FORMS_READ:
					form.setReadCompSearchForms(true);
					break;

				case ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT:
					form.setEditCompSearchForms(true);
					break;

				case ISecurityAPI.FUNC_COMP_CALENDARS_READ:
					form.setReadCompCalendars(true);
					break;

				case ISecurityAPI.FUNC_COMP_CALENDARS_EDIT:
					form.setEditCompCalendars(true);
					break;

				case ISecurityAPI.FUNC_COMP_REPORTS_READ:
					form.setReadCompReports(true);
					break;

				case ISecurityAPI.FUNC_COMP_REPORTS_EDIT:
					form.setEditCompReports(true);
					break;

				case ISecurityAPI.FUNC_COMP_SYSTEM_VARS_READ:
					form.setReadCompSystemVars(true);
					break;

				case ISecurityAPI.FUNC_COMP_SYSTEM_VARS_EDIT:
					form.setEditCompSystemVars(true);
					break;

				case ISecurityAPI.FUNC_COMP_HELPS_READ:
					form.setReadCompHelps(true);
					break;

				case ISecurityAPI.FUNC_COMP_HELPS_EDIT:
					form.setEditCompHelps(true);
					break;

				case ISecurityAPI.FUNC_PUB_ACTIONS_READ:
					form.setReadPubActions(true);
					break;

				case ISecurityAPI.FUNC_PUB_ACTIONS_EDIT:
					form.setEditPubActions(true);
					break;

				case ISecurityAPI.FUNC_PUB_APPLICATIONS_READ:
					form.setReadPubApplications(true);
					break;

				case ISecurityAPI.FUNC_PUB_APPLICATIONS_EDIT:
					form.setEditPubApplications(true);
					break;

				case ISecurityAPI.FUNC_PUB_CONDITIONS_READ:
					form.setReadPubConditions(true);
					break;

				case ISecurityAPI.FUNC_PUB_CONDITIONS_EDIT:
					form.setEditPubConditions(true);
					break;

				case ISecurityAPI.FUNC_PUB_RULES_READ:
					form.setReadPubRules(true);
					break;

				case ISecurityAPI.FUNC_PUB_RULES_EDIT:
					form.setEditPubRules(true);
					break;

				case ISecurityAPI.FUNC_PUB_MILESTONES_READ:
					form.setReadPubMilestones(true);
					break;

				case ISecurityAPI.FUNC_PUB_MILESTONES_EDIT:
					form.setEditPubMilestones(true);
					break;

				case ISecurityAPI.FUNC_PERM_READ:
					form.setReadPermissions(true);
					break;

				case ISecurityAPI.FUNC_PERM_EDIT:
					form.setEditPermissions(true);
					break;

			}
		}
		
		request.setAttribute("saveFunctionsForm", form);
	}

  public String getInfoEntry (HttpServletRequest request, HttpServletResponse response)
	{
  	boolean bInCookie = false;
  	// Sacamos de parametros
  	String entryinfo = request.getParameter ("entryinfo");
  	// Si esta en parametros ...
  	if (entryinfo != null)
  	{
  		// ... la buscamos en las cookies ...
    	Cookie[] allCookies = request.getCookies();
  		for (int i = 0; i < allCookies.length; i++)
  		{
  			// ... y si esta en una cookie, sobreescribimos valor
  			if (allCookies[i].getName().equals("entryinfo"))
  			{
  				if (entryinfo != null)
  					allCookies[i].setValue(entryinfo);
  				entryinfo = allCookies[i].getValue();
  				bInCookie = true;
  			}
  			response.addCookie(allCookies[i]);
  		}
  		// ... y si no esta en la cookie creamos una nueva cookie con ese valor
  		if (!bInCookie)
  		{
  			response.addCookie (new Cookie ("entryinfo", entryinfo));
  		}
  	}
  	// ... si no esta en parametro la buscamos en cookies
  	else
  	{
    	Cookie[] allCookies = request.getCookies();
  		for (int i = 0; i < allCookies.length; i++)
  		{
  			if (allCookies[i].getName().equals("entryinfo"))
  			{
  				entryinfo = allCookies[i].getValue();
  				bInCookie = true;
  			}
  		}
			// ... y si no esta en las cookies metemos nuevas cookie con valor
			// x defecto el de privilegios
			if (!bInCookie)
  		{
				entryinfo = Constants.PRIVILEGES;
  			response.addCookie (new Cookie ("entryinfo", entryinfo));
  		}
  	}

		return entryinfo;
  }

}
