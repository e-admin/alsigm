package ieci.tecdoc.isicres.rpadmin.struts.acciones.configuracionLdap;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.ConfiguracionLdapForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

/**
 * Acción encargada de mostrar los datos actuales de configuración de LDAP
 * @author Iecisa
 * @version $Revision$
 *
 */

public class EditarConfiguracionLdapAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		// Se obtienen los tipos de Servidor LDAP que es posible configurar
		OptionsBean tiposServidor = Utils.obtenerTiposServidorCombo(LocaleFilterHelper.getCurrentLocale(request));
		request.getSession(false).setAttribute("tiposServidor", tiposServidor);

		// Se obtiene la configuración LDAP actual
		CfgLdapConfig ldapConfig = oServicio.getLdapConfig(entidad.getIdentificador());

		// Se rellenan los datos del formulario con los configurados actualmente
		ConfiguracionLdapForm configLdapForm = (ConfiguracionLdapForm)form;
		configLdapForm.set(ldapConfig);

		return mapping.findForward("success");
	}
}