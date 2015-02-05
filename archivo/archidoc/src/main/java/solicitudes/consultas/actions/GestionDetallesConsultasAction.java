package solicitudes.consultas.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.exceptions.ConsultaActionNotAllowedException;
import solicitudes.consultas.forms.DetalleConsultaForm;
import solicitudes.consultas.utils.ExceptionMapper;
import solicitudes.consultas.view.DetalleConsultaPO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.exceptions.DetalleNotFoundException;
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

import common.Constants;
import common.actions.BusquedaBaseAction;
import common.bi.GestionConsultasBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionRechazosBI;
import common.bi.ServiceRepository;
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
import common.util.StringUtils;

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
 * Encapsula todas las acciones relacionadas con los detalles(u.docs) de una
 * consulta.
 */
public class GestionDetallesConsultasAction extends BusquedaBaseAction {

	/** Logger de la clase */
	private static Logger logger = Logger
			.getLogger(GestionDetallesConsultasAction.class);

	// Identificador de la display tag de resultado de la búsqueda de unidades
	// documentales
	private static final String ID_DISPLAY_TAG = "udocs";

	private Busqueda getCfgBusquedaConsultas(HttpServletRequest request) {
		String key = getKeyCfgBusquedas(request,
				ConfiguracionArchivoManager.PRESTAMOS);
		Busqueda busqueda = getCfgBusqueda(request, key);
		return busqueda;

	}

	/**
	 * Prepara el formulario para dar de alta un nuevo detalle(U-documental) de
	 * una consulta.
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
		DetalleConsultaForm detalleConsultaForm = (DetalleConsultaForm) form;

		// Limpiamos los valores que deben estar vacíos
		removeInTemporalSession(request, Constants.LAST_ORDER);
		removeInTemporalSession(request, Constants.LAST_ORDER_DIRECTION);
		removeInTemporalSession(request, Constants.PAGE_NUMBER);
		removeInTemporalSession(request,
				ConsultasConstants.LISTADO_BUSQUEDA_UDOCS);
		removeInTemporalSession(request,
				ConsultasConstants.LISTA_IDS_ELEMENTOS_CF);

		// Insertamos este punto como el punto de navegacion actual
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_NUEVODETALLE,
				request);

		// Establecer la configuración de la búsqueda particular
		Busqueda busqueda = getCfgBusquedaConsultas(request);
		setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
				busqueda);
		// Si aparece el campo de entrada de búsqueda avanzada, limitamos la
		// lista de fichas a las de nivel documental
		PrecondicionesBusquedaFondosGenerica precondiciones = new PrecondicionesBusquedaFondosGenerica();
		precondiciones
				.setTiposNivelFicha(new int[] { TipoNiveles.UNIDAD_DOCUMENTAL_VALUE });
		BusquedasHelper.loadListasBusqueda(busqueda, detalleConsultaForm,
				request, precondiciones);

		setInTemporalSession(request, FondosConstants.IS_PRESTAMO, "false");
		setReturnActionFordward(request,
				mappings.findForward("nuevo_detalle_consulta"));
	}

	/**
	 * Elimina de la consulta las unidades documentales seleccionadas.
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
	public void eliminarDetallesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DetalleConsultaForm frm = (DetalleConsultaForm) form;

		// Almacenamos esta accion para volver al detalle
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_CONSULTAS_ELIMINARDETALLES,
				request);

		eliminarDetallesCodeLogic(frm.getDetallesseleccionados(),
				frm.getIdsolicitud(), mapping, form, request, response);
	}

	/**
	 * Encapsula la lógica que elimina de la consulta las unidades documentales
	 * seleccionadas.
	 * 
	 * @param idsDetallesConsultas
	 *            Listado de los identificadores de los detalles de la consulta
	 *            que deseamos eliminar.
	 * @param idSolicitud
	 *            Identificador de la solicitud a la que pertenecen los
	 *            detalles.
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void eliminarDetallesCodeLogic(String[] idsDetallesConsultas,
			String idSolicitud, ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionConsultasBI bConsultas = services.lookupGestionConsultasBI();
		ActionErrors errores = null;

		ConsultaVO consultaVO = new ConsultaVO();
		consultaVO.setId(idSolicitud);

		String[] idsUdocs = new String[idsDetallesConsultas.length];
		String[] signaturasUdosc = new String[idsDetallesConsultas.length];

		for (int i = 0; i < idsDetallesConsultas.length; i++) {
			String idcompuesto = idsDetallesConsultas[i];

			String idudoc = idcompuesto.substring(0, idcompuesto.indexOf("|"));
			String signaturaudoc = idcompuesto.substring(
					idcompuesto.indexOf("|") + 1, idcompuesto.length());
			idsUdocs[i] = idudoc;
			signaturasUdosc[i] = signaturaudoc;
		}
		// Eliminamos en una transaccion todas las unidades seleccionadas
		bConsultas.eliminarDetallesConsulta(consultaVO, idsUdocs,
				signaturasUdosc);

		if (errores != null) {
			ErrorsTag.saveErrors(request, errores);
		}

		// Redirigimos a la página adecuada
		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Añade a una consulta las unidades documentales seleccionadas.
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
		GestionConsultasBI consultasBI = services.lookupGestionConsultasBI();

		DetalleConsultaForm detalleForm = (DetalleConsultaForm) form;
		// Obtenemos la consulta a la que se van a añadir los detalles
		ConsultaVO infoConsulta = (ConsultaVO) request.getSession()
				.getAttribute(ConsultasConstants.CONSULTA_KEY);

		crearDetalleCodeLogic(detalleForm, infoConsulta, consultasBI, request);

		// Redirigimos a la pagina adecuada
		goBackExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Añade a una consulta las unidades documentales seleccionadas.
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
		GestionConsultasBI consultasBI = services.lookupGestionConsultasBI();

		DetalleConsultaForm detalleForm = (DetalleConsultaForm) form;
		// Obtenemos la consulta a la que se van a añadir los detalles
		// ConsultaVO infoConsulta = (ConsultaVO)
		// request.getSession().getAttribute(ConsultasConstants.CONSULTA_KEY);
		ConsultaVO infoConsulta = (ConsultaVO) getFromTemporalSession(request,
				ConsultasConstants.CONSULTA_KEY);

		crearDetalleCodeLogic(detalleForm, infoConsulta, consultasBI, request);

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
	 * Encapsula la lógica que añade a una consulta las unidades documentales
	 * seleccionadas.
	 * 
	 * @param detalleForm
	 *            form
	 * @param infoConsulta
	 *            consulta a la que añadir
	 * @param consultasBI
	 *            Servicio de acceso aconsultas
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public void crearDetalleCodeLogic(DetalleConsultaForm detalleForm,
			ConsultaVO infoConsulta, GestionConsultasBI consultasBI,
			HttpServletRequest request) {
		// Obtenemos los identificadores de las unidades documentales
		// seleccionadas.Vienen de la
		// forma->//codigo+"|"+id+"|"+expediente+"|"+titulo+"|"+signatura)
		String[] idsDetallesPrestamos = detalleForm.getDetallesseleccionados();

		if (idsDetallesPrestamos != null) {
			for (int i = 0; i < idsDetallesPrestamos.length; i++) {
				DetalleConsultaVO infoDetalleConsulta = new DetalleConsultaVO();
				String codigo, idudoc, titulo, expediente, signatura, fondo, codSist = null;

				StringOwnTokenizer st = new StringOwnTokenizer(
						idsDetallesPrestamos[i], "|", null, true);

				codigo = st.nextToken();
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

				infoDetalleConsulta.setIdsolicitud(infoConsulta.getId());
				infoDetalleConsulta
						.setTiposolicitud(ConsultasConstants.TIPO_SOLICITUD_CONSULTA);
				infoDetalleConsulta.setIdudoc(idudoc);
				infoDetalleConsulta.setTitulo(titulo);
				infoDetalleConsulta.setSignaturaudoc(signatura);
				infoDetalleConsulta.setIdentificacion(codigo);
				infoDetalleConsulta.setExpedienteudoc(numExp.toString());
				infoDetalleConsulta.setIdFondo(fondo);
				infoDetalleConsulta
						.setEstado(ConsultasConstants.ESTADO_SOLICITUD_PENDIENTE);
				infoDetalleConsulta
						.setFestado(Calendar.getInstance().getTime());

				// Obtenemos las copias simples y certificadas para ese id
				int copiaS = 0;
				int copiaC = 0;
				String copiaSimple = request.getParameter("copiaSimple|"
						+ idsDetallesPrestamos[i]);
				String copiaCertificada = request
						.getParameter("copiaCertificada|"
								+ idsDetallesPrestamos[i]);

				// Validar los valores de los campos copia
				try {
					copiaS = Integer.parseInt(copiaSimple);
				} catch (NumberFormatException nfe) {
					logger.warn("Error en copia simple");
				}
				try {
					copiaC = Integer.parseInt(copiaCertificada);
				} catch (NumberFormatException nfe) {
					logger.warn("Error en copia certificada");
				}

				// Obtener los valores de los campos observaciones
				String observaciones = request.getParameter("observaciones|"
						+ idsDetallesPrestamos[i]);

				// Asignamos la información al detalle
				infoDetalleConsulta.setInformacion(DetalleConsultaVO
						.createInformacionXML(copiaS, copiaC, observaciones));

				// Comprobamos si el detalle que estamos añadiendo ya está
				// metido en la consulta
				try {
					consultasBI.getDetalleConsulta(infoConsulta.getId(),
							infoDetalleConsulta.getIdudoc(),
							infoDetalleConsulta.getSignaturaudoc());
				} catch (DetalleNotFoundException dnfe) {
					consultasBI.nuevoDetallesConsulta(infoDetalleConsulta);
				}
			}
		}
	}

	/**
	 * Establece el formulario de la pagina, y redirige a la pagina de nuevo
	 * detalle de consulta.
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
	/*
	 * void verEdicionDetalle(ActionMapping mappings, ActionForm
	 * form,HttpServletRequest request, HttpServletResponse response){
	 * //request.setAttribute("__FORM_NAME", mappings.getName());
	 * setReturnActionFordward(request,
	 * mappings.findForward("nuevo_detalle_consulta")); }
	 */
	/**
	 * Realiza la denegación de los detalles seleccionados para la consulta.
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
	public void denegardetallesconsultasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardamos el punto de navegacion actual para que se vuelva a ver el
		// prestamo
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_DENEGARDETALLESCONSULTAS,
				request);

		try {
			denegarDetallesCodeLogic(mapping, form, request, response);
		} catch (ConsultaActionNotAllowedException canae) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					canae);

			ErrorsTag.saveErrors(request, errores);
		}

		// Redirigimos ala pagina adecuada
		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Realiza la autorización de los detalles seleccionados para la consulta.
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
	public void autorizardetallesconsultasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DetalleConsultaForm frm = (DetalleConsultaForm) form;

		// Guardamos el punto de navegacion actual para que se vuelva a ver el
		// prestamo
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_AUTORIZARDETALLESCONSULTAS,
				request);

		try {
			autorizarDetallesCodeLogic(frm.getDetallesseleccionados(),
					frm.getIdsolicitud(), mapping, form, request, response);
		} catch (ConsultaActionNotAllowedException canae) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					canae);

			ErrorsTag.saveErrors(request, errores);
		}

		// Redirigimos a la pagina adecuada
		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Realiza la devolución de los detalles(u.docs) seleccionados para la
	 * consulta.
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
	public void devolverdetallesconsultasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el form para sacar los detalles que se van a devolver
		DetalleConsultaForm frm = (DetalleConsultaForm) form;

		// Guardamos la pagina actual para que la vuelta sea al detalle
		saveCurrentInvocation(
				KeysClientsInvocations.SOLICITUDES_DEVOLVERDETALLESCONSULTAS,
				request);

		devolverDetallesCodeLogic(frm.getDetallesseleccionados(),
				frm.getIdsolicitud(), mapping, form, request, response);

		// Redirigimos a la pagina adecuada
		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Encapsula la lógica que realiza la denegación de los detalles
	 * seleccionados para la consulta.
	 * 
	 * @param idsDetallesConsultas
	 *            Listado de los identificadores de los detalles que deseamos
	 *            denegar.
	 * @param idSolicitud
	 *            Identificador de la solicitud a la que pertenecen las unidades
	 *            documentales.
	 * @param motivorechazo
	 *            Motivo por que son rechazadas las unidades.
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws ConsultaActionNotAllowedException
	 *             Si no se puede realizar la accion
	 */
	public void denegarDetallesCodeLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws ConsultaActionNotAllowedException {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionConsultasBI bConsultas = services.lookupGestionConsultasBI();

		String[] idsDetallesConsultas = ((DetalleConsultaForm) form)
				.getDetallesseleccionados();
		String idSolicitud = ((DetalleConsultaForm) form).getIdsolicitud();
		String idMotivoRechazo = ((DetalleConsultaForm) form)
				.getIdMotivoRechazo();

		GestionRechazosBI rechazosBI = services.lookupGestionMotivosRechazoBI();
		MotivoRechazoVO motivo = rechazosBI
				.getMotivoRechazoById(idMotivoRechazo);

		ConsultaVO consultaVO = bConsultas.getConsulta(idSolicitud);

		for (int i = 0; i < idsDetallesConsultas.length; i++) {
			String idcompuesto = idsDetallesConsultas[i];
			String idudoc = idcompuesto.substring(0, idcompuesto.indexOf("|"));
			String signaturaudoc = idcompuesto.substring(
					idcompuesto.indexOf("|") + 1, idcompuesto.length());

			// Denegamos el detalle
			bConsultas.denegarDetalleConsulta(consultaVO, idudoc,
					signaturaudoc, motivo.getMotivo(), idMotivoRechazo);
		}
	}

	/**
	 * Encapsula la lógica que realiza la autorización de los detalles
	 * seleccionados para la consulta.
	 * 
	 * @param idsDetallesConsultas
	 *            Listado de los detalles de la consulta que deseamos autorizar
	 * @param idSolicitud
	 *            Identificador de la consulta a la que pertenecen los detalles
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws ConsultaActionNotAllowedException
	 *             Si no se puede realizar la accion
	 */
	public void autorizarDetallesCodeLogic(String[] idsDetallesConsultas,
			String idSolicitud, ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ConsultaActionNotAllowedException {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionConsultasBI bConsultas = services.lookupGestionConsultasBI();

		ConsultaVO consultaVO = bConsultas.getConsulta(idSolicitud);

		for (int i = 0; i < idsDetallesConsultas.length; i++) {
			String idcompuesto = idsDetallesConsultas[i];
			String idudoc = idcompuesto.substring(0, idcompuesto.indexOf("|"));
			String signaturaudoc = idcompuesto.substring(
					idcompuesto.indexOf("|") + 1, idcompuesto.length());

			bConsultas.autorizarDetalleConsulta(consultaVO, idudoc,
					signaturaudoc);
		}
	}

	/**
	 * Encapsula la lógica que realiza la devolución de los detalles(u.docs)
	 * seleccionados para la consulta.
	 * 
	 * @param idsDetallesConsultas
	 *            Identificadores de los detalles de la consulta que se desean
	 *            devolver.
	 * @param idSolicitud
	 *            Identificador de la solictud de consulta
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void devolverDetallesCodeLogic(String[] idsDetallesConsultas,
			String idSolicitud, ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de consultas para el usuario conectado
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();
		ArrayList detalles = new ArrayList();
		List idsUdocs = new ArrayList();

		try {
			for (int i = 0; i < idsDetallesConsultas.length; i++) {
				String idcompuesto = idsDetallesConsultas[i];
				String idudoc = idcompuesto.substring(0,
						idcompuesto.indexOf("|"));
				String signaturaudoc = idcompuesto.substring(
						idcompuesto.indexOf("|") + 1, idcompuesto.length());

				BusquedaDetalleVO bd = new BusquedaDetalleVO();
				bd.setIdudoc(idudoc);
				bd.setSignaturaudoc(signaturaudoc);
				bd.setTiposolicitud(ConsultasConstants.TIPO_SOLICITUD_CONSULTA);
				bd.setIdSolicitud(idSolicitud);

				detalles.add(bd);
				idsUdocs.add(idudoc);
			}

			consultasService.devolverUnidadesDocumentales(detalles);

			if (!CollectionUtils.isEmpty(idsUdocs)) {
				InvocationStack invStack = getInvocationStack(request);
				ClientInvocation cltInv = invStack.getClientInvocation(invStack
						.getSize() - 2);
				cltInv.addParameter(
						ConsultasConstants.ID_UDOC_DEVUELTA,
						(String[]) idsUdocs.toArray(new String[idsUdocs.size()]));
			}

		} catch (ConsultaActionNotAllowedException e) {
			ActionErrors errores = ExceptionMapper.getErrorsExcepcion(request,
					e);

			ErrorsTag.saveErrors(request, errores);
		}
	}

	/**
	 * Visualiza los detalles de una unidad documental seleccionada
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

		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_VERUDOC,
				request);
	}

	/**
	 * Encapsula la lógica de visualización de los detalles de una unidad
	 * documental seleccionada
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
		GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		// Obtenemos el identificado de la unidad documental que deseamos
		// visualizar y obtenemos su id y signatura
		String ids = request.getParameter("ids");
		StringOwnTokenizer st = new StringOwnTokenizer(ids, "|");

		String idsolicitud = st.nextToken();
		String idudoc = st.nextToken();
		String signaturaudoc = st.nextToken();

		DetalleConsultaVO detalle_VO = null;
		try {
			// Obtenemos los detalles de la unidad documental
			detalle_VO = consultasService.getDetalleConsulta(idsolicitud,
					idudoc, signaturaudoc);

			detalle_VO = consultasService.tratarDetalleConsulta(detalle_VO);

			if (appUser
					.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS)) {
				ConsultaVO consulta = (ConsultaVO) getFromTemporalSession(
						request, ConsultasConstants.CONSULTA_KEY);

				if (consulta != null
						&& (consulta.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA
								|| consulta.getEstado() == ConsultasConstants.ESTADO_CONSULTA_SOLICITADA
								|| consulta.getEstado() == ConsultasConstants.ESTADO_CONSULTA_RESERVADA
								|| consulta.getEstado() == ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA
								|| consulta.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ENTREGADA || consulta
								.getEstado() == ConsultasConstants.ESTADO_CONSULTA_DEVUELTA_INCOMPLETA)) {
					request.setAttribute(
							ConsultasConstants.LISTA_CONSULTAS_NO_DISPONIBLES_KEY,
							consultasService.getDetallesConsultasNoDisponibles(
									consulta, detalle_VO));
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
		DetalleConsultaPO detallePO = new DetalleConsultaPO(cclfBI,
				descripcionBI);
		try {
			PropertyUtils.copyProperties(detallePO, detalle_VO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		// Y lo metemos para mostrarla en la vista
		setInTemporalSession(request, ConsultasConstants.DETALLE_CONSULTA_KEY,
				detallePO);

		setReturnActionFordward(request,
				mapping.findForward("ver_detalle_consulta"));
	}

	/**
	 * Limpia valores de sesión para preparar una nueva búsqueda de Unidades
	 * Documentales a añadir a la consulta
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
				ConsultasConstants.LISTADO_BUSQUEDA_UDOCS);
		removeInTemporalSession(request,
				ConsultasConstants.LISTA_IDS_ELEMENTOS_CF);

		// Realizamos la búsqueda
		buscarUDocsExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Prepara el formulario para dar de alta un nuevo detalle(U-documental) de
	 * una consulta
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
				request, ConsultasConstants.LISTADO_BUSQUEDA_UDOCS);

		// Obtenemos el servicio de elementos del cuadro para hacer la búsqueda
		// de U.Docs
		final GestionCuadroClasificacionBI elementosCFService = getGestionCuadroClasificacionBI(request);

		// Obtenemos el formulario
		DetalleConsultaForm detalleConsultaForm = (DetalleConsultaForm) form;

		// Obtener la configuración de la búsqueda
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);

		ConsultaVO consulta = (ConsultaVO) getFromTemporalSession(request,
				ConsultasConstants.CONSULTA_KEY);

		// Validar el formulario
		ActionErrors errores = BusquedasHelper.validateCampos(mappings,
				request, busqueda, detalleConsultaForm);
		if ((errores == null) || errores.isEmpty()) {
			// logger.info("Formulario de búsqueda de U. Docs para consulta validado");

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
					if (detalleConsultaForm.getEstados() == null
							|| (detalleConsultaForm.getEstados() != null && detalleConsultaForm
									.getEstados().length == 0))
						detalleConsultaForm
								.setEstados(new String[] { new Integer(
										IElementoCuadroClasificacion.VIGENTE)
										.toString() });
				}

				BusquedaElementosVO busquedaElementosVO = BusquedasHelper
						.getBusquedaElementosVO(busqueda, null,
								detalleConsultaForm);

				// Rellenamos el campo IdArchivo que es un campo de entrada
				// interno
				busquedaElementosVO.setIdArchivo(consulta.getIdarchivo());

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

				if (!busqueda.getMapSalida().containsKey(
						CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA)) {
					CampoBusqueda campoSignaturaObligatoria = new CampoBusqueda();
					campoSignaturaObligatoria
							.setNombre(CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA_OBLIGATORIA);
					campoSignaturaObligatoria.setMostrar(Constants.TRUE_STRING);
					campoSignaturaObligatoria
							.setTipo(CamposBusquedas.CAMPO_TIPO_TEXTO);
					busqueda.addCampoSalida(campoSignaturaObligatoria);
				}

				// Obtenemos los Ids de los elementos
				final List listaIdsElementos = getIdsElementos(request,
						busquedaElementosVO, elementosCFService,
						numMaxResultados, busqueda);
				SortOrderEnum currentOrderDirection = getOrderCurrentDirection(
						request, busqueda);
				/* Integer orderColumn=getOrderColumnNumber(request); */
				String orderColumn = getOrderColumn(request, busqueda);

				if (!ListUtils.isEmpty(listaIdsElementos)) {
					setInTemporalSession(request,
							ConsultasConstants.LISTA_IDS_ELEMENTOS_CF,
							listaIdsElementos);

					int pageNumber = getPageNumber(request);

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
									busquedaFinal, getProductores(
											busquedaFinal, listaIdsElementos,
											elementosCFService),
									getInteresados(busquedaFinal,
											listaIdsElementos,
											elementosCFService));
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
							ConsultasConstants.LISTADO_BUSQUEDA_UDOCS,
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
						ConsultasConstants.MOSTRAR_LISTADO_BUSQUEDA_UDOCS,
						new Boolean(true));

				// Establece los campos input de copias en funcion del rol del
				// usuario conectado
				if (consulta.getTipoentconsultora().intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT
						|| consulta.getTipoentconsultora().intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO_INT
						|| (consulta.getTipoentconsultora().intValue() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT && consulta
								.getTipo() == ConsultasConstants.TIPO_CONSULTA_INDIRECTA))
					request.setAttribute(ConsultasConstants.VER_BOTONES_COPIAS,
							new Boolean(true));

				// Redireccionamos a la pagina adecuada
				setReturnActionFordward(request,
						mappings.findForward("nuevo_detalle_consulta"));
			} catch (TooManyResultsException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
								new Object[] { new Integer(e.getCount()),
										new Integer(e.getMaxNumResults()) }));

				setReturnActionFordward(request,
						mappings.findForward("nuevo_detalle_consulta"));
			} catch (ColumnNotIndexedException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_COLUMNA_NO_INDEXADA));

				goBackExecuteLogic(mappings, form, request, response);
			} catch (WordOmittedException e) {
				// Añadir los errores al request
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_PALABRA_OMITIDA));

				goBackExecuteLogic(mappings, form, request, response);
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
				goBackExecuteLogic(mappings, form, request, response);
			}
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("nuevo_detalle_consulta"));
		}
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
					ConsultasConstants.LISTA_IDS_ELEMENTOS_CF);
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

		DetalleConsultaForm frm = (DetalleConsultaForm) form;

		consultasBI.actualizarObservaciones(frm.getIdsolicitud(),
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
			busqueda = getCfgBusquedaConsultas(request);
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
						ConsultasConstants.LISTA_IDS_ELEMENTOS_CF,
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

		}

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mapping.findForward("ver_udocs_relacionadas"));
	}

	/**
	 * Método para las búsquedas de procedimientos desde el formulario de
	 * detalles de consultas
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
				(DetalleConsultaForm) form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("nuevo_detalle_consulta"));
	}
}