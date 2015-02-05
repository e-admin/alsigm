package valoracion.actions;

import es.archigest.framework.core.vo.PropertyBean;
import fondos.FondosConstants;
import fondos.model.IElementoCuadroClasificacion;
import fondos.view.SeriePO;
import fondos.vos.FondoVO;
import fondos.vos.SerieVO;
import gcontrol.ControlAccesoConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.ErrorsTag;
import valoracion.ValoracionConstants;
import valoracion.exceptions.ValoracionActionNotAllowedException;
import valoracion.forms.ValoracionForm;
import valoracion.utils.ExceptionMapper;
import valoracion.utils.XMLToSerie;
import valoracion.view.InfoSerie;
import valoracion.view.SerieToPO;
import valoracion.view.ValoracionSeriePO;
import valoracion.view.ValoracionToPO;
import valoracion.vos.BoletinInfoVO;
import valoracion.vos.ValoracionSerieVO;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionNivelesArchivoBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.UncheckedArchivoException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.TypeConverter;

/**
 * Action para el manejo de las acciones que se pueden ser llevadas a cabo
 * durante la elaboracion y gestion de valoraciones de series documentales
 * 
 */
public class GestionValoracionAction extends BaseAction {

	/*
	 * Presenta la informacion de una valoracion
	 */
	public void verValoracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ValoracionForm valoracionForm = (ValoracionForm) form;
		String pIdValoracion = valoracionForm.getId();
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		ValoracionSerieVO valoracion = valoracionBI
				.abrirValoracion(pIdValoracion);
		valoracion = (ValoracionSeriePO) ValoracionToPO.getInstance(services)
				.transform(valoracion);

		if (valoracion != null && valoracion.getValorDictamen() < 0) {
			valoracion.setValorDictamen(Constants.VALOR_DICTAMEN_NO_DEFINIDO);
			valoracion.setValorDictamenValue(Messages.getString(
					ValoracionConstants.PREFIX_KEY_VALORES_DICTAMEN + "0",
					request.getLocale()));
		}

		ClientInvocation lastClient = getInvocationStack(request)
				.getLastClientInvocation();
		if (lastClient.getTitleNavigationToolBar().indexOf("VER_SERIE") > 0) {
			lastClient
					.setPath("/action/manageVistaCuadroClasificacion?actionToPerform=goHome");
			String id = null;
			if (lastClient.getParameters().get("idserie") != null)
				id = ((String[]) lastClient.getParameters().get("idserie"))[0];
			lastClient.getParameters().put("itemID", id);
		}

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_VER_VALORACION, request);
		invocation.setAsReturnPoint(true);

		setInTemporalSession(request, ValoracionConstants.VALORACION_KEY,
				valoracion);

		// obtener la lista de niveles de archivo
		GestionNivelesArchivoBI nivelesArchivo = services
				.lookupGestionNivelesArchivoBI();
		List listaNivelesArchivo = nivelesArchivo.getListaNivelesArchivo();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO,
				listaNivelesArchivo);
		setReturnActionFordward(request,
				mappings.findForward("info_valoracion"));
	}

	/** Presentación de la informacion de la valoración dictaminada de una serie */
	public void verValoracionSerieDictaminadaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionValoracionBI service = services.lookupGestionValoracionBI();
		/* GestionEliminacionBI serviceEliminacion = */services
				.lookupGestionEliminacionBI();
		GestionSeriesBI serviceSeries = services.lookupGestionSeriesBI();
		GestionCuadroClasificacionBI serviceCuadro = services
				.lookupGestionCuadroClasificacionBI();

		SerieVO serie = serviceSeries.getSerie(request.getParameter("idserie"));

		// Obtenemos la valoracion actual y la metemos para mostrarla
		ValoracionSerieVO valoracionDictaminada = service
				.getValoracionDictaminada(request.getParameter("idserie"));

		ValoracionSeriePO valoracionDictaminadaPO = null;
		if (valoracionDictaminada != null) {
			valoracionDictaminadaPO = (ValoracionSeriePO) ValoracionToPO
					.getInstance(services).transform(valoracionDictaminada);
		}

		setInTemporalSession(request, ValoracionConstants.VALORACION_KEY,
				valoracionDictaminadaPO);
		ValoracionSerieVO valoracionEnCurso = service
				.getValoracionEnCurso(request.getParameter("idserie"));
		request.setAttribute(ValoracionConstants.VALORACION_ACTUAL_KEY,
				valoracionEnCurso);
		AppUser appUser = getAppUser(request);
		if (serie.getPermitidoIniciarValoracion())
			if ((valoracionDictaminada == null || valoracionEnCurso == null)
					&& (appUser.getId()
							.equalsIgnoreCase(serie.getIdusrgestor()) || (appUser
							.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))))
				request.setAttribute(
						ValoracionConstants.VER_BOTON_INICIAR_VALORACION,
						new Boolean("true"));

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_ELEMENTO_DEL_CUADRO, request);
		invocation.setTitleNavigationToolBar(TitlesToolBar.CUADRO_VER_SERIE);
		invocation.setAsReturnPoint(true);
		if (getFromTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY) == null)
			setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
					new SeriePO(serie, services));
		if (getFromTemporalSession(request,
				FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION) == null) {
			removeInTemporalSession(request,
					FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION);
			setInTemporalSession(request,
					FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
					serviceCuadro.getAncestors(serie.getId()));
		}

		// obtener la lista de niveles de archivo
		GestionNivelesArchivoBI nivelesArchivo = services
				.lookupGestionNivelesArchivoBI();
		List listaNivelesArchivo = nivelesArchivo.getListaNivelesArchivo();
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO,
				listaNivelesArchivo);

		setReturnActionFordward(request,
				mappings.findForward("valoracion_serie_dictaminada"));
	}

	/*
	 * Presenta las valoraciones de las que el usuario es gestor y que se
	 * encuentran en elaboracion
	 */
	public void valoracionesEnElaboracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		List valoracionesEnElaboracion = valoracionBI
				.getValoracionesEnElaboracion(appUser.getId());
		CollectionUtils.transform(valoracionesEnElaboracion,
				ValoracionToPO.getInstance(services));
		request.setAttribute(ValoracionConstants.LISTA_VALORACIONES_KEY,
				valoracionesEnElaboracion);
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_VALORACIONES_EN_ELABORACION,
				request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mappings.findForward("lista_valoraciones"));
	}

	/* Presenta las valoraciones que estan a la espera de ser gestionadas */
	public void valoracionesAGestionarExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		List valoracionesAGestionar = valoracionBI
				.getValoracionesAGestionar(appUser.getId());
		CollectionUtils.transform(valoracionesAGestionar,
				ValoracionToPO.getInstance(services));
		request.setAttribute(ValoracionConstants.LISTA_VALORACIONES_KEY,
				valoracionesAGestionar);
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_VALORACIONES_A_GESTIONAR,
				request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mappings.findForward("lista_valoraciones_a_gestionar"));

	}

	/*
	 * Presenta el formulario de alta de una valoracion
	 */
	public void nuevaValoracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Establecemos los elementos necesarios para la vista
		this.establecerElementosFormulario(request);
		/* ClientInvocation invocation = */saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_NUEVA_VALORACION, request);
		// invocation.setAsReturnPoint(true);
		GestionFondosBI fondosService = getGestionFondosBI(request);
		int[] estados = { IElementoCuadroClasificacion.VIGENTE };
		removeInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY);
		setInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY,
				fondosService.getFondosXEstados(estados));
		setReturnActionFordward(request,
				mappings.findForward("nueva_valoracion"));
	}

	/*
	 * public void crearValoracionExecuteLogic(ActionMapping mappings,ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) {
	 * //Obtenemos el servicio de valoracion GestionValoracionBI service =
	 * getGestionValoracionBI(request); //Obtenemos los datos del formulario
	 * ValoracionForm valoracion = (ValoracionForm)form;
	 * 
	 * //Validar el formulario ActionErrors errores =
	 * valoracion.validate(mappings, request); if ( (errores == null) ||
	 * errores.isEmpty() ) { ValoracionSerieVO valoracionVO = new
	 * ValoracionSerieVO(); valoracion.populate(valoracionVO);
	 * valoracionVO.setIdSerie( request.getParameter("serieSeleccionada"));
	 * 
	 * //Creamos la valoracion try { valoracionVO =
	 * service.crearValoracion(valoracionVO); ActionRedirect vistaValoracion =
	 * new ActionRedirect(mappings.findForward("redirect_to_info_valoracion"));
	 * vistaValoracion.setRedirect(true);
	 * vistaValoracion.addParameter(Constants.ID, valoracionVO.getId());
	 * popLastInvocation(request); setReturnActionFordward(request,
	 * vistaValoracion); } catch(ValoracionActionNotAllowedException vanae) {
	 * errores = ExceptionMapper.getErrorsExcepcion(vanae);
	 * 
	 * //Añadir los errores al request getErrors(request, true).add(errores);
	 * //establecemos los elementos del form
	 * this.establecerElementosFormulario(request);
	 * setReturnActionFordward(request,
	 * mappings.findForward("nueva_valoracion")); } } else {
	 * logger.info("Formulario invalido");
	 * 
	 * //Añadir los errores al request getErrors(request, true).add(errores);
	 * //establecemos los elementos del form
	 * this.establecerElementosFormulario(request);
	 * setReturnActionFordward(request,
	 * mappings.findForward("nueva_valoracion")); } }
	 */
	/*
	 * Realiza la busqueda de series segun los criterios pasados para el
	 * formulario de alta de valoración.
	 */
	public void buscarSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		this.establecerElementosBusqueda(request);

		// Establecemos los elementos necesarios para la vista
		this.establecerElementosFormulario(request);

		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_NUEVA_VALORACION, request);
		setReturnActionFordward(request,
				mappings.findForward("nueva_valoracion"));
	}

	private void quitarSeriesSeleccionadas(List seriesEncontradas,
			List seriesExistentes) {
		List nuevasSeries = new ArrayList(seriesEncontradas);
		for (int i = 0; i < nuevasSeries.size(); i++) {

			valoracion.view.SeriePO seriePO = (valoracion.view.SeriePO) nuevasSeries
					.get(i);
			for (int j = 0; j < seriesExistentes.size(); j++) {
				InfoSerie infoSerie = (InfoSerie) seriesExistentes.get(j);
				if (seriePO.getId().equals(infoSerie.getId())) {
					seriesEncontradas.remove(seriePO);
					break;
				}
			}
		}
	}

	/*
	 * Realiza la busqueda de series segun los criterios pasados para el
	 * formulario de edición de valoración.
	 */
	public void buscarSerieEdicionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Realizamos la busqueda de las series por los criterios pasados
		String pFondoID = request.getParameter("fondo");
		String pCodigo = request.getParameter("codigo");
		String pTitulo = request.getParameter("tituloBuscar");

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionSeriesBI serieBI = getGestionSeriesBI(request);

		List series = null;

		Integer tipoEdicion = (Integer) getFromTemporalSession(request,
				ValoracionConstants.TIPO_SERIES_EN_EDICION_KEY);

		// if
		// (tipoEdicion.intValue()==ValoracionConstants.TIPO_SERIE_PRECEDENTES)
		series = serieBI.getSeriesActivas(pFondoID, pCodigo, pTitulo);
		// else
		// series=serieBI.getSeriesPorEstados(pFondoID, pCodigo, pTitulo, new
		// int[]{EstadoSerie.VIGENTE});
		CollectionUtils.transform(series, SerieToPO.getInstance(services));

		// Se quitan las series que ya estan seleccionadas

		ValoracionSeriePO valoracionSeriePO = (ValoracionSeriePO) getFromTemporalSession(
				request, ValoracionConstants.VALORACION_KEY);

		if (tipoEdicion.intValue() == ValoracionConstants.TIPO_SERIE_PRECEDENTES
				&& !ListUtils.isEmpty(valoracionSeriePO
						.getListaSeriesPrecedentes())
				&& !ListUtils.isEmpty(series))

			quitarSeriesSeleccionadas(series,
					valoracionSeriePO.getListaSeriesPrecedentes());

		else if (tipoEdicion.intValue() == ValoracionConstants.TIPO_SERIE_RELACIONADAS
				&& !ListUtils.isEmpty(valoracionSeriePO
						.getListaSeriesRelacionadas())
				&& !ListUtils.isEmpty(series))

			quitarSeriesSeleccionadas(series,
					valoracionSeriePO.getListaSeriesRelacionadas());

		request.setAttribute(ValoracionConstants.LISTA_SERIES_KEY, series);

		// Establecemos los elementos necesarios para la vista
		this.establecerElementosFormulario(request);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_serie"));
	}

	private ValoracionSerieVO processPeticionInicioValoracion(
			HttpServletRequest request, ValoracionForm valoracionForm)
			throws ActionNotAllowedException {
		ServiceRepository services = getServiceRepository(request);

		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		String serieSeleccionada = valoracionForm.getIdSerie();
		SerieVO serie = serieBI.getSerie(serieSeleccionada);
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		ValoracionSerieVO valoracion = valoracionBI
				.iniciarValoracionSerie(serie);
		valoracionForm.setTitulo(valoracion.getTitulo());
		// ClientInvocation invocation =
		// saveCurrentInvocation(KeysClientsInvocations.VALORACION_EDICION_VALORACION,
		// request);
		// invocation.setAsReturnPoint(true);
		// setInTemporalSession(request, ValoracionConstants.SERIE_KEY, serie);
		this.establecerElementosFormulario(request);
		return valoracion;
	}

	/*
	 * Selecciona de entre los resultados de busqueda de serie aquella sobre la
	 * que se va a realizar la valoracion
	 */
	public void seleccionarSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ValoracionSerieVO valoracion = processPeticionInicioValoracion(
					request, (ValoracionForm) form);
			// ClientInvocation invocationBusquedaSerie =
			// getInvocationStack(request).getLastClientInvocation();
			// invocationBusquedaSerie.setVisibleInNavigationToolBar(false);
			ActionRedirect vistaValoracion = new ActionRedirect(
					mappings.findForward("redirect_to_info_valoracion"));
			vistaValoracion.setRedirect(true);
			vistaValoracion.addParameter(Constants.ID, valoracion.getId());
			popLastInvocation(request);
			setReturnActionFordward(request, vistaValoracion);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			establecerElementosBusqueda(request);
			setReturnActionFordward(request,
					mappings.findForward("nueva_valoracion"));
		}
	}

	/* Inicia una valoracion sobre una determinada serie */
	public void iniciarValoracionSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionRedirect vistaValoracion = null;
		try {
			ValoracionSerieVO valoracion = processPeticionInicioValoracion(
					request, (ValoracionForm) form);

			vistaValoracion = new ActionRedirect(
					mappings.findForward("redirect_to_info_valoracion"));
			vistaValoracion.setRedirect(true);
			vistaValoracion.addParameter(Constants.ID, valoracion.getId());

			setReturnActionFordward(request, vistaValoracion);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			vistaValoracion = new ActionRedirect(
					mappings.findForward("redirect_to_serie_complete"));
			vistaValoracion.addParameter("itemID",
					((ValoracionForm) form).getIdSerie());
		}
		vistaValoracion.setRedirect(true);
		setReturnActionFordward(request, vistaValoracion);
	}

	/**
	 * Realiza la busqueda de las series segun el filtrado deseado estableciendo
	 * los elementos encontrados para mostrarlos en la vista
	 * 
	 * @param request
	 */
	private void establecerElementosBusqueda(HttpServletRequest request) {
		// Realizamos la busqueda de las series por los criterios pasados
		String pFondoID = request.getParameter("fondo");
		String pCodigo = request.getParameter("codigo");
		String pTitulo = request.getParameter("tituloBuscar");

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionSeriesBI serieBI = getGestionSeriesBI(request);

		List series = serieBI.findSeriesValorables(pFondoID, pCodigo, pTitulo);
		CollectionUtils.transform(series, SerieToPO.getInstance(services));
		request.setAttribute(ValoracionConstants.LISTA_SERIES_KEY, series);
	}

	public void guardarInfoValoracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ValoracionSerieVO valoracion = (ValoracionSerieVO) getFromTemporalSession(
				request, ValoracionConstants.VALORACION_KEY);

		if (valoracion != null) {
			ValoracionForm valoracionForm = (ValoracionForm) form;
			ActionErrors errores = valoracionForm.validate(mappings, request);
			if (errores.isEmpty()) {
				valoracionForm.populate(valoracion);

				try {
					getGestionValoracionBI(request).actualizarValoracion(
							valoracion);
					List plazosNuevos = valoracionForm
							.populateToListPlazosValoracion();
					getGestionValoracionBI(request)
							.updateDeletePlazosValoracion(
									valoracionForm.getId(), plazosNuevos);

					goBackExecuteLogic(mappings, form, request, response);
					return;
				} catch (ValoracionActionNotAllowedException vanae) {
					errores.add(ExceptionMapper.getErrorsExcepcion(request,
							vanae));
				}
			}

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			establecerElementosFormulario(request);
			setReturnActionFordward(request,
					mappings.findForward("edicion_valoracion"));
		}
	}

	/*
	 * Realiza el proceso de almacenamiento de una valoracion tras el proceso de
	 * edicion
	 */
	// public void guardarCambiosExecuteLogic(ActionMapping mappings,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response)
	// {
	// //Obtenemos el servicio de valoracion
	// GestionValoracionBI service = getGestionValoracionBI(request);
	// //Obtenemos los datos del formulario
	// // ValoracionForm valoracion = (ValoracionForm)form;
	//
	// //Validar el formulario
	// // ActionErrors errores = valoracion.validate(mappings, request);
	// // if ( (errores == null) || errores.isEmpty() ) {
	// // ValoracionSerieVO valoracionVO = new ValoracionSerieVO();
	// // valoracion.populate(valoracionVO);
	// // valoracionVO.setSeriesDescendientes(
	// // XMLToSerie.getInstance().transform(
	// //
	// (List)getFromTemporalSession(request,ValoracionConstants.LISTA_SERIES_DESCENDIENTES_KEY))
	// // );
	// // valoracionVO.setSeriesPrecedentes(
	// // XMLToSerie.getInstance().transform(
	// //
	// (List)getFromTemporalSession(request,ValoracionConstants.LISTA_SERIES_PRECEDENTES_KEY))
	// // );
	// // valoracionVO.setSeriesRelacionadas(
	// // XMLToSerie.getInstance().transform(
	// //
	// (List)getFromTemporalSession(request,ValoracionConstants.LISTA_SERIES_RELACIONADAS_KEY))
	// // );
	//
	// //actualizamos la valoracion
	// ValoracionSeriePO valoracion = (ValoracionSeriePO)
	// getFromTemporalSession(
	// request, ValoracionConstants.VALORACION_KEY);
	// valoracion.setSeriesPrecedentes(XMLToSerie.getInstance().transform(
	// valoracion.getListaSeriesPrecedentes()));
	// valoracion.setSeriesDescendientes(XMLToSerie.getInstance().transform(
	// valoracion.getListaSeriesDescendientes()));
	// valoracion.setSeriesRelacionadas(XMLToSerie.getInstance().transform(
	// valoracion.getListaSeriesRelacionadas()));
	// try
	// {
	// service.actualizarValoracion(valoracion);
	// valoracion.setHasBeenChanged(false);
	// } catch (ValoracionActionNotAllowedException vanae)
	// {
	// //Añadir los errores al request
	// getErrors(request, true).add(
	// ExceptionMapper.getErrorsExcepcion(vanae));
	// }
	// goLastClientExecuteLogic(mappings, form, request, response);
	// // } else {
	// // //Añadir los errores al request
	// // getErrors(request, true).add(errores);
	// // }
	//
	// //establecemos los elementos del form
	// // this.establecerElementosFormulario(request);
	// // this.establecerElementosBusqueda(request);
	// //Guardamos los datos y volvemos a la pantalla de edicion
	// // setReturnActionFordward(request,
	// // mappings.findForward("edicion_valoracion"));
	// }

	/*
	 * Presenta el formulario de edicion de una valoracion en el que se
	 * presentan los datos de una valoracion para que estos puedan ser
	 * modificados
	 */
	public void edicionValoracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Establecemos los elementos del formulario
		this.establecerElementosFormulario(request);

		// Obtenemos el id de la valoracion
		ValoracionSerieVO valoracionAEditar = null;
		String idValoracion = request.getParameter(Constants.ID);
		if (idValoracion != null)
			// Obtenemos la valoracion que vamos a editar
			valoracionAEditar = getGestionValoracionBI(request).getValoracion(
					idValoracion);
		else
			valoracionAEditar = (ValoracionSerieVO) getFromTemporalSession(
					request, ValoracionConstants.VALORACION_KEY);

		if (valoracionAEditar == null)
			throw new UncheckedArchivoException(
					"No se ha podido encontrar la valoración a editar");

		if (valoracionAEditar.getTipoRegimenAccesoTemporal() == Integer.MIN_VALUE) {
			valoracionAEditar
					.setTipoRegimenAccesoTemporal(ValoracionSerieVO.SUBTIPO_REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL_TOTAL);
		}

		// Asignamos los datos de la valoracion a editar
		((ValoracionForm) form).setValoracion(valoracionAEditar);

		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_EDICION_VALORACION, request);

		setReturnActionFordward(request,
				mappings.findForward("edicion_valoracion"));
	}

	public void seleccionarSeriesPrecedentesExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_SELECCION_SERIES_PRECEDENTES,
				request);

		removeInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY);
		GestionFondosBI fondosService = getGestionFondosBI(request);

		setInTemporalSession(
				request,
				ValoracionConstants.LISTA_FONDOS_KEY,
				fondosService
						.getFondosXEstados(new int[] { IElementoCuadroClasificacion.VIGENTE }));

		setInTemporalSession(request,
				ValoracionConstants.TIPO_SERIES_EN_EDICION_KEY, new Integer(
						ValoracionConstants.TIPO_SERIE_PRECEDENTES));

		// List fondos = new ArrayList();

		ValoracionForm valoracionForm = (ValoracionForm) form;
		FondoVO fondo = fondosService.getFondoXIdValoracion(valoracionForm
				.getId());
		request.setAttribute("fondo", fondo.getId());
		// if (fondo != null)
		// fondos.add(fondo);

		// setInTemporalSession(request,
		// ValoracionConstants.LISTA_FONDOS_KEY, fondos);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_serie"));
	}

	// public void seleccionarSeriesDescendientesExecuteLogic(
	// ActionMapping mappings, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// {
	// saveCurrentInvocation(
	// KeysClientsInvocations.VALORACION_SELECCION_SERIES_DESCENDIENTES,
	// request);
	//
	// setInTemporalSession(request,
	// ValoracionConstants.LISTA_FONDOS_KEY,
	// getGestionFondosBI(request).getFondosXEstados(
	// new int[] { IElementoCuadroClasificacion.VIGENTE } ));
	//
	// setInTemporalSession(request,
	// ValoracionConstants.TIPO_SERIES_EN_EDICION_KEY,
	// new Integer(ValoracionConstants.TIPO_SERIE_DESCENDIENTES));
	//
	// setReturnActionFordward(request,
	// mappings.findForward("seleccion_serie"));
	// }

	public void seleccionarSeriesRelacionadasExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_SELECCION_SERIES_RELACIONADAS,
				request);

		removeInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY);
		setInTemporalSession(
				request,
				ValoracionConstants.LISTA_FONDOS_KEY,
				getGestionFondosBI(request).getFondosXEstados(
						new int[] { IElementoCuadroClasificacion.VIGENTE }));

		setInTemporalSession(request,
				ValoracionConstants.TIPO_SERIES_EN_EDICION_KEY, new Integer(
						ValoracionConstants.TIPO_SERIE_RELACIONADAS));

		setReturnActionFordward(request,
				mappings.findForward("seleccion_serie"));
	}

	/*
	 * Añade a las series dependientes de la valoracion las series seleccionadas
	 */
	public void anadirSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Integer tipoEdicion = (Integer) getFromTemporalSession(request,
				ValoracionConstants.TIPO_SERIES_EN_EDICION_KEY);

		if (tipoEdicion != null) {
			ValoracionForm valoracionForm = (ValoracionForm) form;
			String[] seriesSeleccionadas = valoracionForm.getSerieIDs();
			String nombreSerie = request.getParameter("nombreSerie");

			ValoracionSeriePO valoracion = (ValoracionSeriePO) getFromTemporalSession(
					request, ValoracionConstants.VALORACION_KEY);

			if (StringUtils.isNotBlank(nombreSerie)) {
				valoracion.getListaSeriesRelacionadas().add(
						new InfoSerie(null, null, nombreSerie));
				valoracion.setSeriesRelacionadas(XMLToSerie.getInstance()
						.transform(valoracion.getListaSeriesRelacionadas()));

				try {
					getGestionValoracionBI(request).actualizarValoracion(
							valoracion);
				} catch (ValoracionActionNotAllowedException vanae) {
					logger.error("Error al añadir series", vanae);
				}
			} else if (seriesSeleccionadas != null
					&& seriesSeleccionadas.length > 0) {
				switch (tipoEdicion.intValue()) {
				case ValoracionConstants.TIPO_SERIE_PRECEDENTES:
					addSeries(request, seriesSeleccionadas,
							valoracion.getListaSeriesPrecedentes());
					valoracion.setSeriesPrecedentes(XMLToSerie.getInstance()
							.transform(valoracion.getListaSeriesPrecedentes()));
					break;

				// case ValoracionConstants.TIPO_SERIE_DESCENDIENTES:
				// addSeries(request, seriesSeleccionadas,
				// valoracion.getListaSeriesDescendientes());
				// valoracion.setSeriesDescendientes(
				// XMLToSerie.getInstance().transform(
				// valoracion.getListaSeriesDescendientes()));
				// break;

				case ValoracionConstants.TIPO_SERIE_RELACIONADAS:
					addSeries(request, seriesSeleccionadas,
							valoracion.getListaSeriesRelacionadas());
					valoracion
							.setSeriesRelacionadas(XMLToSerie
									.getInstance()
									.transform(
											valoracion
													.getListaSeriesRelacionadas()));
					break;
				}

				try {
					getGestionValoracionBI(request).actualizarValoracion(
							valoracion);
				} catch (ValoracionActionNotAllowedException vanae) {
					logger.error("Error al añadir series", vanae);
				}
			}
		}

		goBackExecuteLogic(mappings, form, request, response);
	}

	protected void addSeries(HttpServletRequest request,
			String[] seriesSeleccionadas, List series) {
		GestionSeriesBI serieBI = getGestionSeriesBI(request);
		SerieVO serie = null;

		for (int i = 0; i < seriesSeleccionadas.length; i++) {
			serie = serieBI.getSerie(seriesSeleccionadas[i]);
			if (serie != null)
				series.add(new InfoSerie(serie.getId(), serie
						.getCodReferencia(), serie.getTitulo()));
		}
	}

	/*
	 * Elimina de las series dependientes de la valoracion las series
	 * seleccionadas
	 */
	public void eliminarSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List series = null;
		boolean isModified = false;

		ValoracionSeriePO valoracion = (ValoracionSeriePO) getFromTemporalSession(
				request, ValoracionConstants.VALORACION_KEY);

		// Obtenemos los detalles a añadir
		String[] idsSeriesPrecedentes = request
				.getParameterValues("serieSeleccionadaPrecedenteEliminar");
		String[] idsSeriesRelacionadas = request
				.getParameterValues("serieSeleccionadaRelacionadaEliminar");
		// String[] idsSeriesDescendientes = request
		// .getParameterValues("serieSeleccionadaDescendienteEliminar");

		String[] idsSeriesAEliminar = null;

		if (idsSeriesPrecedentes != null && idsSeriesPrecedentes.length > 0) {
			series = valoracion.getListaSeriesPrecedentes();
			idsSeriesAEliminar = idsSeriesPrecedentes;
		} else if (idsSeriesRelacionadas != null
				&& idsSeriesRelacionadas.length > 0) {
			series = valoracion.getListaSeriesRelacionadas();
			idsSeriesAEliminar = idsSeriesRelacionadas;
		}
		// else if (idsSeriesDescendientes != null
		// && idsSeriesDescendientes.length > 0)
		// {
		// series = valoracion.getListaSeriesDescendientes();
		// idsSeriesAEliminar = idsSeriesDescendientes;
		// }

		if (idsSeriesAEliminar != null && idsSeriesAEliminar.length > 0) {
			Arrays.sort(idsSeriesAEliminar);
			final String[] serieIDs = idsSeriesAEliminar;
			CollectionUtils.filter(series, new Predicate() {
				public boolean evaluate(Object obj) {
					return Arrays.binarySearch(serieIDs,
							((InfoSerie) obj).getId()) < 0;
				}
			});

			isModified = true;
		}

		String[] posSeriesRelacionadasSinFondo = request
				.getParameterValues("serieSinFondoSeleccionadaRelacionadaEliminar");

		if (!ArrayUtils.isEmpty(posSeriesRelacionadasSinFondo)) {
			series = valoracion.getListaSeriesRelacionadas();
			int pos;
			for (int i = posSeriesRelacionadasSinFondo.length - 1; i >= 0; i--) {
				pos = TypeConverter.toInt(posSeriesRelacionadasSinFondo[i], -1);
				if (pos > -1) {
					int cont = 0;
					for (int j = 0; j < series.size(); j++) {
						InfoSerie info = (InfoSerie) series.get(j);
						if (StringUtils.isBlank(info.getId())) {
							cont++;
							if (cont == pos) {
								series.remove(j);
								break;
							}
						}
					}
				}
			}

			isModified = true;
		}

		try {
			if (isModified) {
				valoracion.setSeriesPrecedentes(XMLToSerie.getInstance()
						.transform(valoracion.getListaSeriesPrecedentes()));
				// valoracion.setSeriesDescendientes(XMLToSerie.getInstance().transform(
				// valoracion.getListaSeriesDescendientes()));
				valoracion.setSeriesRelacionadas(XMLToSerie.getInstance()
						.transform(valoracion.getListaSeriesRelacionadas()));

				getGestionValoracionBI(request)
						.actualizarValoracion(valoracion);
			}
		} catch (ValoracionActionNotAllowedException vanae) {
			logger.error("Error al eliminar series", vanae);
		}

		// Establecemos los elementos del formulario
		this.establecerElementosFormulario(request);

		setReturnActionFordward(request,
				mappings.findForward("info_valoracion"));
	}

	/**
	 * Establece todos los elementos necesarios para mostrar en la vista del
	 * formulario
	 * 
	 * @param request
	 */
	private void establecerElementosFormulario(HttpServletRequest request) {
		// Obtenemos el servicio de acceso a fondos
		// GestionFondosBI fondosService = getGestionFondosBI(request);
		GestionValoracionBI valoracionService = getGestionValoracionBI(request);

		// Obtenemos los fondos existentes
		// int[] estados = { IElementoCuadroClasificacion.VIGENTE };
		// setInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY,
		// fondosService.getFondosXEstados(estados));
		// Metemos los metodos de ordenacion para el primer y segundo nivel
		request.setAttribute(
				ValoracionConstants.LISTA_ORDENACIONES_PRIMER_NIVEL_KEY,
				valoracionService
						.getTiposOrdenacion(ValoracionConstants.NIVEL_SERIE_PRIMERO));
		request.setAttribute(
				ValoracionConstants.LISTA_ORDENACIONES_SEGUNDO_NIVEL_KEY,
				valoracionService
						.getTiposOrdenacion(ValoracionConstants.NIVEL_SERIE_SEGUNDO));
		MessageResources messages = getMessageResources(request);
		// Metemos los valores administrativos
		String[] valoresAdministrativos = {
				String.valueOf(ValoracionSerieVO.VALOR_ADMINISTRATIVO_TEMPORAL),
				String.valueOf(ValoracionSerieVO.VALOR_ADMINISTRATIVO_PERMANENTE) };
		List listaValoresAdministrativos = new ArrayList();
		for (int i = 0; i < valoresAdministrativos.length; i++)
			listaValoresAdministrativos
					.add(new PropertyBean(
							valoresAdministrativos[i],
							messages.getMessage(ValoracionConstants.PREFIX_KEY_VALORES_ADMINISTRATIVOS
									+ valoresAdministrativos[i])));
		// request.setAttribute(
		// ValoracionConstants.LISTA_VALORES_ADMINISTRATIVOS_KEY,
		// valoracionService.getTiposValor(ValoracionConstants.VALOR_ADMINISTRATIVO));
		request.setAttribute(
				ValoracionConstants.LISTA_VALORES_ADMINISTRATIVOS_KEY,
				listaValoresAdministrativos);
		// Metemos los valores legales
		String[] valoresLegales = {
				String.valueOf(ValoracionSerieVO.VALOR_LEGAL_TEMPORAL),
				String.valueOf(ValoracionSerieVO.VALOR_LEGAL_PERMANENTE) };
		List listaValoresLegales = new ArrayList();
		for (int i = 0; i < valoresLegales.length; i++)
			listaValoresLegales
					.add(new PropertyBean(
							valoresLegales[i],
							messages.getMessage(ValoracionConstants.PREFIX_KEY_VALORES_LEGALES
									+ valoresLegales[i])));
		// request.setAttribute(
		// ValoracionConstants.LISTA_VALORES_LEGALES_KEY,
		// valoracionService.getTiposValor(ValoracionConstants.VALOR_LEGAL));
		request.setAttribute(ValoracionConstants.LISTA_VALORES_LEGALES_KEY,
				listaValoresLegales);
		// Metemos los valores informativos
		// request.setAttribute(
		// ValoracionConstants.LISTA_VALORES_INFORMATIVOS_KEY,
		// valoracionService.getTiposValor(ValoracionConstants.VALOR_INFORMATIVO));
		// //Metemos los valores historicos
		// request.setAttribute(
		// ValoracionConstants.LISTA_VALORES_HISTORICOS_KEY,
		// valoracionService.getTiposValor(ValoracionConstants.VALOR_HISTORICO));
		// Metemos las técnicas de muestreo
		request.setAttribute(ValoracionConstants.LISTA_TECNICAS_MUESTREO_KEY,
				valoracionService.getTecnicasMuestreo());
		// Metemos los valores del dictamen
		request.setAttribute(ValoracionConstants.LISTA_VALORES_DICTAMEN_KEY,
				valoracionService.getValoresDictamen());
	}

	/* Elimina la valoracion que esta siendo visualizada */
	public void eliminarValoracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		ValoracionForm valoracionForm = (ValoracionForm) form;
		try {
			String[] valoracionID = { valoracionForm.getId() };
			valoracionBI.eliminarValoracion(valoracionID);
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/*
	 * Elimina un conjunto de las valoraciones que un usuario tiene en
	 * elaboracion
	 */
	public void eliminarValoracionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		ValoracionForm valoracionForm = (ValoracionForm) form;
		try {
			valoracionBI.eliminarValoracion(valoracionForm
					.getValoracionSeleccionada());
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/*
	 * Presenta la pantalla que permite realizar la solicitud de validacion de
	 * una valoracion
	 */
	public void solicitarValidacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_SOCITAR_VALIDACION, request);
		if (appUser.hasPermission(AppPermissions.GESTION_VALORACIONES))
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		else
			procesarSolicitudValidacionExecuteLogic(mappings, form, request,
					response);
	}

	/* Procesa una solicitud de validacion de una valoracion */
	public void procesarSolicitudValidacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		ValoracionForm valoracionForm = (ValoracionForm) form;
		try {
			if (valoracionForm.getAutorizarSolicitudValidacionAutomaticamente())
				valoracionBI.autorizarValidacionValoracion(valoracionForm
						.getId());
			else
				valoracionBI.solicitarValidacionValoracion(valoracionForm
						.getId());
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/* Autorización de una solicitud de validacion de una valoracion */
	public void autorizarValidacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		ValoracionForm valoracionForm = (ValoracionForm) form;
		try {
			valoracionBI.autorizarValidacionValoracion(valoracionForm.getId());
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/*
	 * Presenta la pantalla que permite rechazar una solicitud de validacion de
	 * una valoracion
	 */
	public void recogerDatosRechazoValidacionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_RECHAZAR_VALIDACION, request);
		setReturnActionFordward(request, mappings.findForward("cambio_estado"));
	}

	/* Rechazo de una solicitud de validacion de una valoracion */
	public void rechazarValidacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		GestionSeriesBI seriesBI = getGestionSeriesBI(request);
		ValoracionForm valoracionForm = (ValoracionForm) form;
		try {
			ValoracionSeriePO valoracionSeriePO = (ValoracionSeriePO) getFromTemporalSession(
					request, ValoracionConstants.VALORACION_KEY);

			SerieVO serieVO = seriesBI.getSerie(valoracionSeriePO.getIdSerie());

			valoracionBI.rechazarValidacionValoracion(valoracionForm.getId(),
					valoracionForm.getMotivoRechazo(), serieVO);

			/*
			 * valoracionBI.rechazarValidacionValoracion(valoracionForm.getId(),
			 * valoracionForm.getMotivoRechazo());
			 */
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		}
	}

	/*
	 * Presenta la pantalla que permite dar por rechazada la evaluacion de una
	 * valoracion
	 */
	public void recogerDatosRechazoEvaluacionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_RECHAZAR_EVALUACION, request);
		setReturnActionFordward(request, mappings.findForward("cambio_estado"));
	}

	/*
	 * Presenta la pantalla que permite dar por aceptada la evaluación de una
	 * valoracion
	 */
	public void recogerDatosAutorizacionEvaluacionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_AUTORIZAR_EVALUACION, request);
		setReturnActionFordward(request, mappings.findForward("cambio_estado"));
	}

	/* Evaluacion valoracion autorizada */
	public void autorizarEvaluacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		ValoracionForm valoracionForm = (ValoracionForm) form;
		try {
			Date fechaEvaluacion = null;
			if (!StringUtils.isEmpty(valoracionForm.getFechaEvaluacion())
					&& (fechaEvaluacion = DateUtils.getDate(valoracionForm
							.getFechaEvaluacion())) != null) {
				valoracionBI.autorizarEvaluacionValoracion(
						valoracionForm.getId(), fechaEvaluacion);
				goBackExecuteLogic(mappings, form, request, response);
			} else {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_DATE, Messages
								.getString(Constants.ETIQUETA_FECHA,
										request.getLocale())));
				setReturnActionFordward(request,
						mappings.findForward("cambio_estado"));
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		}
	}

	/* Evaluacion valoracion rechazada */
	public void rechazarEvaluacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		GestionSeriesBI seriesBI = getGestionSeriesBI(request);
		ValoracionForm valoracionForm = (ValoracionForm) form;
		Date fechaEvaluacion = null;
		if (!StringUtils.isEmpty(valoracionForm.getFechaEvaluacion())
				&& (fechaEvaluacion = DateUtils.getDate(valoracionForm
						.getFechaEvaluacion())) != null) {
			try {
				ValoracionSeriePO valoracionSeriePO = (ValoracionSeriePO) getFromTemporalSession(
						request, ValoracionConstants.VALORACION_KEY);

				SerieVO serieVO = seriesBI.getSerie(valoracionSeriePO
						.getIdSerie());

				valoracionBI.rechazarEvaluacionValoracion(
						valoracionForm.getId(),
						valoracionForm.getMotivoRechazo(), fechaEvaluacion,
						serieVO);

				/*
				 * valoracionBI.rechazarEvaluacionValoracion(valoracionForm
				 * .getId(), valoracionForm.getMotivoRechazo(),
				 * fechaEvaluacion);
				 */
				goBackExecuteLogic(mappings, form, request, response);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("cambio_estado"));
			}
		} else {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ERROR_DATE,
									Messages.getString(
											ValoracionConstants.LABEL_VALORACIONES_FECHA_EVALUACION,
											request.getLocale())));
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		}
	}

	/**
	 * Presenta la pantalla que permite recoger los datos del dictamen.
	 */
	public void recogerDatosDictamenExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.VALORACION_DICTAMEN,
				request);

		// Almacenar la lista de boletines oficiales
		removeInTemporalSession(request,
				ValoracionConstants.LISTA_BOLETINES_OFICIALES_KEY);
		setInTemporalSession(request,
				ValoracionConstants.LISTA_BOLETINES_OFICIALES_KEY,
				getGestionValoracionBI(request).getBoletinesOficiales());

		setReturnActionFordward(request, mappings.findForward("cambio_estado"));
	}

	/* Dictamen valoracion autorizado */
	public void autorizarDictamenExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		ValoracionForm valoracionForm = (ValoracionForm) form;
		try {
			ActionErrors errors = valoracionForm.validateAutorizacionDictamen(
					mappings, request);
			if (errors.isEmpty()) {
				BoletinInfoVO infoBoletin = new BoletinInfoVO();
				infoBoletin.setFechaDictamen(DateUtils.getDate(valoracionForm
						.getFechaDictamen()));
				infoBoletin.setFechaBoletinDictamen(DateUtils
						.getDate(valoracionForm.getFechaBoletinDictamen()));
				infoBoletin.setFechaResolucionDictamen(DateUtils
						.getDate(valoracionForm.getFechaResolucionDictamen()));
				infoBoletin.setBoletinDictamen(valoracionForm
						.getBoletinDictamen());

				valoracionBI.autorizarDictamenValoracion(
						valoracionForm.getId(), infoBoletin);
				goBackExecuteLogic(mappings, form, request, response);
			} else {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("cambio_estado"));
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		}
	}

	/* Cerrar dictamen favorable */
	public void cerrarDictamenFavorableExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		GestionSeriesBI seriesBI = getGestionSeriesBI(request);

		ValoracionForm valoracionForm = (ValoracionForm) form;
		try {
			/*
			 * valoracionBI.cerrarDictamenFavorableValoracion(
			 * valoracionForm.getId());
			 */
			/*
			 * ValoracionSeriePO valoracion = (ValoracionSeriePO)
			 * getFromTemporalSession(request,
			 * ValoracionConstants.VALORACION_KEY);
			 */

			/*
			 * SeriePO serie = valoracion.getSerie(); IdentificacionSerie
			 * identificacion = valoracion.getSerieExtendida();
			 * InfoBProcedimiento procedimiento =
			 * identificacion.getProcedimiento(); List productores =
			 * identificacion.getProductoresSerie(); List seriesPrecedentes =
			 * valoracion.getListaSeriesPrecedentes(); List seriesRelacionadas =
			 * valoracion.getListaSeriesRelacionadas();
			 */

			ValoracionSeriePO valoracionSeriePO = (ValoracionSeriePO) getFromTemporalSession(
					request, ValoracionConstants.VALORACION_KEY);

			SerieVO serieVO = seriesBI.getSerie(valoracionSeriePO.getIdSerie());

			valoracionBI.cerrarDictamenFavorableValoracion(
					valoracionForm.getId(), serieVO);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/* Dictamen valoracion rechazado */
	public void rechazarDictamenExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionValoracionBI valoracionBI = getGestionValoracionBI(request);
		GestionSeriesBI seriesBI = getGestionSeriesBI(request);

		ValoracionForm valoracionForm = (ValoracionForm) form;

		try {
			ActionErrors errors = valoracionForm.validateRechazoDictamen(
					mappings, request);
			if (errors.isEmpty()) {
				/*
				 * valoracionBI.rechazarDictamenValoracion(
				 * valoracionForm.getId(), valoracionForm.getMotivoRechazo(),
				 * DateUtils.getDate(valoracionForm.getFechaDictamen()));
				 */

				ValoracionSeriePO valoracionSeriePO = (ValoracionSeriePO) getFromTemporalSession(
						request, ValoracionConstants.VALORACION_KEY);

				SerieVO serieVO = seriesBI.getSerie(valoracionSeriePO
						.getIdSerie());

				valoracionBI.rechazarDictamenValoracion(valoracionForm.getId(),
						valoracionForm.getMotivoRechazo(),
						DateUtils.getDate(valoracionForm.getFechaDictamen()),
						serieVO);

				goBackExecuteLogic(mappings, form, request, response);
			} else {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("cambio_estado"));
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		}
	}

	public void homeValoracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();

		List valoracionesEnElaboracion = valoracionBI
				.getValoracionesEnElaboracion(appUser.getId());

		List valoracionesAGestionar = valoracionBI
				.getValoracionesAGestionar(appUser.getId());

		if (appUser.hasPermission(AppPermissions.GESTOR_SERIE)) {
			valoracionesEnElaboracion = (List) CollectionUtils.subtract(
					valoracionesEnElaboracion, valoracionesAGestionar);

			if (valoracionesEnElaboracion.size() > 5)
				valoracionesEnElaboracion = valoracionesEnElaboracion.subList(
						0, 5);

			ValoracionToPO transformerToPO = ValoracionToPO
					.getInstance(services);
			CollectionUtils.transform(valoracionesEnElaboracion,
					transformerToPO);
			request.setAttribute(
					ValoracionConstants.LISTA_VALORACIONES_EN_ELABORACION_KEY,
					valoracionesEnElaboracion);
		}

		if (appUser.hasPermission(AppPermissions.GESTION_VALORACIONES)) {
			if (valoracionesAGestionar.size() > 5)
				valoracionesAGestionar = valoracionesAGestionar.subList(0, 5);

			request.setAttribute(
					ValoracionConstants.LISTA_VALORACIONES_A_GESTIONAR_KEY,
					valoracionesAGestionar);
		}

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SERIES_EN_VALORACION, request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mappings.findForward("home_valoracion_series"));
	}

}