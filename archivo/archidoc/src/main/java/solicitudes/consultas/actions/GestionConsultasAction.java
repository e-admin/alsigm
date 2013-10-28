package solicitudes.consultas.actions;

import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import salas.SalasConsultaConstants;
import salas.model.GestionSalasConsultaBI;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.exceptions.ConsultaActionNotAllowedException;
import solicitudes.consultas.forms.ConsultaForm;
import solicitudes.consultas.utils.ExceptionMapper;
import solicitudes.consultas.vos.ConsultaToPO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.consultas.vos.MotivoConsultaVO;
import solicitudes.consultas.vos.TemaVO;
import solicitudes.forms.SolicitudesBaseForm;
import solicitudes.prestamos.PrestamosConstants;
import util.CollectionUtils;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.bi.GestionConsultasBI;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;

/**
 * Action que engloba todas las acciones relacionadas con consultas.
 */
public class GestionConsultasAction extends BaseGestionConsultasAction {

	private final static String[] PERMISOS_ACCIONES = new String[] {
			AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS,
			AppPermissions.GESTION_SOLICITUDES_CONSULTAS };

	protected void homeconsultasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceClient serviceClient = ServiceClient.create(appUser);
		List orgList = new ArrayList(appUser.getDependentOrganizationList());
		orgList.add(appUser.getOrganization());
		serviceClient.getProperties().put(
				ConsultasConstants.PROPERTY_DEPENDENTORGANIZATIONLIST, orgList);

		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		if (appUser.hasPermission(AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS)) {
			List consultasEnElaboracion = consultasService
					.getConsultasXUsuarioConsultor(appUser.getId());
			if (consultasEnElaboracion.size() > 5)
				consultasEnElaboracion = consultasEnElaboracion.subList(0, 5);
			CollectionUtils.transform(consultasEnElaboracion,
					ConsultaToPO.getInstance(request.getLocale(), services));
			request.setAttribute(
					ConsultasConstants.LISTA_CONSULTAS_EN_ELABORACION_KEY,
					consultasEnElaboracion);
		}

		if (appUser.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS)) {
			List consultasAGestionar = consultasService
					.getListadoConsultasGestionar(serviceClient);
			if (consultasAGestionar.size() > 5)
				consultasAGestionar = consultasAGestionar.subList(0, 5);
			CollectionUtils.transform(consultasAGestionar,
					ConsultaToPO.getInstance(request.getLocale(), services));
			request.setAttribute(
					ConsultasConstants.LISTA_CONSULTAS_A_GESTIONAR_KEY,
					consultasAGestionar);
		}

		if (appUser.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES)) {
			List consultasAEntregar = consultasService
					.obtenerListadoEntregar(appUser.getIdsArchivosUser());
			if (consultasAEntregar.size() > 5)
				consultasAEntregar = consultasAEntregar.subList(0, 5);

			CollectionUtils.transform(consultasAEntregar,
					ConsultaToPO.getInstance(request.getLocale(), services));
			request.setAttribute(
					ConsultasConstants.LISTA_CONSULTAS_A_ENTREGAR_KEY,
					consultasAEntregar);
		}

		saveCurrentInvocation(KeysClientsInvocations.HOME_CONSULTAS, request);

		setReturnActionFordward(request, mapping.findForward("home_consultas"));
	}

	/**
	 * Muestra el listado de las consultas en función del rol del usuario
	 * conectado para que pueda realizar las gestiones oportunas.
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
	protected void listadoconsultaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceClient sc = ServiceClient.create(userVO);
		List orgList = new ArrayList(userVO.getDependentOrganizationList());
		orgList.add(userVO.getOrganization());
		sc.getProperties().put(
				ConsultasConstants.PROPERTY_DEPENDENTORGANIZATIONLIST, orgList);
		ServiceRepository services = ServiceRepository.getInstance(sc);
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		// Establecemos este punto como el sitio actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_CONSULTAS, request);

		// Establecemos los elementos de la vista
		Collection listaConsultas = consultasService
				.getListadoConsultasGestionar(sc);
		CollectionUtils.transform(listaConsultas,
				ConsultaToPO.getInstance(request.getLocale(), services));
		request.setAttribute(ConsultasConstants.LISTA_CONSULTAS_KEY,
				listaConsultas);
		request.setAttribute(ConsultasConstants.VER_BOTON_SOLICITAR_RESERVA,
				new Boolean(false));
		request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR,
				new Boolean(false));
		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD, METHOD_LISTADO_CONSULTA);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_consulta"));
	}

	/**
	 * Muestra el listado de las consultas con reserva en función del rol del
	 * usuario conectado para que pueda realizar las gestiones oportunas.
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
	protected void listadoconsultasreservaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceClient sc = ServiceClient.create(userVO);
		List orgList = new ArrayList(userVO.getDependentOrganizationList());
		orgList.add(userVO.getOrganization());
		sc.getProperties().put(
				ConsultasConstants.PROPERTY_DEPENDENTORGANIZATIONLIST, orgList);
		ServiceRepository services = ServiceRepository.getInstance(sc);
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		// Establecemos este punto como el sitio actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_CONSULTAS, request);

		// Establecemos los elementos de la vista
		Collection listaConsultas = consultasService
				.getListadoConsultasGestionarReserva(sc);
		CollectionUtils.transform(listaConsultas,
				ConsultaToPO.getInstance(request.getLocale(), services));
		request.setAttribute(ConsultasConstants.LISTA_CONSULTAS_KEY,
				listaConsultas);
		request.setAttribute(ConsultasConstants.VER_BOTON_SOLICITAR_RESERVA,
				new Boolean(false));
		request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR,
				new Boolean(false));
		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD, METHOD_LISTADO_VER);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_consulta"));
	}

	/**
	 * Muestra el listado de los prestamos según el rol del usuario que esta
	 * accediendo a la aplicación para su entrega.
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
	protected void listadoconsultaentregarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(userVO));
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		// Establecemos el punto de navegacion actual aqui
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_CONSULTAS_ENTREGAR,
				request);

		// Establecemos los elementos de la vista
		Collection listaConsultas = consultasService
				.obtenerListadoEntregar(userVO.getIdsArchivosUser());
		CollectionUtils.transform(listaConsultas,
				ConsultaToPO.getInstance(request.getLocale(), services));
		request.setAttribute(ConsultasConstants.LISTA_CONSULTAS_KEY,
				listaConsultas);

		// Redirigimos a la página adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_consulta"));
	}

	/**
	 * Muestra el listado de las consultas actuales del usuario.
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
	protected void listadoconsultaverExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(userVO));
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		// Obtenemos las consultas para el usuario(solicitante)
		Collection consultas = consultasService
				.getConsultasXUsuarioConsultor(userVO.getId());
		CollectionUtils.transform(consultas,
				ConsultaToPO.getInstance(request.getLocale(), services));

		// Fijamos este punto como el elemento actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_CONSULTAS_VER,
				request);

		// Establecemos los elementos de la vista
		request.setAttribute(ConsultasConstants.LISTA_CONSULTAS_KEY, consultas);
		request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR,
				new Boolean(true));

		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD, METHOD_LISTADO_VER);

		// Redirigimos a la página adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_consulta"));
	}

	/**
	 * Muestra el listado de las consultas actuales del usuario.
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
	protected void listadoconsultasenelaboracionverExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(userVO));
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		// Obtenemos las consultas para el usuario(solicitante)
		Collection consultas = consultasService
				.getConsultasAbiertasXUsuarioConsultor(userVO.getId());
		CollectionUtils.transform(consultas,
				ConsultaToPO.getInstance(request.getLocale(), services));

		// Fijamos este punto como el elemento actual de navegacion
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_LISTADO_CONSULTAS_VER,
				request);

		// Establecemos los elementos de la vista
		request.setAttribute(ConsultasConstants.LISTA_CONSULTAS_KEY, consultas);
		request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR,
				new Boolean(true));

		// Establecemos el action para la recarga del display
		request.setAttribute(PrestamosConstants.METHOD,
				METHOD_LISTADO_VER_ELABORACION);

		// Redirigimos a la página adecuada
		setReturnActionFordward(request,
				mapping.findForward("listado_consulta"));
	}

	/**
	 * Redirige a la pagina de información de las unidades documentales no
	 * disponibles para una consulta.
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
	protected void listadoconsultanodisponiblesExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = listadoConsultaNoDisponible(mapping, form,
				request, response);
		setReturnActionFordward(request, forward);
	}

	/**
	 * Muestra una consulta que ha sido creada.
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
	protected void verconsultaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idConsulta = ((ConsultaForm) form).getId();

		if (StringUtils.isEmpty(idConsulta)) {
			idConsulta = request.getParameter("idconsulta");
		}

		// verconsultaCodeLogic(mapping, form, request, response);
		verconsultaCodeLogic(idConsulta, mapping, form, request, response);

		// Establecemos este punto como el pto de navegación
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_VERCONSULTA,
				request);
	}

	protected void accionNuevaConsultaDesdeBusquedaExecuteLogic(
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

	protected void nuevoExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		nuevoCodeLogic(mapping, form, request, response);
	}

	/**
	 * Crea una nueva consulta.
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
		AppUser userVO = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(userVO));
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();
		// GestionSalasConsultaBI salasBI = services.lookupGestionSalasBI();

		// Recojo datos del formulario
		ConsultaForm consultaForm = (ConsultaForm) form;
		ActionErrors errors = new ActionErrors();

		try {
			ConsultaVO consultaVO = (ConsultaVO) getFromTemporalSession(
					request, ConsultasConstants.CONSULTA_KEY);
			// asignarTipoEntidad(request,
			// consultaForm.getTipoentconsultora().intValue());

			consultaForm.populate(userVO.getId(), consultaVO);

			// Validamos los datos del formulario
			errors = consultaForm.validate(request, true);

			if (!errors.isEmpty()) {
				ErrorsTag.saveErrors(request, errors);
				goForwardConsulta(mapping, consultaForm, request);
				// setReturnActionFordward(request,
				// mapping.findForward("nueva_consulta"));
			} else {

				// MotivoConsultaVO motivo =
				// consultasService.getMotivoConsultaById(consultaForm.getIdMotivo());

				// Creamos el prestamo con el usuario creador
				ServiceClient usuarioCreador = ServiceClient.create(userVO);
				usuarioCreador.setId(consultaForm.getIdusrsolicitante());
				usuarioCreador.getProperties().put(
						ConsultasConstants.PROPERTY_NOMBRE,
						consultaVO.getNusrconsultor());
				usuarioCreador.getProperties().put(
						ConsultasConstants.PROPERTY_TIPO_SOLICITANTE,
						request.getParameter("solicitante"));

				List listaDetalles = (List) getFromTemporalSession(request,
						SolicitudesConstants.LISTA_DETALLES_UDOCS_SOLICITUD_KEY);

				if (ListUtils.isNotEmpty(listaDetalles)) {
					consultaVO.setFromBusqueda(true);
					consultaVO.setDetallesConsulta(listaDetalles);
				}

				// consultaVO.setIdusrcsala(usuarioSala.getId());

				consultaVO.setFestado(DateUtils.getFechaActual());
				consultaVO
						.setEstado(ConsultasConstants.ESTADO_CONSULTA_ABIERTA);

				try {
					consultasService.insertConsulta(consultaVO, usuarioCreador);

					removeInTemporalSession(request,
							ConsultasConstants.CONSULTA_KEY);
					// Eliminamos de la miga el enlace a creacion

					if (consultaVO.isFromBusqueda()) {
						getInvocationStack(request).reset(request);
					} else {
						popLastInvocation(request);
					}
					// redirigir a la edicion de la consulta
					setReturnActionFordward(request,
							verConsultaBeforeCreate(consultaVO.getId()));
				} catch (ConsultaActionNotAllowedException canae) {
					errors = ExceptionMapper.getErrorsExcepcion(request, canae);
					ErrorsTag.saveErrors(request, errors);

					// ClientInvocation cli =
					// getInvocationStack(request).getLastClientInvocation();
					// cli.addParameters(((ConsultaForm) form).getMap());

					// Redirigimos a la pagina adecuada
					goForwardConsulta(mapping, consultaForm, request);
				}
			}
		} catch (Exception e) {
			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							SolicitudesConstants.ERROR_AL_GUARDAR_CONSULTA, e
									.toString(), request.getLocale()));
			ErrorsTag.saveErrors(request, errors);
			goForwardConsulta(mapping, consultaForm, request);
		}

	}

	/**
	 * Acción para añadir a una consulta las unidades seleccionadas.
    *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void accionAniadirAConsultaDesdeBusquedaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (getServiceClient(request).hasAnyPermission(PERMISOS_ACCIONES)) {

			// Guardamos el punto actual

			// Obtener el archivo al que pertenecen las unidades documentales.
			ArchivoVO archivoVO = (ArchivoVO) getFromTemporalSession(request,
					SolicitudesConstants.ARCHIVO_SOLICITUD_KEY);

			if (archivoVO != null) {
				ServiceRepository services = getServiceRepository(request);
				List listaConsultas = (List) getFromTemporalSession(request,
						SolicitudesConstants.CONSULTAS_PARA_ANIADIR_UDOCS_KEY);

				CollectionUtils
						.transform(listaConsultas, ConsultaToPO.getInstance(
								request.getLocale(), services));
				request.setAttribute(ConsultasConstants.LISTA_CONSULTAS_KEY,
						listaConsultas);

				request.setAttribute(
						ConsultasConstants.VER_BOTON_SOLICITAR_RESERVA,
						new Boolean(false));
				request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR,
						new Boolean(false));

				request.setAttribute(
						ConsultasConstants.ACCION_ANIADIR_A_CONSULTA_KEY,
						new Boolean(true));

				List listaUnidades = (List) getFromTemporalSession(request,
						SolicitudesConstants.LISTA_UDOCS_SOLICITUD_KEY);

				if (ListUtils.isNotEmpty(listaUnidades)) {
					setInTemporalSession(
							request,
							SolicitudesConstants.LISTA_DETALLES_UDOCS_SOLICITUD_KEY,
							getListaInfoDetallesConsultas(listaUnidades));
				}

				saveCurrentInvocation(
						KeysClientsInvocations.SOLICITUDES_SELECCIONAR_CONSULTA,
						request);

				request.setAttribute(ConsultasConstants.METHOD,
						METHOD_ACCION_ANIADIR);
			}
			// Generamos la redireccion a la pagina adecuada
			setReturnActionFordward(request,
					mapping.findForward("listado_consulta"));
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
	protected void aniadirUdocsAConsultaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			List listaDetalles = (List) getFromTemporalSession(request,
					SolicitudesConstants.LISTA_DETALLES_UDOCS_SOLICITUD_KEY);

			GestionConsultasBI consultasService = getGestionConsultasBI(request);

			ConsultaForm formulario = (ConsultaForm) form;

			ConsultaVO consultaVO = consultasService.getConsulta(formulario
					.getId());

			if (ListUtils.isNotEmpty(listaDetalles)) {
				consultaVO.setFromBusqueda(true);
				consultaVO.setDetallesConsulta(listaDetalles);
			}

			ServiceClient usuarioCreador = ServiceClient
					.create(getAppUser(request));

			consultasService.insertarDetallesAConsulta(consultaVO,
					usuarioCreador);
			getInvocationStack(request).reset(request);

			setReturnActionFordward(request,
					verConsultaBeforeCreate(consultaVO.getId()));
		} catch (ConsultaActionNotAllowedException panae) {
			ActionErrors errors = new ActionErrors();
			errors = ExceptionMapper.getErrorsExcepcion(request, panae);
			ErrorsTag.saveErrors(request, errors);
			// Generamos la redireccion a la pagina adecuada
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Lleva al formulario de edición de consultas tras las comprobaciones
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
		AppUser userVO = getAppUser(request);
		ServiceClient sc = ServiceClient.create(userVO);
		ConsultaForm frm = (ConsultaForm) form;

		// Obtenemos la consulta a editar
		ConsultaVO consultaVO = (ConsultaVO) getFromTemporalSession(request,
				ConsultasConstants.CONSULTA_KEY);

		// indicar usuario seleccionado
		setInRequestUsuarioSeleccionado(request, userVO.getId());

		// Establecemos la fecha inicial de reserva en caso de teneral
		if (consultaVO.getFinicialreserva() != null) {
			frm.setFinicialreserva(SolicitudesBaseForm.DateToString(consultaVO
					.getFinicialreserva()));
			frm.setTienereserva(Boolean.TRUE);
		}

		// Habilitamos los botones para visualizarla
		if (consultaVO.getFinicialreserva() != null)
			request.setAttribute(ConsultasConstants.VER_FECHA_INICIO_RESERVA,
					new Boolean(true));
		request.setAttribute(ConsultasConstants.VER_RESERVA, new Boolean(true));

		// solo se puede editar en determinados estados de la consulta
		if (consultaVO.isEditable(sc)) {

			frm.setTipoentconsultora(consultaVO.getTipoentconsultora());
			frm.set(consultaVO);

			if (frm.isTipoEntidadInvestigador()) {

				if (userVO.hasPermissionGestionSolicitudesConsultas()) {
					cargarListaUsuariosSolicitantes(request, frm);
				} else {
					cargarDatosUsuarioSeleccionado(request, frm);
				}
			}

			cargarListaMotivos(request, frm);
			cargarListaTemas(request, frm);

			cargarListaTiposEntrega(request);
			cargarListaArchivos(request, frm);

			// Guardamos el punto actual
			saveCurrentInvocation(
					KeysClientsInvocations.SOLICITUDES_EDICION_CONSULTA,
					request);
			goForwardConsulta(mapping, frm, request);
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					ConsultasConstants.ERRORS_SOLICITUDES_CONSULTA_NO_EDITABLE));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Finaliza el proceso de autorización de una consulta.
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
		// Obtenemos el identificador de la consulta que deseamos finalizar su
		// proceso de autorizacion
		String idConsulta = request.getParameter("idconsulta");

		request.removeAttribute(ConsultasConstants.LISTA_NO_DISPONIBLES);

		autorizardenegarConsultaCodeLogic(idConsulta, mapping, form, request,
				response);

		// Redirigimos a la pagina adecuada en función de si hay unidades
		// documentales no disponibles
		List udocs = (List) request
				.getAttribute(ConsultasConstants.LISTA_NO_DISPONIBLES);
		if (udocs != null && udocs.size() > 0)
			setReturnActionFordward(request, verNoDisponiblesAutorizadas());
		else
			setReturnActionFordward(request,
					verConsultaBeforeCreate(idConsulta));
	}

	/**
	 * Redirige a la pagina de consultas no dipsonibles y autorizadas.
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
	protected void listadoconsultasnodisponiblesautorizadasExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = listadoConsultaNoDisponibleAutorizadas(mapping,
				form, request, response);

		setReturnActionFordward(request, forward);
	}

	/**
	 * REaliza la entrega de una consulta(marcando la consulta y sus unidades
	 * como entregadas).
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
			HttpServletResponse response) {
		// Obtenemos el identificador de la consulta que vamos a entregar
		String idConsulta = request.getParameter("idconsulta");

		entregarConsultaCodeLogic(idConsulta, mapping, form, request, response);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, verConsultaBeforeCreate(idConsulta));
	}

	/**
	 * Guarda los cambios realizados en una consulta.
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

		ActionErrors errors = new ActionErrors();
		ConsultaForm frm = (ConsultaForm) form;
		try {
			AppUser userVO = getAppUser(request);
			ServiceClient sc = ServiceClient.create(userVO);
			ServiceRepository services = ServiceRepository.getInstance(sc);
			GestionConsultasBI consultasService = services
					.lookupGestionConsultasBI();

			// Obtenemos la consulta de la sesion
			ConsultaVO consultaVO = (ConsultaVO) getFromTemporalSession(
					request, ConsultasConstants.CONSULTA_KEY);

			frm.populate(userVO.getId(), consultaVO);

			errors = frm.validate(request, true);

			if (errors.isEmpty()) {

				consultaVO.setFestado(DBUtils.getFechaActual());
				consultaVO.setTipo(getTipoConsulta(request, frm));

				// Establecemos el tipo de consulta
				if (StringUtils.isNotEmpty(frm.getIdMotivo())) {
					MotivoConsultaVO motivo = consultasService
							.getMotivoConsultaById(frm.getIdMotivo());

					if (motivo != null) {
						consultaVO.setMotivo(motivo.getMotivo());
					}
				}

				try {
					consultasService.actualizarConsulta(consultaVO, sc);
				} catch (ConsultaActionNotAllowedException canae) {
					errors = ExceptionMapper.getErrorsExcepcion(request, canae);
				}

				if (errors.isEmpty()) {
					setReturnActionFordward(request,
							verConsultaBeforeCreate(consultaVO.getId()));
				} else {
					goForwardConsulta(mapping, frm, request);
				}

			}

		} catch (Exception e) {
			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							SolicitudesConstants.ERROR_AL_GUARDAR_CONSULTA, e
									.toString(), request.getLocale()));
			ErrorsTag.saveErrors(request, errors);
			goForwardConsulta(mapping, frm, request);
		}

	}

	/**
	 * Elimina las consultas seleccionadas por el usuario.
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
	public void eliminarconsultasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ConsultaForm frm = (ConsultaForm) form;

		eliminarConsultasCodeLogic(frm.getConsultasseleccionadas(), mapping,
				form, request, response);
	}

	/**
	 * Realiza la solicitud de la entrega de la reserva de una consulta.
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
	public void solicitardesdereservaconsultaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio para consulta
		GestionConsultasBI service = services.lookupGestionConsultasBI();

		String idConsulta = request.getParameter("idconsulta");

		try {
			service.solicitarEntregaReserva(idConsulta);
		} catch (ConsultaActionNotAllowedException panae) {
			ActionErrors errors = ExceptionMapper.getErrorsExcepcion(request,
					panae);

			ErrorsTag.saveErrors(request, errors);
		}

		setReturnActionFordward(request, verConsultaBeforeCreate(idConsulta));

		// Redirigimos a la pagina adecuada en función de si hay unidades
		// documentales no disponibles
		if (request.getAttribute(ConsultasConstants.LISTA_NO_DISPONIBLES) == null)
			setReturnActionFordward(request,
					verConsultaBeforeCreate(idConsulta));
		else
			setReturnActionFordward(request, verNoDisponiblesAutorizadas());
	}

	/**
	 * Da de alta solitudes para las unidades documentales seleccionadas de una
	 * determinada consulta.
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
			HttpServletResponse response) {
		String idConsulta = request.getParameter("idconsulta");

		enviarConsultaCodeLogic(idConsulta, mapping, form, request, response);

		// generamos la redirección
		// setReturnActionFordward(request,
		// mappings.findForward("ver_consulta"));

		// setReturnActionFordward(request,
		// verConsultaBeforeCreate(idConsulta));
	}

	/**
	 * Prepara la página para la impresión de la papeleta de la devolución de
	 * una consulta.
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

		// TODO : comprobar por qué no llega el id relleno en el formulario,
		// comparar con préstamos
		// Obtenemos el identificador de la consulta a mostrar
		// String codigo = ((ConsultaForm) form).getId();

		// Obtenemos el identificador de la consulta a mostrar
		String codigo = request.getParameter("idconsulta");

		verconsultaCodeLogic(codigo, mapping, form, request, response);

		// Establecemos el punto de navegación
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_IMPRIMIR_ENTRADA_CONSULTA,
				request);

		// Ocultar el botón para editar las observaciones.
		request.setAttribute(PrestamosConstants.PERMITIR_EDITAR_OBSERVACIONES,
				new Boolean(false));

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("imprimir_entrada_consulta"));

		/*
		 * AppUser userVO = getAppUser(request); ServiceRepository services =
		 * ServiceRepository.getInstance(ServiceClient.create(userVO));
		 * GestionConsultasBI consultasService =
		 * services.lookupGestionConsultasBI();
			*
		 * //Obtenemos el identificador de la consulta y la almancenamos para
		 * mostrarla en sesion String codigo =
		 * request.getParameter("idconsulta"); ConsultaVO consulta_VO =
		 * consultasService.getConsulta(codigo); setInTemporalSession(request ,
		 * ConsultasConstants.CONSULTA_KEY, consulta_VO);
			*
		 * request.getSession().setAttribute(ConsultasConstants.DETALLE_CONSULTA_KEY
		 * ,
		 * consultasService.getDetallesConsultaDevueltas(consulta_VO.getId()));
			*
		 * setReturnActionFordward(request,
		 * mapping.findForward("imprimir_entrada_consulta"));
		 */
	}

	/**
	 * Realiza la comprobacion de la disponibilidad de las unidades documentales
	 * para la consulta indicada.
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
		AppUser userVO = getAppUser(request);
		ServiceClient sc = ServiceClient.create(userVO);
		ServiceRepository services = ServiceRepository.getInstance(sc);
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();
		String codigo = request.getParameter("idConsulta");

		// Obtenemos la consulta por su identificador
		ConsultaVO consulta = consultasService.getConsulta(codigo);
		if (consulta != null) {
			// Obtenemos los detalles del prestamo
			Collection detallesConsultas = consultasService
					.obtenerDetallesConsultaXUsuario(sc, consulta);
			// Comprobamos su disponibilidad
			consultasService.comprobarDisponibilidadDetallesConsulta(consulta,
					detallesConsultas);
			// Establecemos los elementos a mostrar en la vista en función del
			// prestamos y del usuario
			this.establecerVista(request, userVO, consulta, detallesConsultas);
			// Habilitamos la columna de disponibilidad comprobada
			request.setAttribute(ConsultasConstants.VER_COLUMNA_DISPONIBILIDAD,
					new Boolean(true));
			// Establecemos el action para la recarga del display
			request.setAttribute(ConsultasConstants.METHOD,
					METHOD_COMPROBARDISPONIBILIDAD);
		}

		// generamos la redirección
		setReturnActionFordward(request, mappings.findForward("ver_consulta"));
	}

	/**
	 * Elimina la consulta actual.
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
	public void eliminarConsultaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Identificador de la consulta
		String id = request.getParameter(Constants.ID);
		if (StringUtils.isNotBlank(id)) {
			try {
				// Eliminar el préstamo
				getGestionConsultasBI(request).eliminarConsultas(
						new String[] { id });

				// Volver a la página anterior
				goBackExecuteLogic(mappings, form, request, response);
				return;
			} catch (ConsultaActionNotAllowedException e) {
				obtenerErrores(request, true).add(
						ExceptionMapper.getErrorsExcepcion(request, e));
			}
		}

		// Volver a la misma página
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void actualizarCamposExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		List udocsConsulta = (List) getFromTemporalSession(request,
				ConsultasConstants.DETALLE_CONSULTA_KEY);
		String position = request.getParameter("position");

		DetalleConsultaVO detalleConsulta = (DetalleConsultaVO) udocsConsulta
				.get(Integer.parseInt(position) - 1);

		int numCopiasSimples = 0, numCopiasCertificadas = 0;
		String observaciones = null;

		String valor = (String) request.getParameter("valorNumCopiasSimples");
		if (valor != null) {
			if (!valor.equals("") && StringUtils.isNumeric(valor)) {
				numCopiasSimples = Integer.parseInt(valor);
			} else {
				obtenerErrores(request, true)
						.add(Constants.ERROR_GENERAL_MESSAGE,
								new ActionError(
										Constants.ERROR_CAMPO_NO_NUMERICO,
										Messages.getString(
												ConsultasConstants.CONSULTAS_COPIAS_SIMPLES,
												request.getLocale())));
			}
		} else {
			numCopiasSimples = detalleConsulta.getNumeroCopiasSimples();
		}

		valor = (String) request.getParameter("valorNumCopiasCertificadas");
		if (valor != null) {
			if (!valor.equals("") && StringUtils.isNumeric(valor)) {
				numCopiasCertificadas = Integer.parseInt(valor);
			} else {
				obtenerErrores(request, true)
						.add(Constants.ERROR_GENERAL_MESSAGE,
								new ActionError(
										Constants.ERROR_CAMPO_NO_NUMERICO,
										Messages.getString(
												ConsultasConstants.CONSULTAS_COPIAS_CERTIFICADAS,
												request.getLocale())));
			}
		} else {
			numCopiasCertificadas = detalleConsulta
					.getNumeroCopiasCertificadas();
		}

		valor = (String) request.getParameter("valorObservaciones");
		if (valor != null) {
			observaciones = valor;
		} else {
			observaciones = detalleConsulta.getObservaciones();
		}

		if (obtenerErrores(request, true).size() == 0) {
			AppUser appUser = getAppUser(request);
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(appUser));
			GestionConsultasBI consultasBI = services
					.lookupGestionConsultasBI();

			detalleConsulta.setInformacion(DetalleConsultaVO
					.createInformacionXML(numCopiasSimples,
							numCopiasCertificadas, observaciones));

			consultasBI.actualizarDetalleConsulta(detalleConsulta);
		}

		goLastClientExecuteLogic(mappings, form, request, response);
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

		List udocsConsulta = (List) getFromTemporalSession(request,
				ConsultasConstants.DETALLE_CONSULTA_KEY);
		String position = request.getParameter("position");

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionConsultasBI consultasBI = services.lookupGestionConsultasBI();

		DetalleConsultaVO detalleConsulta = (DetalleConsultaVO) udocsConsulta
				.get(Integer.parseInt(position) - 1);

		String expedienteFS = request.getParameter("valorCampoExpedienteFS");
		ActionErrors errors = validarExpedienteFS(request, expedienteFS,
				detalleConsulta);

		if (errors.isEmpty()) {

			// detalleConsulta.setExpedienteudoc(expedienteFS);

			consultasBI.actualizarDetalleConsulta(detalleConsulta);
		}

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
		GestionConsultasBI consultasBI = services.lookupGestionConsultasBI();

		ConsultaForm frm = (ConsultaForm) form;

		consultasBI
				.actualizarObservaciones(frm.getId(), frm.getObservaciones());

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void listadoConsultasSalaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		String idUsrCSala = request
				.getParameter(SalasConsultaConstants.PARAM_ID_USUARIO);
		if (StringUtils.isNotEmpty(idUsrCSala)) {
			List consultasUsuarioSala = salasBI
					.getConsultasUsuarioSala(idUsrCSala);
			CollectionUtils.transform(consultasUsuarioSala, ConsultaToPO
					.getInstance(request.getLocale(),
							getServiceRepository(request)));
			request.setAttribute(SalasConsultaConstants.PARAM_ID_USUARIO,
					idUsrCSala);
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_CONSULTAS_USUARIO_KEY,
					consultasUsuarioSala);
			saveCurrentInvocation(
					KeysClientsInvocations.SALAS_CONSULTAS_USUARIO, request);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_CONSULTAS_USUARIO_SALA));
		}
	}

	public void addTemaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ConsultaForm consultaForm = (ConsultaForm) form;
		consultaForm.setAddTema(Boolean.TRUE);

		ActionErrors errors = consultaForm.validateNuevoTema(request);

		if (errors.isEmpty()) {
			try {

				TemaVO temaVO = getGestionConsultasBI(request).insertTema(
						consultaForm.getIdusrsolicitante(),
						consultaForm.getTemaNuevo(),
						consultaForm.isCheckedConsultaEnSala());

				if (temaVO != null) {
					consultaForm.setIdTema(temaVO.getTema());
				}

				consultaForm.setAddTema(Boolean.FALSE);
				consultaForm.setTemaNuevo(null);

				cargarListaTemas(request, consultaForm);
			} catch (Exception e) {
				errors.add(common.Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(
								SolicitudesConstants.ERROR_AL_CREAR_NUEVO_TEMA,
								e.toString(), request.getLocale()));
			}

		}

		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
		}

		goForwardConsulta(mappings, consultaForm, request);
		// setReturnActionFordward(request,
		// mappings.findForward("nueva_consulta") );
	}

	public void actualizarTipoEntidadExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ConsultaForm consultaForm = (ConsultaForm) form;

		// Reseteo Valores Formulario
		consultaForm.setConsultaEnSala(Boolean.FALSE);
		consultaForm.setFiltroUsuario(null);
		consultaForm.resetDatosSolicitante();

		// Carga de Listas
		cargarListaArchivos(request, consultaForm);
		cargarListaUsuariosSolicitantes(request, consultaForm);

		if (consultaForm.isTipoEntidadInvestigador()) {
			cargarDatosUsuarioConectado(request, consultaForm);
			seleccionarArchivoDefecto(request, consultaForm);

		}

		if (consultaForm.isTipoEntidadCiudadano()
				|| consultaForm.isTipoEntidadOrgano()
				|| (consultaForm.isTipoEntidadInvestigador() && StringUtils
						.isNotEmpty(consultaForm.getIdusrsolicitante()))) {

			cargarListaTemas(request, consultaForm);
			cargarListaMotivos(request, consultaForm);
		}

		goForwardConsulta(mappings, consultaForm, request);
	}

	public void actualizarVerUsuariosConsultaEnSalaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ConsultaForm consultaForm = (ConsultaForm) form;

		// Reseteo de valores del formulario
		consultaForm.setFiltroUsuario(null);
		consultaForm.setIdusrsolicitante(null);

		// Carga de Listas
		cargarListaArchivos(request, consultaForm);

		if (consultaForm.isCheckedConsultaEnSala()) {
			eliminarListaUsuarios(request);
			consultaForm.setIdarchivo(null);
		} else {
			seleccionarArchivoDefecto(request, consultaForm);

			cargarListaUsuariosSolicitantes(request, consultaForm);
		}

		consultaForm.resetDatosSolicitante();
		eliminarListaMotivos(request);
		eliminarListaTemas(request);

		goForwardConsulta(mappings, consultaForm, request);

	}

	public void actualizarConsultaEnSalaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ConsultaForm consultaForm = (ConsultaForm) form;

		cargarDatosUsuarioConectado(request, consultaForm);

		goForwardConsulta(mappings, consultaForm, request);

	}

	public void actualizarDatosUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ConsultaForm consultaForm = (ConsultaForm) form;

		cargarDatosUsuarioSeleccionado(request, consultaForm);

		goForwardConsulta(mappings, consultaForm, request);

	}

	public void filtrarUsuariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ConsultaForm consultaForm = (ConsultaForm) form;

		consultaForm.resetDatosSolicitante();
		consultaForm.setIdusrsolicitante(null);
		cargarListaUsuariosSolicitantes(request, consultaForm);

		eliminarListaMotivos(request);
		eliminarListaTemas(request);

		goForwardConsulta(mappings, consultaForm, request);

	}

	public void actualizarArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ConsultaForm consultaForm = (ConsultaForm) form;
		AppUser appUser = getAppUser(request);

		if (appUser.hasPermissionGestionSolicitudesConsultas()) {
			cargarListaUsuariosSolicitantes(request, consultaForm);
		}

		goForwardConsulta(mappings, consultaForm, request);

	}


	public void volverASolicitadaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idConsulta = request.getParameter("idconsulta");

		AppUser appUser = getAppUser(request);
		if (appUser.hasPermissionGestionSolicitudesConsultas()) {
			getGestionConsultasBI(request).cambiarEstadoDeAutorizadaAEnviadaYDetallesAutorizadosAPendientes(idConsulta);
		}
		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, verConsultaBeforeCreate(idConsulta));

	}


}