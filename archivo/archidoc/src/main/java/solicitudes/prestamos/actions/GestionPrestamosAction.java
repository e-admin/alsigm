package solicitudes.prestamos.actions;

import gcontrol.actions.OrganoPO;
import gcontrol.view.InfoOrganoPO;
import gcontrol.view.UsuarioPO;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.forms.SolicitudesBaseForm;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import solicitudes.prestamos.forms.PrestamoForm;
import solicitudes.prestamos.utils.ExceptionMapper;
import solicitudes.prestamos.utils.PrestamosUtils;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import solicitudes.prestamos.vos.MotivoPrestamoVO;
import solicitudes.prestamos.vos.PrestamoPO;
import solicitudes.prestamos.vos.PrestamoToPO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.vos.MotivoRechazoVO;
import transferencias.actions.UsuarioToGestorPO;
import util.CollectionUtils;
import util.ErrorsTag;
import util.StringOwnTokenizer;
import xml.config.ConfiguracionServicios;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionRechazosBI;
import common.bi.GestionSistemaBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import descripcion.vos.ElementoCFVO;

/**
 * Action que engloba todas las posibles acciones que se pueden realizar sobre
 * los préstamos.
 */
public class GestionPrestamosAction extends BaseAction {

	private final static String PATH_ACTION_LISTADO_PRESTAMOSVER = "/action/gestionPrestamos?method=listadoprestamosver";

	private final static String PATH_ACTION_LISTADO_PRESTAMOS_NO_DISPONIBLES_AUTORIZADAS = "/action/gestionPrestamos?method=listadoprestamosnodisponiblesautorizadas";

	private static final String PATH_ACTION_VER_PRESTAMO = "/action/gestionPrestamos?method=verprestamo&id=";

	/**
	 * Metodo del action que se llama para recarga el display desde la pagina de
	 * listados del menu VER
	 */
	private final static String METHOD_LISTADOSVER = "listadoprestamosver";

	/**
	 * Metodo del action que se llama para recarga el display desde la pagina de
	 * listados del menu ENTREGAR
	 */
	private final static String METHOD_LISTADOSENTREGAR = "listadoprestamosentregar";

	/**
	 * Metodo del action que se llama para recarga el display desde la pagina de
	 * listados del menu CONSULTAR
	 */
	private final static String METHOD_LISTADOS = "listadoprestamos";

	/**
	 * Metodo del action que se llama para recarga el display desde el boton
	 * COMPROBAR_DISPONIBILIDAD
	 */
	private final static String METHOD_COMPROBARDISPONIBILIDAD = "comprobardisponibilidad";

	/**
	 * Metodo del action que se llama para recarga el display desde el boton
	 * COMPROBAR_DISPONIBILIDAD_ENTREGA
	 */
	private final static String METHOD_COMPROBARDISPONIBILIDADENTREGA = "comprobardisponibilidadentrega";

	/**
	 * Metodo del action que se llama para recarga el display desde el boton
	 * COMPROBAR_DISPONIBILIDAD_PRORROGA
	 */
	private final static String METHOD_COMPROBARDISPONIBILIDADPRORROGA = "comprobardisponibilidadprorroga";

	/**
	 * Metodo del action que se llama para recarga el display desde el boton
	 * COMPROBAR_DISPONIBILIDAD
	 */
	public final static String METHOD_VERPRESTAMO = "verprestamo";

	/**
	 * Metodo del action que se llama para recarga el display desde la pagina de
	 * listados del menu CONSULTAR
	 */
	private final static String METHOD_ACCION_ANIADIR = "accionAniadirAPrestamoDesdeBusqueda";

	private final static String METHOD_LISTADO_VER_EN_ELABORACION = "listadoprestamosenelaboracionver";

	private final static String[] PERMISOS_ACCIONES = new String[] {
			AppPermissions.GESTION_PRESTAMOS_ARCHIVO,
			AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS,
			AppPermissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE };

	protected void homeprestamosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = getServiceRepository(request);
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		if (appUser.hasAnyPermission(new String[] {
				AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS,
				AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS })) {
			// Recuperamos los prestamos para ese usuario
			List prestamosEnElaboracion = prestamosService
					.getPrestamosXUsuarioSolicitante(appUser.getId());
			if (prestamosEnElaboracion.size() > 5)
				prestamosEnElaboracion = prestamosEnElaboracion.subList(0, 5);

			CollectionUtils.transform(prestamosEnElaboracion,
					PrestamoToPO.getInstance(request.getLocale(), services));

			// Establecemos el listado de los prestamos a mostrar
			request.setAttribute(
					PrestamosConstants.LISTA_PRESTAMOS_EN_ELABORACION_KEY,
					prestamosEnElaboracion);
		}

		if (appUser.hasPermission(AppPermissions.GESTION_PRESTAMOS_ARCHIVO)) {
			List prestamosAGestionar = prestamosService
					.obtenerListadoGestionar(getAppUser(request)
							.getIdsArchivosUser());
			if (prestamosAGestionar.size() > 5)
				prestamosAGestionar = prestamosAGestionar.subList(0, 5);
			CollectionUtils.transform(prestamosAGestionar,
					PrestamoToPO.getInstance(request.getLocale(), services));
			request.setAttribute(
					PrestamosConstants.LISTA_PRESTAMOS_A_GESTIONAR_KEY,
					prestamosAGestionar);
		}

		if (appUser.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES)) {
			List prestamosAEntregar = prestamosService
					.obtenerListadoEntregar(getAppUser(request)
							.getIdsArchivosUser());
			if (prestamosAEntregar.size() > 5)
				prestamosAEntregar = prestamosAEntregar.subList(0, 5);
			CollectionUtils.transform(prestamosAEntregar,
					PrestamoToPO.getInstance(request.getLocale(), services));
			request.setAttribute(
					PrestamosConstants.LISTA_PRESTAMOS_A_ENTREGAR_KEY,
					prestamosAEntregar);
		}

		saveCurrentInvocation(KeysClientsInvocations.HOME_PRESTAMOS, request);

		setReturnActionFordward(request, mapping.findForward("home_prestamos"));
	}

	/**
	 * Muestra el listado de los prestamos en función del rol del usuario.
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
	protected void listadoprestamosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceClient sc = ServiceClient.create(appUser);
		List orgList = new ArrayList(appUser.getDependentOrganizationList());
		orgList.add(appUser.getOrganization());
		sc.getProperties().put(
				PrestamosConstants.PROPERTY_DEPENDENTORGANIZATIONLIST, orgList);// Añadimos
																				// la
																				// lista
																				// de
																				// organos
																				// descendiente
																				// por
																				// si
																				// es
																				// un
																				// listado
																				// de
																				// gestor
		ServiceRepository services = ServiceRepository.getInstance(sc);
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Establecemos el punto de navegacion actual a este
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_PRESTAMOS, request);

		List prestamos = prestamosService.obtenerListadoGestionar(getAppUser(
				request).getIdsArchivosUser());
		// Asignamos a los prestamos la notas
		prestamosService.asignaEstadosAPrestamos(prestamos);

		CollectionUtils.transform(prestamos,
				PrestamoToPO.getInstance(request.getLocale(), services));
		// Establecemos los elementos de la vista
		// if (!appUser.hasPermission(
		// AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
		request.setAttribute(PrestamosConstants.VER_COLUMNA_NOTAS, new Boolean(
				true));
		// else
		// request.removeAttribute(PrestamosConstants.VER_COLUMNA_NOTAS);
		request.setAttribute(PrestamosConstants.LISTA_PRESTAMOS_KEY, prestamos);
		request.setAttribute(PrestamosConstants.VER_BOTON_SOLICITAR_RESERVA,
				new Boolean(false));
		request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR,
				new Boolean(false));

		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD, METHOD_LISTADOS);

		// Generamos la redireccion a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_prestamo"));
	}

	/**
	 * Muestra el listado de los prestamos con reserva en función del rol del
	 * usuario.
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
	protected void listadoprestamosreservaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceClient sc = ServiceClient.create(appUser);
		List orgList = new ArrayList(appUser.getDependentOrganizationList());
		orgList.add(appUser.getOrganization());
		sc.getProperties().put(
				PrestamosConstants.PROPERTY_DEPENDENTORGANIZATIONLIST, orgList);// Añadimos
																				// la
																				// lista
																				// de
																				// organos
																				// descendiente
																				// por
																				// si
																				// es
																				// un
																				// listado
																				// de
																				// gestor
		ServiceRepository services = ServiceRepository.getInstance(sc);
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Establecemos el punto de navegacion actual a este
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_PRESTAMOS, request);

		List prestamos = prestamosService
				.obtenerListadoGestionarReserva(getAppUser(request)
						.getIdsArchivosUser());
		// Asignamos a los prestamos la notas
		prestamosService.asignaEstadosAPrestamos(prestamos);

		CollectionUtils.transform(prestamos,
				PrestamoToPO.getInstance(request.getLocale(), services));
		// Establecemos los elementos de la vista
		// if (!appUser.hasPermission(
		// AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
		request.setAttribute(PrestamosConstants.VER_COLUMNA_NOTAS, new Boolean(
				true));
		// else
		// request.removeAttribute(PrestamosConstants.VER_COLUMNA_NOTAS);
		request.setAttribute(PrestamosConstants.LISTA_PRESTAMOS_KEY, prestamos);
		request.setAttribute(PrestamosConstants.VER_BOTON_SOLICITAR_RESERVA,
				new Boolean(false));
		request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR,
				new Boolean(false));

		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD, METHOD_LISTADOS);

		// Generamos la redireccion a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_prestamo"));
	}

	/**
	 * Muestra el listado de los prestamos según el rol del usuario que esta
	 * accediendo a la aplicación para su devolución.
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
	protected void listadoprestamosentregarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Establecemos el punto de navegación actual aquí
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_PRESTAMOS_ENTREGAR,
				request);

		List prestamos = prestamosService.obtenerListadoEntregar(getAppUser(
				request).getIdsArchivosUser());
		// Asignamos a los prestamos sus notas asociadas
		prestamosService.asignaEstadosAPrestamos(prestamos);

		CollectionUtils.transform(prestamos,
				PrestamoToPO.getInstance(request.getLocale(), services));
		// Establecemos los elementos de la vista
		request.setAttribute(PrestamosConstants.VER_COLUMNA_NOTAS, new Boolean(
				false));
		request.setAttribute(PrestamosConstants.LISTA_PRESTAMOS_KEY, prestamos);
		request.setAttribute(PrestamosConstants.VER_BOTON_SOLICITAR_RESERVA,
				new Boolean(false));
		request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR,
				new Boolean(false));
		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD, METHOD_LISTADOSENTREGAR);

		// Generamos la redireccion a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_prestamo"));
	}

	/**
	 * Muestra el listado de los prestamos actuales.
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
	protected void listadoprestamosverExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));

		// Establecemos el punto de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_PRESTAMOS_VER,
				request);

		// Generamos el listado de prestamos a mostrar
		List prestamosConNotas = generateListadoPrestamos(appUser);

		CollectionUtils.transform(prestamosConNotas,
				PrestamoToPO.getInstance(request.getLocale(), services));

		// Establecemos el listado de los prestamos a mostrar
		request.setAttribute(PrestamosConstants.LISTA_PRESTAMOS_KEY,
				prestamosConNotas);
		// Activamos el botón de eliminar prestamos
		request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR,
				new Boolean(true));
		// Activamos la columna de las notas de los préstamos
		request.setAttribute(PrestamosConstants.VER_COLUMNA_NOTAS, new Boolean(
				true));
		// Activamos la columna de las notas de los check
		request.setAttribute(PrestamosConstants.VER_COLUMNA_SELECCIONAR,
				new Boolean(true));
		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD, METHOD_LISTADOSVER);
		// Redireccionamos a la página adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_prestamo"));
	}

	/**
	 * Muestra el listado de los prestamos actuales.
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
	protected void listadoprestamosenelaboracionverExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));

		// Establecemos el punto de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_PRESTAMOS_VER,
				request);

		// Generamos el listado de prestamos a mostrar
		List prestamosConNotas = generateListadoPrestamosEnElaboracion(appUser);

		CollectionUtils.transform(prestamosConNotas,
				PrestamoToPO.getInstance(request.getLocale(), services));

		// Establecemos el listado de los prestamos a mostrar
		request.setAttribute(PrestamosConstants.LISTA_PRESTAMOS_KEY,
				prestamosConNotas);
		// Activamos el botón de eliminar prestamos
		request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR,
				new Boolean(true));
		// Activamos la columna de las notas de los préstamos
		request.setAttribute(PrestamosConstants.VER_COLUMNA_NOTAS, new Boolean(
				true));
		// Activamos la columna de las notas de los check
		request.setAttribute(PrestamosConstants.VER_COLUMNA_SELECCIONAR,
				new Boolean(true));
		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD,
				METHOD_LISTADO_VER_EN_ELABORACION);
		// Redireccionamos a la página adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_prestamo"));
	}

	/**
	 * Genera el listado de prestamos para un usuario dado.
	 * 
	 * @param AppUser
	 *            Usuario del que deseamos obtener los préstamos.
	 * @return Listado de los préstamos del usuario.
	 */
	private List generateListadoPrestamos(AppUser appUser) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Recuperamos los prestamos para ese usuario
		List prestamos = prestamosService
				.getPrestamosXUsuarioSolicitante(appUser.getId());

		// Asignamos las notas con los estados para cada prestamo
		prestamosService.asignaEstadosAPrestamos(prestamos);

		return prestamos;
	}

	/**
	 * Genera el listado de prestamos para un usuario dado.
	 * 
	 * @param AppUser
	 *            Usuario del que deseamos obtener los préstamos.
	 * @return Listado de los préstamos del usuario.
	 */
	private List generateListadoPrestamosEnElaboracion(AppUser appUser) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Recuperamos los prestamos para ese usuario
		List prestamos = (List) prestamosService
				.getPrestamosEnElaboracionXIdUsuario(appUser.getId());

		// Asignamos las notas con los estados para cada prestamo
		prestamosService.asignaEstadosAPrestamos(prestamos);

		return prestamos;
	}

	/**
	 * Redirige a la pagina de información de las unidades documentales no
	 * disponibles para un préstamo.
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
	protected void listadoprestamosnodisponiblesautorizadasExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = mapping
				.findForward("ver_prestamos_no_disponibles_autorizadas");
		setReturnActionFordward(request, forward);
	}

	/**
	 * Muestra el detalle de un prestamo.
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
	protected void verprestamoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el identificador del prestamos a mostrar
		String codigo = ((PrestamoForm) form).getId();

		if (StringUtils.isEmpty(codigo))
			codigo = (String) request.getParameter("idprestamo");

		verprestamoCodeLogic(codigo, mapping, form, request, response);

		// Establecemos el punto de navegación
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_VER_PRESTAMO,
				request);
	}

	/**
	 * Prepara la página para la impresión de la papeleta de la entrega de la
	 * devolución de un préstamo.
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
	protected void imprimirEntradaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtenemos el identificador del prestamos a mostrar
		String codigo = ((PrestamoForm) form).getIdprestamo();

		verprestamoCodeLogic(codigo, mapping, form, request, response);

		// Establecemos el punto de navegación
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_IMPRIMIR_ENTRADA_PRESTAMO,
				request);

		request.setAttribute(PrestamosConstants.PERMITIR_EDITAR_OBSERVACIONES,
				new Boolean(false));
		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("imprimir_entrada_prestamo"));
	}

	/**
	 * Encapsula la lógica necesaria para ver los detalles de un préstamo
	 * 
	 * @param codigo
	 *            Identificador del prestamo a ver
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	private void verprestamoCodeLogic(String codigo, ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el usuario que esta trabajando
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));

		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Servicio de Solicitudes
		GestionSolicitudesBI solicitudesBI = services
				.lookupGestionSolicitudesBI();

		// Obtenemos los motivos de rechazo para un préstamo y los metemos en la
		// sesion temporal
		Collection motivos = prestamosService.getMotivosRechazo();
		setInTemporalSession(request,
				PrestamosConstants.LISTA_MOTIVO_RECHAZO_KEY, motivos);

		// Obtenemos los motivos de prorroga y los metemos en la sesion temporal
		Collection motivosprorroga = prestamosService
				.getMotivosRechazoProrroga();
		setInTemporalSession(request,
				PrestamosConstants.LISTA_MOTIVO_RECHAZO_PRORROGA_KEY,
				motivosprorroga);

		// Obtenemos el préstamo por su identificador y lo metemos en la sesion
		// temporal
		PrestamoVO prestamo_VO = prestamosService.verPrestamo(codigo);
		prestamo_VO = (PrestamoVO) solicitudesBI
				.getAditionalSolicitudInformation(prestamo_VO);
		PrestamoPO prestamoPO = (PrestamoPO) PrestamoToPO.getInstance(
				request.getLocale(), services).transform(prestamo_VO);
		setInTemporalSession(request, PrestamosConstants.PRESTAMO_KEY,
				prestamoPO);

		// Obtenemos el motivo de préstamo
		MotivoPrestamoVO motivo = prestamosService
				.getMotivoPrestamoById(prestamo_VO.getIdMotivo());
		setInTemporalSession(request, SolicitudesConstants.MOTIVO_KEY, motivo);

		// Obtenemos los detalles del prestamo
		Collection detallesPrestamos = prestamosService
				.obtenerDetallesPrestamoByUsuario(prestamo_VO);

		// ESTO YA SE HACE DENTRO DEL establecerVistas
		// setInTemporalSession( request ,
		// PrestamosConstants.DETALLE_PRESTAMO_KEY, detallesPrestamos);

		// //Establecemos que tabla a mostrar
		// if
		// (appUser.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
		// request.setAttribute(
		// PrestamosConstants.VER_LISTA_DETALLES_PARA_DEPOSITO, new
		// Boolean(true) );

		// Obtener la fecha Devolución

		if (prestamo_VO.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO) {
			Date fechaDevolucion = prestamo_VO.getFmaxfinprestamo();
			String strFecha = DateUtils.formatDate(fechaDevolucion);
			request.setAttribute("fechaFinPrestamo", strFecha);
		}

		// Establecemos los elementos a mostrar en la vista en función del
		// prestamos y del usuario
		PrestamosUtils.establecerVistas(prestamo_VO, appUser, request,
				prestamosService, detallesPrestamos);

		// Establecemos los detalles
		setInTemporalSession(request, PrestamosConstants.DETALLE_PRESTAMO_KEY,
				detallesPrestamos);

		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD, METHOD_VERPRESTAMO);

		// Obtenemos la lista de posibles gestores de revisión de documentación
		List usuariosGestoresRevDoc = prestamosService
				.getUsuariosGestoresRevDocPosibles(prestamo_VO.getIdarchivo());
		CollectionUtils.transform(usuariosGestoresRevDoc,
				UsuarioToGestorPO.getInstance());

		setInTemporalSession(request,
				PrestamosConstants.LISTA_GESTORESREVDOC_KEY,
				usuariosGestoresRevDoc);

		// generamos la redirección
		setReturnActionFordward(request, mapping.findForward("ver_prestamo"));
	}

	/**
	 * Devuelve el forward al detalle de prestamo.
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
	private void verPrestamo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request, mappings.findForward("ver_prestamo"));
	}

	protected void accionNuevoPrestamoDesdeBusquedaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (getServiceClient(request).hasAnyPermission(PERMISOS_ACCIONES)) {
			nuevoCodeLogic(mapping, form, request, response);
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_SIN_PERMISOS));
			ErrorsTag.saveErrors(request, errors);
			setInTemporalSession(request, "usarCache", Boolean.TRUE);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Acción para añadir a un préstamo las unidades seleccionadas.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void accionAniadirAPrestamoDesdeBusquedaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (getServiceClient(request).hasAnyPermission(PERMISOS_ACCIONES)) {

			// Guardamos el punto actual

			// Obtener el archivo al que pertenecen las unidades documentales.
			ArchivoVO archivoVO = (ArchivoVO) getFromTemporalSession(request,
					SolicitudesConstants.ARCHIVO_SOLICITUD_KEY);

			if (archivoVO != null) {
				ServiceRepository services = getServiceRepository(request);
				List prestamos = (List) getFromTemporalSession(request,
						SolicitudesConstants.PRESTAMOS_PARA_ANIADIR_UDOCS_KEY);
				CollectionUtils
						.transform(prestamos, PrestamoToPO.getInstance(
								request.getLocale(), services));
				// Establecemos los elementos de la vista
				request.setAttribute(PrestamosConstants.LISTA_PRESTAMOS_KEY,
						prestamos);
				request.setAttribute(
						PrestamosConstants.VER_BOTON_SOLICITAR_RESERVA,
						new Boolean(false));
				request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR,
						new Boolean(false));
				request.setAttribute(
						PrestamosConstants.VER_COLUMNA_SELECCIONAR,
						new Boolean(false));
				request.setAttribute(PrestamosConstants.VER_COLUMNA_NOTAS,
						new Boolean(false));

				request.setAttribute(
						PrestamosConstants.ACCION_ANIADIR_A_PRESTAMO_KEY,
						new Boolean(true));

				List listaUnidades = (List) getFromTemporalSession(request,
						SolicitudesConstants.LISTA_UDOCS_SOLICITUD_KEY);

				if (ListUtils.isNotEmpty(listaUnidades)) {
					setInTemporalSession(
							request,
							SolicitudesConstants.LISTA_DETALLES_UDOCS_SOLICITUD_KEY,
							getListaInfoDetallesPrestamos(listaUnidades));
				}

				saveCurrentInvocation(
						KeysClientsInvocations.SOLICITUDES_SELECCIONAR_PRESTAMO,
						request);

				request.setAttribute(PrestamosConstants.METHOD,
						METHOD_ACCION_ANIADIR);
			}
			// Generamos la redireccion a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("listado_prestamo"));
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_SIN_PERMISOS));
			ErrorsTag.saveErrors(request, errors);
			setInTemporalSession(request, "usarCache", Boolean.TRUE);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Acción que añade al préstamo las unidades seleccionadas desde los
	 * resultados de la búsqueda.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void aniadirUdocsAPrestamoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_SELECCIONAR_PRESTAMO,
		// request);

		try {

			List listaDetalles = (List) getFromTemporalSession(request,
					SolicitudesConstants.LISTA_DETALLES_UDOCS_SOLICITUD_KEY);

			GestionPrestamosBI prestamosService = getGestionPrestamosBI(request);

			PrestamoForm formulario = (PrestamoForm) form;

			PrestamoVO prestamoVO = prestamosService.getPrestamo(formulario
					.getId());

			if (ListUtils.isNotEmpty(listaDetalles)) {
				prestamoVO.setFromBusqueda(true);
				prestamoVO.setDetallesPrestamo(listaDetalles);
			}

			ServiceClient usuarioCreador = ServiceClient
					.create(getAppUser(request));

			String tipoSolicitante = PrestamosConstants.TIPO_EXTERNO;

			if (StringUtils.isNotBlank(prestamoVO.getIdorgsolicitante())) {
				tipoSolicitante = PrestamosConstants.TIPO_INTERNO;
			}

			// usuarioCreador.setId( frm.getIduser() );
			usuarioCreador.getProperties().put(
					PrestamosConstants.PROPERTY_NOMBRE,
					prestamoVO.getNusrsolicitante());
			usuarioCreador.getProperties().put(
					PrestamosConstants.PROPERTY_TIPO_SOLICITANTE,
					tipoSolicitante);

			prestamosService.insertarDetallesAPrestamo(prestamoVO,
					usuarioCreador);
			removeInTemporalSession(request, PrestamosConstants.PRESTAMO_KEY);
			getInvocationStack(request).reset(request);

			setReturnActionFordward(request,
					verPrestamoBeforeCreate(prestamoVO.getId()));
		} catch (PrestamoActionNotAllowedException panae) {
			ActionErrors errors = new ActionErrors();
			errors = ExceptionMapper.getErrorsExcepcion(request, panae);
			ErrorsTag.saveErrors(request, errors);
			request.setAttribute(PrestamosConstants.CON_ERRORES, Boolean.TRUE);
			// Generamos la redireccion a la pagina adecuada
			goLastClientExecuteLogic(mapping, form, request, response);
		}

	}

	/**
	 * Prepara los datos y dirige a la página para generar un nuevo préstamo.
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
	protected void nuevoExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		nuevoCodeLogic(mapping, form, request, response);
	}

	/**
	 * Obtiene la lista de InfoDetallePrestamo a partir de una lista de unidades
	 * documentales.
	 * 
	 * @param listaUnidades
	 *            Lista de Unidades Documentales
	 * @return List de InforDetallePrestamo
	 */
	protected List getListaInfoDetallesPrestamos(List listaUnidades) {

		List listaDetalles = new ArrayList();

		if (listaUnidades != null) {

			// Convertir las unidades en detalles
			for (Iterator iterator = listaUnidades.iterator(); iterator
					.hasNext();) {
				ElementoCFVO udoc = (ElementoCFVO) iterator.next();

				String identificacion;
				String idudoc;
				String titulo;
				String expediente;
				String signatura;
				String idFondo;
				String codSist;

				identificacion = udoc.getIdentificacion();
				idudoc = udoc.getId();
				expediente = udoc.getNumexp();
				titulo = udoc.getTitulo();
				signatura = udoc.getSignaturaudoc();
				idFondo = udoc.getIdFondo();
				codSist = udoc.getCodsistproductor();

				// Componer el número del expediente
				StringBuffer numExp = new StringBuffer();
				if (StringUtils.isNotBlank(codSist))
					numExp.append(codSist);
				if (StringUtils.isNotBlank(expediente)) {
					if (numExp.length() > 0)
						numExp.append("-");
					numExp.append(expediente);
				}

				DetallePrestamoVO infoDetallePrestamo = new DetallePrestamoVO();

				infoDetallePrestamo
						.setTiposolicitud(PrestamosConstants.TIPO_SOLICITUD_PRESTAMO);
				infoDetallePrestamo.setIdudoc(idudoc);
				infoDetallePrestamo.setTitulo(titulo);
				infoDetallePrestamo.setSignaturaudoc(signatura);
				infoDetallePrestamo.setIdentificacion(identificacion);
				infoDetallePrestamo.setExpedienteudoc(numExp.toString());
				infoDetallePrestamo.setIdFondo(idFondo);
				infoDetallePrestamo
						.setEstado(PrestamosConstants.ESTADO_SOLICITUD_PENDIENTE);
				infoDetallePrestamo.setFestado(DBUtils.getFechaActual());

				// Obtener los valores de los campos observaciones
				String observaciones = null;

				// Asignamos la información al detalle
				infoDetallePrestamo.setInformacion(DetallePrestamoVO
						.createInformacionXML(observaciones));

				listaDetalles.add(infoDetallePrestamo);

			}
		}

		return listaDetalles;
	}

	/**
	 * Lógica del método nuevoCodeLogic
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param ids
	 */
	protected void nuevoCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));

		// Esta variable indica si se ha vuelto de un error al crear.
		boolean conErrores = false;

		if (request.getAttribute(PrestamosConstants.CON_ERRORES) != null) {
			Boolean rqConErrores = (Boolean) request
					.getAttribute(PrestamosConstants.CON_ERRORES);
			conErrores = rqConErrores.booleanValue();
		}

		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionControlUsuariosBI scu = services
				.lookupGestionControlUsuariosBI();
		// Generamos un prestamo nuevo con los valores por defecto y lo
		// almacenamos en la sesion temporal
		PrestamoVO prestamoVO = preparePrestamoVO(appUser);

		List listaUnidades = (List) getFromTemporalSession(request,
				SolicitudesConstants.LISTA_UDOCS_SOLICITUD_KEY);

		if (ListUtils.isNotEmpty(listaUnidades)) {
			prestamoVO.setFromBusqueda(true);
			setInTemporalSession(request,
					SolicitudesConstants.LISTA_DETALLES_UDOCS_SOLICITUD_KEY,
					getListaInfoDetallesPrestamos(listaUnidades));
		}

		PrestamoPO prestamoPO = (PrestamoPO) PrestamoToPO.getInstance(
				request.getLocale(), services).transform(prestamoVO);

		setInTemporalSession(request,
				PrestamosConstants.PRESTAMO_ARCHIVO_ID_KEY,
				prestamoPO.getIdarchivo());
		setInTemporalSession(request,
				PrestamosConstants.PRESTAMO_ARCHIVO_NOMBRE_KEY,
				prestamoPO.getNombrearchivo());

		setInTemporalSession(request, PrestamosConstants.PRESTAMO_KEY,
				prestamoPO);

		PrestamoForm formulario = (PrestamoForm) getFromTemporalSession(
				request, PrestamosConstants.PRESTAMO_FORM);

		if (!conErrores) {
			if (formulario != null) {
				form = formulario;
				setInTemporalSession(request, PrestamosConstants.PRESTAMO_FORM,
						null);
			}

			if (((PrestamoForm) form).getIduser() == null)
				establecerUsuario(request, (PrestamoForm) form, appUser, true);
		}

		// Si el usuario no es solicitante
		if (appUser.hasPermission(AppPermissions.GESTION_PRESTAMOS_ARCHIVO)
				|| appUser
						.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			// Activamos el select en la vista
			request.setAttribute(PrestamosConstants.VER_SELECT, new Boolean(
					true));

			if (StringUtils.isNotBlank(prestamoVO.getIdorgsolicitante()))
				request.setAttribute(PrestamosConstants.ES_EXTERNO,
						new Boolean(false));
			else
				request.setAttribute(PrestamosConstants.ES_EXTERNO,
						new Boolean(true));
		}

		// Establecemos los usuarios existentes a mostrar en el select
		setInTemporalSession(request, PrestamosConstants.LISTA_USUARIOS_KEY,
				scu.getUsuariosRegistradosSolitantes(((PrestamoForm) form)
						.getFiltroUsuario()));

		loadMotivos(mapping, form, request, response);

		List motivos = (List) getFromTemporalSession(request,
				SolicitudesConstants.LISTA_MOTIVOS_KEY);

		String idMotivo = getIdMotivoXDefecto(motivos);

		if (idMotivo != null) {
			if (formulario == null) {
				formulario = (PrestamoForm) form;
			}
			formulario.setIdmotivo(idMotivo);
		}

		if (ConfigConstants.getInstance().getMarcarArchivoOrganoSolicitud()) {
			if (formulario == null) {
				formulario = (PrestamoForm) form;
			}
			if (appUser.getOrganization() != null)
				formulario.setIdarchivo(appUser.getOrganization()
						.getIdArchivoReceptor());
		}

		// Obtenemos la lista de archivo existentes
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
		List listaArchivos = sistemaBI.getArchivos();

		if (ListUtils.isNotEmpty(listaArchivos)) {
			setInTemporalSession(request, PrestamosConstants.LISTA_ARCHIVOS,
					listaArchivos);
			// Obtenemos la lista de tipos de entrega de la tabla de validación
			// de tipos de entrega
			setInTemporalSession(request,
					PrestamosConstants.LISTA_TIPOS_ENTREGA,
					sistemaBI.getListaTiposEntrega());

			saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_NUEVO,
					request);

			setReturnActionFordward(request,
					mapping.findForward("nuevo_prestamo"));
		} else {
			ActionErrors errors = getErrors(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_NO_EXISTEN_ARCHIVOS));
			ErrorsTag.saveErrors(request, errors);
			goBackExecuteLogic(mapping, formulario, request, response);
		}
	}

	/**
	 * Obtiene el identificador del Motivo .
	 * 
	 * @param motivos
	 *            Lista de motivos
	 * @return Identificador del Motivo Por Defecto
	 */
	public String getIdMotivoXDefecto(List motivos) {

		String idMotivo = null;
		// Si solo hay un motivo, se establece como activo.
		if (!ConfigConstants.getInstance().getMotivoSolicitudOpcional()) {
			if (ListUtils.isNotEmpty(motivos) && motivos.size() == 1) {
				MotivoPrestamoVO motivo = (MotivoPrestamoVO) motivos.get(0);

				idMotivo = motivo.getId();
			}
		}
		return idMotivo;
	}

	public void cargarDatosUsuarioExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Insertamos el punto de navegacion actual
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_NUEVO, request);

		cargarDatosUsuarioCodeLogic(request, form);

		// Redirigimos a la pantalla de creación
		setReturnActionFordward(request, mapping.findForward("nuevo_prestamo"));

	}

	public void cargarDatosUsuarioCodeLogic(HttpServletRequest request,
			ActionForm form) {
		PrestamoForm frm = (PrestamoForm) form;
		AppUser appUser = getAppUser(request);

		// Usuario solicitantes
		setInTemporalSession(
				request,
				PrestamosConstants.LISTA_USUARIOS_KEY,
				getGestionControlUsuarios(request)
						.getUsuariosRegistradosSolitantes(
								frm.getFiltroUsuario()));

		// usuario seleccionado a session
		establecerUsuario(request, frm, appUser, false);

		// Obtenemos la lista de archivo existentes
		setInTemporalSession(request, PrestamosConstants.LISTA_ARCHIVOS,
				getGestionSistemaBI(request).getArchivos());
		// request.setAttribute(PrestamosConstants.LISTA_ARCHIVOS,
		// getGestionSistemaBI(request).getArchivos());

		if (appUser.hasPermission(AppPermissions.GESTION_PRESTAMOS_ARCHIVO)
				|| appUser
						.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			// Activamos el select en la vista
			request.setAttribute(PrestamosConstants.VER_SELECT, new Boolean(
					true));

			if (StringUtils.isNotBlank(frm.getIduser()))
				request.setAttribute(PrestamosConstants.ES_EXTERNO,
						new Boolean(false));
			else
				request.setAttribute(PrestamosConstants.ES_EXTERNO,
						new Boolean(true));

		}

	}

	private void establecerUsuario(HttpServletRequest request,
			PrestamoForm frm, AppUser appUser, boolean nuevo) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionControlUsuariosBI scu = services
				.lookupGestionControlUsuariosBI();

		List users = (List) getFromTemporalSession(request,
				PrestamosConstants.LISTA_USUARIOS_KEY);
		UsuarioVO user = scu.getUsuario(appUser.getId());
		if (users != null && !users.isEmpty()) {
			if (StringUtils.isNotEmpty(frm.getIduser()))
				setInRequestUsuarioSeleccionado(request, frm.getIduser());
			else if (user != null && users.contains(user))
				setInRequestUsuarioSeleccionado(request, user.getId());
			else {
				user = (UsuarioVO) users.get(0);
				setInRequestUsuarioSeleccionado(request, user.getId());
			}
		} else {
			if (nuevo)
				setInRequestUsuarioSeleccionado(request, user.getId());
			else
				setInRequestUsuarioSeleccionado(request, null);
		}
	}

	private void setInRequestUsuarioSeleccionado(HttpServletRequest request,
			String idUsuarioSelect) {
		if (idUsuarioSelect != null && StringUtils.isNotBlank(idUsuarioSelect)) {
			UsuarioVO usuarioSeleccionado = getGestionControlUsuarios(request)
					.getUsuario(idUsuarioSelect);
			UsuarioPO usuarioSeleccionadoPO = new UsuarioPO(
					usuarioSeleccionado, getServiceRepository(request));
			setInTemporalSession(request, PrestamosConstants.ID_USUARIO_KEY,
					usuarioSeleccionadoPO);
			InfoOrganoPO organo = usuarioSeleccionadoPO.getOrganoExterno();
			if (organo != null) {
				setInTemporalSession(request,
						PrestamosConstants.ORGANO_USUARIO_SELECCIONADO_KEY,
						organo.getIdOrganoInterno());
				setInTemporalSession(
						request,
						PrestamosConstants.NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY,
						organo.getNombre());
				return;

			} else {
				OrganoPO organoInterno = usuarioSeleccionadoPO
						.getOrganoEnArchivo();
				if (organoInterno != null) {
					setInTemporalSession(request,
							PrestamosConstants.ORGANO_USUARIO_SELECCIONADO_KEY,
							organoInterno.getIdOrg());
					setInTemporalSession(
							request,
							PrestamosConstants.NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY,
							organoInterno.getNombre());
					return;
				}

			}
		} else
			setInTemporalSession(request, PrestamosConstants.ID_USUARIO_KEY,
					null);

		setInTemporalSession(request,
				PrestamosConstants.ORGANO_USUARIO_SELECCIONADO_KEY, null);
		setInTemporalSession(request,
				PrestamosConstants.NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY, null);
	}

	/**
	 * Inicializa un préstamo para su creación a partir del usuario conectado.
	 * 
	 * @param user
	 *            Usuario conectado a la aplicación.
	 * @return Prestamos con el año actual, el estado abierto y el nombre
	 */
	private PrestamoVO preparePrestamoVO(AppUser user) {
		PrestamoVO ret = new PrestamoVO();

		ret.setAno(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
		ret.setEstado(PrestamosConstants.ESTADO_PRESTAMO_ABIERTO);
		ret.setIdusrgestor(user.getId());

		if (!user.hasPermission(AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS)
				&& !user.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			// Es un solicitante(ROL_SOLICITANTE_PRESTAMOS) establecemos el
			// nombre del organismo y del solicitante
			ret.setIdorgsolicitante(user.getOrganization().getIdOrg());
			ret.setNorgsolicitante(user.getOrganization().getNombre());
			ret.setNusrsolicitante(user.getNombreCompleto());
		}

		return ret;
	}

	/**
	 * Crea un nuevo préstamo con los datos del formulario.
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
	protected void crearExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		PrestamoForm frm = (PrestamoForm) form;
		PrestamoVO prestamoVO = (PrestamoVO) getFromTemporalSession(request,
				PrestamosConstants.PRESTAMO_KEY);
		String tipoSolicitante = frm.getTipoSolicitante();
		setInTemporalSession(request, PrestamosConstants.PRESTAMO_FORM, frm);

		// Validamos los datos del formulario
		ActionErrors errors = frm.validate(request, true);

		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);

			if (Boolean.TRUE.equals(frm.getFromBusqueda())) {
				accionNuevoPrestamoDesdeBusquedaExecuteLogic(mapping, form,
						request, response);
			} else {
				nuevoExecuteLogic(mapping, form, request, response);
			}

		} else {
			// Establecemos los valores del prestamo
			if (frm.getNorgsolicitante() != null)
				prestamoVO.setNorgsolicitante(frm.getNorgsolicitante());
			if (frm.getNusrsolicitante() != null)
				prestamoVO.setNusrsolicitante(frm.getNusrsolicitante());
			if (frm.getFinicialreserva().trim().length() > 0)
				prestamoVO.setFinicialreserva(SolicitudesBaseForm.parseDate(frm
						.getFinicialreserva()));

			prestamoVO.setIdorgsolicitante(frm.getIdorg());
			prestamoVO.setIdusrsolicitante(frm.getIduser());
			prestamoVO.setEstado(PrestamosConstants.ESTADO_PRESTAMO_ABIERTO);
			prestamoVO.setFestado(DBUtils.getFechaActual());
			prestamoVO.setIdarchivo(frm.getIdarchivo());
			prestamoVO.setIdusrgestor(appUser.getId());
			prestamoVO.setNumreclamaciones(0);

			// Nuevos campos
			prestamoVO.setDatossolicitante(frm.getTelefonosolicitante(),
					frm.getFaxsolicitante(), frm.getEmailsolicitante());
			prestamoVO.setDatosautorizado(frm.getDatosautorizado());
			prestamoVO.setTipoentrega(frm.getTipoentrega());
			prestamoVO.setObservaciones(frm.getObservaciones());
			prestamoVO.setIdMotivo(frm.getIdmotivo());

			List listaDetalles = (List) getFromTemporalSession(request,
					SolicitudesConstants.LISTA_DETALLES_UDOCS_SOLICITUD_KEY);

			if (ListUtils.isNotEmpty(listaDetalles)) {
				prestamoVO.setFromBusqueda(true);
				prestamoVO.setDetallesPrestamo(listaDetalles);
			}

			// Creamos el prestamo con el usuario creador
			ServiceClient usuarioCreador = ServiceClient.create(appUser);
			// usuarioCreador.setId( frm.getIduser() );
			usuarioCreador.getProperties().put(
					PrestamosConstants.PROPERTY_NOMBRE,
					frm.getNusrsolicitante());
			usuarioCreador.getProperties().put(
					PrestamosConstants.PROPERTY_TIPO_SOLICITANTE,
					tipoSolicitante);
			if (tipoSolicitante.equals(PrestamosConstants.TIPO_INTERNO)) {
				UsuarioPO usuarioSelecionado = ((UsuarioPO) getFromTemporalSession(
						request, PrestamosConstants.ID_USUARIO_KEY));
				usuarioCreador.getProperties().put(
						PrestamosConstants.PROPERTY_ID_USR_SOLICITANTE,
						usuarioSelecionado.getId());
			}
			try {
				prestamosService.insertPrestamo(prestamoVO, usuarioCreador);

				removeInTemporalSession(request,
						PrestamosConstants.PRESTAMO_KEY);

				// Insertamos el punto de navegacion actual
				if (prestamoVO.isFromBusqueda()) {
					getInvocationStack(request).reset(request);
				} else {
					// Eliminamos de la miga el enlace a creacion
					popLastInvocation(request);
					// Redirigimos a la página adecuada
				}
				setReturnActionFordward(request,
						verPrestamoBeforeCreate(prestamoVO.getId()));
			} catch (PrestamoActionNotAllowedException panae) {
				errors = ExceptionMapper.getErrorsExcepcion(request, panae);
				ErrorsTag.saveErrors(request, errors);
				request.setAttribute(PrestamosConstants.CON_ERRORES,
						Boolean.TRUE);

				if (Boolean.TRUE.equals(frm.getFromBusqueda())) {
					accionNuevoPrestamoDesdeBusquedaExecuteLogic(mapping, form,
							request, response);
				} else {
					nuevoExecuteLogic(mapping, form, request, response);
				}
			}
		}
	}

	/**
	 * Genera una redirección para ver el listado de los prestamos.
	 * 
	 * @return {@link ActionForward} a la página de ver el listado de los
	 *         prestamos
	 */
	private ActionForward listadoVerConRedirect() {
		ActionForward ret = new ActionForward();
		ret.setPath(PATH_ACTION_LISTADO_PRESTAMOSVER);
		ret.setRedirect(true);

		return ret;
	}

	/**
	 * Genera una redirección para ver los detalles de un prestamo identificado
	 * por su id.
	 * 
	 * @param codigoPrestamo
	 *            Identificador del prestamos en la bd
	 * @return {@link ActionForward} a la página de ver prestamo
	 */
	protected ActionForward verPrestamoBeforeCreate(String codigoPrestamo) {
		ActionForward ret = new ActionForward();
		ret.setPath(PATH_ACTION_VER_PRESTAMO + codigoPrestamo);
		ret.setRedirect(true);
		return ret;
	}

	/**
	 * Genera el forward para ir a la pagina de unidad documentales no
	 * disponibles
	 * 
	 * @return Forward generado
	 */
	private ActionForward verNoDisponiblesAutorizadas() {
		ActionForward ret = new ActionForward();
		ret.setPath(PATH_ACTION_LISTADO_PRESTAMOS_NO_DISPONIBLES_AUTORIZADAS);
		ret.setRedirect(false);
		return ret;
	}

	/**
	 * Lleva el prestamos al formulario de edición tras las comprobaciones
	 * necesarias.
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
	protected void edicionExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Obtenemos el prestamos a editar
		PrestamoVO prestamoVO = (PrestamoVO) getFromTemporalSession(request,
				PrestamosConstants.PRESTAMO_KEY);
		if (StringUtils.isNotBlank(prestamoVO.getIdorgsolicitante())) {
			setInRequestUsuarioSeleccionado(request,
					prestamoVO.getIdusrsolicitante());
			((PrestamoForm) form).setNorgsolicitante(prestamoVO
					.getNorgsolicitante());
			((PrestamoForm) form).setNusrsolicitante(prestamoVO
					.getNusrsolicitante());
		} else {
			// no hay usuario en el prestamo, ponemos uno por defecto por si
			// cambia a interno
			setInRequestUsuarioSeleccionado(request, getAppUser(request)
					.getId());
			((PrestamoForm) form).setNorgsolicitante(prestamoVO
					.getNorgsolicitante());
			((PrestamoForm) form).setNusrsolicitante(prestamoVO
					.getNusrsolicitante());
		}
		((PrestamoForm) form).setIdarchivo(prestamoVO.getIdarchivo());
		((PrestamoForm) form).setTipoentrega(prestamoVO.getTipoentrega());
		((PrestamoForm) form).setIdmotivo(prestamoVO.getIdMotivo());

		// Ejecutamos la lógica
		edicionCodeLogic(mapping, form, request, response);

		// Guardamos el punto actual
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_EDICION,
				request);
	}

	protected void recargaEdicionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		PrestamoForm frm = (PrestamoForm) form;

		// setInRequestUsuarioSeleccionado(request, frm.getIduser());

		// Ejecutamos la lógica
		edicionCodeLogic(mapping, form, request, response);

		establecerUsuario(request, frm, appUser, false);

		// Guardamos el punto actual
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_EDICION,
				request);
	}

	/**
	 * Encapsula la lógica que lleva el prestamos al formulario de edición tras
	 * las comprobaciones necesarias.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}r
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	private void edicionCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionControlUsuariosBI scu = services
				.lookupGestionControlUsuariosBI();

		// Obtenemos el prestamos a editar
		PrestamoVO prestamoVO = (PrestamoVO) getFromTemporalSession(request,
				PrestamosConstants.PRESTAMO_KEY);

		if (prestamoVO.getFinicialreserva() != null)
			((PrestamoForm) form).setFinicialreserva(SolicitudesBaseForm
					.DateToString(prestamoVO.getFinicialreserva()));
		if (prestamoVO.getFfinalreserva() != null)
			((PrestamoForm) form).setFfinalreserva(SolicitudesBaseForm
					.DateToString(prestamoVO.getFfinalreserva()));
		if (prestamoVO.getTelefonosolicitante() != null)
			((PrestamoForm) form).setTelefonosolicitante(prestamoVO
					.getTelefonosolicitante());
		if (prestamoVO.getFaxsolicitante() != null)
			((PrestamoForm) form).setFaxsolicitante(prestamoVO
					.getFaxsolicitante());
		if (prestamoVO.getEmailsolicitante() != null)
			((PrestamoForm) form).setEmailsolicitante(prestamoVO
					.getEmailsolicitante());
		if (prestamoVO.getDatosautorizado() != null)
			((PrestamoForm) form).setDatosautorizado(prestamoVO
					.getDatosautorizado());
		if (prestamoVO.getObservaciones() != null)
			((PrestamoForm) form).setObservaciones(prestamoVO
					.getObservaciones());

		// Establecemos los elementos a mostrar en la vista
		if (prestamoVO.getFinicialreserva() != null)
			request.setAttribute(PrestamosConstants.VER_FECHA_INICIO_RESERVA,
					new Boolean(true));
		request.setAttribute(PrestamosConstants.VER_RESERVA, new Boolean(true));

		// Si el usuario no es solicitante
		if (appUser.hasPermission(AppPermissions.GESTION_PRESTAMOS_ARCHIVO)
				|| appUser
						.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			// Activamos el select en la vista
			request.setAttribute(PrestamosConstants.VER_SELECT, new Boolean(
					true));

			// Establecemos los usuarios existentes a mostrar en el select
			// Collection usuarios = scu.getUsuariosRegistradosSolitantes();
			// request.setAttribute(PrestamosConstants.LISTA_USUARIOS_KEY,
			// usuarios);
			// setInTemporalSession(request,
			// PrestamosConstants.LISTA_USUARIOS_KEY, usuarios);

			if (StringUtils.isNotBlank(prestamoVO.getIdorgsolicitante())) {
				request.setAttribute(PrestamosConstants.ES_EXTERNO,
						new Boolean(false));
				((PrestamoForm) form)
						.setTipoSolicitante(PrestamosConstants.TIPO_INTERNO);
			} else {
				request.setAttribute(PrestamosConstants.ES_EXTERNO,
						new Boolean(true));
				((PrestamoForm) form)
						.setTipoSolicitante(PrestamosConstants.TIPO_EXTERNO);
			}
		}

		Collection usuarios = scu
				.getUsuariosRegistradosSolitantes(((PrestamoForm) form)
						.getFiltroUsuario());
		setInTemporalSession(request, PrestamosConstants.LISTA_USUARIOS_KEY,
				usuarios);

		// Establecemos el usuario
		// request.setAttribute(PrestamosConstants.ID_USUARIO_KEY, appUser);

		loadMotivos(mapping, form, request, response);

		// Obtenemos la lista de archivo existentes
		GestionSistemaBI sistemaBI = services.lookupGestionSistemaBI();
		// request.setAttribute(PrestamosConstants.LISTA_ARCHIVOS,
		// sistemaBI.getArchivos());
		setInTemporalSession(request, PrestamosConstants.LISTA_ARCHIVOS,
				sistemaBI.getArchivos());

		// Obtenemos la lista de tipos de entrega de la tabla de validación de
		// tipos de entrega
		// request.setAttribute(PrestamosConstants.LISTA_TIPOS_ENTREGA,
		// sistemaBI.getListaTiposEntrega());
		setInTemporalSession(request, PrestamosConstants.LISTA_TIPOS_ENTREGA,
				sistemaBI.getListaTiposEntrega());

		// solo se puede editar en determinados estados
		if (prestamoVO.isEditable(ServiceClient.create(appUser))) {
			// Redirigimos a la pagina de edicion de prestamo
			setReturnActionFordward(request,
					mapping.findForward("edicion_prestamo"));
		} else {
			ActionErrors errors = new ActionErrors();

			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					PrestamosConstants.ERROR_PRESTAMOS_FORMATO_EXPEDIENTEFS));
			ErrorsTag.saveErrors(request, errors);

			verprestamoCodeLogic(prestamoVO.getId(), mapping, form, request,
					response);
		}
	}

	/**
	 * Guarda los cambios realizados en un prestamo.
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
	protected void guardaredicionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));

		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Obtenemos el prestamo de la sesion
		PrestamoVO prestamoVO = (PrestamoVO) getFromTemporalSession(request,
				PrestamosConstants.PRESTAMO_KEY);
		String tipoSolicitante = request.getParameter("tipoSolicitante");

		// recoger datos del form y los validamos
		PrestamoForm frm = (PrestamoForm) form;
		ActionErrors errors = frm.validate(request, true);

		if (errors.isEmpty()) {
			// Esta vez nos puede venir vacios. Como ya viene validado, en caso
			// de venir vacios los actualizamos igual.
			if (frm.getFinicialreserva().trim().length() > 0)
				prestamoVO.setFinicialreserva(SolicitudesBaseForm.parseDate(frm
						.getFinicialreserva()));
			else
				prestamoVO.setFinicialreserva(null);

			prestamoVO.setNorgsolicitante(frm.getNorgsolicitante().trim());
			prestamoVO.setNusrsolicitante(frm.getNusrsolicitante().trim());
			prestamoVO.setIdusrsolicitante(frm.getIduser());
			prestamoVO.setIdorgsolicitante(frm.getIdorg());
			prestamoVO.setIdarchivo(frm.getIdarchivo());
			prestamoVO.setObservaciones(frm.getObservaciones());
			if (StringUtils.isEmpty(frm.getIdarchivo())) {
				prestamoVO.setIdarchivo(prestamoVO.getArchivo().getId());
			}

			// Nuevos campos
			prestamoVO.setDatossolicitante(frm.getTelefonosolicitante(),
					frm.getFaxsolicitante(), frm.getEmailsolicitante());
			prestamoVO.setDatosautorizado(frm.getDatosautorizado());
			prestamoVO.setTipoentrega(frm.getTipoentrega());

			prestamoVO.setFestado(DBUtils.getFechaActual());
			prestamoVO.setIdMotivo(frm.getIdmotivo());

			ServiceClient usuarioCreador = ServiceClient.create(appUser);
			// usuarioCreador.setId( frm.getIduser() );
			usuarioCreador.getProperties().put(
					PrestamosConstants.PROPERTY_NOMBRE,
					frm.getNusrsolicitante());
			usuarioCreador.getProperties().put(
					PrestamosConstants.PROPERTY_TIPO_SOLICITANTE,
					tipoSolicitante);

			try {
				// Actualizamos los datos del prestamo
				prestamosService.actualizarPrestamo(prestamoVO, usuarioCreador);

				removeInTemporalSession(request,
						PrestamosConstants.PRESTAMO_KEY);

			} catch (PrestamoActionNotAllowedException panae) {
				errors = ExceptionMapper.getErrorsExcepcion(request, panae);
			}
		}

		// Si ha habido errores los almacenamos para mostrarlos en la pagina de
		// edicion
		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			// Cargamos el resto de datos necesario para que se visualize
			// correctamente edicion
			edicionCodeLogic(mapping, form, request, response);
			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("edicion_prestamo"));
		} else
			goBackExecuteLogic(mapping, form, request, response);
	}

	private boolean comprobarDisponibilidadParaEnvio(
			HttpServletRequest request, PrestamoVO prestamo,
			GestionPrestamosBI prestamosService) {

		// Obtenemos los detalles del préstamo
		Collection detallesPrestamo = prestamosService
				.obtenerDetallesPrestamoByUsuario(prestamo);

		// Comprobamos su disponibilidad
		boolean todosDisponibles = prestamosService
				.comprobarDisponibilidadDetallesPrestamo(prestamo,
						detallesPrestamo);

		if (!todosDisponibles) {

			// Establecemos los elementos a mostrar en la vista en función del
			// prestamos y del usuario
			PrestamosUtils.establecerVistas(prestamo, getAppUser(request),
					request, prestamosService, detallesPrestamo);

			// Establecemos los detalles
			setInTemporalSession(request,
					PrestamosConstants.DETALLE_PRESTAMO_KEY, detallesPrestamo);

			// Habilitamos la columna de disponibilidad comprobada
			request.setAttribute(PrestamosConstants.VER_COLUMNA_DISPONIBILIDAD,
					new Boolean(true));

			// Establecemos el action para la recarga del display
			request.setAttribute(PrestamosConstants.METHOD,
					METHOD_COMPROBARDISPONIBILIDAD);

		}

		return todosDisponibles;
	}

	/**
	 * Da de alta solitudes para las unidades documentales seleccionadas de un
	 * determinado préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo para el que se van a dar de alta
	 *            los solicitudes de unidades documentales
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	private void enviarPrestamoCodeLogic(String idPrestamo,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		boolean enviar = true;
		ActionErrors errores = new ActionErrors();

		try {
			PrestamoVO prestamo = prestamosService.getPrestamo(idPrestamo);

			if (!ConfigConstants.getInstance()
					.getPermitirEnviarSolicitudNoDisponible()) {

				enviar = comprobarDisponibilidadParaEnvio(request, prestamo,
						prestamosService);

				if (!enviar) {
					errores.add(
							common.Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(
									common.Constants.ERROR_GENERAL_MESSAGE,
									Messages.getString(
											PrestamosConstants.ERRORS_SOLICITUDES_DETALLES_NO_DISPONIBLES,
											request.getLocale())));
				}
			}

			if (enviar) {
				prestamosService.enviarPrestamo(idPrestamo);
				setReturnActionFordward(request,
						verPrestamoBeforeCreate(idPrestamo));
			} else {
				verPrestamo(mapping, form, request, response);
			}

		} catch (PrestamoActionNotAllowedException e) {
			errores = ExceptionMapper.getErrorsExcepcion(request, e);
			// ErrorsTag.saveErrors(request, errores);
		}

		if (errores != null && errores.size() > 0)
			ErrorsTag.saveErrors(request, errores);
	}

	/**
	 * Encapsula la lógica de finalización del proceso de autorización de un
	 * préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo que deseamos finalizar.
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	private void autorizardenegarPrestamoCodeLogic(String idPrestamo,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		ActionErrors errores = null;

		try {

			String fentrega = request.getParameter("fentrega");

			// Collection udocsNoDisponibles =
			// prestamosService.autorizardenegarPrestamo(idPrestamo);
			Collection udocsNoDisponibles = prestamosService
					.autorizardenegarPrestamo(idPrestamo, fentrega);

			// Establecemos para mostrar en la vista la lista de unidades
			// documentales no disponibles
			request.setAttribute(PrestamosConstants.LISTA_NO_DISPONIBLES,
					udocsNoDisponibles);

		} catch (PrestamoActionNotAllowedException e) {
			errores = ExceptionMapper.getErrorsExcepcion(request, e);
		}

		if (errores != null) {
			ErrorsTag.saveErrors(request, errores);

			setReturnActionFordward(request,
					verPrestamoBeforeCreate(idPrestamo));
			// verPrestamo(mapping, form, request, response);
			return;
		}
	}

	/**
	 * Encapsula la lógica de realización para la reclamación de un préstamo.
	 * 
	 * @param veces
	 *            Número de la reclamación(primera, 1, o segunda,2)
	 * @param idPrestamo
	 *            Identificador del préstamo que deseamos reclamar.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @throws PrestamoActionNotAllowedException
	 *             Si no se puede realizar la accion
	 */
	private void reclamarCodeLogic(int veces, String idPrestamo,
			HttpServletRequest request)
			throws PrestamoActionNotAllowedException {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		prestamosService.reclamar(veces, idPrestamo);
	}

	/**
	 * Elimina los prestamos indicados por el usuario en el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws Exception
	 *             si se produce algún error eliminado los prestamos de la base
	 *             de datos.
	 */
	public void eliminarprestamosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		ActionErrors errores = null;
		try {
			prestamosService.eliminarPrestamos(((PrestamoForm) form)
					.getPrestamosseleccionados());
		} catch (PrestamoActionNotAllowedException e) {
			errores = ExceptionMapper.getErrorsExcepcion(request, e);
		}

		// Si ha habido errores los grabamos para mostrarlos
		if (errores != null) {
			ErrorsTag.saveErrors(request, errores);
		}

		setReturnActionFordward(request, listadoVerConRedirect());
	}

	/**
	 * Da de alta solitudes para las unidades documentales seleccionadas de un
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
	protected void enviardesdevistaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idPrestamo = request.getParameter("idprestamo");

		// request.removeAttribute(PrestamosConstants.LISTA_NO_DISPONIBLES);
		enviarPrestamoCodeLogic(idPrestamo, mapping, form, request, response);

		// setReturnActionFordward(request,
		// verPrestamoBeforeCreate(idPrestamo));
	}

	/**
	 * Finaliza el proceso de autorización de un préstamo.
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
	protected void autorizardenegardesdevistaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el identificador del préstamo que deseamo finalizar su
		// proceso de autorización
		String idPrestamo = request.getParameter("idprestamo");

		request.removeAttribute(PrestamosConstants.LISTA_NO_DISPONIBLES);

		autorizardenegarPrestamoCodeLogic(idPrestamo, mapping, form, request,
				response);

		// Redirigimos a la pagina adecuada en función de si hay unidades
		// documentales no disponibles
		List udocs = (List) request
				.getAttribute(PrestamosConstants.LISTA_NO_DISPONIBLES);
		if (udocs != null && udocs.size() > 0)
			setReturnActionFordward(request, verNoDisponiblesAutorizadas());
		else
			setReturnActionFordward(request,
					verPrestamoBeforeCreate(idPrestamo));
	}

	/**
	 * Realiza la entrega de un préstamo.
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
	protected void entregardesdevistaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errores = new ActionErrors();
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		String idPrestamo = request.getParameter("idprestamo");
		String paramFechaDevolucion = request.getParameter("fechaDevolucion");

		Date fechaDevolucion = null;

		// Comprobar que el parámetro fecha existe
		if (!StringUtils.isBlank(paramFechaDevolucion)) {
			if (!DateUtils.isDate(paramFechaDevolucion)) {
				errores.add(
						Constants.ERROR_DATE,
						new ActionError(
								Constants.ERROR_DATE,
								Messages.getString(
										Constants.ETIQUETA_FECHA_DEVOLUCION_SOLICITUD,
										request.getLocale())));
			} else {
				fechaDevolucion = DateUtils.getDate(paramFechaDevolucion);

				// comprobar fecha mayor o igual a hoy
				if (DateUtils.isFechaMenor(fechaDevolucion,
						DateUtils.getFechaActual())) {
					errores.add(
							Constants.FECHA_ANTERIOR_A_HOY,
							new ActionError(
									Constants.FECHA_ANTERIOR_A_HOY,
									Messages.getString(
											Constants.ETIQUETA_FECHA_DEVOLUCION_SOLICITUD,
											request.getLocale())));
				}

				PrestamoVO prestamoVO = (PrestamoVO) getFromTemporalSession(
						request, PrestamosConstants.PRESTAMO_KEY);
				if (!fechaDevolucion.equals(prestamoVO.getFmaxfinprestamo())) {
					List detalles = (List) getFromTemporalSession(request,
							PrestamosConstants.DETALLE_PRESTAMO_KEY);

					// Si se ha modificado la fecha de devolución del préstamo,
					// hay que volver a comprobar la disponibilidad de sus
					// detalles
					if (!prestamosService
							.comprobarDisponibilidadDetallesPrestamoXFecha(
									detalles,
									DateUtils.getFechaActualSinHora(),
									fechaDevolucion)) {
						errores.add(
								Constants.FECHA_DEVOLUCION_NO_VALIDA,
								new ActionError(
										Constants.FECHA_DEVOLUCION_NO_VALIDA,
										Messages.getString(
												Constants.ETIQUETA_FECHA_DEVOLUCION_SOLICITUD,
												request.getLocale())));
					}
				}
			}
		}

		if (errores.isEmpty()) {
			try {
				prestamosService.entregarPrestamo(idPrestamo, fechaDevolucion);
			} catch (PrestamoActionNotAllowedException e) {
				errores = ExceptionMapper.getErrorsExcepcion(request, e);
			}
		}

		if (!errores.isEmpty()) {
			ErrorsTag.saveErrors(request, errores);
			goLastClientExecuteLogic(mapping, form, request, response);
		} else {
			// Redirigimos a la pagina de ver prestamos
			setReturnActionFordward(request,
					verPrestamoBeforeCreate(idPrestamo));
		}

	}

	/**
	 * Prepara la página para la impresión de la primera reclamación de un
	 * préstamo.
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
	protected void imprimirreclamacion1desdevistaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		imprimirReclamacionCodeLogic(request);

		setReturnActionFordward(request,
				mapping.findForward("imprimir_reclamacion1_prestamo"));
	}

	/**
	 * Prepara la página para la impresión de la segunda reclamación de un
	 * préstamo.
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
	protected void imprimirreclamacion2desdevistaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		imprimirReclamacionCodeLogic(request);

		setReturnActionFordward(request,
				mapping.findForward("imprimir_reclamacion2_prestamo"));
	}

	/**
	 * Solicita la entrega de una reserva de un préstamo.
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
	protected void solicitardesdereservaprestamoExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Recuperamos el identificador del prestamo que deseamos solicitar su
		// entrega
		String idPrestamo = request.getParameter("idprestamo");

		try {
			prestamosService.solicitarEntregaReserva(idPrestamo);
		} catch (PrestamoActionNotAllowedException panae) {
			ActionErrors errors = ExceptionMapper.getErrorsExcepcion(request,
					panae);
			ErrorsTag.saveErrors(request, errors);
		}

		setReturnActionFordward(request, verPrestamoBeforeCreate(idPrestamo));
	}

	/**
	 * Realiza la denegación de una prorroga de un préstamo.
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
	protected void denegarprorrogadesdevistaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		GestionRechazosBI rechazosBI = services.lookupGestionMotivosRechazoBI();
		String idPrestamo = request.getParameter("idprestamo");
		// String motivo = request.getParameter("motivo");
		String idMotivo = request.getParameter("idMotivo");
		MotivoRechazoVO motivo = rechazosBI.getMotivoRechazoById(idMotivo);
		try {
			prestamosService.denegarProrrogas(idPrestamo, motivo.getMotivo(),
					idMotivo);
		} catch (PrestamoActionNotAllowedException e) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					e);
			ErrorsTag.saveErrors(request, errores);
		}

		// Redirigimos al apagina adecuada
		setReturnActionFordward(request, verPrestamoBeforeCreate(idPrestamo));
	}

	/**
	 * Realiza la primera reclamación de un préstamo.
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
	protected void reclamarunadesdevistaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Recuperamos el identificador del préstamo que deseamos reclamart
		String idPrestamo = request.getParameter("idprestamo");

		try {
			reclamarCodeLogic(1, idPrestamo, request);
		} catch (PrestamoActionNotAllowedException panae) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					panae);
			ErrorsTag.saveErrors(request, errores);
		}

		// Redirigimos a la página adecudada
		setReturnActionFordward(request, verPrestamoBeforeCreate(idPrestamo));
	}

	/**
	 * Realiza la segunda reclamación de un préstamo.
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
	protected void reclamardosdesdevistaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Recuperamos el identificador del préstamo que deseamos reclamar
		String idPrestamo = request.getParameter("idprestamo");

		try {
			reclamarCodeLogic(2, idPrestamo, request);
		} catch (PrestamoActionNotAllowedException panae) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					panae);
			ErrorsTag.saveErrors(request, errores);
		}

		// Redirigimos a la página adecuada
		setReturnActionFordward(request, verPrestamoBeforeCreate(idPrestamo));
	}

	/**
	 * Encapsula la lógica de preparacion de la página para la impresión de la
	 * reclamación de un préstamo.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	private void imprimirReclamacionCodeLogic(HttpServletRequest request) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Recuperamos el prestamo y lo almacenamos para mostrarlo en la vista
		String codigo = request.getParameter("idprestamo");
		PrestamoVO prestamo_VO = prestamosService.getPrestamo(codigo);
		PrestamoPO prestamoPO = (PrestamoPO) PrestamoToPO.getInstance(
				request.getLocale(), services).transform(prestamo_VO);
		setInTemporalSession(request, PrestamosConstants.PRESTAMO_KEY,
				prestamoPO);

		// Obtenemos los detalles y los metemos para mostrarlos en la vista
		List lista = (List) prestamosService
				.getDetallesPrestamoEntregadas(prestamo_VO.getId());
		setInTemporalSession(request, PrestamosConstants.DETALLE_PRESTAMO_KEY,
				lista);
		// request.getSession().setAttribute(, );
	}

	/**
	 * Realiza la devolución de las unidades documentales seleccioandas.
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
	public void devolverExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI service = services.lookupGestionPrestamosBI();

		// Obtenemos las unidades documentales a devolver
		String idsDetallesPrestamos = ((PrestamoForm) form).getUdocsadevolver();
		// Obtenemos sus ids para el servicio
		ArrayList identificadores = new ArrayList();
		StringOwnTokenizer st = new StringOwnTokenizer(idsDetallesPrestamos,
				"\r\n");
		while (st.hasMoreTokens())
			identificadores.add(st.nextToken());

		try {
			service.devolverUnidadesDocumentales(identificadores);
		} catch (PrestamoActionNotAllowedException panae) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					panae);

			ErrorsTag.saveErrors(request, errores);
		}

		// Establecemos los elementos a mostrar en la vista
		// request.setAttribute(PrestamosConstants.LISTA_DETALLES_DEVUELTOS_FALLOS,
		// identificadores_fallidos);
		// request.setAttribute(PrestamosConstants.LISTA_DETALLES_DEVUELTOS_EXITOS,
		// identificadores_exitosos);
		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, mappings.findForward("devolver_udocs"));
	}

	/**
	 * Realiza la comprobacion de la disponibilidad de las unidades documentales
	 * para el prestamo indicado.
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
	public void comprobardisponibilidadExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el usuario que esta trabajando
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Obtenemos el préstamo por su identificador
		PrestamoVO prestamo_VO = prestamosService.getPrestamo(request
				.getParameter("idprestamo"));

		if (prestamo_VO != null) {
			// Obtenemos los detalles del prestamo
			Collection detallesPrestamos = prestamosService
					.obtenerDetallesPrestamoByUsuario(prestamo_VO);

			// Comprobamos su disponibilidad
			prestamosService.comprobarDisponibilidadDetallesPrestamo(
					prestamo_VO, detallesPrestamos);

			// Establecemos los elementos a mostrar en la vista en función del
			// prestamos y del usuario
			PrestamosUtils.establecerVistas(prestamo_VO, appUser, request,
					prestamosService, detallesPrestamos);

			// Establecemos los detalles
			setInTemporalSession(request,
					PrestamosConstants.DETALLE_PRESTAMO_KEY, detallesPrestamos);

			// Habilitamos la columna de disponibilidad comprobada
			request.setAttribute(PrestamosConstants.VER_COLUMNA_DISPONIBILIDAD,
					new Boolean(true));

			// Establecemos el action para la recarga del display
			request.setAttribute(PrestamosConstants.METHOD,
					METHOD_COMPROBARDISPONIBILIDAD);
		}

		// generamos la redirección
		verPrestamo(mappings, form, request, response);
	}

	/**
	 * Realiza la comprobacion de la disponibilidad de las unidades documentales
	 * para el prestamo indicado que se va a entregar.
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
	public void comprobardisponibilidadentregaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Obtenemos el usuario que esta trabajando
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		String codigo = request.getParameter("idprestamo");
		// Obtenemos el préstamo por su identificador
		PrestamoVO prestamo_VO = prestamosService.getPrestamo(codigo);

		// Obtenemos los detalles del prestamo
		Collection detallesPrestamos = prestamosService
				.obtenerDetallesPrestamoByUsuario(prestamo_VO);

		// Comprobamos su disponibilidad
		prestamosService.comprobarDisponibilidadDetallesPrestamo(prestamo_VO,
				detallesPrestamos);

		// Establecemos los elementos a mostrar en la vista en función del
		// prestamos y del usuario
		PrestamosUtils.establecerVistas(prestamo_VO, appUser, request,
				prestamosService, detallesPrestamos);

		// Establecemos los detalles
		setInTemporalSession(request, PrestamosConstants.DETALLE_PRESTAMO_KEY,
				detallesPrestamos);

		// Habilitamos la columna de disponibilidad comprobada
		request.setAttribute(PrestamosConstants.VER_COLUMNA_DISPONIBILIDAD,
				new Boolean(true));

		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD,
				METHOD_COMPROBARDISPONIBILIDADENTREGA);

		// generamos la redirección
		verPrestamo(mappings, form, request, response);
	}

	/**
	 * Realiza la comprobacion de la disponibilidad de las unidades documentales
	 * para el prestamo indicado que se va a prorrogar.
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
	public void comprobardisponibilidadprorrogaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Obtenemos el usuario que esta trabajando
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		String codigo = request.getParameter("idprestamo");

		// Obtenemos el préstamo por su identificador
		PrestamoVO prestamo_VO = prestamosService.getPrestamo(codigo);

		// Obtenemos los detalles del prestamo
		Collection detallesPrestamos = prestamosService
				.obtenerDetallesPrestamoByUsuario(prestamo_VO);
		// if
		// (appUser.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
		// request.setAttribute(
		// PrestamosConstants.VER_LISTA_DETALLES_PARA_DEPOSITO, new
		// Boolean(true) );
		// Comprobamos su disponibilidad
		prestamosService.comprobarDisponibilidadDetallesPrestamoProrroga(
				prestamo_VO, detallesPrestamos);
		// Establecemos los elementos a mostrar en la vista en función del
		// prestamos y del usuario
		PrestamosUtils.establecerVistas(prestamo_VO, appUser, request,
				prestamosService, detallesPrestamos);

		// Establecemos los detalles
		setInTemporalSession(request, PrestamosConstants.DETALLE_PRESTAMO_KEY,
				detallesPrestamos);

		// Habilitamos la columna de disponibilidad comprobada
		request.setAttribute(PrestamosConstants.VER_COLUMNA_DISPONIBILIDAD,
				new Boolean(true));
		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD,
				METHOD_COMPROBARDISPONIBILIDADPRORROGA);

		// generamos la redirección
		verPrestamo(mappings, form, request, response);
	}

	/**
	 * Elimina el préstamo actual.
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
	public void eliminarPrestamoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Identificador del préstamo
		String id = request.getParameter(Constants.ID);
		if (StringUtils.isNotBlank(id)) {
			try {
				// Eliminar el préstamo
				getGestionPrestamosBI(request).eliminarPrestamos(
						new String[] { id });

				// Volver a la página anterior
				goBackExecuteLogic(mappings, form, request, response);
				return;
			} catch (PrestamoActionNotAllowedException e) {
				obtenerErrores(request, true).add(
						ExceptionMapper.getErrorsExcepcion(request, e));
			}
		}

		// Volver a la misma página
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void actualizarCampoObservacionesExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List udocsPrestamo = (List) getFromTemporalSession(request,
				PrestamosConstants.DETALLE_PRESTAMO_KEY);
		String position = request.getParameter("position");

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();

		DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) udocsPrestamo
				.get(Integer.parseInt(position) - 1);

		detallePrestamo.setInformacion(DetallePrestamoVO
				.createInformacionXML(request
						.getParameter("valorCampoObservaciones")));
		prestamosBI.actualizarDetallePrestamo(detallePrestamo);

		goLastClientExecuteLogic(mappings, form, request, response);
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

		PrestamoForm frm = (PrestamoForm) form;

		prestamosBI.actualizarObservaciones(frm.getIdprestamo(),
				frm.getObservaciones());

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	private ActionErrors validarExpedienteFS(HttpServletRequest request,
			String expedienteFS, DetallePrestamoVO detallePrestamo) {

		ConfiguracionServicios cs = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionServicios();

		ActionErrors errors = new ActionErrors();

		// Si se ha configurado el sistema para autocompletar el número de
		// expediente, lo realizamos
		if (StringUtils.isNotEmpty(expedienteFS)
				&& StringUtils.isNotEmpty(cs.getCaracterAutoExpFS())
				&& StringUtils.isNotEmpty(cs.getLimiteAutoExpFS())) {

			int limiteInt = new Integer(cs.getLimiteAutoExpFS()).intValue();
			if (limiteInt > expedienteFS.length()) {
				int nofChars = limiteInt - expedienteFS.length();
				int i = 0;
				while (i < nofChars) {
					expedienteFS = cs.getCaracterAutoExpFS() + expedienteFS;
					i++;
				}
			}
		}

		// Rellenamos el expediente del detalle del préstamo por si se ha
		// modificado
		detallePrestamo.setExpedienteudoc(expedienteFS);

		// Si se ha definido un patrón, se comprueba si el número de expediente
		// encaja con él
		if (StringUtils.isNotEmpty(cs.getPatronExpFS())) {

			Pattern patron = Pattern.compile(cs.getPatronExpFS());

			// Chequeamos si el número de expediente cumple el patrón
			Matcher encaja = patron.matcher(expedienteFS);

			if (!encaja.matches()) {
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								PrestamosConstants.ERROR_PRESTAMOS_FORMATO_EXPEDIENTEFS,
								cs.getEtiquetaValExpFS()));
				ErrorsTag.saveErrors(request, errors);
			}
		}

		return errors;
	}

	/**
	 * Actualiza el número de expediente de fracción de serie a solicitar
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void actualizarCampoExpedienteFSExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		List udocsPrestamo = (List) getFromTemporalSession(request,
				PrestamosConstants.DETALLE_PRESTAMO_KEY);
		String position = request.getParameter("position");

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();

		DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) udocsPrestamo
				.get(Integer.parseInt(position) - 1);

		String expedienteFS = request.getParameter("valorCampoExpedienteFS");
		ActionErrors errors = validarExpedienteFS(request, expedienteFS,
				detallePrestamo);

		if (errors.isEmpty()) {
			// detallePrestamo.setExpedienteudoc(expedienteFS);

			prestamosBI.actualizarDetallePrestamo(detallePrestamo);
		}

		goLastClientExecuteLogic(mappings, form, request, response);

	}

	/**
	 * Carga los motivos para el tipo de solicitante seleccionado en el momento
	 * de crear un préstamo
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
	protected void loadMotivosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		loadMotivos(mappings, form, request, response);

		List motivos = (List) getFromTemporalSession(request,
				SolicitudesConstants.LISTA_MOTIVOS_KEY);

		PrestamoForm prestamoForm = (PrestamoForm) form;

		String idMotivo = getIdMotivoXDefecto(motivos);

		if (idMotivo != null) {
			prestamoForm.setIdmotivo(idMotivo);
		}

		setReturnActionFordward(request, mappings.findForward("nuevo_prestamo"));
	}

	protected void loadMotivos(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();

		// Si no selecciono externo siempre se cargan los motivos de interno
		// (esta chequeado por defecto)
		PrestamoForm prestamoForm = (PrestamoForm) form;
		Collection motivos = new ArrayList();
		if (prestamoForm != null && prestamoForm.getTipoSolicitante() != null) {
			Integer[] visibilidad = null;
			if (PrestamosConstants.TIPO_EXTERNO.equals(prestamoForm
					.getTipoSolicitante()))
				motivos = prestamosBI.getMotivosByTipoUsuario(new Integer(
						PrestamosConstants.TIPO_EXTERNO_INT), visibilidad);
			else {
				if (ListUtils.isEmpty(getAppUser(request)
						.getCustodyArchiveList()))
					visibilidad = new Integer[] {
							new Integer(
									SolicitudesConstants.VISIBILIDAD_NO_ARCHIVO),
							new Integer(SolicitudesConstants.VISIBILIDAD_AMBOS) };
				else
					visibilidad = new Integer[] {
							new Integer(
									SolicitudesConstants.VISIBILIDAD_ARCHIVO),
							new Integer(SolicitudesConstants.VISIBILIDAD_AMBOS) };
				motivos = prestamosBI.getMotivosByTipoUsuario(new Integer(
						PrestamosConstants.TIPO_INTERNO_INT), visibilidad);
			}
		}
		setInTemporalSession(request, SolicitudesConstants.LISTA_MOTIVOS_KEY,
				motivos);

		// Si el usuario no es solicitante
		AppUser appUser = getAppUser(request);
		if (appUser.hasPermission(AppPermissions.GESTION_PRESTAMOS_ARCHIVO)
				|| appUser
						.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
			request.setAttribute(PrestamosConstants.VER_SELECT, new Boolean(
					true));
	}
}