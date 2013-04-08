package ieci.tdw.ispac.ispaccatalog.action.permissions;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectTypePermissionsAction extends BaseAction
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
        
        int procId = Integer.parseInt(request.getParameter("procId"));
        String uid = request.getParameter("uid");
        
        //Recupera el nombre de un responsable apartir de un UID dado.
        IResponsible resps = respAPI.getResp(uid);
        request.setAttribute("uidName", resps.get("RESPNAME"));
        
        //Recupera el nombre de un procedimiento apartir de un Id dado.
        IItemCollection itemcol=invesflowapi.getProcedures("WHERE id=" + procId);
        request.setAttribute("procName", itemcol.value().get("NOMBRE"));

        //Recupera los permisos otorgados a un reponsable sobre un procedimiento concreto.
       // IItemCollection permissionsProcedures = respAPI.getPermissions(procId, uid);
        IItemCollection permissionsProcedures= respAPI.getAllPermissionsByPcd(procId,uid);
        EntityForm formP = (EntityForm)form;
        String[] selectedItems = new String[5];
        int cont = 0;
        while (permissionsProcedures.next()) { //Prepara un array de int's con los permisos ya otorgados
            selectedItems[cont++] = permissionsProcedures.value().getString("PERMISO");
        }
        //Se rellenan los parametros necesarios para ver en el formulario
        formP.setMultibox(selectedItems);
        formP.setProperty("uid", resps.get("UID").toString());
        formP.setProperty("procId", String.valueOf(procId));
        
        request.setAttribute("application", 
        					 StaticContext.rewritePage((String) servlet.getServletContext().getAttribute("ispacbase"), 
        											   "common/permissions/typepermissions.jsp"));

        // Asociacion de paginas jsp a cada uno de los tiles
        ActionForward forward = mapping.findForward("success");

        return forward;
    }
}