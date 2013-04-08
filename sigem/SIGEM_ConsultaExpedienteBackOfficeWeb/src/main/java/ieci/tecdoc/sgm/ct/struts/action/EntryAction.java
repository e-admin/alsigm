package ieci.tecdoc.sgm.ct.struts.action;

import java.util.Locale;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;
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
				response.sendRedirect(AutenticacionBackOffice.obtenerUrlLogin(request, ConstantesGestionUsuariosBackOffice.APLICACION_CONSULTA_EXPEDIENTES));
				return null;
			}

			Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);

			// Autorización
			if (!Misc.permisosAplicacion(Misc.obtenerIdUsuario(sesionBO.getDatosEspecificos()), sesionBO.getIdEntidad(), ConstantesGestionUsuariosAdministracion.APLICACION_CONSULTA_EXPEDIENTES)) {
				return mapping.findForward(Misc.NOACCESS_FORWARD);
			}

			session.setAttribute(Misc.SESION_ID, sesionBO.getIdSesion());
			session.setAttribute(Misc.ENTIDAD_ID, sesionBO.getIdEntidad());
			session.setAttribute(Misc.PERMISO_CE, Boolean.TRUE);

			// Limpiar el formulario de búsqueda
			session.removeAttribute(Misc.CNIF);
    		session.removeAttribute(Misc.EXPEDIENTE);
    		session.removeAttribute(Misc.PROCEDIMIENTO);
    		session.removeAttribute(Misc.NUMERO_REGISTRO_INICIAL);
    		session.removeAttribute(Misc.FECHA_DESDE);
    		session.removeAttribute(Misc.FECHA_DESDE_BUSQUEDA);
    		session.removeAttribute(Misc.OPERADOR_CONSULTA);
    		session.removeAttribute(Misc.FECHA_HASTA);
    		session.removeAttribute(Misc.FECHA_HASTA_BUSQUEDA);
    		session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE);
    		session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE_BUSQUEDA);
    		session.removeAttribute(Misc.OPERADOR_CONSULTA_FECHA_INICIAL);
    		session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA);
    		session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA_BUSQUEDA);
    		session.removeAttribute(Misc.ESTADO);

		} catch(Throwable e){
			logger.error("Error inesperado ejecutando acción.", e);
			return mapping.findForward(GLOBAL_FORWARD_ERROR);
		}

		return mapping.findForward("Success");
	}

}
