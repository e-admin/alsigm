package docelectronicos.actions;

import gcontrol.view.UsuarioPO;
import gcontrol.vos.UsuarioVO;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionDocumentosElectronicosBI;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

import docelectronicos.DocumentosConstants;
import docelectronicos.exceptions.DocElectronicosException;
import docelectronicos.forms.CesionControlTareasForm;
import docelectronicos.vos.BusquedaTareasVO;
import docelectronicos.vos.DocTCapturaVO;

public class CesionControlTareasAction extends BaseAction {

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		/* ClientInvocation invocation = */saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_SELECT_TAREA_CEDER,
				request);

		CesionControlTareasForm cesionForm = (CesionControlTareasForm) form;
		cesionForm.setTipoBusqueda(DocumentosConstants.TIPO_BUSQUEDA_POR_TAREA);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_tareas"));

	}

	public void verBuscadorGestorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_SELECT_TAREA_CEDER,
				request);

		// Establecer el tipo de búsqueda
		CesionControlTareasForm cesionForm = (CesionControlTareasForm) form;
		cesionForm
				.setTipoBusqueda(DocumentosConstants.TIPO_BUSQUEDA_POR_USUARIO_CAPTURA);

		// lista de usuarios de captura
		removeInTemporalSession(request,
				DocumentosConstants.USUARIOS_CAPTURADORES_KEY);
		List usuariosCapturadores = getServiceRepository(request)
				.lookupGestionControlUsuariosBI().getCapturadores();
		CollectionUtils.transform(usuariosCapturadores,
				new TransformerUsuarios(getServiceRepository(request)));
		setInTemporalSession(request,
				DocumentosConstants.USUARIOS_CAPTURADORES_KEY,
				usuariosCapturadores);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_tareas"));
	}

	public void buscarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CesionControlTareasForm cesionForm = (CesionControlTareasForm) form;

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_SELECT_TAREA_CEDER,
				request);
		invocation.addParameters(cesionForm.getMap());

		// Validar el formulario
		ActionErrors errores = form.validate(mappings, request);
		if (errores.isEmpty()) {
			BusquedaTareasVO busquedaVO = new BusquedaTareasVO();

			if (cesionForm.getTipoBusqueda() == DocumentosConstants.TIPO_BUSQUEDA_POR_TAREA) {
				busquedaVO.setTitulo(cesionForm.getTitulo());
				busquedaVO.setReferencia(cesionForm.getReferencia());
			} else {
				busquedaVO.setIdUsuarioCaptura(cesionForm.getGestor());
			}

			try {
				List tareas = getGestionDocumentosElectronicosBI(request)
						.getTareasCedibles(busquedaVO);
				CollectionUtils.transform(tareas, new DocTCapturaPOTransformer(
						getServiceRepository(request)));

				request.setAttribute(
						DocumentosConstants.LISTA_TAREAS_CEDIBLES_KEY, tareas);

				cesionForm.setResultado(true);
			} catch (TooManyResultsException e) {
				guardarError(request, e);
			}
		} else
			obtenerErrores(request, true).add(errores);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_tareas"));
	}

	private ActionErrors validateSeleccionGestor(HttpServletRequest request,
			CesionControlTareasForm cesionForm) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isBlank(cesionForm.getNuevoGestor()))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									DocumentosConstants.ERROR_DIGITALIZACION_NECESARIO_USER_CAPTURADOR,
									request.getLocale())));

		return errors;
	}

	private ActionErrors validateSeleccionTareas(HttpServletRequest request,
			CesionControlTareasForm cesionForm) {
		ActionErrors errors = new ActionErrors();

		if (cesionForm.getTareasSeleccionadas() == null
				|| cesionForm.getTareasSeleccionadas().length == 0) {

			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									DocumentosConstants.ERROR_DIGITALIZACION_NECESARIO_SEL_TAREAS_A_CEDER,
									request.getLocale())));
		}

		return errors;
	}

	public void seleccionarExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Valores del formulario
		CesionControlTareasForm cesionForm = (CesionControlTareasForm) form;

		// Validar el formulario
		ActionErrors errores = validateSeleccionTareas(request, cesionForm);
		if (errores.isEmpty()) {
			// Establecemos el punto actual de navegacion
			saveCurrentInvocation(
					KeysClientsInvocations.DIGITALIZACION_SELECT_USUARIO_CEDER,
					request);

			// Información de las relaciones de entrega seleccionadas
			removeInTemporalSession(request,
					DocumentosConstants.LISTA_TAREAS_KEY);
			Collection tareas = getGestionDocumentosElectronicosBI(request)
					.getTareasXIds(cesionForm.getTareasSeleccionadas());
			CollectionUtils.transform(tareas, new DocTCapturaPOTransformer(
					getServiceRepository(request)));
			setInTemporalSession(request, DocumentosConstants.LISTA_TAREAS_KEY,
					tareas);

			// Obtenemos los posibles usuarios de la tarea
			removeInTemporalSession(request,
					DocumentosConstants.USUARIOS_CAPTURADORES_KEY);
			List usuariosCapturadores = getServiceRepository(request)
					.lookupGestionControlUsuariosBI().getCapturadores();
			CollectionUtils.transform(usuariosCapturadores,
					new TransformerUsuarios(getServiceRepository(request)));
			setInTemporalSession(request,
					DocumentosConstants.USUARIOS_CAPTURADORES_KEY,
					usuariosCapturadores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor_tareas"));
		} else {
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_tareas"));
		}
	}

	public void asignarGestorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CesionControlTareasForm cesionForm = (CesionControlTareasForm) form;

		// Validar el formulario
		ActionErrors errores = validateSeleccionGestor(request, cesionForm);
		if (errores.isEmpty()) {
			try {
				GestionDocumentosElectronicosBI documentosBI = getGestionDocumentosElectronicosBI(request);
				List tareas = (List) getFromTemporalSession(request,
						DocumentosConstants.LISTA_TAREAS_KEY);

				String[] ids = new String[tareas.size()];
				int i = 0;
				for (Iterator itTareas = tareas.iterator(); itTareas.hasNext();) {
					DocTCapturaVO tarea = (DocTCapturaVO) itTareas.next();
					ids[i] = tarea.getId();
					i++;
				}

				documentosBI.asignarTareaACapturador(ids,
						cesionForm.getNuevoGestor());
				// Asignamos el usuario a la/s previsión/es seleccionada/s
				UsuarioVO nuevoUsuario = getGestionControlUsuarios(request)
						.getUsuario(cesionForm.getNuevoGestor());
				request.setAttribute(DocumentosConstants.CAPTURADOR_KEY,
						new UsuarioPO(nuevoUsuario,
								getServiceRepository(request)));

				// // Información de las relaciones de entrega seleccionadas
				// List tareas = documentosBI
				// .getTareasXIds(cesionForm.getTareasSeleccionadas());
				// CollectionUtils.transform(tareas,new
				// DocTCapturaPOTransformer(getServiceRepository(request)) );
				// setInTemporalSession(request,
				// DocumentosConstants.LISTA_TAREAS_KEY,
				// tareas);

				setReturnActionFordward(request,
						mappings.findForward("resultado_asignacion_tareas"));
				saveCurrentInvocation(
						KeysClientsInvocations.DIGITALIZACION_RESULTADO_CESION_TAREA,
						request);
			} catch (DocElectronicosException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("seleccion_gestor_tareas"));
			}
		} else {
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor_tareas"));
		}
	}

}
