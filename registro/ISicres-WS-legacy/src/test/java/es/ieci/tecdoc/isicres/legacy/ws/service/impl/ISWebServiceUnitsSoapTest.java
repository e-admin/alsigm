package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import es.ieci.tecdoc.isicres.ws.legacy.service.units.ISWebServiceUnitsSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnit;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitTypesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitsResponse;

public class ISWebServiceUnitsSoapTest extends
		AbstractDependencyInjectionSpringContextTests {

	protected String[] getConfigLocations() {
		String[] result = new String[] { "beans/appContextTest.xml" };
		return result;
	}

	public void testLoadUnitTypes() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitTypesResponse result = client.wsLoadUnitTypes(1, 100, security);
		assertNotNull(result);
		assertEquals(4, result.getTotal());
		assertEquals(4, result.getList().getWSUnitType().size());
		assertNotNull(CollectionUtils.find(result.getList().getWSUnitType(),
				new BeanPropertyValueEqualsPredicate("code", "R")));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnitType(),
				new BeanPropertyValueEqualsPredicate("code", "0")));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnitType(),
				new BeanPropertyValueEqualsPredicate("code", "A")));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnitType(),
				new BeanPropertyValueEqualsPredicate("code", "E")));
	}

	public void testValidateUnitCode() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnit result = client.wsValidateUnitCode("001", security);
		assertNotNull(result);
		assertEquals("001", result.getCode());
		assertNull(result.getFather());
		assertEquals(4887, result.getId());
		assertEquals("SERVICIO DE RELACIONES CON EL CIUDADANO", result
				.getName());
		assertEquals(1, result.getType().getId());
		assertTrue(result.isEnabled());
		
		result = client.wsValidateUnitCode("A04", security);
		assertNotNull(result);
		assertEquals(result.getCode(),"A04");
		
	}

	public void testValidateUnitCode2() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnit result = client.wsValidateUnitCode("xxx", security);
		assertNull(result);
	}

	public void testValidateUnitCode3() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnit result = client.wsValidateUnitCode("ER1", security);
		assertNotNull(result);
		assertEquals(4890, result.getId());
		assertNull(result.getFather());
		assertEquals("ENTIDAD REGISTRAL1", result.getName());
		assertEquals("ER1", result.getCode());
		assertTrue(result.isEnabled());
		assertEquals(0, result.getType().getId());
	}

	public void testLoadUnitsFromCondition() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitsResponse result = client.wsLoadUnitsFromCondition("TYPE = 1", 1,
				10, security);
		assertNotNull(result);
		assertEquals(3, result.getList().getWSUnit().size());
		assertEquals(3, result.getTotal());
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("code", "001")));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("code", "002")));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("code", "003")));
	}

	public void testLoadUnitsFromCondition3() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitsResponse result = client.wsLoadUnitsFromCondition("TYPE = 11",
				1, 10, security);
		assertNotNull(result);
		assertEquals(10, result.getList().getWSUnit().size());
		assertEquals(1802, result.getTotal());
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3085))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3086))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3087))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3088))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3089))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3090))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3091))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3092))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3093))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3094))));
	}

	public void testLoadUnitsFromCondition2() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitsResponse result = client.wsLoadUnitsFromCondition("TYPE = 999",
				1, 10, security);
		assertNotNull(result);
		assertEquals(0, result.getList().getWSUnit().size());
		assertEquals(0, result.getTotal());
	}

	public void testLoadUnitsFromCondition4() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitsResponse result = client.wsLoadUnitsFromCondition("code='A04'",
				1, 10, security);
		assertNotNull(result);
		assertEquals(1, result.getList().getWSUnit().size());
		assertEquals(1, result.getTotal());
		
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("code", "A04")));
		
		result = client.wsLoadUnitsFromCondition("code LIKE 'A04'",
				1, 10, security);
		assertNotNull(result);
		assertEquals(1, result.getList().getWSUnit().size());
		assertEquals(1, result.getTotal());
		
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("code", "A04")));
		
		result = client.wsLoadUnitsFromCondition("ID=2929",
				1, 10, security);
		assertNotNull(result);
		assertEquals(1, result.getList().getWSUnit().size());
		assertEquals(1, result.getTotal());
		
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(2929))));
		
	}

	public void testLoadUnitsFromType() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitsResponse result = client.wsLoadUnitsFromType("E", 1, 10,
				security);
		assertNotNull(result);
		assertEquals(10, result.getList().getWSUnit().size());
		assertEquals(14, result.getTotal());
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3085))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3086))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3087))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3088))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3089))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3090))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3091))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3092))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3093))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3094))));
	}

	public void testLoadUnitsFromTypePaginated() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitsResponse result = client.wsLoadUnitsFromType("E", 9, 20,
				security);
		assertNotNull(result);
		assertEquals(5, result.getList().getWSUnit().size());
		assertEquals(14, result.getTotal());
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3094))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3095))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3096))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3097))));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("id", Long.valueOf(3098))));
	}

	public void testLoadUnitsFromType2() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitsResponse result = client.wsLoadUnitsFromType("999", 1, 10,
				security);
		assertNotNull(result);
		assertEquals(0, result.getList().getWSUnit().size());
		assertEquals(0, result.getTotal());
	}

	public void testLoadUnitsFromUnit() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		/*
		WSUnitsResponse result = client.wsLoadUnitsFromUnit("4805", 0, 10,
				security);
				*/
		WSUnitsResponse result = client.wsLoadUnitsFromUnit("MEH91071", 1, 10,
				security);
		
		assertNotNull(result);
		assertEquals(2, result.getList().getWSUnit().size());
		assertEquals(2, result.getTotal());
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("code", "MEH910712")));
		assertNotNull(CollectionUtils.find(result.getList().getWSUnit(),
				new BeanPropertyValueEqualsPredicate("code", "MEH910711")));
	}

	public void testLoadUnitsFromUnit2() {
		ISWebServiceUnitsSoap client = (ISWebServiceUnitsSoap) this.applicationContext
				.getBean("clientWebServiceUnits");
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		security.setUsernameToken(value);

		WSUnitsResponse result = client.wsLoadUnitsFromUnit("9999", 1, 10,
				security);
		assertNotNull(result);
		assertEquals(0, result.getList().getWSUnit().size());
		assertEquals(0, result.getTotal());
	}
}
