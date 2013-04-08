package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CreateIntrayProcessAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

        String register = request.getParameter("register");
        int pcdId = Integer.parseInt(request.getParameter("procedure"));

		IInvesflowAPI invesflowAPI = session.getAPI();
    	IInboxAPI inbox = invesflowAPI.getInboxAPI();
    	
//    	IItem organization = null;
//    	ISPACConfiguration configuration = ISPACConfiguration.getInstance();
//    	
//		// Comprobar si la multiorganización está activada
//		String multiOrganization = configuration.get(ISPACConfiguration.MULTIORGANIZATION);
//		if ((multiOrganization != null) &&
//			(multiOrganization.toUpperCase().equals("YES"))) {
//			
//			// Organismo de conexión (cuando hay varios organismos tramitando)
//			organization = (IItem) request.getSession().getAttribute("organization");
//		}
//		else {
//			// Un único organismo tramitando
//	        Properties properties = new Properties();
//	        properties.add(new Property(0, "ID", Types.INTEGER));
//	        properties.add(new Property(1, "CODIGO", Types.VARCHAR));
//			organization = new GenericItem(properties, "ID");
//			
//			String organizationId = configuration.get(ISPACConfiguration.ORGANIZATION_ID);
//			if (!StringUtils.isEmpty(organizationId)) {
//				organization.setKey(Integer.parseInt(organizationId));
//			}
//			organization.set("CODIGO", configuration.get(ISPACConfiguration.ORGANIZATION_CODE));
//		}
//
//		// Crear el proceso a partir del registro distribuido
//		int processId = inbox.createProcess(register, pcdId, organization);

    	// Crear el proceso a partir del registro distribuido
		int processId = inbox.createProcess(register, pcdId);

    	
		//return mapping.findForward("success");
		return NextActivity.afterStartProcess(request, processId, invesflowAPI, mapping);
	}
	
}