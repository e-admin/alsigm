package valoracion.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import valoracion.ValoracionConstants;
import valoracion.forms.BusquedaForm;
import valoracion.view.ValoracionSeriePO;
import valoracion.view.ValoracionToPO;
import valoracion.vos.BusquedaVO;
import valoracion.vos.ValoracionSerieVO;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionFondosBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceRepository;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;

import fondos.model.IElementoCuadroClasificacion;

/**
 * Action para las búsquedas de las valoraciones.
 */
public class BusquedaValoracionAction extends BaseAction {

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
		saveCurrentInvocation(KeysClientsInvocations.VALORACION_FORMBUSQUEDA,
				request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("formulario_busqueda"));
	}

	/**
	 * Muestra el listado de las valoraciones según los filtros aplicados.
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
		GestionValoracionBI service = services.lookupGestionValoracionBI();

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaForm) form).getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Guardar el enlace a la página
			saveCurrentInvocation(KeysClientsInvocations.VALORACIONES_BUSCAR,
					request);

			// Información de la paginación
			PageInfo pageInfo = new PageInfo(request, "codigo");
			pageInfo.setDefautMaxNumItems();

			// Recuperar la información del formulario
			BusquedaVO busquedaVO = new BusquedaVO();
			((BusquedaForm) form).populate(busquedaVO);
			busquedaVO.setPageInfo(pageInfo);

			try {
				// Control por usuario
				AppUser user = getAppUser(request);
				if (user.hasAnyPermission(new String[] {
						AppPermissions.CONSULTA_TOTAL_SISTEMA,
						AppPermissions.GESTION_VALORACIONES })) {
					// Todas las valoraciones
				} else if (user.hasPermission(AppPermissions.GESTOR_SERIE)) {
					// Valoraciones de las que el usuario es gestor
					busquedaVO.setIdGestor(user.getId());
				}

				// Realizar la búsqueda
				List valoraciones = service.getValoraciones(busquedaVO);
				CollectionUtils.transform(valoraciones,
						ValoracionToPO.getInstance(services));
				request.setAttribute(
						ValoracionConstants.LISTA_VALORACIONES_KEY,
						valoraciones);

				removeInTemporalSession(request,
						ValoracionConstants.VALORACION_KEY);
				removeInTemporalSession(request, ValoracionConstants.SERIE_KEY);

				setReturnActionFordward(request,
						mapping.findForward("listado_valoraciones"));
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
	 * Muestra el detalle de una valoracion.
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
		GestionValoracionBI service = services.lookupGestionValoracionBI();

		// Obtenemos el identificador de la pista
		String idValoracion = request.getParameter(Constants.ID);
		if (idValoracion != null && idValoracion.trim().length() > 0) {
			// Obtenemos la pista y lo metemos para la vista
			ValoracionSerieVO valoracion = service
					.abrirValoracion(idValoracion);
			ValoracionSeriePO valoracionPO = (ValoracionSeriePO) ValoracionToPO
					.getInstance(services).transform(valoracion);

			setInTemporalSession(request, ValoracionConstants.VALORACION_KEY,
					valoracionPO);
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
		GestionValoracionBI valoracionService = getGestionValoracionBI(request);
		GestionFondosBI fondosService = getGestionFondosBI(request);
		int[] estados = { IElementoCuadroClasificacion.VIGENTE };
		removeInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY);
		setInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY,
				fondosService.getFondosXEstados(estados));

		request.setAttribute("estados",
				valoracionService.getEstadosValoracion());
	}
}
