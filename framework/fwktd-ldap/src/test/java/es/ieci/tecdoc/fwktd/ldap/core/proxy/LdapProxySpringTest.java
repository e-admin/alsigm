package es.ieci.tecdoc.fwktd.ldap.core.proxy;


/**
 * Test Junit del componente LDAP con el proxy Spring
 * @author Iecisa
 * @version $Revision: 72 $
 *
 */
public class LdapProxySpringTest extends LdapProxyBaseTest {

	/**
	 * Logger para la clase
	 */
	private static final String PROXY_BEAN = "ldapProxySpring";
	
	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxyBaseTest#getProxyBeanName()
	 */
	protected String getProxyBeanName() {
		return PROXY_BEAN;
	}

	
}
