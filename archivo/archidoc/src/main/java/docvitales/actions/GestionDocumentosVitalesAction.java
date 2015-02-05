package docvitales.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import se.NotAvailableException;
import se.terceros.InfoTercero;
import se.terceros.exceptions.GestorTercerosException;
import se.usuarios.AppUser;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionDocumentosVitalesBI;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.terceros.IFNotValidException;
import common.util.terceros.ThirdPartyHelper;

import docvitales.DocumentosVitalesConstants;
import docvitales.EstadoDocumentoVital;
import docvitales.exceptions.DocumentoVitalException;
import docvitales.forms.DocumentoVitalForm;
import docvitales.vos.DocumentoVitalExtVO;
import docvitales.vos.FormDocumentoVitalVO;
import docvitales.vos.InfoBDocumentoVitalExtVO;

/**
 * Acción para la gestión de documentos vitales.
 */
public class GestionDocumentosVitalesAction extends BaseAction {

	/**
	 * Muestra la lista de documentos vitales a gestionar.
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
	public void homeDocumentosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_VITALES_GESTIONAR_DOCUMENTOS,
				request);

		// Documentos vitales a gestionar
		List documentos = getGestionDocumentosVitalesBI(request)
				.getDocumentosVitalesAGestionar();
		if (documentos.size() > 5)
			documentos = documentos.subList(0, 5);
		request.setAttribute(DocumentosVitalesConstants.DOCUMENTOS_VITALES_KEY,
				documentos);

		// Redireccionamos a la pagina adecuada
		setReturnActionFordward(request,
				mappings.findForward("home_documentos_vitales"));

	}

	/**
	 * Muestra la lista de documentos vitales pendientes de validacion.
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
	public void documentosPendientesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_VITALES_GESTIONAR_DOCUMENTOS_PENDIENTES,
				request);

		// Documentos vitales a gestionar
		request.setAttribute(DocumentosVitalesConstants.DOCUMENTOS_VITALES_KEY,
				getGestionDocumentosVitalesBI(request)
						.getDocumentosVitalesAGestionar());

		// Redireccionamos a la pagina adecuada
		setReturnActionFordward(request,
				mappings.findForward("listado_documentos_vitales"));

	}

	/**
	 * Muestra la información del documento vital.
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
	public void verDocVitalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardar el enlace a la página
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_VITALES_VER_DOCUMENTO,
				request);
		invocation.setAsReturnPoint(true);

		// Información del documento vital
		DocumentoVitalForm frm = (DocumentoVitalForm) form;

		// Servicio de acceso a los documentos vitales
		GestionDocumentosVitalesBI docVitalesBI = getGestionDocumentosVitalesBI(request);

		// Documento vital
		DocumentoVitalExtVO documento = docVitalesBI.getDocumentoVital(frm
				.getId());

		if (documento != null
				&& documento.getEstadoDocVit() == EstadoDocumentoVital.PENDIENTE_VALIDACION) {
			// Información del documento vital
			frm.set(documento);

			// Obtener los tipos de documentos vitales
			setInTemporalSession(request,
					DocumentosVitalesConstants.TIPOS_DOCUMENTOS_VITALES_KEY,
					docVitalesBI.getTiposDocumentosVitales());

			// Comprobar si hay otro documento vigente del mismo tipo
			InfoBDocumentoVitalExtVO docVigente = docVitalesBI
					.getDocumentoVital(documento.getIdBdTerceros(),
							documento.getIdTipoDocVit(),
							EstadoDocumentoVital.VIGENTE);
			if (docVigente != null) {
				setInTemporalSession(request,
						DocumentosVitalesConstants.DOCUMENTO_VITAL_VIGENTE_KEY,
						docVigente);
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										DocumentosVitalesConstants.ERRORS_DOCVITALES_DOS_TIPOS_IGUALES));
			}
		}

		setInTemporalSession(request,
				DocumentosVitalesConstants.DOCUMENTO_VITAL_KEY,
				DocumentoVitalToPO.getInstance().transform(documento));

		setReturnActionFordward(request,
				mappings.findForward("ver_documento_vital"));
	}

	/**
	 * Muestra la información del documento vital vigente.
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
	public void verDocVitalVigenteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardar el enlace a la página
		/* ClientInvocation invocation = */saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_VITALES_VER_DOCUMENTO_VIGENTE,
				request);

		// Información del documento vital
		DocumentoVitalForm frm = (DocumentoVitalForm) form;

		// Servicio de acceso a los documentos vitales
		GestionDocumentosVitalesBI docVitalesBI = getGestionDocumentosVitalesBI(request);

		// Documento vital
		DocumentoVitalExtVO documento = docVitalesBI.getDocumentoVital(frm
				.getId());
		request.setAttribute(DocumentosVitalesConstants.DOCUMENTO_VITAL_KEY,
				DocumentoVitalToPO.getInstance().transform(documento));

		setReturnActionFordward(request,
				mappings.findForward("ver_documento_vital_vigente"));
	}

	/**
	 * Descarga el documento vital para mostrar en un iframe.
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
	public void downloadIFrameExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Documento Vital
		DocumentoVitalPO documentoVital = (DocumentoVitalPO) getFromTemporalSession(
				request, DocumentosVitalesConstants.DOCUMENTO_VITAL_KEY);
		if (documentoVital != null) {
			try {
				downloadIFrame(response,
						documentoVital.getNombreCompletoFichero(),
						documentoVital.getContenido());
			} catch (Exception e) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION, e
								.toString()));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DocumentosVitalesConstants.ERRORS_DOCVITALES_DOC_NO_ENCONTRADO));
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Descarga el documento vital.
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
	public void downloadExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Documento Vital
		DocumentoVitalPO documentoVital = (DocumentoVitalPO) getFromTemporalSession(
				request, DocumentosVitalesConstants.DOCUMENTO_VITAL_KEY);
		if (documentoVital != null) {
			try {
				download(response, documentoVital.getNombreCompletoFichero(),
						documentoVital.getContenido());
			} catch (Exception e) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION, e
								.toString()));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DocumentosVitalesConstants.ERRORS_DOCVITALES_DOC_NO_ENCONTRADO));
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Descarga el documento vital.
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
	public void download2ExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Documento Vital
		DocumentoVitalPO documentoVital = (DocumentoVitalPO) getFromTemporalSession(
				request, DocumentosVitalesConstants.DOCUMENTO_VITAL_KEY);
		if (documentoVital != null) {
			try {
				download(response, documentoVital.getNombreCompletoFichero(),
						documentoVital.getContenido());
			} catch (Exception e) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION, e
								.toString()));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DocumentosVitalesConstants.ERRORS_DOCVITALES_DOC_NO_ENCONTRADO));
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Valida un documento vital.
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
	public void validarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Información del documento vital
		DocumentoVitalForm frm = (DocumentoVitalForm) form;

		// Validar el formulario
		ActionErrors errores = form.validate(mappings, request);
		if (errores.isEmpty()) {
			// Documento Vital en sesión
			InfoBDocumentoVitalExtVO documentoVital = (InfoBDocumentoVitalExtVO) getFromTemporalSession(
					request, DocumentosVitalesConstants.DOCUMENTO_VITAL_KEY);

			// Recoger la información del documento vital
			frm.populate(documentoVital);

			// Validar el documento vital
			getGestionDocumentosVitalesBI(request).validarDocumentoVital(
					documentoVital);

			goLastClientExecuteLogic(mappings, form, request, response);
		} else {
			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Rechaza un documento vital.
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
	public void rechazarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Información del documento vital
		DocumentoVitalForm frm = (DocumentoVitalForm) form;

		// Validar el documento vital
		getGestionDocumentosVitalesBI(request).rechazarDocumentoVital(
				frm.getId());

		goBackExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Pasa un documento vital a histórico.
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
	public void pasarAHistoricoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Información del documento vital
		DocumentoVitalForm frm = (DocumentoVitalForm) form;

		// Pasar el documento vital a histórico
		getGestionDocumentosVitalesBI(request).pasarAHistoricoDocumentoVital(
				frm.getId());

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Busca la información de los terceros para validación.
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
	public void buscarCiudadanosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		buscarCiudadanosLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("ver_documento_vital"));
	}

	/**
	 * Busca la información de los terceros para creación.
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
	public void buscarCiudadanosCreacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		buscarCiudadanosLogic(mappings, form, request, response);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Busca la información de los terceros.
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
	public void buscarCiudadanosLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Información del documento vital
		DocumentoVitalForm frm = (DocumentoVitalForm) form;

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(frm.getMap());

		List terceros = null;

		try {
			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);

			terceros = ThirdPartyHelper.searchThirdParty(frm,
					appUser.getEntity());
		} catch (IFNotValidException inve) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_NUM_IDENTIFICACION_NO_VALIDO, inve
									.getIfNumber()));
		} catch (GestorTercerosException gte) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_GESTOR_TERCEROS));
		} catch (NotAvailableException nae) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_BUSQUEDA_TERECEROS_NO_DISPONIBLE));
		}

		// Lista de ciudadanos
		setInTemporalSession(request,
				DocumentosVitalesConstants.RESULTADOS_BUSQUEDA_TERCEROS,
				terceros);
	}

	/**
	 * Inicia el formulario de creación de documento vital.
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
	public void initCrearDocumentoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DocumentoVitalForm frm = (DocumentoVitalForm) form;
		frm.resetForCreate();

		// Lista de ciudadanos
		removeInTemporalSession(request,
				DocumentosVitalesConstants.RESULTADOS_BUSQUEDA_TERCEROS);

		setReturnActionFordward(request,
				mapping.findForward("redirect_anadirDocumentoPaso1"));
	}

	/**
	 * Paso 1 de la creación de un documento vital.
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
	public void anadirDocumentoPaso1ExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request, mapping.findForward("buscar_tercero"));
		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_VITALES_PASO1_CREAR_DOCUMENTO,
				request);
	}

	private ActionErrors validateFormInAceptarBusquedaTercero(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		DocumentoVitalForm frm = (DocumentoVitalForm) form;
		if (GenericValidator.isBlankOrNull(frm.getIdBdTerceros())) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DocumentosVitalesConstants.ERRORS_DOCVITALES_SEL_TERCERO));
		}
		return errors;
	}

	public class FinderTercero implements Predicate {
		String idABuscar = null;

		public FinderTercero(String idElemento) {
			idABuscar = idElemento.toUpperCase();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object arg0) {
			return ((InfoTercero) arg0).getId().toUpperCase().equals(idABuscar);
		}
	}

	public ActionErrors validateCrearDocumentoVital(HttpServletRequest request,
			DocumentoVitalForm frm) {

		FormFile fichero = frm.getFile();
		ActionErrors errors = new ActionErrors();

		if (frm.getIdTipoDocVit() == null
				|| frm.getIdTipoDocVit().trim().equals(""))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DocumentosVitalesConstants.LABEL_DOCVITALES_TIPO_DOCUMENTO,
									request.getLocale())));
		if (fichero == null || StringUtils.isBlank(fichero.getFileName())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DocumentosVitalesConstants.LABEL_DOCVITALES_NUEVO_DOCUMENTO,
									request.getLocale())));
		}

		return errors;
	}

	/**
	 * Paso 2 de la creación de un documento vital.
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
	public void anadirDocumentoPaso2ExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// comprobar elemento seleccionado
		DocumentoVitalForm frm = (DocumentoVitalForm) form;
		ActionErrors errors = validateFormInAceptarBusquedaTercero(form);
		if (errors.isEmpty()) {
			saveCurrentInvocation(
					KeysClientsInvocations.DOCUMENTOS_VITALES_PASO2_CREAR_DOCUMENTO,
					request);

			InfoTercero tercero = (InfoTercero) CollectionUtils
					.find((List) getFromTemporalSession(
							request,
							DocumentosVitalesConstants.RESULTADOS_BUSQUEDA_TERCEROS),
							new FinderTercero(frm.getIdBdTerceros()));

			setInTemporalSession(request,
					DocumentosVitalesConstants.TERCERO_DE_DOCUMENTO_VITAL,
					tercero);

			// Obtener los tipos de documentos vitales
			setInTemporalSession(request,
					DocumentosVitalesConstants.TIPOS_DOCUMENTOS_VITALES_KEY,
					getGestionDocumentosVitalesBI(request)
							.getTiposDocumentosVitales());

			setReturnActionFordward(request,
					mapping.findForward("formulario_documento_vital"));
		} else {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Guarda el documento vital.
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
	public void guardarDocumentoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// comprobar elemento seleccionado
			DocumentoVitalForm frm = (DocumentoVitalForm) form;
			ActionErrors errors = validateCrearDocumentoVital(request, frm);
			if (errors.isEmpty()) {
				// Recoger información del documento vital
				FormDocumentoVitalVO documento = new FormDocumentoVitalVO();
				frm.populate(documento);

				// Crear el documento vital
				InfoBDocumentoVitalExtVO docVital = getGestionDocumentosVitalesBI(
						request).insertDocumentoVitalValidado(documento);

				// Eliminar enlaces hasta el punto de retorno
				getInvocationStack(request).goToReturnPoint(request);

				setReturnActionFordward(
						request,
						redirectForwardMethod(request,
								"/gestionDocumentosVitales", "method",
								"verDocVital&id=" + docVital.getId()));
			} else {
				ErrorsTag.saveErrors(request, errors);
				goLastClientExecuteLogic(mapping, form, request, response);
			}
		} catch (DocumentoVitalException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mapping, form, request, response);
		} catch (Exception e) {
			logger.error("Error al guardar el documento", e);
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_ALMACENAR_DOCUMENTO));
			goLastClientExecuteLogic(mapping, form, request, response);
		}

	}
}