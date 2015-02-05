package solicitudes.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import common.actions.BaseAction;
import common.navigation.KeysClientsInvocations;

/**
 * Action que engloba todas las posibles acciones sobre la devolución de
 * solicitudes.
 */
public class DevolucionSolicitudesAction extends BaseAction {

	/**
	 * Prepara el formulario de devolucion de unidades documentales.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void formExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Establecemos el punto de navegacion actual a este
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_DEVOLVER_SOLICITUDES_FORM,
				request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mappings.findForward("formulario_devolucion"));
	}

}