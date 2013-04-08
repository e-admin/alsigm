package solicitudes.actions;

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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import solicitudes.ConsultaUnidadesDocumentalesConstants;
import solicitudes.SolicitudesConstants;
import solicitudes.db.DetalleDBEntity;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import solicitudes.prestamos.forms.DetallePrestamoForm;
import solicitudes.prestamos.utils.ExceptionMapper;
import solicitudes.prestamos.utils.PrestamosUtils;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;
import solicitudes.view.BusquedaDetallePO;
import solicitudes.vos.BusquedaDetalleVO;
import util.CollectionUtils;
import util.ErrorsTag;
import util.StringOwnTokenizer;

import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionConsultasBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrestamosBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.TypeConverter;

import fondos.vos.FondoVO;

/**
 * Action que engloba todas las posibles acciones sobre la devolución de
 * detalles(U.documentales) de un prestamo.
 */
public class DevolucionDetallesAction extends BaseAction {

	/**
	 * Prepara el formulario de devolucion de unidades documentales.
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
	public void formExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Establecemos buscar por fondo+signatura checked
		setInTemporalSession(request, PrestamosConstants.CHECKFONDOYSIGNATURA,
				new Boolean(true));

		DetallePrestamoForm detallePrestamoForm = (DetallePrestamoForm) form;
		detallePrestamoForm.reset(mappings, request);

		// Limpiamos los posibles listados que se hayan generado en otras partes
		// de la aplicación
		removeInTemporalSession(request,
				PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
		request.removeAttribute(PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);

		setReturnActionFordward(request, mappings.findForward("load_page"));
	}

	/**
	 * Añade miga de pan y accede a la pagina.
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
	public void loadPageExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Establecemos el punto de navegacion actual a este
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_DEVOLVERUDOCS,
				request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mappings.findForward("formulario_devolucion"));
	}

	/**
	 * Realiza la devolución de las unidades documentales seleccionadas por el
	 * usuario.
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
	public void devolverDetallesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtener el repositorio de servicios
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		// Obtenemos el servicio de descripción
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Obtener el formulario
		DetallePrestamoForm frm = (DetallePrestamoForm) form;

		// Obtenemos las unidades documentales ya seleccionadas
		ArrayList udocs = (ArrayList) getFromTemporalSession(request,
				PrestamosConstants.LISTA_UDOC_ADEVOLVER);

		ActionErrors errors = PrestamosUtils.validateDevolverDetallesPrestamos(
				udocs, frm.getUdocsarevisardoc(), prestamosService);
		if (errors.isEmpty()) {
			try {

				// Obtener las unidades de las que se revisa la documentación
				Map unidadesDevolverRevDoc = new HashMap();
				String[] udocsarevisardoc = frm.getUdocsarevisardoc();
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

						String observaciones = frm
								.getMapFormValues("observacionesdocrev" + pos);
						String idUsrGestor = frm
								.getMapFormValues("idusrgestorDocRev" + pos);

						RevisionDocumentacionVO revDocVO = new RevisionDocumentacionVO();
						revDocVO.setIdUsrGestor(idUsrGestor);
						revDocVO.setObservaciones(observaciones);

						unidadesDevolverRevDoc.put(
								idudoc + "|" + signaturaudoc, revDocVO);
					}
				}

				// Devolver las unidades documetales
				prestamosService.devolverUnidadesDocumentales(udocs,
						unidadesDevolverRevDoc);

				// Eliminamos de la sesion las unidades documentales que se
				// habian seleccionado para devolver
				removeInTemporalSession(request,
						PrestamosConstants.LISTA_UDOC_ADEVOLVER);

				// Guardar las unidades en sesión
				setInTemporalSession(request,
						PrestamosConstants.LISTA_UDOCS_DEVUELTAS, udocs);

				// Establecemos el punto de navegacion actual a este
				saveCurrentInvocation(
						KeysClientsInvocations.SOLICITUDES_DETALLESDEVUELTOS,
						request);

			} catch (PrestamoActionNotAllowedException panae) {
				ActionErrors errores = ExceptionMapper.getErrorsExcepcion(
						request, panae);
				ErrorsTag.saveErrors(request, errores);
			}

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mappings.findForward("formulario_udocs_devueltas"));

		} else {

			// Guardar los errores para poder mostrarlos
			ErrorsTag.saveErrors(request, errors);

			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mappings.findForward("formulario_devolucion"));
		}
	}

	/**
	 * Añade las unidades documentales seleccionadas al conjunto de elementos a
	 * devolver.
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
	public void anadirDetallesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		final GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		final GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		// Obtenemos las unidades documentales ya seleccionadas
		List udocs = (List) getFromTemporalSession(request,
				PrestamosConstants.LISTA_UDOC_ADEVOLVER);
		if (udocs == null)
			udocs = new ArrayList();

		// Obtenemos los detalles a añadir
		String[] idsDetallesPrestamos = request
				.getParameterValues("detallesseleccionados");
		if (idsDetallesPrestamos != null && idsDetallesPrestamos.length > 0) {
			String id, idudoc, signatura, tipo;
			BusquedaDetalleVO infoDetallePrestamo;
			StringOwnTokenizer st;
			for (int i = 0; i < idsDetallesPrestamos.length; i++) {
				st = new StringOwnTokenizer(idsDetallesPrestamos[i], "|", null,
						true);

				id = st.nextToken();
				idudoc = st.nextToken();
				signatura = st.nextToken();
				tipo = st.nextToken();

				infoDetallePrestamo = prestamosService.getUnidadEntregada(id,
						idudoc, signatura, tipo);

				if (infoDetallePrestamo != null
						&& !udocs.contains(infoDetallePrestamo))
					udocs.add(infoDetallePrestamo);
			}
			List listaUdocsBusqueda = (List) getFromTemporalSession(request,
					PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
			for (Iterator iterator = udocs.iterator(); iterator.hasNext();) {
				BusquedaDetalleVO detalleVO = (BusquedaDetalleVO) iterator
						.next();
				listaUdocsBusqueda.remove(detalleVO);
			}
			listaUdocsBusqueda.remove(udocs);

			// listaUdocsBusqueda.remove(listaEliminados);

		} else {
			// Añadir por signatura de unidad documental
			String signatura = request.getParameter("signatura");
			BusquedaDetalleVO infoDetallePrestamo = null;

			if (signatura != null && signatura.trim().length() > 0) {
				infoDetallePrestamo = prestamosService
						.estaSignaturaEntregada(signatura);

				if (infoDetallePrestamo != null) {
					if (!udocs.contains(infoDetallePrestamo))
						udocs.add(infoDetallePrestamo);
				} else {
					ActionErrors errors = obtenerErrores(request, true);
					errors.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError(
									SolicitudesConstants.ERRORS_SOLICITUDES_UDOC_NO_ENTREGADA,
									new Object[] { signatura }));
					ErrorsTag.saveErrors(request, errors);
				}
			}

			removeInTemporalSession(request,
					PrestamosConstants.MOSTRAR_LISTADO_BUSQUEDA_UDOCS);
			// removeInTemporalSession(request,
			// PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
			setInTemporalSession(request,
					PrestamosConstants.CHECKFONDOYSIGNATURA, new Boolean(true));
			removeInTemporalSession(request, PrestamosConstants.CHECKEXP);
		}

		// Transformamos la lista de VOs en POs
		final GestionCuadroClasificacionBI cclfBI = getGestionCuadroClasificacionBI(request);
		CollectionUtils.transform(udocs, new Transformer() {
			public Object transform(Object obj) {
				BusquedaDetallePO po = new BusquedaDetallePO(cclfBI,
						prestamosService, consultasService);
				try {
					PropertyUtils.copyProperties(po, obj);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				return po;
			}
		});

		// Metemos en la sesion las unidades documentales añadidas
		setInTemporalSession(request, PrestamosConstants.LISTA_UDOC_ADEVOLVER,
				udocs);

		// Establecemos el resto de los elementos
		this.establecerElementosVista(request);

		// Eliminamos los listados y dejamos el check por defecto
		// removeInTemporalSession(request, PrestamosConstants.CHECKEXP);
		// removeInTemporalSession(request,
		// PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
		// request.removeAttribute(PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
		// request.removeAttribute(
		// PrestamosConstants.MOSTRAR_LISTADO_BUSQUEDA_UDOCS);
		// setInTemporalSession(request,PrestamosConstants.CHECKFONDOYSIGNATURA,new
		// Boolean(true));

		// Establecemos el punto de navegacion actual a este
		// TODO: Chequear si esto debe estar o no
		// saveCurrentInvocation(KeysClientsInvocations.SOLICITUDES_DEVOLVERDETALLESPRESTAMOS,
		// request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mappings.findForward("formulario_devolucion"));
	}

	/**
	 * Establece el listado de unidades documentales buscadas y el tipo de
	 * busqueda
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	private void establecerElementosVista(HttpServletRequest request) {
		// Establecemos las udocs buscadas para añadir
		List detallesprestamosFinal = (List) getFromTemporalSession(request,
				PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
		if (detallesprestamosFinal != null) {
			// Establecemos el listado a mostrar
			// request.setAttribute( PrestamosConstants.LISTADO_BUSQUEDA_UDOCS,
			// detallesprestamosFinal);
			setInTemporalSession(request,
					PrestamosConstants.LISTADO_BUSQUEDA_UDOCS,
					detallesprestamosFinal);
			// Mostramos la lista de udocs
			setInTemporalSession(request,
					PrestamosConstants.MOSTRAR_LISTADO_BUSQUEDA_UDOCS,
					new Boolean(true));
			// request.setAttribute(
			// PrestamosConstants.MOSTRAR_LISTADO_BUSQUEDA_UDOCS, new
			// Boolean(true));
		}

		Boolean esBusquedaExpediente = (Boolean) getFromTemporalSession(
				request, PrestamosConstants.CHECKEXP);
		String numero_expediente = (String) getFromTemporalSession(request,
				PrestamosConstants.EXPEDIENTE_BUSQUEDA_UDOCS);
		if (esBusquedaExpediente != null && esBusquedaExpediente.booleanValue()) {
			// Establecemos buscar por expediente checked
			setInTemporalSession(request, PrestamosConstants.CHECKEXP,
					new Boolean(true));
			removeInTemporalSession(request,
					PrestamosConstants.CHECKFONDOYSIGNATURA);
			// Establecemos el num expediente
			if (numero_expediente == null)
				numero_expediente = "";
			// request.setAttribute(
			// PrestamosConstants.EXPEDIENTE_BUSQUEDA_UDOCS, numero_expediente);
			setInTemporalSession(request,
					PrestamosConstants.EXPEDIENTE_BUSQUEDA_UDOCS,
					numero_expediente);
		}

		Boolean esBusquedaFondo = (Boolean) getFromTemporalSession(request,
				PrestamosConstants.CHECKFONDOYSIGNATURA);
		String fondo = (String) getFromTemporalSession(request,
				PrestamosConstants.FONDO_BUSQUEDA_UDOCS);
		String signatura = (String) getFromTemporalSession(request,
				PrestamosConstants.SIGNATURA_BUSQUEDA_UDOCS);
		if (esBusquedaFondo != null && esBusquedaFondo.booleanValue()) {
			// Establecemos buscar por fondo+signatura checked
			setInTemporalSession(request,
					PrestamosConstants.CHECKFONDOYSIGNATURA, new Boolean(true));
			removeInTemporalSession(request, PrestamosConstants.CHECKEXP);

			if (fondo == null)
				fondo = "";
			setInTemporalSession(request,
					PrestamosConstants.FONDO_BUSQUEDA_UDOCS, fondo);
			// request.setAttribute( PrestamosConstants.FONDO_BUSQUEDA_UDOCS,
			// fondo);
			if (signatura == null)
				signatura = "";
			setInTemporalSession(request,
					PrestamosConstants.SIGNATURA_BUSQUEDA_UDOCS, signatura);
			// request.setAttribute(
			// PrestamosConstants.SIGNATURA_BUSQUEDA_UDOCS, signatura);
		}
	}

	/**
	 * Elimina las unidades documentales seleccionadas del conjunto de elementos
	 * a devolver.
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
	public void eliminarDetallesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		final GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		final GestionCuadroClasificacionBI cclfBI = getGestionCuadroClasificacionBI(request);
		final GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();

		// Obtenemos las unidades documentales ya seleccionadas
		List udocs = (List) getFromTemporalSession(request,
				PrestamosConstants.LISTA_UDOC_ADEVOLVER);

		List udocsADevolver = new ArrayList();
		List detallesprestamosFinal = (List) getFromTemporalSession(request,
				PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);

		if (udocs == null)
			udocs = new ArrayList();

		// Obtenemos los detalles a eliminar
		String[] idsDetallesPrestamos = request
				.getParameterValues("detallesseleccionadosdevolver");

		if (idsDetallesPrestamos != null) {
			BusquedaDetalleVO infoDetalle;
			StringOwnTokenizer st;
			for (int i = 0; i < idsDetallesPrestamos.length; i++) {
				st = new StringOwnTokenizer(idsDetallesPrestamos[i], "|");

				infoDetalle = new BusquedaDetalleVO();
				infoDetalle.setIdSolicitud(st.nextToken());
				infoDetalle.setIdudoc(st.nextToken());
				infoDetalle.setSignaturaudoc(st.nextToken());
				infoDetalle.setTiposolicitud(TypeConverter.toInt(
						st.nextToken(), 0));

				BusquedaDetalleVO infoDetallePrestamo = prestamosService
						.getUnidadEntregada(infoDetalle.getIdSolicitud(),
								infoDetalle.getIdudoc(),
								infoDetalle.getSignaturaudoc(),
								String.valueOf(infoDetalle.getTiposolicitud()));
				udocsADevolver.add(infoDetallePrestamo);

				udocs.remove(infoDetalle);
			}
		}

		// Transformamos la lista de VOs en POs
		CollectionUtils.transform(udocs, new Transformer() {
			public Object transform(Object obj) {
				BusquedaDetallePO po = new BusquedaDetallePO(cclfBI,
						prestamosService, consultasService);
				try {
					PropertyUtils.copyProperties(po, obj);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				return po;
			}
		});

		CollectionUtils.transform(udocsADevolver, new Transformer() {
			public Object transform(Object obj) {
				BusquedaDetallePO po = new BusquedaDetallePO(cclfBI,
						prestamosService, consultasService);
				try {
					PropertyUtils.copyProperties(po, obj);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				return po;
			}
		});
		if (detallesprestamosFinal != null)
			detallesprestamosFinal.addAll(0, udocsADevolver);

		request.setAttribute("detallesseleccionadosdevolver", null);

		// Metemos en la sesion las unidades documentales añadidas
		setInTemporalSession(request, PrestamosConstants.LISTA_UDOC_ADEVOLVER,
				udocs);

		// Establecemos el resto de los elementos
		this.establecerElementosVista(request);

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mappings.findForward("formulario_devolucion"));
	}

	/**
	 * Realiza la busqueda de unidades documentales a devolver segun el filtro
	 * del usuario.
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
	public void buscarDetallesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el servicio de prestamos y fondos para el usuario conectado
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		final GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		final GestionConsultasBI consultasService = services
				.lookupGestionConsultasBI();
		GestionFondosBI fondosService = services.lookupGestionFondosBI();

		// El tipo de busqueda utilizado (por expediente o por signatura)
		String tipoBusqueda = request.getParameter("buscarPor");

		// Obtenemos el fondo y la signatura por si deseamos restringir por
		// fondo+signatura
		String fondo = request.getParameter("fondo");
		String signatura = request.getParameter("signatura");

		// Lista de unidades seleccionadas
		List listaUdocs = (List) getFromTemporalSession(request,
				PrestamosConstants.LISTA_UDOC_ADEVOLVER);

		List detallesPrestamos = null;
		DetallePrestamoForm busquedaDetallesEntregadosForm = ((DetallePrestamoForm) form);

		// Estamos buscando por nº expediente
		if (tipoBusqueda
				.equals(SolicitudesConstants.DEVOLUCION_DETALLE_BUSQUEDA_POR_EXPEDIENTE)) {
			// Obtenemos los parametros para la busqueda
			String numero_expediente = request.getParameter("numexp");
			if (numero_expediente == null)
				numero_expediente = request.getParameter("expediente_buscar");
			String like = request.getParameter("expediente_like");

			// Obtenemos las unidades documentales para el expediente solicitado
			detallesPrestamos = (List) prestamosService
					.getUnidadesEntregadasLikeNumExp(numero_expediente, like);

			// Establecemos buscar por expediente checked
			setInTemporalSession(request, PrestamosConstants.CHECKEXP,
					new Boolean(true));
			removeInTemporalSession(request,
					PrestamosConstants.CHECKFONDOYSIGNATURA);

			// Mostramos el expedienteexpediente
			busquedaDetallesEntregadosForm
					.setExpediente_buscar(numero_expediente);
			busquedaDetallesEntregadosForm.setSignatura_like(null);
			busquedaDetallesEntregadosForm.setSignatura_buscar(null);
			// request.setAttribute(
			// PrestamosConstants.EXPEDIENTE_BUSQUEDA_UDOCS, numero_expediente);
			setInTemporalSession(request,
					PrestamosConstants.EXPEDIENTE_BUSQUEDA_UDOCS,
					numero_expediente);
		} else {
			// Estamos buscando por el fondo+signatura
			// if (fondo != null && fondo.trim().length()>0 && signatura != null
			// && signatura.trim().length()>0) {
			// if (signatura != null && signatura.trim().length()>0) {
			String like = request.getParameter("signatura_like");
			// detallesprestamos =
			// prestamosService.getUnidadesEntregadasLikeFondoSignatura(fondo,
			// signatura);
			detallesPrestamos = (List) prestamosService
					.getUnidadesEntregadasLikeSignatura(signatura, like);

			// Establecemos buscar por fondo+signatura checked
			setInTemporalSession(request,
					PrestamosConstants.CHECKFONDOYSIGNATURA, new Boolean(true));
			removeInTemporalSession(request, PrestamosConstants.CHECKEXP);
			// Mostramos el fondo
			setInTemporalSession(request,
					PrestamosConstants.FONDO_BUSQUEDA_UDOCS, fondo);
			// request.setAttribute( PrestamosConstants.FONDO_BUSQUEDA_UDOCS,
			// fondo);
			// Mostramos la signatura
			busquedaDetallesEntregadosForm.setSignatura_buscar(signatura);
			busquedaDetallesEntregadosForm.setNumeroExpediente_like(null);
			busquedaDetallesEntregadosForm.setExpediente_buscar(null);

			setInTemporalSession(request,
					PrestamosConstants.SIGNATURA_BUSQUEDA_UDOCS, signatura);
			// request.setAttribute(
			// PrestamosConstants.SIGNATURA_BUSQUEDA_UDOCS, signatura);
		}

		// Establecemos el fondo al detalle(No nos importa el idFondo en los
		// detalles. Lo actualizamos al titulo del fondo)
		if (detallesPrestamos != null) {
			List detallesprestamosFinal = new ArrayList();

			Iterator it = detallesPrestamos.iterator();
			while (it.hasNext()) {
				BusquedaDetalleVO bdp = (BusquedaDetalleVO) it.next();

				if (CollectionUtils.isEmpty(listaUdocs)
						|| !listaUdocs.contains(bdp)) {
					FondoVO fondoauxiliar = fondosService.getFondoXId(bdp
							.getIdfondo());
					bdp.setIdfondo(fondoauxiliar.getTitulo());

					detallesprestamosFinal.add(bdp);
				}
			}

			// Transformamos los objetos a POs
			final GestionCuadroClasificacionBI cclfBI = getGestionCuadroClasificacionBI(request);
			CollectionUtils.transform(detallesprestamosFinal,
					new Transformer() {
						public Object transform(Object obj) {
							BusquedaDetallePO po = new BusquedaDetallePO(
									cclfBI, prestamosService, consultasService);
							try {
								PropertyUtils.copyProperties(po, obj);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
							return po;
						}
					});

			// Establecemos el listado a mostrar
			// request.setAttribute(PrestamosConstants.LISTADO_BUSQUEDA_UDOCS,
			// detallesprestamosFinal);
			setInTemporalSession(request,
					PrestamosConstants.LISTADO_BUSQUEDA_UDOCS,
					detallesprestamosFinal);

			// //Y lo almacenamos en la sesion para posteriori
			// List lista=(List)getFromTemporalSession(request,
			// PrestamosConstants.LISTADO_BUSQUEDA_UDOCS);
			// if(lista!=null){lista.clear();
			// lista.addAll(detallesprestamos);
			// }
			// setInTemporalSession( request ,
			// PrestamosConstants.LISTADO_BUSQUEDA_UDOCS,detallesprestamosFinal);
		}

		// Mostramos la lista de udocs
		setInTemporalSession(request,
				PrestamosConstants.MOSTRAR_LISTADO_BUSQUEDA_UDOCS, new Boolean(
						true));

		// Redirigimos a la pagina adecuada
		setReturnActionFordward(request,
				mappings.findForward("formulario_devolucion"));
	}// method

	/**
	 * Realiza la impresion de las papeletas de entrega de las unidades
	 * documentales
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
	public void imprimirentradaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// Obtenemos el servicio de prestamos para el usuario conectado
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		// Obtenemos las unidades documentales ya seleccionadas
		ArrayList udocs = (ArrayList) getFromTemporalSession(request,
				PrestamosConstants.LISTA_UDOCS_DEVUELTAS);

		// Obtenemos los detalles(la solicitud y otros datos) para las udos a
		// devolver
		Collection detalles = prestamosService.getDetallesUDocs(udocs);

		if (detalles != null && detalles.size() > 0) {
			// request.getSession().setAttribute(PrestamosConstants.DETALLE_PRESTAMO_KEY,
			// detalles);
			setInTemporalSession(request,
					ConsultaUnidadesDocumentalesConstants.DETALLES_UDOCS_KEY,
					detalles);
			// Redirigimos a la pagina adecuada
			setReturnActionFordward(request,
					mappings.findForward("imprimir_entrada"));
		}
	}

	public void verPrestamoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((DetallePrestamoForm) form).getMap());

		DetallePrestamoForm detallePrestamo = (DetallePrestamoForm) form;
		String idPrestamo = detallePrestamo.getIdPrestamo();

		String devolucion = detallePrestamo.getDevolucion();
		if (Constants.TRUE_STRING.equals(devolucion)) {
			getInvocationStack(request).popLastClientInvocation(request);
		}

		ActionRedirect verPrestamo = null;
		verPrestamo = new ActionRedirect(mappings.findForward("verPrestamo"));
		verPrestamo.addParameter("idprestamo", idPrestamo);
		verPrestamo.setRedirect(true);
		setReturnActionFordward(request, verPrestamo);
	}

	public void verSolicitudExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((DetallePrestamoForm) form).getMap());

		DetallePrestamoForm detallePrestamo = (DetallePrestamoForm) form;
		String idSolicitud = detallePrestamo.getIdsolicitud();
		int tipoSolicitud = detallePrestamo.getTiposolicitud();

		String devolucion = detallePrestamo.getDevolucion();
		if (Constants.TRUE_STRING.equals(devolucion)) {
			getInvocationStack(request).popLastClientInvocation(request);
		}

		ActionRedirect verSolicitud = null;
		if (tipoSolicitud == Integer
				.parseInt(DetalleDBEntity.TIPO_DETALLE_PRESTAMO)) {
			verSolicitud = new ActionRedirect(
					mappings.findForward("verPrestamo"));
			verSolicitud.addParameter("idprestamo", idSolicitud);
		} else {
			verSolicitud = new ActionRedirect(
					mappings.findForward("verConsulta"));
			verSolicitud.addParameter("idconsulta", idSolicitud);
		}
		verSolicitud.setRedirect(false);
		setReturnActionFordward(request, verSolicitud);
	}
}