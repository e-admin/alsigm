package docelectronicos.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.model.EstadoREntrega;
import transferencias.vos.RelacionEntregaVO;
import util.TreeNode;
import valoracion.view.SerieToPO;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;
import common.util.TypeConverter;

import docelectronicos.DocumentosConstants;
import docelectronicos.TipoObjeto;
import docelectronicos.exceptions.DocElectronicosException;
import docelectronicos.forms.ClasificadorForm;
import docelectronicos.model.DocumentosTreeView;
import docelectronicos.model.GestionDocumentosElectronicosBIImpl.DataClfDocYRepEcm;
import docelectronicos.vos.DocClasificadorVO;
import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.DocTCapturaVO;
import docelectronicos.vos.DocumentosTreeModelItemVO;
import fondos.actions.UnidadDocumentalToPO;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;
import fondos.vos.UnidadDocumentalVO;

/**
 * Acción para la gestión de clasificadores.
 */
public class ClasificadorAction extends BaseAction {

	/**
	 * Muestra la información del clasificador.
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
		retrieveCodeLogic(mappings, form, request, response);

		setReturnActionFordward(request,
				mappings.findForward("ver_clasificador"));
	}

	public void retrieveDocumentoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		retrieveCodeLogic(mappings, form, request, response);

		setReturnActionFordward(request,
				mappings.findForward("ver_clasificador"));
	}

	/**
	 * Muestra la información del clasificador.
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
	public void retrieveCodeLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de retrieveExecuteLogic");

		DocumentosTreeView treeView = (DocumentosTreeView) getFromTemporalSession(
				request, DocumentosConstants.DOCUMENT_TREE_KEY);
		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_HOME, request);

		// Interfaz del servicio
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionDocumentosElectronicosBI documentosBI = services
				.lookupGestionDocumentosElectronicosBI();

		// Leer el identificador del clasificador
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id clasificador: " + id);

		// Leer el identificador del objeto
		String idObjeto = request.getParameter("idObjeto");
		if (StringUtils.isBlank(idObjeto))
			idObjeto = (String) getFromTemporalSession(request,
					DocumentosConstants.OBJECT_ID_KEY);
		if (logger.isInfoEnabled())
			logger.info("Id Objeto: " + idObjeto);

		// Leer el tipo de objeto
		int tipoObjeto = TypeConverter.toInt(
				request.getParameter("tipoObjeto"), -1);
		if (tipoObjeto < 0) {
			Integer tipoObjetoInteger = (Integer) getFromTemporalSession(
					request, DocumentosConstants.OBJECT_TYPE_KEY);
			tipoObjeto = (tipoObjetoInteger != null ? tipoObjetoInteger
					.intValue() : TipoObjeto.DESCRIPTOR);
		}
		if (logger.isInfoEnabled())
			logger.info("Tipo Objeto: " + tipoObjeto);

		// Actualizar el árbol de documentos
		String refresh = request.getParameter("refresh");
		String verNodo = request.getParameter("verContent");
		boolean remove = new Boolean(request.getParameter("remove"))
				.booleanValue();
		if ("1".equals(refresh)) {
			String pNodePath = request.getParameter("node");
			if ("1".equals(verNodo))
				pNodePath += "/item" + id;
			if (StringUtils.isEmpty(pNodePath) && !remove)
				pNodePath += "item" + id;

			if (treeView != null && !treeView.getRootNodes().isEmpty()) {
				TreeNode node = treeView.getNode(pNodePath);
				if (node != null && node.getParent() != null)
					node.getParent().setCollapsed(false, false);

				treeView.setSelectedNode(node);
			}
			request.setAttribute(DocumentosConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);
		}

		if (StringUtils.isNotBlank(id)) {
			// Leer la información del clasificador
			DocClasificadorVO clasificador = documentosBI.getClasificador(
					tipoObjeto, idObjeto, id);
			if (logger.isInfoEnabled())
				logger.info("DocClasificadorVO: " + clasificador);

			// Guardar la información del clasificador
			((ClasificadorForm) form).set(clasificador);
		} else {
			((ClasificadorForm) form).setIdObjeto(idObjeto);
			((ClasificadorForm) form).setTipoObjeto(tipoObjeto);
			((ClasificadorForm) form).setRoot(true);
			id = null;
			if (treeView != null)
				treeView.setSelectedNode(null);
		}

		// Obtener los objetos de la carpeta
		List objetos = new ArrayList();
		DocTCapturaVO tarea = (DocTCapturaVO) getFromTemporalSession(request,
				DocumentosConstants.TAREA_KEY);
		List clasificadoresVisibles = null;
		List documentosVisibles = null;
		if (tarea != null) {
			clasificadoresVisibles = documentosBI
					.getClasificadoresVisiblesDesdeTarea(tipoObjeto, idObjeto,
							id);
			documentosVisibles = documentosBI.getDocumentosVisiblesDesdeTarea(
					tipoObjeto, idObjeto, id);

		} else {
			clasificadoresVisibles = documentosBI
					.getClasificadoresVisiblesDesdeCuadro(tipoObjeto, idObjeto,
							id);
			documentosVisibles = documentosBI.getDocumentosVisiblesDesdeCuadro(
					tipoObjeto, idObjeto, id);
		}

		CollectionUtils.transform(clasificadoresVisibles,
				new ClasificadoresPOTranformer(getServiceRepository(request),
						getServiceClient(request), tarea));
		objetos.addAll(clasificadoresVisibles);

		CollectionUtils.transform(documentosVisibles,
				new DocumentosPOTransformer(getServiceRepository(request),
						getServiceClient(request), tarea));
		objetos.addAll(documentosVisibles);

		request.setAttribute(DocumentosConstants.FOLDER_CONTENT_KEY, objetos);

		// Información del objeto
		if (tipoObjeto == TipoObjeto.DESCRIPTOR) {
			request.setAttribute(DocumentosConstants.DESCRIPTOR_KEY,
					getGestionDescripcionBI(request).getDescriptorExt(idObjeto));
		} else // if (tipoObjeto == TipoObjeto.ELEMENTO_CF)
		{
			ElementoCuadroClasificacionVO elemento = getGestionCuadroClasificacionBI(
					request).getElementoCuadroClasificacion(idObjeto);
			if (tarea != null)
				((DocTCapturaPO) tarea).setUser(getAppUser(request));
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
		}

	}

	/**
	 * Muestra el formulario del clasificador.
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

		// Leer el identificador del padre
		String idClfPadre = request.getParameter("idClfPadre");
		if (logger.isInfoEnabled())
			logger.info("Id Clasificador Padre: " + idClfPadre);

		// Servicio de gestión de documentos electrónicos
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionDocumentosElectronicosBI documentosBI = services
				.lookupGestionDocumentosElectronicosBI();

		if (StringUtils.isNotBlank(id)) {
			// Guardar el enlace a la página
			// saveCurrentInvocation(KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_CLASIFICADOR_EDIT,
			// request, getCustomConfigureView(request));
			saveCurrentInvocation(
					KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_CLASIFICADOR_EDIT,
					request);

			// Leer la información del clasificador
			DocClasificadorVO clasificador = documentosBI.getClasificador(
					tipoObjeto, idObjeto, id);
			if (logger.isInfoEnabled())
				logger.info("DocClasificadorVO: " + clasificador);

			// Guardar la información del formulario
			((ClasificadorForm) form).set(clasificador);
			((ClasificadorForm) form).setTienePadreFijo(documentosBI.esFijo(
					idClfPadre, tipoObjeto, idObjeto));
			((ClasificadorForm) form).setTieneHijoFijo(documentosBI
					.tieneHijoFijo(id, tipoObjeto));
		} else {
			// Guardar el enlace a la página
			// saveCurrentInvocation(KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_CLASIFICADOR_FORM,
			// request, getCustomConfigureView(request));
			saveCurrentInvocation(
					KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_CLASIFICADOR_FORM,
					request);

			((ClasificadorForm) form).setIdObjeto(idObjeto);
			((ClasificadorForm) form).setTipoObjeto(tipoObjeto);
			((ClasificadorForm) form).setTienePadreFijo(documentosBI.esFijo(
					idClfPadre, tipoObjeto, idObjeto));
			((ClasificadorForm) form).setTieneHijoFijo(false);
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
		}

		setReturnActionFordward(request,
				mapping.findForward("editar_clasificador"));
	}

	/**
	 * Guarda el clasificador.
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
		String path = "";
		TreeNode node = null;
		logger.info("Inicio de saveExecuteLogic");

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			DocumentosTreeView treeView = (DocumentosTreeView) getFromTemporalSession(
					request, DocumentosConstants.DOCUMENT_TREE_KEY);

			// Recoger la información del clasificador
			DocClasificadorVO clasificador = new DocClasificadorVO();
			((ClasificadorForm) form).populate(clasificador);
			if (logger.isDebugEnabled())
				logger.debug("DocClasificadorVO:\n" + clasificador.toString());

			DocTCapturaVO tarea = (DocTCapturaVO) getFromTemporalSession(
					request, DocumentosConstants.TAREA_KEY);
			if (StringUtils.isBlank(clasificador.getId())) {
				if (tarea != null) {
					// Crear el clasificador
					((ClasificadorForm) form)
							.set(getGestionDocumentosElectronicosBI(request)
									.insertClasificadorDesdeTarea(clasificador));

				} else {
					// Crear el clasificador
					((ClasificadorForm) form)
							.set(getGestionDocumentosElectronicosBI(request)
									.insertClasificadorDesdeCuadro(clasificador));

				}

				if (treeView != null) {
					TreeNode parentNode = treeView.getSelectedNode();

					if (parentNode != null) {
						clasificador
								.setParent((DocumentosTreeModelItemVO) parentNode
										.getModelItem());
						parentNode.setLeaf(false);
					}
					node = treeView.insertNode(parentNode, clasificador);
					path += node.getNodePath();
				}
				// Eliminar la invocación anterior
				popLastInvocation(request);

			} else {
				if (tarea != null) {
					// Modificar el clasificador
					getGestionDocumentosElectronicosBI(request)
							.updateClasificadorDesdeTarea(clasificador);
				} else {
					getGestionDocumentosElectronicosBI(request)
							.updateClasificadorDesdeCuadro(clasificador);
				}

				if (treeView != null) {
					node = treeView.getSelectedNode();
					if (node != null)
						path = node.getNodePath();
					TreeNode parentNode = treeView.getSelectedNode()
							.getParent();
					if (parentNode != null)
						clasificador
								.setParent((DocumentosTreeModelItemVO) parentNode
										.getModelItem());
					TreeNode nodeNuevo = treeView.getNode(node.getNodePath());
					nodeNuevo.setTreeModelItem(clasificador);
					// Obtener el servicio
					GestionDocumentosElectronicosBI documentosBI = getGestionDocumentosElectronicosBI(request);
					// Comprobar si tiene descendientes
					if (documentosBI.tieneDescendientes(
							clasificador.getTipoObjeto(),
							clasificador.getIdObjeto(), clasificador.getId()))
						nodeNuevo.setLeaf(false);
				}
			}
			updateRelacionRechazada(request);
			setReturnActionFordward(
					request,
					redirectForwardMethod(
							request,
							"/clasificador",
							"method",
							"retrieve"
									+ (clasificador.getId() != null ? "&id="
											+ clasificador.getId() : "")
									+ (clasificador.getIdObjeto() != null ? "&idObjeto="
											+ clasificador.getIdObjeto()
											: "") + "&tipoObjeto="
									+ clasificador.getTipoObjeto() + "&node="
									+ path + "&refresh=1"));
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mapping.findForward("editar_clasificador"));
		}
	}

	/**
	 * Elimina el clasificador.
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
		String path = "";
		logger.info("Inicio de removeExecuteLogic");

		// Leer el identificador del clasificador
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id clasificador: " + id);

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

		// Obtener la información del clasificador
		DocClasificadorVO clasificador = documentosBI.getClasificador(
				tipoObjeto, idObjeto, id);

		DocumentosTreeView treeView = (DocumentosTreeView) getFromTemporalSession(
				request, DocumentosConstants.DOCUMENT_TREE_KEY);
		try {
			// Comprobamos si el clasificador tiene descendientes, si es así, no
			// se puede borrar
			if (documentosBI.tieneDescendientes(tipoObjeto, idObjeto, id))
				throw new DocElectronicosException(
						DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_CLASIFICADOR_XTIENE_DESCENDIENTES);

			// Eliminar el clasificador
			documentosBI.removeClasificador(clasificador);

			TreeNode parentNode = treeView.getSelectedNode().getParent();
			if (parentNode != null)
				path = parentNode.getNodePath();
			TreeNode node = treeView.getSelectedNode();
			treeView.removeNode(node);

			// Volver al clasificador padre
			setReturnActionFordward(
					request,
					redirectForwardMethod(
							request,
							"/clasificador",
							"method",
							"retrieve"
									+ (clasificador.getIdClfPadre() != null ? "&id="
											+ clasificador.getIdClfPadre()
											: "")
									+ (clasificador.getIdObjeto() != null ? "&idObjeto="
											+ clasificador.getIdObjeto()
											: "") + "&tipoObjeto=" + tipoObjeto
									+ "&node=" + path + "&remove="
									+ Boolean.TRUE + "&refresh=1"));
		} catch (DocElectronicosException e) {
			guardarError(request, e);

			TreeNode node = treeView.getSelectedNode();
			// Volver al clasificador padre
			setReturnActionFordward(
					request,
					redirectForwardMethod(
							request,
							"/clasificador",
							"method",
							"retrieve"
									+ (id != null ? "&id=" + id : "")
									+ (idObjeto != null ? "&idObjeto="
											+ idObjeto : "") + "&tipoObjeto="
									+ tipoObjeto + "&node="
									+ node.getNodePath() + "&refresh=1"));
		}

	}

	/**
	 * Elimina el contenido seleccionado del clasificador.
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
	protected void removeContenidoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = "";
		logger.info("Inicio de removeContenidoExecuteLogic");

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

		DocumentosTreeView treeView = (DocumentosTreeView) getFromTemporalSession(
				request, DocumentosConstants.DOCUMENT_TREE_KEY);
		TreeNode parentNode = treeView.getSelectedNode();
		try {
			DataClfDocYRepEcm dataClfDocYRepEcm = null;
			String[] listaIdClasificadores = request
					.getParameterValues("eliminarClasificador");
			boolean eliminarClasificador = listaIdClasificadores != null
					&& listaIdClasificadores.length > 0;
			String[] listaIdDocumentos = request
					.getParameterValues("eliminarDocumento");
			GestionDocumentosElectronicosBI docElecBI = getGestionDocumentosElectronicosBI(request);

			int motivoRepEcmExcepcion = 0;
			if (tipoObjeto == TipoObjeto.DESCRIPTOR) {
				motivoRepEcmExcepcion = DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_DOCUMENTO_XFALTA_REPOSITORIO_ECM_DESCRIPTOR;
				dataClfDocYRepEcm = docElecBI
						.getIdFichaClfDocYRepEcmDescriptor(idObjeto);
			} else {
				motivoRepEcmExcepcion = DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_DOCUMENTO_XFALTA_REPOSITORIO_ECM_ELEMENTO_CUADRO;
				dataClfDocYRepEcm = docElecBI
						.getIdFichaClfDocYRepEcmElementoCF(idObjeto);
			}

			// if (tipoObjeto == TipoObjeto.DESCRIPTOR) {
			if (eliminarClasificador) { // Si se han seleccionado clasificadores
										// a eliminar
				int i = 0;
				while (i < listaIdClasificadores.length) {
					// Comprobamos si el clasificador tiene descendientes, si es
					// así, no se puede borrar
					if (docElecBI.tieneDescendientes(tipoObjeto, idObjeto,
							listaIdClasificadores[i]))
						throw new DocElectronicosException(
								DocElectronicosException.XNO_SE_PUEDE_ELIMINAR_CLASIFICADOR_XTIENE_DESCENDIENTES);
					TreeNode node = null;
					if (parentNode != null)
						node = treeView.getNode(parentNode.getNodePath()
								+ "/item" + listaIdClasificadores[i]);
					else
						node = treeView.getNode("item"
								+ listaIdClasificadores[i]);
					treeView.removeNode(node);
					i++;
				}
			} else { // Si no se han seleccionado clasificadores a eliminar,
						// sólo tenemos que hacer comprobación de los documentos

				// Primero comprobamos si tienen depósito electrónico, y en caso
				// de que no
				/*
				 * Si tienen Repositorio ECM - Permitir eliminarlos Sino -
				 * Mensaje de error
				 */

				int i = 0;
				while (i < listaIdDocumentos.length) {
					DocDocumentoVO documentoVO = docElecBI.getDocumento(
							tipoObjeto, idObjeto, listaIdDocumentos[i]);
					if (documentoVO != null
							&& StringUtils.isEmpty(documentoVO
									.getIdExtDeposito())) {
						if (StringUtils
								.isEmpty(dataClfDocYRepEcm.getIdRepEcm()))
							throw new DocElectronicosException(
									motivoRepEcmExcepcion);
					}
					i++;
				}
			}

			// Eliminar el contenido seleccionado del clasificador
			getGestionDocumentosElectronicosBI(request)
					.removeContenidoClasificador(tipoObjeto, idObjeto,
							request.getParameterValues("eliminarClasificador"),
							request.getParameterValues("eliminarDocumento"));

			updateRelacionRechazada(request);

			if (parentNode != null)
				path = parentNode.getNodePath();

			// Volver al clasificador padre
			setReturnActionFordward(
					request,
					redirectForwardMethod(request, "/clasificador", "method",
							"retrieve"
									+ (id != null ? "&id=" + id : "")
									+ (idObjeto != null ? "&idObjeto="
											+ idObjeto : "") + "&tipoObjeto="
									+ tipoObjeto + "&node=" + path
									+ "&refresh=1"));

		} catch (DocElectronicosException e) {
			guardarError(request, e);

			// Volver al clasificador padre
			setReturnActionFordward(
					request,
					redirectForwardMethod(request, "/clasificador", "method",
							"retrieve"
									+ (id != null ? "&id=" + id : "")
									+ (idObjeto != null ? "&idObjeto="
											+ idObjeto : "") + "&tipoObjeto="
									+ tipoObjeto + "&node=" + path
									+ "&refresh=1"));

			// goBackExecuteLogic(mapping, form, request, response);
		}
	}

	public void validarElementoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();
		try {
			boolean haySeleccion = false;
			DocTCapturaPO tarea = (DocTCapturaPO) getFromTemporalSession(
					request, DocumentosConstants.TAREA_KEY);
			String[] idElementoAValidar = getClasificadoresATratar(request);
			if (idElementoAValidar != null && idElementoAValidar.length > 0) {
				docElectronicosService.validarClasificador(tarea,
						idElementoAValidar);
				haySeleccion = true;
			}
			idElementoAValidar = getDocumentosATratar(request);
			if (idElementoAValidar != null && idElementoAValidar.length > 0) {
				docElectronicosService.validarDocumento(tarea,
						idElementoAValidar);
				haySeleccion = true;
			}

			if (!haySeleccion) {
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_ERROR,
								new ActionError(
										DocumentosConstants.LABEL_DOCUMENTOS_SEL_ELEMENTO));
			}

		} catch (DocElectronicosException e) {
			guardarError(request, e);
		}

		goLastClientExecuteLogic(mapping, form, request, response);
	}

	private String[] getClasificadoresATratar(HttpServletRequest request) {
		return (String[]) request.getParameterValues("eliminarClasificador");
	}

	private String[] getDocumentosATratar(HttpServletRequest request) {
		return (String[]) request.getParameterValues("eliminarDocumento");
	}

	public void invalidarElementoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionDocumentosElectronicosBI docElectronicosService = getServiceRepository(
				request).lookupGestionDocumentosElectronicosBI();
		try {
			boolean haySeleccion = false;
			DocTCapturaPO tarea = (DocTCapturaPO) getFromTemporalSession(
					request, DocumentosConstants.TAREA_KEY);
			String[] idElementoAValidar = getClasificadoresATratar(request);
			if (idElementoAValidar != null && idElementoAValidar.length > 0) {
				docElectronicosService.invalidarClasificador(tarea,
						idElementoAValidar);
				haySeleccion = true;
			}
			idElementoAValidar = getDocumentosATratar(request);
			if (idElementoAValidar != null && idElementoAValidar.length > 0) {
				docElectronicosService.invalidarDocumento(tarea,
						idElementoAValidar);
				haySeleccion = true;
			}

			if (!haySeleccion) {
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_ERROR,
								new ActionError(
										DocumentosConstants.LABEL_DOCUMENTOS_SEL_ELEMENTO));
			}

		} catch (DocElectronicosException e) {
			guardarError(request, e);
		}

		goLastClientExecuteLogic(mapping, form, request, response);
	}

	private void updateRelacionRechazada(HttpServletRequest request) {
		RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		if (relacion != null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionBI = services
					.lookupGestionRelacionesBI();
			if (relacion.isRechazada()) {
				relacion.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
				try {
					relacionBI.updateRelacion(relacion);
				} catch (ActionNotAllowedException e) {
					guardarError(request, e);
				}
			}
		}
	}

}