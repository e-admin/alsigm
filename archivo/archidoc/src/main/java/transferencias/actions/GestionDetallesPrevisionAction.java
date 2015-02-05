package transferencias.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.NotAvailableException;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.GestorCatalogoFactory;
import se.procedimientos.InfoBProcedimiento;
import se.procedimientos.InfoBProcedimientoImpl;
import se.procedimientos.IProcedimiento;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.forms.DetallePrevisionForm;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.PrevisionVO;
import util.ErrorsTag;

import common.Constants;
import common.Conversor;
import common.Messages;
import common.MultiEntityConstants;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.ListUtils;
import common.view.POUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.UInsDepositoVO;
import fondos.FondosConstants;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.Serie;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;

/**
 * Action con las diferentes acciones sobre detalles de prevision que un usuario
 * puede llevar a cabo
 */
public class GestionDetallesPrevisionAction extends BaseAction {

	/** Logger de la clase */
	private static final Logger logger = Logger
			.getLogger(GestionDetallesPrevisionAction.class);

	public void infoDetalleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI bi = services.lookupGestionPrevisionesBI();
		String pIdDetallePrevision = request.getParameter("idDetallePrevision");
		DetallePrevisionVO detallePrevisionVO = bi
				.abrirDetallePrevision(pIdDetallePrevision);
		DetallePrevisionPO detallePrevision = (DetallePrevisionPO) DetallePrevisionToPO
				.getInstance(services).transform(detallePrevisionVO);

		request.setAttribute(TransferenciasConstants.DETALLEPREVISION_KEY,
				detallePrevision);

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_DETALLE_PREVISION,
				request);
		setReturnActionFordward(request,
				mappings.findForward("info_detalle_prevision"));
	}

	public void nuevoDetalleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		/* GestionCuadroClasificacionBI cuadroBI = */services
				.lookupGestionCuadroClasificacionBI();

		PrevisionVO infoPrevision = (PrevisionVO) request.getSession()
				.getAttribute(TransferenciasConstants.PREVISION_KEY);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_ALTA_DETALLE_PREVISION,
				request);
		invocation.setAsReturnPoint(true);

		if (infoPrevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador()) {
			DetallePrevisionForm detallePrevisionForm = (DetallePrevisionForm) form;
			GestionFondosBI serviceFondos = services.lookupGestionFondosBI();
			List ltFondos = serviceFondos.getFondosVigentes();
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_CODIGOSFONDO_KEY, ltFondos);
			detallePrevisionForm.setIdFondoDestino(infoPrevision
					.getIdfondodestino());

			setReturnActionFordward(
					request,
					mappings.findForward("edicion_detalle_prevision_entre_archivos"));
		} else {
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_FORMATOS_KEY,
					depositoBI.getFormatosVigentes());
			setReturnActionFordward(request,
					mappings.findForward("edicion_detalle_prevision"));
		}
	}

	public void eliminarDetalleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrevisionVO infoPrevision = (PrevisionVO) getFromTemporalSession(
				request, TransferenciasConstants.PREVISION_KEY);
		String[] detallesAEliminar = { ((DetallePrevisionForm) form)
				.getIdDetallePrevision() };
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI bi = services.lookupGestionPrevisionesBI();
		try {
			bi.eliminarDetallePrevision(infoPrevision, detallesAEliminar);
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void eliminarDetallesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrevisionVO infoPrevision = (PrevisionVO) getFromTemporalSession(
				request, TransferenciasConstants.PREVISION_KEY);
		String[] detallesAEliminar = ((DetallePrevisionForm) form)
				.getSeleccionDetalle();
		if (detallesAEliminar != null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionPrevisionesBI bi = services.lookupGestionPrevisionesBI();
			try {
				bi.eliminarDetallePrevision(infoPrevision, detallesAEliminar);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
			}
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void listaProcedimientosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		PrevisionVO infoPrevision = (PrevisionVO) request.getSession()
				.getAttribute(TransferenciasConstants.PREVISION_KEY);
		GestionPrevisionesBI previsionBI = services
				.lookupGestionPrevisionesBI();
		List procedimientos = previsionBI
				.getProcedimientosPrevision(infoPrevision.getId());
		final GestionCuadroClasificacionBI cclfBI = services
				.lookupGestionCuadroClasificacionBI();
		final GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		CollectionUtils.transform(procedimientos, new Transformer() {
			public Object transform(Object obj) {
				ProcedimientoPO po = new ProcedimientoPO(cclfBI, serieBI);
				try {
					PropertyUtils.copyProperties(po, obj);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				return po;
			}
		});
		// request.getSession().removeAttribute(FondosConstants.LISTA_FONDOS_KEY);

		request.setAttribute(TransferenciasConstants.LISTA_PROCEDIMIENTOS_KEY,
				procedimientos);
		setReturnActionFordward(request,
				mappings.findForward("edicion_detalle_prevision"));
	}

	public class ProcedimientoPO extends InfoBProcedimientoImpl {

		private static final long serialVersionUID = 1L;
		GestionSeriesBI serieBI = null;
		GestionCuadroClasificacionBI cclfBI = null;

		SerieVO serieProcedimiento = null;
		ElementoCuadroClasificacionVO funcion = null;

		ProcedimientoPO(GestionCuadroClasificacionBI cclfBI,
				GestionSeriesBI serieBI) {
			this.cclfBI = cclfBI;
			this.serieBI = serieBI;
		}

		public SerieVO getSerie() {
			if (serieProcedimiento == null)
				serieProcedimiento = serieBI.getSerieProcedimiento(getId());
			return serieProcedimiento;
		}

		public ElementoCuadroClasificacionVO getFuncion() {
			if (funcion == null) {
				SerieVO serie = getSerie();
				if (serie != null)
					funcion = cclfBI.getElementoCuadroClasificacion(serie
							.getIdPadre());
			}
			return funcion;
		}
	}

	class ProcedimientoToPO implements Transformer {
		GestionSeriesBI serieBI = null;
		GestionCuadroClasificacionBI cclfBI = null;

		ProcedimientoToPO(ServiceRepository services) {
			serieBI = services.lookupGestionSeriesBI();
			cclfBI = services.lookupGestionCuadroClasificacionBI();
		}

		public Object transform(Object obj) {
			ProcedimientoPO po = new ProcedimientoPO(cclfBI, serieBI);
			POUtils.copyVOProperties(po, obj);
			return po;
		}
	}

	public void verBuscadorSeriesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		PrevisionVO infoPrevision = (PrevisionVO) request.getSession()
				.getAttribute(TransferenciasConstants.PREVISION_KEY);

		if (infoPrevision.getTipotransferencia() != TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador()) {
			GestionFondosBI fondoBI = services.lookupGestionFondosBI();
			setInTemporalSession(request, FondosConstants.LISTA_FONDOS_KEY,
					fondoBI.getFondosVigentes());
			setReturnActionFordward(request,
					mappings.findForward("edicion_detalle_prevision"));
		} else {
			setReturnActionFordward(
					request,
					mappings.findForward("edicion_detalle_prevision_entre_archivos"));
		}
	}

	public void verBuscadorSeriesOrigenExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(
				request,
				mappings.findForward("edicion_detalle_prevision_entre_archivos"));
	}

	public void verBuscadorSeriesDestinoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(
				request,
				mappings.findForward("edicion_detalle_prevision_entre_archivos"));
	}

	public class SeriePO extends Serie {
		GestionCuadroClasificacionBI cclfBI = null;

		SeriePO(GestionCuadroClasificacionBI cclfBI) {
			this.cclfBI = cclfBI;
		}

		public ElementoCuadroClasificacionVO getFuncion() {
			return cclfBI.getElementoCuadroClasificacion(getIdPadre());
		}
	}

	private void buscarSerieComunLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String fondo, String codigo, String titulo) {

		ServiceClient client = getServiceClient(request);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();

		List series = null;

		// Comprobar si el usuario tiene el permisos de Ampliado de Elaboración
		// de Transferencia Extraordinarias
		if (client
				.hasAnyPermission(new String[] { AppPermissions.AMPLIADO_ELABORACION_TRANSFERENCIAS_EXTRAORDINARIAS })) {
			series = serieBI.findSeriesParaPrevision(fondo, codigo, titulo);
		} else {
			// En caso de que no lo tenga, sólo se muestran las series de las
			// cuales el organo del usuario es productor.
			series = serieBI.findSeriesParaPrevisionByIdOrgano(fondo, codigo,
					titulo, client.getOrganization().getIdOrg());
		}

		final GestionCuadroClasificacionBI cclfBI = services
				.lookupGestionCuadroClasificacionBI();
		CollectionUtils.transform(series, new Transformer() {
			public Object transform(Object obj) {
				SeriePO po = new SeriePO(cclfBI);
				try {
					PropertyUtils.copyProperties(po, obj);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				return po;
			}
		});
		request.setAttribute(FondosConstants.SERIE_KEY, series);
	}

	public void buscarSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener la información de la previsión
		PrevisionVO infoPrevision = (PrevisionVO) request.getSession()
				.getAttribute(TransferenciasConstants.PREVISION_KEY);

		// Obtener el id de fondo destino
		String pFondoID = infoPrevision.getIdfondodestino();

		// Obtener el código de la serie
		String pCodigo = request.getParameter("codigo");

		// Obtener el título de la serie
		String pTitulo = request.getParameter("titulo");

		// Buscar la serie
		buscarSerieComunLogic(mappings, form, request, response, pFondoID,
				pCodigo, pTitulo);

		// Volver al detalle de la previsión
		setReturnActionFordward(request,
				mappings.findForward("edicion_detalle_prevision"));

	}

	public void buscarSerieOrigenExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener el formulario
		DetallePrevisionForm detallePrevisionForm = (DetallePrevisionForm) form;

		// Obtener el fondo destino
		String pFondoID = detallePrevisionForm.getIdFondoOrigen();

		// Obtener el codigo de serie destino
		String pCodigo = request.getParameter("codigoOrigen");

		// Obtener el título de serie destino
		String pTitulo = request.getParameter("tituloOrigen");

		// Buscar la serie
		buscarSerieComunLogic(mappings, form, request, response, pFondoID,
				pCodigo, pTitulo);

		// Volver a la edición del detalle
		setReturnActionFordward(
				request,
				mappings.findForward("edicion_detalle_prevision_entre_archivos"));
	}

	public void buscarSerieDestinoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener el formulario
		DetallePrevisionForm detallePrevisionForm = (DetallePrevisionForm) form;

		// Obtener el fondo destino
		String pFondoID = detallePrevisionForm.getIdFondoDestino();

		// Obtener el codigo de serie destino
		String pCodigo = request.getParameter("codigoDestino");

		// Obtener el título de serie destino
		String pTitulo = request.getParameter("tituloDestino");

		// Buscar la serie
		buscarSerieComunLogic(mappings, form, request, response, pFondoID,
				pCodigo, pTitulo);

		// Volver al detalle de la previsión
		setReturnActionFordward(
				request,
				mappings.findForward("edicion_detalle_prevision_entre_archivos"));
	}

	public void mostrarInfoUnidadesInstalacionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Obtener la información de la previsión
		PrevisionVO infoPrevision = (PrevisionVO) request.getSession()
				.getAttribute(TransferenciasConstants.PREVISION_KEY);

		DetallePrevisionForm detallePrevisionForm = (DetallePrevisionForm) form;
		ActionErrors errores = validarDetallePrevisionForm(request,
				detallePrevisionForm, infoPrevision);

		if (errores != null && errores.size() > 0) {
			ErrorsTag.saveErrors(request, errores);
			setReturnActionFordward(
					request,
					mappings.findForward("edicion_detalle_prevision_entre_archivos"));
		} else {
			ServiceRepository services = getServiceRepository(request);

			List ltUInsDepositoVO = getListaUInstEntreArchivos(
					detallePrevisionForm, infoPrevision, services);

			List ltUDocsElectronicas = getListaUDocsElectronicasEntreArchivos(
					detallePrevisionForm, infoPrevision, services);

			ActionErrors errors = validarDetallePrevisionForm(
					detallePrevisionForm, infoPrevision, ltUInsDepositoVO,
					ltUDocsElectronicas);
			if (errors != null && errors.size() > 0) {
				ErrorsTag.saveErrors(request, errors);
			}

			// Lista de Unidades de Instalación
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_UINST_ENTRE_ARCHIVOS_KEY,
					ltUInsDepositoVO);

			// Lista de Unidades Documentales Electrónicas
			setInTemporalSession(
					request,
					TransferenciasConstants.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY,
					ltUDocsElectronicas);

			// Volver al detalle de la previsión
			setReturnActionFordward(
					request,
					mappings.findForward("edicion_detalle_prevision_entre_archivos"));
		}
	}

	public void buscarProcedimientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionPrevisionesBI previsionBI = services
				.lookupGestionPrevisionesBI();
		String pTitulo = request.getParameter("tituloProcedimiento");
		String pTipoProcedimiento = request.getParameter("tipoProcedimiento");
		try {
			PrevisionVO infoPrevision = (PrevisionVO) getFromTemporalSession(
					request, TransferenciasConstants.PREVISION_KEY);
			List procedimientos = previsionBI.findProcedimientosATransferir(
					infoPrevision.getIdfondodestino(),
					Integer.parseInt(pTipoProcedimiento), pTitulo);
			CollectionUtils.transform(procedimientos, new ProcedimientoToPO(
					getServiceRepository(request)));
			request.setAttribute(
					TransferenciasConstants.LISTA_PROCEDIMIENTOS_KEY,
					procedimientos);
		} catch (GestorCatalogoException gce) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_CATALOGO));
		} catch (NotAvailableException e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_CATALOGO));
		}
		setReturnActionFordward(request,
				mappings.findForward("edicion_detalle_prevision"));
	}

	public void seleccionSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setReturnActionFordward(request,
				mappings.findForward("seleccion_serie"));
	}

	protected ActionErrors validarDetallePrevisionForm(
			DetallePrevisionForm form, PrevisionVO infoPrevision,
			List ltUInstalacionVO, List ltUDocsElectronicas) {
		ActionErrors erroresDetectados = new ActionErrors();
		// if (infoPrevision.getTipotransferencia()==
		// TipoTransferencia.ENTRE_ARCHIVOS.getIdentificador()) {
		if (infoPrevision.isEntreArchivos()) {
			// if ((ltUInstalacionVO == null) || (ltUInstalacionVO.isEmpty())) {
			if (ListUtils.isEmpty(ltUInstalacionVO)
					&& ListUtils.isEmpty(ltUDocsElectronicas)) {
				erroresDetectados
						.add(Constants.ERROR_NO_UI_TRANSFERENCIAS_ENTRE_ARCHIVOS,
								new ActionError(
										Constants.ERROR_NO_UI_TRANSFERENCIAS_ENTRE_ARCHIVOS));
			}
		}

		return erroresDetectados;
	}

	protected ActionErrors validarDetallePrevisionForm(
			HttpServletRequest request, DetallePrevisionForm form,
			PrevisionVO infoPrevision) {
		ActionErrors erroresDetectados = new ActionErrors();

		if (!GenericValidator.isInt(form.getFechaInicio())
				|| Integer.parseInt(form.getFechaInicio()) < 0)
			erroresDetectados.add(
					Constants.ERROR_INVALID,
					new ActionError(Constants.ERROR_INVALID, Messages
							.getString(Constants.ETIQUETA_ANIO_INICIAL,
									request.getLocale())));
		if (!GenericValidator.isInt(form.getFechaFin())
				|| Integer.parseInt(form.getFechaFin()) < 0)
			erroresDetectados.add(
					Constants.ERROR_INVALID,
					new ActionError(Constants.ERROR_INVALID, Messages
							.getString(Constants.ETIQUETA_ANIO_FINAL,
									request.getLocale())));
		if (form.getFechaFin() != null && form.getFechaInicio() != null)
			if (form.getFechaInicio().compareTo(form.getFechaFin()) > 0)
				erroresDetectados.add(
						Constants.ERROR_INITDATE_AFTER_ENDDATE,
						new ActionError(Constants.ERROR_INITDATE_AFTER_ENDDATE,
								Messages.getString(
										Constants.ETIQUETA_ANIO_FINAL,
										request.getLocale())));
		// if (StringUtils.isBlank(form.getProcedimiento())) {
		// erroresDetectados.add(Constants.ERROR_REQUIRED,
		// new ActionError(Constants.ERROR_REQUIRED, "Procedimiento"));
		// }

		if (infoPrevision.getTipotransferencia() != TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador()) {
			if (StringUtils.isBlank(form.getSerieDestino())) {
				erroresDetectados.add(
						Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_SERIE,
										request.getLocale())));
			}
			if (!GenericValidator.isInt(form.getNumUInstalacion()))
				erroresDetectados
						.add(Constants.ERROR_INT,
								new ActionError(
										Constants.ERROR_INT,
										Messages.getString(
												Constants.ETIQUETA_NUM_UNIDADES_INSTALACION,
												request.getLocale())));
			else if (Integer.parseInt(form.getNumUInstalacion()) <= 0)
				erroresDetectados
						.add(Constants.ERROR_INT_MAYOR_CERO,
								new ActionError(
										Constants.ERROR_INT_MAYOR_CERO,
										Messages.getString(
												Constants.ETIQUETA_NUM_UNIDADES_INSTALACION,
												request.getLocale())));

		} else {
			// Realizar comprobaciones para transferencias entre archivos
			if (StringUtils.isBlank(form.getSerieOrigen())) {
				erroresDetectados.add(
						Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_SERIE_ORIGEN,
										request.getLocale())));
			}
			if (StringUtils.isBlank(form.getSerieDestino())) {
				erroresDetectados.add(
						Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_SERIE_DESTINO,
										request.getLocale())));
			}
		}
		return erroresDetectados;
	}

	public void crearDetalleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		PrevisionVO infoPrevision = (PrevisionVO) getFromTemporalSession(
				request, TransferenciasConstants.PREVISION_KEY);
		GestionPrevisionesBI previsionesBI = services
				.lookupGestionPrevisionesBI();

		DetallePrevisionVO infoDetallePrevision = new DetallePrevisionVO(
				infoPrevision.getId());

		DetallePrevisionForm detalleForm = (DetallePrevisionForm) form;

		ActionErrors errores = validarDetallePrevisionForm(request,
				detalleForm, infoPrevision);

		List ltUInstalacionVO = null;
		List ltUDocsElectronicas = null;

		if ((errores == null) || (errores.isEmpty())) {
			// Validar que hay alguna unidad de instalación que cumpla las
			// condiciones
			if (infoPrevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
					.getIdentificador())
				ltUInstalacionVO = getListaUInstEntreArchivos(detalleForm,
						infoPrevision, services);

			ltUDocsElectronicas = getListaUDocsElectronicasEntreArchivos(
					detalleForm, infoPrevision, services);

			errores = validarDetallePrevisionForm(detalleForm, infoPrevision,
					ltUInstalacionVO, ltUDocsElectronicas);
		}

		if (errores != null && errores.size() > 0) {
			ErrorsTag.saveErrors(request, errores);
			if (infoPrevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
					.getIdentificador())
				setReturnActionFordward(
						request,
						mappings.findForward("edicion_detalle_prevision_entre_archivos"));
			else
				setReturnActionFordward(request,
						mappings.findForward("edicion_detalle_prevision"));
		} else {
			try {
				infoDetallePrevision.setIdProcedimiento(detalleForm
						.getProcedimiento());
				infoDetallePrevision.setIdSerieOrigen(detalleForm
						.getSerieOrigen());
				infoDetallePrevision.setIdSerieDestino(detalleForm
						.getSerieDestino());
				infoDetallePrevision.setObservaciones(detalleForm
						.getObservaciones());
				infoDetallePrevision
						.setAnoIniUdoc(detalleForm.getFechaInicio());
				infoDetallePrevision.setAnoFinUdoc(detalleForm.getFechaFin());
				if (infoPrevision.getTipotransferencia() != TipoTransferencia.ENTRE_ARCHIVOS
						.getIdentificador()) {
					infoDetallePrevision.setIdFormatoUI(detalleForm
							.getFormato());
					infoDetallePrevision.setNumUInstalacion(detalleForm
							.getNumUInstalacionAsInt());
				} else {
					String xmlDetallePrev = getXmlUInstEntreArchivos(
							ltUInstalacionVO, ltUDocsElectronicas);
					infoDetallePrevision.setInfo(xmlDetallePrev);

					infoDetallePrevision
							.setIdFormatoUI(getFormatoPreferenteUInstEntreArchivos(ltUInstalacionVO));

					if (ListUtils.isNotEmpty(ltUInstalacionVO)) {
						infoDetallePrevision
								.setNumUInstalacion(ltUInstalacionVO.size());

					} else {
						if (ListUtils.isNotEmpty(ltUDocsElectronicas)) {
							infoDetallePrevision
									.setIdFormatoUI(Constants.FORMATO_UI_TRANSFERENCIA_ELECTRONICA);
							infoDetallePrevision
									.setNumUInstalacion(ltUDocsElectronicas
											.size());
						}
					}
				}

				previsionesBI.nuevoDetallePrevision(infoPrevision,
						infoDetallePrevision);
				goReturnPointExecuteLogic(mappings, form, request, response);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				if (infoPrevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
						.getIdentificador())
					setReturnActionFordward(
							request,
							mappings.findForward("edicion_detalle_prevision_entre_archivos"));
				else
					setReturnActionFordward(request,
							mappings.findForward("edicion_detalle_prevision"));
			}
		}
	}

	public void editarDetalleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().removeAttribute(FondosConstants.LISTA_FONDOS_KEY);
		PrevisionVO infoPrevision = (PrevisionVO) request.getSession()
				.getAttribute(TransferenciasConstants.PREVISION_KEY);
		try {
			String pIdDetallePrevision = request
					.getParameter("idDetallePrevision");
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionPrevisionesBI bi = services.lookupGestionPrevisionesBI();

			DetallePrevisionVO detallePrevisionVO = bi
					.getDetallePrevision(pIdDetallePrevision);
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_EDICION_DETALLE_PREVISION,
					request);
			invocation.setAsReturnPoint(true);
			setInTemporalSession(request,
					TransferenciasConstants.DETALLEPREVISION_KEY,
					detallePrevisionVO);

			DetallePrevisionPO detallePrevision = (DetallePrevisionPO) DetallePrevisionToPO
					.getInstance(services).transform(detallePrevisionVO);

			DetallePrevisionForm detalleForm = (DetallePrevisionForm) form;
			detalleForm.clear();
			detalleForm.populate(detallePrevision);
			//
			// if (detallePrevision.getIdProcedimiento() != null) {
			// detalleForm.setProcedimiento(detallePrevision.getIdProcedimiento());
			// detalleForm.setNombreProcedimiento(detallePrevision.getProcedimiento().getNombre());
			// }
			// if (detallePrevision.getIdSerie() != null) {
			// SerieVO serie = detallePrevision.getSerie();
			// detalleForm.setSerie(detallePrevision.getIdSerie());
			// StringBuffer buff = new StringBuffer()
			// .append(serie.getCodReferencia()).append(" ")
			// .append(serie.getTitulo());
			//
			// detalleForm.setNombreSerie(buff.toString());
			// ElementoCuadroClasificacionVO funcion =
			// detallePrevision.getFuncion();
			// buff.setLength(0);
			// buff.append(funcion.getCodReferencia()).append(" ").append(funcion.getTitulo());
			// detalleForm.setFuncion(buff.toString());
			// }
			// detalleForm.setFechaInicio(detallePrevision.getAnoIniUdoc());
			// detalleForm.setFechaFin(detallePrevision.getAnoFinUdoc());
			//
			// detalleForm.setFormato(detallePrevision.getIdFormatoUI());
			// detalleForm.setNumUInstalacionAsInt(detallePrevision.getNumUInstalacion());
			// detalleForm.setObservaciones(detallePrevision.getObservaciones());

			if (infoPrevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
					.getIdentificador()) {
				GestionFondosBI serviceFondos = services
						.lookupGestionFondosBI();
				List ltFondos = serviceFondos.getFondosVigentes();
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_CODIGOSFONDO_KEY,
						ltFondos);

				List ltUInstalacionVO = getListaUInstEntreArchivos(detalleForm,
						infoPrevision, services);

				setInTemporalSession(request,
						TransferenciasConstants.LISTA_UINST_ENTRE_ARCHIVOS_KEY,
						ltUInstalacionVO);

				setReturnActionFordward(
						request,
						mappings.findForward("edicion_detalle_prevision_entre_archivos"));
			} else {
				GestorEstructuraDepositoBI depositoBI = services
						.lookupGestorEstructuraDepositoBI();
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_FORMATOS_KEY,
						depositoBI.getFormatosVigentes());
				setReturnActionFordward(request,
						mappings.findForward("edicion_detalle_prevision"));
			}
		} catch (GestorCatalogoException e1) {
			// TODO tratar error interactuando con el catalogo de procedimientos
		} catch (NotAvailableException e1) {
		}
		// }
	}

	/*
	 * void verEdicionDetalle(ActionMapping mappings, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) {
	 * ServiceRepository services =
	 * ServiceRepository.getInstance(ServiceClient.create(getAppUser(request)));
	 * GestorEstructuraDepositoBI depositoBI =
	 * services.lookupGestorEstructuraDepositoBI();
	 * setInTemporalSession(request,TransferenciasConstants.LISTA_FORMATOS_KEY,
	 * depositoBI.getFormatos()); saveCurrentInvocation(KeysClientsInvocations.
	 * TRANSFERENCIAS_EDICION_DETALLE_PREVISION, request);
	 * setReturnActionFordward(request,
	 * mappings.findForward("edicion_detalle_prevision")); }
	 */

	/**
	 * Permite obtener la lista de unidades de instalación que cumplen las
	 * condiciones necesarias para realizar una transferencia entre archivos
	 * 
	 * @param detalleForm
	 *            Formulario del detalle
	 * @param infoPrevision
	 *            Información de la previsión
	 * @param services
	 *            Servicios
	 * @return Lista de {@link ElementoCuadroClasificacion}
	 */
	private List getListaUInstEntreArchivos(DetallePrevisionForm detalleForm,
			PrevisionVO infoPrevision, ServiceRepository services) {

		String anioDesde = detalleForm.getFechaInicio();
		String anioHasta = detalleForm.getFechaFin();

		Date fechaFinalDesde = null;
		Date fechaFinalHasta = null;

		if (anioDesde != null) {
			try {
				fechaFinalDesde = DateUtils.getFirstDayOfYear(Conversor
						.toInt(anioDesde));
			} catch (Exception e) {
				logger.error("Error al convertir el año Inicial de las Unidades Documentales");
			}
		}

		if (anioHasta != null) {
			try {
				fechaFinalHasta = DateUtils.getFirstDayOfYear(Conversor
						.toInt(anioHasta) + 1);
			} catch (Exception e) {
				logger.error("Error al convertir el año Final de las Unidades Documentales");

			}
		}

		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();

		return relacionesBI.getUInstParaRelacionEntreArchivos(fechaFinalDesde,
				fechaFinalHasta, infoPrevision.getIdarchivoremitente(),
				detalleForm.getSerieOrigen(), null);
	}

	/**
	 * Permite obtener la lista de Documentos Electrónicos que cumplen los
	 * requisitos para realizar una transferencia entre archivos
	 * 
	 * @param detalleForm
	 *            Formulario del detalle
	 * @param infoPrevision
	 *            Información de la previsión
	 * @param services
	 *            Servicios
	 * @return Lista de {@link ElementoCuadroClasificacion}
	 */
	private List getListaUDocsElectronicasEntreArchivos(
			DetallePrevisionForm detalleForm, PrevisionVO infoPrevision,
			ServiceRepository services) {

		String anioDesde = detalleForm.getFechaInicio();
		String anioHasta = detalleForm.getFechaFin();

		Date fechaFinalDesde = null;
		Date fechaFinalHasta = null;

		if (anioDesde != null) {
			try {
				fechaFinalDesde = DateUtils.getFirstDayOfYear(Conversor
						.toInt(anioDesde));
			} catch (Exception e) {
				logger.error("Error al convertir el año Inicial de las Unidades Documentales");
				return null;
			}
		}

		if (anioHasta != null) {
			try {
				fechaFinalHasta = DateUtils.getFirstDayOfYear(Conversor
						.toInt(anioHasta) + 1);
			} catch (Exception e) {
				logger.error("Error al convertir el año Final de las Unidades Documentales");
				return null;
			}
		}

		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();

		return relacionesBI.getUDocsElectronicasParaRelacionEntreArchivos(
				fechaFinalDesde, fechaFinalHasta,
				infoPrevision.getIdarchivoremitente(),
				detalleForm.getSerieOrigen());
	}

	private String getXmlUInstEntreArchivos(List ltUInstalacionVO,
			List ltUDocsElectronicas) {
		StringBuffer xml = new StringBuffer();
		xml.append("<info>");

		if (!ListUtils.isEmpty(ltUDocsElectronicas)) {
			xml.append("<udocsElectronicas>")
					.append(ltUDocsElectronicas.size())
					.append("</udocsElectronicas>");
		}

		if ((ltUInstalacionVO != null) && (!ltUInstalacionVO.isEmpty())) {

			// Agrupar las unidades de instalación por formato
			Map mapFormatoUInst = new HashMap();
			Iterator it = ltUInstalacionVO.iterator();
			while (it.hasNext()) {
				UInsDepositoVO uInsDepositoVO = (UInsDepositoVO) it.next();

				List ltFormato = (List) mapFormatoUInst.get(uInsDepositoVO
						.getIdformato());
				if (ListUtils.isEmpty(ltFormato)) {
					ltFormato = new ArrayList();
					ltFormato.add(uInsDepositoVO);
					mapFormatoUInst.put(uInsDepositoVO.getIdformato(),
							ltFormato);
				} else {
					ltFormato.add(uInsDepositoVO);
					mapFormatoUInst.put(uInsDepositoVO.getIdformato(),
							ltFormato);
				}
			}

			xml.append("<volumen_formato>");

			// Recorrer los formatos

			it = mapFormatoUInst.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();

				// Obtener las unidades de instalación
				List ltUinst = (List) entry.getValue();

				if (!ListUtils.isEmpty(ltUinst)) {

					// Obtener la primera posicion
					UInsDepositoVO primerUInsDepositoVO = (UInsDepositoVO) ltUinst
							.get(0);

					int unidadesTotales = 0;
					ListIterator itLtUinst = ltUinst.listIterator();
					while (itLtUinst.hasNext()) {
						UInsDepositoVO uInsDepositoVO = (UInsDepositoVO) itLtUinst
								.next();
						int unidades = Integer.parseInt(uInsDepositoVO
								.getUnidadestotales());
						unidadesTotales += unidades;
					}

					xml.append("<formato");
					xml.append(" idFmt=\"")
							.append(primerUInsDepositoVO.getIdformato())
							.append("\"").append(" nombreFmt=\"")
							.append(primerUInsDepositoVO.getNombreFormato())
							.append("\"").append(" numUI=\"")
							.append(ltUInstalacionVO.size()).append("\"")
							.append(" numUDocs=\"").append(unidadesTotales)
							.append("\"").append("/>");
				}
			}
			xml.append("</volumen_formato>");
		}
		xml.append("</info>");
		return xml.toString();
	}

	/**
	 * Permite obtener la unidad de instalación que tiene mayor número de
	 * unidades documentales
	 * 
	 * @param ltUInstalacionVO
	 *            Lista de unidades de instalación
	 * @return
	 */
	private String getFormatoPreferenteUInstEntreArchivos(List ltUInstalacionVO) {

		HashMap map = new HashMap();
		if ((ltUInstalacionVO != null) && (!ltUInstalacionVO.isEmpty())) {

			ListIterator it = ltUInstalacionVO.listIterator();
			while (it.hasNext()) {
				UInsDepositoVO uInsDepositoVO = (UInsDepositoVO) it.next();
				Integer num = (Integer) map.get(uInsDepositoVO.getIdformato());
				if (num == null) {
					map.put(uInsDepositoVO.getIdformato(), new Integer(1));
				} else {
					num = new Integer(num.intValue() + 1);
					map.put(uInsDepositoVO.getIdformato(), num);
				}
			}
		}

		String idFormato = null;
		int mayorFormato = 0;
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			int value = ((Integer) entry.getValue()).intValue();
			if (idFormato == null) {
				idFormato = (String) entry.getKey();
				mayorFormato = value;
			} else if (value > mayorFormato) {
				idFormato = (String) entry.getKey();
				mayorFormato = value;
			}
		}

		return idFormato;
	}

	public void guardarDetalleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener información del usuario conectado
		AppUser appUser = getAppUser(request);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		DetallePrevisionForm detalleForm = (DetallePrevisionForm) form;
		PrevisionVO infoPrevision = (PrevisionVO) request.getSession()
				.getAttribute(TransferenciasConstants.PREVISION_KEY);
		DetallePrevisionVO detallePrevision = (DetallePrevisionVO) getFromTemporalSession(
				request, TransferenciasConstants.DETALLEPREVISION_KEY);
		try {
			if (StringUtils.isNotEmpty(detalleForm.getProcedimiento())) {

				// Obtener la entidad para el usuario conectado
				Properties params = null;

				if ((appUser != null)
						&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
					params = new Properties();
					params.put(MultiEntityConstants.ENTITY_PARAM,
							appUser.getEntity());
				}

				GestorCatalogo catalogoProcedimientos = GestorCatalogoFactory
						.getConnector(params);
				IProcedimiento procedimiento = catalogoProcedimientos
						.recuperarProcedimiento(detalleForm.getProcedimiento());

				if (procedimiento != null) {
					InfoBProcedimiento infoProcedimiento = procedimiento
							.getInformacionBasica();
					detallePrevision.setIdProcedimiento(infoProcedimiento
							.getCodigo());
					GestionSeriesBI seriesBI = services.lookupGestionSeriesBI();
					SerieVO serie = seriesBI
							.getSerieProcedimiento(infoProcedimiento.getId());
					detallePrevision.setIdSerieDestino(serie.getId());
					detallePrevision.setCodSistProductor(infoProcedimiento
							.getCodSistProductor());
					detallePrevision.setNombreSistProductor(infoProcedimiento
							.getNombreSistProductor());
				}
			} else {
				detallePrevision.setIdSerieDestino(detalleForm
						.getSerieDestino());
			}

			ActionErrors validationErrors = validarDetallePrevisionForm(
					request, detalleForm, infoPrevision);

			List ltUInstalacionVO = null;
			List ltUDocsElectronicas = null;
			if ((validationErrors == null) || (validationErrors.isEmpty())) {
				// Validar que hay alguna unidad de instalación que cumpla las
				// condiciones
				if (infoPrevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
						.getIdentificador()) {
					ltUInstalacionVO = getListaUInstEntreArchivos(detalleForm,
							infoPrevision, services);
					ltUDocsElectronicas = getListaUDocsElectronicasEntreArchivos(
							detalleForm, infoPrevision, services);
				}

				validationErrors = validarDetallePrevisionForm(detalleForm,
						infoPrevision, ltUInstalacionVO, ltUDocsElectronicas);
			}

			// if
			// (GenericValidator.isBlankOrNull(detalleForm.getProcedimiento()))
			// validationErrors.add(Constants.ERROR_REQUIRED,
			// new ActionError(Constants.ERROR_REQUIRED, "Procedimiento"));
			if (validationErrors != null && validationErrors.size() > 0) {
				ErrorsTag.saveErrors(request, validationErrors);
				// verEdicionDetalle(mappings, form, request, response);
				if (infoPrevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
						.getIdentificador())
					setReturnActionFordward(
							request,
							mappings.findForward("edicion_detalle_prevision_entre_archivos"));
				else
					setReturnActionFordward(request,
							mappings.findForward("edicion_detalle_prevision"));
			} else {
				GestionPrevisionesBI previsionesBI = services
						.lookupGestionPrevisionesBI();
				detallePrevision.setIdFormatoUI(detalleForm.getFormato());
				detallePrevision.setObservaciones(detalleForm
						.getObservaciones());
				detallePrevision.setAnoIniUdoc(detalleForm.getFechaInicio());
				detallePrevision.setAnoFinUdoc(detalleForm.getFechaFin());

				if (infoPrevision.getTipotransferencia() != TipoTransferencia.ENTRE_ARCHIVOS
						.getIdentificador()) {
					detallePrevision.setIdFormatoUI(detalleForm.getFormato());
					infoPrevision.setNumuinstalacion(infoPrevision
							.getNumuinstalacion()
							- detallePrevision.getNumUInstalacion()
							+ detalleForm.getNumUInstalacionAsInt());
					detallePrevision.setNumUInstalacion(detalleForm
							.getNumUInstalacionAsInt());
				} else {
					// Leer los formatos y transformarlo en xml
					detallePrevision.setIdSerieOrigen(detalleForm
							.getSerieOrigen());

					String xmlDetallePrev = getXmlUInstEntreArchivos(
							ltUInstalacionVO, ltUDocsElectronicas);
					detallePrevision.setInfo(xmlDetallePrev);

					detallePrevision
							.setIdFormatoUI(getFormatoPreferenteUInstEntreArchivos(ltUInstalacionVO));
					detallePrevision
							.setNumUInstalacion(ltUInstalacionVO.size());
				}

				try {
					previsionesBI.modificarDetallePrevision(infoPrevision,
							detallePrevision);
					goBackExecuteLogic(mappings, form, request, response);
				} catch (ActionNotAllowedException anae) {
					guardarError(request, anae);
					setReturnActionFordward(request,
							mappings.findForward("edicion_detalle_prevision"));
				}
			}
		} catch (GestorCatalogoException gce) {
			logger.error("Error en la recuperacion de informacion del sistema gestor de procedimientos. "
					+ gce.getMessage());
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_CATALOGO));
			setReturnActionFordward(request,
					mappings.findForward("edicion_detalle_prevision"));
		} catch (NotAvailableException nae) {
			logger.error("El sistema gestor de procedimientos no implementa la funcionalidad requerida. "
					+ nae.getMessage());
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_CATALOGO));
			setReturnActionFordward(request,
					mappings.findForward("edicion_detalle_prevision"));
		}
	}
}