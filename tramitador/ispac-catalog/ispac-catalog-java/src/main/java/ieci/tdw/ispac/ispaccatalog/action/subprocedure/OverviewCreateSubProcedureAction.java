package ieci.tdw.ispac.ispaccatalog.action.subprocedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaccatalog.managers.NewProcedureMgr;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.wizard.ItemSelectionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción para mostrar un resumen del subproceso antes de crearlo.
 *
 */
public class OverviewCreateSubProcedureAction extends BaseAction {
	
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

		String forward = request.getParameter("forward");

		NewProcedureMgr pcdmgr = new NewProcedureMgr(
				session.getClientContext(), request.getSession());
		pcdmgr.getContext();

		if ("prev".equals(forward)) {

			if (pcdmgr.isBlank()) {
				forward = "begin";
			}

		} else if (!"next".equals(forward)) {

			ItemSelectionHelper itemselsvr = new ItemSelectionHelper(request
					.getSession());
			
			// Establecer la lista de actividades
			request.setAttribute("ActivitiesList", itemselsvr.getItemBeanList(
					ICatalogAPI.ENTITY_P_ACTIVITIES));

			// Establecer el formateador
			setFormatter(request, "ActivitiesListFormatter",
					"/formatters/subprocedure/overviewactivitiesformatter.xml");

			// Indicar si se va a crear un subproceso en blanco
			request.setAttribute("BlankSubPcd", new Boolean(pcdmgr.isBlank()));

			if (StringUtils.isBlank(forward)) {
				forward = "success";
			}
		}

		return mapping.findForward(forward);
	}
}