package ieci.tecdoc.sgm.admsistema.action;

import java.util.ArrayList;
import java.util.List;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.ServicioPermisosBackOffice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListadoUsuariosInternosAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ListadoUsuariosInternosAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_FAILURE = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				String usuario = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
				List entidades = Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario));
				List usuarios = Utilidades.obtenerUsuariosInternosEntidades(entidades);
	
				request.setAttribute(Defs.LISTADO_USUARIOS, usuarios);
				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.LISTADO_USUARIOS, new ArrayList());
				return mapping.findForward(FORWARD_FAILURE);
			}
	}
}
