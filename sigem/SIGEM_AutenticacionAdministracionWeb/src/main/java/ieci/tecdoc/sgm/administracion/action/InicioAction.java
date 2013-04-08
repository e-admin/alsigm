package ieci.tecdoc.sgm.administracion.action;

import ieci.tecdoc.sgm.administracion.utils.Comprobador;
import ieci.tecdoc.sgm.administracion.utils.Defs;
import ieci.tecdoc.sgm.administracion.utils.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.admsesion.administracion.ServicioAdministracionSesionesAdministrador;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.core.services.idioma.ConstantesIdioma;
import ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

import java.io.IOException;
import java.util.ArrayList;
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

public class InicioAction extends Action{

	private static final Logger logger = Logger.getLogger(InicioAction.class);
	private static final String FORWARD_LOGIN = "login";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			HttpSession session = request.getSession();

			ArrayList idiomas = LectorIdiomas.getIdiomas();
			session.setAttribute(ConstantesIdioma.IDIOMAS_DISPONIBLES, idiomas);

			String key = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
			if(Utilidades.isNuloOVacio(key)) {
				key = "";
			}

			String idEntidad = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
			if(Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = (String) session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
				if(Utilidades.isNuloOVacio(idEntidad)) {
					idEntidad = "";
				}
			}

			String idAplicacion = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
			if(Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = (String) session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
				if(Utilidades.isNuloOVacio(idAplicacion)) {
					idAplicacion = "";
				}
			}

			session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION, idAplicacion);

			ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
			List entidades = oServicio.obtenerEntidades();
			request.setAttribute(Defs.PARAMETRO_ENTIDADES, entidades);

			if(!Utilidades.isNuloOVacio(key)) {
				ServicioAdministracionSesionesAdministrador oCliente = LocalizadorServicios.getServicioAdministracionSesionesAdministrador();
				ieci.tecdoc.sgm.core.services.admsesion.administracion.Sesion datosSesion = oCliente.obtenerSesion(key);
				if (datosSesion != null) {
					if (Sesion.TIPO_USUARIO_ADMINISTRADOR == datosSesion.getTipoUsuario()) {
						session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);
						return mapping.findForward(Comprobador.comprobarInformacion(request, key, idEntidad, idAplicacion, null, null, false));
					} else {
						idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
						if(Utilidades.isNuloOVacio(idEntidad)) {
							idEntidad = "";
						}
						session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);
						return mapping.findForward(Comprobador.comprobarInformacionInterno(request, key, idEntidad, idAplicacion, null, null, false));
					}
				}
			}
			session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);
			return mapping.findForward(Comprobador.comprobarInformacion(request, key, idEntidad, idAplicacion, null, null, false));
		} catch(Exception e) {
			logger.error("Se ha producdo un error en inicio.do: ", e.fillInStackTrace());
			return mapping.findForward(FORWARD_LOGIN);
		}
	}

}
