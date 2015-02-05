package export.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import xml.config.Busqueda;

import common.Constants;
import common.actions.BaseAction;
import common.navigation.KeysClientsInvocations;

import fondos.FondosConstants;
import fondos.vos.BusquedaElementosVO;

/**
 * Controlador para la gestión de la navegación a través de la selección de
 * opciones de menú, mediante la miga de pan y a través de los enlaces de acceso
 * rápido de la bandeja de entrada
 */
public class ExportarInformeListadoAction extends BaseAction {

	public void initExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		removeInTemporalSession(request, Constants.SHOW_INFORME_BUSQUEDA_BUTTON);

		// hay que quitar un nivel de la miga de pan -> se van a perder
		// referencias a objetos importantes
		// antes de quitar el nivel, nos quedamos con una referencia de estos
		// objetos, para que podamos seguir accediendo a ellos
		// aunque dejen de estar en la session.
		String uriRetorno = (String) getFromTemporalSession(request,
				FondosConstants.INFORME_BUSQUEDA_URI_RETORNO);
		BusquedaElementosVO busquedaElementosVO = (BusquedaElementosVO) getFromTemporalSession(
				request, FondosConstants.VO_BUSQUEDA_FONDOS);
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);
		List listaIdsElementos = (List) getFromTemporalSession(request,
				FondosConstants.LISTA_IDS_ELEMENTOS_CF);
		String forward = (String) getFromTemporalSession(request,
				Constants.FORWARD_GENERAR_INFORME_LISTADO);

		// miga de pan
		popLastInvocation(request);
		saveCurrentInvocation(KeysClientsInvocations.EXPORTAR_INFORME_LISTADO,
				request);

		// volvemos a meter los objetos en session.
		String showProductor = (String) request
				.getAttribute(FondosConstants.SHOW_PRODUCTOR);
		setInTemporalSession(request, FondosConstants.SHOW_PRODUCTOR,
				showProductor);
		setInTemporalSession(request,
				FondosConstants.INFORME_BUSQUEDA_URI_RETORNO, uriRetorno);
		setInTemporalSession(request, FondosConstants.VO_BUSQUEDA_FONDOS,
				busquedaElementosVO);
		setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
				busqueda);
		setInTemporalSession(request, FondosConstants.LISTA_IDS_ELEMENTOS_CF,
				listaIdsElementos);
		setInTemporalSession(request,
				Constants.FORWARD_GENERAR_INFORME_LISTADO, forward);

		setReturnActionFordward(request, mapping.findForward("init"));
	}

	public void generateExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		removeInTemporalSession(request, Constants.CANCEL_PROGRESSBAR_KEY);
		// obtener el formato seleccionado
		String formato = request.getParameter("formatoListado");
		request.setAttribute(Constants.FORMATO_INFORME_LISTADO_KEY, formato);
		String forward = (String) getFromTemporalSession(request,
				Constants.FORWARD_GENERAR_INFORME_LISTADO);
		// obtener de la session temporal el forward al que llamar para generar
		// el informe

		setInTemporalSession(request, Constants.PROGRESSBAR_PERCENT_KEY,
				new Integer(0));
		setReturnActionFordward(request, mapping.findForward(forward));
	}
}