package ieci.tecdoc.sgm.backoffice.action;

import ieci.tecdoc.sgm.backoffice.utils.Defs;
import ieci.tecdoc.sgm.backoffice.utils.Utilidades;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AlmacenarDatosArchivoAction extends Action{
	
	public ActionForward execute(ActionMapping mapping , ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		try {
			HttpSession session = request.getSession();
			String key = null, tipo = null;

			key = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
			if (Utilidades.isNuloOVacio(key)) {
				key = new String("");
			}
			
			tipo = (String)request.getParameter("tipo");
			
			XmlTextBuilder xml = new XmlTextBuilder();
			xml.addSimpleElement(TAG_TIPO_USUARIO, tipo);
			
			Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);
			AutenticacionBackOffice.modificarDatosSesion(key, sesionBO.getDatosEspecificos() + xml.getText());
			
			// Obtener la url de la aplicación de archivo
			ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
			String url = oServicio.obtenerDireccionAplicacion(ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO);
			url = AutenticacionBackOffice.comprobarURL(request, url);
			request.setAttribute(Defs.PARAMETRO_URL, url);
			
			return mapping.findForward("success");
		} catch(Exception e) {
			request.setAttribute("invalid_user", "true");
			return mapping.findForward("login");
		}
	}
	
	public static final String TAG_TIPO_USUARIO = "TipoUsuario";
}
