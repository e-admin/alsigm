package transferencias.actions;

import gcontrol.exceptions.UsuariosNotAllowedException;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.CAOrganoVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.forms.PrevisionForm;
import transferencias.model.InvalidTipoTransferenciaException;
import transferencias.vos.PrevisionVO;
import util.ErrorsTag;
import auditoria.ArchivoErrorCodes;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.TypeConverter;

/**
 * Action que lleva a cabo las diferentes acciones que pueden ser realizadas
 * sobre previsiones
 * 
 */
public class GestionPrevisionesAction extends BaseAction {
	/** Logger de la clase */
	protected final static Logger logger = Logger
			.getLogger(GestionPrevisionesAction.class);

	public void seleccionTipoPrevisionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_SELECCION_TIPO_NUEVA_PREVISION,
				request);
		setReturnActionFordward(request,
				mappings.findForward("seleccionar_tipoprevision"));
	}

	public void cargarArchivosReceptoresCreacionExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		cargarArchivosReceptoresExecuteLogic(mapping, form, request, response);
		setReturnActionFordward(request, mapping.findForward("nueva_prevision"));
	}

	public void cargarArchivosReceptoresEdicionExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		cargarArchivosReceptoresExecuteLogic(mapping, form, request, response);
		setReturnActionFordward(request,
				mapping.findForward("edicion_prevision"));
	}

	private void cargarArchivosReceptoresExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		PrevisionForm previsionForm = (PrevisionForm) form;
		String idarchivoremitente = previsionForm.getIdarchivoremitente();
		GestionArchivosBI serviceArchivos = services.lookupGestionArchivosBI();
		List ltArchivos = serviceArchivos
				.getArchivosReceptores(idarchivoremitente);
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_CODIGOSARCHIVORECEPTORES_KEY,
				ltArchivos);

	}

	protected void nuevaExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionPrevisionesBI previsionBI = services
				.lookupGestionPrevisionesBI();
		PrevisionForm previsionForm = (PrevisionForm) form;
		int tipoTransferencia = previsionForm.getTipotransferencia();
		int tipoPrevision = previsionForm.getTipoprevision();

		CAOrganoVO infoOrgano = appUser.getOrganization();
		GestionArchivosBI serviceArchivos = services.lookupGestionArchivosBI();
		if (infoOrgano != null) {
			Object obj = serviceArchivos.getArchivoXId(infoOrgano
					.getIdArchivoReceptor());
			if (obj == null) {
				obtenerErrores(request, true).add(
						Constants.ERROR_REQUIRED,
						new ActionError(
								Constants.ERRORS_TRANSFERENCIAS_SIN_ARCHIVO));
				setReturnActionFordward(request,
						mapping.findForward("seleccionar_tipoprevision"));
				return;
			}
		}

		try {
			/* int tipoOperacion = */PrevisionVO.getTipoOperacion(
					tipoTransferencia, tipoPrevision);

			if (infoOrgano == null) {
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_ERROR,
								new ActionError(
										TransferenciasConstants.ERROR_TRANSFERENCIAS_PREVISIONES_NO_EXISTE_ORGANO));
				goLastClientExecuteLogic(mapping, form, request, response);
				return;
			}
			try {

				String codArchivo = null;
				ArchivoVO archivoVO = getGestionArchivosBI(request)
						.getArchivoXId(infoOrgano.getIdArchivoReceptor());

				if (archivoVO != null) {
					codArchivo = archivoVO.getCodigo();
				}

				PrevisionVO prevision = previsionBI.nuevaPrevision(
						appUser.getId(), infoOrgano.getIdOrg(),
						tipoTransferencia, tipoPrevision,
						infoOrgano.getIdArchivoReceptor(), codArchivo);
				if (prevision.getFondoCanBeChanged()) {
					GestionFondosBI serviceFondos = services
							.lookupGestionFondosBI();
					List ltFondos = serviceFondos.getFondosVigentes();

					if (ltFondos == null || ltFondos.size() == 0)
						throw new ActionNotAllowedException(null,
								ArchivoErrorCodes.FONDOS_DESTINO_NO_DEFINIDOS,
								ArchivoModules.TRANSFERENCIAS_MODULE);
					else
						setInTemporalSession(request,
								TransferenciasConstants.LISTA_CODIGOSFONDO_KEY,
								ltFondos);
				}

				/*
				 * if (prevision.isExtraordinaria()){ if
				 * (prevision.getArchivoReceptorCanBeChanged()) {
				 * GestionArchivosBI serviceArchivos =
				 * services.lookupGestionArchivosBI(); List ltArchivos =
				 * serviceArchivos
				 * .getArchivosXId(appUser.getCustodyArchiveList().toArray());
				 * setInTemporalSession(request,TransferenciasConstants.
				 * LISTA_CODIGOSARCHIVORECEPTORES_KEY, ltArchivos); } } else
				 */if (prevision.isEntreArchivos()) {
					if (appUser.getCustodyArchiveList() == null
							|| appUser.getCustodyArchiveList().size() == 0)
						throw new UsuariosNotAllowedException(
								UsuariosNotAllowedException.USUARIO_NO_PERTENECE_A_NINGUN_ARCHIVO);

					if (prevision.getArchivoRemitenteCanBeChanged()) {
						// GestionArchivosBI serviceArchivos =
						// services.lookupGestionArchivosBI();
						List ltArchivos = null;
						if (appUser.getCustodyArchiveList() != null
								&& appUser.getCustodyArchiveList().size() > 0)
							ltArchivos = serviceArchivos.getArchivosXId(appUser
									.getCustodyArchiveList().toArray());
						setInTemporalSession(
								request,
								TransferenciasConstants.LISTA_CODIGOSARCHIVO_KEY,
								ltArchivos);
					}
					if (prevision.getArchivoReceptorCanBeChanged()) {
						// GestionArchivosBI serviceArchivos =
						// services.lookupGestionArchivosBI();
						List ltArchivos = serviceArchivos
								.getArchivosXId(appUser.getCustodyArchiveList()
										.toArray());
						if ((ltArchivos != null) && (!ltArchivos.isEmpty())) {
							if (ltArchivos.size() == 1) {
								// Si hay un solo archivo remitente obtener sus
								// receptores
								ArchivoVO archivo = (ArchivoVO) ltArchivos
										.get(0);

								// Si hay un solo archivo remitente obtener sus
								// receptores
								List ltArchivosReceptores = serviceArchivos
										.getArchivosReceptores(archivo.getId());
								if (ltArchivosReceptores == null
										|| ltArchivosReceptores.size() == 0)
									// Si no hay ninguno error al no haber
									// receptor posible
									throw new ActionNotAllowedException(
											null,
											ArchivoErrorCodes.ARCHIVOS_DESTINO_NO_DEFINIDOS,
											ArchivoModules.TRANSFERENCIAS_MODULE);
								else {
									// Obtener el primer receptor y establecerlo
									// en el formulario
									ArchivoVO archivoReceptor = (ArchivoVO) ltArchivosReceptores
											.get(0);
									previsionForm
											.setIdarchivoreceptor(archivoReceptor
													.getId());
									setInTemporalSession(
											request,
											TransferenciasConstants.LISTA_CODIGOSARCHIVORECEPTORES_KEY,
											ltArchivosReceptores);
								}
							} else {
								// Si hay varios archivos remitentes obtener
								// todos sus receptores
								List ltArchivosReceptores = serviceArchivos
										.getArchivosReceptores(ltArchivos);
								if (ltArchivosReceptores == null
										|| ltArchivosReceptores.size() == 0)
									// Si no hay ninguno error al no haber
									// receptor posible
									throw new ActionNotAllowedException(
											null,
											ArchivoErrorCodes.ARCHIVOS_DESTINO_NO_DEFINIDOS,
											ArchivoModules.TRANSFERENCIAS_MODULE);
								else {
									// Obtener el primer archivo remitente
									ArchivoVO archivo = (ArchivoVO) ltArchivos
											.get(0);

									// Obtener los receptores del primer
									// remitente
									ltArchivosReceptores = serviceArchivos
											.getArchivosReceptores(archivo
													.getId());
									if ((ltArchivosReceptores != null)
											&& (!ltArchivosReceptores.isEmpty())) {
										// Establecer en el formulario el primer
										// receptor
										ArchivoVO archivoReceptor = (ArchivoVO) ltArchivosReceptores
												.get(0);
										previsionForm
												.setIdarchivoreceptor(archivoReceptor
														.getId());
									}

									// Guardar en el formulario de lista de
									// receptores
									setInTemporalSession(
											request,
											TransferenciasConstants.LISTA_CODIGOSARCHIVORECEPTORES_KEY,
											ltArchivosReceptores);
								}
							}
						} else {
							setInTemporalSession(
									request,
									TransferenciasConstants.LISTA_CODIGOSARCHIVORECEPTORES_KEY,
									new ArrayList());
						}
					}
				}

				PrevisionPO previsionPO = (PrevisionPO) PrevisionToPO
						.getInstance(services).transform(prevision);
				setInTemporalSession(request,
						TransferenciasConstants.PREVISION_KEY, previsionPO);
				saveCurrentInvocation(
						KeysClientsInvocations.TRANSFERENCIAS_NUEVA_PREVISION,
						request);
				setReturnActionFordward(request,
						mapping.findForward("nueva_prevision"));
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mapping.findForward("seleccionar_tipoprevision"));
			}
		} catch (InvalidTipoTransferenciaException ite) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ERROR_TIPO_PREVISION_OBLIGATORIO));
			setReturnActionFordward(request,
					mapping.findForward("seleccionar_tipoprevision"));
		}
	}

	protected void crearExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		PrevisionForm frm = (PrevisionForm) form;
		// recojo prevision de session
		PrevisionVO prevision = (PrevisionVO) getFromTemporalSession(request,
				TransferenciasConstants.PREVISION_KEY);
		ActionErrors errors = validate(request, frm);
		if (errors == null) {
			try {
				/* int tipoOperacion = */PrevisionVO.getTipoOperacion(
						prevision.getTipotransferencia(),
						prevision.getTipoprevision());
				// actualizo vo con datos del form
				prevision.setObservaciones(frm.getObservaciones());
				if (!StringUtils.isEmpty(frm.getIdfondo())) {
					prevision.setIdfondodestino(frm.getIdfondo());

					/*
					 * ElementoCuadroClasificacionVO fondo =
					 * getGestionCuadroClasificacionBI(
					 * request).getElementoCuadroClasificacion(
					 * frm.getIdfondo());
					 * 
					 * 
					 * if (fondo != null)
					 * prevision.setIdarchivoreceptor(fondo.getIdArchivo());
					 */
				}

				// Si es una transferencia entre archivos meter
				// idarchivoremitente y receptor
				if (prevision.getTipotransferencia() == TipoTransferencia.ENTRE_ARCHIVOS
						.getIdentificador()) {
					prevision
							.setIdarchivoremitente(frm.getIdarchivoremitente());
					prevision.setIdarchivoreceptor(frm.getIdarchivoreceptor());
				} else {
					AppUser user = getAppUser(request);
					String idarchivoreceptor = null;
					CAOrganoVO organization = user.getOrganization();
					if (organization != null)
						idarchivoreceptor = organization.getIdArchivoReceptor();
					prevision.setIdarchivoreceptor(idarchivoreceptor);
				}

				prevision.setNumuinstalacion(TypeConverter.toInt(
						frm.getNumuinstalacion(), 0));
				previsionesService.insertPrevision(prevision);
				popLastInvocation(request);
				ActionRedirect vistaPrevision = new ActionRedirect(
						mapping.findForward("vista_prevision"), true);
				vistaPrevision.addParameter("idprevision", prevision.getId());
				setReturnActionFordward(request, vistaPrevision);
			} catch (InvalidTipoTransferenciaException ite) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.ERROR_TIPO_PREVISION_OBLIGATORIO));
				setReturnActionFordward(request,
						mapping.findForward("seleccionar_tipoprevision"));
			} catch (ActionNotAllowedException ex) {
				guardarError(request, ex);
				setReturnActionFordward(request,
						mapping.findForward("nueva_prevision"));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("nueva_prevision"));
		}
	}

	protected void verprevisionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		String pIdPrevision = ((PrevisionForm) form).getIdprevision();
		PrevisionPO prevision = (PrevisionPO) PrevisionToPO.getInstance(
				services).transform(
				previsionesService.abrirPrevision(pIdPrevision));
		setReturnActionFordward(request, mapping.findForward("ver_prevision"));
		ClientInvocation thisInvocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_VER_PREVISION, request);
		thisInvocation.setAsReturnPoint(true);
		setInTemporalSession(request, TransferenciasConstants.PREVISION_KEY,
				prevision);
	}

	private PrevisionPO verprevisionConsultaComunLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		String pIdPrevision = ((PrevisionForm) form).getIdprevision();
		PrevisionPO prevision = (PrevisionPO) PrevisionToPO.getInstance(
				services).transform(
				previsionesService.abrirPrevision(pIdPrevision));
		setReturnActionFordward(request, mapping.findForward("ver_prevision"));
		return prevision;
	}

	protected void verprevisionConsultaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PrevisionPO prevision = verprevisionConsultaComunLogic(mapping, form,
				request, response);
		ClientInvocation thisInvocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_VER_PREVISION, request);
		thisInvocation.setAsReturnPoint(true);
		setInTemporalSession(request, TransferenciasConstants.PREVISION_KEY,
				prevision);
	}

	protected void verprevisionConsultaCalendarioPrevisionesExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrevisionPO prevision = verprevisionConsultaComunLogic(mapping, form,
				request, response);
		ClientInvocation thisInvocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_VER_PREVISION_CALENDARIO_PREVISIONES,
				request);
		thisInvocation.setAsReturnPoint(true);
		setInTemporalSession(request, TransferenciasConstants.PREVISION_KEY,
				prevision);
	}

	protected void modificarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrevisionVO prevision = (PrevisionVO) getFromTemporalSession(request,
				TransferenciasConstants.PREVISION_KEY);
		((PrevisionForm) form).setFechainitrans(DateUtils.formatDate(prevision
				.getFechainitrans()));
		((PrevisionForm) form).setFechafintrans(DateUtils.formatDate(prevision
				.getFechafintrans()));
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_MODIFICAR_FECHAS_PREVISION,
				request);
		setReturnActionFordward(request,
				mapping.findForward("modificar_prevision"));
	}

	private boolean validarFecha(String fecha, boolean isFechaIni,
			HttpServletRequest request) {

		if (StringUtils.isEmpty(fecha) && isFechaIni)
			return true;

		// comprobar fecha sea fecha correcta
		String cadFecha = isFechaIni ? Constants.ETIQUETA_TRANSFERENCIAS_FECHA_INICIO
				: Constants.ETIQUETA_TRANSFERENCIAS_FECHA_FIN;
		cadFecha = Messages.getString(cadFecha, request.getLocale());
		if (!DateUtils.isDate(fecha)) {
			obtenerErrores(request, true).add(Constants.ERROR_DATE,
					new ActionError(Constants.ERROR_DATE, cadFecha));
			return false;
		}

		Date dFecha = DateUtils.getDate(fecha);

		// comprobar fecha mayor o igual a hoy
		if (DateUtils.isFechaMenor(dFecha, DateUtils.getFechaActual())) {
			obtenerErrores(request, true).add(
					Constants.NUEVA_FECHA_ANTERIOR_A_HOY,
					new ActionError(Constants.FECHA_ANTERIOR_A_HOY, cadFecha));
			return false;
		}
		return true;
	}

	private boolean validarFechas(String fechaIni, String fechaFin,
			PrevisionVO prevision, HttpServletRequest request) {
		// validar ambas fechas
		// solo si es diferente a la original
		boolean fechasCorrectas = true;
		boolean fechasModificadas = false;
		if (fechaIni != null
				&& !prevision.getFechainitrans().equals(
						DateUtils.getDate(fechaIni))) {
			fechasCorrectas &= validarFecha(fechaIni, true, request);
			fechasModificadas = true;
		}
		if (!prevision.getFechafintrans().equals(DateUtils.getDate(fechaFin))) {
			fechasCorrectas &= validarFecha(fechaFin, false, request);
			fechasModificadas = true;
		}

		if (!fechasCorrectas)
			return false;
		if (!fechasModificadas)
			return true;

		Date dFechaIniTrans = StringUtils.isEmpty(fechaIni) ? prevision
				.getFechainitrans() : DateUtils.getDate(fechaIni);
		Date dFechaFinTrans = DateUtils.getDate(fechaFin);
		// comprobar fecha ini < fin
		if (DateUtils.isFechaMenorOIgual(dFechaFinTrans, dFechaIniTrans)) {
			obtenerErrores(request, true)
					.add(Constants.ERROR_FECHA_INICIAL_MAYOR_O_IGUAL_FECHA_FINAL,
							new ActionError(
									Constants.ERROR_FECHA_INICIAL_MAYOR_O_IGUAL_FECHA_FINAL));
			return false;
		}

		if (!prevision.getFechafintrans().equals(DateUtils.getDate(fechaFin))) {
			if (DateUtils.isFechaMenorOIgual(dFechaFinTrans,
					prevision.getFechafintrans())) {
				obtenerErrores(request, true)
						.add(TransferenciasConstants.ERROR_NUEVA_FECHA_FIN_PREVISION_MENOR_ANTERIOR,
								new ActionError(
										TransferenciasConstants.ERROR_NUEVA_FECHA_FIN_PREVISION_MENOR_ANTERIOR,
										DateUtils.formatDate(prevision
												.getFechafintrans())));
				return false;
			}
		}
		return true;
	}

	protected void guardarmodificacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrevisionVO prevision = (PrevisionVO) getFromTemporalSession(request,
				TransferenciasConstants.PREVISION_KEY);
		PrevisionForm frm = (PrevisionForm) form;

		validarFechas(frm.getFechainitrans(), frm.getFechafintrans(),
				prevision, request);

		if (obtenerErrores(request, false) == null) {
			try {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionPrevisionesBI previsionesService = services
						.lookupGestionPrevisionesBI();
				Date dFechaIniTrans = prevision.getFechainitrans();
				if (!StringUtils.isEmpty(frm.getFechainitrans())) {
					dFechaIniTrans = DateUtils.getDate(frm.getFechainitrans());
				}
				previsionesService.modificarFechasCalendario(prevision.getId(),
						dFechaIniTrans,
						DateUtils.getDate(frm.getFechafintrans()));
				goBackExecuteLogic(mapping, form, request, response);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
			}
		} else {
			setReturnActionFordward(request,
					mapping.findForward("modificar_prevision"));
		}
	}

	protected void edicionExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionBI = services
				.lookupGestionPrevisionesBI();
		PrevisionVO prevision = previsionBI.abrirPrevision(request
				.getParameter("idPrevision"));
		((PrevisionForm) form).setIdfondo(prevision.getIdfondodestino());
		((PrevisionForm) form).setNumuinstalacion(Integer.toString(prevision
				.getNumuinstalacion()));
		((PrevisionForm) form).setIdarchivoremitente(prevision
				.getIdarchivoremitente());
		((PrevisionForm) form).setIdarchivoreceptor(prevision
				.getIdarchivoreceptor());
		((PrevisionForm) form).setObservaciones(prevision.getObservaciones());
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_EDICION_PREVISION,
				request);
		if (prevision.getFondoCanBeChanged()) {
			GestionFondosBI serviceFondos = services.lookupGestionFondosBI();
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_CODIGOSFONDO_KEY,
					serviceFondos.getFondosVigentes());
		}

		if (prevision.isExtraordinaria()) {
			if (prevision.getArchivoReceptorCanBeChanged()) {
				GestionArchivosBI serviceArchivos = services
						.lookupGestionArchivosBI();
				List ltArchivos = serviceArchivos.getArchivosXId(appUser
						.getCustodyArchiveList().toArray());
				setInTemporalSession(
						request,
						TransferenciasConstants.LISTA_CODIGOSARCHIVORECEPTORES_KEY,
						ltArchivos);
			}
		} else if (prevision.isEntreArchivos()) {
			if (prevision.getArchivoRemitenteCanBeChanged()) {
				GestionArchivosBI serviceArchivos = services
						.lookupGestionArchivosBI();
				List ltArchivos = serviceArchivos.getArchivosXId(appUser
						.getCustodyArchiveList().toArray());
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_CODIGOSARCHIVO_KEY,
						ltArchivos);
			}
			if (prevision.getArchivoReceptorCanBeChanged()) {

				GestionArchivosBI serviceArchivos = services
						.lookupGestionArchivosBI();
				List ltArchivos = serviceArchivos.getArchivosXId(appUser
						.getCustodyArchiveList().toArray());
				if ((ltArchivos != null) && (!ltArchivos.isEmpty())) {
					ArchivoVO archivo = (ArchivoVO) ltArchivos.get(0);

					List ltArchivosReceptores = serviceArchivos
							.getArchivosReceptores(archivo.getId());
					setInTemporalSession(
							request,
							TransferenciasConstants.LISTA_CODIGOSARCHIVORECEPTORES_KEY,
							ltArchivosReceptores);
				} else {
					setInTemporalSession(
							request,
							TransferenciasConstants.LISTA_CODIGOSARCHIVORECEPTORES_KEY,
							new ArrayList());
				}
			}
		}

		setReturnActionFordward(request,
				mapping.findForward("edicion_prevision"));
	}

	protected void guardaredicionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrevisionForm frm = (PrevisionForm) form;
		ActionErrors errors = validate(request, frm);
		if (errors == null) {
			try {
				PrevisionVO prevision = (PrevisionVO) getFromTemporalSession(
						request, TransferenciasConstants.PREVISION_KEY);
				if (!prevision.isDetallada())
					prevision.setNumuinstalacion(TypeConverter.toInt(
							frm.getNumuinstalacion(), 0));

				prevision.setIdfondodestino(frm.getIdfondo());
				prevision.setObservaciones(frm.getObservaciones());
				prevision.setIdarchivoremitente(frm.getIdarchivoremitente());
				prevision.setIdarchivoreceptor(frm.getIdarchivoreceptor());
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionPrevisionesBI previsionesService = services
						.lookupGestionPrevisionesBI();
				previsionesService.actualizarPrevision(prevision);
				goBackExecuteLogic(mapping, form, request, response);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mapping.findForward("edicion_prevision"));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("edicion_prevision"));
		}
	}

	protected void enviarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrevisionForm frm = (PrevisionForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		try {
			previsionesService.enviarPrevision(frm.getIdprevision());
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	protected void listadooficinaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// String idorgremitente = appUser.getOrganization().getIdOrg();
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		// Se obtienen las previsiones del usuario que no esten aceptadas
		List previsiones = previsionesService
				.getPrevisionesEnElaboracion(appUser.getId());

		CollectionUtils.transform(previsiones,
				new TransformerPrevisionVOToPrevisionPO(services));

		request.setAttribute(TransferenciasConstants.LISTA_PREVISIONES_KEY,
				previsiones);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_PREVISIONES_EN_ELABORACION,
				request);
		setReturnActionFordward(request,
				mapping.findForward("listado_prevision_oficina"));
	}

	protected void listadoPrevisionesAceptadasORechazadasExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		// String idorgremitente = appUser.getOrganization().getIdOrg();
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		// Se obtienen las previsiones del usuario que no esten aceptadas
		List previsiones = previsionesService
				.getPrevisionesAceptadasRechazadas(appUser.getId());

		CollectionUtils.transform(previsiones,
				new TransformerPrevisionVOToPrevisionPO(services));

		request.setAttribute(TransferenciasConstants.LISTA_PREVISIONES_KEY,
				previsiones);

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_PREVISIONES_EN_ELABORACION,
				request);

		request.setAttribute(
				TransferenciasConstants.SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS,
				new Boolean(true));

		setReturnActionFordward(request,
				mapping.findForward("listado_prevision_oficina"));
	}

	protected void listadoarchivoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		try {
			Collection previsiones = previsionesService
					.getPrevisionesAGestionar();
			CollectionUtils.transform(previsiones,
					PrevisionToPO.getInstance(services));

			CollectionUtils.transform(previsiones,
					new TransformerPrevisionVOToPrevisionPO(services));

			request.setAttribute(TransferenciasConstants.LISTA_PREVISIONES_KEY,
					previsiones);

			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_PREVISIONES_EN_GESTION,
					request);
			setReturnActionFordward(request,
					mapping.findForward("listado_prevision_archivo"));
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	private void eliminarPrevisiones(ServiceClient caller, String[] previsiones)
			throws ActionNotAllowedException {
		ServiceRepository services = ServiceRepository.getInstance(caller);
		GestionPrevisionesBI bPrevisiones = services
				.lookupGestionPrevisionesBI();
		bPrevisiones.eliminarPrevisiones(previsiones);
	}

	public void eliminarprevisionesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrevisionForm frm = (PrevisionForm) form;
		try {
			eliminarPrevisiones(ServiceClient.create(getAppUser(request)),
					frm.getPrevisionesseleccionadas());
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	public void eliminarprevisionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrevisionForm frm = (PrevisionForm) form;
		try {
			String[] prevision = { frm.getIdprevision() };
			eliminarPrevisiones(ServiceClient.create(getAppUser(request)),
					prevision);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goBackExecuteLogic(mapping, form, request, response);
	}

	protected void homePrevisionesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();
		// Se obtienen las previsiones del usuario que no esten aceptadas
		List previsionesEnElaboracion = previsionesService
				.getPrevisionesEnElaboracion(appUser.getId());

		CollectionUtils.transform(previsionesEnElaboracion,
				PrevisionToPO.getInstance(services));

		if (previsionesEnElaboracion.size() > 5)
			previsionesEnElaboracion = previsionesEnElaboracion.subList(0, 5);

		try {
			Collection previsionesAGestionar = previsionesService
					.getPrevisionesAGestionar();
			if (previsionesAGestionar.size() > 5)
				previsionesAGestionar = new ArrayList(previsionesAGestionar)
						.subList(0, 5);

			CollectionUtils.transform(previsionesAGestionar,
					PrevisionToPO.getInstance(services));
			request.setAttribute(
					TransferenciasConstants.LISTA_PREVISIONES_A_GESTIONAR_KEY,
					previsionesAGestionar);
			request.setAttribute(
					TransferenciasConstants.LISTA_PREVISIONES_EN_ELABORACION_KEY,
					previsionesEnElaboracion);

			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_GESTION_PREVISIONES,
					request);
			setReturnActionFordward(request,
					mapping.findForward("home_previsiones"));
		} catch (ActionNotAllowedException anae) {
			request.setAttribute(TransferenciasConstants.LISTA_PREVISIONES_KEY,
					previsionesEnElaboracion);
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_PREVISIONES_EN_ELABORACION,
					request);
			setReturnActionFordward(request,
					mapping.findForward("listado_prevision_oficina"));
		}
	}

	public ActionErrors validate(HttpServletRequest request, PrevisionForm form) {
		ActionErrors ret = new ActionErrors();
		int tipoPrevision = form.getTipoprevision();
		int tipoTransferencia = form.getTipotransferencia();
		if (tipoPrevision == PrevisionVO.PREVISION_NODETALLADA) {
			if (GenericValidator.isBlankOrNull(form.getNumuinstalacion()))
				ret.add(Constants.ERROR_INT,
						new ActionError(
								Constants.ERROR_INT,
								Messages.getString(
										Constants.ETIQUETA_NUM_UNIDADES_INSTALACION,
										request.getLocale())));
			else if (!GenericValidator.isInt(form.getNumuinstalacion()))
				ret.add(Constants.ERROR_INT,
						new ActionError(
								Constants.ERROR_INT,
								Messages.getString(
										Constants.ETIQUETA_NUM_UNIDADES_INSTALACION,
										request.getLocale())));
			else if (Integer.parseInt(form.getNumuinstalacion()) <= 0)
				ret.add(Constants.ERROR_NEGATIVE_NUMBER,
						new ActionError(
								Constants.ERROR_INT_MAYOR_CERO,
								Messages.getString(
										Constants.ETIQUETA_NUM_UNIDADES_INSTALACION,
										request.getLocale())));
		}

		if (tipoTransferencia == TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador()) {
			// Comprobar que se haya seleccionado archivo remitente
			if (GenericValidator.isBlankOrNull(form.getIdarchivoremitente())) {
				ret.add(Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(
										Constants.ETIQUETA_ARCHIVO_REMITENTE,
										request.getLocale())));
			}
			// Comprobar que se haya seleccionado archivo receptor
			if (GenericValidator.isBlankOrNull(form.getIdarchivoreceptor())) {
				ret.add(Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_ARCHIVO_RECEPTOR,
										request.getLocale())));
			}
			// Comprobar que se haya seleccionado fondo destino
			if (GenericValidator.isBlankOrNull(form.getIdfondo())) {
				ret.add(Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_FONDO_DESTINO,
										request.getLocale())));
			}
		} else {
			if (GenericValidator.isBlankOrNull(form.getIdfondo())
					&& (tipoTransferencia != TipoTransferencia.ORDINARIA
							.getIdentificador())
					&& (tipoTransferencia != TipoTransferencia.ENTRE_ARCHIVOS
							.getIdentificador())) {
				ret.add(Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_FONDO,
										request.getLocale())));
			}
		}

		return ret.size() > 0 ? ret : null;
	}

	public class TransformerPrevisionVOToPrevisionPO implements Transformer {
		ServiceRepository services = null;

		TransformerPrevisionVOToPrevisionPO(ServiceRepository services) {
			this.services = services;
		}

		public Object transform(Object previsionVO) {
			return new PrevisionPO((PrevisionVO) previsionVO, services);
		}
	}

}