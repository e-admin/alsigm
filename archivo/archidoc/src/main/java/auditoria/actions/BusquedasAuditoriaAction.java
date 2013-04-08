package auditoria.actions;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import auditoria.AuditoriaConstants;

import common.actions.BaseAction;
import common.bi.GestionAuditoriaBI;
import common.bi.ServiceRepository;
import common.navigation.KeysClientsInvocations;

/**
 * Action para las búsquedas adicionales sobre las pistas de auditoría.
 */
public class BusquedasAuditoriaAction extends BaseAction {

	/** Metodo del action que se llama para recarga el display */
	private final static String ACTION = "auditoriaBuscarExtendida?method=buscar";

	/**
	 * Muestra el listado de las pistas de auditoria según los filtros
	 * aplicados.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void buscarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de buscarExecuteLogic");

		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionAuditoriaBI service = services.lookupGestionAuditoriaBI();

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.AUDITORIA_BUSCAR_EXTERNO,
				request);

		// Obtenemos el tipo de busqueda
		String tipoBusqueda = request
				.getParameter(AuditoriaConstants.TIPO_BUSQUEDA);
		Collection pistas = null;

		if (tipoBusqueda != null) {
			switch (Integer.parseInt(tipoBusqueda)) {
			case AuditoriaConstants.BUSQUEDA_X_OBJECT:
				// Obtenemos el tipo de objeto
				String typeObject = request
						.getParameter(AuditoriaConstants.TIPO_OBJETO);
				// Obtenemos el identificador de bd del objeto
				String id = request.getParameter(AuditoriaConstants.ID_OBJETO);
				if (id != null) {
					// Búsqueda de objetos
					pistas = service.getPistasXObject(
							Integer.parseInt(typeObject), id);
				}
				break;
			}
		}

		request.setAttribute(AuditoriaConstants.LISTA_PISTAS_KEY, pistas);
		// Establecemos el action para la recarga del display
		request.setAttribute(AuditoriaConstants.ACTION, ACTION);

		setReturnActionFordward(request, mapping.findForward("listado_pistas"));
	}
}
