package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción para mostrar la lista de fases del catálogo.
 *
 */
public class ShowCTStagesListAction extends BaseAction {

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
				ISecurityAPI.FUNC_INV_STAGES_READ,
				ISecurityAPI.FUNC_INV_STAGES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Obtiene el nombre de la fase
		String ctStageName = request.getParameter("property(criterio)");

		// Establece la lista de fases del catálogo
		IItemCollection ctStages = catalogAPI.getCTStages(ctStageName);
		request.setAttribute("CTStagesList", 
				CollectionBean.getBeanList(ctStages));

		request.setAttribute("ctStageName",ctStageName);
		
		// Establece el formateador de las fases del catálogo
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_STAGES_EDIT)) {
			setFormatter(request, "CTStagesListFormatter",
					"/formatters/ctstageslistformatter.xml");
		} else {
			setFormatter(request, "CTStagesListFormatter",
					"/formatters/ctstageslistreadonlyformatter.xml");
		}

		return mapping.findForward("success");
	}
}