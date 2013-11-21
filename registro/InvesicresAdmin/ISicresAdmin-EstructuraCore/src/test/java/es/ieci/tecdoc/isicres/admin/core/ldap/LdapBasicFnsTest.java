/**
 *
 */
package es.ieci.tecdoc.isicres.admin.core.ldap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/beans/ldap-server-beans.xml" })
public class LdapBasicFnsTest {

	@Test
	public void testFindEntryDn() throws Exception {

		LdapConnection conn = new LdapConnection();
		String url = "ldap://localhost:33389";
		String user = "uid=administrador,ou=User,ou=invesicres,ou=system";
		String password = "admin";
		LdapConnCfg ldapConnCfg = new LdapConnCfg(LdapEngine.OPENLDAP,
				LdapProvider.SUN, url, user, password, false, 0);
		ldapConnCfg.setLdapVersion(LdapConnection.LDAP_VERSION_3);
		conn.open(ldapConnCfg);

		String busqueda = "ou=User,ou=invesicres,ou=system";
		// Obtener el Dn de la raíz de búsqueda
		String entry = LdapBasicFns.findEntryDn(conn, busqueda, LdapScope.BASE,
				LdapFilter.getNullFilter(conn));

		Assert.assertNotNull(entry);
		Assert.assertTrue(busqueda.equalsIgnoreCase(entry));

	}

}
