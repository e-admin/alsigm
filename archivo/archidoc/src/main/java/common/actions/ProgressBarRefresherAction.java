package common.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import common.Constants;

/**
 * Controlador para la gestión de la navegación a través de la selección de
 * opciones de menú, mediante la miga de pan y a través de los enlaces de acceso
 * rápido de la bandeja de entrada
 */
public class ProgressBarRefresherAction extends BaseAction {

	public void updateProgressBarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request, mapping.findForward("progressBar"));
	}

	public void cancelGenReportExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setInTemporalSession(request, Constants.CANCEL_PROGRESSBAR_KEY,
				Boolean.TRUE);
		setReturnActionFordward(request, mapping.findForward("progressBar"));
	}

	public void resetProgressBarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
				new Integer(0));
		//setInTemporalSession(request, Constants.CANCEL_PROGRESSBAR_KEY,
		//		Boolean.TRUE);
		setReturnActionFordward(request, mapping.findForward("progressBar"));
	}
}