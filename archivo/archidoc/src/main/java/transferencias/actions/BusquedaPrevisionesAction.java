package transferencias.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.forms.BusquedaPrevisionesForm;
import transferencias.vos.BusquedaPrevisionesVO;
import auditoria.ArchivoErrorCodes;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionPrevisionesBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;

import fondos.FondosConstants;

/**
 * Action para las búsquedas de las previsiones.
 */
public class BusquedaPrevisionesAction extends BaseAction {

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
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_FORM_BUSQUEDA_PREVISIONES,
				request);

		// BusquedaPrevisionesForm formPrevisiones = (BusquedaPrevisionesForm)
		// form;

		// Obtenemos el servicio de previsiones
		GestionPrevisionesBI service = getGestionPrevisionesBI(request);

		// Establecemos los fondos vigentes
		request.setAttribute(FondosConstants.LISTA_FONDOS_KEY,
				getGestionFondosBI(request).getFondosVigentes());

		// Establecemos los estados de las previsiones
		List estados = service.getEstadosPrevisiones();
		request.setAttribute(TransferenciasConstants.LISTA_ESTADOS_KEY, estados);

		// // Seleccionar todos los estados
		// if (formPrevisiones.getEstados().length == 0)
		// {
		// String[] idsEstados = new String[estados.size()];
		// Iterator it = estados.iterator();
		// for (int i = 0; it.hasNext(); i++)
		// idsEstados[i] = new Integer(((TypeDescVO) it.next()).getId())
		// .toString();
		// formPrevisiones.setEstados(idsEstados);
		// }

		// Establecemos los tipos de transferencias
		ServiceClient client = getServiceClient(request);
		List tipos = TransferenciasConstants
				.getTiposTransferenciasBusquedaPrevisionesWithClientPermissions(client);
		request.setAttribute(TransferenciasConstants.LISTA_TIPOS_KEY, tipos);

		// // Seleccionar todos los tipos de previsiones
		// if (formPrevisiones.getTipos().length == 0)
		// {
		// String[] idsTipos = new String[tipos.size()];
		// Iterator it = tipos.iterator();
		// for (int i = 0; it.hasNext(); i++)
		// idsTipos[i] = new Integer(((TypeDescVO) it.next()).getId())
		// .toString();
		// formPrevisiones.setTipos(idsTipos);
		// }

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, mapping.findForward("formulario"));
	}

	/**
	 * Muestra el listado de las previsiones.
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
		cli.addParameters(((BusquedaPrevisionesForm) form).getMap());

		// Obtenemos el servicio de previsiones
		GestionPrevisionesBI previsionesBI = getGestionPrevisionesBI(request);

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_LISTADO_BUSQUEDA_PREVISIONES,
					request);

			// Información de paginación
			PageInfo pageInfo = new PageInfo(request, "codigo",
					SortOrderEnum.DESCENDING);
			pageInfo.setDefautMaxNumItems();

			// Obtenemos el formulario de busqueda
			BusquedaPrevisionesForm previsionesForm = (BusquedaPrevisionesForm) form;
			previsionesForm.setPageInfo(pageInfo);

			try {
				// Establecemos los elementos de la vista
				BusquedaPrevisionesVO busquedasPrevisiones = previsionesForm
						.getBusquedaPrevisionesVO();
				/* ServiceClient client = */getServiceClient(request);
				AppUser appUser = getAppUser(request);
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(appUser));

				List listaPrevisiones = new ArrayList();

				if (!getServiceClient(request).hasPermission(
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
						&& !getServiceClient(request).hasPermission(
								AppPermissions.CONSULTA_TOTAL_SISTEMA)) {

					busquedasPrevisiones.setArchivosReceptores(getAppUser(
							request).getIdsArchivosUser());

					String[] organosUsuario = new String[] { getAppUser(request)
							.getOrganization().getIdOrg() };
					busquedasPrevisiones.setOrganosUsuario(organosUsuario);

					if (!ArrayUtils.isEmpty(busquedasPrevisiones
							.getOrganosUsuario())
							|| !ArrayUtils.isEmpty(busquedasPrevisiones
									.getArchivosReceptores())) {
						listaPrevisiones = previsionesBI
								.getPrevisiones(busquedasPrevisiones);
					} else {
						throw new ActionNotAllowedException(
								"El usuario no está asignado a ningún órgano, ni tiene asociados archivos",
								ArchivoErrorCodes.USUARIO_SIN_ORGANO,
								ArchivoModules.TRANSFERENCIAS_MODULE);
					}
				} else {
					listaPrevisiones = previsionesBI
							.getPrevisiones(busquedasPrevisiones);
				}

				if (listaPrevisiones != null) {
					CollectionUtils.transform(listaPrevisiones,
							PrevisionToPO.getInstance(services));
					request.setAttribute(
							TransferenciasConstants.LISTA_PREVISIONES_KEY,
							listaPrevisiones);
				}

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
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				goBackExecuteLogic(mapping, form, request, response);
			}
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			goLastClientExecuteLogic(mapping, form, request, response);

			// //Establecemos los fondos vigentes
			// request.setAttribute(FondosConstants.LISTA_FONDOS_KEY,
			// getGestionFondosBI(request).getFondosVigentes());
			//
			// //Establecemos los estados de las previsiones
			// List estados = previsionesBI.getEstadosPrevisiones();
			// request.setAttribute(TransferenciasConstants.LISTA_ESTADOS_KEY,
			// estados);
			//
			// //Establecemos los tipos de transferencias
			// List tipos = previsionesBI.getTiposTransferencias();
			// request.setAttribute(TransferenciasConstants.LISTA_TIPOS_KEY,
			// tipos);
			//
			// setReturnActionFordward(request, mapping
			// .findForward("formulario"));
		}
	}
}