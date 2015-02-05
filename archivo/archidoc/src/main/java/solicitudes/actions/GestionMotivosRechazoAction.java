package solicitudes.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.forms.MotivoRechazoForm;
import solicitudes.vos.MotivoRechazoVO;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionRechazosBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

public class GestionMotivosRechazoAction extends BaseAction {

	public void initExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.MOTIVOS_RECHAZO, request);
		invocation.setAsReturnPoint(true);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRechazosBI rechazosBI = services.lookupGestionMotivosRechazoBI();
		List listaMotivosRechazo = rechazosBI.getMotivosRechazo();

		setInTemporalSession(request, SolicitudesConstants.LISTA_MOTIVOS_KEY,
				listaMotivosRechazo);
		setInTemporalSession(
				request,
				SolicitudesConstants.MOTIVO_RECHAZO,
				Messages.getString(SolicitudesConstants.MOTIVO_RECHAZO,
						request.getLocale()));
		setInTemporalSession(request, SolicitudesConstants.MOTIVO_EDITABLE_KEY,
				new Boolean(true));

		setReturnActionFordward(request, mappings.findForward("init"));
	}

	public void nuevoMotivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_RECHAZO_ALTA,
				request);

		setReturnActionFordward(request, mappings.findForward("alta_motivo"));
	}

	public void addMotivoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRechazosBI rechazosBI = services.lookupGestionMotivosRechazoBI();

		MotivoRechazoForm motivosForm = (MotivoRechazoForm) form;
		ActionErrors errors = validateForm(motivosForm, request);

		if (errors.size() > 0) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("alta_motivo"));
		} else {
			MotivoRechazoVO motivoVO = new MotivoRechazoVO();
			motivosForm.populate(motivoVO);

			// Para saber si estoy editando o creando uno nuevo
			if (StringUtils.isEmpty(motivoVO.getId())) {
				// Comprobar que no exista ya dicho motivo de consulta
				MotivoRechazoVO aux = rechazosBI.getMotivoRechazo(motivoVO);

				if (aux != null) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_MOTIVO_YA_EXISTE,
									new Object[] { Messages
											.getString(
													SolicitudesConstants.MOTIVO_RECHAZO,
													request.getLocale()) }));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mappings.findForward("alta_motivo"));
				} else {
					rechazosBI.insertMotivoRechazo(motivoVO);
					setReturnActionFordward(request,
							mappings.findForward("list"));
				}
			} else {
				rechazosBI.updateMotivoRechazo(motivoVO);
				goBackExecuteLogic(mappings, form, request, response);
			}
		}
	}

	public void verMotivoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_RECHAZO_DATOS,
				request);

		removeInTemporalSession(request, SolicitudesConstants.MOTIVO_KEY);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRechazosBI rechazosBI = services.lookupGestionMotivosRechazoBI();

		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoRechazoVO motivoVO = rechazosBI
					.getMotivoRechazoById(idMotivo);
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

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_RECHAZO_EDICION,
				request);

		removeInTemporalSession(request,
				SolicitudesConstants.MOTIVO_EDITABLE_KEY);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRechazosBI rechazosBI = services.lookupGestionMotivosRechazoBI();

		MotivoRechazoForm motivosForm = (MotivoRechazoForm) form;
		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoRechazoVO motivoVO = rechazosBI
					.getMotivoRechazoById(idMotivo);
			motivosForm.set(motivoVO);

			// Comprobar que no este referenciado en ningun elemento dicho
			// idMotivo
			boolean editable = true;
			if (rechazosBI.isReferenciado(motivoVO))
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
		GestionRechazosBI rechazosBI = services.lookupGestionMotivosRechazoBI();

		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoRechazoVO motivoVO = rechazosBI
					.getMotivoRechazoById(idMotivo);
			if (motivoVO != null) {

				// Si existe algun elemento que tenga dicho motivo no se podra
				// eliminar
				ActionErrors errors = new ActionErrors();

				if (rechazosBI.isReferenciado(motivoVO)) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_ELIMINAR_MOTIVO,
									new Object[] { Messages
											.getString(
													SolicitudesConstants.MOTIVO_RECHAZO,
													request.getLocale()) }));
					ErrorsTag.saveErrors(request, errors);
					goLastClientExecuteLogic(mappings, form, request, response);
				} else {
					rechazosBI.deleteMotivoRechazo(motivoVO);
					setReturnActionFordward(request,
							mappings.findForward("list"));
				}
			}
		}
	}

	/**
	 * Comprueba que los datos introducidos en el formulario sean correctos.
	 */
	public ActionErrors validateForm(MotivoRechazoForm motivosForm,
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

		// Comprobar que se ha seleccionado el tipo de solicitud.
		if (motivosForm.getTipoSolicitud() == 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_TIPO_CONSULTA,
									request.getLocale())));
		}

		return errors;
	}
}