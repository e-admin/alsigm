/*
 * Created on 04-ene-2005
 *
 */
package deposito.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import common.actions.BaseAction;

import deposito.global.Constants;

/**
 * @author ABELRL
 * 
 *         Utilizado para hacer redirect y elemimnar problemas de F5-Refresh
 */
public class ViewResultadoOperacionActionOLD extends BaseAction {
	private String FORWARD_TIPODEPOSITO = "tipodeposito";
	private String FORWARD_TIPOASIGNABLE = "tipoasignable";
	private String FORWARD_TIPONOASIGNABLE = "tiponoasignable";
	public String TIPO_DEPOSITO_KEY = "tipodeposito";
	public String TIPO_ASIGNABLE_KEY = "tipoasignable";
	public String TIPO_NOASIGNABLE_KEY = "tiponoasignable";
	public final static String URI_OUT_KEY = "uriout";
	public final static String OPERACION_KEY = "operacion";
	public final static String EDIT_OP = "edit";
	public final static String DELETE_OP = "delete";
	public final static String NEW_OP = "nueva";

	protected ActionForward findSuccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) {

		ActionForward ret = super.findSuccess(mapping, form, req, resp);
		String tipoElemento = req.getParameter(Constants.TIPO_ELEMENTO_KEY);
		String operacion = req.getParameter(OPERACION_KEY);
		if (operacion == null)
			req.setAttribute(OPERACION_KEY, NEW_OP);
		else
			req.setAttribute(OPERACION_KEY, operacion);

		if (!(tipoElemento == null)) {
			if (tipoElemento.equalsIgnoreCase(TIPO_DEPOSITO_KEY)) {
				ret = mapping.findForward(FORWARD_TIPODEPOSITO);
			} else if (tipoElemento.equalsIgnoreCase(TIPO_ASIGNABLE_KEY)) {
				ret = mapping.findForward(FORWARD_TIPOASIGNABLE);
			} else if (tipoElemento.equalsIgnoreCase(TIPO_NOASIGNABLE_KEY)) {
				ret = mapping.findForward(FORWARD_TIPONOASIGNABLE);
			}

		} else {
			req.getSession().removeAttribute(Constants.LISTA_RESULTADO_KEY);
			ret = new ActionForward();
			ret.setPath((String) req.getParameter(URI_OUT_KEY));
		}
		return ret;
	}

}
