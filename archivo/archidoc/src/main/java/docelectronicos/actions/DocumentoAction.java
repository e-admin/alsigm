package docelectronicos.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.model.EstadoREntrega;
import transferencias.vos.RelacionEntregaVO;
import util.ErrorsTag;
import util.MimeTypesUtil;
import valoracion.view.SerieToPO;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;
import common.util.ResponseUtil;
import common.util.StringUtils;
import common.util.TypeConverter;
import common.vos.NavegadorElementosVO;

import docelectronicos.DocumentosConstants;
import docelectronicos.TipoObjeto;
import docelectronicos.exceptions.DocElectronicosException;
import docelectronicos.forms.DocumentoForm;
import docelectronicos.model.GestionDocumentosElectronicosBIImpl.DataClfDocYRepEcm;
import docelectronicos.vos.DocDocumentoExtVO;
import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.DocTCapturaVO;
import fondos.actions.UnidadDocumentalToPO;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;
import fondos.vos.UnidadDocumentalVO;

/**
 * Acción para la gestión de documentos electrónicos.
 */
public class DocumentoAction extends BaseAction {


	public void viewDocumentCFExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_HOME, request);

		// Leer el identificador del documento
		String idElemento = request.getParameter(Constants.ID);

		if (idElemento == null) {
			idElemento = (String) getFromTemporalSession(request,
					DocumentosConstants.ACCESO_DOCUMENTOS_IDELEMENTO_KEY);
		}

		// Obtener los documentos del elemento
		if (idElemento != null) {

			List listaDocumentos = getGestionDocumentosElectronicosBI(request)
					.getDocumentosElementoCuadro(idElemento);

			if (ListUtils.isNotEmpty(listaDocumentos)) {
				DocDocumentoVO documento = (DocDocumentoVO) listaDocumentos
						.get(0);

				if (documento != null) {
					String idDocumento = documento.getId();
					String paramTipoObjeto = "" + TipoObjeto.ELEMENTO_CF;
					retrieveCodeLogic(mappings, form, request, response,
							idDocumento, idElemento, paramTipoObjeto);
				} else {
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											DocumentosConstants.ERROR_DOC_ELECTRONICOS_DOCUMENTO_NO_ENCONTRADO));

					goBackExecuteLogic(mappings, form, request, response);
				}
			} else {
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										DocumentosConstants.ERROR_DOC_ELECTRONICOS_DOCUMENTO_NO_ENCONTRADO));
				goBackBusquedaExecuteLogic(mappings, form, request, response);
			}
		} else {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DocumentosConstants.ERROR_DOC_ELECTRONICOS_DOCUMENTO_NO_ENCONTRADO));
			goBackBusquedaExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Muestra la información del documento electrónico.
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
	public void retrieveExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de retrieveExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_HOME, request);

		// Leer el identificador del documento
		String idDocumento = request.getParameter(Constants.ID);

		// Leer el identificador del objeto
		String idObjeto = request.getParameter("idObjeto");
		if (logger.isInfoEnabled())
			logger.info("Id Objeto: " + idObjeto);

		String paramTipoObjeto = request.getParameter("tipoObjeto");

		retrieveCodeLogic(mappings, form, request, response, idDocumento,
				idObjeto, paramTipoObjeto);

	}

	public void retrieveCodeLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String idDocumento, String idObjeto, String paramTipoObjeto) {
		if (logger.isInfoEnabled())
			logger.info("Id Documento: " + idDocumento);


		ActionErrors errors = getErrors(request, true);

		// Leer el tipo de objeto
		int tipoObjeto = TypeConverter.toInt(paramTipoObjeto, -1);
		if (tipoObjeto < 0) {
			Integer tipoObjetoInteger = (Integer) getFromTemporalSession(
					request, DocumentosConstants.OBJECT_TYPE_KEY);
			tipoObjeto = (tipoObjetoInteger != null ? tipoObjetoInteger
					.intValue() : TipoObjeto.DESCRIPTOR);
		}
		if (logger.isInfoEnabled())
			logger.info("Tipo Objeto: " + tipoObjeto);

		// Leo el parametro para saber si estoy en la descripcion de la ficha de
		// documentos con enlace interno.
		int descripcion = TypeConverter.toInt(
				request.getParameter("descripcion"), -1);

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceClient(request));
		if (StringUtils.isNotBlank(idDocumento)) {

			// Servicio de gestión de documentos
			GestionDocumentosElectronicosBI docsBI = services
					.lookupGestionDocumentosElectronicosBI();

			// Leer la información del documento
			DocDocumentoVO documento = null;
			if (descripcion > 0)
				documento = docsBI.getDocumentoByIdInterno(tipoObjeto,
						idObjeto, idDocumento);
			else
				documento = docsBI.getDocumento(tipoObjeto, idObjeto,
						idDocumento);

			if (logger.isInfoEnabled())
				logger.info("DocDocumentoVO: " + documento);

			// Guardar la información del documento
			((DocumentoForm) form).set(documento);

			if (documento != null) {
				List listaDocumentos = getGestionDocumentosElectronicosBI(
						request).getDocumentosVisiblesDesdeCuadro(tipoObjeto,
						documento.getIdObjeto(), documento.getIdClfPadre());
				NavegadorElementosVO navegadorVO = new NavegadorElementosVO(
						listaDocumentos, documento.getId());

				request.setAttribute(
						DocumentosConstants.NAVEGADOR_DOCUMENTOS_KEY,
						navegadorVO);
			}
			else{
				errors.add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								DocumentosConstants.ERROR_DOC_ELECTRONICOS_DOCUMENTO_NO_ENCONTRADO));
			}

			// Carga la información del documento electrónico.
			request.setAttribute(DocumentosConstants.FILE_INFO_KEY,
					docsBI.getInfoFichero(documento));

			// Obtener la información del clasificador padre
			if (StringUtils.isNotBlank(documento.getIdClfPadre()))
				request.setAttribute(
						DocumentosConstants.FOLDER_KEY,
						docsBI.getClasificador(tipoObjeto, idObjeto,
								documento.getIdClfPadre()));
		} else {
			((DocumentoForm) form).setIdObjeto(idObjeto);
			((DocumentoForm) form).setTipoObjeto(tipoObjeto);
		}

		// Información del objeto
		if (tipoObjeto == TipoObjeto.DESCRIPTOR) {
			request.setAttribute(DocumentosConstants.DESCRIPTOR_KEY,
					getGestionDescripcionBI(request).getDescriptorExt(idObjeto));
		} else // if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
		{
			ElementoCuadroClasificacionVO elemento = getGestionCuadroClasificacionBI(
					request).getElementoCuadroClasificacion(idObjeto);
			if (elemento != null) {
				switch (elemento.getTipo()) {
				case ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL:
					UnidadDocumentalToPO udocTransformer = new UnidadDocumentalToPO(
							services);
					UnidadDocumentalVO udoc = getGestionUnidadDocumentalBI(
							request).getUnidadDocumental(idObjeto);
					request.setAttribute(
							DocumentosConstants.UNIDAD_DOCUMENTAL_KEY,
							udocTransformer
									.transform(udoc, getAppUser(request)));
					break;

				case ElementoCuadroClasificacion.TIPO_SERIE:
					SerieToPO serieTransformer = SerieToPO
							.getInstance(services);
					SerieVO serie = getGestionSeriesBI(request).getSerie(
							idObjeto);

					setInTemporalSession(request,
							DocumentosConstants.SERIE_KEY,
							serieTransformer.transform(serie,
									getAppUser(request)));
					break;
				}
			}
			else{
				errors.add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								DocumentosConstants.ERROR_DOC_ELECTRONICOS_DOCUMENTO_NO_ENCONTRADO));

			}
		}

		if(errors != null && !errors.isEmpty()){
			ErrorsTag.saveErrors(request, errors);

			goBackExecuteLogic(mappings, form, request, response);
		}
		else{
			setReturnActionFordward(request, mappings.findForward("ver_documento"));
		}
	}

	/**
	 * Muestra el formulario del documento electrónico.
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
		logger.info("Inicio de formExecuteLogic");

		// Leer el identificador del clasificador
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id Clasificador: " + id);

		// Leer el identificador del objeto
		String idObjeto = request.getParameter("idObjeto");
		if (logger.isInfoEnabled())
			logger.info("Id Objeto: " + idObjeto);

		// Leer el tipo de objeto
		int tipoObjeto = TypeConverter.toInt(
				request.getParameter("tipoObjeto"), TipoObjeto.DESCRIPTOR);
		if (logger.isInfoEnabled())
			logger.info("Tipo Objeto: " + tipoObjeto);

		// Leer el identificador del clasificador
		String idClfPadre = request.getParameter("idClfPadre");
		if (logger.isInfoEnabled())
			logger.info("Id Clasificador Padre: " + idClfPadre);

		if (StringUtils.isNotBlank(id)) {
			// // Guardar el enlace a la página
			// saveCurrentInvocation(KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_DOCUMENTO_EDIT,
			// request, getCustomConfigureView(request));
			saveCurrentInvocation(
					KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_DOCUMENTO_EDIT,
					request);

			// Leer la información del documento
			DocDocumentoVO documento = getGestionDocumentosElectronicosBI(
					request).getDocumento(tipoObjeto, idObjeto, id);
			if (logger.isInfoEnabled())
				logger.info("DocDocumentoVO: " + documento);

			// Guardar la información del formulario
			((DocumentoForm) form).set(documento);
		} else {
			// // Guardar el enlace a la página
			// saveCurrentInvocation(KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_DOCUMENTO_FORM,
			// request, getCustomConfigureView(request));
			saveCurrentInvocation(
					KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_DOCUMENTO_FORM,
					request);

			((DocumentoForm) form).setIdObjeto(idObjeto);
			((DocumentoForm) form).setTipoObjeto(tipoObjeto);
		}

		try {
			DataClfDocYRepEcm dataClfDocYRepEcm = null;
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceClient(request));

			// Información del objeto
			if (tipoObjeto == TipoObjeto.DESCRIPTOR) {
				// Obtener el identificador de la ficha de los clasificadores
				// por defecto
				dataClfDocYRepEcm = services
						.lookupGestionDocumentosElectronicosBI()
						.getIdFichaClfDocYRepEcmDescriptor(idObjeto);

				if (StringUtils.isEmpty(dataClfDocYRepEcm.getIdRepEcm()))
					throw new DocElectronicosException(
							DocElectronicosException.XNO_SE_PUEDE_REALIZAR_OPERACION_FALTA_REPOSITORIO_ECM_DESCRIPTOR);

				request.setAttribute(
						DocumentosConstants.DESCRIPTOR_KEY,
						getGestionDescripcionBI(request).getDescriptorExt(
								idObjeto));

			} else // if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
			{
				dataClfDocYRepEcm = getGestionDocumentosElectronicosBI(request)
						.getIdFichaClfDocYRepEcmElementoCF(idObjeto);

				if (StringUtils.isEmpty(dataClfDocYRepEcm.getIdRepEcm()))
					throw new DocElectronicosException(
							DocElectronicosException.XNO_SE_PUEDE_REALIZAR_OPERACION_FALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO);

				ElementoCuadroClasificacionVO elemento = getGestionCuadroClasificacionBI(
						request).getElementoCuadroClasificacion(idObjeto);
				if (elemento != null) {
					switch (elemento.getTipo()) {
					case ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL:
						UnidadDocumentalToPO udocTransformer = new UnidadDocumentalToPO(
								services);
						UnidadDocumentalVO udoc = getGestionUnidadDocumentalBI(
								request).getUnidadDocumental(idObjeto);
						request.setAttribute(
								DocumentosConstants.UNIDAD_DOCUMENTAL_KEY,
								udocTransformer.transform(udoc,
										getAppUser(request)));
						break;

					case ElementoCuadroClasificacion.TIPO_SERIE:
						SerieToPO serieTransformer = SerieToPO
								.getInstance(services);
						SerieVO serie = getGestionSeriesBI(request).getSerie(
								idObjeto);
						request.setAttribute(DocumentosConstants.SERIE_KEY,
								serieTransformer.transform(serie,
										getAppUser(request)));
						break;
					}
				}
			}

			setReturnActionFordward(request,
					mapping.findForward("editar_documento"));
		} catch (DocElectronicosException e) {
			guardarError(request, e);
			goBackExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Descarga el fichero del documento.
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inicio de downloadExecuteLogic");

		// Leer el identificador del documento
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id Documento: " + id);

		// Leer el identificador del objeto
		String idObjeto = request.getParameter("idObjeto");
		if (logger.isInfoEnabled())
			logger.info("Id Objeto: " + idObjeto);

		// Leer el tipo de objeto
		int tipo = TypeConverter.toInt(request.getParameter("tipoObjeto"),
				TipoObjeto.DESCRIPTOR);
		if (logger.isInfoEnabled())
			logger.info("Tipo Objeto: " + tipo);

		DocDocumentoExtVO fichero = null;

		if (StringUtils.isNotBlank(id))
			fichero = getGestionDocumentosElectronicosBI(request)
					.getDocumentoExt(tipo, idObjeto, id);

		if (fichero != null)
			download(response, fichero);
		else {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DocumentosConstants.ERROR_DOC_ELECTRONICOS_DOCUMENTO_NO_ENCONTRADO));
		}
	}

	/**
	 * Descarga el fichero del documento para mostrarlo en un IFrame.
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
			HttpServletResponse response) throws Exception {
		try {
			logger.info("Inicio de downloadExecuteLogic");

			// Leer el identificador del documento
			String id = request.getParameter(Constants.ID);
			if (logger.isInfoEnabled())
				logger.info("Id Documento: " + id);

			// Leer el identificador del objeto
			String idObjeto = request.getParameter("idObjeto");
			if (logger.isInfoEnabled())
				logger.info("Id Objeto: " + idObjeto);

			// Leer el tipo de objeto
			int tipo = TypeConverter.toInt(request.getParameter("tipoObjeto"),
					TipoObjeto.DESCRIPTOR);
			if (logger.isInfoEnabled())
				logger.info("Tipo Objeto: " + tipo);

			DocDocumentoExtVO fichero = null;

			if (StringUtils.isNotBlank(id))
				fichero = getGestionDocumentosElectronicosBI(request)
						.getDocumentoExt(tipo, idObjeto, id);

			if (fichero != null)
				downloadIFrame(response, fichero);
			else {
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										DocumentosConstants.ERROR_DOC_ELECTRONICOS_DOCUMENTO_NO_ENCONTRADO));
			}
		} catch (Exception e) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DocumentosConstants.ERROR_DOC_ELECTRONICOS_DOCUMENTO_EXTERNO_NO_ENCONTRADO));
			setReturnActionFordward(request,
					mappings.findForward("iframeError"));
		}
	}

	/**
	 * Guarda el documento electrónico.
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			logger.info("Inicio de saveExecuteLogic");

			// Validar el formulario
			ActionErrors errores = form.validate(mapping, request);
			if ((errores == null) || errores.isEmpty()) {
				logger.info("Formulario validado");

				// Recoger la información del documento
				DocDocumentoExtVO documento = new DocDocumentoExtVO();
				((DocumentoForm) form).populate(documento);
				if (logger.isDebugEnabled())
					logger.debug("DocDocumentoVO:\n" + documento.toString());

				DocTCapturaVO tarea = (DocTCapturaVO) getFromTemporalSession(
						request, DocumentosConstants.TAREA_KEY);
				if (StringUtils.isBlank(documento.getId())) {
					if (tarea != null) {
						// Crear el documento
						((DocumentoForm) form)
								.set(getGestionDocumentosElectronicosBI(request)
										.insertDocumentoDesdeTarea(documento));
					} else {
						// Crear el documento
						((DocumentoForm) form)
								.set(getGestionDocumentosElectronicosBI(request)
										.insertDocumentoDesdeCuadro(documento));
					}

					// Eliminar la invocación anterior
					popLastInvocation(request);
				} else {
					if (tarea != null) {
						// Modificar el documento
						((DocumentoForm) form)
								.set(getGestionDocumentosElectronicosBI(request)
										.updateDocumentoDesdeTarea(documento));
					} else {
						// Modificar el documento
						((DocumentoForm) form)
								.set(getGestionDocumentosElectronicosBI(request)
										.updateDocumentoDesdeCuadro(documento));
					}
				}

				RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
						request, TransferenciasConstants.RELACION_KEY);
				if (relacion != null) {
					ServiceRepository services = ServiceRepository
							.getInstance(ServiceClient
									.create(getAppUser(request)));
					GestionRelacionesEntregaBI relacionBI = services
							.lookupGestionRelacionesBI();
					if (relacion.isRechazada()) {
						relacion.setEstado(EstadoREntrega.ABIERTA
								.getIdentificador());
						try {
							relacionBI.updateRelacion(relacion);
						} catch (ActionNotAllowedException e) {
							guardarError(request, e);
						}
					}
				}

				// Actualizar el árbol de documentos
				request.setAttribute(DocumentosConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);

				setReturnActionFordward(
						request,
						redirectForwardMethod(
								request,
								"/documento",
								"method",
								"retrieve"
										+ (documento.getId() != null ? "&id="
												+ documento.getId() : "")
										+ (documento.getIdObjeto() != null ? "&idObjeto="
												+ documento.getIdObjeto()
												: "") + "&tipoObjeto="
										+ documento.getTipoObjeto()));
			} else {
				logger.info("Formulario inv\u00E1lido");

				// Añadir los errores al request
				obtenerErrores(request, true).add(errores);

				setReturnActionFordward(request,
						mapping.findForward("editar_documento"));
			}
		} catch (Exception e) {
			logger.error("Error al guardar el documento", e);
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_ALMACENAR_DOCUMENTO));

			setReturnActionFordward(request,
					mapping.findForward("editar_documento"));
		}
	}

	/**
	 * Elimina el documento electrónico.
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inicio de removeExecuteLogic");

		// Leer el identificador del documento
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id Documento: " + id);

		// Leer el identificador del objeto
		String idObjeto = request.getParameter("idObjeto");
		if (logger.isInfoEnabled())
			logger.info("Id Objeto: " + idObjeto);

		// Leer el tipo de objeto
		int tipoObjeto = TypeConverter.toInt(
				request.getParameter("tipoObjeto"), TipoObjeto.DESCRIPTOR);
		if (logger.isInfoEnabled())
			logger.info("Tipo Objeto: " + tipoObjeto);

		// Obtener el servicio
		GestionDocumentosElectronicosBI documentosBI = getGestionDocumentosElectronicosBI(request);

		// Obtener la información del documento
		DocDocumentoVO documento = documentosBI.getDocumento(tipoObjeto,
				idObjeto, id);

		DataClfDocYRepEcm dataClfDocYRepEcm = null;

		try {

			// Información del objeto
			if (tipoObjeto == TipoObjeto.DESCRIPTOR) {
				// Obtener el identificador de la ficha de los clasificadores
				// por defecto
				dataClfDocYRepEcm = documentosBI
						.getIdFichaClfDocYRepEcmDescriptor(idObjeto);

				if (StringUtils.isEmpty(dataClfDocYRepEcm.getIdRepEcm()))
					throw new DocElectronicosException(
							DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_DOCUMENTO_XFALTA_REPOSITORIO_ECM_DESCRIPTOR);
			} else {

				dataClfDocYRepEcm = documentosBI
						.getIdFichaClfDocYRepEcmElementoCF(idObjeto);

				if (StringUtils.isEmpty(dataClfDocYRepEcm.getIdRepEcm()))
					throw new DocElectronicosException(
							DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_DOCUMENTO_XFALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO);
			}

			// Eliminar el documento
			documentosBI.removeDocumento(documento);

			// Actualizar el árbol de documentos
			request.setAttribute(DocumentosConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);

			// Volver al clasificador padre
			setReturnActionFordward(
					request,
					redirectForwardMethod(
							request,
							"/clasificador",
							"method",
							"retrieve"
									+ (documento.getIdClfPadre() != null ? "&id="
											+ documento.getIdClfPadre()
											: "")
									+ (documento.getIdObjeto() != null ? "&idObjeto="
											+ documento.getIdObjeto()
											: "") + "&tipoObjeto=" + tipoObjeto));
		} catch (DocElectronicosException e) {
			guardarError(request, e);
			setReturnActionFordward(
					request,
					redirectForwardMethod(
							request,
							"/documento",
							"method",
							"retrieve"
									+ (documento.getId() != null ? "&id="
											+ documento.getId() : "")
									+ (documento.getIdObjeto() != null ? "&idObjeto="
											+ documento.getIdObjeto()
											: "") + "&tipoObjeto="
									+ documento.getTipoObjeto()));
		}
	}

	/**
	 * Muestra el contenido del fichero.
	 *
	 * @param response
	 *            {@link HttpServletResponse}
	 * @param fichero
	 *            Información del fichero.
	 * @throws IOException
	 *             si ocurre algún error.
	 */
	protected void download(HttpServletResponse response,
			DocDocumentoExtVO fichero) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fichero.getFileName() + "\"");
		ResponseUtil.getInstance().agregarCabecerasHTTP(response);
		response.setContentLength(new Double(fichero.getTamanoFich())
				.intValue());

		ServletOutputStream os = response.getOutputStream();
		os.write(fichero.getContenido());
		os.flush();
		os.close();
	}

	/**
	 * Muestra el contenido del fichero para ver en un iFrame.
	 *
	 * @param response
	 *            {@link HttpServletResponse}
	 * @param fichero
	 *            Información del fichero.
	 * @throws IOException
	 *             si ocurre algún error.
	 */
	protected void downloadIFrame(HttpServletResponse response,
			DocDocumentoExtVO fichero) throws IOException {
		response.setContentType(MimeTypesUtil.getInstance().getMimeType(
				fichero.getFileName()));
		response.setHeader("Content-disposition", "inline; filename=\""
				+ fichero.getFileName() + "\"");
		ResponseUtil.getInstance().agregarCabecerasHTTP(response);
		response.setContentLength(new Double(fichero.getTamanoFich())
				.intValue());

		ServletOutputStream os = response.getOutputStream();
		os.write(fichero.getContenido());
		os.flush();
		os.close();
	}
}