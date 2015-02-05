package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import es.ieci.tecdoc.isicres.ws.legacy.service.matters.ISWebServiceMattersSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterType;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterTypesResponse;

public class ISWebServiceMattersSoapTest extends
		AbstractDependencyInjectionSpringContextTests {

	protected String[] getConfigLocations() {
		String[] result = new String[] { "beans/appContextTest.xml" };
		return result;
	}

	/**
	 * Verifica la recuperación de los diez primeros tipos de asunto para el
	 * usuario <i>sigem</i> autenticado en la oficina con código <i>001</i>. El
	 * número total debe ser 13.
	 */
	public void testLoadFirstTenMatterTypes() {
		ISWebServiceMattersSoap client = (ISWebServiceMattersSoap) this.applicationContext
				.getBean("clientWebServiceMatters");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSMatterTypesResponse result = client
				.wsLoadMatterTypes(1, 10, security);
		assertNotNull(result);
		assertEquals(13, result.getTotal());
		assertEquals(10, result.getList().getWSMatterType().size());
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TRQS")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TLIC")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TCTLA")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TCAPN")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TEXS")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TLAAC")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TLAANC")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TSLV")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TSRT")));
		assertNotNull(CollectionUtils.find(result.getList().getWSMatterType(),
				new BeanPropertyValueEqualsPredicate("code", "TSCU")));

	}

	/**
	 * Recupera el tipo de asunto con código <i>TLIC</i> para el usuario
	 * <i>sigem</i> autenticado en la oficina con código <i>001</i>.
	 */
	public void testValidateTLICMatterTypeCode() {
		ISWebServiceMattersSoap client = (ISWebServiceMattersSoap) this.applicationContext
				.getBean("clientWebServiceMatters");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSMatterType result = client.wsValidateMatterTypeCode("TLIC", security);
		assertNotNull(result);
		assertEquals("TLIC", result.getCode());
		assertEquals(3, result.getId());
		assertEquals("LICENCIA DE OBRA MENOR", result.getName());
		assertTrue(result.isInputMatterType());
		assertTrue(result.isOutputMatterType());
		assertTrue(result.isEnabled());
	}

	/**
	 * Intenta recuperar el tipo de asunto con código <i>TSUB</i>, pero no se
	 * devuelve por no estar disponible para la oficina con código <i>003</i>.
	 * Está disponible para la oficina con código <i>001</i>.
	 */
	public void testValidateTSUBMatterTypeCodeWithLoginInUnassociatedOffice() {
		ISWebServiceMattersSoap client = (ISWebServiceMattersSoap) this.applicationContext
				.getBean("clientWebServiceMatters");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("003");
		security.setUsernameToken(value);

		WSMatterType result = client.wsValidateMatterTypeCode("TSUB", security);
		assertNull(result);
	}

	/**
	 * Recupera el tipo de asunto con código <i>TSUB</i> estando autenticado en
	 * la oficina con código <i>001</i>, que es para la que está asociado el
	 * tipo de asunto.
	 */
	public void testValidateTSUBMatterTypeCodeWithLoginInAssociatedOffice() {
		ISWebServiceMattersSoap client = (ISWebServiceMattersSoap) this.applicationContext
				.getBean("clientWebServiceMatters");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSMatterType result = client.wsValidateMatterTypeCode("TSUB", security);
		assertNotNull(result);
		assertEquals("TSUB", result.getCode());
		assertEquals(2, result.getId());
		assertTrue(result.isEnabled());
		assertTrue(result.isInputMatterType());
		assertTrue(result.isOutputMatterType());
		assertEquals("SUBVENCIÓN", result.getName());
	}

	/**
	 * Recupera los diez primeros tipos de asunto que cumplen la condición
	 * <i>for_ereg = 0</i>. En este caso no hay ningún tipo de asunto que cumpla
	 * la condición.
	 */
	public void testMatterTypesFromConditionNotForInput() {
		ISWebServiceMattersSoap client = (ISWebServiceMattersSoap) this.applicationContext
				.getBean("clientWebServiceMatters");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSMatterTypesResponse result = client.wsMatterTypesFromCondition(
				"for_ereg = 0", 1, 10, security);
		assertNotNull(result);
		assertEquals(0, result.getTotal());
		assertEquals(0, result.getList().getWSMatterType().size());
	}

	/**
	 * Recupera los diez primeros tipos de asunto que cumplen la condición
	 * <i>for_ereg = 2</i>. En este caso no hay ningún tipo de asunto que cumpla
	 * la condición.
	 */
	public void testMatterTypesFromConditionInvalidFor_EregValue() {
		ISWebServiceMattersSoap client = (ISWebServiceMattersSoap) this.applicationContext
				.getBean("clientWebServiceMatters");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSMatterTypesResponse result = client.wsMatterTypesFromCondition(
				"for_ereg = 2", 1, 10, security);
		assertNotNull(result);
		assertEquals(0, result.getTotal());
		assertEquals(0, result.getList().getWSMatterType().size());
	}

	/**
	 * Recupera los diez primeros tipos de asunto que cumplen la condición
	 * <i>for_ereg = 1</i>. En este caso, el usuario se intenta autenticar en
	 * una oficina que no existe, por lo que no hay ningún tipo de asunto que
	 * cumpla la condición.
	 */
	public void testMatterTypesFromConditionWithLoginInUnknownOffice() {
		ISWebServiceMattersSoap client = (ISWebServiceMattersSoap) this.applicationContext
				.getBean("clientWebServiceMatters");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("900");
		security.setUsernameToken(value);

		try {
			client.wsMatterTypesFromCondition("for_ereg = 1", 1, 10, security);
		} catch (Exception e) {
			assertTrue(e instanceof SOAPFaultException);
			return;
		}
		fail();
	}

	/**
	 * Intenta recuperar el tipo de asunto con código <i>ADB</i>. No se devuelve
	 * por estar deshabilitado.
	 */
	public void testValidateDisabledMatterType() {
		ISWebServiceMattersSoap client = (ISWebServiceMattersSoap) this.applicationContext
				.getBean("clientWebServiceMatters");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSMatterType wsMatterType = client.wsValidateMatterTypeCode("ADB",
				security);

		assertNull(wsMatterType);
	}
}
