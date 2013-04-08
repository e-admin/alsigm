package ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Oficinas;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistradorBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AsociarOficinaNuevaAUsuarioAction extends RPAdminWebAction {

	//private static final Logger logger = Logger.getLogger(AsociarOficinaNuevaAUsuarioAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String idUsuario = (String)request.getParameter("idUsuario");
		String idOficina = (String)request.getParameter("idOficina");
		String accion = (String)request.getParameter("accion");

		if(accion != null && accion.equals("ASOCIAR")) {
			request.setAttribute("idUsuario", idUsuario);
			oServicio.asociarOficinasAUsuario(Integer.parseInt(idUsuario), Integer.parseInt(idOficina) , entidad);
			return mapping.findForward("asociacion");
		} else {
			UsuarioRegistradorBean usuario = null;
			Oficinas oficinas = new Oficinas();
			if(isLdapMethod(entidad.getIdentificador())) {
				usuario = oServicio.obtenerUsuarioLdap(Integer.parseInt(idUsuario), entidad);
				oficinas = oServicio.obtenerOficinasDesasociadasAUsuarioLdap(usuario.getLdapGuid(), entidad);
			}else{
				usuario = oServicio.obtenerUsuario(Integer.parseInt(idUsuario), entidad);
				oficinas = oServicio.obtenerOficinasDesasociadasAUsuario(usuario.getId(), entidad);
			}

//			request.setAttribute("nombreUsuario", usuario.getNombre());
//			request.setAttribute("idUsuario", idUsuario);
			request.setAttribute("usuario", usuario);
			request.setAttribute("oficinas", oficinas);
			return mapping.findForward("success");
		}
	}

}
