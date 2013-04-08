package ieci.tdw.ispac.ispaccatalog.action.permissions;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PermissionsAction extends BaseDispatchAction {

	/**
	 * Elimina el permiso seleccionado.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		int procId = Integer.parseInt(request.getParameter("procId"));
		int permiso=Integer.parseInt(request.getParameter("permiso"));
		String uid=request.getParameter("uid");
        ISecurityAPI api = session.getAPI().getSecurityAPI();
        api.deletePermission(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE,procId, uid, permiso);
	    return new ActionForward(new StringBuffer()
	    		.append("/showProcedureEntity.do?method=rights&entityId=")
	    		.append(request.getParameter("entityId"))
	    		.append("&regId=")
	    		.append(procId)
	    		.toString(), true);
    }
}
