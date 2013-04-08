package transferencias.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.EstadoCotejo;
import transferencias.TransferenciasConstants;
import transferencias.exceptions.ParteUnidadDocumentalNoEliminable;
import transferencias.exceptions.UnidadDocumentalNoPermitidaDivisionException;
import transferencias.exceptions.UnidadInstalacionConUnidadesDocumentalesException;
import transferencias.forms.AsignacionCajasForm;
import transferencias.model.EstadoREntrega;
import transferencias.vos.ReencajadoViewVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocEnUIReeaCRVO;
import transferencias.vos.UIReeaCRVO;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.StringUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.FormatoHuecoVO;

public class AsignacionCajasReencajadoAction extends BaseAction {

	private static final int SUBIR = 1;
	private static final int BAJAR = 2;
	private static final int DIVIDIR = 3;
	private static final int DESCRIPCION = 4;

	private void cargarVista(ActionMapping mappings,
			AsignacionCajasForm asignacionCajasForm, HttpServletRequest request) {
		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
		GestionRelacionesEntregaBI relacionBI = getGestionRelacionesBI(request);
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);

		UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) getFromTemporalSession(request,
				TransferenciasConstants.CAJA_KEY);
		String idRelacion = asignacionCajasForm.getIdRelacion();
		if (StringUtils.isEmpty(idRelacion)) {
			idRelacion = (String) request.getParameter("idRelacion");
		}
		RelacionEntregaVO relacionVO = relacionBI
				.getRelacionXIdRelacion(idRelacion);
		FormatoHuecoVO formato = depositoBI.getFormatoHueco(relacionVO
				.getIdFormatoDestino());

		InfoUDocReeacr infoUdocReeacr = new InfoUDocReeacr(relacionVO,
				relacionEACRBI);
		ReencajadoViewVO reencajadoViewVO = new ReencajadoViewVO(formato,
				infoUdocReeacr, uiReeaCrVO);
		request.setAttribute(
				TransferenciasConstants.REENCAJADO_VIEW_OBJECT_KEY,
				reencajadoViewVO);
		request.setAttribute(TransferenciasConstants.NUM_CAJA, new Integer(
				asignacionCajasForm.getNumeroCaja()));
	}

	public void nuevaCajaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		List udocsSinAsignar = relacionEACRBI
				.getUDocsSinUIReeaCR(formAsignacion.getIdRelacion());
		request.setAttribute(TransferenciasConstants.EXPEDIENTES_SIN_ASIGNAR,
				udocsSinAsignar);

		cargarVista(mappings, formAsignacion, request);
		saveCurrentInvocation(KeysClientsInvocations.TRANSFERENCIAS_NUEVA_CAJA,
				request);
		setReturnActionFordward(request, mappings.findForward("nueva_caja"));
	}

	public void crearCajaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;

		RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		if (!ArrayUtils.isEmpty(formAsignacion.getUdocSeleccionada())) {
			UIReeaCRVO uiReeaCrVO = relacionEACRBI.crearNuevaUICR(
					relacion.getId(), relacion.getIdFormatoRe(),
					formAsignacion.getUdocSeleccionada());
			setInTemporalSession(request, TransferenciasConstants.CAJA_KEY,
					uiReeaCrVO);
			updateRelacionRechazada(request);
			cargarVista(mappings, formAsignacion, request);
			goBackExecuteLogic(mappings, formAsignacion, request, response);
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					TransferenciasConstants.NECESARIO_SELECCIONAR_EXPEDIENTES_PARA_LA_CAJA,
					new ActionError(
							TransferenciasConstants.NECESARIO_SELECCIONAR_EXPEDIENTES_PARA_LA_CAJA));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void editarCajaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		UIReeaCRVO uiReeaCrVO = relacionEACRBI
				.getUIReencajadoById(formAsignacion.getIdUnidadInstalacion());

		setInTemporalSession(request, TransferenciasConstants.CAJA_KEY,
				uiReeaCrVO);
		cargarVista(mappings, formAsignacion, request);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_EDICION_CAJA, request);
		setReturnActionFordward(request, mappings.findForward("edicion_caja"));
	}

	public void editarDescripcionUIExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);

		if (StringUtils.isNotEmpty(formAsignacion.getIdUnidadInstalacion())) {
			UIReeaCRVO uiReeaCrVO = relacionEACRBI
					.getUIReencajadoById(formAsignacion
							.getIdUnidadInstalacion());

			setInTemporalSession(request, TransferenciasConstants.CAJA_KEY,
					uiReeaCrVO);
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_EDICION_DESCRIPCION_CONTENIDO_CAJA,
					request);
			setReturnActionFordward(request,
					mappings.findForward("edicion_descripcionUI"));
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Messages.getString(
							TransferenciasConstants.NO_ENCONTRADA_CAJA,
							request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, formAsignacion, request,
					response);
		}
	}

	public void guardarEditarDescripcionUIExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);

		relacionEACRBI.updateDescripcionUI(
				formAsignacion.getIdUnidadInstalacion(),
				formAsignacion.getDescripcionContenido());

		removeInTemporalSession(request, TransferenciasConstants.CAJA_KEY);
		updateRelacionRechazada(request);
		updateEstadoCotejoPendiente(formAsignacion.getIdUnidadInstalacion(),
				relacionEACRBI);
		cargarVista(mappings, formAsignacion, request);
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void editarDescripcionUdocExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);

		UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) getFromTemporalSession(request,
				TransferenciasConstants.CAJA_KEY);
		List listaUdocs = relacionEACRBI.getUDocsByUIReencajado(uiReeaCrVO
				.getId());

		ActionErrors errors = comprobacionesBasicas(request, formAsignacion,
				listaUdocs, DESCRIPCION);
		if (errors != null && !errors.isEmpty()) {
			obtenerErrores(request, true).add(errors);
			goLastClientExecuteLogic(mappings, formAsignacion, request,
					response);
		} else {
			String idUdocSeleccionada = formAsignacion.getUdocSeleccionada()[0];
			UDocEnUIReeaCRVO udoc = relacionEACRBI
					.getUdocEnUIById(idUdocSeleccionada);

			setInTemporalSession(request,
					TransferenciasConstants.UDOC_UI_SELECCIONADA, udoc);
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_EDICION_DESCRIPCION_PARTE,
					request);
			setReturnActionFordward(request,
					mappings.findForward("edicion_descripcion"));
		}
	}

	public void guardarEditarDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		UDocEnUIReeaCRVO udoc = (UDocEnUIReeaCRVO) getFromTemporalSession(
				request, TransferenciasConstants.UDOC_UI_SELECCIONADA);

		relacionEACRBI.updateDescripcionUdoc(udoc.getId(),
				formAsignacion.getDescripcionContenido());

		removeInTemporalSession(request,
				TransferenciasConstants.UDOC_UI_SELECCIONADA);
		updateRelacionRechazada(request);
		updateEstadoCotejoPendiente(udoc.getIdUIReeaCR(), relacionEACRBI);
		cargarVista(mappings, formAsignacion, request);
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void subirDentroDeCajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);

		UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) getFromTemporalSession(request,
				TransferenciasConstants.CAJA_KEY);
		List listaUdocs = relacionEACRBI.getUDocsByUIReencajado(uiReeaCrVO
				.getId());

		ActionErrors errors = comprobacionesBasicas(request, formAsignacion,
				listaUdocs, SUBIR);
		if (errors != null && !errors.isEmpty()) {
			obtenerErrores(request, true).add(errors);
		} else {
			relacionEACRBI.subirUdocsEnUIReeaCR(uiReeaCrVO.getIdRelEntrega(),
					uiReeaCrVO.getId(), formAsignacion.getUdocSeleccionada());
			updateRelacionRechazada(request);
			updateEstadoCotejoPendiente(uiReeaCrVO.getId(), relacionEACRBI);
		}

		cargarVista(mappings, formAsignacion, request);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void bajarDentroDeCajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);

		UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) getFromTemporalSession(request,
				TransferenciasConstants.CAJA_KEY);
		List listaUdocs = relacionEACRBI.getUDocsByUIReencajado(uiReeaCrVO
				.getId());

		ActionErrors errors = comprobacionesBasicas(request, formAsignacion,
				listaUdocs, BAJAR);
		if (errors != null && !errors.isEmpty()) {
			obtenerErrores(request, true).add(errors);
		} else {
			relacionEACRBI.bajarUdocsEnUI(uiReeaCrVO.getIdRelEntrega(),
					uiReeaCrVO.getId(), formAsignacion.getUdocSeleccionada());
			updateRelacionRechazada(request);
			updateEstadoCotejoPendiente(uiReeaCrVO.getId(), relacionEACRBI);
		}
		cargarVista(mappings, formAsignacion, request);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void sacarExpedienteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		if (!ArrayUtils.isEmpty(formAsignacion.getUdocSeleccionada())) {
			UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) getFromTemporalSession(
					request, TransferenciasConstants.CAJA_KEY);
			relacionEACRBI.extraerUDocsDeUIReeaCR(uiReeaCrVO.getIdRelEntrega(),
					uiReeaCrVO.getId(), formAsignacion.getUdocSeleccionada());
			updateRelacionRechazada(request);
			updateEstadoCotejoPendiente(uiReeaCrVO.getId(), relacionEACRBI);
		}
		cargarVista(mappings, formAsignacion, request);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void incorporarACajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		request.setAttribute(
				TransferenciasConstants.INCORPORANDO_UNIDAD_DOCUMENTAL,
				Boolean.TRUE);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_INCORPORAR_A_CAJA,
				request);
		cargarVista(mappings, formAsignacion, request);
		setReturnActionFordward(request, mappings.findForward("nueva_caja"));
	}

	public void guardarIncorporarACajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) getFromTemporalSession(request,
				TransferenciasConstants.CAJA_KEY);

		if (!ArrayUtils.isEmpty(formAsignacion.getUdocSeleccionada())) {
			relacionEACRBI.incorporarUdocsAUIReeaCR(
					uiReeaCrVO.getIdRelEntrega(), uiReeaCrVO.getId(),
					formAsignacion.getUdocSeleccionada());
			updateRelacionRechazada(request);
			updateEstadoCotejoPendiente(uiReeaCrVO.getId(), relacionEACRBI);
			formAsignacion.setUdocSeleccionada(null);
		}
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void dividirUdocExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);

		UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) getFromTemporalSession(request,
				TransferenciasConstants.CAJA_KEY);
		List listaUdocs = relacionEACRBI.getUDocsByUIReencajado(uiReeaCrVO
				.getId());

		ActionErrors errors = comprobacionesBasicas(request, formAsignacion,
				listaUdocs, DIVIDIR);
		if (errors != null && !errors.isEmpty()) {
			obtenerErrores(request, true).add(errors);
		} else {
			String idUdoc = formAsignacion.getUdocSeleccionada()[0];
			RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);
			try {
				relacionEACRBI.dividirUDocReeaCR(uiReeaCrVO.getIdRelEntrega(),
						uiReeaCrVO.getId(), idUdoc, relacion.getIdFormatoRe());
				updateRelacionRechazada(request);
				updateEstadoCotejoPendiente(uiReeaCrVO.getId(), relacionEACRBI);
			} catch (UnidadDocumentalNoPermitidaDivisionException e) {
				guardarError(request, e);
			}
		}
		cargarVista(mappings, formAsignacion, request);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void eliminarParteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		if (!ArrayUtils.isEmpty(formAsignacion.getUdocSeleccionada())) {
			UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) getFromTemporalSession(
					request, TransferenciasConstants.CAJA_KEY);
			try {
				relacionEACRBI.eliminarPartesUDocReeaCR(uiReeaCrVO.getId(),
						formAsignacion.getUdocSeleccionada());
				updateRelacionRechazada(request);
				updateEstadoCotejoPendiente(uiReeaCrVO.getId(), relacionEACRBI);
			} catch (ParteUnidadDocumentalNoEliminable e) {
				guardarError(request, e);
			}
		}
		cargarVista(mappings, formAsignacion, request);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void eliminarCajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		try {
			RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);
			UIReeaCRVO uiReeaCrVO = relacionEACRBI
					.getUIReencajadoById(formAsignacion
							.getIdUnidadInstalacion());
			relacionEACRBI.eliminarUICRVacia(relacion.getId(),
					uiReeaCrVO.getId());
		} catch (UnidadInstalacionConUnidadesDocumentalesException e) {
			guardarError(request, e);
		}
		updateRelacionRechazada(request);
		cargarVista(mappings, formAsignacion, request);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Realiza las comprobaciones básicas antes hacer cualquier operacion
	 * 
	 * @param form
	 * @param listaUDocs
	 * @param operacion
	 * @param request
	 * @return
	 */
	private ActionErrors comprobacionesBasicas(HttpServletRequest request,
			AsignacionCajasForm form, List listaUdocs, int operacion) {

		ActionErrors errors = new ActionErrors();

		// Comprobar que se ha seleccionado algún registro
		if (ArrayUtils.isEmpty(form.getUdocSeleccionada())) {
			errors.add(Constants.ERROR_NOT_SELECTION, new ActionError(
					Constants.ERROR_NOT_SELECTION));
		}

		switch (operacion) {
		case BAJAR:
			UDocEnUIReeaCRVO udocUltima = (UDocEnUIReeaCRVO) listaUdocs
					.get(listaUdocs.size() - 1);
			if (isSeleccionado(udocUltima, form.getUdocSeleccionada())) {
				errors.add(Constants.ERRORS_RELACIONES_BAJAR_LAST_UDOC,
						new ActionError(
								Constants.ERRORS_RELACIONES_BAJAR_LAST_UDOC));
			}
			break;
		case SUBIR:
			UDocEnUIReeaCRVO udocPrimera = (UDocEnUIReeaCRVO) listaUdocs.get(0);
			if (isSeleccionado(udocPrimera, form.getUdocSeleccionada())) {
				errors.add(Constants.ERRORS_RELACIONES_SUBIR_FIRST_UDOC,
						new ActionError(
								Constants.ERRORS_RELACIONES_SUBIR_FIRST_UDOC));
			}
			break;
		case DIVIDIR:
			if (form.getUdocSeleccionada().length != 1) {
				errors.add(
						Constants.ERROR_DIVIDIR_SOLO_UNA_UNIDAD,
						new ActionError(Constants.ERROR_DIVIDIR_SOLO_UNA_UNIDAD));
			}
			break;
		case DESCRIPCION:
			if (form.getUdocSeleccionada().length != 1) {
				errors.add(Constants.ERROR_DESRCIPCION_SOLO_UNA_UNIDAD,
						new ActionError(
								Constants.ERROR_DESRCIPCION_SOLO_UNA_UNIDAD));
			}
			break;
		}
		return errors;
	}

	private boolean isSeleccionado(UDocEnUIReeaCRVO udoc, String[] seleccionados) {
		for (int i = 0; i < seleccionados.length; i++) {
			if (seleccionados[i].equals(udoc.getId())) {
				return true;
			}
		}
		return false;
	}

	private void updateRelacionRechazada(HttpServletRequest request) {
		RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		if (relacion != null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionBI = services
					.lookupGestionRelacionesBI();
			if (relacion.isRechazada()) {
				relacion.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
				try {
					relacionBI.updateRelacion(relacion);
				} catch (ActionNotAllowedException e) {
					guardarError(request, e);
				}
			}
		}
	}

	/**
	 * Actualiza el estado de cotejo de una unidad de instalación reencajada a
	 * pendiente
	 * 
	 * @param idUiReencajado
	 *            Identificador de la unidad de instalación reencajada
	 * @param relacionEACRBI
	 *            Manager de relaciones reencajadas
	 */
	private void updateEstadoCotejoPendiente(String idUiReencajado,
			GestionRelacionesEACRBI relacionEACRBI) {

		if (StringUtils.isNotEmpty(idUiReencajado)) {
			relacionEACRBI.updateEstadoCotejo(idUiReencajado,
					EstadoCotejo.PENDIENTE.getIdentificador());
		}
	}

}