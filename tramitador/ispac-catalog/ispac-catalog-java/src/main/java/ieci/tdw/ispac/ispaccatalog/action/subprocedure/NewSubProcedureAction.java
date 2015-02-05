package ieci.tdw.ispac.ispaccatalog.action.subprocedure;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaccatalog.managers.NewProcedureMgr;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.wizard.ItemSelectionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

/**
 * Acción para mostrar el formulario de creación de un subproceso.
 *
 */
public class NewSubProcedureAction extends BaseAction {
	
	/**
	 * Ejecuta la lógica de la acción.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_SUBPROCESS_EDIT });

		DynaActionForm dform = (DynaActionForm) form;

		String pcdId = (String) dform.get("pcdId");
		String pcdtype = (String) dform.get("pcdtype");
		String name = (String) dform.get("name");
		String action = (String) dform.get("action");
		String forward = (String) dform.get("forward");
		String parent = (String) dform.get("parent");
		boolean blank = "true".equals((String) dform.get("blank"));

		if ("next".equalsIgnoreCase(forward)) {

			if (StringUtils.isBlank(name)) {

				ActionMessages msgs = new ActionMessages();
				msgs.add("", new ActionMessage(
						"subprocedure.error.name.required"));
				forward = "success";
				saveErrors(request, msgs);
				
			} else {
				
				if (!"draft".equals(pcdtype)) {
					
					// Comprobar nombre de procedimiento no repetido
					IInvesflowAPI invesFlowAPI = session.getAPI();
					IProcedureAPI pcdAPI = invesFlowAPI.getProcedureAPI();

					IItemCollection collection = pcdAPI.getSubprocedureByName(name);
					if (collection.next()) {

						ActionMessages msgs = new ActionMessages();
						msgs.add("", new ActionMessage("subprocedure.error.name.repeated"));
						forward = "success";
						saveErrors(request, msgs);						
					} else {
						if (blank) {
							ItemSelectionHelper.clear(request.getSession());
							forward = "confirm";
						}
					}
				}
			}
		}
			
		if (forward == null || forward.equals("")) {
			forward = "success";
		}

		NewProcedureMgr pcdmgr = new NewProcedureMgr(
				session.getClientContext(), request.getSession());
		pcdmgr.setNewContext(pcdId, IPcdElement.TYPE_SUBPROCEDURE, pcdtype, name, 
				action, parent, blank);

		dform.set("name", pcdmgr.getName());
		dform.set("pcdtype", pcdmgr.getMode());
		dform.set("action", pcdmgr.getAction());
		dform.set("forward", null);
		dform.set("parent", pcdmgr.getParent());

		// Comprobar si existe ya un borrador
		String groupId = request.getParameter("groupId");
		if (groupId != null) {
			request.setAttribute("draftExists", new Boolean(
					pcdmgr.draftExists(Integer.parseInt(groupId))));
		} else {
			request.setAttribute("draftExists", Boolean.FALSE);
		}

		request.setAttribute("ReadOnly", Boolean.toString(pcdmgr
				.checkType(NewProcedureMgr.MODE_DRAFT)));

		return mapping.findForward(forward);
	}

}