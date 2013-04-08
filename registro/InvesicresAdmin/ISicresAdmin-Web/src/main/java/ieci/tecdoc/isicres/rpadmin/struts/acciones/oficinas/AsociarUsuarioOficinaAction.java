package ieci.tecdoc.isicres.rpadmin.struts.acciones.oficinas;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios.AsociarOficinaUsuarioAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.AsociarUsuariosForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.UsuariosRegistradores;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AsociarUsuarioOficinaAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(AsociarOficinaUsuarioAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String idOficina = (String)request.getParameter("idOficina");
		int idOfic = Integer.parseInt(idOficina);
//		OficinaBean oficina = oServicio.obtenerOficina(Integer
//				.parseInt(idOficina), SesionHelper.obtenerEntidad(request));

		String accion = (String)request.getParameter("accion");
		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		// Si accion==null mostramos el listado de posibles usuarios a asociar
		if(accion == null){
			String nombreOficina = (String)request.getParameter("nombreOficina");
			request.setAttribute("nombreOficina", nombreOficina);
			request.setAttribute("idOficina", idOficina);
			UsuariosRegistradores usuarios =  null;
			if(isLdapMethod(entidad.getIdentificador()))
				usuarios = oServicio.obtenerUsuariosLdapAsociacion(idOfic, entidad);
			else
				usuarios = oServicio.obtenerUsuariosAsociacion(idOfic, entidad);
			Utils.traducePerfiles(usuarios, LocaleFilterHelper.getCurrentLocale(request));
			request.setAttribute("usuarios", usuarios);
			return mapping.findForward("listado");
		}else{
			//Si accion!=null puede ser que queremos asociar usuarios a la oficina
			//o desasociar de la oficina un determinado usuario agregado.
			if(accion.equals("DESASOCIAR")) {
				try {
					String idUsuario = (String)request.getParameter("idUsuario");
					oServicio.desasociarOficinaAUsuario(Integer
							.parseInt(idUsuario), idOfic, entidad);
				}catch (ISicresRPAdminException e) {
					logger.error("Error en la aplicación", e);
					ActionErrors errores = new ActionErrors();
					ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
					errores.add("Error interno", error);
					saveErrors(request, errores);
				}
				return mapping.findForward("success");
			}else{
				AsociarUsuariosForm usuariosForm = (AsociarUsuariosForm)form;
				String [] users = usuariosForm.getIdsUser();
				oServicio.asociarUsuarioAOficinas(users, idOfic, entidad.getIdentificador());
				ActionForward fw = mapping.findForward("success");
				ActionForward listado = new ActionForward();
				listado.setName(fw.getName());
				listado.setPath(fw.getPath() + "?idOfic="+ idOficina +"&accion=ASOCIAR_USUARIO");
				return listado;
			}
		}
	}
}