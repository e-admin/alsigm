package fondos.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.NotAvailableException;
import se.instituciones.exceptions.GestorOrganismosException;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.GestorCatalogoFactory;
import se.procedimientos.IProcedimiento;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.ErrorsTag;
import util.TreeModelItem;
import util.TreeNode;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.actions.ActionRedirect;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionNivelesArchivoBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import fondos.FondosConstants;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.exceptions.ProductorProcedimientoNoIncorporadoException;
import fondos.forms.IdentificacionSerieForm;
import fondos.model.EstadoSerie;
import fondos.model.IdentificacionSerie;
import fondos.utils.ProductoresUtils;
import fondos.view.SeriePO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.IInfoProductorSerie;
import fondos.vos.InfoOrganoProductorSerie;
import fondos.vos.InfoProdVigenteHistorico;
import fondos.vos.InfoProductorSerie;
import fondos.vos.OrganoProductorVO;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import gcontrol.ControlAccesoConstants;
import gcontrol.vos.CAOrganoVO;

/**
 * Controlador mediante el que se invocan las acciones que es posible realizar
 * durante la identifiación de una serie documental
 */
public class GestionIdentificacionAction extends BaseIdentificacionAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GestionIdentificacionAction.class);

	private static final String GUID_PRODUCTOR = "guidProductor";
	private static final String GUID_PRODUCTOR_HISTORICO = "guidProductorHistorico";

	public void inicioidentificarserieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
		frm.clear();
		GestionSeriesBI serviceSeries = getGestionSeriesBI(request);
		SerieVO serie = (SerieVO) serviceSeries.getSerie(frm.getIdserie());
		IdentificacionSerie identificacionSerie;
		try {
			// Comprobar que el usuario puede modificar la serie
			serviceSeries.verificarPermitidoModificarIdentificacion(serie);

			identificacionSerie = serviceSeries.abrirIdentificacionSerie(serie);
			frm.fill(identificacionSerie);
			frm.setGuardado(false);
			if (identificacionSerie.getProcedimiento() != null)
				frm.setGuardado(true);
			// para la pagina de datos de la serie
			request.setAttribute(FondosConstants.SERIE_KEY, serie);
			setInTemporalSession(request,
					FondosConstants.IDENTIFICACION_SERIE_KEY,
					identificacionSerie);

			if (serie.getIdProcedimiento() == null
					&& serie.getEstadoserie() == EstadoSerie.EN_ESTUDIO) {
				establecerGuidsProductores(request,
						identificacionSerie.getListaInfoProductoresSerie());
				establecerFlagSustituyeAHistorico(request);
			}

			ActionRedirect forwardIdentificacionSerie = new ActionRedirect(
					mappings.findForward("redirect_to_identificacion"));
			forwardIdentificacionSerie.setRedirect(true);
			setReturnActionFordward(request, forwardIdentificacionSerie);
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (Exception f) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Messages.getString(
							Constants.ERROR_SERIE_SIN_ENTIDAD_PRODUCTORA,
							request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void edicionIdentificacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		setReturnActionFordward(request,
				mappings.findForward("identificacion_serie"));
	}

	public void veridentificacionserieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IdentificacionSerieForm frm = (IdentificacionSerieForm) form;

		String idSerie = request.getParameter("idelementocf");
		if (idSerie == null)
			idSerie = frm.getIdserie();
		else
			frm.setIdserie(idSerie);

		if (!puedeAccederUsuarioAElemento(request, idSerie)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serviceSeries = services.lookupGestionSeriesBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		SerieVO serie = (SerieVO) serviceSeries.getSerie(idSerie);
		SeriePO seriePO = new SeriePO(serie, services);
		IdentificacionSerie identificacionSerie = null;
		try {
			// Se captura la exception por si se produce algun error. Sobre todo
			// por si no tiene entidad productora.
			identificacionSerie = serviceSeries.abrirIdentificacionSerie(serie);

			if (identificacionSerie.getProcedimiento() != null)
				frm.setGuardado(true);
			setInTemporalSession(request,
					FondosConstants.IDENTIFICACION_SERIE_KEY,
					identificacionSerie);
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Messages.getString(
							Constants.ERROR_SERIE_SIN_ENTIDAD_PRODUCTORA,
							request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}
		List listaProductoresVigentes = serviceSeries
				.getProductoresVigentesBySerie(serie.getId());
		List listaProductoresHistoricos = serviceSeries
				.getProductoresHistoricosBySerie(serie.getId());
		List listaProductoresVigentesOrginales = serviceSeries
				.getProductoresVigentesOriginalesBySerie(serie.getId());

		if (serie.isEnEstudio()) {
			establecerGuidsProductores(request, listaProductoresVigentes);
			establecerGuidsProductores(request, listaProductoresHistoricos);
			establecerGuidsProductores(request,
					listaProductoresVigentesOrginales);
		}

		ClientInvocation invocation = saveCurrentTreeViewInvocationAndRefresh(
				KeysClientsInvocations.CUADRO_ELEMENTO_DEL_CUADRO, request,
				FondosConstants.CUADRO_CLF_VIEW_NAME);
		invocation.setTitleNavigationToolBar(TitlesToolBar.CUADRO_VER_SERIE);
		invocation.setAsReturnPoint(true);

		setInTemporalSession(request, FondosConstants.IDENTIFICACION_SERIE_KEY,
				identificacionSerie);

		setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY, seriePO);
		setInTemporalSession(request, FondosConstants.SERIE_KEY, seriePO);
		setInTemporalSession(request,
				FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
				cuadroBI.getAncestors(serie.getId()));

		setInTemporalSession(request, FondosConstants.PRODUCTORES_VIGENTES_KEY,
				listaProductoresVigentes);
		setInTemporalSession(request,
				FondosConstants.PRODUCTORES_HISTORICOS_KEY,
				listaProductoresHistoricos);
		setInTemporalSession(request,
				FondosConstants.PRODUCTORES_ORIGINALES_VIGENTES_KEY,
				listaProductoresVigentesOrginales);

		setInTemporalSession(request,
				FondosConstants.SUSTITUCION_VIGENTE_HISTORICO_KEY,
				new ArrayList());
		setInTemporalSession(request,
				FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY, new ArrayList());

		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
		List archivos = sistemaBI.getArchivos();
		setInTemporalSession(request, ControlAccesoConstants.LISTA_ARCHIVOS,
				archivos);

		// obtener la lista de niveles de archivo
		GestionNivelesArchivoBI nivelesArchivo = services
				.lookupGestionNivelesArchivoBI();
		List listaNivelesArchivo = nivelesArchivo.getListaNivelesArchivo();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO,
				listaNivelesArchivo);

		setReturnActionFordward(request, mappings.findForward("ver_serie"));
	}

	public void sustituirNuevosProductoresExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();

		IdentificacionSerieForm identificacionSerieForm = (IdentificacionSerieForm) form;
		String[] productoresAntiguos = identificacionSerieForm
				.getProductoresAntiguosPrimerNodo();

		// Se crea una cadena para utilizarla para el mantenimiento del estado
		// de los select en la jsp
		String productoresSeleccionados = null;
		for (int i = 0; i < productoresAntiguos.length; i++) {
			if (productoresSeleccionados == null)
				productoresSeleccionados = productoresAntiguos[i];
			else
				productoresSeleccionados += "$" + productoresAntiguos[i];
		}

		identificacionSerieForm
				.setProductoresSeleccionados(productoresSeleccionados);

		Object object = ListUtils.isAnyElementRepeated(
				Arrays.asList(productoresAntiguos), "");

		if (object != null) {
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.MISMO_PRODUCTOR_ACTUAL, new ActionError(
					Constants.MISMO_PRODUCTOR_ACTUAL));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("refrescar_varios_productores"));
			return;
		}

		else {
			List nuevosProductoresDespues = (List) getFromTemporalSession(
					request, FondosConstants.NUEVOS_PRODUCTORES_DESPUES_KEY);
			CAOrganoVO nuevoProductorCAOrganoVODespues = null;
			for (int i = 0; i < nuevosProductoresDespues.size(); i++) {
				nuevoProductorCAOrganoVODespues = (CAOrganoVO) nuevosProductoresDespues
						.get(i);
				String idSerie = identificacionSerieForm.getIdserie();

				// Se pasan a historico los productores actuales no
				// seleccionados

				List listaProductoresAntiguosVigentesDespuesPrimerNodo = (List) getFromTemporalSession(
						request,
						FondosConstants.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_PRIMER_NODO_KEY);

				serieBI.pasarAHistoricoProductoresVigentesDespues(
						listaProductoresAntiguosVigentesDespuesPrimerNodo,
						productoresAntiguos, nuevoProductorCAOrganoVODespues);

				if (StringUtils.isEmpty(productoresAntiguos[i])) {
					try {
						serieBI.createNuevoProductor(
								nuevoProductorCAOrganoVODespues, idSerie);
					} catch (NotAvailableException ex) {
						obtenerErrores(request, true).add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError(
										Constants.ERROR_GESTOR_ORGANIZACION));
						setReturnActionFordward(request,
								mappings.findForward("ver_serie"));
					} catch (GestorCatalogoException ex) {
						obtenerErrores(request, true)
								.add(ActionErrors.GLOBAL_ERROR,
										new ActionError(
												Constants.ERROR_GESTOR_CATALOGO));
						setReturnActionFordward(request,
								mappings.findForward("ver_serie"));
					}
				} else {
					try {
						serieBI.sustituirProductor(productoresAntiguos[i],
								nuevoProductorCAOrganoVODespues,
								identificacionSerieForm.getIdSerie());
					} catch (NotAvailableException ex) {
						obtenerErrores(request, true).add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError(
										Constants.ERROR_GESTOR_ORGANIZACION));
						setReturnActionFordward(request,
								mappings.findForward("ver_serie"));
					} catch (GestorCatalogoException ex) {
						obtenerErrores(request, true)
								.add(ActionErrors.GLOBAL_ERROR,
										new ActionError(
												Constants.ERROR_GESTOR_CATALOGO));
						setReturnActionFordward(request,
								mappings.findForward("ver_serie"));
					}
				}
			}

		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void refrescarIdentificacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		String idSerie = null;
		IdentificacionSerieForm identificacionSerieForm = (IdentificacionSerieForm) form;
		try {
			idSerie = request.getParameter("idSerie");
			if (StringUtils.isBlank(idSerie))
				idSerie = identificacionSerieForm.getIdSerie();
			else
				identificacionSerieForm.setIdserie(idSerie);
			identificacionSerieForm.setProductoresSeleccionados(null);

			HashMap hashMap = serieBI.actualizarIdentificacionSerie(idSerie);
			if (hashMap != null) {
				setInTemporalSession(
						request,
						FondosConstants.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_KEY,
						hashMap.get(FondosConstants.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_KEY));
				setInTemporalSession(
						request,
						FondosConstants.NUEVOS_PRODUCTORES_DESPUES_KEY,
						hashMap.get(FondosConstants.NUEVOS_PRODUCTORES_DESPUES_KEY));
				setInTemporalSession(
						request,
						FondosConstants.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_PRIMER_NODO_KEY,
						hashMap.get(FondosConstants.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_PRIMER_NODO_KEY));

				String antiguosProductoresVigentesDespues = (String) hashMap
						.get(FondosConstants.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_KEY);
				identificacionSerieForm
						.setProductoresAntiguos(antiguosProductoresVigentesDespues);

				setReturnActionFordward(request,
						mappings.findForward("refrescar_varios_productores"));
				return;
			}

			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (ProductorProcedimientoNoIncorporadoException e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.NO_EXISTEN_ORGANOS));
			List listaProductoresADarDeAlta = (List) getFromTemporalSession(
					request, FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY);
			listaProductoresADarDeAlta = new ArrayList();
			listProductoresADarDeAlta(request, e.getProductores(),
					listaProductoresADarDeAlta, identificacionSerieForm);
			setInTemporalSession(request,
					FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY,
					listaProductoresADarDeAlta);

			setReturnActionFordward(request,
					mappings.findForward("crear_organo_productor_serie"));
			return;
		} catch (GestorCatalogoException e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_CATALOGO));
			setReturnActionFordward(request, mappings.findForward("ver_serie"));
		} catch (GestorOrganismosException e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_ORGANIZACION));
			setReturnActionFordward(request, mappings.findForward("ver_serie"));
		} catch (NotAvailableException e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_ORGANIZACION));
			setReturnActionFordward(request, mappings.findForward("ver_serie"));
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request, mappings.findForward("ver_serie"));
		} catch (ArchivoModelException e) {
			if (e.getMessage().endsWith(
					Messages.getString(Constants.NO_EXISTEN_PRODUCTORES,
							request.getLocale()))) {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.NO_EXISTEN_PRODUCTORES));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
			if (e.getMessage().endsWith(
					Messages.getString(Constants.NO_EXISTE_PROCEDIMIENTO,
							request.getLocale()))) {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.NO_EXISTE_PROCEDIMIENTO));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
			if (e.getMessage().endsWith(
					Messages.getString(
							Constants.NO_EXISTEN_PRODUCTORES_VIGENTES,
							request.getLocale()))) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.NO_EXISTEN_PRODUCTORES_VIGENTES));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
			if (e.getMessage().endsWith(
					Messages.getString(
							Constants.PRODUCTOR_EXISTE_EN_HISTORICOS,
							request.getLocale()))) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.PRODUCTOR_EXISTE_EN_HISTORICOS));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		}
	}

	public void pantallaidentificarserieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_PANTALLA_IDENTIFICACION, request);
		invocation.setAsReturnPoint(true);

		IdentificacionSerieForm frm = (IdentificacionSerieForm) form;

		IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);

		ServiceRepository services = getServiceRepository(request);

		// Obtener la Lista de Tipo de documentación
		List listaProductoresADarDeAlta = (List) getFromTemporalSession(
				request, FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY);
		frm.setNombreProductor(null);
		frm.setDescripcionProductor(null);
		frm.setArchivoPorProductor(null);

		// Información Extendida
		if (ConfigConstants.getInstance()
				.getMostrarInformacionIdentificacionExtendia()) {
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			String idTblValidacion = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionFondos()
					.getTablaValidacionTipoDocumentacion();
			if (StringUtils.isNotBlank(idTblValidacion)) {
				List tiposDocumentacion = descripcionBI
						.getValoresValidacion(idTblValidacion);
				setInTemporalSession(request,
						FondosConstants.LISTA_TIPOS_DOCUMENTACION_KEY,
						tiposDocumentacion);
			} else {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_TABLA_VALIDACION_TIPO_DOCUMENTACION,
						new ActionError(
								FondosConstants.ERROR_TABLA_VALIDACION_TIPO_DOCUMENTACION));
				ErrorsTag.saveErrors(request, errors);
			}

			if (identificacionSerie != null) {
				frm.setTipoDocumentacion(identificacionSerie
						.getTipoDocumentacion());
				frm.setVolumenPrevisionAnual(identificacionSerie
						.getVolumenPrevisionAnual());
				frm.setSoportePrevisionAnual(identificacionSerie
						.getSoportePrevisionAnual());
			}
		}

		listProductoresADarDeAlta(request, null, listaProductoresADarDeAlta,
				frm);

		setReturnActionFordward(request,
				mappings.findForward("identificacion_serie"));
	}

	public void eliminarprocedimientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
					request, FondosConstants.IDENTIFICACION_SERIE_KEY);
			identificacionSerie.desvincularProcedimiento();
			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			frm.setDefinicion(null);
			frm.setTramites(null);
			frm.setNormativa(null);
			frm.setDocumentosbasicos(null);
			frm.setChanged(true);
			frm.setGuardado(false);

			List listaProductoresADarDeAlta = (List) getFromTemporalSession(
					request, FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY);
			listaProductoresADarDeAlta.removeAll(listaProductoresADarDeAlta);

		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

		setReturnActionFordward(request,
				mappings.findForward("identificacion_serie"));
	}

	public void pasarAVigenteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			String guidProductorHistorico = frm.getGuidProductorHistorico();
			if (StringUtils.isEmpty(guidProductorHistorico)) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_HISTORICO_NO_SELECCIONADO,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_HISTORICO_NO_SELECCIONADO));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			SerieVO serie = (SerieVO) getFromTemporalSession(request,
					FondosConstants.ELEMENTO_CF_KEY);

			IInfoProductorSerie infoProductorSerie = getInfoProductorSerie(
					request, guidProductorHistorico);

			if (!serie.isEnEstudio()
					|| !infoProductorSerie.isPermitidoPasarAVigente()) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_HISTORICO_NO_PASAR_VIGENTE,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_HISTORICO_NO_PASAR_VIGENTE));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			pasarAVigente(request, guidProductorHistorico, true);

			frm.setChanged(true);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));

		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

	}

	public void eliminarProductorHistoricoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String guidProductorHistorico = request
					.getParameter("guidProductorHistorico");
			if (StringUtils.isEmpty(guidProductorHistorico)) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_HISTORICO_NO_SELECCIONADO,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_HISTORICO_NO_SELECCIONADO));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			InfoProductorSerie infoProductorSerie = getInfoProductorSerie(
					request, guidProductorHistorico);
			SerieVO serie = (SerieVO) getFromTemporalSession(request,
					FondosConstants.ELEMENTO_CF_KEY);

			if (!serie.isEnEstudio()
					|| !infoProductorSerie.isPermitidoEliminar()) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_HISTORICO_NO_ELIMINABLE,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_HISTORICO_NO_ELIMINABLE));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			GestionSeriesBI seriesService = getGestionSeriesBI(request);
			// Comprobar que el productor no está siendo utilizado en ninguna
			// unidad documental de la serie.
			if (!infoProductorSerie.isNuevo()
					&& seriesService.isProductorConUdocs(serie.getId(),
							infoProductorSerie.getIdDescrSistExt())) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_NO_ELIMINABLE_TIENE_UDOCS,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_NO_ELIMINABLE_TIENE_UDOCS));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			eliminarProductor(request, infoProductorSerie);

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			frm.setChanged(true);

			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
	}

	public void productorSustituidoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String guidProductor = request
					.getParameter(GUID_PRODUCTOR_HISTORICO);

			List listaProductoresHistoricos = (List) getFromTemporalSession(
					request, FondosConstants.PRODUCTORES_HISTORICOS_KEY);
			ProductorSerieVO productorHistoricoSerieVO = (ProductorSerieVO) getObjetoByGuid(
					guidProductor, listaProductoresHistoricos);

			// Se obtiene el productor Sustituidor
			IInfoProductorSerie productorSustituidor = (IInfoProductorSerie) getProductorSustituidor(
					request, productorHistoricoSerieVO);

			setInTemporalSession(request,
					FondosConstants.PRODUCTOR_SUSTITUIDOR_KEY,
					productorSustituidor);
			setInTemporalSession(request,
					FondosConstants.PRODUCTOR_SUSTITUIDO_KEY,
					productorHistoricoSerieVO);
			request.setAttribute(FondosConstants.DESDE_METODO_KEY,
					"productorSustituido");

			setReturnActionFordward(request,
					mappings.findForward("sustituir_productor"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

	}

	public void sustituirProductorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;

			String guidProductor = frm.getGuidProductor();
			if (StringUtils.isEmpty(guidProductor)) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			IInfoProductorSerie infoProductorSustituidor = getInfoProductorSerie(
					request, guidProductor);

			if (infoProductorSustituidor != null
					&& !infoProductorSustituidor.isPermitidoReemplazar()) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_VIGENTE_DEBE_SER_NUEVO,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_VIGENTE_DEBE_SER_NUEVO));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			// Se obtiene el productor sustituido
			ProductorSerieVO productorSustituidoSerieVO = getProductorSustituido(
					request, infoProductorSustituidor);

			Date fechaFin = DateUtils.getFechaActualSinHora();
			if (productorSustituidoSerieVO != null
					&& productorSustituidoSerieVO.getFechaFinal() != null) {
				// Establecer la fecha actual como fin del vigencia.
				fechaFin = productorSustituidoSerieVO.getFechaFinal();
			}

			String fechaActualStr = DateUtils.formatDate(fechaFin);
			frm.setFechaFinVigenciaProductor(fechaActualStr);

			setInTemporalSession(request,
					FondosConstants.PRODUCTOR_SUSTITUIDOR_KEY,
					infoProductorSustituidor);
			setInTemporalSession(request,
					FondosConstants.PRODUCTOR_SUSTITUIDO_KEY,
					productorSustituidoSerieVO);

			restablecerListaProductoresVigentesOriginales(request);

			frm.setGuidProductor(null);
			setReturnActionFordward(request,
					mappings.findForward("sustituir_productor"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

	}

	public void eliminarProductorEnEstudioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String guidProductor = request.getParameter(GUID_PRODUCTOR);
			if (StringUtils.isEmpty(guidProductor)) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			InfoProductorSerie infoProductorSerie = getInfoProductorSerie(
					request, guidProductor);

			// Comprobar que el productor no está siendo utilizado en ninguna
			// unidad documental de la serie.
			SerieVO serie = (SerieVO) getFromTemporalSession(request,
					FondosConstants.ELEMENTO_CF_KEY);
			if (infoProductorSerie != null
					&& StringUtils.isNotEmpty(infoProductorSerie
							.getIdDescriptor())
					&& getGestionSeriesBI(request)
							.isProductorConUdocs(serie.getId(),
									infoProductorSerie.getIdDescriptor())) {

				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_NO_ELIMINABLE_TIENE_UDOCS,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_NO_ELIMINABLE_TIENE_UDOCS));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			// Si el productor está sustityendo a otro, este se pasa a vigente
			List sustitucionVigenteHistorico = (List) getFromTemporalSession(
					request, FondosConstants.SUSTITUCION_VIGENTE_HISTORICO_KEY);

			if (ListUtils.isNotEmpty(sustitucionVigenteHistorico)) {
				InfoProdVigenteHistorico infoProdVigenteHistorico = getInfoProdVigenteHistorico(
						sustitucionVigenteHistorico, infoProductorSerie);
				if (infoProdVigenteHistorico != null) {
					InfoProductorSerie infoProductorSerieSustituido = infoProdVigenteHistorico
							.getInfoProductorSerieSustituido();

					if (infoProductorSerieSustituido != null) {
						pasarAVigente(request,
								infoProductorSerieSustituido.getGuid(), false);
					} else {
						IdentificacionSerie identificacionSerie = getIdentificacionSerie(request);
						List listaProductoresHistoricos = identificacionSerie
								.getProductoresHistoricos();

						// Si sustituía a alguien, éste pasa a vigente
						if (infoProductorSerie.isNuevo()
								&& !ListUtils
										.isEmpty(listaProductoresHistoricos)) {
							ProductorSerieVO productorVigenteSerieVO = getGestionSeriesBI(
									request).getProductorVigenteBySerie(
									serie.getId(),
									infoProductorSerie.getIdDescriptor());

							if (productorVigenteSerieVO != null) {
								String idLCA = productorVigenteSerieVO
										.getIdLCAPref();
								for (int i = 0; i < listaProductoresHistoricos
										.size(); i++) {
									ProductorSerieVO productorHistoricoSerieVO = (ProductorSerieVO) listaProductoresHistoricos
											.get(i);

									if (productorHistoricoSerieVO != null
											&& idLCA.equalsIgnoreCase(productorHistoricoSerieVO
													.getIdLCAPref())
											&& productorHistoricoSerieVO
													.isSustituidoPorVigente()) {
										pasarAVigente(request,
												productorHistoricoSerieVO
														.getGuid(), false);
										break;
									}
								}
							}

						}
					}

				}

			}

			eliminarProductor(request, infoProductorSerie);

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			frm.setChanged(true);

			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

	}

	public void realizarSustitucionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			// Obtener el Productor Sustitudor
			InfoProductorSerie infoProductorSerieSustituidor = (InfoProductorSerie) getFromTemporalSession(
					request, FondosConstants.PRODUCTOR_SUSTITUIDOR_KEY);

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			String guidProductor = frm.getGuidProductor();

			// Comprobar que se ha seleccionado el productor
			if (StringUtils.isEmpty(guidProductor)) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("sustituir_productor"));
				return;
			}

			InfoProdVigenteHistorico infoProdVigenteHistorico = getInfoProdVigenteHistorico(
					getListaSustitucionVigenteHistorico(request),
					infoProductorSerieSustituidor);

			// Si ya está sustityendo a otro.
			if (infoProdVigenteHistorico != null) {
				ProductorSerieVO productorSerieVO = infoProdVigenteHistorico
						.getProductorSerieSustituido();

				pasarAVigente(request, productorSerieVO.getGuid(), false);

				getListaSustitucionVigenteHistorico(request).remove(
						infoProdVigenteHistorico);
			}

			InfoProductorSerie infoProductorSerieSustituido = getInfoProductorSerie(
					request, guidProductor);

			if (infoProductorSerieSustituido != null) {
				GestionSeriesBI seriesService = getGestionSeriesBI(request);

				// Validar las fechas
				String fechaFinVigencia = request
						.getParameter("fechaFinVigenciaProductor");
				ActionErrors errors = new ActionErrors();
				errors = validateFechasVigencia(request, errors,
						infoProductorSerieSustituido.getFechaInicio(),
						fechaFinVigencia);

				if (errors != null && !errors.isEmpty()) {
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mappings.findForward("sustituir_productor"));
					return;
				}

				// Establecer la fecha de fin de vigencia del productor.
				infoProductorSerieSustituido.setFechaFin(fechaFinVigencia);
				infoProductorSerieSustituidor
						.setSustituyeAHistorico(guidProductor);

				int marcas = ProductoresUtils.getMarcaForAction(
						infoProductorSerieSustituido.getMarcas(),
						ProductoresUtils.ACCION_SUSTITUIR_VIGENTE);
				infoProductorSerieSustituido.setMarcas(marcas);

				ProductorSerieVO productorSerieVOSustituido = infoProductorSerieSustituido
						.getProductorSerieVO();

				infoProdVigenteHistorico = new InfoProdVigenteHistorico();
				infoProdVigenteHistorico
						.setSustituidor(infoProductorSerieSustituidor);
				infoProdVigenteHistorico
						.setProductorSerieSustituido(productorSerieVOSustituido);
				infoProdVigenteHistorico
						.setInfoProductorSerieSustituido(infoProductorSerieSustituido);

				List listaPermisos = seriesService
						.getPermisosXIdLista(productorSerieVOSustituido
								.getIdLCAPref());
				infoProdVigenteHistorico
						.setListaPermisosDelSustituido(listaPermisos);

				addSustitucionVigenteHistorico(request,
						infoProdVigenteHistorico);

				pasarAHistorico(request, guidProductor, fechaFinVigencia,
						ProductoresUtils.ACCION_SUSTITUIR_VIGENTE);

			}

			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));

		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

	}

	public void eliminarProductoresExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("inicio eliminarProductoresExecuteLogic");

		try {
			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;

			String[] idsProductoresToRemove = frm.getGuidsProductor();

			logger.debug("idsProductores: " + idsProductoresToRemove);

			if (ArrayUtils.isNotEmpty(idsProductoresToRemove)) {
				for (int i = 0; i < idsProductoresToRemove.length; i++) {
					String guid = idsProductoresToRemove[i];
					logger.debug("eliminar productor con guid:" + guid);
					removeProductor(request, guid);
				}
			}

			frm.setChanged(true);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
		logger.debug("inicio eliminarProductoresExecuteLogic");
	}

	public void vincularProcedimientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);
		IdentificacionSerieForm frm = (IdentificacionSerieForm) form;

		try {
			if (StringUtils.isNotBlank(frm.getProcedimiento())) {
				// Obtener información del usuario conectado
				AppUser appUser = getAppUser(request);

				// Obtener la entidad para el usuario conectado
				Properties params = null;

				if ((appUser != null)
						&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
					params = new Properties();
					params.put(MultiEntityConstants.ENTITY_PARAM,
							appUser.getEntity());
				}

				GestorCatalogo gestorCatalogo = GestorCatalogoFactory
						.getConnector(params);
				IProcedimiento procedimiento = gestorCatalogo
						.recuperarProcedimiento(frm.getProcedimiento());
				if (procedimiento.getOrganosProductores() == null
						|| procedimiento.getOrganosProductores().size() == 0) {
					if (!frm.isWarningSinProductor()
							|| (frm.isWarningSinProductor() && !frm
									.getProcedimiento().equalsIgnoreCase(
											frm.getProcedimientoSeleccionado()))) {
						ActionErrors errors = new ActionErrors();
						errors.add(
								Constants.PROCEDIMIENTO_SIN_PRODUCTORES,
								new ActionError(
										Constants.PROCEDIMIENTO_SIN_PRODUCTORES));
						ErrorsTag.saveErrors(request, errors);
						setReturnActionFordward(request,
								mappings.findForward("listado_procedimientos"));
						frm.setWarningSinProductor(true);
						frm.setProcedimientoSeleccionado(frm.getProcedimiento());
						return;
					} else {
						frm.setWarningSinProductor(false);
						frm.setProcedimientoSeleccionado(null);
					}
				}
				identificacionSerie.vincularAProcedimiento(procedimiento,
						getServiceClient(request));
				frm.fill(identificacionSerie);
				frm.setChanged(true);
				frm.setIdProcedimientoSeleccionado(procedimiento
						.getInformacionBasica().getId());
			}

			List listaProductoresADarDeAlta = (List) getFromTemporalSession(
					request, FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY);
			listaProductoresADarDeAlta.removeAll(listaProductoresADarDeAlta);

			frm.setNombreProductor(null);
			frm.setDescripcionProductor(null);
			frm.setArchivoPorProductor(null);
			frm.setGuardado(false);
			listProductoresADarDeAlta(request,
					identificacionSerie.getListaInfoProductoresSerie(),
					listaProductoresADarDeAlta, frm);

			// popLastInvocation(request);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (GestorCatalogoException e) {
			setReturnActionFordward(request,
					mappings.findForward("listado_procedimientos"));
		} catch (GestorOrganismosException e) {
			ActionErrors errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError(e.getMessage()));
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(request,
					mappings.findForward("listado_procedimientos"));
		} catch (NotAvailableException e) {
			setReturnActionFordward(request,
					mappings.findForward("listado_procedimientos"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

	}

	public void verBuscadorProcedimientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 * ClientInvocation invocation =
		 * saveCurrentInvocation(KeysClientsInvocations
		 * .CUADRO_IDENTIFICACION_LISTA_PROCEDIMIENTOS, request);
		 */
		// invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mappings.findForward("listado_procedimientos"));
	}

	public void buscarProcedimientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			String searchToken = request.getParameter("tituloProcedimiento");
			String pTipoProcedimiento = request
					.getParameter("tipoProcedimiento");
			ServiceRepository services = getServiceRepository(request);
			GestionSeriesBI serieBI = services.lookupGestionSeriesBI();

			frm.setProcedimiento(null);
			List procedimientosVO = serieBI.findProcedimientos(
					Integer.parseInt(pTipoProcedimiento), searchToken);

			frm.setWarningSinProductor(false);
			frm.setProcedimientoSeleccionado(null);
			// procedimientos al listado
			setInTemporalSession(request,
					FondosConstants.LISTA_PROCEDIMIENTOS_KEY, procedimientosVO);
		} catch (GestorCatalogoException gce) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_CATALOGO));
		} catch (NotAvailableException nae) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_CATALOGO));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

		// saveCurrentInvocation(KeysClientsInvocations.CUADRO_IDENTIFICACION_LISTA_PROCEDIMIENTOS,
		// request);
		setReturnActionFordward(request,
				mappings.findForward("listado_procedimientos"));
	}

	public void verBuscadorProductoresExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);

		try {
			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			fillIdentificacionSerie(identificacionSerie, frm);
			frm.setGuidsProductor(new String[0]);
			setInTemporalSession(request,
					FondosConstants.ADD_PRODUCTOR_HISTORICO, Boolean.FALSE);

			setInTemporalSession(request,
					FondosConstants.LISTA_POSIBLES_PRODUCTORES_KEY, null);
			// saveCurrentInvocation(KeysClientsInvocations.CUADRO_IDENTIFICACION_SELECCION_PRODUCTORES,
			// request);
			setReturnActionFordward(request,
					mappings.findForward("listado_organos"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
	}

	public void verBuscadorProductoresHistoricosExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
					request, FondosConstants.IDENTIFICACION_SERIE_KEY);

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			fillIdentificacionSerie(identificacionSerie, frm);
			frm.setGuidsProductor(new String[0]);
			frm.setGuidProductorHistorico(null);

			frm.setFechaFinVigenciaProductor(null);
			frm.setFechaInicioVigenciaProductor(null);

			setInTemporalSession(request,
					FondosConstants.ADD_PRODUCTOR_HISTORICO, Boolean.TRUE);

			setInTemporalSession(request,
					FondosConstants.LISTA_POSIBLES_PRODUCTORES_KEY, null);
			/*
			 * saveCurrentInvocation(
			 * KeysClientsInvocations.CUADRO_IDENTIFICACION_SELECCION_PRODUCTORES
			 * , request);
			 */
			setReturnActionFordward(request,
					mappings.findForward("listado_organos"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
	}

	public void buscarPosiblesProductoresExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IdentificacionSerie serie = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);
		ServiceRepository services = getServiceRepository(request);
		String queryString = request.getParameter("tokenNombreProductor");
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();

		try {
			List posiblesProductores = serieBI.findPosiblesProductores(
					queryString, serie);
			// eliminar los q ya estan añadidos
			List productoresActuales = serie.getInfoProductoresNoEliminados();

			establecerGuidsInfoOrganoProductor(request, posiblesProductores);

			if (productoresActuales != null) {
				for (Iterator i = productoresActuales.iterator(); i.hasNext();) {
					IInfoProductorSerie obj = (IInfoProductorSerie) i.next();
					if (obj instanceof InfoOrganoProductorSerie)
						posiblesProductores.remove(obj);
					else {
						IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) obj;
						OrganoProductorVO organoProductorVO = serieBI
								.getOrgProductorXIdDescr(infoProductorSerie
										.getIdDescriptor());
						if (organoProductorVO != null) {
							CAOrganoVO caOrganoVO = serieBI
									.getCAOrgProductorVOXId(organoProductorVO
											.getIdOrgano());
							if (posiblesProductores != null) {
								for (int j = 0; j < posiblesProductores.size(); j++) {
									InfoOrganoProductorSerie infoOrganoProductorSerie = (InfoOrganoProductorSerie) posiblesProductores
											.get(j);
									if (infoOrganoProductorSerie
											.getIdEnSistemaExterno()
											.equalsIgnoreCase(
													caOrganoVO
															.getIdOrgSExtGestor())) {
										posiblesProductores.remove(j);
										break;
									}
								}

							}
						} else {
							for (int j = 0; j < posiblesProductores.size(); j++) {
								IInfoProductorSerie infoOrganoProductorSerie = (IInfoProductorSerie) posiblesProductores
										.get(j);
								if (infoOrganoProductorSerie
										.getIdDescriptor()
										.equalsIgnoreCase(obj.getIdDescriptor())) {
									posiblesProductores.remove(j);
									break;
								}
							}
						}
					}
				}
			}

			setInTemporalSession(request,
					FondosConstants.LISTA_POSIBLES_PRODUCTORES_KEY,
					posiblesProductores);
		} catch (GestorOrganismosException e) {
			logger.error(e);
		} catch (NotAvailableException e) {
			logger.error(e);
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
		// saveCurrentInvocation(KeysClientsInvocations.CUADRO_IDENTIFICACION_SELECCION_PRODUCTORES,
		// request);
		setReturnActionFordward(request,
				mappings.findForward("listado_organos"));
	}

	public void cancelarSusticionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setReturnActionFordward(request,
				mappings.findForward("identificacion_serie"));

	}

	public void incorporarProductoresExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			List listaProductoresADarDeAlta = getListaProductoresADarDeAlta(request);

			SerieVO serie = (SerieVO) getFromTemporalSession(request,
					FondosConstants.ELEMENTO_CF_KEY);
			IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
					request, FondosConstants.IDENTIFICACION_SERIE_KEY);

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			List listaPosiblesProductores = (List) getFromTemporalSession(
					request, FondosConstants.LISTA_POSIBLES_PRODUCTORES_KEY);

			String[] guidProductor = frm.getGuidsProductor();

			if (ListUtils.isEmpty(listaPosiblesProductores)
					|| ArrayUtils.isEmpty(guidProductor)) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_ORGANO_PRODUCTOR_NO_SELECCIONADO,
						new ActionError(
								FondosConstants.ERROR_ORGANO_PRODUCTOR_NO_SELECCIONADO));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("listado_organos"));
				return;
			}

			ActionErrors errors = getErrors(request, true);

			List listaProductoresSeleccionados = new ArrayList();

			Date fechaActual = DateUtils.getFechaActualSinHora();

			if (ArrayUtils.isNotEmpty(guidProductor)) {
				for (int i = 0; i < guidProductor.length; i++) {
					InfoProductorSerie infoProductorSerie = (InfoProductorSerie) getObjetoByGuid(
							guidProductor[i], listaPosiblesProductores);

					if (serie.getEstadoserie() == EstadoSerie.EN_ESTUDIO) {
						infoProductorSerie
								.setMarcas(FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_SIN_GUARDAR);
					}

					infoProductorSerie.setFechaInicio(fechaActual);

					// Sólo añadir un error de que el órgano no tiene código si
					// se permite vincular un procedimiento
					// a la serie
					if (identificacionSerie.getPermitidoVincularProcedimiento()) {
						if (StringUtils.isNotEmpty(infoProductorSerie
								.getCodigo())) {
							addObjectInLista(infoProductorSerie,
									listaProductoresSeleccionados);
						} else {
							errors.add(
									Constants.ERROR_GENERAL_MESSAGE,
									new ActionError(
											FondosConstants.ERROR_ORGANO_SIN_CODIGO,
											infoProductorSerie.getNombre()));
						}
					} else {
						addObjectInLista(infoProductorSerie,
								listaProductoresSeleccionados);

					}
				}
			}

			if (errors.isEmpty()) {
				if (!ListUtils.isEmpty(listaProductoresSeleccionados)) {

					ListIterator it = listaProductoresSeleccionados
							.listIterator();

					while (it.hasNext()) {
						InfoProductorSerie infoProductorSerie = (InfoProductorSerie) it
								.next();

						if (!isObjectInLista(infoProductorSerie,
								identificacionSerie
										.getListaInfoProductoresSerie())) {
							addInfoProductorSerie(request, infoProductorSerie);
						}
					}
				}

				// establecerGuidsProductores(request,
				// listaProductoresSeleccionados);

				listaProductoresADarDeAlta
						.removeAll(listaProductoresADarDeAlta);

				frm.setNombreProductor(null);
				frm.setDescripcionProductor(null);
				frm.setArchivoPorProductor(null);
				listProductoresADarDeAlta(request,
						identificacionSerie.getListaInfoProductoresSerie(),
						listaProductoresADarDeAlta, frm);

				frm.setChanged(true);
				// popLastInvocation(request);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
			} else {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("listado_organos"));
			}
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

	}

	public void incorporarProductorEntidadProductoraExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
					request, FondosConstants.IDENTIFICACION_SERIE_KEY);
			identificacionSerie.incorporarEntidadProductoraComoProductor();

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			List listaProductoresADarDeAlta = (List) getFromTemporalSession(
					request, FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY);
			listaProductoresADarDeAlta.removeAll(listaProductoresADarDeAlta);

			frm.setNombreProductor(null);
			frm.setDescripcionProductor(null);
			frm.setArchivoPorProductor(null);
			listProductoresADarDeAlta(request,
					identificacionSerie.getListaInfoProductoresSerie(),
					listaProductoresADarDeAlta, frm);

			// popLastInvocation(request);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
	}

	public void incorporarProductorHistoricoExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			// SerieVO serie =
			// (SerieVO)getFromTemporalSession(request,
			// FondosConstants.ELEMENTO_CF_KEY);
			IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
					request, FondosConstants.IDENTIFICACION_SERIE_KEY);

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			List listaPosiblesProductores = (List) getFromTemporalSession(
					request, FondosConstants.LISTA_POSIBLES_PRODUCTORES_KEY);

			ActionErrors errors = getErrors(request, true);

			String guidProductor = request
					.getParameter(GUID_PRODUCTOR_HISTORICO);
			if (StringUtils.isEmpty(guidProductor)) {
				errors.add(
						FondosConstants.ERROR_ORGANO_PRODUCTOR_NO_SELECCIONADO,
						new ActionError(
								FondosConstants.ERROR_ORGANO_PRODUCTOR_NO_SELECCIONADO));
			}

			// Comprobar las fechas
			String fechaInicial = frm.getFechaInicioVigenciaProductor();
			String fechaFinal = frm.getFechaFinVigenciaProductor();

			if (StringUtils.isNotEmpty(fechaInicial)
					&& StringUtils.isNotEmpty(fechaFinal)) {
				if (!DateUtils.isDate(fechaInicial)) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_DATE,
									Messages.getString(
											Constants.ETIQUETA_FECHA_INICIO_VIGENCIA_PRODUCTOR,
											request.getLocale())));
				} else {
					Date fechaInicioVigencia = DateUtils.getDate(fechaInicial);
					validateFechasVigencia(request, errors,
							fechaInicioVigencia, fechaFinal);
				}
			} else {
				if (StringUtils.isEmpty(fechaInicial)) {
					errors.add(
							Constants.ERROR_OBLIGATORIA,
							new ActionError(
									Constants.ERROR_OBLIGATORIA,
									Messages.getString(
											Constants.ETIQUETA_FECHA_INICIO_VIGENCIA_PRODUCTOR,
											request.getLocale())));
				}

				if (StringUtils.isEmpty(fechaFinal)) {
					errors.add(
							Constants.ERROR_OBLIGATORIA,
							new ActionError(
									Constants.ERROR_OBLIGATORIA,
									Messages.getString(
											Constants.ETIQUETA_FECHA_FIN_VIGENCIA_PRODUCTOR,
											request.getLocale())));
				}
			}

			if (!errors.isEmpty()) {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("listado_organos"));
				return;
			}

			IInfoProductorSerie infoOrganoProductorSerie = (IInfoProductorSerie) getObjetoByGuid(
					guidProductor, listaPosiblesProductores);

			infoOrganoProductorSerie
					.setMarcas(FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR);

			infoOrganoProductorSerie.setFechaInicio(frm
					.getFechaInicioVigenciaProductor());
			infoOrganoProductorSerie.setFechaFin(frm
					.getFechaFinVigenciaProductor());

			addInfoProductorSerie(request, infoOrganoProductorSerie);

			// List
			// listaProductoresHistoricos=(List)getFromTemporalSession(request,
			// FondosConstants.PRODUCTORES_HISTORICOS_KEY);
			// if(listaProductoresHistoricos == null){
			// listaProductoresHistoricos = new ArrayList();
			// setInTemporalSession(request,
			// FondosConstants.PRODUCTORES_HISTORICOS_KEY,
			// listaProductoresHistoricos);
			// }

			// ProductorSerieVO productorSerieVO =
			// getProductorSerieVO(serie.getId(), infoOrganoProductorSerie);
			// addObjectInLista(productorSerieVO, listaProductoresHistoricos);

			List listaProductoresADarDeAlta = (List) getFromTemporalSession(
					request, FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY);

			listaProductoresADarDeAlta.removeAll(listaProductoresADarDeAlta);

			frm.setNombreProductor(null);
			frm.setDescripcionProductor(null);
			frm.setArchivoPorProductor(null);
			listProductoresADarDeAlta(request,
					identificacionSerie.getListaInfoProductoresSerie(),
					listaProductoresADarDeAlta, frm);

			frm.setChanged(true);

			// popLastInvocation(request);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
	}

	public void guardarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("inicio guardarExecuteLogic");

		IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);

		SerieVO serie = (SerieVO) getFromTemporalSession(request,
				FondosConstants.ELEMENTO_CF_KEY);
		IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
		GestionSeriesBI seriesService = getGestionSeriesBI(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionSeriesBI serviceSeries = services.lookupGestionSeriesBI();

		ActionErrors errors = null;
		try {
			if ((errors = validateFormInSaveIdentificacion(form)) == null) {
				// TODO ESTO TENGO Q PONERLO EN EL SERVICIO !!!!
				fillIdentificacionSerie(identificacionSerie, frm);
				darDeAltaNuevosOrganos(request, frm);

				List listaProductoresHistoricos = null;
				if (identificacionSerie.getProcedimiento() == null
						&& serie.getEstadoserie() == EstadoSerie.EN_ESTUDIO) {
					List sustitucionVigenteHistorico = (List) getFromTemporalSession(
							request,
							FondosConstants.SUSTITUCION_VIGENTE_HISTORICO_KEY);
					listaProductoresHistoricos = (List) getFromTemporalSession(
							request, FondosConstants.PRODUCTORES_HISTORICOS_KEY);
					List listaProductoresPasadosAHistoricos = (List) getFromTemporalSession(
							request,
							FondosConstants.PRODUCTORES_PASADOS_A_HISTORICOS_KEY);
					List listaProductoresHistoricosEliminados = (List) getFromTemporalSession(
							request,
							FondosConstants.PRODUCTORES_HISTORICOS_ELIMINADOS_KEY);
					seriesService
							.saveIdentificacionSerieSinProcedimientoEnEstudio(
									serie, identificacionSerie,
									listaProductoresHistoricos,
									sustitucionVigenteHistorico,
									listaProductoresPasadosAHistoricos,
									listaProductoresHistoricosEliminados);
					listaProductoresHistoricos
							.removeAll(listaProductoresHistoricos);
					listaProductoresHistoricos.addAll(serviceSeries
							.getProductoresHistoricosBySerie(serie.getId()));
					sustitucionVigenteHistorico
							.removeAll(sustitucionVigenteHistorico);
				} else {
					seriesService.saveIdentificacionSerie(serie,
							identificacionSerie);
				}
				// actualizar nodo del arbol
				CuadroClasificacionTreeView treeView = (CuadroClasificacionTreeView) getFromTemporalSession(
						request, FondosConstants.CUADRO_CLF_VIEW_NAME);
				if (treeView != null) {
					TreeNode serieEnArbol = treeView.getSelectedNode();
					TreeModelItem item = serieEnArbol.getModelItem();
					((ElementoCuadroClasificacionVO) item).setTitulo(frm
							.getDenominacion());
				}

				getInvocationStack(request).getLastClientInvocation()
						.addParameter(FondosConstants.REFRESH_VIEW_KEY, "true");
				frm.setChanged(false);
				frm.setGuardado(true);
				serie.setTitulo(frm.getDenominacion());

				if (identificacionSerie.getProcedimiento() != null
						|| serie.getEstadoserie() != EstadoSerie.EN_ESTUDIO) {
					listaProductoresHistoricos = serviceSeries
							.getProductoresHistoricosBySerie(serie.getId());
					List listaProductoresVigentes = serviceSeries
							.getProductoresVigentesBySerie(serie.getId());
					setInTemporalSession(request,
							FondosConstants.PRODUCTORES_VIGENTES_KEY,
							listaProductoresVigentes);
					setInTemporalSession(request,
							FondosConstants.PRODUCTORES_HISTORICOS_KEY,
							listaProductoresHistoricos);
				}

				List listaProductoresADarDeAlta = (List) getFromTemporalSession(
						request, FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY);
				listaProductoresADarDeAlta
						.removeAll(listaProductoresADarDeAlta);
				setReturnActionFordward(request,
						mappings.findForward("inicioIdentificacionSerie"));
			} else {
				obtenerErrores(request, true).add(errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
			}
		} catch (FondosOperacionNoPermitidaException e) {
			logger.debug(e);
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (GestorOrganismosException e) {
			logger.debug(e);
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (NotAvailableException e) {
			logger.debug(e);
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (ActionNotAllowedException e) {
			logger.debug(e);
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}

		logger.debug("fin guardarExecuteLogic");
	}

	public void guardarNuevosOrganosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
		try {
			darDeAltaNuevosOrganos(request, frm);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (FondosOperacionNoPermitidaException e) {
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (GestorOrganismosException e) {
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (NotAvailableException e) {
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
	}

	public void pasarAHistoricoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String guidProductor = request.getParameter(GUID_PRODUCTOR);
			if (StringUtils.isEmpty(guidProductor)) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_VIGENTE_NO_SELECCIONADO));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
					request, FondosConstants.IDENTIFICACION_SERIE_KEY);
			List productoresSerie = identificacionSerie
					.getListaInfoProductoresSerie();

			IInfoProductorSerie productorAHistorico = (IInfoProductorSerie) getObjetoByGuid(
					guidProductor, productoresSerie);

			// Comprobar que el productor sustituidor es un productor nuevo
			if (productorAHistorico != null
					&& !productorAHistorico.isPermitidoPasarAHistorico()) {
				ActionErrors errors = new ActionErrors();
				errors.add(
						FondosConstants.ERROR_PRODUCTOR_VIGENTE_DEBE_SER_EXISTENTE,
						new ActionError(
								FondosConstants.ERROR_PRODUCTOR_VIGENTE_DEBE_SER_EXISTENTE));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("identificacion_serie"));
				return;
			}

			Date fechaFin = DateUtils.getFechaActualSinHora();

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			String fechaActualStr = DateUtils.formatDate(fechaFin);
			frm.setFechaFinVigenciaProductor(fechaActualStr);
			frm.setChanged(true);
			setInTemporalSession(request,
					FondosConstants.PRODUCTOR_A_HISTORICO_KEY,
					productorAHistorico);
			// saveCurrentInvocation(KeysClientsInvocations.SERIES_PRODUCTORES_A_HISTORICOS,
			// request);

			frm.setChanged(true);
			setReturnActionFordward(request,
					mappings.findForward("pasar_productor_a_historico"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
	}

	public void realizarPasarAHistoricoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			InfoProductorSerie infoProductorSerie = (InfoProductorSerie) getFromTemporalSession(
					request, FondosConstants.PRODUCTOR_A_HISTORICO_KEY);

			// Validar las fechas
			String fechaFinVigencia = request
					.getParameter("fechaFinVigenciaProductor");
			ActionErrors errors = new ActionErrors();
			validateFechasVigencia(request, errors,
					infoProductorSerie.getFechaInicio(), fechaFinVigencia);

			if (errors != null && !errors.isEmpty()) {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("pasar_productor_a_historico"));
				return;
			}

			String guidProductor = null;
			if (infoProductorSerie != null) {
				guidProductor = infoProductorSerie.getGuid();
			}

			pasarAHistorico(request, guidProductor, fechaFinVigencia,
					ProductoresUtils.ACCION_PASAR_A_HISTORICO);

			// popLastInvocation(request);

			IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
			frm.setChanged(true);
			setReturnActionFordward(request,
					mappings.findForward("identificacion_serie"));
		} catch (Exception exception) {
			excepcionIdentificacionProductores(mappings, request, exception);
		}
	}

}