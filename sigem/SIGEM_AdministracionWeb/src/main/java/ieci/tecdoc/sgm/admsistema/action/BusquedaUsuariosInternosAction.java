package ieci.tecdoc.sgm.admsistema.action;

import java.util.ArrayList;
import java.util.List;

import ieci.tecdoc.sgm.admsistema.bean.UsuarioEntidad;
import ieci.tecdoc.sgm.admsistema.form.BusquedaForm;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BusquedaUsuariosInternosAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(BusquedaUsuariosInternosAction.class);
	
	public static final String FORWARD_BUSQUEDA = "busqueda";
	public static final String FORWARD_BUSQUEDA_TODOS = "buscar_vacio";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				BusquedaForm busquedaForm = (BusquedaForm)form;
				
				String usuario = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
				
				String busqueda = busquedaForm.getBusqueda();
				
				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				List entidades = Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario));
				List usuarios = Utilidades.obtenerUsuariosInternosEntidades(entidades);			
				
				if (Utilidades.esNuloOVacio(busqueda)) {
					request.setAttribute(Defs.LISTADO_USUARIOS, usuarios);
					return mapping.findForward(FORWARD_BUSQUEDA_TODOS);
				} else { 
					if (busqueda.length() >= 3) { 
						if (usuarios != null) {
							for(int i=usuarios.size()-1; i>=0; i--) {
								if (   ((UsuarioEntidad)usuarios.get(i)).getNombre().toUpperCase().indexOf(busqueda.toUpperCase()) == -1 &&  
										((UsuarioEntidad)usuarios.get(i)).getApellidos().toUpperCase().indexOf(busqueda.toUpperCase()) == -1 &&
										((UsuarioEntidad)usuarios.get(i)).getUsuario().toUpperCase().indexOf(busqueda.toUpperCase()) == -1 )
									usuarios.remove(i);
							}
						} else {
							usuarios = new ArrayList();
						}
					} else {
						request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.usuario.busqueda_corta");
					}
				}
				request.setAttribute(Defs.LISTADO_USUARIOS, usuarios);
				return mapping.findForward(FORWARD_BUSQUEDA);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.LISTADO_ENTIDADES, new ArrayList());
				return mapping.findForward(FORWARD_BUSQUEDA);
			}
	}
}
