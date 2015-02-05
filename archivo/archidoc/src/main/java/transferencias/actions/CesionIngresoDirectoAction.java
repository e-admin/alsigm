package transferencias.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import transferencias.forms.CesionRelacionesForm;
import transferencias.model.EstadoREntrega;
import transferencias.vos.BusquedaRelacionesVO;
import util.CollectionUtils;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.ListUtils;
import common.util.StringUtils;

/**
 * Clase que encapsula todas las acciones relacionadas con la cesión de ingresos
 * directos de unidades documentales por parte de un usuario de la aplicación a
 * otro usuario. Se establece un wizard donde se selecciona una ingreso bien de
 * los existentes para el usuario o de un usuario seleccionado, para
 * posteriormente seleccionar el usuario destino del ingreso.
 */
public class CesionIngresoDirectoAction extends BaseAction {

	/**
	 * Prepara la página para seleccionar el ingreso directo en órgano remitente
	 * del que se va ceder el control.
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
				KeysClientsInvocations.CUADRO_SELECT_INGRESO_CEDER, request);

		// Establecer el tipo de búsqueda
		CesionRelacionesForm cesionForm = (CesionRelacionesForm) form;
		cesionForm
				.setTipoBusqueda(CesionPrevisionesForm.TIPO_BUSQUEDA_POR_PREVISION);

		// Información del usuario conectado
		AppUser appUser = getAppUser(request);

		// Obtener los posibles archivos receptores
		ServiceRepository services = getServiceRepository(request);
		GestionArchivosBI serviceArchivos = services.lookupGestionArchivosBI();
		List ltArchivos = serviceArchivos.getArchivosXId(appUser
				.getCustodyArchiveList().toArray());
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_CODIGOSARCHIVO_KEY, ltArchivos);

		// Establecer el tipo de relaciones
		setInTemporalSession(request,
				TransferenciasConstants.TIPO_RELACION_KEY,
				TransferenciasConstants.INGRESO_DIRECTO_KEY);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_relaciones"));
	}

	/**
	 * Prepara la página para seleccionar la relación de entrega de la que se va
	 * ceder el control a partir de un gestor.
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
		// Establecemos el punto actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_SELECT_INGRESO_CEDER, request);

		// Establecer el tipo de búsqueda
		CesionRelacionesForm cesionForm = (CesionRelacionesForm) form;
		cesionForm
				.setTipoBusqueda(CesionRelacionesForm.TIPO_BUSQUEDA_POR_GESTOR);

		String[] permisos = new String[] {
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
				AppPermissions.INGRESO_DIRECTO_UNIDADES_DOCUMENTALES };

		// Obtener información del usuario conectado
		AppUser appUser = getAppUser(request);
		String[] idsArchivo = ArrayUtils.toString(appUser
				.getCustodyArchiveList().toArray());

		// Obtener los gestores
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_GESTORES_KEY,
				getGestionControlUsuarios(request)
						.getUsuariosConPermisosYArchivos(permisos, idsArchivo));

		setReturnActionFordward(request,
				mappings.findForward("seleccion_relaciones"));
	}

	/**
	 * Realiza la búsqueda de relaciones de entrega para su selección.
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
		CesionRelacionesForm cesionForm = (CesionRelacionesForm) form;

		// Establecemos el punto actual de navegacion
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_SELECT_INGRESO_CEDER, request);
		invocation.addParameters(cesionForm.getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mappings, request);
		if (errores.isEmpty()) {
			BusquedaRelacionesVO busquedaVO = new BusquedaRelacionesVO();

			// Estados
			busquedaVO.setEstados(new String[] {
					"" + EstadoREntrega.ABIERTA.getIdentificador(),
					"" + EstadoREntrega.ENVIADA.getIdentificador(),
					"" + EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
					"" + EstadoREntrega.CORREGIDA_ERRORES.getIdentificador() });

			// Tipos de transferencia
			busquedaVO.setTipos(new String[] { ""
					+ TipoTransferencia.INGRESO_DIRECTO.getIdentificador() });

			if (cesionForm.getTipoBusqueda() == CesionRelacionesForm.TIPO_BUSQUEDA_POR_RELACION) {
				busquedaVO.setCodigo(cesionForm.getCodigo());

				if (StringUtils.isNotEmpty(cesionForm.getArchivo()))
					busquedaVO.setArchivosReceptores(new String[] { cesionForm
							.getArchivo() });
			} else {
				busquedaVO.setIdGestor(cesionForm.getGestor());
			}

			// Si no se ha asignado ningún archivo restringir a los archivos del
			// usuario conectado
			if (ArrayUtils.isEmpty(busquedaVO.getArchivosReceptores())) {
				// Información del usuario conectado
				AppUser appUser = getAppUser(request);
				if (!ListUtils.isEmpty(appUser.getCustodyArchiveList())) {
					String[] idsArchivo = ArrayUtils.toString(appUser
							.getCustodyArchiveList().toArray());
					busquedaVO.setArchivosReceptores(idsArchivo);
				}
			}

			try {
				removeInTemporalSession(request,
						TransferenciasConstants.LISTA_RELACIONES_KEY);
				List relaciones = getGestionRelacionesBI(request)
						.getIngresosDirectosCedibles(busquedaVO);
				CollectionUtils
						.transform(relaciones, RelacionToPO
								.getInstance(getServiceRepository(request)));

				// Obtiene las relaciones de entrega
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_RELACIONES_KEY,
						relaciones);
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
				mappings.findForward("seleccion_relaciones"));
	}

	/**
	 * Obtiene la relación de entrega que vamos a ceder así como los posibles
	 * gestores para dicha relación.
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
		// Establecemos el punto actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_SELECT_USUARIO_CEDER, request);

		// Valores del formulario
		CesionRelacionesForm cesionForm = (CesionRelacionesForm) form;

		// Validar el formulario
		ActionErrors errores = cesionForm.validateSeleccion(mappings, request);
		if (errores.isEmpty()) {
			// Información de las relaciones de entrega seleccionadas
			Collection relaciones = getGestionRelacionesBI(request)
					.getRelacionesXIds(cesionForm.getRelacionesSeleccionadas());
			CollectionUtils.transform(relaciones,
					RelacionToPO.getInstance(getServiceRepository(request)));
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_RELACIONES_KEY, relaciones);

			// Permisos de los usuarios
			String[] permisos = new String[] {
					AppPermissions.ADMINISTRACION_TOTAL_SISTEMA,
					AppPermissions.INGRESO_DIRECTO_UNIDADES_DOCUMENTALES };

			// Obtener los archivos de las relaciones para mostrar los gestores
			// con los permisos
			// y
			Iterator it = relaciones.iterator();
			List idsArchivo = new ArrayList();
			while (it.hasNext()) {
				RelacionEntregaPO relacionPO = (RelacionEntregaPO) it.next();
				if (!idsArchivo.contains(relacionPO.getIdarchivoreceptor()))
					idsArchivo.add(relacionPO.getIdarchivoreceptor());
			}

			String[] strIdsArchivo = ArrayUtils.toString(idsArchivo.toArray());

			// Obtener los gestores
			List gestores = getGestionControlUsuarios(request)
					.getUsuariosConPermisosYAllArchivos(permisos, strIdsArchivo);

			setInTemporalSession(request,
					TransferenciasConstants.LISTA_GESTORES_KEY, gestores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor_relaciones"));
		} else {
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_relaciones"));
		}
	}

	/**
	 * Realiza la asignacion de la relación de entrega al nuevo gestor
	 * seleccionado, estableciendo los campos necesarios.
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
		CesionRelacionesForm cesionForm = (CesionRelacionesForm) form;

		// Validar el formulario
		ActionErrors errores = cesionForm.validateSeleccionGestor(mappings,
				request);
		if (errores.isEmpty()) {
			try {
				GestionRelacionesEntregaBI relacionesBI = getGestionRelacionesBI(request);

				// Asignamos el usuario
				relacionesBI.asignarRelacionesAGestorOrgRemitente(
						cesionForm.getRelacionesSeleccionadas(),
						cesionForm.getGestor());

				request.setAttribute(TransferenciasConstants.GESTOR_KEY,
						relacionesBI.asignarRelacionesAGestorArchivo(
								cesionForm.getRelacionesSeleccionadas(),
								cesionForm.getGestor()));

				// Información de las relaciones de entrega seleccionadas
				Collection relaciones = relacionesBI
						.getRelacionesXIds(cesionForm
								.getRelacionesSeleccionadas());
				CollectionUtils
						.transform(relaciones, RelacionToPO
								.getInstance(getServiceRepository(request)));
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_RELACIONES_KEY,
						relaciones);

				setReturnActionFordward(request,
						mappings.findForward("resultado_asignacion_relaciones"));
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("seleccion_gestor_relaciones"));
			}
		} else {
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor_relaciones"));
		}
	}
}