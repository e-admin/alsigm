package common.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import common.navigation.KeysClientsInvocations;

/**
 * Controlador para la gestión de la navegación a través de la selección de
 * opciones de menú, mediante la miga de pan y a través de los enlaces de acceso
 * rápido de la bandeja de entrada
 */
public class MultilineaErrorAction extends BaseAction {
	public void showExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.ERROR_MULTILINEA, request);

		setReturnActionFordward(request,
				mapping.findForward("show_multilinea_error"));
	}

	public void backAndShowExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		popLastInvocation(request);
		saveCurrentInvocation(KeysClientsInvocations.ERROR_MULTILINEA, request);
		setReturnActionFordward(request,
				mapping.findForward("show_multilinea_error"));
	}
}