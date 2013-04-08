package organizacion.view.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import organizacion.OrganizacionConstants;
import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.vo.OrganizacionVO;
import organizacion.persistence.db.IOrganizacionDBEntity;
import organizacion.view.form.FechasForm;

import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;

public class GestionFechasAction extends BaseAction {

	public void initExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		OrganizacionVO organizacionVO = (OrganizacionVO) getFromTemporalSession(
				request, OrganizacionConstants.ORGANIZACION_KEY);
		if (IOrganizacionDBEntity.HISTORICO.equals(organizacionVO.getEstado()
				.toString())) {
			FechasForm fechasForm = (FechasForm) form;
			fechasForm.setInicio(DateUtils.formatDate(organizacionVO
					.getFiniciovigencia()));
		}
		saveCurrentInvocation(KeysClientsInvocations.ORGANIZACION_FECHAS,
				request);
		setReturnActionFordward(request, mappings.findForward("done"));
	}

	public void guardarEstadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionesService = services
				.lookupGestionOrganizacionBI();
		OrganizacionVO organizacionVO = (OrganizacionVO) getFromTemporalSession(
				request, OrganizacionConstants.ORGANIZACION_KEY);
		FechasForm fechasForm = (FechasForm) form;

		ActionErrors errors = validateForm(request, organizacionVO, fechasForm);
		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request, mappings.findForward("done"));
		} else {
			if (IOrganizacionDBEntity.VIGENTE.equals(organizacionVO.getEstado()
					.toString()))
				organizacionVO.setFiniciovigencia(DateUtils.getDate(fechasForm
						.getInicio()));
			else {
				organizacionVO.setFfinvigencia(DateUtils.getDate(fechasForm
						.getFin()));
				organizacionesService.eliminarUsuariosOrgano(organizacionVO
						.getId());
			}
			organizacionesService.actualizarOrganizacion(organizacionVO);

			popLastInvocation(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	private ActionErrors validateForm(HttpServletRequest request,
			OrganizacionVO organizacionVO, FechasForm fechasForm) {
		ActionErrors errors = new ActionErrors();

		if (IOrganizacionDBEntity.VIGENTE.equals(organizacionVO.getEstado()
				.toString())) {
			if (StringUtils.isEmpty(fechasForm.getInicio())) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(OrganizacionConstants.ETIQUETA_INICIO_VIGENCIA)));
			} else if (!DateUtils.isDate(fechasForm.getInicio())) {
				errors.add(
						Constants.ERROR_DATE_INVALID,
						new ActionError(Constants.ERROR_DATE_INVALID, Messages
								.getString("organizacion.org.fechainicio")));
			} else {
				Date fechaInicio = DateUtils.getDate(fechasForm.getInicio());
				Date fechaActual = DateUtils.getFechaActual();
				if (DateUtils.isFechaMayor(fechaInicio, fechaActual))
					errors.add(
							Constants.ERROR_DATE_AFTER_TODAY,
							new ActionError(
									Constants.ERROR_DATE_AFTER_TODAY,
									Messages.getString("organizacion.org.fechainicio")));
			}
		}

		if (IOrganizacionDBEntity.HISTORICO.equals(organizacionVO.getEstado()
				.toString())) {
			if (StringUtils.isEmpty(fechasForm.getFin())) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(OrganizacionConstants.ETIQUETA_FIN_VIGENCIA)));
			} else if (!DateUtils.isDate(fechasForm.getFin())) {
				errors.add(
						Constants.ERROR_DATE_INVALID,
						new ActionError(Constants.ERROR_DATE_INVALID, Messages
								.getString("organizacion.org.fechafin")));
			} else {
				Date fechaInicio = DateUtils.getDate(fechasForm.getFin());
				Date fechaActual = DateUtils.getFechaActual();
				if (DateUtils.isFechaMayor(fechaInicio, fechaActual)) {
					errors.add(
							Constants.ERROR_DATE_AFTER_TODAY,
							new ActionError(
									Constants.ERROR_DATE_AFTER_TODAY,
									Messages.getString("organizacion.org.fechafin")));
				} else {
					fechaInicio = DateUtils.getDate(fechasForm.getInicio());
					fechaActual = DateUtils.getDate(fechasForm.getFin());
					if (DateUtils.isFechaMayor(fechaInicio, fechaActual))
						errors.add(
								Constants.NUEVA_FECHA_ANTERIOR_A_HOY,
								new ActionError(
										Constants.NUEVA_FECHA_ANTERIOR_A_HOY,
										Messages.getString("organizacion.org.fechafin")));
				}
			}
		}

		return errors;
	}
}