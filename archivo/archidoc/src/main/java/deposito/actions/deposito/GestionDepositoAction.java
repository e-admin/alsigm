package deposito.actions.deposito;

import fondos.vos.BusquedaElementosVO;
import gcontrol.ControlAccesoConstants;
import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.ServiceClient;
import util.ErrorsTag;
import util.PaginatedList;
import util.TreeNode;
import util.TreeView;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionFondosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.pagination.PageInfo;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.DisplayTagUtils;
import common.util.ListUtils;

import deposito.DepositoConstants;
import deposito.actions.ErrorKeys;
import deposito.forms.UbicacionForm;
import deposito.model.DepositoException;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoVO;

public class GestionDepositoAction extends BaseAction {

	public void altaUbicacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Comprobar que el usuario pertenece a una archivo
		if (ListUtils.isEmpty(getAppUser(request).getCustodyArchiveList())) {
			ActionErrors errores = new ActionErrors();

			errores.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_CREAR_UBICACION_USUARIO_SIN_ARCHIVOS));

			ErrorsTag.saveErrors(request, errores);

			goLastClientExecuteLogic(mappings, form, request, response);
		} else {
			saveCurrentInvocation(
					KeysClientsInvocations.DEPOSITO_CREAR_ELEMENTO, request);

			ServiceRepository services = getServiceRepository(request);
			GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
			GestionArchivosBI archivoBI = services.lookupGestionArchivosBI();

			UbicacionForm ubicacionForm = (UbicacionForm) form;
			ubicacionForm.clear();

			removeInTemporalSession(request,
					ControlAccesoConstants.LISTA_ARCHIVOS);

			List archivos = null;
			if (ConfigConstants.getInstance().getMostrarTodasUbicaciones()) {
				archivos = sistemaBI.getArchivos();
			} else {
				List custodyArchiveList = services.getServiceClient()
						.getCustodyArchiveList();
				archivos = archivoBI.getArchivosXId(custodyArchiveList
						.toArray());
			}

			if (!ListUtils.isEmpty(archivos) && archivos.size() == 1) {
				ArchivoVO archivoVO = (ArchivoVO) archivos.get(0);
				ubicacionForm.setIdArchivo(archivoVO.getId());
				ubicacionForm.setNombreArchivo(archivoVO.getNombre());
			} else {
				setInTemporalSession(request,
						ControlAccesoConstants.LISTA_ARCHIVOS, archivos);
			}

			setReturnActionFordward(request,
					mappings.findForward("edicion_deposito"));
		}
	}

	ActionErrors validarFormularioEdicion(HttpServletRequest request,
			UbicacionForm form) {
		ActionErrors errores = new ActionErrors();

		if (StringUtils.isBlank(form.getNombre())) {
			errores.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		} else {

			ServiceRepository services = getServiceRepository(request);
			GestorEstructuraDepositoBI depositoBI = services
					.lookupGestorEstructuraDepositoBI();
			Collection ubicaciones = depositoBI.getEdificios();
			UbicacionForm ubicacionForm = (UbicacionForm) form;
			String idUbicacion = ubicacionForm.getIdUbicacion();
			if (ubicaciones != null && ubicaciones.size() > 0) { // Comprobar
																	// que el
																	// nombre de
																	// la
																	// ubicación
																	// no exista
																	// ya en la
																	// lista
																	// existente
				boolean repetido = false;
				Iterator it = ubicaciones.iterator();
				while (it.hasNext() && !repetido) {
					DepositoVO dvo = (DepositoVO) it.next();
					if (dvo.getNombre() != null) {
						if (!idUbicacion.equalsIgnoreCase(dvo.getId())
								&& dvo.getNombre().toLowerCase()
										.equals(form.getNombre().toLowerCase())) {
							errores.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											ErrorKeys.NOMBRE_UBICACION_REPETIDO));
							repetido = true;
						}
					}
				}
			}
		}

		String caracteresInvalidos = Constants.SLASH;
		if (Constants.hasForbidenChars(form.getNombre(), caracteresInvalidos)) {
			errores.add(
					Constants.ERROR_INVALID_CHARACTERS,
					new ActionError(Constants.ERROR_INVALID_CHARACTERS,
							Messages.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale()), caracteresInvalidos,
							request.getLocale()));

		}

		if (StringUtils.isBlank(form.getUbicacion())) {
			errores.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_UBICACION,
									request.getLocale())));
		}

		return errores;
	}

	public void guardarUbicacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UbicacionForm ubicacionForm = (UbicacionForm) form;

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		// Modificado por Alicia para comprobar que el nombre de ubicación no
		// exista ya
		// ActionErrors errores = validarFormularioEdicion(ubicacionForm);
		ActionErrors errores = validarFormularioEdicion(request, ubicacionForm);
		if (errores.size() > 0) {
			obtenerErrores(request, true).add(errores);
			setReturnActionFordward(request,
					mappings.findForward("edicion_deposito"));
		} else {
			TreeView depositoTreeView = (TreeView) getFromTemporalSession(
					request, DepositoConstants.DEPOSITO_VIEW_NAME);
			DepositoVO ubicacion = null;
			try {
				if (StringUtils.isBlank(ubicacionForm.getIdUbicacion())) {
					ubicacion = new DepositoVO();
					ubicacion.setNombre(ubicacionForm.getNombre());
					ubicacion.setUbicacion(ubicacionForm.getUbicacion());
					ubicacion.setIdArchivo(ubicacionForm.getIdArchivo());

					depositoBI.guardarUbicacion(ubicacion);
					if (depositoTreeView != null) {
						TreeNode newViewNode = depositoTreeView.insertNode(
								null, ubicacion);
						depositoTreeView.setSelectedNode(newViewNode);
					}
				} else {
					ubicacion = depositoBI.getUbicacion(ubicacionForm
							.getIdUbicacion());
					ubicacion.setNombre(ubicacionForm.getNombre());
					ubicacion.setUbicacion(ubicacionForm.getUbicacion());
					ubicacion.setIdArchivo(ubicacionForm.getIdArchivo());
					depositoBI.guardarUbicacion(ubicacion);
					if (depositoTreeView != null) {
						TreeNode nodoModificado = depositoTreeView
								.findNode(ubicacion);
						nodoModificado.setTreeModelItem(ubicacion);
					}
				}
				ActionRedirect vistaUbicacion = new ActionRedirect(
						mappings.findForward("redirect_to_info_ubicacion"),
						true);
				vistaUbicacion.addParameter("idUbicacion", ubicacion.getId());
				vistaUbicacion.addParameter("refreshView", "true");
				popLastInvocation(request);
				setReturnActionFordward(request, vistaUbicacion);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mappings.findForward("edicion_deposito"));
			}
		}
	}

	public void verUbicacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		String pIdUbicacion = request.getParameter("idUbicacion");
		DepositoVO ubicacion = depositoBI.getUbicacion(pIdUbicacion);

		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();

		ArchivoVO archivoVO = sistemaBI.getArchivo(ubicacion.getIdArchivo());
		ubicacion.setNombreArchivo(archivoVO.getNombre());

		List descendientes = depositoBI.getHijosElemento(ubicacion.getId(),
				ubicacion.getIdTipoElemento());
		request.setAttribute(DepositoConstants.EDIFICIO_KEY, ubicacion);
		setInTemporalSession(request, DepositoConstants.EDIFICIO_KEY, ubicacion);

		request.setAttribute(DepositoConstants.LISTA_DESCENDIENTES_KEY,
				descendientes);

		request.setAttribute(DepositoConstants.RESUMEN_OCUPACION,
				depositoBI.getResumenOcupacion(ubicacion));

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_VER_ELEMENTO, request);
		invocation
				.setTitleNavigationToolBar(TitlesToolBar.DEPOSITO_VER_ELEMENTO
						+ ubicacion.getIdTipoElemento());

		setReturnActionFordward(request, mappings.findForward("info_ubicacion"));
	}

	public void verOcupacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		String pIdUbicacion = request.getParameter("idUbicacion");
		DepositoVO ubicacion = depositoBI.getUbicacion(pIdUbicacion);
		request.setAttribute(DepositoConstants.ELEMENTO_DEPOSITO_KEY, ubicacion);

		request.setAttribute("__INFORME_OCUPACION",
				depositoBI.getInformeOcupacionDeposito(pIdUbicacion));

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_INFORME_OCUPACION, request);
		setReturnActionFordward(request,
				mappings.findForward("informe_ocupacion"));
	}

	public void editarUbicacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UbicacionForm ubicacionForm = (UbicacionForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
		String pIdUbicacion = request.getParameter("idUbicacion");
		DepositoVO ubicacion = depositoBI.getUbicacion(pIdUbicacion);
		ubicacionForm.setIdUbicacion(ubicacion.getId());
		ubicacionForm.setNombre(ubicacion.getNombre());
		ubicacionForm.setUbicacion(ubicacion.getUbicacion());

		ArchivoVO archivoVO = sistemaBI.getArchivo(ubicacion.getIdArchivo());
		ubicacionForm.setIdArchivo(ubicacion.getIdArchivo());
		ubicacionForm.setNombreArchivo(archivoVO.getNombre());

		removeInTemporalSession(request, ControlAccesoConstants.LISTA_ARCHIVOS);
		GestionArchivosBI archivoBI = services.lookupGestionArchivosBI();
		List archivos = null;
		if (ConfigConstants.getInstance().getMostrarTodasUbicaciones()) {
			archivos = sistemaBI.getArchivos();
		} else {
			List custodyArchiveList = services.getServiceClient()
					.getCustodyArchiveList();
			archivos = archivoBI.getArchivosXId(custodyArchiveList.toArray());
		}

		// Si tiene algún hueco no se puede editar
		if (ListUtils.isNotEmpty(archivos) && archivos.size() > 1
				&& depositoBI.isEditableArchivoUbicacion(ubicacion.getId())) {

			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_ARCHIVOS, archivos);
		}

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_EDITAR_ELEMENTO,
				request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_deposito"));
	}

	public void eliminarUbicacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		String pIdUbicacion = request.getParameter("idUbicacion");
		try {
			ElementoVO elementoEliminado = depositoBI.eliminarElemento(
					pIdUbicacion, DepositoVO.ID_TIPO_ELEMENTO_UBICACION);
			TreeView depositoTreeView = (TreeView) getFromTemporalSession(
					request, DepositoConstants.DEPOSITO_VIEW_NAME);
			if (depositoTreeView != null) {
				TreeNode nodoEliminado = depositoTreeView
						.findNode(elementoEliminado);
				depositoTreeView.setSelectedNode(nodoEliminado.getParent());
				depositoTreeView.removeNode(nodoEliminado);
			}
			popLastInvocation(request);
			ClientInvocation lastInvocation = getInvocationStack(request)
					.getLastClientInvocation();
			ActionRedirect forward = new ActionRedirect(new ActionForward(
					lastInvocation.getInvocationURI()));
			forward.addParameter("refreshView", "true");
			setReturnActionFordward(request, forward);
		} catch (DepositoException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void nuevaBusquedaUIExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// TODO: precargar ámbito de ubicaciones
		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_BUSQUEDA_UINST,
				request);

		// lista de fondos y lista de formatos
		removeInTemporalSession(request,
				DepositoConstants.LISTADO_FONDOS_BUSQUEDA);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionFondosBI fondosService = services.lookupGestionFondosBI();
		Collection fondos = fondosService.getFondosVigentes();
		setInTemporalSession(request,
				DepositoConstants.LISTADO_FONDOS_BUSQUEDA, fondos);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		removeInTemporalSession(request, DepositoConstants.LISTA_FORMATOS);
		setInTemporalSession(request, DepositoConstants.LISTA_FORMATOS,
				depositoBI.getFormatosVigentes());

		initBusqueda(request);
		setReturnActionFordward(request, mappings.findForward("buscar_ui"));
	}

	public void buscarUIExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		UbicacionForm ubicacionForm = (UbicacionForm) form;
		// el campo ubicacion es obligatorio
		if (StringUtils.isEmpty(ubicacionForm.getIdUbicacion())) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(DepositoConstants.UBICACION,
									request.getLocale())));

		}

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestorEstructuraDepositoBI estructuraDepositoBI = services
				.lookupGestorEstructuraDepositoBI();

		if (obtenerErrores(request, true).isEmpty()) {

			if (request.getParameter("tipoBusqueda") == null) {
				initBusqueda(request);
			}
			int numMaxResultados = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
					.getNumMaxResultados();
			Object limitarResultadosBusqueda = request
					.getAttribute(DepositoConstants.LIMITAR_RESULTADOS_BUSQUEDA);
			if (limitarResultadosBusqueda != null
					&& ((Boolean) limitarResultadosBusqueda).booleanValue())
				numMaxResultados = Integer.MAX_VALUE;

			int numeroElmentosPorPagina = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
					.getNumResultadosPorPagina();
			boolean isExportAction = PageInfo.checkExportingList(request);

			setInTemporalSession(request,
					DepositoConstants.LISTADO_UNIDS_INST_BUSQUEDA_KEY, null);

			SortOrderEnum currentOrderDirection = getOrderCurrentDirection(request);
			String orderColumn = getOrderColumn(request);

			BusquedaElementosVO busquedaElementosVO = makeBusquedaElementosVO(
					request, new BusquedaElementosVO(), ubicacionForm,
					currentOrderDirection, orderColumn);
			if (!DisplayTagUtils.isPaginating(request)) {
				removeInTemporalSession(request,
						DepositoConstants.LISTA_IDS_ELEMENTOS);
			}
			// TODO: realizar la búsqueda y establecer en sesión la lista
			// resultado obtenida
			try {
				List listaIdsElementos = getIdsElementos(request,
						busquedaElementosVO, estructuraDepositoBI,
						numMaxResultados);

				if (!ListUtils.isEmpty(listaIdsElementos)) {
					setInTemporalSession(request,
							DepositoConstants.LISTA_IDS_ELEMENTOS,
							listaIdsElementos);

					int pageNumber = getPageNumber(request);

					List idsTiposToShow = null;
					if (isExportAction)
						idsTiposToShow = ListUtils.getItems(1,
								listaIdsElementos.size(), listaIdsElementos);
					else
						idsTiposToShow = ListUtils.getItems(pageNumber,
								numeroElmentosPorPagina, listaIdsElementos);

					List ltElements = getElementos(idsTiposToShow,
							busquedaElementosVO, estructuraDepositoBI);

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

					setInTemporalSession(request,
							DepositoConstants.LISTADO_UNIDS_INST_BUSQUEDA_KEY,
							paginatedList);
					setInTemporalSession(request, Constants.LAST_ORDER,
							orderColumn);
					setInTemporalSession(request,
							Constants.LAST_ORDER_DIRECTION,
							currentOrderDirection);
					setInTemporalSession(request, Constants.PAGE_NUMBER,
							new Integer(pageNumber));
				} else {
					setInTemporalSession(request,
							DepositoConstants.LISTADO_UNIDS_INST_BUSQUEDA_KEY,
							new ArrayList());
				}
			} catch (TooManyResultsException e) {
				guardarError(request, e);
			} catch (Throwable e) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_GENERAL_MESSAGE, e
								.getMessage()));
			}
		}
		if (!DisplayTagUtils.isDisplayTagOperation(request)) {
			saveCurrentInvocation(
					KeysClientsInvocations.DEPOSITO_BUSQUEDA_UINST, request);
		}

		setReturnActionFordward(request, mappings.findForward("buscar_ui"));
	}

	public void generarInformeBusquedaDepositoExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws DepositoException {
		SortOrderEnum currentOrderDirection = getOrderCurrentDirection(request);
		String orderColumn = getOrderColumn(request);
		UbicacionForm ubicacionForm = (UbicacionForm) form;

		setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
				new Integer(0));
		removeInTemporalSession(request, Constants.CANCEL_PROGRESSBAR_KEY);
		BusquedaElementosVO busquedaElementosVO = makeBusquedaElementosVO(
				request, new BusquedaElementosVO(), ubicacionForm,
				currentOrderDirection, orderColumn);
		setInTemporalSession(request, DepositoConstants.VO_BUSQUEDA_DEPOSITO,
				busquedaElementosVO);
		request.setAttribute(DepositoConstants.LIMITAR_RESULTADOS_BUSQUEDA,
				new Boolean(true));
		buscarUIExecuteLogic(mappings, form, request, response);
		if (!obtenerErrores(request, true).isEmpty())
			return;

		removeInTemporalSession(request,
				DepositoConstants.LISTADO_UNIDS_INST_BUSQUEDA_KEY);
		// redireccionar a la action que genera el informe
		setInTemporalSession(request,
				Constants.FORWARD_GENERAR_INFORME_LISTADO,
				"informe_busqueda_deposito");
		setReturnActionFordward(request,
				mappings.findForward("sel_formato_informe_listado"));
	}

	public boolean isAmbitoVacio(UbicacionForm ubicacionForm) {
		return (ubicacionForm.getIdAmbito() == null
				|| ubicacionForm.getIdAmbito().length == 0 || StringUtils
				.isEmpty(ubicacionForm.getIdAmbito()[0]));
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

	private BusquedaElementosVO makeBusquedaElementosVO(
			HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO,
			UbicacionForm ubicacionForm, SortOrderEnum orderDirection,
			String orderColumn) {

		busquedaElementosVO.setIdUbicacion(ubicacionForm.getIdUbicacion());
		if (!isAmbitoVacio(ubicacionForm)) {
			busquedaElementosVO.setIdAmbito(ubicacionForm.getIdAmbito());
			busquedaElementosVO.setCodOrdenAmbito(ubicacionForm
					.getCodOrdenAmbito());
		}

		busquedaElementosVO.setSignaturaLike(ubicacionForm.getSignatura_like());
		busquedaElementosVO.setSignatura(ubicacionForm.getSignatura());
		busquedaElementosVO.setFondo(ubicacionForm.getFondo());
		busquedaElementosVO.setFormato(ubicacionForm.getFormato());
		busquedaElementosVO.setOrderColumnName(orderColumn);

		if (SortOrderEnum.ASCENDING.getName().equalsIgnoreCase(
				orderDirection.getName()))
			busquedaElementosVO.setTipoOrdenacion(" ASC ");
		else
			busquedaElementosVO.setTipoOrdenacion(" DESC ");

		/* Fecha */
		CustomDateRange range = CustomDateFormat.getDateRange(
				ubicacionForm.getFechaComp(),
				new CustomDate(ubicacionForm.getFechaFormato(), ubicacionForm
						.getFechaA(), ubicacionForm.getFechaM(), ubicacionForm
						.getFechaD(), ubicacionForm.getFechaS()),
				new CustomDate(ubicacionForm.getFechaIniFormato(),
						ubicacionForm.getFechaIniA(), ubicacionForm
								.getFechaIniM(), ubicacionForm.getFechaIniD(),
						ubicacionForm.getFechaIniS()),
				new CustomDate(ubicacionForm.getFechaFinFormato(),
						ubicacionForm.getFechaFinA(), ubicacionForm
								.getFechaFinM(), ubicacionForm.getFechaFinD(),
						ubicacionForm.getFechaFinS()));

		busquedaElementosVO.setFechaIni(range.getInitialDate());
		busquedaElementosVO.setFechaFin(range.getFinalDate());
		busquedaElementosVO.setFechaIniOperador(ubicacionForm.getFechaComp());

		return busquedaElementosVO;

	}

	public List getIdsElementos(HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO,
			GestorEstructuraDepositoBI estructuraDepositoBI, int numMaxResults)
			throws TooManyResultsException {
		List listaIdsElementos = new ArrayList();

		CustomDateRange rangoFechas = new CustomDateRange();
		rangoFechas.setInitialDate(busquedaElementosVO.getFechaIni());
		rangoFechas.setFinalDate(busquedaElementosVO.getFechaFin());

		if (DisplayTagUtils.isPaginating(request)) {
			listaIdsElementos = (List) getFromTemporalSession(request,
					DepositoConstants.LISTA_IDS_ELEMENTOS);
		} else {
			// si se selecciono ambitos, la ubicacion del VO llega vacia.
			if (busquedaElementosVO.getCodOrdenAmbito() == null
					|| busquedaElementosVO.getCodOrdenAmbito().length == 0) {
				// para evitar el cruze de mas tablas de las necesarias.
				listaIdsElementos = estructuraDepositoBI
						.getIdsUnidsInstalacion(
								busquedaElementosVO.getIdUbicacion(),
								busquedaElementosVO.getSignaturaLike(),
								busquedaElementosVO.getSignatura(),
								busquedaElementosVO.getFondo(),
								busquedaElementosVO.getFormato(),
								numMaxResults, rangoFechas);
			} else {
				listaIdsElementos = estructuraDepositoBI
						.getIdsUnidsInstalacion(
								busquedaElementosVO.getIdUbicacion(),
								busquedaElementosVO.getCodOrdenAmbito(),
								busquedaElementosVO.getSignaturaLike(),
								busquedaElementosVO.getSignatura(),
								busquedaElementosVO.getFondo(),
								busquedaElementosVO.getFormato(),
								numMaxResults, rangoFechas);
			}
		}
		return listaIdsElementos;
	}

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

	private List getElementos(List idsToShow,
			BusquedaElementosVO busquedaElementosVO,
			GestorEstructuraDepositoBI estructuraDepositoBI) {
		List listaElementos = new ArrayList();
		listaElementos = estructuraDepositoBI.getUnidsInstalacion(idsToShow,
				busquedaElementosVO);

		return listaElementos;
	}

	private void initBusqueda(HttpServletRequest request) {

		SortOrderEnum currentOrderDirection = SortOrderEnum.ASCENDING;
		int pageNumber = DisplayTagUtils.getDefaultPageNumber();

		setInTemporalSession(request,
				DepositoConstants.LISTADO_UNIDS_INST_BUSQUEDA_KEY, null);
		setInTemporalSession(request, Constants.LAST_ORDER, null);
		setInTemporalSession(request, Constants.LAST_ORDER_DIRECTION,
				currentOrderDirection);
		setInTemporalSession(request, Constants.PAGE_NUMBER, new Integer(
				pageNumber));
	}
}
