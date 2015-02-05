package transferencias.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.Errors;
import transferencias.TransferenciasConstants;
import transferencias.forms.RecepcionRelacionesForm;
import transferencias.vos.PrevisionVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocElectronicaVO;
import transferencias.vos.UnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionReeaVO;
import util.ErrorsTag;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ConfiguracionTransferencias;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.StrutsExceptionFormatter;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.ListUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.UDocEnUiDepositoVO;

/**
 * Action con la logica a ejecutar para el tratamiento de las relaciones
 * enviadas
 *
 */
public class RecepcionRelacionesAction extends BaseAction {

	public void recibirrelacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RecepcionRelacionesForm frm = (RecepcionRelacionesForm) form;

		AppUser appUser = getAppUser(request);
		ServiceClient serviceClient = ServiceClient.create(appUser);
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);

		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();
		RelacionEntregaVO relacionVO = relacionesBI.getRelacionXIdRelacion(frm
				.getCodigo());
		// lista de usuario gestores de archivo
		List usuariosGestoresDeArchivo = relacionesBI
				.getPosiblesGestores(relacionVO);

		// Añadir los superusuarios
		GestionControlUsuariosBI controlUsuariosBI = (GestionControlUsuariosBI) services
				.lookupGestionControlUsuariosBI();
		String[] permisosSuperUser = { AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };
		if (usuariosGestoresDeArchivo == null)
			usuariosGestoresDeArchivo = new ArrayList();
		util.CollectionUtils.addList(usuariosGestoresDeArchivo,
				controlUsuariosBI.getUsuariosConPermisos(permisosSuperUser));

		CollectionUtils.transform(usuariosGestoresDeArchivo,
				UsuarioToGestorPO.getInstance());
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
				.getInstance(services).transform(relacionVO);

		frm.setFormaDocumental(relacionPO.getTipoDocumental());
		frm.setIdFormato(relacionPO.getIdFormatoDestino());

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		// Obtener las ubicaciones cuyo archivo solo se sacan los Arhivos a los
		// que pertenece el usuario.
		Collection edificios = depositoBI
				.getUbicacionesXIdsArchivo(new String[] { relacionVO
						.getIdarchivoreceptor() });

		boolean noExisteEspacioDisponible = false;
		List formatosDisponiblesParaCambiar = null;
		if (!relacionVO.isSinDocsFisicos()) {
			if (relacionPO.isSignaturacionAsociadaHuecoYSignaturaSolicitable()) {
				try {
					// Comprobar si alguno de los huecos asociados a la
					// signatura ha
					// sido reutilizado
					relacionesBI.checkHuecosAsociadosRelacionLibres(
							relacionVO.getId(), false);
				} catch (ActionNotAllowedException e) {
					noExisteEspacioDisponible = true;
					ActionErrors actionErrors = new ActionErrors();
					actionErrors.add(ActionErrors.GLOBAL_ERROR,
							StrutsExceptionFormatter.getInstance()
									.formatException(e));
					ErrorsTag.saveErrors(request, actionErrors);
				}
				formatosDisponiblesParaCambiar = new ArrayList();
				formatosDisponiblesParaCambiar.add(relacionPO.getFormato());
			} else {
				CollectionUtils.transform(edificios,
						TransformerToEspacioEnUbicacionPO.getInstance(services,
								relacionPO.getIdFormatoDestino()));
				int nUnidadesInstalacion = relacionPO.getNumUIs();
				if (edificios != null) {

					noExisteEspacioDisponible = true;

					for (Iterator i = edificios.iterator(); i.hasNext()
							&& noExisteEspacioDisponible;) {
						EspacioEnUbicacionPO ubicacion = (EspacioEnUbicacionPO) i
								.next();
						if (ubicacion.getNumeroHuecosDisponibles() >= nUnidadesInstalacion) {
							noExisteEspacioDisponible = false;
						}
					}
				}
				formatosDisponiblesParaCambiar = relacionPO.getFormatoDestino()
						.isRegular() ? depositoBI.getFormatosRegulares()
						: depositoBI.getFormatosIrregulares();
			}

			setInTemporalSession(request,
					TransferenciasConstants.NO_EXISTE_ESPACIO_DISPONIBLE,
					new Boolean(noExisteEspacioDisponible));
		}
		ConfiguracionTransferencias config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionTransferencias();
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		List formasDocumentales = descripcionBI.getValoresValidacion(config
				.getIdTablaValidacionFormaDocumental());

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_RECIBIR_RELACION, request);
		invocation.setAsReturnPoint(true);
		setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
				relacionPO);
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_EDIFICIOS_KEY, edificios);
		setInTemporalSession(request,
				TransferenciasConstants.LISTADO_USUARIOS_KEY,
				usuariosGestoresDeArchivo);
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_FORMAS_DOCUMENTALES_KEY,
				formasDocumentales);
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_FORMATOS_KEY,
				formatosDisponiblesParaCambiar);
		setReturnActionFordward(request,
				mapping.findForward("recepcion_relacion"));
	}

	public void seleccionarFormatoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RecepcionRelacionesForm frm = (RecepcionRelacionesForm) form;
		ServiceRepository services = getServiceRepository(request);

		RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		// Obtener las ubicaciones cuyo archivo solo se sacan los Arhivos a los
		// que pertenece el usuario.
		Collection edificios = depositoBI
				.getUbicacionesXIdsArchivo(new String[] { relacion
						.getIdarchivoreceptor() });

		CollectionUtils.transform(
				edificios,
				TransformerToEspacioEnUbicacionPO.getInstance(services,
						frm.getIdFormato()));

		int nUnidadesInstalacion = relacion.getNumeroUnidadesInstalacion();
		boolean noExisteEspacioDisponible = false;
		for (Iterator i = edificios.iterator(); i.hasNext();) {
			EspacioEnUbicacionPO ubicacion = (EspacioEnUbicacionPO) i.next();
			if (ubicacion.getNumeroHuecosDisponibles() >= nUnidadesInstalacion)
				noExisteEspacioDisponible = false;
		}

		// Cuando se cambia de Formato, se deselecciona el depósito y el
		// indicador de reserva.
		// Ya que la ubicación seleccionada con anterioridad, podría no ser
		// válida.
		frm.setSolicitarreserva(false);

		setInTemporalSession(request,
				TransferenciasConstants.LISTA_EDIFICIOS_KEY, edificios);
		setInTemporalSession(request,
				TransferenciasConstants.NO_EXISTE_ESPACIO_DISPONIBLE,
				new Boolean(noExisteEspacioDisponible));
		setReturnActionFordward(request,
				mapping.findForward("recepcion_relacion"));
	}

	protected void relacionesUbicacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RecepcionRelacionesForm frm = (RecepcionRelacionesForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		String idUbicacion = request.getParameter("ubicacion");
		List relacionesUbicacion = relacionBI.getRelacionesPendientesDeUbicar(
				idUbicacion, frm.getIdFormato());
		CollectionUtils.transform(relacionesUbicacion,
				RelacionToPO.getInstance(services));
		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				relacionesUbicacion);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_RECIBIR_RELACION, request);
		setReturnActionFordward(request,
				mapping.findForward("recepcion_relacion"));
	}

	protected void seleccionarUbicacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List ubicaciones = (List) getFromTemporalSession(request,
				TransferenciasConstants.LISTA_EDIFICIOS_KEY);
		final String idUbicacion = request.getParameter("ubicacion");
		EspacioEnUbicacionPO ubicacionSeleccionada = (EspacioEnUbicacionPO) CollectionUtils
				.find(ubicaciones, new Predicate() {
					public boolean evaluate(Object obj) {
						return idUbicacion.equals(((EspacioEnUbicacionPO) obj)
								.getId());
					}
				});
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_DEPOSITOS_KEY,
				ubicacionSeleccionada.getDepositosUbicacion());

		String seleccionado = ubicacionSeleccionada.getId()
				+ Constants.DELIMITER_IDS
				+ ubicacionSeleccionada.getTipoElemento();

		setInTemporalSession(request, TransferenciasConstants.ID_SELECCIONADO,
				seleccionado);

		setReturnActionFordward(request,
				mapping.findForward("recepcion_relacion"));
	}

	public void guardarrecibirrelacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RecepcionRelacionesForm frm = (RecepcionRelacionesForm) form;
		boolean hizoReserva = frm.isSolicitarreserva();
		String relacionId = ((RecepcionRelacionesForm) form).getCodigo();
		RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		String idUserGestorDeLaRelacion = ((RecepcionRelacionesForm) form)
				.getIdusuarioseleccionado();
		try {
			ActionErrors errors = new ActionErrors();
			if (StringUtils.isBlank(frm.getIdusuarioseleccionado()))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						Errors.ERROR_GESTOR_ARCHIVO_NO_SELECCIONADO));

			if (!Constants.TRUE_STRING.equals(frm.getRelacionSinDocsFisicos())) {
				if (StringUtils.isBlank(frm.getIdubicacionseleccionada())
						&& (!relacion
								.isSignaturacionAsociadaHuecoYSignaturaSolicitable()))
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							Errors.ERROR_NO_UBICACION_SELECCIONADA));
				if (frm.isSolicitarreserva()) {
					if (StringUtils.isBlank(frm
							.getIdElementoSeleccionadoReserva()))
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								Errors.ERROR_NO_DEPOSITO_SELECCIONADO));
				}
			}
			if (errors.size() == 0) {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionRelacionesEntregaBI relacionesBI = services
						.lookupGestionRelacionesBI();

				String idDepositoReserva = null;
				String idTipoDepostoReserva = null;
				String idElementoSeleccionadoReserva = null;
				String idTipoElementoSeleccionadoReserva = null;

				if (hizoReserva) {
					idDepositoReserva = frm.getIdDepositoReserva();
					idTipoDepostoReserva = frm.getIdTipoDepositoReserva();
					idElementoSeleccionadoReserva = frm
							.getIdElementoSeleccionadoReserva();
					idTipoElementoSeleccionadoReserva = frm
							.getIdTipoElementoSeleccionadoReserva();
				}

				relacionesBI.recibirRelacionEntrega(relacionId,
						idUserGestorDeLaRelacion, frm.getIdFormato(),
						frm.getFormaDocumental(),
						frm.getIdubicacionseleccionada(), idDepositoReserva,
						idTipoDepostoReserva, idElementoSeleccionadoReserva,
						idTipoElementoSeleccionadoReserva);

				goBackExecuteLogic(mapping, form, request, response);
			} else {
				obtenerErrores(request, true).add(errors);
				goLastClientExecuteLogic(mapping, form, request, response);
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	public void enviarrelacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			ActionErrors errors = new ActionErrors();

			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionesBI = services
					.lookupGestionRelacionesBI();
			GestionPrevisionesBI previsionesBI = services
					.lookupGestionPrevisionesBI();

			RecepcionRelacionesForm frm = (RecepcionRelacionesForm) form;

			RelacionEntregaVO relacionVO = relacionesBI
					.getRelacionXIdRelacion(frm.getCodigo());

			if (relacionVO != null) {
				PrevisionVO previsionVO = previsionesBI.getPrevision(relacionVO
						.getIdprevision());

				if (previsionVO != null) {
					Date today = DateUtils.getFechaActualSinHora();
					if (DateUtils.isFechaMenor(today,
							previsionVO.getFechainitrans()))
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								Constants.ERROR_PREVISION_FUERA_PLAZO));
				}
			}

			if (relacionVO.isEntreArchivos()) {
				// Comprobar que todas los expedientes están completos, es decir
				// que los expedientes que están partidos, en la relación,
				// deberán ir todas las cajas que los contienen.

				// Obtener las lista de unidades de instalación
				List listaUinst = relacionesBI
						.getUnidadesInstalacionEntreArchivos(relacionVO.getId());
				List listaIdsUInst = new ArrayList();
				if (!ListUtils.isEmpty(listaUinst)) {
					Iterator it = listaUinst.iterator();
					while (it.hasNext()) {
						UnidadInstalacionReeaVO uInst = (UnidadInstalacionReeaVO) it
								.next();
						listaIdsUInst.add(uInst.getId());
					}
				}

				// Obtener la lista de unidades documentales
				List listaUdocs = relacionesBI
						.getUDocsXidRelacionEntreArchivos(relacionVO.getId());

				List listaIdsUdocs = new ArrayList();
				if (!ListUtils.isEmpty(listaUdocs)) {
					Iterator it = listaUdocs.iterator();
					while (it.hasNext()) {
						UnidadDocumentalVO udoc = (UnidadDocumentalVO) it
								.next();
						listaIdsUdocs.add(udoc.getId());

					}
				}

				List listaUDocsIncompletas = relacionesBI
						.getPartesUdocsNoIncluidasEnRelacion(
								relacionVO.getId(), listaIdsUdocs,
								listaIdsUInst);

				if (!ListUtils.isEmpty(listaUDocsIncompletas)) {
					Iterator it = listaUDocsIncompletas.iterator();

					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							Constants.ERROR_RELACION_CON_UNIDADES_INCOMPLETAS));
					while (it.hasNext()) {
						UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) it
								.next();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								Constants.ETIQUETA_SIGNATURA_UNIDAD_DOCUMENTAL,
								udoc.getSignaturaudoc()));
					}
				}

				if (errors.size() == 0) {
					if (!ListUtils.isEmpty(listaUdocs)) {
						Iterator it = listaUdocs.iterator();
						while (it.hasNext()) {
							UnidadDocumentalVO udoc = (UnidadDocumentalVO) it
									.next();
							if (udoc.getMarcasBloqueo() > 0) {
								errors.add(
										ActionErrors.GLOBAL_ERROR,
										new ActionError(
												Constants.ERROR_RELACIONES_ENTRE_ARCHIVOS_UNIDADES_BLOQUEADAS));
								break;
							}
						}
					}
				}

				if (errors.size() == 0) {
					List udocsSinDocumentosFisicos = relacionesBI
							.getUDocsElectronicasByIdRelacionEntreArchivos(relacionVO
									.getId());
					if (!ListUtils.isEmpty(udocsSinDocumentosFisicos)) {
						Iterator it = udocsSinDocumentosFisicos.iterator();
						while (it.hasNext()) {
							UDocElectronicaVO udoc = (UDocElectronicaVO) it
									.next();
							if (udoc.getMarcasBloqueo() > 0) {
								errors.add(
										ActionErrors.GLOBAL_ERROR,
										new ActionError(
												Constants.ERROR_RELACIONES_ENTRE_ARCHIVOS_UNIDADES_BLOQUEADAS));
								break;
							}
						}
					}
				}
			}

			if (errors.size() == 0) {
				relacionVO = relacionesBI
						.enviarRelacionEntrega(frm.getCodigo());

				RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
						.getInstance(services).transform(relacionVO);
				setInTemporalSession(request,
						TransferenciasConstants.RELACION_KEY, relacionPO);
			} else {
				obtenerErrores(request, true).add(errors);
				setReturnActionFordward(request,
						mappings.findForward("recepcion_relacion"));
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void informeenvioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request, mappings.findForward("informe_envio"));
	}

	public void initRechazarRelacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_RECHAZAR_RELACION,
				request);
		setReturnActionFordward(request,
				mappings.findForward("rechazo_relacion"));
	}

	public void rechazarRelacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// pasar al estado 8 (Rechazada)
		// insertar el motivo en el campo --- de la tabla ----
		RecepcionRelacionesForm frm = (RecepcionRelacionesForm) form;
		ActionErrors errors = new ActionErrors();
		setReturnActionFordward(request,
				mappings.findForward("rechazo_relacion"));

		if (StringUtils.isEmpty(frm.getMotivoRechazo())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_MOTIVO_DE_RECHAZO,
									request.getLocale())));
			obtenerErrores(request, true).add(errors);
			return;
		}

		RelacionEntregaVO relacionVO = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		relacionVO.setObservaciones(frm.getMotivoRechazo());

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();
		try {
			relacionesBI.rechazarRelacionEntrega(relacionVO);
		} catch (ActionNotAllowedException e) {
			// mensaje de error por problema de permisos
			guardarError(request, e);
		}

		// volvemos a la pantalla de detalle de relacion. No se puede realizar
		// ninguna operacion con la relacion, salvo la de editar.
		goBackExecuteLogic(mappings, form, request, response);
	}
}