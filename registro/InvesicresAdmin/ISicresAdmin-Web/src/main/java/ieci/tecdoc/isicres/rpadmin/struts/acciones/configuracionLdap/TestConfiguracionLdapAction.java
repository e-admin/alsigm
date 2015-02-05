package ieci.tecdoc.isicres.rpadmin.struts.acciones.configuracionLdap;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.ConfiguracionLdapForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;


public class TestConfiguracionLdapAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;
		ActionErrors errores = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		ConfiguracionLdapForm configLdapForm = (ConfiguracionLdapForm)form;
		CfgLdapConfig ldapConfig = configLdapForm.populate();

		try {
			oServicio.testLdapConfig(ldapConfig);

			ActionMessage mesage = new ActionMessage(
					"ieci.tecdoc.sgm.rpadmin.configuracionLdap.test.resultadoOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);

			af = mapping.findForward("success");
		} catch (ISicresRPAdminException ex) {
			ActionError error = new ActionError(
					"ieci.tecdoc.sgm.rpadmin.configuracionLdap.test.resultadoKO");
				errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		return af;
	}
}