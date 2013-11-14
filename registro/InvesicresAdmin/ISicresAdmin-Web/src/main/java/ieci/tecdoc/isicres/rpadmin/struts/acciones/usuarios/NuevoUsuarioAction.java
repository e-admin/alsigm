package ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.ldap.LdapUtil;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UsuarioForm;
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
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasAuthConfig;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class NuevoUsuarioAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(NuevoUsuarioAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		// Si es Ldap comprobar que la configuración es correcta
		if (isLdapMethod(entidad.getIdentificador())){
			try {
				// Obtener la configuración Ldap para la entidad
				UasAuthConfig  authCfg = UasConfigUtilLdap.createUasAuthConfig(entidad.getIdentificador());

				// Obtener la ruta de búsqueda
				String startNode = authCfg.getUserStart();

				// Comprobar que el nodo base de grupos existe
				LdapUtil.checkExistenciaNodo(entidad.getIdentificador(), startNode);
			} catch (Exception e) {
				logger.error("Error en la comprobación de existencia de nodo LDAP: " + e.getMessage(),e);

				ActionErrors errores = new ActionErrors();
				ActionError error = null;

				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.configuracion.usuarios.ldap.incorrecta");
				errores.add("Error interno", error);
				saveErrors(request, errores);
				ActionForward af = mapping.findForward("error");
				return af;
			}
		}

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		OptionsBean perfiles = oServicio.obtenerPerfilesCombo(entidad);
		Utils.traducePerfiles(perfiles, LocaleFilterHelper.getCurrentLocale(request));
		request.setAttribute("perfil", perfiles);
		request.setAttribute("oficina", oServicio.obtenerOficinasCombo(entidad));

		// Tipo de usuario (Normal o ldap)
		UsuarioForm usuarioForm = (UsuarioForm) form;
		if (isLdapMethod(entidad.getIdentificador())){
			usuarioForm.setTipoUsuario(String.valueOf(Usuario.LDAP_USER_TYPE));
		} else {
			usuarioForm.setTipoUsuario(String.valueOf(Usuario.SICRES_USER_TYPE));
		}
		return mapping.findForward("success");
	}

}
