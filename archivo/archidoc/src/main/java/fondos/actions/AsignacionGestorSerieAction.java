package fondos.actions;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;
import util.TreeNode;
import util.TreeView;

import common.Constants;
import common.actions.BaseAction;
import common.actions.NodeSelectionHandlerAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;

import fondos.FondosConstants;
import fondos.model.IElementoCuadroClasificacion;
import fondos.view.SeriePO;
import fondos.vos.FondoVO;
import fondos.vos.SerieVO;
import gcontrol.view.TransformerUsuariosVOToUsuariosPO;
import gcontrol.view.UsuarioPO;
import gcontrol.vos.UsuarioVO;

/**
 * Lleva a cabo los difentes pasos a realizar para completar el cambio del
 * gestor asignado a una serie a medida que estos son solicitados por el usuario
 * - Visualización de la pantalla de seleccion de las series cuyo gestor se
 * desea modificar - Busqueda en el sistema de un conjunto de series entre las
 * que poder elegir aquellas cuyo gestor se quiere modificar - Obtencion de las
 * series de un determinado gestor en caso de lo que se dese es transferir el
 * control de las series asigandas a un gestor a otro - Visualizacion de las
 * series elegidas entre las obtenidas por alguno de los métodos anteriores
 * (busqueda o series asignadas a un gestor) y el conjunto de usuarios entre los
 * que es posible seleccionar un nuevo gestor para las series - Modificar el
 * gestor asignado a un conjunto de series
 */
public class AsignacionGestorSerieAction extends BaseAction implements
		NodeSelectionHandlerAction {

	public void verBuscadorSeriesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.CUADRO_VER_BUSCADOR,
				request);
		ServiceRepository services = getServiceRepository(request);
		GestionFondosBI fondoBI = services.lookupGestionFondosBI();

		int[] estados = { IElementoCuadroClasificacion.VIGENTE };
		setInTemporalSession(request, FondosConstants.LISTA_FONDOS_KEY,
				fondoBI.getFondosXEstados(estados));

		setInTemporalSession(request, FondosConstants.LISTA_GESTORES_SERIE_KEY,
				getGestoresDeSeries(request));

		setReturnActionFordward(request,
				mappings.findForward("seleccion_series"));

	}

	// TODO REPASAR SI SON TODOS LOS GESTORES DE SERIES LOS Q AQUI SE SACAN
	List getGestoresDeSeries(HttpServletRequest request) {
		ServiceRepository services = getServiceRepository(request);
		GestionControlUsuariosBI usuariosService = services
				.lookupGestionControlUsuariosBI();
		List gestores = usuariosService.getGestoresSerie();
		if (!util.CollectionUtils.isEmpty(gestores)) {
			CollectionUtils.transform(gestores,
					new TransformerUsuariosVOToUsuariosPO(services));
		}
		return gestores;
	}

	public class SerieAAsignarPO extends SeriePO {
		ServiceRepository services = null;
		GestionFondosBI fondoBI = null;
		FondoVO fondo = null;

		SerieAAsignarPO(SerieVO serie, ServiceRepository services) {
			super(serie, services);
			this.services = services;
			this.fondoBI = services.lookupGestionFondosBI();
		}

		public FondoVO getFondo() {
			if (fondo == null)
				fondo = fondoBI.getFondoXId(getIdFondo());
			return fondo;
		}
	}

	private class SerieVO2PO implements Transformer {
		ServiceRepository services = null;

		SerieVO2PO(HttpServletRequest request) {
			services = getServiceRepository(request);
		}

		public Object transform(Object arg0) {
			return new SerieAAsignarPO((SerieVO) arg0, services);
		}

	};

	public void buscarSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = validateInBuscar(request);
		if (errors == null) {

			String pFondoID = request.getParameter("fondo");
			String pCodigo = request.getParameter("codigo");
			String pTitulo = request.getParameter("titulo");

			saveCurrentInvocation(KeysClientsInvocations.CUADRO_VER_BUSCADOR,
					request);

			GestionSeriesBI serieBI = getGestionSeriesBI(request);

			List series = serieBI.findSeries(pFondoID, pCodigo, pTitulo);
			CollectionUtils.transform(series, new SerieVO2PO(request));

			request.setAttribute(FondosConstants.SERIE_KEY, series);
		} else
			ErrorsTag.saveErrors(request, errors);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_series"));
	}

	private ActionErrors validateInBuscar(HttpServletRequest request) {
		String pCodigo = request.getParameter("codigo");
		String pTitulo = request.getParameter("titulo");
		String pFondoID = request.getParameter("fondo");

		// Se ha introducido este código para evitar que el usuario busque por
		// el %
		pCodigo = StringUtils.replace(pCodigo, Constants.COMODIN_BUSQUEDAS,
				Constants.STRING_EMPTY);
		pTitulo = StringUtils.replace(pTitulo, Constants.COMODIN_BUSQUEDAS,
				Constants.STRING_EMPTY);

		if (GenericValidator.isBlankOrNull(pFondoID)
				&& GenericValidator.isBlankOrNull(pCodigo)
				&& GenericValidator.isBlankOrNull(pTitulo)) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					FondosConstants.INTRODUZCA_ALGUN_CRITERIO_DE_BUSQUEDA,
					new ActionError(
							FondosConstants.INTRODUZCA_ALGUN_CRITERIO_DE_BUSQUEDA));
			return errors;
		}
		return null;
	}

	public void seriesGestorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pGestor = request.getParameter("gestor");

		saveCurrentInvocation(KeysClientsInvocations.CUADRO_VER_BUSCADOR,
				request);

		GestionSeriesBI serieBI = getGestionSeriesBI(request);
		List series = serieBI.findSeriesByGestor(pGestor);
		CollectionUtils.transform(series, new SerieVO2PO(request));

		request.setAttribute(FondosConstants.SERIE_KEY, series);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_series"));
	}

	public void seleccionarSeriesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionErrors errors = validateInSeleccionarSeries(request);
		if (errors == null) {
			String[] seriesSeleccionadas = request
					.getParameterValues("serieSeleccionada");

			GestionSeriesBI serieBI = getGestionSeriesBI(request);
			List series = serieBI.getSeries(seriesSeleccionadas);
			CollectionUtils.transform(series, new SerieVO2PO(request));

			setInTemporalSession(request, "serieSeleccionada",
					seriesSeleccionadas);

			request.setAttribute(FondosConstants.SERIE_KEY, series);

			request.setAttribute(FondosConstants.LISTA_GESTORES_SERIE_KEY,
					getGestoresDeSeries(request));

			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_SELECCIONAR_NUEVO_GESTOR,
					request);

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor"));

		} else {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

	}

	private ActionErrors validateInSeleccionarSeries(HttpServletRequest request) {
		String[] seriesSeleccionadas = request
				.getParameterValues("serieSeleccionada");
		if (seriesSeleccionadas == null || seriesSeleccionadas.length == 0) {
			ActionErrors errors = new ActionErrors();
			errors.add(FondosConstants.NECESARIO_SELECCIONAR_UNA_SERIE,
					new ActionError(
							FondosConstants.NECESARIO_SELECCIONAR_UNA_SERIE));
			return errors;
		}
		return null;
	}

	public void asignarGestorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = getGestionSeriesBI(request);
		String idGestor = request.getParameter("idGestor");
		String[] serieIDs = (String[]) getFromTemporalSession(request,
				"serieSeleccionada");
		try {

			serieBI.asignarGestor(idGestor, serieIDs);

			List series = serieBI.getSeries(serieIDs);
			request.setAttribute(FondosConstants.SERIE_KEY, series);

			// TODO REPASAR SI SON TODOS LOS GESTORES DE SERIES LOS Q AQUI SE
			// SACAN
			GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);
			UsuarioVO userGestor = usuariosService.getUsuario(idGestor);

			request.setAttribute(FondosConstants.GESTOR_SERIES_KEY,
					new UsuarioPO(userGestor, services));

			setReturnActionFordward(request,
					mappings.findForward("resultado_asignacion"));
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/*************************************************************************************/

	public void verTiposAsignacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_VER_TIPOS_ASIGNACION, request);
		setReturnActionFordward(request,
				mappings.findForward("seleccion_tipo_asignacion"));
	}

	public void seleccionTipoAsignacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pTipoAsignacion = request.getParameter("tipoAsignacion");
		ClientInvocation verTipos = getInvocationStack(request)
				.getLastClientInvocation();
		verTipos.addParameter("tipoAsignacion", pTipoAsignacion);
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_SELECCION_TIPO_ASIGNACION,
				request);
		int tipoAsignacion = Integer.parseInt(pTipoAsignacion);
		if (tipoAsignacion == 0) {

			GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);

			TreeView treeView = new CuadroClasificacionTreeView(
					bi.getCuadroClasificacion());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request,
					FondosConstants.CFVIEW_PARA_SELECCION_SERIE, treeView);
			setReturnActionFordward(request,
					mappings.findForward("seleccion_serie"));
		} else {
			/* AppUserRI usersRI = */getAppUserRI(request);
			// TODO REPASAR SI SON TODOS LOS GESTORES DE SERIES LOS Q AQUI SE
			// SACAN
			request.setAttribute(FondosConstants.LISTA_GESTORES_SERIE_KEY,
					getGestoresDeSeries(request));

			setReturnActionFordward(request,
					mappings.findForward("seleccion_gestor"));
		}
	}

	public void verNodoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request,
				verNodo(mappings, form, request, response));
	}

	public ActionForward verNodo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				FondosConstants.CFVIEW_PARA_SELECCION_SERIE);
		if (treeView == null)
			return null;
		TreeNode node = treeView.getNode(request.getParameter("node"));
		treeView.setSelectedNode(node);
		ClientInvocation seleccionSerie = getInvocationStack(request)
				.getLastClientInvocation();
		seleccionSerie
				.addParameter("pathSerieSeleccionada", node.getNodePath());

		/*
		 * NavigationContextStack navContext =
		 * NavigationContextStack.getInstance(request.getSession());
		 * navContext.setOnCancelURI(mappings.getPath(),
		 * RequestUtil.getParameterString(request.getParameterMap()));
		 */
		GestionSeriesBI serieBI = getGestionSeriesBI(request);
		request.setAttribute(FondosConstants.SERIE_KEY,
				serieBI.getSerie((String) node.getNodeId()));
		return mappings.findForward("datos_serie");
	}

	public void seleccionSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.CUADRO_SELECCION_SERIE,
				request);
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				FondosConstants.CFVIEW_PARA_SELECCION_SERIE);
		TreeNode node = treeView.getSelectedNode();
		GestionSeriesBI serieBI = getGestionSeriesBI(request);
		setInTemporalSession(request, FondosConstants.SERIE_KEY,
				Collections.singletonList(serieBI.getSerie((String) node
						.getNodeId())));

		request.setAttribute(FondosConstants.LISTA_GESTORES_SERIE_KEY,
				getGestoresDeSeries(request));

		setReturnActionFordward(request,
				mappings.findForward("seleccion_gestor_a_asignar"));
	}

	public void seleccionGestorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String idGestor = request.getParameter("idGestor");
		ClientInvocation verSeleccionGestor = getInvocationStack(request)
				.getLastClientInvocation();
		verSeleccionGestor.addParameter("idGestor", idGestor);
		saveCurrentInvocation(KeysClientsInvocations.CUADRO_SELECCION_GESTOR,
				request);
		GestionSeriesBI serieBI = getGestionSeriesBI(request);
		List series = serieBI.findSeriesByGestor(idGestor);
		;
		// request.getSession().setAttribute(FondosConstants.SERIE_KEY, series);
		setInTemporalSession(request, FondosConstants.SERIE_KEY, series);
		/* AppUserRI usersRI = */getAppUserRI(request);
		request.setAttribute(FondosConstants.LISTA_GESTORES_SERIE_KEY,
				getGestoresDeSeries(request));

		setReturnActionFordward(request,
				mappings.findForward("seleccion_gestor_a_asignar"));
	}

	public void seleccionGestorAAsignarExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		/* GestionSeriesBI serieBI = */getGestionSeriesBI(request);
		// String idGestor = request.getParameter("idGestor");
		/* AppUserRI usersRI = */getAppUserRI(request);

		List series = (List) getFromTemporalSession(request,
				FondosConstants.SERIE_KEY);
		final String[] serieIds = new String[series.size()];
		CollectionUtils.forAllDo(series, new Closure() {
			int count = 0;

			public void execute(Object arg0) {
				serieIds[count++] = ((SerieVO) arg0).getId();
			}
		});
		setReturnActionFordward(request,
				mappings.findForward("resultado_asignacion_gestor"));
	}

	public String getHandlerPath() {
		return "/action/gestionAsignacionSerie?method=verNodo";
	}
}