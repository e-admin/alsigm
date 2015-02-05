package solicitudes.consultas.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.forms.MotivoConsultaForm;
import solicitudes.consultas.vos.MotivoConsultaVO;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionConsultasBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

public class GestionMotivosConsultaAction extends BaseAction {

	public void initExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.MOTIVOS_CONSULTA, request);
		invocation.setAsReturnPoint(true);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionConsultasBI consultasBI = services.lookupGestionConsultasBI();
		List listaMotivosConsulta = consultasBI.getMotivosConsulta();

		setInTemporalSession(request, SolicitudesConstants.LISTA_MOTIVOS_KEY,
				listaMotivosConsulta);
		setInTemporalSession(request, SolicitudesConstants.MOTIVO_EDITABLE_KEY,
				new Boolean(true));
		setInTemporalSession(request, SolicitudesConstants.MOTIVO_CONSULTA,
				Messages.getString(SolicitudesConstants.MOTIVO_CONSULTA,
						request.getLocale()));
		setInTemporalSession(request, ConsultasConstants.CHECK_INVESTIGADOR,
				ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR);
		setInTemporalSession(request, ConsultasConstants.CHECK_CIUDADANO,
				ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_CIUDADANO);
		setInTemporalSession(request, ConsultasConstants.CHECK_ORGEXTERNO,
				ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO);
		setReturnActionFordward(request, mappings.findForward("init"));
	}

	public void nuevoMotivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_CONSULTA_ALTA,
				request);

		setReturnActionFordward(request, mappings.findForward("alta_motivo"));
	}

	public void addMotivoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionConsultasBI consultasBI = services.lookupGestionConsultasBI();

		MotivoConsultaForm motivosForm = (MotivoConsultaForm) form;
		ActionErrors errors = validateForm(motivosForm, request);

		if (errors.size() > 0) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("alta_motivo"));
		} else {
			MotivoConsultaVO motivoVO = new MotivoConsultaVO();
			motivosForm.populate(motivoVO);

			// Para saber si estoy editando o creando uno nuevo
			if (StringUtils.isEmpty(motivoVO.getId())) {
				// Comprobar que no exista ya dicho motivo de consulta
				MotivoConsultaVO aux = consultasBI.getMotivoConsulta(motivoVO);

				if (aux != null) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_MOTIVO_YA_EXISTE,
									new Object[] { Messages
											.getString(
													SolicitudesConstants.MOTIVO_CONSULTA,
													request.getLocale()) }));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mappings.findForward("alta_motivo"));
				} else {
					consultasBI.insertarMotivoConsulta(motivoVO);
					setReturnActionFordward(request,
							mappings.findForward("list"));
				}
			} else {
				consultasBI.actualizarMotivo(motivoVO);
				goBackExecuteLogic(mappings, form, request, response);
			}
		}
	}

	public void verMotivoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_CONSULTA_DATOS,
				request);

		removeInTemporalSession(request, SolicitudesConstants.MOTIVO_KEY);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionConsultasBI consultaBI = services.lookupGestionConsultasBI();

		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoConsultaVO motivoVO = consultaBI
					.getMotivoConsultaById(idMotivo);
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

		saveCurrentInvocation(KeysClientsInvocations.MOTIVO_CONSULTA_EDICION,
				request);

		removeInTemporalSession(request,
				SolicitudesConstants.MOTIVO_EDITABLE_KEY);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionConsultasBI consultasBI = services.lookupGestionConsultasBI();

		MotivoConsultaForm motivosForm = (MotivoConsultaForm) form;
		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoConsultaVO motivoVO = consultasBI
					.getMotivoConsultaById(idMotivo);
			motivosForm.set(motivoVO);

			// Comprobar que no tenga consultas en ASGPCONSULTA para ese
			// idMotivo
			int consultasXMotivo = consultasBI
					.getCountConsultaByIdMotivo(idMotivo);
			boolean editable = true;
			if (consultasXMotivo > 0)
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
		GestionConsultasBI consultasBI = services.lookupGestionConsultasBI();

		String idMotivo = request.getParameter("idMotivo");
		if (StringUtils.isNotEmpty(idMotivo)) {
			MotivoConsultaVO motivoVO = consultasBI
					.getMotivoConsultaById(idMotivo);
			if (motivoVO != null) {

				// Si existe alguna consulta que tenga dicho motivo no se podra
				// eliminar
				ActionErrors errors = new ActionErrors();
				int consultasXMotivo = consultasBI
						.getCountConsultaByIdMotivo(idMotivo);
				if (consultasXMotivo > 0) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_ELIMINAR_MOTIVO,
									new Object[] { Messages
											.getString(
													SolicitudesConstants.MOTIVO_CONSULTA,
													request.getLocale()) }));
					ErrorsTag.saveErrors(request, errors);
					goLastClientExecuteLogic(mappings, form, request, response);
				} else {
					consultasBI.deleteMotivo(motivoVO);
					setReturnActionFordward(request,
							mappings.findForward("list"));
				}
			}
		}
	}

	/**
	 * Comprueba que los datos introducidos en el formulario sean correctos.
	 */
	public ActionErrors validateForm(MotivoConsultaForm motivosForm,
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
		if (motivosForm.getTipoEntidad() == 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_TIPO_ENTIDAD,
									request.getLocale())));
		}

		// Comprobar que se ha seleccionado el tipo de consulta.
		if (motivosForm.getTipoConsulta() == 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_TIPO_CONSULTA,
									request.getLocale())));
		}

		// Si selecciono tipo de entidad = "INVESTIGADOR" comprobar que puso la
		// visibilidad
		if (motivosForm.getTipoEntidad() == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT
				&& motivosForm.getVisibilidad() == 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_VISIBILIDAD,
									request.getLocale())));
		}

		// Comprobar que el tipo de entidad seleccionada sea correcta
		if (errors.isEmpty()
				&& motivosForm.getTipoConsulta() == ConsultasConstants.TIPO_CONSULTA_DIRECTA
				&& motivosForm.getTipoEntidad() != ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_TIPO_ENTIDAD_INVALIDO));
		}
		return errors;
	}
}