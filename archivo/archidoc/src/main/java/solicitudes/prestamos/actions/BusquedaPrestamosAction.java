package solicitudes.prestamos.actions;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.forms.BusquedaForm;
import solicitudes.prestamos.vos.BusquedaVO;
import solicitudes.prestamos.vos.PrestamoToPO;
import util.CollectionUtils;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionPrestamosBI;
import common.bi.ServiceRepository;
import common.exceptions.NotCheckedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;

/**
 * Action para las búsquedas de los préstamos.
 */
public class BusquedaPrestamosAction extends BaseAction {
	private final static Logger logger = Logger
			.getLogger(BusquedaPrestamosAction.class);

	/**
	 * Metodo del action que se llama para recarga el display desde la pagina de
	 * listados del menu BUSCAR
	 */
	private final static String METHOD_LISTADOSBUSCAR = "buscar";

	/**
	 * Muestra el formulario para realizar búsquedas.
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
	protected void formBusquedaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de formBusqExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_FORMBUSQUEDA,
				request);

		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI service = getGestionPrestamosBI(request);

		// Establecemos los estados
		Collection estados = service.getEstadosPrestamo();
		request.setAttribute(PrestamosConstants.LISTA_ESTADOS_KEY, estados);

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

		// Establecemos las notas
		request.setAttribute(PrestamosConstants.LISTA_NOTAS_KEY,
				service.getNotas());

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("formulario_busqueda"));
	}

	// /**
	// * Muestra los órganos accesibles del usuario.
	// *
	// * @param mapping
	// * {@link ActionMapping}con los mapeos asociado.
	// * @param form
	// * {@link ActionForm}asociado al action.
	// * @param request
	// * {@link HttpServletRequest}
	// * @param response
	// * {@link HttpServletResponse}
	// */
	// protected void showOrganosExecuteLogic(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response)
	// {
	// logger.info("Inicio de showOrganosExecuteLogic");
	//
	// // Guardar el enlace a la página
	// saveCurrentInvocation(
	// KeysClientsInvocations.SOLICITUDES_FORMBUSQUEDA, request);
	//
	// AppUser appUser = getAppUser(request);
	// ServiceClient sc = ServiceClient.create(appUser);
	//
	// // Guardar la info de los órganos dependientes en el cliente
	// List orgList = new ArrayList();
	// orgList.add(appUser.getOrganization());
	// orgList.addAll(appUser.getDependentOrganizationList());
	// sc.getProperties().put(
	// PrestamosConstants.PROPERTY_DEPENDENTORGANIZATIONLIST, orgList);
	// ServiceRepository services = ServiceRepository.getInstance(sc);
	//
	// //Obtenemos el servicio de prestamos para el usuario conectado
	// GestionPrestamosBI service = services.lookupGestionPrestamosBI();
	//
	// // Obtenemos la lista de órganos para la búsqueda
	// setInTemporalSession(request,
	// PrestamosConstants.LISTA_ORGANOS_KEY,
	// service.getOrganosBusqueda());
	//
	// // Redirigimos a la pagina adecuada
	// setReturnActionFordward(request, mapping
	// .findForward("formulario_busqueda"));
	// }

	/**
	 * Muestra el listado de los prestamos según el rol del usuario que esta
	 * accediendo a la aplicación y los filtros aplicados.
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
			saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_BUSCAR,
					request);

			AppUser userVO = getAppUser(request);
			ServiceClient sc = ServiceClient.create(userVO);
			ServiceRepository services = ServiceRepository.getInstance(sc);

			// Obtenemos el servicio de prestamos para el usuario conectado
			GestionPrestamosBI prestamosService = services
					.lookupGestionPrestamosBI();

			try {
				// Información de paginación
				PageInfo pageInfo = new PageInfo(request, "codigo",
						SortOrderEnum.DESCENDING);
				pageInfo.setDefautMaxNumItems();

				// Obtenemos los criterios de búsqueda
				BusquedaVO busqueda = ((BusquedaForm) form).getBusquedaVO();
				busqueda.setPageInfo(pageInfo);

				if (userVO.hasAnyPermission(new String[] {
						AppPermissions.GESTION_PRESTAMOS_ARCHIVO,
						AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES,
						AppPermissions.CONSULTA_TOTAL_SISTEMA })) {
					// Todos los préstamos
				} else if (userVO
						.hasPermission(AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE)) {
					// Los préstamos del órgano del usuario y dependientes
					busqueda.setOrganosUsuarioSolicitante(userVO
							.getAllDependentOrganizationIds());
				} else if (userVO
						.hasPermission(AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS)
						|| userVO
								.hasPermission(AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS)) {
					// Préstamos que gestiona el usuario
					busqueda.setGestor(userVO.getId());
				} else {
					// Rol no permitido lanzamos una exception
					throw new NotCheckedException(
							"Se ha intentado generar un listado con un rol no permitido");
				}

				// Buscar los préstamos
				List prestamos = prestamosService.getPrestamos(busqueda, null);

				// Asignamos a los prestamos sus notas asociadas
				prestamosService.asignaEstadosAPrestamos(prestamos);

				CollectionUtils
						.transform(prestamos, PrestamoToPO.getInstance(
								request.getLocale(), services));
				// Establecemos los elementos de la vista
				request.setAttribute(PrestamosConstants.LISTA_PRESTAMOS_KEY,
						prestamos);

				// Activamos la columna de las notas de los préstamos
				request.setAttribute(PrestamosConstants.VER_COLUMNA_NOTAS,
						new Boolean(true));

				// Establecemos el action para la recarga del display
				request.setAttribute(PrestamosConstants.METHOD,
						METHOD_LISTADOSBUSCAR);

				// Redireccionamos a la pagina adecuada
				setReturnActionFordward(request,
						mapping.findForward("listado_prestamo"));
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