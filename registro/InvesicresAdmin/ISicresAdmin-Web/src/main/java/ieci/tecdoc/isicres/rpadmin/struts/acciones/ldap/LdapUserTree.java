package ieci.tecdoc.isicres.rpadmin.struts.acciones.ldap;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConstants;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasAuthConfig;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;

public class LdapUserTree extends LdapBaseTree {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Obtener la información de acceso a LDAP
		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		UasAuthConfig  authCfg = UasConfigUtilLdap.createUasAuthConfig(entidad.getIdentificador());

		loadTree(request, Constantes.LDAP_USER_TREE, Constantes.LDAP_SERVICE_USER_TREE, authCfg.getUserStart(), LdapConstants.LDAP_TYPE_TREE_USER);

        return mapping.findForward("success");
	}

}
