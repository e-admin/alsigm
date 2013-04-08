package transferencias.actions;

import gcontrol.vos.ArchivoVO;

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
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.forms.BusquedaRelacionesForm;
import transferencias.vos.BusquedaRelacionesVO;
import util.ErrorsTag;
import auditoria.ArchivoErrorCodes;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.util.ListUtils;

import deposito.DepositoConstants;
import deposito.actions.ErrorKeys;

/**
 * Action para las búsquedas de las relaciones.
 */
public class BusquedaRelacionesAction extends BaseAction {
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
	protected void formBusquedaIngresosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de formBusquedaIngresosExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_FORM_BUSQUEDA_INGRESOS_DIRECTOS,
				request);

		// Establecemos los estados de las relaciones
		request.setAttribute(TransferenciasConstants.LISTA_ESTADOS_KEY,
				getGestionRelacionesBI(request).getEstadosIngresosDirectos());

		BusquedaRelacionesForm formulario = (BusquedaRelacionesForm) form;

		removeInTemporalSession(request, DepositoConstants.LISTA_FORMATOS);
		setInTemporalSession(request, DepositoConstants.LISTA_FORMATOS,
				getGestorEstructuraDepositoBI(request).getFormatos());

		// Obtener los posibles archivos receptores
		GestionArchivosBI serviceArchivos = getGestionArchivosBI(request);
		List ltArchivos = serviceArchivos.getArchivosXId(getAppUser(request)
				.getCustodyArchiveList().toArray());
		if (ListUtils.isNotEmpty(ltArchivos)) {
			if (ltArchivos.size() == 1) {
				ArchivoVO archivo = (ArchivoVO) ltArchivos.get(0);
				formulario.setIdArchivo(archivo.getId());
				formulario.setNombreArchivo(archivo.getNombre());

			} else {
				setInTemporalSession(request,
						DepositoConstants.LISTA_ARCHIVOS_USUARIO_KEY,
						ltArchivos);
			}

			formulario.setIngresoDirecto(Boolean.TRUE);

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("formularioIngresosDirectos"));
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ErrorKeys.ERROR_USUARIO_SIN_ARCHIVOS, new ActionError(
					ErrorKeys.ERROR_USUARIO_SIN_ARCHIVOS));
			ErrorsTag.saveErrors(request, errors);
			goBackExecuteLogic(mapping, formulario, request, response);
		}

	}

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
				KeysClientsInvocations.TRANSFERENCIAS_FORM_BUSQUEDA_RELACIONES,
				request);

		// Establecemos los estados de las relaciones
		request.setAttribute(TransferenciasConstants.LISTA_ESTADOS_KEY,
				getGestionRelacionesBI(request).getEstadosRelaciones());

		// Establecemos los tipos de transferencias
		ServiceClient client = getServiceClient(request);
		List tipos = TransferenciasConstants
				.getTiposTransferenciasBusquedaRelacionesWithClientPermissions(client);
		request.setAttribute(TransferenciasConstants.LISTA_TIPOS_KEY, tipos);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, mapping.findForward("formulario"));
	}

	/**
	 * Muestra el listado de las relaciones.
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
		cli.addParameters(((BusquedaRelacionesForm) form).getMap());

		// Obtenemos el servicio de previsiones
		GestionRelacionesEntregaBI relacionesBI = getGestionRelacionesBI(request);

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			BusquedaRelacionesForm relacionesForm = (BusquedaRelacionesForm) form;

			// Guardar el enlace a la página
			if (relacionesForm.esIngresoDirecto()) {
				saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_LISTADO_INGRESOS_DIRECTOS,
						request);
			} else {
				saveCurrentInvocation(
						KeysClientsInvocations.TRANSFERENCIAS_LISTADO_BUSQUEDA_RELACIONES,
						request);
			}

			// Información de paginación
			PageInfo pageInfo = new PageInfo(request, "codigo",
					SortOrderEnum.DESCENDING);
			pageInfo.setDefautMaxNumItems();

			// Obtenemos el formulario de busqueda
			relacionesForm.setPageInfo(pageInfo);

			try {
				BusquedaRelacionesVO busquedaRelacionesVO = relacionesForm
						.getBusquedaRelacionesVO();

				List listaRelaciones = new ArrayList();

				if (!getServiceClient(request).hasPermission(
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
						&& !getServiceClient(request).hasPermission(
								AppPermissions.CONSULTA_TOTAL_SISTEMA)) {

					busquedaRelacionesVO.setArchivosReceptores(getAppUser(
							request).getIdsArchivosUser());

					String[] organosUsuario = new String[] { getAppUser(request)
							.getOrganization().getIdOrg() };
					busquedaRelacionesVO.setOrganosUsuario(organosUsuario);

					if (!ArrayUtils.isEmpty(busquedaRelacionesVO
							.getOrganosUsuario())
							|| !ArrayUtils.isEmpty(busquedaRelacionesVO
									.getArchivosReceptores())) {
						listaRelaciones = relacionesBI
								.getRelaciones(busquedaRelacionesVO);
					} else {
						throw new ActionNotAllowedException(
								"El usuario no está asignado a ningún órgano, ni tiene asociados archivos",
								ArchivoErrorCodes.USUARIO_SIN_ORGANO,
								ArchivoModules.TRANSFERENCIAS_MODULE);
					}
				} else {
					listaRelaciones = relacionesBI
							.getRelaciones(busquedaRelacionesVO);
				}

				if (listaRelaciones != null) {
					CollectionUtils.transform(listaRelaciones, RelacionToPO
							.getInstance(getServiceRepository(request)));

					// Establecemos los elementos de la vista
					request.setAttribute(
							TransferenciasConstants.LISTA_RELACIONES_KEY,
							listaRelaciones);

				}

				// Redireccionamos a la pagina adecuada
				if (relacionesForm.esIngresoDirecto())
					setReturnActionFordward(request,
							mapping.findForward("listado_ingresos_directos"));
				else
					setReturnActionFordward(request,
							mapping.findForward("listado"));
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

			// //Establecemos los estados de las relaciones
			// request.setAttribute(TransferenciasConstants.LISTA_ESTADOS_KEY,
			// relacionesBI.getEstadosRelaciones());
			//
			// //Establecemos los tipos de transferencias
			// request.setAttribute(TransferenciasConstants.LISTA_TIPOS_KEY,
			// getGestionPrevisionesBI(request).getTiposTransferencias());
			//
			// setReturnActionFordward(request,
			// mapping.findForward("formulario"));
		}
	}
}