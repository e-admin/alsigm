package ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UsuarioForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistradorBean;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EditarUsuarioAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(EditarUsuarioAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String id = (String)request.getParameter("idUsuario");
		if(id == null || id.equals("")) {
			id = (String)request.getSession(false).getAttribute("idUsuario");
		}

		if( id != null && !id.equals("")) {
            // Mostramos la página de editarUsuario.jsp
			OptionsBean perfiles = oServicio.obtenerPerfilesCombo(entidad);
			Utils.traducePerfiles(perfiles, LocaleFilterHelper.getCurrentLocale(request));
			request.setAttribute("perfil", perfiles);
			request.setAttribute("oficina", oServicio.obtenerOficinasCombo(entidad));
			//request.setAttribute("id", id);
			request.getSession(false).setAttribute("idUsuario", id);
			UsuarioForm usuarioForm = (UsuarioForm)form;
			UsuarioRegistradorBean usuario = null;
			if (isLdapMethod(entidad.getIdentificador()))
				usuario = oServicio.obtenerUsuarioLdap(Integer.parseInt(id), entidad);
			else
				usuario = oServicio.obtenerUsuario(Integer.parseInt(id), entidad);

			//request.setAttribute("nombreUsuario", usuario.getNombre());

			//Si no tenemos usuario en el formulario es la primera vez que se carga, rellenar con datos de negocio
			if(usuarioForm.getId()==null) {
				BeanUtils.copyProperties(usuarioForm, usuario);
			}
		}
		return mapping.findForward("success");
	}
}