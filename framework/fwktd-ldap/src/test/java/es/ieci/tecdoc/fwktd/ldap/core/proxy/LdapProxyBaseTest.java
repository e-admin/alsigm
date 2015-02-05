package es.ieci.tecdoc.fwktd.ldap.core.proxy;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConstants;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapScopeConstants;
import es.ieci.tecdoc.fwktd.ldap.core.exception.LdapException;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapErrorCodes;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;

/**
 * Test Junit del componente LDAP
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public abstract class LdapProxyBaseTest extends TestCase {

	/**
	 * Fichero de beans de Spring
	 */
	protected static String SPRING_BEANS_FILE ="es/ieci/tecdoc/fwktd/ldap/core/proxy/proxy-beans.xml";

	/**
	 * Identificador del cargador de configuracion de Open Ldap
	 */
	protected static String SPRING_LDAP_PROXY_OPEN_LDAP_CONFIG_BEAN="ldapContextConfigMapLoaderOpenLdap";

	/**
	 * Identificador del cargador de configuracion de Active Directory
	 */
	protected static String SPRING_LDAP_PROXY_ACTIVE_DIRECTORY_CONFIG_BEAN="ldapContextConfigMapLoaderActiveDirectory";

	/**
	 * Identificador del cargador de configuracion de Sun Directory Server
	 */
	protected static String SPRING_LDAP_PROXY_SUN_DIRECTORY_SERVER_CONFIG_BEAN="ldapContextConfigMapLoaderSunDirectoryServer";

	/**
	 * Obtener el nombre del proxy
	 * @return nombre del proxy
	 */
	protected abstract String getProxyBeanName();

	/**
	 * Permite obtener el proxy con el cargador de configuracion especificado
	 * @param contextConfigLoaderBeanName Identificador del cargador de configuracion
	 * @return proxy con el cargador de configuracion especificado
	 */
	protected LdapProxy getLdapProxy(String contextConfigLoaderBeanName){
		ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_BEANS_FILE);
		LdapProxy proxy = (LdapProxy)context.getBean(getProxyBeanName());
		LdapContextConfigLoader configLoader=(LdapContextConfigLoader) context.getBean(contextConfigLoaderBeanName);
		proxy.setLdapContextConfigLoader(configLoader);
		return proxy;
	}

	/**
	 * Permite obtener el proxy de Active Directory
	 * @return proxy de Active Directory
	 */
	protected LdapProxy getLdapAD(){
		return getLdapProxy(SPRING_LDAP_PROXY_ACTIVE_DIRECTORY_CONFIG_BEAN);
	}

	/**
	 * Permite obtener el proxy de Open Ldap
	 * @return proxy de Open Ldap
	 */
	protected LdapProxy getLdapOpenLdap(){
		return getLdapProxy(SPRING_LDAP_PROXY_OPEN_LDAP_CONFIG_BEAN);
	}

	/**
	 * Permite obtener el proxy de Sun Directory Server
	 * @return proxy de Sun Directory Server
	 */
	protected LdapProxy getLdapSunDirectoryServer(){
		return getLdapProxy(SPRING_LDAP_PROXY_SUN_DIRECTORY_SERVER_CONFIG_BEAN);
	}

	/**
	 * Test de autenticacion de Active Directory con contrasenia correcta
	 */
	public void testAuthenticateSpringActiveDirectoryCorrect(){
		LdapProxy proxy = getLdapAD();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("CN=nacho,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp");
		authenticationInfo.setUserCredentials("archivo_2007");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {

		}
		assertEquals("5f648285b040cd4dac2051885dfa5ffe", ret) ;
	}

	/**
	 * Test de autenticacion de Active Directory con contrasenia no correcta
	 */
	public void testAuthenticateSpringActiveDirectoryNotCorrect(){
		LdapProxy proxy = getLdapAD();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("CN=nacho,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp");
		authenticationInfo.setUserCredentials("archivo_2007k");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);
	}

	/**
	 * Test de autenticacion de Active Directory con usuario incorrecto
	 */
	public void testAuthenticateSpringActiveDirectoryNotFound(){
		LdapProxy proxy = getLdapAD();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("CN=nacho2,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp");
		authenticationInfo.setUserCredentials("archivo_2007");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);
	}

	/**
	 * Test de autenticacion de Active Directory con contrasenia vacia
	 */
	public void testAuthenticateSpringActiveDirectoryEmpty()  {
		LdapProxy proxy = getLdapAD();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("CN=nacho,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp");
		authenticationInfo.setUserCredentials("");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Sun Directory Server con contrasenia correcta
	 */
	public void testAuthenticateSpringSunDirectoryServerCorrect()  {
		LdapProxy proxy = getLdapSunDirectoryServer();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("uid=nacho,ou=Users,dc=iecisa,dc=corp");
		authenticationInfo.setUserCredentials("archivo_2007");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {

		}
		assertEquals("86b5a982-aa2011dc-802cebc5-dcc21b8e", ret) ;
	}

	/**
	 * Test de autenticacion de Sun Directory Server con contrasenia no correcta
	 */
	public void testAuthenticateSpringSunDirectoryServerNotCorrect(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("uid=nacho,ou=Users,dc=iecisa,dc=corp");
		authenticationInfo.setUserCredentials("ieciovi2k");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Sun Directory Server con usuario incorrecto
	 */
	public void testAuthenticateSpringSunDirectoryServerNotFound(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("uid=nacho2,ou=Users,dc=iecisa,dc=corp");
		authenticationInfo.setUserCredentials("ieciovi2");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Sun Directory Server con contrasenia vacia
	 */
	public void testAuthenticateSpringSunDirectoryServerEmpty(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("uid=nacho,ou=Users,dc=iecisa,dc=corp");
		authenticationInfo.setUserCredentials("");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Open Ldap con contrasenia correcta
	 */
	public void testAuthenticateSpringOpenLdapCorrect(){
		LdapProxy proxy = getLdapOpenLdap();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("cn=ngomila,ou=Usuarios,DC=portsdebalears,DC=com");
		authenticationInfo.setUserCredentials("ldap");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {

		}
		assertEquals("1", ret) ;
	}

	/**
	 * Test de autenticacion de Open Ldap con usuario incorrecto
	 */
	public void testAuthenticateSpringOpenLdapNotFound(){
		LdapProxy proxy = getLdapOpenLdap();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("cn=ngomila2,ou=Usuarios,DC=portsdebalears,DC=com");
		authenticationInfo.setUserCredentials("ldap");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Open Ldap con contrasenia no correcta
	 */
	public void testAuthenticateSpringOpenLdapNotCorrect(){
		LdapProxy proxy = getLdapOpenLdap();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("cn=ngomila,ou=Usuarios,DC=portsdebalears,DC=com");
		authenticationInfo.setUserCredentials("ldap2");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Open Ldap con contrasenia vacia
	 */
	public void testAuthenticateSpringOpenLdapEmpty(){
		LdapProxy proxy = getLdapOpenLdap();
		LdapAuthenticationUserDnVO authenticationInfo = new LdapAuthenticationUserDnVO();
		authenticationInfo.setUserDn("cn=ngomila,ou=Usuarios,DC=portsdebalears,DC=com");
		authenticationInfo.setUserCredentials("");
		String ret = null;
		try {
			ret = proxy.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Devuelve un objeto de busqueda a partir de los parametros
	 * @param base Nodo de busqueda
	 * @param expected limite de resultados esperados
	 * @param attr attributo
	 * @param value valor del atributo
	 * @param retAttrs valores a devolver
	 * @param scope Ambito de busqueda
	 * @return objeto de busqueda a partir de los parametros
	 */
	private LdapSearchVO getLdapSearchVO (final String base, final int expected, final String attr, final String value, final String [] retAttrs, final String scope) {
		LdapSearchVO ldapSearch = new LdapSearchVO();
		ldapSearch.setBase(base);
		ldapSearch.setScope(LdapScopeConstants.SCOPE_SUBTREE);
		ldapSearch.setExpected(expected);
		if ((retAttrs!=null)&&(retAttrs.length>0)){
			ldapSearch.setRetAttrs(retAttrs);
		}
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "person"));
		if ((attr!=null)&&(value!=null)){
			filter.and(new EqualsFilter(attr, value));
		}

		ldapSearch.setFilter(filter);

		return ldapSearch;
	}

	/**
	 * Test de busqueda Active Directory
	 */
	public void testFindSpringActiveDirectory(){
		LdapProxy proxy = getLdapAD();
		List result = null;
		try {
			result = proxy.find(getLdapSearchVO("",LdapConstants.NO_SEARCH_LIMIT, null, null, null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result) ;
		assertEquals(1551, result.size());
	}

	/**
	 * Test de busqueda Sun Directory Server
	 */
	public void testFindSpringSunDirectoryServer(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		List result = null;
		try {
			result = proxy.find(getLdapSearchVO("",LdapConstants.NO_SEARCH_LIMIT, null, null, null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result) ;
		assertEquals(43, result.size());
	}

	/**
	 * Test de busqueda Open Ldap
	 */
	public void testFindSpringOpenLdap(){
		LdapProxy proxy = getLdapOpenLdap();
		List result = null;
		try {
			result = proxy.find(getLdapSearchVO("",LdapConstants.NO_SEARCH_LIMIT, null, null, null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result) ;
		assertEquals(4, result.size());
	}

	/**
	 * Test de busqueda Active Directory con atributos
	 */
	public void testFindSpringAttributesActiveDirectory(){
		LdapProxy proxy = getLdapAD();
		List result = null;
		try {
			result = proxy.find(getLdapSearchVO("",LdapConstants.NO_SEARCH_LIMIT, null, null, new String [] {"sn"},LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result) ;
		assertEquals(1551, result.size());
	}

	/**
	 * Test de busqueda Sun Directory Server con atributos
	 */
	public void testFindSpringAttributesSunDirectoryServer(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		List result = null;
		try {
			result = proxy.find(getLdapSearchVO("",LdapConstants.NO_SEARCH_LIMIT, null, null, new String [] {"sn"},LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result) ;
		assertEquals(43, result.size());
	}

	/**
	 * Test de busqueda Open Ldap con atributos
	 */
	public void testFindSpringAttributesOpenLdap(){
		LdapProxy proxy = getLdapOpenLdap();
		List result = null;
		try {
			result = proxy.find(getLdapSearchVO("",LdapConstants.NO_SEARCH_LIMIT, null, null, new String [] {"sn"},LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result) ;
		assertEquals(4, result.size());
	}

	/**
	 * Test de busqueda Active Directory con limite
	 */
	public void testFindSpringActiveDirectoryWithLimit(){
		LdapProxy proxy = getLdapAD();
		List result = null;
		int limit = 1;
		try {
			result = proxy.find(getLdapSearchVO("",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result) ;
		if (result!=null) {
			assertEquals(limit, result.size());
		}
	}

	/**
	 * Test de busqueda Sun Directory Server con limite
	 */
	public void testFindSpringSunDirectoryServerWithLimit(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		List result = null;
		int limit = 1;
		try {
			result = proxy.find(getLdapSearchVO("",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result) ;
		if (result!=null) {
			assertEquals(limit, result.size());
		}
	}

	/**
	 * Test de busqueda Open Ldap con limite
	 */
	public void testFindSpringOpenLdapWithLimit(){
		LdapProxy proxy = getLdapOpenLdap();
		List result = null;
		int limit = 1;
		try {
			result = proxy.find(getLdapSearchVO("",limit,"cn","ngomila", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals(limit, result.size());
		}
	}

	/**
	 * Test de busqueda ldapguid Active Directory
	 */
	public void testFindEntryGuidActiveDirectory(){
		LdapProxy proxy = getLdapAD();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryGuid(getLdapSearchVO("",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null){
			assertEquals("5f648285b040cd4dac2051885dfa5ffe",result);
		}
	}

	/**
	 * Test de busqueda ldapguid Sun Directory Manager
	 */
	public void testFindEntryGuidSunDirectoryServer(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryGuid(getLdapSearchVO("",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("86b5a982-aa2011dc-802cebc5-dcc21b8e",result);
		}
	}


	/**
	 * Test de busqueda ldapguid Open Ldap
	 */
	public void testFindEntryGuidOpenLdap(){
		LdapProxy proxy = getLdapOpenLdap();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryGuid(getLdapSearchVO("",limit,"cn","ngomila", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("1",result);
		}
	}

	/**
	 * Test de busqueda dn Active Directory
	 */
	public void testFindEntryDnWithEmptyBaseActiveDirectory(){
		LdapProxy proxy = getLdapAD();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("CN=nacho,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp",result);
		}
	}

	/**
	 * Test de busqueda dn Active Directory con base
	 */
	public void testFindEntryDnWithBaseActiveDirectory(){
		LdapProxy proxy = getLdapAD();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("CN=Users",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("CN=nacho,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp",result);
		}
	}

	/**
	 * Test de busqueda dn Active Directory con base y scope one level
	 */
	public void testFindEntryDnWithBaseOneLevelActiveDirectory(){
		LdapProxy proxy = getLdapAD();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("CN=Users",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_ONELEVEL));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("CN=nacho,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp",result);
		}
	}

	/**
	 * Test de busqueda dn Sun Directory Manager
	 */
	public void testFindEntryDnWithEmptyBaseSunDirectoryServer(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("uid=nacho,ou=users,dc=iecisa,dc=corp",result);
		}
	}

	/**
	 * Test de busqueda dn Sun Directory Manager con base
	 */
	public void testFindEntryDnWithBaseSunDirectoryServer(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("ou=users",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("uid=nacho,ou=users,dc=iecisa,dc=corp",result);
		}
	}

	/**
	 * Test de busqueda dn Sun Directory Manager con base y scope one level
	 */
	public void testFindEntryDnWithBaseOneLevelSunDirectoryServer(){
		LdapProxy proxy = getLdapSunDirectoryServer();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("ou=users",limit,"cn","nacho", null,LdapScopeConstants.SCOPE_ONELEVEL));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("uid=nacho,ou=users,dc=iecisa,dc=corp",result);
		}
	}

	/**
	 * Test de busqueda dn Open Ldap
	 */
	public void testFindEntryDnWithEmptyBaseOpenLdap(){
		LdapProxy proxy = getLdapOpenLdap();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("",limit,"cn","ngomila", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("cn=ngomila,ou=Usuarios,dc=portsdebalears,dc=com",result);
		}
	}

	/**
	 * Test de busqueda dn Open Ldap con base
	 */
	public void testFindEntryDnWithBaseOpenLdap(){
		LdapProxy proxy = getLdapOpenLdap();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("ou=Usuarios",limit,"cn","ngomila", null,LdapScopeConstants.SCOPE_SUBTREE));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("cn=ngomila,ou=Usuarios,dc=portsdebalears,dc=com",result);
		}
	}

	/**
	 * Test de busqueda dn Open Ldap con base y scope one level
	 */
	public void testFindEntryDnWithBaseOneLevelOpenLdap(){
		LdapProxy proxy = getLdapOpenLdap();
		String result = null;
		int limit = 1;
		try {
			result = proxy.findEntryDn(getLdapSearchVO("ou=Usuarios",limit,"cn","ngomila", null,LdapScopeConstants.SCOPE_ONELEVEL));
		} catch (Exception e) {
		}
		assertNotNull(result);
		if (result!=null) {
			assertEquals("cn=ngomila,ou=Usuarios,dc=portsdebalears,dc=com",result);
		}
	}
}
