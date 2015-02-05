package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class ConsultaRegistroTelematicoWebAction extends Action {

	private static final Logger logger = Logger.getLogger(ConsultaRegistroTelematicoWebAction.class);

	public static final String GLOBAL_FORWARD_ERROR = "";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;
		HttpSession session = request.getSession();

		try {
			// Autenticación y autorización
			Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);
			Boolean permiso = (Boolean) session.getAttribute(Defs.PERMISO_CRT);
			if ((sesionBO == null) || (permiso == null) || (!permiso.booleanValue())) {

				session.removeAttribute(Defs.SESION_ID);
				session.removeAttribute(Defs.ENTIDAD_ID);
				session.removeAttribute(Defs.PERMISO_CRT);

				return mapping.findForward(Defs.ENTRY_FORWARD);
			}

	    	forward = executeAction(mapping, form, request, response);

		} catch(Throwable e){
			logger.error("Error inesperado ejecutando acción.", e);
			return mapping.findForward(GLOBAL_FORWARD_ERROR);
		}

		return forward;
	}

	/**
	 * Método específico que se ejecuta en cada acción.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
