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

public class DeleteSignerAction extends BaseAction {
	
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
        EntityForm entityForm= (EntityForm) form;
        IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
		String regId="";
		
		//int keyId = Integer.parseInt(request.getParameter("delSigner"));
		
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
			//Comprobar si el circuito no esta instanciado , en cuyo caso no se podra eliminar ningún firmante 
			if(procedureAPI.isSignProcessInUse(Integer.parseInt(entityForm.getKey()))){
		    	   throw new ISPACInfo("exception.signProcess.inUse.noDeletedSigner");
		    }
	        // Abrir transacción
	        cct.beginTX();
	        
	        for(int i=0; i<entityForm.getMultibox().length; i++){
	        	
	        int keyId=Integer.parseInt(((String [])entityForm.getMultibox())[i]);
	        // Borrar el firmante
	        IItem detail = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL, keyId);
	        regId = detail.getString("ID_CIRCUITO");
	        String step = detail.getString("ID_PASO");
	        detail.delete(cct);
	        
	        // Actualizar el orden de los firmantes
	        IItemCollection details = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL, "WHERE ID_CIRCUITO = " + regId + " AND ID_PASO > " + step + " ORDER BY ID_PASO");
	        while (details.next()) {
	        	
	        	detail = details.value();
	        	detail.set("ID_PASO", detail.getInt("ID_PASO") - 1);
	        	detail.store(cct);
	        }
	        
	        // Actualizar el proceso de firma
	        IItem signprocess = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_SIGNPROCESS_HEADER, Integer.parseInt(regId));
	        signprocess.set("NUM_PASOS", signprocess.getInt("NUM_PASOS") - 1);
	        signprocess.store(cct);
	        
			// Si todo ha sido correcto se hace commit de la transacción
	        bCommit = true;
	        }
			return new ActionForward(new StringBuffer()
		    		.append("/showCTEntity.do?entityId=")
		    		.append(ICatalogAPI.ENTITY_SIGNPROCESS_HEADER)
		    		.append("&regId=")
		    		.append(regId)
		    		.toString(), true);
		}
		finally {
			cct.endTX(bCommit);
		}
	}
	
}