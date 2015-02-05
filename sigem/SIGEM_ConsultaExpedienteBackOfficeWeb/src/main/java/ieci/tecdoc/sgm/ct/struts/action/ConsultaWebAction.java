package ieci.tecdoc.sgm.ct.struts.action;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.ct.utilities.Misc;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class ConsultaWebAction extends Action {

	private static final Logger logger = Logger.getLogger(ConsultaWebAction.class);

	public static final String GLOBAL_FORWARD_ERROR = "errorGlobal";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;
		HttpSession session = request.getSession();

		try {
			// Autenticación y autorización
			Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);
			Boolean permiso = (Boolean) session.getAttribute(Misc.PERMISO_CE);
			if ((sesionBO == null) || (permiso == null) || (!permiso.booleanValue())) {

				// Limpiar el acceso
				session.removeAttribute(Misc.SESION_ID);
				session.removeAttribute(Misc.ENTIDAD_ID);
				session.removeAttribute(Misc.PERMISO_CE);

				return mapping.findForward(Misc.ENTRY_FORWARD);
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
