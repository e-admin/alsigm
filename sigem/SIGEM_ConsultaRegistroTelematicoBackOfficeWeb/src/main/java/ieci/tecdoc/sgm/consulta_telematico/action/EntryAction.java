package ieci.tecdoc.sgm.consulta_telematico.action;

import java.util.Locale;

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.consulta_telematico.utils.Utils;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EntryAction extends Action {

	private static final Logger logger = Logger.getLogger(EntryAction.class);

	public static final String GLOBAL_FORWARD_ERROR = "";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		try {
			// Obtener el locale seleccionado por el filtro
			Locale locale = LocaleFilterHelper.getCurrentLocale(request);
			if (locale != null) {
				session.setAttribute("org.apache.struts.action.LOCALE", locale);
			}

			// Autenticación
			if(!AutenticacionBackOffice.autenticar(request)){
				response.sendRedirect(AutenticacionBackOffice.obtenerUrlLogin(request, ConstantesGestionUsuariosBackOffice.APLICACION_CONSULTA_REGISTROS_TELEMATICOS));
				return null;
			}

			Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);

			// Autorización
			if (!Utils.permisosAplicacion(Utils.obtenerIdUsuario(sesionBO.getDatosEspecificos()), sesionBO.getIdEntidad(), ConstantesGestionUsuariosAdministracion.APLICACION_CONSULTA_REGISTROS_TELEMATICOS)) {
				return mapping.findForward(Defs.NOACCESS_FORWARD);
			}

			session.setAttribute(Defs.SESION_ID, sesionBO.getIdSesion());
			session.setAttribute(Defs.ENTIDAD_ID, sesionBO.getIdEntidad());
			session.setAttribute(Defs.PERMISO_CRT, Boolean.TRUE);

		} catch(Throwable e){
			logger.error("Error inesperado ejecutando acción.", e);
			return mapping.findForward(GLOBAL_FORWARD_ERROR);
		}

		return mapping.findForward(Defs.SUCCESS_FORWARD);
	}

}
