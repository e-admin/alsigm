package solicitudes.prestamos.actions;

import gcontrol.vos.CAOrganoVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.forms.CesionPrestamosForm;
import solicitudes.prestamos.vos.BusquedaVO;
import solicitudes.prestamos.vos.PrestamoPO;
import solicitudes.prestamos.vos.PrestamoToPO;
import solicitudes.prestamos.vos.PrestamoVO;
import transferencias.TransferenciasConstants;
import util.CollectionUtils;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionPrestamosBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

/**
 * Clase que encapsula todas las acciones relacionadas con la cesión de
 * prestamos por parte de un usuario de la aplicación a otro usuario. Se
 * establece un wizard donde se selecciona un prestamo bien de los existentes
 * para el usuario o de un usuario seleccionado, para posteriormente seleccionar
 * el usuario destino de dicho préstamo.
 */
public class CesionPrestamosAction extends BaseAction {

	/**
	 * Prepara la página para seleccionar el prestamo del que se va ceder el
	 * control.
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
	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Establecemos el punto actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_SELECT_PRESTAMO_CEDER,
				request);

		// Establecer el tipo de búsqueda
		CesionPrestamosForm cesionForm = (CesionPrestamosForm) form;
		cesionForm
				.setTipoBusqueda(CesionPrestamosForm.TIPO_BUSQUEDA_POR_PRESTAMO);

		// Información del usuario conectado
		AppUser appUser = getAppUser(request);

		List organos = new ArrayList();
		if (appUser.getOrganization() != null)
			organos.add(appUser.getOrganization());
		if (!CollectionUtils.isEmpty(appUser.getDependentOrganizationList()))
			organos.addAll(appUser.getDependentOrganizationList());

		setInTemporalSession(request, PrestamosConstants.LISTA_ORGANOS_KEY,
				organos);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_prestamos"));
	}

	/**
	 * Prepara la página para seleccionar el préstamo del que se va ceder el
	 * control a partir de un gestor.
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
	public void verBuscadorGestorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CesionPrestamosForm cesionForm = (CesionPrestamosForm) form;

		// Establecemos el punto actual de navegacion
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_SELECT_PRESTAMO_CEDER,
				request);
		invocation.addParameters(cesionForm.getMap());

		// Establecer el tipo de búsqueda
		cesionForm
				.setTipoBusqueda(CesionPrestamosForm.TIPO_BUSQUEDA_POR_GESTOR);

		// Comprobar el órgano
		if (StringUtils.isBlank(cesionForm.getOrgano())) {
			List organos = (List) getFromTemporalSession(request,
					TransferenciasConstants.LISTA_ORGANOS_KEY);

			if (!CollectionUtils.isEmpty(organos)) {
				CAOrganoVO organo = (CAOrganoVO) organos.get(0);
				cesionForm.setOrgano(organo.getIdOrg());
			}
		}

		// Obtener los gestores
		setInTemporalSession(
				request,
				PrestamosConstants.LISTA_GESTORES_KEY,
				getGestionPrestamosBI(request).getGestoresConPrestamos(
						cesionForm.getOrgano()));

		setReturnActionFordward(request,
				mappings.findForward("seleccion_prestamos"));
	}

	/**
	 * Realiza la búsqueda de préstamos para su selección.
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
	public void buscarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		// Establecemos el punto actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_SELECT_PRESTAMO_CEDER,
				request);

		// Validar el formulario
		ActionErrors errores = form.validate(mappings, request);
		if (errores.isEmpty()) {
			CesionPrestamosForm cesionForm = (CesionPrestamosForm) form;
			BusquedaVO busquedaVO = new BusquedaVO();

			// Estados
			busquedaVO
					.setEstados(new String[] {
							"" + PrestamosConstants.ESTADO_PRESTAMO_ABIERTO,
							"" + PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO,
							"" + PrestamosConstants.ESTADO_PRESTAMO_RESERVADO,
							"" + PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO,
							"" + PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO,
							""
									+ PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO });

			if (cesionForm.getTipoBusqueda() == CesionPrestamosForm.TIPO_BUSQUEDA_POR_PRESTAMO) {
				busquedaVO.setCodigo(cesionForm.getCodigo());

				if (StringUtils.isBlank(cesionForm.getOrgano())) {
					List organos = (List) getFromTemporalSession(request,
							TransferenciasConstants.LISTA_ORGANOS_KEY);
					if (!CollectionUtils.isEmpty(organos)) {
						String[] idsOrganos = new String[organos.size()];
						for (int i = 0; i < organos.size(); i++) {
							CAOrganoVO organo = (CAOrganoVO) organos.get(i);
							idsOrganos[i] = organo.getIdOrg();
						}
						busquedaVO.setOrganosUsuarioSolicitante(idsOrganos);
					}
				} else
					busquedaVO
							.setOrganosUsuarioSolicitante(new String[] { cesionForm
									.getOrgano() });
			} else {
				busquedaVO.setGestor(cesionForm.getGestor());
			}

			try {
				// Obtiene la lista de préstamos
				Collection listaPrestamos = getGestionPrestamosBI(request)
						.getPrestamos(busquedaVO,
								getAppUser(request).getIdsArchivosUser());
				CollectionUtils
						.transform(listaPrestamos, PrestamoToPO.getInstance(
								request.getLocale(), services));
				request.setAttribute(PrestamosConstants.LISTA_PRESTAMOS_KEY,
						listaPrestamos);

				cesionForm.setResultado(true);
			} catch (TooManyResultsException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
								new Object[] { new Integer(e.getCount()),
										new Integer(e.getMaxNumResults()) }));
			}
		} else
			obtenerErrores(request, true).add(errores);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_prestamos"));
	}

	/**
	 * Obtiene el prestamo que vamos a ceder así como los posibles gestores de
	 * dicho préstamo.
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
	public void seleccionarExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		// Establecemos el punto actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_SELECT_USUARIO_CEDER,
				request);

		// Información del formulario
		CesionPrestamosForm cesionForm = (CesionPrestamosForm) form;

		// Validar el formulario
		ActionErrors errores = cesionForm.validateSeleccion(mappings, request);
		if (errores.isEmpty()) {
			GestionPrestamosBI prestamosBI = getGestionPrestamosBI(request);

			// Información del préstamo seleccionado
			PrestamoVO prestamo = prestamosBI.getPrestamo(cesionForm
					.getPrestamoSeleccionado());
			PrestamoPO prestamoPO = (PrestamoPO) PrestamoToPO.getInstance(
					request.getLocale(), services).transform(prestamo);
			setInTemporalSession(request, PrestamosConstants.PRESTAMO_KEY,
					prestamoPO);

			// Obtenemos los posibles usuarios de la previsión
			setInTemporalSession(request,
					PrestamosConstants.LISTA_GESTORES_KEY,
					prestamosBI.getUsuariosGestoresPosibles(prestamo));

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor"));
		} else {
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_prestamos"));
		}
	}

	/**
	 * Realiza la asignacion del préstamo al nuevo gestor seleccionado,
	 * estableciendo los campos necesarios.
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
	public void asignarGestorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// Información del formulario
			CesionPrestamosForm cesionForm = (CesionPrestamosForm) form;

			// Validar el formulario
			ActionErrors errores = cesionForm.validateSeleccionGestor(mappings,
					request);
			if (errores.isEmpty()) {
				// Información del préstamo seleccionado
				PrestamoVO prestamo = (PrestamoVO) getFromTemporalSession(
						request, PrestamosConstants.PRESTAMO_KEY);

				GestionPrestamosBI prestamosBI = getGestionPrestamosBI(request);

				// Asignamos el usuario a la/s previsión/es seleccionada/s
				request.setAttribute(
						PrestamosConstants.GESTOR_KEY,
						prestamosBI.asignarPrestamoAGestor(prestamo,
								cesionForm.getGestor()));

				// Actualizar la información del préstamo
				prestamo = prestamosBI.getPrestamo(prestamo.getId());
				ServiceRepository services = getServiceRepository(request);
				PrestamoPO prestamoPO = (PrestamoPO) PrestamoToPO.getInstance(
						request.getLocale(), services).transform(prestamo);

				setInTemporalSession(request, PrestamosConstants.PRESTAMO_KEY,
						prestamoPO);

				setReturnActionFordward(request,
						mappings.findForward("resultado_asignacion"));
			} else {
				obtenerErrores(request, true).add(errores);

				setReturnActionFordward(request,
						mappings.findForward("seleccion_gestor"));
			}
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
			// cleanTemporalSessionValues(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}
}
