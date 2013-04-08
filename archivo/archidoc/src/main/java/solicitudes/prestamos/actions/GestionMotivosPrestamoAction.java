package solicitudes.prestamos.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.forms.MotivoPrestamoForm;
import solicitudes.prestamos.vos.MotivoPrestamoVO;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionPrestamosBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

public class GestionMotivosPrestamoAction extends BaseAction {

	public void initExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.MOTIVOS_PRESTAMO, request);
		invocation.setAsReturnPoint(true);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();
		List listaMotivosPrestamo = prestamosBI.getMotivosPrestamo();

		setInTemporalSession(request, SolicitudesConstants.LISTA_MOTIVOS_KEY,
				listaMotivosPrestamo);
		setInTemporalSession(request, SolicitudesConstants.MOTIVO_PRESTAMO,
				Messages.getString(SolicitudesConstants.MOTIVO_PRESTAMO,
						request.getLocale()));
		setInTemporalSession(request, PrestamosConstants.CHECK_INTERNO,
				PrestamosConstants.TIPO_INTERNO_STRING);
		setInTemporalSession(request, PrestamosConstants.CHECK_EXTERNO,
				PrestamosConstants.TIPO_EXTERNO_STRING);
		setInTemporalSession(request, SolicitudesConstants.MOTIVO_EDITABLE_KEY,
				new Boolean(true));
		setReturnActionFordward(request, mappings.findForward("init"));
	}

	public void nuevoMotivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_PRESTAMO_ALTA,
				request);

		setReturnActionFordward(request, mappings.findForward("alta_motivo"));
	}

	public void addMotivoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();

		MotivoPrestamoForm motivosForm = (MotivoPrestamoForm) form;
		ActionErrors errors = validateForm(motivosForm, request);

		if (errors.size() > 0) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("alta_motivo"));
		} else {
			MotivoPrestamoVO motivoVO = new MotivoPrestamoVO();
			motivosForm.populate(motivoVO);

			// Para saber si estoy editando o creando uno nuevo
			if (StringUtils.isEmpty(motivoVO.getId())) {
				// Comprobar que no exista ya dicho motivo de prestamo
				MotivoPrestamoVO aux = prestamosBI.getMotivoPrestamo(motivoVO);

				if (aux != null) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_MOTIVO_YA_EXISTE,
									new Object[] { Messages
											.getString(
													SolicitudesConstants.MOTIVO_PRESTAMO,
													request.getLocale()) }));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mappings.findForward("alta_motivo"));
				} else {
					prestamosBI.insertarMotivoPrestamo(motivoVO);
					setReturnActionFordward(request,
							mappings.findForward("list"));
				}
			} else {
				prestamosBI.actualizarMotivoPrestamo(motivoVO);
				goBackExecuteLogic(mappings, form, request, response);
			}
		}
	}

	public void verMotivoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_PRESTAMO_DATOS,
				request);

		removeInTemporalSession(request, SolicitudesConstants.MOTIVO_KEY);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();

		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoPrestamoVO motivoVO = prestamosBI
					.getMotivoPrestamoById(idMotivo);
			setInTemporalSession(request, SolicitudesConstants.MOTIVO_KEY,
					motivoVO);
		}
		setReturnActionFordward(request, mappings.findForward("datos_motivo"));
	}

	/**
	 * Carga la página para la edición del nivel del cuadro de clasificacion
	 * seleccionado.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void editarMotivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_PRESTAMO_EDICION,
				request);

		removeInTemporalSession(request,
				SolicitudesConstants.MOTIVO_EDITABLE_KEY);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();

		MotivoPrestamoForm motivosForm = (MotivoPrestamoForm) form;
		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoPrestamoVO motivoVO = prestamosBI
					.getMotivoPrestamoById(idMotivo);
			motivosForm.set(motivoVO);

			// Comprobar que no tenga prestamos en ASGPPRESTAMO para ese
			// idMotivo
			int prestamosXMotivo = prestamosBI
					.getCountPrestamoByIdMotivo(idMotivo);
			boolean editable = true;
			if (prestamosXMotivo > 0)
				editable = false;

			setInTemporalSession(request,
					SolicitudesConstants.MOTIVO_EDITABLE_KEY, new Boolean(
							editable));

			setReturnActionFordward(request,
					mappings.findForward("alta_motivo"));
		}
	}

	public void eliminarMotivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();

		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoPrestamoVO motivoVO = prestamosBI
					.getMotivoPrestamoById(idMotivo);
			if (motivoVO != null) {

				// Si existe algun prestamo que tenga dicho motivo no se podra
				// eliminar
				ActionErrors errors = new ActionErrors();
				int prestamosXMotivo = prestamosBI
						.getCountPrestamoByIdMotivo(idMotivo);
				if (prestamosXMotivo > 0) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_ELIMINAR_MOTIVO,
									new Object[] { Messages
											.getString(
													SolicitudesConstants.MOTIVO_PRESTAMO,
													request.getLocale()) }));
					ErrorsTag.saveErrors(request, errors);
					goLastClientExecuteLogic(mappings, form, request, response);
				} else {
					prestamosBI.deleteMotivoPrestamo(motivoVO);
					setReturnActionFordward(request,
							mappings.findForward("list"));
				}
			}
		}
	}

	/**
	 * Comprueba que los datos introducidos en el formulario sean correctos.
	 */
	public ActionErrors validateForm(MotivoPrestamoForm motivosForm,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Comprobar que se ha introducido la descripcion
		if (StringUtils.isEmpty(motivosForm.getMotivo())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_DESCRIPCION,
									request.getLocale())));
		}

		// Comprobar que se ha seleccionado el tipo de entidad.
		if (motivosForm.getTipoUsuario() == 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_TIPO_USUARIO,
									request.getLocale())));
		}

		// Si selecciono tipo de usuario = "INTERNO" comprobar que puso la
		// visibilidad
		if (motivosForm.getTipoUsuario() == PrestamosConstants.TIPO_INTERNO_INT
				&& motivosForm.getVisibilidad() == 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_VISIBILIDAD,
									request.getLocale())));
		}

		return errors;
	}
}