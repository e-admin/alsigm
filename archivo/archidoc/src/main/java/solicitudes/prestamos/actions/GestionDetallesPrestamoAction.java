package solicitudes.prestamos.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.exceptions.DetalleNotFoundException;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import solicitudes.prestamos.forms.DetallePrestamoForm;
import solicitudes.prestamos.utils.ExceptionMapper;
import solicitudes.prestamos.utils.PrestamosUtils;
import solicitudes.prestamos.view.DetallePrestamoPO;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import solicitudes.prestamos.vos.PrestamoPO;
import solicitudes.prestamos.vos.PrestamoToPO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;
import solicitudes.vos.BusquedaDetalleVO;
import solicitudes.vos.MotivoRechazoVO;
import util.CollectionUtils;
import util.ErrorsTag;
import util.PaginatedList;
import util.StringOwnTokenizer;
import xml.config.Busqueda;
import xml.config.CampoBusqueda;
import xml.config.ConfiguracionArchivoManager;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoErrorCodes;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionRechazosBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.exceptions.ColumnNotIndexedException;
import common.exceptions.NotCheckedException;
import common.exceptions.SecurityException;
import common.exceptions.SintaxErrorException;
import common.exceptions.TooManyResultsException;
import common.exceptions.WordOmittedException;
import common.navigation.ClientInvocation;
import common.navigation.InvocationStack;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.DisplayTagUtils;
import common.util.ListUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.UDocEnUiDepositoVO;
import descripcion.actions.ElementoCFToPO;
import descripcion.model.TipoCampo;
import descripcion.model.TipoNiveles;
import descripcion.model.xml.card.TipoFicha;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.ElementoCFPO;
import descripcion.vos.ElementoCFVO;
import fondos.FondosConstants;
import fondos.model.CamposBusquedas;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.PrecondicionesBusquedaFondosGenerica;
import fondos.utils.BusquedasHelper;
import fondos.vos.BusquedaElementosVO;

/**
 * Action que engloba todas las posibles acciones sobre los
 * detalles(U.documentales) de un prestamo.
 */
public class GestionDetallesPrestamoAction extends BaseAction {

	/** Logger de la clase */
	private static Logger logger = Logger
			.getLogger(GestionDetallesPrestamoAction.class);

	// Identificador de la display tag de resultado de la búsqueda de unidades
	// documentales
	private static final String ID_DISPLAY_TAG = "udocs";

	private Busqueda getCfgBusquedaPrestamos(HttpServletRequest request) {
		String key = getKeyCfgBusquedas(request,
				ConfiguracionArchivoManager.PRESTAMOS);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;
	}

	/**
	 * Prepara el formulario para dar de alta un nuevo detalle(U-documental) de
	 * un préstamo.
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
	public void nuevoDetalleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el formulario
		DetallePrestamoForm detallePrestamoForm = (DetallePrestamoForm) form;

		// Limpiamos los valores que deben estar vacíos
		removeInTemporalSession(request, Constants.LAST_ORDER);
		removeInTemporalSession(request, Constants.LAST_ORDER_DIRECTION);
		removeInTemporalSession(request, Constants.PAGE_NUMBER);
		removeInTemporalSession(request,
				PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
		removeInTemporalSession(request,
				PrestamosConstants.LISTA_IDS_ELEMENTOS_CF);

		// Insertamos este punto como el punto de navegacion actual
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_NUEVODETALLE,
				request);

		// Movido a después del save. Establecer la configuración de la búsqueda
		// particular
		Busqueda busqueda = getCfgBusquedaPrestamos(request);
		setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
				busqueda);
		// Si aparece el campo de entrada de búsqueda avanzada, limitamos la
		// lista de fichas a las de nivel documental
		PrecondicionesBusquedaFondosGenerica precondiciones = new PrecondicionesBusquedaFondosGenerica();
		precondiciones
				.setTiposNivelFicha(new int[] { TipoNiveles.UNIDAD_DOCUMENTAL_VALUE });
		BusquedasHelper.loadListasBusqueda(busqueda, detallePrestamoForm,
				request, precondiciones);

		setInTemporalSession(request, FondosConstants.IS_PRESTAMO, "true");
		// Redirigimos a la página adecuada
		setReturnActionFordward(request,
				mappings.findForward("nuevo_detalle_prestamo"));
	}

	/**
	 * Limpia valores de sesión para preparar una nueva búsqueda de Unidades
	 * Documentales a añadir al préstamo
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
	public void nuevaBusquedaUDocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Limpiamos los valores que deben estar vacíos
		removeInTemporalSession(request, Constants.LAST_ORDER);
		removeInTemporalSession(request, Constants.LAST_ORDER_DIRECTION);
		removeInTemporalSession(request, Constants.PAGE_NUMBER);
		removeInTemporalSession(request,
				PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
		removeInTemporalSession(request,
				PrestamosConstants.LISTA_IDS_ELEMENTOS_CF);

		// Realizamos la búsqueda
		buscarUDocsExecuteLogic(mappings, form, request, response);
	}

	// private String getOrderColumn(HttpServletRequest request, Busqueda
	// busqueda)
	// {
	// //String orderColumn = ElementoCFVO.getDefaultOrderingColName();
	// int orderColumnPos =
	// ElementoCFVO.getColumnNumberByXMLColumnName((String)busqueda.getCamposOrdenacion().get(0));
	// String orderColumn = ElementoCFVO.getColumnNameByNumber(orderColumnPos);
	//
	// if(DisplayTagUtils.isPaginating(request))
	// {
	// String lastOrderColumn = (String)getFromTemporalSession(request,
	// Constants.LAST_ORDER);
	// //String lastOrderColumn =
	// (String)request.getAttribute(Constants.LAST_ORDER);
	// if(lastOrderColumn!=null)
	// orderColumn=lastOrderColumn;
	// }
	// else
	// {
	// if(DisplayTagUtils.getOrderColumn(request)!=null)
	// orderColumn = DisplayTagUtils.getOrderColumn(request);
	// }
	// return orderColumn;
	// }

	// private SortOrderEnum getOrderCurrentDirection(HttpServletRequest
	// request, Busqueda busqueda)
	// {
	// SortOrderEnum currentOrderDirection=null;
	// SortOrderEnum
	// lastOrderDirection=(SortOrderEnum)getFromTemporalSession(request,
	// Constants.LAST_ORDER_DIRECTION);
	// //SortOrderEnum lastOrderDirection =
	// (SortOrderEnum)request.getAttribute(Constants.LAST_ORDER_DIRECTION);
	//
	// if (lastOrderDirection == null) lastOrderDirection =
	// busqueda.getTipoOrdenacionEnum();
	//
	// if(DisplayTagUtils.isPaginating(request))
	// {
	// //if(lastOrderDirection==null) return SortOrderEnum.ASCENDING;
	// if(lastOrderDirection==null) return busqueda.getTipoOrdenacionEnum();
	// else return lastOrderDirection;
	// }
	//
	// else
	// {
	//
	// String orderColumn = getOrderColumn(request, busqueda);
	// String lastOrderColumn = (String)getFromTemporalSession(request,
	// Constants.LAST_ORDER);
	// //String lastOrderColumn =
	// (String)request.getAttribute(Constants.LAST_ORDER);
	//
	// if(orderColumn.equals(lastOrderColumn)) //Misma columna
	// {
	//
	// if(lastOrderDirection==null)
	// {
	// currentOrderDirection=busqueda.getTipoOrdenacionEnum();
	// }
	// else
	// {
	// if(SortOrderEnum.ASCENDING.getName().equalsIgnoreCase(lastOrderDirection.getName()))
	// {
	// currentOrderDirection=SortOrderEnum.DESCENDING;
	// }
	// else
	// {
	// currentOrderDirection=SortOrderEnum.ASCENDING;
	// }
	// }
	// }
	// else //Distinta columna
	// {
	// if(lastOrderDirection==null)
	// {
	// currentOrderDirection=busqueda.getTipoOrdenacionEnum();
	// }
	// else
	// {
	// currentOrderDirection=lastOrderDirection;
	// }
	// }
	//
	// return currentOrderDirection;
	// }
	//
	// }

	private BusquedaElementosVO fillBusquedaElementosVO(
			HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda) {

		busquedaElementosVO.setOrderColumn(ElementoCFVO
				.getColumnNumberByName(getOrderColumn(request, busqueda)));
		busquedaElementosVO
				.setTipoOrdenacion(((SortOrderEnum) getOrderCurrentDirection(
						request, busqueda)).getName());

		return busquedaElementosVO;
	}

	private List getIdsElementos(HttpServletRequest request,
			BusquedaElementosVO busquedaElementosVO,
			GestionCuadroClasificacionBI service, int numMaxResults,
			Busqueda busqueda) throws TooManyResultsException {
		List listaIdsElementos = null;

		if (DisplayTagUtils.isPaginating(request)) {
			listaIdsElementos = (List) getFromTemporalSession(request,
					PrestamosConstants.LISTA_IDS_ELEMENTOS_CF);
		} else {
			busquedaElementosVO = fillBusquedaElementosVO(request,
					busquedaElementosVO, busqueda);

			if (DisplayTagUtils.isSorting(request))
				// listaIdsElementos =
				// service.getIdsElementos(busquedaElementosVO,0,busqueda);
				// TODO: Ojo a este cambio!!! Probar muy bien las búsquedas de
				// udocs para préstamos
				listaIdsElementos = service.getIdsElementosYPartes(
						busquedaElementosVO, 0, busqueda);
			else
				// TODO: Ojo a este cambio!!! Probar muy bien las búsquedas de
				// udocs para préstamos
				// listaIdsElementos =
				// service.getIdsElementos(busquedaElementosVO,numMaxResults,busqueda);
				listaIdsElementos = service.getIdsElementosYPartes(
						busquedaElementosVO, numMaxResults, busqueda);
		}
		return listaIdsElementos;
	}

	private int getPageNumber(HttpServletRequest request) {
		int pageNumber = 1;
		if (DisplayTagUtils.getPageNumber(request) != null) {
			pageNumber = Integer.parseInt(DisplayTagUtils
					.getPageNumber(request));
		} else if (getFromTemporalSession(request, Constants.PAGE_NUMBER) != null)
		// else if(request.getAttribute(Constants.PAGE_NUMBER)!=null)
		{
			// pageNumber=((Integer)request.getAttribute(Constants.PAGE_NUMBER)).intValue();
			pageNumber = ((Integer) getFromTemporalSession(request,
					Constants.PAGE_NUMBER)).intValue();
		}
		return pageNumber;
	}

	/**
	 * Prepara el formulario para dar de alta un nuevo detalle(U-documental) de
	 * un préstamo.
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
	public void buscarUDocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Mostrar la lista de sesión si se ha hecho un goBack
		PaginatedList paginatedList = (PaginatedList) getFromTemporalSession(
				request, PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);

		// Obtenemos el servicio de elementos del cuadro para hacer la búsqueda
		// de U.Docs
		GestionCuadroClasificacionBI elementosCFService = getGestionCuadroClasificacionBI(request);

		// Obtenemos el formulario
		DetallePrestamoForm detallePrestamoForm = (DetallePrestamoForm) form;

		// Obtener la configuración de la búsqueda
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);

		PrestamoVO prestamo = (PrestamoVO) getFromTemporalSession(request,
				PrestamosConstants.PRESTAMO_KEY);

		// Validar el formulario
		ActionErrors errores = BusquedasHelper.validateCampos(mappings,
				request, busqueda, detallePrestamoForm);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			try {
				int numMaxResultados = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral().getNumMaxResultados();
				int numeroElmentosPorPagina = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral().getNumResultadosPorPagina();

				// Si estamos ante una búsqueda donde no hace falta rellenar el
				// estado es necesario establecer que se busque en elementos
				// vigentes
				if (!busqueda.getMapEntrada().containsKey(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO)) {
					if (detallePrestamoForm.getEstados() == null
							|| (detallePrestamoForm.getEstados() != null && detallePrestamoForm
									.getEstados().length == 0))
						detallePrestamoForm
								.setEstados(new String[] { new Integer(
										IElementoCuadroClasificacion.VIGENTE)
										.toString() });
				}

				BusquedaElementosVO busquedaElementosVO = BusquedasHelper
						.getBusquedaElementosVO(busqueda, null,
								detallePrestamoForm);

				// Rellenamos el campo IdArchivo que es un campo de entrada
				// interno
				busquedaElementosVO.setIdArchivo(prestamo.getIdarchivo());

				busquedaElementosVO.getCamposRellenos().add(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_IDARCHIVO);

				// Añadir el campo de entrada Id_Archivo al ser obligatorio en
				// el préstamo
				CampoBusqueda campoArchivo = new CampoBusqueda();

				if (!busqueda.getMapEntrada().containsKey(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_IDARCHIVO)) {
					campoArchivo
							.setNombre(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_IDARCHIVO);
					campoArchivo.setMostrar(Constants.TRUE_STRING);
					campoArchivo.setTipo(CamposBusquedas.CAMPO_TIPO_TEXTO);
					busqueda.addCampoEntrada(campoArchivo);
				}

				// Obtenemos los Ids, Signatura de las unidades documentales
				List listaIdsElementos = getIdsElementos(request,
						busquedaElementosVO, elementosCFService,
						numMaxResultados, busqueda);
				SortOrderEnum currentOrderDirection = getOrderCurrentDirection(
						request, busqueda);
				String orderColumn = getOrderColumn(request, busqueda);

				if (!ListUtils.isEmpty(listaIdsElementos)) {
					setInTemporalSession(request,
							PrestamosConstants.LISTA_IDS_ELEMENTOS_CF,
							listaIdsElementos);

					int pageNumber = getPageNumber(request);

					// String[] idsToShow = DisplayTagUtils.getIds(pageNumber,
					// numeroElmentosPorPagina, listaIdsElementos);
					// Se pasa como número de elementos a mostrar el doble
					// porque hay el doble de strings identificadores, dos por
					// elemento
					String[] idsToShow = DisplayTagUtils.getIds(pageNumber,
							numeroElmentosPorPagina, listaIdsElementos);

					List ltElements = elementosCFService.getElementosYPartes(
							idsToShow,
							fillBusquedaElementosVO(request,
									busquedaElementosVO, busqueda), busqueda);

					// Transformar la lista de elementos en POS
					final ServiceRepository servicios = getServiceRepository(request);
					final Busqueda busquedaFinal = (Busqueda) getFromTemporalSession(
							request, FondosConstants.CFG_BUSQUEDA_KEY);
					CollectionUtils.transform(ltElements, new Transformer() {
						public Object transform(Object obj) {
							ElementoCFPO po = new ElementoCFPO(servicios,
									busquedaFinal, null, null);
							try {
								PropertyUtils.copyProperties(po, obj);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}

							return po;

						}
					});

					paginatedList = new PaginatedList();
					paginatedList.setFullListSize(listaIdsElementos.size());
					paginatedList.setList(ltElements);
					paginatedList.setObjectsPerPage(numeroElmentosPorPagina);
					paginatedList.setPageNumber(pageNumber);
					paginatedList.setSearchId(ID_DISPLAY_TAG);
					paginatedList.setSortCriterion(orderColumn.toString());
					paginatedList.setSortDirection(currentOrderDirection);

					setInTemporalSession(request,
							PrestamosConstants.LISTADO_BUSQUEDA_UDOCS,
							paginatedList);

					setInTemporalSession(request, Constants.LAST_ORDER,
							orderColumn);
					setInTemporalSession(request,
							Constants.LAST_ORDER_DIRECTION,
							currentOrderDirection);
					setInTemporalSession(request, Constants.PAGE_NUMBER,
							new Integer(pageNumber));
				}

				// Mostramos la lista de udocs
				request.setAttribute(
						PrestamosConstants.MOSTRAR_LISTADO_BUSQUEDA_UDOCS,
						new Boolean(true));

				// Redireccionamos a la pagina adecuada
				setReturnActionFordward(request,
						mappings.findForward("nuevo_detalle_prestamo"));
			} catch (TooManyResultsException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
								new Object[] { new Integer(e.getCount()),
										new Integer(e.getMaxNumResults()) }));

				setReturnActionFordward(request,
						mappings.findForward("nuevo_detalle_prestamo"));
			} catch (ColumnNotIndexedException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_COLUMNA_NO_INDEXADA));

				// goBackExecuteLogic(mappings, form, request, response);
				goLastClientExecuteLogic(mappings, form, request, response);
			} catch (WordOmittedException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_PALABRA_OMITIDA));

				// goBackExecuteLogic(mappings, form, request, response);
				goLastClientExecuteLogic(mappings, form, request, response);
			} catch (SintaxErrorException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_SINTAXIS_INCORRECTA));

				goBackExecuteLogic(mappings, form, request, response);
			} catch (SecurityException e) {
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_ERROR,
								new ActionError(
										"archigest.archivo.unidadesDocumentales.sinPermisos"));
				// goBackExecuteLogic(mappings, form, request, response);
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("nuevo_detalle_prestamo"));
		}
	}

	/**
	 * Elimina los detalles(udocs) asociados a un préstamo
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
	public void eliminarDetallesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Obtenemos los datos del formulario
		DetallePrestamoForm frm = (DetallePrestamoForm) form;
		// Almacenamos esta accion para volver al detalle
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_ELIMINARDETALLES, request);

		String[] idsUdocs = new String[frm.getDetallesseleccionados().length];
		String[] signaturasUdosc = new String[frm.getDetallesseleccionados().length];

		for (int i = 0; i < frm.getDetallesseleccionados().length; i++) {
			String idcompuesto = frm.getDetallesseleccionados()[i];

			String idudoc = idcompuesto.substring(0, idcompuesto.indexOf("|"));
			String signaturaudoc = idcompuesto.substring(
					idcompuesto.indexOf("|") + 1, idcompuesto.length());
			idsUdocs[i] = idudoc;
			signaturasUdosc[i] = signaturaudoc;
		}
		// Eliminamos en una transaccion todas las unidades seleccionadas
		prestamosService.eliminarDetallesPrestamo(frm.getIdsolicitud(),
				idsUdocs, signaturasUdosc);

		// Redirigimos a la página adecuada
		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Realiza la denegación de las unidades documentales seleccionadas de un
	 * determinado préstamo.
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
	public void denegardetallesprestamosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Guardamos el punto de navegacion actual para que se vuelva a ver el
		// prestamo
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_DENEGARDETALLESPRESTAMOS,
				request);

		try {
			// Ejecutamos la logica de autorizar
			denegarDetallesCodeLogic(mapping, form, request, response);
		} catch (PrestamoActionNotAllowedException panae) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					panae);

			ErrorsTag.saveErrors(request, errores);
		}

		// Redirigimos a la página adecuada
		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Realiza la autorización de las unidades documentales seleccionadas de un
	 * determinado préstamo.
	 * 
	 * @param idSolicitud
	 *            Identificiador de la solicitud a la que estan asociados los
	 *            préstamos.
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void autorizardetallesprestamosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DetallePrestamoForm frm = (DetallePrestamoForm) form;

		// Guardamos el punto de navegacion actual para que se vuelva a ver el
		// prestamo
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_AUTORIZARDETALLESPRESTAMOS,
				request);

		// Ejecutamos la logica de autorizar
		autorizarDetallesCodeLogic(frm.getDetallesseleccionados(),
				frm.getIdsolicitud(), mapping, form, request, response);

		// Redirigimos a la página adecuada
		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Realizar la devolución de los detalles de un préstamo.
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
	public void devolverdetallesprestamosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener el formulario
		DetallePrestamoForm frm = (DetallePrestamoForm) form;

		// Obtener el prestamo
		PrestamoVO prestamo = (PrestamoVO) getFromTemporalSession(request,
				PrestamosConstants.PRESTAMO_KEY);

		// Obtener el repositorio de servicios
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		// Obtenemos el servicio de descripción
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		ActionErrors errors = PrestamosUtils.validateDevolverDetallesPrestamos(
				frm.getDetallesseleccionados(), frm.getUdocsarevisardoc(),
				prestamo.getId(), prestamosService);
		if (errors.isEmpty()) {

			// Devolver los detalles de los prestamos
			devolverDetallesCodeLogic(frm.getDetallesseleccionados(),
					frm.getIdsolicitud(), mapping, form, request, response);

		} else {

			// Guardar los errores para poder mostrarlos
			ErrorsTag.saveErrors(request, errors);
		}

		// Obtenemos el servicio de solicitudes
		GestionSolicitudesBI solicitudesService = services
				.lookupGestionSolicitudesBI();

		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD,
				GestionPrestamosAction.METHOD_VERPRESTAMO);

		// Obtener los detalles de los prestamos
		Collection detallesPrestamos = prestamosService
				.obtenerDetallesPrestamoByUsuario(prestamo);

		// Establecemos los detalles
		setInTemporalSession(request, PrestamosConstants.DETALLE_PRESTAMO_KEY,
				detallesPrestamos);

		// Obtenemos el préstamo por su identificador y lo metemos en la sesion
		// temporal
		PrestamoVO prestamo_VO = prestamosService.verPrestamo(prestamo.getId());
		prestamo_VO = (PrestamoVO) solicitudesService
				.getAditionalSolicitudInformation(prestamo_VO);
		PrestamoPO prestamoPO = (PrestamoPO) PrestamoToPO.getInstance(
				request.getLocale(), services).transform(prestamo_VO);
		setInTemporalSession(request, PrestamosConstants.PRESTAMO_KEY,
				prestamoPO);

		// Establecer los botones a mostrar
		PrestamosUtils.establecerVistas(prestamo_VO, getAppUser(request),
				request, prestamosService, detallesPrestamos);

		// Mostrar el préstamo
		setReturnActionFordward(request, mapping.findForward("ver_prestamo"));
	}

	/**
	 * Realiza la denegación de las unidades documentales seleccionadas de un
	 * determinado préstamo.
	 * 
	 * @param idsDetallesPrestamos
	 *            Listado de los identificadores de las unidades documentales
	 *            seleccionados.
	 * @param idSolicitud
	 *            Identificador de la solicitud a la que van asociadas las
	 *            unidades documentales.
	 * @param motivorechazo
	 *            Motivo por el que se rechazan las unidades documentales del
	 *            préstamo.
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void denegarDetallesCodeLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws PrestamoActionNotAllowedException {
		AppUser appUser = getAppUser(request);
		ServiceClient sc = ServiceClient.create(appUser);
		sc.getProperties().put(PrestamosConstants.PROPERTY_ARCHIVOS_CUSTODIA,
				appUser.getCustodyArchiveList());
		ServiceRepository services = ServiceRepository.getInstance(sc);
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		String[] idsDetallesPrestamos = ((DetallePrestamoForm) form)
				.getDetallesseleccionados();
		String idSolicitud = ((DetallePrestamoForm) form).getIdsolicitud();
		// String motivorechazo= ((DetallePrestamoForm)form).getMotivorechazo();
		String idMotivoRechazo = ((DetallePrestamoForm) form)
				.getIdMotivoRechazo();

		GestionRechazosBI rechazosBI = services.lookupGestionMotivosRechazoBI();
		MotivoRechazoVO motivo = rechazosBI
				.getMotivoRechazoById(idMotivoRechazo);

		PrestamoVO prestamoVO = prestamosService.getPrestamo(idSolicitud);

		for (int i = 0; i < idsDetallesPrestamos.length; i++) {
			String idcompuesto = idsDetallesPrestamos[i];
			String idudoc = idcompuesto.substring(0, idcompuesto.indexOf("|"));
			String signaturaudoc = idcompuesto.substring(
					idcompuesto.indexOf("|") + 1, idcompuesto.length());

			prestamosService.denegarDetallePrestamo(prestamoVO, idudoc,
					signaturaudoc, motivo.getMotivo(), idMotivoRechazo);
		}
	}

	/**
	 * Realiza la autorización de las unidades documentales seleccionadas de un
	 * determinado préstamo.
	 * 
	 * @param idsDetallesPrestamos
	 *            Listado de los identificadores de los detalles de un prestamo
	 *            que se han seleccionado para su autorización.
	 * @param idSolicitud
	 *            Identificador de la solicitud a la que pertenecen las unidades
	 *            documentales
	 * @param idSolicitud
	 *            Identificiador de la solicitud a la que estan asociados los
	 *            préstamos.
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar la autorizacion del préstamo
	 */
	public void autorizarDetallesCodeLogic(String[] idsDetallesPrestamos,
			String idSolicitud, ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceClient sc = ServiceClient.create(appUser);
		sc.getProperties().put(PrestamosConstants.PROPERTY_ARCHIVOS_CUSTODIA,
				appUser.getCustodyArchiveList());
		ServiceRepository services = ServiceRepository.getInstance(sc);
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		PrestamoVO prestamoVO = prestamosService.getPrestamo(idSolicitud);

		List errSignatures = new ArrayList();
		for (int i = 0; i < idsDetallesPrestamos.length; i++) {
			String idcompuesto = idsDetallesPrestamos[i];
			String idudoc = idcompuesto.substring(0, idcompuesto.indexOf("|"));
			String signaturaudoc = idcompuesto.substring(
					idcompuesto.indexOf("|") + 1, idcompuesto.length());

			try {
				prestamosService.autorizarDetallePrestamo(prestamoVO, idudoc,
						signaturaudoc, sc);
			} catch (PrestamoActionNotAllowedException panae) {
				errSignatures.add(signaturaudoc);
			}
		}

		if (!errSignatures.isEmpty()) {
			ActionErrors errores = ExceptionMapper.getErrorsSignatures(
					ArchivoErrorCodes.ERROR_AUTORIZACION_DETALLE_NO_DISPONIBLE,
					errSignatures);

			ErrorsTag.saveErrors(request, errores);
		}

	}

	/**
	 * Encapsula la lógica de la devolución de los detalles de un préstamo.
	 * 
	 * @param idsDetallesPrestamos
	 *            Listado de los identificadores de los préstamos
	 * @param Identificador
	 *            de la solicitud.
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void devolverDetallesCodeLogic(String[] idsDetallesPrestamos,
			String idSolicitud, ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		// GestionUnidadDocumentalBI udocBI =
		// services.lookupGestionUnidadDocumentalBI();
		DetallePrestamoForm detalleForm = (DetallePrestamoForm) form;
		String[] udocsarevisardoc = detalleForm.getUdocsarevisardoc();

		ArrayList detalles = new ArrayList();
		List idsUdocs = new ArrayList();

		try {
			// Obtenemos el prestamo
			// PrestamoVO prestamoVO =
			// prestamosService.getPrestamo(idSolicitud);

			for (int i = 0; i < idsDetallesPrestamos.length; i++) {
				String idcompuesto = idsDetallesPrestamos[i];
				String idudoc = idcompuesto.substring(0,
						idcompuesto.indexOf("|"));
				String signaturaudoc = idcompuesto.substring(
						idcompuesto.indexOf("|") + 1, idcompuesto.length());

				BusquedaDetalleVO bd = new BusquedaDetalleVO();
				bd.setIdudoc(idudoc);
				bd.setSignaturaudoc(signaturaudoc);
				bd.setTiposolicitud(PrestamosConstants.TIPO_SOLICITUD_PRESTAMO);
				bd.setIdSolicitud(idSolicitud);

				detalles.add(bd);
				idsUdocs.add(idudoc);
			}

			// Obtener las unidades de las que se revisa la documentación
			Map unidadesDevolverRevDoc = new HashMap();
			if ((udocsarevisardoc != null) && (udocsarevisardoc.length > 0)) {

				for (int i = 0; i < udocsarevisardoc.length; i++) {
					String idcompuesto = udocsarevisardoc[i];
					String idudoc = idcompuesto.substring(0,
							idcompuesto.indexOf("|"));
					String signaturaudoc = idcompuesto.substring(
							idcompuesto.indexOf("|") + 1,
							idcompuesto.lastIndexOf("|"));
					String pos = idcompuesto.substring(
							idcompuesto.lastIndexOf("|") + 1,
							idcompuesto.length());

					String observaciones = detalleForm
							.getMapFormValues("observacionesdocrev" + pos);
					String idUsrGestor = detalleForm
							.getMapFormValues("idusrgestorDocRev" + pos);

					RevisionDocumentacionVO revDocVO = new RevisionDocumentacionVO();
					revDocVO.setIdUsrGestor(idUsrGestor);
					revDocVO.setObservaciones(observaciones);

					unidadesDevolverRevDoc.put(idudoc + "|" + signaturaudoc,
							revDocVO);
				}
			}

			prestamosService.devolverUnidadesDocumentales(detalles,
					unidadesDevolverRevDoc);

			if (!CollectionUtils.isEmpty(idsUdocs)) {
				InvocationStack invStack = getInvocationStack(request);
				ClientInvocation cltInv = invStack.getClientInvocation(invStack
						.getSize() - 2);
				cltInv.addParameter(
						PrestamosConstants.ID_UDOC_DEVUELTA,
						(String[]) idsUdocs.toArray(new String[idsUdocs.size()]));
			}

		} catch (PrestamoActionNotAllowedException e) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					e);
			ErrorsTag.saveErrors(request, errores);
		}
	}

	/**
	 * Añade a un préstamos las unidades documentales seleccionadas.
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
	public void crearDetalleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Añadimos el detalle al préstamo
		crearDetalle(request, form, prestamosService);

		// Volvemos a la página de listado de unidades en préstamo
		goBackExecuteLogic(mappings, form, request, response);
	}

	protected void crearDetalle(HttpServletRequest request, ActionForm form,
			GestionPrestamosBI prestamosService) {

		// Obtenemos el prestamo al que se van asociar los detalles
		PrestamoVO infoPrestamo = (PrestamoVO) request.getSession()
				.getAttribute("PRESTAMOKEY");
		DetallePrestamoForm detalleForm = (DetallePrestamoForm) form;
		String[] idsDetallesPrestamos = detalleForm.getDetallesseleccionados(); // request.getParameterValues("detallesseleccionados");

		if (idsDetallesPrestamos != null) {
			// codigo+"|"+id+"|"+expediente+"|"+titulo+"|"+signatura)
			for (int i = 0; i < idsDetallesPrestamos.length; i++) {

				DetallePrestamoVO infoDetallePrestamo = new DetallePrestamoVO();

				String identificacion, idudoc, titulo, expediente, signatura, fondo, codSist = null;
				StringOwnTokenizer st = new StringOwnTokenizer(
						idsDetallesPrestamos[i], "|", null, true);

				identificacion = st.nextToken();
				idudoc = st.nextToken();
				expediente = st.nextToken();
				if (expediente.equals("null"))
					expediente = null;
				titulo = st.nextToken();
				signatura = st.nextToken();
				fondo = st.nextToken();
				codSist = st.nextToken();
				if (codSist.equals("null"))
					codSist = null;

				// Componer el número del expediente
				StringBuffer numExp = new StringBuffer();
				if (StringUtils.isNotBlank(codSist))
					numExp.append(codSist);
				if (StringUtils.isNotBlank(expediente)) {
					if (numExp.length() > 0)
						numExp.append("-");
					numExp.append(expediente);
				}

				infoDetallePrestamo.setIdsolicitud(infoPrestamo.getId());
				infoDetallePrestamo
						.setTiposolicitud(PrestamosConstants.TIPO_SOLICITUD_PRESTAMO);
				infoDetallePrestamo.setIdudoc(idudoc);
				infoDetallePrestamo.setTitulo(titulo);
				infoDetallePrestamo.setSignaturaudoc(signatura);
				// infoDetallePrestamo.setIdentificacion(codigo);
				infoDetallePrestamo.setIdentificacion(identificacion);
				infoDetallePrestamo.setExpedienteudoc(numExp.toString());
				infoDetallePrestamo.setIdFondo(fondo);
				infoDetallePrestamo
						.setEstado(PrestamosConstants.ESTADO_SOLICITUD_PENDIENTE);
				infoDetallePrestamo.setFestado(DBUtils.getFechaActual());

				// Obtener los valores de los campos observaciones
				String observaciones = request.getParameter("observaciones|"
						+ idsDetallesPrestamos[i]);

				// Asignamos la información al detalle
				infoDetallePrestamo.setInformacion(DetallePrestamoVO
						.createInformacionXML(observaciones));

				try {
					// Comprobamos si el detalle que estamos añadiendo ya esta
					// incluído en el prestamo.
					prestamosService.getDetallePrestamo(infoPrestamo.getId(),
							infoDetallePrestamo.getIdudoc(),
							infoDetallePrestamo.getSignaturaudoc());
				} catch (DetalleNotFoundException dnfe) {
					prestamosService.nuevoDetallePrestamo(infoDetallePrestamo);
				}
			}
		}

	}

	/**
	 * Añade a un préstamos las unidades documentales seleccionadas.
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
	public void crearDetalleUDocRelExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Añadimos el detalle al préstamo
		crearDetalle(request, form, prestamosService);

		// Marcamos la variable que indica que el envío de datos ha finalizado
		// para poder cerrar la ventana popup
		request.setAttribute(SolicitudesConstants.ENVIO_FINALIZADO_KEY,
				new Boolean(true));

		// Redirigimos a la pagina de udocs relacionadas donde se trata el
		// parámetor anterior
		setReturnActionFordward(request,
				mappings.findForward("ver_udocs_relacionadas"));
	}

	/**
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	void verPrestamo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// request.setAttribute("__FORM_NAME", mappings.getName());
		setReturnActionFordward(request, mappings.findForward("ver_prestamo"));
	}

	/**
	 * Muestra el detalle de una unidad documental asociada a un préstamo.
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
	protected void verudocExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		verudocCodeLogic(mapping, form, request, response);
		// Establecemos el punto de navegación actual aquí
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_VERUDOC,
				request);
	}

	/**
	 * Encapsula la lógica que se encarga de mostrar el detalle de una unidad
	 * documental asociada a un préstamo.
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
	protected void verudocCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Recuperamos el identificador de la unidad documental que deseamos ver
		String ids = request.getParameter("ids");

		StringOwnTokenizer st = new StringOwnTokenizer(ids, "|");
		String idsolicitud = st.nextToken();
		String idudoc = st.nextToken();
		String signaturaudoc = st.nextToken();

		DetallePrestamoVO detalleVO = null;
		try {
			detalleVO = prestamosService.getDetallePrestamo(idsolicitud,
					idudoc, signaturaudoc);

			detalleVO = prestamosService.tratarDetallePrestamo(detalleVO);

			if (appUser.hasPermission(AppPermissions.GESTION_PRESTAMOS_ARCHIVO)) {
				PrestamoVO prestamo = (PrestamoVO) getFromTemporalSession(
						request, PrestamosConstants.PRESTAMO_KEY);

				if (prestamo != null
						&& (prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ABIERTO
								|| prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO
								|| prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_RESERVADO
								|| prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO
								|| prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO || prestamo
								.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO)) {
					request.setAttribute(
							PrestamosConstants.LISTA_PRESTAMOS_NO_DISPONIBLES_KEY,
							prestamosService.getDetallesPrestamosNoDisponibles(
									prestamo, detalleVO));
				}
			}
		} catch (DetalleNotFoundException e) {
			logger.error(
					"Se ha producido un error recuperando el detalle de la unidad documental",
					e);

			throw new NotCheckedException(
					"Error recuperando el detalle de la unidad documental", e);
		}

		// Transformamos el detalle a objeto de presentación
		final GestionCuadroClasificacionBI cclfBI = getGestionCuadroClasificacionBI(request);
		final GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);
		final GestionPrestamosBI prestamoBI = getGestionPrestamosBI(request);
		DetallePrestamoPO detallePO = new DetallePrestamoPO(cclfBI,
				descripcionBI, prestamoBI);
		try {
			PropertyUtils.copyProperties(detallePO, detalleVO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		// Metemos el detalle en la sesion
		// setInTemporalSession(request ,
		// PrestamosConstants.DETALLE_PRESTAMO_KEY, detalleVO);
		setInTemporalSession(request, PrestamosConstants.DETALLE_PRESTAMO_KEY,
				detallePO);
		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("ver_detalle_prestamo"));
	}

	/**
	 * Actualiza las observaciones del préstamo
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void actualizarObservacionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();

		DetallePrestamoForm frm = (DetallePrestamoForm) form;

		prestamosBI.actualizarObservaciones(frm.getIdsolicitud(),
				frm.getObservaciones());

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Encapsula la lógica que se encarga de mostrar el detalle de una unidad
	 * documental asociada a un préstamo.
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
	public void verudocsRelacionadasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		PaginatedList paginatedList = new PaginatedList();

		// Obtener la configuración de la búsqueda
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);
		if (busqueda == null) {
			busqueda = getCfgBusquedaPrestamos(request);
			setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
					busqueda);
		}

		SortOrderEnum currentOrderDirection = getOrderCurrentDirection(request,
				busqueda);
		String orderColumn = getOrderColumn(request, busqueda);

		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		// Recuperamos el identificador de la unidad documental que deseamos ver
		String idudoc = request.getParameter("idudoc");
		String idCampoUDocsRel = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getUdocsRel();

		List listaRefs = descripcionBI.getValues(TipoFicha.FICHA_ELEMENTO_CF,
				TipoCampo.REFERENCIA_VALUE, idudoc, idCampoUDocsRel);
		List listaIdsUDocsRel = new ArrayList();
		List ltElements = new ArrayList();
		if (listaRefs != null && listaRefs.size() > 0) {

			try {
				Iterator it = listaRefs.iterator();
				while (it.hasNext()) {
					CampoReferenciaVO ref = (CampoReferenciaVO) it.next();

					List listaUDocs = depositoBI
							.getUDocsById(new String[] { ref.getIdObjRef() });

					if (listaUDocs != null && listaUDocs.size() > 0) {
						Iterator itUDocs = listaUDocs.iterator();

						while (itUDocs.hasNext()) {
							UDocEnUiDepositoVO udocEnUI = (UDocEnUiDepositoVO) itUDocs
									.next();
							String idCompuesto = udocEnUI.getIdunidaddoc()
									+ Constants.SEPARADOR_IDENTIFICADOR_BUSQUEDAS
									+ udocEnUI.getSignaturaudoc();
							listaIdsUDocsRel.add(idCompuesto);
						}
					}
				}

				setInTemporalSession(request,
						PrestamosConstants.LISTA_IDS_ELEMENTOS_CF,
						listaIdsUDocsRel);

				// String[] idsToShow = DisplayTagUtils.getIds(pageNumber,
				// numeroElmentosPorPagina, listaIdsElementos);
				// Se pasa como número de elementos a mostrar el doble porque
				// hay el doble de strings identificadores, dos por elemento
				// String[] idsToShow = DisplayTagUtils.getIds(1, 0,
				// listaIdsUDocsRel);

				String[] idsToShow = ArrayUtils.toString(listaIdsUDocsRel
						.toArray());

				BusquedaElementosVO busquedaElementosVO = new BusquedaElementosVO();

				ltElements = cuadroBI.getElementosYPartes(
						idsToShow,
						fillBusquedaElementosVO(request, busquedaElementosVO,
								busqueda), busqueda);
			} catch (TooManyResultsException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
								new Object[] { new Integer(e.getCount()),
										new Integer(e.getMaxNumResults()) }));

				goBackExecuteLogic(mapping, form, request, response);
			}

			// Transformar la lista de elementos en POS
			/*
			 * final GestionCuadroClasificacionBI cclfBI =
			 * getGestionCuadroClasificacionBI(request); final
			 * GestionDescripcionBI descBI = getGestionDescripcionBI(request);
			 * 
			 * CollectionUtils.transform(ltElements, new Transformer() { public
			 * Object transform(Object obj) { ElementoCFPO po = new
			 * ElementoCFPO(cclfBI, descBI, busqueda); try {
			 * PropertyUtils.copyProperties(po, obj); } catch (Exception e) {
			 * logger.error(e.getMessage()); } return po; } });
			 */

			CollectionUtils.transform(ltElements,
					ElementoCFToPO.getInstance(services, busqueda, null, null));

			paginatedList.setFullListSize(listaIdsUDocsRel.size());
			paginatedList.setList(ltElements);
			paginatedList.setObjectsPerPage(listaIdsUDocsRel.size());
			paginatedList.setPageNumber(1);
			paginatedList.setSearchId(ID_DISPLAY_TAG);
			paginatedList.setSortCriterion(orderColumn.toString());
			paginatedList.setSortDirection(currentOrderDirection);

			setInTemporalSession(request,
					SolicitudesConstants.LISTA_UDOCS_RELACIONADAS,
					paginatedList);
			setInTemporalSession(request, Constants.LAST_ORDER, orderColumn);
			setInTemporalSession(request, Constants.LAST_ORDER_DIRECTION,
					currentOrderDirection);
			setInTemporalSession(request, Constants.PAGE_NUMBER, new Integer(1));

			// setInTemporalSession(request ,
			// PrestamosConstants.LISTA_UDOCS_RELACIONADAS, listaUDocsRel);
		}

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("ver_udocs_relacionadas"));
	}

	/**
	 * Método para las búsquedas de procedimientos desde el formulario de
	 * detalles de préstamos
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void buscarProcedimientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BusquedasHelper.buscarProcedimientoComunLogic(mappings,
				(DetallePrestamoForm) form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("nuevo_detalle_prestamo"));
	}
}