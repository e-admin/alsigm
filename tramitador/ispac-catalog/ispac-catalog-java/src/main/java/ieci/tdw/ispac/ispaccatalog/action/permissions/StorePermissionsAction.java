package ieci.tdw.ispac.ispaccatalog.action.permissions;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class StorePermissionsAction extends BaseAction
{
	public ActionForward executeAction(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response,
	SessionAPI session) throws Exception {
	    
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        IInvesflowAPI invesflowapi= session.getAPI();
        IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();
        
        EntityForm  entityForm = (EntityForm) form;
        int procId = Integer.parseInt(entityForm.getProperty("procId"));
        String uid = entityForm.getProperty("uid");
        IResponsible responsible = respAPI.getResp(uid);
        
       // respAPI.deletePermissions(procId, uid);
        respAPI.deletePermissions(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE, procId,responsible);
       
        String[] permisosSeleccionados = entityForm.getMultibox();
        if(permisosSeleccionados!=null){
        	int [] ipermisosSeleccionados=new int [permisosSeleccionados.length];
	        for(int i=0; i<permisosSeleccionados.length; i++){
	        	ipermisosSeleccionados[i]=Integer.parseInt(permisosSeleccionados[i]);
	        }
	        //respAPI.addPermissions(procId, uid, ipermisosSeleccionados);
	        respAPI.addPermissions(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE, procId,responsible, ipermisosSeleccionados );
        }
        return mapping.findForward("success");
    }
}