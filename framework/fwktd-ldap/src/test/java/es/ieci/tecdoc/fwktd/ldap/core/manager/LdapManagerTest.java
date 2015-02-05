package es.ieci.tecdoc.fwktd.ldap.core.manager;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.OrFilter;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.config.LdapManagerConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapGroupAttributeConstants;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapUserAttributeConstants;
import es.ieci.tecdoc.fwktd.ldap.core.exception.LdapException;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapErrorCodes;
import es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserNameVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchManagerVO;

/**
 * Test Junit del componente manager LDAP
 * @author Iecisa
 * @version $Revision: 114 $
 *
 */
public class LdapManagerTest extends TestCase {

	/**
	 * Fichero de beans de Spring
	 */
	protected static String SPRING_BEANS_FILE ="es/ieci/tecdoc/fwktd/ldap/core/manager/manager-beans.xml";

	/**
	 * Identificador del cargador de configuracion de Open Ldap
	 */
	protected static String SPRING_LDAP_PROXY_OPEN_LDAP_CONFIG_BEAN="ldapContextConfigMapLoaderOpenLdap";

	/**
	 * Identificador del cargador de configuracion del manager de Open Ldap
	 */
	protected static String SPRING_LDAP_MANAGER_OPEN_LDAP_CONFIG_BEAN="ldapManagerConfigMapLoaderOpenLdap";

	/**
	 * Identificador del cargador de configuracion de Active Directory
	 */
	protected static String SPRING_LDAP_PROXY_ACTIVE_DIRECTORY_CONFIG_BEAN="ldapContextConfigMapLoaderActiveDirectory";

	/**
	 * Identificador del cargador de configuracion del manager de Active Directory
	 */
	protected static String SPRING_LDAP_MANAGER_ACTIVE_DIRECTORY_CONFIG_BEAN="ldapManagerConfigMapLoaderActiveDirectory";


	/**
	 * Identificador del cargador de configuracion de Sun Directory Server
	 */
	protected static String SPRING_LDAP_PROXY_SUN_DIRECTORY_SERVER_CONFIG_BEAN="ldapContextConfigMapLoaderSunDirectoryServer";

	/**
	 * Identificador del cargador de configuracion del manager de Sun Directory Server
	 */
	protected static String SPRING_LDAP_MANAGER_SUN_DIRECTORY_SERVER_CONFIG_BEAN="ldapManagerConfigMapLoaderSunDirectoryServer";

	/**
	 * Obtener el nombre del proxy
	 * @return nombre del proxy
	 */
	protected String getProxyBeanName(){
		return "ldapProxySun";
	}

	/**
	 * Permite obtener el manager con el cargador de configuracion especificado
	 * @param contextConfigLoaderBeanName Identificador del cargador de configuracion
	 * @return proxy con el cargador de configuracion especificado
	 */
	protected LdapManager getLdapManager(String contextConfigLoaderBeanName, String managerConfigLoaderBeanName){
		ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_BEANS_FILE);
		LdapProxy proxy = (LdapProxy)context.getBean(getProxyBeanName());
		LdapContextConfigLoader configLoader=(LdapContextConfigLoader) context.getBean(contextConfigLoaderBeanName);
		proxy.setLdapContextConfigLoader(configLoader);
		LdapManagerConfigLoader mgrConfigLoader=(LdapManagerConfigLoader) context.getBean(managerConfigLoaderBeanName);
		LdapManager manager = (LdapManager)context.getBean("ldapManagerSun");
		manager.setLdapManagerConfigLoader(mgrConfigLoader);
		return manager;
	}

	/**
	 * Permite obtener el manager de Active Directory
	 * @return manager de Active Directory
	 */
	protected LdapManager getLdapAD(){
		return getLdapManager(SPRING_LDAP_PROXY_ACTIVE_DIRECTORY_CONFIG_BEAN, SPRING_LDAP_MANAGER_ACTIVE_DIRECTORY_CONFIG_BEAN);
	}

	/**
	 * Permite obtener el manager de Open Ldap
	 * @return manager de Open Ldap
	 */
	protected LdapManager getLdapOpenLdap(){
		return getLdapManager(SPRING_LDAP_PROXY_OPEN_LDAP_CONFIG_BEAN,SPRING_LDAP_MANAGER_OPEN_LDAP_CONFIG_BEAN);
	}

	/**
	 * Permite obtener el manager de Sun Directory Server
	 * @return manager de Sun Directory Server
	 */
	protected LdapManager getLdapSunDirectoryServer(){
		return getLdapManager(SPRING_LDAP_PROXY_SUN_DIRECTORY_SERVER_CONFIG_BEAN, SPRING_LDAP_MANAGER_SUN_DIRECTORY_SERVER_CONFIG_BEAN);
	}

	/**
	 * Test de autenticacion de Active Directory con contrasenia correcta
	 */
	public void testAuthenticateSpringActiveDirectoryCorrect(){
		LdapManager mgr = getLdapAD();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("nacho");
		authenticationInfo.setUserCredentials("archivo_2007");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {

		}
		assertEquals("5f648285b040cd4dac2051885dfa5ffe", ret) ;
	}

	/**
	 * Test de autenticacion de Active Directory con contrasenia no correcta
	 */
	public void testAuthenticateSpringActiveDirectoryNotCorrect(){
		LdapManager mgr = getLdapAD();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("nacho");
		authenticationInfo.setUserCredentials("archivo_2007k");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);
	}

	/**
	 * Test de autenticacion de Active Directory con usuario no encontrado
	 */
	public void testAuthenticateSpringActiveDirectoryNotFound(){
		LdapManager mgr = getLdapAD();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("nacho2");
		authenticationInfo.setUserCredentials("archivo_2007k");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10011_NODE_NOT_FOUND_ERROR, ((LdapException)e).getErrCode());
		}
		assertNull(ret);
	}

	/**
	 * Test de autenticacion de Active Directory con contrasenia vacia
	 */
	public void testAuthenticateSpringActiveDirectoryEmpty()  {
		LdapManager mgr = getLdapAD();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("nacho");
		authenticationInfo.setUserCredentials("");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Sun Directory Server con contrasenia correcta
	 */
	public void testAuthenticateSpringSunDirectoryServerCorrect()  {
		LdapManager mgr = getLdapSunDirectoryServer();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("nacho");
		authenticationInfo.setUserCredentials("archivo_2007");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {

		}
		assertEquals("86b5a982-aa2011dc-802cebc5-dcc21b8e", ret) ;
	}

	/**
	 * Test de autenticacion de Sun Directory Server con contrasenia no correcta
	 */
	public void testAuthenticateSpringSunDirectoryServerNotCorrect(){
		LdapManager mgr = getLdapSunDirectoryServer();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("nacho");
		authenticationInfo.setUserCredentials("ieciovi2k");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Sun Directory Server con usuario no encontrado
	 */
	public void testAuthenticateSpringSunDirectoryServerNotFound(){
		LdapManager mgr = getLdapSunDirectoryServer();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("nacho2");
		authenticationInfo.setUserCredentials("ieciovi2");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10011_NODE_NOT_FOUND_ERROR, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Sun Directory Server con contrasenia vacia
	 */
	public void testAuthenticateSpringSunDirectoryServerEmpty(){
		LdapManager mgr = getLdapSunDirectoryServer();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("nacho");
		authenticationInfo.setUserCredentials("");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Open Ldap con contrasenia correcta
	 */
	public void testAuthenticateSpringOpenLdapCorrect(){
		LdapManager mgr = getLdapOpenLdap();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("ngomila");
		authenticationInfo.setUserCredentials("ldap");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {

		}
		assertEquals("1", ret) ;

	}

	/**
	 * Test de autenticacion de Open Ldap con contrasenia no correcta
	 */
	public void testAuthenticateSpringOpenLdapNotCorrect(){
		LdapManager mgr = getLdapOpenLdap();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("ngomila");
		authenticationInfo.setUserCredentials("ldap2");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Open Ldap con usuario no encontrado
	 */
	public void testAuthenticateSpringOpenLdapNotFound(){
		LdapManager mgr = getLdapOpenLdap();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("ngomila2");
		authenticationInfo.setUserCredentials("ldap");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10011_NODE_NOT_FOUND_ERROR, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de autenticacion de Open Ldap con contrasenia vacia
	 */
	public void testAuthenticateSpringOpenLdapEmpty(){
		LdapManager mgr = getLdapOpenLdap();
		LdapAuthenticationUserNameVO authenticationInfo = new LdapAuthenticationUserNameVO();
		authenticationInfo.setUserName("ngomila");
		authenticationInfo.setUserCredentials("");
		String ret = null;
		try {
			ret = mgr.authenticate(authenticationInfo) ;
		} catch (Exception e) {
			assertEquals(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, ((LdapException)e).getErrCode());
		}
		assertNull(ret);

	}

	/**
	 * Test de busqueda de un usuario para Active Directory
	 */
	public void testFindUserActiveDirectory() throws Exception{
		LdapManager mgr = getLdapAD();
		List ret = null;
		OrFilter filter = new OrFilter();
		filter.or(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GIVEN_NAME), "*nacho*"));
		filter.or(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_SURNAME), "*nacho*"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddUserClasses(true);
			ret = mgr.findUser(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(1, ret.size());
		assertEquals("5f648285b040cd4dac2051885dfa5ffe", ((LdapEntryVO)ret.get(0)).getAttributeSingleValue(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GUID))) ;

	}

	/**
	 * Test de busqueda de varios usuarios para Active Directory
	 */
	public void testFindUsersActiveDirectory() throws Exception{
		LdapManager mgr = getLdapAD();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GIVEN_NAME), "*a*"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddUserClasses(true);
			ret = mgr.findUsers(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(22, ret.size());
	}

	/**
	 * Test de busqueda de un usuario para Sun Directory Manager
	 */
	public void testFindUserSunDirectoryManager() throws Exception{
		LdapManager mgr = getLdapSunDirectoryServer();
		List ret = null;
		OrFilter filter = new OrFilter();
		filter.or(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GIVEN_NAME), "*nacho*"));
		filter.or(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_SURNAME), "*nacho*"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddUserClasses(true);
			ret = mgr.findUser(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(1, ret.size());
		assertEquals("86b5a982-aa2011dc-802cebc5-dcc21b8e", ((LdapEntryVO)ret.get(0)).getAttributeSingleValue(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GUID))) ;

	}

	/**
	 * Test de busqueda de varios usuarios para Sun Directory Manager
	 */
	public void testFindUsersSunDirectoryManager() throws Exception{
		LdapManager mgr = getLdapSunDirectoryServer();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GIVEN_NAME), "*a*"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddUserClasses(true);
			ret = mgr.findUsers(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(11, ret.size());
	}

	/**
	 * Test de busqueda de un usuario para Open Ldap
	 */
	public void testFindUserOpenLdap() throws Exception{
		LdapManager mgr = getLdapOpenLdap();
		List ret = null;
		OrFilter filter = new OrFilter();
		filter.or(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GIVEN_NAME), "*ngomila*"));
		filter.or(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_SURNAME), "*ngomila*"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddUserClasses(true);
			ret = mgr.findUser(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(1, ret.size());
		assertEquals("1", ((LdapEntryVO)ret.get(0)).getAttributeSingleValue(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GUID))) ;

	}

	/**
	 * Test de busqueda de varios usuarios para Open Ldap
	 */
	public void testFindUsersOpenLdap() throws Exception{
		LdapManager mgr = getLdapOpenLdap();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new LikeFilter(mgr.getUsersReturningAttribute(LdapUserAttributeConstants.LDAP_USER_ATTRIBUTE_GIVEN_NAME), "*a*"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddUserClasses(true);
			ret = mgr.findUsers(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(3, ret.size());
	}

	/**
	 * Test de busqueda de varios grupos de usuario para Active Directory
	 */
	public void testFindUserGroupsActiveDirectory() throws Exception{
		LdapManager mgr = getLdapAD();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(
						mgr.getGroupsReturningAttribute(LdapGroupAttributeConstants.LDAP_GROUP_ATTRIBUTE_MEMBER),
						"CN=nacho,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddGroupClasses(true);
			ret = mgr.findUserGroups(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(2, ret.size());
	}

	/**
	 * Test de busqueda de varios grupos de usuario para Active Directory con base vacio
	 */
	public void testFindUserGroupsActiveDirectoryWithEmptyBase() throws Exception{
		LdapManager mgr = getLdapAD();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(
						mgr.getGroupsReturningAttribute(LdapGroupAttributeConstants.LDAP_GROUP_ATTRIBUTE_MEMBER),
						"CN=nacho,CN=Users,DC=SERVIDOR-GDOC3,DC=iecisa,DC=corp"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddGroupClasses(true);
			searchMgrVO.setBase("");
			ret = mgr.findUserGroups(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(4, ret.size());
	}

	/**
	 * Test de busqueda de grupos de usuario para Sun Directory Manager
	 */
	public void testFindUserGroupsSunDirectoryManager() throws Exception{
		LdapManager mgr = getLdapSunDirectoryServer();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(
						mgr.getGroupsReturningAttribute(LdapGroupAttributeConstants.LDAP_GROUP_ATTRIBUTE_MEMBER),
						"uid=nacho,ou=Users,dc=iecisa,dc=corp"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddGroupClasses(true);
			ret = mgr.findUserGroups(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(1, ret.size());
	}

	/**
	 * Test de busqueda de grupos de usuario para Sun Directory Manager con base vacio
	 */
	public void testFindUserGroupsSunDirectoryManagerWithEmptyBase() throws Exception{
		LdapManager mgr = getLdapSunDirectoryServer();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(
						mgr.getGroupsReturningAttribute(LdapGroupAttributeConstants.LDAP_GROUP_ATTRIBUTE_MEMBER),
						"uid=nacho,ou=Users,dc=iecisa,dc=corp"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setBase("");
			searchMgrVO.setAddGroupClasses(true);
			ret = mgr.findUserGroups(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(1, ret.size());
	}

	/**
	 * Test de busqueda de grupos de usuario para Open Ldap
	 */
	public void testFindUserGroupsOpenLdap() throws Exception{
		LdapManager mgr = getLdapOpenLdap();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(
						mgr.getGroupsReturningAttribute(LdapGroupAttributeConstants.LDAP_GROUP_ATTRIBUTE_MEMBER),
						"cn=ngomila,ou=Usuarios,DC=portsdebalears,DC=com"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setAddGroupClasses(true);
			ret = mgr.findUserGroups(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(2, ret.size());
	}

	/**
	 * Test de busqueda de grupos de usuario para Open Ldap con base vacio
	 */
	public void testFindUserGroupsOpenLdapWithEmptyBase() throws Exception{
		LdapManager mgr = getLdapOpenLdap();
		List ret = null;
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(
						mgr.getGroupsReturningAttribute(LdapGroupAttributeConstants.LDAP_GROUP_ATTRIBUTE_MEMBER),
						"cn=ngomila,ou=Usuarios,DC=portsdebalears,DC=com"));
		try {
			LdapSearchManagerVO searchMgrVO = new LdapSearchManagerVO();
			searchMgrVO.setFilter(filter);
			searchMgrVO.setBase("");
			searchMgrVO.setAddGroupClasses(true);
			ret = mgr.findUserGroups(searchMgrVO);
		} catch (Exception e) {

		}
		assertEquals(2, ret.size());
	}
}
