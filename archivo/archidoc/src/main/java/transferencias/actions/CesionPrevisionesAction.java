package transferencias.actions;

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

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.forms.CesionPrevisionesForm;
import transferencias.model.EstadoPrevision;
import transferencias.vos.BusquedaPrevisionesVO;
import transferencias.vos.PrevisionVO;
import util.CollectionUtils;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

/**
 * Clase que encapsula todas las acciones relacionadas con la cesión de
 * previsiones por parte de un usuario de la aplicación a otro usuario. Se
 * establece un wizard donde se selecciona una previsión bien de las existentes
 * para el usuario o de un usuario seleccionado, para posteriormente seleccionar
 * el usuario destino de dicha previsión.
 */
public class CesionPrevisionesAction extends BaseAction {

	/**
	 * Prepara la página para seleccionar la previsión de la que se va ceder el
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
	public void verBuscadorPrevisionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Establecemos el punto actual de navegacion
		/* ClientInvocation invocation = */saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_SELECT_PREVISION_CEDER,
				request);
		// invocation.setAsReturnPoint(true);

		// Establecer el tipo de búsqueda
		CesionPrevisionesForm cesionForm = (CesionPrevisionesForm) form;
		cesionForm
				.setTipoBusqueda(CesionPrevisionesForm.TIPO_BUSQUEDA_POR_PREVISION);

		// Información del usuario conectado
		AppUser appUser = getAppUser(request);

		List organos = new ArrayList();
		if (appUser.getOrganization() != null)
			organos.add(appUser.getOrganization());
		if (!CollectionUtils.isEmpty(appUser.getDependentOrganizationList()))
			organos.addAll(appUser.getDependentOrganizationList());

		setInTemporalSession(request,
				TransferenciasConstants.LISTA_ORGANOS_KEY, organos);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_previsiones"));
	}

	/**
	 * Prepara la página para seleccionar la previsión de la que se va ceder el
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
	public void verBuscadorPrevisionesGestorExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CesionPrevisionesForm cesionForm = (CesionPrevisionesForm) form;

		// Establecemos el punto actual de navegacion
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_SELECT_PREVISION_CEDER,
				request);
		invocation.addParameters(cesionForm.getMap());

		// Establecer el tipo de búsqueda
		cesionForm
				.setTipoBusqueda(CesionPrevisionesForm.TIPO_BUSQUEDA_POR_GESTOR);

		int[] tiposTransferencias = null;

		// Obtener los tipos de transferencia
		switch (cesionForm.getTipo()) {
		case 2: // Transf. Extraordinaria
			tiposTransferencias = new int[] {
					TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
							.getIdentificador(),
					TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
							.getIdentificador() };
			break;
		default: // Transf. Ordinaria
			tiposTransferencias = new int[] { TipoTransferencia.ORDINARIA
					.getIdentificador() };
		}

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
				TransferenciasConstants.LISTA_GESTORES_KEY,
				getGestionPrevisionesBI(request).getGestoresConPrevision(
						cesionForm.getOrgano(), tiposTransferencias));

		setReturnActionFordward(request,
				mappings.findForward("seleccion_previsiones"));
	}

	/**
	 * Realiza la búsqueda de previsiones para su selección.
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
	public void buscarPrevisionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		// Establecemos el punto actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_SELECT_PREVISION_CEDER,
				request);

		// Validar el formulario
		ActionErrors errores = form.validate(mappings, request);
		if (errores.isEmpty()) {
			CesionPrevisionesForm cesionForm = (CesionPrevisionesForm) form;
			BusquedaPrevisionesVO busquedaVO = new BusquedaPrevisionesVO();

			// Estados
			busquedaVO.setEstados(new String[] {
					"" + EstadoPrevision.ABIERTA.getIdentificador(),
					"" + EstadoPrevision.ENVIADA.getIdentificador(),
					"" + EstadoPrevision.RECHAZADA.getIdentificador() });

			// Tipos de transferencia
			switch (cesionForm.getTipo()) {
			case 1: // Transf. Ordinaria
				busquedaVO.setTipos(new String[] { ""
						+ TipoTransferencia.ORDINARIA.getIdentificador() });
				break;
			case 2: // Transf. Extraordinaria
				busquedaVO
						.setTipos(new String[] {
								""
										+ TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
												.getIdentificador(),
								""
										+ TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
												.getIdentificador() });
				break;
			}

			if (cesionForm.getTipoBusqueda() == CesionPrevisionesForm.TIPO_BUSQUEDA_POR_PREVISION) {
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
						busquedaVO.setOrganosUsuario(idsOrganos);
					}
				} else
					busquedaVO.setOrganosUsuario(new String[] { cesionForm
							.getOrgano() });
			} else {
				busquedaVO.setOrganosUsuario(new String[] { cesionForm
						.getOrgano() });
				busquedaVO.setIdGestor(cesionForm.getGestor());
			}

			try {
				// Obtiene las previsiones
				Collection previsiones = getGestionPrevisionesBI(request)
						.getPrevisiones(busquedaVO);
				CollectionUtils.transform(previsiones,
						PrevisionToPO.getInstance(services));
				request.setAttribute(
						TransferenciasConstants.LISTA_PREVISIONES_KEY,
						previsiones);

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
				mappings.findForward("seleccion_previsiones"));
	}

	/**
	 * Obtiene la previsión que vamos a ceder así como los posibles gestores
	 * para dicha previsión.
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
	public void seleccionarPrevisionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		// Establecemos el punto actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_SELECT_USUARIO_CEDER,
				request);

		// Valores del formulario
		CesionPrevisionesForm cesionForm = (CesionPrevisionesForm) form;

		// Validar el formulario
		ActionErrors errores = cesionForm.validateSeleccionPrevisiones(
				mappings, request);
		if (errores.isEmpty()) {
			// Información de las previsiones seleccionadas
			Collection previsiones = getGestionPrevisionesBI(request)
					.getPrevisionesXId(cesionForm.getPrevisionesSeleccionadas());
			CollectionUtils.transform(previsiones,
					PrevisionToPO.getInstance(services));
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_PREVISIONES_KEY, previsiones);

			// Información de la primera previsión seleccionada
			PrevisionVO prevision = (PrevisionVO) previsiones.iterator().next();

			// Permisos de los usuarios
			String[] permisos = null;
			if (prevision.getTipotransferencia() == TipoTransferencia.ORDINARIA
					.getIdentificador())
				permisos = new String[] {
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
						AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_ORDINARIAS,
						AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_ORDINARIAS };
			else
				// Extraordinaria (con o sin signatura)
				permisos = new String[] {
						AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
						AppPermissions.ESTANDAR_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS,
						AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS,
						AppPermissions.ELABORACION_TRANSFERENCIAS_ENTRE_ARCHIVOS };

			// Obtenemos los posibles usuarios de la previsión
			GestionControlUsuariosBI controlUsuariosBI = getGestionControlUsuarios(request);
			List gestores = controlUsuariosBI.getUsuarios(
					prevision.getIdorgremitente(), permisos);

			String[] permisosSuperUser = { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };
			if (gestores == null)
				gestores = new ArrayList();
			CollectionUtils
					.addList(gestores, controlUsuariosBI
							.getUsuariosConPermisos(permisosSuperUser));

			setInTemporalSession(request,
					TransferenciasConstants.LISTA_GESTORES_KEY, gestores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor"));
		} else {
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_previsiones"));
		}
	}

	/**
	 * Realiza la asignacion de la previsión al nuevo gestor seleccionado,
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
		CesionPrevisionesForm cesionForm = (CesionPrevisionesForm) form;

		// Validar el formulario
		ActionErrors errores = cesionForm.validateSeleccionGestor(mappings,
				request);
		if (errores.isEmpty()) {
			try {
				// Asignamos el usuario a la/s previsión/es seleccionada/s
				request.setAttribute(
						TransferenciasConstants.GESTOR_KEY,
						getGestionPrevisionesBI(request)
								.asignarPrevisionesAGestor(
										cesionForm
												.getPrevisionesSeleccionadas(),
										cesionForm.getTipo() == 1 ? TipoTransferencia.ORDINARIA
												: TipoTransferencia.EXTRAORDINARIA_SIGNATURADA,
										cesionForm.getGestor()));

				setReturnActionFordward(request,
						mappings.findForward("resultado_asignacion"));
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("seleccion_gestor"));
			}
		} else {
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor"));
		}
	}
}
