package solicitudes.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.ServiceClient;
import solicitudes.ConsultaUnidadesDocumentalesConstants;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.forms.ConsultaUnidadesDocumentalesForm;
import solicitudes.model.ConsultaUnidadesDocumentalesBI;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.view.DetalleToPO;
import solicitudes.vos.DetalleVO;
import util.CollectionUtils;
import util.ErrorsTag;
import util.PaginatedList;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.Messages;
import common.TiposUsuario;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.exceptions.TooManyResultsException;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.DisplayTagUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import es.archigest.framework.core.vo.PropertyBean;
import fondos.vos.BusquedaElementosVO;

public class ConsultaUnidadesDocumentalesAction extends BaseAction {

	/**
	 * Metodo que se ejecuta cuando se pulsa en el menu el enlace de consultar
	 * unidades documentales. Introduce en memoria todos los objetos necesarios
	 * para la gestión de la consulta de unidades documentales
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void initExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {

			/* ServiceRepository services = */ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));

			SortOrderEnum currentOrderDirection = SortOrderEnum.ASCENDING;
			int pageNumber = DisplayTagUtils.getDefaultPageNumber();

			setInTemporalSession(
					request,
					ConsultaUnidadesDocumentalesConstants.LISTADO_BUSQUEDA_UDOCS,
					null);
			setInTemporalSession(
					request,
					ConsultaUnidadesDocumentalesConstants.LISTADO_TIPOS_USUARIO_PRESTAMOS,
					TiposUsuario.getTiposUsuarioPrestamos());
			setInTemporalSession(
					request,
					ConsultaUnidadesDocumentalesConstants.LISTADO_TIPOS_USUARIO_CONSULTAS,
					TiposUsuario.getTiposUsuarioConsultas());
			setInTemporalSession(
					request,
					ConsultaUnidadesDocumentalesConstants.LISTADO_TIPOS_SERVICIO,
					getTiposServicio());
			setInTemporalSession(request, Constants.LAST_ORDER, null);
			setInTemporalSession(request, Constants.LAST_ORDER_DIRECTION,
					currentOrderDirection);
			setInTemporalSession(request, Constants.PAGE_NUMBER, new Integer(
					pageNumber));
			setInTemporalSession(request,
					ConsultaUnidadesDocumentalesConstants.MOSTRAR_RESULTADOS,
					Boolean.FALSE);

			saveCurrentInvocation(
					KeysClientsInvocations.SOLICITUDES_CONSULTAR_UNIDADES_DOCUMENTALES,
					request);

			setReturnActionFordward(request, mapping.findForward("init"));

			ConsultaUnidadesDocumentalesForm consultaUnidadesDocumentalesForm = (ConsultaUnidadesDocumentalesForm) form;
			consultaUnidadesDocumentalesForm.reset();

		} catch (IllegalAccessException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.toString()));
			ErrorsTag.saveErrors(request, errors);
		} catch (InvocationTargetException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.toString()));
			ErrorsTag.saveErrors(request, errors);
		} catch (NoSuchMethodException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.toString()));
			ErrorsTag.saveErrors(request, errors);
		}
	}

	/**
	 * Método que guarda la invocación al acceder a la gestión de consultar
	 * unidades documentales
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void consultarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request, mapping.findForward("consultar"));
	}

	/**
	 * Método que se ejecuta cuando se pulsa la opción de buscar en la gestión
	 * de consultas de unidades documentales. Obtiene la lista de unidades
	 * documentales de acuerdo con los criterios de búsqueda introducidos en el
	 * formulario
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ConsultaUnidadesDocumentalesForm consultaUnidadesDocumentalesForm = (ConsultaUnidadesDocumentalesForm) form;
		consultaUnidadesDocumentalesForm.setTiposServicio(request
				.getParameterValues("tiposServicio"));
		ActionErrors errors = validateForm(request,
				consultaUnidadesDocumentalesForm, new ActionErrors());
		if ((errors == null) || errors.isEmpty()) {
			try {

				if ("buscar".equals(request.getParameter("linkPressed"))) {
					setInTemporalSession(request, Constants.LAST_ORDER, null);
					setInTemporalSession(request,
							Constants.LAST_ORDER_DIRECTION,
							SortOrderEnum.ASCENDING);
					setInTemporalSession(request, Constants.PAGE_NUMBER,
							new Integer(DisplayTagUtils.getDefaultPageNumber()));
				}

				boolean isExportAction = PageInfo.checkExportingList(request);

				ConsultaUnidadesDocumentalesBI consultaService = getConsultaUnidadesDocumentalesBI(request);

				setInTemporalSession(
						request,
						ConsultaUnidadesDocumentalesConstants.LISTADO_BUSQUEDA_UDOCS,
						null);
				setInTemporalSession(
						request,
						ConsultaUnidadesDocumentalesConstants.MOSTRAR_RESULTADOS,
						Boolean.TRUE);

				int numMaxResultados = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral().getNumMaxResultados();
				int numeroElmentosPorPagina = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral().getNumResultadosPorPagina();

				SortOrderEnum currentOrderDirection = getOrderCurrentDirection(request);
				String orderColumn = getOrderColumn(request);

				BusquedaElementosVO busquedaElementosVO = makeBusquedaElementosVO(
						request, new BusquedaElementosVO(),
						consultaUnidadesDocumentalesForm,
						currentOrderDirection, orderColumn);

				List listaIdsElementos = getIdsElementos(request,
						busquedaElementosVO, consultaService, numMaxResultados);

				if (!ListUtils.isEmpty(listaIdsElementos)) {
					setInTemporalSession(
							request,
							ConsultaUnidadesDocumentalesConstants.LISTA_IDS_ELEMENTOS,
							listaIdsElementos);

					int pageNumber = getPageNumber(request);

					List idsTiposToShow = null;
					if (isExportAction)
						idsTiposToShow = ListUtils.getItems(1,
								listaIdsElementos.size(), listaIdsElementos);
					else
						idsTiposToShow = ListUtils.getItems(pageNumber,
								numeroElmentosPorPagina, listaIdsElementos);

					removeInTemporalSession(
							request,
							ConsultaUnidadesDocumentalesConstants.LISTADO_BUSQUEDA_UDOCS);
					List ltElements = getElementos(idsTiposToShow,
							busquedaElementosVO, consultaService);

					ServiceRepository services = getServiceRepository(request);
					CollectionUtils.transform(ltElements, DetalleToPO
							.getInstance(request.getLocale(), services, false));

					PaginatedList paginatedList = new PaginatedList();
					paginatedList.setFullListSize(listaIdsElementos.size());
					paginatedList.setList(ltElements);
					paginatedList.setObjectsPerPage(numeroElmentosPorPagina);
					paginatedList.setPageNumber(pageNumber);
					paginatedList.setSortCriterion(orderColumn);

					if (currentOrderDirection.getName().equals("ascending"))
						paginatedList
								.setSortDirection(SortOrderEnum.DESCENDING);
					else
						paginatedList.setSortDirection(SortOrderEnum.ASCENDING);

					setInTemporalSession(
							request,
							ConsultaUnidadesDocumentalesConstants.LISTADO_BUSQUEDA_UDOCS,
							paginatedList);
					setInTemporalSession(request, Constants.LAST_ORDER,
							orderColumn);
					setInTemporalSession(request,
							Constants.LAST_ORDER_DIRECTION,
							currentOrderDirection);
					setInTemporalSession(request, Constants.PAGE_NUMBER,
							new Integer(pageNumber));
				}

			} catch (TooManyResultsException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
								new Object[] { new Integer(e.getCount()),
										new Integer(e.getMaxNumResults()) }));
			}

			setReturnActionFordward(request, mapping.findForward("consultar"));
		}

		else {
			setInTemporalSession(
					request,
					ConsultaUnidadesDocumentalesConstants.LISTADO_BUSQUEDA_UDOCS,
					null);
			obtenerErrores(request, true).add(errors);
			setReturnActionFordward(request, mapping.findForward("consultar"));
		}

		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_CONSULTAR_UNIDADES_DOCUMENTALES,
				request);
	}

	/**
	 * @param request
	 * @return Devuelve el numero de página solicitado del displaytag
	 */
	private int getPageNumber(HttpServletRequest request) {
		int pageNumber = 1;
		if (DisplayTagUtils.getPageNumber(request) != null) {
			pageNumber = Integer.parseInt(DisplayTagUtils
					.getPageNumber(request));
		} else {
			pageNumber = ((Integer) getFromTemporalSession(request,
					Constants.PAGE_NUMBER)).intValue();
		}
		return pageNumber;
	}

	/**
	 * @param request
	 * @param busquedaElementosVO
	 *            . Contiene la información de los criterios de búsqueda
	 * @param consultaUnidadesDocumentalesService
	 *            . Servicio para la consulta de unidades documentales
	 * @param numMaxResults
	 *            . Número máximo de resultados que puede proporcionar la
	 *            consulta
	 * @return una lista de {@link DetalleVO} conteniendo cada uno de ellos el
	 *         indentificador de la unidad documental y el de la solicitud
	 * @throws TooManyResultsException
	 */
	public List getIdsElementos(HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO,
			ConsultaUnidadesDocumentalesBI consultaUnidadesDocumentalesService,
			int numMaxResults) throws TooManyResultsException {
		List listaIdsElementos = new ArrayList();

		if (DisplayTagUtils.isPaginating(request)) {
			listaIdsElementos = (List) getFromTemporalSession(request,
					ConsultaUnidadesDocumentalesConstants.LISTA_IDS_ELEMENTOS);
		} else {
			removeInTemporalSession(request,
					ConsultaUnidadesDocumentalesConstants.LISTA_IDS_ELEMENTOS);
			String[] tiposServicio = busquedaElementosVO.getTiposServicio();
			if (tiposServicio != null && tiposServicio.length > 0) {
				if (tiposServicio.length == 2)
					listaIdsElementos = consultaUnidadesDocumentalesService
							.getIdsElementosEnPrestamosYConsultas(
									busquedaElementosVO, numMaxResults);

				else if (tiposServicio.length == 1)
					listaIdsElementos = consultaUnidadesDocumentalesService
							.getIdsElementosEnPrestamosOrConsultas(
									busquedaElementosVO, tiposServicio[0],
									numMaxResults);
			}
		}
		return listaIdsElementos;
	}

	/**
	 * 
	 * @param idsToShow
	 *            Listas de {@link DetalleVO} con los identificadores de las
	 *            unidades documentales y de las solicitudes que se van a
	 *            mostrar
	 * @param busquedaElementosVO
	 *            . Contiene la información de los criterios de búsqueda
	 * @param consultaUnidadesDocumentalesService
	 *            . Servicio para la consulta de unidades documentales
	 * @return una lista de {@link DetalleVO} conteniendo cada uno de ellos la
	 *         información que se va a mostrar en el displayTable
	 */
	private List getElementos(List idsToShow,
			BusquedaElementosVO busquedaElementosVO,
			ConsultaUnidadesDocumentalesBI consultaUnidadesDocumentalesService) {
		List listaElementos = new ArrayList();

		String[] tiposServicio = busquedaElementosVO.getTiposServicio();
		if (tiposServicio != null && tiposServicio.length == 2) {
			listaElementos = consultaUnidadesDocumentalesService
					.getElementosEnPrestamosYConsultas(idsToShow,
							busquedaElementosVO);
		} else if (tiposServicio != null && tiposServicio.length == 1) {
			listaElementos = consultaUnidadesDocumentalesService
					.getElementosEnPrestamosOrConsultas(idsToShow,
							busquedaElementosVO, tiposServicio[0]);
		}
		return listaElementos;
	}

	/**
	 * 
	 * @param request
	 * @return El nombre de la columna del displayTable por la que se ordena la
	 *         consulta
	 */
	private String getOrderColumn(HttpServletRequest request) {
		String orderColumn = null;

		if (DisplayTagUtils.isSorting(request))
			orderColumn = DisplayTagUtils.getOrderColumn(request);
		else
			orderColumn = (String) getFromTemporalSession(request,
					Constants.LAST_ORDER);

		return orderColumn;
	}

	/**
	 * 
	 * @param request
	 * @return {@link SortOrderEnum} con la dirección (ascendente o descendente)
	 *         utilizada en la ordenación de la consulta del displayTable
	 */
	private SortOrderEnum getOrderCurrentDirection(HttpServletRequest request) {
		SortOrderEnum currentOrderDirection = null;
		SortOrderEnum lastOrderDirection = (SortOrderEnum) getFromTemporalSession(
				request, Constants.LAST_ORDER_DIRECTION);

		if (DisplayTagUtils.isPaginating(request)
				|| !DisplayTagUtils.isSorting(request)) {
			return lastOrderDirection;
		}

		else {
			String orderColumn = getOrderColumn(request);
			String lastOrderColumn = (String) getFromTemporalSession(request,
					Constants.LAST_ORDER);

			if (orderColumn == null && lastOrderColumn == null) {
				currentOrderDirection = SortOrderEnum.ASCENDING;
			}

			else if (orderColumn.equals(lastOrderColumn)) // Misma columna
			{

				if (SortOrderEnum.ASCENDING.getName().equalsIgnoreCase(
						lastOrderDirection.getName())) {
					currentOrderDirection = SortOrderEnum.DESCENDING;
				} else {
					currentOrderDirection = SortOrderEnum.ASCENDING;
				}

			} else // Distinta columna
			{
				currentOrderDirection = lastOrderDirection;
			}

			return currentOrderDirection;
		}

	}

	/**
	 * Obtiene los codRefObjetoAmbito de la request
	 * 
	 * @param request
	 * @return un array con los codRefObjetoAmbito
	 */
	private String[] getCodRefObjetosAmbito(HttpServletRequest request) {
		return request.getParameterValues("codRefObjetoAmbito");
	}

	/**
	 * Obtiene los nombreObjetoAmbito de la request
	 * 
	 * @param request
	 * @return un array con los nombreObjetoAmbito
	 */
	private String[] getNombreObjetosAmbito(HttpServletRequest request) {
		return request.getParameterValues("nombreObjetoAmbito");
	}

	private boolean isAmbitoVacio(ConsultaUnidadesDocumentalesForm udocForm) {
		return (udocForm.getIdObjetoAmbito() == null
				|| udocForm.getIdObjetoAmbito().length == 0 || StringUtils
				.isEmpty(udocForm.getIdObjetoAmbito()[0]));
	}

	/**
	 * 
	 * @param busquedaElementosVO
	 * @param consultaUnidadesDocumentalesForm
	 * @param orderDirection
	 * @param orderColumn
	 * @return un {@link BusquedaElementosVO} con los criterios de búsqueda para
	 *         obtener las unidades documentales
	 */
	private BusquedaElementosVO makeBusquedaElementosVO(
			HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO,
			ConsultaUnidadesDocumentalesForm consultaUnidadesDocumentalesForm,
			SortOrderEnum orderDirection, String orderColumn) {

		busquedaElementosVO
				.setTipoUsuarioPrestamos(consultaUnidadesDocumentalesForm
						.getTipoUsuarioPrestamos());
		busquedaElementosVO
				.setTipoUsuarioConsultas(consultaUnidadesDocumentalesForm
						.getTipoUsuarioConsultas());
		if (!isAmbitoVacio(consultaUnidadesDocumentalesForm))
			busquedaElementosVO
					.setIdObjetoAmbito(consultaUnidadesDocumentalesForm
							.getIdObjetoAmbito());

		CustomDateRange range = CustomDateFormat.getDateRange(
				consultaUnidadesDocumentalesForm.getFechaCompIni(),
				new CustomDate(consultaUnidadesDocumentalesForm
						.getFechaFormatoIni(), consultaUnidadesDocumentalesForm
						.getFechaAIni(), consultaUnidadesDocumentalesForm
						.getFechaMIni(), consultaUnidadesDocumentalesForm
						.getFechaDIni(), consultaUnidadesDocumentalesForm
						.getFechaSIni()),
				new CustomDate(consultaUnidadesDocumentalesForm
						.getFechaIniFormatoIni(),
						consultaUnidadesDocumentalesForm.getFechaIniAIni(),
						consultaUnidadesDocumentalesForm.getFechaIniMIni(),
						consultaUnidadesDocumentalesForm.getFechaIniDIni(),
						consultaUnidadesDocumentalesForm.getFechaIniSIni()),
				new CustomDate(consultaUnidadesDocumentalesForm
						.getFechaFinFormatoIni(),
						consultaUnidadesDocumentalesForm.getFechaFinAIni(),
						consultaUnidadesDocumentalesForm.getFechaFinMIni(),
						consultaUnidadesDocumentalesForm.getFechaFinDIni(),
						consultaUnidadesDocumentalesForm.getFechaFinSIni()));

		busquedaElementosVO.setFechaIniIni(range.getInitialDate());
		busquedaElementosVO.setFechaIniFin(range.getFinalDate());

		CustomDateRange range2 = CustomDateFormat.getDateRange(
				consultaUnidadesDocumentalesForm.getFechaCompFin(),
				new CustomDate(consultaUnidadesDocumentalesForm
						.getFechaFormatoFin(), consultaUnidadesDocumentalesForm
						.getFechaAFin(), consultaUnidadesDocumentalesForm
						.getFechaMFin(), consultaUnidadesDocumentalesForm
						.getFechaDFin(), consultaUnidadesDocumentalesForm
						.getFechaSFin()),
				new CustomDate(consultaUnidadesDocumentalesForm
						.getFechaFinFormatoIni(),
						consultaUnidadesDocumentalesForm.getFechaFinAIni(),
						consultaUnidadesDocumentalesForm.getFechaFinMIni(),
						consultaUnidadesDocumentalesForm.getFechaFinDIni(),
						consultaUnidadesDocumentalesForm.getFechaFinSIni()),
				new CustomDate(consultaUnidadesDocumentalesForm
						.getFechaFinFormatoFin(),
						consultaUnidadesDocumentalesForm.getFechaFinAFin(),
						consultaUnidadesDocumentalesForm.getFechaFinMFin(),
						consultaUnidadesDocumentalesForm.getFechaFinDFin(),
						consultaUnidadesDocumentalesForm.getFechaFinSFin()));

		busquedaElementosVO.setFechaFinIni(range2.getInitialDate());
		busquedaElementosVO.setFechaFinFin(range2.getFinalDate());

		busquedaElementosVO.setTiposServicio(consultaUnidadesDocumentalesForm
				.getTiposServicio());
		busquedaElementosVO.setOrderColumnName(orderColumn);
		busquedaElementosVO.setSolicitante(consultaUnidadesDocumentalesForm
				.getSolicitante());
		busquedaElementosVO
				.setNumeroExpediente(consultaUnidadesDocumentalesForm
						.getNumexp());

		busquedaElementosVO.setSignatura(consultaUnidadesDocumentalesForm
				.getSignatura());
		busquedaElementosVO.setSignaturaLike(consultaUnidadesDocumentalesForm
				.getSignatura_like());

		if (SortOrderEnum.ASCENDING.getName().equalsIgnoreCase(
				orderDirection.getName()))
			busquedaElementosVO.setTipoOrdenacion(" ASC ");
		else
			busquedaElementosVO.setTipoOrdenacion(" DESC ");

		return busquedaElementosVO;

	}

	/**
	 * Valida la fecha de solicitud
	 * 
	 * @param consultaUnidadesDocumentalesForm
	 * @param errors
	 */
	private void validateFechaEntrega(HttpServletRequest request,
			ConsultaUnidadesDocumentalesForm consultaUnidadesDocumentalesForm,
			ActionErrors errors) {

		if (!new CustomDate(
				consultaUnidadesDocumentalesForm.getFechaFormatoIni(),
				consultaUnidadesDocumentalesForm.getFechaAIni(),
				consultaUnidadesDocumentalesForm.getFechaMIni(),
				consultaUnidadesDocumentalesForm.getFechaDIni(),
				consultaUnidadesDocumentalesForm.getFechaSIni()).validate()
				|| !new CustomDate(
						consultaUnidadesDocumentalesForm
								.getFechaIniFormatoIni(),
						consultaUnidadesDocumentalesForm.getFechaIniAIni(),
						consultaUnidadesDocumentalesForm.getFechaIniMIni(),
						consultaUnidadesDocumentalesForm.getFechaIniDIni(),
						consultaUnidadesDocumentalesForm.getFechaIniSIni())
						.validate()
				|| !new CustomDate(
						consultaUnidadesDocumentalesForm
								.getFechaFinFormatoIni(),
						consultaUnidadesDocumentalesForm.getFechaFinAIni(),
						consultaUnidadesDocumentalesForm.getFechaFinMIni(),
						consultaUnidadesDocumentalesForm.getFechaFinDIni(),
						consultaUnidadesDocumentalesForm.getFechaFinSIni())
						.validate()) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							SolicitudesConstants.LABEL_FORM_FECHA_SOLICITUD,
							request.getLocale())));
		}

	}

	/**
	 * Valida la fecha de devolucion
	 * 
	 * @param consultaUnidadesDocumentalesForm
	 * @param errors
	 */
	private void validateFechaDevolucion(HttpServletRequest request,
			ConsultaUnidadesDocumentalesForm consultaUnidadesDocumentalesForm,
			ActionErrors errors) {

		if (!new CustomDate(
				consultaUnidadesDocumentalesForm.getFechaFormatoFin(),
				consultaUnidadesDocumentalesForm.getFechaAFin(),
				consultaUnidadesDocumentalesForm.getFechaMFin(),
				consultaUnidadesDocumentalesForm.getFechaDFin(),
				consultaUnidadesDocumentalesForm.getFechaSFin()).validate()
				|| !new CustomDate(
						consultaUnidadesDocumentalesForm
								.getFechaFinFormatoIni(),
						consultaUnidadesDocumentalesForm.getFechaFinAIni(),
						consultaUnidadesDocumentalesForm.getFechaFinMIni(),
						consultaUnidadesDocumentalesForm.getFechaFinDIni(),
						consultaUnidadesDocumentalesForm.getFechaFinSIni())
						.validate()
				|| !new CustomDate(
						consultaUnidadesDocumentalesForm
								.getFechaFinFormatoFin(),
						consultaUnidadesDocumentalesForm.getFechaFinAFin(),
						consultaUnidadesDocumentalesForm.getFechaFinMFin(),
						consultaUnidadesDocumentalesForm.getFechaFinDFin(),
						consultaUnidadesDocumentalesForm.getFechaFinSFin())
						.validate()) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							SolicitudesConstants.LABEL_FECHA_DEVOLUCION,
							request.getLocale())));
		}

	}

	private void validateTipoServicio(HttpServletRequest request,
			ConsultaUnidadesDocumentalesForm consultaUnidadesDocumentalesForm,
			ActionErrors errors) {
		if (consultaUnidadesDocumentalesForm.getTiposServicio() == null
				|| consultaUnidadesDocumentalesForm.getTiposServicio().length == 0)
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									SolicitudesConstants.LABEL_FORM_TIPO_SERVICIO,
									request.getLocale())));
	}

	/**
	 * Valida el ambito introducido
	 * 
	 * @param request
	 * @param errors
	 */
	private void validateAmbitos(HttpServletRequest request, ActionErrors errors) {
		String[] codRefObjetosAmbito = getCodRefObjetosAmbito(request);
		String[] nombreObjetosAmbito = getNombreObjetosAmbito(request);
		if (!ArrayUtils.isEmpty(codRefObjetosAmbito)) {
			String[] codRefs = (String[]) ArrayUtils.clone(codRefObjetosAmbito);
			for (int i = codRefs.length - 1; i >= 0; i--) {
				if (StringUtils.isBlank(codRefs[i]))
					codRefs = (String[]) ArrayUtils.remove(codRefs, i);
				else {
					for (int j = i - 1; j >= 0; j--) {
						if (codRefs[i].equals(codRefs[j])) {
							errors.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_AMBITO_REPETIDO,
											nombreObjetosAmbito[i]));
						} else if (StringUtils.indexOf(codRefs[i], codRefs[j]) == 0) {
							errors.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_AMBITO_CONTENIDO,
											nombreObjetosAmbito[i],
											nombreObjetosAmbito[j]));
						} else if (StringUtils.indexOf(codRefs[j], codRefs[i]) == 0) {
							errors.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_AMBITO_CONTENIDO,
											nombreObjetosAmbito[j],
											nombreObjetosAmbito[i]));
						}
					}
				}
			}
		}
	}

	/**
	 * Valida el formulario
	 * 
	 * @param request
	 * @param consultaUnidadesDocumentalesForm
	 * @param errors
	 * @return
	 */
	public ActionErrors validateForm(HttpServletRequest request,
			ConsultaUnidadesDocumentalesForm consultaUnidadesDocumentalesForm,
			ActionErrors errors) {
		if (!DisplayTagUtils.isPaginating(request)
				&& !DisplayTagUtils.isSorting(request)) {
			validateAmbitos(request, errors);
			validateFechaEntrega(request, consultaUnidadesDocumentalesForm,
					errors);
			validateFechaDevolucion(request, consultaUnidadesDocumentalesForm,
					errors);
			validateTipoServicio(request, consultaUnidadesDocumentalesForm,
					errors);

			if (StringUtils.isNotEmpty(consultaUnidadesDocumentalesForm
					.getFechaAIni())
					&& StringUtils.isNotEmpty(consultaUnidadesDocumentalesForm
							.getFechaAFin())) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_FECHA_ENTREGA_Y_DEVOLUCION));
			}
		}

		return errors;
	}

	/**
	 * @return Devuelve una lista de PropertyBeans con los valores de los tipos
	 *         de servicio existentes 1=Prestamo 2=Consulta
	 */
	private List getTiposServicio() {

		List list = new ArrayList();

		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setLabel(PrestamosConstants.TIPO_SERVICIO_PRESTAMO);
		propertyBean.setValue(String
				.valueOf(PrestamosConstants.TIPO_SOLICITUD_PRESTAMO));
		list.add(propertyBean);

		propertyBean = new PropertyBean();
		propertyBean.setLabel(PrestamosConstants.TIPO_SERVICIO_CONSULTA);
		propertyBean.setValue(String
				.valueOf(ConsultasConstants.TIPO_SOLICITUD_CONSULTA));
		list.add(propertyBean);

		return list;

	}
}
