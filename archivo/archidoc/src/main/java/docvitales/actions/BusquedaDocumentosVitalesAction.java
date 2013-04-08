package docvitales.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.actions.BaseAction;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;

import docvitales.DocumentosVitalesConstants;
import docvitales.forms.BusquedaDocumentosVitalesForm;

/**
 * Action para las búsquedas de los documentos vitales.
 */
public class BusquedaDocumentosVitalesAction extends BaseAction {

	/**
	 * Muestra el formulario para realizar búsquedas.
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
	protected void formBusquedaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_VITALES_FORM_BUSQUEDA,
				request);

		// Obtener los tipos de documentos vitales
		request.setAttribute(
				DocumentosVitalesConstants.TIPOS_DOCUMENTOS_VITALES_KEY,
				getGestionDocumentosVitalesBI(request)
						.getTiposDocumentosVitales());

		// Obtener los estados de documentos vitales
		request.setAttribute(
				DocumentosVitalesConstants.ESTADOS_DOCUMENTOS_VITALES_KEY,
				getGestionDocumentosVitalesBI(request)
						.getEstadosDocumentosVitales());

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, mapping.findForward("formulario"));
	}

	/**
	 * Muestra el listado de los documentos vitales.
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
		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaDocumentosVitalesForm) form).getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DOCUMENTOS_VITALES_LISTADO_BUSQUEDA,
					request);

			// Información de paginación
			PageInfo pageInfo = new PageInfo(request, "identidad");
			pageInfo.setDefautMaxNumItems();

			// Obtenemos el formulario de busqueda
			BusquedaDocumentosVitalesForm documentosForm = (BusquedaDocumentosVitalesForm) form;
			documentosForm.setPageInfo(pageInfo);

			try {
				// Establecemos los elementos de la vista
				request.setAttribute(
						DocumentosVitalesConstants.DOCUMENTOS_VITALES_KEY,
						getGestionDocumentosVitalesBI(request)
								.getDocumentosVitales(
										documentosForm.getBusquedaVO()));

				// Redireccionamos a la pagina adecuada
				setReturnActionFordward(request, mapping.findForward("listado"));
			} catch (TooManyResultsException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
								new Object[] { new Integer(e.getCount()),
										new Integer(e.getMaxNumResults()) }));

				goBackExecuteLogic(mapping, form, request, response);
			}
		} else {
			obtenerErrores(request, true).add(errores);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}
}