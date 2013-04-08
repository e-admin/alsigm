package deposito.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ActionForwardUtils;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.forms.FormatoForm;
import deposito.model.DepositoException;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.FormatoHuecoVO;

public class GestionFormatosAction extends BaseAction {

	public void listaFormatosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// FormatoForm rolForm = (FormatoForm) form;
		GestorEstructuraDepositoBI depositoBI = getGestorEstructuraDepositoBI(request);
		List listaFormatos = depositoBI.getFormatos();

		request.setAttribute(DepositoConstants.LISTA_FORMATOS, listaFormatos);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_LISTADO_FORMATOS, request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request, mappings.findForward("lista_formatos"));
	}

	public void verFormatoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		FormatoForm formatoForm = (FormatoForm) form;
		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);

		FormatoHuecoVO formato = depositoBI
				.getFormatoHueco(formatoForm.getId());
		setInTemporalSession(request, DepositoConstants.INFO_FORMATO,
				new FormatoHuecoPO(depositoBI, formato));

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_INFO_FORMATO, request);

		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request, mappings.findForward("info_formato"));

	}

	public void altaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_ALTA_FORMATO, request);

		invocation.setAsReturnPoint(true);

		removeInTemporalSession(request, DepositoConstants.INFO_FORMATO);

		FormatoForm formatoForm = (FormatoForm) form;
		if (formatoForm.isRegular())
			formatoForm.setMultidoc(true);
		else
			formatoForm.setMultidoc(false);
		formatoForm.setVigente(true);

		setReturnActionFordward(request,
				mappings.findForward("formulario_formato"));
	}

	public void edicionExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FormatoForm formatoForm = (FormatoForm) form;
		// GestorEstructuraDepositoBI depositoBI =
		// getGestionDespositoBI(request);
		FormatoHuecoVO formato = (FormatoHuecoVO) getFromTemporalSession(
				request, DepositoConstants.INFO_FORMATO);
		formatoForm.populate(formato);
		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_EDICION_FORMATO,
				request);
		setReturnActionFordward(request,
				mappings.findForward("formulario_formato"));
	}

	public void eliminarVariosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		FormatoForm formatoForm = (FormatoForm) form;
		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
		try {
			ActionErrors errors = validateFormInDelete(form);
			if (errors == null) {
				depositoBI.deleteFormatos(formatoForm
						.getFormatosSeleccionados());
			}

		} catch (DepositoException e) {
			guardarError(request, e);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void eliminarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FormatoForm formatoForm = (FormatoForm) form;
		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);
		try {
			depositoBI.deleteFormato(formatoForm.getFormatoSeleccionado());

		} catch (DepositoException e) {
			guardarError(request, e);
		}
		goReturnPointExecuteLogic(mappings, form, request, response);
	}

	private ActionErrors validateFormInDelete(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		FormatoForm formatoForm = (FormatoForm) form;
		if (formatoForm.getFormatosSeleccionados() == null
				&& formatoForm.getFormatosSeleccionados().length == 0) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					ErrorKeys.ERROR_SELECCIONES_AL_MENOS_UNO));

		}
		return errors.size() > 0 ? errors : null;
	}

	/**
     * 
     */
	protected ActionErrors validateForm(HttpServletRequest request,
			ActionForm form) {
		return validateForm(request, form, null);
	}

	private ActionErrors validateForm(HttpServletRequest request,
			ActionForm form, GestorEstructuraDepositoBI depositoBI) {
		ActionErrors errors = new ActionErrors();
		FormatoForm formatoForm = (FormatoForm) form;
		if (GenericValidator.isBlankOrNull(formatoForm.getNombre())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}
		if (formatoForm.isRegular()) {
			if (formatoForm.getLongitud() <= 0) {
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_INT_MAYOR_CERO,
								Messages.getString(Constants.ETIQUETA_LONGITUD,
										request.getLocale())));
			}
		}

		if (depositoBI != null) {
			FormatoHuecoVO formato = depositoBI.getFormatoByNombre(formatoForm
					.getNombre());
			if (formato != null
					&& (StringUtils.isEmpty(formatoForm.getId()) || !formato
							.getId().equals(formatoForm.getId()))) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						Constants.ERROR_CREAR_ELEMENTO_YA_EXISTE));
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	public void guardarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FormatoForm formatoForm = (FormatoForm) form;

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((FormatoForm) form).getMap());

		FormatoHuecoVO formato = (FormatoHuecoVO) getFromTemporalSession(
				request, DepositoConstants.INFO_FORMATO);
		GestorEstructuraDepositoBI depositoBI = getGestorEstructuraDepositoBI(request);
		ActionErrors errors = validateForm(request, form, depositoBI);
		if (errors == null) {
			if (formato == null) {
				formato = new FormatoHuecoVO();
				formatoForm.fillVO(formato);
				depositoBI.insertarFormato(formato);

			} else {
				formatoForm.fillVOInUpdate((FormatoHuecoPO) formato);
				depositoBI.modificarFormato(formato);
			}

			ActionRedirect vistaFormato = new ActionRedirect(
					ActionForwardUtils.getActionForward(request, "verFormato"),
					true);
			vistaFormato.addParameter(Constants.ID, formato.getId());
			popLastInvocation(request);

			setReturnActionFordward(request, vistaFormato);

		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("formulario_formato"));
		}
	}

}
