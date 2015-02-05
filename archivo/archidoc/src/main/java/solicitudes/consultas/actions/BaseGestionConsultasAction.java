/**
 *
 */
package solicitudes.consultas.actions;

import gcontrol.actions.OrganoPO;
import gcontrol.view.UsuarioPO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import salas.SalasConsultaConstants;
import salas.vos.UsuarioArchivoSalasConsultaVO;
import salas.vos.UsuarioSalasConsultaVO;
import se.instituciones.InfoOrgano;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.exceptions.ConsultaActionNotAllowedException;
import solicitudes.consultas.forms.ConsultaForm;
import solicitudes.consultas.utils.ExceptionMapper;
import solicitudes.consultas.view.DetalleConsultaPO;
import solicitudes.consultas.vos.ConsultaPO;
import solicitudes.consultas.vos.ConsultaToPO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.ConsultaViewVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.consultas.vos.MotivoConsultaVO;
import solicitudes.prestamos.PrestamosConstants;
import util.CollectionUtils;
import util.ErrorsTag;
import xml.config.ConfiguracionServicios;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionConsultasBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.IKeyValue;

import descripcion.vos.ElementoCFVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BaseGestionConsultasAction extends BaseAction {
	protected final static String PATH_ACTION_LISTADO_CONSULTAS = "/action/gestionConsultas?method=listadoconsultaver";
	protected final static String PATH_ACTION_LISTADO_CONSULTAS_NO_DISPONIBLES_AUTORIZADAS = "/action/gestionConsultas?method=listadoconsultasnodisponiblesautorizadas";
	protected final static String PATH_ACTION_VER_CONSULTA = "/action/gestionConsultas?method=verconsulta&id=";
	protected final static String METHOD_LISTADO_VER = "listadoconsultaver";
	protected final static String METHOD_LISTADO_CONSULTA = "listadoconsulta";
	protected final static String METHOD_COMPROBARDISPONIBILIDAD = "comprobardisponibilidad";
	protected final static String METHOD_VERCONSULTA = "verconsulta";

	protected final static String METHOD_LISTADO_VER_ELABORACION = "listadoconsultasenelaboracionver";

	/**
	 * Metodo del action que se llama para recarga el display desde la pagina de
	 * listados del menu CONSULTAR
	 */
	protected final static String METHOD_ACCION_ANIADIR = "accionAniadirAConsultaDesdeBusqueda";

	protected List getListaInfoDetallesConsultas(List listaUnidades) {
		List listaDetalles = new ArrayList();

		if (ListUtils.isNotEmpty(listaUnidades)) {
			// Convertir las unidades en detalles
			for (Iterator iterator = listaUnidades.iterator(); iterator
					.hasNext();) {

				ElementoCFVO udoc = (ElementoCFVO) iterator.next();

				// String codigo= udoc.getCodigo();
				String idudoc = udoc.getId();
				String titulo = udoc.getTitulo();
				String expediente = udoc.getNumexp();
				String signatura = udoc.getSignaturaudoc();
				String fondo = udoc.getIdFondo();
				String codSist = udoc.getCodsistproductor();
				String identificacion = udoc.getIdentificacion();

				// Componer el número del expediente
				StringBuffer numExp = new StringBuffer();
				if (StringUtils.isNotBlank(codSist))
					numExp.append(codSist);
				if (StringUtils.isNotBlank(expediente)) {
					if (numExp.length() > 0)
						numExp.append("-");
					numExp.append(expediente);
				}

				DetalleConsultaVO infoDetalleConsulta = new DetalleConsultaVO();

				infoDetalleConsulta
						.setTiposolicitud(ConsultasConstants.TIPO_SOLICITUD_CONSULTA);
				infoDetalleConsulta.setIdudoc(idudoc);
				infoDetalleConsulta.setTitulo(titulo);
				infoDetalleConsulta.setSignaturaudoc(signatura);
				infoDetalleConsulta.setIdentificacion(identificacion);
				infoDetalleConsulta.setExpedienteudoc(numExp.toString());
				infoDetalleConsulta.setIdFondo(fondo);
				infoDetalleConsulta
						.setEstado(ConsultasConstants.ESTADO_SOLICITUD_PENDIENTE);
				infoDetalleConsulta.setFestado(DateUtils.getFechaActual());

				// Obtenemos las copias simples y certificadas para ese id
				int copiaS = 0;
				int copiaC = 0;

				// Asignamos la información al detalle
				infoDetalleConsulta.setInformacion(DetalleConsultaVO
						.createInformacionXML(copiaS, copiaC, Constants.BLANK));

				listaDetalles.add(infoDetalleConsulta);

			}
		}
		return listaDetalles;

	}

	/**
	 * Inicializa una consulta para su creación a partir del usuario conectado.
    *
	 * @param user
	 *            Usuario conectado que va a crear la consulta.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return Consulta con el año actual, estado ABIERTO, el id usuario
	 *         solicitante
	 */
	protected ConsultaVO prepareConsultaVO(AppUser user, ActionForm form,
			HttpServletRequest request) {
		ConsultaVO ret = new ConsultaVO();
		ConsultaForm consultaForm = (ConsultaForm) form;

		ret.setAno(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
		ret.setEstado(ConsultasConstants.ESTADO_CONSULTA_ABIERTA);
		ret.setIdusrsolicitante(user.getId());

		if (user.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS)
				|| user.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			// Check del radio de Ciudadano
			request.setAttribute(ConsultasConstants.CHECK_CIUDADANO,
					new Boolean(true));
			ret.setTipoentconsultora(new Integer(
					ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT));
			consultaForm.setTipoentconsultora(new Integer(
					ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT));
		} else {
			if (user.getOrganization() != null)
				ret.setNorgconsultor(user.getOrganization().getNombre());
			String nameSurname = user.getName();
			if (StringUtils.isNotEmpty(user.getSurname()))
				nameSurname = nameSurname + " " + user.getSurname();
			// ret.setNusrconsultor(user.getName() + " " + user.getSurname());
			ret.setNusrconsultor(nameSurname);
			ret.setTipo(ConsultasConstants.TIPO_CONSULTA_DIRECTA);
			ret.setTipoentconsultora(new Integer(
					ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT));
			consultaForm
					.setTipoentconsultora(new Integer(
							ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT));

			// Check del radio de Investigador
			request.setAttribute(ConsultasConstants.CHECK_INVESTIGADOR,
					new Boolean(true));
			request.setAttribute(ConsultasConstants.CHECK_INTERNO, new Boolean(
					true));
			request.setAttribute(ConsultasConstants.ID_INVESTIGADOR,
					user.getId());
		}

		return ret;
	}

	/**
	 * Encapsula la lógica de finalización del proceso de autorización de una
	 * consulta.
    *
	 * @param idConsulta
	 *            Identificador de la consulta que deseamos finalizar
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void autorizardenegarConsultaCodeLogic(String idConsulta,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio para el usuario conectado
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		ActionErrors errores = null;

		try {

			String fentrega = request.getParameter("fentrega");

			Collection udocsNoDisponibles = consultasService
					.autorizardenegarConsulta(idConsulta, fentrega);

			// Establecemos para mostrar en la vista la lista de unidades
			// documentales no disponibles
			request.setAttribute(ConsultasConstants.LISTA_NO_DISPONIBLES,
					udocsNoDisponibles);
		} catch (ConsultaActionNotAllowedException e) {
			errores = ExceptionMapper.getErrorsExcepcion(request, e);
		}

		if (errores != null) {
			ErrorsTag.saveErrors(request, errores);

			setReturnActionFordward(request,
					verConsultaBeforeCreate(idConsulta));
		}
	}

	/**
	 * Encapsula la logica que rEaliza la entrega de una consulta(marcando la
	 * consulta y sus unidades como entregadas).
    *
	 * @param idConsulta
	 *            Identificador de la consulta que deseamos finalizar
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void entregarConsultaCodeLogic(String idConsulta,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(userVO));
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();
		ActionErrors errores = null;
		// ConfigConstants.getInstance()

		try {
			consultasService.entregarConsulta(idConsulta);
		} catch (ConsultaActionNotAllowedException canae) {
			errores = ExceptionMapper.getErrorsExcepcion(request, canae);
		}

		if (errores != null) {
			ErrorsTag.saveErrors(request, errores);
		}
	}

	protected boolean comprobarDisponibilidadParaEnvio(
			HttpServletRequest request, ConsultaVO consulta,
			GestionConsultasBI consultasService) {

		// Obtenemos los detalles de la consulta
		Collection detallesConsultas = consultasService
				.obtenerDetallesConsultaXUsuario(getServiceClient(request),
						consulta);

		// Comprobamos su disponibilidad
		boolean todosDisponibles = consultasService
				.comprobarDisponibilidadDetallesConsulta(consulta,
						detallesConsultas);

		if (!todosDisponibles) {

			// Establecemos los elementos a mostrar en la vista en función del
			// prestamos y del usuario
			this.establecerVista(request, getAppUser(request), consulta,
					detallesConsultas);

			// Habilitamos la columna de disponibilidad comprobada
			request.setAttribute(ConsultasConstants.VER_COLUMNA_DISPONIBILIDAD,
					new Boolean(true));

			// Establecemos el action para la recarga del display
			request.setAttribute(ConsultasConstants.METHOD,
					METHOD_COMPROBARDISPONIBILIDAD);

		}

		return todosDisponibles;
	}

	/**
	 * Da de alta solitudes para las unidades documentales seleccionadas de una
	 * determinada consulta.
    *
	 * @param idConsulta
	 *            Identificador de la consulta para la que se van a dar de alta
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
	protected void enviarConsultaCodeLogic(String idConsulta,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();
		ActionErrors errores = new ActionErrors();
		boolean enviar = true;

		try {
			ConsultaVO consulta = consultasService.getConsulta(idConsulta);

			if (!ConfigConstants.getInstance()
					.getPermitirEnviarSolicitudNoDisponible()) {

				enviar = comprobarDisponibilidadParaEnvio(request, consulta,
						consultasService);

				if (!enviar) {
					errores.add(
							common.Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(
									common.Constants.ERROR_GENERAL_MESSAGE,
									Messages.getString(
											ConsultasConstants.ERRORS_SOLICITUDES_DETALLES_NO_DISPONIBLES,
											request.getLocale())));
				}
			}

			if (enviar) {
				consultasService.enviarConsulta(consulta);
				setReturnActionFordward(request,
						verConsultaBeforeCreate(idConsulta));
			} else {
				setReturnActionFordward(request,
						mapping.findForward("ver_consulta"));
			}

		} catch (ConsultaActionNotAllowedException e) {
			errores = ExceptionMapper.getErrorsExcepcion(request, e);
		}

		if (errores != null && errores.size() > 0)
			ErrorsTag.saveErrors(request, errores);
	}

	/**
	 * Encapsula la lógica que elimina las consultas seleccionadas por el
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
	 * @throws Exception
	 *             Si se produce un error durante el borrado.
	 */
	protected void eliminarConsultasCodeLogic(String[] idsConsultas,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(userVO));
		GestionConsultasBI service = services.lookupGestionConsultasBI();

		ActionErrors errores = null;
		try {
			service.eliminarConsultas(idsConsultas);
		} catch (ConsultaActionNotAllowedException e2) {
			errores = ExceptionMapper.getErrorsExcepcion(request, e2);
		}

		// Si ha habido errores los almacenamos para mostrarlos
		if (errores != null) {
			ErrorsTag.saveErrors(request, errores);
		}

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, verListado());
	}

	/**
	 * Genera una redirección para ver el listado de las consultas.
    *
	 * @return {@link ActionForward} a la página de ver el listado de las
	 *         consultas.
	 */
	private ActionForward verListado() {
		ActionForward ret = new ActionForward();
		ret.setPath(PATH_ACTION_LISTADO_CONSULTAS);
		ret.setRedirect(true);

		return ret;
	}

	/**
	 * Genera una redirección para ver los detalles de una consulta identificada
	 * por su id.
    *
	 * @param codigoPrestamo
	 *            Identificador de la consulta en la bd
	 * @return {@link ActionForward} a la página de ver consulta
	 */
	protected ActionForward verConsultaBeforeCreate(String codigoConsulta) {
		ActionForward ret = new ActionForward();
		ret.setPath(PATH_ACTION_VER_CONSULTA + codigoConsulta);
		ret.setRedirect(true);

		return ret;
	}

	/**
	 * Obtiene el identificador del Motivo .
    *
	 * @param motivos
	 *            Lista de motivos
	 * @return Identificador del Motivo Por Defecto
	 */
	protected String getIdMotivoXDefecto(String idMotivo, List motivos) {
		// Si solo hay un motivo, se establece como activo.
		if (!ConfigConstants.getInstance().getMotivoSolicitudOpcional()) {
			if (ListUtils.isNotEmpty(motivos) && motivos.size() == 1) {
				MotivoConsultaVO motivo = (MotivoConsultaVO) motivos.get(0);
				idMotivo = motivo.getId();
			}
		}
		return idMotivo;
	}

	/**
	 * Carga los datos del usuario seleccionado.
    *
	 * @param request
	 * @param consultaForm
	 */
	protected void cargarDatosUsuarioSeleccionado(HttpServletRequest request,
			ConsultaForm consultaForm) {
		String idUsrSolicitante = consultaForm.getIdusrsolicitante();

		UsuarioVO usuarioVO = null;

		String nombreSolicitante = null;
		String nombreOrgano = null;

		consultaForm.resetDatosSolicitante();

		if (StringUtils.isNotEmpty(idUsrSolicitante)) {

			if (consultaForm.isCheckedConsultaEnSala()) {
				UsuarioSalasConsultaVO usuarioSalasConsultaVO = getGestionSalasBI(
						request).getUsuarioSalaById(idUsrSolicitante);

				if (usuarioSalasConsultaVO != null) {
					nombreSolicitante = usuarioSalasConsultaVO
							.getNombreCompleto();
				}
			} else {
				usuarioVO = getGestionControlUsuarios(request).getUsuario(
						idUsrSolicitante);

				if (usuarioVO != null) {
					CAOrganoVO organoVO = getGestionControlUsuarios(request)
							.getOrganoUsuarioEnArchivo(idUsrSolicitante);
					nombreSolicitante = usuarioVO.getNombreCompleto();

					if (organoVO != null) {
						nombreOrgano = organoVO.getNombre();
					}
				}
			}
			consultaForm.setNorgconsultor(nombreOrgano);
			consultaForm.setNusrconsultor(nombreSolicitante);

			cargarListaMotivos(request, consultaForm);
			cargarListaTemas(request, consultaForm);
		} else {
			eliminarListaMotivos(request);
			eliminarListaTemas(request);
		}
	}

	protected void goForwardConsulta(ActionMapping mappings,
			ConsultaForm consultaForm, HttpServletRequest request) {
		ConsultaViewVO consultaViewVO = new ConsultaViewVO(request,
				consultaForm);

		request.setAttribute(ConsultasConstants.CONSULTA_VIEW_OBJECT_KEY,
				consultaViewVO);

		String forwardName = "nueva_consulta";

		setReturnActionFordward(request, mappings.findForward(forwardName));
	}

	protected void setInRequestUsuarioSeleccionado(HttpServletRequest request,
			String idUsuarioSelect) {
		UsuarioVO usuarioSeleccionado = getGestionControlUsuarios(request)
				.getUsuario(idUsuarioSelect);
		UsuarioPO usuarioSeleccionadoPO = new UsuarioPO(usuarioSeleccionado,
				getServiceRepository(request));
		setInTemporalSession(request, ConsultasConstants.ID_INVESTIGADOR,
				usuarioSeleccionadoPO);
		InfoOrgano organo = usuarioSeleccionadoPO.getOrganoExterno();
		if (organo != null) {
			setInTemporalSession(request,
					ConsultasConstants.ID_ORGANO_USUARIO_SELECCIONADO_KEY,
					organo.getNombre());
			return;
		} else {
			OrganoPO organoInterno = usuarioSeleccionadoPO.getOrganoEnArchivo();
			if (organoInterno != null) {
				setInTemporalSession(request,
						ConsultasConstants.ID_ORGANO_USUARIO_SELECCIONADO_KEY,
						organoInterno.getNombre());
				return;
			}
		}
		setInTemporalSession(request,
				ConsultasConstants.ID_ORGANO_USUARIO_SELECCIONADO_KEY, null);
	}

	/**
	 * Encapsula la lógica que muestra una consulta que ha sido creada.
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
	protected void verconsultaCodeLogic(String codigo, ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser userVO = getAppUser(request);
		ServiceClient sc = ServiceClient.create(userVO);
		ServiceRepository services = ServiceRepository.getInstance(sc);
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();
		GestionSolicitudesBI solicitudesBI = services
				.lookupGestionSolicitudesBI();

		// Obtenemos los datos de la consulta y los metemos en sesion para
		// mostrarlo en la vista
		// String codigo = ((ConsultaForm) form).getId();
		ConsultaVO consulta_VO = consultasService.verConsulta(codigo);
		consulta_VO = (ConsultaVO) solicitudesBI
				.getAditionalSolicitudInformation(consulta_VO);
		ConsultaPO consultaPO = (ConsultaPO) ConsultaToPO.getInstance(
				request.getLocale(), services).transform(consulta_VO);
		setInTemporalSession(request, ConsultasConstants.CONSULTA_KEY,
				consultaPO);

		// Obtenemos el motivo de consulta
		MotivoConsultaVO motivo = consultasService
				.getMotivoConsultaById(consulta_VO.getIdMotivo());
		setInTemporalSession(request, SolicitudesConstants.MOTIVO_KEY, motivo);

		// Obtenemos los motivos de rechazo
		Collection motivosColeccion = consultasService.getMotivosRechazo();
		setInTemporalSession(request,
				ConsultasConstants.LISTA_MOTIVO_RECHAZO_KEY, motivosColeccion);

		// Obtenemos el tema de la consulta y lo metemos en sesion para
		// mostrarlo
		setInTemporalSession(request, ConsultasConstants.TEMA_KEY,
				consulta_VO.getTema());

		// Obtenemos el motivo de la consulta y lo metemos en sesion para
		// mostrarlo
		// MotivoVO motivoVO =
		// consultasService.getMotivo(consulta_VO.getMotivo());
		// setInTemporalSession(request , ConsultasConstants.MOTIVO_KEY,
		// motivoVO.getMotivo());
		// setInTemporalSession(request , ConsultasConstants.MOTIVO_KEY,
		// consulta_VO.getMotivo());

		// Obtenemos las consultas para el usuario
		Collection detallesConsultas = consultasService
				.obtenerDetallesConsultaXUsuario(sc, consulta_VO);

		// Establecemos los elemenos a mostrar en la vista
		this.establecerVista(request, userVO, consulta_VO, detallesConsultas);

		if(consulta_VO.isFueraPlazoEntrega()){

			ActionErrors errors = new ActionErrors();

			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.CONSULTA_NO_PUEDE_SER_ENTREGADA_X_FMAXFIN_SUPERADA,
									request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
		}


		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request, mapping.findForward("ver_consulta"));

		// Establecemos el action para la recarga del display
		request.setAttribute(ConsultasConstants.METHOD, METHOD_VERCONSULTA);
	}

	/**
	 * Establece los elementos que se deben mostrar en la vista en función del
	 * usuario conectado
    *
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param userVO
	 *            Usuario conectado
	 * @param consulta_VO
	 *            Consulta de la que deseamos obtener los elementos a mostrar en
	 *            la vista
	 * @param detallesConsultas
	 *            Detalles de la consulta a mostrar.
	 */
	protected void establecerVista(HttpServletRequest request, AppUser userVO,
			ConsultaVO consulta_VO, Collection detallesConsultas) {

		// Transformar la lista de elementos en POS
		final GestionCuadroClasificacionBI cclfBI = getGestionCuadroClasificacionBI(request);
		final GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);
		CollectionUtils.transform(detallesConsultas, new Transformer() {
			public Object transform(Object obj) {
				DetalleConsultaPO po = new DetalleConsultaPO(cclfBI,
						descripcionBI);
				try {
					PropertyUtils.copyProperties(po, obj);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				return po;
			}
		});

		// Establecemos el listado de los detalles de la consulta
		setInTemporalSession(request, ConsultasConstants.DETALLE_CONSULTA_KEY,
				detallesConsultas);

		if (userVO.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
			request.setAttribute(
					ConsultasConstants.VER_LISTA_DETALLES_PARA_DEPOSITO,
					new Boolean(true));

		// En caso de existir Fecha Máxima de Fin Consulta ==> Establecemos como
		// visible la Fecha Máxima de Fin Consulta
		if (consulta_VO.getFmaxfinconsulta() != null
				&& consulta_VO.getEstado() != ConsultasConstants.ESTADO_CONSULTA_DENEGADA) {
			request.setAttribute(ConsultasConstants.FECHA_MAX_FIN_CONSULTA_KEY,
					consulta_VO.getFmaxfinconsulta());

			request.setAttribute(ConsultasConstants.VER_FECHA_MAX_FIN_CONSULTA,
					new Boolean(true));
		} else {
			request.setAttribute(ConsultasConstants.VER_FECHA_MAX_FIN_CONSULTA,
					new Boolean(false));
		}

		// En caso de existir Fecha Entrega ==> Establecemos como visible la
		// Fecha Entrega
		if (consulta_VO.getFentrega() != null) {
			request.setAttribute(ConsultasConstants.FECHA_ENTREGA_KEY,
					consulta_VO.getFentrega());
			request.setAttribute(ConsultasConstants.VER_FECHA_ENTREGA,
					new Boolean(true));
		} else {
			request.setAttribute(ConsultasConstants.VER_FECHA_ENTREGA,
					new Boolean(false));
		}

		// En caso de estar devuelta ==> Establecemos como visible la Fecha
		// Devolución
		if (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_DEVUELTA) {
			request.setAttribute(ConsultasConstants.FECHA_DEVOLUCION_KEY,
					consulta_VO.getFestado());
			request.setAttribute(ConsultasConstants.VER_FECHA_DEVOLUCION,
					new Boolean(true));
		} else {
			request.setAttribute(ConsultasConstants.VER_FECHA_DEVOLUCION,
					new Boolean(false));
		}

		// En caso de estar reservada ==> Establecemos como visible la Fecha de
		// Reserva
		if (consulta_VO.getFinicialreserva() != null) {
			request.setAttribute(ConsultasConstants.VER_FECHA_INICIO_RESERVA,
					new Boolean(true));
			request.setAttribute(ConsultasConstants.VER_RESERVA, new Boolean(
					true));
		} else if (consulta_VO.getFinicialreserva() == null) {
			request.setAttribute(ConsultasConstants.VER_RESERVA, new Boolean(
					true));
			request.setAttribute(ConsultasConstants.VER_FECHA_INICIO_RESERVA,
					new Boolean(false));
		}
		if (consulta_VO.getTipo() == ConsultasConstants.TIPO_CONSULTA_INDIRECTA) {
			request.setAttribute(ConsultasConstants.VER_BOTON_INFORMACION,
					new Boolean(true));
		}

		// BOTON ENVIAR SOLICITUD A ARCHIVO
		if (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA
				&& detallesConsultas != null
				&& !detallesConsultas.isEmpty()
				&& userVO.getId().equals(consulta_VO.getIdusrsolicitante())) {
			request.setAttribute(ConsultasConstants.VER_BOTON_ENVIAR_SOLICITAR,
					new Boolean(true));
			request.setAttribute(
					ConsultasConstants.VER_BOTON_VER_DISPONIBILIDAD,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_ENVIAR_SOLICITAR,
					new Boolean(false));

		establecerBotonesVista(request, userVO, consulta_VO, detallesConsultas);
	}

	/**
	 * Establece los botones a mostrar en la vista
    *
	 * @param request
	 * @param userVO
	 * @param consulta_VO
	 * @param detallesConsultas
	 */
	protected void establecerBotonesVista(HttpServletRequest request,
			AppUser userVO, ConsultaVO consulta_VO, Collection detallesConsultas) {
		// Comprobamos si se han tratado todos los detalles de la consulta
		boolean todostratados = true;
		if (detallesConsultas != null && !detallesConsultas.isEmpty()) {
			Iterator todosDetalles = detallesConsultas.iterator();

			while (todostratados && todosDetalles.hasNext())
				todostratados = ((DetalleConsultaVO) todosDetalles.next())
						.getEstado() != ConsultasConstants.ESTADO_SOLICITUD_PENDIENTE;
		}

		// BOTON ENVIAR AUTORIZAR O DENEGAR
		// if ( userVO.hasRole(UserVO.ROL_ADMINISTRADOR_CONSULTAS) &&
		if ((userVO.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) || userVO
				.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS))
				&& consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_SOLICITADA) {
			request.setAttribute(
					ConsultasConstants.VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR,
					new Boolean(todostratados));
			request.setAttribute(
					ConsultasConstants.VER_BOTON_VER_DISPONIBILIDAD,
					new Boolean(true));
		} else {
			request.setAttribute(
					ConsultasConstants.VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR,
					new Boolean(false));
		}

		// BOTON IMPRIMIR SALIDA
		// if ( userVO.hasRole(UserVO.ROL_OPERARIO_DEPOSITO_CONSULTAS) &&
		if ((userVO.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) || userVO
				.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
				&& (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ENTREGADA)) {
			// || consulta_VO.getEstado() ==
			// ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA) ) {
			request.setAttribute(ConsultasConstants.VER_BOTON_IMPRIMIR_SALIDA,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_IMPRIMIR_SALIDA,
					new Boolean(false));

		// BOTON IMPRIMIR ENTRADA
		// if ( userVO.hasRole(UserVO.ROL_ADMINISTRADOR_PRESTAMOS) &&
		if ((userVO.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) || userVO
				.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES))
				&& (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA || consulta_VO
						.getEstado() == ConsultasConstants.ESTADO_CONSULTA_DEVUELTA)) {
			request.setAttribute(ConsultasConstants.VER_BOTON_IMPRIMIR_ENTRADA,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_IMPRIMIR_ENTRADA,
					new Boolean(false));

		// BOTON ENTREGAR
		// if ( ( userVO.hasRole(UserVO.ROL_OPERARIO_DEPOSITO_CONSULTAS) ||
		// userVO.hasRole(UserVO.ROL_ADMINISTRADOR_CONSULTAS)) &&
		if (! consulta_VO.isFueraPlazoEntrega() &&
				(userVO.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES) || userVO
				.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
				&& consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA) {
			request.setAttribute(ConsultasConstants.VER_BOTON_ENTREGAR,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_ENTREGAR,
					new Boolean(false));

		// BOTON VER_BOTON_VOLVER_A_SOLICITADA
		if(consulta_VO.isFueraPlazoEntrega()){
			request.setAttribute(ConsultasConstants.VER_BOTON_VOLVER_A_SOLICITADA,
					new Boolean(true));
		}
		else{
			request.setAttribute(ConsultasConstants.VER_BOTON_VOLVER_A_SOLICITADA,
					new Boolean(false));
		}


		// BOTON EDITAR
		if ((consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA || (consulta_VO
				.getEstado() == ConsultasConstants.ESTADO_CONSULTA_RESERVADA))
				&& (userVO.getId().equals(consulta_VO.getIdusrsolicitante()))
				&& (consulta_VO.getFfinalreserva() == null ? true : consulta_VO
						.getFinicialreserva().after(
								GregorianCalendar.getInstance().getTime()))) {
			request.setAttribute(ConsultasConstants.VER_BOTON_EDITAR,
					new Boolean(true));
		} else {
			request.setAttribute(ConsultasConstants.VER_BOTON_EDITAR,
					new Boolean(false));

			if (userVO.isUsuarioArchivo()) {
				request.setAttribute(
						PrestamosConstants.PERMITIR_EDITAR_OBSERVACIONES,
						new Boolean(true));
			}
		}

		// BOTON ELIMINAR
		if ((consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA || consulta_VO
				.getEstado() == ConsultasConstants.ESTADO_CONSULTA_RESERVADA)
				&& userVO.getId().equals(consulta_VO.getIdusrsolicitante())) {
			request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR,
					new Boolean(false));

		// BOTON ANADIR DETALLE
		if (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA
				&& (userVO.getId().equals(consulta_VO.getIdusrsolicitante()))) {
			request.setAttribute(ConsultasConstants.VER_BOTON_ANADIR_DETALLE,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_ANADIR_DETALLE,
					new Boolean(false));

		// BOTON ELIMINAR DETALLE
		if (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA
				&& (userVO.getId().equals(consulta_VO.getIdusrsolicitante()))
				&& (detallesConsultas != null && !detallesConsultas.isEmpty())) {
			request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR_DETALLE,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_ELIMINAR_DETALLE,
					new Boolean(false));

		// BOTON AUTORIZAR DETALLE
		// if ( userVO.hasRole(UserVO.ROL_ADMINISTRADOR_CONSULTAS) &&
		if ((userVO.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) || userVO
				.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS))
				&& (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_SOLICITADA)) {
			request.setAttribute(
					ConsultasConstants.VER_BOTON_AUTORIZAR_DETALLE,
					new Boolean(true));
		} else
			request.setAttribute(
					ConsultasConstants.VER_BOTON_AUTORIZAR_DETALLE,
					new Boolean(false));

		// BOTON DENEGAR DETALLE
		// if ( userVO.hasRole(UserVO.ROL_ADMINISTRADOR_CONSULTAS) &&
		if ((userVO.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) || userVO
				.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS))
				&& (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_SOLICITADA)) {
			request.setAttribute(ConsultasConstants.VER_BOTON_DENEGAR_DETALLE,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_DENEGAR_DETALLE,
					new Boolean(false));

		// BOTON DEVOLVER
		// if ( userVO.hasRole(UserVO.ROL_ADMINISTRADOR_CONSULTAS) &&
		if ((userVO.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) || userVO
				.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS))
				&& (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ENTREGADA || consulta_VO
						.getEstado() == ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA)) {
			request.setAttribute(ConsultasConstants.VER_BOTON_DEVOLVER,
					new Boolean(true));
		} else
			request.setAttribute(ConsultasConstants.VER_BOTON_DEVOLVER,
					new Boolean(false));

		// BOTON VER_BOTON_SOLICITAR_RESERVA
		if ((userVO.getId().equals(consulta_VO.getIdusrsolicitante()))
				&& (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_RESERVADA)) {
			Boolean estamosEnFechaReserva = new Boolean(
					DateUtils.esHoy(consulta_VO.getFinicialreserva()));
			request.setAttribute(
					ConsultasConstants.VER_BOTON_SOLICITAR_RESERVA,
					estamosEnFechaReserva);

			request.setAttribute(
					ConsultasConstants.VER_BOTON_VER_DISPONIBILIDAD,
					estamosEnFechaReserva);
		} else {
			request.setAttribute(
					ConsultasConstants.VER_BOTON_SOLICITAR_RESERVA,
					new Boolean(false));
		}

		if (userVO.isUsuarioArchivo()
				||

				(consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA || consulta_VO
						.getEstado() == ConsultasConstants.ESTADO_CONSULTA_SOLICITADA)
				&& userVO.getId().equals(consulta_VO.getIdusrsolicitante())) {
			request.setAttribute(
					ConsultasConstants.VER_BOTONES_MODIFICAR_COLUMNAS,
					new Boolean(true));
		} else {
			request.setAttribute(
					ConsultasConstants.VER_BOTONES_MODIFICAR_COLUMNAS,
					new Boolean(false));
		}

		// BOTON MODIFICAR COLUMNA EXPEDIENTE DE FRACCIÓN SERIE
		if ((consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA || consulta_VO
				.getEstado() == ConsultasConstants.ESTADO_CONSULTA_SOLICITADA)
				&& userVO.getId().equals(consulta_VO.getIdusrsolicitante()))
			request.setAttribute(
					ConsultasConstants.VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS,
					new Boolean(true));
		else
			request.setAttribute(
					ConsultasConstants.VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS,
					new Boolean(false));

		// BOTON EDITAR COLUMNA OBSERVACIONES DE LOS DETALLES
		if (userVO.isUsuarioArchivo()
				|| (consulta_VO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA || consulta_VO
						.getEstado() == ConsultasConstants.ESTADO_CONSULTA_SOLICITADA)
				&& userVO.getId().equals(consulta_VO.getIdusrsolicitante())) {
			request.setAttribute(
					ConsultasConstants.VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES,
					new Boolean(true));
		} else
			request.setAttribute(
					ConsultasConstants.VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES,
					new Boolean(false));
	}

	/**
	 * Obtiene el forward para la pagina de ver prestamos no disponibles
    *
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @return Forwars a los prestamos no disponibles.
	 */
	protected ActionForward listadoConsultaNoDisponible(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mappings.findForward("ver_consultas_no_disponibles");
	}

	protected Collection getUsuarioInvestigadores(GestionControlUsuariosBI gcu,
			String filtro) {

		String[] permisos = { AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS,
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };
		return gcu.getUsuariosConPermisos(permisos, filtro);
	}

	// protected void establecerUsuario(HttpServletRequest request,
	// ConsultaForm frm, AppUser appUser, boolean nuevo) {
	// ServiceRepository services = ServiceRepository
	// .getInstance(ServiceClient.create(appUser));
	// GestionControlUsuariosBI scu = services
	// .lookupGestionControlUsuariosBI();
	//
	// List users = (List) getFromTemporalSession(request,
	// ConsultasConstants.LISTA_USUARIOS_INVESTIGADORES_KEY);
	// UsuarioVO user = scu.getUsuario(appUser.getId());
	// if (users != null && !users.isEmpty()) {
	// if (StringUtils.isNotEmpty(frm.getIduser()))
	// setInRequestUsuarioSeleccionado(request, frm.getIduser());
	// else if (user != null && users.contains(user)) {
	// setInRequestUsuarioSeleccionado(request, user.getId());
	// } else {
	// user = (UsuarioVO) users.get(0);
	// setInRequestUsuarioSeleccionado(request, user.getId());
	// }
	// } else {
	// if (nuevo)
	// setInRequestUsuarioSeleccionado(request, user.getId());
	// else
	// setInRequestUsuarioSeleccionado(request, null);
	// }
	// }

	/**
	 * Obtiene el forward al action para ver las consultas no autorizadas
    *
	 * @return {@link ActionForward} a la accion
	 */
	protected ActionForward verNoDisponiblesAutorizadas() {
		ActionForward ret = new ActionForward();
		ret.setPath(PATH_ACTION_LISTADO_CONSULTAS_NO_DISPONIBLES_AUTORIZADAS);
		ret.setRedirect(false);

		return ret;
	}

	/**
	 * Genera el forward para ir a la pagina de consultas no dipsonibles y
	 * autorizadas.
    *
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @return
	 */
	protected ActionForward listadoConsultaNoDisponibleAutorizadas(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mappings.findForward("ver_consultas_no_disponibles_autorizadas");
	}

	protected ActionErrors validarExpedienteFS(HttpServletRequest request,
			String expedienteFS, DetalleConsultaVO detalleConsulta) {

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
		detalleConsulta.setExpedienteudoc(expedienteFS);

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

	protected void asignarTipoEntidad(HttpServletRequest request,
			int tipoEntidadConsultora) {
		switch (tipoEntidadConsultora) {
		case ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT:
			request.setAttribute(ConsultasConstants.CHECK_CIUDADANO,
					new Boolean(true));
			break;
		case ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO_INT:
			request.setAttribute(ConsultasConstants.CHECK_ORGEXTERNO,
					new Boolean(true));
			break;
		case ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT:
			request.setAttribute(ConsultasConstants.CHECK_INVESTIGADOR,
					new Boolean(true));

			// Si además de investigador, es un usuario que no puede crear
			// consultas más que para sí mismo,
			// lo indicamos marcando el CHECK_INTERNO a TRUE.
			// No puede modificar más que los datos de teléfono, fax, ...,
			// temas, archivo
			AppUser userVO = getAppUser(request);
			if (!userVO
					.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS)
					&& !userVO
							.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
				request.setAttribute(ConsultasConstants.CHECK_INTERNO,
						new Boolean(true));
			break;
		}
	}

	/**
	 * Prepara la página con el formulario para el alta de una nueva consulta.
    *
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param consultaForm
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void nuevoCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AppUser userVO = getAppUser(request);

		ConsultaForm consultaForm = (ConsultaForm) form;

		// Insertamos el punto de navegacion actual
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_CONSULTAS_NUEVO, request);

		// Generamos una consulta nueva con los valores por defecto y lo
		// almacenamos en la sesion temporal
		ConsultaVO consultaVO = prepareConsultaVO(userVO, consultaForm, request);
		setInTemporalSession(request, ConsultasConstants.CONSULTA_KEY,
				consultaVO);

		List listaUnidades = (List) getFromTemporalSession(request,
				SolicitudesConstants.LISTA_UDOCS_SOLICITUD_KEY);

		if (ListUtils.isNotEmpty(listaUnidades)) {
			consultaVO.setFromBusqueda(true);
			setInTemporalSession(request,
					SolicitudesConstants.LISTA_DETALLES_UDOCS_SOLICITUD_KEY,
					getListaInfoDetallesConsultas(listaUnidades));
		}

		removeInTemporalSession(request,
				SalasConsultaConstants.USUARIO_SALA_KEY);

		AppUser appUser = getAppUser(request);
		if (!appUser.hasPermissionGestionSolicitudesConsultas()) {
			cargarDatosUsuarioConectado(request, consultaForm);
		}

		cargarListaTiposEntrega(request);
		cargarListaMotivos(request, consultaForm);
		cargarListaTemas(request, consultaForm);

		boolean conArchivos = cargarListaArchivos(request, consultaForm);

		if (!conArchivos) {
			ActionErrors errors = getErrors(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					Constants.ERROR_NO_EXISTEN_ARCHIVOS));
			ErrorsTag.saveErrors(request, errors);
			goBackExecuteLogic(mapping, consultaForm, request, response);
			return;
		}

		// Redirigimos a la página adecuada
		goForwardConsulta(mapping, consultaForm, request);
		// setReturnActionFordward(request,
		// mapping.findForward("nueva_consulta") );
	}

	/**
	 * Carga en Sessión la Lista de Temas
    *
	 * @param request
	 * @param consultaForm
	 */
	protected void cargarListaTemas(HttpServletRequest request,
			ConsultaForm consultaForm) {

		ConsultaViewVO view = new ConsultaViewVO(request, consultaForm);

		List listaTemas = new ArrayList();

		if (consultaForm.isCheckedConsultaEnSala()) {
			listaTemas = getGestionConsultasBI(request).getTemasUsuarioSala(
					consultaForm.getIdusrsolicitante());
		} else {
			if (view.isEsCiudadano()) {
				listaTemas = getGestionConsultasBI(request).getTemasCiudadano();
			} else if (view.isEsInvestigador()) {
				listaTemas = getGestionConsultasBI(request)
						.getTemasUsuarioInvestigador(
								consultaForm.getIdusrsolicitante());
			} else if (view.isEsOrgano()) {
				listaTemas = getGestionConsultasBI(request).getTemasOrgano();
			}
		}
		setInTemporalSession(request, ConsultasConstants.LISTA_TEMA_KEY,
				listaTemas);
	}

	protected void eliminarListaTemas(HttpServletRequest request) {
		setInTemporalSession(request, ConsultasConstants.LISTA_TEMA_KEY,
				new ArrayList());
	}

	protected void eliminarListaMotivos(HttpServletRequest request) {
		setInTemporalSession(request, ConsultasConstants.LISTA_MOTIVO_KEY,
				new ArrayList());
	}

	protected void eliminarListaUsuarios(HttpServletRequest request) {
		setInTemporalSession(request,
				ConsultasConstants.LISTA_USUARIOS_CONSULTAS_KEY,
				new ArrayList());
	}

	/**
	 * Obtiene la lista de Usuarios Solicitantes
    *
	 * @param request
	 * @param consultaForm
	 * @return
	 */
	protected void cargarListaUsuariosSolicitantes(HttpServletRequest request,
			ConsultaForm consultaForm) {

		AppUser appUser = getAppUser(request);

		if (consultaForm.isTipoEntidadInvestigador()) {

			if (appUser.hasPermissionGestionSolicitudesConsultas()) {
				List listaUsuarios = new ArrayList();
				if (consultaForm.isCheckedConsultaEnSala()) {
					listaUsuarios = getGestionSalasBI(request)
							.getUsuariosConPermisoConsultaSala(
									consultaForm.getIdarchivo(),
									consultaForm.getFiltroUsuario());

					if (ListUtils.isNotEmpty(listaUsuarios)) {
						UsuarioSalasConsultaVO usuarioNulo = new UsuarioSalasConsultaVO();
						listaUsuarios.add(0, usuarioNulo);
					}
				} else {
					listaUsuarios = (List) getUsuarioInvestigadores(
							getGestionControlUsuarios(request),
							consultaForm.getFiltroUsuario());

					if (ListUtils.isNotEmpty(listaUsuarios)) {
						UsuarioVO usuarioNulo = new UsuarioVO();
						listaUsuarios.add(0, usuarioNulo);
					}
				}

				setInTemporalSession(request,
						ConsultasConstants.LISTA_USUARIOS_CONSULTAS_KEY,
						listaUsuarios);
			}
		}
	}

	/**
	 * Carga la lista de Archivos
    *
	 * @param request
	 * @return true si hay archivos, false en caso contrario.
	 */
	protected boolean cargarListaArchivos(HttpServletRequest request,
			ConsultaForm consultaForm) {
		AppUser appUser = getAppUser(request);

		List listaArchivos = new ArrayList();

		UsuarioSalasConsultaVO usuarioSalasConsultaVO = appUser
				.getUsuarioSalasConsultaVO();

		if (consultaForm.isTipoEntidadInvestigador()
				&& consultaForm.isCheckedConsultaEnSala()
				&& usuarioSalasConsultaVO != null) {
			listaArchivos.addAll(usuarioSalasConsultaVO.getListaArchivos());

			if (ListUtils.isNotEmpty(listaArchivos)) {
				listaArchivos.add(0, new UsuarioSalasConsultaVO());
			}
		} else {
			listaArchivos = getGestionArchivosBI(request).getListaArchivos();

			if (consultaForm.isCheckedConsultaEnSala()
					&& ListUtils.isNotEmpty(listaArchivos)) {
				listaArchivos.add(0, new UsuarioArchivoSalasConsultaVO());
			}
		}

		if (ListUtils.isNotEmpty(listaArchivos)) {
			setInTemporalSession(request, ConsultasConstants.LISTA_ARCHIVOS,
					listaArchivos);
			return true;
		} else {
			setInTemporalSession(request, ConsultasConstants.LISTA_ARCHIVOS,
					listaArchivos);
			return false;
		}

	}

	protected void cargarListaTiposEntrega(HttpServletRequest request) {
		List listaTiposEntrega = getGestionSistemaBI(request)
				.getListaTiposEntrega();
		setInTemporalSession(request, ConsultasConstants.LISTA_TIPOS_ENTREGA,
				listaTiposEntrega);
	}

	protected void cargarListaMotivos(HttpServletRequest request,
			ConsultaForm consultaForm) {

		int tipoEntidadConsultora = consultaForm.getTipoentconsultora()
				.intValue();

		List motivos = new ArrayList();

		if (consultaForm.isTipoEntidadCiudadano()
				|| consultaForm.isTipoEntidadOrgano()) {
			motivos = getGestionConsultasBI(request).getMotivosByTipoEntidad(
					tipoEntidadConsultora);
		} else {

			if (consultaForm.isCheckedConsultaEnSala()) {
				motivos = getGestionConsultasBI(request)
						.getMotivosUsuarioConsultaEnSala(getAppUser(request),
								consultaForm.getIdusrsolicitante());
			} else {
				motivos = getGestionConsultasBI(request)
						.getMotivosUsuarioInvestigador(getAppUser(request),
								consultaForm.getIdusrsolicitante());
			}

		}

		// Establecer el motivo por defecto.
		String idMotivo = getIdMotivoXDefecto(consultaForm.getIdMotivo(),
				motivos);
		consultaForm.setIdMotivo(idMotivo);

		setInTemporalSession(request, ConsultasConstants.LISTA_MOTIVO_KEY,
				motivos);
	}

	protected void cargarDatosUsuarioConectado(HttpServletRequest request,
			ConsultaForm consultaForm) {
		consultaForm.setNorgconsultor(null);
		consultaForm.setNusrconsultor(null);
		consultaForm.setIdusrsolicitante(null);

		AppUser appUser = getAppUser(request);

		if (consultaForm.isCheckedConsultaEnSala()) {
			UsuarioSalasConsultaVO usuarioSalasConsultaVO = appUser
					.getUsuarioSalasConsultaVO();
			if (usuarioSalasConsultaVO != null) {
				consultaForm.setIdusrsolicitante(usuarioSalasConsultaVO
						.getIdscausr());
				consultaForm.setNusrconsultor(usuarioSalasConsultaVO
						.getNombreCompleto());
			}
		} else {
			consultaForm.setIdusrsolicitante(appUser.getId());
			consultaForm.setNusrconsultor(appUser.getNombreCompleto());

			if (appUser.getOrganization() != null) {
				consultaForm.setNorgconsultor(appUser.getOrganization()
						.getNombre());
			}
		}
	}

	protected void seleccionarArchivoDefecto(HttpServletRequest request,
			ConsultaForm consultaForm) {

		if (!consultaForm.isCheckedConsultaEnSala()) {
			List listaArchivos = (List) getFromTemporalSession(request,
					ConsultasConstants.LISTA_ARCHIVOS);

			if (ListUtils.isNotEmpty(listaArchivos)) {
				for (Iterator iterator = listaArchivos.iterator(); iterator
						.hasNext();) {
					IKeyValue archivo = (IKeyValue) iterator.next();

					if (archivo != null
							&& StringUtils.isNotEmpty(archivo.getKey())) {
						consultaForm.setIdarchivo(archivo.getKey());
						break;
					}
				}
			}
		}
	}

	protected int getTipoConsulta(HttpServletRequest request,
			ConsultaForm consultaForm) {

		int tipoConsulta = ConsultasConstants.TIPO_CONSULTA_INDIRECTA;

		AppUser appUser = getAppUser(request);
		String idUsuarioSeleccionado = consultaForm.getIdusrsolicitante();

		if (consultaForm.isTipoEntidadInvestigador()) {

			if (consultaForm.isCheckedConsultaEnSala()) {
				UsuarioSalasConsultaVO usuario = appUser
						.getUsuarioSalasConsultaVO();

				if (usuario != null && idUsuarioSeleccionado != null
						&& idUsuarioSeleccionado.equals(usuario.getId())) {
					tipoConsulta = ConsultasConstants.TIPO_CONSULTA_DIRECTA;
				}
			} else {

				if (idUsuarioSeleccionado != null
						&& idUsuarioSeleccionado.equals(appUser.getId())) {
					tipoConsulta = ConsultasConstants.TIPO_CONSULTA_DIRECTA;
				}
			}
		}

		return tipoConsulta;

	}
}
