package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccionesUsuarioAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(AccionesUsuarioAction.class);
	
	public static final String FORWARD_SIN_ACCION = "sinAccion";
	public static final String FORWARD_ELIMINAR = "eliminar";
	public static final String FORWARD_MODIFICAR = "modificar";
	public static final String FORWARD_BUSCAR = "buscar";
	public static final String FORWARD_NUEVO = "nuevo";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				String usuario = request.getParameter(Defs.PARAMETRO_USUARIO_SELECCIONADO);
				if (Utilidades.esNuloOVacio(usuario))
					usuario = "";
				String accion = request.getParameter(Defs.PARAMETRO_ACCION_USUARIO);
				if (Utilidades.esNuloOVacio(accion))
					accion = "";
				String busqueda = request.getParameter(Defs.PARAMETRO_BUSQUEDA_USUARIO);
				if (Utilidades.esNuloOVacio(busqueda))
					busqueda = "";
				
				if (Defs.ACCION_ELIMINAR_USUARIO.equals(accion)) {
					ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
					Usuario datosUsuario = new Usuario();
					datosUsuario.setUsuario(usuario);
					try {
						oServicio.bajaPerfil(usuario);
						oServicio.bajaUsuario(datosUsuario);
						request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.usuario.baja_usuario");
					} catch(Exception e) {
						request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.usuario.baja_usuario");
					}
					return mapping.findForward(FORWARD_ELIMINAR);
				} else if (Defs.ACCION_MODIFICAR_USUARIO.equals(accion)) {
					request.setAttribute(Defs.PARAMETRO_USUARIO_SELECCIONADO, usuario);
					return mapping.findForward(FORWARD_MODIFICAR);
				} else if (Defs.ACCION_BUSCAR_USUARIOS.equals(accion)) {
					return mapping.findForward(FORWARD_BUSCAR);
				} else if (Defs.ACCION_NUEVO_USUARIO.equals(accion)) {
					ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
					request.getSession().setAttribute(Defs.PARAMETRO_ADMINISTRADOR_APLICACIONES_A_ELEGIR, oServicio.getAplicaciones());
					return mapping.findForward(FORWARD_NUEVO);
				}

				return mapping.findForward(FORWARD_SIN_ACCION);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.general");
				return mapping.findForward(FORWARD_SIN_ACCION);
			}
	}
}
