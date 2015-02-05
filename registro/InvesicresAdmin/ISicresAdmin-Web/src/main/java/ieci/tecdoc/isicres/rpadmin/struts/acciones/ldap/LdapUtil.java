package ieci.tecdoc.isicres.rpadmin.struts.acciones.ldap;

import es.ieci.tecdoc.isicres.admin.core.ldap.LdapBasicFns;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapFilter;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapScope;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;

/**
 * Clase de utilidad para LDAP
 */
public class LdapUtil {

	public static void checkExistenciaNodo(String entidad, String start) throws Exception {

		// Crear una conexión LDAP
		LdapConnection ldapConn = new LdapConnection();
		LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
		ldapConn.open(connCfg);

		// Obtener el Dn de la raíz de búsqueda
		LdapBasicFns.findEntryDn(ldapConn, start, LdapScope.BASE, LdapFilter.getNullFilter(ldapConn));
	}
}
