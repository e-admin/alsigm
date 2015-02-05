package ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Oficina;
import es.ieci.tecdoc.isicres.admin.beans.Oficinas;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistradorBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AsociarOficinaUsuarioAction extends RPAdminWebAction {

	//private static final Logger logger = Logger.getLogger(AsociarOficinaUsuarioAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String idUsuario = (String)request.getParameter("idUsuario");
		String idOfcina = (String)request.getParameter("idOficina");
		String accion = (String)request.getParameter("accion");
		boolean isLdap = isLdapMethod(entidad.getIdentificador());
		if( accion != null && accion.equals("DESASOCIAR")) {
			if(isLdap){
				oServicio.desasociarOficinaAUsuarioLDAP(Integer.parseInt(idUsuario), Integer.parseInt(idOfcina), entidad);
			}else{
				oServicio.desasociarOficinaAUsuario(Integer.parseInt(idUsuario), Integer.parseInt(idOfcina), entidad);
			}
		}

		UsuarioRegistradorBean usuario = null;
		Oficinas oficinasAsociadas = new Oficinas();
		Oficinas oficinasDepto = new Oficinas();
		if(isLdap){
			usuario = oServicio.obtenerUsuarioLdap(Integer.parseInt(idUsuario), entidad);
			oficinasDepto = oServicio.obtenerOficinasUsuarioLdap(usuario.getLdapGuid(), entidad);
		} else {
			usuario = oServicio.obtenerUsuario(Integer.parseInt(idUsuario), entidad);
			Oficina oficinaDepto = oServicio.obtenerOficinaAsociadaADeptoUsuario(usuario.getId(),entidad);
			if(oficinaDepto != null)
				oficinasDepto.add(oficinaDepto);
		}
		oficinasAsociadas = oServicio.obtenerOficinasAsociadasAUsuario(usuario.getId(), entidad);

		Integer idOficPef = oServicio.obtenerIdOficinaPreferenteUsuario(usuario.getId(), entidad);
		request.setAttribute("idOficPref", idOficPef);

		request.setAttribute("isLdap", new Boolean(isLdapMethod(entidad.getIdentificador())));
		request.setAttribute("oficinasDepto", oficinasDepto.getLista());
		request.setAttribute("idUsuario", idUsuario);
		request.setAttribute("nombreUsuario", usuario.getNombre());
		request.setAttribute("oficinas", oficinasAsociadas);
		return mapping.findForward("success");
	}
}