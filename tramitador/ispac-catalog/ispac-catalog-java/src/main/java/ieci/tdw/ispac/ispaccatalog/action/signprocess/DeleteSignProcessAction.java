package ieci.tdw.ispac.ispaccatalog.action.signprocess;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteSignProcessAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form, 
									   HttpServletRequest request,
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

		// Formulario asociado al proceso de firma
		EntityForm defaultForm = (EntityForm) form;

		int keyId = Integer.parseInt(defaultForm.getKey());
		int entityId = Integer.parseInt(defaultForm.getEntity());
		
		// Comprobar si se puede eliminar
		if (procedureAPI.isInUse( entityId, keyId)) {
			return mapping.findForward("use");
		}
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
	        // Abrir transacción
	        cct.beginTX();
	        
	        // Si el proceso de firma esta en uso(presente en la tabla SPAC_CTOS_FIRMA) entonces no puede eliminarse
	        int count = catalogAPI.countCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS, "WHERE ID_CIRCUITO = " + keyId);
	        if (count > 0) {
	        	
	        	throw new ISPACInfo(getResources(request).getMessage("error.signprocess.inUse"));
	        }
	        
	        // Borrar los detalles
	        IItemCollection details = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL, "WHERE ID_CIRCUITO = " + keyId);
	        while (details.next()) {
	        	
	        	IItem detail = details.value();
	        	detail.delete(cct);
	        }

	        // Borrar el proceso de firma
	        IItem signprocess = catalogAPI.getCTEntity(entityId, keyId);
	        signprocess.delete(cct);

			// Si todo ha sido correcto se hace commit de la transacción
	        bCommit = true;

	        return mapping.findForward("success");
		}
		finally {
			cct.endTX(bCommit);
		}
	}
	
}