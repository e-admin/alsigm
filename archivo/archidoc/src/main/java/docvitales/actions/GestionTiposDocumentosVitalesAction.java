package docvitales.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import common.actions.BaseAction;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;
import common.vos.ResultadoRegistrosVO;

import docvitales.DocumentosVitalesConstants;
import docvitales.exceptions.TipoDocumentoVitalEnUsoException;
import docvitales.forms.TipoDocumentoVitalForm;
import docvitales.vos.TipoDocumentoVitalVO;

/**
 * Acción para la gestión de tipos de documentos vitales.
 */
public class GestionTiposDocumentosVitalesAction extends BaseAction {
	/**
	 * Muestra la lista de tipos de documentos vitales.
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
	public void listaTiposDocumentosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_VITALES_VER_TIPOS_DOCUMENTOS,
				request);

		// Obtener los tipos de documentos vitales
		request.setAttribute(
				DocumentosVitalesConstants.TIPOS_DOCUMENTOS_VITALES_KEY,
				getGestionDocumentosVitalesBI(request)
						.getTiposDocumentosVitales());

		setReturnActionFordward(request, mappings.findForward("ver_tipos"));
	}

	/**
	 * Muestra el formulario del tipo de documento vital.
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
	protected void formExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TipoDocumentoVitalForm frm = (TipoDocumentoVitalForm) form;

		if (StringUtils.isNotBlank(frm.getId())) {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DOCUMENTOS_VITALES_EDITAR_TIPO_DOCUMENTO,
					request);

			// Tipo de documento vital
			TipoDocumentoVitalVO tipo = (TipoDocumentoVitalVO) getFromTemporalSession(
					request,
					DocumentosVitalesConstants.TIPO_DOCUMENTO_VITAL_KEY);

			// Guardar la información del tipo de documento vital
			frm.set(tipo);
		} else {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DOCUMENTOS_VITALES_CREAR_TIPO_DOCUMENTO,
					request);
		}

		setReturnActionFordward(request, mapping.findForward("editar_tipo"));
	}

	/**
	 * Muestra el tipo de documento vital.
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
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TipoDocumentoVitalForm frm = (TipoDocumentoVitalForm) form;

		// Guardar el enlace a la página
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_VITALES_VER_TIPO_DOCUMENTO,
				request);
		invocation.setAsReturnPoint(true);

		// Guardar la información del tipo de documento vital
		setInTemporalSession(
				request,
				DocumentosVitalesConstants.TIPO_DOCUMENTO_VITAL_KEY,
				getGestionDocumentosVitalesBI(request).getTipoDocumentoVital(
						frm.getId()));

		setReturnActionFordward(request, mapping.findForward("ver_tipo"));
	}

	private ActionErrors validateSaveForm(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		TipoDocumentoVitalForm frm = (TipoDocumentoVitalForm) form;
		if (GenericValidator.isBlankOrNull(frm.getDescripcion())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							DocumentosVitalesConstants.ERRORS_GESTIONTIPOSDOCUMENTOS_DESCRIPCION_VACIO));
		}
		if (GenericValidator.isBlankOrNull(frm.getNombre())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							DocumentosVitalesConstants.ERRORS_GESTIONTIPOSDOCUMENTOS_NOMBRE_VACIO));
		}
		return errors;
	}

	/**
	 * Guarda el tipo de documento vital.
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
	protected void saveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Validar el formulario
		// ActionErrors errores = form.validate(mapping, request);

		// ActionErrors errores = form.validate(mapping, request);
		ActionErrors errores = validateSaveForm(form);
		if (errores.isEmpty()) {
			// Recoger la información del tipo de documento vital
			TipoDocumentoVitalForm frm = (TipoDocumentoVitalForm) form;
			TipoDocumentoVitalVO tipo = new TipoDocumentoVitalVO();
			frm.populate(tipo);

			if (StringUtils.isBlank(tipo.getId())) {
				// Crear el tipo de documento vital
				tipo = getGestionDocumentosVitalesBI(request)
						.insertTipoDocumentoVital(tipo);

				// Eliminar el enlace de creación
				popLastInvocation(request);
			} else {
				// Modificar el tipo de documento vital
				getGestionDocumentosVitalesBI(request)
						.updateTipoDocumentoVital(tipo);
			}

			setReturnActionFordward(
					request,
					redirectForwardMethod(request, "method", "retrieve&id="
							+ tipo.getId()));
		} else {
			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Elimina el tipo de documento vital.
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
	protected void removeExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TipoDocumentoVitalForm frm = (TipoDocumentoVitalForm) form;

		// Tipo de documento vital
		TipoDocumentoVitalVO tipo = (TipoDocumentoVitalVO) getFromTemporalSession(
				request, DocumentosVitalesConstants.TIPO_DOCUMENTO_VITAL_KEY);

		try {
			// Eliminar el tipo de documento vital
			getGestionDocumentosVitalesBI(request).deleteTipoDocumentoVital(
					frm.getId());

			goBackExecuteLogic(mapping, form, request, response);
		} catch (TipoDocumentoVitalEnUsoException e) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DocumentosVitalesConstants.ERRORS_DOCVITALES_DELETE_TIPODOCVITAL_EN_USO,
									tipo.getNombre()));
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Elimina los tipos de documentos vitales seleccionados.
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
	protected void removeTiposExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TipoDocumentoVitalForm frm = (TipoDocumentoVitalForm) form;

		// Eliminar el tipo de documento vital
		ResultadoRegistrosVO res = getGestionDocumentosVitalesBI(request)
				.deleteTiposDocumentosVitales(frm.getIdsSeleccionados());

		// Comprobar que no haya errores
		if (!res.getErrores().isEmpty())
			obtenerErrores(request, true).add(res.getErrores());

		goLastClientExecuteLogic(mapping, form, request, response);
	}

}