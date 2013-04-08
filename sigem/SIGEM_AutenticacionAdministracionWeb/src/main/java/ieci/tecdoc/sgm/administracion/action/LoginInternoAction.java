package ieci.tecdoc.sgm.administracion.action;

import ieci.tecdoc.sgm.administracion.form.LoginAccesoForm;
import ieci.tecdoc.sgm.administracion.utils.Comprobador;
import ieci.tecdoc.sgm.administracion.utils.Defs;
import ieci.tecdoc.sgm.administracion.utils.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginInternoAction extends Action {

	private static final Logger logger = Logger.getLogger(LoginInternoAction.class);
	private static final String FORWARD_LOGIN = "login";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			HttpSession session = request.getSession();
			LoginAccesoForm loginForm = (LoginAccesoForm)form;

			String key = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
			if(Utilidades.isNuloOVacio(key)) {
				key = "";
			}

			String idAplicacion = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
			if(Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = "";
			}

			String usuario = null;
			String password = null;

			String singleSignOn = (String) session.getServletContext().getAttribute(Defs.PLUGIN_SINGLE_SIGN_ON);
			if ((singleSignOn != null) && (singleSignOn.equalsIgnoreCase("true"))) {

				// Autenticación Single-Sign On
				usuario = (String) session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_USUARIO);
			} else {

				// Autenticación Usuario / Password
				usuario = loginForm.getUsername();
				if (Utilidades.isNuloOVacio(usuario))
					usuario = "";

				password = loginForm.getPassword();
				if (Utilidades.isNuloOVacio(password))
					password = "";

				session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_USUARIO, usuario);
				session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_PASSWORD, password);
			}

			String idEntidad = loginForm.getIdEntidadInterno();
			if (Utilidades.isNuloOVacio(idEntidad))
				idEntidad = "";

			session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);
			session.setAttribute(Defs.PARAMETRO_ENTIDADES_INTERNO, idEntidad);

			ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
			List entidades = oServicio.obtenerEntidades();
			request.setAttribute(Defs.PARAMETRO_ENTIDADES, entidades);

			return mapping.findForward(Comprobador.comprobarInformacionInterno(request, key, idEntidad, idAplicacion, usuario, password, false));

		} catch(Exception e) {
			logger.error("Se ha producdo un error en loginInterno.do: ", e.fillInStackTrace());
			return mapping.findForward(FORWARD_LOGIN);
		}
	}

}
