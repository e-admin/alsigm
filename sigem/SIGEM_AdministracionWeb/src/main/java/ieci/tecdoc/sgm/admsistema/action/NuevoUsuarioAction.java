package ieci.tecdoc.sgm.admsistema.action;

import java.util.Date;

import ieci.tecdoc.sgm.admsistema.form.UsuarioForm;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.Perfil;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NuevoUsuarioAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(NuevoUsuarioAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			int i=0, j=0;
			String username = null;
			String[] entidades = null;
			String[][] permisos = null;
			
			try {
				UsuarioForm usuarioForm = (UsuarioForm)form;
				
				entidades = Utilidades.obtenerEntidadesSeleccionadas(usuarioForm.getAdministradorEntidades());
				permisos = Utilidades.obtenerPermisosSeleccionados(usuarioForm.getAdministradorPermisosEntidades());
				username = usuarioForm.getAdministradorUsername();
				
				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				
				Usuario usuario = new Usuario();
				usuario.setNombre(usuarioForm.getAdministradorNombre());
				usuario.setApellidos(usuarioForm.getAdministradorApellidos());
				usuario.setUsuario(username);
				usuario.setPassword(usuarioForm.getAdministradorPassword());
				usuario.setFechaAlta(new Date());
				oServicio.altaUsuario(usuario);

				for (i=0; i<entidades.length; i++) {
					oServicio.actualizaPerfiles(permisos[i], username, entidades[i]);
				}
				
				request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.usuario.alta_usuario");
				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				if (Utilidades.esNuloOVacio(username)) {
					try {
						ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
						Usuario usuario = new Usuario();
						usuario.setNombre(username);
						oServicio.bajaUsuario(usuario);
					
						Perfil perfil = new Perfil();
						perfil.setIdUsuario(username);
						for(int k=i; k>=0; k--) {
							perfil.setIdEntidad(entidades[k]);
							for(int m=j; m>=0; m--) {
								perfil.setIdAplicacion(permisos[i][m]);
								oServicio.bajaPerfil(perfil);
							}
						}				
					} catch(Exception ex) {}
				}
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.usuario.alta_usuario");
				return mapping.findForward(FORWARD_ERROR);
			} 
	}
}
