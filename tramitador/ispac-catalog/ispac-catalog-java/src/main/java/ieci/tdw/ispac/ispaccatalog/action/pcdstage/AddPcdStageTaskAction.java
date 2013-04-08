/*
 * Created on 11-nov-2005
 *
 *
 */
package ieci.tdw.ispac.ispaccatalog.action.pcdstage;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AddPcdStageTaskAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI pcdapi= invesFlowAPI.getProcedureAPI();

		int cttaskid = Integer.parseInt(request.getParameter("cttaskId"));
		int pstageid = Integer.parseInt(request.getParameter("pstageId"));

		//Capturamos la excepcion y se continua con el proceso de actualizacion del arbol, para el caso en el que se intenta asocair un tramite ya asociado (doble click).
		try{
			synchronized (this) {
				pcdapi.addTask(pstageid,cttaskid);
			}
			TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
			if (tree!=null)
				tree.getSelectedNode().refresh();
		}catch (ISPACInfo e){
		}
		
		//Para que la acción redirigida (SetObject.do) refresque la pantalla principal.
		request.setAttribute("Refresh","true");
		return mapping.findForward("success");
    }
}
