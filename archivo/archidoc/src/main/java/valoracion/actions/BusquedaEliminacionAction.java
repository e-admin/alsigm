package valoracion.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import valoracion.ValoracionConstants;
import valoracion.forms.BusquedaForm;
import valoracion.view.EliminacionToPO;
import valoracion.vos.BusquedaVO;
import valoracion.vos.EliminacionSerieVO;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionEliminacionBI;
import common.bi.GestionFondosBI;
import common.bi.ServiceRepository;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;

import fondos.model.IElementoCuadroClasificacion;

/**
 * Action para las búsquedas de las eliminaciones.
 */
public class BusquedaEliminacionAction extends BaseAction {

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
		logger.info("Inicio de formBusquedaExecuteLogic");

		// Establecemos los elementos de la vista
		this.establecerElementosVista(request);

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.ELIMINACION_FORMBUSQUEDA,
				request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("formulario_busqueda"));
	}

	/**
	 * Muestra el listado de las eliminaciones según los filtros aplicados.
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
		GestionEliminacionBI service = services.lookupGestionEliminacionBI();

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaForm) form).getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Guardar el enlace a la página
			saveCurrentInvocation(KeysClientsInvocations.ELIMINACIONES_BUSCAR,
					request);

			// Información de paginación
			PageInfo pageInfo = new PageInfo(request, "codigo");
			pageInfo.setDefautMaxNumItems();

			// Recuperar la información del formulario
			BusquedaVO busquedaVO = new BusquedaVO();
			((BusquedaForm) form).populate(busquedaVO);
			busquedaVO.setPageInfo(pageInfo);

			try {
				// Realizar la búsqueda
				List eliminaciones = service.getEliminaciones(busquedaVO);
				CollectionUtils.transform(eliminaciones,
						EliminacionToPO.getInstance(services));
				request.setAttribute(
						ValoracionConstants.LISTA_ELIMINACIONES_KEY,
						eliminaciones);

				removeInTemporalSession(request,
						ValoracionConstants.ELIMINACION_KEY);
				removeInTemporalSession(request, ValoracionConstants.SERIE_KEY);

				setReturnActionFordward(request,
						mapping.findForward("listado_eliminaciones"));
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
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Muestra el detalle de una eliminacion.
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
	protected void detailExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de detailExecuteLogic");

		// Obtenemos el servicio para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionEliminacionBI service = services.lookupGestionEliminacionBI();

		// Obtenemos el identificador de la eliminacion
		String id = request.getParameter(Constants.ID);
		if (id != null && id.trim().length() > 0) {
			// Obtenemos la pista y lo metemos para la vista
			EliminacionSerieVO eliminacion = service
					.abrirEliminacion(id, false);
			setInTemporalSession(request, ValoracionConstants.ELIMINACION_KEY,
					EliminacionToPO.getInstance(services)
							.transform(eliminacion));
		}

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_DETALLEVALORACION, request);
		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("detalle_valoracion"));
	}

	/**
	 * Establece los elementos necesarios para mostrar en la vista
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	private void establecerElementosVista(HttpServletRequest request) {
		GestionEliminacionBI eliminacionService = getGestionEliminacionBI(request);
		GestionFondosBI fondosService = getGestionFondosBI(request);
		int[] estados = { IElementoCuadroClasificacion.VIGENTE };
		removeInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY);
		setInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY,
				fondosService.getFondosXEstados(estados));

		request.setAttribute("estados",
				eliminacionService.getEstadosEliminacion());
	}
}
