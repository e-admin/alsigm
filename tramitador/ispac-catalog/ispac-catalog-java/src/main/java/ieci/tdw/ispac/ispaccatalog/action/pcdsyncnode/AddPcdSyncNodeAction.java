package ieci.tdw.ispac.ispaccatalog.action.pcdsyncnode;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.SyncNodeElement;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para añadir un nodo de sincronización a un procedimiento.
 *
 */
public class AddPcdSyncNodeAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI procedureAPI= invesFlowAPI.getProcedureAPI();

		int pcdid = TypeConverter.parseInt(defaultForm.getProperty("ID_PCD"));
		int type = TypeConverter.parseInt(defaultForm.getProperty("TIPO"),
				SyncNodeElement.SYNCNODE_OR);

		// Crear el nodo de sincronización
		procedureAPI.addSyncNode(pcdid, type);

		// Actualizar el árbol del procedimiento
		TreeView tree = (TreeView)request.getSession().getAttribute(
				ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree!=null) {
			tree.getSelectedNode().refresh();
		}
		
		// Refrescar la pantalla principal
		request.setAttribute("Refresh", "true");
		
		return mapping.findForward("success");
    }
}
