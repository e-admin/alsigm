package ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UsuarioOficinaForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OficinaBean;
import es.ieci.tecdoc.isicres.admin.beans.UsuariosRegistradores;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoUsuariosOficinaAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ListadoUsuariosOficinaAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		UsuarioOficinaForm usuarioOficinaForm = (UsuarioOficinaForm)form;

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String id = usuarioOficinaForm.getIdOfic();
		if(id == null || id.equals("")) {
			id = (String)request.getSession(false).getAttribute("idOficina");
			usuarioOficinaForm.setIdOfic(id);
			usuarioOficinaForm.setUsuarios(true);
			usuarioOficinaForm.setAgregados(true);
		}

		if( id != null && !id.equals("")) {
			String accion = request.getParameter("accion");
			if(accion!=null && accion.equals("ASOCIAR_USUARIO")){
				usuarioOficinaForm.setUsuarios(true);
				usuarioOficinaForm.setAgregados(true);
			}

			OficinaBean oficina = oServicio.obtenerOficina(Integer.parseInt(id), entidad);
			//request.setAttribute("nombreOficina", oficina.getNombre());
			request.getSession(false).setAttribute("nombreOficina", oficina.getNombre());

			// Para saber si existen usuarios disponibles para añadir a la oficina
			UsuariosRegistradores users =  new UsuariosRegistradores();
			UsuariosRegistradores usuarios = new UsuariosRegistradores();
			if(isLdapMethod(entidad.getIdentificador())){
				users = oServicio.obtenerUsuariosLdapAsociacion(oficina.getId(), entidad);
				usuarios = oServicio.obtenerUsuariosOficinaLdap(oficina.getId(), usuarioOficinaForm.isUsuarios(),
														usuarioOficinaForm.isAgregados(), entidad);
			}else{
				users =  oServicio.obtenerUsuariosAsociacion(oficina.getId(), entidad);
				usuarios = oServicio.obtenerUsuariosOficina(oficina.getId(), usuarioOficinaForm.isUsuarios(),
						usuarioOficinaForm.isAgregados(), entidad);
			}

			if(users.count() > 0)
				usuarioOficinaForm.setUsuariosDisponibles(true);
			else
				usuarioOficinaForm.setUsuariosDisponibles(false);


			Utils.traducePerfiles(usuarios, LocaleFilterHelper.getCurrentLocale(request));
			request.setAttribute("usuarios", usuarios);
			request.getSession(false).setAttribute("idOficina", id);
			return mapping.findForward("success");
		}
		return mapping.findForward("error");
	}

}
