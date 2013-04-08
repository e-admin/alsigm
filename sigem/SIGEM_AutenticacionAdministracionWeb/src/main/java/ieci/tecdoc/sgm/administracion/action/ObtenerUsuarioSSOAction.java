package ieci.tecdoc.sgm.administracion.action;

import ieci.tecdoc.sgm.administracion.utils.Utilidades;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ObtenerUsuarioSSOAction extends Action{

	private static final Logger logger = Logger.getLogger(ObtenerUsuarioSSOAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try{
			HttpSession session = request.getSession();

			String idEntidad = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
			if (!Utilidades.isNuloOVacio(idEntidad)) {
				session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);
			}

			String idAplicacion = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
			if (!Utilidades.isNuloOVacio(idAplicacion)) {
				session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION, idAplicacion);
			}

			String usuario = null;
			String userDn = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_SSO_USER_DN);
			if (!Utilidades.isNuloOVacio(userDn)) {

				int indexCn = userDn.indexOf("CN=");
				if (indexCn != -1) {

					usuario = userDn.substring(indexCn + 3);
					int index = usuario.indexOf(",");
					if (index != -1) {
						usuario = usuario.substring(0, index);
					}

					usuario = usuario.trim();
				}
			}

			if (!Utilidades.isNuloOVacio(usuario)) {
				session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_USUARIO, usuario);
				session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_SSO_USER_DN, userDn);
			} else {
				session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_USUARIO);
				session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_SSO_USER_DN);
			}

		}catch(Exception e){
			logger.error("Se ha producido un error al obtener el usuario SSO", e.fillInStackTrace());
	   	}

		return mapping.findForward("success");
	}
}
