package valoracion.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import util.ErrorsTag;
import util.MessagesTag;
import util.StringOwnTokenizer;
import util.TreeNode;
import util.TreeView;
import valoracion.ValoracionConstants;
import valoracion.exceptions.EliminacionActionNotAllowedException;
import valoracion.forms.EliminacionForm;
import valoracion.model.FinalizarEliminacionThread;
import valoracion.utils.ExceptionMapper;
import valoracion.utils.XMLtoCriterio;
import valoracion.view.EliminacionPO;
import valoracion.view.EliminacionToPO;
import valoracion.view.SerieToPO;
import valoracion.vos.CriterioFechaVO;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.ThreadSelectionMap;
import valoracion.vos.UnidadesDocumentalesEliminacionVO;
import valoracion.vos.ValoracionSerieVO;
import xml.config.ConfiguracionArchivoManager;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionEliminacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionSeriesBI;
import common.bi.SerieNoValoradaException;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

import descripcion.model.TipoNiveles;
import fondos.FondosConstants;
import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.PrecondicionesBusquedaFondosGenerica;
import fondos.model.TipoNivelCF;
import fondos.view.SeriePO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;
import gcontrol.vos.ArchivoVO;

/**
 * Action para el manejo de las acciones que se pueden ser llevadas a cabo
 * durante la elaboracion y gestion de eliminaciones de las valoraciones de
 * series documentales.
 */
public class GestionEliminacionAction extends BaseAction {

	public void udocsSeleccionablesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionEliminacionBI eliminacionBI = services
				.lookupGestionEliminacionBI();
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		String idSerie = request.getParameter("idSerie");

		try {
			removeInTemporalSession(request,
					ValoracionConstants.LISTA_UDOCS_KEY);
			setInTemporalSession(request, ValoracionConstants.LISTA_UDOCS_KEY,
					eliminacionBI.getUdocsSeleccionables(idSerie, new PageInfo(
							request, "codigo")));

			SerieVO serie = serieBI.getSerie(idSerie);

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_ELEMENTO_DEL_CUADRO, request);
			invocation
					.setTitleNavigationToolBar(TitlesToolBar.CUADRO_VER_SERIE);
			invocation.setAsReturnPoint(true);

			if (getFromTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY) == null)
				setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
						new SeriePO(serie, services));
			if (getFromTemporalSession(request,
					FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION) == null) {
				setInTemporalSession(
						request,
						FondosConstants.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION,
						cuadroBI.getAncestors(serie.getId()));
			}
		} catch (SerieNoValoradaException e) {
			// Si la serie no está valorada no tiene unidades
			// documentales seleccionables
		} catch (TooManyResultsException e) {
		}

		setReturnActionFordward(request, mappings.findForward("ver_serie"));
	}

	/*
	 * Muestra un listado de las unidades documentales afectadas por la
	 * eliminacion
	 */
	public void verUdocsExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);

		// Obtenemos el id de la eliminacion
		String id = request.getParameter(Constants.ID);
		// String idSerie = request.getParameter("idSerie");
		String idArchivo = request.getParameter("idArchivo");

		saveCurrentInvocation(KeysClientsInvocations.ELIMINACION_VER_UDOCS,
				request);

		// Metemos las udocs para mostrarlas y redirigimos la pagina adecuada
		UnidadesDocumentalesEliminacionVO unidadesDocumentales = eliminacionBI
				.getUnidadesEliminacion(id, idArchivo, new PageInfo(request,
						"signaturaudoc"), true);
		List listaUdocs = unidadesDocumentales.getListaUnidades();
		Map uinstParciales = unidadesDocumentales.getMapUInstParciales();

		request.setAttribute(ValoracionConstants.LISTA_UDOCS_KEY, listaUdocs);

		request.setAttribute(ValoracionConstants.VER_UI_PARCIALES_KEY,
				Boolean.TRUE);

		if (uinstParciales != null) {
			request.setAttribute(
					ValoracionConstants.ELIMINACION_CON_UI_PARCIALES_KEY,
					new Integer(uinstParciales.size()));
		}

		setReturnActionFordward(request, mappings.findForward("info_udocs"));
	}

	/*
	 * Muestra un listado de las unidades documentales no afectadas por la
	 * eliminacion
	 */
	public void verUdocsConservacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);

		// Obtenemos el id de la eliminacion
		String id = request.getParameter(Constants.ID);
		// String idSerie = request.getParameter("idSerie");
		String idArchivo = request.getParameter("idArchivo");

		saveCurrentInvocation(KeysClientsInvocations.ELIMINACION_VER_UDOCS,
				request);

		try {
			// Metemos las udocs para mostrarlas y redirigimos la pagina
			// adecuada
			request.setAttribute(ValoracionConstants.LISTA_UDOCS_KEY,
					eliminacionBI.getUnidadesConservacion(id, idArchivo,
							new PageInfo(request, "signaturaudoc")));
		} catch (TooManyResultsException e) {
		}

		setReturnActionFordward(request, mappings.findForward("info_udocs"));
	}

	/*
	 * Realiza la finalización de la eliminacion
	 */
	public void finalizarEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtenemos el id de la eliminacion
		String id = request.getParameter(Constants.ID);

		ThreadSelectionMap mapEliminaciones = getThreadSelectionMap();
		if (!mapEliminaciones.existeEliminacion(id)) {

			ActionErrors errors = new ActionErrors();

			if (!getServiceClient(request).hasPermissionEdicionDocumentos()) {
				errors.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionError(
								ValoracionConstants.ERROR_ELIMINACION_NO_EJECUTABLE_SIN_PERMISOS_NAME,
								new Object[] { Messages
										.getString(
												ValoracionConstants.PERMISO_EDICION_DOCUMENTOS_ELECTRONICOS,
												request.getLocale()) }));
			}

			if (!getServiceClient(request).hasPermissionEliminacionCuadro()) {
				errors.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionError(
								ValoracionConstants.ERROR_ELIMINACION_NO_EJECUTABLE_SIN_PERMISOS_NAME,
								new Object[] { Messages
										.getString(
												ValoracionConstants.PERMISO_ELIMINACION_CUADRO_CLASIFICACION,
												request.getLocale()) }));
			}

			if (errors.isEmpty()) {

				// Lanzar el thread de la eliminación
				FinalizarEliminacionThread thread = new FinalizarEliminacionThread(
						id, getServiceClient(request), mapEliminaciones);
				thread.start();

				// Ocultar el botón de finalización
				request.setAttribute(
						ValoracionConstants.ELIMINACION_OCULTAR_FINALIZAR_ELIMINACION_KEY,
						new Boolean(true));

				// Añadir los mensajes al request
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						ValoracionConstants.LABEL_FINALIZACION_ELIMINACION,
						request.getLocale()));

				MessagesTag.saveMessages(request, messages);

				setReturnActionFordward(request,
						mappings.findForward("info_eliminacion"));
				// openEliminacion(eliminacion, mappings,form,request,response);
			} else {
				obtenerErrores(request, true).add(errors);
				setReturnActionFordward(request,
						mappings.findForward("info_eliminacion"));
			}
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Messages.getString(
							ValoracionConstants.ERROR_FINALIZACION_ELIMINACION,
							request.getLocale())));
			obtenerErrores(request, true).add(errors);
			setReturnActionFordward(request,
					mappings.findForward("info_eliminacion"));
		}

	}

	private synchronized ThreadSelectionMap getThreadSelectionMap() {
		ThreadSelectionMap eliminaciones = null;
		ServletContext contexto = getServlet().getServletContext();
		eliminaciones = (ThreadSelectionMap) contexto
				.getAttribute(ValoracionConstants.IDS_ELIMINACION_KEY);
		if (eliminaciones == null) {
			eliminaciones = new ThreadSelectionMap();
			contexto.setAttribute(ValoracionConstants.IDS_ELIMINACION_KEY,
					eliminaciones);
		}
		return eliminaciones;
	}

	public void recogerFechaDestruccionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.ELIMINACION_DESTRUCCION_FISICA, request);
		setReturnActionFordward(request, mappings.findForward("cambio_estado"));
	}

	/*
	 * Realiza la finalización de la destrucción fisica de la eliminacion
	 */
	public void destruirFisicamenteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);

		// Obtenemos el id de la eliminacion
		String id = request.getParameter(Constants.ID);
		String fechaElim = request.getParameter("fechaDestruccion");

		// Validar la fecha de eliminacion
		if (DateUtils.isDate(fechaElim)) {
			Date fechaEliminacion = DateUtils.getDate(fechaElim);
			try {
				eliminacionBI.ejecutarDestruccionFisica(id, fechaEliminacion);
				goBackExecuteLogic(mappings, form, request, response);
			} catch (EliminacionActionNotAllowedException eanae) {
				obtenerErrores(request, true).add(
						ExceptionMapper.getErrorsExcepcion(request, eanae));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			ActionErrors errores = new ActionErrors();
			if (fechaElim != null && fechaElim.trim().length() > 0)
				errores.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_DATE,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACIONES_ELIMINACION_FECHA_EJECUCION,
										request.getLocale())));
			else
				errores.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACIONES_ELIMINACION_FECHA_EJECUCION,
										request.getLocale())));

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

	}

	/*
	 * Realiza la ejecución de la eliminacion
	 */
	public void aceptarEjecucionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el id de la eliminacion
		String id = request.getParameter(Constants.ID);

		try {
			getGestionEliminacionBI(request).ejecutarEliminacion(id);
		} catch (EliminacionActionNotAllowedException eanae) {
			obtenerErrores(request, true).add(
					ExceptionMapper.getErrorsExcepcion(request, eanae));
		}
		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);
		EliminacionForm eliminacionForm = (EliminacionForm) form;

		EliminacionSerieVO eliminacion = eliminacionBI.abrirEliminacion(
				eliminacionForm.getId(), true);

		openEliminacion(eliminacion, mappings, form, request, response);
	}

	/*
	 * Realiza el rechazo de la eliminacion
	 */
	public void aceptarEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el id de la eliminacion
		String id = request.getParameter(Constants.ID);
		try {
			getGestionEliminacionBI(request).aprobarEliminacion(id);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (EliminacionActionNotAllowedException eanae) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ExceptionMapper.getErrorsExcepcion(request, eanae));
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		}
	}

	public void pantallaRechazoSeleccionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		/* GestionEliminacionBI eliminacionBI = */getGestionEliminacionBI(request);

		saveCurrentInvocation(
				KeysClientsInvocations.ELIMINACION_RECHAZAR_SELECCION, request);
		setReturnActionFordward(request, mappings.findForward("cambio_estado"));
	}

	/*
	 * Realiza el rechazo de la eliminacion
	 */
	public void rechazarEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);

		// Obtenemos el id de la eliminacion
		String id = request.getParameter(Constants.ID);
		String motivo = request.getParameter("motivoRechazo");
		try {
			eliminacionBI.rechazarEliminacion(id, motivo);
			goBackExecuteLogic(mappings, form, request, response);
		} catch (EliminacionActionNotAllowedException eanae) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ExceptionMapper.getErrorsExcepcion(request, eanae));
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		}

		// openEliminacion(mappings,form,request,response);
	}

	/*
	 * Realiza la aprobación de la eliminacion
	 */
	public void solicitarAprobacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);

		// Obtenemos el id de la eliminacion
		String id = request.getParameter(Constants.ID);
		try {
			String pAutorizacionDirecta = request
					.getParameter("autorizarAutomaticamente");
			String pFechaEjecucion = request.getParameter("fechaEjecucion");
			Date fechaEjecucion = null;
			if (StringUtils.isNotBlank(pFechaEjecucion))
				fechaEjecucion = DateUtils.getDate(pFechaEjecucion);
			if (fechaEjecucion != null) {
				eliminacionBI
						.solicitarAprobacionEliminacion(id, fechaEjecucion);
				if (TypeConverter.toBoolean(pAutorizacionDirecta))
					eliminacionBI.aprobarEliminacion(id);
				goBackExecuteLogic(mappings, form, request, response);
			} else {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
						new ActionError(ErrorKeys.FECHA_EJECUCION_NO_VALIDA));
				setReturnActionFordward(request,
						mappings.findForward("cambio_estado"));
			}
		} catch (EliminacionActionNotAllowedException eanae) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ExceptionMapper.getErrorsExcepcion(request, eanae));
			setReturnActionFordward(request,
					mappings.findForward("cambio_estado"));
		}
	}

	/*
	 * Realiza el proceso de solicitud de aprobación de la eliminación
	 */
	public void pantallaSolicitudAprobacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EliminacionSerieVO eliminacion = (EliminacionSerieVO) getFromTemporalSession(
				request, ValoracionConstants.ELIMINACION_KEY);
		EliminacionForm eliminacionForm = (EliminacionForm) form;
		eliminacionForm.setFechaEjecucion(DateUtils.formatDate(eliminacion
				.getFechaEjecucion()));
		saveCurrentInvocation(
				KeysClientsInvocations.ELIMINACION_SOLICITUD_APROBACION,
				request);
		setReturnActionFordward(request, mappings.findForward("cambio_estado"));
	}

	/*
	 * Presenta la informacion de una eliminacion
	 */
	public void verEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_VER_ELIMINACION, request);

		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);
		EliminacionForm eliminacionForm = (EliminacionForm) form;

		EliminacionSerieVO eliminacion = eliminacionBI.abrirEliminacion(
				eliminacionForm.getId(), true);

		this.openEliminacion(eliminacion, mappings, form, request, response);
	}

	/*
	 * Presenta la informacion de una eliminacion a partir de los datos de una
	 * serie en el cuadro
	 */
	public void verEliminacionConCuadroExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_VER_ELIMINACION, request);
		String viewName = request.getParameter("viewName");
		TreeView treeView = (TreeView) request.getSession().getAttribute(
				viewName);
		if (treeView != null && treeView.getSelectedNode() != null) {
			GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);

			// Obtener la eliminación a partir de la serie seleccionada en el
			// árbol
			TreeNode node = treeView.getSelectedNode();
			ElementoCuadroClasificacionVO elementoCF = (ElementoCuadroClasificacionVO) node
					.getModelItem();
			EliminacionSerieVO eliminacion = eliminacionBI
					.getEliminacionSerie(elementoCF.getId());

			if (eliminacion != null)
				this.openEliminacion(eliminacion, mappings, form, request,
						response);
			else
				goBackExecuteLogic(mappings, form, request, response);
		}
	}

	/*
	 * Obtiene la informacion de una eliminacion
	 */
	private void openEliminacion(EliminacionSerieVO eliminacion,
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Obtenemos el servicio para el usuario conectado

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionEliminacionBI eliminacionBI = services
				.lookupGestionEliminacionBI();
		GestionArchivosBI archivosBI = services.lookupGestionArchivosBI();

		String idArchivo = eliminacion.getIdArchivo();
		ArchivoVO archivoVO = null;

		if (idArchivo != null)
			archivoVO = archivosBI.getArchivoXId(eliminacion.getIdArchivo());

		setInTemporalSession(request, ValoracionConstants.ELIMINACION_KEY,
				EliminacionToPO.getInstance(services).transform(eliminacion));

		setInTemporalSession(request, ValoracionConstants.SERIE_KEY, services
				.lookupGestionSeriesBI().getSerie(eliminacion.getIdSerie()));
		setInTemporalSession(request, TransferenciasConstants.ARCHIVO_KEY,
				archivoVO);

		// Obtener las unidades documentales y guardarlas
		List ltUnidades = eliminacionBI
				.getUnidadesEliminacionAConservar(eliminacion.getId());
		int numUdocs = (ListUtils.isEmpty(ltUnidades)) ? 0 : ltUnidades.size();
		setInTemporalSession(request,
				ValoracionConstants.ELIMINACION_NUM_UDOCS_CONSERVAR_KEY,
				new Integer(numUdocs));

		ThreadSelectionMap mapEliminaciones = getThreadSelectionMap();
		if (mapEliminaciones.existeEliminacion(eliminacion.getId())) {
			// Ocultar el botón de finalización
			request.setAttribute(
					ValoracionConstants.ELIMINACION_OCULTAR_FINALIZAR_ELIMINACION_KEY,
					new Boolean(true));

			// Añadir los mensajes al request
			ActionMessages messages = new ActionMessages();
			messages.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage(
							ValoracionConstants.LABEL_FINALIZACION_ELIMINACION_EN_PROCESO,
							request.getLocale()));

			MessagesTag.saveMessages(request, messages);
		}

		setReturnActionFordward(request,
				mappings.findForward("info_eliminacion"));
	}

	/*
	 * Presenta el formulario de alta de una eliminacion
	 */
	public void nuevaEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		removeInTemporalSession(request, ValoracionConstants.ELIMINACION_KEY);
		removeInTemporalSession(request, ValoracionConstants.SERIE_KEY);

		// Establecemos los elementos necesarios para la vista
		this.establecerElementosFormulario(request);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		AppUser appUser = getAppUser(request);
		GestionArchivosBI serviceArchivos = services.lookupGestionArchivosBI();

		removeInTemporalSession(request,
				TransferenciasConstants.LISTA_CODIGOSARCHIVO_KEY);
		List ltArchivos = serviceArchivos.getArchivosXId(appUser
				.getCustodyArchiveList().toArray());
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_CODIGOSARCHIVO_KEY, ltArchivos);

		saveCurrentInvocation(
				KeysClientsInvocations.ELIMINACION_NUEVA_ELIMINACION, request);
		setReturnActionFordward(request,
				mappings.findForward("nueva_eliminacion"));
	}

	/*
	 * Realiza la busqueda de series segun los criterios pasados para el
	 * formulario de alta de eliminación.
	 */
	public void buscarSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		this.establecerElementosBusqueda(request);

		// Establecemos los elementos necesarios para la vista
		this.establecerElementosFormulario(request);

		saveCurrentInvocation(
				KeysClientsInvocations.ELIMINACION_NUEVA_ELIMINACION, request);
		setReturnActionFordward(request,
				mappings.findForward("nueva_eliminacion"));
	}

	private EliminacionSerieVO processInicioEliminacion(
			HttpServletRequest request, EliminacionForm eliminacionForm)
			throws ActionNotAllowedException {
		EliminacionSerieVO eliminacion = new EliminacionSerieVO();
		StringOwnTokenizer tok = new StringOwnTokenizer(
				eliminacionForm.getIdSerie(), "|");

		eliminacion.setIdSerie(tok.nextToken());
		eliminacion.setIdValoracion(tok.nextToken());
		eliminacion.setSeleccionUdoc(ValoracionConstants.SELECCION_UDOC_NO);
		eliminacion.setIdArchivo(eliminacionForm.getIdArchivo());

		// Obtenemos la valoracion a la que se va asociar la eliminacion
		ValoracionSerieVO valoracion = getGestionValoracionBI(request)
				.getValoracion(eliminacion.getIdValoracion());

		eliminacion.setMaxAnosVigencia(Math.max(
				valoracion.getAnosVigenciaAdministrativa(),
				valoracion.getAnosVigenciaLegal()));

		if (valoracion.getValorDictamen() == ValoracionSerieVO.VALOR_DICTAMEN_ELIMINACION_TOTAL)
			eliminacion
					.setTipoEliminacion(ValoracionConstants.TIPO_ELIMINACION_TOTAL);
		else
			eliminacion
					.setTipoEliminacion(ValoracionConstants.TIPO_ELIMINACION_PARCIAL);

		eliminacion = getGestionEliminacionBI(request).iniciarSeleccionSerie(
				eliminacion);

		// Cargamos el resto de elementos del formulario
		this.establecerElementosFormulario(request);

		return eliminacion;
	}

	public void seleccionarArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		getInvocationStack(request).popLastClientInvocation(request);
		setReturnActionFordward(request,
				mappings.findForward("continuarInicioEliminacion"));

	}

	public void continuarInicioEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			EliminacionSerieVO eliminacion = processInicioEliminacion(request,
					(EliminacionForm) form);

			ActionRedirect vistaEliminacion = new ActionRedirect(
					mappings.findForward("redirect_to_info_eliminacion"));
			vistaEliminacion.setRedirect(true);
			vistaEliminacion.addParameter(Constants.ID, eliminacion.getId());
			setReturnActionFordward(request, vistaEliminacion);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goBackExecuteLogic(mappings, form, request, response);
		}
	}

	public void iniciarEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Si el usuario pertenece a mas de un archivo sacamos una pantalla
		// intermedia
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		AppUser appUser = getAppUser(request);
		EliminacionForm eliminacionForm = (EliminacionForm) form;

		GestionArchivosBI serviceArchivos = services.lookupGestionArchivosBI();
		List ltArchivos = serviceArchivos.getArchivosXId(appUser
				.getCustodyArchiveList().toArray());

		if (ListUtils.isEmpty(ltArchivos)) {
			// El usuario no tiene archivo
			ActionErrors errors = new ActionErrors();
			errors.add(ValoracionConstants.ERROR_USUARIO_SIN_ARCHIVO,
					new ActionError(
							ValoracionConstants.ERROR_USUARIO_SIN_ARCHIVO));
			ErrorsTag.saveErrors(request, errors);

			ActionRedirect verSerie = new ActionRedirect(
					mappings.findForward("udocsSeleccionables"), true);
			verSerie.addParameter("idSerie", eliminacionForm.getIdSerie() + "|"
					+ eliminacionForm.getIdValoracion());
			setReturnActionFordward(request, verSerie);
		} else if (ltArchivos.size() > 1) {
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_CODIGOSARCHIVO_KEY,
					ltArchivos);
			saveCurrentInvocation(KeysClientsInvocations.ELECCION_ARCHIVO,
					request);
			setReturnActionFordward(request,
					mappings.findForward("eleccion_archivo"));
		} else {
			ArchivoVO archivoVO = (ArchivoVO) ltArchivos.get(0);
			eliminacionForm.setIdArchivo(archivoVO.getId());
			setReturnActionFordward(request,
					mappings.findForward("continuarInicioEliminacion"));
		}

	}

	public void seleccionarSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			EliminacionSerieVO eliminacion = processInicioEliminacion(request,
					(EliminacionForm) form);

			ActionRedirect vistaEliminacion = new ActionRedirect(
					mappings.findForward("redirect_to_info_eliminacion"));
			vistaEliminacion.setRedirect(true);
			vistaEliminacion.addParameter(Constants.ID, eliminacion.getId());
			popLastInvocation(request);
			setReturnActionFordward(request, vistaEliminacion);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			establecerElementosBusqueda(request);
			setReturnActionFordward(request,
					mappings.findForward("nueva_eliminacion"));
		}
	}

	public void crearEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el servicio de eliminacion
		GestionEliminacionBI service = getGestionEliminacionBI(request);

		// Obtenemos los datos del formulario
		EliminacionForm eliminacion = (EliminacionForm) form;

		// Validar el formulario
		ActionErrors errores = eliminacion.validate(mappings, request);
		if ((errores == null) || errores.isEmpty()) {
			EliminacionSerieVO eliminacionVO = new EliminacionSerieVO();
			eliminacion.populate(eliminacionVO);
			// Obtenemos la valoracion a la que se va asociar la eliminacion
			ValoracionSerieVO valoracion = (ValoracionSerieVO) getFromTemporalSession(
					request, ValoracionConstants.VALORACION_KEY);
			eliminacionVO.setIdSerie(valoracion.getIdSerie());
			eliminacionVO.setIdValoracion(valoracion.getId());
			eliminacionVO
					.setEstado(ValoracionConstants.ESTADO_ELIMINACION_ABIERTA);
			eliminacionVO.setFechaEstado(DBUtils.getFechaActual());
			if (valoracion.getAnosVigenciaAdministrativa() > valoracion
					.getAnosVigenciaLegal())
				eliminacionVO.setMaxAnosVigencia(valoracion
						.getAnosVigenciaAdministrativa());
			else
				eliminacionVO.setMaxAnosVigencia(valoracion
						.getAnosVigenciaLegal());
			if (valoracion.getValorDictamen() == ValoracionSerieVO.VALOR_DICTAMEN_ELIMINACION_TOTAL)
				eliminacionVO
						.setTipoEliminacion(ValoracionConstants.TIPO_ELIMINACION_TOTAL);
			else
				eliminacionVO
						.setTipoEliminacion(ValoracionConstants.TIPO_ELIMINACION_PARCIAL);
			eliminacionVO
					.setSeleccionUdoc(ValoracionConstants.SELECCION_UDOC_NO);

			// Creamos la eliminacion
			try {
				eliminacionVO = service.iniciarSeleccionSerie(eliminacionVO);

				ActionRedirect vistaEliminacion = new ActionRedirect(
						mappings.findForward("redirect_to_info_eliminacion"));
				vistaEliminacion.setRedirect(true);
				vistaEliminacion.addParameter(Constants.ID,
						eliminacionVO.getId());
				popLastInvocation(request);
				setReturnActionFordward(request, vistaEliminacion);
			} catch (EliminacionActionNotAllowedException eanae) {
				errores = ExceptionMapper.getErrorsExcepcion(request, eanae);

				// Añadir los errores al request
				obtenerErrores(request, true).add(errores);
				// establecemos los elementos del form
				this.establecerElementosFormulario(request);
				this.establecerElementosBusqueda(request);
				setReturnActionFordward(request,
						mappings.findForward("nueva_eliminacion"));
			}

		} else {
			logger.info("Formulario invalido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);
			// establecemos los elementos del form
			this.establecerElementosFormulario(request);
			this.establecerElementosBusqueda(request);
			setReturnActionFordward(request,
					mappings.findForward("nueva_eliminacion"));
		}
	}

	/*
	 * Realiza el proceso de almacenamiento de una eliminacion tras el proceso
	 * de edicion
	 */
	public void guardarEdicionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el servicio de eliminacion
		GestionEliminacionBI service = getGestionEliminacionBI(request);
		// Obtenemos los datos del formulario
		EliminacionForm eliminacion = (EliminacionForm) form;

		// Validar el formulario
		ActionErrors errores = eliminacion.validate(mappings, request);
		if ((errores == null) || errores.isEmpty()) {
			EliminacionSerieVO eliminacionVO = (EliminacionSerieVO) getFromTemporalSession(
					request, ValoracionConstants.ELIMINACION_KEY);
			// Si es una actualizacion de los datos los cogemos del formulario
			if (request.getParameter("type").equalsIgnoreCase(
					ValoracionConstants.ACTUALIZACION_DATOS)) {
				eliminacionVO.setTitulo(eliminacion.getTitulo());
				eliminacionVO.setFechaEjecucion(DateUtils.getDate(eliminacion
						.getFechaEjecucion()));
				eliminacionVO.setNotas(eliminacion.getNotas());
			}
			XMLtoCriterio criterio = new XMLtoCriterio(
					getServiceClient(request).getEngine());
			eliminacionVO.setCondicionBusqueda(criterio
					.transform((List) getFromTemporalSession(request,
							ValoracionConstants.LISTA_CRITERIOS_KEY)));

			// actualizamos la eliminación
			try {
				if (request.getParameter("type").equalsIgnoreCase(
						ValoracionConstants.ACTUALIZACION_DATOS))
					eliminacionVO = service
							.actualizarEliminacion(eliminacionVO);
				else
					eliminacionVO = service
							.actualizarCriteriosEliminacion(eliminacionVO);
			} catch (EliminacionActionNotAllowedException eanae) {
				errores = ExceptionMapper.getErrorsExcepcion(request, eanae);

				// Añadir los errores al request
				obtenerErrores(request, true).add(errores);
			}
		} else {
			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);
		}

		// establecemos los elementos del form
		this.establecerElementosFormulario(request);
		// Guardamos los datos y volvemos a la pantalla de edicion
		setReturnActionFordward(request,
				mappings.findForward("edicion_eliminacion"));
	}

	/*
	 * Realiza el borrado de las eliminaciones seleccionadas.
	 */
	public void eliminarEliminacionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);

		try {
			String[] eliminacionesID = request
					.getParameterValues("eliminacionSeleccionada");
			eliminacionBI.eliminarEliminaciones(eliminacionesID);

			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/*
	 * Presenta las eliminaciones de las que el usuario es gestor y que se
	 * encuentran en elaboracion
	 */
	public void eliminacionesEnElaboracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos el servicio para el usuario conectado
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionEliminacionBI eliminacionBI = services
				.lookupGestionEliminacionBI();

		// Obtenemos las eliminaciones en elaboracion y las metemos para mostrar
		List eliminacionesEnElaboracion = eliminacionBI
				.getEliminacionesEnElaboracion(appUser.getId(),
						appUser.getIdsArchivosUser());
		CollectionUtils.transform(eliminacionesEnElaboracion,
				EliminacionToPO.getInstance(services));
		request.setAttribute(ValoracionConstants.LISTA_ELIMINACIONES_KEY,
				eliminacionesEnElaboracion);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.ELIMINACION_ELIMINACIONES_EN_ELABORACION,
				request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request,
				mappings.findForward("lista_eliminaciones"));
	}

	/* Elimina la eliminación que esta siendo visualizada */
	public void eliminarEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionEliminacionBI eliminacionBI = getGestionEliminacionBI(request);
		EliminacionForm eliminacionForm = (EliminacionForm) form;
		try {
			String[] eliminacionID = { eliminacionForm.getId() };
			eliminacionBI.eliminarEliminaciones(eliminacionID);
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/*
	 * Presenta el formulario de edicion de una eliminacion en el que se
	 * presentan los datos para que estos puedan ser modificados
	 */
	public void edicionEliminacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Establecemos los elementos del formulario
		// this.establecerElementosFormulario(request);

		// Obtenemos el id de la eliminacion
		String idEliminacion = request.getParameter(Constants.ID);
		// Obtenemos la eliminacion que vamos a editar
		EliminacionSerieVO eliminacion = getGestionEliminacionBI(request)
				.getEliminacion(idEliminacion);
		// Asignamos los datos de la a editar
		((EliminacionForm) form).setEliminacion(eliminacion);
		removeInTemporalSession(request, ValoracionConstants.ELIMINACION_KEY);
		setInTemporalSession(request, ValoracionConstants.ELIMINACION_KEY,
				EliminacionToPO.getInstance(getServiceRepository(request))
						.transform(eliminacion));
		XMLtoCriterio criterio = new XMLtoCriterio(getServiceClient(request)
				.getEngine());
		removeInTemporalSession(request,
				ValoracionConstants.LISTA_CRITERIOS_KEY);
		setInTemporalSession(request, ValoracionConstants.LISTA_CRITERIOS_KEY,
				criterio.transform(eliminacion.getCondicionBusqueda()));

		saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_EDICION_ELIMINACION, request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_eliminacion"));
	}

	/**
	 * Presenta el formulario de edicion avanzado de una eliminacion en el que
	 * se presentan las unidades documentales a conservar
	 * 
	 * @param mappings
	 *            Mapping
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta
	 */
	public void edicionEliminacionAvanzadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Establecemos los elementos del formulario
		// this.establecerElementosFormulario(request);

		// Obtenemos el id de la eliminacion
		String idEliminacion = request.getParameter(Constants.ID);
		// Obtenemos la eliminacion que vamos a editar
		EliminacionSerieVO eliminacion = getGestionEliminacionBI(request)
				.getEliminacion(idEliminacion);
		// Asignamos los datos de la a editar
		((EliminacionForm) form).setEliminacion(eliminacion);

		// Obtener el repositorio de servicios
		ServiceRepository services = getServiceRepository(request);

		setInTemporalSession(request, ValoracionConstants.ELIMINACION_KEY,
				EliminacionToPO.getInstance(services).transform(eliminacion));

		// Obtener las unidades documentales y guardarlas
		removeInTemporalSession(request,
				ValoracionConstants.ELIMINACION_UDOCS_CONSERVAR_KEY);
		GestionEliminacionBI eliminacionBI = services
				.lookupGestionEliminacionBI();
		List ltUnidades = eliminacionBI
				.getUnidadesEliminacionAConservar(idEliminacion);
		setInTemporalSession(request,
				ValoracionConstants.ELIMINACION_UDOCS_CONSERVAR_KEY, ltUnidades);

		// Poner las seleccionadas a vacío
		EliminacionForm frm = (EliminacionForm) form;
		frm.setSelectedUdoc(null);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.VALORACION_EDICION_ELIMINACION_AVANZADA,
				request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request,
				mappings.findForward("edicion_eliminacion_avanzada"));
	}

	/**
	 * Presenta el formulario de selección avanzada de unidades documentales
	 * 
	 * @param mappings
	 *            Mapping
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta
	 */
	public void addUdocsExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EliminacionPO eliminacion = (EliminacionPO) getFromTemporalSession(
				request, ValoracionConstants.ELIMINACION_KEY);

		// Establecer las precondiciones de la búsqueda
		PrecondicionesBusquedaFondosGenerica precondicionesBusqueda = new PrecondicionesBusquedaFondosGenerica();
		// precondicionesBusqueda.setFilePath(PrecondicionesBusquedaFondosGenerica.BUSQUEDA_GENERICA_ELIMINACION_SELECCION_UDOCS);
		precondicionesBusqueda
				.setTipoBusqueda(ConfiguracionArchivoManager.ELIMINACION);
		precondicionesBusqueda.setEstados(new String[] { String
				.valueOf(IElementoCuadroClasificacion.VIGENTE) });
		precondicionesBusqueda.setTipoElemento(String
				.valueOf(TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador()));
		precondicionesBusqueda
				.setTiposNivelFicha(new int[] { TipoNiveles.UNIDAD_DOCUMENTAL_VALUE });
		precondicionesBusqueda
				.setBloqueos(new String[] { FondosConstants.UDOC_DESBLOQUEADA });

		int numResultadosPorPagina = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos()
				.getNumResultadosPagSeleccionUdocConservar();

		precondicionesBusqueda
				.setNumResultadosPorPagina(numResultadosPorPagina);

		// no puede ser nulo.
		Object archivo = getFromTemporalSession(request,
				TransferenciasConstants.ARCHIVO_KEY);
		if (archivo != null && (archivo instanceof ArchivoVO)) {
			String idArchivo = ((ArchivoVO) archivo).getId();
			precondicionesBusqueda.setIdArchivo(idArchivo);
		}
		precondicionesBusqueda.setIdRefObjetoAmbito(new String[] { eliminacion
				.getSerie().getId() });

		// Establecer los elementos a restringir
		ActionErrors errors = new ActionErrors();
		ServiceRepository services = getServiceRepository(request);
		GestionEliminacionBI eliminacionBI = services
				.lookupGestionEliminacionBI();
		String subqueryIdsUnidadesRestringir = eliminacionBI
				.getSubConsultaIDsUdocsAEliminar(eliminacion);
		Map subqueryRestringir = new HashMap();
		subqueryRestringir
				.put(ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_COLUMN_NAME_S,
						subqueryIdsUnidadesRestringir);
		precondicionesBusqueda
				.setSubqueryElementosRestringir(subqueryRestringir);

		// Establecer el forward a ejecutar y el de retorno
		precondicionesBusqueda.setForwardListado("seleccion");
		precondicionesBusqueda.setForwardRetorno("returnSeleccionEliminacion");

		// Poner las seleccionadas a vacío
		EliminacionForm frm = (EliminacionForm) form;
		frm.setSelectedUdoc(null);

		// Guardar el enlace a la página
		ClientInvocation inv = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_FORM_BUSQUEDA_AVANZADA_ELEMENTOS,
				request);
		inv.setAsReturnPoint(true);

		// Guardar las precondiciones de la búsqueda
		setInTemporalSession(request,
				FondosConstants.PRECONDICIONES_BUSQUEDA_KEY,
				precondicionesBusqueda);

		// Redirigimos a la página adecuada
		if (errors.isEmpty())
			setReturnActionFordward(request,
					mappings.findForward("edicion_eliminacion_seleccion_udocs"));
		else
			setReturnActionFordward(request,
					mappings.findForward("edicion_eliminacion_avanzada"));
	}

	/**
	 * Método de retorno para obtener las unidades documentales seleccionadas
	 * 
	 * @param mappings
	 *            Mapping
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta
	 */
	public void addUdocsReturnExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] selectedElem = (String[]) getFromTemporalSession(request,
				FondosConstants.ELEMENTOS_SELECCIONADOS_BUSQUEDA_KEY);

		// Obtener la eliminacion
		EliminacionSerieVO eliminacion = (EliminacionSerieVO) getFromTemporalSession(
				request, ValoracionConstants.ELIMINACION_KEY);

		// Obtener el repositorio de servicios
		ServiceRepository services = getServiceRepository(request);
		GestionEliminacionBI eliminacionBI = services
				.lookupGestionEliminacionBI();

		if (!ArrayUtils.isEmpty(selectedElem)) {
			// Añadir las unidades documentales como unidades a conservar
			List ltUnidadesSinFechas = eliminacionBI
					.insertUnidadesEliminacionAConservar(eliminacion.getId(),
							selectedElem);
			if (!ListUtils.isEmpty(ltUnidadesSinFechas)) {
				ActionErrors errores = new ActionErrors();
				String message = Messages
						.getString(
								ValoracionConstants.LABEL_VALORACIONES_ELIMINACION_UNIDADES_SIN_FECHAS,
								request.getLocale());
				errores.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						message));

				obtenerErrores(request, true).add(errores);

				Map mapElementosError = new HashMap();
				mapElementosError.put(message, ltUnidadesSinFechas);
				request.setAttribute(Constants.LISTA_ELEMENTOS_ERROR_KEY,
						mapElementosError);
			}
		}

		// Actualizar la lista de unidades documentales
		removeInTemporalSession(request,
				ValoracionConstants.ELIMINACION_UDOCS_CONSERVAR_KEY);
		setInTemporalSession(request,
				ValoracionConstants.ELIMINACION_UDOCS_CONSERVAR_KEY,
				eliminacionBI.getUnidadesEliminacionAConservar(eliminacion
						.getId()));

		// Redirigimos a la página adecuada
		setReturnActionFordward(request,
				mappings.findForward("edicion_eliminacion_avanzada"));
	}

	/**
	 * Permite eliminar unidades documentales a conservar
	 * 
	 * @param mappings
	 *            Mapping
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta
	 */
	public void delUdocsExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EliminacionPO eliminacion = (EliminacionPO) getFromTemporalSession(
				request, ValoracionConstants.ELIMINACION_KEY);

		// Obtener el formulario
		EliminacionForm frm = (EliminacionForm) form;

		// Obtener los elementos a eliminar
		String[] idsElementos = frm.getSelectedUdoc();

		// Obtener el repositorio de servicios
		ServiceRepository services = getServiceRepository(request);
		GestionEliminacionBI eliminacionBI = services
				.lookupGestionEliminacionBI();

		// Eliminar los seleccionados
		if (!ArrayUtils.isEmpty(idsElementos)) {

			eliminacionBI.eliminarUnidadesEliminacionAConservar(
					eliminacion.getId(), idsElementos);
		}

		// Actualizar la lista de unidades documentales
		removeInTemporalSession(request,
				ValoracionConstants.ELIMINACION_UDOCS_CONSERVAR_KEY);
		setInTemporalSession(request,
				ValoracionConstants.ELIMINACION_UDOCS_CONSERVAR_KEY,
				eliminacionBI.getUnidadesEliminacionAConservar(eliminacion
						.getId()));

		// Redirigimos a la página adecuada
		setReturnActionFordward(request,
				mappings.findForward("edicion_eliminacion_avanzada"));
	}

	/*
	 * Añade fechas a los criterios de eliminacion
	 */
	public void anadirFechaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos los datos del formulario
		EliminacionForm eliminacion = (EliminacionForm) form;

		ActionErrors errores = eliminacion.validateCriterios(request);
		if ((errores == null) || errores.isEmpty()) {
			// Obtenemos el criterio del formulario
			CriterioFechaVO criterio = eliminacion
					.getCriterio(getServiceClient(request).getEngine());

			List criterios = (List) getFromTemporalSession(request,
					ValoracionConstants.LISTA_CRITERIOS_KEY);
			if (criterios == null) {
				criterios = new ArrayList();
				setInTemporalSession(request,
						ValoracionConstants.LISTA_CRITERIOS_KEY, criterios);
			}

			// Añadimos el criterio al listado
			if (!criterios.contains(criterio))
				criterios.add(criterio);

			// Limpiar los rangos de fechas
			eliminacion.setFechaMIni(null);
			eliminacion.setFechaDIni(null);
			eliminacion.setFechaMFin(null);
			eliminacion.setFechaDFin(null);

			request.setAttribute(ValoracionConstants.CRITERIOS_MODIFICADOS_KEY,
					new Boolean(true));
		} else {
			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);
		}

		// Establecemos los elementos del formulario
		this.establecerElementosFormulario(request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_eliminacion"));
	}

	/*
	 * Elimina fechas de los criterios de eliminacion
	 */
	public void eliminarFechaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Obtenemos los criterios que deseamos eliminar
		String[] ids = request.getParameterValues("criterioEliminar");

		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				List criterios = (List) getFromTemporalSession(request,
						ValoracionConstants.LISTA_CRITERIOS_KEY);
				CriterioFechaVO criterio = new CriterioFechaVO(ids[i]);
				criterio.setComparator(CriterioFechaVO.COMPARATOR_ID);

				if (criterios != null && criterios.contains(criterio))
					criterios.remove(criterio);
			}

			request.setAttribute(ValoracionConstants.CRITERIOS_MODIFICADOS_KEY,
					new Boolean(true));
		}

		// Establecemos los elementos del formulario
		this.establecerElementosFormulario(request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_eliminacion"));
	}

	/**
	 * Realiza la busqueda de las series segun el filtrado deseado estableciendo
	 * los elementos encontrados para mostrarlos en la vista
	 * 
	 * @param request
	 */
	private void establecerElementosBusqueda(HttpServletRequest request) {
		// Realizamos la busqueda de las series por los criterios pasados
		String pFondoID = request.getParameter("fondo");
		String pCodigo = request.getParameter("codigo");
		String pTitulo = request.getParameter("tituloBuscar");

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionSeriesBI serieBI = getGestionSeriesBI(request);

		List series = serieBI.findSeriesSeleccionables(pFondoID, pCodigo,
				pTitulo);
		CollectionUtils.transform(series, SerieToPO.getInstance(services));
		request.setAttribute(ValoracionConstants.LISTA_SERIES_KEY, series);
	}

	/**
	 * Establece todos los elementos necesarios para mostrar en la vista del
	 * formulario
	 * 
	 * @param request
	 */
	private void establecerElementosFormulario(HttpServletRequest request) {
		// Obtenemos el servicio de acceso a fondos
		GestionFondosBI fondosService = getGestionFondosBI(request);

		// Obtenemos los fondos existentes
		int[] estados = { IElementoCuadroClasificacion.VIGENTE };
		removeInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY);
		setInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY,
				fondosService.getFondosXEstados(estados));
	}

	public void homeSeleccionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionEliminacionBI eliminacionBI = services
				.lookupGestionEliminacionBI();

		// Obtenemos las eliminaciones en elaboracion y las metemos para mostrar
		List eliminacionesEnElaboracion = eliminacionBI
				.getEliminacionesEnElaboracion(appUser.getId(),
						appUser.getIdsArchivosUser());
		CollectionUtils.transform(eliminacionesEnElaboracion,
				EliminacionToPO.getInstance(services));
		request.setAttribute(ValoracionConstants.LISTA_ELIMINACIONES_KEY,
				eliminacionesEnElaboracion);

		// Lista de fondos
		List fondos = (List) getFromTemporalSession(request,
				ValoracionConstants.LISTA_FONDOS_KEY);
		if (fondos == null) {
			GestionFondosBI fondoBI = services.lookupGestionFondosBI();
			removeInTemporalSession(request,
					ValoracionConstants.LISTA_FONDOS_KEY);
			setInTemporalSession(request, ValoracionConstants.LISTA_FONDOS_KEY,
					fondoBI.getFondosVigentes());
		}
		saveCurrentInvocation(KeysClientsInvocations.SELECCION_SERIES, request);
		setReturnActionFordward(request,
				mappings.findForward("home_seleccion_series"));
	}

	public void buscarSeleccionesConUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Parámetros
		String fondo = request.getParameter("fondo");
		String minNumUdocs = request.getParameter("minNumUdocs");

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameter("fondo", fondo);
		cli.addParameter("minNumUdocs", minNumUdocs);

		ActionErrors errores = validateFormBuscarSeleccionesConUdocs(request);
		if (errores.isEmpty()) {
			saveCurrentInvocation(KeysClientsInvocations.ELIMINACIONES_BUSCAR,
					request);

			try {
				request.setAttribute(
						ValoracionConstants.LISTA_SERIES_KEY,
						getGestionValoracionBI(request)
								.getSeriesSeleccionables(fondo,
										TypeConverter.toInt(minNumUdocs, 0),
										new PageInfo(request, "codigo")));
			} catch (TooManyResultsException e) {
			}

			setReturnActionFordward(request,
					mappings.findForward("lista_series_seleccionables"));
		} else {
			// Añadir los errores
			obtenerErrores(request, true).add(errores);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	protected ActionErrors validateFormBuscarSeleccionesConUdocs(
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Número mínimo de unidades documentales
		String minNumUdocs = request.getParameter("minNumUdocs");
		if (StringUtils.isBlank(minNumUdocs))
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACIONES_SELECCION_NUMERO_MINIMO_UNIDADES,
									request.getLocale())));
		else if (!NumberUtils.isNumber(minNumUdocs))
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_INT,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACIONES_SELECCION_NUMERO_MINIMO_UNIDADES,
									request.getLocale())));

		return errors;
	}
}