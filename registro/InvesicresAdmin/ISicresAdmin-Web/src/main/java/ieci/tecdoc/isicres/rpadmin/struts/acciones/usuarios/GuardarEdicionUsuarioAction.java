package ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UsuarioForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistradorBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarEdicionUsuarioAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarEdicionUsuarioAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward af = null;
		try {
			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			UsuarioForm usuarioForm = (UsuarioForm) form;

			// Guardamos los datos del usuario
			String id = usuarioForm.getId();
			if (id != null && !id.equals("")) {
				ActionErrors errores = Utils.validarDatosUsuario(usuarioForm);
				if (errores != null) {
					saveErrors(request, errores);
					af = mapping.findForward("error");
				} else {
					usuarioForm.toUpperCase(request);
					UsuarioRegistradorBean usuarioEditado = new UsuarioRegistradorBean();
					BeanUtils.copyProperties(usuarioEditado, usuarioForm);
					usuarioEditado.setId(Integer.parseInt(id));

					if (logger.isDebugEnabled()) {
						logger.debug("El usuario modificado: " + usuarioEditado.toString());
					}

					if(isLdapMethod(entidad.getIdentificador()))
						oServicio.editarUsuarioLdap(usuarioEditado, entidad);
					else
						oServicio.editarUsuario(usuarioEditado, entidad);

					ActionMessages messages = new ActionMessages();
					ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.usuarios.resultado.editadoOK");
					messages.add("Result: ", mesage);
					saveMessages(request, messages);
					af = mapping.findForward("success");
				}
			}
		} catch (Exception e) {
			logger.error("Error en la aplicacion", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}
		return af;
	}
}
