package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import es.ieci.tecdoc.isicres.ws.legacy.service.session.ISWebServiceSessionSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.WSSession;

/**
 * Pruebas de integración para verificar el correcto funcionamiento del WS con
 * interfaz <code>ISWebServiceSessionSoap</code>.
 * 
 * @see ISWebServiceSessionSoap
 * 
 * @author IECISA
 * 
 */
public class ISWebServiceSessionSoapTest extends
		AbstractDependencyInjectionSpringContextTests {

	private static final String CLIENT_WEB_SERVICE_SESSION = "clientWebServiceSessionSoap";

	protected String[] getConfigLocations() {
		String[] result = new String[] { "beans/appContextTest.xml" };
		return result;
	}

	/**
	 * Verifica que el usuario <i>operador1</i> puede autenticarse en la oficina
	 * con código <i>001</i>.
	 */
	public void testLoginOperador1() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSSession wsLogin = client.wsLogin(security);

		assertNotNull(wsLogin);
		assertEquals(6, wsLogin.getId());
		assertEquals("1, OPERADOR", wsLogin.getFullName());
		assertEquals("operador1", wsLogin.getName());
		assertEquals("001", wsLogin.getOfficeCode());
		assertEquals("OFICINA DE REGISTRO1", wsLogin.getOfficeName());
		assertEquals(1, wsLogin.getProfile());
	}

	/**
	 * Verifica que el usuario <i>operador1</i> no puede autenticarse en la
	 * oficina con código <i>003</i> por no estar asociado a ella.
	 */
	public void testLoginOperador1InWrongOffice() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador1");
		value.setPassword("operador1");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		try {
			client.wsLogin(security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}

		fail();
	}

	/**
	 * Verifica que el usuario <i>operador2</i> puede autenticarse en la oficina
	 * con código <i>999</i>.
	 */
	public void testLoginOperador2() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		WSSession wsLogin = client.wsLogin(security);

		assertNotNull(wsLogin);
		assertEquals(7, wsLogin.getId());
		assertEquals("2, OPERADOR", wsLogin.getFullName());
		assertEquals("operador2", wsLogin.getName());
		assertEquals("999", wsLogin.getOfficeCode());
		assertEquals("OFICINA DE REGISTRO TELEMATICO", wsLogin.getOfficeName());
		assertEquals(1, wsLogin.getProfile());
	}

	/**
	 * Verifica que el usuario <i>operador2</i> no puede autenticarse en la
	 * oficina con código <i>003</i> por no estar asociado a ella.
	 */
	public void testLoginOperador2InWrongOffice() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("operador2");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		try {
			client.wsLogin(security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Verifica que el usuario <i>operador2</i> no puede autenticarse en la
	 * oficina con código <i>999</i> por introducir una contraseña incorrecta.
	 */
	public void testLoginOperador2WrongPassword() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("operador2");
		value.setPassword("password");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		try {
			client.wsLogin(security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Test que verifica que el usuario <i>sigem</i>(superusuario) puede
	 * autenticarse en la oficina con código <i>001</i>.
	 */
	public void testLoginSigemInOR1() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSSession wsLogin = client.wsLogin(security);

		assertNotNull(wsLogin);
		assertEquals(3, wsLogin.getId());
		assertEquals(StringUtils.EMPTY, wsLogin.getFullName());
		assertEquals("sigem", wsLogin.getName());
		assertEquals("001", wsLogin.getOfficeCode());
		assertEquals("OFICINA DE REGISTRO1", wsLogin.getOfficeName());
		assertEquals(3, wsLogin.getProfile());
	}

	/**
	 * Test que verifica que el usuario <i>sigem</i>(superusuario) puede
	 * autenticarse en la oficina con código <i>003</i>.
	 */
	public void testLoginSigemInOR2() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		WSSession wsLogin = client.wsLogin(security);

		assertNotNull(wsLogin);
		assertEquals(3, wsLogin.getId());
		assertEquals(StringUtils.EMPTY, wsLogin.getFullName());
		assertEquals("sigem", wsLogin.getName());
		assertEquals("003", wsLogin.getOfficeCode());
		assertEquals("OFICINA DE REGISTRO 2", wsLogin.getOfficeName());
		assertEquals(3, wsLogin.getProfile());
	}

	/**
	 * Test que verifica que el usuario <i>sigem</i>(superusuario) puede
	 * autenticarse en la oficina con código <i>999</i> (Oficina de registro
	 * telemático).
	 */
	public void testLoginSigemInORT() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("999");
		security.setUsernameToken(value);

		WSSession wsLogin = client.wsLogin(security);

		assertNotNull(wsLogin);
		assertEquals(3, wsLogin.getId());
		assertEquals(StringUtils.EMPTY, wsLogin.getFullName());
		assertEquals("sigem", wsLogin.getName());
		assertEquals("999", wsLogin.getOfficeCode());
		assertEquals("OFICINA DE REGISTRO TELEMATICO", wsLogin.getOfficeName());
		assertEquals(3, wsLogin.getProfile());
	}

	/**
	 * Verifica que un usuario no registrado no se puede autenticar.
	 */
	public void testLoginUnknownUser() {
		ISWebServiceSessionSoap client = (ISWebServiceSessionSoap) this.applicationContext
				.getBean(CLIENT_WEB_SERVICE_SESSION);
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("foo");
		value.setPassword("bar");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		try {
			client.wsLogin(security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

}
