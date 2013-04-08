package fondos.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.ErrorsTag;
import util.TreeModelItem;
import util.TreeNode;
import util.TreeView;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.TruncatedList;
import common.view.ErrorKeys;

import fondos.FondosConstants;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.forms.SolicitudSerieForm;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IdentificacionSerie;
import fondos.model.Serie;
import fondos.model.SolicitudSerie;
import fondos.model.TipoNivelCF;
import fondos.view.SeriePO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;
import fondos.vos.NivelFichaUDocRepEcmVO;
import fondos.vos.OpcionesDescripcionSerieVO;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import fondos.vos.SolicitudSerieVO;
import gcontrol.model.TipoListaControlAcceso;
import gcontrol.view.TransformerUsuariosVOToUsuariosPO;

/**
 * Controller de las acciones implicadas en la gestiï¿½n de series documentales
 */
public class GestionSerieAction extends BaseAction {

	public void verfichaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request, mappings.findForward("ver_ficha"));
	}

	public void editarfichaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request, mappings.findForward("editar_ficha"));
	}

	public void nuevaSolicitudAltaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SolicitudSerieForm solicititudAltaForm = (SolicitudSerieForm) form;
		String idNivelPadre = request.getParameter("idNivelPadre");
		solicititudAltaForm.setIdPadre(request.getParameter("idPadre"));
		GestionCuadroClasificacionBI cuadroBI = getGestionCuadroClasificacionBI(request);

		List nivelesTipoSerie = cuadroBI.getNivelesByTipo(TipoNivelCF.SERIE,
				idNivelPadre);
		request.setAttribute(FondosConstants.LISTA_NIVELES_TIPO_SERIE_KEY,
				nivelesTipoSerie);

		// TODO Si no hay gestores mostrar mensaje de aviso al usuarios indicano
		// que no se puede solicitar el alta
		/* GestionControlUsuariosBI gestionUsuariosService = */getGestionControlUsuarios(request);
		/* GestionSeriesBI biSeries = */getGestionSeriesBI(request);

		preparePageToAltaSolicitud(request);

		// poner usuario gestor por defecto el de serviClient
		// solictitudAltaForm.setIdGestor(getServiceClient(request).getId());

		List nivelesTipoUdoc = cuadroBI.getNivelesByTipo(
				TipoNivelCF.UNIDAD_DOCUMENTAL, idNivelPadre);
		setDefaultValuesInSolicitud(solicititudAltaForm, nivelesTipoSerie,
				nivelesTipoUdoc);

		ElementoCuadroClasificacionVO elementoPadre = cuadroBI
				.getElementoCuadroClasificacion(solicititudAltaForm
						.getIdPadre());

		List jerarquia = new ArrayList();
		if (elementoPadre != null)
			jerarquia = cuadroBI.getAncestors(solicititudAltaForm.getIdPadre());
		jerarquia.add(elementoPadre);

		setInTemporalSession(request,
				FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
				jerarquia);

		List todosNivelesTipoUdoc = cuadroBI.getNivelesByTipo(
				TipoNivelCF.UNIDAD_DOCUMENTAL, null);
		setInTemporalSession(request,
				FondosConstants.LISTA_NIVELES_DOCUMENTALES_KEY,
				todosNivelesTipoUdoc);


		cargarListaRepositoriosECM(request);

		setReturnActionFordward(request,
				mappings.findForward("nueva_solicitud_alta"));

		ClientInvocation cli = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_SOLICITUD_ALTA_SERIE, request);
		cli.setAsReturnPoint(true);

	}

	public void preparePageToAltaSolicitud(HttpServletRequest request) {
		ServiceRepository services = getServiceRepository(request);
		/* GestionCuadroClasificacionBI biCuadro = */services
				.lookupGestionCuadroClasificacionBI();

		// TODO Si no hay gestores mostrar mensaje de aviso al usuarios indicano
		// que no se puede solicitar el alta
		GestionControlUsuariosBI gestionUsuariosService = getGestionControlUsuarios(request);
		/* GestionSeriesBI biSeries = */getGestionSeriesBI(request);

		List gestoresDeSeries = gestionUsuariosService.getGestoresSerie();
		CollectionUtils.transform(gestoresDeSeries,
				new TransformerUsuariosVOToUsuariosPO(services));
		request.setAttribute(FondosConstants.LISTA_GESTORES_SERIE_KEY,
				gestoresDeSeries);

		// lista de volumnes para asignar a la serie y udocs de la serie(todos
		// los de la tabla)
		GestionDocumentosElectronicosBI documentosService = getGestionDocumentosElectronicosBI(request);
		List repositoriosEcm = documentosService.getRepositoriosEcm();

		if (ListUtils.isEmpty(repositoriosEcm)) {
			repositoriosEcm = new ArrayList();
			logger.info("No se ha definido ningún repositorio ECM para almacenamiento");
		}

		cargarListaRepositoriosECM(request);

		// lista de fichas clasificadores de documentos para asignar a la serie
		// y udocs de la serie
		List fichasCLDoc = documentosService.getFichas();
		request.setAttribute(FondosConstants.LISTA_FICHAS_CL_DOCUMENTALES_KEY,
				fichasCLDoc);

		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

		// fichas descriptivas para serie
		List fichasParaSerie = descripcionService
				.getFichasByTiposNivel(new int[] {
						TipoNivelCF.SERIE.getIdentificador(),
						ElementoCuadroClasificacion.TIPO_ALL });
		request.setAttribute(FondosConstants.LISTA_FICHAS_PARA_SERIES_KEY,
				fichasParaSerie);

		// fichas descriptivas para udocs de la serie
		List fichasParaUdocs = descripcionService
				.getFichasByTiposNivel(new int[] {
						TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador(),
						ElementoCuadroClasificacion.TIPO_ALL });
		request.setAttribute(FondosConstants.LISTA_FICHAS_PARA_UDOCS_KEY,
				fichasParaUdocs);

		GestionCuadroClasificacionBI cuadroBI = getGestionCuadroClasificacionBI(request);
		// niveles documentales
		List nivelesDocumentales = cuadroBI.getNivelesByTipo(
				TipoNivelCF.UNIDAD_DOCUMENTAL, null);
		request.setAttribute(FondosConstants.LISTA_NIVELES_DOCUMENTALES_KEY,
				nivelesDocumentales);
	}

	void setDefaultValuesInSolicitud(SolicitudSerieForm solictitudAltaForm,
			List nivelesTipoSerie, List nivelesTipoUdoc) {
		if (!util.CollectionUtils.isEmptyCollection(nivelesTipoSerie)) {
			if (nivelesTipoSerie.size() == 1) {
				INivelCFVO nivelSerie = (INivelCFVO) nivelesTipoSerie
						.iterator().next();
				solictitudAltaForm.setIdFichaDescriptivaSerie(nivelSerie
						.getIdFichaDescrPref());
				solictitudAltaForm.setIdRepEcmSerie(nivelSerie
						.getIdRepEcmPref());
			}
		}

		// setDefaultsUdocsValues(solictitudAltaForm, nivelesTipoUdoc);
	}

	// TODO CAMBIAR AL SISTEMA DEL GESTIONADOR DE NOT ALLOWED EXCEPTION
	public ActionErrors validateFormInProcesarSolicitud(
			HttpServletRequest request, SolicitudSerieForm solicitudAltaForm) {

		ActionErrors errors = validateFormInfoNiveles(request);

		if (errors == null)
			errors = new ActionErrors();

		if (GenericValidator.isBlankOrNull(solicitudAltaForm.getCodigo()))
			errors.add(Constants.CODIGO_ES_NECESARIO, new ActionError(
					Constants.CODIGO_ES_NECESARIO));

		String delimitadorCodigoReferencia = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos()
				.getDelimitadorCodigoReferencia();

		if (Constants.hasForbidenChars(solicitudAltaForm.getCodigo(),
				delimitadorCodigoReferencia)) {
			errors.add(Constants.ERROR_INVALID_CHARACTERS, new ActionError(
					Constants.ERROR_INVALID_CHARACTERS, "Codigo",
					delimitadorCodigoReferencia));
		}

		if (GenericValidator.isBlankOrNull(solicitudAltaForm.getDenominacion()))
			errors.add(Constants.DENOMINACION_ES_NECESARIO, new ActionError(
					Constants.DENOMINACION_ES_NECESARIO));

		if (ConfigConstants.getInstance().getMostrarCampoOrdenacionCuadro()) {
			if (GenericValidator.isBlankOrNull(solicitudAltaForm
					.getCodOrdenacion())) {
				errors = new ActionErrors();
				errors.add(
						Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(
										Constants.ETIQUETA_CODIGO_ORDENACION,
										request.getLocale())));
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	public List obtenerNivelesFichaUDocRepEcm(HttpServletRequest request) {
		List nivelesFichaUDocRepEcm = new ArrayList();

		String[] nivelesDocumentales = request.getParameterValues("campo_9");
		String[] fichasDescriptivas = request.getParameterValues("campo_10");
		String[] fichasClasifDocsAsoc = request.getParameterValues("campo_11");
		String[] repositoriosEcm = request.getParameterValues("campo_12");

		if (nivelesDocumentales != null && nivelesDocumentales.length > 0) {
			for (int i = 0; i < nivelesDocumentales.length; i++) {
				NivelFichaUDocRepEcmVO nivelFichaUDocRepEcmVO = new NivelFichaUDocRepEcmVO();
				String nivel = nivelesDocumentales[i];
				if (StringUtils.isNotEmpty(nivel)) {
					nivelFichaUDocRepEcmVO.setIdNivel(nivel);
					if (fichasDescriptivas != null
							&& fichasDescriptivas.length > 0) {
						if (StringUtils.isNotEmpty(fichasDescriptivas[i])){
							nivelFichaUDocRepEcmVO
									.setIdFichaDescrPrefUdoc(fichasDescriptivas[i]);
						}
						if (StringUtils.isNotEmpty(fichasClasifDocsAsoc[i])){
							nivelFichaUDocRepEcmVO
									.setIdFichaClfDocPrefUdoc(fichasClasifDocsAsoc[i]);
						}

						if (repositoriosEcm != null && StringUtils.isNotEmpty(repositoriosEcm[i])){
							nivelFichaUDocRepEcmVO
									.setIdRepEcmPrefUdoc(repositoriosEcm[i]);
						}

						String checkRepEcm = request
								.getParameter("campo_12_check_" + (i + 1));
						if (StringUtils.isNotEmpty(checkRepEcm))
							nivelFichaUDocRepEcmVO.setUpdateUDocs("true");
					}
					nivelesFichaUDocRepEcm.add(nivelFichaUDocRepEcmVO);
				}
			}
		}

		return nivelesFichaUDocRepEcm;
	}

	public void procesarSolicitudAltaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		SolicitudSerieForm solicitudAltaForm = (SolicitudSerieForm) form;

		SerieVO infoAltaSerie = new Serie();

		List listaNivelesUDocRepEcm = obtenerNivelesFichaUDocRepEcm(request);

		infoAltaSerie.setNivelesFichaUDocRepEcm(listaNivelesUDocRepEcm);
		SeriePO seriePO = new SeriePO(infoAltaSerie, services);
		if (seriePO != null
				&& seriePO.getOpcionesDescripcion() != null
				&& seriePO.getOpcionesDescripcion()
						.getInfoFichaUDocRepEcmUDocsSerie() != null)
			setInTemporalSession(request,
					FondosConstants.NIVELES_FICHA_UDOC_REP_ECM, seriePO
							.getOpcionesDescripcion()
							.getInfoFichaUDocRepEcmUDocsSerie());
		else
			removeInTemporalSession(request,
					FondosConstants.NIVELES_FICHA_UDOC_REP_ECM);

		ActionErrors errors = validateFormInProcesarSolicitud(request,
				(SolicitudSerieForm) form);

		if (errors == null) {

			if (GenericValidator.isBlankOrNull(solicitudAltaForm.getCodigo())) {
				errors = new ActionErrors();
				errors.add(Constants.CODIGO_ES_NECESARIO, new ActionError(
						Constants.CODIGO_ES_NECESARIO));
			}
			if (GenericValidator.isBlankOrNull(solicitudAltaForm
					.getDenominacion())) {
				errors = new ActionErrors();
				errors.add(Constants.TITULO_ES_NECESARIO, new ActionError(
						Constants.TITULO_ES_NECESARIO));
			}

			GestionCuadroClasificacionBI cuadroBI = getGestionCuadroClasificacionBI(request);

			// comprobar que el codigo introducido para la serie a crear no
			// existe en alguna serie del fondo
			CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
					request, FondosConstants.CUADRO_CLF_VIEW_NAME);
			TreeModelItem elementoPadre = treeView.getSelectedNode()
					.getModelItem();

			List seriesCodigoRepetido = cuadroBI
					.getElementosCFXNivelYFondoYCodigoYTitulo(cuadroBI
							.getNivelesSerie(),
							((ElementoCuadroClasificacion) elementoPadre)
									.getIdFondo(), solicitudAltaForm
									.getCodigo(), null, false);
			if (seriesCodigoRepetido != null && seriesCodigoRepetido.size() > 0) {
				if (errors == null)
					errors = obtenerErrores(request, true);
				errors.add(Constants.ERROR_SERIE_CODIGO_REPETIDO_ENFONDO,
						new ActionError(
								Constants.ERROR_SERIE_CODIGO_REPETIDO_ENFONDO));
			}

			if (obtenerErrores(request, false) != null) {
				util.ErrorsTag.saveErrors(request, errors);
				nuevaSolicitudAltaExecuteLogic(mappings, form, request,
						response);
				return;
			}

			infoAltaSerie.setCodigo(solicitudAltaForm.getCodigo());
			infoAltaSerie.setDenominacion(solicitudAltaForm.getDenominacion());
			infoAltaSerie.setIdPadre(solicitudAltaForm.getIdPadre());
			infoAltaSerie.setIdNivel(solicitudAltaForm.getIdNivelSerie());
			infoAltaSerie
					.setObservaciones(solicitudAltaForm.getObservaciones());
			infoAltaSerie.setOrdPos(solicitudAltaForm.getCodOrdenacion());
			if (StringUtils.isNotEmpty(solicitudAltaForm
					.getIdFichaDescriptivaSerie()))
				infoAltaSerie.setIdFichaDescr(solicitudAltaForm
						.getIdFichaDescriptivaSerie());
			if (StringUtils.isNotEmpty(solicitudAltaForm.getIdRepEcmSerie()))
				infoAltaSerie.setIdRepEcm(solicitudAltaForm.getIdRepEcmSerie());

			infoAltaSerie.setNivelesFichaUDocRepEcm(listaNivelesUDocRepEcm);
			try {
				GestionSeriesBI bi = getGestionSeriesBI(request);
				if (solicitudAltaForm.isAutorizacionAutomatica()) {
					infoAltaSerie.setIdusrgestor(solicitudAltaForm
							.getIdGestor());

					SerieVO serie = bi.nuevaSerie(
							(ElementoCuadroClasificacionVO) elementoPadre,
							infoAltaSerie);

					TreeNode newNode = treeView.itemAdded(elementoPadre,
							(TreeModelItem) serie);
					treeView.setSelectedNode(newNode);

					ActionRedirect vistaSerie = new ActionRedirect(
							mappings.findForward("redirect_to_vista_serie"));
					vistaSerie.setRedirect(true);
					vistaSerie
							.addParameter("refreshView", String.valueOf(true));
					vistaSerie.addParameter("node", newNode.getNodePath(),
							false);
					setReturnActionFordward(request, vistaSerie);
				} else {
					bi.solicitarAltaDeSerie(
							(ElementoCuadroClasificacionVO) elementoPadre,
							infoAltaSerie, solicitudAltaForm.getMotivo(),
							getAppUser(request).getId());

					ActionRedirect forward = new ActionRedirect(
							mappings.findForwardConfig("ver_clasificador_padre"));
					forward.addParameter("idelementocf",
							elementoPadre.getItemId());
					forward.setRedirect(true);
					setReturnActionFordward(request, forward);
				}
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				nuevaSolicitudAltaExecuteLogic(mappings, form, request,
						response);
			}
		} else {
			util.ErrorsTag.saveErrors(request, errors);
			nuevaSolicitudAltaExecuteLogic(mappings, form, request, response);
			return;
		}

	}

	public void editarInfoSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		String idSerie = request.getParameter("idSerie");

		if (!checkPermisosSobreElemento(request, idSerie,
				FondosConstants.PERMISOS_EDICION_ELEMENTO_CUADRO)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		if (idSerie != null) {
			SerieVO serie = serieBI.getSerie(idSerie);
			SeriePO seriePO = new SeriePO(serie, services);

			SolicitudSerieForm dataForm = (SolicitudSerieForm) form;
			dataForm.setIdFichaDescriptivaSerie(serie.getIdFichaDescr());
			dataForm.setIdRepEcmSerie(serie.getIdRepEcm());
			dataForm.setIdLCA(serie.getIdLCA());
			dataForm.setNivelAcceso(serie.getNivelAcceso());
			dataForm.setCodOrdenacion(serie.getOrdPos());

			// lista de volumnes para asignar a la serie y udocs de la
			// serie(todos los de la tabla)
			GestionDocumentosElectronicosBI documentosService = services
					.lookupGestionDocumentosElectronicosBI();

			// lista de fichas clasificadores de documentos para asignar a la
			// serie y udocs de la serie
			List fichasCLDoc = documentosService.getFichas();

			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			// fichas descriptivas para serie
			List fichasParaSerie = descripcionService
					.getFichasByTiposNivel(new int[] {
							TipoNivelCF.SERIE.getIdentificador(),
							ElementoCuadroClasificacion.TIPO_ALL });

			// fichas descriptivas para udocs de la serie
			List fichasParaUdocs = descripcionService
					.getFichasByTiposNivel(new int[] {
							TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador(),
							ElementoCuadroClasificacion.TIPO_ALL });

			// niveles documentales de tipo udoc
			List nivelesDocumentales = cuadroBI.getNivelesByTipo(
					TipoNivelCF.UNIDAD_DOCUMENTAL, null);

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_EDICION_SERIE, request);
			invocation.setAsReturnPoint(true);

			cargarListaRepositoriosECM(request);
			setInTemporalSession(request,
					FondosConstants.LISTA_FICHAS_CL_DOCUMENTALES_KEY,
					fichasCLDoc);
			setInTemporalSession(request,
					FondosConstants.LISTA_FICHAS_PARA_SERIES_KEY,
					fichasParaSerie);
			setInTemporalSession(request,
					FondosConstants.LISTA_FICHAS_PARA_UDOCS_KEY,
					fichasParaUdocs);
			setInTemporalSession(request,
					FondosConstants.LISTA_NIVELES_DOCUMENTALES_KEY,
					nivelesDocumentales);
			setInTemporalSession(request, FondosConstants.INFO_UDOCS_SERIE,
					seriePO.getOpcionesDescripcion()
							.getInfoFichaUDocRepEcmUDocsSerie());
			request.setAttribute(
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY,
					getGestionControlUsuarios(request)
							.getListasControlAccesoByTipo(
									TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION));

			setReturnActionFordward(request,
					mappings.findForward("edicion_info_serie"));
		} else
			goLastClientExecuteLogic(mappings, form, request, response);
	}

	public ActionErrors validateFormInfoNiveles(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		ArrayList idsNiveles = new ArrayList();

		String[] nivelesDocumentales = request.getParameterValues("campo_9");
		String[] fichasDescriptivas = request.getParameterValues("campo_10");
		String[] fichasClasifDocsAsoc = request.getParameterValues("campo_11");
		String[] repositoriosEcm = request.getParameterValues("campo_12");

		if (nivelesDocumentales != null && nivelesDocumentales.length > 0) {
			for (int i = 0; i < nivelesDocumentales.length; i++) {
				String nivel = nivelesDocumentales[i];
				if (StringUtils.isNotEmpty(nivel)) {
					if (idsNiveles.contains(nivel)) {
						errors.add(
								Constants.NIVEL_FICHAS_UDOC_REP_ECM_NO_PUEDE_REPETIR_NIVEL,
								new ActionError(
										Constants.NIVEL_FICHAS_UDOC_REP_ECM_NO_PUEDE_REPETIR_NIVEL));
					}
					if (fichasDescriptivas != null
							&& fichasDescriptivas.length > 0) {
						if (StringUtils.isEmpty(fichasDescriptivas[i])
								&& StringUtils.isEmpty(fichasClasifDocsAsoc[i])
							    && StringUtils.isEmpty(repositoriosEcm[i])) {
							errors.add(
									Constants.NIVEL_FICHAS_UDOC_REP_ECM_ALGUN_DATO_NECESARIO,
									new ActionError(
											Constants.NIVEL_FICHAS_UDOC_REP_ECM_ALGUN_DATO_NECESARIO));
						}
					}
					idsNiveles.add(nivel);
				} else {
					errors.add(
							Constants.NIVEL_FICHAS_UDOC_REP_ECM_NIVEL_NECESARIO,
							new ActionError(
									Constants.NIVEL_FICHAS_UDOC_REP_ECM_NIVEL_NECESARIO));
				}
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	public void guardarInfoSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		SolicitudSerieForm dataForm = (SolicitudSerieForm) form;

		List nivelesFichaUDocRepEcm = obtenerNivelesFichaUDocRepEcm(request);
		String fichaDescripcionSerie = dataForm.getIdFichaDescriptivaSerie();
		String idRepositorioEcmSerie = dataForm.getIdRepEcmSerie();
		int nivelAcceso = dataForm.getNivelAcceso();
		String idLCA = dataForm.getIdLCA();

		ActionErrors errors = validateFormInfoNiveles(request);

		if (ConfigConstants.getInstance().getMostrarCampoOrdenacionCuadro()) {
			if (StringUtils.isEmpty(dataForm.getCodOrdenacion())) {
				if (errors == null)
					errors = new ActionErrors();
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(
										Constants.ETIQUETA_CODIGO_ORDENACION,
										request.getLocale())));
			}
		}

		if (errors == null) {

			try {
				serieBI.setInfoSerie(dataForm.getIdSerie(),
						fichaDescripcionSerie, idRepositorioEcmSerie,
						nivelesFichaUDocRepEcm, idLCA, nivelAcceso,
						dataForm.getCodOrdenacion());
				goBackExecuteLogic(mappings, form, request, response);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("edicion_info_serie"));
			}
		} else {
			// Aqui almacenamos las fichas por nivel independientemente en caso
			// de error en introduccion de datos
			SerieVO serie = serieBI.getSerie(dataForm.getIdSerie());
			serie.setNivelesFichaUDocRepEcm(nivelesFichaUDocRepEcm);
			SeriePO seriePO = new SeriePO(serie, services);
			OpcionesDescripcionSerieVO opcsDescVO = seriePO
					.getOpcionesDescripcion();
			if (opcsDescVO != null
					&& opcsDescVO.getInfoFichaUDocRepEcmUDocsSerie() != null) {
				removeInTemporalSession(request,
						FondosConstants.INFO_UDOCS_SERIE);
				setInTemporalSession(request,
						FondosConstants.NIVELES_FICHA_UDOC_REP_ECM,
						opcsDescVO.getInfoFichaUDocRepEcmUDocsSerie());
			}
			util.ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

	}

	public void eliminarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		String idSerie = request.getParameter("idSerie");

		// comprobar que no tiene unidades documentales.
		if (serieBI.getCountUnidadesDocumentales(idSerie) > 0) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					FondosConstants.ERROR_FONDOS_SERIES_ELIMINAR_CON_UDOCS));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}
		try {
			serieBI.eliminarSerie(idSerie);

			// borrado del nodo del arbol
			CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
					request, FondosConstants.CUADRO_CLF_VIEW_NAME);
			if (treeView != null) {
				TreeNode nodoSeleccionado = null;
				nodoSeleccionado = treeView.getSelectedNode();
				treeView.setSelectedNode(nodoSeleccionado.getParent());
				treeView.removeNode(nodoSeleccionado);

				ActionRedirect result = new ActionRedirect(
						mappings.findForward("refreshView"), true);
				result.addParameter("refreshView", "true");
				nodoSeleccionado = treeView.getSelectedNode();
				if (nodoSeleccionado != null)
					result.addParameter("node", nodoSeleccionado.getNodePath(),
							false);

				setReturnActionFordward(request, result);
			} else
				goBackExecuteLogic(mappings, form, request, response);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void solicitarAutorizacionCambiosExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String idSerie = request.getParameter("idSerie");
		SerieVO serie = (SerieVO) getFromTemporalSession(request,
				FondosConstants.ELEMENTO_CF_KEY);
		GestionSeriesBI seriesBI = getGestionSeriesBI(request);

		IdentificacionSerie identificacionSerie = seriesBI
				.getIdentificacionSerie(serie);
		setInTemporalSession(request, FondosConstants.IDENTIFICACION_SERIE_KEY,
				identificacionSerie);

		SolicitudSerieForm solicitudAltaForm = (SolicitudSerieForm) form;
		solicitudAltaForm.setIdSerie(idSerie);
		setReturnActionFordward(request,
				mappings.findForward("nueva_solicitud_aprobacionCambios"));

		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_SOLICITUD_APROBACION_CAMBIOS,
				request);

	}

	public void procesarSolicitudAutorizacionCambiosExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SolicitudSerieForm solicitudAltaForm = (SolicitudSerieForm) form;
		GestionSeriesBI bi = getGestionSeriesBI(request);
		try {
			if (solicitudAltaForm.isAutorizacionAutomatica()) {
				bi.autorizarCambiosEnIdentificacion(solicitudAltaForm
						.getIdSerie());
			} else {
				AppUser appUser = getAppUser(request);
				bi.solicitarAutorizacionCambios(solicitudAltaForm.getIdSerie(),
						solicitudAltaForm.getMotivo(), appUser.getId());
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}

		goBackExecuteLogic(mappings, form, request, response);

	}

	/**
	 * Muestra las solicitudes pendientes de aprobaciï¿½n
	 */
	public void listaSolicitudesAprobacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_LISTA_SOLICITUDES_APROBACION,
				request);
		ServiceRepository services = getServiceRepository(request);
		List solicitudesAGestionar = services.lookupGestionSeriesBI()
				.getSolicitudesAGestionar();
		CollectionUtils.transform(solicitudesAGestionar,
				SolicitudSerieToPO.getInstance(services));
		request.setAttribute(FondosConstants.LISTA_SOLICITUDES_KEY,
				solicitudesAGestionar);
		setReturnActionFordward(request,
				mappings.findForward("lista_solicitudes"));
	}

	public void misSolicitudesAprobacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_MIS_SOLICITUDES_APROBACION,
				request);
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionSeriesBI bi = services.lookupGestionSeriesBI();
		List solicitudesAlta = bi.getSolicitudesGestor(appUser.getId());
		CollectionUtils.transform(solicitudesAlta,
				SolicitudSerieToPO.getInstance(services));
		request.setAttribute(FondosConstants.LISTA_SOLICITUDES_KEY,
				solicitudesAlta);
		setReturnActionFordward(request,
				mappings.findForward("lista_solicitudes"));
	}

	public void verSolicitudExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI bi = services.lookupGestionSeriesBI();
		SolicitudSerieVO solicitudAlta = bi.abrirSolicitud(request
				.getParameter("idSolicitud"));
		if (solicitudAlta != null) {
			viewSolicitud(request, services, solicitudAlta);
		}
		saveCurrentInvocation(KeysClientsInvocations.CUADRO_VER_SOLICITUD,
				request);

		setReturnActionFordward(request, mappings.findForward("view_solicitud"));
	}

	public void verSolicitudBySerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI bi = services.lookupGestionSeriesBI();
		String idSerie = request.getParameter("idSerie");
		if (idSerie != null) {
			SolicitudSerieVO solicitud = bi.getSolicitudBySerie(idSerie);
			if (solicitud != null) {
				SolicitudSerieVO solicitudAlta = bi.abrirSolicitud(solicitud
						.getId());
				viewSolicitud(request, services, solicitudAlta);
			}
		}
		saveCurrentInvocation(KeysClientsInvocations.CUADRO_VER_SERIE, request);
		setReturnActionFordward(request, mappings.findForward("view_solicitud"));
	}

	private void viewSolicitud(HttpServletRequest request,
			ServiceRepository services, SolicitudSerieVO solicitudAlta) {
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		GestionSeriesBI bi = services.lookupGestionSeriesBI();
		SolicitudSeriePO solicitudAltaPO = (SolicitudSeriePO) SolicitudSerieToPO
				.getInstance(services).transform(solicitudAlta);
		setInTemporalSession(request, FondosConstants.DETALLE_SOLICITUD_KEY,
				solicitudAltaPO);
		SerieVO serie = bi.getSerie(solicitudAlta.getIdSerie());

		if (!solicitudAltaPO.isSolicitada() && serie != null) {
			SeriePO seriePO = new SeriePO(serie, services);
			setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
					seriePO);
			setInTemporalSession(request,
					FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
					cuadroBI.getAncestors(solicitudAlta.getIdSerie()));
		}
		//else {
//			ActionErrors errors = new ActionErrors();
//			errors.add(
//					Constants.ERROR_GENERAL_MESSAGE,
//					new ActionError(Constants.ERROR_RECORD_NOT_FOUND, Messages
//							.getString(Constants.ETIQUETA_SERIE,
//									request.getLocale())));
//
//			ErrorsTag.saveErrors(request, errors);
//
//		}

	}

	public void autorizarSolicitudExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		/* GestionControlUsuariosBI usuariosService = */services
				.lookupGestionControlUsuariosBI();
		SolicitudSerieVO solicitud = (SolicitudSerieVO) getFromTemporalSession(
				request, FondosConstants.DETALLE_SOLICITUD_KEY);
		GestionSeriesBI seriesService = getGestionSeriesBI(request);
		// ActionErrors errors = null;
		if (solicitud.getTipo() == SolicitudSerie.SOLICITUD_ALTA) {

			SolicitudSerieForm solictitudAltaForm = (SolicitudSerieForm) form;

			preparePageToAltaSolicitud(request);
			GestionCuadroClasificacionBI gestionCuadro = services
					.lookupGestionCuadroClasificacionBI();

			// obtener el nivel de serie
			ElementoCuadroClasificacionVO elementoSerie = gestionCuadro
					.getElementoCuadroClasificacion(solicitud.getIdSerie());

			cargarListaRepositoriosECM(request);

			if (elementoSerie != null) {
				INivelCFVO nivelSerie = gestionCuadro.getNivelCF(elementoSerie
						.getIdNivel());
				solictitudAltaForm.setIdFichaDescriptivaSerie(nivelSerie
						.getIdFichaDescrPref());
				solictitudAltaForm.setIdRepEcmSerie(nivelSerie
						.getIdRepEcmPref());

				// obtener niveles de Udoc
				// List nivelesTipoUdoc =
				// gestionCuadro.getNivelesByTipo(TipoNivelCF.UNIDAD_DOCUMENTAL,
				// nivelSerie.getId());
				// setDefaultsUdocsValues(solictitudAltaForm, nivelesTipoUdoc);
				saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_ACEPTACION_SOLICITUD,
						request);

				solictitudAltaForm.setIdGestor(null);
				setReturnActionFordward(request,
						mappings.findForward("aceptacion_rechazo"));
			} else {
				getErrors(request, true).add(
						Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(Constants.ERROR_RECORD_NOT_FOUND,
								Messages.getString(Constants.ETIQUETA_SERIE,
										request.getLocale())));
				goLastClientExecuteLogic(mappings, solictitudAltaForm, request,
						response);
			}
		} else {

			try {
				AppUser appUser = getAppUser(request);
				seriesService.autorizarSolicitud(solicitud, appUser.getId(),
						null);
				/*
				 * SolicitudSeriePO solicitudAltaPO = (SolicitudSeriePO)
				 */SolicitudSerieToPO.getInstance(services)
						.transform(solicitud);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
			}
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void rechazarSolicitudExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.CUADRO_RECHAZO_SOLICITUD,
				request);
		setReturnActionFordward(request,
				mappings.findForward("aceptacion_rechazo"));
	}

	public void ejecutarSolicitudDenegadaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SolicitudSerieVO solicitud = (SolicitudSerieVO) getFromTemporalSession(
				request, FondosConstants.DETALLE_SOLICITUD_KEY);
		GestionSeriesBI bi = getGestionSeriesBI(request);
		AppUser userVO = getAppUser(request);
		bi.rechazarSolicitud(solicitud, request.getParameter("motivoRechazo"),
				userVO.getId());

		// setReturnActionFordward(request,
		// mappings.findForward("view_solicitud"));
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void ejecutarSolicitudAltaAprobadaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		SolicitudSerieVO solicitudAlta = (SolicitudSerieVO) getFromTemporalSession(
				request, FondosConstants.DETALLE_SOLICITUD_KEY);
		SolicitudSerieForm solicitudForm = (SolicitudSerieForm) form;

		List listaNivelesUDocRepEcm = obtenerNivelesFichaUDocRepEcm(request);

		Serie serie = new Serie();
		serie.setIdFichaDescr(solicitudForm.getIdFichaDescriptivaSerie());
		serie.setIdRepEcm(solicitudForm.getIdRepEcmSerie());
		serie.setNivelesFichaUDocRepEcm(listaNivelesUDocRepEcm);

		SeriePO seriePO = new SeriePO(serie, services);

		ActionErrors errors = validateFormInfoNiveles(request);

		if (errors == null) {

			AppUser userVO = getAppUser(request);

			Map infoAltaSerie = new HashMap();

			infoAltaSerie.put(Serie.GESTOR_BEAN_PROPERTY,
					request.getParameter("idGestor"));
			infoAltaSerie.put(
					ElementoCuadroClasificacion.IDFICHADESCR_BEAN_PROPERTY,
					solicitudForm.getIdFichaDescriptivaSerie());
			infoAltaSerie.put(
					ElementoCuadroClasificacion.REPOSITORIO_ECM_BEAN_PROPERTY,
					solicitudForm.getIdRepEcmSerie());
			infoAltaSerie.put(Serie.INFO_FICHA_UDOC_REP_ECM_BEAN_PROPERTY,
					serie.getInfoFichaUDocRepEcm());

			try {
				serieBI.autorizarSolicitud(solicitudAlta, userVO.getId(),
						infoAltaSerie);
				request.setAttribute(FondosConstants.DETALLE_SOLICITUD_KEY,
						solicitudAlta);
				TreeView treeView = (TreeView) getFromTemporalSession(request,
						FondosConstants.CUADRO_CLF_VIEW_NAME);
				if (treeView != null) {
					GestionCuadroClasificacionBI cuadroClasificacionBI = services
							.lookupGestionCuadroClasificacionBI();
					ElementoCuadroClasificacionVO clasificadorSerie = cuadroClasificacionBI
							.getElementoPadre(solicitudAlta.getIdSerie());
					TreeNode node = treeView.getNode(treeView.getTreeModel()
							.calculateItemPath(clasificadorSerie));
					node.refresh();
				}
				goBackExecuteLogic(mappings, form, request, response);

			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			if (seriePO != null
					&& seriePO.getOpcionesDescripcion() != null
					&& seriePO.getOpcionesDescripcion()
							.getInfoFichaUDocRepEcmUDocsSerie() != null)
				request.setAttribute(
						FondosConstants.NIVELES_FICHA_UDOC_REP_ECM, seriePO
								.getOpcionesDescripcion()
								.getInfoFichaUDocRepEcmUDocsSerie());
			util.ErrorsTag.saveErrors(request, errors);

			autorizarSolicitudExecuteLogic(mappings, form, request, response);

			return;
		}
	}

	public void eliminarSolicitudesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		SolicitudSerieForm solicitudForm = (SolicitudSerieForm) form;
		try {
			serieBI.eliminarSolicitudes(solicitudForm
					.getSolicitudSeleccionada());
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void eliminarSolicitudExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		try {
			String idSolicitud = request.getParameter("idSolicitud");
			serieBI.eliminarSolicitudes(new String[] { idSolicitud });
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

	}

	public void verEnFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter(Constants.ID);

		ActionRedirect ret = new ActionRedirect(
				mappings.findForward("ver_en_fondos"));
		ret.addParameter("itemID", id);
		setReturnActionFordward(request, ret);

	}

	public void verDesdeFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
		String id = request.getParameter(Constants.ID);
		ElementoCuadroClasificacionVO elementoCF = null;
		if (id != null) {
			elementoCF = bi.getElementoCuadroClasificacion(id);
			List ancestros = bi.getAncestors(elementoCF.getId());
			ElementoCuadroClasificacionVO padre = null;
			if (!util.CollectionUtils.isEmpty(ancestros)) {
				padre = (ElementoCuadroClasificacionVO) ancestros.get(0);
				ElementoCuadroClasificacionVO hijo;
				for (int i = 1; i < ancestros.size(); i++) {
					hijo = (ElementoCuadroClasificacionVO) ancestros.get(i);
					hijo.setParentElement(padre);
					padre = hijo;
				}
			}
			elementoCF.setParentElement(padre);
			TreeView treeView = (TreeView) getFromTemporalSession(request,
					FondosConstants.CUADRO_CLF_VIEW_NAME);
			if (treeView != null) {
				TreeNode nodeToShow = treeView
						.findNode((TreeModelItem) elementoCF);
				if (nodeToShow != null) {
					treeView.setSelectedNode(nodeToShow);
					nodeToShow.setVisible();
				}
			}
		}
		request.setAttribute(FondosConstants.REFRESH_VIEW_KEY, Boolean.TRUE);

		ActionRedirect ret = new ActionRedirect(
				mappings.findForward("ver_en_fondos"));
		((ActionRedirect) ret).addParameter("itemID", id);
		((ActionRedirect) ret).addParameter("refreshView", "true");

		setReturnActionFordward(request, ret);

	}

	public void pantallaSolicitudPasoAVigenteExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SolicitudSerieForm solicitudForm = (SolicitudSerieForm) form;
		AppUser appUser = getAppUser(request);
		if (appUser.hasPermission(AppPermissions.GESTION_SOLICITUDES_SERIE))
			solicitudForm.setAutorizacionAutomatica(true);
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_PASO_SERIE_A_VIGENTE, request);
		setReturnActionFordward(request,
				mappings.findForward("paso_serie_a_vigente"));

	}

	public void solicitarCambioVigenciaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SolicitudSerieForm solicitudForm = (SolicitudSerieForm) form;
		try {
			GestionSeriesBI bi = getGestionSeriesBI(request);

			if (solicitudForm.isAutorizacionAutomatica())
				bi.serieVigente(solicitudForm.getIdSerie());
			else
				bi.solicitarPasoAVigente(request.getParameter("idSerie"),
						solicitudForm.getMotivo(), getAppUser(request).getId());
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("paso_serie_a_vigente"));
		}

	}

	public void pantallaSolicitudPasoAHistoricaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SolicitudSerieForm solicitudForm = (SolicitudSerieForm) form;
		AppUser appUser = getAppUser(request);
		if (appUser.hasPermission(AppPermissions.GESTION_SOLICITUDES_SERIE))
			solicitudForm.setAutorizacionAutomatica(true);
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_PASO_SERIE_A_HISTORICA, request);
		setReturnActionFordward(request,
				mappings.findForward("paso_serie_a_historica"));
	}

	public void solicitarPasoAHistoricaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SolicitudSerieForm solicitudForm = (SolicitudSerieForm) form;
		GestionSeriesBI bi = getGestionSeriesBI(request);
		try {
			if (solicitudForm.isAutorizacionAutomatica())
				bi.serieHistorica(solicitudForm.getIdSerie());
			else
				bi.solicitarPasoAHistorica(solicitudForm.getIdSerie(),
						solicitudForm.getMotivo(), getAppUser(request).getId());
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("paso_serie_a_historica"));
		}
	}

	public void serieEnEstudioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI usuariosService = services
				.lookupGestionControlUsuariosBI();
		GestionSeriesBI seriesService = services.lookupGestionSeriesBI();
		GestionCuadroClasificacionBI cuadroService = services
				.lookupGestionCuadroClasificacionBI();
		String pIDSerie = request.getParameter("idSerie");
		SolicitudSerieForm solicitudForm = (SolicitudSerieForm) form;
		SerieVO serie = null;
		if (request.getParameter("idGestor") == null) {
			List gestoresDeSeries = usuariosService.getGestoresSerie();
			CollectionUtils.transform(gestoresDeSeries,
					new TransformerUsuariosVOToUsuariosPO(services));

			serie = seriesService.getSerie(pIDSerie);
			SeriePO seriePO = new SeriePO(serie, services);

			// por defecto seleccionar el gestor actual de la serie
			solicitudForm.setIdGestor(serie.getIdusrgestor());
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_PASO_SERIE_A_EN_ESTUDIO,
					request);
			invocation.setAsReturnPoint(true);
			setInTemporalSession(request, FondosConstants.SERIE_KEY, seriePO);
			setInTemporalSession(request,
					FondosConstants.LISTA_GESTORES_SERIE_KEY, gestoresDeSeries);
			setInTemporalSession(request,
					FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
					cuadroService.getAncestors(serie.getId()));

			setReturnActionFordward(request,
					mappings.findForward("paso_serie_a_estudio"));

		} else {
			try {
				String gestorSerie = null;
				if (solicitudForm.getSeleccionNuevoGestor())
					gestorSerie = solicitudForm.getIdGestor();
				else {
					serie = (SerieVO) getFromTemporalSession(request,
							FondosConstants.SERIE_KEY);
					gestorSerie = serie.getIdusrgestor();
				}
				seriesService.serieEnEstudio(pIDSerie, gestorSerie);
				goBackExecuteLogic(mappings, form, request, response);
			} catch (FondosOperacionNoPermitidaException e) {
				guardarError(request, e);
				setReturnActionFordward(request,
						mappings.findForward("paso_serie_a_estudio"));
			}
		}
	}

	public void verSerieSolicitudExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI seriesService = services.lookupGestionSeriesBI();
		SolicitudSerieVO solicitud = seriesService.getDetalleSolicitud(request
				.getParameter("idSolicitud"));
		SerieVO serieVO = seriesService.getSerie(solicitud.getIdSerie());
		SeriePO seriePO = new SeriePO(serieVO, services);
		setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY, seriePO);
		setInTemporalSession(request, FondosConstants.SERIE_KEY, seriePO);
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_ELEMENTO_DEL_CUADRO, request)
				.setTitleNavigationToolBar(TitlesToolBar.CUADRO_VER_SERIE);

		setReturnActionFordward(request, mappings.findForward("ver_serie"));
	}

	public void homeIdentificacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		List seriesEnIdentificacion = new TruncatedList(
				serieBI.getSeriesEnIdentificacion(appUser.getId()), 5);
		request.setAttribute(FondosConstants.SERIES_EN_IDENTIFICACION,
				seriesEnIdentificacion);

		SolicitudSerieToPO transformerToPO = SolicitudSerieToPO
				.getInstance(services);

		List solicitudesPendientes = new TruncatedList(
				serieBI.getSolicitudesGestor(appUser.getId()), 5);
		CollectionUtils.transform(solicitudesPendientes, transformerToPO);
		request.setAttribute(
				FondosConstants.LISTA_SOLICITUDES_PENDIENTES_APROBACION_KEY,
				solicitudesPendientes);

		List solicitudesAGestionar = new TruncatedList(
				serieBI.getSolicitudesAGestionar(), 5);
		CollectionUtils.transform(solicitudesAGestionar, transformerToPO);
		request.setAttribute(FondosConstants.LISTA_SOLICITUDES_A_GESTIONAR_KEY,
				solicitudesAGestionar);

		saveCurrentInvocation(KeysClientsInvocations.SERIES_EN_IDENTIFICACION,
				request);
		setReturnActionFordward(request,
				mappings.findForward("home_gestion_series"));
	}

	public void verSeriesEnIdentificacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		List seriesEnIdentificacion = serieBI.getSeriesEnIdentificacion(appUser
				.getId());
		request.setAttribute(FondosConstants.SERIES_EN_IDENTIFICACION,
				seriesEnIdentificacion);
		saveCurrentInvocation(KeysClientsInvocations.SERIES_A_IDENTIFICAR,
				request);
		setReturnActionFordward(request,
				mappings.findForward("series_en_identificacion"));
	}

	private void moveSerieCodeLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String idSerie) {

		SolicitudSerieForm solicitudSerieForm = (SolicitudSerieForm) form;

		ServiceRepository services = getServiceRepository(request);
		GestionCuadroClasificacionBI cfBI = services
				.lookupGestionCuadroClasificacionBI();

		ElementoCuadroClasificacionVO elementoCuadroClasificacionVO = cfBI
				.getElementoCuadroClasificacion(idSerie);
		solicitudSerieForm.setFondoSerie(elementoCuadroClasificacionVO
				.getIdFondo());

		// Establecemos los fondos vigentes
		setInTemporalSession(request, FondosConstants.LISTA_FONDOS_KEY,
				getGestionFondosBI(request).getFondosVigentes());

		removeInTemporalSession(request, FondosConstants.LISTA_ELEMENTOS_CF);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_clasificador_series"));
	}

	public void moveSerieExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String idSerie = request.getParameter("idSerie");

		moveSerieCodeLogic(mappings, form, request, response, idSerie);

	}

	/**
	 * Accion para mover el elemento desde el resultado de una bï¿½squeda.
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void accionMoveSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (getServiceClient(request).hasPermission(
				AppPermissions.MODIFICACION_CUADRO_CLASIFICACION)) {
			String[] ids = (String[]) getFromTemporalSession(request,
					FondosConstants.ACCION_ELEMENTOS_KEY);
			ServiceRepository services = getServiceRepository(request);
			GestionSeriesBI bi = services.lookupGestionSeriesBI();
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();
			SerieVO serie = bi.getSerie(ids[0]);
			SeriePO seriePO = new SeriePO(serie, services);
			setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
					seriePO);
			setInTemporalSession(request,
					FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
					cuadroBI.getAncestors(ids[0]));

			moveSerieCodeLogic(mappings, form, request, response, ids[0]);

		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_SIN_PERMISOS));
			ErrorsTag.saveErrors(request, errors);
			setInTemporalSession(request, "usarCache", Boolean.TRUE);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

	}

	public void seleccionarNuevoPadreExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		removeInTemporalSession(request,
				FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		SolicitudSerieForm solicitudSerieForm = (SolicitudSerieForm) form;

		ServiceRepository services = getServiceRepository(request);
		/* GestionFondosBI fondosBI = */services.lookupGestionFondosBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		final SeriePO seriePO = (SeriePO) getFromTemporalSession(request,
				FondosConstants.ELEMENTO_CF_KEY);

		INivelCFVO nivelSerie = cuadroBI.getNivelCF(seriePO.getIdNivel());

		GestionCuadroClasificacionBI cfBI = services
				.lookupGestionCuadroClasificacionBI();
		List posiblesNivelesPadre = cfBI.getNivelesPadre(nivelSerie.getId(),
				nivelSerie.getTipo());
		CollectionUtils.transform(posiblesNivelesPadre, new Transformer() {
			public Object transform(Object arg0) {
				return ((INivelCFVO) arg0).getId();
			}
		});

		String[] nivelesID = (String[]) posiblesNivelesPadre
				.toArray(new String[0]);
		// List clasificadoresSerie =
		// cfBI.getElementosCuadroClasificacionXNivel(nivelesID, null);
		List clasificadoresSerie = cfBI
				.getElementosCFXNivelYFondoYCodigoYTitulo(nivelesID,
						solicitudSerieForm.getFondoSerie(),
						solicitudSerieForm.getCodigo(),
						solicitudSerieForm.getTituloSerie());

		CollectionUtils.filter(clasificadoresSerie, new Predicate() {
			public boolean evaluate(Object arg0) {
				return !((ElementoCuadroClasificacionVO) arg0).getId().equals(
						seriePO.getIdPadre());
			}
		});

		setInTemporalSession(request, FondosConstants.SERIE_KEY, seriePO);
		setInTemporalSession(request, FondosConstants.LISTA_ELEMENTOS_CF,
				clasificadoresSerie);
		// request.setAttribute(FondosConstants.SERIE_KEY, seriePO);
		// request.setAttribute(FondosConstants.LISTA_ELEMENTOS_CF,
		// clasificadoresSerie);
		saveCurrentInvocation(KeysClientsInvocations.CUADRO_MOVER_SERIE,
				request);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_clasificador_series"));

	}

	private ActionErrors validateMoverSerie(String idSerie, String nuevoPadre) {
		ActionErrors errors = new ActionErrors();
		if (nuevoPadre == null)
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					ErrorKeys.SELECCIONAR_NUEVO_PADRE_PARA_LA_SERIE));

		return errors.size() > 0 ? errors : null;
	}

	public void moverSerieExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Boolean movimientoFinalizado = (Boolean) getFromTemporalSession(
				request, FondosConstants.MOVIMIENTO_FINALIZADO_KEY);

		if (movimientoFinalizado == null) {
			movimientoFinalizado = new Boolean(false);
		}

		if (!movimientoFinalizado.booleanValue()) {

			String pIdSerie = request.getParameter("idSerie");
			String pNuevoPadre = request
					.getParameter("clasificadorSeleccionado");
			GestionSeriesBI seriesBI = getGestionSeriesBI(request);
			ActionErrors errors = null;
			try {
				errors = validateMoverSerie(pIdSerie, pNuevoPadre);
				if (errors == null) {

					SerieVO serie = seriesBI.getSerie(pIdSerie);
					seriesBI.moverSerie(serie, pNuevoPadre);

					GestionCuadroClasificacionBI cfBI = getGestionCuadroClasificacionBI(request);

					SerieVO serieDestino = seriesBI.getSerie(pIdSerie);
					SeriePO serieDestinoPO = new SeriePO(serieDestino,
							getServiceRepository(request));

					setInTemporalSession(request,
							FondosConstants.SERIE_DESTINO, serieDestinoPO);

					CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
							request, FondosConstants.CUADRO_CLF_VIEW_NAME);

					if (treeView != null) {
						TreeModelItem nuevoPadre = (TreeModelItem) cfBI
								.getElementoCuadroClasificacion(pNuevoPadre);

						// Corregido - Alicia
						/*********************************/
						// TreeNode nodeNuevoPadre =
						// treeView.findNode(nuevoPadre);
						TreeNode nodeNuevoPadre = treeView.getNode(treeView
								.getTreeModel().calculateItemPath(nuevoPadre));
						/*********************************/

						TreeNode nodeFondo = treeView.getSelectedNode();
						treeView.removeNode(nodeFondo);
						nodeFondo = treeView.insertNode(nodeNuevoPadre,
								(TreeModelItem) serie);
						treeView.setSelectedNode(nodeFondo);
					}

					request.setAttribute(FondosConstants.REFRESH_VIEW_KEY,
							Boolean.TRUE);
					setReturnActionFordward(request,
							mappings.findForward("informe_mover"));

					request.setAttribute(FondosConstants.REFRESH_VIEW_KEY,
							Boolean.TRUE);
					setReturnActionFordward(request,
							mappings.findForward("informe_mover_serie"));

				} else {
					ErrorsTag.saveErrors(request, errors);
					goLastClientExecuteLogic(mappings, form, request, response);
				}

			} catch (FondosOperacionNoPermitidaException re) {
				guardarError(request, re);
				if (re.getCodError() == FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_PROVOCA_FONDOS_DISTINTOS_EN_MISMA_CAJA) {
					request.setAttribute(Constants.LABEL_MENSAJE_MULTILINEA,
							Constants.MULTILINEA_ERROR_UIs_AFECTADAS);
					request.setAttribute(Constants.LINEAS_MENSAJE_MULTILINEA,
							re.getIdsProblematicos());
					setReturnActionFordward(request,
							mappings.findForward("back_and_error_multilinea"));
					return;
				}
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			setReturnActionFordward(request,
					mappings.findForward("informe_mover_serie"));
		}
	}

	public void actualizarFechaInicialExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		List productoresVigentes = (List) getFromTemporalSession(request,
				FondosConstants.PRODUCTORES_VIGENTES_KEY);
		String position = request.getParameter("position");

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();

		ProductorSerieVO productorSerieVO = (ProductorSerieVO) productoresVigentes
				.get(Integer.parseInt(position) - 1);
		productorSerieVO = serieBI.getProductorVigenteBySerie(
				productorSerieVO.getIdserie(),
				productorSerieVO.getIdProductor());

		String fechaInicial = request.getParameter("valueFechaInicial");
		if (DateUtils.isDate(fechaInicial)) {
			if (DateUtils.getDate(fechaInicial).after(
					DateUtils.getFechaActual())) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						Constants.ERROR_DATE_AFTER_TODAY,
						new ActionError(Constants.ERROR_DATE_AFTER_TODAY,
								Messages.getString(
										"archigest.archivo.fechaInicial",
										request.getLocale())));
				ErrorsTag.saveErrors(request, errors);
			} else {
				productorSerieVO.setFechaInicial(request
						.getParameter("valueFechaInicial"));
				serieBI.updateByIdSerieIdProductor(productorSerieVO);
			}
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							"archigest.archivo.fechaInicial",
							request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void actualizarFechaFinalVigenciaHistoricoExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();

		String tipo = request.getParameter("tipo");
		String position = request.getParameter("position");
		ProductorSerieVO productorSerieVO = null;
		if (Messages.getString(FondosConstants.ESTADO_PRODUCTOR_VIGENTE,
				request.getLocale()).equals(tipo)) {
			List productoresVigente = (List) getFromTemporalSession(request,
					FondosConstants.PRODUCTORES_VIGENTES_KEY);
			productorSerieVO = (ProductorSerieVO) productoresVigente
					.get(Integer.parseInt(position) - 1);
			productorSerieVO = serieBI.getProductorVigenteBySerie(
					productorSerieVO.getIdserie(),
					productorSerieVO.getIdProductor());
		} else {
			List productoresHistoricos = (List) getFromTemporalSession(request,
					FondosConstants.PRODUCTORES_HISTORICOS_KEY);
			productorSerieVO = (ProductorSerieVO) productoresHistoricos
					.get(Integer.parseInt(position) - 1);
			productorSerieVO = serieBI.getProductorHistoricoBySerie(
					productorSerieVO.getIdserie(),
					productorSerieVO.getIdProductor());
		}
		String fecha = request.getParameter("valueFechaInicial");
		if (DateUtils.isDate(fecha)) {
			// Comprobar que la fecha introducida sea igual o anterior a la
			// fecha actual
			if (DateUtils.getDate(fecha).after(DateUtils.getFechaActual())) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						Constants.ERROR_DATE_AFTER_TODAY,
						new ActionError(
								Constants.ERROR_DATE_AFTER_TODAY,
								Messages.getString(
										Constants.ETIQUETA_FECHA_FIN_VIGENCIA_PRODUCTOR,
										request.getLocale())));
				ErrorsTag.saveErrors(request, errors);
			} else if (DateUtils.getDate(fecha).compareTo(
					DateUtils.getFechaSinHora(productorSerieVO
							.getFechaInicial())) == DateUtils.FECHA_MENOR) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_DATE_NO_BEFORE,
								Messages.getString(
										Constants.ETIQUETA_FECHA_FIN_VIGENCIA_PRODUCTOR,
										request.getLocale()),
								Messages.getString(
										Constants.ETIQUETA_FECHA_INICIO,
										request.getLocale())));
				ErrorsTag.saveErrors(request, errors);
			} else {
				productorSerieVO.setFechaFinal(request
						.getParameter("valueFechaInicial"));
				serieBI.updateByIdSerieIdProductor(productorSerieVO);
			}
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							Constants.ETIQUETA_FECHA_FIN_VIGENCIA_PRODUCTOR,
							request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

}
