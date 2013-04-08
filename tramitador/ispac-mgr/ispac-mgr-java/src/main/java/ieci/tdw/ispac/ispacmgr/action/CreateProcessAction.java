package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CreateProcessAction extends BaseAction
{
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
    	ITXTransaction tx = invesflowAPI.getTransactionAPI();
    	
    	// Identificador del procedimiento a crear
    	String procedureId = request.getParameter("procedureId");
    	int idProcedure = Integer.parseInt(procedureId);
    	
//    	MULTIORGANIZACION EN UNA MISMA BBDD    	
//    	IItem organization = null;
//    	ISPACConfiguration configuration = ISPACConfiguration.getInstance();
//
// Comprobar si la multiorganización está activada
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
		
		// Obtener el código de procedimiento para el número de expediente
		IItem ctProcedure = entitiesAPI.getEntity(SpacEntities.SPAC_CT_PROCEDIMIENTOS, idProcedure);
		Map params = new HashMap();
		params.put("COD_PCD", ctProcedure.getString("COD_PCD"));
		
		// Crear el proceso del expediente
		int nIdProcess = tx.createProcess(idProcedure, params);

    	return NextActivity.afterStartProcess(request, nIdProcess, invesflowAPI, mapping);
	}
	
}
