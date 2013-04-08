package ieci.tdw.ispac.ispaccatalog.action.pcdstage;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para añadir una fase a un procedimiento.
 *
 */
public class AddPcdStageAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI procedureAPI= invesFlowAPI.getProcedureAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		int ctstageid = TypeConverter.parseInt(
				request.getParameter("ctstageId"), -1);
		int pcdid = Integer.parseInt(request.getParameter("pcdId"));

		// Obtener el nombre de la fase
		String ctstagename = null;
		if (ctstageid > 0) {
			IItem ctstage = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_STAGE, 
					ctstageid);
			if (ctstage != null) {
				ctstagename = ctstage.getString("NOMBRE");
			}
		} else {
			ctstagename = request.getParameter("textvalue");
		}
				
		// Asociar la fase al procedimiento
		try{
			synchronized (this) {
				procedureAPI.addStage(pcdid, ctstageid, ctstagename);
			}
			// Actualizar el árbol del procedimiento
			TreeView tree = (TreeView)request.getSession().getAttribute(
					ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
			if (tree!=null) {
				tree.getSelectedNode().refresh();
			}
		}catch (ISPACInfo e){
			if (ctstageid == -1)
				throw e;
		}
		
			
		// Refrescar la pantalla principal
		request.setAttribute("Refresh", "true");
		
		return mapping.findForward("success");
    }
}
