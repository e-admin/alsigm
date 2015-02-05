package ieci.tecdoc.isicres.rpadmin.struts.acciones.configuracionLdap;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.ConfiguracionLdapForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;


public class GuardarConfiguracionLdapAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ConfiguracionLdapForm configLdapForm = (ConfiguracionLdapForm)form;
		ActionForward af = null;
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		ActionErrors errores = Utils.validarDatosConfiguracionLdap(configLdapForm);

		if (errores != null) {
			saveErrors(request, errores);
			af = mapping.findForward("error");
		} else {
			CfgLdapConfig ldapConfig = configLdapForm.populate();

			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			oServicio.updateLdapConfig(entidad.getIdentificador(), ldapConfig);

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage(
					"ieci.tecdoc.sgm.rpadmin.configuracionLdap.resultado.modificadaOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);

			af = mapping.findForward("success");
		}

		return af;
	}
}