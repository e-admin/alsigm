package docelectronicos.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;

import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.ServiceRepository;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.util.StringUtils;

import descripcion.vos.AutoridadVO;
import docelectronicos.DocumentosConstants;
import docelectronicos.TipoObjeto;
import docelectronicos.exceptions.DocElectronicosException;
import docelectronicos.forms.TareaForm;
import docelectronicos.vos.DocTCapturaVO;
import fondos.model.ElementoCuadroClasificacion;

/**
 * Gestio de las tareas de digitalizacion
 */
public class GestionTareasAction extends BaseAction {
	// html_arbol_docs.jsp

	// html_add_doc_tarea.jsp
	/**
	 * busqueda de elemento sobre el cual realizar la tarea de captura( unidad
	 * documental o entidad)
	 */
	public void initAnadirTareaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionDocumentosElectronicosBI serviceDocElec = services
				.lookupGestionDocumentosElectronicosBI();

		TareaForm frm = (TareaForm) form;
		frm.resetForCreate();
		frm.setBuscarPor(DocumentosConstants.BUSQUEDA_X_DESCRIPTOR);
		setInTemporalSession(request,
				DocumentosConstants.LISTA_LISTAS_DESCRIPTORAS_KEY,
				serviceDocElec.getListasDescriptorasDigitalizables());

		setInTemporalSession(request, DocumentosConstants.LISTA_FONDOS_KEY,
				serviceDocElec.getFondosDigitalizables());

		removeInTemporalSession(request,
				DocumentosConstants.RESPUESTA_BUSQUEDA_ENTIDADES);
		removeInTemporalSession(request,
				DocumentosConstants.RESPUESTA_BUSQUEDA_CUADRO);

		setReturnActionFordward(request,
				mapping.findForward("redirect_anadirTareaPaso1"));
	}

	public void anadirTareaPaso1ExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request,
				mapping.findForward("buscar_elemento_tarea"));

		/* ClientInvocation invocation= */saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_PASO1_CREAR_TAREA,
				request);
	}

	private ActionErrors validateFormInAceptarBusquedaElementoTarea(
			ActionForm form) {
		ActionErrors errors = new ActionErrors();
		TareaForm frm = (TareaForm) form;
		if (GenericValidator.isBlankOrNull(frm.getIdElemento())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							DocumentosConstants.ERRORS_DIGITALIZACION_SELECCIONEUNELEMENTO));
		}
		return errors.size() > 0 ? errors : null;
	}

	public class FinderDescriptor implements Predicate {
		String idABuscar = null;

		public FinderDescriptor(String idDescriptor) {
			idABuscar = idDescriptor.toUpperCase();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object arg0) {
			return ((AutoridadVO) arg0).getId().toUpperCase().equals(idABuscar);
		}
	}

	public class FinderElementoCuadro implements Predicate {
		String idABuscar = null;

		public FinderElementoCuadro(String idElemento) {
			idABuscar = idElemento.toUpperCase();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object arg0) {
			return ((ElementoCuadroClasificacion) arg0).getId().toUpperCase()
					.equals(idABuscar);
		}
	}

	public void anadirTareaPaso2ExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// comprobar elemento seleccionado
			TareaForm frm = (TareaForm) form;
			ActionErrors errors = validateFormInAceptarBusquedaElementoTarea(form);
			if (errors == null) {
				// borrar tarea de session para indicar a la jsp que es una
				// creacion de tarea
				removeInTemporalSession(request, DocumentosConstants.TAREA_KEY);

				saveCurrentInvocation(
						KeysClientsInvocations.DIGITALIZACION_PASO2_CREAR_TAREA,
						request);

				GestionDocumentosElectronicosBI serviceDocElec = getServiceRepository(
						request).lookupGestionDocumentosElectronicosBI();

				List elementos = null;
				if (frm.isBusquedaPorElCuadro()) {
					elementos = (List) getFromTemporalSession(request,
							DocumentosConstants.RESPUESTA_BUSQUEDA_CUADRO);

					ElementoCuadroClasificacion elementoCuadro = (ElementoCuadroClasificacion) CollectionUtils
							.find(elementos,
									new FinderElementoCuadro(frm
											.getIdElemento()));

					serviceDocElec.checkTareaCreableSobreElemento(
							elementoCuadro.getId(), elementoCuadro.getTipo());

					setInTemporalSession(request,
							DocumentosConstants.ELEMENTO_PARA_CREAR_TAREA_KEY,
							elementoCuadro);
					removeInTemporalSession(request,
							DocumentosConstants.DESCRIPTOR_PARA_CREAR_TAREA_KEY);

				} else {
					elementos = (List) getFromTemporalSession(request,
							DocumentosConstants.RESPUESTA_BUSQUEDA_ENTIDADES);
					AutoridadVO autoridad = (AutoridadVO) CollectionUtils.find(
							elementos,
							new FinderDescriptor(frm.getIdElemento()));

					serviceDocElec.checkTareaCreableSobreElemento(
							autoridad.getId(), TipoObjeto.DESCRIPTOR_EN_BD);

					setInTemporalSession(
							request,
							DocumentosConstants.DESCRIPTOR_PARA_CREAR_TAREA_KEY,
							autoridad);
					removeInTemporalSession(request,
							DocumentosConstants.ELEMENTO_PARA_CREAR_TAREA_KEY);
				}

				// lista de usuarios de captura
				List usuariosCapturadores = getServiceRepository(request)
						.lookupGestionControlUsuariosBI().getCapturadores();

				CollectionUtils.transform(usuariosCapturadores,
						new TransformerUsuarios(getServiceRepository(request)));

				request.setAttribute(
						DocumentosConstants.USUARIOS_CAPTURADORES_KEY,
						usuariosCapturadores);

				// Se limpian los campos del formulario
				if (obtenerErrores(request, false) == null) {
					frm.setIdGestor(null);
					frm.setObservaciones(null);
				}
				setReturnActionFordward(request,
						mapping.findForward("formulario_tarea"));

			} else {
				ErrorsTag.saveErrors(request, errors);
				goLastClientExecuteLogic(mapping, form, request, response);
			}
		} catch (DocElectronicosException e) {
			guardarError(request, e);
			goBackExecuteLogic(mapping, form, request, response);

		}

	}

	public void editarTareaExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setReturnActionFordward(request,
				mapping.findForward("formulario_tarea"));

	}

	public void guardarTareaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		TareaForm frm = (TareaForm) form;
		DocTCapturaPO tareaPO = (DocTCapturaPO) getFromTemporalSession(request,
				DocumentosConstants.TAREA_KEY);
		boolean isEditando = tareaPO != null;
		ActionErrors errors = validateFormInGuardarTarea(frm, isEditando);
		if (errors == null) {
			if (isEditando) {
				// actualizar documentoPO
				frm.updateTareaVO(tareaPO);
				getServiceRepository(request)
						.lookupGestionDocumentosElectronicosBI().updateTarea(
								tareaPO);

				goBackExecuteLogic(mapping, form, request, response);

			} else {
				DocTCapturaVO newTarea = new DocTCapturaVO();
				if (!frm.isBusquedaPorElCuadro()) {
					AutoridadVO autoridad = (AutoridadVO) getFromTemporalSession(
							request,
							DocumentosConstants.DESCRIPTOR_PARA_CREAR_TAREA_KEY);
					frm.populateTareaVO(newTarea, autoridad);

				} else {
					ElementoCuadroClasificacion elemento = (ElementoCuadroClasificacion) getFromTemporalSession(
							request,
							DocumentosConstants.ELEMENTO_PARA_CREAR_TAREA_KEY);
					frm.populateTareaVO(newTarea, elemento);

				}
				try {
					/* DocTCapturaVO tarea = */getServiceRepository(request)
							.lookupGestionDocumentosElectronicosBI()
							.insertTarea(newTarea);
					goReturnPointExecuteLogic(mapping, form, request, response);

				} catch (DocElectronicosException e) {
					guardarError(request, e);
					goLastClientExecuteLogic(mapping, form, request, response);
				}
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * @param frm
	 * @param isEditando
	 * @return
	 */
	private ActionErrors validateFormInGuardarTarea(TareaForm frm,
			boolean isEditando) {
		ActionErrors errors = new ActionErrors();
		if (isEditando) {
			return null;
		} else {
			if (GenericValidator.isBlankOrNull(frm.getIdGestor())) {
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								DocumentosConstants.ERRORS_DIGITALIZACION_SELECCIONEUSUCAPTURA));
			}
		}
		return errors.size() > 0 ? errors : null;
	}

	public void buscarElementoDeTareaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		TareaForm frm = (TareaForm) form;
		try {
			GestionDocumentosElectronicosBI documentosService = getServiceRepository(
					request).lookupGestionDocumentosElectronicosBI();
			if (!frm.isBusquedaPorElCuadro()) {

				String idLista = null;
				if (!GenericValidator.isBlankOrNull(frm.getIdLista()))
					idLista = frm.getIdLista();

				String titulo = null;
				if (!GenericValidator.isBlankOrNull(frm.getTituloDescriptor()))
					titulo = frm.getTituloDescriptor();

				// Información de paginación
				PageInfo pageInfo = new PageInfo(request, "nombre");
				pageInfo.setDefautMaxNumItems();

				removeInTemporalSession(request,
						DocumentosConstants.RESPUESTA_BUSQUEDA_ENTIDADES);
				List autoridades = documentosService
						.getDescriptoresDigitalizables(idLista, titulo,
								pageInfo);

				setInTemporalSession(request,
						DocumentosConstants.RESPUESTA_BUSQUEDA_ENTIDADES,
						autoridades);
				removeInTemporalSession(request,
						DocumentosConstants.RESPUESTA_BUSQUEDA_CUADRO);

			} else {

				// busqueda en el cuadro
				String fondo = null;
				if (!GenericValidator.isBlankOrNull(frm.getIdFondo()))
					fondo = frm.getIdFondo();

				// Información de paginación
				PageInfo pageInfo = new PageInfo(request, "fechaEstado");
				pageInfo.setDefautMaxNumItems();

				// por titulo y codigo
				String titulo = null;
				if (!StringUtils.isEmpty(frm.getTitulo())
						&& !StringUtils.isBlank(frm.getTitulo()))
					titulo = frm.getTitulo();

				String codigo = null;
				if (!StringUtils.isEmpty(frm.getCodigo())
						&& !StringUtils.isBlank(frm.getCodigo()))
					codigo = frm.getCodigo();

				removeInTemporalSession(request,
						DocumentosConstants.RESPUESTA_BUSQUEDA_CUADRO);
				List udocs = documentosService.getElementosDigitalizables(
						fondo, titulo, codigo, pageInfo);

				setInTemporalSession(request,
						DocumentosConstants.RESPUESTA_BUSQUEDA_CUADRO, udocs);
				removeInTemporalSession(request,
						DocumentosConstants.RESPUESTA_BUSQUEDA_ENTIDADES);

			}

		} catch (TooManyResultsException e) {
			guardarError(request, e);
		}

		setReturnActionFordward(request,
				mapping.findForward("buscar_elemento_tarea"));
	}

	public void verTareasPendientesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();
		List tareasPendientes = docElectronicosService.getTareasPendientes();
		CollectionUtils.transform(tareasPendientes,
				new DocTCapturaPOTransformer(getServiceRepository(request)));
		setInTemporalSession(request,
				DocumentosConstants.LISTA_TAREAS_PENDIENTES_KEY,
				tareasPendientes);

		setReturnActionFordward(request,
				mapping.findForward("lista_tareas_pendientes"));

		saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_GESTION_TAREAS_PENDIENTES,
				request).setAsReturnPoint(true);

	}

	public void verTareasAGestionarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();

		List tareasAGestionar = docElectronicosService.getTareasAGestionar();
		CollectionUtils.transform(tareasAGestionar,
				new DocTCapturaPOTransformer(getServiceRepository(request)));
		setInTemporalSession(request,
				DocumentosConstants.LISTA_TAREAS_A_GESTIONAR_KEY,
				tareasAGestionar);

		setReturnActionFordward(request,
				mapping.findForward("lista_tareas_gestionar"));

		saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_GESTION_TAREAS_GESTIONAR,
				request).setAsReturnPoint(true);

	}

	public void homeTareasDigitalizacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();

		List tareasAGestionar = docElectronicosService.getTareasAGestionar();
		if (tareasAGestionar != null) {
			if (tareasAGestionar.size() > 5)
				tareasAGestionar = tareasAGestionar.subList(0, 5);
			CollectionUtils
					.transform(tareasAGestionar, new DocTCapturaPOTransformer(
							getServiceRepository(request)));
			setInTemporalSession(request,
					DocumentosConstants.LISTA_TAREAS_A_GESTIONAR_KEY,
					tareasAGestionar);
		}

		List tareasPendientes = docElectronicosService.getTareasPendientes();
		if (tareasPendientes != null) {
			if (tareasPendientes.size() > 5)
				tareasPendientes = tareasPendientes.subList(0, 5);
			CollectionUtils
					.transform(tareasPendientes, new DocTCapturaPOTransformer(
							getServiceRepository(request)));
			setInTemporalSession(request,
					DocumentosConstants.LISTA_TAREAS_PENDIENTES_KEY,
					tareasPendientes);
		}

		setReturnActionFordward(request, mapping.findForward("home_tareas"));

		ClientInvocation cli = saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_GESTION_TAREAS, request);
		cli.setAsReturnPoint(true);

	}

	public void verTareaExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();
		TareaForm tareaForm = (TareaForm) form;

		DocTCapturaVO tareaVO = docElectronicosService.getTarea(tareaForm
				.getIdTarea());
		DocTCapturaPO tareaPO = null;
		if (tareaVO != null)
			tareaPO = new DocTCapturaPO(getServiceRepository(request), tareaVO);

		setInTemporalSession(request, DocumentosConstants.TAREA_KEY, tareaPO);
		ActionRedirect ret = new ActionRedirect(
				mapping.findForward("redirect_vista_tarea"));
		ret.setRedirect(true);
		ret.addParameter(Constants.ID, tareaVO.getIdObj());
		ret.addParameter("tipo",
				String.valueOf(TipoObjeto.valueOf(tareaVO.getTipoObj())));

		setReturnActionFordward(request, ret);

	}

	public void finalizarCapturaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.DIGITALIZACION_FINALIZAR_CAPTURA,
				request);
		TareaForm tareaForm = (TareaForm) form;
		tareaForm.resetForCreate();
		String idTarea = request.getParameter("idTarea");
		tareaForm.setIdTarea(idTarea);

		setReturnActionFordward(request,
				mapping.findForward("finalizar_captura"));
	}

	public void guardarFinalizarCapturaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();
		try {

			DocTCapturaPO tareaPO = (DocTCapturaPO) getFromTemporalSession(
					request, DocumentosConstants.TAREA_KEY);
			TareaForm tareaForm = (TareaForm) form;
			docElectronicosService.finalizarCaptura(tareaPO.getId(),
					tareaForm.getObservaciones());

			DocTCapturaVO tareaActualizada = docElectronicosService
					.getTarea(tareaPO.getId());
			setInTemporalSession(request, DocumentosConstants.TAREA_KEY,
					new DocTCapturaPO(getServiceRepository(request),
							tareaActualizada));

			goBackExecuteLogic(mapping, form, request, response);

		} catch (DocElectronicosException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mapping, form, request, response);

		}
	}

	public void finalizarValidacionTareaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();
		try {

			DocTCapturaPO tarea = (DocTCapturaPO) getFromTemporalSession(
					request, DocumentosConstants.TAREA_KEY);

			// si la tarea tiene elementos sin validar
			boolean tareaTieneElementosNoValidos = docElectronicosService
					.tareaTieneElementosNoValidos(tarea.getIdObj(),
							tarea.getTipoObj());
			if (tareaTieneElementosNoValidos) {
				// dirigir a recogida de motivo de error
				setReturnActionFordward(request,
						mapping.findForward("finalizar_validacion_tarea"));

				saveCurrentInvocation(
						KeysClientsInvocations.DIGITALIZACION_FINALIZAR_VALIDACION_CAPTURA,
						request);

			} else {

				docElectronicosService.finalizarValidacionTarea(tarea.getId(),
						null);
				DocTCapturaVO tareaActualizada = docElectronicosService
						.getTarea(tarea.getId());
				setInTemporalSession(request, DocumentosConstants.TAREA_KEY,
						new DocTCapturaPO(getServiceRepository(request),
								tareaActualizada));

				goLastClientExecuteLogic(mapping, form, request, response);
			}

		} catch (DocElectronicosException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	public void guardarfinalizarValidacionTareaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();
		try {
			TareaForm tareaForm = (TareaForm) form;

			DocTCapturaPO tarea = (DocTCapturaPO) getFromTemporalSession(
					request, DocumentosConstants.TAREA_KEY);

			docElectronicosService.finalizarValidacionTarea(tarea.getId(),
					tareaForm.getObservaciones());
			DocTCapturaVO tareaActualizada = docElectronicosService
					.getTarea(tarea.getId());
			setInTemporalSession(request, DocumentosConstants.TAREA_KEY,
					new DocTCapturaPO(getServiceRepository(request),
							tareaActualizada));

			goBackExecuteLogic(mapping, form, request, response);

		} catch (DocElectronicosException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mapping, form, request, response);
		}

	}

	public void eliminarTareaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();
		try {
			// TareaForm tareaForm = (TareaForm) form;

			DocTCapturaPO tarea = (DocTCapturaPO) getFromTemporalSession(
					request, DocumentosConstants.TAREA_KEY);

			docElectronicosService.eliminarTareaCaptura(tarea.getId());

			List lista = new ArrayList();
			lista.add(tarea);
			setInTemporalSession(request,
					DocumentosConstants.LISTA_TAREAS_ELIMINADAS_KEY, lista);

			setReturnActionFordward(request,
					mapping.findForward("tarea_eliminada"));

			saveCurrentInvocation(
					KeysClientsInvocations.DIGITALIZACION_ELIMINACION_TAREA,
					request);

		} catch (DocElectronicosException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mapping, form, request, response);
		}

	}

}
