package ieci.tecdoc.sgm.admsistema.action;

import java.util.ArrayList;
import java.util.List;

import ieci.tecdoc.sgm.admsistema.form.UsuarioForm;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.Perfil;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ModificarUsuarioAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ModificarUsuarioAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_MODIFICAR = "modificar";
	public static final String FORWARD_FAILURE = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				UsuarioForm usuarioForm = (UsuarioForm)form;

				String usuarioTrabajo = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
				
				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				
				if (usuarioForm == null || Utilidades.esNuloOVacio(usuarioForm.getAdministradorUsername())) {
					String username = request.getParameter(Defs.PARAMETRO_USUARIO_SELECCIONADO);
					
					Usuario datosUsuario = oServicio.obtenerUsuario(username);
					if (form == null) form = new UsuarioForm();
					((UsuarioForm)form).setAdministradorUsername(datosUsuario.getUsuario());
					((UsuarioForm)form).setAdministradorApellidos(datosUsuario.getApellidos());
					((UsuarioForm)form).setAdministradorNombre(datosUsuario.getNombre());
					((UsuarioForm)form).setAdministradorPassword("");
					((UsuarioForm)form).setAdministradorPasswordRepetido("");
					((UsuarioForm)form).setAdministradorPasswordNuevo("");
					
					if (!usuarioTrabajo.equals(datosUsuario.getUsuario()))
						request.setAttribute(Defs.PARAMETRO_ADMINISTRADOR_MODIFICAR, new Boolean(false));
					else request.setAttribute(Defs.PARAMETRO_ADMINISTRADOR_MODIFICAR, new Boolean(true)); 
					
					List entidades = Utilidades.entidadesAdministrar(usuarioTrabajo, oServicio.getEntidades(usuarioTrabajo));
					if (entidades == null)
						entidades = new ArrayList();
					Entidad[] entidadesUsuario = oServicio.getEntidades(username);
					boolean hayDiferentes = comprobarDiferencias(entidades, entidadesUsuario);
					
					List ents = new ArrayList();
					List perfiles = new ArrayList();
					Perfil[] tmpPerfiles = null;
					for (int i=0; i<entidades.size(); i++) {
						tmpPerfiles = oServicio.getPerfiles(username, ((Entidad)entidades.get(i)).getIdentificador());
						if (tmpPerfiles != null && tmpPerfiles.length > 0) {
							perfiles.add(tmpPerfiles);
							ents.add(entidades.get(i));
						}
					}
					
					Entidad[] entidadesPerm = new Entidad[ents.size()];
					Perfil[][] permisos = new Perfil[ents.size()][];
					for(int i=0; i<ents.size(); i++) {
						entidadesPerm[i] = (Entidad)ents.get(i);
						permisos[i] = (Perfil[])perfiles.get(i);
					}
					
					request.setAttribute(Defs.PARAMETRO_ADMINISTRADOR_ENTIDADES_CARGADAS, entidadesPerm);
					request.setAttribute(Defs.PARAMETRO_ADMINISTRADOR_PERMISOS_ENTIDADES_CARGADOS, permisos);
					request.setAttribute(Defs.PARAMETRO_ADMINISTRADOR_SIN_PERMISOS, new Boolean(hayDiferentes));
					
					request.getSession().setAttribute(Defs.PARAMETRO_ADMINISTRADOR_APLICACIONES_A_ELEGIR, oServicio.getAplicaciones());
					
					return mapping.findForward(FORWARD_MODIFICAR);

				} else {
					if (usuarioForm == null)
						return mapping.findForward(GLOBAL_FORWARD_ERROR);
					
					Usuario usuario = new Usuario();
					usuario.setNombre(usuarioForm.getAdministradorNombre());
					usuario.setApellidos(usuarioForm.getAdministradorApellidos());
					usuario.setUsuario(usuarioForm.getAdministradorUsername());
					oServicio.actualizaUsuario(usuario);
					
					if (!Utilidades.esNuloOVacio(usuarioForm.getAdministradorPassword()) &&
							!Utilidades.esNuloOVacio(usuarioForm.getAdministradorPassword()) &&
							!Utilidades.esNuloOVacio(usuarioForm.getAdministradorPassword())) { 
						usuario.setPassword(usuarioForm.getAdministradorPassword());
						oServicio.actualizaPasswordUsuario(usuario, usuarioForm.getAdministradorPasswordNuevo());
					}
					
					String[] entidades = Utilidades.obtenerEntidadesSeleccionadas(usuarioForm.getAdministradorEntidades());
					String[][] permisos = Utilidades.obtenerPermisosSeleccionados(usuarioForm.getAdministradorPermisosEntidades());
					
					List entidadesTrabajo = Utilidades.entidadesAdministrar(usuarioTrabajo, oServicio.getEntidades(usuarioTrabajo));
					for(int i=0; i<entidadesTrabajo.size(); i++)
						oServicio.bajaPerfil(((Entidad)entidadesTrabajo.get(i)).getIdentificador(), usuarioForm.getAdministradorUsername());
					
					for(int i=0; i<entidades.length; i++)
						oServicio.actualizaPerfiles(permisos[i], usuarioForm.getAdministradorUsername(), entidades[i]);
					
					request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.usuario.modificacion_correcta");
					return mapping.findForward(FORWARD_SUCCESS);
				}
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.usuario.modificacion_incorrecta");
				return mapping.findForward(FORWARD_FAILURE);
			} 
	}
	
	private static boolean comprobarDiferencias(List entidadesAdministrador, Entidad[] entidadesUsuario) {
		boolean encontrado;
		for(int i=0; i<entidadesUsuario.length; i++) {
			encontrado = false;
			for(int j=0; j<entidadesAdministrador.size(); j++) {
				if (((Entidad)entidadesAdministrador.get(j)).getIdentificador().equals(entidadesUsuario[i].getIdentificador())) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado)
				return true;
		}
		return false;
	}
}
