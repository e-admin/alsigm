package solicitudes.consultas.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.forms.BusquedaForm;
import solicitudes.consultas.vos.BusquedaVO;
import solicitudes.consultas.vos.ConsultaToPO;
import util.CollectionUtils;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionConsultasBI;
import common.bi.ServiceRepository;
import common.exceptions.NotCheckedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;

/**
 * Action para las búsquedas de los préstamos.
 */
public class BusquedaConsultasAction extends BaseAction {
	/**
	 * Metodo del action que se llama para recarga el display desde la pagina de
	 * listados del menu BUSCAR
	 */
	private final static String METHOD_LISTADOSBUSCAR = "buscar";

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
		logger.info("Inicio de formBusqExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_FORMBUSQUEDACONSULTA,
				request);

		// Obtenemos los estados de una consulta
		List estados = (List) getGestionConsultasBI(request)
				.getEstadosConsulta();
		request.setAttribute(ConsultasConstants.LISTA_ESTADOS_KEY, estados);

		// // Seleccionar todos los estados
		// if (((BusquedaForm) form).getEstados().length == 0)
		// {
		// String[] idsEstados = new String[estados.size()];
		// Iterator it = estados.iterator();
		// for (int i = 0; it.hasNext(); i++)
		// idsEstados[i] = new Integer(((EstadoVO) it.next()).getId())
		// .toString();
		// ((BusquedaForm) form).setEstados(idsEstados);
		// }

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("formulario_busqueda"));
	}

	/**
	 * Muestra el listado de las consultas según el rol del usuario que esta
	 * accediendo a la aplicación y los filtros aplicados.
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

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaForm) form).getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.SOLICITUDES_BUSCARCONSULTAS, request);

			// Recuperar el usuario
			AppUser user = getAppUser(request);
			GestionConsultasBI consultasService = getGestionConsultasBI(request);

			// Información de paginación
			PageInfo pageInfo = new PageInfo(request, "codigo",
					SortOrderEnum.DESCENDING);
			pageInfo.setDefautMaxNumItems();

			// Obtenemos el formulario de busqueda
			BusquedaVO busqueda = ((BusquedaForm) form).getBusquedaVO();
			busqueda.setPageInfo(pageInfo);

			if (user.hasAnyPermission(new String[] {
					AppPermissions.GESTION_SOLICITUDES_CONSULTAS,
					AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES,
					AppPermissions.CONSULTA_TOTAL_SISTEMA })) {
				// Todas las solicitudes
			} else if (user
					.hasPermission(AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS)) {
				// Solicitudes propias
				busqueda.setIdSolicitante(user.getId());
			} else {
				// Rol no permitido lanzamos una exception
				throw new NotCheckedException(
						"Se ha intentado generar un listado con un rol no permitido");
			}

			try {
				// Establecemos los elementos de la vista
				List consultas = consultasService.getConsultas(busqueda, null);

				AppUser userVO = getAppUser(request);
				ServiceClient sc = ServiceClient.create(userVO);
				ServiceRepository services = ServiceRepository.getInstance(sc);
				CollectionUtils
						.transform(consultas, ConsultaToPO.getInstance(
								request.getLocale(), services));

				request.setAttribute(ConsultasConstants.LISTA_CONSULTAS_KEY,
						consultas);

				// Establecemos el action para la recarga del display
				request.setAttribute(ConsultasConstants.METHOD,
						METHOD_LISTADOSBUSCAR);

				// Redireccionamos a la pagina adecuada
				setReturnActionFordward(request,
						mapping.findForward("listado_consultas"));
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
}
