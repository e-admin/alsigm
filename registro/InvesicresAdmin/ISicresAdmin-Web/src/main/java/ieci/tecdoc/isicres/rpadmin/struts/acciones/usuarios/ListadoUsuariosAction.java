package ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Oficina;
import es.ieci.tecdoc.isicres.admin.beans.Oficinas;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistrador;
import es.ieci.tecdoc.isicres.admin.beans.UsuariosRegistradores;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoUsuariosAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ListadoUsuariosAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		UsuariosRegistradores usuarios = null;

		if (isLdapMethod(entidad.getIdentificador())){
			usuarios = oServicio.obtenerUsuariosLdap(entidad);
			Utils.traducePerfiles(usuarios, LocaleFilterHelper.getCurrentLocale(request));
		}else{
			usuarios = oServicio.obtenerUsuarios(entidad);
			Utils.traducePerfiles(usuarios, LocaleFilterHelper.getCurrentLocale(request));
		}

		Oficinas oficinas  = oServicio.obtenerOficinas(entidad);

		//Meter las oficinas en un Map
		Map oficinasMap = new HashedMap();
		if(oficinas != null && oficinas.getLista() != null && oficinas.getLista().size() > 0){
			for (Iterator itOficinas = oficinas.getLista().iterator(); itOficinas.hasNext();) {
				Oficina oficina = (Oficina) itOficinas.next();
				if(oficina != null){
					oficinasMap.put(new Integer(oficina.getId()), oficina);
				}
			}
		}

		if(usuarios != null && usuarios.getLista() != null && usuarios.getLista().size() > 0){
			for (Iterator it = usuarios.getLista().iterator(); it.hasNext();) {
				UsuarioRegistrador usuario = (UsuarioRegistrador) it.next();
				Oficina oficina = new Oficina();

				Integer idOficinaPref = oServicio.obtenerIdOficinaPreferenteUsuario(usuario.getId(), entidad);
				if(idOficinaPref != null){
					oficina = (Oficina) oficinasMap.get(idOficinaPref);
					if(oficina != null)
						usuario.setOficinaRegistro(oficina.getAbreviatura());
				}
				else
					usuario.setOficinaRegistro(null);
			}
		}

		request.setAttribute("usuarios", usuarios);
		return mapping.findForward("success");
	}
}