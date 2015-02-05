package descripcion.actions;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.AppUser;
import util.CollectionUtils;
import util.ErrorsTag;
import util.PaginatedList;
import xml.config.Busqueda;
import xml.config.ConfiguracionArchivoManager;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.actions.BusquedaBaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ColumnNotIndexedException;
import common.exceptions.SintaxErrorException;
import common.exceptions.TooManyResultsException;
import common.exceptions.WordOmittedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.util.DisplayTagUtils;
import common.util.ListUtils;
import common.view.IVisitedRowVO;
import common.vos.BandejaActividadesVO;

import deposito.DepositoConstants;
import descripcion.DescripcionConstants;
import descripcion.vos.ElementoCFPO;
import descripcion.vos.ElementoCFVO;
import fondos.FondosConstants;
import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.forms.BusquedaElementosForm;
import fondos.model.CamposBusquedas;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.NivelCF;
import fondos.utils.BusquedasHelper;
import fondos.vos.BusquedaElementosVO;

/**
 * Acción para la gestión de las búsquedas sobre la descripción de los elementos
 * del cuadro de clasificación.
 */
public class DescripcionAction extends BusquedaBaseAction {
	private static final String ID_DISPLAY_TAG = "elemento";

	private Busqueda getCfgBusquedaBandejaSimple(HttpServletRequest request) {
		String key = getKeyCfgBusquedas(request,
				ConfiguracionArchivoManager.BANDEJA_SIMPLE);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;
	}

	private Busqueda getCfgBusquedaBandejaAvanzada(HttpServletRequest request) {
		String key = getKeyCfgBusquedas(request,
				ConfiguracionArchivoManager.BANDEJA_AVANZADA);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;
	}

	/**
	 * Muestra el formulario para realizar búsquedas de fondos.
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
	protected void formBusqExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Guardar el enlace a la página
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_FORM_BUSQ, request);
		invocation.setAsReturnPoint(true);

		removeInTemporalSession(request,
				DescripcionConstants.LISTA_IDS_ELEMENTOS_CF);
		removeInTemporalSession(request, Constants.LAST_ORDER);
		removeInTemporalSession(request, Constants.LAST_ORDER_DIRECTION);
		removeInTemporalSession(request, Constants.PAGE_NUMBER);

		// Crear un map de elementos visitados nuevo
		setInTemporalSession(request,
				DescripcionConstants.MAP_ELEMENTOS_CF_VISITADOS, new HashMap());

		// Establecer la configuración de la búsqueda particular
		BusquedaElementosForm busquedaElementosForm = (BusquedaElementosForm) form;
		Busqueda busqueda = getCfgBusquedaBandejaAvanzada(request);
		setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
				busqueda);
		BusquedasHelper.loadListasBusqueda(busqueda, busquedaElementosForm,
				request, null);

		setReturnActionFordward(request,
				mapping.findForward("formulario_busqueda"));
	}

	/**
	 * Recarga la bandeja de entrada
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
	protected void formBandejaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardar el enlace a la página
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.HOME_CONSULTAS, request);
		invocation.setAsReturnPoint(true);

		// Guardar las actividades del usuario
		AppUser userVO = getAppUser(request);
		ServiceRepository services = getServiceRepository(request);
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();

		BandejaActividadesVO actividadesUsuario = sistemaBI
				.getActividadesUsuario(userVO);
		request.setAttribute(Constants.BANDEJA_KEY, actividadesUsuario);

		removeInTemporalSession(request,
				DescripcionConstants.LISTA_IDS_ELEMENTOS_CF);
		removeInTemporalSession(request, Constants.LAST_ORDER);
		removeInTemporalSession(request, Constants.LAST_ORDER_DIRECTION);
		removeInTemporalSession(request, Constants.PAGE_NUMBER);

		// Establecer la configuración de la búsqueda particular
		BusquedaElementosForm busquedaElementosForm = (BusquedaElementosForm) form;
		Busqueda busqueda = getCfgBusquedaBandejaSimple(request);
		setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
				busqueda);
		BusquedasHelper.loadListasBusqueda(busqueda, busquedaElementosForm,
				request, null);

		setReturnActionFordward(request, mapping.findForward("load_bandeja"));
	}

	/**
	 * Realiza la búsqueda general de fondos.
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

	protected void busqExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		removeInTemporalSession(request, Constants.SHOW_INFORME_BUSQUEDA_BUTTON);
		
		// Obtener el parámetro cuya existencia indica que se está solicitando
		// la exportación de los datos obtenidos como resultado de la búsqueda
		boolean isExportAction = PageInfo.checkExportingList(request);

		BusquedaElementosForm busquedaElementosForm = (BusquedaElementosForm) form;
		busquedaElementosForm.setPostBack(Constants.TRUE_STRING);

		PaginatedList paginatedList = (PaginatedList) getFromTemporalSession(
				request, DescripcionConstants.ELEMENTOS_KEY);

		final GestionCuadroClasificacionBI serviceCF = getGestionCuadroClasificacionBI(request);

		Boolean usarCache = (Boolean) getFromTemporalSession(request,
				"usarCache");
		if (usarCache != null && usarCache.booleanValue()) {
			removeInTemporalSession(request, "usarCache");
			// Guardar el enlace a la página
			if (!DisplayTagUtils.isDisplayTagOperation(request)) {
				saveCurrentInvocation(KeysClientsInvocations.DESCRIPCION_BUSQ,
						request);
			}
			setReturnActionFordward(request,
					mapping.findForward("listado_busqueda"));
		} else {
			// Añadir los parámetros del formulario al enlace
			ClientInvocation cli = getInvocationStack(request)
					.getLastClientInvocation();
			cli.addParameters(busquedaElementosForm.getMap());

			// Obtener la configuración de la búsqueda
			Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
					FondosConstants.CFG_BUSQUEDA_KEY);

			// Comprobar el tipo de búsqueda - Ver si funciona bien sin esta
			// comprobación
			// if (busquedaElementosForm.getTipoBusqueda() ==
			// TipoBusqueda.TIPO_BUSQUEDA_BANDEJA_SIMPLE)
			// Si estamos ante una búsqueda donde no hace falta rellenar los
			// niveles, es necesario establecer que se busque en todos los
			// niveles
			// excepto el 1
			if (!busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION)) {
				if (busquedaElementosForm.getNiveles() == null
						|| (busquedaElementosForm.getNiveles() != null && busquedaElementosForm
								.getNiveles().length == 0)) {
					// Si se hace una búsqueda reducida, se limita la búsqueda a
					// elementos mayor que el 1
					List niveles = serviceCF.getNivelesCF();
					// List niveles =
					// (List)request.getAttribute(DescripcionConstants.NIVELES_KEY);
					String[] idsNiveles = new String[niveles.size()];
					for (int i = 0; i < niveles.size(); i++)
						idsNiveles[i] = ((NivelCF) niveles.get(i)).getId();
					busquedaElementosForm.setNiveles(idsNiveles);
				}
			}

			// Si estamos ante una búsqueda donde no hace falta rellenar el
			// estado es necesario establecer que se busque en elementos
			// vigentes
			if (!busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO)) {
				if (busquedaElementosForm.getEstados() == null
						|| (busquedaElementosForm.getEstados() != null && busquedaElementosForm
								.getEstados().length == 0))
					busquedaElementosForm
							.setEstados(new String[] { new Integer(
									IElementoCuadroClasificacion.VIGENTE)
									.toString() });
			}

			// Validar el formulario
			ActionErrors errores = BusquedasHelper.validateCampos(mapping,
					request, busqueda, busquedaElementosForm);
			if ((errores == null) || errores.isEmpty()) {
				// Guardar el enlace a la página
				if (!DisplayTagUtils.isDisplayTagOperation(request)) {
					saveCurrentInvocation(
							KeysClientsInvocations.DESCRIPCION_BUSQ, request);
				}

				// Información de paginación
				// PageInfo pageInfo = new PageInfo(request,"codReferencia");
				// Chequear que este cambio está bien !! (codreferencia)
				PageInfo pageInfo = new PageInfo(
						request,
						ElementoCuadroClasificacionDBEntityImplBase.CODREFERENCIA_COLUMN_NAME);
				pageInfo.setDefautMaxNumItems();

				// Recuperar la información del formulario
				BusquedaElementosVO busquedaElementosVO = BusquedasHelper
						.getBusquedaElementosVO(busqueda, null,
								busquedaElementosForm);

				busquedaElementosVO.setPageInfo(pageInfo);
				GestionDescripcionBI service = getGestionDescripcionBI(request);

				try {
					int numMaxResultados = ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionGeneral().getNumMaxResultados();
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

					final List listaIdsElementos = getIdsElementos(request,
							busquedaElementosVO, service, numMaxResultados,
							busqueda);
					SortOrderEnum currentOrderDirection = getOrderCurrentDirection(
							request, busqueda);

					String orderColumn = getOrderColumn(request, busqueda);

					if (!ListUtils.isEmpty(listaIdsElementos)) {
						// Búsqueda de elementos del cuadro de clasificación
						request.setAttribute(
								FondosConstants.VO_BUSQUEDA_FONDOS,
								busquedaElementosVO);
						setInTemporalSession(request,
								FondosConstants.LISTA_IDS_ELEMENTOS_CF,
								listaIdsElementos);
						setInTemporalSession(request,
								DescripcionConstants.LISTA_IDS_ELEMENTOS_CF,
								listaIdsElementos);

						int pageNumber = getPageNumber(request);

						// Comprobamos si es una exportación, en cuyo caso, es
						// necesario obtener todos los objetos en lugar de sólo
						// los de la página
						String[] idsToShow = null;
						if (isExportAction)
							idsToShow = DisplayTagUtils.getIds(1,
									numMaxResultados, listaIdsElementos);
						else
							idsToShow = DisplayTagUtils.getIds(pageNumber,
									numeroElmentosPorPagina, listaIdsElementos);

						removeInTemporalSession(request,
								DescripcionConstants.ELEMENTOS_KEY);
						List ltElements = service.getElementos(
								idsToShow,
								fillBusquedaElementosVO(request,
										busquedaElementosVO, busqueda),
								busqueda);

						// Transformar la lista de elementos en POS
						final Busqueda busquedaFinal = (Busqueda) getFromTemporalSession(
								request, FondosConstants.CFG_BUSQUEDA_KEY);
						final ServiceRepository servicios = getServiceRepository(request);

						CollectionUtils.transform(ltElements,
								new Transformer() {
									public Object transform(Object obj) {
										ElementoCFPO po = new ElementoCFPO(
												servicios, busquedaFinal,
												getProductores(busquedaFinal,
														listaIdsElementos,
														serviceCF),
												getInteresados(busquedaFinal,
														listaIdsElementos,
														serviceCF));
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

						setInTemporalSession(request,
								DescripcionConstants.ELEMENTOS_KEY,
								paginatedList);

						// Restaurar los elementos visitados
						if (!CollectionUtils.isEmpty(ltElements)) {
							Map mapVisitedElements = (Map) getFromTemporalSession(
									request,
									DescripcionConstants.MAP_ELEMENTOS_CF_VISITADOS);
							if ((ltElements != null) && (!ltElements.isEmpty())) {
								ListIterator it = ltElements.listIterator();
								while (it.hasNext()) {
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
					setReturnActionFordward(request,
							mapping.findForward("listado_busqueda"));
				} catch (TooManyResultsException e) {
					// Añadir los errores al request
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_TOO_MANY_RESULTS,
											new Object[] {
													new Integer(e.getCount()),
													new Integer(e
															.getMaxNumResults()) }));

					setInTemporalSession(request, Constants.SHOW_INFORME_BUSQUEDA_BUTTON,Boolean.TRUE);

					if (getInvocationStack(request).getSize() - 1 > 1) {
						getInvocationStack(request).getClientInvocation(
								getInvocationStack(request).getSize() - 2);
					} else { // busqueda en bandeja de entrada. Siempre usa la
								// misma URL -> no vale por parametro.
						getInvocationStack(request).getHome();
					}
					goBackBusquedaExecuteLogic(mapping, form, request, response);
				} catch (ColumnNotIndexedException e) {
					// Añadir los errores al request
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_COLUMNA_NO_INDEXADA));

					goBackExecuteLogic(mapping, form, request, response);
				} catch (WordOmittedException e) {
					// Añadir los errores al request
					obtenerErrores(request, true).add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(Constants.ERROR_PALABRA_OMITIDA));

					goBackExecuteLogic(mapping, form, request, response);
				} catch (SintaxErrorException e) {
					// Añadir los errores al request
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_SINTAXIS_INCORRECTA));

					goBackExecuteLogic(mapping, form, request, response);
				}
			} else {
				// Añadir los errores al request
				obtenerErrores(request, true).add(errores);
				goLastClientExecuteLogic(mapping, form, request, response);
			}
		}
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
		busqExecuteLogic(mappings, form, request, response);
		BusquedaElementosVO busquedaElementosVO = (BusquedaElementosVO) request
				.getAttribute(FondosConstants.VO_BUSQUEDA_FONDOS);
		setInTemporalSession(request, FondosConstants.VO_BUSQUEDA_FONDOS,
				busquedaElementosVO);
		setInTemporalSession(request,
				FondosConstants.INFORME_BUSQUEDA_URI_RETORNO, uriRetorno);

		request.setAttribute(FondosConstants.SHOW_PRODUCTOR, "1");
		if (!obtenerErrores(request, true).isEmpty()) {
			setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
					new Integer(100));
			return;
		}
		Object obj = getFromTemporalSession(request,
				DescripcionConstants.LISTA_IDS_ELEMENTOS_CF);
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
		removeInTemporalSession(request, DescripcionConstants.ELEMENTOS_KEY);
		// redireccionar a la action que genera el informe a traves de iText
		setInTemporalSession(request,
				Constants.FORWARD_GENERAR_INFORME_LISTADO,
				"informe_busqueda_fondos");
		setReturnActionFordward(request,
				mappings.findForward("sel_formato_informe_listado"));
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
		// busquedaGeneralVO.setOrderColumn(getOrderColumnNumber(request).intValue());
		busquedaElementosVO.setOrderColumn(ElementoCFVO
				.getColumnNumberByName(getOrderColumn(request, busqueda)));
		busquedaElementosVO
				.setTipoOrdenacion(((SortOrderEnum) getOrderCurrentDirection(
						request, busqueda)).getName());

		return busquedaElementosVO;
	}

	private List getIdsElementos(HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO,
			GestionDescripcionBI service, int numMaxResults, Busqueda busqueda)
			throws TooManyResultsException {
		List listaIdsElementos = null;
		if (DisplayTagUtils.isPaginating(request)) {
			listaIdsElementos = (List) getFromTemporalSession(request,
					DescripcionConstants.LISTA_IDS_ELEMENTOS_CF);
		} else {
			removeInTemporalSession(request,
					DescripcionConstants.LISTA_IDS_ELEMENTOS_CF);
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
	 * Método para la búsqueda de procedimientos en la bandeja
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoBandejaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(BusquedaElementosForm) form, request, response);

		// Guardar las actividades del usuario
		AppUser userVO = getAppUser(request);
		ServiceRepository services = getServiceRepository(request);
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();

		BandejaActividadesVO actividadesUsuario = sistemaBI
				.getActividadesUsuario(userVO);
		request.setAttribute(Constants.BANDEJA_KEY, actividadesUsuario);

		setReturnActionFordward(request, mappings.findForward("load_bandeja"));
	}

	/**
	 * Método para la búsqueda de procedimientos en la búsqueda archivística
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoAmpliadaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(BusquedaElementosForm) form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("formulario_busqueda"));
	}
}