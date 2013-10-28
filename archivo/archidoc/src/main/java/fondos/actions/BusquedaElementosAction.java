package fondos.actions;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import util.CollectionUtils;
import util.ErrorsTag;
import util.PaginatedList;
import util.TreeView;
import xml.config.Busqueda;
import xml.config.ConfiguracionArchivoManager;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BusquedaBaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.ColumnNotIndexedException;
import common.exceptions.SintaxErrorException;
import common.exceptions.TooManyResultsException;
import common.exceptions.WordOmittedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.DisplayTagUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.view.IVisitedRowVO;

import deposito.DepositoConstants;
import descripcion.DescripcionConstants;
import descripcion.model.TipoNiveles;
import descripcion.vos.ElementoCFPO;
import descripcion.vos.ElementoCFVO;
import export.ExcelReport;
import fondos.FondosConstants;
import fondos.forms.BusquedaElementosForm;
import fondos.model.CamposBusquedas;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.NivelCF;
import fondos.model.PrecondicionesBusquedaFondosGenerica;
import fondos.model.TipoNivelCF;
import fondos.utils.BusquedasHelper;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Action para las busquedas de los elementos del cuadro.
 */
public class BusquedaElementosAction extends BusquedaBaseAction {
	/** Logger de la clase */
	private final static Logger logger = Logger
			.getLogger(BusquedaElementosAction.class);
	/*
	 * private static final String
	 * ERROR_LISTA_VACIA="archigest.archivo.descripcion.busqueda.vacia"; private
	 * static final String ERROR_TOO_MANY_RESULTS="error.too_many_results";
	 */
	private static final String ID_DISPLAY_TAG = "elemento";

	private static final String FORWARD_LISTADO_UDOCS_SERIE = "form_busqueda_udocs_serie";

	private Busqueda getCfgBusquedaFondosRapida(HttpServletRequest request)
			throws Exception {
		String key = getKeyCfgBusquedas(request,
				ConfiguracionArchivoManager.FONDOS_RAPIDA);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;
	}

	private Busqueda getCfgBusquedaUdocsSerie(HttpServletRequest request)
			throws Exception {
		String key = ConfiguracionArchivoManager.UDOCS_SERIE;
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;
	}

	protected Busqueda getCfgBusquedaFondosAvanzada(HttpServletRequest request)
			throws Exception {
		String key = getKeyCfgBusquedas(request,
				ConfiguracionArchivoManager.FONDOS_AVANZADA);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;
	}

	/**
	 * Permite cargar la configuracion de la busqueda de fondos generica
	 *
	 * @param request
	 *            Peticion actual
	 * @param tipoBusqueda
	 *            Tipo de busqueda
	 * @return Busqueda configuracion de la busqueda
	 * @throws Exception
	 */
	protected Busqueda getCfgBusquedaFondosGenerica(HttpServletRequest request,
			String tipoBusqueda) throws Exception {
		String key = getKeyCfgBusquedas(request, tipoBusqueda);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;
	}

	/**
	 * Muestra el formulario para realizar busquedas rapidas.
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void formBusquedaRapidaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Guardar el enlace a la pagina
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_FORM_BUSQUEDA_RAPIDA_ELEMENTOS,
				request);

		// Inicializar los valores de sesion
		initializeSessionValues(request);

		// Establecer la configuracion de la busqueda particular
		removeInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY);
		Busqueda busqueda;
		try {
			busqueda = getCfgBusquedaFondosRapida(request);
			setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
					busqueda);
			BusquedasHelper.loadListasBusqueda(busqueda,
					(BusquedaElementosForm) form, request, null);

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("form_busqueda_rapida"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mapping, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Muestra el formulario para realizar busquedas rapidas.
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void formBusquedaUDocsSerieExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ClientInvocation invocation= saveCurrentTreeViewInvocationAndRefresh(
				KeysClientsInvocations.FILTRO_UDOCS_SERIE, request,
				FondosConstants.CUADRO_CLF_VIEW_NAME);
		invocation.setAsReturnPoint(true);

		String idSerie = request.getParameter("idSerie");

		// Inicializar los valores de sesion
		initializeSessionValues(request);

		// Establecer la configuracion de la busqueda particular
		removeInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_SERIE_KEY);
		Busqueda busqueda;
		try {
			busqueda = getCfgBusquedaUdocsSerie(request);

			// Si aparece el campo de entrada de busqueda avanzada, limitamos la
			// lista de fichas a las de nivel documental
			PrecondicionesBusquedaFondosGenerica precondiciones = new PrecondicionesBusquedaFondosGenerica();
			precondiciones
					.setTiposNivelFicha(new int[] { TipoNiveles.UNIDAD_DOCUMENTAL_VALUE });
			precondiciones.setIdRefObjetoAmbito(new String[] { idSerie });
			precondiciones.setTipoObjetoAmbito(new String[] { TipoNivelCF.SERIE
					.getIdentificadorAsString() });
			precondiciones.setForwardListado(FORWARD_LISTADO_UDOCS_SERIE);
			precondiciones.setForwardRetorno(FORWARD_LISTADO_UDOCS_SERIE);
			int numMaxResultados = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionFondos()
					.getNumMaxResultadosSerie();
			if (numMaxResultados > 0) {
				precondiciones.setNumMaxResultados(numMaxResultados);
			} else {
				request.setAttribute(
						DepositoConstants.LIMITAR_RESULTADOS_BUSQUEDA,
						new Boolean(true));
				precondiciones.setNumMaxResultados(Integer.MAX_VALUE);
			}
			precondiciones
					.setEntradaParaMigaPan(KeysClientsInvocations.RESULTADO_UDOCS_SERIE);
			precondiciones
					.setKeyCfgBusqueda(FondosConstants.CFG_BUSQUEDA_SERIE_KEY);

			setInTemporalSession(request,
					FondosConstants.PRECONDICIONES_BUSQUEDA_KEY, precondiciones);

			setInTemporalSession(request,
					FondosConstants.CFG_BUSQUEDA_SERIE_KEY, busqueda);
			BusquedasHelper.loadListasBusqueda(busqueda,
					(BusquedaElementosForm) form, request, null);

			// Obtenemos los productores de las unidades documentales de la
			// serie
			setInTemporalSession(
					request,
					FondosConstants.LISTA_PRODUCTORES_KEY,
					getGestionSeriesBI(request).getProductoresUdocsSerie(
							idSerie));

			// buscarCodeLogic(mapping, form, request, response);

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("form_busqueda_udocs_serie"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mapping, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Muestra el formulario para realizar busquedas rapidas.
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void formBusquedaAvanzadaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// Guardar el enlace a la pagina
			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_FORM_BUSQUEDA_AVANZADA_ELEMENTOS,
					request);

			// Inicializar los valores de sesion
			initializeSessionValues(request);

			// Establecer la configuracion de la busqueda particular
			removeInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY);
			Busqueda busqueda = getCfgBusquedaFondosAvanzada(request);
			setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
					busqueda);
			BusquedasHelper.loadListasBusqueda(busqueda,
					(BusquedaElementosForm) form, request, null);

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("form_busqueda_avanzada"));

		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mapping, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mapping, form, request, response);
		}
	}

	private PrecondicionesBusquedaFondosGenerica inicializarBusquedaGenerica(
			ActionForm form, HttpServletRequest request) throws Exception {
		// Inicializar los valores de sesion
		initializeSessionValues(request);

		// Obtener el tipo de busqueda
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);

		// Establecer la configuracion de la busqueda particular
		removeInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY);
		Busqueda busqueda = getCfgBusquedaFondosGenerica(request,
				precondiciones.getTipoBusqueda());
		setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
				busqueda);
		BusquedasHelper.loadListasBusqueda(busqueda,
				(BusquedaElementosForm) form, request, precondiciones);

		return precondiciones;
	}

	/**
	 * Muestra el formulario para realizar busquedas rapidas.
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void formBusquedaGenericaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			inicializarBusquedaGenerica(form, request);

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("form_busqueda_generica"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mapping, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Muestra el formulario para realizar busquedas rapidas.
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void formBusquedaGenericaSeleccionExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			inicializarBusquedaGenerica(form, request);

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("busqueda_seleccion"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mapping, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Muestra el formulario para realizar busquedas.
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
	protected void formBusquedaElementosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de formExecuteLogic");

		// Obtener la configuracion de la busqueda
		Busqueda busqueda = getCfgBusquedaBandejaElementos(request);
		PrecondicionesBusquedaFondosGenerica precondiciones = getPrecondicionesBusquedaElementos();

		precondiciones.setKeyCfgBusqueda(FondosConstants.CFG_BUSQUEDA_KEY);
		precondiciones.setKeyElementos(DescripcionConstants.ELEMENTOS_KEY);

		setInTemporalSession(request,
				FondosConstants.PRECONDICIONES_BUSQUEDA_KEY, precondiciones);

		setInTemporalSession(request, precondiciones.getKeyCfgBusqueda(),
				busqueda);

		// Recuperar la informacion del formulario
		BusquedaElementosForm busquedaElementosForm = (BusquedaElementosForm) form;

		// Cargar las listas de objetos
		BusquedasHelper.loadListasBusqueda(busqueda, busquedaElementosForm,
				request, precondiciones);

		// Retornar a la busqueda
		setReturnActionFordward(request,
				mapping.findForward("form_busqueda_elementos"));
	}

	protected void formBusquedaValoracionSeleccionUdocsExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			inicializarBusquedaGenerica(form, request);

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("busqueda_valoracion_seleccion_udocs"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mapping, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mapping, form, request, response);
		}
	}

	private void initializeSessionValues(HttpServletRequest request) {
		// Cuadro de clasificacion para la seleccion de ambitos
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				FondosConstants.CUADRO_CLF_SEL_VIEW_NAME);
		if (treeView == null) {
			treeView = new CuadroClasificacionTreeView(
					getGestionCuadroClasificacionBI(request)
							.getCuadroClasificacion());
			setInTemporalSession(request,
					FondosConstants.CUADRO_CLF_SEL_VIEW_NAME, treeView);
		}

		// Eliminar la lista de elementos en sesion
		removeInTemporalSession(request, FondosConstants.LISTA_ELEMENTOS_CF);
		removeInTemporalSession(request, FondosConstants.LISTA_IDS_ELEMENTOS_CF);
		removeInTemporalSession(request, Constants.LAST_ORDER);

		removeInTemporalSession(request, Constants.LAST_ORDER_DIRECTION);
		removeInTemporalSession(request, Constants.PAGE_NUMBER);

		// Elimina la marca de lista cacheada
		removeInTemporalSession(request, FondosConstants.LISTA_CACHEADA_KEY);

		// Crear un map de elementos visitados nuevo
		setInTemporalSession(request,
				FondosConstants.MAP_ELEMENTOS_CF_VISITADOS, new HashMap());
	}

	private int getPageNumber(HttpServletRequest request) {
		int pageNumber = 1;
		if (DisplayTagUtils.getPageNumber(request) != null) {
			pageNumber = Integer.parseInt(DisplayTagUtils
					.getPageNumber(request));
		} else if (getFromTemporalSession(request, Constants.PAGE_NUMBER) != null) {
			pageNumber = ((Integer) getFromTemporalSession(request,
					Constants.PAGE_NUMBER)).intValue();
		}
		return pageNumber;
	}

	private BusquedaElementosVO fillBusquedaElementosVO(
			HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda) {

		// busquedaElementosVO.setOrderColumn(getOrderColumnNumber(request).intValue());
		busquedaElementosVO.setOrderColumn(ElementoCFVO
				.getColumnNumberByName(getOrderColumn(request, busqueda)));
		busquedaElementosVO
				.setTipoOrdenacion(((SortOrderEnum) getOrderCurrentDirection(
						request, busqueda)).getName());

		return busquedaElementosVO;
	}

	protected List getIdsElementos(HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO,
			GestionCuadroClasificacionBI service, int numMaxResults,
			Busqueda busqueda) throws TooManyResultsException {
		List listaIdsElementos = null;

		if (DisplayTagUtils.isPaginating(request)) {
			listaIdsElementos = (List) getFromTemporalSession(request,
					FondosConstants.LISTA_IDS_ELEMENTOS_CF);
		} else {
			removeInTemporalSession(request,
					FondosConstants.LISTA_IDS_ELEMENTOS_CF);
			busquedaElementosVO = fillBusquedaElementosVO(request,
					busquedaElementosVO, busqueda);
			if (DisplayTagUtils.isSorting(request))
				listaIdsElementos = service.getIdsElementos(
						busquedaElementosVO, 0, busqueda);
			else
				listaIdsElementos = service.getIdsElementos(
						busquedaElementosVO, numMaxResults, busqueda);
		}
		return listaIdsElementos;
	}

	/**
	 * Muestra la lista de elementos del cuadro de clasificacion.
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

	protected void busquedaElementosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de busquedaElmentosExecuteLogic");

		// Obtener las precondiciones por si es una busqueda generica
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);

		BusquedaElementosForm formulario = (BusquedaElementosForm) form;
		formulario.setPostBack(Constants.TRUE_STRING);

		// Recuperar la informacion del formulario
		BusquedaElementosForm busquedaElementosForm = (BusquedaElementosForm) form;

		// Recuperar la informacion de la busqueda
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);

		// Validar el formulario
		ActionErrors errores = BusquedasHelper.validateCampos(mapping, request,
				busqueda, busquedaElementosForm);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Informacion de paginacion
			PageInfo pageInfo = new PageInfo(request, 10, "codReferencia");
			pageInfo.setDefautMaxNumItems();

			BusquedaElementosVO busquedaElementosVO = BusquedasHelper
					.getBusquedaElementosVO(busqueda, null,
							busquedaElementosForm);
			busquedaElementosVO.setPageInfo(pageInfo);

			busquedaElementosVO.setServiceClient(ServiceClient
					.create(getAppUser(request)));

			try {
				// busqueda de elementos del cuadro de clasificacion
				List listaElementos =

				getGestionDescripcionBI(request)
						.searchElementosCuadroClasificacion(busquedaElementosVO);

				request.setAttribute(DescripcionConstants.ELEMENTOS_KEY,
						listaElementos);

				setReturnActionFordward(request,
						mapping.findForward("ver_elementos"));
			} catch (TooManyResultsException e) {
				// Aniadir los errores al request
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
								new Object[] { new Integer(e.getCount()),
										new Integer(e.getMaxNumResults()) }));

				// Cargar las listas de objetos
				BusquedasHelper.loadListasBusqueda(busqueda,
						busquedaElementosForm, request, precondiciones);

				// Retornar a la busqueda
				setReturnActionFordward(request,
						mapping.findForward("form_busqueda_elementos"));

			}
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Aniadir los errores al request
			obtenerErrores(request, true).add(errores);
			// Cargar las listas de objetos
			BusquedasHelper.loadListasBusqueda(busqueda, busquedaElementosForm,
					request, precondiciones);

			// Retornar a la busqueda
			setReturnActionFordward(request,
					mapping.findForward("form_busqueda_elementos"));
		}
	}

	/**
	 * Muestra el lista de elementos.
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void buscarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		buscarCodeLogic(mapping, form, request, response);
	}

	/**
	 * Muestra el lista de elementos.
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void buscarCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de buscarExecuteLogic");

		removeInTemporalSession(request, Constants.SHOW_INFORME_BUSQUEDA_BUTTON);

		BusquedaElementosForm formulario = (BusquedaElementosForm) form;
		formulario.setPostBack(Constants.TRUE_STRING);

		final ServiceRepository servicios = getServiceRepository(request);

		// Obtener el parametro cuya existencia indica que se esta solicitando
		// la exportacion de los datos obtenidos como resultado de la busqueda
		boolean isExportAction = PageInfo.checkExportingList(request);

		// Obtener las precondiciones por si es una busqueda generica
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);

		// Mostrar la lista de sesion si se ha hecho un goBack
		Object obj = getFromTemporalSession(request,
				FondosConstants.LISTA_ELEMENTOS_CF);
		if (obj != null && !(obj instanceof PaginatedList)) {
			if ((precondiciones != null)
					&& !StringUtils.isEmpty(precondiciones
							.getEntradaParaMigaPan())) {
				String invocacion = precondiciones.getEntradaParaMigaPan();

				if (!DisplayTagUtils.isPaginating(request)
						&& !DisplayTagUtils.isSorting(request)) {
					saveCurrentInvocation(invocacion, request);
				}
			}
			return;
		}

		PaginatedList paginatedList = (PaginatedList) getFromTemporalSession(
				request, FondosConstants.LISTA_ELEMENTOS_CF);
		if (paginatedList != null
				&& !ListUtils.isEmpty(paginatedList.getList())
				&& StringUtils.isNotBlank((String) getFromTemporalSession(
						request, FondosConstants.LISTA_CACHEADA_KEY))) {

			String invocacion = KeysClientsInvocations.CUADRO_LISTADO_BUSQUEDA_ELEMENTOS;
			if ((precondiciones != null)
					&& !StringUtils.isEmpty(precondiciones
							.getEntradaParaMigaPan())) {
				invocacion = precondiciones.getEntradaParaMigaPan();
			}

			if (!DisplayTagUtils.isPaginating(request)
					&& !DisplayTagUtils.isSorting(request)) {
				saveCurrentInvocation(invocacion, request);
			}
			// A�adido para el caso de vuelta de una division de fraccion de
			// serie
			// currentInv.setAsReturnPoint(true);

			// Elimina la marca de lista cacheada
			removeInTemporalSession(request, FondosConstants.LISTA_CACHEADA_KEY);

			// Redireccionamos a la pagina adecuada
			if ((precondiciones != null)
					&& (StringUtils.isNotEmpty(precondiciones
							.getForwardListado()))) {
				setReturnActionFordward(request,
						mapping.findForward(precondiciones.getForwardListado()));
			} else {
				setReturnActionFordward(request, mapping.findForward("listado"));
			}
			return;
		}

		// Establecemos los niveles
		request.setAttribute(FondosConstants.LISTA_NIVELES_KEY,
				getGestionCuadroClasificacionBI(request).getNivelesCF());

		// Aniadir los parametros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaElementosForm) form).getMap());
		cli.setAsReturnPoint(true);

		// Obtenemos el servicio de elementos del cuadro
		final GestionCuadroClasificacionBI service = getGestionCuadroClasificacionBI(request);

		// Obtenemos el formulario de busqueda
		BusquedaElementosForm elementosForm = (BusquedaElementosForm) form;

		// Obtener la configuracion de la busqueda
		Busqueda busqueda = null;

		if (precondiciones != null
				&& StringUtils.isNotEmpty(precondiciones.getKeyCfgBusqueda())) {
			busqueda = (Busqueda) getFromTemporalSession(request,
					precondiciones.getKeyCfgBusqueda());
		} else {
			busqueda = (Busqueda) getFromTemporalSession(request,
					FondosConstants.CFG_BUSQUEDA_KEY);
		}

		if (busqueda == null) {
			// Aniadir los errores al request
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_GENERICO_BUSQUEDAS));
			logger.error("Error al Obtener la configuracion de la busqueda de la sesion(Key: "
					+ FondosConstants.CFG_BUSQUEDA_KEY);

			setReturnActionFordward(request,
					mapping.findForward(precondiciones.getForwardRetorno()));
		} else {
			// Si estamos ante una busqueda donde no hace falta rellenar los
			// niveles, es necesario establecer que se busque en todos los
			// niveles
			// excepto el 1
			if (!busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION)) {
				if (elementosForm.getNiveles() == null
						|| (elementosForm.getNiveles() != null && elementosForm
								.getNiveles().length == 0)) {
					// Si se hace una busqueda reducida, se limita la busqueda a
					// elementos mayor que el 1
					List niveles = getGestionCuadroClasificacionBI(request)
							.getNivelesCF();
					String[] idsNiveles = new String[niveles.size()];
					for (int i = 0; i < niveles.size(); i++)
						idsNiveles[i] = ((NivelCF) niveles.get(i)).getId();
					elementosForm.setNiveles(idsNiveles);
				}
			}

			// Si estamos ante una busqueda donde no hace falta rellenar el
			// estado es necesario establecer que se busque en elementos
			// vigentes
			if (!busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO)) {
				if (elementosForm.getEstados() == null
						|| (elementosForm.getEstados() != null && elementosForm
								.getEstados().length == 0))
					elementosForm.setEstados(new String[] { new Integer(
							IElementoCuadroClasificacion.VIGENTE).toString() });
			}

			if (!getServiceClient(request).hasPermission(
					AppPermissions.CREACION_CUADRO_CLASIFICACION))
				elementosForm.setEstados(new String[] { ""
						+ IElementoCuadroClasificacion.VIGENTE });

			// Validar el formulario
			ActionErrors errores = BusquedasHelper.validateCampos(mapping,
					request, busqueda, elementosForm);
			if ((errores == null) || errores.isEmpty()) {
				logger.info("Formulario validado");

				int numMaxResultados = 0;

				String invocacion = KeysClientsInvocations.CUADRO_LISTADO_BUSQUEDA_ELEMENTOS;
				if (precondiciones != null) {
					if (!StringUtils.isEmpty(precondiciones
							.getEntradaParaMigaPan())) {
						invocacion = precondiciones.getEntradaParaMigaPan();
					}

					numMaxResultados = precondiciones.getNumMaxResultados();
				}

				if (!DisplayTagUtils.isPaginating(request)
						&& !DisplayTagUtils.isSorting(request)) {
					saveCurrentInvocation(invocacion, request);
				}

				// Informacion de paginacion
				PageInfo pageInfo = new PageInfo(request, "codigo");
				pageInfo.setDefautMaxNumItems();
				elementosForm.setPageInfo(pageInfo);

				try {
					if (numMaxResultados == 0) {
						numMaxResultados = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionGeneral()
								.getNumMaxResultados();
					}
					Object limitarResultadosBusqueda = request
							.getAttribute(DepositoConstants.LIMITAR_RESULTADOS_BUSQUEDA);
					if (limitarResultadosBusqueda != null
							&& ((Boolean) limitarResultadosBusqueda)
									.booleanValue())
						numMaxResultados = Integer.MAX_VALUE;
					int numeroElmentosPorPagina = ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionGeneral()
							.getNumResultadosPorPagina();

					String keyListaElementos = FondosConstants.LISTA_ELEMENTOS_CF;
					if (precondiciones != null) {

						if (StringUtils.isNotEmpty(precondiciones
								.getKeyElementos())) {
							keyListaElementos = precondiciones
									.getKeyElementos();
						}

						if (precondiciones.getNumResultadosPorPagina() > 0) {
							numeroElmentosPorPagina = precondiciones
									.getNumResultadosPorPagina();
						}
					}
					removeInTemporalSession(request, keyListaElementos);

					BusquedaElementosVO busquedaElementosVO = BusquedasHelper
							.getBusquedaElementosVO(busqueda, precondiciones,
									elementosForm);
					final List listaIdsElementos = getIdsElementos(request,
							busquedaElementosVO, service, numMaxResultados,
							busqueda);
					SortOrderEnum currentOrderDirection = getOrderCurrentDirection(
							request, busqueda);
					/* Integer orderColumn=getOrderColumnNumber(request); */
					String orderColumn = getOrderColumn(request, busqueda);

					removeInTemporalSession(request,
							FondosConstants.LISTA_ELEMENTOS_CF);
					if (!ListUtils.isEmpty(listaIdsElementos)) {
						setInTemporalSession(request,
								FondosConstants.LISTA_IDS_ELEMENTOS_CF,
								listaIdsElementos);
						request.setAttribute(
								FondosConstants.VO_BUSQUEDA_FONDOS,
								busquedaElementosVO);
						int pageNumber = getPageNumber(request);

						// String[] idsToShow=DisplayTagUtils.getIds(pageNumber,
						// numeroElmentosPorPagina, listaIdsElementos);
						// Comprobamos si es una exportacion, en cuyo caso, es
						// necesario obtener todos los objetos en lugar de solo
						// los de la pagina
						String[] idsToShow = null;
						if (isExportAction
								|| elementosForm.isMostrarSinPaginar())
							idsToShow = DisplayTagUtils.getIds(1,
									numMaxResultados, listaIdsElementos);
						else
							idsToShow = DisplayTagUtils.getIds(pageNumber,
									numeroElmentosPorPagina, listaIdsElementos);

						// List ltElements =
						// service.getElementos(idsToShow,fillBusquedaElementosVO(request,elementosForm.getBusquedaElementosVO()));
						// List ltElements =
						// service.getElementosBRF(idsToShow,fillBusquedaElementosVO(request,elementosForm.getBusquedaElementosVO()));
						List ltElements = service.getElementos(
								idsToShow,
								fillBusquedaElementosVO(request,
										busquedaElementosVO, busqueda),
								busqueda);

						// Transformar la lista de elementos en POS
						final Busqueda busquedaFinal = busqueda;
						CollectionUtils.transform(ltElements,
								new Transformer() {
									public Object transform(Object obj) {
										ElementoCFPO po = new ElementoCFPO(
												servicios, busquedaFinal,
												getProductores(busquedaFinal,
														listaIdsElementos,
														service),
												getInteresados(busquedaFinal,
														listaIdsElementos,
														service));
										try {
											PropertyUtils.copyProperties(po,
													obj);
										} catch (Exception e) {
											logger.error(e.getMessage());
										}
										return po;
									}
								});

						paginatedList = new PaginatedList();
						paginatedList.setFullListSize(listaIdsElementos.size());
						paginatedList.setList(ltElements);
						paginatedList
								.setObjectsPerPage(numeroElmentosPorPagina);
						paginatedList.setPageNumber(pageNumber);
						paginatedList.setSearchId(ID_DISPLAY_TAG);
						paginatedList.setSortCriterion(orderColumn.toString());
						paginatedList.setSortDirection(currentOrderDirection);

						if (!elementosForm.isMostrarSinPaginar()) {
							setInTemporalSession(request, keyListaElementos,
									paginatedList);
						} else {
							setInTemporalSession(request, keyListaElementos,
									ltElements);
						}

						// Restaurar los elementos visitados
						if (!CollectionUtils.isEmpty(ltElements)) {
							Map mapVisitedElements = (Map) getFromTemporalSession(
									request,
									FondosConstants.MAP_ELEMENTOS_CF_VISITADOS);
							if ((ltElements != null) && (!ltElements.isEmpty())) {
								ListIterator it = ltElements.listIterator();
								while (it.hasNext()) {
									// ElementoCuadroClasificacion element =
									// (ElementoCuadroClasificacion) it.next();
									ElementoCFPO element = (ElementoCFPO) it
											.next();
									if ((mapVisitedElements != null)
											&& (mapVisitedElements.get(element
													.getId()) != null)) {
										element.setRowStyle(IVisitedRowVO.CSS_FILA_CARGADA);
									}
								}
							}
						}

						setInTemporalSession(request, Constants.LAST_ORDER,
								orderColumn);
						setInTemporalSession(request,
								Constants.LAST_ORDER_DIRECTION,
								currentOrderDirection);
						setInTemporalSession(request, Constants.PAGE_NUMBER,
								new Integer(pageNumber));
					}
					// Redireccionamos a la pagina adecuada
					if ((precondiciones != null)
							&& (StringUtils.isNotEmpty(precondiciones
									.getForwardListado()))) {
						setReturnActionFordward(request,
								mapping.findForward(precondiciones
										.getForwardListado()));
					} else {
						setReturnActionFordward(request,
								mapping.findForward("listado"));
					}
				} catch (TooManyResultsException e) {
					// Aniadir los errores al request
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_TOO_MANY_RESULTS,
											new Object[] {
													new Integer(e.getCount()),
													new Integer(e
															.getMaxNumResults()) }));
					getInvocationStack(request)
					.getClientInvocation(
							getInvocationStack(request).getSize() - 2);

					setInTemporalSession(request, Constants.SHOW_INFORME_BUSQUEDA_BUTTON, Boolean.TRUE);

					// Redireccionamos a la pagina adecuada
					if ((precondiciones != null)
							&& (StringUtils.isNotEmpty(precondiciones
									.getForwardListado()))) {
						setReturnActionFordward(request,
								mapping.findForward(precondiciones
										.getForwardRetorno()));
					} else {
						goBackExecuteLogic(mapping, form, request, response);
					}
				} catch (ColumnNotIndexedException e) {
					// Aniadir los errores al request
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_COLUMNA_NO_INDEXADA));

					if ((precondiciones != null)
							&& (StringUtils.isNotEmpty(precondiciones
									.getForwardListado()))) {
						setReturnActionFordward(request,
								mapping.findForward(precondiciones
										.getForwardRetorno()));
					} else {
						goBackExecuteLogic(mapping, form, request, response);
					}
				} catch (WordOmittedException e) {
					// Aniadir los errores al request
					obtenerErrores(request, true).add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(Constants.ERROR_PALABRA_OMITIDA));

					logger.error("Error de Palabra Omitida", e);

					if ((precondiciones != null)
							&& (StringUtils.isNotEmpty(precondiciones
									.getForwardListado()))) {
						setReturnActionFordward(request,
								mapping.findForward(precondiciones
										.getForwardRetorno()));
					} else {
						goBackExecuteLogic(mapping, form, request, response);
					}
				} catch (SintaxErrorException e) {
					// Aniadir los errores al request
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_SINTAXIS_INCORRECTA));

					logger.error("Error En la sintaxis de la Consulta", e);

					if (precondiciones != null
							&& StringUtils.isNotEmpty(precondiciones
									.getForwardRetorno()))
						setReturnActionFordward(request,
								mapping.findForward(precondiciones
										.getForwardRetorno()));
					else
						goBackExecuteLogic(mapping, form, request, response);
				} catch (Exception e) {
					// Aniadir los errores al request
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_GENERICO_BUSQUEDAS));
					logger.error("Error al Ejecutar la busqueda", e);

					if (precondiciones != null
							&& StringUtils.isNotEmpty(precondiciones
									.getForwardRetorno()))
						setReturnActionFordward(request,
								mapping.findForward(precondiciones
										.getForwardRetorno()));
					else
						goBackExecuteLogic(mapping, form, request, response);
				}
			} else {
				logger.info("Formulario inv\u00E1lido");

				// Aniadir los errores al request
				obtenerErrores(request, true).add(errores);

				goLastClientExecuteLogic(mapping, form, request, response);
			}
		}// fin if(busqueda == null)
	}

	public void generarInformeBusquedaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
				new Integer(0));
		removeInTemporalSession(request, Constants.CANCEL_PROGRESSBAR_KEY);
		String uriRetorno = getInvocationStack(request)
				.getLastClientInvocation().getInvocationURI();
		request.getSession().setAttribute(ErrorsTag.KEY_ERRORS,
				new ActionErrors());
		request.setAttribute(DepositoConstants.LIMITAR_RESULTADOS_BUSQUEDA,
				new Boolean(true));
		buscarExecuteLogic(mappings, form, request, response);
		BusquedaElementosVO busquedaElementosVO = (BusquedaElementosVO) request
				.getAttribute(FondosConstants.VO_BUSQUEDA_FONDOS);
		setInTemporalSession(request, FondosConstants.VO_BUSQUEDA_FONDOS,
				busquedaElementosVO);
		setInTemporalSession(request,
				FondosConstants.INFORME_BUSQUEDA_URI_RETORNO, uriRetorno);

		if (!obtenerErrores(request, true).isEmpty()) {
			setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
					new Integer(100));
			return;
		}
		Object obj = getFromTemporalSession(request,
				FondosConstants.LISTA_ELEMENTOS_CF);
		List lista = null;
		if (obj instanceof PaginatedList) {
			lista = ((PaginatedList) obj).getList();
		} else
			lista = (List) obj;
		if (lista == null) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_EXPORTAR_BUSQUEDA));
			setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
					new Integer(100));
			return;
		}
		removeInTemporalSession(request, FondosConstants.LISTA_ELEMENTOS_CF);
		// redireccionar a la action que genera el informe a traves de iText
		setInTemporalSession(request,
				Constants.FORWARD_GENERAR_INFORME_LISTADO,
				"informe_busqueda_fondos");

		// popLastInvocation(request);
		setReturnActionFordward(request,
				mappings.findForward("sel_formato_informe_listado"));
	}

	public void generateInformeUdocsSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		List listaIdsElementos = (List) getFromTemporalSession(request,
				FondosConstants.LISTA_IDS_ELEMENTOS_CF);

		if (ListUtils.isNotEmpty(listaIdsElementos)) {
			List listaUdocs = serieBI.getUdocsSerieByIdsElementos(ArrayUtils
					.getArrayIds(listaIdsElementos));
			setListaUnidadesDocumentales(request, listaUdocs);
		}

		ActionRedirect ret = new ActionRedirect(
				mappings.findForward("generate_informe_udocs_serie"));
		setReturnActionFordward(request, ret);
	}

	private void setListaUnidadesDocumentales(HttpServletRequest request,
			Collection listaUdocs) {
		CollectionUtils
				.transform(listaUdocs, UnidadDocumentalToPO
						.getInstance(getServiceRepository(request)));
		setInTemporalSession(request,
				FondosConstants.LISTA_UNIDADES_DOCUMENTALES_INFORME_SERIE,
				listaUdocs);
	}

	/**
	 * Ver el elemento en el cuadro de clasificacion
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void verEnCuadroExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		String id = request.getParameter(Constants.ID);
		if (!puedeAccederUsuarioAElemento(request, id)) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		ElementoCuadroClasificacionVO elementoCF = cuadroBI
				.getElementoCuadroClasificacion(id);

		if (elementoCF != null) {

			// Marcar el listado como cacheado
			setInTemporalSession(request, FondosConstants.LISTA_CACHEADA_KEY,
					"1");

			// Obtener la lista para marcar el seleccionado
			List elements = null;
			Object lista = getFromTemporalSession(request,
					FondosConstants.LISTA_ELEMENTOS_CF);
			if (lista instanceof PaginatedList) {
				PaginatedList paginatedList = (PaginatedList) lista;
				if (paginatedList != null) {
					elements = paginatedList.getList();
				}
			} else {
				elements = (List) lista;
			}

			Map mapVisitedElements = (Map) getFromTemporalSession(request,
					FondosConstants.MAP_ELEMENTOS_CF_VISITADOS);
			if ((elements != null) && (!elements.isEmpty())) {
				ListIterator it = elements.listIterator();
				while (it.hasNext()) {
					// ElementoCuadroClasificacion element =
					// (ElementoCuadroClasificacion) it.next();
					ElementoCFPO element = (ElementoCFPO) it.next();
					if (element.getId().equals(id)) {
						element.setRowStyle(IVisitedRowVO.CSS_FILA_CARGADA);
						if (mapVisitedElements != null)
							mapVisitedElements.put(id, id);
						break;
					}
				}
			}

			if (!DisplayTagUtils.isPaginating(request)
					&& !DisplayTagUtils.isSorting(request)) {
				ClientInvocation invocation = saveCurrentInvocation(
						KeysClientsInvocations.VER_EN_CUADRO, request);
				invocation.setVisibleInNavigationToolBar(false);
			}

			ActionRedirect ret = new ActionRedirect(
					mappings.findForward("verEnCuadro"));
			ret.addParameter("itemID", id);
			setReturnActionFordward(request, ret);
		} else {

			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ERRORS_FONDOS_ELEMENTO_NO_EXISTE));
			goBackExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Metodo para seleccionar los elementos y redirigir al Metodo de retorno
	 *
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void seleccionarExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		BusquedaElementosForm frm = (BusquedaElementosForm) form;
		getInvocationStack(request).getLastReturnPoint(request)
				.removeInTemporalSession(request,
						FondosConstants.ELEMENTOS_SELECCIONADOS_BUSQUEDA_KEY);

		// Obtener los ids de elementos seleccionados
		String[] idsElementosSeleccionados = frm.getSelectedElem();

		// Obtener las precondiciones de busqueda
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);

		// Ir al utlimo punto de retorno
		getInvocationStack(request).goToLastReturnPoint(request);

		// Obtener el utlimo punto de retorno y salvar los ids de elementos
		// seleccionados
		getInvocationStack(request).getLastReturnPoint(request)
				.setInTemporalSession(request,
						FondosConstants.ELEMENTOS_SELECCIONADOS_BUSQUEDA_KEY,
						idsElementosSeleccionados);

		// Volver al forward de retorno
		setReturnActionFordward(request,
				mappings.findForward(precondiciones.getForwardRetorno()));
	}

	/**
	 * Metodo para las busquedas de procedimientos desde el formulario de
	 * busquedas rapida
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoRapidaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(BusquedaElementosForm) form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("form_busqueda_rapida"));
	}

	/**
	 * Metodo para las busquedas de procedimientos desde el formulario de
	 * busquedas avanzada
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoAvanzadaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(BusquedaElementosForm) form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("form_busqueda_avanzada"));
	}

	/**
	 * Metodo para las busquedas de procedimientos desde el formulario de
	 * busquedas rapida
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoGenericaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(BusquedaElementosForm) form, request, response);

		// Obtener el tipo de busqueda
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);

		// Redireccionamos a la pagina adecuada
		setReturnActionFordward(request,
				mappings.findForward(precondiciones.getForwardListado()));
	}

	/**
	 * Metodo para las busquedas de procedimientos desde el formulario de
	 * busqueda de elementos
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoElementosExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(BusquedaElementosForm) form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("form_busqueda_elementos"));
	}

	/**
	 * Metodo para las busquedas de procedimientos desde el formulario de
	 * busqueda reemplazo avanzado
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoReemplazoAvanzadoExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(BusquedaElementosForm) form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("form_busqueda_generica"));
	}

	/**
	 * Metodo para las busquedas de procedimientos desde el formulario de
	 * busqueda seleccion eliminacion
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoSeleccionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(BusquedaElementosForm) form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("busqueda_valoracion_seleccion_udocs"));
	}

	public void exportarAExcelExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionCuadroClasificacionBI cuadroBI = getGestionCuadroClasificacionBI(request);
		BusquedaElementosForm formulario = (BusquedaElementosForm) form;
		String[] idsAmbito = formulario.getIdObjetoAmbito();
		ExcelReport ex = new ExcelReport();

		if (!ArrayUtils.isEmpty(idsAmbito)) {
			for (int i = 0; i < idsAmbito.length; i++) {
				short nivel = 0;

				ElementoCuadroClasificacionVO elemento = cuadroBI
						.getElementoCuadroClasificacion(idsAmbito[i]);

				if (elemento != null && elemento.isVigente()) {
					ex.addHoja(Messages.getString(
							"archigest.archivo.cf.ambito", request.getLocale())
							+ (i + 1));

					String texto = elemento.getItemName();

					if (elemento.isVigente()) {
						ex.addCelda(nivel, texto);
					}

					ex.addFila();
					if (elemento.getTipo() != TipoNivelCF.SERIE
							.getIdentificador()) {
						exportHijos(elemento.getId(), ex, cuadroBI, (short) 1);
					}
				}
			}

			try {
				download(response, "cuadro.xls", ex.getBytes());

			} catch (Exception e) {
				logger.error("Error al exportar a Excel: " + e);
				ActionErrors errors = new ActionErrors();
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERRORS_FONDOS_EXPORTAR_EXCEL,
								Messages.getString(
										Constants.ETIQUETA_CUADRO_CLASIFICACION,
										request.getLocale())));
				saveErrors(request, errors);
			}
		}

		setReturnActionFordward(request, null);

	}

	private void exportHijos(String idElemento, ExcelReport ex,
			GestionCuadroClasificacionBI cuadroBI, short nivel) {
		List listaHijos = cuadroBI
				.getHijosVigentesElementoCuadroClasificacion(idElemento);

		if (listaHijos != null) {
			ListIterator it = listaHijos.listIterator();
			while (it.hasNext()) {
				ElementoCuadroClasificacionVO elementoVO = (ElementoCuadroClasificacionVO) it
						.next();

				if (elementoVO.isVigente()) {
					String texto = elementoVO.getItemName();
					ex.addCelda(nivel, texto);

					ex.addFila();

					if (elementoVO.getTipo() != TipoNivelCF.SERIE
							.getIdentificador()) {
						short nivelNuevo = (short) (nivel + 1);
						exportHijos(elementoVO.getId(), ex, cuadroBI,
								nivelNuevo);
					}
				}
			}
		}
	}

	public void incorporarResultadosBusquedaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List idsElementosBusqueda = (List) getFromTemporalSession(request,
				FondosConstants.LISTA_IDS_ELEMENTOS_CF);

		getInvocationStack(request).getLastReturnPoint(request)
				.removeInTemporalSession(request,
						FondosConstants.ELEMENTOS_SELECCIONADOS_BUSQUEDA_KEY);

		// Obtener las precondiciones de busqueda
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);

		// Ir al utlimo punto de retorno
		getInvocationStack(request).goToLastReturnPoint(request);

		// Obtener el utlimo punto de retorno y salvar los ids de elementos
		// seleccionados
		getInvocationStack(request).getLastReturnPoint(request)
				.setInTemporalSession(request,
						FondosConstants.ELEMENTOS_SELECCIONADOS_BUSQUEDA_KEY,
						CollectionUtils.toArray(idsElementosBusqueda));

		// Volver al forward de retorno
		setReturnActionFordward(request,
				mappings.findForward(precondiciones.getForwardRetorno()));
	}

	private Busqueda getCfgBusquedaBandejaElementos(HttpServletRequest request) {

		String key = getKeyCfgBusquedas(request,
				ConfiguracionArchivoManager.ELEMENTOS);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;

	}

	private PrecondicionesBusquedaFondosGenerica getPrecondicionesBusquedaElementos() {
		PrecondicionesBusquedaFondosGenerica precondiciones = new PrecondicionesBusquedaFondosGenerica();
		precondiciones.setForwardListado("ver_elementos");
		precondiciones.setForwardRetorno("form_busqueda_elementos");
		return precondiciones;
	}

}